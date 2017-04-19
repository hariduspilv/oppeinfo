'use strict';

angular.module('hitsaOis').controller('ReceptionSaisAdmissionListController', function ($q, $scope, Classifier, Curriculum, QueryUtils) {
  var clMapper = Classifier.valuemapper({studyForm: 'OPPEVORM', language: 'OPPEKEEL'});
  QueryUtils.createQueryForm($scope, '/saisAdmissions', {order: 'code'}, clMapper.objectmapper);
  $q.all(clMapper.promises).then($scope.loadData);

  $scope.saisAdmissionCodes = QueryUtils.endpoint('/autocomplete/saisAdmissionCodes').query();
  $scope.curriculumVersions = Curriculum.queryVersions({sais: true});
});
