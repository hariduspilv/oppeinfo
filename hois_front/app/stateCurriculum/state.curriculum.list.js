'use strict';

angular.module('hitsaOis').controller('StateCurriculumListController', ['$scope', '$sessionStorage', 'Classifier', 'QueryUtils', '$q', function ($scope, $sessionStorage, Classifier, QueryUtils, $q) {
    var clMapper = Classifier.valuemapper({status: 'OPPEKAVA_STAATUS', ekrLevel: 'EKR'});
    QueryUtils.createQueryForm($scope, '/stateCurriculum', {order: $scope.currentLanguageNameField()}, clMapper.objectmapper);
    $q.all(clMapper.promises).then($scope.loadData);
    $scope.filteredOutStatuses = [{code: 'OPPEKAVA_STAATUS_M'}];
}]);