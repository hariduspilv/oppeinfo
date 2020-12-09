'use strict';

angular.module('hitsaOis').controller('SubjectStudyPeriodStudentGroupSearchController',
function ($scope, $route, QueryUtils, ArrayUtils, DataUtils, USER_ROLES, AuthService, message, $mdDialog, $q) {
    $scope.schoolId = $route.current.locals.auth.school.id;

    $scope.canEdit = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_KOORM);
    $scope.currentNavItem = 'studentGroups';
    $scope.addNew = add;

    $scope.formState = {
        xlsUrl: 'subjectStudyPeriods/studentGroups/searchByStudentGroup.xls',
        xlsUrlSummary: "subjectStudyPeriods/studentGroups/workloadsummary.xls"
    };

    QueryUtils.createQueryForm($scope, '/subjectStudyPeriods/studentGroups', {order: 'sg.code'}, function () {
        $scope.periodId = $scope.criteria.studyPeriod;
    });

    function setCurrentStudyPeriod() {
        if($scope.criteria) {
          if (!$scope.criteria.studyYear) {
            $scope.criteria.studyYear = DataUtils.getCurrentStudyYearOrPeriod($scope.studyYears).id;
            if (!$scope.criteria.studyPeriod) {
              $scope.criteria.studyPeriod = DataUtils.getCurrentStudyYearOrPeriod($scope.studyPeriods).id;
            }
          }
          $scope.filteredStudyPeriods = $scope.studyPeriods.filter($scope.filterStudyPeriods);
        }
        $scope.loadData();
    }

    $scope.studyYears = QueryUtils.endpoint('/autocomplete/studyYears').query();
    $scope.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriodsWithYear').query();
    $scope.studyPeriods.$promise.then(function (response) {
        response.forEach(function (studyPeriod) {
            studyPeriod.display = $scope.currentLanguageNameField(studyPeriod.studyYear) + ' ' + $scope.currentLanguageNameField(studyPeriod);
        });
    });
    $q.all([$scope.studyYears.$promise, $scope.studyPeriods.$promise])
      .then(setCurrentStudyPeriod)
      .then(function () {
        $scope.$watch('criteria.studyYear', function () {
          $scope.filteredStudyPeriods = $scope.studyPeriods.filter($scope.filterStudyPeriods);
          var hasSelected = !$scope.criteria.studyPeriod ||
            !!$scope.filteredStudyPeriods.find(function (it) { return it.id === $scope.criteria.studyPeriod; });
          if (!hasSelected || !$scope.criteria.studyYear) {
            $scope.criteria.studyPeriod = undefined;
          }
        });
      });

    $scope.load = function() {
        if (!$scope.searchForm.$valid) {
          message.error('main.messages.form-has-errors');
          return false;
        } else {
          $scope.loadData();
        }
      };

    $scope.directiveControllers = [];
    var clearCriteria = $scope.clearCriteria;
    $scope.clearCriteria = function () {
        clearCriteria();
        $scope.directiveControllers.forEach(function (c) {
            c.clear();
        });
    }

    $scope.$watch('criteria.department', function() {
        if ($scope.criteria.department && $scope.criteria.curriculum) {
            var curriculum = $scope.curricula.find(function (it) { return it.id === $scope.criteria.curriculum; });
            if (curriculum.departments.indexOf($scope.criteria.department) === -1) {
                $scope.hiddenCriteria.curriculum = undefined;
            }
        }
    });

    $scope.searchCurriculums = function (text) {
        return DataUtils.filterArrayByText($scope.curricula, text, function (obj, regex) {
            return $scope.filterCurriculums(obj) && regex.test(obj.display.toUpperCase());
        });
    };

    $scope.$watch("hiddenCriteria.curriculum", function (value) {
      $scope.criteria.curriculum = angular.isObject(value) ? value.id : value;
      if ($scope.criteria.curriculum) {
        if ($scope.criteria.curriculumVersion &&
          $scope.hiddenCriteria.curriculumVersion.curriculum !== $scope.criteria.curriculum) {
          $scope.hiddenCriteria.curriculumVersion = undefined;
        }
        if ($scope.criteria.studentGroup) {
          deselectStudentGroupIfNeeded();
        }
      }
    });

    $scope.$watch("hiddenCriteria.curriculumVersion", function (value) {
      $scope.criteria.curriculumVersion = angular.isObject(value) ? value.id : value;
      if ($scope.criteria.curriculumVersion) {
        if ($scope.criteria.studentGroup) {
          deselectStudentGroupIfNeeded();
        }
      }
    });

    $scope.$watchCollection("hiddenCriteria.studentGroup", function (value) {
      if (!angular.isArray(value)) {
        $scope.criteria.studentGroup = [];
        return;
      }
      $scope.criteria.studentGroup = value.map(function (it) { return it.id; });
    });

    function deselectStudentGroupIfNeeded() {
      $scope.hiddenCriteria.studentGroup = ($scope.hiddenCriteria.studentGroup || [])
        .filter(function (it) { return (!$scope.criteria.curriculum || it.curriculum === $scope.criteria.curriculum) &&
          (!$scope.criteria.curriculumVersion || it.curriculumVersion === $scope.criteria.curriculumVersion); });
    }

    $scope.curricula = QueryUtils.endpoint('/subjectStudyPeriods/studentGroups/curricula').query({}, function(result) {
        result.forEach(function (cur) {
            cur.display = cur.code + " - " + $scope.currentLanguageNameField(cur);
        });
    });

    $scope.filterCurriculums = function(curriculum) {
        return $scope.criteria.department ? ArrayUtils.includes(curriculum.departments, $scope.criteria.department) : true;
    };

    $scope.filterStudyPeriods = function (studyPeriod) {
      return !$scope.criteria.studyYear || studyPeriod.studyYear.id === $scope.criteria.studyYear;
    }

    function add() {
      var studyPeriods = $scope.studyPeriods;
      var selectedStudyPeriod = $scope.criteria.studyPeriod;
      var curLangField = $scope.currentLanguageNameField;

      $mdDialog.show({
        controller: function ($scope, $location) {
          $scope.formState = {studyPeriods: studyPeriods};
          $scope.record = {
            studyPeriod: selectedStudyPeriod
          };
          $scope.currentLanguageNameField = curLangField;

          $scope.$watchGroup(['record.studyPeriod', 'record.studentGroup'], function () {
            $scope.formState.previousPlans = [];
            if ($scope.record.studyPeriod && $scope.record.studentGroup) {
              $scope.formState.previousPlans = QueryUtils.endpoint('/subjectStudyPeriods/studentGroups/existingPlans/' +
                $scope.record.studyPeriod + '/' + $scope.record.studentGroup).query();
            }
          });

          $scope.create = function () {
            if(!$scope.newForm.$valid) {
              message.error('main.messages.form-has-errors');
              return;
            }

            if (!$scope.record.previousPlan) {
              $mdDialog.hide();
              $location.url('/subjectStudyPeriods/studentGroups/new?studyPeriodId=' + $scope.record.studyPeriod + '&studentGroupId=' + $scope.record.studentGroup);
            } else {
              QueryUtils.endpoint("/subjectStudyPeriods/studentGroups/copyPlan/" +
                $scope.record.studyPeriod + "/" + $scope.record.studentGroup + "/" +
                $scope.record.previousPlan.periodId + "/" + $scope.record.previousPlan.groupId).save2({}, function () {
                  $mdDialog.hide();
                  $location.url('/subjectStudyPeriods/studentGroups/' + $scope.record.studentGroup + '/' + $scope.record.studyPeriod + '/edit');
              });
            }
          };
          $scope.cancel = $mdDialog.hide;
        },
        templateUrl: 'subjectStudyPeriod/studentGroup/new.subject.study.period.student.group.dialog.html',
        clickOutsideToClose: true
      });
    }
});
