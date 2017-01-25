'use strict';

angular.module('hitsaOis').controller('SchoolStudyLevelsController',

  function (QueryUtils, Classifier, message, $q, $scope) {
    $scope.studyLevelDefs = Classifier.query({mainClassCode: 'OPPEASTE'});
    $scope.studyLevels = QueryUtils.endpoint('/school/studyLevels').get();

    $q.all([$scope.studyLevelDefs.$promise, $scope.studyLevels.$promise]).then(function() {
      Classifier.setSelectedCodes($scope.studyLevelDefs.content, $scope.studyLevels.studyLevels);
    });

    $scope.update = function() {
      $scope.studyLevels.studyLevels = Classifier.getSelectedCodes($scope.studyLevelDefs.content);

      $scope.studyLevels.$put().then(function() {
        message.info('main.messages.update.success');
      });
    };
  }
);
