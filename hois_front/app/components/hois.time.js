'use strict';

angular.module('hitsaOis')
  .directive('hoisTime', function () {

    return {
      template: '<input style="min-width:3em;" class="md-input" type="text">', //placeholder="HH:mm" should be added by component user
      restrict: 'E',
      require: 'ngModel',
      replace: true,
      scope: {
        ngModel: '='
      },
      link: {
        post: function (scope, element, attrs, ctrl) {
          var timeFormat = "HH:mm";

          scope.$watch("ngModel", function() {
            updateTime(scope.ngModel);
          });
/*
          scope.ngModel.$validators.format = function(modelValue, viewValue) {
            return !viewValue || angular.isDate(viewValue) || moment(viewValue, timeFormat, true).isValid();
          };

          scope.ngModel.$validators.required = function(modelValue, viewValue) {
            return angular.isUndefined(attrs.required) || !ngModel.$isEmpty(modelValue) || !ngModel.$isEmpty(viewValue);
          };
*/
          function updateTime(time) {
            // instead of using moment() we should use moment.utc as we store local time which stores without timezone.
            // when it tries to get value from search history then it will adjust to timezone. *.utc will not adjust it.
            var stringToUtcTime;
            if (time && typeof time === 'string') {
              stringToUtcTime = moment.utc(time);
              if (stringToUtcTime.isValid()) {
                var d = stringToUtcTime._d;
                d.setHours(stringToUtcTime.hours(), stringToUtcTime.minutes());
                scope.ngModel = d;
                return;
              }
            }

            var value = moment(time, angular.isDate(time) ? null : timeFormat, true),
              strValue = value.format(timeFormat);
            if(value.isValid()) {
              updateInputElement(strValue);
              //scope.ngModel.$setViewValue(strValue);
            } else {
              updateInputElement(time);
              //scope.ngModel.$setViewValue(time);
            }

            //scope.ngModel.$render();
          }

          function updateInputElement(value) {
            element.val(value);
          }

          function onInputElementEvents(event) {
            var timeStr = event.target.value;
            var time = moment(timeStr, timeFormat, true);
            /*if(event.target.value !== ngModel.$viewValue)
              updateTime(event.target.value);*/
            if (time.isValid()) {
              if (!scope.ngModel) {
                scope.ngModel = time.toDate();
              } else {
                var old = moment(scope.ngModel, typeof scope.ngModel === 'string' && scope.ngModel.length === 5 ? timeFormat : undefined);
                old.hours(time.hours());
                old.minutes(time.minutes());
                scope.ngModel = old.toDate();
              }
            }
            if (timeStr === '') { // when text value is set to nothing it should reset ngModel.
              scope.ngModel = undefined;
            }
            ctrl.$setValidity('time', timeStr ? time.isValid() : true);
          }

          element.unbind('input').unbind('keydown').unbind('change');
          element.bind('blur', function (e) {
            scope.$apply(function () {
              onInputElementEvents(e);
            });
          });
        }
      }
    };
  });
