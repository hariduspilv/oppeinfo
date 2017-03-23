'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
      .when('/messages/sent', {
        templateUrl: 'message/message.sent.html',
        controller: 'messageSentController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      })
      .when('/messages/received', {
        templateUrl: 'message/message.received.html',
        controller: 'messageReceivedController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      }).when('/message/:id/view', {
        templateUrl: 'message/message.view.html',
        controller: 'messageViewController',
        controllerAs: 'controller',
        resolve: {translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      }).when('/message/:id/respond', {
        templateUrl: 'message/message.respond.html',
        controller: 'messageRespondController',
        controllerAs: 'controller',
        resolve: {translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      }).when('/message/new', {
        templateUrl: 'message/message.new.html',
        controller: 'messageNewController',
        controllerAs: 'controller',
        resolve: {translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      });
}]).controller('messageSentController', ['$scope', 'QueryUtils', function ($scope, QueryUtils) {
    $scope.currentNavItem = 'message.sent';
    QueryUtils.createQueryForm($scope, '/message/sent', {order: "-inserted"});
    $scope.loadData();

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

}]).controller('messageReceivedController', ['$scope', 'QueryUtils', function ($scope, QueryUtils) {
    $scope.currentNavItem = 'message.received';
    QueryUtils.createQueryForm($scope, '/message/received', {order: "-inserted"});
    $scope.loadData();
}]).controller('messageViewController', ['$scope', 'QueryUtils', '$route', 'DataUtils', '$resource', 'config', function ($scope, QueryUtils, $route, DataUtils, $resource, config) {
    var baseUrl = '/message';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var id = $route.current.params.id;

    var backUrl = $route.current.params.backUrl;
    $scope.formState = {
        backUrl: backUrl === "received" ?  "#/messages/received" : "#/messages/sent"
    };
    $scope.isSent = $scope.formState.backUrl === "#/messages/sent";

    function afterLoad() {
        DataUtils.convertStringToDates($scope.record, ['inserted']);
        $scope.record.receivers =  $scope.record.receiversNames.join("; ");
        setRead();
    }
    
    $scope.record = Endpoint.get({id: id}, afterLoad);

    function setRead() {
        if(!$scope.isSent && !$scope.record.isRead) {
            $resource(config.apiUrl + '/message/' + $scope.record.id).update(null);
        }
    }


}]).controller('messageRespondController', ['$scope', 'QueryUtils', '$route', 'message', '$location', function ($scope, QueryUtils, $route, message, $location) {
    var baseUrl = '/message';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var id = $route.current.params.id;

    var backUrl = $route.current.params.backUrl;
    $scope.formState = {
        backUrl: backUrl && backUrl === "home" ? "#/" : "#/message/" + id + "/view?backUrl=received"
    };

    function afterLoad() {
        var record = {
            subject: "Re: " + $scope.respondedMessage.subject,
            content: "> " + $scope.respondedMessage.subject,
            receivers: [$scope.respondedMessage.sendersId],
            responseTo: id
        };
        $scope.record = new Endpoint(record);
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
}]).controller('messageNewController', ['$scope', 'QueryUtils', '$route', 'message', '$location', 'ArrayUtils', '$resource', 'config', function ($scope, QueryUtils, $route, message, $location, ArrayUtils, $resource, config) {

    var baseUrl = '/message';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    $scope.record = new Endpoint();

    var backUrl = $route.current.params.backUrl;
    $scope.formState = {
        backUrl: backUrl && backUrl === "received" ? "#/messages/received" : "#/messages/sent"
    };

    $scope.targetGroups = ["ROLL_O", "ROLL_T", "ROLL_L", "ROLL_P"];
    $scope.receivers = [];

    $scope.studyForm = [];
    $scope.studentGroup = [];
    $scope.curriculum = [];

    $scope.addReceiver = function(receiver) {
        if(receiver && !isPersonAdded(receiver.id)) {
            $scope.receivers.push({
                personId: receiver.id,
                idcode: receiver.idcode,
                fullname: receiver.name,
                role: $scope.targetGroup ? [$scope.targetGroup] : receiver.role,
                addedWithAutocomplete: true
            });
        }
        $scope.receiver = undefined;
    };

    function isPersonAdded(personId) {
        return $scope.receivers.filter(function (r){return r.personId === personId;}).length > 0;
    }

    $scope.removeReceiver = function(receiver) {
        ArrayUtils.remove($scope.receivers, receiver);
    };

    function getStudents(query) {
        $resource(config.apiUrl + "/students", query).get().$promise.then(function(response){
            var list = studentListToReceiverOption(response).filter(function(s){return !isPersonAdded(s.personId);});
            $scope.receivers = $scope.receivers.concat(list);
        });
    }

    function studentListToReceiverOption(page) {
        var list = page.content.map(function(s){
            return {
                personId: s.personId,
                idcode: s.idcode,
                fullname: s.fullname,
                role: ["ROLL_T"],
                curriculum: s.curriculum.id,
                studentGroup: s.studentGroup.id, 
                studyForm: s.studyForm
            };
        });
        return list;
    }

    function filterReceivers() {
        $scope.receivers = $scope.receivers.filter(function(r){
            console.log(r.fullname + " " + !r.addedWithAutocomplete);
            return r.addedWithAutocomplete || 
            ArrayUtils.includes($scope.curriculum, r.curriculum) || 
            ArrayUtils.includes($scope.studentGroup, r.studentGroup) || 
            ArrayUtils.includes($scope.studyForm, r.studyForm);
        });
    }

    $scope.getStudentsByCurriculum = function() {
        if($scope.curriculum.length > 0) {
            getStudents({curriculum: $scope.curriculum});
        }
    };

    var previousCurriculum = false;
    $scope.$watch('curriculum', function(newValue, oldValue) {
            if(!previousCurriculum) {
                previousCurriculum = true;
            } else {
                if (!newItemAdded(newValue, oldValue)){
                    filterReceivers();
                } else {
                    $scope.getStudentsByCurriculum();
                }
            }
        }
    );

    $scope.getStudentsByStudentGroup = function() {
        if($scope.studentGroup.length > 0) {
            getStudents({studentGroupId: $scope.studentGroup});
        }
    };

    var previousStudentGroup = false;
    $scope.$watch('studentGroup', function(newValue, oldValue) {
            if(!previousStudentGroup) {
                previousStudentGroup = true;
            } else {
                if (!newItemAdded(newValue, oldValue)){
                    filterReceivers();
                } else {
                    $scope.getStudentsByStudentGroup();
                }
            }
        }
    );

    $scope.getStudentsByStudyForm = function() {
        if($scope.studyForm.length > 0) {
            getStudents({studyForm: $scope.studyForm});
        }
    };

    var previousStudyForm = false;
    $scope.$watch('studyForm', function(newValue, oldValue) {
            if(!previousStudyForm) {
                previousStudyForm = true;
            } else {
                if (!newItemAdded(newValue, oldValue)){
                    filterReceivers();
                } else {
                    $scope.getStudentsByStudyForm();
                }
            }
        }
    );

    function newItemAdded(newArray, oldArray) {
        return newArray.length > oldArray.length;
    }

    $scope.querySearch = function(queryText) {
      if(!queryText || queryText.length < 3) {
        return [];
      }
      var query = {
          role: $scope.targetGroup,
          name: queryText
      };
      var resource = $resource(config.apiUrl + '/users/list');
      return resource.query(query).$promise;    
    };

    $scope.send = function() {
        $scope.messageNewForm.$setSubmitted();
        if(!formIsValid()) {
            message.error('main.messages.form-has-errors');
            return;
        }
        function afterSend() {
            message.info('message.messageSent');
            $location.path($scope.formState.backUrl.substring(1));
        }
        $scope.record.receivers = $scope.receivers.map(function(r){return r.personId;});
        $scope.record.$save(afterSend);
    };

    function formIsValid() {
        return $scope.messageNewForm.$valid && $scope.receivers.length > 0;
    }
}]);
