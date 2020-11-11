'use strict';

angular.module('hitsaOis').constant('SCHOOL_BOARD_COLORS', {
  'groupTimetables': 'green-600',
  'teacherTimetables': 'blue-700',
  'roomTimetables': 'deep-orange-600',
  'currentLessons': 'pink-500',
  'currentLessonsPerRooms': 'deep-purple-A200',
  'freeRooms': 'amber-100'
}).factory('SchoolBoardUtils', ['QueryUtils',
  function (QueryUtils) {
    var schoolBoardUtils = {};

    schoolBoardUtils.getSchool = function (schoolIdentifier) {
      return QueryUtils.endpoint('/schoolBoard/' + schoolIdentifier + '/school/').get().$promise.then(function (result) {
        result.logo = result.logo ? 'data:image/JPEG;base64,' + result.logo : '';
        return result;
      });
    };

    return schoolBoardUtils;
  }
]).controller('SchoolBoardController', ['$location', '$route', '$scope', 'SCHOOL_BOARD_COLORS', 'SchoolBoardUtils',
  function ($location, $route, $scope, SCHOOL_BOARD_COLORS, SchoolBoardUtils) {
    $scope.schoolId = $route.current.params.schoolId;
    $scope.baseUrl = '/schoolBoard/' + $scope.schoolId;
    $scope.colors = SCHOOL_BOARD_COLORS;

    SchoolBoardUtils.getSchool($scope.schoolId).then(function (school) {
      $scope.school = school;
    });

    $scope.openLink = function (link) {
      $location.path(link);
    };
  }
]).controller('SchoolBoardTimetableTypeSelectionController', ['$location', '$route', '$scope', '$timeout', 'SCHOOL_BOARD_COLORS', 'SchoolBoardUtils', 'QueryUtils', 'config',
  function ($location, $route, $scope, $timeout, SCHOOL_BOARD_COLORS, SchoolBoardUtils, QueryUtils, config) {
    $scope.schoolId = $route.current.params.schoolId;
    $scope.type = $route.current.params.type;
    $scope.typeColor = SCHOOL_BOARD_COLORS[$scope.type + 'Timetables'];
    $scope.currentSection = 'schoolBoard.' + $scope.type + 'Timetable';
    var baseUrl = '/schoolBoard/' + $scope.schoolId;

    SchoolBoardUtils.getSchool($scope.schoolId).then(function (school) {
      $scope.school = school;
      if ($scope.type === 'teacher') {
        $scope.currentSection += school.higher ? 'Higher' : 'Vocational';
      }
    });

    $scope.types = QueryUtils.endpoint('/schoolBoard/:schoolIdentifier/:type/timetables').query({
      type: $scope.type,
      schoolIdentifier: $scope.schoolId
    });

    $scope.openTimetable = function (type) {
      $location.path(baseUrl + '/' + $scope.type + '/' + type[$scope.type + 'Id']);
    };

    var returnToSchoolBoardTimeout = $timeout(returnToSchoolBoard, config.schoolBoardRedirectInSeconds * 1000);

    $scope.$on('$destroy', function() {
      $timeout.cancel(returnToSchoolBoardTimeout);
    });

    function returnToSchoolBoard() {
      $location.path(baseUrl);
    }

  }
]).controller('SchoolBoardTimetableController', ['$location', '$route', '$scope', '$timeout', 'SchoolBoardUtils', 'config',
  function ($location, $route, $scope, $timeout, SchoolBoardUtils, config) {
    $scope.schoolId = $route.current.params.schoolId;
    $scope.type = $route.current.params.type;
    $scope.typeId = $route.current.params.typeId;
    $scope.currentSection = 'schoolBoard.' + $scope.type + 'Timetable';
    var baseUrl = '/schoolBoard/' + $scope.schoolId;

    SchoolBoardUtils.getSchool($scope.schoolId).then(function (school) {
      $scope.school = school;
      if ($scope.type === 'teacher') {
        $scope.currentSection += school.higher ? 'Higher' : 'Vocational';
      }
    });

    var returnToSchoolBoardTimeout = $timeout(returnToSchoolBoard, config.schoolBoardRedirectInSeconds * 1000);

    $scope.$on('resetReturnToSchoolBoard', function() {
      $timeout.cancel(returnToSchoolBoardTimeout);
      returnToSchoolBoardTimeout = $timeout(returnToSchoolBoard, config.schoolBoardRedirectInSeconds * 1000);
    });

    $scope.$on('$destroy', function() {
      $timeout.cancel(returnToSchoolBoardTimeout);
    });

    function returnToSchoolBoard() {
      $location.path(baseUrl);
    }
  }
]).controller('SchoolBoardCurrentEventsController', ['$q', '$route', '$scope', '$timeout', 'SCHOOL_BOARD_COLORS', 'WEEKDAY', 'Classifier', 'SchoolBoardUtils', 'QueryUtils', 'config',
  function ($q, $route, $scope, $timeout, SCHOOL_BOARD_COLORS, WEEKDAY, Classifier, SchoolBoardUtils, QueryUtils, config) {
    $scope.schoolId = $route.current.params.schoolId;
    $scope.roomId = $route.current.params.roomId;
    var showSchoolEvents = angular.isUndefined($scope.roomId);

    var baseUrl = '/schoolBoard/' + $scope.schoolId;
    $scope.headerColor = showSchoolEvents ? SCHOOL_BOARD_COLORS.currentLessons : SCHOOL_BOARD_COLORS.currentLessonsPerRooms;

    SchoolBoardUtils.getSchool($scope.schoolId).then(function (school) {
      $scope.school = school;
    });

    if (!showSchoolEvents) {
      $scope.room = QueryUtils.endpoint(baseUrl + '/room/' + $scope.roomId).get();
    }

    var clMapper = Classifier.valuemapper({ capacityType: 'MAHT' });
    var eventsUrl = baseUrl + '/currentEvents' + (!showSchoolEvents ? '/' + $scope.roomId : '');
    var updateCurrentEventsTimeout;
    updateCurrentEvents();

    $scope.$on('$destroy', function() {
      $timeout.cancel(updateCurrentEventsTimeout);
    });

    function updateCurrentEvents() {
      QueryUtils.endpoint(eventsUrl).query().$promise.then(function (result) {
        $q.all(clMapper.promises).then(function () {
          $scope.events = clMapper.objectmapper(result);
          updateDate();
        });
      });
      updateCurrentEventsTimeout = $timeout(updateCurrentEvents, config.schoolBoardRefreshInSeconds * 1000);
    }

    function updateDate() {
      var now = moment().tz('Europe/Tallinn');
      $scope.currentDate = now.format('DD.MM.YYYY');
      $scope.weekday = WEEKDAY[now.weekday()];
    }
  }
]).controller('SchoolBoardRoomsWithCurrentEventsController', ['$location', '$route', '$scope', '$timeout', 'SCHOOL_BOARD_COLORS', 'SchoolBoardUtils', 'QueryUtils', 'config',
  function ($location, $route, $scope, $timeout, SCHOOL_BOARD_COLORS, SchoolBoardUtils, QueryUtils, config) {
    $scope.schoolId = $route.current.params.schoolId;
    $scope.colors = SCHOOL_BOARD_COLORS;
    $scope.currentSection = 'schoolBoard.currentLessons.perRoomsLabel';
    var baseUrl= '/schoolBoard/' + $scope.schoolId;

    SchoolBoardUtils.getSchool($scope.schoolId).then(function (school) {
      $scope.school = school;
    });

    $scope.rooms = QueryUtils.endpoint(baseUrl + '/roomsWithCurrentEvents').query();

    $scope.openRoom = function (room) {
      $location.path('/schoolBoard/' + $scope.schoolId + '/currentEvents/' + room.roomId);
    };

    var returnToSchoolBoardTimeout = $timeout(returnToSchoolBoard, config.schoolBoardRedirectInSeconds * 1000);

    $scope.$on('$destroy', function() {
      $timeout.cancel(returnToSchoolBoardTimeout);
    });

    function returnToSchoolBoard() {
      $location.path(baseUrl);
    }
  }
]).controller('SchoolBoardFreeRoomsController', ['$location', '$route', '$scope', '$timeout', 'SCHOOL_BOARD_COLORS', 'SchoolBoardUtils', 'QueryUtils', 'config',
  function ($location, $route, $scope, $timeout, SCHOOL_BOARD_COLORS, SchoolBoardUtils, QueryUtils, config) {
    $scope.schoolId = $route.current.params.schoolId;
    $scope.type = $route.current.params.type;
    $scope.colors = SCHOOL_BOARD_COLORS;
    $scope.currentSection = 'schoolBoard.freeRooms.label';
    $scope.currentNavItem = $scope.type;
    $scope.currentDate = moment().tz('Europe/Tallinn').format('DD.MM.YYYY HH:mm');
    var baseUrl = '/schoolBoard/' + $scope.schoolId;

    SchoolBoardUtils.getSchool($scope.schoolId).then(function (school) {
      $scope.school = school;
    });

    var returnToSchoolBoardTimeout = $timeout(returnToSchoolBoard, config.schoolBoardRedirectInSeconds * 1000);

    $scope.$on('$destroy', function() {
      $timeout.cancel(returnToSchoolBoardTimeout);
    });

    function returnToSchoolBoard() {
      $location.path(baseUrl);
    }

    $scope.rooms = {};
    $scope.criteria = { size: 20, page: 1, order: 'b_code, r_code' };

    $scope.loadRooms = function () {
      $timeout.cancel(returnToSchoolBoardTimeout);
      returnToSchoolBoardTimeout = $timeout(returnToSchoolBoard, config.schoolBoardRedirectInSeconds * 1000);
      var query = QueryUtils.getQueryParams($scope.criteria);
      $scope.rooms.$promise = QueryUtils.endpoint(baseUrl + '/freeRooms/' + $scope.type).search(query, afterRoomsLoad);
    };

    function afterRoomsLoad(result) {
      $scope.rooms.content = result.content;
      $scope.rooms.totalElements = result.totalElements;
    }

    $scope.loadRooms();
  }
]);
