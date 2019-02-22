'use strict';

angular.module('hitsaOis').controller('LessonplanEventSearchController',
  function ($scope, $route, message, QueryUtils, USER_ROLES, AuthService) {
    // var beforeColon = /^[^:]+/;
    // var afterColon = /:(.*)/;
    // var beforeT = /.+?(?=T)/;
    $scope.auth = $route.current.locals.auth;
    $scope.canEdit = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_SYNDMUS);
    $scope.formState = {};
    var baseUrl = '/timetableevents';

    QueryUtils.endpoint(baseUrl + '/searchFormData').search().$promise.then(function (result) {
      $scope.formState.studyPeriods = result.studyPeriods;
      $scope.formState.studyPeriods.forEach(function (studyPeriod) {
        studyPeriod[$scope.currentLanguageNameField()] = $scope.currentLanguageNameField(studyPeriod.studyYear) + ' ' + $scope.currentLanguageNameField(studyPeriod);
      });
      $scope.formState.teachers = result.teachers;

      if (!angular.isDefined($scope.criteria.singleEvent)) {
        $scope.criteria.singleEvent = true;
      }

      if (!angular.isDefined($scope.criteria.from)) {
        $scope.criteria.from = new Date();
      }
      $scope.loadData();
    });

    $scope.$watch('criteria.roomObject', function () {
      $scope.criteria.room = $scope.criteria.roomObject ? $scope.criteria.roomObject.id : null;
    });

    QueryUtils.createQueryForm($scope, baseUrl, {
      order:'4 desc'
    });

    var _clearCriteria = $scope.clearCriteria;
    $scope.directiveControllers = []; 
    $scope.clearEventCriteria = function () {
      _clearCriteria();
      $scope.criteria.singleEvent = false;
      $scope.directiveControllers.forEach(function (c) { 
        c.clear(); 
      }); 
    };

    $scope.afterNow = function (date, time) {
      date = new Date(date);
      var year = date.getFullYear();
      var month = date.getMonth() + 1;
      var day = date.getDate();
      var dateString = '' + year + '-' + month + '-' + day;
      return new Date(dateString + ' ' + time) > new Date();
    };

    $scope.allowedToEdit = function (event) {
      if ($scope.auth.isAdmin()) {
        return true;
      }

      if ($scope.auth.isTeacher()) {
        if (!event.teachers) {
          return false;
        }

        for (var i = 0; i < event.teachers.length; i++) {
          if (event.teachers[i].id === $scope.auth.teacher) {
            return true;
          }
        }
      }
      return false;
    };
  });
