'use strict';

angular.module('hitsaOis').factory('resourceErrorHandler', function ($q, message) {
  return {
    responseError: function (response, form) {
      if (response.status === 404) {
        message.error('main.messages.record.notFound');
      } else if (response.status === 409) {
        message.error('main.messages.record.modified');
      } else if (response.data && response.data._errors) {
        var popupshown = false;
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
                ctrl.$serverError = ctrl.$serverError || [];
                ctrl.$serverError.push(err);
                ctrl.$setValidity('serverside', false);
              }
              return;
            }
          }
          popupshown = true;
          message.error(err.code + (err.field ? ' ' + err.field : ''), err.params);
        });
        if(!popupshown) {
          message.error('main.messages.form-has-errors');
        }
      } else {
        message.error('main.messages.system.failure');
      }
      return $q.reject(response);
    }
  };
});
