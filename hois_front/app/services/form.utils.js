'use strict';

angular.module('hitsaOis').factory('FormUtils', ['$location', 'dialogService', 'message',
  function ($location, dialogService, message) {

    function withValidForm(form, callback) {
      form.$setSubmitted();
      if(!form.$valid) {
        message.error('main.messages.form-has-errors');
        return;
      }
      return callback();
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
      deleteRecord: function(record, backUrl, dialogOptions) {
        dialogService.confirmDialog(dialogOptions, function() {
          record.$delete().then(function() {
            message.info('main.messages.delete.success');
            $location.url(backUrl);
          }).catch(angular.noop);
        });
      }
    };
  }
]);
