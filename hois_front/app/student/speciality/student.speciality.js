'use strict';

angular.module('hitsaOis').controller('StudentSpecialitySearchController',
  ['$route', '$scope', 'message', 'QueryUtils', function ($route, $scope, message, QueryUtils) {
    var baseUrl = "/students/highspecialities";
    $scope.auth = $route.current.locals.auth;

    $scope.autocompletes = [];
    $scope.curriculumVersions = QueryUtils.endpoint("/autocomplete/curriculumversions").query({closed: false});
    $scope.specialities = QueryUtils.endpoint("/autocomplete/highspecialities").query();
    $scope.availableSpecialities = [];
    $scope.filteredVersions = [];

    function filterSpeciality(spec) {
      return angular.isArray(spec.curriculumVersions) && spec.curriculumVersions.filter(function(cv) {
        return cv === $scope.criteria.curriculumVersion;
      }).length > 0;
    }

    function postLoad() {
      $scope.specialities.$promise.then(function(result) {
        $scope.availableSpecialities = result.filter(filterSpeciality);
      });
    }

    QueryUtils.createQueryForm($scope, baseUrl, {}, postLoad);

    function filterVersions() {
      var needClear = true;
      $scope.filteredVersions = $scope.curriculumVersions.filter(function(row) {
        var passed = row.curriculum === $scope.criteria.curriculum && row.specialities && row.specialities.length > 1;
        if (passed && row.id === $scope.criteria.curriculumVersion) {
          needClear = false;
        }
        return passed;
      });
      if (needClear) {
        return $scope.criteria.curriculumVersion = null;
      }
    }

    $scope.$watch('criteria.curriculum', filterVersions);
    $scope.$watchCollection('curriculumVersions', filterVersions);

    // Sort object is placed in backend. So if you want to use sorting in criteria then you will need to remove sorting in backend.
    var loadData = $scope.loadData;
    $scope.loadData = function() {
      $scope.searchForm.$setSubmitted();
      if(!$scope.searchForm.$valid) {
        message.error('main.messages.form-has-errors');
      } else {
        loadData();
      }
    }

    var clearCriteria = $scope.clearCriteria;
    $scope.clearCriteria = function() {
      $scope.autocompletes.forEach(function(ac) {
        ac.clear();
      });
      clearCriteria();
    };

    $scope.save = function() {
      QueryUtils.endpoint(baseUrl).post($scope.tabledata.content, function(result) {
        $scope.tabledata.content.forEach(function(row) {
          var existed = result.filter(function(studentSpec) {
            return row.id === studentSpec.id;
          });
          if (existed && existed.length > 0) {
            row.speciality.id = existed[0].speciality.id;
          }
        });
        message.info("main.messages.update.success");
      }, function(result) {
        message.error("main.messages.update.fail");
      });
    }
  }]
);