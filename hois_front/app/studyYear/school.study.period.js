'use strict';

angular.module('hitsaOis').config(function ($routeProvider, USER_ROLES) {

  $routeProvider
    .when('/school/studyYears', {
      templateUrl: 'studyYear/study.year.list.html',
      controller: 'StudyYearsListController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPEPERIOOD]
      }
    })
    .when('/school/studyYears/:code/new', {
      templateUrl: 'studyYear/study.year.edit.html',
      controller: 'StudyYearsEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPEPERIOOD]
      }
    })
    .when('/school/studyYears/:id/view', {
      templateUrl: 'studyYear/study.year.view.html',
      controller: 'StudyYearsEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPEPERIOOD]
      }
    })
    .when('/school/studyYears/:id/edit', {
      templateUrl: 'studyYear/study.year.edit.html',
      controller: 'StudyYearsEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPEPERIOOD]
      }
    });
  })
  .controller('StudyYearsListController', function ($scope, QueryUtils, USER_ROLES, AuthService) {
    $scope.endpoint = QueryUtils.endpoint('/school/studyYears');
    $scope.tabledata = $scope.endpoint.query();
    $scope.canEdit = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPEPERIOOD);
  })
  .controller('StudyYearsEditController', function ($location, $mdDialog, $route, $scope, Classifier, dialogService, message, DataUtils, 
      QueryUtils, USER_ROLES, AuthService, $q) {
    $scope.canEdit = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPEPERIOOD);
    $scope.auth = $route.current.locals.auth;

    var id = $route.current.params.id;
    var code = $route.current.params.code;

    var Endpoint = QueryUtils.endpoint('/school/studyYears');

    var periodTypes = Classifier.valuemapper({type: 'OPPEPERIOOD'});
    
    $scope.eventTypes = {};
    Classifier.queryForDropdown({mainClassCode: 'SYNDMUS'}, function(arrayResult) {
      $scope.eventTypes = Classifier.toMap(arrayResult);
    });
    
    function afterLoad() {
      DataUtils.convertStringToDates($scope.studyYear, ['startDate', 'endDate']);
      $scope.studyPeriods = $scope.studyYear.studyPeriods || [];
      $scope.studyPeriodEvents = $scope.studyYear.studyPeriodEvents || [];
      $q.all(periodTypes.promises).then(function () {
        periodTypes.objectmapper($scope.studyPeriods);
      });
      DataUtils.convertStringToDates($scope.studyPeriods, ['startDate', 'endDate']);
      DataUtils.convertStringToDates($scope.studyPeriodEvents, ['start', 'end']);
    }

    if (id) {
      $q.all(periodTypes.promises).then(function () {
        $scope.studyYear = Endpoint.get({id: id}, afterLoad);
      });
    } else if (code) {
      $scope.studyYear = new Endpoint({year: code});
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
        scope.studyYear = parentScope.studyYear;

        scope.typeChange = function () {
          if (scope.studyPeriod.type) {
            var type = periodTypes.objectmapper({type : scope.studyPeriod.type});
            if (!angular.isString(scope.studyPeriod.nameEt)) {
              scope.studyPeriod.nameEt = type.type.nameEt;
            }
            /* todo method for all possible languages?
             if (!angular.isString(scope.studyPeriod.nameEn)) {
             scope.studyPeriod.nameEn = type.type.nameEn;
             }
             */
          }
        };
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
          if(!formValid()) {
            return;
          }
          var period = scope.studyPeriod;
          if (period.id) {
            period.$update().then(afterSave);
          } else {
            period.$save().then(afterSave);
          }
        };
        $scope.cancel = $mdDialog.hide;

        function formValid() {
          scope.dialogForm.$setSubmitted();
          if(!scope.dialogForm.startDate.$valid && scope.dialogForm.startDate.$error.mindate ||
             !scope.dialogForm.endDate.$valid && scope.dialogForm.endDate.$error.maxdate) {
            message.error('studyYear.studyPeriod.error.notInsideStudyYear');
            return false;
          }
          if(overlapWithOtherStudyPeriods(scope.studyPeriod)) {
            message.error('studyYear.studyPeriod.error.overlapWithOtherStudyPeriod');
            return false;
          }
          if(!scope.dialogForm.$valid) {
            message.error('main.messages.form-has-errors');
            return false;
          }
          return true;
        }

        function overlapWithOtherStudyPeriods(studyPeriod) {
          for(var i = 0; i < parentScope.studyPeriods.length; i++) {
            if(parentScope.studyPeriods[i].id === studyPeriod.id) {
              continue;
            }
            if(DataUtils.periodsOverlap(studyPeriod, parentScope.studyPeriods[i])) {
              return true;
            }
          }
          return false;
        }

      };

      $mdDialog.show({
        controller: DialogController,
        templateUrl: 'studyYear/study.period.dialog.html'
      });
    };

    $scope.openStudyPeriodEventDialog = function (item) {
      var uniqueEvent = ['SYNDMUS_AVES', 'SYNDMUS_DEKP', 'SYNDMUS_VOTA'];
      var allowBeforePeriod = ['SYNDMUS_DEKP'];
      var requiresEndEvent = ['SYNDMUS_DEKP'];
      var StudyPeriodEventEndpoint = QueryUtils.endpoint('/school/studyYears/'+$scope.studyYear.id+'/studyPeriodEvents');
      var parentScope = $scope;
      var DialogController = function ($scope) {
        $scope.auth = parentScope.auth;
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

        $scope.uniqueInStudyPeriod = false;
        $scope.requiresEnd = false;
        $scope.$watch("studyPeriodEvent", function () {
          if (uniqueEvent.indexOf($scope.studyPeriodEvent.eventType) !== -1) {
            $scope.uniqueInStudyPeriod = true;
            if (!$scope.studyPeriodEvent.studyPeriod) {
              $scope.studyPeriodEvent.studyPeriod = DataUtils.getCurrentStudyYearOrPeriod($scope.studyPeriods);
            }
          } else {
            $scope.uniqueInStudyPeriod = false;
          }
          $scope.requiresEnd = requiresEndEvent.indexOf($scope.studyPeriodEvent.eventType) !== -1;
        }, true);

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

        $scope.cancel = $mdDialog.hide;

        $scope.submit = function () {
          // calendar event must be unique in study period if it's type is ”Avalduse esitamine”, ”Deklareerimisperiood”, ”VÕTA taotluse vastuvõtmine”
          $scope.dialogForm.$setSubmitted();

          var errors = false;

          if (uniqueEvent.indexOf($scope.studyPeriodEvent.eventType) !== -1) {

            if (angular.isUndefined($scope.studyPeriodEvent.studyPeriod)) {
              $scope.studyPeriodEvent.studyPeriod = null;
            }

            if ($scope.studyPeriodEvent.studyPeriod) {
              var inSamePeriod = parentScope.studyPeriodEvents.filter(function (it) {
                if (it.studyPeriod && $scope.studyPeriodEvent.studyPeriod.id === it.studyPeriod.id &&
                    $scope.studyPeriodEvent.id !== it.id &&
                    it.eventType === $scope.studyPeriodEvent.eventType) {
                  return true;
                }
              });
              if (inSamePeriod.length > 0) {
                errors = true;
                message.error('studyYear.studyPeriodEvent.error.uniqueInPeriod');
              }
            }
          }

          // calendar event start time must be earlier than end time
          if ($scope.studyPeriodEvent.start && $scope.studyPeriodEvent.end && $scope.studyPeriodEvent.start.getTime() > $scope.studyPeriodEvent.end.getTime()) {
            errors = true;
            message.error('studyYear.studyPeriod.error.startDateLaterThanEndDate');
          }

          // calendar event date must be between study period's start and end date
          if ($scope.studyPeriodEvent.studyPeriod) {
            var start;
            if ($scope.studyPeriodEvent.start && $scope.studyPeriodEvent.end) {
              start = moment($scope.studyPeriodEvent.start).startOf('day');
              var end = moment($scope.studyPeriodEvent.end).endOf('day');

              if ((allowBeforePeriod.indexOf($scope.studyPeriodEvent.eventType) === -1 && start.isBefore(moment($scope.studyPeriodEvent.studyPeriod.startDate).startOf('day'))) || 
                   end.isAfter(moment($scope.studyPeriodEvent.studyPeriod.endDate).endOf('day'))) {
                errors = true;
                message.error(allowBeforePeriod.indexOf($scope.studyPeriodEvent.eventType) === -1 ? 
                              'studyYear.studyPeriod.error.outsideStudyPeriod' :
                              'studyYear.studyPeriod.error.endBeforeStudyPeriodEnd');
              }
            } else if ($scope.studyPeriodEvent.start) {
              start = moment($scope.studyPeriodEvent.start).startOf('day');

              if ((allowBeforePeriod.indexOf($scope.studyPeriodEvent.eventType) === -1 && start.isBefore(moment($scope.studyPeriodEvent.studyPeriod.startDate).startOf('day'))) || 
                  start.isAfter(moment($scope.studyPeriodEvent.studyPeriod.endDate).endOf('day'))) {
                errors = true;
                message.error('studyYear.studyPeriod.error.outsideStudyPeriod');
              }
            }
          }

          if ($scope.dialogForm.$valid && !errors) {
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
  })
;
