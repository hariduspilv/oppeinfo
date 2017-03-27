'use strict';

angular.module('hitsaOis')
  .controller('HigherEducationCurriculumController', function ($scope, Classifier, Curriculum, Session, dialogService, ArrayUtils, message, $route, $location, QueryUtils, oisFileService, $translate, DataUtils, $rootScope) {

    $scope.formView = {
        curriculum: "curriculum",
        version: "version"
    };

    $scope.showMainForm = true;

    // TODO: finish readonly

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
      versions: [],
      jointPartners: [],
      higher: true,
      status: 'OPPEKAVA_STAATUS_S',
      consecution: 'OPPEKAVA_TYPE_E',
      draft: 'OPPEKAVA_LOOMISE_VIIS_PUUDUB',
      validFrom: new Date(),
      joint: false,
      abroad: false,
      optionalStudyCredits: 0   //TODO: which version to take when calculating this value? (probably current)
    };

    // --- Get and Set Data
    $scope.studyLevels = [];
    function getAllowedStudyLevels() {
        QueryUtils.endpoint('/school/studyLevels').get().$promise.then(function(response){
            var studyLevelCodes = response.studyLevels;

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
            Curriculum.getAreasOfStudyByGroupOfStudy($scope.curriculum.group, setValds);
        }
    };

    $scope.clearIscedClass = function() {
        $scope.curriculum.fieldOfStudy = undefined;
    };

    $scope.areasOfStudy = [];
    function setValds(response) {
        $scope.areasOfStudy = response.map(function(a){return a.code;});
    }

    function getCurriculum() {
      if (id) {
        Endpoint.get({ id: id }).$promise.then(function (response) {
          $scope.curriculum = response;
          setVariablesForExistingCurriculum();
        });
      } else {
        $scope.curriculum = new Endpoint(initialCurriculumScope);
         getEhisSchoolsSelection();
      }
    }
    getCurriculum();

    function setVariablesForExistingCurriculum() {
        DataUtils.convertStringToDates($scope.curriculum, ["validFrom", "validThru", "approval", "ehisChanged", "accreditationDate", "accreditationValidDate", "merRegDate"]);
        setStudyPeriod();
        setAreaOfStudy();
        $scope.getAreasOfStudy();
        getJointPartners();
        setReadOnly();
        getEhisSchoolsSelection();
        $scope.curriculum.abroad = false;
    }

    function setReadOnly() {
        $scope.readOnly = $scope.curriculum.status === "OPPEKAVA_STAATUS_K" || $scope.curriculum.status === "OPPEKAVA_STAATUS_C" ||
        $route.current.$$route.originalPath.indexOf("view") !== -1;
    }

    function setStudyPeriod() {
      var MONTHS_IN_YEAR = 12;
      $scope.curriculum.studyPeriodYears = Math.floor($scope.curriculum.studyPeriod / MONTHS_IN_YEAR);
      $scope.curriculum.studyPeriodMonths = $scope.curriculum.studyPeriod % MONTHS_IN_YEAR;
    }

    function setAreaOfStudy() {

        if($scope.curriculum.iscedClass) {
            if($scope.curriculum.iscedClass.indexOf("ISCED_VALD") !== -1) {
                $scope.curriculum.areaOfStudy = $scope.curriculum.iscedClass;
            } else {

                Classifier.getParentsWithMainClass('ISCED_VALD', $scope.curriculum.iscedClass).$promise.then(function (response) {
                    $scope.curriculum.areaOfStudy = response[0].code;
                    $scope.curriculum.fieldOfStudy = $scope.curriculum.iscedClass;
                });
            }
        }
    }

    //TODO: method currently not used
    // function getMyEhisSchool() {
    //   if($rootScope.currentUser && Session.school.id) {
    //     QueryUtils.endpoint("/school").get({id: Session.school.id}).$promise.then(function(response) {
    //       $scope.myEhisSchool = response.ehisSchool;
    //     });
    //   }
    // }
    // getMyEhisSchool();


    $scope.removejointPartner = function(deletedPartner) {
        // old solution
        $scope.removeFromArray($scope.curriculum.jointPartners, deletedPartner);
        if(!deletedPartner.abroad) {
            $scope.removeFromArray($scope.jointPartnersEhisSchools, deletedPartner.ehisSchool);
            if($scope.curriculum.jointMentor === deletedPartner.ehisSchool) {
                $scope.curriculum.jointMentor = undefined;
            }
            deletePartersSubjects(deletedPartner.ehisSchool);
        }

        // if(!deletedPartner.abroad && anySubjectAddedFromThisSchool(deletedPartner)) {
        //     dialogService.confirmDialog({prompt: 'curriculum.prompt.deleteJointPartner'}, function() {
        //         removeFinallyJointPartner(deletedPartner);
        //     });
        // } else {
        //     removeFinallyJointPartner(deletedPartner);
        // }
    };

    function removeFinallyJointPartner(deletedPartner) {
        $scope.removeFromArray($scope.curriculum.jointPartners, deletedPartner);
        if(!deletedPartner.abroad) {
            $scope.removeFromArray($scope.jointPartnersEhisSchools, deletedPartner.ehisSchool);
            if($scope.curriculum.jointMentor === deletedPartner.ehisSchool) {
                $scope.curriculum.jointMentor = undefined;
            }
            deletePartersSubjects(deletedPartner.ehisSchool);
        }
    }

    function anySubjectAddedFromThisSchool(deletedPartner) {
        var anyAdded = false;
        $scope.curriculum.versions.forEach(function(v){
            v.modules.forEach(function(m){
                m.subjects.forEach(function(s){
                    if(s.ehisSchool === deletedPartner.ehisSchool) {
                        anyAdded = true;
                    }
                });
                // var removedSubjects = m.subjects.filter(function(s){
                //     return s.ehisSchoolCode === partnersEhisSchool.ehisSchool;
                // });
                // removedSubjects.forEach(function(rs){
                //     $scope.deleteSubject(m, rs);
                // });
            });
        });
        return anyAdded;
        // return true;
    }

    // TODO: removal in iteration
    function deletePartersSubjects(partnersEhisSchool) {
        $scope.curriculum.versions.forEach(function(v){
            v.modules.forEach(function(m){
                var removedSubjects = m.subjects.filter(function(s){
                    return s.ehisSchoolCode === partnersEhisSchool;
                });
                removedSubjects.forEach(function(rs){
                    $scope.deleteSubject(m, rs);
                });
            });
        });
    }

    // --- Validation

    $scope.gradeRequired = function() {
        return !$scope.curriculum || !$scope.curriculum.origStudyLevel || $scope.curriculum.origStudyLevel !== 'OPPEASTE_514';
    };

    function curriculumFormIsValid() {
        return $scope.higherEducationCurriculumForm.$valid && $scope.curriculum.specialities.length > 0 && $scope.jointPartnersAdded()
        //  && allVersionsValid()
         ;
    }

    $scope.jointPartnersAdded = function() {
        return !$scope.curriculum.joint || $scope.curriculum.jointPartners.length > 0;
    };


    function allVersionsValid() {
        if(!$scope.curriculum.versions || $scope.curriculum.versions.length === 0) {
            return true;
        }
        for(var i = 0; i < $scope.curriculum.versions.length; i++) {
            if(!$scope.versionIsValid($scope.curriculum.versions[i])) {
                return false;
            }
        }
        return true;
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

      if($scope.curriculum.joint) {
          addSharedInformationToJointPartners();
      } else {
          $scope.curriculum.jointMentor = undefined;
          $scope.curriculum.jointPartners = [];
      }

      if (!curriculumFormIsValid()) {
        message.error('main.messages.form-has-errors');
        return;
      }
      clearGradesIfNecessary();
        $scope.curriculum.iscedClass = $scope.curriculum.fieldOfStudy ?
        $scope.curriculum.fieldOfStudy : $scope.curriculum.areaOfStudy;
        if($scope.curriculum.id) {
            $scope.curriculum.$update().then(function(){
                message.updateSuccess();
                setVariablesForExistingCurriculum();
            });
        } else {
            $scope.curriculum.$save().then(function(response){
                message.info('main.messages.create.success');
                $location.path('/higherEducationCurriculum/'+ response.id +'/edit');
            });
        }
    };

    $scope.changeJointMentors = function() {
        if($scope.curriculum.abroad) {
            $scope.jointPartnersEhisSchools = [];
        }
    };

    function clearGradesIfNecessary() {
        if(!$scope.gradeRequired()) {
            $scope.curriculum.grades = [];
        }
    }

    $scope.delete = function () {
        dialogService.confirmDialog({prompt: 'curriculum.deleteconfirmHigher'}, function() {
            $scope.curriculum.$delete().then(function () {
                message.info('main.messages.delete.success');
                $location.path(baseUrl);
            });
        });
    };

    function getEhisSchoolsSelection() {
        $scope.jointPartnersEhisSchools = [];
        $scope.myEhisSchool = $rootScope.currentUser.school.ehisSchool;
        $scope.jointPartnersEhisSchools.push($rootScope.currentUser.school.ehisSchool);
        $scope.curriculum.jointPartners.forEach(function(e){
            if(e.ehisSchool) {
                $scope.jointPartnersEhisSchools.push(e.ehisSchool);
            }
        });
    }

    $scope.addJointPartner = function () {
        var jointPartner = {abroad: $scope.curriculum.abroad};
        if (jointPartner.abroad) {
            jointPartner.nameEt = $scope.curriculum.jointPartnerForeignNameEt;
            jointPartner.nameEn = $scope.curriculum.jointPartnerForeignNameEn;
        } else {
            for(var i = 0; i < $scope.jointPartnersEhisSchools.length; i++) {
                if( $scope.jointPartnersEhisSchools.indexOf($scope.curriculum.jointPartnerEhisSchool) !== -1) {
                    $scope.clearJointPartnersFields();
                    return;
                }
            }
            jointPartner.ehisSchool = $scope.curriculum.jointPartnerEhisSchool;
            $scope.jointPartnersEhisSchools.push(jointPartner.ehisSchool);
        }
        $scope.curriculum.jointPartners.push(jointPartner);
        $scope.clearJointPartnersFields();
    };

    var addSharedInformationToJointPartners = function () {
      $scope.curriculum.jointPartners.forEach(function(it) {
        it.contractEt = $scope.curriculum.contractEt;
        it.contractEn = $scope.curriculum.contractEn;
        it.supervisor = $scope.curriculum.supervisor;
      });
    };

    $scope.clearJointPartnersFields = function() {
        $scope.curriculum.jointPartnerForeignNameEt = undefined;
        $scope.curriculum.jointPartnerForeignNameEn = undefined;
        $scope.curriculum.jointPartnerEhisSchool = undefined;
    };

    function getJointPartners() {
        if($scope.curriculum.jointPartners && $scope.curriculum.jointPartners.length > 0) {
            $scope.curriculum.abroad = false;
            $scope.curriculum.contractEt = $scope.curriculum.jointPartners[0].contractEt;
            $scope.curriculum.contractEn = $scope.curriculum.jointPartners[0].contractEn;
            $scope.curriculum.supervisor = $scope.curriculum.jointPartners[0].supervisor;
        }
    }

    // --- Dialog Windows

    $scope.openAddSpecialtyDialog = function (editedSpecialty) {
      var DialogController = function (scope) {
        if (editedSpecialty) {
            scope.data =  angular.extend({}, editedSpecialty);
        }
        scope.maxCredits = $scope.curriculum.credits ? $scope.curriculum.credits : 0;
      };

      dialogService.showDialog('higherEducationCurriculum/higher.education.curriculum.specialty.add.dialog.html', DialogController,
        function (submitScope) {
            var data = submitScope.data;
            if(!data.occupation) {
                data.occupationEt = undefined;
                data.occupationEn = undefined;
            }
            if(editedSpecialty) {
                angular.extend(editedSpecialty, data);
            } else {
                data.referenceNumber = getReferenceNumber($scope.curriculum.specialities);
                $scope.curriculum.specialities.push(data);
            }
        });
    };

    function getReferenceNumber(list) {
        var existingReferenceNumbers = list.map(function(el){
            return el.referenceNumber;
        });
        var rand;
        while(true) {
            rand = - Math.floor((Math.random() * 1000) + 1);
            if(!ArrayUtils.includes(existingReferenceNumbers, rand)) {
                break;
            }
        }
        return rand;
    }

    $scope.removeSpeciality = function(speciality) {
        if(specAddedToVersion(speciality)) {
            message.error('curriculum.error.specAddedToVersion');
            return;
        }
        $scope.removeFromArray($scope.curriculum.specialities, speciality);
    };

    function specAddedToVersion(spec) {
        var spescIds = [];
        $scope.curriculum.versions.forEach(function(v){
            spescIds = spescIds.concat(v.specialitiesReferenceNumbers);
        });
        return spescIds.indexOf(spec.id) !== -1;
    }

    $scope.openAddGradeDialog = function (editingGrade) {
      var DialogController = null;
      if (editingGrade) {
        DialogController = function (scope) {
          scope.data = angular.extend({}, editingGrade);
        };
      }
      dialogService.showDialog('higherEducationCurriculum/higher.education.curriculum.grade.add.dialog.html', DialogController,
        function (submitScope) {
          var data = submitScope.data;
            if(editingGrade) {
                angular.extend(editingGrade, data);
            } else {
                $scope.curriculum.grades.push(data);
            }
        });
    };

    $scope.openAddFileDialog = function () {
      dialogService.showDialog('higherEducationCurriculum/vocationalCurriculum/file.add.dialog.html', null, function (submitScope) {
        var data = submitScope.data;
        data.sendEhis = false;
        data.ehis = false;
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

    $scope.setStatus = function(newStatus) {
      $scope.curriculum.status = newStatus.code;
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
        var currentStatusCode = $scope.curriculum.status;
        var enabledStatusesToChange = {
            'OPPEKAVA_STAATUS_S': ['OPPEKAVA_STAATUS_M', 'OPPEKAVA_STAATUS_C'],
            'OPPEKAVA_STAATUS_M': ['OPPEKAVA_STAATUS_S', 'OPPEKAVA_STAATUS_K'],
            'OPPEKAVA_STAATUS_K': ['OPPEKAVA_STAATUS_M', 'OPPEKAVA_STAATUS_C'],
            'OPPEKAVA_STAATUS_C': ['OPPEKAVA_STAATUS_S', 'OPPEKAVA_STAATUS_M', 'OPPEKAVA_STAATUS_K'],
        };
        return enabledStatusesToChange[currentStatusCode].indexOf(status.code) !== -1;
    };











    // version form validation

    $scope.versionIsValid = function(version) {
        return version.specialitiesReferenceNumbers && version.specialitiesReferenceNumbers.length > 0 && $scope.anyModuleAdded(version) && allModulesValid(version);
    };


    function allModulesValid(version) {
        if(!version.modules) {
            return false;
        }
        for(var i = 0; i < version.modules.length; i++) {
            if(!$scope.moduleValid(version.modules[i])) {
                return false;
            }
        }
        return true;
    }

    $scope.moduleValid = function(module1) {
        return $scope.moduleHasSubjects(module1);
    };

    $scope.anyModuleAdded = function(version) {
        return version.modules.filter(function(el){return !el.minorSpeciality;}).length > 0;
    };

    $scope.moduleHasSubjects = function(module1) {
        if(module1.minorSpeciality) {
            return module1.subjects && module1.subjects.length > 0;
        } else {
            return module1.type === 'KORGMOODUL_L' || module1.type === 'KORGMOODUL_V' || module1.subjects && module1.subjects.length > 0;
        }
    };

    // modules and specialities

    // TODO: may be useful 
    function getAllSubjectIdsFromModules(modules) {
        var subjects = [];
        for(var i = 0; i < modules.length; i++) {
            for(var j = 0; j < modules[i].subjects.length; j++) {
                subjects.push(modules[i].subjects[j].subjectId);
            }
        }
        return subjects;
    }
  });
