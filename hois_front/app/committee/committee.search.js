'use strict';

angular.module('hitsaOis').controller('CommitteeSearchController', ['$scope', 'QueryUtils', function ($scope, QueryUtils) {

  var baseUrl = "/committees";
  QueryUtils.createQueryForm($scope, baseUrl, {order: 'c.id'});
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

}]);
