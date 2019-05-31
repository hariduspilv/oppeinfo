'use strict';

angular.module('hitsaOis').controller('CommitteeSearchController', 
function ($scope, $route, QueryUtils, USER_ROLES, AuthService) {
  $scope.canEdit = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_KOMISJON);

  var baseUrl = "/committees";
  $scope.committeeType = $route.current.params.type;
  $scope.showName = ['KOMISJON_T', 'KOMISJON_V', 'KOMISJON_A'].indexOf($scope.committeeType) !== -1;
  $scope.showPersonSearch = ['KOMISJON_T', 'KOMISJON_V', 'KOMISJON_A'].indexOf($scope.committeeType) !== -1;

  QueryUtils.createQueryForm($scope, baseUrl, {type: $scope.committeeType, order: 'c.id'});
  var _clearCriteria = $scope.clearCriteria;
  $scope.clearCriteria = function() {
    _clearCriteria();
    $scope.criteria.type = $scope.committeeType;
  };
  $scope.loadData();

  var savedMember;
  if($scope.criteria) {
    savedMember = $scope.criteria.member;
  }

  $scope.members = QueryUtils.endpoint(baseUrl + '/members').query(function() {
    if(savedMember) {
      $scope.criteria.member = $scope.members.find(function(m){
        return m.id === savedMember.id && m.nameEt === savedMember.nameEt;
      });
    }
  });

  $scope.$watch('criteria.member', function(){
    if($scope.criteria.member) {
      if($scope.criteria.member.id) {
        $scope.criteria.teacher = $scope.criteria.member.id;
        $scope.criteria.memberName = null;
      } else {
        $scope.criteria.teacher = null;
        $scope.criteria.memberName = $scope.criteria.member.nameEt;
      }
    } else {
      $scope.criteria.teacher = null;
      $scope.criteria.memberName = null;
    }
  });
});
