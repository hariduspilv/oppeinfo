'use strict';

angular.module('hitsaOis').controller('CommitteeSearchController', 
function ($scope, $route, QueryUtils, USER_ROLES, AuthService, FormUtils) {
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

  var _loadData = $scope.loadData;
  $scope.loadData = function() {
    if ($scope.searchForm) {
      FormUtils.withValidForm($scope.searchForm, _loadData);
    } else {
      _loadData();
    }
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
});
