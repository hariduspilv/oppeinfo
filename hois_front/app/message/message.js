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
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A, USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_P]
        }
      }).when('/message/:id/respond', {
        templateUrl: 'message/message.respond.html',
        controller: 'messageRespondController',
        controllerAs: 'controller',
        resolve: {translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A, USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_P]
        }
      }).when('/message/new', {
        templateUrl: 'message/message.new.html',
        controller: 'messageNewController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); },
            auth: function (AuthResolver) { return AuthResolver.resolve(); }
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
//         backUrl: backUrl && backUrl === "home" ? "#/" : "#/message/" + id + "/view?backUrl=received"
        backUrl: backUrl && backUrl === "home" ? "#/" : "#/messages/received"
    };

    function afterLoad() {
        var record = {
            subject: "Re: " + $scope.respondedMessage.subject,
            content: "\n>" + $scope.respondedMessage.content,
            receivers: [$scope.respondedMessage.sendersId],
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
}]).controller('messageNewController', ['$scope', 'QueryUtils', '$route', 'message', '$location', 'ArrayUtils', '$resource', 'config', '$rootScope', '$q', function ($scope, QueryUtils, $route, message, $location, ArrayUtils, $resource, config, $rootScope, $q) {

    var baseUrl = '/message';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    $scope.record = new Endpoint();
    $scope.auth = $route.current.locals.auth;

    var backUrl = $route.current.params.backUrl;
    $scope.formState = {
        backUrl: backUrl && backUrl === "received" ? "#/messages/received" : "#/messages/sent"
    };

    // $scope.targetGroups = ["ROLL_O", "ROLL_T", "ROLL_L", "ROLL_P"];
    if($scope.auth.isAdmin()) {
        $scope.targetGroups = ["ROLL_O", "ROLL_T", "ROLL_L", "ROLL_P"];
    } else  if($scope.auth.isTeacher()) {
        $scope.targetGroups = ["ROLL_T", "ROLL_L"];
    } else  if ($scope.auth.isParent() || $scope.auth.isStudent()) {
        $scope.targetGroups = ["ROLL_O"];
        $scope.targetGroup = "ROLL_O";
    }
    $scope.receivers = [];

    $scope.studyForm = [];
    $scope.studentGroup = [];
    $scope.curriculum = [];
    $scope.studyGroupStudentsParents = [];

    var studentGroupSearchUrl = $scope.auth.isAdmin() ? '/studentgroups' : '/message/studentgroups';

    function getStudentGroups() {
        $resource(config.apiUrl + studentGroupSearchUrl, {curriculums: $scope.curriculum, studyForm: $scope.studyForm, size: 1000, sort: 'code'}).get().$promise.then(function(response){
            $scope.studentGroups = response.content.map(function(sg){
                return {
                    id: sg.id,
                    nameEt: sg.code,
                    nameEn: sg.code
                };
            });
            var studentGroupIds = response.content.map(function(sg){return sg.id;});
            $scope.studentGroup = $scope.studentGroup.filter(function(sg){return studentGroupIds.indexOf(sg) !== -1;});
        });
    }
    if(ArrayUtils.includes($scope.targetGroups, "ROLL_T")) {
        getStudentGroups();
    }

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
        query.size = 1000;
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
            return includesOrEmpty(r.role, $scope.targetGroup) && (r.addedWithAutocomplete || 
            includesOrEmpty($scope.curriculum, r.curriculum) && 
            includesOrEmpty($scope.studentGroup, r.studentGroup) && 
            includesOrEmpty($scope.studyForm, r.studyForm) && 
            includesOrEmpty($scope.studyGroupStudentsParents, r.studentGroup));
        });
    }

    $scope.tarGetGroupChanged = function() {
        filterReceivers();
        $scope.curriculum = [];
        $scope.studentGroup = [];
        $scope.studyForm = [];
        $scope.studyGroupStudentsParents = [];
    };

    function includesOrEmpty(array, item) {
        return array.length === 0 || ArrayUtils.includes(array, item);
    }

    function anyFilterApplied() {
        return $scope.studyForm.length > 0 || $scope.studentGroup.length > 0 || $scope.curriculum.length > 0 || $scope.studyGroupStudentsParents.length > 0;
    }

    var previousCurriculum = false;
    $scope.$watch('curriculum', function() {
            if(!previousCurriculum) {
                previousCurriculum = true;
            } else {
                getStudentGroups();
                filterReceivers();
                if(anyFilterApplied()) {
                    getStudents({studyForm: $scope.studyForm, studentGroupId: $scope.studentGroup, curriculum: $scope.curriculum});
                } else {
                    removeAllStudents();
                }
            }
        }
    );

    var previousStudentGroup = false;
    $scope.$watch('studentGroup', function() {
            if(!previousStudentGroup) {
                previousStudentGroup = true;
            } else {
                filterReceivers();
                if(anyFilterApplied()) {
                    getStudents({studyForm: $scope.studyForm, studentGroupId: $scope.studentGroup, curriculum: $scope.curriculum});
                } else {
                    removeAllStudents();
                }
            }
        }
    );

    var previousStudentGroupParents = false;
    $scope.$watch('studyGroupStudentsParents', function() {
            if(!previousStudentGroupParents) {
                previousStudentGroupParents = true;
            } else {
                filterReceivers();
                if(anyFilterApplied()) {
                    getParents({studentGroupId: $scope.studyGroupStudentsParents, size: 1000});
                } else {
                    removeAllStudents();
                }
            }
        }
    );

    function getParents(query) {
        $resource(config.apiUrl + '/message/parents', query).get().$promise.then(function(response){
            console.log(response);
            var list = parentsPageToReceiverOptionList(response).filter(function(p){return !isPersonAdded(p.personId);});
            $scope.receivers = $scope.receivers.concat(list);
        });
    }

    function parentsPageToReceiverOptionList(page) {
        var list = page.content.map(function(p){
            return {
                personId: p.personId,
                idcode: p.idcode,
                fullname: p.fullname,
                role: ["ROLL_L"],
                studentGroup: p.studentGroup, 
            };
        });
        return list;
    }

    var previousStudyForm = false;
    $scope.$watch('studyForm', function() {
            if(!previousStudyForm) {
                previousStudyForm = true;
            } else {
                getStudentGroups();
                filterReceivers();
                if(anyFilterApplied()) {
                    getStudents({studyForm: $scope.studyForm, studentGroupId: $scope.studentGroup, curriculum: $scope.curriculum});
                } else {
                    removeAllStudents();
                }
            }
        }
    );

    function removeAllStudents() {
        $scope.receivers = $scope.receivers.filter(function (r){return r.addedWithAutocomplete;});
    }

    $scope.querySearch = function (text) {
        var url = '/message/persons';
        var lookup = QueryUtils.endpoint(url);
        var deferred = $q.defer();
        lookup.search({
            role: $scope.targetGroup,
            name: text,
            school: $scope.targetGroup === 'ROLL_P' ? undefined : $rootScope.currentUser.school.ehisSchool
        }, function (data) {
            deferred.$$resolve(data.content);
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
            $location.path($scope.formState.backUrl.substring(1));
        }
        $scope.record.receivers = $scope.receivers.map(function(r){return r.personId;});
        $scope.record.$save(afterSend);
    };

    function formIsValid() {
        return $scope.messageNewForm.$valid && $scope.receivers.length > 0;
    }
}]);
