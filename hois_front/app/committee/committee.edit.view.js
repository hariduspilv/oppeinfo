'use strict';

angular.module('hitsaOis').controller('CommitteeEditViewController', ['$scope', 'dialogService', 'QueryUtils', 'message', 'ArrayUtils', '$route', '$location', 'orderByFilter', 'DataUtils', '$rootScope', '$q', 'Classifier', 'ScholarshipUtils',
function ($scope, dialogService, QueryUtils, message, ArrayUtils, $route, $location, orderBy, DataUtils, $rootScope, $q, Classifier, ScholarshipUtils) {

  var baseUrl = "/committees";
  var id = $route.current.params.id;
  $scope.committeeType = $route.current.params.type;
  $scope.showName = ['KOMISJON_T', 'KOMISJON_V', 'KOMISJON_A'].indexOf($scope.committeeType) !== -1;
  $scope.showPersonSearch = ['KOMISJON_T', 'KOMISJON_V', 'KOMISJON_A'].indexOf($scope.committeeType) !== -1;
  $scope.allowExternalPerson = ['KOMISJON_A', 'KOMISJON_K'].indexOf($scope.committeeType) !== -1;
  $scope.formState = {};

  var Endpoint = QueryUtils.endpoint(baseUrl);

  var clMapper = Classifier.valuemapper({
    scholarshipType: 'STIPTOETUS'
  });
  function afterload() {
    DataUtils.convertStringToDates($scope.record, ['validFrom', 'validThru']);
    $scope.record.members = orderBy($scope.record.members, ['memberName']);
    $q.all(clMapper.promises).then(function () {
      if ($scope.record.scholarshipDecisions) {
        clMapper.objectmapper($scope.record.scholarshipDecisions);
      }
    });

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
    $scope.record = new Endpoint({type: $scope.committeeType, members: []});
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
      }).catch(angular.noop);
    } else {
      $scope.record.$save().then(function(response){
        message.info('main.messages.create.success');
        $location.url(baseUrl + "/" + $scope.committeeType + "/" + response.id + "/edit?_noback");
      }).catch(angular.noop);
    }
  };

  $scope.delete = function() {
    dialogService.confirmDialog({prompt: 'committee.prompt.deleteconfirm'}, function() {
      $scope.record.$delete().then(function(){
        message.info('committee.message.deleted');
        $location.path(baseUrl + "/" + $scope.committeeType);
      });
    });
  };

  $scope.curriculumSelected = function() {
    if ($scope.formState.curriculum) {
      if (!angular.isArray($scope.record.curriculums)) {
        $scope.record.curriculums = [];
      }
      if ($scope.record.curriculums.some(function (e) {
          return e.id === $scope.formState.curriculum.id;
        })) {
        message.error('committee.error.duplicateCurriculum');
        $scope.formState.curriculum = undefined;
        return;
      }
      $scope.record.curriculums.push($scope.formState.curriculum);
      $scope.formState.curriculum = undefined;
    }
  };

  $scope.deleteCurriculum = function (curriculum) {
    var index = $scope.record.curriculums.indexOf(curriculum);
    if (index !== -1) {
      $scope.record.curriculums.splice(index, 1);
    }
  };

  $scope.getScholarshipTypeForUrl = ScholarshipUtils.getScholarshipTypeGroup;

}]);
