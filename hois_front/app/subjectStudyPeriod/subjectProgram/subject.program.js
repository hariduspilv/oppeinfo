'use strict';

angular.module('hitsaOis').controller('SubjectProgramController', ['$scope', 'QueryUtils', '$route', 'ArrayUtils', 'message', 'dialogService', '$location', 'config', '$rootScope',
  function ($scope, QueryUtils, $route, ArrayUtils, message, dialogService, $location, config, $rootScope) {
    
    var subjectId = $route.current.params.subjectId;
    var subjectStudyPeriodId = $route.current.params.subjectStudyPeriodId;
    var subjectProgramId = $route.current.params.subjectProgramId;
    var baseUrl = "/subject/subjectProgram";
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var initial = {
      studyContentType: 'OPPETOOSISU_T',
      status: 'AINEPROGRAMM_STAATUS_L'
    }

    $rootScope.removeLastUrlFromHistory(function(url){
      return url && url.indexOf("new") !== -1;
    });

    $scope.selectedSubjectProgram = undefined;
    $scope.subjectPrograms = QueryUtils.endpoint(baseUrl + "/teacher/" + subjectId).query();

    $scope.studyContentWk = [];
    $scope.studyContentDt = [];
    
    $scope.subject = QueryUtils.endpoint("/subject").get({id: subjectId});

    function get() {
      if (subjectProgramId) {
        $scope.subjectProgram = Endpoint.get({id: subjectProgramId}, dtoToModel);
      } else {
        $scope.subjectProgram = new Endpoint(initial);
      }
    }

    function save() {
      $scope.subjectProgram.$update().then(function (response) {
        message.info("main.messages.update.success");
        dtoToModel(response);
        $scope.subjectProgramForm.$setPristine();
      })
    }

    function create() {
      $scope.subjectProgram.$save().then(function (response) {
        message.info("main.messages.create.success");
        $location.url("/subjectStudyPeriods/" + subjectId + "/" + subjectStudyPeriodId + "/subjectProgram/" + response.id + "/edit");
      })
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
      })
    };

    $scope.addStudyContentWk = function () {
      $scope.studyContentWk.push({
        id: null,
        weekNr: null,
        studyInfo: null
      })
    };

    $scope.removeFromArray = ArrayUtils.remove;

    $scope.delete = function () {
      dialogService.confirmDialog({prompt: 'subjectProgram.operation.delete.message'}, function() {
        $scope.subjectProgram.$delete().then(function () {
          message.info('main.messages.delete.success');
          $location.url("/subjectStudyPeriods");
        });
      });
    }

    $scope.getSubjectProgramData = function (program) {
      if (angular.isDefined(program)) {
        QueryUtils.endpoint(baseUrl).get({id: program.id}).$promise.then(function (response) {
          $scope.subjectProgram.independentStudy = response.independentStudy;
          $scope.subjectProgram.assessmentDescription = response.assessmentDescription;
          $scope.subjectProgram.studyLiterature = response.studyLiterature;
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
          })
          dtoToModel(response);
        })
      }
    }

    $scope.edit = function () {
      if ($scope.subjectProgram.status !== 'AINEPROGRAMM_STAATUS_I') {
        dialogService.confirmDialog({prompt: 'subjectProgram.messages.editAccept'}, function() {
          $location.url("/subjectStudyPeriods/" + subjectId + "/" + subjectStudyPeriodId + "/subjectProgram/" + subjectProgramId + "/edit");
        });
      } else {
        $location.url("/subjectStudyPeriods/" + subjectId + "/" + subjectStudyPeriodId + "/subjectProgram/" + subjectProgramId + "/edit");
      }
    }

    $scope.completeProgram = function () {
      dialogService.confirmDialog({prompt: 'subjectProgram.operation.complete.message'}, function() {
        QueryUtils.endpoint(baseUrl + "/complete").get({id: $scope.subjectProgram.id}).$promise.then(function () {
          message.info('subjectProgram.operation.complete.success');
          get();
        });
      });
    }

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

    get();
}]);