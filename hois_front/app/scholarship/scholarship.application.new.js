'use strict';

angular.module('hitsaOis').controller('ScholarshipApplicationNewController', ['$filter', '$location', '$route', '$scope', '$translate', '$q', 'Classifier', 'ScholarshipUtils', 'QueryUtils', 'message',
  function ($filter, $location, $route, $scope, $translate, $q, Classifier, ScholarshipUtils, QueryUtils, message) {
    $scope.auth = $route.current.locals.auth;
    $scope.scholarshipType = $route.current.params.type;
    $scope.formState = {};
    var baseUrl = '/scholarships';

    var clMapper = Classifier.valuemapper({type: 'STIPTOETUS'});
    $q.all(clMapper.promises).then(function () {
      QueryUtils.endpoint(baseUrl + '/schoolAvailableStipends').query({scholarshipType: $scope.scholarshipType})
        .$promise.then(function (result) {
          result.forEach(function (term) {
            clMapper.objectmapper(term);
            term.nameEt += " - " + term.type.nameEt;
            term.nameEn += " - " + (term.type.nameEn !== null ? term.type.nameEn : term.type.nameEt);
            var period = ScholarshipUtils.applicationPeriod(term, $filter, $translate);
            if (period !== null) {
              term.nameEt += ' (' + period + ')';
              term.nameEn += ' (' + period + ')';
            }
          });
          $scope.formState.terms = result;
        });
    });

    $scope.termChanged = function () {
      $scope.formState.student = null;
      $scope.application.student = null;
      $scope.termCompliance = null;
    };

    $scope.studentChanged = function (student) {
      if (student && ($scope.application.student === null || $scope.application.student.id !== student.id)) {
        $scope.application.student = student;
        QueryUtils.endpoint(baseUrl + '/studentTermCompliance/:studentId/:scholarshipTermId').search({
          studentId: student.id,
          scholarshipTermId: $scope.application.termId
        }).$promise.then(function (termCompliance) {
          if (termCompliance.fullyComplies) {
            $location.url(baseUrl + '/' + $scope.application.termId + '/application/' + student.id + '?_noback');
          } else {
            message.error($scope.scholarshipType ? 'stipend.messages.error.studentDoesntComplyScholarship' :
              'stipend.messages.error.studentDoesntComplyGrant');
            $scope.termCompliance = termCompliance;
          }
        });
      }
    };
  }
]);
