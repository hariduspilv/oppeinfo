'use strict';

angular.module('hitsaOis').factory('resourceErrorHandler', function ($q, message) {
  return {
    responseError: function (response) {
      if (response.status === 404) {
        message.error('not.found');
      } else if (response.status === 409) {
        message.error('record.modified');
      } else if (response.data && response.data._errors) {
        angular.forEach(response.data._errors, function (err) {
          message.error(err.code + (err.field ? ' ' + err.field : ''), err.params);
        });
      } else {
        message.error('main.messages.system.failure');
      }
      return $q.reject(response);
    }
  };
});
