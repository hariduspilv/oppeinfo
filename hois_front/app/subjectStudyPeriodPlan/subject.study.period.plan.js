'use strict';

angular.module('hitsaOis').controller('subjectStudyPeriodPlanSearchController', 
['$scope', 'QueryUtils', 'ArrayUtils', 'message', 'DataUtils', 'Classifier',
function ($scope, QueryUtils, ArrayUtils, message, DataUtils, Classifier) {

    QueryUtils.createQueryForm($scope, '/subjectStudyPeriodPlans', {order: 'id'});
    $scope.loadData();

    function setCurrentStudyPeriod() {
        // $scope.criteria.studyPeriod = $scope.studyPeriods.find(function(item){
        //     DataUtils.convertStringToDates(item, ["startDate", "endDate"]);
        //     return new Date() <= item.endDate;
        // }).id;
        $scope.criteria.studyPeriod = DataUtils.getCurrentStudyYearOrPeriod($scope.studyPeriods).id;
    }

    QueryUtils.endpoint('/subjectStudyPeriodPlans/studyPeriods').query().$promise.then(function(response){
        // $scope.studyPeriods = response.sort(function(el1, el2){
        //     return el1.startDate >= el2.startDate;
        // });
        $scope.studyPeriods = response;
        DataUtils.sortStudyYearsOrPeriods(response);
        console.log("$scope.studyPeriods, ", $scope.studyPeriods);
        setCurrentStudyPeriod();
    });

    QueryUtils.endpoint('/subjectStudyPeriodPlans/curriculums').query().$promise.then(function(response){
        $scope.curriculums = response;
        console.log("$scope.curriculums, ", $scope.curriculums);
    });

    Classifier.queryForDropdown({mainClassCode: 'MAHT'}, function(response){
        $scope.capacityTypes = response;
    });

    $scope.$watch('criteria.subjectObject', function() {
            if($scope.criteria.subjectObject) {
                $scope.criteria.subject = $scope.criteria.subjectObject.id;
            }
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
        return capacity.hours;
    };

}]).controller('subjectStudyPeriodPlanNewController', 
['$scope', 'QueryUtils', 'ArrayUtils', 'message', 'DataUtils', '$mdDialog', 'dialogService', '$route', 'Classifier', '$location',
function ($scope, QueryUtils, ArrayUtils, message, DataUtils, $mdDialog, dialogService, $route, Classifier, $location) {

    var id = $route.current.params.id;
    var baseUrl = '/subjectStudyPeriodPlans';
    var Endpoint = QueryUtils.endpoint(baseUrl);

    var initialRecord = {
        studyPeriod: $route.current.params.studyPeriodId,
        subject: $route.current.params.subjectId,
        studyForms: [],
        curriculums: [],
        capacities: [
            {isContact: false, hours: 0, capacityType: 'MAHT_a'},
            {isContact: false, hours: 0, capacityType: 'MAHT_i'},
            {isContact: false, hours: 0, capacityType: 'MAHT_p'},
            {isContact: false, hours: 0, capacityType: 'MAHT_l'}
        ]

    };

    function getStudyPeriod(studyPeriodId) {
        QueryUtils.endpoint(baseUrl + "/studyPeriod/" + studyPeriodId).get().$promise.then(
            function(response) {
                $scope.studyPeriod = response;
            }
        );
    }

    function getStudySubject(subjectId) {
        QueryUtils.endpoint(baseUrl + "/subject/" + subjectId).get().$promise.then(
            function(response) {
                $scope.subject = response;
            }
        );
    }

    if (id) {
        Endpoint.get({ id: id }).$promise.then(function (response) {
            $scope.record = response;
            getStudyPeriod(response.studyPeriod);
            getStudySubject(response.subject);
        });
    } else {
        $scope.record = new Endpoint(initialRecord);
            getStudyPeriod($scope.record.studyPeriod);
            getStudySubject($scope.record.subject);
    }

    Classifier.queryForDropdown({mainClassCode: 'MAHT'}, function(response){
        $scope.capacityTypes = response;
    });

    Classifier.queryForDropdown({mainClassCode: 'OPPEVORM'}, function(response){
        $scope.studyForms = response;
    });

    QueryUtils.endpoint(baseUrl + "/curriculums").query().$promise.then(
        function(response) {
            $scope.curriculums = response;
        }
    );


    $scope.getTotalCapacity = function() {
        if(!$scope.record) {
            return 0;
        }
        return $scope.record.capacities.reduce(function(sum, el){
            return sum + el.hours;
        }, 0);
    };

    $scope.save = function(){
        if(!$scope.subjectStudyPeriodPlanEditForm.$valid) {
            message.error('main.messages.form-has-errors');
            return;
        }
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
