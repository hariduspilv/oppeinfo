'use strict';

angular.module('hitsaOis').controller('LessonplanJournalEditController', ['$resource', '$location', '$mdDialog', '$route', '$scope', 'dialogService', 'message', 'Classifier', 'QueryUtils',
  function ($resource, $location, $mdDialog, $route, $scope, dialogService, message, Classifier, QueryUtils) {
    var id = $route.current.params.id;
    var lessonPlanModule = $route.current.params.lessonPlanModule;
    var baseUrl = '/lessonplans/journals';
    $scope.formState = {capacityTypes: Classifier.queryForDropdown({mainClassCode: 'MAHT'})};
    $scope.record = QueryUtils.endpoint(baseUrl).get({id: id || 'new', lessonPlanModule: lessonPlanModule});
    $scope.formState.studentGroups = QueryUtils.endpoint('/autocomplete/studentgroups').query({valid: true, higher: false});
    $scope.record.$promise.then(function(result) {
      $scope.formState.capacityTypes.$promise.then(function() {
        Classifier.setSelectedCodes($scope.formState.capacityTypes, $scope.record.journalCapacityTypes);
      });

      $scope.formState.themes = result.themes;
      delete result.themes;
      $scope.formState.themeMap = $scope.formState.themes.reduce(function(acc, item) { acc[item.id] = item; return acc; }, {});


      for(var i = 0; result.groups.length > i; i++) {
        result.groups[i].group.modules = QueryUtils.endpoint('/autocomplete/curriculumversionomodules').query({curriculumVersionId: result.groups[i].curriculumVersion});
        result.groups[i].group.themes = QueryUtils.endpoint('/autocomplete/curriculumversionomodulethemes').query({curriculumVersionOmoduleId: result.groups[i].curriculumVersionOccupationModule});
      }
    });

    $scope.lessonPlanModule = $route.current.params.lessonPlanModule;

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
        $scope.record.$update().then( function(result) {
          message.updateSuccess;
          for(var i = 0; result.groups.length > i; i++) {
            result.groups[i].group.modules = QueryUtils.endpoint('/autocomplete/curriculumversionomodules').query({curriculumVersionId: result.groups[i].curriculumVersion});
            result.groups[i].group.themes = QueryUtils.endpoint('/autocomplete/curriculumversionomodulethemes').query({curriculumVersionOmoduleId: result.groups[i].curriculumVersionOccupationModule});
          }
        });
      }else{
        $scope.record.$save().then(function() {
          message.info('main.messages.create.success');
          $location.url(baseUrl + '/' + $scope.record.id + '/edit?_noback&lessonPlanModule='+lessonPlanModule);
        });
      }
    };

    $scope.filter = function(key, array) {
      return array.filter( function(obj) {
        return obj.id === key;
      })[0];
    };

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'journal.deleteconfirm'}, function() {
        $scope.record.$delete().then(function() {
          message.info('main.messages.delete.success');
          $location.url('/lessonplans/vocational/'+$scope.record.lessonPlan+'/edit');
        });
      });
    };

    $scope.addTheme = function() {
      var themeId = $scope.formState.theme;
      if($scope.record.journalOccupationModuleThemes === null) {
        $scope.record.journalOccupationModuleThemes = [];
      }
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

    $scope.addTeacher = function() {
      if($scope.record.journalTeachers === null) {
        $scope.record.journalTeachers = [];
      }
      if($scope.record.journalTeachers.some(function(e) { return e.teacher.id === $scope.formState.teacher.id; })) {
        message.error('lessonplan.journal.duplicateteacher');
        return;
      }
      $scope.record.journalTeachers.push({teacher: $scope.formState.teacher});
      $scope.formState.teacher = undefined;
    };

    $scope.deleteTeacher = function(teacher) {
      var teacherIndex = -1;
      $scope.record.journalTeachers.forEach(function(item, index) {
        if(item.id === teacher.id) {
          teacherIndex = index;
        }
      });
      if(teacherIndex !== -1) {
        $scope.record.journalTeachers.splice(teacherIndex, 1);
      }
    };

    $scope.addGroup = function() {
      var group = JSON.parse(JSON.stringify($scope.filter($scope.formState.group, $scope.formState.studentGroups)));
      if($scope.record.groups === null || typeof $scope.record.groups === 'undefined') {
        $scope.record.groups = [];
      }
      if($scope.record.groups.some(function(e) { return e.group.id === $scope.formState.group; })) {
        message.error('lessonplan.journal.duplicategroup');
        return;
      }
      QueryUtils.endpoint('/autocomplete/curriculumversionomodules').query({curriculumVersionId: group.curriculumVersion}).$promise.then( function(response) {
        group.modules = response;
        $scope.record.groups.push({group: group, studentGroup: group.id});
      });
      $scope.formState.group = undefined;
    };

    $scope.newSelectedModule = function(moduleTheme) {
      if(moduleTheme.curriculumVersionOccupationModule !== null && moduleTheme.curriculumVersionOccupationModule !== "") {
        moduleTheme.curriculumVersionOccupationModuleThemes = null;
        QueryUtils.endpoint('/autocomplete/curriculumversionomodulethemes').query({curriculumVersionOmoduleId: moduleTheme.curriculumVersionOccupationModule}).$promise.then( function(response) {
          moduleTheme.group.themes = response;
        });
      } else {
        moduleTheme.group.themes = null;
      }
    };

    $scope.deleteGroup = function(group) {
      var groupIndex = -1;
      $scope.record.groups.forEach(function(item, index) {
        if(item.id === group.id) {
          groupIndex = index;
        }
      });
      if(groupIndex !== -1) {
        $scope.record.groups.splice(groupIndex, 1);
      }
    };
  }
]);
