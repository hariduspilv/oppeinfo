'use strict';

angular.module('hitsaOis').controller('ScholarshipApplicationController', ['Classifier', '$scope', '$location', 'message', 'QueryUtils', '$route', 'DataUtils', '$q',
  function (Classifier, $scope, $location, message, QueryUtils, $route, DataUtils, $q) {
    $scope.criteria = {};
    $scope.criteria.allowedStipendTypes = $route.current.locals.params.allowedStipendTypes;

    $scope.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriods').query();
    var clMapper = Classifier.valuemapper({
      status: 'STIPTOETUS_STAATUS'
    });

    var promises = clMapper.promises;
    promises.push($scope.studyPeriods.$promise);

    $q.all(promises).then(function () {
      $scope.criteria.studyPeriod = DataUtils.getCurrentStudyYearOrPeriod($scope.studyPeriods).id;
    });
  }
]);
