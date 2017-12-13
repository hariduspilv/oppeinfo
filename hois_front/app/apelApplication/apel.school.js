'use strict';

angular.module('hitsaOis').controller('ApelSchoolListController', function ($scope, QueryUtils, $q) {

  QueryUtils.createQueryForm($scope, '/apelSchool', {
    order: $scope.currentLanguage() === 'en' ? 'nameEn' : 'nameEt'
  });
  $q.all().then($scope.loadData);

  $scope.clearCriteria = function () {
    $scope.criteria = {};
  };
  
}).controller('ApelSchoolEditController', function ($scope, $route, dialogService, message, QueryUtils, $location, Classifier) {
    var id = $route.current.params.id;
    var baseUrl = '/apelSchool';
    var Endpoint = QueryUtils.endpoint(baseUrl);

    if (id) {
      $scope.apelSchool = Endpoint.get({id: id});
    } else {
      $scope.apelSchool = new Endpoint();
    }

    $scope.update = function() {
      $scope.apelSchoolForm.$setSubmitted();
      if(!$scope.apelSchoolForm.$valid) {
        message.error('main.messages.form-has-errors');
        return;
      }

      if($scope.apelSchool.id) {
        $scope.apelSchool.$update().then(message.updateSuccess);
      } else {
        $scope.apelSchool.$save().then(function() {
          message.info('main.messages.create.success');
          $location.url('apelSchools/' + $scope.apelSchool.id + '/edit');
        });
      }
    };

    $scope.ehisSchoolChanged = function (ehisCode) {
      Classifier.get(ehisCode).$promise.then(function (ehisSchool) {
        $scope.apelSchool.nameEt = ehisSchool.nameEt;
        $scope.apelSchool.nameEn = ehisSchool.nameEn;
      });
    };

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'apel.apelSchool.deleteConfirm'}, function() {
        $scope.apelSchool.$delete().then(function() {
          message.info('main.messages.delete.success');
          $location.url('/apelSchools');
        });
      });
    };
    
});
