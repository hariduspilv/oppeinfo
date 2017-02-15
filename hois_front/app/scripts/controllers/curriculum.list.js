'use strict';


angular.module('hitsaOis')
  .controller('CurriculumListController', function (QueryUtils, Curriculum, $scope, $translate, $route, Classifier, $location, $rootScope, AuthService) {

    $scope.query = {
      order: 'id',
      size: 10,
      page: 1,
      isJoint: false
    };

    // TODO add more table classifier fields
    var clMapper = Classifier.valuemapper({origStudyLevel: 'OPPEASTE', status: 'OPPEKAVA_STAATUS'});
    function success(response) {
      $scope.curriculums = clMapper.objectmapper(response.content);
      $scope.curriculums.count = response.totalElements;
    }

    $scope.clearInputs = function() {
      QueryUtils.clearQueryParams($scope.query);
    };

    $scope.getCurriculums = function () {
        if($rootScope.currentUser.school && $scope.disableSchoolSelection) {
            $scope.query.school = $rootScope.currentUser.school.id;
        }
      $scope.promise = Curriculum.query($scope.query, success).$promise;
    };

    $scope.listOfClassifierValds = Classifier.queryForDropdown({mainClassCode: 'ISCED_VALD'});

    $scope.resetAndGetClassifierSuuns = function() {
      $scope.query.iscedClassCode = undefined;
      $scope.query.iscedSuun = undefined;
      $scope.getClassifierSuuns();
    };

    $scope.getClassifierSuuns = function() {
      if($scope.query && $scope.query.iscedVald) {
        Classifier.getChildren($scope.query.iscedVald).$promise.then(function(response) {
          $scope.listOfClassifierSuuns = response;
        });
      }
    };

    $scope.getClassifierGroups = function() {
      if($scope.query && $scope.query.iscedSuun) {
        Classifier.getChildren($scope.query.iscedSuun).$promise.then(function(response) {
          $scope.listOfClassifierGroups = response;
        });
      }
    };

    // TODO/FIXME methods?
    if(AuthService.isAuthenticated()) {
      // TODO: remove rolecode based filtration and use isAuthorized when roles/rights are fixed
      $scope.disableSchoolSelection = $rootScope.currentUser.roleCode === 'ROLL_A';
      if($scope.disableSchoolSelection) {
        $scope.query.school = $rootScope.currentUser.school.id;
      }
      $scope.getCurriculums();
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
  });
