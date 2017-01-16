'use strict';

angular.module('hitsaOis').controller('SubjectEditController', function ($scope, $route, $resource, $location, config, message, QueryUtils) {
    var id = $route.current.params.id;

    $resource(config.apiUrl+'/subject/initEditFormData').get().$promise.then(function(result) {
      angular.extend($scope, result.toJSON());
    });

    var Endpoint = QueryUtils.endpoint('/subject');
    if(id) {
      $scope.subject = Endpoint.get({id: id});
    } else {
      $scope.subject = new Endpoint();
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
      $scope.subject.$delete().then(function() {
        $location.path( '/subject');
      });
    };
  });
