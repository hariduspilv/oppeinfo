'use strict';

angular.module('hitsaOis').controller('FormSearchController', ['$q', '$route', '$scope', 'Classifier', 'QueryUtils', 'USER_ROLES', 'message',
  function ($q, $route, $scope, Classifier, QueryUtils, USER_ROLES, message) {
    var baseUrl = '/forms';
    var auth = $route.current.locals.auth;
    $scope.canEdit = auth.authorizedRoles.indexOf(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_LOPBLANKETT) !== -1;
    
    var clMapper = Classifier.valuemapper({type: 'LOPUBLANKETT', status: 'LOPUBLANKETT_STAATUS'});
    QueryUtils.createQueryForm($scope, baseUrl, {}, clMapper.objectmapper);
    $scope.criteria = {};
    $scope.afterLoadData = function(resultData) {
      $scope.tabledata.content = resultData;
      if(resultData.length === 0) {
        message.info('main.messages.error.notFound');
      }
      clMapper.objectmapper(resultData);
    };
    $scope.loadData = function() {
      var query = $scope.getCriteria();
      $scope.toStorage(baseUrl, query);
      $scope.tabledata.$promise = QueryUtils.endpoint(baseUrl).query(query, $scope.afterLoadData);
    };

    $scope.formState = {xlsUrl: 'forms/forms.xls'};

    $q.all(clMapper.promises).then(function() {
      $scope.loadData();
    });
  }
]).controller('FormEditController', ['$route', '$scope', 'message', 'Classifier', 'FormUtils', 'QueryUtils', 'dialogService',
function($route, $scope, message, Classifier, FormUtils, QueryUtils, dialogService) {
  var baseUrl = '/forms';
  $scope.auth = $route.current.locals.auth;

  $scope.actionType = $route.current.params.action;
  $scope.getButtonLabelKey = function() {
    if ($scope.actionType === 'delete') {
      return 'main.button.delete';
    } else if ($scope.actionType === 'defected') {
      return 'form.button.defected';
    } else {
      return 'main.button.save';
    }
  };

  $scope.formState = {};

  $scope.update = function() {
    FormUtils.withValidForm($scope.formForm, function() {
      if ($scope.actionType === 'delete' || $scope.actionType === 'defected') {
        dialogService.confirmDialog({
          prompt: 'form.confirm.' + $scope.actionType,
          from: $scope.formState.from,
          thru: $scope.formState.thru
        }, function() {
          QueryUtils.endpoint(baseUrl + '/' + $scope.actionType).save($scope.formState, function() {
            if ($scope.actionType === 'delete') {
              message.info('main.messages.delete.success');
            } else if ($scope.actionType === 'defected') {
              message.updateSuccess();
            }
            $scope.formForm.$setPristine();
          }).$promise.catch(angular.noop);
        });
      } else {
        QueryUtils.endpoint(baseUrl + '/' + $scope.actionType).save($scope.formState, function() {
          message.updateSuccess();
          $scope.formForm.$setPristine();
        }).$promise.catch(angular.noop);
      }
    });
  };
}
]);
