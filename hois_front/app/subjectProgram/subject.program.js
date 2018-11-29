'use strict';

angular.module('hitsaOis').controller('SubjectProgramController', ['$scope', 'QueryUtils', '$route', 'ArrayUtils', 'message', 'dialogService', '$location', 'config', '$rootScope', '$timeout',
function ($scope, QueryUtils, $route, ArrayUtils, message, dialogService, $location, config, $rootScope, $timeout) {
  
  var formTypes = Object.freeze({
    periods: "periods",
    programs: "programs"
  });

  var subjectId = $route.current.params.subjectId;
  var subjectStudyPeriodId = $route.current.params.subjectStudyPeriodId;
  var formType = formTypes[$route.current.params.form]; // Used to understand from where did user come.
  if (angular.isUndefined(formType)) {
    message.error('main.messages.error.nopermission');
    $location.path('/');
  }
  var subjectProgramId = $route.current.params.subjectProgramId;
  var baseUrl = "/subject/subjectProgram";
  var Endpoint = QueryUtils.endpoint(baseUrl);
  var initial = {
    studyContentType: 'OPPETOOSISU_T',
    status: 'AINEPROGRAMM_STAATUS_L'
  };

  $rootScope.removeLastUrlFromHistory(function(url){
    return url && url.indexOf("new") !== -1;
  });

  $scope.selectedSubjectProgram = undefined;
  $scope.subjectPrograms = QueryUtils.endpoint(baseUrl + "/teacher/" + subjectId).query();

  $scope.studyContentWk = [];
  $scope.studyContentDt = [];
  $scope.userTeacherId = $route.current.locals.auth.teacher;
  $scope.isSupervisor = false;

  $scope.subject = QueryUtils.endpoint(baseUrl + "/subject").get({id: subjectId});

  function get() {
    if (subjectProgramId) {
      $timeout(function () {
        if ($scope.subjectProgram) {
          $scope.subjectProgram.$get({id: subjectProgramId}, dtoToModel);
        } else {
          $scope.subjectProgram = Endpoint.get({id: subjectProgramId}, dtoToModel);
        }
      });
    } else {
      $scope.subjectProgram = new Endpoint(initial);
    }
  }

  function save() {
    $scope.subjectProgram.$update().then(function (response) {
      message.info("main.messages.update.success");
      dtoToModel(response);
      $scope.subjectProgramForm.$setPristine();
    });
  }

  function create() {
    $scope.subjectProgram.$save().then(function (response) {
      message.info("main.messages.create.success");
      $location.url("/subjectProgram/" + subjectId + "/" + subjectStudyPeriodId + "/" + formType + "/" + response.id + "/edit");
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
      $scope.studyContentWk.sort(function (w1, w2) {
        return w1.weekNr - w2.weekNr;
      });
    }
    $scope.isSupervisor = ArrayUtils.includes(response.supervisorIds, $scope.userTeacherId);
  }

  function modelToDto() {
    $scope.subjectProgram.subjectId = subjectId;
    $scope.subjectProgram.subjectStudyPeriodId = subjectStudyPeriodId;
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
    $scope.studyContentDt.push({
      id: null,
      studyDt: null,
      studyInfo: null
    });
  };

  $scope.addStudyContentWk = function () {
    $scope.studyContentWk.push({
      id: null,
      weekNr: null,
      studyInfo: null
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
        response.studyContents.forEach(function (sc) {
          sc.id = null;
        });
        dtoToModel(response);
      });
    }
  };

  $scope.pdfUrl = function () {
    return config.apiUrl + baseUrl + "/print/" + $scope.subjectProgram.id + "/program.pdf";
  };

  $scope.edit = function () {
    if ($scope.subjectProgram.status !== 'AINEPROGRAMM_STAATUS_I') {
      dialogService.confirmDialog({prompt: 'subjectProgram.messages.editAccept'}, function() {
        $location.url("/subjectProgram/" + subjectId + "/" + subjectStudyPeriodId + "/" + formType + "/" + subjectProgramId + "/edit");
      });
    } else {
      $location.url("/subjectProgram/" + subjectId + "/" + subjectStudyPeriodId + "/" + formType + "/" + subjectProgramId + "/edit");
    }
  };

  $scope.completeProgram = function () {
    dialogService.confirmDialog({prompt: 'subjectProgram.operation.complete.message'}, function() {
      QueryUtils.endpoint(baseUrl + "/complete").get({id: $scope.subjectProgram.id}).$promise.then(function () {
        message.info('subjectProgram.operation.complete.success');
        $location.url("/subjectProgram/" + subjectId + "/" + subjectStudyPeriodId + "/" + formType + "/" + $scope.subjectProgram.id + "/view");
      });
    });
  };

  $scope.confirm = function () {
    dialogService.confirmDialog({prompt: 'subjectProgram.operation.confirm.message'}, function() {
      QueryUtils.endpoint(baseUrl + "/confirm").get({id: $scope.subjectProgram.id}).$promise.then(function () {
        message.info('subjectProgram.operation.confirm.success');
        get();
      });
    });
  };

  $scope.reject = function () {
    dialogService.confirmDialog({prompt: 'subjectProgram.operation.reject.message'}, function() {
      QueryUtils.endpoint(baseUrl + "/reject/" + $scope.subjectProgram.id).save("test").$promise.then(function () {
        message.info('subjectProgram.operation.reject.success');
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
        // saved in dialogScope.saveTheme()
      });
  };

  $scope.save = function () {
    if (!validationPassed()) {
      return;
    }
    modelToDto();
    if ($scope.subjectProgram.id) {
      save();
    } else {
      create();
    }
  };

  $scope.getDefaultBack = function () {
    if (formType === formTypes.programs) {
      return "#/subjectProgram";
    }
    return "#/subjectStudyPeriods";
  }

  get();
}]).controller('SubjectProgramSearch', ['$scope', 'QueryUtils', '$route', 'DataUtils', 'Classifier',
function ($scope, QueryUtils, $route, DataUtils, Classifier) {
  var baseUrl = '/subject/subjectProgram';
  QueryUtils.createQueryForm($scope, baseUrl);

  $scope.auth = $route.current.locals.auth;
  $scope.criteria.status = 'AINEPROGRAMM_STAATUS_V';
  
  var autocompleteTeacher = QueryUtils.endpoint("/autocomplete/teachersList");
  var autocompleteStudyPeriod = QueryUtils.endpoint('/autocomplete/studyPeriodsWithYear');
  var subjectsQuery = QueryUtils.endpoint(baseUrl + "/subjects");

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
    $scope.subjects.$promise.then(function () {
      $scope.loadData();
    });
    $scope.studyPeriods.forEach(function (studyPeriod) {
      studyPeriod[$scope.currentLanguageNameField()] = $scope.currentLanguageNameField(studyPeriod.studyYear) + ' ' + 
        $scope.currentLanguageNameField(studyPeriod);
    });
  }

  $scope.studyPeriods = autocompleteStudyPeriod.query(afterStudyPeriodsWithYearLoad);
  $scope.subjects = subjectsQuery.query();

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
    $scope.criteria.status = 'AINEPROGRAMM_STAATUS_V';
  };

  $scope.load = function() {
    if (!$scope.searchForm.$valid) {
      message.error('main.messages.form-has-errors');
      return false;
    } else {
      $scope.loadData();
    }
  };
}]);