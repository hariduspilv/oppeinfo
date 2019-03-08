'use strict';

angular.module('hitsaOis').controller('PracticeStudentListController', function ($scope, $route, QueryUtils, Classifier, $q) {
    $scope.currentNavItem = 'practice.students';    
    $scope.auth = $route.current.locals.auth;
    var clMapper = Classifier.valuemapper({
        status: 'LEPING_STAATUS'
    });
    $scope.formState = {xlsUrl: 'practiceEnterprise/practiceStudentStatistics.xls'};
    QueryUtils.createQueryForm($scope, '/practiceEnterprise/studentStatistics', {}, clMapper.objectmapper);
    $q.all(clMapper.promises).then($scope.loadData);

}).controller('PracticeContractListController', function ($scope, $route, QueryUtils, Classifier, $q) {
    $scope.currentNavItem = 'practice.contracts';    
    $scope.auth = $route.current.locals.auth;
    var clMapper = Classifier.valuemapper({
        cancelCode: 'LEPING_TYH_POHJUS'
    });
    $scope.formState = {xlsUrl: 'practiceEnterprise/practiceContractStatistics.xls'};
    QueryUtils.createQueryForm($scope, '/practiceEnterprise/contractStatistics', {}, clMapper.objectmapper);
    $q.all(clMapper.promises).then($scope.loadData);

}).controller('PracticeStudyYearListController', function ($scope, $route, QueryUtils, Classifier, $q) {
    $scope.currentNavItem = 'practice.studyYear';    
    $scope.auth = $route.current.locals.auth;
    var clMapper = Classifier.valuemapper({
        status: 'LEPING_STAATUS'
    });
    $scope.formState = {xlsUrl: 'practiceEnterprise/practiceStudyYearStatistics.xls'};
    QueryUtils.createQueryForm($scope, '/practiceEnterprise/studyYearStatistics', {}, clMapper.objectmapper);
    $q.all(clMapper.promises).then($scope.loadData);

});