'use strict';

angular.module('hitsaOis').controller('ScholarshipSearchController', ['dialogService', '$scope', 'Classifier', '$location', 'message', 'QueryUtils', '$route', 'DataUtils', 'ArrayUtils', '$q',
  function (dialogService, $scope, Classifier, $location, message, QueryUtils, $route, DataUtils, ArrayUtils, $q) {
    var baseUrl = '/scholarships';
    var clMapper = Classifier.valuemapper({
      type: 'STIPTOETUS'
    });

    $scope.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriods').query();
    QueryUtils.createQueryForm($scope, baseUrl, {
      order: 'dId'
    }, clMapper.objectmapper);

    var promises = clMapper.promises;
    promises.push($scope.studyPeriods.$promise);

    $q.all(promises).then(function () {
      $scope.criteria.studyPeriod = DataUtils.getCurrentStudyYearOrPeriod($scope.studyPeriods).id;
      $scope.loadData();
    });

    if ($route.current.params && $route.current.params.grants) {
      $scope.criteria.allowedStipendTypes = ['STIPTOETUS_POHI', 'STIPTOETUS_ERI', 'STIPTOETUS_SOIDU'];
      $scope.scholarshipType = "grants";
    } else if ($route.current.params && $route.current.params.scholarships) {
      $scope.criteria.allowedStipendTypes = ['STIPTOETUS_ERIALA', 'STIPTOETUS_TULEMUS', 'STIPTOETUS_MUU'];
      $scope.scholarshipType = "scholarships";
    } else {
      $scope.criteria.allowedStipendTypes = ['STIPTOETUS_DOKTOR'];
      $scope.criteria.type = 'STIPTOETUS_DOKTOR';
    }

    $scope.changeStipend = function(row) {
      if(row.isOpen) {
        dialogService.confirmDialog({
          prompt: 'stipend.confirmations.isPublishedChange',
          accept: 'main.yes',
          cancel: 'main.no'
        },
        function () {
          $location.url(baseUrl + '/' + row.id + '/edit');
        });
      } else {
        $location.url(baseUrl + '/' + row.id + '/edit');
      }
    };

  }
]);
