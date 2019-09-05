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
  .controller('CurriculumPublicController', function ($scope, QueryUtils, $route, config) {
	
    function entityToForm(curriculum) {
      $scope.curriculum = curriculum;

      $scope.curriculum.studyPeriodMonths = curriculum.studyPeriod % 12;
			$scope.curriculum.studyPeriodYears = Math.floor(curriculum.studyPeriod / 12);
    }
    
    var entity = $route.current.locals.entity;
    if (angular.isDefined(entity)) {
      $scope.publicUrl = config.apiUrl + '/public/curriculum/' + entity.id + '?format=json';
      entityToForm(entity);
    }

}).controller('CurriculumPublicVersionController', ['$scope', '$route', 'config',
function ($scope, $route, config) {
  
  $scope.version = $route.current.locals.curriculumVersion;
  $scope.curriculum = $route.current.locals.curriculum;

  $scope.years = [];
  $scope.mappedSubjects = {};

  $scope.version.$promise.then(function(response) {
    $scope.publicUrl = config.apiUrl + '/public/curriculum/' + $scope.curriculum.id + '/' + $scope.version.id +'?format=json';

    $scope.years = [];
    $scope.mappedSubjects = {};

    response.modules.forEach(function (mod) {
      for (var i = 0; i < mod.subjects.length; i++) {
        if (mod.subjects[i].studyYearNumber !== null) {
          if (!$scope.mappedSubjects[mod.subjects[i].studyYearNumber]) {
            $scope.years.push(mod.subjects[i].studyYearNumber);
            $scope.mappedSubjects[mod.subjects[i].studyYearNumber] = [];
          }
          $scope.mappedSubjects[mod.subjects[i].studyYearNumber].push(mod.subjects[i]);
        }
      }
    });
  });
}]);

