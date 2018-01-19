'use strict';

angular.module('hitsaOis').controller('ScholarshipSearchController', ['dialogService', '$scope', 'Classifier', '$location', 'message', 'QueryUtils', '$route', 'DataUtils', 'ArrayUtils', '$q',
  function (dialogService, $scope, Classifier, $location, message, QueryUtils, $route, DataUtils, ArrayUtils, $q) {
    var baseUrl = '/scholarships';
    var clMapper = Classifier.valuemapper({
      type: 'STIPTOETUS'
    });

    //TODO: hardcoded
    $scope.statuses = [{
      id: 0,
      nameEt: 'Sisestamisel',
    }, {
      id: 1,
      nameEt: 'Avalik',
    }];

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

    $scope.criteria.allowedStipendTypes = $route.current.locals.params.allowedStipendTypes;
    $scope.scholarshipType = $route.current.locals.params.scholarshipType;

    $scope.changeStipend = function (row) {
      var messageText;
      if (['STIPTOETUS_POHI', 'STIPTOETUS_ERI', 'STIPTOETUS_SOIDU', 'STIPTOETUS_DOKTOR'].indexOf(row.type.code) !== -1) {
        messageText = 'stipend.confirmations.grantIsPublishedChange';
      } else {
        messageText = 'stipend.confirmations.scholarshipIsPublishedChange';
      }
      if (row.isOpen) {
        dialogService.confirmDialog({
            prompt: messageText,
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
