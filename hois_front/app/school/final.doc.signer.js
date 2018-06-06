'use strict';

angular.module('hitsaOis').controller('FinalDocSignerEditController', ['$location', '$route', '$scope', 'dialogService', 'message', 'QueryUtils', 'FormUtils', 'USER_ROLES',

  function ($location, $route, $scope, dialogService, message, QueryUtils, FormUtils, USER_ROLES) {
    var id = $route.current.params.id;
    var baseUrl = '/school/finaldocsigners';
    var auth = $route.current.locals.auth;
    $scope.canEdit = auth.authorizedRoles.indexOf(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_LOPDOKALLKIRI) !== -1;
    var Endpoint = QueryUtils.endpoint(baseUrl);
    if (id) {
      $scope.record = Endpoint.get({id: id});
    } else {
      $scope.record = new Endpoint({isFirst: false, isValid: true});
    }

    $scope.update = function() {
      FormUtils.withValidForm($scope.finalDocSignerForm, function() {
        if ($scope.record.id) {
          $scope.record.$update().then(message.updateSuccess).catch(angular.noop);
        } else {
          $scope.record.$save().then(function() {
            message.info('main.messages.create.success');
            $location.url(baseUrl + '/' + $scope.record.id + '/edit?_noback');
          }).catch(angular.noop);
        }
      });
    };

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'finalDocSigner.deleteconfirm'}, function() {
        $scope.record.$delete().then(function() {
          message.info('main.messages.delete.success');
          $location.url(baseUrl + '?_noback');
        }).catch(angular.noop);
      });
    };
  }
]);
