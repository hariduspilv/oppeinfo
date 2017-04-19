'use strict';

angular.module('hitsaOis').controller('SchoolStudyLevelsController', ['$q', '$scope', '$rootScope','Classifier', 'QueryUtils', 'AUTH_EVENTS', 'message', function ($q, $scope, $rootScope, Classifier, QueryUtils, AUTH_EVENTS,message) {
    $scope.studyLevelDefs = Classifier.queryForDropdown({mainClassCode: 'OPPEASTE'});
    $scope.studyLevels = QueryUtils.endpoint('/school/studyLevels').search();

    $q.all([$scope.studyLevelDefs.$promise, $scope.studyLevels.$promise]).then(function() {
      Classifier.setSelectedCodes($scope.studyLevelDefs, $scope.studyLevels.studyLevels);
    });

    $scope.update = function() {
      $scope.studyLevels.studyLevels = Classifier.getSelectedCodes($scope.studyLevelDefs);

      $scope.studyLevels.$put().then(function() {
        message.updateSuccess();
        $rootScope.$broadcast(AUTH_EVENTS.reAuthenticate);
      });
    };
  }
]);
