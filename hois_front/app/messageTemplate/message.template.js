'use strict';

angular.module('hitsaOis').controller('MessageTemplateListController', ['$scope', 'Classifier', 'DataUtils', 'QueryUtils', '$q',
  function ($scope, Classifier, DataUtils, QueryUtils, $q) {

    var clMapper = Classifier.valuemapper({type: 'TEATE_LIIK'});
    QueryUtils.createQueryForm($scope, '/messageTemplate', {order: 'type.' + $scope.currentLanguageNameField()}, function(result) {
      clMapper.objectmapper(result);
      DataUtils.convertStringToDates(result, ['validThru', 'validFrom']);
      var now = moment();
      for (var i = 0; i < result.length; i++) {
        var row = result[i];
        row._isValid = (!row.validFrom || moment(row.validFrom).isSameOrBefore(now, 'day')) && (!row.validThru || moment(row.validThru).isSameOrAfter(now, 'day'));
      }
    });
    DataUtils.convertStringToDates($scope.criteria, ['validFrom', 'validThru']);

    $q.all(clMapper.promises).then($scope.loadData);
  }
]).controller('MessageTemplateEditController', ['$location', '$route', '$scope', 'dialogService', 'message',
                                                'DataUtils', 'QueryUtils', '$rootScope', 'Classifier', '$q',
  function ($location, $route, $scope, dialogService, message, DataUtils, QueryUtils, $rootScope, Classifier, $q) {

    $scope.readOnly = $route.current.$$route.originalPath.indexOf("view") !== -1;
    var clMapper = Classifier.valuemapper({userRights: 'TEEMAOIGUS'});

    $scope.getIsValid = function() {
        $scope.isValid =
        ($scope.record.validFrom === undefined || $scope.record.validFrom === null || $scope.record.validFrom <= new Date()) &&
        ($scope.record.validThru === undefined || $scope.record.validThru === null || $scope.record.validThru >= new Date());
    };

    function afterLoad() {
      $q.all(clMapper.promises).then(function () {
        DataUtils.convertStringToDates($scope.record, ['validFrom', 'validThru']);
        getUsedTypes();
        if ($scope.record.userRights && $scope.record.userRights.length > 0) {
          QueryUtils.createQueryForm($scope, '/messageTemplate/usersByPermission',
            {permissionObjects: $scope.record.userRights},
            mapUserRights, undefined, true, true);
          $scope.loadData();
        }
        clMapper.objectmapper($scope.record);
        $scope.getIsValid();
        if($scope.messageTemplateForm) {
          $scope.messageTemplateForm.$setPristine();
        }
      });
    }

    function mapUserRights() {
      angular.forEach($scope.tabledata.content, clMapper.objectmapper);
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

    $scope.$watch('record.type', function (newV, oldV) {
      if (oldV === newV || !newV || !!$scope.record.content) {
        return;
      }
      QueryUtils.loadingWheel($scope, true);
      QueryUtils.endpoint(baseUrl + "/contentTemplate").search({templateType: newV}, function (data) {
        $scope.record.content = data.content;
        QueryUtils.loadingWheel($scope, false);
      }, function () {
        QueryUtils.loadingWheel($scope, false);
      });
    });
  }
]);
