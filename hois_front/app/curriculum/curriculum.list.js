'use strict';

  angular.module('hitsaOis').controller('CurriculumListController', ['$scope', '$sessionStorage', 'Classifier', 'DataUtils', 'QueryUtils', '$q', '$rootScope', 'AuthService', '$location', '$route', function ($scope, $sessionStorage, Classifier, DataUtils, QueryUtils, $q, $rootScope, AuthService, $location, $route) {
    $scope.auth = $route.current.locals.auth;
    var clMapper = Classifier.valuemapper({origStudyLevel: 'OPPEASTE', status: 'OPPEKAVA_STAATUS'});
    QueryUtils.createQueryForm($scope, '/curriculum', {order: $scope.currentLanguageNameField()}, clMapper.objectmapper);
    $q.all(clMapper.promises).then($scope.loadData);
    DataUtils.convertStringToDates($scope.criteria, ['validFrom', 'validThru']);

    // FIXME: oppesuund is lost when returning to page

    function getListOfStudyLevels() {
        if($scope.auth.isExternalExpert()) {
            $scope.school = {
                higher: true,
                vocational: true
            };
        } else {
            // without using this temp varialbe selected studyLevels are lost after returning to search page
            var selectedStudyLevels = $scope.criteria.studyLevel;
            QueryUtils.endpoint('/school/studyLevels').search().$promise.then(function(response){
                $scope.criteria.studyLevel = selectedStudyLevels;
                $scope.studyLevelOptions = response.studyLevels;
                $scope.school = {
                    higher: $scope.studyLevelOptions.filter(function(s){return s.indexOf('OPPEASTE_5') !== -1;}).length > 0,
                    vocational: $scope.studyLevelOptions.filter(function(s){return s.indexOf('OPPEASTE_4') !== -1;}).length > 0
                };
            });
        }
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
