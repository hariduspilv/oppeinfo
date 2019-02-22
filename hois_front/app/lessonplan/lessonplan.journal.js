'use strict';

angular.module('hitsaOis').controller('LessonplanJournalEditController', ['$location', '$route', '$scope', '$timeout', 'dialogService', 'message', 'Classifier', 'QueryUtils', '$http',
  function ($location, $route, $scope, $timeout, dialogService, message, Classifier, QueryUtils, $http) {
    $scope.auth = $route.current.locals.auth;
    var id = $route.current.params.id;
    var lessonPlan = $route.current.params.lessonPlan;
    var occupationModule = $route.current.params.occupationModule;
    var lessonPlanModule = $route.current.params.lessonPlanModule;
    var baseUrl = '/lessonplans/journals';
    $scope.formState = {
      capacityTypes: QueryUtils.endpoint('/autocomplete/schoolCapacityTypes').query({journalId: id, isHigher: false, isTimetable: true})
    };

    $scope.record = QueryUtils.endpoint(baseUrl).get({
      id: id || 'new',
      lessonPlan: lessonPlan,
      occupationModule: occupationModule,
      lessonPlanModule: lessonPlanModule ? lessonPlanModule : undefined
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
            curriculumVersion: result.groups[i].curriculumVersion
          });
          result.groups[i].group.themes = QueryUtils.endpoint('/autocomplete/curriculumversionomodulethemes').query({
            addStudyYearToName: true,
            existInOtherJournals: true,
            journalId: id,
            studentGroupId: result.groups[i].studentGroup,
            curriculumVersionOmoduleId: result.groups[i].curriculumVersionOccupationModule
          });
          $timeout(setJournalFormPristine);
        }
      }
      checkCapacitiesForThemes();
      $scope.record.lessonPlan = result.lessonPlan;
    });

    function setJournalFormPristine() {
      if ($http.pendingRequests.length > 0) {
        $timeout(setJournalFormPristine);
      } else {
        $scope.journalForm.$setPristine();
      }
    }

    $scope.lessonPlanModule = $route.current.params.lessonPlanModule;

    function formIsValid() {
      var themes = $scope.record.journalOccupationModuleThemes;
      $scope.journalForm.theme.$setValidity('required', themes && themes.length > 0);
      $scope.journalForm.$setSubmitted();
      if (!$scope.journalForm.$valid) {
        message.error('main.messages.form-has-errors');
        return false;
      }
      return true;
    }

    $scope.searchStudentGroups = function (text) {
      if (!text) {
        return [];
      }
      var regExp = new RegExp('^.*' + text.replace("%", ".*").toUpperCase() + '.*$');
      return $scope.formState.studentGroups.filter(function (group) {
        return regExp.test($scope.$parent.currentLanguageNameField(group).toUpperCase());
      });
    }

    $scope.orderByThemeName = function (themeId) {
      return $scope.currentLanguageNameField($scope.formState.themeMap[themeId]);
    };

    $scope.update = function () {
      if (!formIsValid()) {
        return;
      } else {
        $scope.record.journalCapacityTypes = Classifier.getSelectedCodes($scope.formState.capacityTypes);
        if ($scope.record.journalCapacityTypes.length === 0) {
          message.error('lessonplan.journal.noJournalCapacityTypes');
          return;
        }
      }

      var existInOtherJournals =  themesThatExistInOtherJournals();
      if (existInOtherJournals.length > 0) {
        dialogService.confirmDialog({
          prompt: 'lessonplan.journal.themeExistInOtherJournalsConfirm',
          themes: existInOtherJournals.join(', ')
        }, function () {
          update();
        });
      } else {
        update();
      }
    };

    function update() {
      if ($scope.record.id) {
        $scope.record.$update().then(function (result) {
          message.updateSuccess();
          $scope.removedJournalTeacherIds = {};
          for (var i = 0; result.groups.length > i; i++) {
            result.groups[i].group.modules = QueryUtils.endpoint('/autocomplete/curriculumversionomodules').query({
              curriculumVersion: result.groups[i].curriculumVersion
            });
            result.groups[i].group.themes = QueryUtils.endpoint('/autocomplete/curriculumversionomodulethemes').query({
              addStudyYearToName: true,
              existInOtherJournals: true,
              journalId: id,
              studentGroupId: result.groups[i].studentGroup,
              curriculumVersionOmoduleId: result.groups[i].curriculumVersionOccupationModule
            });
          }
          $timeout(setJournalFormPristine);
        }).catch(angular.noop);
      } else {
        $scope.record.$save().then(function () {
          message.info('main.messages.create.success');
          $scope.journalForm.$setPristine();
          $scope.removedJournalTeacherIds = {};
          $location.url(baseUrl + '/' + $scope.record.id + '/edit?_noback&lessonPlanModule=' + $scope.record.lessonPlanModuleId);
        }).catch(angular.noop);
      }
    }

    function themesThatExistInOtherJournals() {
      var existInOtherJournals = [];

      $scope.record.journalOccupationModuleThemes.forEach(function (themeId) {
        var theme = $scope.formState.themeMap[themeId];
        if (theme.existsInOtherJournals) {
          existInOtherJournals.push($scope.currentLanguageNameField(theme));
        }
      });

      if ($scope.record.groups !== null) {
        for (var groupIndex = 0; groupIndex < $scope.record.groups.length; groupIndex++) {
          var group = $scope.record.groups[groupIndex];
          group.curriculumVersionOccupationModuleThemes.forEach(function (selectedThemeId) {
            var theme = group.group.themes.filter(function (theme) {
              return theme.id === selectedThemeId;
            })[0];
            if (theme.existsInOtherJournals) {
              existInOtherJournals.push($scope.currentLanguageNameField(theme));
            }
          });
        }
      }
      return existInOtherJournals;
    }

    $scope.filter = function (key, array) {
      return array.filter(function (obj) {
        return obj.id === key;
      })[0];
    };

    $scope.delete = function () {
      dialogService.confirmDialog({
        prompt: 'lessonplan.journal.deleteconfirm'
      }, function () {
        $scope.record.$delete().then(function () {
          message.info('main.messages.delete.success');
          $location.url('/lessonplans/vocational/' + $scope.record.lessonPlan + '/edit');
        }).catch(angular.noop);
      });
    };

    $scope.addTheme = function () {
      var themeId = $scope.formState.theme;
      if(themeId) {
        if ($scope.record.journalOccupationModuleThemes === null) {
          $scope.record.journalOccupationModuleThemes = [];
        }
        if ($scope.record.journalOccupationModuleThemes.indexOf(themeId) !== -1) {
          message.error('lessonplan.journal.duplicatetheme');
          $scope.journalForm.$setPristine();
          return;
        }
        $scope.record.journalOccupationModuleThemes.push(themeId);
        checkCapacitiesForThemes();
      }
      $scope.formState.theme = null;
    };

    $scope.deleteTheme = function (themeId) {
      var index = $scope.record.journalOccupationModuleThemes.indexOf(themeId);
      if (index !== -1) {
        $scope.record.journalOccupationModuleThemes.splice(index, 1);
      }
      checkCapacitiesForThemes();
      $scope.journalForm.$setDirty();
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
      $scope.journalForm.$setDirty();
    };

    $scope.addTeacher = function () {
      if ($scope.record.journalTeachers === null) {
        $scope.record.journalTeachers = [];
      }
      if ($scope.record.journalTeachers.some(function (e) {
          return e.teacher.id === $scope.formState.teacher.id;
        })) {
        message.error($scope.auth.higher ? 'lessonplan.journal.duplicateteacherHigher' : 'lessonplan.journal.duplicateteacherVocational');
        $scope.journalForm.$setPristine();
        return;
      }
      $scope.record.journalTeachers.push({
        id: $scope.removedJournalTeacherIds ? $scope.removedJournalTeacherIds[$scope.formState.teacher.id] : null,
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
      
      if (!$scope.removedJournalTeacherIds) {
        $scope.removedJournalTeacherIds = {};
      }
      $scope.removedJournalTeacherIds[teacher.teacher.id] = teacher.id;
      $scope.journalForm.$setDirty();
    };

    $scope.addGroup = function () {
      var group = $scope.formState.group;
      if ($scope.record.groups === null || typeof $scope.record.groups === 'undefined') {
        $scope.record.groups = [];
      }
      if ($scope.record.groups.some(function (e) {
          return e.group.id === group.id;
        })) {
        message.error('lessonplan.journal.duplicategroup');
        return;
      }
      QueryUtils.endpoint('/autocomplete/curriculumversionomodules').query({
        curriculumVersion: group.curriculumVersion
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
          $scope.journalForm.$setPristine();
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
          addStudyYearToName: true,
          existInOtherJournals: true,
          journalId: id,
          studentGroupId: moduleTheme.studentGroup,
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
        if (item.studentGroup === group.studentGroup) {
          groupIndex = index;
        }
      });
      if (groupIndex !== -1) {
        $scope.record.groups.splice(groupIndex, 1);
      }
      $scope.journalForm.$setDirty();
    };
  }
]);
