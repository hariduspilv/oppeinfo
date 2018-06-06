'use strict';

/**
 * @ngdoc directive
 * @name hitsaOis.directive:hoisCurriculumVersionSelect
 * @description
 * # hoisCurriculumVersionSelect
 */
angular.module('hitsaOis').directive('hoisSelect', function (Curriculum, School, QueryUtils, DataUtils) {
  return {
    template: '<md-select md-on-open="queryPromise">' +
    '<md-option ng-if="!isMultiple && !isRequired && !ngRequired" md-option-empty></md-option>' +
    '<md-option ng-repeat="option in filteredOptions | orderBy: showProperty ? showProperty : $root.currentLanguageNameField()" ng-value="option[valueProperty]"' +
    'aria-label="{{$root.currentLanguageNameField(option)}}">{{showProperty ? option[showProperty] : $root.currentLanguageNameField(option)}}</md-option></md-select>',
    restrict: 'E',
    replace: true,
    scope: {
      ngModel: '=',
      criteria: '=',
      filterValues: '@', // model of array of filtered-out values (either primitives or objects with valueProperty attribute defined)
      multiple: '@',
      ngRequired: '=',
      required: '@',
      values: '@',
      valueProperty: '@',
      showProperty: '@',
      preselectCurrent: '@',
      selectCurrentStudyYear: '@',
      loadAfterDefer: '=',
    },
    link: function postLink(scope, element, attrs) {
      scope.isMultiple = angular.isDefined(scope.multiple);
      scope.isRequired = angular.isDefined(scope.required);
      scope.valueProperty = angular.isString(scope.valueProperty) ? scope.valueProperty : 'id';
      //fix select not showing required visuals if required attribute is used
      element.attr('required', scope.isRequired);

      scope.options = [];
      scope.filteredOptions = [];
      scope.hideOptions = [];

      function doFilter() {
        scope.filteredOptions = (scope.options || []).filter(function(it) {return scope.hideOptions.indexOf(it[scope.valueProperty]) === -1; });
      }

      function afterLoad(result) {
        scope.options = result.content;
        doFilter();
      }

      function preselectCurrentStudyYearOrPeriod() {
        if (!scope.ngModel) {
          var current = DataUtils.getCurrentStudyYearOrPeriod(scope.options);
          if (current) {
            scope.ngModel = current.id;
          }
        }
      }

      function afterStudyYearsLoad() {
        if (angular.isDefined(scope.selectCurrentStudyYear)) {
          preselectCurrentStudyYearOrPeriod();
        }
      }

      function afterStudyPeriodsWithYearLoad() {
        scope.options.forEach(function (studyPeriod) {
          studyPeriod.display = scope.$root.currentLanguageNameField(studyPeriod.studyYear) + ' ' + 
            scope.$root.currentLanguageNameField(studyPeriod);
        });
        if (angular.isDefined(scope.preselectCurrent)) {
          preselectCurrentStudyYearOrPeriod();
        }
      }

      function loadValues() {
        if (angular.isDefined(attrs.type)) {
          if (attrs.type === 'building') {
            scope.options = QueryUtils.endpoint('/autocomplete/buildings').query();
          } else if (attrs.type === 'curriculum') {
            scope.options = QueryUtils.endpoint('/autocomplete/curriculumsDropdown').query();
          } else if (attrs.type === 'curriculumversion') {
            scope.options = Curriculum.queryVersions(scope.criteria);
          } else if (attrs.type === 'directivecoordinator') {
            scope.options = QueryUtils.endpoint('/autocomplete/directivecoordinators').query(scope.criteria);
          } else if (attrs.type === 'journal') {
            scope.options = QueryUtils.endpoint('/autocomplete/journals').query(scope.criteria);
          } else if (attrs.type === 'school') {
            scope.options = School.getAll();
          } else if (attrs.type === 'studentgroup') {
            scope.options = QueryUtils.endpoint('/autocomplete/studentgroups').query(scope.criteria);
          } else if (attrs.type === 'studyyear') {
            scope.options = QueryUtils.endpoint('/autocomplete/studyYears').query({}, afterStudyYearsLoad);
          } else if (attrs.type === 'teacher') {
            scope.options = QueryUtils.endpoint('/autocomplete/teachersList').query(scope.criteria);
          } else if (attrs.type === 'enterprise') {
            scope.options = QueryUtils.endpoint('/autocomplete/enterprises').query();
          } else if(attrs.type === 'saisadmissioncode') {
            scope.options = QueryUtils.endpoint('/autocomplete/saisAdmissionCodes').query();
          } else if (attrs.type === 'subject') {
            scope.options = QueryUtils.endpoint('/autocomplete/subjectsList').query(scope.criteria);
          } else if(attrs.type === 'studyperiod') {
            scope.options = QueryUtils.endpoint('/autocomplete/studyPeriods').query();
          } else if(attrs.type === 'studyperiodyear') {
            if (!scope.showProperty) {
              scope.showProperty = 'display';
            }
            scope.options = QueryUtils.endpoint('/autocomplete/studyPeriodsWithYear').query(afterStudyPeriodsWithYearLoad);
          } else if(attrs.type === 'apelschool') {
            scope.options = QueryUtils.endpoint('/autocomplete/apelschools').query();
          }

          if (angular.isDefined(scope.options) && angular.isDefined(scope.options.$promise)) {
            scope.queryPromise = scope.options.$promise;
            scope.options.$promise.then(doFilter);
          }
        } else if (angular.isDefined(scope.values)) {
          scope.$parent.$watchCollection(scope.values, function (values) {
            afterLoad({content: values});
          });
        }
      }

      if(angular.isDefined(scope.filterValues)) {
        scope.$parent.$watchCollection(scope.filterValues, function(changedFilterValues) {
          scope.hideOptions = [];
          if (angular.isArray(changedFilterValues)) {
            changedFilterValues.forEach(function(it) {
              var value = angular.isObject(it) ? it[scope.valueProperty] : it;

              if (angular.isDefined(value)) {
                scope.hideOptions.push(value);
              }
            });
          }
          doFilter();
        });
      }

      if (angular.isDefined(scope.loadAfterDefer)) {
        scope.loadAfterDefer.promise.then(loadValues);
      } else {
        loadValues();
      }
    }
  };
});
