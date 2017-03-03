'use strict';

/*
Code below can be used if new one is not working properly
TODO: delete it after 15.03.2017
*/

// angular.module('hitsaOis')
//   .controller('StateCurriculumListController', function (QueryUtils, $scope, Classifier, $resource, config) {

//     $scope.criteria = {
//       order: $scope.currentLanguageNameField(),
//       size: 10,
//       page: 1,
//       name: ''
//     };
//     var clMapper = Classifier.valuemapper({status: 'OPPEKAVA_STAATUS', ekrLevel: 'EKR'});

//     $scope.clearCriteria = function() {
//       QueryUtils.clearQueryParams($scope.criteria);
//     };

//     $scope.loadData = function () {
//       var resource = $resource(config.apiUrl + '/stateCurriculum');
//       var queryParams = QueryUtils.getQueryParams($scope.criteria);
//       $scope.tabledata = {};
//       $scope.tabledata.$promise = resource.get(queryParams).$promise.then(function(response){
//         $scope.tabledata.content = clMapper.objectmapper(response.content);
//         $scope.tabledata.totalElements = response.totalElements;
//       });
//     };

//     $scope.loadData();

//     $scope.filteredOutStatuses = [{code: 'OPPEKAVA_STAATUS_M'}];
//   });


angular.module('hitsaOis').controller('StateCurriculumListController', ['$scope', '$sessionStorage', 'Classifier', 'DataUtils', 'QueryUtils', '$q', function ($scope, $sessionStorage, Classifier, DataUtils, QueryUtils, $q) {
    var clMapper = Classifier.valuemapper({status: 'OPPEKAVA_STAATUS', ekrLevel: 'EKR'});
    QueryUtils.createQueryForm($scope, '/stateCurriculum', {order: $scope.currentLanguageNameField()}, clMapper.objectmapper);
    $q.all(clMapper.promises).then($scope.loadData);
    $scope.filteredOutStatuses = [{code: 'OPPEKAVA_STAATUS_M'}];
}]);