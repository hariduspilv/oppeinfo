(function() {
  'use strict';

  angular
    .module('hitsaOis')
    .controller('MidtermResultSearchController', MidtermResultSearchController)
    .controller('MidtermResultViewController', MidtermResultViewController);

  MidtermResultSearchController.$inject = ['$scope', 'QueryUtils'];
  function MidtermResultSearchController($scope, QueryUtils) {
    QueryUtils.createQueryForm($scope, '/midtermResult', {order: $scope.currentLanguage() === 'en' ? 's.nameEn,s.code' : 's.nameEt,s.code'}, undefined, true, true);
    $scope.$watch('criteria.studyPeriod', function (newId, oldId) {
      if (!newId || newId === oldId) {
        return;
      }
      $scope.loadData();
    });

    var _loadData = $scope.loadData;
    $scope.loadData = function () {
      if (!$scope.criteria.studyPeriod) {
        return;
      }
      _loadData();
    };
  }

  MidtermResultViewController.$inject = ['$scope', 'QueryUtils', '$route', 'message'];
  function MidtermResultViewController($scope, QueryUtils, $route, message) {
    var declarationSubjectId = parseInt($route.current.params.id);

    $scope.order = '-midtermDate';

    if (!declarationSubjectId || isNaN(declarationSubjectId)) {
      message.error('main.messages.error.nopermission');
      $scope.back('#/midtermResult');
      return;
    }

    $scope.declarationSubject = QueryUtils.endpoint('/midtermResult').get({id: declarationSubjectId});
  }
})();