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
      $scope.teacherOccupationForm.$setSubmitted();
      if(!$scope.teacherOccupationForm.$valid) {
        message.error('main.messages.form-has-errors');
        return;
      }

      if($scope.teacherOccupation.id) {
        $scope.teacherOccupation.$update().then(message.updateSuccess);
      }else{
        $scope.teacherOccupation.$save().then(function() {
          message.info('main.messages.create.success');
          $location.url(baseUrl + '/' + $scope.teacherOccupation.id + '/edit');
        });
      }
    };

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'teacher.occupation.deleteconfirm'}, function() {
        $scope.teacherOccupation.$delete().then(function() {
          message.info('main.messages.delete.success');
          $location.url(baseUrl);
        });
      });
    };
}]);
