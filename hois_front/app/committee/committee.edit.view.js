'use strict';

angular.module('hitsaOis').controller('CommitteeEditViewController', ['$scope', 'dialogService', 'QueryUtils', 'message', 'ArrayUtils', '$route', '$location', 'orderByFilter', 'DataUtils', function ($scope, dialogService, QueryUtils, message, ArrayUtils, $route, $location, orderBy, DataUtils) {

  var baseUrl = "/committees";
  var id = $route.current.params.id;

  $scope.formState = {
    waitingResponse: false
  };

  var Endpoint = QueryUtils.endpoint(baseUrl);

  function afterload() {
    releaseButtons();
    DataUtils.convertStringToDates($scope.record, ['validFrom', 'validThru']);
    $scope.record.members = orderBy($scope.record.members, ['memberName']);
  }

  if(id) {
    $scope.record = Endpoint.get({id: id}, afterload);
  } else {
    $scope.record = new Endpoint({members: []});
  }

  function releaseButtons() {
    $scope.formState.waitingResponse = false;
  }

    function blockButtons() {
    $scope.formState.waitingResponse = true;
  }

  $scope.addMember = function() {
    $scope.record.members.push({isExternal: false, isChairman: false});
  };

  $scope.removeMember = function(member) {
    ArrayUtils.remove($scope.record.members, member);
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
    blockButtons();
    if($scope.record.id) {
      $scope.record.$update().then(function(response){
        message.info('main.messages.update.success');
        $scope.record = response;
        afterload();
      }, releaseButtons);
    } else {
      $scope.record.$save().then(function(response){
        message.info('main.messages.create.success');
        $location.path(baseUrl + "/" + response.id + "/edit");
      }, releaseButtons);
    }
  };

  $scope.delete = function() {
    dialogService.confirmDialog({prompt: 'committee.prompt.deleteconfirm'}, function() {
      blockButtons();
      $scope.record.$delete().then(function(){
        message.info('committee.message.deleted');
        $location.path(baseUrl);
      }, releaseButtons);
    });
  };

}]);
