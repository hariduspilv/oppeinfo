'use strict';

angular.module('hitsaOis').controller('StateCurriculumListController', ['$scope', '$sessionStorage', '$route', 'Classifier', 'QueryUtils', '$q', 'DataUtils', 
  function ($scope, $sessionStorage, $route, Classifier, QueryUtils, $q, DataUtils) {
    $scope.isPublic = $route.current.locals.params && $route.current.locals.params.isPublic;
    var clMapper = Classifier.valuemapper({status: 'OPPEKAVA_STAATUS', ekrLevel: 'EKR', stateCurrClass: 'EHIS_ROK'});
    QueryUtils.createQueryForm($scope, $scope.isPublic ? '/public/statecurriculumsearch' : '/stateCurriculum', 
      {order: $scope.currentLanguageNameField()}, clMapper.objectmapper);
    $q.all(clMapper.promises).then($scope.loadData);
    $scope.filteredOutStatuses = [{code: 'OPPEKAVA_STAATUS_M'}];
    DataUtils.convertStringToDates($scope.criteria, ['validFrom', 'validThru']);
    $scope.auth = $route.current.locals.auth;
    
    if ($scope.isPublic) {
      $scope.formState = {
        canCreate: false,
        canView: false
      };
    } else {
      $scope.formState = {};
      QueryUtils.endpoint('/stateCurriculum/canCreate').search().$promise.then(function(response){
        $scope.formState.canCreate = response.canCreate;
      });
      QueryUtils.endpoint('/stateCurriculum/canView').search().$promise.then(function(response){
        $scope.formState.canView = response.canView;
      });
    }
}]);
