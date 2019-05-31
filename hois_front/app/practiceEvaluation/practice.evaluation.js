'use strict';

angular.module('hitsaOis').controller('PracticeEvaluationSearchController', ['$q', '$scope', 'dialogService', 'message', 'Classifier', 'QueryUtils',
  function ($q, $scope, dialogService, message, Classifier, QueryUtils) {
    var baseUrl = '/practiceEvaluation';

    $scope.criteria = { isActive: true };
    var clMapper = Classifier.valuemapper({target: 'PRAKTIKA_SIHTRYHM'});
    QueryUtils.createQueryForm($scope, baseUrl, {order: 'pe.name_et', isActive: $scope.criteria.isActive}, clMapper.objectmapper);

    $q.all(clMapper.promises).then($scope.loadData);
  }
]).controller('PracticeEvaluationEditController', ['$scope', '$route', '$location', 'dialogService', 'message', 'QueryUtils', 'Classifier', 'FormUtils', 'ArrayUtils',
  function ($scope, $route, $location, dialogService, message, QueryUtils, Classifier, FormUtils, ArrayUtils) {
    var baseUrl = '/practiceEvaluation';
    var id = $route.current.params.id;  
    $scope.auth = $route.current.locals.auth;

    Classifier.queryForDropdown({mainClassCode: 'PRAKTIKA_KRITEERIUM'}).$promise.then(function(result) {
      $scope.criteriaMap = Classifier.toMap(result);
    });

    function afterLoad() {
      $scope.record.criteria.sort(function(el1, el2){
        return el1.orderNr - el2.orderNr;
      });
    }

    var Endpoint = QueryUtils.endpoint(baseUrl);
    if (id) {
      $scope.record = Endpoint.get({id: id});
      $scope.record.$promise.then(afterLoad);
    } else {
      $scope.record = new Endpoint({ isActive: true, criteria: [] });
      afterLoad();
    }

    $scope.update = function() {
      FormUtils.withValidForm($scope.practiceEvaluationForm, function() {
        if ($scope.record.id) {
          $scope.record.$update().then(function () {
            message.updateSuccess();
            afterLoad();
            $scope.practiceEvaluationForm.$setPristine();
          }).catch(angular.noop);
        } else {
          $scope.record.$save().then(function() {
            message.info('main.messages.create.success');
            $location.url('/practice/evaluation/' + $scope.record.id + '/edit?_noback');
          }).catch(angular.noop);
        }
      });
    };

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'practiceEvaluation.deleteconfirm'}, function() {
        $scope.record.$delete().then(function() {
          message.info('main.messages.delete.success');
          $location.url('/practice/evaluation');
        });
      });
    };

    function areRequiredCriteriaFieldsFilled() {
      return $scope.criteria.nameEt && $scope.criteria.type;
    }
    
    $scope.isEditingCriteria = false;
    $scope.editedCriteria = null;
    $scope.criteria = {};
    $scope.addCriteria = function() {
      if (areRequiredCriteriaFieldsFilled()) {
        $scope.criteria.orderNr = $scope.record.criteria.length;
        $scope.record.criteria.push($scope.criteria);
        $scope.criteria = {};
      }
    };

    $scope.swapCriteria = function(index1, index2) {
      var temp = $scope.record.criteria[index1];
      $scope.record.criteria[index1] = $scope.record.criteria[index2];
      $scope.record.criteria[index2] = temp;
      temp = $scope.record.criteria[index1].orderNr;
      $scope.record.criteria[index1].orderNr = $scope.record.criteria[index2].orderNr;
      $scope.record.criteria[index2].orderNr = temp;
      $scope.practiceEvaluationForm.$setDirty();
    };

    $scope.editCriteria = function(criteria) {
      $scope.isEditingCriteria = true;
      $scope.editedCriteria = criteria;
      $scope.criteria.nameEt = criteria.nameEt;
      $scope.criteria.addInfo = criteria.addInfo;
      $scope.criteria.type = criteria.type;
      $scope.criteria.disabled = criteria.disabled;
    };

    $scope.saveCriteria = function() {
      if (areRequiredCriteriaFieldsFilled()) {
        $scope.isEditingCriteria = false;

        $scope.editedCriteria.nameEt = $scope.criteria.nameEt;
        $scope.editedCriteria.addInfo = $scope.criteria.addInfo;
        $scope.editedCriteria.type = $scope.criteria.type;

        $scope.criteria = {};
      }
    };

    $scope.removeCriteria = function(array, item) {
      if (array.length > 1) {
        dialogService.confirmDialog({prompt: 'practiceEvaluation.criteria.deleteconfirm'}, function() {
          ArrayUtils.remove(array, item);
          $scope.practiceEvaluationForm.$setDirty();
        });
      } else {
        dialogService.messageDialog({prompt: 'main.messages.error.atLeastOneShouldBeAdded'});
      }
    };

  }
]).controller('PracticeEvaluationViewController', ['$route', '$scope', 'oisFileService', 'Classifier', 'QueryUtils',
  function ($route, $scope, oisFileService, Classifier, QueryUtils) {
    var baseUrl = '/practiceEvaluation';
    var id = $route.current.params.id;

    Classifier.queryForDropdown({mainClassCode: 'PRAKTIKA_KRITEERIUM'}).$promise.then(function(result) {
      $scope.criteriaMap = Classifier.toMap(result);
    });

    function afterLoad() {
      $scope.record.criteria.sort(function(el1, el2){
        return el1.orderNr - el2.orderNr;
      });
    }

    $scope.record = QueryUtils.endpoint(baseUrl).get({id: id}, function() {
      afterLoad();
    });
  }
]);
