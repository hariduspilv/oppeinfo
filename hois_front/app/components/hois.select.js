'use strict';

/**
 * @ngdoc directive
 * @name hitsaOis.directive:hoisCurriculumVersionSelect
 * @description
 * # hoisCurriculumVersionSelect
 */
angular.module('hitsaOis').directive('hoisSelect', function (Curriculum, School, QueryUtils, DataUtils) {
  return {
    template: '<md-select>'+
      '<md-option ng-if="!isMultiple && !isRequired && !ngRequired" md-option-empty></md-option>'+
      '<md-option ng-repeat="option in options | orderBy: showProperty ? showProperty : $root.currentLanguageNameField()" ng-value="option[valueProperty]"'+
      'aria-label="{{$root.currentLanguageNameField(option)}}">{{showProperty ? option[showProperty] : $root.currentLanguageNameField(option)}}</md-option></md-select>',
    restrict: 'E',
    replace: true,
    scope: {
      ngModel: '=',
      criteria: '=',
      multiple:'@',
      ngRequired:'=',
      required:'@',
      values: '@',
      valueProperty: '@',
      showProperty: '@',
      selectCurrentStudyYear: '@'
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

      var afterStudyYearsLoad = function()  {
        if (angular.isDefined(scope.selectCurrentStudyYear) && !scope.ngModel) {
          var currentStudyYear = DataUtils.getCurrentStudyYearOrPeriod(scope.options);
          if (currentStudyYear) {
            scope.ngModel = currentStudyYear.id;
          }
        }
      };

      if(angular.isDefined(attrs.type)) {
        if(attrs.type === 'building') {
          scope.options = QueryUtils.endpoint('/autocomplete/buildings').query();
        } else if(attrs.type === 'curriculum') {
          QueryUtils.endpoint('/autocomplete/curriculums').search(afterLoad);
        } else if(attrs.type === 'curriculumversion') {
          scope.options = Curriculum.queryVersions(scope.criteria);
        } else if(attrs.type === 'directivecoordinator') {
          scope.options = QueryUtils.endpoint('/autocomplete/directivecoordinators').query(scope.criteria);
        } else if(attrs.type === 'journal') {
          scope.options = QueryUtils.endpoint('/autocomplete/journals').query();
        } else if(attrs.type === 'school') {
          scope.options = School.getAll();
        } else if(attrs.type === 'studentgroup') {
          scope.options = QueryUtils.endpoint('/autocomplete/studentgroups').query(scope.criteria);
        } else if(attrs.type === 'studyyear') {
          scope.options = QueryUtils.endpoint('/autocomplete/studyYears').query({}, afterStudyYearsLoad);
        } else if(attrs.type === 'teacher') {
          scope.options = QueryUtils.endpoint('/autocomplete/teachersList').query();
        } else if(attrs.type === 'enterprise') {
          scope.options = QueryUtils.endpoint('/autocomplete/enterprises').query();
        }
      } else if(angular.isDefined(scope.values)) {
        scope.$parent.$watchCollection(scope.values, function(values) {
          scope.options = values;
        });
      }
    }
  };
});
