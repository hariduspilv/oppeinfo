'use strict';

angular.module('hitsaOis')
  .controller('HigherCurriculumController', function ($scope, Classifier, Curriculum, dialogService, ArrayUtils, message, $route, $location, QueryUtils, oisFileService, DataUtils, $rootScope, config) {

    $scope.auth = $route.current.locals.auth;

    $scope.STATUS = Curriculum.STATUS;

    $scope.maxStydyYears = {max: 100};

    var CurriculumFileEndpoint;
    var baseUrl = '/curriculum';
    var id = $route.current.params.id;

    $scope.formState = {
        readOnly: $route.current.$$route.originalPath.indexOf("view") !== -1,
        curriculumPdfUrl: config.apiUrl + baseUrl + '/print/' + id
    };

    $scope.removeFromArray = function(array, item) {
        dialogService.confirmDialog({prompt: 'curriculum.itemDeleteConfirm'}, function() {
            ArrayUtils.remove(array, item);
        });
    };

    var Endpoint = QueryUtils.endpoint(baseUrl);
    var SpecialityEndpoint = QueryUtils.endpoint(baseUrl + '/speciality');

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
      studyPeriodMonths: 0,
      validFrom: new Date()
    };

    if(!$scope.auth.school) {
        $scope.schoolDepartments = QueryUtils.endpoint('/autocomplete/schooldepartments').query();
    }

    // --- Get and Set Data
    $scope.studyLevels = [];
    function getAllowedStudyLevels() {
        if($scope.auth.school) {
            QueryUtils.endpoint('/school/studyLevels').search().$promise.then(function(response) {
                $scope.studyLevels = response.studyLevels.filter(isHigherStudyLevel);
            });
        }
    }
    getAllowedStudyLevels();

    function isHigherStudyLevel(studyLevelCode) {
        return parseInt(studyLevelCode.substring(studyLevelCode.length - 3)) >= 500;
    }

    $scope.areasOfStudy = [];
    $scope.getAreasOfStudy = function(listOfGroupsChanged) {
        if(listOfGroupsChanged) {
            $scope.curriculum.areaOfStudy = undefined;
        }
        if($scope.curriculum.group) {
            Curriculum.getAreasOfStudyByGroupOfStudy($scope.curriculum.group, function(response){
              $scope.areasOfStudy = response;
            });
        }
    };

    $scope.clearIscedClass = function() {
        $scope.curriculum.fieldOfStudy = undefined;
    };

    function getCurriculum() {
      if (id) {
        Endpoint.get({ id: id }).$promise.then(function (response) {
          CurriculumFileEndpoint = QueryUtils.endpoint(baseUrl + '/' + id + '/file');
          $scope.curriculum = response;
          setVariablesForExistingCurriculum();
        });
      } else {
        $scope.curriculum = new Endpoint(initialCurriculumScope);
         getEhisSchoolsSelection();
        //  $scope.currentStatus = Curriculum.STATUS.ENTERING;
         $scope.formState.curriculum = {status: $scope.curriculum.status};
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
        $scope.formState.sentToEhis = $scope.curriculum.ehisStatus === 'OPPEKAVA_EHIS_STAATUS_A' || $scope.curriculum.ehisStatus === 'OPPEKAVA_EHIS_STAATUS_M';
        // $scope.currentStatus = $scope.curriculum.status;
        $scope.formState.curriculum = {
          status: $scope.curriculum.status,
          ehisStatus: $scope.curriculum.ehisStatus,
          ehisChanged: $scope.curriculum.ehisChanged
        };
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
        return $scope.higherCurriculumForm.$valid &&
        (!$scope.strictValidation() || $scope.curriculum.specialities.length > 0 && $scope.jointPartnersAdded());
    }

    $scope.jointPartnersAdded = function() {
        return !$scope.curriculum.joint || $scope.curriculum.jointPartners.length > 0;
    };

    $scope.codeUniqueQuery = {
      id: id,
      url: baseUrl + '/unique/code'
    };

    $scope.merCodeUniqueQuery = {
      id: id,
      url: baseUrl + '/unique/merCode'
    };

    // --- Save and Delete

    function updateJointInfo() {
      if($scope.curriculum.joint) {
          addSharedInformationToJointPartners();
          if(jointInfoAddedButNotPartners()) {
              var jointPartner = {
                abroad: $scope.curriculum.abroad,
                supervisor: $scope.curriculum.supervisor,
                contractEt: $scope.curriculum.contractEt,
                contractEn: $scope.curriculum.contractEn
              };
              $scope.curriculum.jointPartners.push(jointPartner);
          }
      } else {
          $scope.curriculum.jointMentor = undefined;
          $scope.curriculum.jointPartners = [];
      }
    }

    function jointInfoAddedButNotPartners() {
        return ArrayUtils.isEmpty($scope.curriculum.jointPartners) && (
            angular.isDefined($scope.curriculum.supervisor) ||
            angular.isDefined($scope.curriculum.contractEt) ||
            angular.isDefined($scope.curriculum.contractEn)
        );
    }

   $scope.hasVerifiedVersions = function() {
      var versions = $scope.curriculum.versions;
      if(ArrayUtils.isEmpty(versions)) {
        return false;
      }
      return !ArrayUtils.isEmpty(versions.filter(function(el){
        return el.status === Curriculum.VERSION_STATUS.K;
      }));
    };

    function validationPassed(messages) {
      $scope.higherCurriculumForm.$setSubmitted();
      if (!curriculumFormIsValid()) {
          var errorMessage = messages && messages.errorMessage ? messages.errorMessage : 'main.messages.form-has-errors';
          message.error(errorMessage);
          return false;
      }
      if ($scope.strictValidation() && !$scope.hasVerifiedVersions()) {
          message.error('curriculum.error.noVersion');
          return false;
      }
      return true;
    }

    function goToReadOnlyForm() {
      if(!$scope.formState.readOnly) {
        $location.path('/higherCurriculum/'+ $scope.curriculum.id +'/view').search({_noback: true});
      }
    }

    function save(messages) {
        if(!validationPassed(messages)) {
          return;
        }
        updateJointInfo();
        clearGradesIfNecessary();
        $scope.curriculum.iscedClass = $scope.curriculum.fieldOfStudy ?
        $scope.curriculum.fieldOfStudy : $scope.curriculum.areaOfStudy;
        $scope.curriculum.studyPeriodMonths = $scope.curriculum.studyPeriodMonths ? $scope.curriculum.studyPeriodMonths : 0;
        $scope.curriculum.studyPeriod = $scope.curriculum.studyPeriodMonths + 12 * $scope.curriculum.studyPeriodYears;

        if($scope.curriculum.id) {
            $scope.curriculum.$update().then(function(){
                var updateSuccess = messages && messages.updateSuccess ? messages.updateSuccess : 'main.messages.create.success';
                message.info(updateSuccess);
                // if($scope.curriculum.status !== Curriculum.STATUS.ENTERING || $scope.curriculum.ehisStatus === 'OPPEKAVA_EHIS_STAATUS_A') {
                if($scope.curriculum.status !== $scope.formState.curriculum.status) {
                    goToReadOnlyForm();
                }
                setVariablesForExistingCurriculum();
            });
        } else {
            $scope.curriculum.newFiles = $scope.curriculum.files;
            $scope.curriculum.files = undefined;
            $scope.curriculum.$save().then(function(response){
                message.info('main.messages.create.success');
                $location.path('/higherCurriculum/'+ response.id +'/edit').search({_noback: true});
            });
        }
    }

    $scope.save = function () {
        // $scope.curriculum.status = $scope.currentStatus;
        $scope.curriculum.status = $scope.formState.curriculum.status;
        setTimeout(save, 0);
    };

    $scope.getStar = function() {
        return $scope.strictValidation() ? '' : ' *';
    };

    $scope.strictValidation = function() {
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
            if($scope.curriculum.jointPartners.length === 1 &&
            !($scope.curriculum.jointPartners[0].ehisSchool || $scope.curriculum.jointPartners[0].nameEt || $scope.curriculum.jointPartners[0].nameEn)) {
                $scope.curriculum.jointPartners = [];
            }
        }
    }

    $scope.filterEmptyJointPartners = function(jointPartner) {
        return jointPartner.ehisSchool || jointPartner.nameEt || jointPartner.nameEn;
    };

    // --- Dialog Windows

    $scope.openAddSpecialtyDialog = function (editedSpecialty) {
      var DialogController = function (scope) {
        if (editedSpecialty) {
            scope.data =  angular.extend({}, editedSpecialty);
        }
        scope.maxCredits = $scope.curriculum.credits ? $scope.curriculum.credits : 0;
        scope.readOnly = $scope.formState.readOnly;
        scope.isNew = !angular.isDefined(editedSpecialty);

        scope.delete = function() {
          if(specAddedToVersion(editedSpecialty)) {
            message.error('curriculum.error.specAddedToVersion');
            return;
          }
          dialogService.confirmDialog({ prompt: 'curriculum.itemDeleteConfirm' }, function () {
            ArrayUtils.remove($scope.curriculum.specialities, editedSpecialty);
            if (scope.data.id) {
              var spec = new SpecialityEndpoint($scope.curriculum);
              spec.$update().then(function (response) {
                message.info('main.messages.delete.success');
                $scope.curriculum.specialities = response.specialities;
              });
            }
            scope.cancel();
          });
        };
      };

      dialogService.showDialog('higherCurriculum/higher.curriculum.specialty.add.dialog.html', DialogController,
        function (submitScope) {
            var data = submitScope.data;
            if(!data.occupation) {
                data.occupationEt = undefined;
                data.occupationEn = undefined;
            }
            if (editedSpecialty) {
              $scope.curriculum.specialities.splice($scope.curriculum.specialities.indexOf(editedSpecialty), 1);
            }
            $scope.curriculum.specialities.push(data);
            if ($scope.curriculum.id) {
              var savedSpec = new SpecialityEndpoint($scope.curriculum);
              savedSpec.$update().then(function (response) {
                message.info('main.messages.create.success');
                $scope.curriculum.specialities = response.specialities;
              });
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
          scope.editing = angular.isDefined(editingGrade);

          scope.delete = function () {
            dialogService.confirmDialog({ prompt: 'curriculum.itemDeleteConfirm' }, function () {
              ArrayUtils.remove($scope.curriculum.grades, editingGrade);
              if (scope.data.id) {
                var GradeEndpoint = QueryUtils.endpoint('/curriculum/grade');
                var deletedGrade = new GradeEndpoint($scope.curriculum);
                deletedGrade.$update().then(function (response) {
                  message.info('main.messages.delete.success');
                  $scope.curriculum.grades = response.grades;
                });
              }
              scope.cancel();
            });
          };
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
          if ($scope.curriculum.id) {
            var GradeEndpoint = QueryUtils.endpoint('/curriculum/grade');
            var savedGrade = new GradeEndpoint($scope.curriculum);
            savedGrade.$update().then(function (response) {
              message.info('main.messages.create.success');
              $scope.curriculum.grades = response.grades;
            });
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
            if(CurriculumFileEndpoint) {
              var newFile = new CurriculumFileEndpoint(data);
              newFile.$save().then(function(response){
                message.info('main.messages.create.success');
                $scope.curriculum.files.push(response);
              });
            } else {
              $scope.curriculum.files.push(data);
            }
        });
      });
    };

    $scope.deleteFile = function(file) {
        dialogService.confirmDialog({prompt: 'curriculum.itemDeleteConfirm'}, function() {
            ArrayUtils.remove($scope.curriculum.files, file);
            if(CurriculumFileEndpoint) {
              var deletedFile = new CurriculumFileEndpoint(file);
              deletedFile.$delete().then(function () {
                message.info('main.messages.delete.success');
              });
            }
        });
    };

    $scope.getUrl = function(file) {
      return file.id ? config.apiUrl + '/oisfile/get/' + file.oisFile.id : oisFileService.getFileUrl(file.oisFile);
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
            prompt: $scope.formState.readOnly ? 'curriculum.statuschangeReadOnly.higher.close' : 'curriculum.statuschange.higher.close',
            updateSuccess: 'curriculum.success.closed'
        };

        dialogService.confirmDialog({prompt: messages.prompt}, function() {
            var ClosingEndpoint = QueryUtils.endpoint("/curriculum/close");
            var closedCurriculum = new ClosingEndpoint($scope.curriculum);
            closedCurriculum.$update().then(function(response){
              $scope.curriculum = response;
              message.info(messages.updateSuccess);
              setVariablesForExistingCurriculum();
              // $location.path('/higherCurriculum/' + $scope.curriculum.id +'/view').search({_noback: true});
              goToReadOnlyForm();
            });
        });
    };

    $scope.goToEditForm = function() {
        if(!$scope.curriculum) {
            return;
        }
        if($scope.curriculum.status === Curriculum.STATUS.VERIFIED) {
            dialogService.confirmDialog({
            prompt: 'curriculum.prompt.editAccepted',
            }, function(){
                $location.path('/higherCurriculum/' + $scope.curriculum.id + '/edit').search({_noback: true});
            });
        } else {
            $location.path('/higherCurriculum/' + $scope.curriculum.id + '/edit').search({_noback: true});
        }
    };

    $scope.canBeEdited = function (){
        //TODO: add user role check
        var status = $scope.curriculum ? $scope.curriculum.status : null;
        return $scope.formState.readOnly && (status === Curriculum.STATUS.ENTERING || status === Curriculum.STATUS.PROCEEDING || status === Curriculum.STATUS.VERIFIED);
    };

    $scope.sendToEhis = function() {
        $scope.curriculum.ehisStatus = 'OPPEKAVA_EHIS_STAATUS_A';
        $scope.curriculum.ehisChanged = new Date();
        save({updateSuccess: "curriculum.sentToEhis"});
    };

    $scope.updateFromEhis = function() {
        $scope.curriculum.status = Curriculum.STATUS.VERIFIED;
        $scope.curriculum.ehisStatus = 'OPPEKAVA_EHIS_STAATUS_R';
        $scope.curriculum.ehisChanged = new Date();
        save({updateSuccess: "curriculum.message.ehisStatusUpdated"});
    };

    $scope.goToVersionNewForm = function(){
        var url = '/higherCurriculum/' + $scope.curriculum.id + '/version/new';
        if(!$scope.formState.readOnly) {
            dialogService.confirmDialog({prompt: 'curriculum.prompt.goToVersionForm'}, function() {
                $location.path(url);
            });
        } else {
            $location.path(url);
        }
    };

    $scope.goToVersionForm = function(version) {
        var url = $scope.formState.readOnly || version.status !== Curriculum.VERSION_STATUS.S ? '/higherCurriculum/' + $scope.curriculum.id + '/version/' + version.id + '/view' :
        '/higherCurriculum/' + $scope.curriculum.id + '/version/' + version.id + '/edit';
        if(!$scope.formState.readOnly) {
            dialogService.confirmDialog({prompt: 'curriculum.prompt.goToVersionForm'}, function() {
                $location.path(url);
            });
        } else {
            $location.path(url);
        }
    };

    $scope.versionCanBeAdded = function() {
        return $scope.curriculum && (
            $scope.formState.curriculum.status === Curriculum.STATUS.ENTERING && !$scope.formState.readOnly ||
            $scope.formState.curriculum.status === Curriculum.STATUS.PROCEEDING ||
            $scope.formState.curriculum.status === Curriculum.STATUS.VERIFIED
        );
    };

  });
