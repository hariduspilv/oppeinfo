'use strict';

angular.module('hitsaOis').controller('subjectStudyPeriodPlanSearchController', ['$scope', 'QueryUtils', 'ArrayUtils', 'message', 'DataUtils', 'Classifier', '$q', '$translate',
  function ($scope, QueryUtils, ArrayUtils, message, DataUtils, Classifier) {

    QueryUtils.createQueryForm($scope, '/subjectStudyPeriodPlans', {order: 'id'});

    function setCurrentStudyPeriod() {
        if($scope.criteria && !$scope.criteria.studyPeriod) {
            $scope.criteria.studyPeriod = DataUtils.getCurrentStudyYearOrPeriod($scope.studyPeriods).id;
        }
        $scope.loadData();
        $scope.checkIfStudyPeriodIsPast();
    }

    QueryUtils.endpoint('/autocomplete/studyPeriods').query().$promise.then(function(response){
        $scope.studyPeriods = response;
        DataUtils.sortStudyYearsOrPeriods($scope.studyPeriods);
        setCurrentStudyPeriod();
    });

    $scope.curriculums = QueryUtils.endpoint('/subjectStudyPeriodPlans/curriculums').query();
    $scope.capacityTypes = Classifier.queryForDropdown({mainClassCode: 'MAHT', higher: true});

    $scope.$watch('criteria.subjectObject', function() {
            $scope.criteria.subject = $scope.criteria.subjectObject ? $scope.criteria.subjectObject.id : null;
        }
    );

    $scope.$watch('criteria.studyPeriod', function() {
            if($scope.studyPeriods && !$scope.criteria.studyPeriod) {
                setCurrentStudyPeriod();
            }
        }
    );

    $scope.getCapacitiesHours = function(capacityCode, plan) {
        if(!plan.capacities) {
            return;
        }
        var capacity = plan.capacities.find(function(el){return el.capacityType === capacityCode;});
        if(!capacity) {
            return '-';
        }
        return capacity.hours ? capacity.hours : '-';
    };

    $scope.pastStudyPeriodSelected = false;

    $scope.checkIfStudyPeriodIsPast = function() {
        if(!$scope.studyPeriods) {
            return;
        }
        var studyPeriod = $scope.studyPeriods.find(function(el){return el.id === $scope.criteria.studyPeriod; });
        if(studyPeriod) {
            $scope.pastStudyPeriodSelected = DataUtils.isPastStudyYearOrPeriod(studyPeriod);
        }
    };

    $scope.$watch('criteria.curriculum', function() {
            $scope.subjectQueryParams = $scope.criteria.curriculum ?
            {curricula: [$scope.criteria.curriculum], status: ['AINESTAATUS_K']} : {status: ['AINESTAATUS_K']};
        }
    );

}]).controller('subjectStudyPeriodPlanNewController', ['$scope', 'QueryUtils', 'ArrayUtils', 'message', 'DataUtils', '$mdDialog', 'dialogService', '$route', 'Classifier', '$location',
  function ($scope, QueryUtils, ArrayUtils, message, DataUtils, $mdDialog, dialogService, $route, Classifier, $location) {

    var id = $route.current.params.id;
    var baseUrl = '/subjectStudyPeriodPlans';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    /**
     * Probably readonly is not needed at all, as user do not have access to form in this case.
     * Required security check is also present in back end
     */
    $scope.readOnly = false;

    var initialRecord = {
        studyPeriod: $route.current.params.studyPeriodId,
        subject: $route.current.params.subjectId,
        studyForms: [],
        curriculums: []
    };

    function getStudyPeriod(studyPeriodId) {
        QueryUtils.endpoint(baseUrl + "/studyPeriod/" + studyPeriodId).get().$promise.then(
            function(response) {
                $scope.studyPeriod = response;
                $scope.readOnly = DataUtils.isPastStudyYearOrPeriod($scope.studyPeriod);
            }
        );
    }

    function getStudySubject(subjectId) {
      $scope.subject = QueryUtils.endpoint(baseUrl + "/subject/" + subjectId).get();
    }

    function getCurriculums() {
        QueryUtils.endpoint(baseUrl + "/curriculums").query({subjects: [$scope.record.subject]}).$promise.then(
            function(response) {
                $scope.curriculums = response;
                $scope.curriculums.forEach(function(el){
                    el.selected = ArrayUtils.includes($scope.record.curriculums, el.id);
                });
            }
        );
    }

    function setInitialCapacities() {
        Classifier.queryForDropdown({mainClassCode: 'MAHT', higher: true}, function(response){
            var capacities = [];
            for(var i = 0; i < response.length; i++) {
                capacities.push({isContact: false, hours: null, capacityType: response[i].code});
            }
            $scope.record.capacities = capacities;
        });
    }

    function hasCapacity(capacityCode) {
      var capacity = $scope.record.capacities.find(function(el){
        return el.capacityType === capacityCode;
      });
      return angular.isDefined(capacity) && capacity !== null;
    }

    function addMissingCapacities() {
        if(!$scope.record.capacities) {
          $scope.record.capacities = [];
        }
        Classifier.queryForDropdown({mainClassCode: 'MAHT', higher: true}, function(response){
            for(var i = 0; i < response.length; i++) {
              if(!hasCapacity(response[i].code)) {
                $scope.record.capacities.push({isContact: false, hours: null, capacityType: response[i].code});
              }
            }
        });
    }

    function getStudyForms() {
        Classifier.queryForDropdown({mainClassCode: 'OPPEVORM', higher: true}, function(response){
            $scope.studyForms = response;
            $scope.studyForms.forEach(function(el){
                el.selected = ArrayUtils.includes($scope.record.studyForms, el.code);
            });
        });
    }

    if (id) {
        Endpoint.get({ id: id }).$promise.then(function (response) {
            $scope.record = response;
            getStudyPeriod(response.studyPeriod);
            getStudySubject(response.subject);
            getCurriculums();
            getStudyForms();
            addMissingCapacities();
        });
    } else {
        $scope.record = new Endpoint(initialRecord);
            setInitialCapacities();
            getStudyPeriod($scope.record.studyPeriod);
            getStudySubject($scope.record.subject);
            getCurriculums();
            getStudyForms();
    }

    $scope.getTotalCapacity = function() {
        if(!$scope.record || !$scope.record.capacities) {
            return 0;
        }
        return $scope.record.capacities.reduce(function(sum, el){
            return el.hours ? sum + el.hours : sum;
        }, 0);
    };

    function createOrUpdate() {
        if($scope.record.id) {
            $scope.record.$update().then(function(){
                message.info('main.messages.create.success');
            });
        } else {
            $scope.record.$save().then(function(response){
                message.info('main.messages.create.success');
                $location.path(baseUrl + "/" + response.id + "/edit");
            });
        }
    }

    $scope.save = function(){
        if(!$scope.subjectStudyPeriodPlanEditForm.$valid) {
            message.error('main.messages.form-has-errors');
            return;
        }
        $scope.record.studyForms = $scope.studyForms.filter(function(el){return el.selected;}).map(function(el){return el.code;});
        $scope.record.curriculums = $scope.curriculums.filter(function(el){return el.selected;}).map(function(el){return el.id;});

        QueryUtils.endpoint(baseUrl + '/exists').search($scope.record).$promise.then(function(response){
            if(response.exists) {
                dialogService.confirmDialog({prompt: 'subjectStudyPeriodPlan.overrideconfirm'}, function() {
                    createOrUpdate();
                });
            } else {
                createOrUpdate();
            }
        });
    };

    $scope.delete = function() {
        dialogService.confirmDialog({prompt: 'subjectStudyPeriodPlan.deleteconfirm'}, function() {
            $scope.record.$delete().then(function() {
                message.info('main.messages.delete.success');
                $location.path(baseUrl);
            });
        });
    };
}]);
