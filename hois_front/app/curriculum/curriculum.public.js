'use strict';

angular.module('hitsaOis')
  .controller('CurriculumPublicListController', ['$scope', 'Classifier', 'DataUtils', 'QueryUtils', '$q', '$route',
  function ($scope, Classifier, DataUtils, QueryUtils, $q, $route) {
    var schoolId = $route.current.params.schoolId;

    var clMapper = Classifier.valuemapper({origStudyLevel: 'OPPEASTE', status: 'OPPEKAVA_STAATUS'});
    QueryUtils.createQueryForm($scope, '/public/curriculumsearch', {schoolId: schoolId, order: $scope.currentLanguageNameField()}, clMapper.objectmapper);

    $q.all(clMapper.promises).then($scope.loadData);
    DataUtils.convertStringToDates($scope.criteria, ['validFrom', 'validThru']);

    if (schoolId) {
      $scope.criteria.school = schoolId;
    }
    
  }])
  .controller('CurriculumPublicController', function ($scope, QueryUtils, $route) {
	
    function entityToForm(curriculum) {
      $scope.curriculum = curriculum;

      $scope.curriculum.studyPeriodMonths = curriculum.studyPeriod % 12;
			$scope.curriculum.studyPeriodYears = Math.floor(curriculum.studyPeriod / 12);
    }
    
    var entity = $route.current.locals.entity;
    if (angular.isDefined(entity)) {
      entityToForm(entity);
    }

}).controller('CurriculumPublicVersionController', ['$scope', 'Classifier', 'DataUtils', 'QueryUtils', '$q', '$route',
function ($scope, Classifier, DataUtils, QueryUtils, $q, $route) {
  
  $scope.version = $route.current.locals.curriculumVersion;
  $scope.curriculum = $route.current.locals.curriculum;
}]);

