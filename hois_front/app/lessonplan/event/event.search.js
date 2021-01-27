'use strict';

angular.module('hitsaOis').controller('LessonplanEventSearchController',
  function ($httpParamSerializer, $route, $scope, USER_ROLES, AuthService, QueryUtils, config, dialogService) {
    $scope.currentNavItem = 'events';
    $scope.auth = $route.current.locals.auth;
    $scope.eventsPerm = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_SYNDMUS);
    $scope.canEdit = AuthService.isAuthorized([USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_SYNDMUS, USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_PERSYNDMUS]);
    $scope.canViewPersonalEvents = $scope.auth.isAdmin() || ($scope.auth.isLeadingTeacher() && AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_PERSYNDMUS));
    $scope.formState = {};
    var baseUrl = '/timetableevents';

    $scope.formState.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriodsWithYear').query();
    $scope.formState.studyPeriods.$promise.then(function (response) {
      response.forEach(function (studyPeriod) {
        studyPeriod[$scope.currentLanguageNameField()] = $scope.currentLanguageNameField(studyPeriod.studyYear) + ' ' + $scope.currentLanguageNameField(studyPeriod);
      });
    });

    QueryUtils.createQueryForm($scope, baseUrl);

    if ($scope.eventsPerm) {
      if (!angular.isDefined($scope.criteria.singleEvent)) {
        $scope.criteria.singleEvent = true;
      }
      if (!angular.isDefined($scope.criteria.personalEvent)) {
        $scope.criteria.personalEvent = false;
      }
      if (!angular.isDefined($scope.criteria.juhanEvent)) {
        $scope.criteria.juhanEvent = false;
      }
    }

    if (!angular.isDefined($scope.criteria.from)) {
      $scope.criteria.from = new Date();
    }
    $scope.loadData();

    $scope.$watch('criteria.roomObject', function () {
      $scope.criteria.room = $scope.criteria.roomObject ? $scope.criteria.roomObject.id : null;
    });

    $scope.isPersonalEventChanged = function () {
      if (!$scope.criteria.singleEvent) {
        $scope.criteria.singleEvent = true;
      }
    };

    $scope.isJuhanEventChanged = function () {
      if (!$scope.criteria.singleEvent) {
        $scope.criteria.singleEvent = true;
      }
    };

    var _clearCriteria = $scope.clearCriteria;
    $scope.directiveControllers = [];
    $scope.clearEventCriteria = function () {
      _clearCriteria();
      $scope.criteria.singleEvent = false;
      $scope.criteria.personalEvent = false;
      $scope.criteria.juhanEvent = false;
      $scope.directiveControllers.forEach(function (c) {
        c.clear();
      });
      $scope.criteria.order = null;
    };

    $scope.printUrl = function () {
      return config.apiUrl + baseUrl + '/search.pdf?' + $httpParamSerializer($scope.criteria);
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
      if (event.isJuhanEvent) {
        return false;
      }
      if ($scope.auth.isAdmin()) {
        return !event.isPersonal || event.person.id === $scope.auth.person;
      } else if ($scope.auth.isLeadingTeacher()) {
        return event.singleEvent && (!event.isPersonal || event.person.id === $scope.auth.person);
      } else if ($scope.auth.isTeacher()) {
        return event.singleEvent && event.insertedTeacherId === $scope.auth.teacher;
      }
      return false;
    };

    $scope.openEventRelatedEntitiesDialog = function (event, entityType) {
      dialogService.showDialog('lessonplan/event/event.related.entities.dialog.html', function (dialogScope) {
        dialogScope.event = event;
        dialogScope.entityType = entityType;

        dialogScope.tableData = { size: 10 };
        if (entityType === 'teacher') {
          dialogScope.tableData.content = event.teachers;
          dialogScope.searchLabel = $scope.auth.higher ? 'lessonplan.event.teachersHigher' : 'lessonplan.event.teachersVocational';
          dialogScope.searchParam = 'name';
        } else if (entityType === 'studentGroup') {
          dialogScope.tableData.content = event.studentGroups;
          dialogScope.searchLabel = 'lessonplan.event.studentGroups';
          dialogScope.searchParam = 'code';
        } else if (entityType === 'student') {
          dialogScope.tableData.content = event.students;
          dialogScope.searchLabel = 'lessonplan.event.students';
          dialogScope.searchParam = 'name';
        }
      });
    };
  })
  .controller('LessonplanEventRoomSearchController', ['$scope', 'QueryUtils', 'FormUtils', '$timeout', 'message', 'USER_ROLES',
  function ($scope, QueryUtils, FormUtils, $timeout, message, USER_ROLES) {
    $scope.currentNavItem = 'rooms';
    var startTimeCopy, endTimeCopy;
    $scope.canEdit = $scope.isAuthorized([USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_SYNDMUS, USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_PERSYNDMUS]);
    $scope.canAddEvent = canAddEvent;

    $scope.setPartlyBox = checkCheckboxPartlyBusy;
    $scope.event = getEventLink;

    QueryUtils.createQueryForm($scope, '/timetableevents/rooms', {isFreeRoom: true, from: new Date(), thru: new Date()});

    var _loadData = $scope.loadData;
    $scope.loadData = function() {
      startTimeCopy = angular.copy($scope.criteria.startTime);
      endTimeCopy = angular.copy($scope.criteria.endTime);
      if ($scope.searchForm) {
        $scope.timeError = $scope.criteria.startTime && $scope.criteria.endTime && $scope.criteria.startTime >= $scope.criteria.endTime;

        if ($scope.timeError) {
          message.error('main.messages.form-has-errors');
          return;
        }
        FormUtils.withValidForm($scope.searchForm, _loadData);
      }
    };

    var _clearCriteria = $scope.clearCriteria;
    $scope.clearCriteria = function () {
      _clearCriteria();
      $scope.criteria.isFreeRoom = true;
    }

    // Initialize form before.
    $timeout($scope.loadData);

    function checkCheckboxPartlyBusy() {
      if (!$scope.criteria.isFreeRoom) {
        $scope.criteria.isPartlyBusyRoom = false;
      }
    }

    function getEventLink(row) {
      return '#/timetable/timetableEvent/new?' +
             'room=' + row.id + '&' +
             ((row.times || []).length === 0 && startTimeCopy ? 'startTime=' + (startTimeCopy instanceof Date ? startTimeCopy.toISOString() : startTimeCopy) + '&' : '') +
             ((row.times || []).length === 0 && endTimeCopy ? 'endTime=' + (endTimeCopy instanceof Date ? endTimeCopy.toISOString() : endTimeCopy) + '&' : '') +
             'date=' + row.startDate;
    }

    function canAddEvent(row) {
      return $scope.canEdit && $scope.criteria.isFreeRoom && row.isUsedInStudy;
    }
  }]);
