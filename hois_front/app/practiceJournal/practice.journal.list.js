'use strict';

angular.module('hitsaOis').controller('PracticeJournalListController', function ($scope, $route, $location, QueryUtils, dialogService) {
  $scope.auth = $route.current.locals.auth;
  QueryUtils.createQueryForm($scope, '/practiceJournals', {order: 'student_person.lastname,student_person.firstname'});
  $scope.loadData();

  $scope.newPracticeJournal = function () {
    if ($scope.auth.school.higher && $scope.auth.school.vocational) {
      dialogService.showDialog('practiceJournal/vocational.higher.select.dialog.html', function () {
      }, function (submittedDialogScope) {
        if (submittedDialogScope.higher === true) {
          $location.path("/practiceJournals/new").search({ higher: true });
        } else {
          $location.path("/practiceJournals/new");
        }
      });
    } else {
      $location.path("/practiceJournals/new");
    }
  };
});
