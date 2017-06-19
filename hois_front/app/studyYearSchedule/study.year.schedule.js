'use strict';

angular.module('hitsaOis').controller('studyYearScheduleController', ['$scope', 'QueryUtils', 'ArrayUtils', 'message', 'DataUtils', '$mdDialog', 'dialogService', function ($scope, QueryUtils, ArrayUtils, message, DataUtils, $mdDialog, dialogService) {

    $scope.criteria = {
        schoolDepartments: [],
        studyYear: null,
        studyPeriods: [], 
        studentGroups: []
    };

    $scope.weeks = [];
    $scope.studyYearSchedules = [];

    QueryUtils.endpoint('/school/studyYearScheduleLegends').search().$promise.then(function(response){
        $scope.legends = response.legends;
    });

    QueryUtils.endpoint('/autocomplete/schooldepartments').query().$promise.then(function(response){
        $scope.schoolDepartments = response;
        if(ArrayUtils.isEmpty($scope.criteria.schoolDepartments)) {
            $scope.criteria.schoolDepartments = $scope.schoolDepartments.map(function(el){
                return el.id;
            });
        }
    });

    QueryUtils.endpoint('/studyYearSchedule/studentGroups').query().$promise.then(function(response){
        $scope.studentGroups = response;
    });

    function selectCurrentStudyYear() {
        $scope.criteria.studyYear = DataUtils.getCurrentStudyYearOrPeriod($scope.studyYears);
        DataUtils.sortStudyYearsOrPeriods($scope.criteria.studyYear.studyPeriods);
    }

    QueryUtils.endpoint('/studyYearSchedule/studyYears').query().$promise.then(function(response){
        $scope.studyYears = response;
    }).then(selectCurrentStudyYear);

    $scope.$watch('criteria.studyYear', function() {
            if($scope.criteria.studyYear) {
                $scope.criteria.studyPeriods = $scope.criteria.studyYear.studyPeriods.map(function(p){return p.id;});
                getSchedules();
                getWeeks();
            }
        }
    );

    $scope.$watch('criteria.schoolDepartments', function() {
            if(!ArrayUtils.isEmpty($scope.studentGroups)){
                $scope.criteria.studentGroups = $scope.studentGroups
                .filter(function(sg){return ArrayUtils.intersect(sg.schoolDepartments, $scope.criteria.schoolDepartments);})
                .map(function(sg){return sg.id;});
                getSchedules();
            }
        }
    );

    function getSchedules() {
        $scope.record = QueryUtils.endpoint('/studyYearSchedule').search($scope.criteria);
        $scope.record.$promise.then(function(response){
            $scope.studyYearSchedules = response.studyYearSchedules;
        });
    }

    function getWeeks() {

        var weeks = [];
        var weekNr = 1;
        var start = new Date($scope.criteria.studyYear.startDate);
        var end = $scope.criteria.studyYear.endDate;

        while(start < end) {
            var week = {};
            week.start = start;
            start = new Date(start.getFullYear() ,start.getMonth() ,start.getDate() + (7 - start.getDay()));
            week.end = start;
            week.weekNr = weekNr++;

            var studyPeriod = getWeeksPeriod(week);
            week.studyPeriodId = studyPeriod ? studyPeriod.id : null;

            weeks.push(week);
            start = new Date(start.getFullYear() ,start.getMonth() ,start.getDate() + 1);
        }
        $scope.weeks = weeks;

        function getPeriodsLengthInWeeks (acc, val) {
            var num = val.studyPeriodId === $scope.criteria.studyYear.studyPeriods[i].id ? 1 : 0;
            return acc + num;
        }
        // set number of weeks to study periods
        for(var i = 0; i < $scope.criteria.studyYear.studyPeriods.length; i++) {
            var weeksNumber = $scope.weeks.reduce(getPeriodsLengthInWeeks, 0);
            $scope.criteria.studyYear.studyPeriods[i].weeks = weeksNumber;
        }
    }

    function getWeeksPeriod(week) {
        return DataUtils.getStudyYearOrPeriodAt(week.start, $scope.criteria.studyYear.studyPeriods);
    }

    $scope.filterSchoolDepartments = function(dept) {
        return !ArrayUtils.isEmpty($scope.criteria.schoolDepartments) && ArrayUtils.includes($scope.criteria.schoolDepartments, dept.id);
    };

    $scope.filterStudentGroups = function(dept) {
        return function(sg) {
            return ArrayUtils.includes(sg.schoolDepartments, dept.id);
        };
    };

    $scope.getLegendById = function(legendId) {
        return $scope.legends.find(function(el){return el.id === legendId; });
    };

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
        if(!week.studyPeriodId) {
            return;
        }
        var DialogController = function (scope) {
            scope.studentGroup = studentGroup;
            scope.week = week;
            scope.legends = $scope.legends;
            scope.data = {
                weeks: getNumberOfWeeks(schedule),
                legend: schedule.studyYearScheduleLegend
            };
            scope.translationData = {weeks: $scope.weeks.length - week.weekNr + 1};
        };
        dialogService.showDialog('studyYearSchedule/study.year.schedule.add.dialog.html', DialogController,
            function (submitScope) {
                if(!submitScope.data.legend) {
                    for(var j = 0; j < submitScope.data.weeks; j++) {
                        var deletedSchedule = $scope.getSchedule(studentGroup.id, week.weekNr + j);
                        if(ArrayUtils.includes($scope.studyYearSchedules, deletedSchedule)) {
                            ArrayUtils.remove($scope.studyYearSchedules, deletedSchedule);
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
                            studyPeriod: i === 0 ? week.studyPeriodId : $scope.getWeeksPeriod(week.weekNr + i)
                        };

                        if(!schedulesEqual(oldSchedule, newSchedule)) {
                            if(ArrayUtils.includes($scope.studyYearSchedules, oldSchedule)) {
                                ArrayUtils.remove($scope.studyYearSchedules, oldSchedule);
                            }
                            $scope.studyYearSchedules.push(newSchedule);
                        }
                    }
                }
            });
    };

    function schedulesEqual(s1, s2) {
        return s1.studyYearScheduleLegend === s2.studyYearScheduleLegend &&
        s1.studentGroup === s2.studentGroup &&
        s1.weekNr === s2.weekNr;
    }

    $scope.getSchedule = function(studentGroupId, weekNr) {
        var schedule = $scope.studyYearSchedules.find(function(s){
            return s.weekNr === weekNr && s.studentGroup === studentGroupId;
        });
        if(!schedule) {
            schedule = {
                studyYearScheduleLegend: null,
                studentGroup: studentGroupId,
                weekNr: weekNr
            };
        }
        return schedule;
    };

    $scope.save = function() {
        $scope.record.studyYearSchedules = $scope.studyYearSchedules;
        $scope.record.$put().then(function(response){
            message.updateSuccess();
            $scope.studyYearSchedules = response.studyYearSchedules;
        });
    };

    $scope.$watch('criteria.studyYear', function(){
      if(!$scope.criteria.studyYear) {
        $scope.isPastStudyYear = true;
      } else {
        $scope.isPastStudyYear = DataUtils.isPastStudyYearOrPeriod($scope.criteria.studyYear);
      }
    });
}]);