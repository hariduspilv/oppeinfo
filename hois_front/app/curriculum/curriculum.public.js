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
  .controller('CurriculumPublicController', function ($scope, $route, config, Classifier, ArrayUtils, Curriculum, $q, dialogService) {
    var clMapper = Classifier.valuemapper({ partOccupations: 'OSAKUTSE', specialityCodes: 'SPETSKUTSE'});
    var moduleTypes = Object.freeze({ // Sorting module types
      KUTSEMOODUL_P: 0,
      KUTSEMOODUL_Y: 1,
      KUTSEMOODUL_L: 2,
      KUTSEMOODUL_V: 3
    });

    $scope.moduleOrderBy = Curriculum.curriculumModuleOrder;

    Classifier.queryForDropdown({mainClassCode: 'KUTSEMOODUL'}, function(response) {
      $scope.moduleTypes = response;
      $scope.moduleTypes.sort(function (a, b) {
        return moduleTypes[a.code] - moduleTypes[b.code];
      });
    });

    $scope.filterTypesWithoutEmptyModules = function(type) {
      if (!$scope.curriculum) {
        return false;
      }
      var modules = $scope.curriculum.modules.filter(function(el){
        return ArrayUtils.isEmpty(el.occupationCodes);
      });
      var thisTypeModules = modules ? modules.filter(function(el){
        return el.moduleCode === type.code;
      }) : [];
      return !ArrayUtils.isEmpty(thisTypeModules);
    };

    $scope.filterTypesWithoutModules = function(occupationCode, modules) {
      return function(type) {
        var thisTypeModules = modules ? modules.filter(function(el){
          var occupations = el.occupationCodes;
          return ArrayUtils.includes(occupations, occupationCode) && el.moduleCode === type.code;
        }) : [];
        return !ArrayUtils.isEmpty(thisTypeModules);
      };
    };

    $scope.filterModulesByType = function(typeCode, occupationCode) {
      return function(module1) {
          var moduleOccupations = module1.occupationCodes;
          return module1.moduleCode === typeCode && ArrayUtils.includes(moduleOccupations, occupationCode);
      };
    };
    
    $scope.getTotalCredits = function (modules) {
      return modules.reduce(function (sum, module) {
        return sum + module.credits;
      }, 0);
    };

    $scope.openViewModuleDialog = function(curriculumModule) {
      dialogService.showDialog('curriculum/dialog/module.view.dialog.html',
      function(dialogScope) {
          dialogScope.occupationsSelected = {};
          dialogScope.partOccupationsSelected = {};
          dialogScope.specialitiesSelected = {};
          dialogScope.module = {
            outcomes: [],
            occupations: [],
            curriculumOccupations: $scope.curriculum.occupations
          };

          if (angular.isDefined(curriculumModule)) {
            angular.extend(dialogScope.module, curriculumModule);
            dialogScope.module.module = curriculumModule.moduleCode;
            if (angular.isArray(curriculumModule.occupations)) {
              dialogScope.module.occupations = curriculumModule.occupationCodes;
              curriculumModule.occupationCodes.forEach(function(occupation) {
                if (angular.isDefined(occupation)) {
                  if(occupation.indexOf('KUTSE') === 0 || occupation.indexOf('OSAKUTSE') === 0) {
                    dialogScope.occupationsSelected[occupation] = true;
                  } else if(occupation.indexOf('SPETSKUTSE') === 0) {
                    dialogScope.specialitiesSelected[occupation] = true;
                  }
                }
              });
            }
          }
      },
      null);
    };

    function entityToForm(curriculum) {
      $scope.curriculum = curriculum;

      $scope.curriculum.studyPeriodMonths = curriculum.studyPeriod % 12;
      $scope.curriculum.studyPeriodYears = Math.floor(curriculum.studyPeriod / 12);
      clMapper.objectmapper(curriculum.occupations);
      $scope.curriculumPdfUrl = config.apiUrl + '/public/print/curriculum/' + curriculum.id + '/general.pdf';
      $scope.curriculumModulesPdfUrl = config.apiUrl + '/public/print/curriculum/' + curriculum.id + '/modules.pdf';
    }
    
    var entity = $route.current.locals.entity;
    if (angular.isDefined(entity)) {
      $scope.publicUrl = config.apiUrl + '/public/curriculum/' + entity.id + '?format=json';
      $q.all(clMapper.promises).then(function () {
        entityToForm(entity);
      });
    }

}).controller('CurriculumPublicVersionController', ['$scope', '$route', 'config', 'Curriculum', 'dialogService', 'QueryUtils', 'Classifier',
function ($scope, $route, config, Curriculum, dialogService, QueryUtils, Classifier) {
  
  $scope.version = $route.current.locals.curriculumVersion;
  $scope.curriculum = $route.current.locals.curriculum;
  $scope.moduleOrderBy = Curriculum.curriculumModuleOrder;
  var assessments = Classifier.queryForDropdown({ mainClassCode: 'KUTSEHINDAMISVIIS'});
  $scope.capacities = QueryUtils.endpoint('/public/' + $scope.curriculum.id + '/schoolCapacityTypes').query({ isHigher: false });

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
  
    $scope.curriculumVersionPdfUrl = config.apiUrl + '/public/print/curriculumVersion/' + $scope.version.id + '/general.pdf';
    $scope.curriculumVersionModulesPdfUrl = config.apiUrl + '/public/print/curriculumVersion/' + $scope.version.id + '/modules.pdf';
  });
  
  $scope.openViewThemeDialog = function (theme) {
    dialogService.showDialog('curriculum/dialog/theme.view.dialog.html',
      function (dialogScope) {
        theme.capacities = uniqueCapacityArray(theme.capacities.concat($scope.capacities.map(classifierToCapacity)));
        dialogScope.occupationModuleTheme = angular.copy(theme);
        dialogScope.assessments = assessments;

        function uniqueCapacityArray(arr) {
          var a = arr.concat();
          for (var i = 0; i < a.length; ++i) {
              for (var j = i+1; j < a.length; ++j) {
                  if (a[i].capacityType === a[j].capacityType) {
                      a.splice(j--, 1);
                  }
              }
          }
          return a;
        }

        function classifierToCapacity(c) {
          return {
            capacityType: c.nameEt
          };
        }
      });
  };

}]);

