'use strict';

angular.module('hitsaOis').controller('FinalThesisEditController', function ($scope, $route, $location, $q, Classifier, DataUtils, QueryUtils, config, dialogService, message) {
  $scope.auth = $route.current.locals.auth;
  var endpoint = '/finalThesis';
  var FinalThesisEndpoint = QueryUtils.endpoint(endpoint);

  function entityToForm(entity) {
    $scope.thesis = entity;
  }

  var entity = $route.current.locals.entity;
  if (angular.isDefined(entity)) {
    entityToForm(entity);
    
    if (!$scope.thesis.canBeEdited && $route.current.$$route.originalPath.indexOf("view") === -1) {
      $location.url(endpoint + '/' + $scope.thesis.id + '/view?_noback');
    }

    $scope.finalThesisPdfUrl = config.apiUrl + endpoint + '/print/' + entity.id + '/finalThesis.pdf';
  } else {
    $scope.thesis = {
      hasDraft: false,
      supervisors: []
    };

    if ($scope.auth.isStudent()) {
      $scope.thesis.student = {id: $scope.auth.student}; 
    }
  }

  $scope.$watch('thesis.student', function () {
    if ($scope.thesis.student) {
      QueryUtils.endpoint('/students/' + $scope.thesis.student.id).get(function (result) {
        $scope.thesis.person = result.person;
        $scope.thesis.curriculumVersion = result.curriculumVersion;
        $scope.thesis.studentGroup = result.studentGroup;
        $scope.thesis.isVocational = result.curriculumVersion.isVocational;
      });
    }
  });

  $scope.openAddSupervisorDialog = function (supervisorIndex) {
    var supervisorsCount = $scope.thesis.supervisors ? $scope.thesis.supervisors.length : 0;
    if (supervisorsCount >= 3 && !angular.isDefined(supervisorIndex)) {
      message.error('finalThesis.error.maxSupervisors');
    } else {
      dialogService.showDialog('finalThesis/supervisor.add.dialog.html', function (dialogScope) {
        dialogScope.newSupervisor = angular.isDefined(supervisorIndex) ? false : true;
        if (!dialogScope.newSupervisor) {
          dialogScope.supervisor = angular.copy($scope.thesis.supervisors[supervisorIndex]);
        } else {
          dialogScope.supervisor = {isExternal: false};
        }
        
        dialogScope.$watch("supervisor.teacher", function () {
          if (dialogScope.supervisor && dialogScope.supervisor.teacher) {
            QueryUtils.endpoint(endpoint + '/teacher/').get({teacherId: dialogScope.supervisor.teacher.id}, function (result) {
              dialogScope.supervisor.firstname = result.person.firstname;
              dialogScope.supervisor.lastname = result.person.lastname;
              dialogScope.supervisor.email = result.email;
              dialogScope.supervisor.occupation = result.teacherOccupation.nameEt;
            });
          }
        });
  
        dialogScope.typeChanged = function (isExternal) {
          dialogScope.supervisor = {isExternal: isExternal};
        };
  
        dialogScope.removeSupervisor = function () {
          dialogService.confirmDialog({prompt: 'finalThesis.supervisorDeleteConfirm'}, function() {
            $scope.thesis.supervisors.splice(supervisorIndex, 1);
            dialogScope.cancel();
          });
        };

        dialogScope.submitSupervisor = function () {
          if (teacherAlreadyAdded(dialogScope.supervisor, supervisorIndex)) {
            message.error('finalThesis.error.teacherAlreadyIsSupervisor');
          } else {
            dialogScope.submit();
          }
        };
  
      }, function (submittedDialogScope) {
        var modifiedSupervisor = submittedDialogScope.supervisor;
        removePreviousPrimarySupervisor(modifiedSupervisor);

        if (angular.isDefined(supervisorIndex)) {
          $scope.thesis.supervisors[supervisorIndex] = modifiedSupervisor;
        } else {
          $scope.thesis.supervisors.push(modifiedSupervisor);
        }
      });
    }
  };

  function teacherAlreadyAdded(modifiedSupervisor, modifiedSupervisorIndex) {
    var teacherId = modifiedSupervisor.teacher ? modifiedSupervisor.teacher.id : null;

    if (teacherId) {
      for (var i = 0; i < $scope.thesis.supervisors.length; i++) {
        if ($scope.thesis.supervisors[i].teacher && $scope.thesis.supervisors[i].teacher.id === teacherId && modifiedSupervisorIndex !== i) {
          return true;
        }
      }
    }
    return false;
  }

  function removePreviousPrimarySupervisor(modifiedSupervisor) {
    if (modifiedSupervisor.isPrimary) {
      for (var i = 0; i < $scope.thesis.supervisors.length; i++) {
        $scope.thesis.supervisors[i].isPrimary = false;
      }
    }
  }

  function isValid() {
    $scope.finalThesisForm.$setSubmitted();
    if($scope.thesis.supervisors.length === 0) {
      message.error('finalThesis.error.supervisorRequired');
      return false;
    }

    if(!$scope.finalThesisForm.$valid) {
      message.error('main.messages.form-has-errors');
      return false;
    }

    return true;
  }

  $scope.save = function () {
    if (isValid()) {
      var finalThesis = new FinalThesisEndpoint($scope.thesis);
  
      if (angular.isDefined($scope.thesis.id)) {
        finalThesis.$update().then(function (result) {
          message.info('main.messages.update.success');
          entityToForm(result);
        }).catch(angular.noop);
      } else {
        finalThesis.$save().then(function (result) {
          message.info('main.messages.update.success');
          $location.url(endpoint + '/' + result.id + '/edit?_noback');
        }).catch(angular.noop);
      }
    }
  };

  $scope.confirm = function () {
    if (isValid()) {
      dialogService.confirmDialog({
        prompt: 'finalThesis.confirmConfirm'
      }, function () {
        QueryUtils.endpoint(endpoint + '/' + $scope.thesis.id + '/confirm').put($scope.thesis, function (response) {
          message.info('finalThesis.isConfirmed');
          entityToForm(response);
        });
      });
    }
  };
});