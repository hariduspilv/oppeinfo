'use strict';


angular.module('hitsaOis')
  .controller('CurriculumListController', function (QueryUtils, Curriculum, $scope, $translate, $route, Classifier, School, $location, $rootScope) {

    $scope.query = {
      order: 'id',
      size: 10,
      page: 1,
      isJoint: false
    };

    if($scope.isSelectDialog) {
      $scope.query.school = [$rootScope.auth.school.id];
    }

    $rootScope.$watch('auth', function(){
        if($rootScope.auth) {
            $scope.disableSchoolSelection = $rootScope.auth.roleCode === 'ROLL_A';
            if($scope.disableSchoolSelection) {
                $scope.query.school = $rootScope.auth.school.id;
            }
            $scope.getCurriculums();
        }
    });

  function success(response) {
    $scope.curriculums = response.content;
    $scope.curriculums.count = response.totalElements;
  }

    $scope.clearInputs = function() {
      QueryUtils.clearQueryParams($scope.query);
    };

    $scope.getCurriculums = function () {
      // passing them as date objects causes error (described in back end StateCurriculumSearchCommand class)
      $scope.query.validFromMillis = $scope.query.validFrom ? $scope.query.validFrom.getTime() : null;
      $scope.query.validThruMillis = $scope.query.validThru ? $scope.query.validThru.getTime() : null;
      $scope.query.lang = $translate.use().toUpperCase();
      $scope.promise = Curriculum.query($scope.query, success).$promise;
    };

    $scope.getClassifierValds = function() {
      var query = {
        mainClassCode: 'ISCED_VALD'
      };
      Classifier.queryForDropdown(query).$promise.then(function(response) {
        $scope.listOfClassifierValds = response;
      });
    };
    $scope.getClassifierValds();

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

    function getListOfStatuses() {
      Classifier.get('OPPEKAVA_STAATUS').$promise.then(function(response) {
        $scope.listOfStatuses = response.children;
      });
    }
    getListOfStatuses();

    function getListOfEhisStatuses() {
      Classifier.get('OPPEKAVA_EHIS_STAATUS').$promise.then(function(response) {
        $scope.listOfEhisStatuses = response.children;
      });
    }
    getListOfEhisStatuses();

    function getListOfStudyLanguages() {
      Classifier.get('OPPEKEEL').$promise.then(function(response) {
        $scope.listOfStudyLanguages = response.children;
      });
    }
    getListOfStudyLanguages();

    function getListOfEkrLevels() {
      Classifier.get('EKR').$promise.then(function(response){
        $scope.listOfEkrLevels = response.children;
      });
    }
    getListOfEkrLevels();

    function getListOfStudyLevels() {
        QueryUtils.endpoint('/school/studyLevels').get().$promise.then(function(response){
            var studyLevelCodes = response.studyLevels;
            $scope.listOfStudyLevels = [];
            studyLevelCodes.forEach(function(it) {
                    $scope.listOfStudyLevels.push({code: it});
            });
        });
    }
    getListOfStudyLevels();

    function getListOfSchools() {
      School.getAll().$promise.then(function(response){
        $scope.listOfSchools = response;
      });
    }
    getListOfSchools();

    function getDepartments() {
      QueryUtils.endpoint('/autocomplete/schooldepartments')
      .get({lang: $translate.use().toUpperCase()}).$promise.then(function(result){
        $scope.listOfDepartments = result.content;
      });
    }
    getDepartments();

    $scope.edit = function(curriculum) {
      if(curriculum.higher) {
        $location.path('/higherEducationCurriculum/' + curriculum.id + '/edit');
      } else {
        $location.path('/vocational/curriculum/' + curriculum.id + '/edit');
      }
    };

    $scope.view = function(curriculum) {
      if(curriculum.higher) {
        $location.path('/higherEducationCurriculum/' + curriculum.id + '/view');
      } else {
        //   TODO: view mode not yet implemented
        $location.path('/vocational/curriculum/' + curriculum.id + '/edit');
      }
    };
  });
