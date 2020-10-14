'use strict';

angular.module('hitsaOis').controller('studyYearScheduleController',
function ($scope, $route, QueryUtils, ArrayUtils, message, DataUtils, $window, dialogService, USER_ROLES, AuthService, config, $httpParamSerializer, $q) {
    $scope.auth = $route.current.locals.auth;
    $scope.canEdit = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPETOOGRAAFIK);
    $scope.schoolId = $route.current.params.schoolId;
    var promises = [];
    
    $scope.colorOptions = {
        disabled: true
    };
    $scope.useMyFilter = $scope.auth !== undefined && ($scope.auth.isLeadingTeacher() || $scope.auth.isStudent() || $scope.auth.isParent());

    $scope.criteria = {
        schoolDepartments: [],
        studyYear: null,
        studyPeriods: [],
        studentGroups: []
    };
    if ($scope.useMyFilter) {
        $scope.criteria.showMine = true;
    }

    $scope.formState = {
        xlsUrl: 'studyYearSchedule/studyYearSchedule.xlsx',
        pdfUrl: 'studyYearSchedule/studyYearSchedule.pdf'
    };

    $scope.weeks = [];
    $scope.studyYearSchedules = [];
    QueryUtils.loadingWheel($scope,true);
    var publicSchoolDepartmentEndpoint = QueryUtils.endpoint('/autocomplete/schooldepartments');
    if ($scope.schoolId) {
        publicSchoolDepartmentEndpoint = QueryUtils.endpoint('/public/studyYearSchedule/schooldepartments');
    }
    publicSchoolDepartmentEndpoint.query($scope.schoolId ? {schoolId: $scope.schoolId} : {}).$promise.then(function(response){
        $scope.schoolDepartments = response;
        if (ArrayUtils.isEmpty($scope.criteria.schoolDepartments)) {
            $scope.criteria.schoolDepartments = $scope.schoolDepartments.filter(function(dep){
                return dep.valid;
            }).map(function(dep){
                return dep.id;
            });
            QueryUtils.loadingWheel($scope,false);
        }
    });

    var publicStudyYearScheduleLegendEndpoint = QueryUtils.endpoint('/school/studyYearScheduleLegends');
    if ($scope.schoolId) {
        publicStudyYearScheduleLegendEndpoint = QueryUtils.endpoint('/public/studyYearScheduleLegends');
    }
    $scope.legends = publicStudyYearScheduleLegendEndpoint.search($scope.schoolId ? {schoolId: $scope.schoolId} : {});
    QueryUtils.loadingWheel($scope,true);
    $scope.legends.$promise.then(function (response) {
        $scope.legends = response.legends;
        QueryUtils.loadingWheel($scope,false);
    });

    function getStudentGroups() {
        var studentGroupEndpoint = QueryUtils.endpoint('/studyYearSchedule/studentGroups');
        if ($scope.schoolId) {
            studentGroupEndpoint = QueryUtils.endpoint('/public/studyYearSchedule/studentGroups');
        }
        $scope.studentGroups = studentGroupEndpoint.query($scope.schoolId ? {schoolId: $scope.schoolId} : {showMine: $scope.criteria.showMine});
        if ($scope.criteria.showMine) {
            QueryUtils.loadingWheel($scope,true);
            $scope.studentGroups.$promise.then(function(response){
                if (angular.isArray(response) && response.length > 0) {
                    $scope.criteria.schoolDepartments = response[0].schoolDepartments;
                }
                QueryUtils.loadingWheel($scope,false);
            });
        }
        $scope.criteria.studentGroups = [];
    }

    getStudentGroups();

    function selectCurrentStudyYear() {
        $scope.criteria.studyYear = DataUtils.getCurrentStudyYearOrPeriod($scope.studyYears);
        if ($scope.criteria.studyYear) {
            $scope.criteria.studyYear.studyPeriods = DataUtils.sortStudyYearsOrPeriods($scope.criteria.studyYear.studyPeriods);
        }
        $scope.yearSelectionTrigger();
    }
    var studyYearEndpoint = QueryUtils.endpoint('/studyYearSchedule/studyYears');
    if ($scope.schoolId) {
        studyYearEndpoint = QueryUtils.endpoint('/public/studyYears');
    }
    studyYearEndpoint.query($scope.schoolId ? {schoolId: $scope.schoolId} : {}).$promise.then(function(response) {
        $scope.studyYears = response;
        selectCurrentStudyYear();
    });

    $scope.schoolDepartmentsChanged = function() {
        if(!ArrayUtils.isEmpty($scope.studentGroups)){
            $scope.criteria.studentGroups = $scope.studentGroups
            .filter(function(sg){return ArrayUtils.intersect(sg.schoolDepartments, $scope.criteria.schoolDepartments);})
            .map(function(sg){return sg.id;});
            getSchedules();
        }
    };

    $scope.showMineChanged = function() {
        getStudentGroups();
        getSchedules();
    };

    $scope.exportUrl = function(url) {
        var params = {
            studyYearId: $scope.criteria.studyYear ? $scope.criteria.studyYear.id : null,
            schoolDepartments: $scope.criteria.schoolDepartments,
            showMine: $scope.criteria.showMine
        };
        return config.apiUrl + '/'+ url + '?' + $httpParamSerializer(params);
    };

    function getSchedules() {
        // because of many parameters this endpoint was changed from get to post method, it doesn't save anything
        var studyYearScheduleEndpoint = QueryUtils.endpoint('/studyYearSchedule');
        if ($scope.schoolId) {
            studyYearScheduleEndpoint = QueryUtils.endpoint('/public/studyYearSchedule/'+$scope.schoolId);
        }
        $scope.record = studyYearScheduleEndpoint.save($scope.criteria);
        QueryUtils.loadingWheel($scope,true);
        $scope.record.$promise.then(function(response){
            $scope.studyYearSchedules = response.studyYearSchedules;
            QueryUtils.loadingWheel($scope,false);
        });
    }

    function getWeeks() {
        var studyPeriodEndpoint = QueryUtils.endpoint('/studyYearSchedule/studyYearPeriods/' + $scope.criteria.studyYear.id);
        if ($scope.schoolId) {
            studyPeriodEndpoint = QueryUtils.endpoint('/public/studyYearSchedule/studyYearPeriods/' + $scope.criteria.studyYear.id);
        }
      $scope.studyPeriods = studyPeriodEndpoint.query();
      $scope.studyPeriods.$promise.then(function (response) {
        getStudyPeriodWeeks(response);
      });
    }

    function getStudyPeriodWeeks(studyPeriods) {
      var weeks = [];

      studyPeriods.forEach(function (studyPeriod) {
        for (var i = 0; i < studyPeriod.weekNrs.length; i++) {
          var week = {};
          week.weekNr = studyPeriod.weekNrs[i];
          week.studyPeriodId = studyPeriod.id;
          var start = moment(studyPeriod.weekBeginningDates[i], "YYYY-MM-DD'T'hh:mm:ss.SSS'Z'");
          week.start = start.toDate();
          var end = start.add(6, 'days');
          week.end = end.toDate();
          week.notInStudyPeriod = studyPeriod.externalWeeks.indexOf(week.weekNr) !== -1;
          if (studyPeriod.vacations != null) {
            studyPeriod.vacations.forEach(function(vacation) {
                var vacationStart = moment(vacation.start, "YYYY-MM-DD'T'hh:mm:ss.SSS'Z'").toDate();
                var vacationEnd = moment(vacation.end, "YYYY-MM-DD'T'hh:mm:ss.SSS'Z'").toDate();
                if (vacationStart <= week.end && (vacation.end === null || vacationEnd >= week.start)) {
                    week.hasVacation = true;
                }
              });
          }
          weeks.push(week);
        }
      });

      $scope.weeks = weeks;

      function getPeriodsLengthInWeeks(acc, val) {
        var num = val.studyPeriodId === $scope.criteria.studyYear.studyPeriods[i].id ? 1 : 0;
        return acc + num;
      }

      function getYearsLengthInWeeks(acc, val) {
        return acc + val.weeks;
      }

      // set number of weeks to study periods
      for (var i = 0; i < $scope.criteria.studyYear.studyPeriods.length; i++) {
        var weeksNumber = $scope.weeks.reduce(getPeriodsLengthInWeeks, 0);
        $scope.criteria.studyYear.studyPeriods[i].weeks = weeksNumber;
      }
      $scope.criteria.studyYear.weeks = $scope.criteria.studyYear.studyPeriods.reduce(getYearsLengthInWeeks, 0);
    }

    $scope.filterSchoolDepartments = function(dept) {
        return !ArrayUtils.isEmpty($scope.criteria.schoolDepartments) && ArrayUtils.includes($scope.criteria.schoolDepartments, dept.id);
    };

    $scope.filterStudentGroups = function(dept) {
        return function(sg) {
            return ArrayUtils.includes(sg.schoolDepartments, dept.id) && $scope.isValidGroup(sg);
        };
    };

    $scope.getLegendById = function(legendId) {
        if(angular.isArray($scope.legends)) {
            var id = $scope.legends.find(function(el){return el.id === legendId; });
            return id;
        }
    };

    $scope.isValidGroup = function (group) {
        if (!$scope.criteria.studyYear) {
            return true;
        }
        return DataUtils.isValidObject($scope.criteria.studyYear.startDate, $scope.criteria.studyYear.endDate, group.validFrom, group.validThru);
    };

    $scope.$watch(
        function () {
            return document.getElementsByClassName("lessonplan")[0].offsetHeight;
        },
        function () {
            var rowLength = document.getElementsByClassName("lessonplan")[0].offsetHeight + 17;
            var defaultHeight =  $window.innerHeight - 340;
            if (rowLength < defaultHeight) {
                angular.element(document.getElementsByClassName("container")[0]).css('height', rowLength + 'px');
            } else {
                angular.element(document.getElementsByClassName("container")[0]).css('height', defaultHeight + 'px');
            }
        }
    );

    angular.element($window).bind('resize', function(){
        var rowLength = document.getElementsByClassName("lessonplan")[0].offsetHeight + 17;
        var defaultHeight =  $window.innerHeight - 340;
        if (rowLength < defaultHeight) {
            angular.element(document.getElementsByClassName("container")[0]).css('height', rowLength + 'px');
        } else {
            angular.element(document.getElementsByClassName("container")[0]).css('height', defaultHeight + 'px');
        }
    });

    function getNumberOfWeeks (schedule) {
        if(!schedule.studyYearScheduleLegend) {
            return 1;
        }
        var sum = 0;
        var legend = schedule.studyYearScheduleLegend;
        while(schedule.studyYearScheduleLegend === legend) {
            sum++;
            schedule = $scope.getSchedule(schedule.studentGroup, schedule.weekNr + 1);
        }
        return sum;
    }

    $scope.getWeeksPeriod = function(weekNr) {
        return $scope.weeks.find(function(el){
            return el.weekNr === weekNr;
        }).studyPeriodId;
    };

    $scope.openAddScheduleDialog = function (studentGroup, week, schedule) {
        if(!week.studyPeriodId || !$scope.canEdit) {
            return;
        }
        var DialogController = function (scope) {
            scope.hasValue = schedule.studyYearScheduleLegend ? true : false;
            scope.studentGroup = studentGroup;
            scope.week = week;
            scope.legends = $scope.legends;
            scope.data = {
                weeks: getNumberOfWeeks(schedule),
                legend: schedule.studyYearScheduleLegend,
                addInfo: schedule.addInfo
            };
            scope.translationData = {weeks: $scope.weeks.length - week.weekNr + 1};
        };
        dialogService.showDialog('studyYearSchedule/study.year.schedule.add.dialog.html', DialogController,
            function (submitScope) {
                promises = [];
                if(!submitScope.data.legend) {
                    for(var j = 0; j < submitScope.data.weeks; j++) {
                        var deletedSchedule = $scope.getSchedule(studentGroup.id, week.weekNr + j);
                        //id is needed for deletion
                        if (deletedSchedule.id) {
                            deleteSchedule(deletedSchedule);
                        }
                    }
                } else {
                    for(var i = 0; i < submitScope.data.weeks; i++) {
                        if(week.weekNr + i > $scope.weeks.length) {
                            break;
                        }
                        var oldSchedule = $scope.getSchedule(studentGroup.id, week.weekNr + i);

                        var newSchedule = {
                            studyYearScheduleLegend: submitScope.data.legend,
                            weekNr: week.weekNr + i,
                            studentGroup: studentGroup.id,
                            studyPeriod: i === 0 ? week.studyPeriodId : $scope.getWeeksPeriod(week.weekNr + i),
                            addInfo: submitScope.data.addInfo
                        };

                        if (oldSchedule.studyYearScheduleLegend === null) {
                            createSchedule(newSchedule);
                        } else {
                            updateSchedule(oldSchedule, newSchedule);
                        }
                    }
                }
                $q.all(promises).then(getSchedules);
            });
    };

    $scope.getSchedule = function(studentGroupId, weekNr) {
        var schedule = $scope.studyYearSchedules.find(function(s){
            return s.weekNr === weekNr && s.studentGroup === studentGroupId;
        });
        if(!schedule) {
            schedule = {
                studyYearScheduleLegend: null,
                studentGroup: studentGroupId,
                weekNr: weekNr,
                addInfo: ""
            };
        }
        return schedule;
    };

    function deleteSchedule(schedule) {
        if ($scope.canEdit) {
            $scope.record.studyYearSchedule = schedule;
            // $update is used instead of $delete because of many extra params
            promises.push($scope.record.$update({id: schedule.id}));
        }
    }

    function updateSchedule(oldSchedule, newSchedule) {
        if ($scope.canEdit) {
            $scope.record.studyYearSchedule = newSchedule;
            var ScheduleEndPoint = QueryUtils.endpoint('/studyYearSchedule/schedule');
            var record = new ScheduleEndPoint($scope.record);
            promises.push(record.$update({id: oldSchedule.id}));
        }
    }

    function createSchedule(newSchedule) {
        if ($scope.canEdit) {
            $scope.record.studyYearSchedule = newSchedule;
            var ScheduleEndPoint = QueryUtils.endpoint('/studyYearSchedule/schedule');
            var record = new ScheduleEndPoint($scope.record);
            promises.push(record.$save());
        }
    }

    $scope.refreshHeight = function() {
        var rowLength = document.getElementsByClassName("lessonplan")[0].rows.length * 21;
        var defaultHeight =  $window.innerHeight - 340;
        if (rowLength < defaultHeight) {
        angular.element(document.getElementsByClassName("container")[0]).css('height', rowLength + 'px');
        } else {
        angular.element(document.getElementsByClassName("container")[0]).css('height', defaultHeight + 'px');
        }
    };

    $scope.yearSelectionTrigger = function() {
    if(!$scope.criteria.studyYear) {
        $scope.isPastStudyYear = true;
    } else {
        if ($scope.criteria.studyYear !== $scope.lastPickedStudyYear) {
            $scope.isPastStudyYear = DataUtils.isPastStudyYearOrPeriod($scope.criteria.studyYear);
            $scope.criteria.studyYear.studyPeriods = DataUtils.sortStudyYearsOrPeriods($scope.criteria.studyYear.studyPeriods);
            $scope.criteria.studyPeriods = $scope.criteria.studyYear.studyPeriods.map(function(p){return p.id;});
            getSchedules();
            getWeeks();
            $scope.lastPickedStudyYear = $scope.criteria.studyYear;
        }
        }
    };
});
