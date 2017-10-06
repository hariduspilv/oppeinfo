'use strict';

angular.module('hitsaOis').controller('StudentJournalListController', ['$scope', '$route', 'QueryUtils', '$mdDialog', 'Classifier', 
    function ($scope, $route, QueryUtils, $mdDialog, Classifier) {
        $scope.currentNavItem = 'journals';
        var studentId = $route.current.locals.auth.student;

        QueryUtils.endpoint('/journals/studentJournals/').query({studentId: studentId}).$promise.then(function (result) {
            $scope.journalsByYears = getJournalsSortedByYearCodes(result);
        });

        function getJournalYearCodes(journals) {
            var yearCodes = [];
            for (var i = 0; i < journals.length; i++) {
                if (yearCodes.indexOf(journals[i].yearCode) === -1) {
                    yearCodes.push(journals[i].yearCode);
                }
            }
            return yearCodes;
        }

        function getJournalYears(yearCodes) {
            var journalYears = [];
            for (var i = 0; i < yearCodes.length; i++) {
                journalYears.push({yearCode: yearCodes[i], journals: []});
            }
            return journalYears;
        }

        function getJournalsSortedByYearCodes(journals) {
            var yearCodes = getJournalYearCodes(journals);
            console.log(getJournalYears(yearCodes));
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

        $scope.showJournal = function (journalId) {
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
                    Classifier.queryForDropdown({ mainClassCode: 'SISSEKANNE' }, function (response) {
                        response.forEach(function (it) {
                            $scope.journalEntryTypes[it.code] = it;
                        });
                    });
                    $scope.getEntryColor = function (type) {
                        return $scope.journalEntryTypeColors[type];
                    };

                    QueryUtils.endpoint('/journals/' + journalId + '/studentJournal/').search({ studentId: studentId }).$promise.then(function (result) {
                        $scope.journal = result;
                    });

                    $scope.close = $mdDialog.hide;
                },
                templateUrl: 'student/student.journal.dialog.html',
                clickOutsideToClose: true
            });
        };
    }
]).controller('StudentJournalStudyController', ['$scope', 'QueryUtils', 
    function ($scope, QueryUtils) {
        $scope.currentNavItem = 'study';
    }
]).controller('StudentJournalTasksController', ['$scope', 'QueryUtils', 
    function ($scope, QueryUtils) {
        $scope.currentNavItem = 'tasks';
    }
]);