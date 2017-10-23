'use strict';

angular.module('hitsaOis').controller('TimetableEventController', ['$scope', 'message', 'QueryUtils', 'DataUtils', '$route', '$location', '$rootScope', 'Classifier',
  function ($scope, message, QueryUtils, DataUtils, $route, $location) {
    var baseUrl = '/timetableevents';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    $scope.timetableEvent = new Endpoint();

    $scope.save = function () {
      $scope.eventForm.$setSubmitted();
      $scope.timetableEvent.$save().then(function () {
        message.info('timetable.timetableEvent.created');
        $location.url('/lessonplans/events?_menu');
      });
    };
  }
]);