'use strict';

angular.module('hitsaOis').controller('StudentScholarshipController', ['$route', '$scope', '$q', 'Classifier', 'QueryUtils', 'dialogService',
  function ($route, $scope, $q, Classifier, QueryUtils, dialogService) {
    var baseUrl = '/scholarships';
    var drGrant = $route.current.locals.drGrant;
    var clMapper = Classifier.valuemapper({
      type: 'STIPTOETUS', status: 'STIPTOETUS_STAATUS'
    });
    $q.all(clMapper.promises).then(function () {
      var available = QueryUtils.endpoint(baseUrl + (drGrant ? '/availableDrGrants' : '/availableStipends')).query({}, function (result) {
        clMapper.objectmapper(result);
        $scope.possibleStipends = result;
      }).$promise;
      var student = QueryUtils.endpoint(baseUrl + (drGrant ? '/studentDrGrants' : '/studentStipends')).query({}, function (result) {
        clMapper.objectmapper(result);
        $scope.studentStipends = result;
      }).$promise;
      $q.all([available, student]).then(function () {
        $scope.possibleStipends.forEach(function (possible) {
          var match = $scope.studentStipends.find(function (application) {
            return application.termId === possible.id;
          });
          possible.application = match;
          possible.canApply = possible.termCompliance.fullyComplies ? !angular.isDefined(match) : false;
          possible.alreadyApplied = !angular.isDefined(match) ? false : true;
        });
      });
    });

    $scope.scholarshipTerms = function (stipend) {
      dialogService.showDialog('scholarship/templates/scholarship.term.dialog.html', function (dialogScope) {
        dialogScope.stipend = stipend;
      });
    };

    $scope.scholarshipTermCompliances = function (stipend) {
      dialogService.showDialog('scholarship/templates/scholarship.term.compliance.dialog.html', function (dialogScope) {
        dialogScope.stipend = stipend;
      });
    };
  }
]);
