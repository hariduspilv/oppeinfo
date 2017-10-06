(function () {
  'use strict';

  function getCurrentWeekTimetables(scope, timetables) {
    var startOfWeek = moment().startOf('isoweek');

    for (var i = 0; i <= timetables.length - 1; i++) {
      var timetableStartDate = moment(timetables[i].startDate).set({ hour: 0, minute: 0, second: 0, millisecond: 0 });
      if (moment(timetableStartDate).isSame(startOfWeek)) {
        scope.showTimetablesByCurriculums(timetables[i].id, scope.timetables[i].studyPeriodId);
        scope.shownPeriodIndex = getShownPeriodIndex(timetables, scope.timetableId);
      }
    }
  }

  function getShownPeriodIndex(timetables, timetableId) {
    for (var i = 0; i <= timetables.length - 1; i++) {
      if (timetables[i].id === timetableId) {
        return i;
      }
    }
  }

  angular.module('hitsaOis').controller('GeneralTimetableByGroupController', ['$scope', 'QueryUtils',
    function ($scope, QueryUtils) {
      $scope.currentNavItem = "studentGroup";
      $scope.timetables = QueryUtils.endpoint('/timetables/generalTimetables/').query();

      QueryUtils.endpoint('/timetables/generalTimetables/').query().$promise.then(function (result) {
        $scope.timetables = result;
        getCurrentWeekTimetables($scope, result);
      });

      $scope.showTimetablesByCurriculums = function (timetableId, studyPeriodId) {
        $scope.timetableId = timetableId;
        $scope.studyPeriodId = studyPeriodId;
        $scope.shownPeriodIndex = getShownPeriodIndex($scope.timetables, timetableId);

        QueryUtils.endpoint('/timetables/groupPeriodTimetables/').query(
          {studyPeriodId: $scope.studyPeriodId, timetableId: $scope.timetableId }).$promise.then(function (result) {
            $scope.timetablesByCurriculums = result;
        });
      };
    }
  ]).controller('GeneralTimetableByTeacherController', ['$scope', 'QueryUtils',
    function ($scope, QueryUtils) {
      $scope.currentNavItem = "teacher";
      $scope.timetables = QueryUtils.endpoint('/timetables/generalTimetables/').query();

      QueryUtils.endpoint('/timetables/generalTimetables/').query().$promise.then(function (result) {
        $scope.timetables = result;
        getCurrentWeekTimetables($scope, result);
      });

      $scope.showTimetablesByCurriculums = function (timetableId, studyPeriodId) {
        $scope.timetableId = timetableId;
        $scope.studyPeriodId = studyPeriodId;
        $scope.shownPeriodIndex = getShownPeriodIndex($scope.timetables, timetableId);

        QueryUtils.endpoint('/timetables/teacherPeriodTimetables/').query(
          {studyPeriodId: $scope.studyPeriodId, timetableId: $scope.timetableId }).$promise.then(function (result) {
            $scope.timetablesByTeachers = result;
        });
      };
    }
  ]).controller('GeneralTimetableByRoomController', ['$scope', 'QueryUtils',
      function ($scope, QueryUtils) {
        $scope.currentNavItem = "room";
        $scope.timetables = QueryUtils.endpoint('/timetables/generalTimetables/').query();

        QueryUtils.endpoint('/timetables/generalTimetables/').query().$promise.then(function (result) {
          $scope.timetables = result;
          getCurrentWeekTimetables($scope, result);
        });

        $scope.showTimetablesByCurriculums = function (timetableId, studyPeriodId) {
          $scope.timetableId = timetableId;
          $scope.studyPeriodId = studyPeriodId;
          $scope.shownPeriodIndex = getShownPeriodIndex($scope.timetables, timetableId);

          QueryUtils.endpoint('/timetables/roomPeriodTimetables/').query(
            {studyPeriodId: $scope.studyPeriodId, timetableId: $scope.timetableId }).$promise.then(function (result) {
              $scope.timetablesByRooms = result;
            });
        };
      }
    ]);
}());
