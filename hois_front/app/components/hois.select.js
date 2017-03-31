'use strict';

/**
 * @ngdoc directive
 * @name hitsaOis.directive:hoisCurriculumVersionSelect
 * @description
 * # hoisCurriculumVersionSelect
 */
angular.module('hitsaOis').directive('hoisSelect', function (Curriculum, School, QueryUtils) {
  return {
    template: '<md-select>'+
      '<md-option ng-if="!isMultiple && !isRequired && !ngRequired" md-option-empty></md-option>'+
      '<md-option ng-repeat="option in options | orderBy: $root.currentLanguageNameField()" ng-value="option[valueProperty]"'+
      'aria-label="{{$root.currentLanguageNameField(option)}}">{{$root.currentLanguageNameField(option)}}</md-option></md-select>',
    restrict: 'E',
    replace: true,
    scope: {
      multiple:'@',
      ngRequired:'=',
      required:'@',
      values: '@',
      valueProperty: '@'
    },
    link: function postLink(scope, element, attrs) {
      scope.isMultiple = angular.isDefined(scope.multiple);
      scope.isRequired = angular.isDefined(scope.required);
      scope.valueProperty = angular.isString(scope.valueProperty) ? scope.valueProperty : 'id';
      //fix select not showing required visuals if required attribute is used
      element.attr('required', scope.isRequired);

      var afterLoad = function(result) {
        scope.options = result.content;
      };

      if(angular.isDefined(attrs.type)) {
        if(attrs.type === 'building') {
          QueryUtils.endpoint('/autocomplete/buildings').search(afterLoad);
        } else if(attrs.type === 'curriculumversion') {
          Curriculum.queryVersions().$promise.then(afterLoad);
        } else if(attrs.type === 'directivecoordinator') {
          QueryUtils.endpoint('/autocomplete/directivecoordinators').search(afterLoad);
        } else if(attrs.type === 'school') {
          scope.options = School.getAll();
        } else if(attrs.type === 'curriculum') {
          QueryUtils.endpoint('/autocomplete/curriculums').search(afterLoad);
        } else if(attrs.type === 'studentgroups') {
          QueryUtils.endpoint('/autocomplete/studentgroups').search(afterLoad);
        }
      } else if(angular.isDefined(scope.values)) {
        scope.$parent.$watchCollection(scope.values, function(values) {
          scope.options = values;
        });
      }
    }
  };
});
