'use strict';

angular.module('hitsaOis').controller('PracticeJournalListController', function ($scope, $route, $location, $q, QueryUtils, dialogService, ArrayUtils, USER_ROLES, Classifier, message) {
  $scope.auth = $route.current.locals.auth;
  var clMapper = Classifier.valuemapper({status: 'PAEVIK_STAATUS'});
  QueryUtils.createQueryForm($scope, '/practiceJournals', {order: 'student_person.lastname,student_person.firstname'}, clMapper.objectmapper);
  
  var unbindStudyYearWatch = $scope.$watch('criteria.studyYear', function(value) {
    if (angular.isNumber(value)) {
      unbindStudyYearWatch();
      $q.all(clMapper.promises).then($scope.loadData);
    }
  });
  
  $scope.directiveControllers = [];
  var clearCriteria = $scope.clearCriteria;
  $scope.clearCriteria = function () {
    clearCriteria();
    $scope.directiveControllers.forEach(function (c) {
      c.clear();
    });
  };

  $scope.load = function() {
    if (!$scope.criteria.studyYear) {
      message.error('main.messages.form-has-errors');
      return false;
    } else {
      $scope.loadData();
    }
  };

  function canCreate() {
    return $scope.auth.isAdmin() && ArrayUtils.includes($scope.auth.authorizedRoles, USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_PRAKTIKAPAEVIK);
  }

  $scope.formState = {
    canCreate: canCreate()
  };

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
