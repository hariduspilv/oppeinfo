'use strict';

angular.module('hitsaOis').controller('ReceptionSaisAdmissionListController', function ($scope, QueryUtils, Classifier) {
  var clMapper = Classifier.valuemapper({studyForm: 'OPPEVORM', language: 'OPPEKEEL'});
  QueryUtils.createQueryForm($scope, '/saisAdmissions', {order: 'code'}, clMapper.objectmapper);
  $scope.loadData();

  var SaisAdmissionsEndpoint = QueryUtils.endpoint('/autocomplete/saisAdmissionCodes');
  SaisAdmissionsEndpoint.query({}, function(result) {
    $scope.saisAdmissionCodes = result;
  });


  var CurriculumVersionsEndpoint = QueryUtils.endpoint('/saisAdmissions/curriculumVersions');
  CurriculumVersionsEndpoint.query({}, function(result) {
    $scope.curriculumVersions = result;
  });
});
