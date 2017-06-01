'use strict';

angular.module('hitsaOis')
  .directive('hoisTime', function () {

    return {
      template: '<input style="min-width:3em;" class="md-input" type="text">', //placeholder="HH:mm" should be added by component user
      restrict: 'E',
      require: ['ngModel'],
      replace: true,
      scope: {
        ngModel: '='
      },
      link: {
        post: function (scope, element, attrs) {
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
            var time = moment(event.target.value, timeFormat, true);
            /*if(event.target.value !== ngModel.$viewValue)
              updateTime(event.target.value);*/
            if (time.isValid()) {
              if (!scope.ngModel) {
                scope.ngModel = time.toDate();
              } else {
                var old = moment(scope.ngModel);
                old.hours(time.hours());
                old.minutes(time.minutes());
                scope.ngModel = old.toDate();
              }
            }
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
