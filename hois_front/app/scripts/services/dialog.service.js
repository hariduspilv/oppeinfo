'use strict';

/**
 * @ngdoc service
 * @name hitsaOis.dialogService
 * @description
 * # dialogService
 * Service in the hitsaOis.
 */
angular.module('hitsaOis')
  .service('dialogService', function ($mdDialog, ArrayUtils) {

    this.showSelectDialog = function(templateUrl, selectCallback, cancelCallback) {
      $mdDialog.show({
        controller: function($scope, $mdDialog) {
          $scope.cancel = function() {
            $mdDialog.hide();
          };

          $scope.isTableSelect = true;
          $scope.select = function() {
            $mdDialog.hide();
            if(angular.isFunction(selectCallback)) {
        		  selectCallback($scope.tableSelected);
        	  }
          };
          $scope.tableSelected = [];
        },
        templateUrl: templateUrl,
        parent: angular.element(document.body),
        clickOutsideToClose: true,
        onRemoving: function() {
          if(angular.isFunction(cancelCallback)) {
        		  cancelCallback();
        	}
        }
      });
    };

    /**
     * In dialog template inputs ng-model must use pattern data.*in order to dataCallback to work.
     * dataCallback function argument will then be dialog model data
     *
     * For form validation to work dialog form name must be dialogForm
     */
    this.showDialog = function(templateUrl, dialogController, dataCallback) {
      $mdDialog.show({
        controller: function($scope, $rootScope, $mdDialog) {
          $scope.removeFromArray = ArrayUtils.remove;
          $scope.data = {};
          $scope.currentLanguageNameField = $rootScope.currentLanguageNameField;


          $scope.cancel = function() {
            $mdDialog.hide();
          };


          $scope.submit = function() {
            var valid = true;
            if($scope.dialogForm) {
              $scope.dialogForm.$setSubmitted();
              valid = $scope.dialogForm.$valid;
            }

            if(valid) {
              if(dataCallback !== null) {
                dataCallback($scope.data, $scope);
              }
              $mdDialog.hide();
            }
          };

          if(dialogController !== null) {
            dialogController($scope);
          }
        },
        templateUrl: templateUrl,
        skipHide: true,
        clickOutsideToClose: true
      });
    };

    this.hide = function() {
      $mdDialog.hide();
    };
  });
