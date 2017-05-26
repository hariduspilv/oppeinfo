'use strict';

angular.module('hitsaOis')
  .controller('HigherCurriculumController', function ($scope, Classifier, Curriculum, dialogService, ArrayUtils, message, $route, $location, QueryUtils, oisFileService, DataUtils, $rootScope) {

    $scope.auth = $route.current.locals.auth;

    $scope.STATUS = Curriculum.STATUS;

    $scope.formState = {
        readOnly: $route.current.$$route.originalPath.indexOf("view") !== -1,
        sentToEhis: angular.isDefined($route.current.params._sentToEhis)
    };

    $scope.removeFromArray = function(array, item) {
        dialogService.confirmDialog({prompt: 'curriculum.itemDeleteConfirm'}, function() {
            ArrayUtils.remove(array, item);
        });
    };

    var baseUrl = '/curriculum';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var SpecialityEndpoint = QueryUtils.endpoint(baseUrl + '/speciality');
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
      status: Curriculum.STATUS.ENTERING,
      consecution: 'OPPEKAVA_TYPE_E',
      draft: 'OPPEKAVA_LOOMISE_VIIS_PUUDUB',
      joint: false,
      abroad: false,
      studyPeriodMonths: 0
    };

    if(!$scope.auth.school) {
        $scope.schoolDepartments = QueryUtils.endpoint('/autocomplete/schooldepartments').query();
    }

    // --- Get and Set Data
    $scope.studyLevels = [];
    function getAllowedStudyLevels() {
        if($scope.auth.school) {
            QueryUtils.endpoint('/school/studyLevels').search().$promise.then(function(response){
                var studyLevelCodes = response.studyLevels;

                studyLevelCodes.forEach(function(it) {
                    if(isHigherStudyLevel(it)) {
                        $scope.studyLevels.push({code: it});
                    }
                });
            });
        }
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
         $scope.currentStatus = Curriculum.STATUS.ENTERING;
      }
    }
    getCurriculum();

    function setVariablesForExistingCurriculum() {
        DataUtils.convertStringToDates($scope.curriculum, ["validFrom", "validThru", "approval", "ehisChanged", "accreditationDate", "accreditationValidDate", "merRegDate"]);
        setStudyPeriod();
        setAreaOfStudy();
        $scope.getAreasOfStudy();
        getJointPartners();
        getEhisSchoolsSelection();
        $scope.curriculum.abroad = false;

        $scope.formState.notEditableBasicData = $scope.curriculum.status === Curriculum.STATUS.VERIFIED;
        $scope.currentStatus = $scope.curriculum.status;
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

    $scope.removejointPartner = function(deletedPartner) {
        dialogService.confirmDialog({prompt: 'curriculum.itemDeleteConfirm'}, function() {
            ArrayUtils.remove($scope.curriculum.jointPartners, deletedPartner);
            if(!deletedPartner.abroad) {
                ArrayUtils.remove($scope.jointPartnersEhisSchools, deletedPartner.ehisSchool);
                if($scope.curriculum.jointMentor === deletedPartner.ehisSchool) {
                    $scope.curriculum.jointMentor = undefined;
                }
                deletePartersSubjects(deletedPartner.ehisSchool);
            }
        });
    };

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
        return $scope.higherCurriculumForm.$valid && (!$scope.strictValidation() || $scope.curriculum.specialities.length > 0 && $scope.jointPartnersAdded());
    }

    $scope.jointPartnersAdded = function() {
        return !$scope.curriculum.joint || $scope.curriculum.jointPartners.length > 0;
    };

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

    function save(messages) {
      $scope.higherCurriculumForm.$setSubmitted();

      if($scope.curriculum.joint) {
          addSharedInformationToJointPartners();
      } else {
          $scope.curriculum.jointMentor = undefined;
          $scope.curriculum.jointPartners = [];
      }

      if (!curriculumFormIsValid()) {
          var errorMessage = messages && messages.errorMessage ? messages.errorMessage : 'main.messages.form-has-errors';
          message.error(errorMessage);
          return;
      } else if ($scope.curriculum.status === Curriculum.STATUS.PROCEEDING && ArrayUtils.isEmpty($scope.curriculum.versions)) {
          message.error('curriculum.error.noVersion');
          return;
      } 
      clearGradesIfNecessary();
        $scope.curriculum.iscedClass = $scope.curriculum.fieldOfStudy ?
        $scope.curriculum.fieldOfStudy : $scope.curriculum.areaOfStudy;

        $scope.curriculum.studyPeriodMonths = $scope.curriculum.studyPeriodMonths ? $scope.curriculum.studyPeriodMonths : 0;
        $scope.curriculum.studyPeriod = $scope.curriculum.studyPeriodMonths + 12 * $scope.curriculum.studyPeriodYears;

        if($scope.curriculum.id) {
            $scope.curriculum.$update().then(function(){
                var updateSuccess = messages && messages.updateSuccess ? messages.updateSuccess : 'main.messages.create.success';
                message.info(updateSuccess);
                setVariablesForExistingCurriculum();
                if($scope.curriculum.status === Curriculum.STATUS.CLOSED) {
                    $location.path('/higherCurriculum/'+ $scope.curriculum.id +'/view').search({});
                }
            });
        } else {
            $scope.curriculum.$save().then(function(response){
                message.info('main.messages.create.success');
                $location.path('/higherCurriculum/'+ response.id +'/edit');
            });
        }
    }

    $scope.save = function () {
        $scope.curriculum.status = $scope.currentStatus;
        setTimeout(save, 0);
    };

    $scope.getStar = function() {
        return $scope.strictValidation() ? '' : ' *';
    };

    $scope.strictValidation = function() {
        // return $scope.curriculum && ($scope.curriculum.status !== Curriculum.STATUS.ENTERING || $scope.curriculum.status !== Curriculum.STATUS.CLOSED);
        return $scope.curriculum && ($scope.curriculum.status === Curriculum.STATUS.VERIFIED || $scope.curriculum.status === Curriculum.STATUS.PROCEEDING);
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
        $scope.myEhisSchool = $rootScope.currentUser.school ? $rootScope.currentUser.school.ehisSchool : null;
        $scope.jointPartnersEhisSchools.push($scope.myEhisSchool);
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
        scope.readOnly = $scope.formState.readOnly;
      };

      dialogService.showDialog('higherCurriculum/higher.curriculum.specialty.add.dialog.html', DialogController,
        function (submitScope) {
            var data = submitScope.data;
            var spec;
            if(!data.occupation) {
                data.occupationEt = undefined;
                data.occupationEn = undefined;
            }
            if(editedSpecialty) {
                 if($scope.curriculum.id) {
                    spec = new SpecialityEndpoint(data);
                    spec.$update().then(function(response){
                        message.info('main.messages.update.success');
                        angular.extend(editedSpecialty, response);
                    });
                } else {
                    angular.extend(editedSpecialty, data);
                }

            } else {
                data.referenceNumber = getReferenceNumber($scope.curriculum.specialities);
                if($scope.curriculum.id) {
                    spec = new SpecialityEndpoint(data);
                    spec.curriculum = $scope.curriculum.id;
                    spec.$save().then(function(responses){
                        message.info('main.messages.create.success');
                        $scope.curriculum.specialities.push(responses);
                    });
                } else {
                    $scope.curriculum.specialities.push(data);
                }    
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

        dialogService.confirmDialog({prompt: 'curriculum.itemDeleteConfirm'}, function() {

            if(specAddedToVersion(speciality)) {
                message.error('curriculum.error.specAddedToVersion');
                return;
            }
            if(speciality.id) {
                var spec = new SpecialityEndpoint(speciality);
                spec.$delete().then(function(){
                    message.info('main.messages.delete.success');
                    ArrayUtils.remove($scope.curriculum.specialities, speciality);
                });
            } else {
                ArrayUtils.remove($scope.curriculum.specialities, speciality);
            }
        });
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
          scope.readOnly = $scope.formState.readOnly;
        };
      }
      dialogService.showDialog('higherCurriculum/higher.curriculum.grade.add.dialog.html', DialogController,
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
      dialogService.showDialog('vocationalCurriculum/file.add.dialog.html', null, function (submitScope) {
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

   function setStatus(newStatus, messages) {
        dialogService.confirmDialog({prompt: messages.prompt}, function() {
            $scope.curriculum.status = newStatus;
            // setTimeout is needed for validation of ng-required fields
            setTimeout(function(){
                save(messages);
            }, 0);
        });
    }

    $scope.setStatusProceed = function() {
        var messages = {
            prompt: $scope.formState.readOnly ? 'curriculum.statuschangeReadOnly.higher.proceed' : 'curriculum.statuschange.higher.proceed',
            errorMessage: 'curriculum.error.inputFieldsNotFilledOnProcede',
            updateSuccess: 'curriculum.success.proceed'
        };
        setStatus(Curriculum.STATUS.PROCEEDING, messages);
    };

    $scope.setStatusClosed = function() {
        var messages = {
            prompt: $scope.formState.readOnly ? 'curriculum.statuschangeReadOnly.higher.close' : 'curriculum.statuschange.higher.close'
        };
        setStatus(Curriculum.STATUS.CLOSED, messages);
    };


    $scope.goToEditForm = function() {
        if(!$scope.curriculum) {
            return;
        }
        if($scope.curriculum.status === Curriculum.STATUS.VERIFIED) {
            dialogService.confirmDialog({
            prompt: 'curriculum.prompt.editAccepted', 
            }, function(){
                $location.path('/higherCurriculum/' + $scope.curriculum.id + '/edit');
            });
        } else {
            $location.path('/higherCurriculum/' + $scope.curriculum.id + '/edit');
        }
    };

    $scope.canBeEdited = function (){
        //TODO: add user role check
        var status = $scope.curriculum ? $scope.curriculum.status : null;
        return $scope.formState.readOnly && (status === Curriculum.STATUS.ENTERING || status === Curriculum.STATUS.PROCEEDING || status === Curriculum.STATUS.VERIFIED);
    };

    $scope.sendToEhis = function() {
        message.info("curriculum.sentToEhis");
        // $location.path('/vocationalCurriculum/' + $scope.curriculum.id + '/view?_senttoehis');
        $location.path('/higherCurriculum/' + $scope.curriculum.id + '/view').search({_sentToEhis: true});
    };

    $scope.updateFromEhis = function() {
        $scope.curriculum.status = Curriculum.STATUS.VERIFIED;
        save({updateSuccess: "curriculum.message.statusUpdated"});
    };

    $scope.goToVersionForm = function(version) {
        if($scope.formState.readOnly || version.status !== Curriculum.VERSION_STATUS.S) {
            $location.path('/higherCurriculum/' + $scope.curriculum.id + '/version/' + version.id + '/view');
        } else {
            $location.path('/higherCurriculum/' + $scope.curriculum.id + '/version/' + version.id + '/edit');
        }
    };


  });
