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
      var DialogController = function (scope) {
        if (item) {
          scope.studyPeriod =  new StudyPeriodEndpoint(item);
          scope.studyPeriod.type = scope.studyPeriod.type.code;
        } else {
          scope.studyPeriod = new StudyPeriodEndpoint({});
        }
        scope.studyPeriod.year = $scope.studyYear.year;
        scope.remove = function () {
          dialogService.confirmDialog({prompt: 'studyYear.studyPeriod.deleteconfirm'}, function() {
            scope.studyPeriod.$delete().then(function() {
              $scope.studyPeriods = $scope.studyPeriods.filter(function (it) {
                return it !== item;
              });
              message.info('main.messages.delete.success');
              $mdDialog.hide();

            });
          });
        };
      };

      var afterSave = function (data) {
        DataUtils.convertStringToDates(data, ['startDate', 'endDate']);
        if (item) {
          angular.extend(item, data);
        } else {
          $scope.studyPeriods.push(data);
        }
      };

      dialogService.showDialog('studyYear/study.period.dialog.html', DialogController,
        function (submitScope) {
          var period = submitScope.studyPeriod;
          if (period.id) {
            period.$update().then(afterSave);
          } else {
            period.$save().then(afterSave);
          }
        });
    };

    $scope.openStudyPeriodEventDialog = function (item) {
      var StudyPeriodEventEndpoint = QueryUtils.endpoint('/school/studyYears/'+$scope.studyYear.id+'/studyPeriodEvents');
      var DialogController = function (scope) {
        if (item) {
          scope.studyPeriodEvent =  new StudyPeriodEventEndpoint(item);
          if (scope.studyPeriodEvent.studyPeriod) {
            for (var i = 0; i < $scope.studyPeriods.length; i++) {
              if ($scope.studyPeriods[i].id === scope.studyPeriodEvent.studyPeriod.id) {
                scope.studyPeriodEvent.studyPeriod = $scope.studyPeriods[i];
                break;
              }
            }
          }
        } else {
          scope.studyPeriodEvent = new StudyPeriodEventEndpoint({});
        }
        var end = scope.studyPeriodEvent.end;
        scope.studyPeriodEvent.end = function (value) {
          if (arguments.length && !end) {
            var momentValue = moment(value);
            if (momentValue.isSame(moment(value).startOf('day'))) {
              value = momentValue.hours(23).minutes(59).toDate();
            }
          }
          return arguments.length ? (end = value) : end;
        };

        scope.studyPeriods = $scope.studyPeriods;
        scope.remove = function () {
          dialogService.confirmDialog({prompt: 'studyYear.studyPeriodEvent.deleteconfirm'}, function() {
            scope.studyPeriodEvent.$delete().then(function() {
              $scope.studyPeriodEvents = $scope.studyPeriodEvents.filter(function (it) {
                return it !== item;
              });
              message.info('main.messages.delete.success');
              $mdDialog.hide();

            });
          });
        };
      };

      var afterSave = function (data) {
        DataUtils.convertStringToDates(data, ['start', 'end']);
        if (item) {
          angular.extend(item, data);
        } else {
          $scope.studyPeriodEvents.push(data);
        }
      };

      dialogService.showDialog('studyYear/study.period.event.dialog.html', DialogController,
        function (submitScope) {
          var periodEvent = submitScope.studyPeriodEvent;
          console.log(submitScope);
          if (periodEvent.id) {
            periodEvent.$update().then(afterSave);
          } else {
            periodEvent.$save().then(afterSave);
          }
        });
    };

    $scope.update = function () {
      $scope.schoolYearForm.$setSubmitted();
      if ($scope.schoolYearForm.$valid) {
        if (id) {
          $scope.studyYear.$update().then(function() {
            afterLoad();
            message.info('main.messages.update.success');
          });
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
