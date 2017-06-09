'use strict';

angular.module('hitsaOis').controller('ModuleProtocolController', function ($scope, $route, Classifier, $q, ArrayUtils, QueryUtils, message, $location) {
  $scope.auth = $route.current.locals.auth;
  var clMapper = Classifier.valuemapper({ status: 'PROTOKOLL_STAATUS' });

  function entityToDto(entity) {
    if (entity) {
      $q.all(clMapper.promises).then(function () {
        $scope.protocol = clMapper.objectmapper(entity);
      });
    }
  }
  entityToDto($route.current.locals.entity);

  $scope.grades = Classifier.queryForDropdown({ mainClassCode: 'KUTSEHINDAMINE' });
  $scope.deleteProtocolStudent = function (protocolStudent) {
    ArrayUtils.remove($scope.protocol.protocolStudents, protocolStudent);
  };

  var ModuleProtocolEndpoint = QueryUtils.endpoint('/moduleProtocols');
  $scope.save = function () {
    if (angular.isNumber($scope.protocol.id)) {
      new ModuleProtocolEndpoint({ id: $scope.protocol.id, version: $scope.protocol.version, protocolStudents: $scope.protocol.protocolStudents })
        .$update().then(function (result) {
          message.info('main.messages.create.success');
          entityToDto(result);
        });
    } else {
      new ModuleProtocolEndpoint({protocolNr: $scope.protocol.protocolNr, protocolStudents: $scope.protocol.protocolStudents, protocolVdata: $scope.protocol.protocolVdata})
        .$save().then(function (result) {
          message.info('main.messages.create.success');
          $location.path('/moduleProtocols/' + result.id + '/edit');
        });
    }
  };

});
