
'use strict';

angular.module('hitsaOis')
  .factory('message', function($mdToast, $translate, $rootScope) {
    var factory = {};

    function translateParam(param) {
      if (typeof param === 'object') {
        var translatedValue = $rootScope.currentLanguageNameField(param);
        if (translatedValue) {
          return translatedValue;
        }
      }
      return param;
    }

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
      var translatedParams = {};
      for (var key in params) {
        translatedParams[key] = translateParam(params[key]);
      }
      $translate(messageText, translatedParams).then(function(message) {
          showMessage(message, toastClass);
        })
        .catch(function() {
          showMessage(messageText, toastClass);
        });
    }

    factory.info = function(messageText, params) {
      showTranslatedMessage(messageText, 'toastInfo', params);
    };

    factory.warn = function(messageText, params) {
      showTranslatedMessage(messageText, 'toastWarn', params);
    };

    factory.error = function(messageText, params) {
      showTranslatedMessage(messageText, 'toastError', params);
    };

    factory.updateSuccess = function() {
      showTranslatedMessage('main.messages.update.success', 'toastInfo');
    };

    return factory;
  });
