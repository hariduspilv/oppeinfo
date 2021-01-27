'use strict';

angular.module('hitsaOis').controller('SubjectProgramController',
  ['$scope', 'QueryUtils', '$route', 'ArrayUtils', 'message', 'dialogService',
   '$location', 'config', '$rootScope', '$timeout', '$mdColors', 'Classifier', '$filter', '$translate',
function ($scope, QueryUtils, $route, ArrayUtils, message, dialogService, $location, config, $rootScope, $timeout,
          $mdColors, Classifier, $filter, $translate) {

  var formTypes = Object.freeze({
    periods: "periods",
    programs: "programs",
    teachers: "teachers",
    public: "public"
  });

  $scope.swapCriteria = function(index1, index2) {
    var temp = $scope.studyContentWk[index1];
    $scope.studyContentWk[index1] = $scope.studyContentWk[index2];
    $scope.studyContentWk[index2] = temp;
    temp = $scope.studyContentWk[index1].orderNr;
    $scope.studyContentWk[index1].orderNr = $scope.studyContentWk[index2].orderNr;
    $scope.studyContentWk[index2].orderNr = temp;
    $scope.subjectProgramForm.$setDirty();
  };

  $scope.isString = function(possibleString) {
    return angular.isString(possibleString);
  };

  $scope.auth = $route.current.locals.auth;
  var subjectId = $route.current.params.subjectId;
  var subjectStudyPeriodId = $route.current.params.subjectStudyPeriodId;
  var teacherId = $route.current.params.teacher;
  var joint = $route.current.params.joint;
  var isView = $location.path().indexOf("/view") !== -1;

  var formType = formTypes[$route.current.params.form || ($route.current.locals.params ? $route.current.locals.params.form : undefined)]; // Used to understand from where did user come.
  if (angular.isUndefined(formType)) {
    message.error('main.messages.error.nopermission');
    $location.path('/');
  }
  $scope.isPublic = formType === formTypes.public;
  var subjectProgramId = $route.current.params.subjectProgramId;
  var baseUrl = "/subject/subjectProgram";
  var Endpoint = QueryUtils.endpoint("/" + ($scope.isPublic && !$scope.auth ? "public" : "subject") + "/subjectProgram");
  var initial = {
    teacherId: teacherId,
    studyContentType: 'OPPETOOSISU_T',
    status: 'AINEPROGRAMM_STAATUS_L',
    isLetterGrade: !!$scope.auth && !!$scope.auth.school ? $scope.auth.school.letterGrades : undefined,
    isJoint: joint
  };

  var mappedGrades = {};
  var grades = Classifier.queryForDropdown({ mainClassCode: 'KORGHINDAMINE' });
  grades.$promise.then(function () {
    grades.forEach(function (grade) {
      mappedGrades[grade.code] = grade;
    });
  });

  $scope.getGradeName = function (code) {
    if (!mappedGrades[code]) {
      return "";
    }
    return $filter('hoisHigherGrade')(mappedGrades[code], ($scope.subjectProgram || {}).isLetterGrade);
  };

  $rootScope.removeLastUrlFromHistory(function(url){
    return url && url.indexOf("new") !== -1;
  });

  $scope.customBack = function (def, form) {
    unlock();
    $scope.back(def, form);
  };

  $scope.selectedSubjectProgram = undefined;

  $scope.studyContentWk = [];
  $scope.studyContentDt = [];
  $scope.userTeacherId = $scope.auth ? $scope.auth.teacher : undefined;
  $scope.isSupervisor = false;
  $scope.modifyStudyContentTeacher = modifyStudyContentTeacher;
  $scope.isPartOfProgramTeachers = isPartOfProgramTeachers;
  $scope.teacherField = teacherField;
  $scope.changedTeacher = changedTeacher;

  function getAdditionalData(sId, pId) {
    $scope.subject = QueryUtils.endpoint(($scope.isPublic && !$scope.auth ? "/public/subject/view" : baseUrl + "/subject")).get({id: sId, program: pId});
    if ($scope.auth) {
      if (!$scope.subjectProgram.id && ($scope.auth.isTeacher() || $scope.auth.isAdmin())) {
        $scope.subjectPrograms = QueryUtils.endpoint(baseUrl + "/teacher/" + sId).query();
      }
      if (!!subjectStudyPeriodId) {
        QueryUtils.endpoint(baseUrl + "/additionalData").get({
          id: subjectStudyPeriodId,
          joint: $scope.subjectProgram.isJoint,
          teacher: teacherId
        }, function (response) {
          if (!$scope.subjectProgram.id) {
            $scope.subjectProgram.teachers = response.teachers;
            $scope.subjectProgram.period = response.period;
            $scope.teachers = ($scope.subjectProgram.teachers || []).reduce(function (acc, cur) {
              return (!acc ? $scope.currentLanguageNameField(cur.teacher) : acc + ', ' + $scope.currentLanguageNameField(cur.teacher));
            }, null);
            $scope.studyPeriod = $scope.subjectProgram.period;
          }
        });
      }
    }
    $scope.teachers = ($scope.subjectProgram.teachers || []).reduce(function (acc, cur) {
      return (!acc ? $scope.currentLanguageNameField(cur.teacher) : acc + ', ' + $scope.currentLanguageNameField(cur.teacher));
    }, null);
    $scope.studyPeriod = $scope.subjectProgram.period;
  }

  function get() {
    if (subjectProgramId) {
      $timeout(function () {
        if ($scope.subjectProgram) {
          $scope.subjectProgram.$get({id: subjectProgramId}, dtoToModel);
        } else {
          $scope.subjectProgram = Endpoint.get({id: subjectProgramId}, dtoToModel);
        }
        $scope.subjectProgram.$promise.then(function(response) {
          subjectId = response.subjectId;
          getAdditionalData(subjectId, $scope.subjectProgram.id);
        }).catch(function() {
          $scope.back($scope.getDefaultBack());
        });
        $scope.subjectProgram.$promise.then(checkLock);
      });
    } else {
      $scope.subjectProgram = new Endpoint(initial);
    }
    if (subjectId) {
      getAdditionalData(subjectId);
    }
  }

  function save() {
    $scope.subjectProgram.$update().then(function (response) {
      message.info("main.messages.update.success");
      dtoToModel(response);
      $scope.subjectProgramForm.$setPristine();
      checkLock();
    });
  }

  function create() {
    $scope.subjectProgram.$save().then(function (response) {
      message.info("main.messages.create.success");
      $location.url("/subjectProgram/" + formType + "/" + response.id + "/edit");
      checkLock();
    });
  }

  function validationPassed() {
    $scope.subjectProgramForm.$setSubmitted();
    if (!$scope.subjectProgramForm.$valid) {
      message.error('main.messages.form-has-errors');
      return false;
    }
    return true;
  }

  function checkLock(fConfirmed, force) {
    if (!$scope.subjectProgram.id || !force && isView) {
      return;
    }
    QueryUtils.endpoint(baseUrl + "/checkLock").get({id: $scope.subjectProgram.id}, function (response) {
      if (!!response.locked) {
        var msg = $translate.instant('subjectProgram.messages.lockedBy',
          {teacher: response.lockedBy, time: $filter('hoisTime')(response.locked)});
        dialogService.confirmDialog({prompt: msg}, function () {
          var promise = lock(force);
          if (angular.isFunction(fConfirmed) && !!promise && !!promise.$promise && angular.isFunction(promise.$promise.then)) {
            promise.$promise.then(fConfirmed);
          }
        }, function () {
          $location.url("/subjectProgram/" + formType + "/" + $scope.subjectProgram.id + "/view" + (isView ? "" : "?_noback"));
        });
      } else {
        var promise = lock(force);
        if (angular.isFunction(fConfirmed) && !!promise && !!promise.$promise && angular.isFunction(promise.$promise.then)) {
          promise.$promise.then(fConfirmed);
        }
      }
    });
  }

  function lock(force) {
    if (!$scope.subjectProgram.id || !force && isView) {
      return;
    }
    QueryUtils.loadingWheel($rootScope, true);
    return QueryUtils.endpoint(baseUrl + "/lock").get({id: $scope.subjectProgram.id}, function (response) {
      if (angular.isDefined(response.version)) {
        $scope.subjectProgram.version = response.version;
      }
      QueryUtils.loadingWheel($rootScope, false);
    }, function () {
      QueryUtils.loadingWheel($rootScope, false);
    });
  }

  function unlock() {
    if (!$scope.subjectProgram.id) {
      return;
    }
    QueryUtils.endpoint(baseUrl + "/unlock").get({id: $scope.subjectProgram.id}, function (response) {
      if (angular.isDefined(response.version)) {
        $scope.subjectProgram.version = response.version;
      }
    });
  }

  /*
  var weekRegex = /^\d+(\-\d+)?$/;
  function compareWeek(week1, week2) {
    if (week1 === week2) {
      return 0;
    }
    if (angular.isUndefined(week1) || angular.isUndefined(week2)) {
      return angular.isUndefined(week1) ? 1 : -1;
    }

    var isValid1 = weekRegex.test(week1);
    var isValid2 = weekRegex.test(week2);

    if (isValid1 && isValid2) {
      var splittedWeek1 = week1.split("-");
      var splittedWeek2 = week2.split("-");
      return parseInt(splittedWeek1? splittedWeek1[0] : week1) - parseInt(splittedWeek2 ? splittedWeek2[0] : week2);
    } else if (isValid1 || isValid2) {
      return isValid1 ? -1 : 1;
    }
    return week1.localeCompare(week2);
  }
  */

  function dtoToModel(response) {
    $scope.studyContentWk = [];
    $scope.studyContentDt = [];
    if (response.studyContentType === 'OPPETOOSISU_K') {
      $scope.studyContentDt = response.studyContents;
      $scope.studyContentDt.forEach(function (sc) {
        sc.studyDt = new Date(sc.studyDt);
      });
      $scope.studyContentDt.sort(function (d1, d2) {
        return d1.studyDt - d2.studyDt;
      });
    } else if (response.studyContentType === 'OPPETOOSISU_N') {
      $scope.studyContentWk = response.studyContents;
      // old contents should start using orderNr for week
      $scope.studyContentWk.forEach(function(contentWk) {
        if (contentWk.orderNr === null || contentWk.orderNr === undefined) {
          contentWk.orderNr = $scope.studyContentWk.indexOf(contentWk) + 1;
        }
      });
    }
    $scope.isSupervisor = ArrayUtils.includes(response.supervisorIds || [], $scope.userTeacherId);
    $scope.studyContentTypeChange();
  }

  /**
   *
   * @param studyContent SubjectProgramStudyContent
   * @param teacher SubjectProgramTeacher
   */
  function modifyStudyContentTeacher(studyContent, teacher) {
    if (!studyContent || !teacher) {
      return;
    }
    if (!angular.isArray(studyContent.teachers)) {
      studyContent.teachers = [];
    }
    var idx = studyContent.teachers.indexOf(teacher.teacher.id);
    if (idx === -1) {
      studyContent.teachers.push(teacher.teacher.id);
    } else {
      studyContent.teachers.splice(idx, 1);
    }
  }

  function isPartOfProgramTeachers(teacherId) {
    if (!$scope.subjectProgram.id) {
      return false;
    }
    return ($scope.subjectProgram.teachers || []).find(function (it) {
      return it.teacher.id === teacherId;
    }) !== undefined;
  }

  function teacherField(content) {
    var teacherLine = $scope.isString(content.teacher) ? content.teacher : $scope.currentLanguageNameField(content.teacher);
    if (angular.isArray(content.teachers)) {
      var contentTeachers = $scope.currentLanguageNameField(($scope.subjectProgram.teachers || [])
        .filter(function (it) {
          return content.teachers.indexOf(it.teacher.id) !== -1;
        }).map(function (it) {
          return it.teacher;
        }), ', ');
      if (contentTeachers.length > 0) {
        if (!teacherLine) {
          teacherLine = "";
        }
        if (teacherLine.length > 0) {
          teacherLine += ", ";
        }
        teacherLine += contentTeachers;
      }
      return teacherLine;
    }
  }

  function changedTeacher(content) {
    if (!$scope.subjectProgram.isJoint || !content.teacher || ($scope.subjectProgram.teachers || []).length === 0) {
      return;
    }
    var hasTeacherInProgram = $scope.subjectProgram.teachers.find(function (it) {
      return it.teacherId === content.teacher.id;
    });
    if (!!hasTeacherInProgram) {
      if (content.teachers.indexOf(hasTeacherInProgram.teacher.id) === -1) {
        content.teachers.push(hasTeacherInProgram.teacher.id);
      }
      content.teacher = null;
    }
  }

  var warningColor = $mdColors.getThemeColor('warn');
  $scope.showWarningAboutType = false;
  $scope.studyContentTypeChange = function() {
    var showWarning = false;
    if ($scope.subjectProgram.studyContentType !== 'OPPETOOSISU_K' && ($scope.studyContentDt || []).length > 0) {
      document.querySelector("md-radio-button[value='OPPETOOSISU_K']").style.color = warningColor;
      showWarning = true;
    } else {
      document.querySelector("md-radio-button[value='OPPETOOSISU_K']").style.color = "";
    }
    if ($scope.subjectProgram.studyContentType !== 'OPPETOOSISU_N' && ($scope.studyContentWk || []).length > 0) {
      document.querySelector("md-radio-button[value='OPPETOOSISU_N']").style.color = warningColor;
      showWarning = true;
    } else {
      document.querySelector("md-radio-button[value='OPPETOOSISU_N']").style.color = "";
    }
    if ($scope.subjectProgram.studyContentType !== 'OPPETOOSISU_T' && $scope.subjectProgram.studyDescription) {
      document.querySelector("md-radio-button[value='OPPETOOSISU_T']").style.color = warningColor;
      showWarning = true;
    } else {
      document.querySelector("md-radio-button[value='OPPETOOSISU_T']").style.color = "";
    }
    $scope.showWarningAboutType = showWarning;
  };

  function modelToDto() {
    if (!$scope.subjectProgram.id) {
      $scope.subjectProgram.subjectId = subjectId;
      $scope.subjectProgram.subjectStudyPeriodId = subjectStudyPeriodId;
    }
    if ($scope.subjectProgram.studyContentType === 'OPPETOOSISU_K') {
      $scope.subjectProgram.studyDescription = null;
      $scope.studyContentWk = [];
      $scope.subjectProgram.studyContents = $scope.studyContentDt;
    } else if ($scope.subjectProgram.studyContentType === 'OPPETOOSISU_N') {
      $scope.subjectProgram.studyDescription = null;
      $scope.studyContentDt = [];
      $scope.subjectProgram.studyContents = $scope.studyContentWk;
    } else {
      $scope.studyContentWk = [];
      $scope.studyContentDt = [];
      $scope.subjectProgram.studyContents = null;
    }
  }

  $scope.addStudyContentDt = function () {
    var listLength = $scope.studyContentDt.length;
    $scope.studyContentDt.push({
      id: null,
      studyDt: null,
      studyInfo: null,
      orderNr: listLength + 1,
      teachers: []
    });
  };

  $scope.addStudyContentWk = function () {
    var listLength = $scope.studyContentWk.length;
    $scope.studyContentWk.push({
      id: null,
      weekNr: null,
      studyInfo: null,
      orderNr: listLength + 1,
      teachers: []
    });
  };

  $scope.removeFromArray = ArrayUtils.remove;

  $scope.delete = function () {
    dialogService.confirmDialog({prompt: 'subjectProgram.operation.delete.message'}, function() {
      $scope.subjectProgram.$delete().then(function () {
        message.info('main.messages.delete.success');
        if (formType === formTypes.periods) {
          $location.url("/subjectStudyPeriods");
        } else if (formType === formTypes.programs) {
          $location.url("/subjectProgram");
        }
      });
    });
  };

  $scope.getSubjectProgramData = function (program) {
    if (angular.isDefined(program)) {
      QueryUtils.endpoint(baseUrl).get({id: program.id}).$promise.then(function (response) {
        $scope.subjectProgram.independentStudy = response.independentStudy;
        $scope.subjectProgram.assessmentDescription = response.assessmentDescription;
        $scope.subjectProgram.studyLiterature = response.studyLiterature;
        $scope.subjectProgram.studyDescription = response.studyDescription;
        $scope.subjectProgram.studyContentType = response.studyContentType;
        $scope.subjectProgram.publicAll = response.publicAll;
        $scope.subjectProgram.publicHois = response.publicHois;
        $scope.subjectProgram.publicStudent = response.publicStudent;
        $scope.subjectProgram.passDescription = response.passDescription;
        $scope.subjectProgram.npassDescription = response.npassDescription;
        $scope.subjectProgram.grade0Description = response.grade0Description;
        $scope.subjectProgram.grade1Description = response.grade1Description;
        $scope.subjectProgram.grade2Description = response.grade2Description;
        $scope.subjectProgram.grade3Description = response.grade3Description;
        $scope.subjectProgram.grade4Description = response.grade4Description;
        $scope.subjectProgram.grade5Description = response.grade5Description;
        $scope.subjectProgram.addInfo = response.addInfo;
        response.studyContents.forEach(function (sc) {
          sc.id = null;
          sc.teachers = [];
          changedTeacher(sc);
        });
        dtoToModel(response);
      });
    }
  };

  $scope.pdfUrl = function () {
    if ($scope.isPublic && !$scope.auth) {
      return config.apiUrl + '/public/print/subjectProgram/' + $scope.subjectProgram.id + "/program.pdf";
    }
    return config.apiUrl + baseUrl + "/print/" + $scope.subjectProgram.id + "/program.pdf";
  };

  $scope.edit = function () {
    checkLock(function () {
      if ($scope.subjectProgram.status !== 'AINEPROGRAMM_STAATUS_I') {
        dialogService.confirmDialog({prompt: 'subjectProgram.messages.editAccept'}, function() {
          $location.url("/subjectProgram/" + formType + "/" + subjectProgramId + "/edit");
        });
      } else {
        checkLock(function () {
          $location.url("/subjectProgram/" + formType + "/" + subjectProgramId + "/edit");
        }, true);
      }
    }, true);
  };

  function completeProgram() {
    dialogService.confirmDialog({prompt: 'subjectProgram.operation.complete.' +
        ($scope.subjectProgram.isJoint ? 'messageJoint' : 'message')}, function() {
      QueryUtils.endpoint(baseUrl + "/complete").get({id: $scope.subjectProgram.id}).$promise.then(function () {
        message.info('subjectProgram.operation.complete.success');
        $location.url("/subjectProgram/" + formType + "/" + $scope.subjectProgram.id + "/view?_noback");
      });
    });
  }

  $scope.completeProgram = function () {
    if ($scope.subjectProgramForm.$dirty) {
      dialogService.confirmDialog({prompt: 'main.messages.confirmFormDataNotSaved'}, function() {
        completeProgram();
      });
    } else {
      completeProgram();
    }
  };

  $scope.confirm = function () {
    dialogService.confirmDialog({prompt: 'subjectProgram.operation.confirm.message'}, function() {
      QueryUtils.endpoint(baseUrl + "/confirm").get({id: $scope.subjectProgram.id}).$promise.then(function () {
        message.info('subjectProgram.operation.confirm.success');
        get();
      });
    });
  };

  $scope.openRejectDialog = function () {

    var form = 'subjectProgram/dialog/subject.program.reject.dialog.html';

    dialogService.showDialog(form,
      function (dialogScope) {

        function isFormValid() {
          dialogScope.dialogForm.$setSubmitted();
          if (!dialogScope.dialogForm.$valid) {
            message.error('main.messages.form-has-errors');
            return false;
          }
          return true;
        }

        dialogScope.rejectProgram = function () {
          if (isFormValid()) {
            QueryUtils.endpoint(baseUrl + "/reject/" + $scope.subjectProgram.id).save(dialogScope.rejectInfo).$promise.then(function () {
              message.info('subjectProgram.operation.reject.success');
              get();
            });
          }
        };
      }, function () {

      });
  };

  $scope.save = function () {
    if (!validationPassed()) {
      return;
    }
    if ($scope.showWarningAboutType) {
      dialogService.confirmDialog({prompt: 'subjectProgram.messages.previousStudyContentBeingDeleted'}, function() {
        modelToDto();
        if ($scope.subjectProgram.id) {
          save();
        } else {
          create();
        }
      });
    } else {
      modelToDto();
      if ($scope.subjectProgram.id) {
        save();
      } else {
        create();
      }
    }
  };

  $scope.getDefaultBack = function () {
    if (formType === formTypes.programs) {
      return "#/subjectProgram";
    } else if (formType === formTypes.periods && ($scope.auth.isAdmin() || $scope.auth.isTeacher())) {
      return "#/subjectStudyPeriods";
    } else if (formType === formTypes.teachers && angular.isDefined($scope.subjectProgram)) {
      if ($scope.auth.isTeacher()) {
        return "#/teachers/myData";
      } else if (($scope.subjectProgram.teachers || []).length === 1) {
        return "#/teachers/" + ($scope.auth.isTeacher() ? "myData" : $scope.subjectProgram.teachers[0].teacher.id);
      } else {
        return "#/subjectStudyPeriods";
      }
    }
    return "#/";
  };

  get();
}]).controller('SubjectProgramSearch', ['$scope', 'QueryUtils', '$route', 'DataUtils', 'Classifier', 'message', '$q',
function ($scope, QueryUtils, $route, DataUtils, Classifier, message, $q) {
  $scope.myData = $route.current.locals.params.myData;

  var baseUrl = '/subject/subjectProgram';
  QueryUtils.createQueryForm($scope, baseUrl + ($scope.myData ? '/myPrograms' : ''));

  $scope.auth = $route.current.locals.auth;
  if (angular.isUndefined($scope.criteria.status) && !$scope.myData) {
    $scope.criteria.status = 'AINEPROGRAMM_STAATUS_V';
  }

  var autocompleteTeacher = QueryUtils.endpoint("/autocomplete/teachersList");
  var autocompleteStudyPeriod = QueryUtils.endpoint('/autocomplete/studyPeriodsWithYear');
  var deferred = $q.defer();

  if ($scope.auth.isAdmin() && $scope.myData && $scope.teacher) {
    $scope.teacher.$promise.then(function (response) {
      $scope.criteria.teacher = { id: response.id };
      QueryUtils.endpoint(baseUrl + ($scope.myData ? "/program" : "/curriculum") + "/subjects" + "?teacher=" + response.id).query({}, function (response) {
        $scope.subjects = response;
        deferred.resolve(response);
      });
    });
  } else {
    $scope.subjectAutocompleteUrl = baseUrl + ($scope.myData ? "/program" : "/curriculum") + "/subjects";
  }

  function preselectCurrentStudyYearOrPeriod() {
    if (!$scope.criteria.studyPeriod) {
      var current = DataUtils.getCurrentStudyYearOrPeriod($scope.studyPeriods);
      if (current) {
        $scope.criteria.studyPeriod = current.id;
      }
    }
  }

  function afterStudyPeriodsWithYearLoad() {
    preselectCurrentStudyYearOrPeriod();
    deferred.promise.then(function () {
      if (!$scope.myData) {
        $scope.loadData();
      }
    });
    $scope.studyPeriods.forEach(function (studyPeriod) {
      studyPeriod[$scope.currentLanguageNameField()] = $scope.currentLanguageNameField(studyPeriod.studyYear) + ' ' +
        $scope.currentLanguageNameField(studyPeriod);
    });
  }

  $scope.studyPeriods = autocompleteStudyPeriod.query(afterStudyPeriodsWithYearLoad);

  Classifier.queryForDropdown({mainClassCode: 'AINEPROGRAMM_STAATUS'}, function(response) {
      $scope.statusOptions = Classifier.toMap(response);
  });

  $scope.searchTeacher = function (name) {
      return autocompleteTeacher.query({name: name, valid: true}).$promise;
  };

  $scope.getStudyPeriodName = function (studyPeriod) {
    return $scope.studyPeriods.filter(function (period) {
      return period.id === studyPeriod.id;
    })[0][$scope.currentLanguageNameField()];
  };

  $scope.clearCriteriaBefore = function () {
    $scope.clearCriteria();
    preselectCurrentStudyYearOrPeriod();
    if ($scope.auth.isAdmin() && $scope.myData && $scope.teacher) {
      $scope.teacher.$promise.then(function (response) {
        $scope.criteria.teacher = { id: response.id };
      });
    }
  };

  $scope.load = function() {
    if (!$scope.searchController.searchForm.$valid) {
      message.error('main.messages.form-has-errors');
      return false;
    } else {
      $scope.loadData();
    }
  };
}]);
