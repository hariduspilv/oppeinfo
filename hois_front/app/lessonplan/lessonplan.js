'use strict';

angular.module('hitsaOis').controller('LessonplanSearchController', ['$location', '$mdDialog', '$route', '$scope', 'DataUtils', 'QueryUtils', 'Session',
  function ($location, $mdDialog, $route, $scope, DataUtils, QueryUtils, Session) {
    var school = Session.school || {};
    $scope.formState = {higher: school.higher, vocational: school.vocational};
    $scope.currentNavItem = $route.current.$$route.data.currentNavItem;

    var baseUrl, criteria;
    if($scope.currentNavItem === 'lessonplan.vocational') {
      baseUrl = '/lessonplans';
      criteria = {};
      $scope.newLessonplan = function() {
        var formState = $scope.formState;
        $mdDialog.show({
          controller: function($scope) {
            $scope.formState = formState;
            $scope.record = {};

            $scope.create = function() {
              QueryUtils.endpoint(baseUrl).save($scope.record).$promise.then(function(result) {
                $mdDialog.hide();
                $location.url(baseUrl+'/vocational/'+result.id+'/edit');
              });
            };
            $scope.cancel = $mdDialog.hide;
          },
          templateUrl: 'lessonplan/new.lessonplan.dialog.html',
          clickOutsideToClose: true
        });
      };
      QueryUtils.endpoint(baseUrl+'/searchFormData').search().$promise.then(function(result) {
        var studyYears = result.studyYears;
        $scope.formState.studyYears = studyYears;
        if(!$scope.criteria.studyYear) {
          var sy = DataUtils.getCurrentStudyYearOrPeriod($scope.formState.studyYears);
          if(sy) {
            $scope.criteria.studyYear = sy.id;
          }
        }
        if($scope.criteria.studyYear) {
          $scope.loadData();
        }
        var allstudentgroups = result.studentGroups;
        var existingplans = result.studentGroupMapping;
        var studentgroups = {};
        for(var i = 0, cnt = studyYears.length; i < cnt; i++) {
          var syid = studyYears[i].id;
          var existing = existingplans[syid] || [];
          studentgroups[syid] = allstudentgroups.filter(function(it) { return existing.indexOf(it.id) === -1; });
        }
        $scope.formState.studentgroups = studentgroups;
      });
    } else if($scope.currentNavItem === 'lessonplan.vocational-byteacher') {
      baseUrl = '/lessonplans/byteacher';
      criteria = {};
    }

    QueryUtils.createQueryForm($scope, baseUrl, criteria);
  }
]).controller('LessonplanEditController', ['$location', '$mdDialog', '$route', '$scope', 'message', 'Classifier', 'QueryUtils',
  function ($location, $mdDialog, $route, $scope, message, Classifier, QueryUtils) {
    var id = $route.current.params.id;
    var baseUrl = '/lessonplans';
    $scope.formState = {capacityTypes: Classifier.queryForDropdown({mainClassCode: 'MAHT'})};
    QueryUtils.endpoint(baseUrl).get({id: id}).$promise.then(function(result) {
      $scope.formState.studyPeriods = result.studyPeriods;
      $scope.formState.weekNrs = result.weekNrs;
      delete result.studyPeriods;
      $scope.record = result;
    });

    $scope.newJournal = function(module) {
      $location.url(baseUrl + '/journals/new?lessonPlanModule=' + module.id);
    };
    $scope.editJournal = function(id, module) {
      $location.url(baseUrl + '/journals/' + id + '?lessonPlanModule=' + module.id);
    };

    $scope.update = function() {
      $scope.record.$update().then(message.updateSuccess);
    };
  }
]).controller('LessonplanJournalEditController', ['$location', '$mdDialog', '$route', '$scope', 'dialogService', 'message', 'Classifier', 'QueryUtils',
  function ($location, $mdDialog, $route, $scope, dialogService, message, Classifier, QueryUtils) {
    var id = $route.current.params.id;
    var lessonPlanModule = $route.current.params.lessonPlanModule;
    var baseUrl = '/lessonplans/journals';
    $scope.formState = {capacityTypes: Classifier.queryForDropdown({mainClassCode: 'MAHT'})};
    $scope.record = QueryUtils.endpoint(baseUrl).get({id: id || 'new', lessonPlanModule: lessonPlanModule});
    $scope.record.$promise.then(function(result) {
      $scope.formState.capacityTypes.$promise.then(function() {
        Classifier.setSelectedCodes($scope.formState.capacityTypes, $scope.record.journalCapacityTypes);
      });

      $scope.formState.themes = result.themes;
      delete result.themes;
      $scope.formState.themeMap = $scope.formState.themes.reduce(function(acc, item) { acc[item.id] = item; return acc; }, {});
    });

    function formIsValid() {
      $scope.journalForm.$setSubmitted();
      if(!$scope.journalForm.$valid) {
        message.error('main.messages.form-has-errors');
        return false;
      }
      return true;
    }

    $scope.update = function() {
      if(!formIsValid()) {
        return;
      }

      $scope.record.journalCapacityTypes = Classifier.getSelectedCodes($scope.formState.capacityTypes);
      if($scope.record.id) {
        $scope.record.$update().then(message.updateSuccess);
      }else{
        $scope.record.$save().then(function() {
          message.info('main.messages.create.success');
          $location.url(baseUrl + '/' + $scope.record.id + '/edit');
        });
      }
    };

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'journal.deleteconfirm'}, function() {
        $scope.record.$delete().then(function() {
          message.info('main.messages.delete.success');
          $location.url('/lessonplans/vocational/'+$scope.formState.lessonplan+'/edit?lessonPlanModule='+lessonPlanModule);
        });
      });
    };

    $scope.addTheme = function() {
      var themeId = $scope.formState.theme;
      if($scope.record.journalOccupationModuleThemes.indexOf(themeId) !== -1) {
        message.error('lessonplan.journal.duplicatetheme');
        return;
      }
      $scope.record.journalOccupationModuleThemes.push(themeId);
      $scope.formState.theme = null;
    };

    $scope.deleteTheme = function(themeId) {
      var index = $scope.record.journalOccupationModuleThemes.indexOf(themeId);
      if(index !== -1) {
        $scope.record.journalOccupationModuleThemes.splice(index, 1);
      }
    };
  }
]);
