'use strict';

angular.module('hitsaOis')
.controller('messageSentController', ['$scope', 'QueryUtils', 'DataUtils', '$route', "USER_ROLES", "AuthService",
  function ($scope, QueryUtils, DataUtils, $route, USER_ROLES, AuthService) {
    $scope.currentNavItem = 'message.sent';
    QueryUtils.createQueryForm($scope, '/message/sent', {order: "-inserted"});
    $scope.loadData();
    DataUtils.convertStringToDates($scope.criteria, ['sentFrom', 'sentThru']);
    $scope.auth = $route.current.locals.auth;
    $scope.canSeeAutomatic = $scope.auth.isAdmin() && AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_AUTOTEADE);

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
      canSend: AuthService.isAuthorized($route.routes["/message/new"].data.authorizedRoles)
    };

    angular.element(document).ready(function() {
        var scrollAmount = document.getElementsByName('message.sent')[0].offsetLeft;
        document.getElementById('scrolling_div').scrollLeft += scrollAmount;
    });

}]).controller('messageAutomaticSentController', ['$scope', 'QueryUtils', 'DataUtils', '$route', "USER_ROLES", "AuthService",
  function ($scope, QueryUtils, DataUtils, $route, USER_ROLES, AuthService) {
    $scope.currentNavItem = 'message.automaticSent';
    QueryUtils.createQueryForm($scope, '/message/sent/automatic', {order: "-inserted"});
    $scope.loadData();
    DataUtils.convertStringToDates($scope.criteria, ['sentFrom', 'sentThru']);
    $scope.auth = $route.current.locals.auth;
    $scope.canSeeAutomatic = $scope.auth.isAdmin() && AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_AUTOTEADE);

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
    angular.element(document).ready(function() {
        var scrollAmount = document.getElementsByName('message.automaticSent')[0].offsetLeft;
        document.getElementById('scrolling_div').scrollLeft += scrollAmount;
    });

}]).controller('messageReceivedController', ['$scope', 'QueryUtils', 'DataUtils', '$route', '$rootScope', "USER_ROLES", "AuthService",
  function ($scope, QueryUtils, DataUtils, $route, $rootScope, USER_ROLES, AuthService) {
    $scope.currentNavItem = 'message.received';
    QueryUtils.createQueryForm($scope, '/message/received', {order: "-inserted"});
    $scope.loadData();
    DataUtils.convertStringToDates($scope.criteria, ['sentFrom', 'sentThru']);
    $scope.auth = $route.current.locals.auth;
    $scope.canSeeAutomatic = $scope.auth.isAdmin() && AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_AUTOTEADE);

    QueryUtils.endpoint('/message/received/new').get().$promise.then(function (result) {
        $rootScope.unreadMessages = result.unread;
    });

    $scope.formState = {
      canSend: AuthService.isAuthorized($route.routes["/message/new"].data.authorizedRoles)
    };

}]).controller('messageViewController', ['$scope', '$route', 'QueryUtils', 'DataUtils', '$resource', 'config', '$rootScope', 'AuthService', function ($scope, $route, QueryUtils, DataUtils, $resource, config, $rootScope, AuthService) {
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
}]).controller('messageNewController', ['$scope', 'QueryUtils', '$route', 'message', 'ArrayUtils', '$resource', 'config', '$rootScope', '$q', '$translate', 
function ($scope, QueryUtils, $route, message, ArrayUtils, $resource, config, $rootScope, $q, $translate) {

    var baseUrl = '/message';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    $scope.record = new Endpoint();
    $scope.auth = $route.current.locals.auth;
    $scope.formState = {};

    if ($scope.auth.isAdmin()) {
        $scope.targetGroups = ['ROLL_O', 'ROLL_T', 'ROLL_L', 'ROLL_P'];
    } else if ($scope.auth.isLeadingTeacher()) {
        $scope.targetGroups = ['ROLL_T', 'ROLL_L'];
    } else if ($scope.auth.isTeacher()) {
        $scope.targetGroups = ['ROLL_T', 'ROLL_L'];
        $scope.groupSearchTypes = [{
            code: 'SEARCH_STUDENT_GROUP',
            name: $translate.instant("message.searchTypeByGroups")
        }];
        if ($scope.auth.school.vocational) {
            $scope.groupSearchTypes.push({
                code:'SEARCH_JOURNAL',
                name: $translate.instant("message.searchTypeByJournals")
            });
        }
        if ($scope.auth.school.higher) {
            $scope.groupSearchTypes.push({
                code:'SEARCH_SUBJECTS',
                name: $translate.instant("message.searchTypeBySubjects")
            });
        }
    } else if ($scope.auth.isParent() || $scope.auth.isStudent()) {
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
    $scope.journalStudent = [];
    $scope.journalStudentParent = [];
    $scope.subjectStudent = [];
    $scope.subjectStudentParent = [];

    function getStudentGroups() {
      QueryUtils.endpoint(baseUrl + '/studentgroups').query({curriculums: $scope.curriculum, studyForm: $scope.studyForm}).$promise.then(function(response) {
        $scope.studentGroups = response.map(function(sg) {
          return { id: sg.id, nameEt: sg.code, nameEn: sg.code, isHigher: sg.higher };
        });
        var studentGroupIds = response.map(function(sg){return sg.id;});
        $scope.studentGroup = $scope.studentGroup.filter(function(sg){return studentGroupIds.indexOf(sg) !== -1;});
     });
    }

    function getJournals() {
        QueryUtils.endpoint(baseUrl + '/teacherjournals').query().$promise.then(function (response) {
            $scope.journals = response.map(function (r) {
                return {
                    id: r.id,
                    nameEt: r.nameEt,
                    nameEn: r.nameEn
                };
            });
        });
    }

    function getSubjects() {
        QueryUtils.endpoint(baseUrl + '/teachersubjects').query().$promise.then(function (response) {
            $scope.subjects = response.map(function (r) {
                return {
                    id: r.id,
                    code: r.code,
                    nameEt: r.nameEt,
                    nameEn: r.nameEn
                };
            });
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
                journal: receiver.journal,
                subject: receiver.subject,
                addedWithAutocomplete: true
            };
            if($scope.auth.isTeacher()) {
                newReceiver.curriculumObject = receiver.curriculum;
                newReceiver.studentGroupObject = receiver.studentGroup;
            }
            $scope.receivers.push(newReceiver);
            if($scope.targetGroup === 'ROLL_T' && (receiver === null || !receiver.higher)) {
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
    }

    function getStudents(query) {
        query.size = 1000;
        $resource(config.apiUrl + baseUrl + "/students", query).query().$promise.then(function(response) {
            // It returns their parents as well.
            // HITSAOIS-54 8
            // if there are repeated values in the current list then append new values to them and group
            groupRepeatsIntoReceivers(studentAndParentListToReceiverOption(response).filter(function(r) {
                if (r.higher && r.role[0] === 'ROLL_L') {
                    return false;
                }
                return true;
            }));
        });
    }

    function studentAndParentListToReceiverOption(response) {
        var list = response.map(function(s){
            return {
                studentId: s.id,
                personId: s.personId,
                idcode: s.idcode,
                fullname: s.fullname,
                higher: s.higher,
                // role: ["ROLL_T"],
                role: s.role,
                studyForm: s.studyForm,
                journal: s.journal !== null ? [s.journal] : [],
                subject: s.subject !== null ? [s.subject] : [],
                curriculum: [s.curriculum], // HITSAOIS-54 8 for grouping
                studentGroup: [s.studentGroup] // HITSAOIS-54 8 for grouping
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
            includesOrEmpty($scope.studyGroupStudentsParents, r.studentGroup ? r.studentGroup.id : null) &&
            includesOrEmpty($scope.journalStudent, r.journal ? r.journal.id : null) &&
            includesOrEmpty($scope.journalStudentParent, r.journal ? r.journal.id : null) &&
            includesOrEmpty($scope.subjectStudent, r.subject ? r.subject.id : null) &&
            includesOrEmpty($scope.subjectStudentParent, r.subject ? r.subject.id : null));
        });
    }
    /**
     * do not remove student's parents, if student is added with autocomplete
     */
    function isStudentsParent(r) {
        return $scope.targetGroup === 'ROLL_T' && includesOrEmpty(r.role, 'ROLL_L');
    }

    $scope.targetGroupChanged = function() {
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
        $scope.journalStudent = [];
        $scope.journalStudentParent = [];
        $scope.subjectStudent = [];
        $scope.subjectStudentParent = [];
    };

    $scope.searchTypeChanged = function() {
        //$scope.receivers = $scope.receivers.filter(function (r) { return r.addedWithAutocomplete });
        filterReceivers();
        $scope.curriculum = [];
        $scope.studentGroup = [];
        $scope.studyForm = [];
        $scope.studyGroupStudentsParents = [];
        $scope.journalStudent = [];
        $scope.journalStudentParent = [];
        $scope.subjectStudent = [];
        $scope.subjectStudentParent = [];
    };

    function includesOrEmpty(array, item) {
        return array.length === 0 || array.indexOf(item) !== -1;
    }

    function anyFilterApplied() {
        return $scope.studyForm.length > 0 || $scope.studentGroup.length > 0 || $scope.curriculum.length > 0 ||
            $scope.studyGroupStudentsParents.length > 0 || $scope.journalStudent.length > 0 || $scope.journalStudentParent.length > 0 ||
            $scope.subjectStudent.length > 0 || $scope.subjectStudentParent.length > 0;
    }

    var previousCurriculum = false;
    $scope.$watch('curriculum', function() {
            if(!previousCurriculum) {
                previousCurriculum = true;
            } else {
                getGroups();
                filterReceivers();
                if(anyFilterApplied()) {
                    getStudents({studyForm: $scope.studyForm, studentGroupId: $scope.studentGroup, curriculum: $scope.curriculum});
                } else {
                    removeAllStudents();
                }
            }
        }
    );

    function clearAndApply(fApply, oData) {
        filterReceivers();
        if(anyFilterApplied()) {
            fApply(oData);
        } else {
            removeAllStudents();
        }
    }

    /*
     * We have to block the first run in $watch because when page is loaded then watch is fired.
     */

    var blockLoadingJournalStudent = true;
    $scope.$watch('journalStudent', function() {
        if (blockLoadingJournalStudent) {
            blockLoadingJournalStudent = false;
        } else {
            clearAndApply(getStudents, {journalId: $scope.journalStudent});
        }
    });

    var blockLoadingJournalStudentParent = true;
    $scope.$watch('journalStudentParent', function() {
        if (blockLoadingJournalStudentParent) {
            blockLoadingJournalStudentParent = false;
        } else {
            clearAndApply(getParents, {journalId: $scope.journalStudentParent});
        } 
    });
    
    var blockLoadingSubjectStudent = true;
    $scope.$watch('subjectStudent', function() {
        if (blockLoadingSubjectStudent) {
            blockLoadingSubjectStudent = false;
        } else {
            clearAndApply(getStudents, {subjectId: $scope.subjectStudent});
        }
    });
    
    var blockLoadingSubjectStudentParent = true;
    $scope.$watch('subjectStudentParent', function() {
        if (blockLoadingSubjectStudentParent) {
            blockLoadingSubjectStudentParent = false;
        } else {
            clearAndApply(getParents, {subjectId: $scope.subjectStudentParent});
        }
    });

    var blockLoadingStudentGroup = true;
    $scope.$watch('studentGroup', function() {
        if (blockLoadingStudentGroup) {
            blockLoadingStudentGroup = false;
        } else {
            clearAndApply(getStudents, {studyForm: $scope.studyForm, studentGroupId: $scope.studentGroup, curriculum: $scope.curriculum});
        }
    });

    var blockLoadingStudentGroupStudentsParents = true;
    $scope.$watch('studyGroupStudentsParents', function() {
        if (blockLoadingStudentGroupStudentsParents) {
            blockLoadingStudentGroupStudentsParents = false;
        } else {
            clearAndApply(getParents, {studentGroupId: $scope.studyGroupStudentsParents});
        }
    });

    function getParents(query) {
        $resource(config.apiUrl + '/message/parents', query).query().$promise.then(function(response){
            // HITSAOIS-54 8
            groupRepeatsIntoReceivers(parentsPageToReceiverOptionList(response, false));
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
                journal: p.journal !== null ? [p.journal] : [],
                subject: p.subject !== null ? [p.subject] : [],
                studentGroup: [p.studentGroup],
                curriculum: [p.curriculum],
                addedWithAutocomplete: addedWithAutocomplete
            };
        });
        return list;
    }

    var previousStudyForm = false;
    $scope.$watch('studyForm', function() {
            if(!previousStudyForm) {
                previousStudyForm = true;
            } else {
                getGroups();
                filterReceivers();
                if(anyFilterApplied()) {
                    getStudents({studyForm: $scope.studyForm, studentGroupId: $scope.studentGroup, curriculum: $scope.curriculum});
                } else {
                    removeAllStudents();
                }
            }
        }
    );

    function getGroups() {
        if ($scope.auth.isTeacher()) {
            if (angular.isDefined($scope.groupSearchType) && $scope.groupSearchType !== null) {
                switch ($scope.groupSearchType) {
                    case "SEARCH_STUDENT_GROUP":
                        getStudentGroups();
                        break;
                    case "SEARCH_JOURNAL":
                        getJournals();
                        break;
                    case "SEARCH_SUBJECTS":
                        getSubjects();
                        break;
                    default:
                        break;
                }
            }
        } else {
            getStudentGroups();
        }
    }

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
            for (var i = 0; i < data.length; i++) {
                data[i].curriculum = [data[i].curriculum];
                data[i].studentGroup = [data[i].studentGroup];
                data[i].journal = [data[i].journal];
                data[i].subject = [data[i].subject];
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
        $scope.record.receivers = $scope.receivers.map(function(r){return r.personId;});
        $scope.record.$save(afterSend);
    };

    function formIsValid() {
        return $scope.messageNewForm.$valid && $scope.receivers.length > 0;
    }
}]);
