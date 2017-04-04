'use strict';

  angular.module('hitsaOis').controller('CurriculumListController', ['$scope', '$sessionStorage', 'Classifier', 'DataUtils', 'QueryUtils', '$q', '$rootScope', 'AuthService', '$location', '$route', function ($scope, $sessionStorage, Classifier, DataUtils, QueryUtils, $q, $rootScope, AuthService, $location, $route) {
    $scope.auth = $route.current.locals.auth;
    var clMapper = Classifier.valuemapper({origStudyLevel: 'OPPEASTE', status: 'OPPEKAVA_STAATUS'});
    QueryUtils.createQueryForm($scope, '/curriculum', {order: $scope.currentLanguageNameField()}, clMapper.objectmapper);
    $q.all(clMapper.promises).then($scope.loadData);

    // TODO/FIXME methods?
    if(AuthService.isAuthenticated()) {
      // TODO: remove rolecode based filtration and use isAuthorized when roles/rights are fixed
      $scope.loadData();
    }

    function getListOfStudyLevels() {
        if($scope.auth.isExternalExpert()) {
            QueryUtils.endpoint('/classifier/all').query({mainClassCode: "OPPEASTE"}).$promise.then(function(response){
                $scope.studyLevelCodes = response;
            });
        } else {
            QueryUtils.endpoint('/school/studyLevels').get().$promise.then(function(response){
                $scope.studyLevelCodes = response.studyLevels;
            });
        }
    }
    getListOfStudyLevels();

    function getSchoolDepartments() {
        if($scope.auth.isExternalExpert()) {
            QueryUtils.endpoint('/curriculum/schoolDepartments').query().$promise.then(function(response){
                console.log("school departments: ");
                console.log(response);
                $scope.schoolDepartments = response;
            });
        }
    }
    getSchoolDepartments();

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
