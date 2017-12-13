'use strict';

angular.module('hitsaOis').controller('CommitteeEditViewController', ['$scope', 'dialogService', 'QueryUtils', 'message', 'ArrayUtils', '$route', '$location', 'orderByFilter', 'DataUtils', '$rootScope', function ($scope, dialogService, QueryUtils, message, ArrayUtils, $route, $location, orderBy, DataUtils, $rootScope) {

  var baseUrl = "/committees";
  var id = $route.current.params.id;

  var Endpoint = QueryUtils.endpoint(baseUrl);

  function afterload() {
    DataUtils.convertStringToDates($scope.record, ['validFrom', 'validThru']);
    $scope.record.members = orderBy($scope.record.members, ['memberName']);

    if($scope.committeeEditForm) {
      $scope.committeeEditForm.$setPristine();
    }
  }

  $rootScope.removeLastUrlFromHistory(function(lastUrl){
    return lastUrl && (lastUrl.indexOf('committees/' + id + '/view') !== -1 || lastUrl.indexOf('committees/new') !== -1);
  });

  if(id) {
    $scope.record = Endpoint.get({id: id}, afterload);
  } else {
    $scope.record = new Endpoint({members: []});
  }

  $scope.addMember = function() {
    $scope.record.members.push({isExternal: false, isChairman: false});
    $scope.committeeEditForm.$setDirty();
  };

  $scope.removeMember = function(member) {
    ArrayUtils.remove($scope.record.members, member);
    $scope.committeeEditForm.$setDirty();
  };

  function hasOneChairman() {
    return $scope.record.members.filter(isChairman).length === 1;
  }

  function isChairman(member) {
    return member.isChairman;
  }

  $scope.assingChairman = function(member) {
    if(member.isChairman) {
      $scope.record.members.forEach(function(m){
        if(m !== member) {
          m.isChairman = false;
        }
      });
    }
  };

  function formValid() {
    $scope.committeeEditForm.$setSubmitted();
    if(!$scope.committeeEditForm.$valid) {
      message.error("main.messages.form-has-errors");
      return false;
    }
    if(!hasOneChairman()) {
      message.error("committee.error.chairman");
      return false;
    }
    return true;
  }
  // TODO: release buttons after failure
  $scope.save = function() {
    if(!formValid()) {
      return;
    }
    if($scope.record.id) {
      $scope.record.$update().then(function(response){
        message.info('main.messages.update.success');
        $scope.record = response;
        afterload();
      });
    } else {
      $scope.record.$save().then(function(response){
        message.info('main.messages.create.success');
        $location.path(baseUrl + "/" + response.id + "/edit");
      });
    }
  };

  $scope.delete = function() {
    dialogService.confirmDialog({prompt: 'committee.prompt.deleteconfirm'}, function() {
      $scope.record.$delete().then(function(){
        message.info('committee.message.deleted');
        $location.path(baseUrl);
      });
    });
  };

}]);
