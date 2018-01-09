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

    angular.module('hitsaOis').controller('StudentJournalListController', ['$scope', '$route', 'QueryUtils', '$mdDialog', 'Classifier', 'ArrayUtils',
        function ($scope, $route, QueryUtils, $mdDialog, Classifier, ArrayUtils) {
            $scope.currentNavItem = 'journals';

            var studentId = $route.current.locals.auth.student;

            QueryUtils.endpoint('/journals/studentJournals/').query({studentId: studentId}).$promise.then(function (result) {
                getEntryType(result);
                $scope.journalsByYears = getJournalsSortedByYearCodes(result);
            });

            function getEntryType(journals) {
                for (var i = 0; i < journals.length; i++) {
                    for (var j = 0; j < journals[i].journalEntries.length; j++) {
                        journals[i].journalEntries[j].entryTypeClassifier = Classifier.get(journals[i].journalEntries[j].entryType);
                    }
                }
            }

            function getJournalYearCodes(journals) {
                var yearCodes = [];
                for (var i = 0; i < journals.length; i++) {
                    if (yearCodes.indexOf(journals[i].yearCode) === -1) {
                        yearCodes.push(journals[i].yearCode);
                    }
                }
                return yearCodes.sort().reverse();
            }

            function getJournalYears(yearCodes) {
                var journalYears = [];
                for (var i = 0; i < yearCodes.length; i++) {
                    journalYears.push({yearCode: yearCodes[i], journals: [], collapsableOpen: i === 0 ? true : false});
                }
                return journalYears;
            }

            function getJournalsSortedByYearCodes(journals) {
                var yearCodes = getJournalYearCodes(journals);
                var journalYears = getJournalYears(yearCodes);

                for (var i = 0; i < journals.length; i++) {
                    for (var j = 0; j < journalYears.length; j++) {
                        if (journals[i].yearCode === journalYears[j].yearCode) {
                            journalYears[j].journals.push(journals[i]);
                        }
                    }
                }
                return journalYears;
            }

            $scope.resultIsPostive = function (result) {
                var postiveResults = ['A', '3', '4', '5'];
                return ArrayUtils.contains(postiveResults, result);
            };

            $scope.isTestButNotFinal = function (entryType) {
                var boldResults = ['SISSEKANNE_E', "SISSEKANNE_I", "SISSEKANNE_H"];
                return ArrayUtils.contains(boldResults, entryType);
            }

            $scope.showJournal = function (journal) {
                $mdDialog.show({
                    controller: function ($scope) {
                        $scope.journalEntryTypeColors = {
                            'SISSEKANNE_T': 'default-grey-50',
                            'SISSEKANNE_E': 'default-amber-100',
                            'SISSEKANNE_I': 'default-lime-100',
                            'SISSEKANNE_H': 'default-pink-50',
                            'SISSEKANNE_L': 'default-pink-300'
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
            var studentId = $route.current.locals.auth.student;

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
            var studentId = $route.current.locals.auth.student;

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