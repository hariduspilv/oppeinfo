'use strict';

angular.module('hitsaOis').controller('MessageTemplateListController', ['$scope', '$sessionStorage', 'Classifier', 'DataUtils', 'QueryUtils', '$q', function ($scope, $sessionStorage, Classifier, DataUtils, QueryUtils, $q) {

    var clMapper = Classifier.valuemapper({type: 'TEATE_LIIK'});
    QueryUtils.createQueryForm($scope, '/messageTemplate', {order: 'type.' + $scope.currentLanguageNameField()}, clMapper.objectmapper);
    $q.all(clMapper.promises).then($scope.loadData);
    DataUtils.convertStringToDates($scope.criteria, ['validFrom', 'validThru']);

    $scope.isValid = function(record) {
        return (!record.validFrom || new Date(record.validFrom) <= new Date()) && (!record.validThru || new Date(record.validThru) >= new Date());
    };
}]).controller('MessageTemplateEditController', ['$location', '$route', '$scope', 'dialogService', 'message', 'DataUtils', 'QueryUtils', '$rootScope',
  function ($location, $route, $scope, dialogService, message, DataUtils, QueryUtils, $rootScope) {

    $scope.readOnly = $route.current.$$route.originalPath.indexOf("view") !== -1;

    $scope.getIsValid = function() {
        $scope.isValid =
        ($scope.record.validFrom === undefined || $scope.record.validFrom === null || $scope.record.validFrom <= new Date()) &&
        ($scope.record.validThru === undefined || $scope.record.validThru === null || $scope.record.validThru >= new Date());
    };

    function afterLoad() {
      DataUtils.convertStringToDates($scope.record, ['validFrom', 'validThru']);
      getUsedTypes();
      $scope.getIsValid();
      if($scope.messageTemplateForm) {
        $scope.messageTemplateForm.$setPristine();
      }
    }

    var baseUrl = '/messageTemplate';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var id = $route.current.params.id;

    $rootScope.removeLastUrlFromHistory(function(lastUrl){
      return lastUrl === '#/messageTemplate/' + id + '/view' ||
             lastUrl === '#/messageTemplate/new';
      });

    if(id) {
        $scope.record = Endpoint.get({id: id}, afterLoad);
    } else {
        $scope.record = new Endpoint();
        afterLoad();
    }

    $scope.$watch('record.validFrom', function() {
            if($scope.record.validThru && $scope.record.validThru < $scope.record.validFrom) {
                $scope.record.validThru = null;
            }
        }
    );

    $scope.usedTypes = [];
    function getUsedTypes() {
        $scope.allowedTypes = QueryUtils.endpoint("/messageTemplate/usedTypeCodes").query({code: $scope.record.type}).$promise.then(function(response){
            $scope.allowedTypes = response;
        });
    }

    $scope.save = function() {
        $scope.messageTemplateForm.$setSubmitted();
        if(!$scope.messageTemplateForm.$valid) {
            message.error('main.messages.form-has-errors');
            return;
        }

        if($scope.record.id) {
            $scope.record.$update(afterLoad).then(message.updateSuccess);
        }else{
            $scope.record.$save().then(function() {
                message.info('main.messages.create.success');
                $location.path(baseUrl + "/" + $scope.record.id + "/edit");
            });
        }
    };

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'messageTemplate.deleteconfirm'}, function() {
        $scope.record.$delete().then(function() {
          message.info('main.messages.delete.success');
          $location.path(baseUrl);
        });
      });
    };
  }
]);
