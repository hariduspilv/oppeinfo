'use strict';

angular.module('hitsaOis').controller('studyYearScheduleController', 
function ($scope, $route, QueryUtils, ArrayUtils, message, DataUtils, $window, dialogService, USER_ROLES, AuthService, config, $httpParamSerializer) {
    $scope.auth = $route.current.locals.auth;
    $scope.canEdit = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPETOOGRAAFIK);

    $scope.colorOptions = {
        disabled: true
    };
    $scope.useMyFilter = $scope.auth.isLeadingTeacher() || $scope.auth.isStudent() || $scope.auth.isParent();

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
    QueryUtils.endpoint('/autocomplete/schooldepartments').query().$promise.then(function(response){
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

    $scope.legends = QueryUtils.endpoint('/school/studyYearScheduleLegends').search();
    QueryUtils.loadingWheel($scope,true);
    $scope.legends.$promise.then(function (response) {
        $scope.legends = response.legends;
        QueryUtils.loadingWheel($scope,false);
    });

    function getStudentGroups() {
        $scope.studentGroups = QueryUtils.endpoint('/studyYearSchedule/studentGroups').query({showMine: $scope.criteria.showMine});
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

    $scope.studyYears = QueryUtils.endpoint('/studyYearSchedule/studyYears').query(selectCurrentStudyYear);

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
        $scope.record = QueryUtils.endpoint('/studyYearSchedule').save($scope.criteria);
        QueryUtils.loadingWheel($scope,true);
        $scope.record.$promise.then(function(response){
            $scope.studyYearSchedules = response.studyYearSchedules;
            QueryUtils.loadingWheel($scope,false);
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
                            studyPeriod: i === 0 ? week.studyPeriodId : $scope.getWeeksPeriod(week.weekNr + i),
                            addInfo: submitScope.data.addInfo
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
        s1.weekNr === s2.weekNr &&
        s1.addInfo === s2.addInfo;
    }

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

    $scope.save = function() {
        $scope.record.studyYearSchedules = $scope.studyYearSchedules;
        $scope.record.$put().then(function(response){
            message.updateSuccess();
            $scope.studyYearSchedules = response.studyYearSchedules;
        });
    };

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
