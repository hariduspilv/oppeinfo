'use strict';

angular.module('hitsaOis')
  .factory('LessonPlanTableService', function ($compile, $filter, $translate) {
    var lessonPlanTableService = {};

    var BASE_URL = '/lessonplans';
    var WHOLE_NUMBER_REGEX = /^\d+$/;
    var NEW_LINE_HEX = "\x0A";

    var SEPARATOR = '/';
    var MODULE_ID = 'module';
    var JOURNAL_ID = 'journal';
    var WEEK_HOUR_INPUT = 'weekHour';
    var WEEK_TOTAL_INPUT = 'weekTotal';
    var CAPACITY_TOTAL = 'capacityTotal';
    var JOURNAL_TOTAL = 'journalTotal';
    var MODULE_CAPACITY_TOTAL = 'moduleCapacityTotal';
    var MODULE_WEEK_HOUR = 'moduleWeekHour';
    var MODULE_TOTAL = 'moduleTotal';
    var MODULE_WEEK_TOTAL = 'moduleWeekTotal';
    var PERIOD_HOUR = 'periodHour';
    var PERIOD_TOTAL_INPUT = 'periodTotal';
    var MODULE_PERIOD_HOUR = 'modulePeriodHour';
    var MODULE_PERIOD_TOTAL = 'modulePeriodTotal';
    var GRAND_CAPACITY_PERIOD_TOTAL = 'grandPeriodTotal';
    var GRAND_CAPACITY_WEEK_TOTAL = 'grandWeekTotal';
    var GRAND_CAPACITY_TOTAL = 'grandCapacityTotal';
    var GRAND_PERIOD_TOTAL = 'grandPeriodTotal';
    var GRAND_WEEK_TOTAL = 'grandWeekTotal';
    var GRAND_TOTAL = 'grandTotal';

    // CLASSES
    var BUTTON = 'md-primary change-button';
    var CENTER = 'center';
    var CROSS = 'cross';
    var DIVIDER = 'dividerBorder';
    var FIX = 'fix';
    var TEACHER_STUDY_LOAD = 'lessonplan-teacher-load';
    var TEACHER_LOAD_LABEL = 'lessonplan-teacher-load-label';
    var TEACHER_CAPACITIES = 'material-icons pointer';
    var TOTAL_ROW = 'lessonplan-total-row';

    lessonPlanTableService.generateLessonPlan = function (scope) {
      var table = document.getElementById("lessonplan-table-body");
      table.innerHTML = '';

      //table.appendChild(tableHeader(scope));

      var journalDialogFunctions = [];
      for (var moduleIndex = 0; moduleIndex < scope.record.modules.length; moduleIndex++) {
        var module = scope.record.modules[moduleIndex];
        table.appendChild(moduleRow(scope, module, moduleIndex));
        table.appendChild(moduleHoursRow(scope, module));

        for (var journalIndex = 0; journalIndex < module.journals.length; journalIndex++) {
          var journal = module.journals[journalIndex];
          table.appendChild(journalRow(scope, journal));

          if (journal.teachers.length === 1) {
            table.appendChild(teachersStudyLoadRow(scope, journal.teachers[0].teacher.id));
          }

          var capacityTypes = getCapacityTypes(scope.formState.capacityTypes, journal.hours);
          for (var ctIndex = 0; ctIndex < capacityTypes.length; ctIndex++) {
            var capacityType = capacityTypes[ctIndex];
            var ctRow = document.createElement('tr');
            ctRow.className = FIX;

            if (ctIndex === 0) {
              var journalInfoColumn = document.createElement('td');
              journalInfoColumn.className = FIX;
              journalInfoColumn.rowSpan = Object.keys(capacityTypes).length + 1;

              var journalInfoColumnDiv = document.createElement('div');
              journalInfoColumnDiv.setAttribute('layout', 'row');
              journalInfoColumnDiv.setAttribute('layout-align', 'space-between stretch');

              var journalInfoDiv = document.createElement('div');
              var teachersGroupsSpan = document.createElement('span');
              for (var sgIndex = 0; sgIndex < journal.studentGroups.length; sgIndex++) {
                var studentGroupCode = journal.studentGroups[sgIndex];
                if (scope.record.studentGroupCode !== studentGroupCode) {
                  var studentGroupDiv = document.createElement('div');
                  studentGroupDiv.innerHTML = studentGroupCode;
                  teachersGroupsSpan.appendChild(studentGroupDiv);
                }
              }

              // One of the groups is lessonplan connected group and is not shown in info div
              if (journal.studentGroups.length > 1 && journal.teachers.length > 0) {
                teachersGroupsSpan.appendChild(document.createElement('br'));
              }

              for (var teacherIndex = 0; teacherIndex < journal.teachers.length; teacherIndex++) {
                var teacher = journal.teachers[teacherIndex];
                var teacherDiv = document.createElement('div');
                teacherDiv.title = $translate.instant('lessonplan.teacherLoad',
                  getTeacherLoad(scope.formState.studyPeriods, scope.formState.teachers, teacher.teacher.id));
                teacherDiv.innerHTML = '';
                teacherDiv.innerHTML = teacher.teacher.nameEt;
                teachersGroupsSpan.appendChild(teacherDiv);
              }
              journalInfoDiv.appendChild(teachersGroupsSpan);
              
              if (journal.groupProportion.code !== 'PAEVIK_GRUPI_JAOTUS_1') {
                journalInfoDiv.appendChild(document.createElement('br'));

                var groupProportionDiv = document.createElement('div');
                groupProportionDiv.className = 'layout-row';
                groupProportionDiv.innerHTML = $translate.instant('lessonplan.journal.groupProportion') + '&nbsp;' +
                  scope.currentLanguageNameField(journal.groupProportion);
                journalInfoDiv.appendChild(groupProportionDiv);
              }
              journalInfoColumnDiv.appendChild(journalInfoDiv);

              var journalButtonsDiv = document.createElement('div');
              journalButtonsDiv.setAttribute('layout', 'column');
              journalButtonsDiv.setAttribute('layout-align', 'space-around center');

              if (journal.teachers.length > 1 || journal.capacityDiff) {
                var journalTeacherCapacitySpan = document.createElement('i');
                journalTeacherCapacitySpan.className = TEACHER_CAPACITIES;
                journalTeacherCapacitySpan.innerHTML = '*';
                journalTeacherCapacitySpan.title = $translate.instant('lessonplan.teacherSpecificLoads');

                journalDialogFunctions[journal.id] = scope.teacherCapacities.bind(this, journal);
                journalTeacherCapacitySpan.addEventListener('click', journalDialogFunctions[journal.id]);

                journalButtonsDiv.appendChild(journalTeacherCapacitySpan);
              }

              var editJournalLink = document.createElement('a');
              editJournalLink.className = BUTTON;
              editJournalLink.innerHTML = $translate.instant('main.button.change');
              editJournalLink.href = editJournalUrl(journal);
              journalButtonsDiv.appendChild(editJournalLink);

              journalInfoColumnDiv.appendChild(journalButtonsDiv);
              journalInfoColumn.appendChild(journalInfoColumnDiv);
              ctRow.appendChild(journalInfoColumn);
            }

            ctRow.appendChild(capacityTypeValueColumn(capacityType));

            var capcityTotalId = CAPACITY_TOTAL + SEPARATOR + moduleIndex + SEPARATOR + journalIndex + SEPARATOR + capacityType.code;
            var capcityTotalValue = totalColumnValue(scope.formState.journalTotals[journal.id][capacityType.code]);
            var fontColor = journalCapacityTotalFontColor(scope, moduleIndex, journalIndex, capacityType.code);
            var ctJournalTotal = totalColumn(capcityTotalId, capcityTotalValue, fontColor);
            var journalRequiredLessons = journal.requiredLessons[capacityType.code] ? journal.requiredLessons[capacityType.code] : 0;
            ctJournalTotal.title = $translate.instant('lessonplan.planned') + " " + journalRequiredLessons;
            ctRow.appendChild(ctJournalTotal);

            if (!scope.atLeastOneShownPeriod) {
              ctRow.appendChild(noShownPeriodColumn());
            }

            if (scope.formState.showWeeks) {
              for (var weekIndex = 0; weekIndex < scope.formState.weekNrs.length; weekIndex++) {
                var week = scope.formState.weekNrs[weekIndex];
                if (week.show) {
                  var ctHourColumn = document.createElement('td');
                  ctHourColumn.classList.add(CENTER);
                  if (week.endOfPeriod) {
                    ctHourColumn.classList.add(DIVIDER);
                  }

                  var ctHourInput = document.createElement('input');
                  ctHourInput.setAttribute('type', 'number');
                  ctHourInput.setAttribute('name', WEEK_HOUR_INPUT + SEPARATOR + moduleIndex + SEPARATOR +
                    journalIndex + SEPARATOR + capacityType.code + SEPARATOR + weekIndex);
                  ctHourInput.setAttribute('value', journal.hours[capacityType.code][weekIndex]);
                  setWeekHourInputListener(scope, ctHourInput);

                  ctHourColumn.appendChild(ctHourInput);

                  ctRow.appendChild(ctHourColumn);
                }
              }
            } else {
              for (var spIndex = 0; spIndex < scope.formState.studyPeriods.length; spIndex++) {
                var studyPeriod = scope.formState.studyPeriods[spIndex];
                if (studyPeriod._selected) {
                  var spHourColumn = document.createElement('td');
                  spHourColumn.colSpan = studyPeriod.weekNrs.length;
                  spHourColumn.classList.add(CENTER);
                  spHourColumn.classList.add(DIVIDER);

                  var spHourInput = document.createElement('input');
                  spHourInput.setAttribute('type', 'number');
                  spHourInput.setAttribute('name', PERIOD_HOUR + SEPARATOR + moduleIndex + SEPARATOR +
                    journalIndex + SEPARATOR + capacityType.code + SEPARATOR + spIndex);
                  spHourInput.setAttribute('value', journal.spHours[capacityType.code][spIndex]);
                  setStudyPeriodHourInputListener(scope, spHourInput);
                  spHourColumn.appendChild(spHourInput);

                  ctRow.appendChild(spHourColumn);
                }
              }
            }

            table.appendChild(ctRow);
          }
          table.appendChild(journalTotalRow(scope, journal));
        }

        // module total rows
        if (scope.formState.showTotals) {
          addModuleTotalRows(scope, table, module, moduleIndex);
        }
      }

      addTotalRows(scope, table);
      table = $compile(table)(scope);
      scope.$broadcast('refreshFixedColumns');
    };

    /* HELPER FUNCTIONS */
    lessonPlanTableService.getUniqueJournalThemes = function (themes) {
      return getUniqueJournalThemes(themes);
    };

    lessonPlanTableService.getCapacityByCode = function (capacityTypes, capacityCode) {
      return getCapacityByCode(capacityTypes, capacityCode);
    };

    lessonPlanTableService.getLegendByWeek = function (scope, weekNr) {
      return getLegendByWeek(scope, weekNr);
    };

    lessonPlanTableService.getCapacityTypes = function (capacityTypes, hours) {
      return getCapacityTypes(capacityTypes, hours);
    };

    lessonPlanTableService.getTeacherStudyLoad = function (teachers, teacherId) {
      return getTeacherStudyLoad(teachers, teacherId);
    };

    lessonPlanTableService.getTeacherLoad = function (studyPeriods, teachers, teacherId) {
      return getTeacherLoad(studyPeriods, teachers, teacherId);
    };

    lessonPlanTableService.getPlannedLessonsTitle = function (teacherId, teachers, capacities) {
      var teacher = getTeacherStudyLoad(teachers, teacherId);
      return teacher ? plannedLessonsTitle(teacher, capacities) : '';
    };

    lessonPlanTableService.getStudyLoadTitle = function (teacherId, teachers, capacities, weekIndex) {
      var teacher = getTeacherStudyLoad(teachers, teacherId);
      return teacher ? studyLoadTitle(teacher, capacities, weekIndex) : '';
    };

    lessonPlanTableService.getStudyLoadSpTitle = function (teacherId, teachers, capacities, studyPeriodIndex) {
      var teacher = getTeacherStudyLoad(teachers, teacherId);
      return teacher ? studyLoadSpTitle(teacher, capacities, studyPeriodIndex) : '';
    };

    function getUniqueJournalThemes(themes) {
      return themes.filter(function (obj, index, themes) {
        return themes.map(function (mapObj) {
          return mapObj.id;
        }).indexOf(obj.id) === index;
      });
    }

    function getCapacityByCode(capacityTypes, capacityCode) {
      if (capacityTypes) {
        return capacityTypes.find(function (it) {
          return it.code === capacityCode;
        });
      }
    }

    function getLegendByWeek(scope, weekNr) {
      return scope.formState.legends[weekNr];
    }

    function getTeacherStudyLoad(teachers, teacherId) {
      return teachers.filter(function (teacher) {
        return teacher.id === teacherId;
      })[0];
    }

    function newJournalUrl(lessonPlanId, module) {
      return '/#' + BASE_URL + '/journals/new?lessonPlan=' + lessonPlanId +
        '&occupationModule=' + module.occupationModuleId + (module.id ? ('&lessonPlanModule=' + module.id) : '');
    }

    function editJournalUrl(journal) {
      return '/#' + BASE_URL + '/journals/' + journal.id + '/edit?lessonPlanModule=' + journal.lessonPlanModule;
    }

    function getCapacityTypes(capacityTypes, hours) {
      if (capacityTypes) {
        return capacityTypes.filter(function (ct) {
          return hours !== undefined && hours[ct.code] !== undefined;
        });
      }
    }

    function getTeacherLoad(studyPeriods, teachers, teacherId) {
      var teacher = teachers.find(function (teacher) {
        return teacher.id === teacherId;
      });

      if (teacher) {
        var scheduleLoad = teacher.isStudyPeriodScheduleLoad ? teacher.scheduleLoad * studyPeriods.length : teacher.scheduleLoad;
        return {
          scheduleLoad: scheduleLoad,
          unplannedLessons: scheduleLoad - teacher.plannedLessons > 0 ? scheduleLoad - teacher.plannedLessons : 0
        };
      }
      return null;
    }

    function totalColumnValue(value) {
      return value ? $filter('hoisNumber')(value, 1) : 0;
    }

    function capacityTypeValueColumn(capacityType) {
      var ctValueColumn = document.createElement('td');
      ctValueColumn.classList.add(CENTER);
      ctValueColumn.classList.add(FIX);
      ctValueColumn.innerHTML = capacityType.value.toUpperCase();
      return ctValueColumn;
    }

    function isPositiveWholeNumber(value) {
      return WHOLE_NUMBER_REGEX.test(value) && value > 0;
    }

    /* TABLE GENERATION FUNCTIONS */
    //only updates when scrolling, when study periods are changed, it disappears and reappears when table is scrolled
    function tableHeader(scope) {
      var header = document.createElement("thead");
      var studyPeriodsRow = document.createElement("tr");
      studyPeriodsRow.appendChild(moduleLengthColumn());

      if (!scope.atLeastOneShownPeriod) {
        studyPeriodsRow.appendChild(noShownPeriodColumn());
      }

      for (var spIndex = 0; spIndex < scope.formState.studyPeriods.length; spIndex++) {
        var studyPeriod = scope.formState.studyPeriods[spIndex];
        if (studyPeriod._selected) {
          var studyPeriodColumn = document.createElement("th");
          studyPeriodColumn.colSpan = studyPeriod.weekNrs.length;
          studyPeriodColumn.className = DIVIDER;
          studyPeriodColumn.innerHTML = scope.currentLanguageNameField(studyPeriod) + ' ' +
            $filter('hoisDayMonth')(studyPeriod.startDate, 1) + '-' + $filter('hoisDayMonth')(studyPeriod.endDate, 1);
          studyPeriodsRow.appendChild(studyPeriodColumn);
        }
      }
      header.appendChild(studyPeriodsRow);

      if (scope.formState.showWeeks) {
        var weeksRow = document.createElement("tr");
        weeksRow.appendChild(moduleLengthColumn());

        if (!scope.atLeastOneShownPeriod) {
          studyPeriodsRow.appendChild(noShownPeriodColumn());
        }

        for (var weekIndex = 0; weekIndex < scope.formState.weekNrs.length; weekIndex++) {
          var week = scope.formState.weekNrs[weekIndex];
          if (week.show) {
            var weekColumn = document.createElement("th");
            weekColumn.title = scope.formState.weekBeginningDates[weekIndex];
            weekColumn.innerHTML = week.nr;
            weekColumn.classList.add(CENTER);
            if (week.endOfPeriod) {
              weekColumn.classList.add(DIVIDER);
            }

            var legend = getLegendByWeek(scope, week.nr);
            if (legend) {
              weekColumn.style = legend;
            }
            weeksRow.appendChild(weekColumn);
          }
        }
        header.appendChild(weeksRow);
      }
      return header;
    }

    function moduleRow(scope, module, moduleIndex) {
      var row = document.createElement("tr");

      var moduleNameTd = document.createElement("td");
      moduleNameTd.id = MODULE_ID + module.occupationModuleId;
      moduleNameTd.colSpan = 3;
      moduleNameTd.className = DIVIDER;
      moduleNameTd.style = 'border-top:1px solid #aaa';
      var moduleSpan = document.createElement("span");
      moduleSpan.className = 'hois-collapse-header';
      moduleSpan.innerHTML = scope.currentLanguageNameField(module) + '&nbsp;' + '&nbsp;';
      if (scope.record.curriculumVersion.id !== module.curriculumVersionId) {
        moduleSpan.style = 'color: red';
      }
      moduleNameTd.appendChild(moduleSpan);

      var addModuleLink = document.createElement("a");
      addModuleLink.innerHTML = $translate.instant('lessonplan.addjournal');
      addModuleLink.href = newJournalUrl(scope.lessonPlanId, module);
      moduleNameTd.appendChild(addModuleLink);
      row.appendChild(moduleNameTd);

      var responsibleTeacherTd = document.createElement("td");
      responsibleTeacherTd.colSpan = scope.formState.shownWeeksCount;
      responsibleTeacherTd.className = DIVIDER;
      responsibleTeacherTd.style = 'border-top:1px solid #aaa; min-width: 300px;';
      var emptyDiv1 = document.createElement("div");
      emptyDiv1.className = 'layout-row';
      var emptyDiv2 = document.createElement("div");
      emptyDiv2.className = 'flex-10';
      emptyDiv2.innerHTML = '&nbsp;';

      emptyDiv1.appendChild(emptyDiv2);

      var responsibleTeacherDiv = document.createElement("div");
      responsibleTeacherDiv.className = 'flex-70';

      var responsibleTeacherAutocomplete = document.createElement("hois-autocomplete");
      responsibleTeacherAutocomplete.setAttribute('label', $translate.instant('lessonplan.responsibleformodule'));
      responsibleTeacherAutocomplete.setAttribute('ng-model', 'record.modules[' + moduleIndex + '].teacher');
      responsibleTeacherAutocomplete.setAttribute('method', 'teachers');
      var additionalQueryParams = 
        '{valid: true' + (scope.record.modules[moduleIndex].teacher ? ', selectedTeacherId:' + scope.record.modules[moduleIndex].teacher.id : '') + '}';
      responsibleTeacherAutocomplete.setAttribute('additional-query-params', additionalQueryParams);
      responsibleTeacherDiv.appendChild(responsibleTeacherAutocomplete);

      emptyDiv1.appendChild(responsibleTeacherDiv);
      responsibleTeacherTd.appendChild(emptyDiv1);
      row.appendChild(responsibleTeacherTd);

      return row;
    }

    function moduleHoursRow(scope, module) {
      var row = document.createElement("tr");
      var moduleHoursColumn = document.createElement("td");
      moduleHoursColumn.className = DIVIDER;
      moduleHoursColumn.colSpan = 3;
      moduleHoursColumn.innerHTML = $translate.instant('lessonplan.modulehours') + ': ' + module.totalHours;
      row.appendChild(moduleHoursColumn);

      var shownWeeksColumn = document.createElement("td");
      shownWeeksColumn.className = DIVIDER;
      shownWeeksColumn.colSpan = scope.formState.shownWeeksCount;
      row.appendChild(shownWeeksColumn);
      return row;
    }

    function journalRow(scope, journal) {
      var row = document.createElement("tr");

      var journalNameColumn = document.createElement("td");
      journalNameColumn.id = JOURNAL_ID + journal.id;
      journalNameColumn.className = DIVIDER;
      journalNameColumn.colSpan = 3;
      journalNameColumn.style = 'font-weight: bold;';
      journalNameColumn.innerHTML = journal.nameEt;
      row.appendChild(journalNameColumn);

      var journalThemesColumn = document.createElement("td");
      journalThemesColumn.className = DIVIDER;
      journalThemesColumn.colSpan = scope.formState.shownWeeksCount;

      var themes = getUniqueJournalThemes(journal.themes);
      for (var themeIndex = 0; themeIndex < themes.length; themeIndex++) {
        var theme = themes[themeIndex];
        var themeInnerHTML = '';

        if (themeIndex > 0) {
          themeInnerHTML += ', &nbsp;';
        }

        var capacities = '';
        for (var capacity in theme.hours) {
          if (theme.hours.hasOwnProperty(capacity)) {
            var type = getCapacityByCode(scope.formState.capacityTypes, capacity);
            if (capacities !== '' && type) {
              capacities += '/';
            }
            if (type) {
              capacities += type.value.toUpperCase() + theme.hours[capacity];
            }
          }
        }

        var themeSpan = document.createElement("span");
        themeInnerHTML += theme.nameEt + ' ' + theme.credits + ' ' + $translate.instant('lessonplan.credits') + ' (' + capacities + ')';
        themeInnerHTML += '&nbsp;';

        themeSpan.innerHTML = themeInnerHTML;
        journalThemesColumn.appendChild(themeSpan);
      }
      row.appendChild(journalThemesColumn);
      return row;
    }

    function teachersStudyLoadRow(scope, teacherId) {
      var teacher = getTeacherStudyLoad(scope.record.teachers, teacherId);
      var teacherCapacities = getCapacityTypes(scope.formState.capacityTypes, teacher.plannedLessonsByCapacity);
      var studyLoadRow = document.createElement('tr');
      studyLoadRow.appendChild(document.createElement('td'));

      var loadLabelColumn = document.createElement('td');
      loadLabelColumn.colSpan = 2;
      loadLabelColumn.classList.add(TEACHER_LOAD_LABEL);
      loadLabelColumn.classList.add(FIX);
      loadLabelColumn.classList.add(DIVIDER);
      loadLabelColumn.innerHTML = $translate.instant('lessonplan.load') + ' ' + totalColumnValue(teacher.plannedLessons);
      loadLabelColumn.title = plannedLessonsTitle(teacher, teacherCapacities);
      studyLoadRow.appendChild(loadLabelColumn);

      if (scope.formState.showWeeks) {
        for (var weekIndex = 0; weekIndex < scope.formState.weekNrs.length; weekIndex++) {
          var week = scope.formState.weekNrs[weekIndex];
          if (week.show) {
            var loadByWeek = teacher.studyLoadByWeek ? teacher.studyLoadByWeek[weekIndex] : null;
            var wTotalColumn = weekTotalColumn(null, loadByWeek, week);
            wTotalColumn.classList.add(TEACHER_STUDY_LOAD);
            wTotalColumn.title = studyLoadTitle(teacher, teacherCapacities, weekIndex);
            studyLoadRow.appendChild(wTotalColumn);
          }
        }
      } else {
        for (var spIndex = 0; spIndex < scope.formState.studyPeriods.length; spIndex++) {
          var studyPeriod = scope.formState.studyPeriods[spIndex];
          if (studyPeriod._selected) {
            var loadByPeriod = teacher.studyLoadByPeriod ? teacher.studyLoadByPeriod[spIndex] : null;
            var spTotalColumn = studyPeriodTotalColumn(null, loadByPeriod, studyPeriod);
            spTotalColumn.classList.add(TEACHER_STUDY_LOAD);
            spTotalColumn.title = studyLoadSpTitle(teacher, teacherCapacities, spIndex);
            studyLoadRow.appendChild(spTotalColumn);
          }
        }
      }
      return studyLoadRow;
    }

    function addModuleTotalRows(scope, table, module, moduleIndex) {
      // capacity totals
      var capacityTypes = getCapacityTypes(scope.formState.capacityTypes, scope.formState.moduleTotals[module.id]);
      for (var ctIndex = 0; ctIndex < capacityTypes.length; ctIndex++) {
        var capacityType = capacityTypes[ctIndex];

        var moduleCtTotalRow = document.createElement('tr');
        moduleCtTotalRow.className = FIX;

        if (ctIndex === 0) {
          var moduleNameColumn = document.createElement('td');
          moduleNameColumn.className = FIX;
          moduleNameColumn.rowSpan = Object.keys(capacityTypes).length + 1;
          moduleNameColumn.innerHTML = scope.currentLanguageNameField(module) + ' ' + $translate.instant('lessonplan.moduleTotal');
          moduleCtTotalRow.appendChild(moduleNameColumn);
        }

        moduleCtTotalRow.appendChild(capacityTypeValueColumn(capacityType));

        var capacityTotalId = MODULE_CAPACITY_TOTAL + SEPARATOR + moduleIndex + SEPARATOR + capacityType.code;
        var capacityTotalValue = totalColumnValue(scope.formState.moduleTotals[module.id]._[capacityType.code]);
        moduleCtTotalRow.appendChild(totalColumn(capacityTotalId, capacityTotalValue));

        if (!scope.atLeastOneShownPeriod) {
          moduleCtTotalRow.appendChild(noShownPeriodColumn());
        }

        if (scope.formState.showWeeks) {
          for (var weekIndex = 0; weekIndex < scope.formState.weekNrs.length; weekIndex++) {
            var week = scope.formState.weekNrs[weekIndex];
            if (week.show) {
              var moduleWeekId = MODULE_WEEK_HOUR + SEPARATOR + moduleIndex + SEPARATOR + 
                capacityType.code + SEPARATOR + weekIndex;
              var moduleWeekValue = totalColumnValue(scope.formState.moduleTotals[module.id][capacityType.code][weekIndex]);
              moduleCtTotalRow.appendChild(weekTotalColumn(moduleWeekId, moduleWeekValue, week));
            }
          }
        } else {
          for (var spIndex = 0; spIndex < scope.formState.studyPeriods.length; spIndex++) {
            var studyPeriod = scope.formState.studyPeriods[spIndex];
            if (studyPeriod._selected) {
              var modulePeriodId = MODULE_PERIOD_HOUR + SEPARATOR + moduleIndex + SEPARATOR + capacityType.code + SEPARATOR + spIndex;
              var modulePeriodValue = totalColumnValue(scope.formState.spModuleTotals[module.id][capacityType.code][spIndex]);
              moduleCtTotalRow.appendChild(studyPeriodTotalColumn(modulePeriodId, modulePeriodValue, studyPeriod));
            }
          }
        }
        table.appendChild(moduleCtTotalRow);
      }
      // total row
      table.appendChild(moduleTotalRow(scope, module, moduleIndex, capacityTypes));
    }

    function moduleTotalRow(scope, module, moduleIndex, capacityTypes) {
      var row = document.createElement('tr');
      row.className = FIX;

      if (capacityTypes.length === 0) {
        var noCapacitiesColumn = document.createElement('td');
        noCapacitiesColumn.innerHTML = '&nbsp';
        noCapacitiesColumn.classList.add(FIX);
        noCapacitiesColumn.classList.add(CENTER);
        row.appendChild(noCapacitiesColumn);
      }

      var moduleTotalStringColumn = document.createElement('td');
      moduleTotalStringColumn.className = FIX;
      moduleTotalStringColumn.innerHTML = $translate.instant('lessonplan.total');
      row.appendChild(moduleTotalStringColumn);

      var moduleTotalId = MODULE_TOTAL + SEPARATOR + moduleIndex;
      var moduleTotalValue = totalColumnValue(scope.formState.moduleTotals[module.id].__);
      row.appendChild(totalColumn(moduleTotalId, moduleTotalValue));

      if (!scope.atLeastOneShownPeriod) {
        row.appendChild(noShownPeriodColumn());
      }

      if (scope.formState.showWeeks) {
        for (var weekIndex = 0; weekIndex < scope.formState.weekNrs.length; weekIndex++) {
          var week = scope.formState.weekNrs[weekIndex];
          if (week.show) {
            var moduleWeekId = MODULE_WEEK_TOTAL + SEPARATOR + moduleIndex + SEPARATOR + SEPARATOR + weekIndex;
            var moduleWeekValue = totalColumnValue(scope.formState.moduleTotals[module.id]._._[weekIndex]);
            row.appendChild(weekTotalColumn(moduleWeekId, moduleWeekValue, week));
          }
        }
      } else {
        for (var spIndex = 0; spIndex < scope.formState.studyPeriods.length; spIndex++) {
          var studyPeriod = scope.formState.studyPeriods[spIndex];
          if (studyPeriod._selected) {
            var modulePeriodId = MODULE_PERIOD_TOTAL + SEPARATOR + moduleIndex + SEPARATOR + spIndex;
            var modulePeriodValue = totalColumnValue(scope.formState.spModuleTotals[module.id]._._[spIndex]);
            row.appendChild(studyPeriodTotalColumn(modulePeriodId, modulePeriodValue, studyPeriod));
          }
        }
      }
      return row;
    }

    function addTotalRows(scope, table) {
      // capacity totals
      var totalCapacityTypes = getCapacityTypes(scope.formState.capacityTypes, scope.formState.grandTotals);
      for (var ctIndex = 0; ctIndex < totalCapacityTypes.length; ctIndex++) {
        var capacityType = totalCapacityTypes[ctIndex];

        var totalCtRow = document.createElement('tr');
        totalCtRow.classList.add(FIX);
        totalCtRow.classList.add(TOTAL_ROW);

        if (ctIndex === 0) {
          var totalsStringColumn = document.createElement('td');
          totalsStringColumn.rowSpan = Object.keys(totalCapacityTypes).length + 1;
          totalsStringColumn.className = FIX;
          totalsStringColumn.innerHTML = $translate.instant('lessonplan.grandTotal');
          totalCtRow.appendChild(totalsStringColumn);
        }

        totalCtRow.appendChild(capacityTypeValueColumn(capacityType));

        var id = GRAND_CAPACITY_TOTAL + SEPARATOR + capacityType.code;
        var value = totalColumnValue(scope.formState.grandTotals._[capacityType.code]);
        totalCtRow.appendChild(totalColumn(id, value));

        if (!scope.atLeastOneShownPeriod) {
          totalCtRow.appendChild(noShownPeriodColumn());
        }

        if (scope.formState.showWeeks) {
          for (var weekIndex = 0; weekIndex < scope.formState.weekNrs.length; weekIndex++) {
            var week = scope.formState.weekNrs[weekIndex];
            if (week.show) {
              var grandWeekId = GRAND_CAPACITY_WEEK_TOTAL + SEPARATOR + capacityType.code + SEPARATOR + weekIndex;
              var grandWeekValue = totalColumnValue(scope.formState.grandTotals[capacityType.code][weekIndex]);
              totalCtRow.appendChild(weekTotalColumn(grandWeekId, grandWeekValue, week));
            }
          }
        } else {
          for (var spIndex = 0; spIndex < scope.formState.studyPeriods.length; spIndex++) {
            var studyPeriod = scope.formState.studyPeriods[spIndex];
            if (studyPeriod._selected) {
              var grandPeridId = GRAND_CAPACITY_PERIOD_TOTAL + SEPARATOR + capacityType.code + SEPARATOR + spIndex;
              var grandPeridValue = totalColumnValue(scope.formState.capGrandTotalsSp[capacityType.code][spIndex]);
              totalCtRow.appendChild(studyPeriodTotalColumn(grandPeridId, grandPeridValue, studyPeriod));
            }
          }
        }
        table.appendChild(totalCtRow);
      }
      // grand total row
      table.appendChild(grandTotalRow(scope));
    }

    function grandTotalRow(scope) {
      var row = document.createElement('tr');
      row.classList.add(FIX);
      row.classList.add(TOTAL_ROW);

      var totalsStringColumn = document.createElement('td');
      totalsStringColumn.className = FIX;
      totalsStringColumn.innerHTML = $translate.instant('lessonplan.total');
      row.appendChild(totalsStringColumn);

      var grandTotalId = GRAND_TOTAL;
      var grandTotalValue = totalColumnValue(scope.formState.grandTotals.__);
      row.appendChild(totalColumn(grandTotalId, grandTotalValue));

      if (!scope.atLeastOneShownPeriod) {
        row.appendChild(noShownPeriodColumn());
      }

      if (scope.formState.showWeeks) {
        for (var weekIndex = 0; weekIndex < scope.formState.weekNrs.length; weekIndex++) {
          var week = scope.formState.weekNrs[weekIndex];
          if (week.show) {
            var grandWeekId = GRAND_WEEK_TOTAL + SEPARATOR + weekIndex;
            var grandWeekValue = totalColumnValue(scope.formState.grandTotals._._[weekIndex]);
            row.appendChild(weekTotalColumn(grandWeekId, grandWeekValue, week));
          }
        }
      } else {
        for (var spIndex = 0; spIndex < scope.formState.studyPeriods.length; spIndex++) {
          var studyPeriod = scope.formState.studyPeriods[spIndex];
          if (studyPeriod._selected) {
            var grandPeriodId = GRAND_PERIOD_TOTAL + SEPARATOR + spIndex;
            var grandPeriodValue = totalColumnValue(scope.formState.grandTotalsSp[spIndex]);
            row.appendChild(studyPeriodTotalColumn(grandPeriodId, grandPeriodValue, studyPeriod));
          }
        }
      }
      return row;
    }

    function moduleLengthColumn() {
      var column = document.createElement("th");
      column.colSpan = 3;
      column.classList.add(CROSS);
      column.classList.add(DIVIDER);
      return column;
    }

    function journalCapacityTotalFontColor(scope, moduleIndex, journalIndex, capacityCode) {
      var journal = scope.record.modules[moduleIndex].journals[journalIndex];
      var requiredLessons = journal.requiredLessons[capacityCode] ? journal.requiredLessons[capacityCode] : 0;
      return scope.formState.journalTotals[journal.id][capacityCode] !== requiredLessons ? 'red' : 'inherit';
    }

    function setWeekHourInputListener(scope, input) {
      input.addEventListener('input', function () {
        var keys = this.name.split(SEPARATOR);
        var moduleIndex = keys[1],
          journalIndex = keys[2],
          capacityCode = keys[3],
          weekIndex = keys[4];
        var module = scope.record.modules[moduleIndex];
        var journal = module.journals[journalIndex];

        var isValidInput = this.value === '' || isPositiveWholeNumber(this.value);
        input.style = isValidInput ? null : 'border: 1px solid rgb(221,44,0);';
        input.classList.add(CENTER); 
        journal.hours[capacityCode][weekIndex] = isValidInput ? this.value : null;
        scope.updateTotals(journal, capacityCode, weekIndex);

        journal.hours[capacityCode][weekIndex] = this.value;
        scope.updateTotals(journal, capacityCode, weekIndex);

        var weekTotal = document.getElementById(WEEK_TOTAL_INPUT + SEPARATOR + journal.id + SEPARATOR + weekIndex);
        weekTotal.innerHTML = totalColumnValue(scope.formState.journalTotals[journal.id]._[weekIndex]);

        if (scope.formState.showTotals) {
          var moduleCapacityWeekTotal = document.getElementById(MODULE_WEEK_HOUR + SEPARATOR + moduleIndex + SEPARATOR + capacityCode + SEPARATOR + weekIndex);
          moduleCapacityWeekTotal.innerHTML = totalColumnValue(scope.formState.moduleTotals[module.id][capacityCode][weekIndex]);

          var moduleWeekTotal = document.getElementById(MODULE_WEEK_TOTAL + SEPARATOR + moduleIndex + SEPARATOR + SEPARATOR + weekIndex);
          moduleWeekTotal.innerHTML = totalColumnValue(scope.formState.moduleTotals[module.id]._._[weekIndex]);

        }

        var grandCapacityWeekTotal = document.getElementById(GRAND_CAPACITY_WEEK_TOTAL + SEPARATOR + capacityCode + SEPARATOR + weekIndex);
        grandCapacityWeekTotal.innerHTML = totalColumnValue(scope.formState.grandTotals[capacityCode][weekIndex]);

        var grandWeekTotal = document.getElementById(GRAND_WEEK_TOTAL + SEPARATOR + weekIndex);
        grandWeekTotal.innerHTML = totalColumnValue(scope.formState.grandTotals._._[weekIndex]);

        changeTotalsIrrespectiveOfChosenPeriod(scope, module, journal, moduleIndex, journalIndex, capacityCode);
      });
      input.addEventListener('mousewheel', function(event) {
        event.preventDefault(); 
      });
    }

    function setStudyPeriodHourInputListener(scope, input) {
      input.addEventListener('input', function () {
        var keys = this.name.split(SEPARATOR);
        var moduleIndex = keys[1],
          journalIndex = keys[2],
          capacityCode = keys[3],
          spIndex = keys[4];
        var module = scope.record.modules[moduleIndex];
        var journal = module.journals[journalIndex];

        var isValidInput = this.value === '' || isPositiveWholeNumber(this.value);
        journal.spHours[capacityCode][spIndex] = isValidInput ? this.value : null;
        input.style = isValidInput ? null : 'border: 1px solid rgb(221,44,0);';
        scope.updateLessonCountByStudyPeriod(journal, capacityCode, spIndex);

        var studyPeriodTotal = document.getElementById(PERIOD_TOTAL_INPUT + SEPARATOR + journal.id + SEPARATOR + spIndex);
        studyPeriodTotal.innerHTML = totalColumnValue(scope.formState.journalTotals[journal.id]._spTotals[spIndex]);

        if (scope.formState.showTotals) {
          var moduleCapacityStudyPeriodTotal =
            document.getElementById(MODULE_PERIOD_HOUR + SEPARATOR + moduleIndex + SEPARATOR + capacityCode + SEPARATOR + spIndex);
          moduleCapacityStudyPeriodTotal.innerHTML = totalColumnValue(scope.formState.spModuleTotals[module.id][capacityCode][spIndex]);

          var moduleStudyPeriodTotal = document.getElementById(MODULE_PERIOD_TOTAL + SEPARATOR + moduleIndex + SEPARATOR + spIndex);
          moduleStudyPeriodTotal.innerHTML = totalColumnValue(scope.formState.spModuleTotals[module.id]._._[spIndex]);
        }

        var grandCapacityPeriodTotal = document.getElementById(GRAND_CAPACITY_PERIOD_TOTAL + SEPARATOR + capacityCode + SEPARATOR + spIndex);
        grandCapacityPeriodTotal.innerHTML = totalColumnValue(scope.formState.capGrandTotalsSp[capacityCode][spIndex]);

        var grandPeriodTotal = document.getElementById(GRAND_PERIOD_TOTAL + SEPARATOR + spIndex);
        grandPeriodTotal.innerHTML = totalColumnValue(scope.formState.grandTotalsSp[spIndex]);

        changeTotalsIrrespectiveOfChosenPeriod(scope, module, journal, moduleIndex, journalIndex, capacityCode);
      });
      input.addEventListener('mousewheel', function(event) {
        event.preventDefault(); 
      });
    }

    function changeTotalsIrrespectiveOfChosenPeriod(scope, module, journal, moduleIndex, journalIndex, capacityCode) {
      var journalCapacityTotal =
        document.getElementById(CAPACITY_TOTAL + SEPARATOR + moduleIndex + SEPARATOR + journalIndex + SEPARATOR + capacityCode);
      journalCapacityTotal.style.color = journalCapacityTotalFontColor(scope, moduleIndex, journalIndex, capacityCode);
      journalCapacityTotal.innerHTML = totalColumnValue(scope.formState.journalTotals[journal.id][capacityCode]);

      var journalTotal = document.getElementById(JOURNAL_TOTAL + SEPARATOR + journal.id);
      journalTotal.innerHTML = totalColumnValue(scope.formState.journalTotals[journal.id].__);

      if (scope.formState.showTotals) {
        var moduleCapacityTotal = document.getElementById(MODULE_CAPACITY_TOTAL + SEPARATOR + moduleIndex + SEPARATOR + capacityCode);
        moduleCapacityTotal.innerHTML = totalColumnValue(scope.formState.moduleTotals[module.id]._[capacityCode]);

        var moduleTotal = document.getElementById(MODULE_TOTAL + SEPARATOR + moduleIndex);
        moduleTotal.innerHTML = totalColumnValue(scope.formState.moduleTotals[module.id].__);
      }

      var grandCapacityTotal = document.getElementById(GRAND_CAPACITY_TOTAL + SEPARATOR + capacityCode);
      grandCapacityTotal.innerHTML = totalColumnValue(scope.formState.grandTotals._[capacityCode]);

      var grandTotal = document.getElementById(GRAND_TOTAL);
      grandTotal.innerHTML = totalColumnValue(scope.formState.grandTotals.__);
    }

    function journalTotalRow(scope, journal) {
      var row = document.createElement('tr');
      row.className = FIX;

      var titleColumn = document.createElement('td');
      titleColumn.className = FIX;
      titleColumn.innerHTML = $translate.instant('lessonplan.total');
      row.appendChild(titleColumn);

      var journalTotalId = JOURNAL_TOTAL + SEPARATOR + journal.id;
      var journalTotalValue = totalColumnValue(scope.formState.journalTotals[journal.id].__);
      row.appendChild(totalColumn(journalTotalId, journalTotalValue));

      if (!scope.atLeastOneShownPeriod) {
        row.appendChild(noShownPeriodColumn());
      }

      if (scope.formState.showWeeks) {
        for (var weekIndex = 0; weekIndex < scope.formState.weekNrs.length; weekIndex++) {
          var week = scope.formState.weekNrs[weekIndex];
          if (week.show) {
            var weekTotalId = WEEK_TOTAL_INPUT + SEPARATOR + journal.id + SEPARATOR + weekIndex;
            var weekTotalValue = totalColumnValue(scope.formState.journalTotals[journal.id]._[weekIndex]);
            row.appendChild(weekTotalColumn(weekTotalId, weekTotalValue, week));
          }
        }
      } else {
        for (var spIndex = 0; spIndex < scope.formState.studyPeriods.length; spIndex++) {
          var studyPeriod = scope.formState.studyPeriods[spIndex];
          if (studyPeriod._selected) {
            var periodTotalId = PERIOD_TOTAL_INPUT + SEPARATOR + journal.id + SEPARATOR + spIndex;
            var periodTotalValue = totalColumnValue(scope.formState.journalTotals[journal.id]._spTotals[spIndex]);
            row.appendChild(studyPeriodTotalColumn(periodTotalId, periodTotalValue, studyPeriod));
          }
        }
      }
      return row;
    }

    function totalColumn(id, value, fontColor) {
      var column = document.createElement('td');
      column.id = id;
      column.innerHTML = angular.isDefined(value) ? value : '';
      column.classList.add(CENTER);
      column.classList.add(DIVIDER);
      column.classList.add(FIX);
      column.style.color = fontColor;
      return column;
    }

    function weekTotalColumn(id, value, week, fontColor) {
      var totalHourColumn = document.createElement('td');
      totalHourColumn.id = id;
      totalHourColumn.innerHTML = angular.isDefined(value) ? value : '';
      totalHourColumn.classList.add(CENTER);
      if (week.endOfPeriod) {
        totalHourColumn.classList.add(DIVIDER);
      }
      totalHourColumn.style.color = fontColor;
      return totalHourColumn;
    }

    function studyPeriodTotalColumn(id, value, studyPeriod, fontColor) {
      var totalHourColumn = document.createElement('td');
      totalHourColumn.id = id;
      totalHourColumn.innerHTML = angular.isDefined(value) ? value : '';
      totalHourColumn.colSpan = studyPeriod.weekNrs.length;
      totalHourColumn.classList.add(CENTER);
      totalHourColumn.classList.add(DIVIDER);
      totalHourColumn.style.color = fontColor;
      return totalHourColumn;
    }

    function noShownPeriodColumn() {
      var column = document.createElement('td');
      column.colSpan = 1;
      column.classList.add(DIVIDER);
      return column;
    }

    function plannedLessonsTitle(teacher, capacityTypes) {
      var title = "";
      for (var ctIndex = 0; ctIndex < capacityTypes.length; ctIndex++) {
        var capacityType = capacityTypes[ctIndex];
        if (teacher.plannedLessonsByCapacity && teacher.plannedLessonsByCapacity[capacityType.code]) {
          title += capacityType.value.toUpperCase() + ": " + teacher.plannedLessonsByCapacity[capacityType.code] + NEW_LINE_HEX;
        }
      }
      return title;
    }

    function studyLoadTitle(teacher, capacityTypes, weekIndex) {
      var title = "";
      for (var ctIndex = 0; ctIndex < capacityTypes.length; ctIndex++) {
        var capacityType = capacityTypes[ctIndex];
        if (teacher.studyLoadByWeekAndCapacity && teacher.studyLoadByWeekAndCapacity[capacityType.code] &&
          teacher.studyLoadByWeekAndCapacity[capacityType.code][weekIndex]) {
          title += capacityType.value.toUpperCase() + ": " +
            teacher.studyLoadByWeekAndCapacity[capacityType.code][weekIndex] + NEW_LINE_HEX;
        }
      }
      return title;
    }

    function studyLoadSpTitle(teacher, capacityTypes, spIndex) {
      var title = "";
      for (var ctIndex = 0; ctIndex < capacityTypes.length; ctIndex++) {
        var capacityType = capacityTypes[ctIndex];
        if (teacher.studyLoadByPeriodAndCapacity && teacher.studyLoadByPeriodAndCapacity[capacityType.code] &&
          teacher.studyLoadByPeriodAndCapacity[capacityType.code][spIndex]) {
          title += capacityType.value.toUpperCase() + ": " +
            teacher.studyLoadByPeriodAndCapacity[capacityType.code][spIndex] + NEW_LINE_HEX;
        }
      }
      return title;
    }

    return lessonPlanTableService;
  });
