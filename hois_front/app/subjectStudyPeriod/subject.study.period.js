'use strict';

angular.module('hitsaOis').controller('SubjectStudyPeriodSearchController', ['$scope', 'QueryUtils', 'DataUtils', '$route', 'ArrayUtils', 'Classifier', 'message',
  function ($scope, QueryUtils, DataUtils, $route, ArrayUtils, Classifier, message) {
    QueryUtils.createQueryForm($scope, '/subjectStudyPeriods', {order: 's.' + $scope.currentLanguageNameField()});

    $scope.auth = $route.current.locals.auth;

    function setCurrentStudyPeriod() {
        if($scope.criteria && !ArrayUtils.isEmpty($scope.studyPeriods) && !$scope.criteria.studyPeriods) {
            $scope.criteria.studyPeriods = DataUtils.getCurrentStudyYearOrPeriod($scope.studyPeriods).id;
        }
    }

    Classifier.queryForDropdown({mainClassCode: 'AINEPROGRAMM_STAATUS'}, function(response) {
        $scope.statusOptions = Classifier.toMap(response);
    });

    $scope.load = function() {
        if (!$scope.searchForm.$valid) {
          message.error('main.messages.form-has-errors');
          return false;
        } else {
          $scope.loadData();
        }
      };

    QueryUtils.endpoint('/autocomplete/studyPeriodsWithYear').query().$promise.then(function(response){
        $scope.studyPeriods = response;
        $scope.studyPeriods.forEach(function (studyPeriod) {
          studyPeriod[$scope.currentLanguageNameField()] = $scope.currentLanguageNameField(studyPeriod.studyYear) + ' ' + $scope.currentLanguageNameField(studyPeriod);
        });
        setCurrentStudyPeriod();
        $scope.loadData();
    });

    $scope.$watch('criteria.studentObject', function() {
      if($scope.criteria) {
        $scope.criteria.student = $scope.criteria.studentObject ? $scope.criteria.studentObject.id : null;
      }
    });

    $scope.showTeachers = function(row, bool) {
        var name = "";
        if( row.teachers) {
            name = row.teachers.join("; ");
            var MAX_INITIAL_LENGTH = 30;
            if(!bool && name.length > MAX_INITIAL_LENGTH) {
                name = name.substring(0, MAX_INITIAL_LENGTH) + "...";
            }
        }
        return name;
    };

    $scope.testStatus = { code: "AINEPROGRAMM_STAATUS_L"};
  }
]).controller('SubjectStudyPeriodEditController', ['$scope', 'QueryUtils', 'ArrayUtils', '$route', 'dialogService', 'message', '$q', '$rootScope', 'DeclarationType',
  function ($scope, QueryUtils, ArrayUtils, $route, dialogService, message, $q, $rootScope, DeclarationType) {

    var baseUrl = '/subjectStudyPeriods';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var id = $route.current.params.id;

    var studyPeriodId = $route.current.params.studyPeriodId ? parseInt($route.current.params.studyPeriodId, 10) : null;
    var studentGroupId = $route.current.params.studentGroupId ? parseInt($route.current.params.studentGroupId, 10) : null;
    var subjectId = $route.current.params.subjectId ? parseInt($route.current.params.subjectId, 10) : null;
    var teacherId = $route.current.params.teacherId ? parseInt($route.current.params.teacherId, 10) : null;

    $scope.hasObligatoryStudentGroup = studentGroupId !== null;
    $scope.obligatoryTeacher = teacherId;

    QueryUtils.endpoint('/subjectStudyPeriods/studentGroups/list').query(function(result) {
      $scope.studentGroups = result.filter(function(el){
          return studentGroupId ? el.id !== studentGroupId : true;
      }).map(function(el){
          var newEl = el;
          newEl.nameEt = el.code;
          newEl.nameEn = el.code;
          return newEl;
      });
    });

    $rootScope.removeLastUrlFromHistory(function(lastUrl){
      return lastUrl && (lastUrl.indexOf('subjectStudyPeriod/' + id + '/view') !== -1);
    });

    if(studentGroupId) {
      $scope.studentGroup = QueryUtils.endpoint('/studentgroups').get({id: studentGroupId});
    }

    $scope.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriodsWithYear').query({}, function (response) {
        response.forEach(function (studyPeriod) {
            studyPeriod[$scope.currentLanguageNameField()] = $scope.currentLanguageNameField(studyPeriod.studyYear) + ' ' + $scope.currentLanguageNameField(studyPeriod);
        });
    });
    $scope.subjects = QueryUtils.endpoint('/subjectStudyPeriods/subjects/list').query();

    if(id) {
        Endpoint.get({id: id}).$promise.then(function(response){
            $scope.record = response;
            if(studentGroupId) {
                ArrayUtils.remove($scope.record.studentGroups, studentGroupId);
            }
            if(!$scope.record.groupProportion) {
                $scope.record.groupProportion = 'PAEVIK_GRUPI_JAOTUS_1';
            }
            $scope.disableSubject = true;
            $scope.subject = QueryUtils.endpoint('/subjectStudyPeriods/subject').get({id: response.subject}, function (subject) {
                $scope.subjects.$promise.then(function (subjects) {
                    for (var i = 0; i < subjects.length; i++) {
                        if (subjects[i].id === subject.id) {
                            return;
                        }
                    }
                    subjects.push(subject);
                });
            });
        });
    } else {
        var initialObject = {
            groupProportion: 'PAEVIK_GRUPI_JAOTUS_1',
            studyPeriod: studyPeriodId,
            subject: subjectId,
            teachers: [],
            studentGroups: []
        };
        $scope.record = new Endpoint(initialObject);
        $scope.disableSubject = subjectId !== null;
        if(teacherId) {
            QueryUtils.endpoint('/subjectStudyPeriods/teacher').get({id: teacherId}).$promise.then(function(response){
                $scope.record.teachers.push({teacherId: response.id, name: response.nameEt});
            });
        }
    }

    $scope.addTeacher = function(teacher) {
        if(teacher && !isTeacherAdded(teacher)) {
            $scope.record.teachers.push({
                teacherId: teacher.id,
                name: teacher.nameEt,
                isSignatory: false
            });
        } else if (teacher && isTeacherAdded(teacher)) {
            message.error('subjectStudyPeriodTeacher.teacherAlreadyAdded');
        }
        $scope.teacher = undefined;
    };

    $scope.querySearch = function (text) {
        var lookup = QueryUtils.endpoint('/subjectStudyPeriods/teachers/page');
        var deferred = $q.defer();
        lookup.search({
            name: text
        }, function (data) {
            deferred.resolve(data.content);
        });
        return deferred.promise;
    };

    function isTeacherAdded(teacher) {
        return $scope.record.teachers.filter(function(t){return t.teacherId === teacher.id;}).length > 0;
    }

    $scope.removeTeacher = function(teacher) {
        ArrayUtils.remove($scope.record.teachers, teacher);
    };

    function subjectAddedAutomaticallyToDecaration() {
      return DeclarationType.DEKLARATSIOON_KOIK === $scope.record.declarationType ||
      DeclarationType.DEKLARATSIOON_LISA === $scope.record.declarationType;
    }

    function formValid() {
      $scope.subjectStudyPeriodEditForm.$setSubmitted();
      if(!$scope.subjectStudyPeriodEditForm.$valid) {
        message.error('main.messages.form-has-errors');
        return false;
      }
      if(!$scope.record.teachers || $scope.record.teachers.length === 0) {
          message.error('subjectStudyPeriod.error.teacherNotAdded');
          return false;
      }
      return true;
    }

    function unique(arr) {
        var seen = {};
        return arr.filter(function(item) {
            return seen.hasOwnProperty(item) ? false : (seen[item] = true);
        });
    }

    function prepareDtoForSaving() {
      if(studentGroupId) {
        $scope.record.studentGroups.push(studentGroupId);
      }
      $scope.record.studentGroups = unique($scope.record.studentGroups);
      if($scope.record.teachers.length === 1) {
          $scope.record.teachers[0].isSignatory = true;
      }
    }

    function replaceLastUrlIf(lastUrl) {
      return lastUrl.indexOf("new") !== -1;
    }

    function replaceLastUrl() {
      if(studentGroupId && studyPeriodId) {
        $rootScope.replaceLastUrl("#/subjectStudyPeriods/studentGroups/" + studentGroupId + "/" + studyPeriodId + "/edit", replaceLastUrlIf);
      } else if(teacherId && studyPeriodId) {
          $rootScope.replaceLastUrl("#/subjectStudyPeriods/teachers/" + teacherId + "/" + studyPeriodId + "/edit", replaceLastUrlIf);
      } else if (subjectId && studyPeriodId && !studentGroupId && !teacherId) {
          $rootScope.replaceLastUrl("#/subjectStudyPeriods/subjects/" + subjectId + "/" + studyPeriodId + "/edit", replaceLastUrlIf);
      }
    }

    function afterCreate() {
      message.updateSuccess();
      replaceLastUrl();
      $scope.subjectStudyPeriodEditForm.$setPristine();
    }

    function save() {
      if(!formValid()) {
        return;
      }
      prepareDtoForSaving();

      if($scope.record.id) {
          $scope.record.$update().then(function(){
            message.updateSuccess();
            $scope.subjectStudyPeriodEditForm.$setPristine();
          });
      } else {
          $scope.record.$save().then(afterCreate);
      }
    }

    $scope.save = function() {
      if(subjectAddedAutomaticallyToDecaration()) {
        dialogService.confirmDialog({prompt: 'subjectStudyPeriod.prompt.addedAutomaticallyToDeclarations'}, save);
      } else {
        save();
      }
    };

    $scope.delete = function() {
       dialogService.confirmDialog({prompt: 'subjectStudyPeriodTeacher.deleteconfirm'}, function() {
         $scope.record.$delete().then(function() {
           message.info('main.messages.delete.success');
            $rootScope.back('#' + baseUrl);
         });
      });
    };
  }
]).controller('SubjectStudyPeriodViewController', ['$scope', 'QueryUtils', '$route',
  function ($scope, QueryUtils, $route) {

    var Endpoint = QueryUtils.endpoint('/subjectStudyPeriods');
    var id = $route.current.params.id;

    $scope.record = Endpoint.get({id: id});
    $scope.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriodsWithYear').query({}, function (response) {
        response.forEach(function (studyPeriod) {
            studyPeriod[$scope.currentLanguageNameField()] = $scope.currentLanguageNameField(studyPeriod.studyYear) + ' ' + $scope.currentLanguageNameField(studyPeriod);
        });
    });

    QueryUtils.endpoint('/subjectStudyPeriods/studentGroups/list').query(function(result) {
        $scope.studentGroups = result.map(function(el){
            var newEl = el;
            newEl.nameEt = el.code;
            newEl.nameEn = el.code;
            return newEl;
        });
    });

    $scope.subjects = QueryUtils.endpoint('/subjectStudyPeriods/subjects/list').query();
    $scope.record.$promise.then(function (response) {
        QueryUtils.endpoint('/subjectStudyPeriods/subject').get({id: response.subject}, function (subject) {
            $scope.subjects.$promise.then(function (subjects) {
                for (var i = 0; i < subjects.length; i++) {
                    if (subjects[i].id === subject.id) {
                        return;
                    }
                }
                subjects.push(subject);
            });
        });
    });
  }
]).constant('DeclarationType', {
  'DEKLARATSIOON_EI': 'DEKLARATSIOON_EI',
  'DEKLARATSIOON_KOIK' : 'DEKLARATSIOON_KOIK',
  'DEKLARATSIOON_LISA': 'DEKLARATSIOON_LISA'
});
