'use strict';
// todo this to main controller? and use this data on auth?
angular.module('hitsaOis').controller('HomeController', ['$scope', 'School',
  function ($scope, School) {
    $scope.schools = School.getSchoolsWithLogo();
    
  }
]).controller('AuthenticatedHomeController', ['$scope', 'AUTH_EVENTS', 'AuthService', 'QueryUtils', '$resource', 'config', 'Session',
  function ($scope, AUTH_EVENTS, AuthService, QueryUtils, $resource, config, Session) {

    $scope.criteria = {size: 5, page: 1, order: 'inserted, title, id'};
    $scope.generalmessages = {};
    $scope.loadGeneralMessages = function() {
      var query = QueryUtils.getQueryParams($scope.criteria);
      $scope.generalmessages.$promise = QueryUtils.endpoint('/generalmessages/show').search(query, function(result) {
        $scope.generalmessages.content = result.content;
        $scope.generalmessages.totalElements = result.totalElements;
      });
    };
    $scope.unreadMessageCriteria = {size: 5, page: 1, order: '-inserted'};
    $scope.unreadMessages = {};

    $scope.loadUnreadMessages = function() {
      var acceptedRoles = ['ROLL_P', 'ROLL_L', 'ROLL_O', 'ROLL_T', 'ROLL_A'];
      $scope.showUnreadMessages = acceptedRoles.indexOf(Session.roleCode) !== -1;
      if(!$scope.showUnreadMessages) {
          return;
      }
      var query = QueryUtils.getQueryParams($scope.unreadMessageCriteria);
      $scope.unreadMessages.$promise = QueryUtils.endpoint('/message/received/mainPage').search(query, function(result) {
        $scope.unreadMessages.content = result.content;
        $scope.unreadMessages.totalElements = result.totalElements;
      });
    };

    $scope.readMessage = function(message) {
        message.clicked = !message.clicked;
        if(!message.isRead) {
            $resource(config.apiUrl + '/message/' + message.id).update(null).$promise.then(function(){
                message.isRead = true;
            });
        }
    };

    $scope.openCloseDetails = function(message) {
      message.clicked = !message.clicked;
    };

    function afterAuthentication() {
      $scope.loadGeneralMessages();
      $scope.loadUnreadMessages();
      checkIfHasUnacceptedAbsences();
      checkUnconfirmedJournals();
      expiringOccupationStandards();
    }

    if (AuthService.isAuthenticated()) {
      afterAuthentication();
    }
    $scope.$on(AUTH_EVENTS.loginSuccess, afterAuthentication);
    $scope.$on(AUTH_EVENTS.userChanged, afterAuthentication);

    function checkIfHasUnacceptedAbsences() {
      if(['ROLL_A', 'ROLL_O'].indexOf(Session.roleCode) !== -1) {
        QueryUtils.endpoint('/absences/hasUnaccepted').search().$promise.then(function(response) {
          $scope.hasUnacceptedAbsences = response.hasUnaccepted;
        });
      } else {
        $scope.hasUnacceptedAbsences = undefined;
      }
    }

    function checkUnconfirmedJournals() {
      if(['ROLL_A', 'ROLL_O'].indexOf(Session.roleCode) !== -1) {
        QueryUtils.endpoint('/journals/unconfirmedJournalsInfo').search().$promise.then(function(response) {
          $scope.unconfirmedJournals = {
            count: response.unconfirmedJournalCount,
            anyFinished: response.hasEndedUnconfirmedJournals
          };
        });
      } else {
        $scope.unconfirmedJournals = undefined;
      }
    }

    var initialNumberOfOccupationStandards = 3;
    $scope.numberOfOccupationStandards = initialNumberOfOccupationStandards;
    function expiringOccupationStandards() {
      if(['ROLL_A'].indexOf(Session.roleCode) !== -1) {
        $scope.expiringOccupationStandards = QueryUtils.endpoint('/curriculumOccupation/expiringOccupationStandards').query();
      } else {
        $scope.expiringOccupationStandards = undefined;
      }
    }

    $scope.toggleNumberOfOccupationStandards = function() {
      $scope.numberOfOccupationStandards = $scope.numberOfOccupationStandards ? undefined : initialNumberOfOccupationStandards;
    };
  }
]).controller('StudentHomeController', ['$scope', 'QueryUtils', 'Session', 'AUTH_EVENTS',
  function($scope, QueryUtils, Session, AUTH_EVENTS) {

    function getStudentInformation() {
      if (Session.studentId) {
        $scope.sessionStudentId = Session.studentId;
  
        QueryUtils.endpoint('/journals/studentJournalTasks/').search({studentId: Session.studentId}).$promise.then(function (result) {
          getTodayAndTomorrowDate();
          sortTasksByDate(result.tasks.reverse());
          $scope.testTaskTypes = ['SISSEKANNE_H', 'SISSEKANNE_L', 'SISSEKANNE_E', 'SISSEKANNE_I'];
        });
  
        $scope.studentAbsences = QueryUtils.endpoint('/journals/studentJournalAbsences/').query({studentId: Session.studentId});
        $scope.lastResults = QueryUtils.endpoint('/journals/studentJournalLastResults/').query({studentId: Session.studentId});
      } else {
        $scope.sessionStudentId = null;
        $scope.tasks = [];
        $scope.studentAbsences = [];
        $scope.lastResults = [];
      }
    }
    
    function afterAuthentication() {
      getStudentInformation();
    }
    
    getStudentInformation();
    $scope.$on(AUTH_EVENTS.userChanged, afterAuthentication);

    function getTodayAndTomorrowDate() {
      var today, tomorrow;
      today = new Date();
      today.setHours(0, 0, 0, 0);
      tomorrow = new Date();
      tomorrow.setDate(today.getDate() + 1);
      tomorrow.setHours(0, 0, 0, 0);

      $scope.todaysDate = today;
      $scope.tomorrowsDate = tomorrow;
    }

    function sortTasksByDate(tasks) {
      $scope.tasks = {todayTasks: [], tomorrowTasks: [], laterTasks: []};
      for (var i = 0; i < tasks.length; i++) {
        var taskDate = new Date(tasks[i].date);
        taskDate.setHours(0, 0, 0, 0);

        if (taskDate.getTime() === $scope.todaysDate.getTime()) {
          $scope.tasks.todayTasks.push(tasks[i]);
        } else if (taskDate.getTime() === $scope.tomorrowsDate.getTime()) {
          $scope.tasks.tomorrowTasks.push(tasks[i]);
        } else if (taskDate > $scope.tomorrowsDate) {
          $scope.tasks.laterTasks.push(tasks[i]);
        }
      }
    }
  }
]);
