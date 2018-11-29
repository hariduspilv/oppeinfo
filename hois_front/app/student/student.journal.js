(function () {
    'use strict';

    function getStudyYearWeeks(studyYearStartDate, studyYearEndDate) {
        var weeks = [];
        var weekNr = 1;
        var start = new Date(studyYearStartDate);
        var end = new Date(studyYearEndDate);

        while (start < end) {
            var week = {};
            week.weekNr = weekNr++;
            week.start = start;
            start = new Date(start.getFullYear(), start.getMonth(), start.getDate() + (7 - start.getDay()));
            week.end = start;
            week.entries = [];

            weeks.push(week);
            start = new Date(start.getFullYear(), start.getMonth(), start.getDate() + 1);
        }
        return weeks;
    }

    function sortEntriesByWeeks(studyYearStartDate, studyYearEndDate, entries) {
        var weeks = getStudyYearWeeks(studyYearStartDate, studyYearEndDate);

        for (var i = 0; i < entries.length; i++) {
            for (var j = 0; j < weeks.length; j++) {
                var entryDate = new Date(entries[i].date).setHours(0, 0, 0, 0);

                if (weeks[j].start.setHours(0, 0, 0, 0) < entryDate && entryDate < weeks[j].end.setHours(0, 0, 0, 0)) {
                    weeks[j].entries.push(entries[i]);
                    break;
                }
            }
        }
        return weeks;
    }

    function getCurrentWeek(weeks) {
        var currentWeekStart = moment().startOf('isoWeek').toDate();

        for (var i = 0; i < weeks.length; i++) {
            if (weeks[i].start.getTime() === currentWeekStart.getTime()) {
                return weeks[i];
            }
        }
    }

    function getPreviousAndNextWeek(scope) {
        var shownWeekIndex;
        for (var i = 0; i <= scope.weeks.length - 1; i++) {
            if (scope.weeks[i].weekNr === scope.shownWeek.weekNr) {
                shownWeekIndex = i;
            }
        }
        scope.previousWeekIndex = scope.weeks[shownWeekIndex - 1] ? shownWeekIndex - 1 : null;
        scope.nextWeekIndex = scope.weeks[shownWeekIndex + 1] ? shownWeekIndex + 1 : null;
    }

    angular.module('hitsaOis').controller('StudentJournalListController', ['$scope', '$route', 'QueryUtils', '$mdDialog', 'Classifier', 'DataUtils',
        function ($scope, $route, QueryUtils, $mdDialog, Classifier, DataUtils) {
            $scope.currentNavItem = 'journals';
            $scope.auth = $route.current.locals.auth;
            var studentId = $scope.auth.student;
            var clMapper = Classifier.valuemapper({entryType: 'SISSEKANNE'});

            QueryUtils.endpoint('/journals/studentJournalStudyYears/').query({studentId: studentId}).$promise.then(function (studyYears) {
                var currentSy = DataUtils.getCurrentStudyYearOrPeriod(studyYears);
                var currentSyCode = currentSy ? currentSy.code : null;
                //DataUtils method sorts studyYears
                $scope.journalsByYears = getJournalYears(studyYears.reverse(), currentSyCode);

                if (currentSy) {
                    $scope.getStudyYearJournals(currentSy.id, currentSy.code);
                }
            });

            $scope.getStudyYearJournals = function (syId, syCode) {
                if ($scope.journalsByYears[getJournalYearIndex(syCode)].journals.length === 0) {
                    QueryUtils.loadingWheel($scope, true);
                    QueryUtils.endpoint('/journals/studentJournals/').query({studentId: studentId, studyYearId: syId}).$promise.then(function (journals) {
                        journals.forEach(function (journal) {
                            journal.journalEntries.forEach(function (entry) {
                                clMapper.objectmapper(entry);
                            });
                            $scope.journalsByYears[getJournalYearIndex(syCode)].journals.push(journal);
                        });
                        QueryUtils.loadingWheel($scope, false);
                    });
                }
            };

            function getJournalYearIndex(syCode) {
                for (var i = 0; $scope.journalsByYears.length; i++) {
                    if ($scope.journalsByYears[i].yearCode === syCode) {
                        return i;
                    }
                }
                return null;
            }

            function getJournalYears(studyYears, currentSyCode) {
                var journalYears = [];
                for (var i = 0; i < studyYears.length; i++) {
                    journalYears.push({
                        yearId: studyYears[i].id,
                        yearCode: studyYears[i].code,
                        journals: [],
                        collapsableOpen: studyYears[i].code === currentSyCode ? true : false
                    });
                }
                return journalYears;
            }

            var positiveResults = ['A', '3', '4', '5'];
            $scope.resultIsPostive = function (result) {
                return positiveResults.indexOf(result) !== -1;
            };

            var boldResults = ['SISSEKANNE_E', "SISSEKANNE_I", "SISSEKANNE_H"];
            $scope.isTestButNotFinal = function (entryType) {
                return boldResults.indexOf(entryType) !== -1;
            };

            $scope.showJournal = function (journal) {
                $mdDialog.show({
                    controller: function ($scope) {
                        $scope.journalEntryTypeColors = {
                            'SISSEKANNE_T': 'default-grey-50',
                            'SISSEKANNE_E': 'default-amber-100',
                            'SISSEKANNE_I': 'default-lime-100',
                            'SISSEKANNE_H': 'default-pink-50',
                            'SISSEKANNE_L': 'default-pink-300',
                            'SISSEKANNE_O': 'default-light-blue-50',
                            'SISSEKANNE_P': 'default-teal-100',
                            'SISSEKANNE_R': 'default-indigo-100'
                        };
                        $scope.journalEntryTypes = {};
                        Classifier.queryForDropdown({ mainClassCode: 'SISSEKANNE' }, function (result) {
                          $scope.journalEntryTypes = Classifier.toMap(result);
                        });
                        $scope.getEntryColor = function (type) {
                            return $scope.journalEntryTypeColors[type];
                        };
                        $scope.journal = journal;
                        $scope.close = $mdDialog.hide;
                    },
                    templateUrl: 'student/student.journal.dialog.html',
                    clickOutsideToClose: true
                });
            };
        }
    ]).controller('StudentJournalStudyController', ['$scope', '$route', 'QueryUtils', 
        function ($scope, $route, QueryUtils) {
            $scope.currentNavItem = 'study';
            $scope.auth = $route.current.locals.auth;
            var studentId = $scope.auth.student;

            QueryUtils.endpoint('/journals/studentJournalStudy/').search({studentId: studentId}).$promise.then(function (result) {
                $scope.weeks = sortEntriesByWeeks(result.studyYearStartDate, result.studyYearEndDate, result.entries);
                $scope.shownWeek = getCurrentWeek($scope.weeks);
                getPreviousAndNextWeek($scope);
            });

            $scope.changeWeek = function(weekIndex) {
                $scope.shownWeek = $scope.weeks[weekIndex];
                getPreviousAndNextWeek($scope);
            };
        }
    ]).controller('StudentJournalTasksController', ['$scope', '$route', 'QueryUtils', 
        function ($scope, $route, QueryUtils) {
            $scope.currentNavItem = 'tasks';
            $scope.auth = $route.current.locals.auth;
            var studentId = $scope.auth.student;

            QueryUtils.endpoint('/journals/studentJournalTasks/').search({studentId: studentId}).$promise.then(function (result) {
                $scope.weeks = sortEntriesByWeeks(result.studyYearStartDate, result.studyYearEndDate, result.tasks);
                $scope.shownWeek = getCurrentWeek($scope.weeks);
                getPreviousAndNextWeek($scope);
            });

            $scope.changeWeek = function(weekIndex) {
                $scope.shownWeek = $scope.weeks[weekIndex];
                getPreviousAndNextWeek($scope);
            };
        }
    ]);
}());