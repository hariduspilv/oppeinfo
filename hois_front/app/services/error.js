'use strict';

angular.module('hitsaOis').factory('resourceErrorHandler', function ($q, message) {
  return {
    responseError: function (response) {
      if (response.status === 404) {
        message.error('not.found');
      } else if (response.status === 409) {
        message.error('main.messages.record.modified');
      } else if (response.data && response.data._errors) {
        var form = response.config.data.form;
        form = form ? form() : undefined;
        angular.forEach(response.data._errors, function (err) {
          if(err.field && form) {
            var ctrl = form[err.field];
            if(ctrl && ctrl.$setValidity && ctrl.$setDirty) {
              if(err.code === 'required') {
                // check for presence of required validator and add if missing
                if(!ctrl.$validators.required) {
                  // add validator
                  ctrl.$validators.required = function(modelValue, viewValue) {
                    return !ctrl.$isEmpty(viewValue);
                  };
                }
                ctrl.$setValidity(err.code, false);
              } else {
                // ctrl.$serverError = ctrl.$serverError || [];
                // ctrl.$serverError.push(err);
                // ctrl.$setValidity('servererror', false);
              }
              return;
            }
          }
          message.error(err.code + (err.field ? ' ' + err.field : ''), err.params);
        });
      } else {
        message.error('main.messages.system.failure');
      }
      return $q.reject(response);
    }
  };
});
