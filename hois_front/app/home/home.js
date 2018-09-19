'use strict';
// todo this to main controller? and use this data on auth?
angular.module('hitsaOis').controller('HomeController', ['$scope', 'School', '$location',
  function ($scope, School, $location) {
    $scope.schools = School.getSchoolsWithLogo();
    
    $scope.openSchoolCurriculumSearch = function (schoolId) {
      $location.path('curriculums/' + schoolId);
    };

  }
]).controller('AuthenticatedHomeController', ['$rootScope', '$scope', '$timeout', 'AUTH_EVENTS', 'AuthService', 'QueryUtils', '$resource', 'config', 'Session', '$filter',
  function ($rootScope, $scope, $timeout, AUTH_EVENTS, AuthService, QueryUtils, $resource, config, Session, $filter) {

    /**
     * Still under question if we need to add a delay for timeout.
     */
    $scope.finish = function () {
      $timeout(function () {
        $scope.balance();
      });
      return false;
    };

    /**
     * Handler.
     * 
     * @param {Function} finishEvent 
     */
    function PageLoadingHandler(finishEvent) {
      this.promises = {};
      this.lock = true;

      this.finishEvent = finishEvent;

      /**
       * Adds a promise and gives functions to this promise.
       * 
       * @param {String} sName 
       * @param {Promise} pPromise 
       * @param {Funciton} fThen 
       * @param {Function} fCatch 
       */
      this.addPromise = function (sName, pPromise, fThen, fCatch) {
        var isPromise = typeof pPromise.then === 'function';
        if (isPromise) {
          pPromise.then(fThen);
          if (fCatch !== null || fCatch !== undefined) {
            pPromise.catch(function (error) {
              console.log(error);
              this.setFinish(sName);
              throw error;
            });
            pPromise.catch(fCatch);
          } else {
            pPromise.catch(function (error) {
              console.log(error);
              this.setFinish(sName);
            });
          }
          this.promises[sName] = pPromise;
        }
      };

      this.setFinish = function (sPromiseName) {
        if (sPromiseName in this.promises) {
          delete this.promises[sPromiseName];
        }
        this.finish();
      };

      /**
       * Allows to run finish function.
       */
      this.enable = function() {
        this.lock = false;
        this.finish();
      };

      /**
       * Resets handler.
       */
      this.reset = function () {
        this.lock = true;
        this.promises = {};
      };

      this.finish = function () {
        if (!this.lock && angular.equals(this.promises, {})) {
          this.finishEvent();
          this.lock = true;
        }
      };
    }

    $scope.pageLoadingHandler = new PageLoadingHandler($scope.finish);

    $scope.criteria = {size: 5, page: 1, order: 'inserted, title, id'};
    $scope.generalmessages = {};
    $scope.loadGeneralMessages = function() {
      $scope.showGeneralMessages = ['ROLL_P', 'ROLL_V'].indexOf(Session.roleCode) === -1;
      if(!$scope.showGeneralMessages) {
          return;
      }
      var query = QueryUtils.getQueryParams($scope.criteria);
      // Not sure if it should be QueryUtils.endpoint('/generalmessages/show').search(query).$promise or just QueryUtils.endpoint('/generalmessages/show').search(query)
      // Before md-progress used the second one, so I will leave it so as it was before.
      $scope.generalmessages.$promise = QueryUtils.endpoint('/generalmessages/show').search(query);
      $scope.pageLoadingHandler.addPromise("generalMessages", $scope.generalmessages.$promise.$promise, function(result) {
        $scope.generalmessages.content = result.content;
        $scope.generalmessages.totalElements = result.totalElements;
        if (result.totalElements === 0) {
          $scope.pageLoadingHandler.setFinish("generalMessages");
        }
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
      $scope.unreadMessages.$promise = QueryUtils.endpoint('/message/received/mainPage').search(query);
      $scope.pageLoadingHandler.addPromise("unreadMessages", $scope.unreadMessages.$promise.$promise, function(result) {
        $scope.unreadMessages.content = result.content;
        $scope.unreadMessages.totalElements = result.totalElements;
        if (result.totalElements === 0) {
          $scope.pageLoadingHandler.setFinish("unreadMessages");
        }
      });
    };

    $scope.readMessage = function(message) {
        message.clicked = !message.clicked;
        if(!message.isRead) {
            $resource(config.apiUrl + '/message/' + message.id).update(null).$promise.then(function(){
                message.isRead = true;
                QueryUtils.endpoint('/message/received/new').get().$promise.then(function (result) {
                  $rootScope.unreadMessages = result.unread;
                });
            });
        }
    };

    $scope.openCloseDetails = function(message) {
      message.clicked = !message.clicked;
    };

    function afterAuthentication() {
      $scope.pageLoadingHandler.reset();
      $scope.loadGeneralMessages();
      $scope.loadUnreadMessages();
      checkIfHasUnacceptedAbsences();
      checkUnconfirmedJournals();
      expiringOccupationStandards();
      studentAfterAuthentication();
      $scope.pageLoadingHandler.enable();
    }

    function studentAfterAuthentication() {
      if (Session.studentId) {
        $scope.sessionStudentId = Session.studentId;
        getStudentInformation();
      } else {
        $scope.sessionStudentId = null;
        $scope.tasks = [];
        $scope.studentAbsences = [];
        $scope.lastResults = [];
      }
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
        $scope.pageLoadingHandler.addPromise("expiringOccupationStandards",
          QueryUtils.endpoint('/curriculumOccupation/expiringOccupationStandards').query().$promise,
          function (result) {
            $scope.expiringOccupationStandards = result;
            if (result.length === 0) {
              $scope.pageLoadingHandler.setFinish("expiringOccupationStandards");
            }
          }
        );
      } else {
        $scope.expiringOccupationStandards = undefined;
      }
    }

    $scope.toggleNumberOfOccupationStandards = function() {
      $scope.numberOfOccupationStandards = $scope.numberOfOccupationStandards ? undefined : initialNumberOfOccupationStandards;
    };

    function getStudentInformation() {
      $scope.pageLoadingHandler.addPromise("studentTasks",
        QueryUtils.endpoint('/journals/studentJournalTasks/').search({studentId: Session.studentId}).$promise,
        function (result) {
          if (angular.isDefined(result.tasks) && result.tasks.length > 0) {
            getTodayAndTomorrowDate();
            sortTasksByDate(result.tasks.reverse());
          } else {
            $scope.pageLoadingHandler.setFinish("studentTasks");
          }
        }
      );

      $scope.testTaskTypes = ['SISSEKANNE_H', 'SISSEKANNE_L', 'SISSEKANNE_E', 'SISSEKANNE_I', 'SISSEKANNE_P', 'SISSEKANNE_R'];

      $scope.pageLoadingHandler.addPromise("studentAbsences",
        QueryUtils.endpoint('/journals/studentJournalAbsences/').query({studentId: Session.studentId}).$promise,
        function (result) {
          $scope.studentAbsences = result;
          if (result.length === 0) {
            $scope.pageLoadingHandler.setFinish("studentAbsences");
          }
        }
      );

      $scope.pageLoadingHandler.addPromise("lastResults",
        QueryUtils.endpoint('/journals/studentJournalLastResults/').query({studentId: Session.studentId}).$promise,
        function (result) {
          $scope.lastResults = result;
          if (result.length === 0) {
            $scope.pageLoadingHandler.setFinish("lastResults");
          }
        }
      );

      $scope.declaration = undefined;
      if (Session.roleCode !== 'ROLL_L') {
        $scope.pageLoadingHandler.addPromise("declaration",
          QueryUtils.endpoint('/declarations/current').get().$promise,
          function (result) {
            $scope.declaration = result;
            if (angular.isUndefined(result.subjects) || result.subjects.length === 0) {
              $scope.pageLoadingHandler.setFinish("declaration");
            }
          }
        );
      } 
    }

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

    /**
     * Returns a grouped arrays by given key.
     * 
     * @param {Array} source Array of objects
     * @param {String} key Key which is used for grouping
     * @param {String} filterName Filter name from $filter if needed. Applied to key's value
     */
    $scope.getGroupedValues = function (source, key, filterName) {
      var groupedResult = {};
      for (var i = 0; i < source.length; i++) {
        var groupKey = (filterName === null) ? source[i][key] : $filter(filterName)(source[i][key]);
        if (groupKey === null) {
          groupKey = "";
        }
        if (groupKey in groupedResult) {
          groupedResult[groupKey].push(source[i]);
        } else {
          groupedResult[groupKey] = [source[i]];
        }
      }
      return groupedResult;
    };
    
    /**
     * Returns array. Removes an unnecessary inner arrays.
     * 
     * @param {Array} source 
     */
    $scope.translateObjectToArray = function (source) {
      var array = [];
      for (var key in source) {
        for (var value in source[key]) {
          array.push(source[key][value]);
        }
      }
      return array;
    };

    /*
    * Hack: Allows to load all the page and render it.
    * Requires to use ng-if (or other check on every $digest) and
    * ng-init inside of dummy html which is included using ng-include.
    */

    $scope.balance = function () {
      var div1 = document.getElementById("column-1");
      var div2 = document.getElementById("column-2");
      if (div1 !== null && div2 !== null) {
        if (div2.children.length !== 0) {
          var itemCount = div2.children.length;
          for (var _ = 0; _ < itemCount; _++) {
            div1.appendChild(div2.children[0]);
          }
        }
        var count = div1.children.length;
        var maxLength = sumChildrenHeight(div1);
        var balance = maxLength / 2;
        for (var i = count - 1; i >= 0; i--) {
          var div2Height = sumChildrenHeight(div2);
          if (div2Height + div1.children[i].clientHeight < balance) {
            addElementAtTop(div1.children[i], div2);
          } else {
            var diffBefore = Math.abs(div2Height - balance);
            var diffAfter = Math.abs(div2Height + div1.children[i].clientHeight - balance);
            if (diffAfter < diffBefore) {
              addElementAtTop(div1.children[i], div2);
            }
            break;
          }
        }
      }
      return false;
    };

    /**
     * Places at top of the container an element.
     * 
     * @param {Element} elem Element to be placed
     * @param {Element} container  Element where elem should be placed.
     */
    function addElementAtTop(elem, container) {
      if (container.children.length === 0) {
        container.appendChild(elem);
      } else {
        container.insertBefore(elem, container.children[0]);
      }
    }

    /**
     * Used to determine the real height of the div block.
     * If it was checked by div.clientHeight then it would return any value but 0
     * in case if we had 2 columns because its height would be the same as the height of his parent.
     * For the view with 1 column (xs, sm, md) it would return 0 as it should
     * with 0 children.
     * 
     * @param {Element} elem 
     */
    function sumChildrenHeight(elem) {
      var sum = 0;
      for (var i = 0; i < elem.children.length; i++) {
        sum += elem.children[i].clientHeight;
      }
      return sum;
    }
  }
]);