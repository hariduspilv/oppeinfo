'use strict';

angular.module('hitsaOis')
  .controller('HigherEducationCurriculumController', function ($scope, Classifier, Curriculum, Session, dialogService, ArrayUtils, message, $route, $location, QueryUtils, oisFileService, $translate, DataUtils, $rootScope) {

    //   TODO: finish readonly

    $scope.removeFromArray = ArrayUtils.remove;

    var baseUrl = '/curriculum';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var id = $route.current.params.id;

    var initialCurriculumScope = {
      files: [],
      specialities: [],
      grades: [],
      studyLanguages: [],
      departments: [],
      higher: true,
      status: {code: 'OPPEKAVA_STAATUS_S'},
      consecution: {code: 'OPPEKAVA_TYPE_E'},
      draft: {code: 'OPPEKAVA_LOOMISE_VIIS_PUUDUB'},
      validFrom: new Date(),
      optionalStudyCredits: 0
    };

    // --- Get and Set Data

    function getAllowedStudyLevels() {
        QueryUtils.endpoint('/school/studyLevels').get().$promise.then(function(response){
            var studyLevelCodes = response.studyLevels;
            $scope.studyLevels = [];
            studyLevelCodes.forEach(function(it) {
                if(isHigherStudyLevel(it)) {
                    $scope.studyLevels.push({code: it});
                }
            });
        });
    }
    getAllowedStudyLevels();

    function isHigherStudyLevel(studyLevelCode) {
        return parseInt(studyLevelCode.substring(studyLevelCode.length - 3)) >= 500;
    }

    $scope.getAreasOfStudy = function(listOfGroupsChanged) {
        if(listOfGroupsChanged) {
            $scope.curriculum.areaOfStudy = undefined;
        }
        if($scope.curriculum.group) {
            Curriculum.getAreasOfStudyByGroupOfStudy($scope.curriculum.group.code, setValds);
        }
    };

    $scope.clearIscedClass = function() {
        $scope.curriculum.iscedClass = undefined;
    };

    function setValds(response) {
        $scope.areasOfStudy = response;
    }

    function getCurriculum() {
      if (id) {
        Endpoint.get({ id: id }).$promise.then(function (response) {
          $scope.curriculum = response;
          setVariablesForExistingCurriculum();
        });
      } else {
        $scope.curriculum = new Endpoint(initialCurriculumScope);
      }
    }
    getCurriculum();

    function setVariablesForExistingCurriculum() {
        $scope.curriculum.studyLanguageClassifiers = getFromWrapper($scope.curriculum.studyLanguages, "studyLang");
        $scope.curriculum.schoolDepartments = getFromWrapper($scope.curriculum.departments, "schoolDepartment");
        DataUtils.convertStringToDates($scope.curriculum, ["validFrom", "validThru", "approval", "ehisChanged", "accreditationDate", "accreditationValidDate", "merRegDate"]);
        setStudyPeriod();
        setAreaOfStudy();
        $scope.getAreasOfStudy();
        getJointPartners();
        setReadOnly();
    }

    function setReadOnly() {
        $scope.readOnly = $scope.curriculum.status.code === "OPPEKAVA_STAATUS_K" || $scope.curriculum.status.code === "OPPEKAVA_STAATUS_C" ||
        $route.current.$$route.originalPath.indexOf("view") !== -1;
    }

    function getFromWrapper(initialList, propertyName) {
        var list = [];
        if(initialList && propertyName) {
            initialList.forEach(function(it) {
                list.push(it[propertyName]);
            });
        }
        return list;
    }

    function setStudyPeriod() {
      var MONTHS_IN_YEAR = 12;
      $scope.curriculum.studyPeriodYears = Math.floor($scope.curriculum.studyPeriod / MONTHS_IN_YEAR);
      $scope.curriculum.studyPeriodMonths = $scope.curriculum.studyPeriod % MONTHS_IN_YEAR;
    }

    function setAreaOfStudy() {
        if($scope.curriculum.iscedClass) {
            Classifier.getParentsWithMainClass('ISCED_VALD', $scope.curriculum.iscedClass.code).$promise.then(function (response) {
                $scope.curriculum.areaOfStudy = response[0];
            });
        }
    }

    function getDepartments() {
      QueryUtils.endpoint('/autocomplete/schooldepartments')
        .get({ lang: $translate.use().toUpperCase() }).$promise.then(function (result) {
          $scope.schoolDepartments = result.content;
        });
    }
    getDepartments();

    function getMyEhisSchool() {
      if($rootScope.currentUser && Session.school.id) {
        QueryUtils.endpoint("/school").get({id: Session.school.id}).$promise.then(function(response) {
          $scope.myEhisSchool = {code: response.ehisSchool};
        });
      }
    }
    getMyEhisSchool();

    // --- Validation

    $scope.gradeRequired = function() {
        return !$scope.curriculum || !$scope.curriculum.origStudyLevel || $scope.curriculum.origStudyLevel.code !== 'OPPEASTE_514';
    };

    function formIsValid() {
        return $scope.higherEducationCurriculumForm.$valid && $scope.curriculum.specialities.length > 0 &&
        (!$scope.gradeRequired() || $scope.curriculum.grades.length > 0);
    }

    $scope.codeUniqueQuery = {
      id: id,
      paramName: 'code',
      url: baseUrl + '/unique'
    };

    $scope.merCodeUniqueQuery = {
      id: $scope.codeUniqueQuery.id,
      paramName: 'merCode',
      url: $scope.codeUniqueQuery.url
    };

    // --- Save and Delete

    $scope.save = function () {
        if($scope.readOnly) {
            return;
        }
      $scope.higherEducationCurriculumForm.$setSubmitted();

      setJointPartners();

      if (!formIsValid()) {
        message.error('main.messages.form-has-errors');
        return;
      }
      clearGradesIfNecessary();

      createOrUpdate().then(function() {
         setVariablesForExistingCurriculum();
         message.info('main.messages.create.success');
      }).catch(function () { message.error('main.messages.create.failure'); });
    };

    function setJointPartners() {
        if(!$scope.curriculum.joint) {
            $scope.curriculum.jointPartners = [];
            $scope.curriculum.jointMentor = null;
            return;
        }

        var commonFields = {
            'abroad': $scope.curriculum.abroad,
            'contractEt': $scope.curriculum.contractEt,
            'contractEn': $scope.curriculum.contractEn,
            'supervisor': $scope.curriculum.supervisor
        };
        $scope.curriculum.jointPartners = [];
        if($scope.curriculum.abroad !== undefined && $scope.curriculum.abroad === false) {
            $scope.curriculum.jointPartnersEhisSchools.forEach(function(it){
                var newPartner = angular.extend({}, commonFields);
                newPartner.ehisSchool = it;
                $scope.curriculum.jointPartners.push(newPartner);
            });
        } else if ($scope.curriculum.abroad) {
            $scope.curriculum.jointPartnersForeign.forEach(function(it){
                var newPartner = angular.extend({}, commonFields);
                newPartner.nameEt = it;
                $scope.curriculum.jointPartners.push(newPartner);
            });
        }
    }

    function getJointPartners() {
        if($scope.curriculum.jointPartners && $scope.curriculum.jointPartners.length > 0) {
            $scope.curriculum.abroad = $scope.curriculum.jointPartners[0].abroad;
            $scope.curriculum.contractEt = $scope.curriculum.jointPartners[0].contractEt;
            $scope.curriculum.contractEn = $scope.curriculum.jointPartners[0].contractEn;
            $scope.curriculum.supervisor = $scope.curriculum.jointPartners[0].supervisor;
            if($scope.curriculum.abroad) {
                $scope.curriculum.jointPartnersForeign = [];
                 $scope.curriculum.jointPartners.forEach(function(it){
                     $scope.curriculum.jointPartnersForeign.push(it.nameEt);
                 });
            } else {
                $scope.curriculum.jointPartnersEhisSchools = [];
                 $scope.curriculum.jointPartners.forEach(function(it){
                     $scope.curriculum.jointPartnersEhisSchools.push(it.ehisSchool);
                 });
            }
        }
    }

    $scope.changeJointMentors = function() {
        if($scope.curriculum.abroad) {
            $scope.curriculum.jointPartnersEhisSchools = [];
        }
    };

    function createOrUpdate() {
        return $scope.curriculum.id ? $scope.curriculum.$update() : $scope.curriculum.$save();
    }

    function clearGradesIfNecessary() {
        if(!$scope.gradeRequired()) {
            $scope.curriculum.grades = [];
        }
    }

    $scope.delete = function () {
      $scope.curriculum.$delete().then(function () {
        message.info('main.messages.delete.success');
        $location.path(baseUrl);
      });
    };

    // --- Dialog Windows

    $scope.openAddSpecialtyDialog = function (newSpecialty) {
      var DialogController = null;
      if (newSpecialty) {
        DialogController = function (scope) {
          scope.data = newSpecialty;
        };
      }
      dialogService.showDialog('views/templates/higher.education.specialty.add.dialog.html', DialogController,
        function (data) {
          if (!ArrayUtils.includes($scope.curriculum.specialities, data)) {
            $scope.curriculum.specialities.push(data);
          }
        });
    };

    $scope.openAddGradeDialog = function (newGrade) {
      var DialogController = null;
      if (newGrade) {
        DialogController = function (scope) {
          scope.data = newGrade;
        };
      }
      dialogService.showDialog('views/templates/higher.education.grade.add.dialog.html', DialogController,
        function (data) {
          if (!ArrayUtils.includes($scope.curriculum.grades, data)) {
            $scope.curriculum.grades.push(data);
          }
        });
    };

    $scope.openAddFileDialog = function () {
      dialogService.showDialog('views/templates/vocational.curriculum.file.add.dialog.html', null, function (data) {
        data.sendEhis = false;
        data.ehis = false;
        data.ehisFile = data.ehisFile;
        data.oisFile = oisFileService.getFromLfFile(data.file[0], function(file) {
            data.oisFile = file;
            $scope.curriculum.files.push(data);
        });
      });
    };

    $scope.getUrl = function(oisFile) {
      return oisFileService.getFileUrl(oisFile);
    };

    // --- Statuses

    $scope.setStatus = function(status) {
      $scope.curriculum.status = status;
    };

    $scope.statuses = [
        {code: 'OPPEKAVA_STAATUS_S', button: 'main.button.status.edit'},
        {code: 'OPPEKAVA_STAATUS_M', button: 'main.button.status.approving'},
        {code: 'OPPEKAVA_STAATUS_K', button: 'main.button.status.confirm'},
        {code: 'OPPEKAVA_STAATUS_C', button: 'main.button.status.close'}
    ];

    $scope.filterStatusChangeOptions = function(status) {
        if(!$scope.curriculum || !$scope.curriculum.status || !status) {
            return false;
        }
        var currentStatusCode = $scope.curriculum.status.code;
        var enabledStatusesToChange = {
            'OPPEKAVA_STAATUS_S': ['OPPEKAVA_STAATUS_M', 'OPPEKAVA_STAATUS_C'],
            'OPPEKAVA_STAATUS_M': ['OPPEKAVA_STAATUS_S', 'OPPEKAVA_STAATUS_K'],
            'OPPEKAVA_STAATUS_K': ['OPPEKAVA_STAATUS_M', 'OPPEKAVA_STAATUS_C'],
            'OPPEKAVA_STAATUS_C': ['OPPEKAVA_STAATUS_S', 'OPPEKAVA_STAATUS_M', 'OPPEKAVA_STAATUS_K'],
        };
        return enabledStatusesToChange[currentStatusCode].indexOf(status.code) !== -1;
    };
  });
