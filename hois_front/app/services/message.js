
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

    function showTranslatedMessage(messageText, toastClass) {
      $translate(messageText).then(function(message) {
          showMessage(message, toastClass);
        })
        .catch(function() {
          showMessage(messageText, toastClass);
        });
    }

    factory.info = function(messageText) {
      showTranslatedMessage(messageText, 'toastInfo');
    };

    factory.warn = function(messageText) {
      showTranslatedMessage(messageText, 'toastWarn');
    };

    factory.error = function(messageText) {
      showTranslatedMessage(messageText, 'toastError');
    };

    return factory;
  });
