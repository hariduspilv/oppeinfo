'use strict';

angular.module('hitsaOis').config(function ($routeProvider) {
  $routeProvider
    .when('/generalmessages', {
      templateUrl: 'views/general.message.list.html',
      controller: 'GeneralMessageSearchController',
      controllerAs: 'controller'
    })
    .when('/generalmessages/new', {
        templateUrl: 'views/general.message.edit.html',
        controller: 'GeneralMessageEditController',
        controllerAs: 'controller'
    })
    .when('/generalmessages/:id', {
        templateUrl: 'views/general.message.edit.html',
        controller: 'GeneralMessageEditController',
        controllerAs: 'controller'
    });
}).controller('GeneralMessageSearchController', function ($scope, $sessionStorage, Classifier, QueryUtils) {
  $scope.roleDefs = Classifier.query({mainClassCode: 'ROLL'});
  $scope.fromStorage = function(key) {
    var criteria = JSON.parse($sessionStorage[key] || '{}');
    if(criteria.validFrom) {
      criteria.validFrom = new Date(Date.parse(criteria.validFrom));
    }
    if(criteria.validThru) {
      criteria.validThru = new Date(Date.parse(criteria.validThru));
    }
    return criteria;
  };

  var now = new Date();
  now.setUTCHours(0, 0, 0, 0);
  QueryUtils.createQueryForm($scope, '/generalmessages', {validFrom: now}, function() {
    var rows = $scope.tabledata.content;
    var roleMapper = function(i) { return $scope.roleDefs[i]; };
    for(var rowNo = 0; rowNo < rows.length; rowNo++) {
      var row = rows[rowNo];
      if(row.validFrom) {
        row.validFrom = new Date(Date.parse(row.validFrom));
      }
      if(row.validThru) {
        row.validThru = new Date(Date.parse(row.validThru));
      }
      row.targets = (row.targets || []).map(roleMapper);
    }
  });
  $scope.getCriteria = function() {
    var criteria = QueryUtils.getQueryParams($scope.criteria);
    criteria.targets = (criteria._targets || []).map(function (i) { return i.code; });
    delete criteria._targets;
    return criteria;
  };

  $scope.roleDefs.$promise.then(function() {
    $scope.roleDefs = Classifier.toMap($scope.roleDefs.content);
    $scope.loadData();
  });
}).controller('GeneralMessageEditController', function ($location, $route, $scope, message, Classifier, QueryUtils) {
  $scope.roleDefs = Classifier.query({mainClassCode: 'ROLL'});

  var baseUrl = '/generalmessages';
  var Endpoint = QueryUtils.endpoint(baseUrl);

  var afterLoad = function() {
    if($scope.record.validFrom) {
      $scope.record.validFrom = new Date(Date.parse($scope.record.validFrom));
    }
    if($scope.record.validThru) {
      $scope.record.validThru = new Date(Date.parse($scope.record.validThru));
    }

    $scope.roleDefs.$promise.then(function() {
      $scope.roleDefs.content = $scope.roleDefs.content.filter(function(i) { return i.code !== 'ROLL_H'; });
      Classifier.setSelectedCodes($scope.roleDefs.content, $scope.record.targets || []);
    });
  };

  var id = $route.current.params.id;
  if(id) {
    $scope.record = Endpoint.get({id: id}, afterLoad);
  } else {
    // new general message
    var now = new Date();
    now.setUTCHours(0, 0, 0, 0);
    $scope.record = new Endpoint({validFrom: now, validThru: null});
    afterLoad();
  }

  $scope.isFormValid = function() {
	return $scope.generalMessageForm.$valid;
  };

  $scope.beforeSave = function() {
    $scope.record.targets = Classifier.getSelectedCodes($scope.roleDefs.content);
  };

  $scope.update = function() {
    if(!$scope.isFormValid()) {
      message.error('main.messages.form-has-errors');
      return;
    }

    $scope.beforeSave();

    if($scope.record.id) {
      $scope.record.$update(afterLoad).then(function() {
        message.info('main.messages.update.success');
        $location.path(baseUrl);
      });
    }else{
      $scope.record.$save(afterLoad).then(function() {
        message.info('main.messages.create.success');
        $location.path(baseUrl);
      });
    }
  };

  $scope.delete = function() {
    $scope.record.$delete().then(function() {
      message.info('main.messages.delete.success');
      $location.path(baseUrl);
    });
  };
});
