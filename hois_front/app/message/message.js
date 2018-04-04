'use strict';

angular.module('hitsaOis')
.controller('messageSentController', ['$scope', 'QueryUtils', 'DataUtils', '$route', function ($scope, QueryUtils, DataUtils, $route) {
    $scope.currentNavItem = 'message.sent';
    QueryUtils.createQueryForm($scope, '/message/sent', {order: "-inserted"});
    $scope.loadData();
    DataUtils.convertStringToDates($scope.criteria, ['sentFrom', 'sentThru']);
    $scope.auth = $route.current.locals.auth;

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
      canSend: $scope.auth.isAdmin() || $scope.auth.isTeacher() || $scope.auth.isStudent() || $scope.auth.isParent()
    };

}]).controller('messageAutomaticSentController', ['$scope', 'QueryUtils', 'DataUtils', '$route',function ($scope, QueryUtils, DataUtils, $route) {
    $scope.currentNavItem = 'message.automaticSent';
    QueryUtils.createQueryForm($scope, '/message/sent/automatic', {order: "-inserted"});
    $scope.loadData();
    DataUtils.convertStringToDates($scope.criteria, ['sentFrom', 'sentThru']);
    $scope.auth = $route.current.locals.auth;

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

}]).controller('messageReceivedController', ['$scope', 'QueryUtils', 'DataUtils', '$route', '$rootScope', function ($scope, QueryUtils, DataUtils, $route, $rootScope) {
    $scope.currentNavItem = 'message.received';
    QueryUtils.createQueryForm($scope, '/message/received', {order: "-inserted"});
    $scope.loadData();
    DataUtils.convertStringToDates($scope.criteria, ['sentFrom', 'sentThru']);
    $scope.auth = $route.current.locals.auth;

    QueryUtils.endpoint('/message/received/new').get().$promise.then(function (result) {
        $rootScope.unreadMessages = result.unread;
    });

    $scope.formState = {
      canSend: $scope.auth.isAdmin() || $scope.auth.isTeacher() || $scope.auth.isStudent() || $scope.auth.isParent()
    };

}]).controller('messageViewController', ['$scope', '$route', 'QueryUtils', 'DataUtils', '$resource', 'config', '$rootScope', function ($scope, $route, QueryUtils, DataUtils, $resource, config, $rootScope) {
    var baseUrl = '/message';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var id = $route.current.params.id;

    var backUrl = $route.current.params.backUrl;
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
    $scope.formState = {};

    if($scope.auth.isAdmin()) {
        $scope.targetGroups = ['ROLL_O', 'ROLL_T', 'ROLL_L', 'ROLL_P'];
    } else  if($scope.auth.isTeacher()) {
        $scope.targetGroups = ['ROLL_T', 'ROLL_L'];
    } else  if ($scope.auth.isParent() || $scope.auth.isStudent()) {
        $scope.targetGroups = ['ROLL_O'];
    } else {
        $scope.targetGroups = [];
    }
    if($scope.targetGroups.length === 1) {
        $scope.targetGroup = $scope.targetGroups[0];
    }
    $scope.receivers = [];
    $scope.studyForm = [];
    $scope.studentGroup = [];
    $scope.curriculum = [];
    $scope.studyGroupStudentsParents = [];

    function getStudentGroups() {
      QueryUtils.endpoint(baseUrl + '/studentgroups').query({curriculums: $scope.curriculum, studyForm: $scope.studyForm}).$promise.then(function(response) {
        $scope.studentGroups = response.map(function(sg) {
          return { id: sg.id, nameEt: sg.code, nameEn: sg.code };
        });
        var studentGroupIds = response.map(function(sg){return sg.id;});
        $scope.studentGroup = $scope.studentGroup.filter(function(sg){return studentGroupIds.indexOf(sg) !== -1;});
     });
    }
    if($scope.targetGroups.indexOf('ROLL_T') !== -1) {
        getStudentGroups();
    }

    $scope.addReceiver = function(receiver) {
        if(receiver && !isPersonAdded(receiver.personId)) {
            //TODO: array.map is not needed anymore except for addedWithAutocomplete property!!
            var newReceiver = {
                studentId: receiver.id,
                personId: receiver.personId,
                idcode: receiver.idcode,
                fullname: receiver.fullname,
                role: $scope.targetGroup ? [$scope.targetGroup] : receiver.role,
                curriculum: receiver.curriculum,
                studentGroup: receiver.studentGroup,
                addedWithAutocomplete: true
            };
            if($scope.auth.isTeacher()) {
                newReceiver.curriculumObject = receiver.curriculum;
                newReceiver.studentGroupObject = receiver.studentGroup;
            }
            $scope.receivers.push(newReceiver);
            if($scope.targetGroup === 'ROLL_T') {
                getHisParents(newReceiver);
            }
        }
        $scope.receiver = undefined;
    };

    function getHisParents(newReceiver) {
        $resource(config.apiUrl + '/message/' +newReceiver.studentId + '/parents').query().$promise.then(function(response){
            var list = parentsPageToReceiverOptionList(response, true).filter(function(p){return !isPersonAdded(p.personId);});
            $scope.receivers = $scope.receivers.concat(list);
        });
    }

    function isPersonAdded(personId) {
        return $scope.receivers.filter(function (r){return r.personId === personId;}).length > 0;
    }

    function isStudent(receiver) {
      return receiver.role.length === 1 && receiver.role[0] === 'ROLL_T';
    }

    $scope.removeReceiver = function(receiver) {
        ArrayUtils.remove($scope.receivers, receiver);
        if(isStudent(receiver)) {
            // student, remove also his/her representative(s)
            $scope.receivers = $scope.receivers.filter(function(r){return r.studentId !== receiver.studentId;});
        }
    };

    function getStudents(query) {
      query.size = 1000;
      $resource(config.apiUrl + "/" + baseUrl + "/students", query).query().$promise.then(function(response) {
        var list = studentListToReceiverOption(response).filter(function(s) {
          var existing = $scope.receivers.filter(function(r) { return r.personId === s.personId;} );
          if(!existing.length) {
            return true;
          }
          var receiver = existing[0];
          if(isStudent(receiver)) {
            // group add overrides autocomplete add
            receiver.addedWithAutocomplete = false;
          }
          return false;
        });
        $scope.receivers = $scope.receivers.concat(list);
      });
    }

    function studentListToReceiverOption(response) {
        var list = response.map(function(s){
            return {
                studentId: s.id,
                personId: s.personId,
                idcode: s.idcode,
                fullname: s.fullname,
                // role: ["ROLL_T"],
                role: s.role,
                studyForm: s.studyForm,
                curriculum: s.curriculum,
                studentGroup: s.studentGroup,
            };
        });
        return list;
    }

    function filterReceivers() {
        $scope.receivers = $scope.receivers.filter(function(r){
            return (isStudentsParent(r) || includesOrEmpty(r.role, $scope.targetGroup)) &&
            (r.addedWithAutocomplete ||
            includesOrEmpty($scope.curriculum, r.curriculum ? r.curriculum.id : null) &&
            includesOrEmpty($scope.studentGroup, r.studentGroup ? r.studentGroup.id : null) &&
            includesOrEmpty($scope.studyForm, r.studyForm) &&
            includesOrEmpty($scope.studyGroupStudentsParents, r.studentGroup ? r.studentGroup.id : null));
        });
    }
    /**
     * do not remove student's parents, if student is added with autocomplete
     */
    function isStudentsParent(r) {
        return $scope.targetGroup === 'ROLL_T' && includesOrEmpty(r.role, 'ROLL_L');
    }

    $scope.tarGetGroupChanged = function() {
        /*
        filterReceivers() did not work in the following scenario:
        user adds a student which has representative and then selects Parents as target group.
        In that case student's representative hadn't been removed from the list.
        So now list of receivers is just completely cleared.
        */
        // filterReceivers();
        $scope.receivers = [];
        $scope.curriculum = [];
        $scope.studentGroup = [];
        $scope.studyForm = [];
        $scope.studyGroupStudentsParents = [];
    };

    function includesOrEmpty(array, item) {
        return array.length === 0 || array.indexOf(item) !== -1;
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
                    getParents({studentGroupId: $scope.studyGroupStudentsParents});
                } else {
                    removeAllStudents();
                }
            }
        }
    );

    function getParents(query) {
        $resource(config.apiUrl + '/message/parents', query).query().$promise.then(function(response){
            //TODO: array.map is not needed anymore
            var list = parentsPageToReceiverOptionList(response, false).filter(function(p){return !isPersonAdded(p.personId);});
            $scope.receivers = $scope.receivers.concat(list);
        });
    }

    function parentsPageToReceiverOptionList(response, addedWithAutocomplete) {
        var list = response.map(function(p){
            return {
                studentId: p.id,
                personId: p.personId,
                idcode: p.idcode,
                fullname: p.fullname,
                role: ['ROLL_L'],
                studentGroup: p.studentGroup,
                curriculum: p.curriculum,
                addedWithAutocomplete: addedWithAutocomplete,
                hideLearning: true
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

    var lookup = QueryUtils.endpoint('/message/persons');
    $scope.querySearch = function (text) {
        var deferred = $q.defer();
        lookup.query({
            role: $scope.targetGroup,
            name: text
        }, function (data) {
            deferred.resolve(data);
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
        $scope.record.receivers = $scope.receivers.map(function(r){return r.personId;});
        $scope.record.$save(afterSend);
    };

    function formIsValid() {
        return $scope.messageNewForm.$valid && $scope.receivers.length > 0;
    }
}]);
