'use strict';

angular.module('hitsaOis').controller('StateCurriculumListController', ['$scope', '$sessionStorage', 'Classifier', 'QueryUtils', '$q', 'DataUtils', function ($scope, $sessionStorage, Classifier, QueryUtils, $q, DataUtils) {
    var clMapper = Classifier.valuemapper({status: 'OPPEKAVA_STAATUS', ekrLevel: 'EKR'});
    QueryUtils.createQueryForm($scope, '/stateCurriculum', {order: $scope.currentLanguageNameField()}, clMapper.objectmapper);
    $q.all(clMapper.promises).then($scope.loadData);
    $scope.filteredOutStatuses = [{code: 'OPPEKAVA_STAATUS_M'}];
    DataUtils.convertStringToDates($scope.criteria, ['validFrom', 'validThru']);

    $scope.formState = {};

    QueryUtils.endpoint('/stateCurriculum/canCreate').search().$promise.then(function(response){
      $scope.formState.canCreate = response.canCreate;
    });

    QueryUtils.endpoint('/stateCurriculum/canView').search().$promise.then(function(response){
      $scope.formState.canView = response.canView;
    });
}]);
