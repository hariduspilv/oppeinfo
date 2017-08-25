'use strict';

angular.module('hitsaOis')
  .controller('HigherCurriculumVersionController', function ($scope, Curriculum, dialogService, ArrayUtils, message, $route, $location, QueryUtils, $translate, $rootScope, $routeParams, DataUtils, config, Session) {

    var baseUrl = '/curriculum';
    $scope.curriculum = $route.current.locals.curriculum;
    $scope.formState = {
      readOnly: $route.current.$$route.originalPath.indexOf("view") !== -1,
      strictValidation: false
    };
    $scope.myEhisSchool = Session.school ? Session.school.school.ehisSchool : null;
    $scope.ehisSchools = $scope.curriculum.jointPartners.map(function (p) { return p.ehisSchool; });
    $scope.ehisSchools.push($scope.myEhisSchool);
    // var SpecialityEndpoint = QueryUtils.endpoint(baseUrl + '/speciality');
    var SpecialityEndpoint = QueryUtils.endpoint('/curriculum/' + $scope.curriculum.id + '/speciality');


    var entity = { curriculum: $scope.curriculum.id, modules: [] };

    $scope.VERSION_STATUS = Curriculum.VERSION_STATUS;

    var initialVersion = {
      status: $scope.VERSION_STATUS.S,
      validFrom: new Date(),
      code: $scope.curriculum.code + "/" + new Date().getFullYear(),
      curriculum: $scope.curriculum.id,
      modules: []
    };

    $scope.removeModule = function (versionModules, deletedModule) {
      dialogService.confirmDialog({ prompt: 'curriculum.itemDeleteConfirm' }, function () {
        ArrayUtils.remove(versionModules, deletedModule);
      });
    };

    if (angular.isDefined($route.current.params.versionId)) {
      $scope.curriculum.versions.forEach(function (it) {
        if (it.id.toString() === $route.current.params.versionId) {
          angular.extend(entity, it);
        }
      });
      $scope.version = entity;
      $rootScope.removeLastUrlFromHistory(function(lastUrl){
        return lastUrl === '#/higherCurriculum/' + $scope.version.curriculum + '/version/' + $scope.version.id + '/view' ||
               lastUrl === '#/higherCurriculum/' + $scope.version.curriculum + '/version/' + $scope.version.id + '/edit' ||
               lastUrl === '#/higherCurriculum/' + $scope.version.curriculum + '/version/new';
      });

    } else if (angular.isDefined($route.current.locals.copy)) {
      angular.extend(entity, initialVersion, $route.current.locals.copy);
      $scope.version = entity;
    } else {
      angular.extend(entity, initialVersion);
      $scope.version = entity;
    }
    DataUtils.convertStringToDates($scope.version, ["validFrom", "validThru"]);
    $scope.formState.curriculumPdfUrl = config.apiUrl + baseUrl + '/print/' + $scope.version.id + '/curriculum.pdf';

    var CurriculumVersionEndpoint = QueryUtils.endpoint('/curriculum/' + $scope.version.curriculum + '/versions');






    // --- Dialog Windows

    function getReferenceNumber(list) {
      var existingReferenceNumbers = list.map(function (el) {
        return el.referenceNumber;
      });
      var rand;
      while (true) {
        rand = - Math.floor((Math.random() * 1000) + 1);
        if (!ArrayUtils.includes(existingReferenceNumbers, rand)) {
          break;
        }
      }
      return rand;
    }


    $scope.openAddSpecialtyDialog = function () {
      var DialogController = function (scope) {
        scope.maxCredits = $scope.curriculum.credits ? $scope.curriculum.credits : 0;
        scope.isNew = true;
      };
      dialogService.showDialog('higherCurriculum/higher.curriculum.specialty.add.dialog.html', DialogController,
        function (submitScope) {
          var data = submitScope.data;
          if (!data.occupation) {
            data.occupationEt = undefined;
            data.occupationEn = undefined;
          }
          var spec = new SpecialityEndpoint(data);
          spec.curriculum = $scope.curriculum.id;
          spec.$save().then(function (responses) {
            message.info('main.messages.create.success');
            $scope.curriculum.specialities.push(responses);
          });
        });
    };

    $scope.versionCodes = $scope.curriculum.versions.filter(function (v) { return v.id !== $scope.version.id; }).map(function (v) { return v.code; });

    function setAdmissionYearsSelelction() {
      $scope.admissionYears = [];
      var currentYear = new Date().getFullYear();
      for (var year = currentYear - 10; year <= currentYear + 2; year++) {
        $scope.admissionYears.push(year);
      }
    }
    setAdmissionYearsSelelction();

    $scope.curriculumVersionCodeUniqueQuery = {
      id: $route.current.params.versionId,
      url: baseUrl + '/unique/version/code'
    };

    $scope.save = function () {
      $scope.formState.strictValidation = false;
      setTimeout(save, 0);
    };

    // function formIsValid()

    function validationPassed(messages) {
      $scope.higherCurriculumVersionForm.$setSubmitted();

      if (!versionFormIsValid()) {
        var errorMessage = messages && messages.errorMessage ? messages.errorMessage : 'main.messages.form-has-errors';
        message.error(errorMessage);
        return false;
      }
      return true;
    }

    function save(messages) {

      if (!validationPassed(messages)) {
        return;
      }
      var curriculumVersion = new CurriculumVersionEndpoint($scope.version);

      if (angular.isDefined($scope.version.id)) {
        curriculumVersion.$update().then(function (response) {
          var updateSuccess = messages && messages.updateSuccess ? messages.updateSuccess : 'main.messages.create.success';
          message.info(updateSuccess);
          $scope.version = response;
          DataUtils.convertStringToDates($scope.version, ["validFrom", "validThru"]);
          $scope.version.curriculum = $scope.curriculum.id;
          getCurriculum();
        });
      } else {
        curriculumVersion.$save().then(function (response) {
          message.info('main.messages.create.success');
          $location.path('/higherCurriculum/' + $scope.version.curriculum + '/version/' + response.id + '/edit');
        });
      }
    }

    $scope.saveAndConfirm = function() {
      var messages = {
        prompt: $scope.formState.readOnly ? 'curriculum.statuschangeReadOnly.version.prompt.verify' : 'curriculum.statuschange.version.prompt.verify',
        updateSuccess: 'curriculum.statuschange.version.success.verified'
      };
      var SaveAndConfirmEndpoint = QueryUtils.endpoint("/curriculum/" + $scope.curriculum.id + "/versions/saveAndConfirm");
      $scope.formState.strictValidation = true;
      changeStatus(SaveAndConfirmEndpoint, messages);
    };

    $scope.confirm = function() {
      var messages = {
        prompt: $scope.formState.readOnly ? 'curriculum.statuschangeReadOnly.version.prompt.verify' : 'curriculum.statuschange.version.prompt.verify',
        updateSuccess: 'curriculum.statuschange.version.success.verified'
      };
      var ConfirmEndpoint = QueryUtils.endpoint("/curriculum/" + $scope.curriculum.id + "/versions/confirm");
      $scope.formState.strictValidation = true;
      changeStatus(ConfirmEndpoint, messages);
    };

    $scope.setStatusClosed = function() {

      var messages = {
        prompt: $scope.formState.readOnly ? 'curriculum.statuschangeReadOnly.version.prompt.close' : 'curriculum.statuschange.version.prompt.close',
        updateSuccess: 'curriculum.statuschange.version.success.closed'
      };
      var ClosingEndpoint = QueryUtils.endpoint("/curriculum/" + $scope.curriculum.id + "/versions/close");
      $scope.formState.strictValidation = false;
      changeStatus(ClosingEndpoint, messages);
    };

    function changeStatus(StatusChangeEndpoint, messages) {
      if (!validationPassed(messages)) {
        return;
      }
      setTimeout(function(){
        dialogService.confirmDialog({ prompt: messages.prompt }, function () {
            new StatusChangeEndpoint($scope.version).$update().then(function(response){
              var updateSuccess = messages && messages.updateSuccess ? messages.updateSuccess : 'main.messages.create.success';
              message.info(updateSuccess);
              $scope.version = response;
              DataUtils.convertStringToDates($scope.version, ["validFrom", "validThru"]);
              $scope.version.curriculum = $scope.curriculum.id;
              getCurriculum();
              $scope.formState.strictValidation = false;
              goToReadOnlyForm(response);
            });
        });
      }, 0);
    }

    function goToReadOnlyForm(response) {
      if(!$scope.formState.readOnly) {
        $location.path('/higherCurriculum/' + $scope.version.curriculum + '/version/' + response.id + '/view');
      }
    }

    $scope.goToEditForm = function () {
      if (!$scope.curriculum) {
        return;
      }
      if ($scope.version.status === Curriculum.VERSION_STATUS.K) {
        dialogService.confirmDialog({
          prompt: 'curriculum.statuschange.version.prompt.editAccepted',
        }, function () {
          $location.path('/higherCurriculum/' + $scope.version.curriculum + '/version/' + $scope.version.id + '/edit');
        });
      } else {
        $location.path('/higherCurriculum/' + $scope.version.curriculum + '/version/' + $scope.version.id + '/edit');
      }
    };

    /**
     * When saving curriculum version, newly created specialities return with new referenceNumbers
     * And these numbers should be updated in curriculum as well
     */
    function getCurriculum() {
      $scope.curriculum = QueryUtils.endpoint('/curriculum').get({ id: $scope.curriculum.id });
    }

    function versionFormIsValid() {
      return $scope.higherCurriculumVersionForm.$valid && ($scope.versionIsValid() || !$scope.strictValidation());
    }

    $scope.strictValidation = function () {
      return $scope.version.status === Curriculum.VERSION_STATUS.K || $scope.formState.strictValidation;
    };

    $scope.delete = function () {
      dialogService.confirmDialog({ prompt: 'curriculum.version.deleteconfirm' }, function () {
        var curriculumVersion = new CurriculumVersionEndpoint($scope.version);
        curriculumVersion.$delete().then(function () {
          message.info('main.messages.delete.success');
          // $location.path('higherCurriculum/' + $scope.version.curriculum + '/view');
          $rootScope.back('#/higherCurriculum/' + $scope.version.curriculum + '/view');
        });
      });
    };

    $scope.copy = function () {
      var copy = angular.copy($scope.version);
      ['id', 'version', 'changed', 'changedBy', 'inserted', 'insertedBy', 'status', 'validFrom', 'validThru']
        .forEach(function (property) {
          delete copy[property];
        });
      copy.modules.forEach(function (m) {
        m.id = undefined;
        m.version = undefined;
        m.subjects.forEach(function (s) {
          s.id = undefined;
          s.version = undefined;
        });
        m.electiveModules.forEach(function (e) {
          e.id = undefined;
          e.version = undefined;
        });
      });
      // $location.path('/higherCurriculum/' + $scope.version.curriculum + '/version/new').search({ _noback: true });
      $location.path('/higherCurriculum/' + $scope.version.curriculum + '/version/new');
      $routeParams.versionCopy = copy;
      $route.current.locals.versionCopy = copy;
      $route.current.params.versionCopy = copy;
    };

    // version form validation

    $scope.versionIsValid = function () {
      // return $scope.anyModuleAdded(version) && allModulesValid(version) && $scope.allSpecialitiesValid();
      return $scope.allSpecialitiesValid() && $scope.allMinorSpecialitiesValid();

    };

    function getModulesBySpeciality(specialityId) {
      return $scope.version.modules.filter(function (el) {
        return !el.minorSpeciality && ArrayUtils.includes(el.specialitiesReferenceNumbers, specialityId);
      });
    }

    $scope.allMinorSpecialitiesValid = function () {
      var minorSpecs = $scope.version.modules.filter(function (el) {
        return el.minorSpeciality;
      });
      for (var i = 0; i < minorSpecs.length; i++) {
        if (!$scope.moduleValid(minorSpecs[i])) {
          return false;
        }
      }
      return true;
    };

    $scope.allSpecialitiesValid = function () {
      if (ArrayUtils.isEmpty($scope.version.specialitiesReferenceNumbers) || ArrayUtils.isEmpty($scope.version.modules)) {
        return false;
      }
      var specs = $scope.version.specialitiesReferenceNumbers;
      for (var i = 0; i < specs.length; i++) {
        if (!$scope.specialityValid(specs[i])) {
          return false;
        }
      }
      return true;
    };

    $scope.specialityValid = function (specialityId) {
      var modules = getModulesBySpeciality(specialityId);
      if (ArrayUtils.isEmpty(modules)) {
        return false;
      }
      for (var i = 0; i < modules.length; i++) {
        if (!$scope.moduleValid(modules[i])) {
          return false;
        }
      }
      return true;
    };

    $scope.moduleValid = function (module1) {
      return $scope.moduleHasSubjects(module1);
    };

    $scope.anyModuleAdded = function (version) {
      return version.modules.filter(function (el) { return !el.minorSpeciality; }).length > 0;
    };

    $scope.moduleMustHaveSubjects = function(module) {
      return module.type !== 'KORGMOODUL_V';
    };

    $scope.moduleHasSubjects = function (module1) {
      if (module1.minorSpeciality) {
        return module1.subjects && module1.subjects.length > 0;
      } else {
        return !$scope.moduleMustHaveSubjects(module1) || module1.subjects && module1.subjects.length > 0;
      }
    };


    // modules and specialities

    $scope.removeModuleFromSpeciality = function (module1, speciality) {
      dialogService.confirmDialog({ prompt: 'curriculum.itemDeleteConfirm' }, function () {
        ArrayUtils.remove(module1.specialitiesReferenceNumbers, speciality.referenceNumber);
        deleteModulesWithNoSpeciality($scope.version);
      });
    };

    function deleteModulesWithNoSpeciality(version) {
      version.modules = version.modules.filter(function (m) {
        return moduleHasSpeciality(m);
      });
    }

    function moduleHasSpeciality(m) {
      return m.minorSpeciality || (m.specialitiesReferenceNumbers && m.specialitiesReferenceNumbers.length > 0);
    }

    $scope.filterModulesBySpeciality = function (spec) {
      return function (module1) {
        return ArrayUtils.includes(module1.specialitiesReferenceNumbers, spec.referenceNumber);
      };
    };

    $scope.filterSpecialities = function (spec) {
      if (!$scope.curriculum || !$scope.version || !$scope.version.specialitiesReferenceNumbers) {
        return false;
      }
      return ArrayUtils.includes($scope.version.specialitiesReferenceNumbers, spec.referenceNumber);
    };

    $scope.removeSpecsFromModules = function () {
      $scope.version.modules.forEach(function (m) {
        if (!m.minorSpeciality) {
          m.specialitiesReferenceNumbers = m.specialitiesReferenceNumbers.filter(function (n) {
            return $scope.version.specialitiesReferenceNumbers &&
              $scope.version.specialitiesReferenceNumbers.indexOf(n) !== -1;
          });
        }
      });
      deleteModulesWithNoSpeciality($scope.version);
    };

    // subjects
    $scope.deleteSubject = function (module1, subject) {
      dialogService.confirmDialog({ prompt: 'curriculum.itemDeleteConfirm' }, function () {
        ArrayUtils.remove(module1.subjects, subject);
        $scope.setCompulsoryAndTotalStudyCredits(module1);
      });
    };

    $scope.setCompulsoryAndTotalStudyCredits = function (module1) {
      module1.compulsoryStudyCredits = 0;
      module1.subjects.forEach(function (e) {
        if (!e.optional) {
          module1.compulsoryStudyCredits += e.credits;
        }
      });
      module1.totalCredits = module1.compulsoryStudyCredits + module1.optionalStudyCredits;
    };

    /**
     * we cannot add subject to module, if subject is already added to some module of the same speciality..
     */
    function filterSubjectsBySpeciality (subject, editingModule, newModule) {
      var otherModules = getAllVersionModulesExcept(editingModule).filter(function(el){
        return !el.minorSpeciality;
      });
      otherModules = otherModules.filter(function(el){
        return ArrayUtils.intersect(newModule.specialitiesReferenceNumbers, el.specialitiesReferenceNumbers);
      });
      var subjectsFromOtherModules = getAllSubjectIdsFromModules(otherModules);
      return !ArrayUtils.includes(subjectsFromOtherModules, subject.subjectId);
    }

    function getAllVersionModulesExcept(excludedModule) {
      return $scope.version.modules.filter(function (m) { return m !== excludedModule; });
    }

    function getAllSubjectIdsFromModules(modules) {
      var subjects = [];
      for (var i = 0; i < modules.length; i++) {
        for (var j = 0; j < modules[i].subjects.length; j++) {
          subjects.push(modules[i].subjects[j].subjectId);
        }
      }
      return subjects;
    }

    $scope.$watchCollection('version.specialitiesReferenceNumbers', function(newValues, oldValues){
      if(specialityRemoved(newValues, oldValues)) {
        var removedSpecialities = getRemovedSpecialities(newValues, oldValues);
        for(var i = 0; i < removedSpecialities.length; i++) {
          if(getModulesBySpeciality(removedSpecialities[i]).length > 0) {
            message.error("curriculum.error.specAddedToModule");
            $scope.version.specialitiesReferenceNumbers = oldValues;
            return;
          }
        }
      }
    });

    function specialityRemoved(newValues, oldValues) {
      return !angular.isDefined(newValues) && angular.isDefined(oldValues) ||
      angular.isDefined(newValues) && angular.isDefined(oldValues) && newValues.length < oldValues.length;
    }

    function getRemovedSpecialities(newValues, oldValues) {
      if(!angular.isDefined(newValues)) {
        return oldValues;
      }
      var removedSpecialities = [];
      for(var i = 0; i < oldValues.length; i++) {
        if(!ArrayUtils.contains(newValues, oldValues[i])) {
          removedSpecialities.push(oldValues[i]);
        }
      }
      return removedSpecialities;
    }


    function getSubjects(callback) {
      QueryUtils.endpoint('/curriculum/' + $scope.version.curriculum + '/possibleSubjects').query().$promise.then(callback);
    }

    // dialog windows

    var newModuleTypes = [];

    $scope.openAddModuleDialog = function (editingModule) {
      var DialogController = function (scope) {
        scope.editing = angular.isDefined(editingModule);
        scope.specialities = $scope.curriculum.specialities.filter(function (s) {
          return $scope.version.specialitiesReferenceNumbers && $scope.version.specialitiesReferenceNumbers.indexOf(s.referenceNumber) !== -1;
        });
        scope.myEhisSchool = $scope.myEhisSchool;

        scope.formState = {
          moduleMustHaveSubjects: !editingModule ||  $scope.moduleMustHaveSubjects(editingModule),
          readOnly: $scope.formState.readOnly
        };
        scope.readOnly = $scope.formState.readOnly;

        QueryUtils.endpoint(baseUrl + '/versionHmoduleTypes').query().$promise.then(function (response) {
          scope.moduleTypes = response.concat(newModuleTypes);
          scope.data.typeObject = scope.moduleTypes.find(function (el) {
            if (scope.data.type !== 'KORGMOODUL_M') {
              return el.code === scope.data.type;
            } else {
              return el.code === null && el.nameEn === scope.data.typeNameEn && el.nameEt === scope.data.typeNameEt;
            }
          });
        });

        scope.$watch('data.typeObject', function(newValue){
          if(newValue && newValue.code === 'KORGMOODUL_V') {
            scope.formState.moduleMustHaveSubjects = false;
            clearFreeModulesProperties();
            validateSubjects();
          } else if(newValue) {
            scope.formState.moduleMustHaveSubjects = true;
            validateSubjects();
          }
        });

        function clearFreeModulesProperties() {
          scope.data.subjects = [];
          scope.data.electiveModules = [];
          scope.data.compulsoryStudyCredits = 0;
          scope.data.electiveModulesNumber = 0;
          scope.data.totalCredits = scope.data.optionalStudyCredits;
        }

        scope.addElectiveModule = function () {
          var newElectiveModule = {
            nameEt: scope.optionalNameEt,
            nameEn: scope.optionalNameEn,
            referenceNumber: getReferenceNumber(scope.data.electiveModules)
          };
          scope.data.electiveModules.push(newElectiveModule);
          scope.optionalNameEt = undefined;
          scope.optionalNameEn = undefined;
        };
        scope.editingElectiveModule = false;
        var editedElectiveModule = null;

        scope.editElectiveModule = function (electiveModule) {
          editedElectiveModule = electiveModule;
          scope.editingElectiveModule = true;
          scope.optionalNameEt = electiveModule.nameEt;
          scope.optionalNameEn = electiveModule.nameEn;
        };

        scope.finishEditElectiveModule = function () {
          scope.editingElectiveModule = false;
          editedElectiveModule.nameEt = scope.optionalNameEt;
          editedElectiveModule.nameEn = scope.optionalNameEn;
          scope.optionalNameEt = undefined;
          scope.optionalNameEn = undefined;
        };

        getSubjects(function(result){
              scope.subjectsUnfiltered = result;
              filterSubjects();
        });

        scope.filterSubjects = function() {
          filterSubjects();
        };

        function filterSubjects() {
          // filter subjects already added
          if(!scope.subjectsUnfiltered) {
              return;
          }
          scope.subjects = scope.subjectsUnfiltered.filter(function (s) {
            for (var i = 0; i < scope.data.subjects.length; i++) {
              if (scope.data.subjects[i].subjectId === s.subjectId) {
                return false;
              }
            }
            return true;
          });
          // filter subjects added to other modules of one of the modules specialities
          scope.subjects = scope.subjects.filter(function(el){
            return filterSubjectsBySpeciality(el, editingModule, scope.data);
          });
        }

        scope.deleteElectiveModule = function (electiveModule) {
          scope.data.subjects.forEach(function (e) {
            if (e.electiveModule === electiveModule.referenceNumber) {
              e.electiveModule = undefined;
            }
          });
          ArrayUtils.remove(scope.data.electiveModules, electiveModule);
        };

        scope.addSubject = function () {
          var newSubject = angular.copy(scope.data.selectedSubject);
          newSubject.optional = false;
          scope.data.subjects.push(newSubject);
          scope.data.selectedSubject = undefined;
          scope.setCompulsoryStudyCredits();
          filterSubjects();
          validateSubjects();
        };

        scope.setCompulsoryStudyCredits = function (subject) {
          $scope.setCompulsoryAndTotalStudyCredits(scope.data);
          if (subject && !subject.optional) {
            subject.electiveModule = undefined;
          }
        };

        scope.removeSubject = function (subject) {
          ArrayUtils.remove(scope.data.subjects, subject);
          scope.setCompulsoryStudyCredits();
          filterSubjects();
          validateSubjects();
        };

        function validateSubjects() {
          if(!scope.formState.moduleMustHaveSubjects) {
            scope.data.subjectAdded = 'true';
          } else if(scope.data.type !== 'KORGMOODUL_V') {
            scope.data.subjectAdded = scope.data.subjects.length > 0 ? 'true' : null;
          } else {
            scope.data.subjectAdded = 'true';
          }
        }

        if (editingModule) {
          scope.data = angular.copy(editingModule);
          validateSubjects();
        } else {
          scope.data = {
            electiveModules: [],
            specialitiesReferenceNumbers: [],
            subjects: [],
            minorSpeciality: false,
            optionalStudyCredits: 0,
            totalCredits: 0,
            compulsoryStudyCredits: 0,
            electiveModulesNumber: 0
          };
        }

        scope.delete = function () {
          dialogService.confirmDialog({ prompt: 'curriculum.moduleDeleteConfirm' }, function () {
            if(scope.data.id) {
              var ModuleEndpoint = QueryUtils.endpoint('/curriculum/version/' + $scope.version.id + '/higherModule');
              new ModuleEndpoint(scope.data).$delete().then(function() {
                message.info('main.messages.delete.success');
                ArrayUtils.remove($scope.version.modules, editingModule);
              });
            } else {
              ArrayUtils.remove($scope.version.modules, editingModule);
            }
            scope.cancel();
          });
        };
      };
      dialogService.showDialog('higherCurriculum/higher.curriculum.version.module.html', DialogController,
        function (submitScope) {
          var data = submitScope.data;

          // user selected classifier
          if (data.typeObject.code !== 'KORGMOODUL_M' && data.typeObject.code !== null) {
            data.typeNameEt = null;
            data.typeNameEn = null;
            data.type = data.typeObject.code;
            // user selected module type saved before
          } else if (data.typeObject.code === null) {
            data.type = 'KORGMOODUL_M';
            data.typeNameEt = data.typeObject.nameEt;
            data.typeNameEn = data.typeObject.nameEn;
            // user created new module type
          } else {
            data.type = 'KORGMOODUL_M';
            if (!$scope.version.id) {    //in another case new value is taken from database query
              newModuleTypes.push({ code: null, nameEt: data.typeNameEt, nameEn: data.typeNameEn });
            }
          }
        //  if (editingModule) {
        //     $scope.version.modules.splice($scope.version.modules.indexOf(editingModule), 1);
        //   }
        //   $scope.version.modules.push(data);
        //   if ($scope.version.id) {
        //     var ModuleEndpoint = QueryUtils.endpoint('/curriculum/higher/version/modules');
        //     var savedModule = new ModuleEndpoint($scope.version);
        //     savedModule.$update().then(function (response) {
        //       message.info('main.messages.create.success');
        //       $scope.version.modules = response.modules;
        //       $scope.version.specialitiesReferenceNumbers = response.specialitiesReferenceNumbers;
        //     });
        //   }
          data.curriculumVersionSpecialities = $scope.version.specialitiesReferenceNumbers;
          if($scope.version.id) {
            var ModuleEndpoint = QueryUtils.endpoint('/curriculum/version/' + $scope.version.id + '/higherModule');
            var savedModule = new ModuleEndpoint(data);
            if(data.id) {
              savedModule.$update().then(function(response) {
                message.info('main.messages.update.success');
                angular.extend(editingModule, response);
            });
          } else {
            savedModule.$save().then(function(response) {
                message.info('main.messages.create.success');
                $scope.version.modules.push(response);
            });
          }
        } else {
          if(editingModule) {
              angular.extend(editingModule, data);
          } else {
              $scope.version.modules.push(data);
          }
        }
      });
    };

    function subjectAddedToOtherModules(subject) {
      var modules = $scope.version.modules.filter(function(el){
        return !el.minorSpeciality;
      });
      var subjectsIds = [];
      modules.forEach(function(module){
        var subjects = module.subjects.map(function(el){
          return el.subjectId;
        });
        subjectsIds = subjectsIds.concat(subjects);
      });
      return ArrayUtils.includes(subjectsIds, subject.subjectId);

    }


    //TODO: code duplication
    $scope.openAddMinorSpecialtyDialog = function (editingModule) {

      var DialogController = function (scope) {

        scope.editing = angular.isDefined(editingModule);
        scope.myEhisSchool = $scope.myEhisSchool;
        scope.readOnly = $scope.formState.readOnly;
        scope.addElectiveModule = function () {
          var newElectiveModule = {
            nameEt: scope.optionalNameEt,
            nameEn: scope.optionalNameEn,
            referenceNumber: getReferenceNumber(scope.data.electiveModules)
          };
          scope.data.electiveModules.push(newElectiveModule);
          scope.optionalNameEt = undefined;
          scope.optionalNameEn = undefined;
        };
        scope.editingElectiveModule = false;
        var editedElectiveModule = null;

        scope.editElectiveModule = function (electiveModule) {
          editedElectiveModule = electiveModule;
          scope.editingElectiveModule = true;
          scope.optionalNameEt = electiveModule.nameEt;
          scope.optionalNameEn = electiveModule.nameEn;
        };

        scope.finishEditElectiveModule = function () {
          scope.editingElectiveModule = false;
          editedElectiveModule.nameEt = scope.optionalNameEt;
          editedElectiveModule.nameEn = scope.optionalNameEn;
          scope.optionalNameEt = undefined;
          scope.optionalNameEn = undefined;
        };

        getSubjects(function(result){
              scope.subjectsUnfiltered = result.filter(subjectAddedToOtherModules);
              filterSubjects();
        });

        function filterSubjects() {
          scope.subjects = scope.subjectsUnfiltered.filter(function (s) {
            for (var i = 0; i < scope.data.subjects.length; i++) {
              if (scope.data.subjects[i].subjectId === s.subjectId) {
                return false;
              }
            }
            return true;
          });
        }

        scope.deleteElectiveModule = function (electiveModule) {
          scope.data.subjects.forEach(function (e) {
            if (e.electiveModule === electiveModule.referenceNumber) {
              e.electiveModule = undefined;
            }
          });
          ArrayUtils.remove(scope.data.electiveModules, electiveModule);
        };

        scope.addSubject = function () {
          var newSubject = angular.copy(scope.data.selectedSubject);
          newSubject.optional = false;
          scope.data.subjects.push(newSubject);
          scope.data.selectedSubject = undefined;
          scope.setCompulsoryStudyCredits();
          filterSubjects();
          validateSubjects();
        };

        function validateSubjects() {
          scope.data.subjectAdded = $scope.moduleHasSubjects(scope.data) ? 'true' : null;
        }

        scope.setCompulsoryStudyCredits = function (subject) {
          $scope.setCompulsoryAndTotalStudyCredits(scope.data);
          if (subject && !subject.optional) {
            subject.electiveModule = undefined;
          }
        };

        scope.removeSubject = function (subject) {
          ArrayUtils.remove(scope.data.subjects, subject);
          scope.setCompulsoryStudyCredits();
          filterSubjects();
          validateSubjects();
        };

        // scope.delete = function () {
        //   dialogService.confirmDialog({ prompt: 'curriculum.itemDeleteConfirm' }, function () {
        //     ArrayUtils.remove($scope.version.modules, editingModule);
        //     if (scope.data.id) {
        //       var ModuleEndpoint = QueryUtils.endpoint('/curriculum/higher/version/modules');
        //       var deletedModule = new ModuleEndpoint($scope.version);
        //       deletedModule.$update().then(function (response) {
        //         message.info('main.messages.delete.success');
        //         $scope.version.modules = response.modules;
        //       });
        //     }
        //     scope.cancel();
        //   });
        // };

        scope.delete = function () {
          dialogService.confirmDialog({ prompt: 'curriculum.minorSpecialtyDeleteConfirm' }, function () {
            if(scope.data.id) {
              var ModuleEndpoint = QueryUtils.endpoint('/curriculum/version/' + $scope.version.id + '/higherModule');
              new ModuleEndpoint(scope.data).$delete().then(function() {
                message.info('main.messages.delete.success');
                ArrayUtils.remove($scope.version.modules, editingModule);
              });
            } else {
              ArrayUtils.remove($scope.version.modules, editingModule);
            }
            scope.cancel();
          });
        };

        if (editingModule) {
          scope.data = angular.copy(editingModule);
          validateSubjects();
        } else {
          scope.data = {
            electiveModules: [],
            subjects: [],
            minorSpeciality: true, // different from module controller

            optionalStudyCredits: 0,
            totalCredits: 0,
            compulsoryStudyCredits: 0,
            type: 'KORGMOODUL_M', // different from module controller
            electiveModulesNumber: 0
          };
        }
      };
      dialogService.showDialog('higherCurriculum/higher.curriculum.version.minorSpecialty.html', DialogController,
        function (submitScope) {
          var data = submitScope.data;


          // if (editingModule) {
          //   $scope.version.modules.splice($scope.version.modules.indexOf(editingModule), 1);
          // }
          // $scope.version.modules.push(data);
          // if ($scope.version.id) {
          //   var ModuleEndpoint = QueryUtils.endpoint('/curriculum/higher/version/modules');
          //   var savedModule = new ModuleEndpoint($scope.version);
          //   savedModule.$update().then(function (response) {
          //     message.info('main.messages.create.success');
          //     $scope.version.modules = response.modules;
          //     $scope.version.specialitiesReferenceNumbers = response.specialitiesReferenceNumbers;
          //   });
          // }
        // data.curriculumVersionSpecialities = $scope.version.specialitiesReferenceNumbers;

        //FIXME: redord modified error occures in some cases
          if($scope.version.id) {
            var ModuleEndpoint = QueryUtils.endpoint('/curriculum/version/' + $scope.version.id + '/higherModule');
            var savedModule = new ModuleEndpoint(data);
            if(data.id) {
              savedModule.$update().then(function(response) {
                message.info('main.messages.update.success');
                angular.extend(editingModule, response);
            });
            } else {
              savedModule.$save().then(function(response) {
                  message.info('main.messages.create.success');
                  $scope.version.modules.push(response);
              });
            }
          } else {
            if(editingModule) {
                angular.extend(editingModule, data);
            } else {
                $scope.version.modules.push(data);
            }
          }

        });
    };

  });
