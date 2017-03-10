'use strict';

  angular.module('hitsaOis').controller('CurriculumListController', ['$scope', '$sessionStorage', 'Classifier', 'DataUtils', 'QueryUtils', '$q', '$rootScope', 'AuthService', '$location', function ($scope, $sessionStorage, Classifier, DataUtils, QueryUtils, $q, $rootScope, AuthService, $location) {

    var clMapper = Classifier.valuemapper({origStudyLevel: 'OPPEASTE', status: 'OPPEKAVA_STAATUS'});
    QueryUtils.createQueryForm($scope, '/curriculum', {order: $scope.currentLanguageNameField()}, clMapper.objectmapper);
    $q.all(clMapper.promises).then($scope.loadData);

    // TODO/FIXME methods?
    if(AuthService.isAuthenticated()) {
      // TODO: remove rolecode based filtration and use isAuthorized when roles/rights are fixed
      $scope.disableSchoolSelection = $rootScope.currentUser.roleCode === 'ROLL_A';
      if($scope.disableSchoolSelection) {
        $scope.criteria.school = $rootScope.currentUser.school.id;
      }
      $scope.loadData();
    }

    function getListOfStudyLevels() {
        QueryUtils.endpoint('/school/studyLevels').get().$promise.then(function(response){
            $scope.studyLevelCodes = response.studyLevels;
        });
    }
    getListOfStudyLevels();

    $scope.edit = function(curriculum) {
      if(curriculum.higher) {
        $location.path('/higherEducationCurriculum/' + curriculum.id + '/edit');
      } else {
        $location.path('/vocationalCurriculum/' + curriculum.id + '/edit');
      }
    };

    $scope.view = function(curriculum) {
      if(curriculum.higher) {
        $location.path('/higherEducationCurriculum/' + curriculum.id + '/view');
      } else {
        $location.path('/vocationalCurriculum/' + curriculum.id + '/edit');
      }
    };
}]);
