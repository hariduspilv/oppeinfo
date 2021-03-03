'use strict';

angular.module('hitsaOis')
.controller('messageSentController', ['$scope', 'QueryUtils', 'DataUtils', '$route', "USER_ROLES", "AuthService",
  'dialogService', 'message', '$location',
  function ($scope, QueryUtils, DataUtils, $route, USER_ROLES, AuthService, dialogService, message, $location) {
    $scope.STATES = Object.freeze({
      SEARCH: 0,
      DELETE: 1,
    });

    $scope.currentNavItem = 'message.sent';
    $scope.deleteQueryForm = {};
    QueryUtils.createQueryForm($scope, '/message/sent', {order: "-inserted"});
    DataUtils.convertStringToDates($scope.criteria, ['sentFrom', 'sentThru']);
    $scope.auth = $route.current.locals.auth;
    $scope.canSeeAutomatic = $scope.auth.isAdmin() && AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_AUTOTEADE);

    // saved state for back btn
    if ($location.search()['delete']) {
      $scope.state = $scope.STATES.DELETE;
    } else {
      $scope.state = $scope.STATES.SEARCH;
    }
    $scope.$watch('state', function (newVal) {
      if (newVal === $scope.STATES.DELETE) {
        $location.search('delete', true);
      } else {
        $location.search('delete', undefined);
      }
    });

    $scope.showReceivers = function(row, bool) {
        var name = "";
        if( row.receivers) {
            name = row.receivers.join("; ");
            var MAX_INITIAL_LENGTH = 20;
            if(!bool && name.length > MAX_INITIAL_LENGTH) {
                name = name.substring(0, MAX_INITIAL_LENGTH) + "...";
            }
        }
        return name;
    };

    $scope.formState = {
      canSend: AuthService.isAuthorized($route.routes["/message/new"].data.authorizedRoles),
      canDelete: true
    };

    QueryUtils.createQueryForm($scope.deleteQueryForm, '/message/messagesForDelete',
      {order: "-inserted", sent: true, automatic: false, size: 100}, undefined, false, true);

    var ignore = ['page', 'size', 'sent', 'automatic'];
    function updateCriteria(source, destination) {
      for (var key in destination) {
        if (!destination.hasOwnProperty(key) || ignore.indexOf(key) !== -1) {
          continue;
        }
        destination[key] = undefined;
      }
      for (key in source) {
        if (!source.hasOwnProperty(key) || ignore.indexOf(key) !== -1) {
          continue;
        }
        destination[key] = source[key];
      }
    }

    $scope.deleteQueryForm._loadData = $scope.deleteQueryForm.loadData;
    $scope.deleteQueryForm.loadData = function () {
      $scope.deleteQueryForm._loadData();
      updateCriteria($scope.deleteQueryForm.criteria, $scope.criteria);
      $scope.updateStorage();
    };

    function unselectAll() {
      $scope.selectAll = false;
      $scope.updateAllCheckBoxes(false);
    }

    $scope.deleteForm = function () {
      $scope.state = $scope.STATES.DELETE;
      updateCriteria($scope.criteria, $scope.deleteQueryForm.criteria);
      $scope.deleteQueryForm.loadData();
    };

    $scope.changeStateToSearch = function () {
      $scope.state = $scope.STATES.SEARCH;
      unselectAll();
      $scope.loadData();
    };

    $scope.delete = function () {
      if (!$scope.deleteQueryForm) {
        return;
      }
      var ids = $scope.deleteQueryForm.tabledata.content.filter(function (it) {
        return it.delete === true;
      }).map(function (it) {
        return it.id;
      });
      if (ids.length === 0) {
        message.error("main.messages.error.atLeastOneMustBeSelected");
        return;
      }
      dialogService.confirmDialog({prompt: 'message.confirmDelete'}, function () {
        QueryUtils.endpoint('/message/messages').post({receiver: false, ids: ids}, function () {
          $scope.deleteQueryForm.loadData();
          message.info('main.messages.delete.success');
        });
      });
    };

    $scope.updateAllCheckBoxes = function (value) {
      if (!$scope.deleteQueryForm) {
        return;
      }
      $scope.deleteQueryForm.tabledata.content.forEach(function (it) {
        it.delete = value;
      });
    };

    if ($scope.state === $scope.STATES.DELETE) {
      $scope.deleteForm();
    } else {
      $scope.loadData();
    }
}]).controller('messageAutomaticSentController', ['$scope', 'QueryUtils', 'DataUtils', '$route', "USER_ROLES", "AuthService", 'dialogService', 'message', '$location',
  function ($scope, QueryUtils, DataUtils, $route, USER_ROLES, AuthService, dialogService, message, $location) {
    $scope.STATES = Object.freeze({
      SEARCH: 0,
      DELETE: 1,
    });
    $scope.currentNavItem = 'message.automaticSent';
    $scope.deleteQueryForm = {};
    QueryUtils.createQueryForm($scope, '/message/sent/automatic', {order: "-inserted"});
    DataUtils.convertStringToDates($scope.criteria, ['sentFrom', 'sentThru']);
    $scope.auth = $route.current.locals.auth;
    $scope.canSeeAutomatic = $scope.auth.isAdmin() && AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_AUTOTEADE);

    // saved state for back btn
    if ($location.search()['delete']) {
      $scope.state = $scope.STATES.DELETE;
    } else {
      $scope.state = $scope.STATES.SEARCH;
    }
    $scope.$watch('state', function (newVal) {
      if (newVal === $scope.STATES.DELETE) {
        $location.search('delete', true);
      } else {
        $location.search('delete', undefined);
      }
    });

    $scope.showReceivers = function(row, bool) {
        var name = "";
        if( row.receivers) {
            name = row.receivers.join("; ");
            var MAX_INITIAL_LENGTH = 20;
            if(!bool && name.length > MAX_INITIAL_LENGTH) {
                name = name.substring(0, MAX_INITIAL_LENGTH) + "...";
            }
        }
        return name;
    };

    $scope.formState = {
      canSend: false,
      canDelete: true
    };

    QueryUtils.createQueryForm($scope.deleteQueryForm, '/message/messagesForDelete',
      {order: "-inserted", sent: true, automatic: true, size: 100}, undefined, false, true);

    var ignore = ['page', 'size', 'sort', 'sent', 'automatic'];
    function updateCriteria(source, destination) {
      for (var key in destination) {
        if (!destination.hasOwnProperty(key) || ignore.indexOf(key) !== -1) {
          continue;
        }
        destination[key] = undefined;
      }
      for (key in source) {
        if (!source.hasOwnProperty(key) || ignore.indexOf(key) !== -1) {
          continue;
        }
        destination[key] = source[key];
      }
    }

    $scope.deleteQueryForm._loadData = $scope.deleteQueryForm.loadData;
    $scope.deleteQueryForm.loadData = function () {
      $scope.deleteQueryForm._loadData();
      updateCriteria($scope.deleteQueryForm.criteria, $scope.criteria);
      $scope.updateStorage();
    };

    function unselectAll() {
      $scope.selectAll = false;
      $scope.updateAllCheckBoxes(false);
    }

    $scope.deleteForm = function () {
      $scope.state = $scope.STATES.DELETE;
      updateCriteria($scope.criteria, $scope.deleteQueryForm.criteria);
      $scope.deleteQueryForm.loadData();
    };

    $scope.changeStateToSearch = function () {
      $scope.state = $scope.STATES.SEARCH;
      unselectAll();
      $scope.loadData();
    };

    $scope.delete = function () {
      if (!$scope.deleteQueryForm) {
        return;
      }
      var ids = $scope.deleteQueryForm.tabledata.content.filter(function (it) {
        return it.delete === true;
      }).map(function (it) {
        return it.id;
      });
      if (ids.length === 0) {
        message.error("main.messages.error.atLeastOneMustBeSelected");
        return;
      }
      dialogService.confirmDialog({prompt: 'message.confirmDelete'}, function () {
        QueryUtils.endpoint('/message/messages').post({receiver: false, ids: ids}, function () {
          $scope.deleteQueryForm.loadData();
          message.info('main.messages.delete.success');
        });
      });
    };

    $scope.updateAllCheckBoxes = function (value) {
      if (!$scope.deleteQueryForm) {
        return;
      }
      $scope.deleteQueryForm.tabledata.content.forEach(function (it) {
        it.delete = value;
      });
    };

    if ($scope.state === $scope.STATES.DELETE) {
      $scope.deleteForm();
    } else {
      $scope.loadData();
    }
}]).controller('messageReceivedController', ['$scope', 'QueryUtils', 'DataUtils', '$route', '$rootScope', "USER_ROLES", "AuthService", 'dialogService', 'message', '$location',
  function ($scope, QueryUtils, DataUtils, $route, $rootScope, USER_ROLES, AuthService, dialogService, message, $location) {
    $scope.STATES = Object.freeze({
      SEARCH: 0,
      DELETE: 1,
    });

    $scope.currentNavItem = 'message.received';
    $scope.deleteQueryForm = {};
    $scope.auth = $route.current.locals.auth;
    $scope.canSeeAutomatic = $scope.auth.isAdmin() && AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_AUTOTEADE);

    // saved state for back btn
    if ($location.search()['delete']) {
      $scope.state = $scope.STATES.DELETE;
    } else {
      $scope.state = $scope.STATES.SEARCH;
    }
    $scope.$watch('state', function (newVal) {
      if (newVal === $scope.STATES.DELETE) {
        $location.search('delete', true);
      } else {
        $location.search('delete', undefined);
      }
    });

    $scope.formState = {
      canSend: AuthService.isAuthorized($route.routes["/message/new"].data.authorizedRoles),
      canDelete: true
    };

    QueryUtils.createQueryForm($scope, '/message/received', {order: "-inserted"});
    DataUtils.convertStringToDates($scope.criteria, ['sentFrom', 'sentThru']);

    QueryUtils.endpoint('/message/received/new').get().$promise.then(function (result) {
      $rootScope.unreadMessages = result.unread;
    });

    QueryUtils.createQueryForm($scope.deleteQueryForm, '/message/messagesForDelete',
      {order: "-inserted", sent: false, automatic: false, size: 100}, undefined, false, true);

    var ignore = ['page', 'size', 'sort', 'sent', 'automatic'];
    function updateCriteria(source, destination) {
      for (var key in destination) {
        if (!destination.hasOwnProperty(key) || ignore.indexOf(key) !== -1) {
          continue;
        }
        destination[key] = undefined;
      }
      for (key in source) {
        if (!source.hasOwnProperty(key) || ignore.indexOf(key) !== -1) {
          continue;
        }
        destination[key] = source[key];
      }
    }

    $scope.deleteQueryForm._loadData = $scope.deleteQueryForm.loadData;
    $scope.deleteQueryForm.loadData = function () {
      $scope.deleteQueryForm._loadData();
      updateCriteria($scope.deleteQueryForm.criteria, $scope.criteria);
      $scope.updateStorage();
    };

    function unselectAll() {
      $scope.selectAll = false;
      $scope.updateAllCheckBoxes(false);
    }

    $scope.deleteForm = function () {
      $scope.state = $scope.STATES.DELETE;
      updateCriteria($scope.criteria, $scope.deleteQueryForm.criteria);
      $scope.deleteQueryForm.loadData();
    };

    $scope.changeStateToSearch = function () {
      $scope.state = $scope.STATES.SEARCH;
      unselectAll();
      $scope.loadData();
    };

    $scope.delete = function () {
      if (!$scope.deleteQueryForm) {
        return;
      }
      var ids = $scope.deleteQueryForm.tabledata.content.filter(function (it) {
        return it.delete === true;
      }).map(function (it) {
        return it.id;
      });
      if (ids.length === 0) {
        message.error("main.messages.error.atLeastOneMustBeSelected");
        return;
      }
      dialogService.confirmDialog({prompt: 'message.confirmDeleteReceiver'}, function () {
        QueryUtils.endpoint('/message/messages').post({receiver: true, ids: ids}, function () {
          $scope.deleteQueryForm.loadData();
          message.info('main.messages.delete.success');
        });
      });
    };

    $scope.updateAllCheckBoxes = function (value) {
      if (!$scope.deleteQueryForm) {
        return;
      }
      $scope.deleteQueryForm.tabledata.content.forEach(function (it) {
        it.delete = value;
      });
    };

    if ($scope.state === $scope.STATES.DELETE) {
      $scope.deleteForm();
    } else {
      $scope.loadData();
    }
}]).controller('messageAutomaticReceivedController', ['$scope', 'QueryUtils', 'DataUtils', '$route', '$rootScope', "USER_ROLES", "AuthService", 'dialogService', 'message', '$location',
  function ($scope, QueryUtils, DataUtils, $route, $rootScope, USER_ROLES, AuthService, dialogService, message, $location) {
    $scope.STATES = Object.freeze({
      SEARCH: 0,
      DELETE: 1,
    });

    $scope.currentNavItem = 'message.automaticReceived';
    $scope.deleteQueryForm = {};
    QueryUtils.createQueryForm($scope, '/message/received/automatic', {order: "-inserted"});
    DataUtils.convertStringToDates($scope.criteria, ['sentFrom', 'sentThru']);
    $scope.auth = $route.current.locals.auth;
    $scope.canSeeAutomatic = $scope.auth.isAdmin() && AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_AUTOTEADE);

    // saved state for back btn
    if ($location.search()['delete']) {
      $scope.state = $scope.STATES.DELETE;
    } else {
      $scope.state = $scope.STATES.SEARCH;
    }
    $scope.$watch('state', function (newVal) {
      if (newVal === $scope.STATES.DELETE) {
        $location.search('delete', true);
      } else {
        $location.search('delete', undefined);
      }
    });

    QueryUtils.endpoint('/message/received/new').get().$promise.then(function (result) {
      $rootScope.unreadMessages = result.unread;
    });

    $scope.formState = {
      canSend: false,
      canDelete: true
    };

    QueryUtils.createQueryForm($scope.deleteQueryForm, '/message/messagesForDelete',
      {order: "-inserted", sent: false, automatic: true, size: 100}, undefined, false, true);

    var ignore = ['page', 'size', 'sort', 'sent', 'automatic'];
    function updateCriteria(source, destination) {
      for (var key in destination) {
        if (!destination.hasOwnProperty(key) || ignore.indexOf(key) !== -1) {
          continue;
        }
        destination[key] = undefined;
      }
      for (key in source) {
        if (!source.hasOwnProperty(key) || ignore.indexOf(key) !== -1) {
          continue;
        }
        destination[key] = source[key];
      }
    }

    $scope.deleteQueryForm._loadData = $scope.deleteQueryForm.loadData;
    $scope.deleteQueryForm.loadData = function () {
      $scope.deleteQueryForm._loadData();
      updateCriteria($scope.deleteQueryForm.criteria, $scope.criteria);
      $scope.updateStorage();
    };

    function unselectAll() {
      $scope.selectAll = false;
      $scope.updateAllCheckBoxes(false);
    }

    $scope.deleteForm = function () {
      $scope.state = $scope.STATES.DELETE;
      updateCriteria($scope.criteria, $scope.deleteQueryForm.criteria);
      $scope.deleteQueryForm.loadData();
    };

    $scope.changeStateToSearch = function () {
      $scope.state = $scope.STATES.SEARCH;
      unselectAll();
      $scope.loadData();
    };

    $scope.delete = function () {
      if (!$scope.deleteQueryForm) {
        return;
      }
      var ids = $scope.deleteQueryForm.tabledata.content.filter(function (it) {
        return it.delete === true;
      }).map(function (it) {
        return it.id;
      });
      if (ids.length === 0) {
        message.error("main.messages.error.atLeastOneMustBeSelected");
        return;
      }
      dialogService.confirmDialog({prompt: 'message.confirmDeleteReceiver'}, function () {
        QueryUtils.endpoint('/message/messages').post({receiver: true, ids: ids}, function () {
          $scope.deleteQueryForm.loadData();
          message.info('main.messages.delete.success');
        });
      });
    };

    $scope.updateAllCheckBoxes = function (value) {
      if (!$scope.deleteQueryForm) {
        return;
      }
      $scope.deleteQueryForm.tabledata.content.forEach(function (it) {
        it.delete = value;
      });
    };

    if ($scope.state === $scope.STATES.DELETE) {
      $scope.deleteForm();
    } else {
      $scope.loadData();
    }
}]).controller('messageViewController', ['$scope', '$route', 'QueryUtils', 'DataUtils', '$resource', 'config', '$rootScope', 'AuthService',
  function ($scope, $route, QueryUtils, DataUtils, $resource, config, $rootScope, AuthService) {
    var baseUrl = '/message';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var id = $route.current.params.id;

    var backUrl = $route.current.params.backUrl;
    $scope.canSend = AuthService.isAuthorized($route.routes["/message/:id/respond"].data.authorizedRoles);
    $scope.isSent = backUrl !== 'received';

    function afterLoad() {
        DataUtils.convertStringToDates($scope.record, ['inserted']);
        $scope.record.receivers =  $scope.record.receiversNames.join("; ");
        setRead();
    }

    $scope.record = Endpoint.get({id: id}, afterLoad);

    function setRead() {
        if (!$scope.isSent && !$scope.record.isRead) {
            QueryUtils.endpoint('/message/' + $scope.record.id).update().$promise.then(function () {
                updateNewMessagesCount();
            });
        } else {
            updateNewMessagesCount();
        }
    }

    function updateNewMessagesCount() {
        QueryUtils.endpoint('/message/received/new').get().$promise.then(function (result) {
            $rootScope.unreadMessages = result.unread;
        });
    }

}]).controller('messageRespondController', ['$scope', 'QueryUtils', '$route', 'message', '$location',
  function ($scope, QueryUtils, $route, message, $location) {
    var baseUrl = '/message';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var id = $route.current.params.id;

    var backUrl = $route.current.params.backUrl;
    $scope.formState = {
//         backUrl: backUrl && backUrl === "home" ? "#/" : "#/message/" + id + "/view?backUrl=received"
        backUrl: backUrl && backUrl === "home" ? "#/" : "#/messages/received"
    };
    checkIfUserHasEmail();

    function afterLoad() {
        var record = {
            subject: "Re: " + $scope.respondedMessage.subject,
            content: "\n>" + $scope.respondedMessage.content,
            receivers: [{person: $scope.respondedMessage.sendersId, role: $scope.respondedMessage.sendersRole}],
            responseTo: id
        };
        $scope.record = new Endpoint(record);

        // Put cursor to the start of the content textarea
        setTimeout(function(){
            var textarea = document.getElementById("content");
            textarea.focus();
            textarea.selectionStart = 0;
            textarea.selectionEnd = 0;
        }, 0);
    }


    $scope.respondedMessage = Endpoint.get({id: id}, afterLoad);

    $scope.send = function() {
        $scope.messageRespondForm.$setSubmitted();
        if(!$scope.messageRespondForm.$valid) {
            message.error('main.messages.form-has-errors');
            return;
        }
        function afterSend() {
            message.info('message.messageSent');
            $location.path($scope.formState.backUrl.substring(1));
        }
        $scope.record.$save(afterSend);
    };

    function checkIfUserHasEmail() {
        QueryUtils.endpoint(baseUrl + '/hasEmail').get({}, function (response) {
            $scope.noEmail = !response.hasEmail;
        });
    }
}]).controller('messageNewController', ['$scope', 'QueryUtils', '$route', 'message', 'ArrayUtils', '$resource', 'config', '$rootScope', '$q', '$translate', 'dialogService',
  function ($scope, QueryUtils, $route, message, ArrayUtils, $resource, config, $rootScope, $q, $translate, dialogService) {

    var baseUrl = '/message';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    $scope.record = new Endpoint();
    $scope.auth = $route.current.locals.auth;
    $scope.formState = {};
    checkIfUserHasEmail();

    setTargetGroups();
    /** @type {Array<Receiver>} */
    $scope.receivers = [];
    $scope.studyForm = [];
    $scope.studentGroup = [];
    $scope.curriculum = [];
    $scope.studyGroupStudentsParents = [];
    $scope.journalStudent = [];
    $scope.journalStudentParent = [];
    $scope.journalTeacher = [];
    $scope.subjectStudent = [];
    $scope.subjectStudentParent = [];
    $scope.subjectTeacher = [];

    $scope.distinctList = getUniqueElements;

    $scope.groupSearchType = undefined;
    $scope.groupSearchVal = [];
    $scope.isRolePartOfGroupSearch = isRolePartOfGroupSearch;
    $scope.autocompleteControllers = [];
    $scope.groupSearchTypeUrl = 'x';
    $scope.groupSearchLabel = '';
    $scope.filterGroupSearchTypes = filterGroupSearchTypes;
    $scope.updateGroupSearchTypeValues = updateGroupSearchTypeValues;
    var groupSearchStorage = {};

    var ignorePersonId = {};

    function getStudentGroups() {
      QueryUtils.endpoint(baseUrl + '/studentgroups').query({curriculums: $scope.curriculum, studyForm: $scope.studyForm}).$promise.then(function(response) {
        $scope.studentGroups = response.map(function(sg) {
          return { id: sg.id, nameEt: sg.nameEt, nameEn: sg.nameEn };
        });
        var studentGroupIds = response.map(function(sg){return sg.id;});
        $scope.studentGroup = $scope.studentGroup.filter(function(sg){return studentGroupIds.indexOf(sg) !== -1;});
     });
    }

    $scope.addReceiver = function(receiver) {
        if(receiver && !isPersonAdded(receiver.personId)) {
            var newReceiver = new Receiver(receiver, true);
            $scope.receivers.push(newReceiver);
            if (!$scope.auth.isStudent() && $scope.targetGroup === 'ROLL_T' && (!receiver.higher)) {
                getHisParents(newReceiver);
            }
        }
        $scope.receiver = undefined;
    };

    function getHisParents(newReceiver) {
        $resource(config.apiUrl + '/message/' +newReceiver.studentId + '/parents').query().$promise.then(function(response){
            groupRepeatsIntoReceivers(parentsPageToReceiverOptionList(response, true).filter(function(p){return !isPersonAdded(p.personId);}));
        });
    }

    function isPersonAdded(personId) {
      return $scope.receivers.filter(function (r) {
        return r.personId === personId;
      }).length > 0;
    }

    function isStudent(receiver) {
      return receiver.role.items.length === 1 && receiver.role.items[0] === 'ROLL_T';
    }

    $scope.removeReceiver = function(receiver) {
        ArrayUtils.remove($scope.receivers, receiver);
        if(isStudent(receiver)) {
            // student, remove also his/her representative(s)
            $scope.receivers = $scope.receivers.filter(function (r) {
              return r.studentId !== receiver.studentId || r.removeTargetGroup('ROLL_T');
            });
            if (anyFilterApplied()) {
              ignorePersonId[receiver.personId] = true;
            } else {
              ignorePersonId = {};
            }
        }
    };

    /**
     * Finds out if the target is a part of object.
     *
     * @param {Object} object
     * @param {Object} target
     * @param {String} key
     */
    function isSamePartOfByKey(object, target, key) {
        var keyPath = key;
        if (typeof key === "string") {
            keyPath = key.split(".");
        }
        var objValue = object;
        var targetValue = target;
        for (var i = 0; i < keyPath.length; i++) {
            if (angular.isArray(objValue)) {
                if (objValue.length > 1) {
                    for (var x = 0; x < objValue.length; x++) {
                        if (isSamePartOfByKey(objValue[x], targetValue, keyPath.slice(i))) {
                            return true;
                        }
                    }
                    return false;
                } else if (objValue.length === 1) {
                    objValue = objValue[0];
                }
            }
            if (angular.isArray(targetValue)) {
                targetValue = targetValue[0];
            }

            if ((angular.isUndefined(objValue) || objValue === null) || (angular.isUndefined(targetValue) || targetValue === null)) {
                return objValue === targetValue;
            }
            objValue = keyPath[i] in objValue ? objValue[keyPath[i]] : null;
            targetValue = keyPath[i] in targetValue ? targetValue[keyPath[i]] : null;
        }
        return objValue === targetValue;
    }

    /**
     *
     * @param {Object} source
     * @param {Object} container
     * @param {Function} fDuplicateCheck Function. Checks for existing values. Arguments: [element in container]; this: [element from source].
     * @param {Function} fExists Function. Executed when there is any existing value. Arguments: [existing item in container], [duplicate item from source].
     */
    function groupItems (source, container, fDuplicateCheck, fExists) {
        for (var i = 0; i < source.length; i++) {
            var existing = container.filter(fDuplicateCheck, source[i]); // supposed that here we have only 1 element... Hope so
            if (existing.length > 0) {
                fExists(existing[0], source[i]);
            } else {
                container.push(source[i]);
            }
        }
    }

    /**
     * Groups elements in array
     * Used mainly for students/parents
     *
     * @param {Array} list
     */
    function groupRepeatsIntoReceivers(list) {
        groupItems(list, $scope.receivers,
            function (r) {
                return r.personId === this.personId && r.role.items[0] === this.role.items[0];
            },
            function (existing, duplicate) {
                existing.mergeReceiver(duplicate);
            }
        );
    }

    function getStudents(query) {
      var hasParameters = false;
      for (var k in query) {
        if (query.hasOwnProperty(k)) {
          hasParameters = true;
          break;
        }
      }
      if (!hasParameters) {
        return;
      }
      query.page = 0;
      query.size = 1000;
      repeatQuery(baseUrl + "/students", query, function (response) {
        // It returns their parents as well.
        // HITSAOIS-54 8
        // if there are repeated values in the current list then append new values to them and group
        groupRepeatsIntoReceivers(studentAndParentListToReceiverOption(response).filter(function(r) {
          if (r.higher && r.role[0] === 'ROLL_L') {
            return false;
          }
          return !ignorePersonId[r.personId];
        }));
      });
    }

    function repeatQuery(url, query, successCallback) {
      QueryUtils.endpoint(url).query(query, function (response) {
        successCallback(response);
        if (response.length >= query.size) {
          query.page++;
          repeatQuery(url, query, successCallback);
        }
      });
    }

    function studentAndParentListToReceiverOption(response) {
      return response.map(function(s){
        return new Receiver(s, false);
      });
    }

    function filterReceivers(role) {
      $scope.receivers = $scope.receivers.filter(function (r) {
        return r.addedWithAutocomplete || r.removeTargetGroup(role);
      });
    }

    function getUniqueElements(arr) {
      var seen = {};
      return (arr || []).filter(function(it) {
        return !!it && !!it.id;
      }).reduce(function (acc, it) {
        if (!seen[it.id]) {
          acc.push(it);
        }
        seen[it.id] = true;
        return acc;
      }, []);
    }

    $scope.targetGroupChanged = function(newVal, oldVal) {
      getGroups();

      var existingData = groupSearchStorage[newVal];
      var existingSearchGroup;
      for (var key in existingData) {
        if (!existingData.hasOwnProperty(key)) {
          continue;
        }
        if (angular.isArray(existingData[key]) && existingData[key].length > 0) {
          existingSearchGroup = key;
        }
      }
      existingSearchGroup = $scope.groupSearchTypes.find(function (it) {
        return it.code === existingSearchGroup;
      });
      var oldType = $scope.groupSearchType;
      if (existingSearchGroup) {
        $scope.groupSearchType = existingSearchGroup;
      }

      $scope.groupSearchTypeUrl = getGroupSearchUrl();
      $scope.groupSearchLabel = getGroupSearchLabel();
      storeAndReceiveGroupSearchValues(oldVal, (oldType || {}).code,
        newVal, ($scope.groupSearchType || {}).code);
      $scope.autocompleteControllers.forEach(function (ctrl) {
        ctrl.clearSearchText();
      });
    };

    $scope.searchTypeChanged = function(newVal, oldVal) {
      if ($scope.targetGroup === 'ROLL_T') {
        if ($scope.auth.isTeacher()) {
          $scope.studentGroup = [];
          clearGroupSearchValues('ROLL_T', 'SEARCH_STUDENT_GROUP');
        }
        $scope.journalStudent = [];
        $scope.subjectStudent = [];
        clearGroupSearchValues('ROLL_T', 'SEARCH_JOURNAL');
        clearGroupSearchValues('ROLL_T', 'SEARCH_SUBJECTS');
      }
      if ($scope.targetGroup === 'ROLL_L') {
        $scope.studyGroupStudentsParents = [];
        $scope.journalStudentParent = [];
        $scope.subjectStudentParent = [];
        clearGroupSearchValues('ROLL_L');
      }
      if ($scope.targetGroup === 'ROLL_O') {
        $scope.journalTeacher = [];
        $scope.subjectTeacher = [];
        clearGroupSearchValues('ROLL_O');
      }
      getGroups();
      $scope.groupSearchTypeUrl = getGroupSearchUrl();
      $scope.groupSearchLabel = getGroupSearchLabel();
      storeAndReceiveGroupSearchValues($scope.targetGroup, oldVal, $scope.targetGroup, newVal);
      $scope.autocompleteControllers.forEach(function (ctrl) {
        ctrl.clearSearchText();
      });
    };

    function includesOrEmpty(array, item) {
        if (angular.isArray(item)) {
          return item.find(function (it) {
            return includesOrEmpty(array, it);
          });
        }
        return array.length === 0 || array.indexOf(item) !== -1;
    }

    function anyFilterApplied() {
        return $scope.targetGroup === 'ROLL_T' &&
          ($scope.studyForm.length > 0 || $scope.studentGroup.length > 0 || $scope.curriculum.length > 0 ||
            $scope.journalStudent.length > 0 || $scope.subjectStudent.length > 0) ||
          $scope.targetGroup === 'ROLL_L' && ($scope.studyGroupStudentsParents.length > 0 ||
            $scope.journalStudentParent.length > 0 || $scope.subjectStudentParent.length > 0) ||
          $scope.targetGroup === 'ROLL_O' && ($scope.journalTeacher.length > 0 || $scope.subjectTeacher.length > 0);
    }

    function clearAndApply(fApply, oData, role) {
        filterReceivers(role);
        if(anyFilterApplied()) {
          if (angular.isFunction(fApply)) {
            fApply(oData);
          }
        }
    }

    function compareArrays(arr1, arr2) {
      var same = true;
      if (arr1.length !== arr2.length) {
        return false;
      }
      for (var i = 0; i < arr1.length; i++) {
        if (angular.isArray(arr1[i]) && (angular.isArray(arr2[i]))) {
          same = same && compareArrays(arr1[i], arr2[i]);
        } else {
          same = same && (arr1[i] === arr2[i]);
        }
        if (!same) {
          break;
        }
      }
      return same;
    }

    $scope.$watchGroup(['curriculum', 'studyForm', 'journalStudent', 'subjectStudent', 'studentGroup'], function(newValues, oldValues) {
      if ((!newValues[0] || !newValues[0].length) &&
        (!newValues[1] || !newValues[1].length) &&
        (!newValues[2] || !newValues[2].length) &&
        (!newValues[3] || !newValues[3].length) &&
        (!newValues[4] || !newValues[4].length)) {
        clearAndApply(undefined, undefined, ["ROLL_T"]);
        return;
      }
      if (compareArrays(newValues, oldValues)) {
        return;
      }
      clearAndApply(getStudents, {
        studyForm: $scope.studyForm,
        studentGroupId: $scope.studentGroup,
        curriculum: $scope.curriculum,
        journalId: $scope.journalStudent,
        subjectId: $scope.subjectStudent
      }, ['ROLL_T']);
    });

    $scope.$watchGroup(['journalStudentParent', 'subjectStudentParent', 'studyGroupStudentsParents'], function (newValues, oldValues) {
      if ((!newValues[0] || !newValues[0].length) && (!newValues[1] || !newValues[1].length) && (!newValues[2] || !newValues[2].length)) {
        clearAndApply(undefined, undefined, "ROLL_L");
        return;
      }
      if (compareArrays(newValues, oldValues)) {
        return;
      }
      clearAndApply(getParents, {
        journalId: $scope.journalStudentParent,
        subjectId: $scope.subjectStudentParent,
        studentGroupId: $scope.studyGroupStudentsParents
      }, 'ROLL_L');
    });

    $scope.$watchGroup(['journalTeacher', 'subjectTeacher'], function (newValues, oldValues) {
      if ((!newValues[0] || !newValues[0].length) && (!newValues[1] || !newValues[1].length)) {
        clearAndApply(undefined, undefined, "ROLL_O");
        return;
      }
      if (compareArrays(newValues, oldValues)) {
        return;
      }
      clearAndApply(getTeachers, {
        journalId: $scope.journalTeacher,
        subjectId: $scope.subjectTeacher
      }, 'ROLL_O');
    });

    function getParents(query) {
      var hasParameters = false;
      for (var k in query) {
        if (query.hasOwnProperty(k)) {
          hasParameters = true;
          break;
        }
      }
      if (!hasParameters) {
        return;
      }
      query.page = 0;
      query.size = 1000;
      repeatQuery(baseUrl + "/parents", query, function (response) {
        // HITSAOIS-54 8
        groupRepeatsIntoReceivers(parentsPageToReceiverOptionList(response, false));
      });
    }

    function getTeachers(query) {
      var hasParameters = false;
      for (var k in query) {
        if (query.hasOwnProperty(k)) {
          hasParameters = true;
          break;
        }
      }
      if (!hasParameters) {
        return;
      }
      query.page = 0;
      query.size = 1000;
      repeatQuery(baseUrl + "/teachers", query, function (response) {
        // HITSAOIS-54 8
        groupRepeatsIntoReceivers(studentAndParentListToReceiverOption(response));
      });
    }

    function parentsPageToReceiverOptionList(response, addedWithAutocomplete) {
        return response.map(function(p){
          p.role = 'ROLL_L';
          return new Receiver(p, addedWithAutocomplete)
        });
    }

    function getGroups() {
      if ($scope.auth.isTeacher() || $scope.auth.isLeadingTeacher() || $scope.auth.isAdmin()) {
        if (angular.isDefined($scope.groupSearchType) && $scope.groupSearchType !== null) {
          switch ($scope.groupSearchType) {
            case "SEARCH_STUDENT_GROUP":
              getStudentGroups();
              break;
            default:
              break;
          }
        } else {
          getStudentGroups();
        }
      }
    }

    var lookup = QueryUtils.endpoint('/message/persons');
    $scope.querySearch = function (text) {
        var deferred = $q.defer();
        lookup.query({
            role: $scope.targetGroup,
            name: text
        }, function (data) {
            for (var i = 0; i < data.length; i++) {
                data[i].curriculum = [data[i].curriculum];
                data[i].studentGroup = [data[i].studentGroup];
                data[i].journal = data[i].journals;
                data[i].subject = data[i].subjects;
            }
            var container = [];
            groupItems(data, container,
                function (r) {
                    return r.personId === this.personId && r.role[0] === this.role[0];
                },
                function (existing, duplicate) {
                    if (!isSamePartOfByKey(existing, duplicate, "curriculum.id")) {
                        existing.curriculum.push(duplicate.curriculum[0]);
                    }
                    if (!isSamePartOfByKey(existing, duplicate, "studentGroup.id")) {
                        existing.studentGroup.push(duplicate.studentGroup[0]);
                    }
                    if (!isSamePartOfByKey(existing, duplicate, "journal.id")) {
                        existing.journal.push(duplicate.journal[0]);
                    }
                    if (!isSamePartOfByKey(existing, duplicate, "subject.id")) {
                        existing.subject.push(duplicate.subject[0]);
                    }
                }
            );
            deferred.resolve(container);
        });
        return deferred.promise;
    };

    $scope.send = function() {
        $scope.messageNewForm.$setSubmitted();
        if(!formIsValid()) {
            message.error('main.messages.form-has-errors');
            return;
        }
        function afterSend() {
            message.info('message.messageSent');
            $rootScope.back('#/messages/sent');
        }
        $scope.record.receivers = $scope.receivers.map(function(r) {
            return {
                person: r.personId,
                role: (r.role.items || [])[0]
            };
        });
        $scope.record.$save(afterSend);
    };

    function formIsValid() {
        return $scope.messageNewForm.$valid && $scope.receivers.length > 0;
    }

    function checkIfUserHasEmail() {
        QueryUtils.endpoint(baseUrl + '/hasEmail').get({}, function (response) {
            $scope.noEmail = !response.hasEmail;
        });
    }

    function setTargetGroups() {
      QueryUtils.endpoint(baseUrl + '/new').search({}, function (response) {
        $scope.targetGroups = response.targetGroups;
        $scope.targetGroup = response.targetGroup;
        $scope.groupSearchTypes = response.additionalGroup;
      });
    }

    function isRolePartOfGroupSearch() {
      if (!angular.isArray($scope.groupSearchTypes)) {
        return false;
      }
      return $scope.groupSearchTypes.find(function (it) {
        return it.roles.indexOf($scope.targetGroup) !== -1;
      }) !== undefined;
    }

    function getGroupSearchLabel() {
      if (!$scope.groupSearchType) {
        return undefined;
      }
      if ($scope.groupSearchType.code === 'SEARCH_JOURNAL') {
        if ($scope.targetGroup === 'ROLL_T') {
          return 'message.journalStudent';
        } else if ($scope.targetGroup === 'ROLL_L') {
          return 'message.journalStudentParent';
        } else if ($scope.targetGroup === 'ROLL_O') {
          return 'message.journalTeacher';
        }
      } else if ($scope.groupSearchType.code === 'SEARCH_SUBJECTS') {
        if ($scope.targetGroup === 'ROLL_T') {
          return 'message.subjectStudent';
        } else if ($scope.targetGroup === 'ROLL_L') {
          return 'message.subjectStudentParent';
        } else if ($scope.targetGroup === 'ROLL_O') {
          return 'message.subjectTeacher';
        }
      } else if ($scope.groupSearchType.code === 'SEARCH_STUDENT_GROUP') {
        if ($scope.targetGroup === 'ROLL_T') {
          return 'message.studyGroupStudents';
        } else if ($scope.targetGroup === 'ROLL_L') {
          return 'message.studyGroupStudentsParents';
        }
      }
      return undefined;
    }

    function getGroupSearchUrl() {
      if (!$scope.groupSearchType) {
        return 'x';
      }
      if ($scope.groupSearchType.code === 'SEARCH_JOURNAL') {
        return '/message/journals';
      } else if ($scope.groupSearchType.code === 'SEARCH_SUBJECTS') {
        return '/message/subjects';
      } else if ($scope.groupSearchType.code === 'SEARCH_STUDENT_GROUP') {
        return '/message/studentgroups';
      }
      return 'x'; //cannot reach
    }

    function filterGroupSearchTypes(val) {
      if (!val) {
        return false;
      }
      return (val.roles || []).indexOf($scope.targetGroup) !== -1;
    }

    function storeAndReceiveGroupSearchValues(storeTargetGroup, storeGroupSearch, receiveTargetGroup, receiveGroupSearch) {
      if (!!storeTargetGroup) {
        if (!groupSearchStorage[storeTargetGroup]) {
          groupSearchStorage[storeTargetGroup] = {};
        }
        groupSearchStorage[storeTargetGroup][storeGroupSearch] = angular.copy($scope.groupSearchVal, []);
      }
      if (!groupSearchStorage[receiveTargetGroup]) {
        groupSearchStorage[receiveTargetGroup] = {};
      }
      if (!angular.isArray(groupSearchStorage[receiveTargetGroup][receiveGroupSearch])) {
        groupSearchStorage[receiveTargetGroup][receiveGroupSearch] = [];
      }
      $scope.groupSearchVal = groupSearchStorage[receiveTargetGroup][receiveGroupSearch];
    }

    function clearGroupSearchValues(targetGroup, groupSearch) {
      if (!groupSearchStorage[targetGroup]) {
        return;
      }
      if (!groupSearch) {
        groupSearchStorage[targetGroup] = {};
        return;
      }
      groupSearchStorage[targetGroup][groupSearch] = {};
    }

    function getRepresentativeVariableForGroupSearchType(targetGroup, groupSearch) {
      if (!groupSearch) {
        return undefined;
      }
      if (groupSearch === 'SEARCH_JOURNAL') {
        if (targetGroup === 'ROLL_T') {
          return 'journalStudent';
        } else if (targetGroup === 'ROLL_L') {
          return 'journalStudentParent';
        } else if (targetGroup === 'ROLL_O') {
          return 'journalTeacher';
        }
        return '/message/teacherjournals';
      } else if (groupSearch === 'SEARCH_SUBJECTS') {
        if (targetGroup === 'ROLL_T') {
          return 'subjectStudent';
        } else if (targetGroup === 'ROLL_L') {
          return 'subjectStudentParent';
        } else if (targetGroup === 'ROLL_O') {
          return 'subjectTeacher';
        }
      } else if (groupSearch === 'SEARCH_STUDENT_GROUP') {
        if (targetGroup === 'ROLL_T') {
          return 'studentGroup';
        } else if (targetGroup === 'ROLL_L') {
          return 'studyGroupStudentsParents';
        }
      }
      return undefined;
    }

    $scope.$watchCollection('groupSearchVal', updateGroupSearchTypeValues);

    function updateGroupSearchTypeValues() {
      var ref = getRepresentativeVariableForGroupSearchType($scope.targetGroup, ($scope.groupSearchType || {}).code);
      if (!ref) {
        return;
      }
      $scope[ref] = ($scope.groupSearchVal || []).map(function (it) {
        return it.id;
      });
    }

    /**
     *
     * @param {object} receiver
     * @param {boolean} autocomplete
     * @constructor
     */
    function Receiver(receiver, autocomplete) {
      var _self = this;

      // filled by student or representative
      this.studentId = receiver.id;

      this.personId = receiver.personId;
      this.idcode = receiver.idcode;
      this.fullname = receiver.fullname;
      this.firstname = receiver.firstname;
      this.lastname = receiver.lastname;

      // filled by student
      this.higher = receiver.higher;

      this.role = new FilterItem(selfIdentifier);
      this.role.addItem($scope.targetGroup, receiver.role);

      // from which side has been filled
      this.targetGroup = convertToArrayIfNeeded($scope.targetGroup);

      // filled by student
      this.curriculum = new FilterItem(idIdentifier);
      this.curriculum.addItem($scope.targetGroup, receiver.curriculum);
      this.studentGroup = new FilterItem(idIdentifier);
      this.studentGroup.addItem($scope.targetGroup, receiver.studentGroup);

      // filled by student/representative/teacher
      this.journal = new FilterItem(idIdentifier);
      this.journal.addItem($scope.targetGroup, receiver.journals);
      this.subject = new FilterItem(idIdentifier);
      this.subject.addItem($scope.targetGroup, receiver.subjects);

      this.addedWithAutocomplete = autocomplete;

      function convertToArrayIfNeeded(data) {
        if (angular.isArray(data)) {
          return data;
        }
        return [data];
      }

      function selfIdentifier(item) {
        return item;
      }

      function idIdentifier(item) {
        return item.id;
      }

      /**
       *
       * @param {object} receiver
       */
      this.mergeObject = function (receiver) {
        if (_self.targetGroup.indexOf($scope.targetGroup) === -1) {
          _self.targetGroup.push($scope.targetGroup);
        }
        if (receiver.addedWithAutocomplete) {
          _self.addedWithAutocomplete = true;
        }

        _self.role.addItem($scope.targetGroup, receiver.role);
        _self.curriculum.addItem($scope.targetGroup, receiver.curriculum);
        _self.studentGroup.addItem($scope.targetGroup, receiver.studentGroup);
        _self.journal.addItem($scope.targetGroup, receiver.journal);
        _self.subject.addItem($scope.targetGroup, receiver.subject);
      }

      /**
       *
       * @param {Receiver} receiver
       */
      this.mergeReceiver = function (receiver) {
        if (_self.targetGroup.indexOf($scope.targetGroup) === -1) {
          _self.targetGroup.push($scope.targetGroup);
        }
        if (receiver.addedWithAutocomplete) {
          _self.addedWithAutocomplete = true;
        }

        _self.role.addItem($scope.targetGroup, receiver.role.items);
        _self.curriculum.addItem($scope.targetGroup, receiver.curriculum.items);
        _self.studentGroup.addItem($scope.targetGroup, receiver.studentGroup.items);
        _self.journal.addItem($scope.targetGroup, receiver.journal.items);
        _self.subject.addItem($scope.targetGroup, receiver.subject.items);
        receiver.destroy();
      }

      /**
       *
       * @param {string|Array<string>} targetGroup
       * @returns {boolean} true if still exists, false if not
       */
      this.removeTargetGroup = function (targetGroup) {
        if (angular.isArray(targetGroup)) {
          var stillExists = false;
          for (var group in targetGroup) {
            if (!targetGroup.hasOwnProperty(group)) {
              continue;
            }
            stillExists = stillExists || removeTargetGroup(targetGroup[group]);
          }
          return stillExists;
        }
        return removeTargetGroup(targetGroup);
      }

      function removeTargetGroup(targetGroup) {
        var idx = _self.targetGroup.indexOf(targetGroup);
        if (idx === -1) {
          return true;
        }
        _self.targetGroup.splice(idx, 1);
        if (_self.targetGroup.length === 0) {
          _self.destroy();
          return false;
        }
        _self.role.removeTargetGroup(targetGroup);
        _self.curriculum.removeTargetGroup(targetGroup);
        _self.studentGroup.removeTargetGroup(targetGroup);
        _self.journal.removeTargetGroup(targetGroup);
        _self.subject.removeTargetGroup(targetGroup);
        return true;
      }

      this.destroy = function () {
        delete _self.studentId;
        delete _self.personId;
        delete _self.idcode;
        delete _self.fullname;
        delete _self.firstname;
        delete _self.lastname;
        delete _self.higher;

        delete _self.role;
        delete _self.targetGroup;

        delete _self.curriculum;
        delete _self.studentGroup;
        delete _self.journal;
        delete _self.subject;

        delete this;
      }
    }

    /**
     *
     * @param {function} identifier
     * @constructor
     */
    function FilterItem(identifier) {

      this.items = [];
      this.identifier = identifier;

      var _self = this;
      var itemsByIdent = {};

      this.addItem = function (targetGroup, item) {
        if (!targetGroup || !item) {
          return false;
        }
        if (angular.isArray(item)) {
          item.forEach(function (it) {
            addItem(targetGroup, it);
          });
        } else {
          addItem(targetGroup, item);
        }
        return true;
      }

      this.removeTargetGroup = function (targetGroup) {
        if (!targetGroup) {
          return false;
        }
        for (var ident in itemsByIdent) {
          if (!itemsByIdent.hasOwnProperty(ident)) {
            continue;
          }
          var idx = itemsByIdent[ident].indexOf(targetGroup);
          if (idx !== -1) {
            itemsByIdent[ident].splice(idx, 1);
            if (itemsByIdent[ident].length === 0) {
              var i = 0;
              while (i < _self.items.length) {
                var itemIdent = _self.identifier(_self.items[i]);
                if (typeof itemIdent === 'number' && _self.identifier(_self.items[i]) === parseInt(ident) ||
                  _self.identifier(_self.items[i]) === ident) {
                  _self.items.splice(i, 1);
                }
                else {
                  ++i;
                }
              }
            }
          }
        }
        return true;
      }

      function addItem(targetGroup, item) {
        if (!item) {
          return;
        }
        var ident = _self.identifier(item);
        if (!itemsByIdent[ident]) {
          itemsByIdent[ident] = [];
        }
        if (itemsByIdent[ident].indexOf(targetGroup) === -1) {
          itemsByIdent[ident].push(targetGroup);
          var exists = false;
          for (var i = 0; i < _self.items.length; i++) {
            var itemIdent = _self.identifier(_self.items[i]);
            if (itemIdent === ident) {
              exists = true;
              break;
            }
          }
          if (!exists) {
            _self.items.push(item);
          }
        }
      }
    }
}]);
