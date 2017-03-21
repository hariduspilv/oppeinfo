'use strict';

angular.module('hitsaOis').config(function ($routeProvider, USER_ROLES) {
  var authorizedRoles = {authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]};

  $routeProvider
    .when('/school/studyYears', {
      templateUrl: 'studyYear/study.year.list.html',
      controller: 'StudyYearsListController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
      data: authorizedRoles
    })
    .when('/school/studyYears/:code/new', {
      templateUrl: 'studyYear/study.year.edit.html',
      controller: 'StudyYearsEditController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
      data: authorizedRoles
    })
    .when('/school/studyYears/:id/edit', {
      templateUrl: 'studyYear/study.year.edit.html',
      controller: 'StudyYearsEditController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
      data: authorizedRoles
    });
  })
  .controller('StudyYearsListController', ['$scope', 'QueryUtils', function ($scope, QueryUtils) {
    $scope.endpoint = QueryUtils.endpoint('/school/studyYears');
    $scope.tabledata = $scope.endpoint.query();
  }])
  .controller('StudyYearsEditController', ['$location', '$mdDialog', '$route', '$scope', 'Classifier', 'dialogService', 'message', 'DataUtils','QueryUtils', function ($location, $mdDialog, $route, $scope, Classifier, dialogService, message, DataUtils, QueryUtils) {
    var id = $route.current.params.id;
    var code = $route.current.params.code;

    var Endpoint = QueryUtils.endpoint('/school/studyYears');

    var periodTypes = Classifier.valuemapper({type: "OPPEPERIOOD"});

    function afterLoad() {
      DataUtils.convertStringToDates($scope.studyYear, ['startDate', 'endDate']);
      $scope.studyPeriods = $scope.studyYear.studyPeriods || [];
      $scope.studyPeriodEvents = $scope.studyYear.studyPeriodEvents || [];
      periodTypes.objectmapper($scope.studyPeriods);
      $scope.studyPeriods.forEach(function (it) {
        DataUtils.convertStringToDates(it, ['startDate', 'endDate']);
      });
      $scope.studyPeriodEvents.forEach(function (it) {
        DataUtils.convertStringToDates(it, ['start', 'end']);
      });
    }

    if (id) {
      $scope.studyYear = Endpoint.get({id: id}, afterLoad);
    } else if (code) {
      $scope.studyYear = new Endpoint();
      $scope.studyYear.year = code;
      $scope.studyPeriods = [];
      $scope.studyPeriodEvents = [];
    }

    $scope.openStudyPeriodDialog = function (item) {
      var StudyPeriodEndpoint = QueryUtils.endpoint('/school/studyYears/'+$scope.studyYear.id+'/studyPeriods');
      var parentScope = $scope;
      var DialogController = function ($scope) {
        var scope = $scope;
        if (item) {
          scope.studyPeriod =  new StudyPeriodEndpoint(item);
          scope.studyPeriod.type = scope.studyPeriod.type.code;
        } else {
          scope.studyPeriod = new StudyPeriodEndpoint({});
        }
        scope.studyPeriod.year = parentScope.studyYear.year;

        scope.delete = function () {
          dialogService.confirmDialog({prompt: 'studyYear.studyPeriod.deleteconfirm'}, function() {
            scope.studyPeriod.$delete().then(function() {
              parentScope.studyPeriods = parentScope.studyPeriods.filter(function (it) {
                return it !== item;
              });
              message.info('main.messages.delete.success');
              $mdDialog.hide();

            });
          });
        };

        var afterSave = function (data) {
          DataUtils.convertStringToDates(data, ['startDate', 'endDate']);
          periodTypes.objectmapper(data);
          if (item) {
            angular.extend(item, data);
          } else {
            parentScope.studyPeriods.push(data);
          }
          $mdDialog.hide();
          message.updateSuccess();
        };

        scope.submit = function () {
          scope.dialogForm.$setSubmitted();
          if (scope.dialogForm.$valid) {
            var period = scope.studyPeriod;
            if (period.id) {
              period.$update().then(afterSave);
            } else {
              period.$save().then(afterSave);
            }
          }
        };
        $scope.cancel = function() {
          $mdDialog.hide();
        };
      };

      $mdDialog.show({
        controller: DialogController,
        templateUrl: 'studyYear/study.period.dialog.html'
      });
    };

    $scope.openStudyPeriodEventDialog = function (item) {
      var StudyPeriodEventEndpoint = QueryUtils.endpoint('/school/studyYears/'+$scope.studyYear.id+'/studyPeriodEvents');
      var parentScope = $scope;
      var DialogController = function ($scope) {
        if (item) {
          $scope.studyPeriodEvent = new StudyPeriodEventEndpoint(item);
          if ($scope.studyPeriodEvent.studyPeriod) {
            for (var i = 0; i < parentScope.studyPeriods.length; i++) {
              if (parentScope.studyPeriods[i].id === $scope.studyPeriodEvent.studyPeriod.id) {
                $scope.studyPeriodEvent.studyPeriod = parentScope.studyPeriods[i];
                break;
              }
            }
          }
        } else {
          $scope.studyPeriodEvent = new StudyPeriodEventEndpoint({});
        }

        $scope.studyPeriods = parentScope.studyPeriods;
        $scope.delete = function () {
          dialogService.confirmDialog({prompt: 'studyYear.studyPeriodEvent.deleteconfirm'}, function() {
            $scope.studyPeriodEvent.$delete().then(function() {
              parentScope.studyPeriodEvents = parentScope.studyPeriodEvents.filter(function (it) {
                return it !== item;
              });
              message.info('main.messages.delete.success');
              $mdDialog.hide();

            });
          });
        };

        $scope.cancel = function() {
          $mdDialog.hide();
        };

        $scope.submit = function () {
          $scope.dialogForm.$setSubmitted();

          if ($scope.dialogForm.$valid) {
            var studyPeriodEvent = $scope.studyPeriodEvent;
            if (studyPeriodEvent.id) {
              studyPeriodEvent.$update().then(afterSave);
            } else {
              studyPeriodEvent.$save().then(afterSave);
            }
          }
        };

      };

      var afterSave = function (data) {
        DataUtils.convertStringToDates(data, ['start', 'end']);
        if (item) {
          angular.extend(item, data);
        } else {
          parentScope.studyPeriodEvents.push(data);
        }
        $mdDialog.hide();
        message.updateSuccess();
      };


      $mdDialog.show({
        controller: DialogController,
        templateUrl: 'studyYear/study.period.event.dialog.html'
      });
    };

    $scope.update = function () {
      $scope.schoolYearForm.$setSubmitted();
      if ($scope.schoolYearForm.$valid) {
        if (id) {
          $scope.studyYear.$update().then(afterLoad).then(message.updateSuccess);
        } else if (code) {
          $scope.studyYear.$save().then(function(response) {
            $location.path('/school/studyYears/'+ response.id +'/edit');
            message.info('main.messages.create.success');
          });
        }
      }
    };
  }])
;
