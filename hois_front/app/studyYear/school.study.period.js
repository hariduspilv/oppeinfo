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
  .controller('StudyYearsEditController', ['$location', '$route', '$scope', 'dialogService', 'DataUtils','QueryUtils', function ($location, $route, $scope, dialogService, DataUtils, QueryUtils) {
    var id = $route.current.params.id;
    var code = $route.current.params.code;

    var Endpoint = QueryUtils.endpoint('/school/studyYears');

    function afterLoad() {
      DataUtils.convertStringToDates($scope.studyYear, ['startDate', 'endDate']);
    }

    if (id) {
      $scope.studyYear = Endpoint.get({id: id}, afterLoad);
    } else if (code) {
      $scope.studyYear = new Endpoint();
      $scope.studyYear.year = code;
      $scope.studyYear.studyPeriods = [];
      $scope.studyYear.studyEvents = [];
    }

    $scope.openStudyPeriodDialog = function (item) {
      var DialogController = function (scope) {
        if (item) {
          scope.data =  angular.extend({}, item);
        }
        scope.year = $scope.year;
        scope.remove = function (it) {

        }
      };

      dialogService.showDialog('studyYear/study.period.dialog.html', DialogController,
        function (submitScope) {
          var data = submitScope.data;
          if(item) {
            angular.extend(item, data);
          } else {
            $scope.studyYear.studyPeriods.push(data);
          }
        });
    };

    $scope.openStudyEventDialog = function (item) {
      var DialogController = function (scope) {
        if (item) {
          scope.data =  angular.extend({}, item);
        }
        scope.year = $scope.year;
        scope.remove = function (it) {

        }
      };

      dialogService.showDialog('studyYear/study.event.dialog.html', DialogController,
        function (submitScope) {
          var data = submitScope.data;
          if(item) {
            angular.extend(item, data);
          } else {
            $scope.studyYear.studyEvents.push(data);
          }
        });
    };

    $scope.update = function () {
      $scope.schoolYearForm.$setSubmitted();
      if ($scope.schoolYearForm.$valid) {
        if (id) {
          $scope.studyYear.$update().then(function() {
            message.info('main.messages.update.success');
          });
        } else if (code) {
          $scope.studyYear.$save().then(function(response) {
            $location.path('/school/studyYears/'+ response.id +'/edit');
            message.info('main.messages.create.success');
          });
        }
      }
    }
  }])
;
