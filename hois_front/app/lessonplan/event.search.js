'use strict';

angular.module('hitsaOis').controller('LessonplanEventSearchController',
  function ($scope, message, QueryUtils, USER_ROLES, AuthService) {
    var beforeColon = /^[^:]+/;
    var afterColon = /:(.*)/;
    var beforeT = /.+?(?=T)/;
    $scope.canEdit = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_SYNDMUS);
    $scope.formState = {};
    var baseUrl = '/timetableevents';


    QueryUtils.endpoint(baseUrl + '/searchFormData').search().$promise.then(function (result) {
      $scope.formState.studentGroups = result.studentGroups;
      $scope.formState.studyPeriods = result.studyPeriods;
      $scope.formState.teachers = result.teachers;
      $scope.criteria.singleEvent = true;
      $scope.loadData();
    });


    $scope.clearCriteria = function () {
      $scope.criteria = {};
      $scope.criteria.singleEvent = true;
    };

    $scope.$watch('criteria.roomObject', function () {
      $scope.criteria.room = $scope.criteria.roomObject ? $scope.criteria.roomObject.id : null;
    });

    QueryUtils.createQueryForm($scope, baseUrl, {
      order: 'id'
    });


    $scope.afterNow = function (date, time) {
      date = new Date(date);
      var year = date.getFullYear();
      var month = date.getMonth() + 1;
      var day = date.getDate();
      var dateString = '' + year + '-' + month + '-' + day;
      return new Date(dateString + ' ' + time) > new Date();
    };
  });
