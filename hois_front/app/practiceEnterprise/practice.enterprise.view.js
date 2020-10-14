'use strict';

angular.module('hitsaOis').controller('PracticeEnterpriseViewController', ['$route', '$scope', 'QueryUtils', 'message', function ($route, $scope, QueryUtils, message) {
    $scope.currentNavItem = 'enterprise.data';
    $scope.auth = $route.current.locals.auth;
    $scope.viewForm = true;
    var id = $route.current.params.id;
    var baseUrl = '/practiceEnterprise';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    $scope.enterprise = Endpoint.get({id: id});
    $scope.enterprise.$promise.then(function (enterpriseResponse) {
        window.localStorage.setItem('enterpriseName', enterpriseResponse.name);
        if (enterpriseResponse.regCode !== null) {
            window.localStorage.setItem('enterpriseRegCode', enterpriseResponse.regCode);
          } else {
            window.localStorage.removeItem('enterpriseRegCode');
          }
        if (enterpriseResponse.application) {
            window.localStorage.setItem('enterpriseApplication', enterpriseResponse.application);
        } else {
            window.localStorage.removeItem('enterpriseApplication');
        }
    });

    $scope.update = function() {
        if ($scope.enterprise.country === 'RIIK_EST' && $scope.enterprise.regCode && !$scope.enterprise.person) {
            QueryUtils.loadingWheel($scope, true);
            QueryUtils.endpoint(baseUrl + '/updateRegCode/' + id).get(
            {
                country: $scope.enterprise.country,
                regCode: $scope.enterprise.regCode,
                language: $scope.enterprise.language,
                active: $scope.enterprise.active,
                enterpriseSchoolId: $scope.enterprise.enterpriseSchoolId
            }
            ).$promise.then(function (response) {
                QueryUtils.loadingWheel($scope, false);
                if (response.status === undefined || response.status === null) {
                    $scope.enterprise = response;
                } else {
                    message.error(response.status);
                }
            }).catch(angular.noop);
        }
    };

}]);