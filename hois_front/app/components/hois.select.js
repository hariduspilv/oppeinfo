'use strict';

/**
 * @ngdoc directive
 * @name hitsaOis.directive:hoisCurriculumVersionSelect
 * @description
 * # hoisCurriculumVersionSelect
 * 
 * @since 22.01.2019 Added trackBy to md-select which uses `valueProperty` to ignore moment when mdSelect decides to put form into dirty state.
 */
angular.module('hitsaOis').directive('hoisSelect', function ($rootScope, Curriculum, School, QueryUtils, DataUtils) {
  return {
    templateUrl: 'components/hois.select.html',
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
      orderByProperty: '@',
      showProperty: '@',
      preselectCurrent: '@',
      selectCurrentStudyYear: '@',
      loadAfterDefer: '=',
      warningParam: '@',
      sortedQuery: '=',
      hideValues: '&?'
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

      scope.orderBy = !scope.sortedQuery ? (scope.showProperty ? scope.showProperty : $rootScope.currentLanguageNameField()) : scope.orderByProperty;

      function doFilter() {
        scope.filteredOptions = (scope.options || []).filter(function(it) {
          return scope.hideOptions.indexOf(it[scope.valueProperty]) === -1 &&
            (!scope.hideValues || !angular.isFunction(scope.hideValues()) || !scope.hideValues()(it) || it[scope.valueProperty] === scope.ngModel);
        });
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
          var endpointUrl;
          switch (attrs.type) {
            case 'school':
              scope.options = School.getAll();
              break;
            case 'curriculumversion':
              scope.options = Curriculum.queryVersions(scope.criteria);
              break;
            case 'studyyear':
              scope.options = QueryUtils.endpoint('/autocomplete/studyYears').query({}, afterStudyYearsLoad);
              break;
            case 'building':
              endpointUrl = endpointUrl || '/autocomplete/buildings';
              /* falls through */
            case 'curriculum':
              endpointUrl = endpointUrl || '/autocomplete/curriculumsauto';
              /* falls through */
            case 'curriculumDepartments':
              endpointUrl = endpointUrl || '/autocomplete/curriculumdepartments';
              /* falls through */
            case 'directivecoordinator':
              endpointUrl = endpointUrl || '/autocomplete/directivecoordinators';
              /* falls through */
            case 'journal':
              endpointUrl = endpointUrl || '/autocomplete/journals';
              /* falls through */
            case 'studentgroup':
              endpointUrl = endpointUrl || '/autocomplete/studentgroups';
              /* falls through */
            case 'teacher':
              endpointUrl = endpointUrl || '/autocomplete/teachersList';
              /* falls through */
            case 'enterpriseLocations':
              endpointUrl = endpointUrl || '/autocomplete/enterpriseLocations';
              /* falls through */
            case 'supervisors':
              endpointUrl = endpointUrl || '/autocomplete/supervisors';
              /* falls through */
            case 'contacts':
              endpointUrl = endpointUrl || '/autocomplete/contacts';
              /* falls through */
            case 'practiceEvaluation':
              endpointUrl = endpointUrl || '/autocomplete/practiceEvaluation';
              /* falls through */
            case 'subject':
              endpointUrl = endpointUrl || '/autocomplete/subjectsList';
              /* falls through */
            case 'committee':
              endpointUrl = endpointUrl || '/autocomplete/committeesList';
              scope.options = QueryUtils.endpoint(endpointUrl).query(scope.criteria);
              break;
            case 'enterprise':
              endpointUrl = endpointUrl || '/autocomplete/enterprises';
              /* falls through */
            case 'saisadmissioncode':
              endpointUrl = endpointUrl || '/autocomplete/saisAdmissionCodes';
              /* falls through */
            case 'saisCurriculumClassifiers':
              endpointUrl = endpointUrl || '/autocomplete/saisCurriculumClassifiers';
              /* falls through */
            case 'saisadmissioncodearchived':
              endpointUrl = endpointUrl || '/autocomplete/saisAdmissionCodesArchived';
              /* falls through */
            case 'apelschool':
              endpointUrl = endpointUrl || '/autocomplete/apelschools';
              /* falls through */
            case 'studyperiod':
              endpointUrl = endpointUrl || '/autocomplete/studyPeriods';
              scope.options = QueryUtils.endpoint(endpointUrl).query();
              break;
            case 'studyperiodyear':
              if (!scope.showProperty) {
                scope.showProperty = 'display';
              }
              scope.options = QueryUtils.endpoint('/autocomplete/studyPeriodsWithYear').query(afterStudyPeriodsWithYearLoad);
              break;
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
