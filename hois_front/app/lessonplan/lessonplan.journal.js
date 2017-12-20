'use strict';

angular.module('hitsaOis').controller('LessonplanJournalEditController', ['$location', '$route', '$scope', 'dialogService', 'message', 'Classifier', 'QueryUtils',
  function ($location, $route, $scope, dialogService, message, Classifier, QueryUtils) {
    var id = $route.current.params.id;
    var lessonPlanModule = $route.current.params.lessonPlanModule;
    var baseUrl = '/lessonplans/journals';
    $scope.formState = {
      capacityTypes: Classifier.queryForDropdown({
        mainClassCode: 'MAHT',
        vocational: true
      })
    };
    $scope.record = QueryUtils.endpoint(baseUrl).get({
      id: id || 'new',
      lessonPlanModule: lessonPlanModule
    });
    $scope.record.$promise.then(function (result) {
      $scope.formState.capacityTypes.$promise.then(function () {
        Classifier.setSelectedCodes($scope.formState.capacityTypes, $scope.record.journalCapacityTypes);
      });
      QueryUtils.endpoint('/autocomplete/studentgroups').query({
        valid: true,
        higher: false
      }).$promise.then(function (groups) {
        $scope.formState.studentGroups = groups.filter(function (group) {
          return group.id !== result.studentGroupId;
        });
      });

      $scope.formState.themes = result.themes;
      delete result.themes;
      $scope.formState.themeMap = $scope.formState.themes.reduce(function (acc, item) {
        acc[item.id] = item;
        return acc;
      }, {});

      if (angular.isArray(result.groups)) {
        for (var i = 0; result.groups.length > i; i++) {
          result.groups[i].group.modules = QueryUtils.endpoint('/autocomplete/curriculumversionomodules').query({
            curriculumVersionId: result.groups[i].curriculumVersion
          });
          result.groups[i].group.themes = QueryUtils.endpoint('/autocomplete/curriculumversionomodulethemes').query({
            curriculumVersionOmoduleId: result.groups[i].curriculumVersionOccupationModule
          });
        }
      }
    });

    $scope.lessonPlanModule = $route.current.params.lessonPlanModule;

    function formIsValid() {
      $scope.journalForm.$setSubmitted();
      if (!$scope.journalForm.$valid) {
        message.error('main.messages.form-has-errors');
        return false;
      }
      return true;
    }

    $scope.update = function () {
      if (!formIsValid()) {
        return;
      }

      $scope.record.journalCapacityTypes = Classifier.getSelectedCodes($scope.formState.capacityTypes);
      if ($scope.record.id) {
        $scope.record.$update().then(function (result) {
          message.updateSuccess();
          for (var i = 0; result.groups.length > i; i++) {
            result.groups[i].group.modules = QueryUtils.endpoint('/autocomplete/curriculumversionomodules').query({
              curriculumVersionId: result.groups[i].curriculumVersion
            });
            result.groups[i].group.themes = QueryUtils.endpoint('/autocomplete/curriculumversionomodulethemes').query({
              curriculumVersionOmoduleId: result.groups[i].curriculumVersionOccupationModule
            });
          }
        });
      } else {
        $scope.record.$save().then(function () {
          message.info('main.messages.create.success');
          $location.url(baseUrl + '/' + $scope.record.id + '/edit?_noback&lessonPlanModule=' + lessonPlanModule);
        });
      }
    };

    $scope.filter = function (key, array) {
      return array.filter(function (obj) {
        return obj.id === key;
      })[0];
    };

    $scope.delete = function () {
      dialogService.confirmDialog({
        prompt: 'journal.deleteconfirm'
      }, function () {
        $scope.record.$delete().then(function () {
          message.info('main.messages.delete.success');
          $location.url('/lessonplans/vocational/' + $scope.record.lessonPlan + '/edit');
        });
      });
    };

    $scope.addTheme = function () {
      var themeId = $scope.formState.theme;
      if ($scope.record.journalOccupationModuleThemes === null) {
        $scope.record.journalOccupationModuleThemes = [];
      }
      if ($scope.record.journalOccupationModuleThemes.indexOf(themeId) !== -1) {
        message.error('lessonplan.journal.duplicatetheme');
        return;
      }
      $scope.record.journalOccupationModuleThemes.push(themeId);
      checkCapacitiesForThemes();
      $scope.formState.theme = null;
    };

    $scope.deleteTheme = function (themeId) {
      var index = $scope.record.journalOccupationModuleThemes.indexOf(themeId);
      if (index !== -1) {
        $scope.record.journalOccupationModuleThemes.splice(index, 1);
      }
      checkCapacitiesForThemes();
    };

    function checkCapacitiesForThemes() {
      var capacities = {};
      $scope.record.journalOccupationModuleThemes.forEach(function (themeId) {
        var currentTheme = $scope.formState.themes.find(function (currTheme) {
          return currTheme.id === themeId;
        });
        for (var cap in currentTheme.capacities) {
          if (angular.isDefined(capacities[cap])) {
            capacities[cap] += currentTheme.capacities[cap];
          } else {
            capacities[cap] = currentTheme.capacities[cap];
          }
        }
      });
      var formstateCaps = $scope.formState.capacityTypes;

      formstateCaps.forEach(function (it) {
        if (angular.isDefined(capacities[it.code])) {
          it._selected = true;
          if (angular.isDefined(it.hours)) {
            it.hours = capacities[it.code];
          } else {
            it.hours = capacities[it.code];
          }
        } else {
          it._selected = false;
          it.hours = 0;
        }
      });
    }

    $scope.deleteRoom = function (room) {
      var index = $scope.record.journalRooms.indexOf(room);
      if (index !== -1) {
        $scope.record.journalRooms.splice(index, 1);
      }
    };

    $scope.addTeacher = function () {
      if ($scope.record.journalTeachers === null) {
        $scope.record.journalTeachers = [];
      }
      if ($scope.record.journalTeachers.some(function (e) {
          return e.teacher.id === $scope.formState.teacher.id;
        })) {
        message.error('lessonplan.journal.duplicateteacher');
        return;
      }
      $scope.record.journalTeachers.push({
        teacher: $scope.formState.teacher
      });
      $scope.formState.teacher = undefined;
    };

    $scope.deleteTeacher = function (teacher) {
      var teacherIndex = -1;
      $scope.record.journalTeachers.forEach(function (item, index) {
        if (item.id === teacher.id) {
          teacherIndex = index;
        }
      });
      if (teacherIndex !== -1) {
        $scope.record.journalTeachers.splice(teacherIndex, 1);
      }
    };

    $scope.addGroup = function () {
      var group = JSON.parse(JSON.stringify($scope.filter($scope.formState.group, $scope.formState.studentGroups)));
      if ($scope.record.groups === null || typeof $scope.record.groups === 'undefined') {
        $scope.record.groups = [];
      }
      if ($scope.record.groups.some(function (e) {
          return e.group.id === $scope.formState.group;
        })) {
        message.error('lessonplan.journal.duplicategroup');
        return;
      }
      QueryUtils.endpoint('/autocomplete/curriculumversionomodules').query({
        curriculumVersionId: group.curriculumVersion
      }).$promise.then(function (response) {
        group.modules = response;
        $scope.record.groups.push({
          group: group,
          studentGroup: group.id
        });
      });
      $scope.formState.group = undefined;
    };

    $scope.$watch('formState.room', function () {
      if (angular.isDefined($scope.formState.room) && $scope.formState.room !== null) {
        if ($scope.record.journalRooms.some(function (e) {
            return e.id === $scope.formState.room.id;
          })) {
          message.error('lessonplan.journal.duplicateroom');
          $scope.formState.room = undefined;
          return;
        }
        $scope.record.journalRooms.push($scope.formState.room);
        $scope.formState.room = undefined;
      }
    });

    $scope.$watch('formState.theme', function () {
      if (angular.isDefined($scope.formState.theme) && $scope.formState.theme !== null) {
        $scope.addTheme();
      }
    });

    $scope.$watch('formState.teacher', function () {
      if (angular.isDefined($scope.formState.teacher) && $scope.formState.teacher !== null) {
        $scope.addTeacher();
      }
    });

    $scope.$watch('formState.group', function () {
      if (angular.isDefined($scope.formState.group) && $scope.formState.group !== null) {
        $scope.addGroup();
      }
    });

    $scope.newSelectedModule = function (moduleTheme) {
      if (moduleTheme.curriculumVersionOccupationModule !== null && moduleTheme.curriculumVersionOccupationModule !== "") {
        moduleTheme.curriculumVersionOccupationModuleThemes = null;
        QueryUtils.endpoint('/autocomplete/curriculumversionomodulethemes').query({
          curriculumVersionOmoduleId: moduleTheme.curriculumVersionOccupationModule
        }).$promise.then(function (response) {
          moduleTheme.group.themes = response;
        });
      } else {
        moduleTheme.group.themes = null;
      }
    };

    $scope.deleteGroup = function (group) {
      var groupIndex = -1;
      $scope.record.groups.forEach(function (item, index) {
        if (item.id === group.id) {
          groupIndex = index;
        }
      });
      if (groupIndex !== -1) {
        $scope.record.groups.splice(groupIndex, 1);
      }
    };
  }
]);
