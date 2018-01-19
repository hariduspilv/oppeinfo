'use strict';

angular.module('hitsaOis')
.controller('SchoolStudyLevelsController', 
  function ($q, $scope, $rootScope, Classifier, QueryUtils, AUTH_EVENTS, message, USER_ROLES, AuthService) {
    $scope.studyLevelDefs = Classifier.queryForDropdown({mainClassCode: 'OPPEASTE', order: 'code'});
    $scope.studyLevels = QueryUtils.endpoint('/school/studyLevels').search();
    $scope.canEdit = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPETASE);

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
  });
