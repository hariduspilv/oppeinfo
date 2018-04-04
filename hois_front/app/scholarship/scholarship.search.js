'use strict';

angular.module('hitsaOis').controller('ScholarshipSearchController', 
  function ($scope, $location, $q, $route, $timeout, dialogService, message, Classifier, QueryUtils, DataUtils, USER_ROLES, AuthService) {
    $scope.canEdit = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_STIPTOETUS);
    var baseUrl = '/scholarships';
    var clMapper = Classifier.valuemapper({type: 'STIPTOETUS'});
    $scope.formState = {};

    //TODO: hardcoded
    $scope.statuses = [{id: 0, nameEt: 'Sisestamisel'}, {id: 1, nameEt: 'Avalik'}];

    $scope.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriods').query();
    QueryUtils.createQueryForm($scope, baseUrl, {order: 'id'}, clMapper.objectmapper);

    var promises = clMapper.promises;
    promises.push($scope.studyPeriods.$promise);

    $scope.criteria.allowedStipendTypes = $route.current.locals.params.allowedStipendTypes;
    if($route.current.locals.params.scholarship) {
      $scope.formState.typeIsScholarship = true;
      $scope.scholarshipType = "scholarship";
    } else if ($route.current.locals.params.grant) {
      $scope.scholarshipType = "grant";
    } else if ($route.current.locals.params.scholarshipType) {
      $scope.scholarshipType = $route.current.locals.params.scholarshipType;
    }

    $q.all(promises).then(function () {
      if(!$scope.criteria.studyPeriod) {
        var sp = DataUtils.getCurrentStudyYearOrPeriod($scope.studyPeriods);
        if(sp) {
          $scope.criteria.studyPeriod = sp.id;
        }
      }
      $timeout($scope.loadData);
    });

    $scope.changeStipend = function (row) {
      var messageText;
      if (['STIPTOETUS_POHI', 'STIPTOETUS_ERI', 'STIPTOETUS_SOIDU', 'STIPTOETUS_DOKTOR'].indexOf(row.type.code) !== -1) {
        messageText = 'stipend.confirmations.grantIsPublishedChange';
      } else {
        messageText = 'stipend.confirmations.scholarshipIsPublishedChange';
      }
      if (row.isOpen) {
        dialogService.confirmDialog({prompt: messageText}, function () {
          $location.url(baseUrl + '/' + row.id + '/edit');
        });
      } else {
        $location.url(baseUrl + '/' + row.id + '/edit');
      }
    };
  }
);
