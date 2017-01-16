'use strict';

angular.module('hitsaOis')
  .controller('HigherEducationCurriculumController', function ($scope, Classifier, StateCurriculum, Curriculum, dialogService, ClassifierConnect, ArrayUtils, $resource, config, message, $route, $location, $rootScope, QueryUtils, oisFileService, $translate, DataUtils) {

    $scope.readOnly = false;
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

    $scope.getAreasOfStudy = function(listOfGroupsChanged) {
        if(listOfGroupsChanged) {
            $scope.curriculum.iscedClass = undefined;
            $scope.curriculum.areaOfStudy = undefined;
        }
        if($scope.curriculum.group) {
            Curriculum.getAreasOfStudyByGroupOfStudy($scope.curriculum.group.code, setValds);
        }
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
        DataUtils.convertStringToDates($scope.curriculum, ["validFrom", "validThru", "approval", "ehisChanged", "accreditationDate", "accreditationValidDate"]);
        setStudyPeriod();
        setAreaOfStudy();
        $scope.getAreasOfStudy();
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
      Classifier.getParentsWithMainClass('ISCED_VALD', $scope.curriculum.iscedClass.code).$promise.then(function (response) {
        $scope.curriculum.areaOfStudy = response[0];
      });
    }

    function getDepartments() {
      QueryUtils.endpoint('/autocomplete/schooldepartments')
        .get({ lang: $translate.use().toUpperCase() }).$promise.then(function (result) {
          $scope.schoolDepartments = result.content;
        });
    }
    getDepartments();

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
      $scope.higherEducationCurriculumForm.$setSubmitted();
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
        data.oisFile = oisFileService.getFromLfFile(data.file[0]);
        $scope.curriculum.files.push(data);
      });
    };

    // TODO: not used yet
    $scope.getUrl = function(file) {
      return oisFileService.getFileUrl(file);
    };

    // --- Statuses

    $scope.setStatus = function(status) {
      $scope.curriculum.status = status;
    };

    function getStatuses() {
      Classifier.get('OPPEKAVA_STAATUS').$promise.then(function(response){
        $scope.statuses = response.children;
        setStatusButtonLabels();
        setStatusOrders();
      });
    }
    getStatuses();

    function setStatusButtonLabels() {
        var buttonLabels = {
            'OPPEKAVA_STAATUS_S': "main.button.status.edit",
            'OPPEKAVA_STAATUS_M': "main.button.status.approving",
            'OPPEKAVA_STAATUS_K': "main.button.status.confirm",
            'OPPEKAVA_STAATUS_C': "main.button.status.close"
        };
        $scope.statuses.forEach(function(it){
            it.button = buttonLabels[it.code];
        });
    }

    function setStatusOrders() {
        var statusCodes = ['OPPEKAVA_STAATUS_S', 'OPPEKAVA_STAATUS_M', 'OPPEKAVA_STAATUS_K', 'OPPEKAVA_STAATUS_C'];
        $scope.statuses.forEach(function(it){
            it.order = statusCodes.indexOf(it.code);
        });
    }

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
