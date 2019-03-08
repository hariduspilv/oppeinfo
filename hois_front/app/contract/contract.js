'use strict';

angular.module('hitsaOis').controller('ContractEditController', function ($location, $scope, $route, dialogService, message, Classifier, DataUtils, QueryUtils, $q, ArrayUtils) {
  $scope.auth = $route.current.locals.auth;
  $scope.contract = {moduleSubjects: [{}]};
  $scope.formState = {};

  function entityToForm(entity) {
    $scope.formState.isHigher = entity.isHigher;
    DataUtils.convertObjectToIdentifier(entity, ['contractCoordinator', 'practiceEvaluation', 'studentPracticeEvaluation']);
    entity.moduleSubjects.forEach(function (it) {
      DataUtils.convertObjectToIdentifier(it, ['module', 'theme', 'subject']);
    });
    $scope.contract = entity;
    var clMapper = Classifier.valuemapper({
      status: 'LEPING_STAATUS'
    });

    $q.all(clMapper.promises).then(function () {
      if ($scope.contract) {
        clMapper.objectmapper($scope.contract);
      }
    });
  }

  var addStringToPracticePlan = function(addable) {
    if (angular.isString(addable)) {
      if ($scope.contract.practicePlan === undefined || $scope.contract.practicePlan === null) {
        $scope.contract.practicePlan = addable;
      } else {
        if (($scope.contract.practicePlan + "\n" + addable).length <= 20000) {
          $scope.contract.practicePlan += "\n" + addable;
        } else {
          var index = 20000 - $scope.contract.practicePlan.length + 2;
          $scope.contract.practicePlan += "\n" + addable.substring(0, index);
        }
      }
    }
  }

  $scope.sendEmailAgain = function (supervisor) {
    var ContractEndpoint = QueryUtils.endpoint('/contracts/sendEmail');
    var contract = new ContractEndpoint({id: supervisor});
    contract.$update().then(function () {
      message.updateSuccess();
    }).catch(angular.noop);
  };

  var entity = $route.current.locals.entity;
  if (angular.isDefined(entity)) {
    entity.$promise.then(function(response) {
      if (response.canEdit === false) {
        message.error("main.messages.error.nopermission");
        $scope.back("#/");
      }
    });
  }
  if (angular.isDefined(entity)) {
    entityToForm(entity);
  } else {
    if ($route.current.params.practiceApplication) {
      var practiceApplication = parseInt($route.current.params.practiceApplication, 10);
      QueryUtils.endpoint('/practiceApplication/contractData/' + practiceApplication).get(function (result) {
        $scope.formState.isHigher = result.isHigher;
        $scope.contract.student = result.student;
        $scope.contract.enterprise = result.enterprise;
        $scope.contract.contactPersonName = result.contactPersonName;
        $scope.contract.contactPersonPhone = result.contactPersonPhone;
        $scope.contract.contactPersonEmail = result.contactPersonEmail;
        $scope.contract.supervisorName = result.supervisorName;
        $scope.contract.supervisorPhone = result.supervisorPhone;
        $scope.contract.supervisorEmail = result.supervisorEmail;
        $scope.contract.practiceApplication = practiceApplication;
      });
    } else {
      $scope.formState.isHigher = $scope.auth.school.higher === true && (($scope.auth.school.vocational === true && $route.current.params.higher === true) || $scope.auth.school.vocational === false);
    }
    $scope.contract.isPracticeSchool = true;
  }

  var clMapper = Classifier.valuemapper({ studyForm: 'OPPEVORM' });
  $scope.$watch('contract.student', function (student) {
    if (angular.isObject(student)) {
      QueryUtils.endpoint('/students/' + student.id).search(function (result) {
        $scope.formState.student = clMapper.objectmapper(result);
      });

      if ($scope.formState.isHigher) {
        loadStudentPracticeSubjects(student);
      } else {
        loadStudentPracticeModules(student);
      }
    }
  });

  $scope.$watch('contract.enterprise', function (enterprise) {
    if (angular.isObject(enterprise)) {
      QueryUtils.endpoint('/practiceEnterprise/enterpriseContacts/' + enterprise.id).query(function (result) {
        $scope.formState.contactsByName = {};
        result.forEach(function (it) {
          $scope.formState.contactsByName[it.contactPersonName] = it;
        });
        $scope.formState.contacts = result;
        if (result.length === 1) {
          $scope.contract.contactPersonName = result[0].contactPersonName;
          $scope.contract.contactPersonEmail= result[0].contactPersonEmail;
          $scope.contract.contactPersonPhone= result[0].contactPersonPhone;
        } else {
          if ($scope.contract.contactPersonName !== undefined && $scope.contract.contactPersonName !== null && !$scope.formState.contactsByName[$scope.contract.contactPersonName]) {
            $scope.contract.contactPersonEmail = undefined;
            $scope.contract.contactPersonPhone = undefined;
            $scope.contract.contactPersonName = undefined;
          }
        }
      });
      QueryUtils.endpoint('/autocomplete/supervisors').query({id: enterprise.id}).$promise.then(function (result) {
        $scope.supervisors = result;
        var nameList = result.map(function (item) {
          return item.supervisorName;
        });
        if ($scope.contract.supervisors !== undefined) {
          $scope.contract.supervisors.some(function (supervisor) {
            if (nameList.indexOf(supervisor.supervisorName) === -1) {
              $scope.contract.supervisors = $scope.contract.supervisors.filter(function (item) {
                return item.supervisorName !== supervisor.supervisorName;
              });
            }
          });
        }
        if (result.length === 1 && ($scope.contract.supervisors === undefined || $scope.contract.supervisors.length === 0)) {
          $scope.contract.supervisor = result[0];
        }
      });

    }
  });
  $scope.$watch('contract.isPracticeEnterprise', function (value) {
    if (value) {
      QueryUtils.endpoint('/autocomplete/enterpriseLocations').query({id: $scope.contract.enterprise.id}).$promise.then(function (result) {
        $scope.formState.enterpriseLocations = result;
        if (result.length === 1 && $scope.contract.practicePlace === null) {
          $scope.contract.practicePlace = result[0].nameEt;
        }
      });
    }
  });

  function loadStudentPracticeModules(student) {
    QueryUtils.endpoint('/contracts/studentPracticeModules/' + student.id).query(function (result) {
      $scope.formState.modulesById = {}; 
      $scope.formState.themesById = {};
      $scope.formState.themesByModuleId = {};
      result.forEach(function (it) {
        $scope.formState.modulesById[it.module.id] = it;
        it.themes.forEach(function (it) {
          $scope.formState.themesById[it.theme.id] = it;
        });
        $scope.formState.themesByModuleId[it.module.id] = it.themes.map(function (it) { return it.theme; });
      });
      $scope.formState.modules = result.map(function (it) { return it.module; });
      removeUnsuitableModules();
    });
  }

  function removeUnsuitableModules() {
    var modules = $scope.formState.modules.map(function (it) { return it.id; });
    for (var i = $scope.contract.moduleSubjects.length - 1; i >= 0; i--) {
      if ($scope.contract.moduleSubjects[i].module && modules.indexOf($scope.contract.moduleSubjects[i].module) === -1) {
        ArrayUtils.remove($scope.contract.moduleSubjects, $scope.contract.moduleSubjects[i]);
      }
    }
  }

  function loadStudentPracticeSubjects(student) {
    QueryUtils.endpoint('/contracts/studentPracticeSubjects/' + student.id).query(function (result) {
      $scope.formState.subjectsById = {};
      result.forEach(function (it) {
        $scope.formState.subjectsById[it.id] = it;
      });
      $scope.formState.subjects = result;
      removeUnsuitableSubjects();
    });
  }

  function removeUnsuitableSubjects() {
    var subjects = $scope.formState.subjects.map(function (it) { return it.id; });
    for (var i = $scope.contract.moduleSubjects.length - 1; i >= 0; i--) {
      if ($scope.contract.moduleSubjects[i].subject && subjects.indexOf($scope.contract.moduleSubjects[i].subject) === -1) {
        ArrayUtils.remove($scope.contract.moduleSubjects, $scope.contract.moduleSubjects[i]);
      }
    }
  }

  function setModuleCredits(moduleSubject) {
    if(moduleSubject.module && !moduleSubject.theme) {
      moduleSubject.credits = $scope.formState.modulesById[moduleSubject.module].credits;
      moduleSubject.hours = DataUtils.creditsToHours(moduleSubject.credits);
    }
  }

  $scope.contactChanged = function (contactPersonName) {
    if ($scope.formState.contactsByName[contactPersonName]) {
      $scope.contract.contactPersonEmail = $scope.formState.contactsByName[contactPersonName].contactPersonEmail;
      $scope.contract.contactPersonPhone = $scope.formState.contactsByName[contactPersonName].contactPersonPhone;
    }
  };

  $scope.addModuleSubject = function () {
    $scope.contract.moduleSubjects.push({});
  };

  $scope.removeModuleSubject = function (item) {
    ArrayUtils.remove($scope.contract.moduleSubjects, item);
  };

  $scope.getAstronomicalHours = DataUtils.getAstronomicalHours;

  $scope.moduleChanged = function (moduleId, moduleSubject) {
    var module = $scope.formState.modulesById[moduleId];
    if (module) {
      var themeIds = module.themes.map(function (it) { return it.theme.id; });
      if (themeIds.indexOf(moduleSubject.theme) === -1) {
        moduleSubject.theme = null;
      }

      setModuleCredits(moduleSubject);

      if (angular.isString(module.assessmentMethodsEt)) {
        addStringToPracticePlan(module.assessmentMethodsEt);
      }
    }
  };

  $scope.themeChanged = function (themeId, moduleSubject) {
    if ($scope.formState.themesById[themeId]) {
      moduleSubject.credits = $scope.formState.themesById[themeId].credits;
      moduleSubject.hours = DataUtils.creditsToHours(moduleSubject.credits);

      if (angular.isString($scope.formState.themesById[themeId].subthemes)) {
        addStringToPracticePlan($scope.formState.themesById[themeId].subthemes);
      }
    } else {
      setModuleCredits(moduleSubject);
    }
  };

  $scope.$watch('contract.supervisor', function () {
    if (angular.isDefined($scope.contract) && angular.isDefined($scope.contract.supervisor) && $scope.contract.supervisor !== null) {
      $scope.addSupervisor();
    }
  });

  $scope.addSupervisor = function () {
    if (!angular.isArray($scope.contract.supervisors)) {
      $scope.contract.supervisors = [];
    }
    if ($scope.contract.supervisors.some(function (supervisor) {
        return supervisor.supervisorName === $scope.contract.supervisor.supervisorName;
      })) {
      message.error('timetable.timetableEvent.error.duplicateSupervisor');
      return;
    }
    $scope.contract.supervisors.push($scope.contract.supervisor);
    $scope.contract.supervisor = undefined;
  };

  $scope.deleteSupervisor = function (supervisor) {
    var index = $scope.contract.supervisors.indexOf(supervisor);
    if (index !== -1) {
      $scope.contract.supervisors.splice(index, 1);
      $scope.contractForm.$setDirty();
    }
  };

  $scope.subjectChanged = function (subjectId, moduleSubject) {
    if ($scope.formState.subjectsById[subjectId]) {
      moduleSubject.credits = $scope.formState.subjectsById[subjectId].credits;
      moduleSubject.hours = DataUtils.creditsToHours(moduleSubject.credits);

      if (angular.isString($scope.formState.subjectsById[subjectId].outcomesEt)) {
        addStringToPracticePlan($scope.formState.subjectsById[subjectId].outcomesEt);
      }
    }
  };

  $scope.updateHours = function (moduleSubject, index) {
    if (angular.isNumber(moduleSubject.credits)) {
      moduleSubject.hours = DataUtils.creditsToHours(moduleSubject.credits);
      $scope.contractForm['hours[' + index + ']'].$setDirty();
    }
  };

  $scope.updateCredits = function (moduleSubject, index) {
    moduleSubject.credits = DataUtils.hoursToCredits(moduleSubject.hours);
    $scope.contractForm['credits[' + index + ']'].$setDirty();
  };

  $scope.isPracticeAbsenceChanged = function () {
    $scope.contract.isPracticeHidden = false;
  };

  $scope.isPracticeHiddenChanged = function () {
    $scope.contract.isPracticeAbsence = false;
  };

  function validationPassed() {
    $scope.contractForm.$setSubmitted();
    if(!$scope.contractForm.$valid) {
      message.error('main.messages.form-has-errors');
      return false;
    }

    if($scope.contract.moduleSubjects.length === 0) {
      message.error($scope.formState.isHigher ? 'contract.messages.atleastOneSubjectRequired' : 'contract.messages.atleastOneModuleRequired');
      return false;
    }

    return true;
  }

  var ContractEndpoint = QueryUtils.endpoint('/contracts');
  $scope.save = function (success) {
    if(!validationPassed()) {
      return false;
    }

    $scope.contract.isHigher = $scope.formState.isHigher;
    var contract = new ContractEndpoint($scope.contract);
    if (angular.isDefined($scope.contract.id)) {
      contract.$update().then(function () {
        message.updateSuccess();
        entityToForm(contract);
        if (angular.isFunction(success)) {
          success();
        }
        $scope.contractForm.$setPristine();
      }).catch(angular.noop);
    } else {
      contract.$save().then(function () {
        message.info('main.messages.create.success');
        $location.url('/contracts/' + contract.id + '/edit?_noback');
      }).catch(angular.noop);
    }
  };

  $scope.delete = function () {
    dialogService.confirmDialog({ prompt: 'contract.deleteconfirm' }, function () {
      var contract = new ContractEndpoint($scope.contract);
      contract.$delete().then(function () {
        message.info('main.messages.delete.success');
        $scope.back('/contracts');
      }).catch(angular.noop);
    });
  };

  $scope.sendToEkis = function () {
    if(!validationPassed()) {
      return false;
    }

    QueryUtils.endpoint('/contracts/checkForEkis').get({id: $scope.contract.id}).$promise.then(function(check) {
      dialogService.confirmDialog({ prompt: (check.templateExists ? 'contract.ekisconfirm' : 'contract.ekisconfirmTemplateMissing'), template: check.templateName }, function () {
        $scope.save(function () {
          var EkisEndpoint = QueryUtils.endpoint('/contracts/sendToEkis/' + $scope.contract.id);
          new EkisEndpoint().$save().then(function (contract) {
            message.info('contract.messages.sendToEkis.success');
            $location.url('/contracts/' + contract.id + '/view?_noback');
          }).catch(angular.noop);
        });
      });
    });
  };
});
