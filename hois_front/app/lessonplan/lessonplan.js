(function () {
  'use strict';

  function rowSum(row) {
    return row.reduce(function (sum, item) {
      if (item !== null && isFinite(item) && item > 0) {
        sum += item;
      }
      return sum;
    }, 0);
  }


  function createWeekTotalsRow(scope) {
    var row = [];
    for (var i = 0, cnt = scope.formState.weekNrs.length; i < cnt; i++) {
      row[i] = 0;
    }
    return row;
  }

  function createStudyPeriodTotalsRow(scope) {
    var row = [];
    for (var i = 0, cnt = scope.formState.studyPeriods.length; i < cnt; i++) {
      row[i] = 0;
    }
    return row;
  }

  function updateJournalRowTotals(scope, journal, capacityType) {
    var sum = rowSum(journal.hours[capacityType]);
    scope.formState.journalTotals[journal.id][capacityType] = sum;
  }

  function updateSpJournalRows(scope, journal, capacityType) {
    var hours = journal.hours[capacityType];
    scope.formState.studyPeriods.forEach(function (sp) {
      journal.spHours[capacityType][sp.arrayIndex] = rowSum(hours.slice(sp.weekIndex[0], sp.weekIndex[1]));
    });
  }

  function updateSpJournalTotals(scope, journal) {
    if (!angular.isObject(scope.formState.journalTotals[journal.id]._spTotals)) {
      scope.formState.journalTotals[journal.id]._spTotals = {};
    }
    scope.formState.studyPeriods.forEach(function (sp, spIndex) {
      scope.formState.journalTotals[journal.id]._spTotals[spIndex] = Object.values(journal.spHours).reduce(function (sum, item) {
        if (angular.isObject(item) && !isNaN(item[spIndex])) {
          sum += item[spIndex];
        }
        return sum;
      }, 0);
    });
  }

  function initializeSpGrandTotals(scope, capacityType) {
    if (!angular.isObject(scope.formState.capGrandTotalsSp)) {
      scope.formState.capGrandTotalsSp = {};
      scope.formState.grandTotalsSp = {};
    }
    if (!angular.isObject(scope.formState.capGrandTotalsSp[capacityType])) {
      scope.formState.capGrandTotalsSp[capacityType] = {};
    }
    scope.formState.studyPeriods.forEach(function (sp) {
      if (angular.isArray(scope.formState.grandTotals[capacityType])) {
        scope.formState.capGrandTotalsSp[capacityType][sp.arrayIndex] = rowSum(scope.formState.grandTotals[capacityType].slice(sp.weekIndex[0], sp.weekIndex[1]));
        scope.formState.grandTotalsSp[sp.arrayIndex] = rowSum(scope.formState.grandTotals._._.slice(sp.weekIndex[0], sp.weekIndex[1]));
      }
    });
  }

  function updateJournalTotals(scope, journal, index) {
    var sum = scope.formState.capacityTypes.reduce(function (sum, c) {
      var hours = journal.hours[c.code];
      if (hours !== undefined) {
        var item = hours[index];
        if (item !== null && isFinite(item) && item > 0) {
          sum += item;
        }
      }
      return sum;
    }, 0);
    var totals = scope.formState.journalTotals[journal.id];
    totals._[index] = sum;
    totals.__ = rowSum(totals._);
  }

  function updateTotals(scope, totals, capacityType, index) {
    totals._[capacityType] = rowSum(totals[capacityType]);
    var sum = scope.formState.capacityTypes.reduce(function (sum, c) {
      var hours = totals[c.code];
      if (hours !== undefined) {
        sum += hours[index];
      }
      return sum;
    }, 0);
    totals._._[index] = sum;
    totals.__ = rowSum(totals._._);
  }

  angular.module('hitsaOis').controller('LessonplanSearchController',
    function ($location, $mdDialog, $route, $scope, DataUtils, QueryUtils, Session, USER_ROLES, AuthService, message) {
      $scope.auth = $route.current.locals.auth;
      $scope.canEdit = AuthService.isAuthorized([USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_TUNNIJAOTUSPLAAN,
        USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_AINEOPPETAJA]);

      var school = Session.school || {};
      $scope.formState = {
        higher: school.higher,
        vocational: school.vocational
      };
      
      $scope.currentNavItem = $route.current.$$route.data.currentNavItem;
      var baseUrl = '/lessonplans';
      QueryUtils.createQueryForm($scope, baseUrl, {order: 'sg.code'});

      $scope.load = function() {
        if (!$scope.vocationalSearchForm.$valid) {
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
      };
      
      $scope.newLessonplan = function () {
        var formState = $scope.formState;
        var studyYear = $scope.criteria.studyYear;

        $mdDialog.show({
          controller: function ($scope) {
            $scope.formState = formState;
            $scope.record = {
              studyYear: studyYear
            };

            $scope.$watchGroup(['record.studyYear', 'record.studentGroup'], function () {
              $scope.formState.previousLessonplans = [];
              if (angular.isDefined($scope.record.studyYear) && angular.isDefined($scope.record.studentGroup)) {
                var studyYear = $scope.formState.studyYears.find(function (it) { return it.id === $scope.record.studyYear; });
                var studentGroup = $scope.formState.studentGroups.find(function (it) { return it.id === $scope.record.studentGroup; });
                var curriculumLessonPlans = $scope.formState.curriculumLessonPlans[studentGroup.curriculum];
                if (curriculumLessonPlans) {
                  $scope.formState.previousLessonplans = curriculumLessonPlans.filter(function (it) {
                    return new Date(it.studyYearEndDate).getTime() <= new Date(studyYear.endDate).getTime();
                  });
                }
              }
            });

            $scope.search = function (text) {
              if (!text) {
                return [];
              }
              var regExp = new RegExp('^.*' + text.replace("%", ".*").toUpperCase() + '.*$');
              return $scope.formState.studentGroups.filter(function (group) {
                return regExp.test($scope.$parent.currentLanguageNameField(group).toUpperCase());
              }).filter(function (group) {
                return angular.isDefined($scope.formState.studentGroupMap[$scope.record.studyYear].find(function (it) {
                  return it.id === group.id;
                }));
              });
            };

            $scope.create = function () {
              if(!$scope.newLessonPlanForm.$valid) {
                message.error('main.messages.form-has-errors');
                return;
              }

              QueryUtils.endpoint(baseUrl).save($scope.record).$promise.then(function (result) {
                $mdDialog.hide();
                $location.url(baseUrl + '/vocational/' + result.id + '/edit');
              });
            };
            $scope.cancel = $mdDialog.hide;
          },
          templateUrl: 'lessonplan/new.lessonplan.dialog.html',
          clickOutsideToClose: true
        });
      };

      function validStudentGroups(studyYear, groups) {
        var validGroups = [];
        var syStart = studyYear.startDate;
        var syEnd = studyYear.endDate;

        for (var i = 0; i < groups.length; i++) {
          if (isValidStudentGroup(syStart, syEnd, groups[i].validFrom, groups[i].validThru, validGroups) && validGroups.indexOf(groups[i].id) === -1) {
            validGroups.push(groups[i].id);
          }
        }
        return validGroups;
      }

      function isValidStudentGroup(studyYearStart, studyYearEnd, validFrom, validThru) {
        return (!validFrom || moment(validFrom).isSameOrBefore(studyYearEnd)) && (!validThru || moment(validThru).isSameOrAfter(studyYearStart));
      }

      function filterStudentGroups(existing, valid) {
        return function (it) {
          return existing.indexOf(it.id) === -1 && valid.indexOf(it.id) !== -1;
        };
      }

      QueryUtils.endpoint(baseUrl + '/searchFormData').search().$promise.then(function (result) {
        var studyYears = result.studyYears;
        $scope.formState.studyYears = studyYears;
        if (!$scope.criteria.studyYear) {
          var sy = DataUtils.getCurrentStudyYearOrPeriod($scope.formState.studyYears);
          if (sy) {
            $scope.criteria.studyYear = sy.id;
          }
        }
        if ($scope.criteria.studyYear) {
          $scope.loadData();
        }
        var allstudentgroups = result.studentGroups;
        var existingplans = result.studentGroupMapping;
        var studentgroups = {};
        for (var i = 0, cnt = studyYears.length; i < cnt; i++) {
          var syid = studyYears[i].id;
          var existing = existingplans[syid] || [];
          var valid = validStudentGroups(studyYears[i], allstudentgroups);
          studentgroups[syid] = allstudentgroups.filter(filterStudentGroups(existing, valid));
        }
        $scope.formState.studentGroups = allstudentgroups;
        $scope.formState.studentGroupMap = studentgroups;
        $scope.formState.curriculumVersions = result.curriculumVersions;
        $scope.formState.curriculumLessonPlans = result.curriculumLessonPlans;
      });

      $scope.$watch('criteria.teacherObject', function (value) {
        $scope.criteria.teacher = angular.isObject(value) ? value.id : value;
      });
    }
  ).controller('LessonplanTeacherSearchController',
    function ($route, $scope, DataUtils, QueryUtils, Session, USER_ROLES, AuthService, message) {
      $scope.auth = $route.current.locals.auth;

      $scope.canEdit = AuthService.isAuthorized([USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_TUNNIJAOTUSPLAAN,
        USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_AINEOPPETAJA]);

      var school = Session.school || {};
      $scope.formState = {
        higher: school.higher,
        vocational: school.vocational
      };
      $scope.currentNavItem = $route.current.$$route.data.currentNavItem;

      var baseUrl = '/lessonplans/byteacher';

      $scope.load = function() {
        if (!$scope.vocationalTeacherSearchForm.$valid) {
          message.error('main.messages.form-has-errors');
          return false;
        } else {
          $scope.loadData();
        }
      };

      $scope.formState.studyYears = QueryUtils.endpoint('/autocomplete/studyYears').query();
      $scope.formState.studyYears.$promise.then(function () {
        if (!$scope.criteria.studyYear) {
          var sy = DataUtils.getCurrentStudyYearOrPeriod($scope.formState.studyYears);
          if (sy) {
            $scope.criteria.studyYear = sy.id;
          }
        }
        if ($scope.criteria.studyYear) {
          $scope.loadData();
        }
      });

      QueryUtils.createQueryForm($scope, baseUrl, {});
    }
  ).controller('LessonplanEditController', ['$location', '$route', '$scope', '$timeout', '$window', 'message', 'Classifier', 'LessonPlanTableService', 'QueryUtils', 'dialogService', 'stateStorageService',
    
  function ($location, $route, $scope, $timeout, $window ,message, Classifier, LessonPlanTableService, QueryUtils, dialogService, stateStorageService) {
      var id = $route.current.params.id;
      var schoolId = $route.current.locals.auth.school.id;
      var stateKey = 'lessonplan';
      var baseUrl = '/lessonplans';
      var journalMapper = Classifier.valuemapper({groupProportion: 'PAEVIK_GRUPI_JAOTUS'});
      $scope.lessonPlanId = id;

      $scope.formState = {
        showWeeks: true,
        scrollPosition: 0
      };
      var saveScrollPosition = true;

      var containter = angular.element(document.getElementById('lessonplan-container'));
      containter.bind('scroll', function() {
        if (saveScrollPosition) {
          $scope.formState.scrollPosition = containter[0].scrollTop;
        }
      });

      $scope.$on('$locationChangeStart', function (event, nextUrl) {
        if (nextUrl.indexOf('journal') !== -1) {
          var lessonplanScrollPosition = {};
          lessonplanScrollPosition[id] = $scope.formState.scrollPosition;
          stateStorageService.changeState(schoolId, stateKey, {scrollPosition: lessonplanScrollPosition});
        }
      });

      function setLessonplanContainerScroll(scrollValue) {
        saveScrollPosition = false;
        containter[0].scrollTop = scrollValue;
        $timeout(function () {
          saveScrollPosition = true;
        }, 1000);
      }

      $scope.showWeeksChanged = function () {
        stateStorageService.changeState(schoolId, stateKey, {showWeeks: $scope.formState.showWeeks});
        LessonPlanTableService.generateLessonPlan($scope);
      };

      $scope.showTotalsChanged = function () {
        stateStorageService.changeState(schoolId, stateKey, {showTotals: $scope.formState.showTotals});
        LessonPlanTableService.generateLessonPlan($scope);
      };

      $scope.updateStudyPeriods = function () {
        var selectedStudyPeriods = $scope.formState.studyPeriods.filter(function (sp) {
          return sp._selected;
        }).map(function (sp) { return sp.id; });

        $scope.formState.weekNrs.forEach(function (weekNr) {
          weekNr.show = selectedStudyPeriods.indexOf(weekNr.spId) !== -1;
        });
        
        $scope.atLeastOneShownPeriod = atLeastOneShownPeriod();
        stateStorageService.changeState(schoolId, stateKey, {
          showWeeks: $scope.formState.showWeeks,
          showTotals: $scope.formState.showTotals,
          selectedStudyPeriods: selectedStudyPeriods
        });
        sumShownWeeks();
        LessonPlanTableService.generateLessonPlan($scope);
      };

      function sumShownWeeks() {
        $scope.formState.shownWeeksCount = $scope.formState.weekNrs.filter(function (weekNr) {
          return weekNr.show;
        }).length;
      }

      function atLeastOneShownPeriod() {
        for (var i = 0; i < $scope.formState.studyPeriods.length; i++) {
          if ($scope.formState.studyPeriods[i]._selected) {
            return true;
          }
        }
        return false;
      }

      function updateModuleTotals(module, capacityType, index) {
        var moduleTotals = $scope.formState.moduleTotals[module.id];
        var sum = module.journals.reduce(function (sum, journal) {
          var hours = journal.hours[capacityType];
          if (hours !== undefined && hours[index] !== undefined) {
            sum += hours[index] * (1 / journal.groupProportion.value);
          }
          return sum;
        }, 0);
        moduleTotals[capacityType][index] = sum;

        updateTotals($scope, moduleTotals, capacityType, index);
      }

      function updateSpModuleTotals(module, capacityType, studyPeriodIndex) {
        var spModuleTotals = $scope.formState.spModuleTotals[module.id];
        var sum = module.journals.reduce(function (sum, journal) {
          var hours = journal.spHours[capacityType];
          if (hours !== undefined && hours[studyPeriodIndex] !== undefined) {
            sum += hours[studyPeriodIndex] * (1 / journal.groupProportion.value);
          }
          return sum;
        }, 0);
        spModuleTotals[capacityType][studyPeriodIndex] = sum;

        updateTotals($scope, spModuleTotals, capacityType, studyPeriodIndex);
      }

      function updateGrandTotals(capacityType, index) {
        var sum = Object.keys($scope.formState.moduleMap).reduce(function (sum, moduleId) {
          var moduleTotals = $scope.formState.moduleTotals[moduleId];
          var hours = moduleTotals[capacityType];
          if (hours !== undefined) {
            sum += hours[index];
          }
          return sum;
        }, 0);
        var grandTotals = $scope.formState.grandTotals;
        grandTotals[capacityType][index] = sum;
        updateTotals($scope, grandTotals, capacityType, index);
      }
      
      var copyOfRecord;
      QueryUtils.loadingWheel($scope, true);
      QueryUtils.endpoint(baseUrl).get({
        id: id
      }).$promise.then(function (result) {
        $scope.formState.studyPeriods = result.studyPeriods;
        var spLocationPointer = 0;
        $scope.formState.studyPeriods.forEach(function (sp, spIndex) {
          sp.weekIndex = [spLocationPointer, spLocationPointer + sp.weekNrs.length];
          sp.arrayIndex = spIndex;
          spLocationPointer += sp.weekNrs.length;
        });

        $scope.formState.weekNrs = [];
        result.studyPeriods.forEach(function (studyPeriod, index) {
          for (var i = 0; i < studyPeriod.weekNrs.length; i++) {
            $scope.formState.weekNrs.push({
              spId: studyPeriod.id,
              spIndex: index,
              nr: studyPeriod.weekNrs[i],
              endOfPeriod: i === studyPeriod.weekNrs.length - 1,
              shown: true
            });
          }
        });

        $scope.formState.weekBeginningDates = result.weekBeginningDates;
        $scope.formState.legends = result.legends.reduce(function (acc, item) {
          acc[item.weekNr] = item.color;
          return acc;
        }, {});
        delete result.studyPeriods;
        delete result.weekNrs;
        delete result.weekBeginningDates;
        delete result.legends;
        $scope.formState.moduleMap = {};
        $scope.formState.rowTotals = {};
        $scope.formState.journalTotals = {};
        $scope.formState.moduleTotals = {};
        $scope.formState.spModuleTotals = {};
        $scope.formState.grandTotals = {
          _: {
            _: createWeekTotalsRow($scope)
          }
        };
        $scope.formState.studyPeriodMonths = result.studyPeriod % 12;
        $scope.formState.studyPeriodYears = Math.floor(result.studyPeriod / 12);
        $scope.formState.xlsUrl = 'lessonplans/' + id + '/lessonplan.xls';

        var state = stateStorageService.loadState(schoolId, stateKey);
        if (!angular.equals({}, state)) {
          $scope.formState.showWeeks = state.showWeeks;
          $scope.formState.showTotals = state.showTotals;
          $scope.formState.scrollPosition = state.scrollPosition && state.scrollPosition[id] ? state.scrollPosition[id] : 0;
          setSelectedStudyPeriods(state.selectedStudyPeriods);
        } else {
          setSelectedStudyPeriods();
        }
        initializeTotals(result);
        QueryUtils.loadingWheel($scope, false);
        $scope.windowWidth = $window.innerWidth;

        angular.element(document.getElementsByClassName("container")).css('height', $window.innerHeight-340 + 'px');
      });

      /*
      $scope.finished = function () {
        var tb;
        tb = angular.element(document.getElementsByClassName("lessonplan")).find("tbody")[0].clientHeight+340+18;
        //console.log(document.getElementById('ng-repeat-table-test').getBoundingClientRect());
        //console.log("x: "+angular.element(document.getElementsByClassName("lessonplan")).find("tbody")[0].clientHeight+"_"+tb);
        //console.log("x: "+angular.element(document.getElementsByClassName("lessonplan"))[0].offsetHeight+"_"+tb);
        //console.log("x: "+angular.element(document.getElementsByClassName("lessonplan")).find("tbody")[0].offsetHeight+"_"+tb);
        //angular.element(document.getElementsByClassName("container")).css('height', (tb > $window.innerHeight ? $window.innerHeight : tb) -250 + 'px');
        $scope.tb=tb;
      };
      */

      angular.element($window).bind('resize', function(){
        //angular.element(document.getElementsByClassName("container")).css('height', ($scope.tb > $window.innerHeight ? $window.innerHeight : $scope.tb)-340 + 'px');
        angular.element(document.getElementsByClassName("container")).css('height', $window.innerHeight - 340 + 'px');
      });

      function setSelectedStudyPeriods(selectedStudyPeriods) {
        if (!selectedStudyPeriods) {
          selectedStudyPeriods = $scope.formState.studyPeriods.map(function (sp) { return sp.id; });
        }
        
        $scope.formState.studyPeriods.forEach(function (sp) {
          sp._selected = selectedStudyPeriods.indexOf(sp.id) !== -1;
        });
      }

      function initializeTotals(result) {
        $scope.formState.capacityTypes = result.lessonPlanCapacities;

        $scope.formState.teachers = result.teachers;
        result.modules.forEach(function (module) {
          $scope.formState.moduleMap[module.id] = module;
          $scope.formState.spModuleTotals[module.id] = {
            _: {
              _: createStudyPeriodTotalsRow($scope)
            }
          };
          $scope.formState.moduleTotals[module.id] = {
            _: {
              _: createWeekTotalsRow($scope)
            }
          };
          module.journals.forEach(function (journal) {
            journalMapper.objectmapper(journal);
            journal.spHours = {};
            journal.spTotals = {};
            journal.lessonPlanModule = module.id;
            $scope.formState.journalTotals[journal.id] = {
              _: createWeekTotalsRow($scope)
            };
            $scope.formState.capacityTypes.forEach(function (c) {
              var capacityType = c.code;
              journal.spHours[capacityType] = {};
              var hours = journal.hours[capacityType];
              if (hours !== undefined) {
                updateJournalRowTotals($scope, journal, capacityType);
                updateSpJournalRows($scope, journal, capacityType);
                var moduleTotals = $scope.formState.moduleTotals[module.id];
                if (moduleTotals[capacityType] === undefined) {
                  moduleTotals[capacityType] = createWeekTotalsRow($scope);
                }
                var spModuleTotals = $scope.formState.spModuleTotals[module.id];
                if (spModuleTotals[capacityType] === undefined) {
                  spModuleTotals[capacityType] = createStudyPeriodTotalsRow($scope);
                }
                var grandTotals = $scope.formState.grandTotals;
                if (grandTotals[capacityType] === undefined) {
                  grandTotals[capacityType] = createWeekTotalsRow($scope);
                }
              }
            });
            for (var i = 0, wnCnt = $scope.formState.weekNrs.length; i < wnCnt; i++) {
              updateJournalTotals($scope, journal, i);
            }
            updateSpJournalTotals($scope, journal);
            journal.requiredLessons = {};
            journal.themes.forEach(function (theme) {
              for (var key in theme.hours) {
                if (angular.isUndefined(journal.requiredLessons[key])) {
                  journal.requiredLessons[key] = 0;
                }
                journal.requiredLessons[key] += theme.hours[key];
              }
            });
          });

          $scope.formState.capacityTypes.forEach(function (c) {
            var capacityType = c.code;
            var moduleTotals = $scope.formState.moduleTotals[module.id];
            if (moduleTotals[capacityType] !== undefined) {
              for (var i = 0, wnCnt = $scope.formState.weekNrs.length; i < wnCnt; i++) {
                updateModuleTotals(module, capacityType, i);
              }
              for (var j = 0, spCnt = $scope.formState.studyPeriods.length; j < spCnt; j++) {
                updateSpModuleTotals(module, capacityType, j);
              }
            }
          });
        });
        $scope.formState.capacityTypes.forEach(function (c) {
          var capacityType = c.code;
          var grandTotals = $scope.formState.grandTotals;
          if (grandTotals[capacityType] !== undefined) {
            for (var i = 0, wnCnt = $scope.formState.weekNrs.length; i < wnCnt; i++) {
              updateGrandTotals(capacityType, i);
            }
          }
          initializeSpGrandTotals($scope, capacityType);
        });
        $scope.record = result;
        updateCopyOfRecord();
        $scope.updateStudyPeriods();
        LessonPlanTableService.generateLessonPlan($scope);
        setLessonplanContainerScroll($scope.formState.scrollPosition);
      }

      $scope.updateTotals = function (journal, capacityType, index) {
        var hours = journal.hours[capacityType];
        var value = parseInt(hours[index], 10);
        if (isNaN(value) || value < 0) {
          value = undefined;
        }
        if (value !== hours[index]) {
          hours[index] = value;
        }

        updateJournalRowTotals($scope, journal, capacityType);
        updateJournalTotals($scope, journal, index);
        updateModuleTotals($scope.formState.moduleMap[journal.lessonPlanModule], capacityType, index);
        updateGrandTotals(capacityType, index);
        //study period updates - we have to keep 2 different forms up to date at the same time
        $scope.updateSpRowByWeek(journal, capacityType, index);
        updateSpJournalTotals($scope, journal);
        updateSpModuleTotals($scope.formState.moduleMap[journal.lessonPlanModule], capacityType, $scope.formState.weekNrs[index].spIndex);
        $scope.updateSpGrandTotals(capacityType, index);
      };

      $scope.getCapacityByCode = function (capacityCode) {
        if ($scope.formState.capacityTypes) {
          return $scope.formState.capacityTypes.find(function (it) {
            return it.code === capacityCode;
          });
        }
      };

      $scope.getLegendByWeek = function (weekNr) {
        return LessonPlanTableService.getLegendByWeek($scope, weekNr);
      };

      $scope.updateLessonCountByStudyPeriod = function (journal, capacityType, spIndex) {
        var studyPeriod = $scope.formState.studyPeriods[spIndex];
        var newTotalHours = journal.spHours[capacityType][spIndex];
        var lessonsByWeek = newTotalHours / studyPeriod.weekNrs.length;
        if (lessonsByWeek % 1 !== 0) {
          lessonsByWeek = Math.ceil(lessonsByWeek);
        }
        var lessonsAdded = 0;
        var weekIndex = studyPeriod.weekIndex[0];
        for (var weekNr = 0; weekNr < studyPeriod.weekNrs.length; weekNr++) {
          journal.hours[capacityType][weekIndex] = lessonsByWeek;
          lessonsAdded += lessonsByWeek;
          $scope.updateTotals(journal, capacityType, weekIndex);
          if (lessonsAdded === Number(newTotalHours)) {
            lessonsByWeek = 0;
          }
          if (((newTotalHours - lessonsAdded) / (studyPeriod.weekNrs.length - weekNr - 1)) % 1 === 0) {
            lessonsByWeek = (newTotalHours - lessonsAdded) / (studyPeriod.weekNrs.length - weekNr - 1);
          }
          weekIndex += 1;
        }
      };

      $scope.updateSpGrandTotals = function (capacityType, index) {
        if (!angular.isObject($scope.formState.capGrandTotalsSp)) {
          $scope.formState.capGrandTotalsSp = {};
          $scope.formState.grandTotalsSp = {};
        }
        if (!angular.isObject($scope.formState.capGrandTotalsSp[capacityType])) {
          $scope.formState.capGrandTotalsSp[capacityType] = {};
        }
        var sp = $scope.formState.studyPeriods.find(function (it) {
          return it.weekIndex[0] <= index && it.weekIndex[1] >= index;
        });
        $scope.formState.capGrandTotalsSp[capacityType][sp.arrayIndex] = rowSum($scope.formState.grandTotals[capacityType].slice(sp.weekIndex[0], sp.weekIndex[1]));
        $scope.formState.grandTotalsSp[sp.arrayIndex] = rowSum($scope.formState.grandTotals._._.slice(sp.weekIndex[0], sp.weekIndex[1]));
      };

      $scope.updateSpRowByWeek = function (journal, capacityType, index) {
        var hours = journal.hours[capacityType];
        var sp = $scope.formState.studyPeriods.find(function (it) {
          return it.weekIndex[0] <= index && it.weekIndex[1] >= index;
        });
        journal.spHours[capacityType][sp.arrayIndex] = rowSum(hours.slice(sp.weekIndex[0], sp.weekIndex[1]));
      };
      
      $scope.update = function () {
        $scope.record.$update().then(message.updateSuccess).then(function() {
          initializeTotals($scope.record);
          updateCopyOfRecord();
          $scope.$broadcast('refreshFixedColumns');
        });
      };

      function updateCopyOfRecord() {
        copyOfRecord = angular.toJson($scope.record);
      }

      $scope.delete = function () {
        dialogService.confirmDialog({prompt: 'lessonplan.prompt.deleteLessonplan'}, function() {
          $scope.record.$delete().then(function() {
            message.info('main.messages.delete.success');
            redirectBack();
          }).catch(angular.noop);
        });
      };

      $scope.back = function() {
        if(copyOfRecord === angular.toJson($scope.record)) {
          redirectBack();
        } else {
          dialogService.confirmDialog({
            prompt: 'lessonplan.changed.save',
            accept: 'main.yes',
            cancel: 'main.no'
          },
          function () {
            $scope.record.$update().then(message.updateSuccess).then(redirectBack);
          },
          function() {
            redirectBack();
          });
        }
      };

      function redirectBack() {
        $location.path("/lessonplans/vocational");
      }

    }
  ]).controller('LessonplanTeacherViewController', ['$route', '$scope', 'LessonPlanTableService', 'QueryUtils',

    function ($route, $scope, LessonPlanTableService, QueryUtils) {
      $scope.auth = $route.current.locals.auth;
      var id = $route.current.params.id;
      var studyYearId = $route.current.params.studyYear;
      var baseUrl = '/lessonplans/byteacher';

      $scope.formState = {
        showWeeks: true,
        showCapacityTypes: true,
        xlsUrl: 'lessonplans/byteacher/' + id + '/' + studyYearId + '/lessonplanbyteacher.xls'
      };
      $scope.keys = Object.keys;

      $scope.getCapacityTypes = function (hours) {
        if ($scope.formState.capacityTypes) {
          return $scope.formState.capacityTypes.filter(function (ct) {
            return hours !== undefined && hours[ct.code] !== undefined;
          });
        }
      };

      $scope.getCapacityByCode = function (capacityCode) {
        return LessonPlanTableService.getCapacityByCode($scope.formState.capacityTypes, capacityCode);
      };

      $scope.getUniqueJournalThemes = function(themes) {
        return LessonPlanTableService.getUniqueJournalThemes(themes);
      };

      $scope.sumSubjectHours = function(hours) {
        if (angular.isDefined($scope.formState.subjectCapacityTypes)) {
          return $scope.formState.subjectCapacityTypes.reduce(function(total, sct) {
            var periodHours = hours[sct.studyPeriod];
            if (periodHours) {
              var capacityHours = periodHours[sct.code];
              if (capacityHours) {
                total += capacityHours;
              }
            }
            return total;
          }, 0);
        }
      };

      QueryUtils.endpoint(baseUrl + '/:id/:studyYear').search({
        id: id,
        studyYear: studyYearId
      }).$promise.then(function (result) {
        $scope.formState.capacityTypes = result.lessonPlanCapacities;
        
        $scope.formState.studyPeriods = result.studyPeriods;
        var spLocationPointer = 0;
        $scope.formState.studyPeriods.forEach(function (sp, spIndex) {
          sp.weekIndex = [spLocationPointer, spLocationPointer + sp.weekNrs.length];
          sp.arrayIndex = spIndex;
          spLocationPointer += sp.weekNrs.length;
        });

        $scope.formState.weekNrs = [];
        result.studyPeriods.forEach(function (studyPeriod) {
          for (var i = 0; i < studyPeriod.weekNrs.length; i++) {
            $scope.formState.weekNrs.push({
              nr: studyPeriod.weekNrs[i],
              endOfPeriod: i === studyPeriod.weekNrs.length - 1
            });
          }
        });

        $scope.formState.weekBeginningDates = result.weekBeginningDates;
        $scope.formState.rowTotals = {};
        $scope.formState.journalTotals = {};
        $scope.formState.grandTotals = {
          _: {
            _: createWeekTotalsRow($scope)
          }
        };
        
        // initialize totals
        result.journals.forEach(function (journal) {
          journal.spHours = {};
          journal.spTotals = {};
          $scope.formState.journalTotals[journal.id] = {
            _: createWeekTotalsRow($scope)
          };
          $scope.formState.capacityTypes.forEach(function (c) {
            var capacityType = c.code;
            journal.spHours[capacityType] = {};
            var hours = journal.hours[capacityType];
            if (hours !== undefined) {
              updateJournalRowTotals($scope, journal, capacityType);
              updateSpJournalRows($scope, journal, capacityType);
              var grandTotals = $scope.formState.grandTotals;
              if (grandTotals[capacityType] === undefined) {
                grandTotals[capacityType] = createWeekTotalsRow($scope);
              }
            }
          });
          for (var i = 0, wnCnt = $scope.formState.weekNrs.length; i < wnCnt; i++) {
            updateJournalTotals($scope, journal, i);
          }
          updateSpJournalTotals($scope, journal);
        });

        function calculateHours(i, capacityType) {
          return result.journals.reduce(function (sum, journal) {
            var hours = journal.hours[capacityType];
            if (hours !== undefined) {
              sum += hours[i];
            }
            return sum;
          }, 0);
        }

        $scope.formState.capacityTypes.forEach(function (c) {
          var capacityType = c.code;
          var grandTotals = $scope.formState.grandTotals;
          if (grandTotals[capacityType] !== undefined) {
            for (var i = 0, wnCnt = $scope.formState.weekNrs.length; i < wnCnt; i++) {
              grandTotals[capacityType][i] = calculateHours(i, capacityType);
              updateTotals($scope, grandTotals, capacityType, i);
            }
          }
          initializeSpGrandTotals($scope, capacityType);
        });
        // subjects
        $scope.formState.subjectCapacityTypes = [];
        $scope.formState.studyPeriods.forEach(function (sp) {
          for (var i = 0; i < $scope.formState.capacityTypes.length; i++) {
            var capacity = $scope.formState.capacityTypes[i];
            $scope.formState.subjectCapacityTypes.push({
              studyPeriod: sp.id,
              code: capacity.code,
              value: capacity.value,
              endOfPeriod: i === $scope.formState.capacityTypes.length - 1
            });
          }
        });
        $scope.record = result;
      });
    }
  ]);
}());
