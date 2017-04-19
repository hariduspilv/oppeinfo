'use strict';

angular.module('hitsaOis').controller('studyYearScheduleController', ['$scope', 'QueryUtils', 'ArrayUtils', 'message', 'DataUtils', '$mdDialog', 'dialogService', function ($scope, QueryUtils, ArrayUtils, message, DataUtils, $mdDialog, dialogService) {

    $scope.criteria = {
        schoolDepartments: [],
        studyYear: null,
        studyPeriods: [], 
        studentGroups: []
    };

    function selectCurrentStudyYear() {
        for(var i = 0; i < $scope.studyYears.length; i++) {
            DataUtils.convertStringToDates($scope.studyYears[i], ["startDate", "endDate"]);
        }
        $scope.criteria.studyYear = $scope.studyYears.find(function(item){
            var date = new Date();
            // DataUtils.convertStringToDates(item, ["startDate", "endDate"]);
            return date >= item.startDate && date <= item.endDate;
        });
    }

    QueryUtils.endpoint('/studyYearSchedule/studyYears').query().$promise.then(function(response){
        $scope.studyYears = response;
        console.log("studyYears", response);
    }).then(selectCurrentStudyYear);

    $scope.$watch('criteria.studyYear', function() {
            if($scope.criteria.studyYear) {
                $scope.criteria.studyPeriods = $scope.criteria.studyYear.studyPeriods.map(function(p){return p.id;});
                getSchedules();
                getWeeks();
            }
        }
    );

    var queryOnLoad = true;
    $scope.$watch('criteria.schoolDepartments', function() {
            if(queryOnLoad) {
                queryOnLoad = false;
            } else {
                $scope.criteria.studentGroups = $scope.studentGroups.
                filter(function(sg){return arraysIntersect(sg.schoolDepartments, $scope.criteria.schoolDepartments);})
                .map(function(sg){return sg.id;});
                getSchedules();
            }
        }
    );

    function arraysIntersect(arr1, arr2) {
        for(var i = 0; i < arr1.length; i++) {
            if(ArrayUtils.includes(arr2, arr1[i])) {
                return true;
            }
        }
        return false;
    }

    function getSchedules() {
        $scope.record = QueryUtils.endpoint('/studyYearSchedule').search($scope.criteria);
        $scope.record.$promise.then(function(response){
            $scope.studyYearSchedules = response.studyYearSchedules;
            // console.log("study year schedules", $scope.studyYearSchedules);

            // $scope.getScedule(1, 2); // only for testing
        });
    }

    function getWeeks() {

        console.log("getWeeks");
        var weeks = [];
        var weekNr = 1;
        var start = new Date($scope.criteria.studyYear.startDate);
        var end = $scope.criteria.studyYear.endDate;

        while(start < end) {
            var week = {};
            week.start = start;
            start = new Date(start.getFullYear() ,start.getMonth() ,start.getDate()+7);
            week.end = start;
            week.weekNr = weekNr++;
            weeks.push(week);
        }
        console.log(weeks);
    }

    QueryUtils.endpoint('/school/studyYearScheduleLegends').search().$promise.then(function(response){
        $scope.legends = response.legends;
    });

    QueryUtils.endpoint('/autocomplete/schooldepartments').query().$promise.then(function(response){
        $scope.schoolDepartments = response;
    });

    QueryUtils.endpoint('/studyYearSchedule/studentGroups').query().$promise.then(function(response){
        $scope.studentGroups = response;
    });

    $scope.filterSchoolDepartments = function(dept) {
        return $scope.criteria.schoolDepartments.length === 0 || ArrayUtils.includes($scope.criteria.schoolDepartments, dept.id);
    };

    $scope.getSelectedYearsStudyPeriods = function(){
        if($scope.studyYears) {
            return $scope.studyYears.find(function(sy){return sy.id === $scope.criteria.studyYear.id;}).studyPeriods;
        }
    };

    $scope.filterStudentGroups = function(dept) {
        return function(sg) {
            return ArrayUtils.includes(sg.schoolDepartments, dept.id);
        };
    };

    $scope.save = function() {
        console.log("save");
        $scope.record.studyYearSchedules = $scope.studyYearSchedules;
        $scope.record.$put().then(function(response){
            message.updateSuccess();
            $scope.studyYearSchedules = response.studyYearSchedules;
        });
    };

    $scope.weeks = [1, 2, 3, 4, 5, 6];  //TODO: change it 


    $scope.openAddScheduleDialog = function (studentGroup, week) {



        var DialogController = function (scope) {

            scope.studentGroup = studentGroup;
            scope.week = week;

            scope.legends = $scope.legends;
            scope.data = {
                weeks: 1
            };
        };
        dialogService.showDialog('studyYearSchedule/study.year.schedule.add.dialog.html', DialogController,
            function (submitScope) {

            });
    };

    $scope.getScedule = function(studentGroupId, weekNr) {
        var schedule = $scope.studyYearSchedules.find(function(s){
            return s.weekNr === weekNr && s.studentGroup === studentGroupId;
        });
        if(!schedule) {
            schedule = {
                legend: null
            };
        }
        return schedule;
    };


}]);