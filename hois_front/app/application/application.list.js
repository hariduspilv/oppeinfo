'use strict';


angular.module('hitsaOis').controller('ApplicationListController', function ($rootScope, $scope, QueryUtils, Session) {
    var Endpoint = QueryUtils.endpoint('/school');
    $scope.school = Endpoint.get({id: Session.school.id});
});
