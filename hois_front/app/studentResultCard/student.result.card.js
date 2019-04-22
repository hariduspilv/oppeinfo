'use strict';

angular.module('hitsaOis').controller('StudentResultCardListController', ['$httpParamSerializer', '$q', '$route', '$scope', '$window', 'Classifier', 'QueryUtils', 'config',
  function ($httpParamSerializer, $q, $route, $scope, $window, Classifier, QueryUtils, config) {
    $scope.auth = $route.current.locals.auth;

    var clMapper = Classifier.valuemapper({studyForm: 'OPPEVORM', status: 'OPPURSTAATUS'});
    QueryUtils.createQueryForm($scope, '/students', {size: 100, order: 'person.lastname, person.firstname'}, clMapper.objectmapper);
    
    $scope.criteria.higher = false;
    if (!$scope.criteria.status) {
      $scope.criteria.status = ['OPPURSTAATUS_L'];
    }

    $scope.selectedStudents = [];

    $q.all().then($scope.loadData);

    $scope.print = function () {
      $window.location = config.apiUrl + '/students/studentResultCards.pdf?' + $httpParamSerializer({studentIds: $scope.selectedStudents});
    };

    var clearCriteria = $scope.clearCriteria;
    $scope.clearCriteria = function () {
      clearCriteria();
      $scope.criteria.higher = false;
    };
  }
]).controller('StudentResultCardController', ['$httpParamSerializer', '$route', '$scope', '$window', 'QueryUtils', 'config',
  function ($httpParamSerializer, $route, $scope, $window, QueryUtils, config) {
    $scope.auth = $route.current.locals.auth;
    var id = $route.current.params.id;

    QueryUtils.endpoint('/students/' + id + '/studentResultCard').search(function (result) {
      $scope.resultCard = result;
    });

    $scope.print = function () {
      $window.location = config.apiUrl + '/students/studentResultCards.pdf?' + $httpParamSerializer({studentIds: id});
    };
  }
]);