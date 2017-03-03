'use strict';

angular.module('hitsaOis').controller('TeacherOccupationEditController', ['$location', '$route', '$scope', 'dialogService', 'message', 'QueryUtils',

  function ($location, $route, $scope, dialogService, message, QueryUtils) {
    var id = $route.current.params.id;

    var baseUrl = '/school/teacheroccupations';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    if(id) {
      $scope.teacherOccupation = Endpoint.get({id: id});
    } else {
      // new occupation
      $scope.teacherOccupation = new Endpoint({occupationEt: '', occupationEn: '', isValid: true});
    }

    $scope.update = function() {
      if(!$scope.teacherOccupationForm.$valid) {
        message.error('main.messages.form-has-errors');
        return;
      }
      var msg = $scope.teacherOccupation.id ? 'main.messages.update.success' : 'main.messages.create.success';
      function afterSave() {
        message.info(msg);
      }
      if($scope.teacherOccupation.id) {
        $scope.teacherOccupation.$update().then(afterSave);
      }else{
        $scope.teacherOccupation.$save().then(afterSave);
      }
    };

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'teacher.occupation.deleteconfirm'}, function() {
        $scope.teacherOccupation.$delete().then(function() {
          message.info('main.messages.delete.success');
          $location.path(baseUrl);
        });
      });
    };
}]).controller('TeacherOccupationListController', ['$scope', 'QueryUtils', function ($scope, QueryUtils) {
  QueryUtils.createQueryForm($scope, '/school/teacheroccupations', {order: 'occupationEt'});

  $scope.loadData();
}]);
