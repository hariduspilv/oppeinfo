'use strict';

angular.module('hitsaOis')
  .controller('HigherCurriculumController', function ($scope, Classifier, Curriculum, dialogService, ArrayUtils, message, $route, $location, QueryUtils, oisFileService, DataUtils, $rootScope, config, Session) {
    $scope.auth = $route.current.locals.auth;
    $scope.STATUS = Curriculum.STATUS;

    $scope.maxStydyYears = {max: 100};

    var CurriculumFileEndpoint;
    var baseUrl = '/curriculum';
    var id = $route.current.params.id;
    $scope.publicUrl = config.apiUrl + '/public/curriculum/' + id + '?format=json';

    $scope.formState = {
        readOnly: $route.current.$$route.originalPath.indexOf("view") !== -1,
        curriculumXmlUrl: config.apiUrl + baseUrl + '/xml/' + id + '/curriculum.xml',
        strictValidation: false
    };

    function getInitialSchoolDepartments() {
      if(id) {
        QueryUtils.endpoint('/curriculum/schoolDepartments/' + id).query().$promise.then(function(response){
          $scope.schoolDepartments = response;
        });
      } else {
        getSchoolDepartmentOptions();
      }
    }
    getInitialSchoolDepartments();

    $scope.removeFromArray = function(array, item) {
        dialogService.confirmDialog({prompt: 'curriculum.itemDeleteConfirm'}, function() {
            ArrayUtils.remove(array, item);
        });
    };

    var Endpoint = QueryUtils.endpoint(baseUrl);

    var initialCurriculumScope = {
      files: [],
      specialities: [],
      grades: [],
      studyLanguages: [],
      departments: [],
      versions: [],
      addresses: [],
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
    $scope.validation = {
      addressesLength: 0
    };

    // --- Get and Set Data
    $scope.studyLevels = [];
    function getAllowedStudyLevels() {
      QueryUtils.endpoint('/curriculum/studyLevels').query({curriculum: id, isHigher: true}).$promise.then(function(response) {
          $scope.studyLevels = response;
      });
    }
    getAllowedStudyLevels();

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

    function getEhisSchoolsSelection() {
        $scope.jointPartnersEhisSchools = [];
        $scope.myEhisSchool = $scope.curriculum.id ? $scope.curriculum.ehisSchool : Session.school.ehisSchool;
        $scope.jointPartnersEhisSchools.push($scope.myEhisSchool);
        $scope.curriculum.jointPartners.forEach(function(e){
            if(e.ehisSchool) {
                $scope.jointPartnersEhisSchools.push(e.ehisSchool);
            }
        });
    }

    if (id) {
      Endpoint.get({ id: id }).$promise.then(function (response) {
        CurriculumFileEndpoint = QueryUtils.endpoint(baseUrl + '/' + id + '/file');
        $scope.curriculum = response;
        setVariablesForExistingCurriculum();

        $rootScope.removeLastUrlFromHistory(function(lastUrl){
        return lastUrl === '#/higherCurriculum/' + $scope.curriculum.id + '/view' ||
               lastUrl === '#/higherCurriculum/' + $scope.curriculum.id + '/edit' ||
               lastUrl === '#/higherCurriculum/new';
        });
      });
    } else {
      $scope.curriculum = new Endpoint(initialCurriculumScope);
      getEhisSchoolsSelection();
      // $scope.currentStatus = Curriculum.STATUS.ENTERING;
      $scope.formState.curriculum = {status: $scope.curriculum.status};
    }

    function setVariablesForExistingCurriculum() {
        DataUtils.convertStringToDates($scope.curriculum, ["validFrom", "validThru", "approval", "ehisChanged", "accreditationDate", "accreditationValidDate", "merRegDate"]);
        setStudyPeriod();
        $scope.getAreasOfStudy();
        updateAddresses();
        getJointPartners();
        $scope.formState.jointDisabled = disableJoint();
        getEhisSchoolsSelection();
        $scope.curriculum.abroad = false;

        $scope.formState.notEditableBasicData = $scope.curriculum.status === Curriculum.STATUS.VERIFIED;
        $scope.formState.sentToEhis = $scope.curriculum.ehisStatus === 'OPPEKAVA_EHIS_STAATUS_A' || $scope.curriculum.ehisStatus === 'OPPEKAVA_EHIS_STAATUS_M';
        $scope.formState.strictValidation = false;
    }

    function setStudyPeriod() {
      var MONTHS_IN_YEAR = 12;
      $scope.curriculum.studyPeriodYears = Math.floor($scope.curriculum.studyPeriod / MONTHS_IN_YEAR);
      $scope.curriculum.studyPeriodMonths = $scope.curriculum.studyPeriod % MONTHS_IN_YEAR;
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
        removeJointInfo();
      }
    }

    function removeJointInfo() {
      $scope.curriculum.abroad = false;
      $scope.curriculum.jointPartners = [];

      $scope.curriculum.supervisor = undefined;
      $scope.curriculum.jointMentor = undefined;
      $scope.curriculum.contractEt = undefined;
      $scope.curriculum.contractEn = undefined;
    }

    function jointInfoAddedButNotPartners() {
        return ArrayUtils.isEmpty($scope.curriculum.jointPartners) && (
            angular.isDefined($scope.curriculum.supervisor) ||
            angular.isDefined($scope.curriculum.contractEt) ||
            angular.isDefined($scope.curriculum.contractEn)
        );
    }

    $scope.hasRequiredFiles = function() {
      var files = $scope.curriculum.files;
      if(ArrayUtils.isEmpty(files)) {
        return false;
      }
      return !ArrayUtils.isEmpty(files.filter(function(file){
        return file.ehisFile === 'EHIS_FAIL_15773' && file.sendEhis === true && 
         file.oisFile && file.oisFile.ftype === 'application/pdf';
      }));
    };

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
      if ($scope.strictValidation()) {
        if (!$scope.hasRequiredFiles()) {
          message.error('curriculum.error.noSummaryFile');
          return false;
        } else if (!$scope.hasVerifiedVersions()) {
          message.error('curriculum.error.noVersion');
          return false;
        }
      }
      return true;
    }

    function goToReadOnlyForm() {
      if(!$scope.formState.readOnly) {
        $location.path('/higherCurriculum/'+ $scope.curriculum.id +'/view');
      }
    }

    function mapModelToDto() {
      updateJointInfo();
      clearGradesIfNecessary();
      $scope.curriculum.studyPeriodMonths = $scope.curriculum.studyPeriodMonths ? $scope.curriculum.studyPeriodMonths : 0;
      $scope.curriculum.studyPeriod = $scope.curriculum.studyPeriodMonths + 12 * $scope.curriculum.studyPeriodYears;
    }

    function save(messages) {
      if(!validationPassed(messages)) {
        return;
      }
      mapModelToDto();
      if($scope.curriculum.id) {
        $scope.curriculum.$update().then(function(){
          var updateSuccess = messages && messages.updateSuccess ? messages.updateSuccess : 'main.messages.create.success';
          message.info(updateSuccess);
          setVariablesForExistingCurriculum();
          $scope.higherCurriculumForm.$setPristine();
        });
      } else {
        $scope.curriculum.newFiles = $scope.curriculum.files;
        $scope.curriculum.files = undefined;
        $scope.curriculum.$save().then(function(response){
          message.info('main.messages.create.success');
          $location.path('/higherCurriculum/'+ response.id +'/edit');
        });
      }
    }

    $scope.save = function () {
      $scope.formState.strictValidation = false;
      setTimeout(save, 0);
    };

    $scope.getStar = function() {
        return $scope.strictValidation() ? '' : ' *';
    };

    $scope.strictValidation = function() {
        return $scope.curriculum && ($scope.curriculum.status === Curriculum.STATUS.VERIFIED ||
          $scope.curriculum.status === Curriculum.STATUS.PROCEEDING || $scope.formState.strictValidation);
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

    $scope.$watch('curriculum.joint', function(newVal, oldVal){
      if(oldVal && !newVal) {
        removeJointInfo();
        getEhisSchoolsSelection();
        getSchoolDepartmentOptions();
      }
    });

    function getSchoolDepartmentOptions() {
      QueryUtils.endpoint('/curriculum/schoolDepartments').query({ehisShools: $scope.jointPartnersEhisSchools, curriculum: id}).$promise.then(function(response){
        $scope.schoolDepartments = response;
        /*
         * Although partners' school departments are not displayed in options,
         * they are not actually removed from the list of
         * curriculum's school department.
         * Code below handles that.
         */
        if($scope.curriculum.schoolDepartments) {
          var schoolDepartmentsIds = response.map(function(d){
            return d.id;
          });
          $scope.curriculum.schoolDepartments = $scope.curriculum.schoolDepartments.filter(function(d){
            return ArrayUtils.includes(schoolDepartmentsIds, d);
          });
        }
      });
    }

    $scope.removejointPartner = function(deletedPartner) {

        dialogService.confirmDialog({prompt: 'curriculum.itemDeleteConfirm'}, function() {
            ArrayUtils.remove($scope.curriculum.jointPartners, deletedPartner);
            if(!deletedPartner.abroad) {
                ArrayUtils.remove($scope.jointPartnersEhisSchools, deletedPartner.ehisSchool);
                if($scope.curriculum.jointMentor === deletedPartner.ehisSchool) {
                    $scope.curriculum.jointMentor = undefined;
                }
            }
            $scope.higherCurriculumForm.$setDirty();
            getSchoolDepartmentOptions();
        });
    };

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
        getSchoolDepartmentOptions();
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

    function disableJoint() {
      if(!$scope.curriculum.joint || ArrayUtils.isEmpty($scope.curriculum.jointPartners)) {
        return false;
      }
      return $scope.curriculum.jointPartners.some(function(p){
        return p.hasSubjects;
      });
    }

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

    function isAddressAdded(item) {
      return ArrayUtils.contains($scope.curriculum.addresses.map(function (address) {
        return address.addressOv;
      }), item.addressOv);
    }

    $scope.addAddress = function () {
      if (isAddressAdded($scope.formState.address)) {
        message.error("curriculum.error.addressAlreadyAdded");
        return;
      }
      $scope.curriculum.addresses.push($scope.formState.address);
      $scope.formState.address = {};
      updateAddresses();
    };

    function updateAddresses() {
      if (angular.isArray($scope.curriculum.addresses)) {
        $scope.validation.addressesLength = $scope.curriculum.addresses.length;
      }
    }

    $scope.removeAddress = function(item) {
      dialogService.confirmDialog({prompt: 'curriculum.itemDeleteConfirm'}, function() {
        ArrayUtils.remove($scope.curriculum.addresses, item);
        $scope.higherCurriculumForm.$setDirty();
        updateAddresses();
      });
    };

    // --- Dialog Windows

    function deleteListItem(list, scope, editedItem, ItemEndpoint) {
      dialogService.confirmDialog({prompt: 'curriculum.itemDeleteConfirm'}, function() {
        if(scope.data.id) {
        new ItemEndpoint(scope.data).$delete().then(function() {
          message.info('main.messages.delete.success');
          ArrayUtils.remove(list, editedItem);
        });
        } else {
          ArrayUtils.remove(list, editedItem);
          $scope.higherCurriculumForm.$setDirty();
        }
        scope.cancel();
      });
    }

    function saveListItem(ItemEndpoint, data, list, editedItem){
      if($scope.curriculum.id) {
        var savedItem = new ItemEndpoint(data);
        if(data.id) {
          savedItem.$update().then(function(response) {
              message.info('main.messages.update.success');
              angular.extend(editedItem, response);
          });
        } else {
          savedItem.$save().then(function(response) {
              message.info('main.messages.create.success');
              list.push(response);
          });
        }
      } else {
        if(editedItem) {
            angular.extend(editedItem, data);
        } else {
            list.push(data);
        }
        $scope.higherCurriculumForm.$setDirty();
      }
    }

    $scope.openAddSpecialtyDialog = function (editedSpecialty) {
      var DialogController = function (scope) {
        if (editedSpecialty) {
            scope.data =  angular.extend({}, editedSpecialty);
        }
        scope.maxCredits = $scope.curriculum.credits ? $scope.curriculum.credits : 0;
        scope.formState = {
          isNew: !angular.isDefined(editedSpecialty)
        };

        scope.delete = function() {
          if(scope.data.addedToVersion) {
            message.error("curriculum.error.specAddedToVersion");
            return;
          }
          deleteListItem($scope.curriculum.specialities, scope, editedSpecialty,
            QueryUtils.endpoint('/curriculum/' + $scope.curriculum.id + '/speciality'));
            $scope.higherCurriculumForm.$setDirty();
        };
      };

      dialogService.showDialog('higherCurriculum/higher.curriculum.specialty.add.dialog.html', DialogController,
        function (submitScope) {
            var data = submitScope.data;
            if(!data.occupation) {
                data.occupationEt = undefined;
                data.occupationEn = undefined;
            }
            saveListItem(QueryUtils.endpoint('/curriculum/' + $scope.curriculum.id + '/speciality'),
              data, $scope.curriculum.specialities, editedSpecialty);
        });
    };


    $scope.openViewSpecialtyDialog = function (editedSpecialty) {
      var DialogController = function (scope) {
        scope.data =  angular.extend({}, editedSpecialty);
      };
      dialogService.showDialog('higherCurriculum/higher.curriculum.specialty.view.dialog.html', DialogController, null);
    };

    $scope.openAddGradeDialog = function (editingGrade) {
      var DialogController = null;
      if (editingGrade) {
        DialogController = function (scope) {
          scope.data = angular.extend({}, editingGrade);
          scope.editing = angular.isDefined(editingGrade);
          scope.delete = function() {
            deleteListItem($scope.curriculum.grades, scope, editingGrade,
            QueryUtils.endpoint('/curriculum/' + $scope.curriculum.id + '/grade'));
          };
        };
      }
      dialogService.showDialog('higherCurriculum/higher.curriculum.grade.add.dialog.html', DialogController,
        function (submitScope) {
          var data = submitScope.data;

          saveListItem(QueryUtils.endpoint('/curriculum/' + $scope.curriculum.id + '/grade'),
             data, $scope.curriculum.grades, editingGrade);
        });
    };

    $scope.openViewGradeDialog = function (editingGrade) {
      var DialogController = function (scope) {
        scope.data = angular.extend({}, editingGrade);
      };
      dialogService.showDialog('higherCurriculum/higher.curriculum.grade.view.dialog.html', DialogController, null);
    };

    $scope.openAddFileDialog = function () {
      dialogService.showDialog('curriculum/dialog/file.add.dialog.html', function(scope){
        scope.fileTypeCriteria = {
          higher: true
        };
      }, function (submitScope) {
        var data = submitScope.data;
        data.sendEhis = false;
        data.ehis = false;
        data.oisFile = oisFileService.getFromLfFile(data.file[0], function(file) {
            data.oisFile = file;
            var newFile = new CurriculumFileEndpoint(data);
            newFile.$save().then(function(response){
              message.info('main.messages.create.success');
              $scope.curriculum.files.push(response);
            });
        });
      });
    };

    $scope.deleteFile = function(file) {
        dialogService.confirmDialog({prompt: 'curriculum.itemDeleteConfirm'}, function() {
            ArrayUtils.remove($scope.curriculum.files, file);
            var deletedFile = new CurriculumFileEndpoint(file);
            deletedFile.$delete().then(function () {
              message.info('main.messages.delete.success');
            });
        });
    };

    $scope.getUrl = oisFileService.getUrl;

    // --- Statuses

    $scope.saveAndProceed = function() {
      var messages = {
          prompt: 'curriculum.statuschange.higher.proceed',
          errorMessage: 'curriculum.error.inputFieldsNotFilledOnProcede',
          updateSuccess: 'curriculum.success.proceed'
      };
      $scope.formState.strictValidation = true;

      dialogService.confirmDialog({prompt: messages.prompt}, function() {
        // setTimeout is needed for validation of ng-required fields
        setTimeout(function(){

          if(!validationPassed(messages)) {
            return;
          }
          mapModelToDto();
          var SaveAndProceedEndpoint = QueryUtils.endpoint('/curriculum/saveAndProceed');
          changeStatus(SaveAndProceedEndpoint, messages);
        }, 0);
      });
    };

    $scope.changeStatusUnderRevision = function () {
      var messages = {
          updateSuccess: 'curriculum.success.opened'
      };
      dialogService.confirmDialog({prompt: "curriculum.prompt.setStatusUnderRevision"}, function () {
        setTimeout(function () {
          var setStatusEndpoint = QueryUtils.endpoint(baseUrl + '/underrevision');
          changeStatus(setStatusEndpoint, messages);
        });
      });
    };

    $scope.setStatusClosed = function() {

      var messages = {
        prompt: $scope.formState.readOnly ? 'curriculum.statuschangeReadOnly.higher.close' : 'curriculum.statuschange.higher.close',
        updateSuccess: 'curriculum.success.closed'
      };

      dialogService.confirmDialog({prompt: messages.prompt}, function() {
        var ClosingEndpoint = QueryUtils.endpoint("/curriculum/close");
        changeStatus(ClosingEndpoint, messages);
      });
    };

    $scope.sendToEhis = function(isTest) {
      dialogService.confirmDialog({ prompt: 'curriculum.ehisconfirm' }, function () {
        var SendToEhisEndpoint = QueryUtils.endpoint("/curriculum/sendToEhis" + (isTest ? "/test" : ""));
        changeStatus(SendToEhisEndpoint, {updateSuccess: 'curriculum.sentToEhis'});
      });
    };

    $scope.updateFromEhis = function(isTest) {
      var UpdateFromEhisEndpoint = QueryUtils.endpoint("/curriculum/updateFromEhis" + (isTest ? "/test" : ""));
      changeStatus(UpdateFromEhisEndpoint, {updateSuccess: 'curriculum.message.ehisStatusUpdated'});
    };

    function changeStatus(StatusChangeEndpoint, messages) {
      new StatusChangeEndpoint($scope.curriculum).$update().then(function(response){
        $scope.curriculum = response;
        message.info(messages.updateSuccess);
        setVariablesForExistingCurriculum();
        goToReadOnlyForm();
        $scope.higherCurriculumForm.$setPristine();
      });
    }

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

    $scope.goToVersionNewForm = function(){
        var url = '/higherCurriculum/' + $scope.curriculum.id + '/version/new';
        if(!$scope.formState.readOnly && $scope.higherCurriculumForm.$dirty) {
            dialogService.confirmDialog({prompt: 'main.messages.confirmFormDataNotSaved'}, function() {
                $location.path(url);
            });
        } else {
            $location.path(url);
        }
    };

    $scope.goToVersionForm = function(version) {
        var url = $scope.formState.readOnly || version.status !== Curriculum.VERSION_STATUS.S ? '/higherCurriculum/' + $scope.curriculum.id + '/version/' + version.id + '/view' :
        '/higherCurriculum/' + $scope.curriculum.id + '/version/' + version.id + '/edit';
        if(!$scope.formState.readOnly && $scope.higherCurriculumForm.$dirty) {
            dialogService.confirmDialog({prompt: 'main.messages.confirmFormDataNotSaved'}, function() {
                $location.path(url);
            });
        } else {
            $location.path(url);
        }
    };

    $scope.versionCanBeAdded = function() {
        return $scope.curriculum && $scope.curriculum.canChange && (
            $scope.curriculum.status === Curriculum.STATUS.ENTERING && !$scope.formState.readOnly ||
            $scope.curriculum.status === Curriculum.STATUS.PROCEEDING ||
            $scope.curriculum.status === Curriculum.STATUS.VERIFIED
        );
    };

    $scope.searchTeacher = function (searchText) {
      return QueryUtils.endpoint("/curriculum/teachers").query({
        name: searchText
      }).$promise;
    };
  });
