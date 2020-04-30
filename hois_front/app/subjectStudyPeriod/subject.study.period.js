'use strict';

function compareSubgroups(a, b) {
  var result = a.code.localeCompare(b.code);
            
  if (result !== 0) {
      return result;
  }
  
  if (a.teacher === b.teacher) {
      return 0;
  }
  if (!a.teacher) {
      return -1;
  } else if (!b.teacher) {
      return 1;
  }
  
  // AutocompleteResult for teacher has the same value for nameEt, nameEn, nameRu
  result = a.teacher.nameEt.localeCompare(b.teacher.nameEt);
  
  if (result !== 0) {
      return result;
  }

  if (a.id === b.id) {
      return 0;
  }
  if (!a.id) {
      return 1;
  } else if (!b.id) {
      return -1;
  }
  
  return b.id - a.id;
}

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
]).controller('SubjectStudyPeriodEditController', ['$location', '$route', '$rootScope', '$scope', '$q', 'ArrayUtils', 'DeclarationType', 'QueryUtils', 'dialogService', 'message',
  function ($location, $route, $rootScope, $scope, $q, ArrayUtils, DeclarationType, QueryUtils, dialogService, message) {
    $scope.auth = $route.current.locals.auth;

    var baseUrl = '/subjectStudyPeriods';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var id = $route.current.params.id;

    var studyPeriodId = $route.current.params.studyPeriodId ? parseInt($route.current.params.studyPeriodId, 10) : null;
    var studentGroupId = $route.current.params.studentGroupId ? parseInt($route.current.params.studentGroupId, 10) : null;
    var subjectId = $route.current.params.subjectId ? parseInt($route.current.params.subjectId, 10) : null;
    var teacherId = $route.current.params.teacherId ? parseInt($route.current.params.teacherId, 10) : null;

    $scope.SUBGROUP_LIMIT = 20;

    $scope.hasObligatoryStudentGroup = studentGroupId !== null;
    $scope.obligatoryTeacher = teacherId;

    $scope.addSubgroup = addSubgroup;
    $scope.removeSubgroup = removeSubgroup;

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
      $scope.record = $route.current.locals.record;
      if (!$scope.record.canUpdate) {
        $location.url('/subjectStudyPeriod/' + $scope.record.id + '/view?_noback');
      }

      if(studentGroupId) {
          ArrayUtils.remove($scope.record.studentGroups, studentGroupId);
      }
      if(!$scope.record.groupProportion) {
          $scope.record.groupProportion = 'PAEVIK_GRUPI_JAOTUS_1';
      }
      $scope.disableSubject = true;
      $scope.subject = QueryUtils.endpoint('/subjectStudyPeriods/subject').get({id: $scope.record.subject}, function (subject) {
          $scope.subjects.$promise.then(function (subjects) {
              for (var i = 0; i < subjects.length; i++) {
                  if (subjects[i].id === subject.id) {
                      return;
                  }
              }
              subjects.push(subject);
          });
      });
      $scope.record.subgroups.sort(compareSubgroups);
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
      $scope.record.subgroups.sort(compareSubgroups);
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
            $scope.record.subgroups.sort(compareSubgroups);
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

    function addSubgroup(defGroupCount, defGroupPlaces) {
      if (!angular.isArray($scope.record.subgroups)) {
        $scope.record.subgroups = [];
      }

      var groups = collectGroups($scope.record.studentGroups);
      if (studentGroupId) {
        groups.push($scope.studentGroup);
      }
      groups.sort(compareSubgroups);

      if (groups.length > 0) {
        var subgroupPerGroup = Math.floor(defGroupCount / groups.length);
        var extraSubgroups = defGroupCount % groups.length;
        for (var i = 0; i < groups.length; i++) {
          for (var j = 0; j < subgroupPerGroup; j++) {
            $scope.record.subgroups.push(createGroup(groups[i].code + "-" + (j + 1), defGroupPlaces));
          }
          if (extraSubgroups > 0) {
            $scope.record.subgroups.push(createGroup(groups[i].code + "-" + (j + 1), defGroupPlaces));
            extraSubgroups--;
          }
        }
        return;
      }

      for (var k = 0; k < defGroupCount; k++) {
        $scope.record.subgroups.push(createGroup(null, defGroupPlaces));
      }
    }

    function removeSubgroup(subgroup) {
      ArrayUtils.remove($scope.record.subgroups, subgroup);
    }

    function createGroup(code, places) {
      return {
        code: code ? code : null,
        places: places,
        teacher: null
      };
    }

    function collectGroups(idArr) {
      if (!$scope.studentGroups) {
        return [];
      }
      return idArr.map(function (id) {
        return $scope.studentGroups.find(function (el) {
          return el.id === id;
        });
      }).filter(function (el) {
        return !!el;
      });
    }
  }
]).controller('SubjectStudyPeriodViewController', ['$scope', 'QueryUtils', '$route',
  function ($scope, QueryUtils, $route) {

    $scope.record = $route.current.locals.record;
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
        $scope.record.subgroups.sort(compareSubgroups);
    });
  }
]).constant('DeclarationType', {
  'DEKLARATSIOON_EI': 'DEKLARATSIOON_EI',
  'DEKLARATSIOON_KOIK' : 'DEKLARATSIOON_KOIK',
  'DEKLARATSIOON_LISA': 'DEKLARATSIOON_LISA'
});
