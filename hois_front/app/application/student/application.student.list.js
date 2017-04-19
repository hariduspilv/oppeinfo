'use strict';


angular.module('hitsaOis').controller('ApplicationStudentListController', function ($scope, $location, $route, $q, QueryUtils, Classifier) {
  $scope.auth = $route.current.locals.auth;
  var studentId = $route.current.locals.auth.student;
  var applicationsMapper = Classifier.valuemapper({type: 'AVALDUS_LIIK', status: 'AVALDUS_STAATUS'});
  QueryUtils.createQueryForm($scope, '/students/'+studentId+'/applications', {order: 'type.' + $scope.currentLanguageNameField()}, applicationsMapper.objectmapper);
  $q.all(applicationsMapper.promises).then($scope.loadData);

  if (!$scope.auth.isParent()) {
    var StudentApplicableApplicationsEndpoint = QueryUtils.endpoint('/applications/student/'+studentId+'/applicable');
    StudentApplicableApplicationsEndpoint.search(function(result) {
      $scope.applicationTypesApplicable = result;
      $scope.applicationTypes = Classifier.queryForDropdown({mainClassCode: 'AVALDUS_LIIK'});
    });
  }
});
