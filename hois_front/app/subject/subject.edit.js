'use strict';

angular.module('hitsaOis').controller('SubjectEditController',
  function ($scope, $route, $resource, $location, $translate, $q, config, dialogService, message, QueryUtils) {
    var id = $route.current.params.id;

    $resource(config.apiUrl+'/subject/initEditFormData').get().$promise.then(function(result) {
      angular.extend($scope, result.toJSON());
    });

    var Endpoint = QueryUtils.endpoint('/subject');
    if(id) {
      $scope.subject = Endpoint.get({id: id});
    } else {
      $scope.subject = new Endpoint();
      $scope.subject.status = 'AINESTAATUS_S';
    }

    $scope.update = function() {
      $scope.subjectForm.$setSubmitted();
      if($scope.subjectForm.$valid) {
        if($scope.subject.id) {
          $scope.subject.$update().then(function() {
            message.info('main.messages.update.success');
          });
        } else {
          $scope.subject.$save().then(function(response) {
            $location.path('/subject/'+ response.id +'/edit');
            message.info('main.messages.create.success');
          });
        }
      }
    };

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'subject.deleteconfirm'}, function() {
        $scope.subject.$delete().then(function() {
          message.info('main.messages.delete.success');
          $location.path( '/subject');
        });
      });
    };
  });
