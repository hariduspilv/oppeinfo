'use strict';

angular.module('hitsaOis').controller('GeneralMessageSearchController', ['$scope', '$sessionStorage', 'Classifier', 'DataUtils', 'QueryUtils', function ($scope, $sessionStorage, Classifier, DataUtils, QueryUtils) {
  $scope.roleDefs = Classifier.queryForDropdown({mainClassCode: 'ROLL'});
  $scope.fromStorage = function(key) {
    var criteria = JSON.parse($sessionStorage[key] || '{}');
    DataUtils.convertStringToDates(criteria, ['validFrom', 'validThru']);
    return criteria;
  };

  var now = new Date();
  now.setHours(0, 0, 0, 0);
  QueryUtils.createQueryForm($scope, '/generalmessages', {validFrom: now, order: 'validFrom'}, function(rows) {
    var roleMapper = function(i) { return $scope.roleDefs[i]; };
    for(var rowNo = 0; rowNo < rows.length; rowNo++) {
      var row = rows[rowNo];
      DataUtils.convertStringToDates(row, ['validFrom', 'validThru']);
      row.targets = (row.targets || []).map(roleMapper);
    }
  });

  $scope.roleDefs.$promise.then(function() {
    $scope.roleDefs = Classifier.toMap($scope.roleDefs);
    $scope.loadData();
  });
}]).controller('GeneralMessageEditController', ['$location', '$route', '$scope', 'dialogService', 'message', 'Classifier', 'DataUtils', 'QueryUtils',
  function ($location, $route, $scope, dialogService, message, Classifier, DataUtils, QueryUtils) {
    var id = $route.current.params.id;
    var baseUrl = '/generalmessages';

    function afterLoad() {
      DataUtils.convertStringToDates($scope.record, ['validFrom', 'validThru']);
      Classifier.setSelectedCodes($scope.roleDefs, $scope.record.targets || []);
    }

    $scope.roleDefs = Classifier.queryForDropdown({mainClassCode: 'ROLL'}, function() {
      $scope.roleDefs = $scope.roleDefs.filter(function(i) { return i.code !== 'ROLL_H'; });

      var Endpoint = QueryUtils.endpoint(baseUrl);
      if(id) {
        $scope.record = Endpoint.get({id: id}, afterLoad);
      } else {
        // new general message
        var now = new Date();
        now.setHours(0, 0, 0, 0);
        $scope.record = new Endpoint({validFrom: now, validThru: null});
        afterLoad();
      }
    });

    $scope.isFormValid = function() {
      return $scope.generalMessageForm.$valid;
    };

    $scope.update = function() {
      $scope.generalMessageForm.$setSubmitted();
      if(!$scope.isFormValid()) {
        message.error('main.messages.form-has-errors');
        return;
      }

      $scope.record.targets = Classifier.getSelectedCodes($scope.roleDefs);

      function afterSave() {
        message.info(id ? 'main.messages.update.success' : 'main.messages.create.success');
        if(!id) {
          $location.path(baseUrl + '/' + $scope.record.id + '/edit');
        }
      }
      if($scope.record.id) {
        $scope.record.$update(afterLoad).then(afterSave);
      }else{
        $scope.record.$save(afterLoad).then(afterSave);
      }
    };

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'generalmessage.deleteconfirm'}, function() {
        $scope.record.$delete().then(function() {
          message.info('main.messages.delete.success');
          $location.path(baseUrl);
        });
      });
    };
  }
]);
