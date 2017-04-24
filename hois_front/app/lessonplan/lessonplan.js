'use strict';

angular.module('hitsaOis').controller('LessonplanSearchController', ['$route', '$scope', 'QueryUtils', 'Session',
  function ($route, $scope, QueryUtils, Session) {
    var school = Session.school || {};
    $scope.formState = {higher: school.higher, vocational: school.vocational};

    $scope.currentNavItem = $route.current.$$route.data.currentNavItem;

    var baseUrl, criteria;
    if($scope.currentNavItem === 'lessonplan.vocational') {
      baseUrl = '/lessonplans';
      criteria = {};
    } else if($scope.currentNavItem === 'lessonplan.vocational-byteacher') {
      baseUrl = '/lessonplans/byteacher';
      criteria = {};
    }

    QueryUtils.createQueryForm($scope, baseUrl, criteria);
  }
]);
