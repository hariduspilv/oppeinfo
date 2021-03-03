'use strict';

angular.module('hitsaOis').factory('FormUtils', ['$location', 'dialogService', 'message', 'QueryUtils',
  function ($location, dialogService, message, QueryUtils) {

    function withValidForm(form, callback) {
      form.$setSubmitted();
      if(!form.$valid) {
        message.error('main.messages.form-has-errors');
        return;
      }
      return callback();
    }

    /**
     *
     * @param {Scope} loadingOptions.scope
     */
    function turnOnLoadingWheelIfNeeds(loadingOptions) {
      if (!!loadingOptions) {
        QueryUtils.loadingWheel(loadingOptions.scope, true);
      }
    }

    /**
     *
     * @param {Scope} loadingOptions.scope
     */
    function turnOffLoadingWheelIfNeeds(loadingOptions) {
      if (!!loadingOptions) {
        QueryUtils.loadingWheel(loadingOptions.scope, false);
      }
    }

    return {
      withValidForm: withValidForm,
      saveRecord: function(form, record, baseUrl, updateCallback) {
        withValidForm(form, function() {
          if(record.id) {
            record.$update().then(message.updateSuccess).then(updateCallback).catch(angular.noop);
          } else {
            record.$save().then(function() {
              message.info('main.messages.create.success');
              $location.url(baseUrl + '/' + record.id + '/edit?_noback');
            }).catch(angular.noop);
          }
        });
      },
      /**
       *
       * @param record
       * @param {string} backUrl
       * @param {Object} dialogOptions
       * @param {Object} loadingOptions
       * @param {Scope} loadingOptions.scope
       */
      deleteRecord: function(record, backUrl, dialogOptions, loadingOptions) {
        dialogService.confirmDialog(dialogOptions, function() {
          turnOnLoadingWheelIfNeeds(loadingOptions);
          record.$delete().then(function() {
            message.info('main.messages.delete.success');
            turnOffLoadingWheelIfNeeds(loadingOptions);
            $location.url(backUrl);
          }).catch(function () {
            turnOffLoadingWheelIfNeeds(loadingOptions);
          });
        });
      }
    };
  }
]);
