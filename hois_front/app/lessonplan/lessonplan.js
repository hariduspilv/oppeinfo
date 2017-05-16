'use strict';

angular.module('hitsaOis').controller('LessonplanSearchController', ['$location', '$mdDialog', '$route', '$scope', 'QueryUtils', 'Session',
  function ($location, $mdDialog, $route, $scope, QueryUtils, Session) {
    var school = Session.school || {};
    $scope.formState = {higher: school.higher, vocational: school.vocational};
    $scope.currentNavItem = $route.current.$$route.data.currentNavItem;

    var baseUrl, criteria;
    if($scope.currentNavItem === 'lessonplan.vocational') {
      baseUrl = '/lessonplans';
      criteria = {};
      $scope.newLessonplan = function() {
        var formState = $scope.formState;
        $mdDialog.show({
          controller: function($scope) {
            $scope.formState = formState;
            $scope.record = {};

            $scope.create = function() {
              QueryUtils.endpoint(baseUrl).save($scope.record).$promise.then(function(result) {
                $mdDialog.hide();
                $location.url(baseUrl+'/vocational/'+result.id+'/edit');
              });
            };
            $scope.cancel = $mdDialog.hide;
          },
          templateUrl: 'lessonplan/new.lessonplan.dialog.html',
          clickOutsideToClose: true
        });
      };
      $scope.formState.createData = QueryUtils.endpoint(baseUrl+'/searchFormData').search();
      $scope.formState.createData.$promise.then(function(result) {
        $scope.formState.studyyears = result.studyyears;
        var allstudentgroups = result.studentgroups;
        var existingplans = result.studentgroupmapping;
        var studentgroups = {};
        for(var i = 0, cnt = $scope.formState.studyyears.length; i < cnt; i++) {
          var syid = $scope.formState.studyyears[i].id;
          var existing = existingplans[syid] || [];
          studentgroups[syid] = allstudentgroups.filter(function(it) { return existing.indexOf(it.id) === -1; });
        }
        $scope.formState.studentgroups = studentgroups;
      });
    } else if($scope.currentNavItem === 'lessonplan.vocational-byteacher') {
      baseUrl = '/lessonplans/byteacher';
      criteria = {};
    }

    QueryUtils.createQueryForm($scope, baseUrl, criteria);
  }
]).controller('LessonplanEditController', ['$location', '$mdDialog', '$route', '$scope', 'message', 'QueryUtils',
  function ($location, $mdDialog, $route, $scope, message, QueryUtils) {
    var id = $route.current.params.id;
    $scope.formState = {};
    $scope.record = QueryUtils.endpoint('/lessonplans').get({id: id});

    $scope.update = function() {
      $scope.record.$update().then(message.updateSuccess);
    };
  }
]);
