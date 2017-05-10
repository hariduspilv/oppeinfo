'use strict';

/**
 * @ngdoc service
 * @name hitsaOis.dialogService
 * @description
 * # dialogService
 * Service in the hitsaOis.
 */
angular.module('hitsaOis').service('dialogService', ['$mdDialog', 'ArrayUtils',

  function ($mdDialog, ArrayUtils) {

    var defaultConfirmDialogOptions = {accept: 'main.yes', cancel: 'main.no'};

    /**
     * For form validation to work dialog form name must be dialogForm
     */
    this.showDialog = function(templateUrl, dialogController, submitCallback, cancelCallback, userConfig) {
      var submitted = false;
      //var config = angular.extend({}, userConfig);
      $mdDialog.show({
        controller: function($scope, $rootScope, $mdDialog) {
          $scope.removeFromArray = ArrayUtils.remove;
          $scope.currentLanguageNameField = $rootScope.currentLanguageNameField;

          $scope.cancel = function() {
            $mdDialog.hide();
          };

          $scope.submit = function() {
            var valid = true;
            if ($scope.dialogForm) {
              $scope.dialogForm.$setSubmitted();
              valid = $scope.dialogForm.$valid;
            }

            if (valid) {
              if (angular.isFunction(submitCallback)) {
                submitCallback($scope);
              }
              submitted = true;
              $mdDialog.hide();
            } else {
              console.log('dialog form invalid: ', $scope.dialogForm.$error);
            }
          };

          if (angular.isFunction(dialogController)) {
            dialogController($scope);
          }
        },
        templateUrl: templateUrl,
/*        skipHide: true,*/
        //clickOutsideToClose: angular.isDefined(config.clickOutsideToClose) ? config.clickOutsideToClose === true : true,
        //dialog general behaviour is that clicking outside should not close dialog
        clickOutsideToClose: false,
        onRemoving: function() {
          if (angular.isFunction(cancelCallback)) {
              if (submitted !== true) {
        		    cancelCallback();
              }
        	}
        }
      });
    };

    this.hide = function() {
      $mdDialog.hide();
    };

    this.confirmDialog = function(options, callback) {
      $mdDialog.show({
        controller: function($scope) {
          $scope.messages = angular.extend({}, defaultConfirmDialogOptions, options);
          $scope.accept = function() {
            $mdDialog.hide();
            callback();
          };
          $scope.cancel = $mdDialog.hide;
        },
        templateUrl: 'components/confirm.dialog.html',
        //dialog general behaviour is that clicking outside should not close dialog
        clickOutsideToClose: false,
        skipHide: true
      });
    };
  }
]);
