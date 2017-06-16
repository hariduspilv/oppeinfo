
'use strict';

angular.module('hitsaOis')
  .factory('message', function($mdToast, $translate) {
    var factory = {};

    function showMessage(message, toastClass) {
      $mdToast.show(
        $mdToast.simple()
          .textContent(message)
          .hideDelay(3000)
          .position('top')
          .toastClass(toastClass)
      );
    }

    function showTranslatedMessage(messageText, toastClass, params) {
      $translate(messageText, params).then(function(message) {
          showMessage(message, toastClass);
        })
        .catch(function() {
          showMessage(messageText, toastClass);
        });
    }

    factory.info = function(messageText, params) {
      showTranslatedMessage(messageText, 'toastInfo', params);
    };

    factory.warn = function(messageText) {
      showTranslatedMessage(messageText, 'toastWarn');
    };

    factory.error = function(messageText) {
      showTranslatedMessage(messageText, 'toastError');
    };

    factory.updateSuccess = function() {
      showTranslatedMessage('main.messages.update.success', 'toastInfo');
    };

    return factory;
  });
