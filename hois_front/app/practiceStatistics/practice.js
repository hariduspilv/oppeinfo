'use strict';

angular.module('hitsaOis').controller('PracticeStudentListController', function ($scope, $route, QueryUtils, Classifier, $q, $localStorage) {
    $scope.currentNavItem = 'practice.students';    
    $scope.auth = $route.current.locals.auth;
    var clMapper = Classifier.valuemapper({
        status: 'LEPING_STAATUS'
    });
    if ($route.current.params._menu) {
        $localStorage.contractStatisticsReset = true;
        $localStorage.studyYearStatisticsReset = true;
    }
    if ($localStorage.contractStatisticsReset) {
        $scope.contractParam = "?_menu";
    }
    if ($localStorage.studyYearStatisticsReset) {
        $scope.studyYearParam = "?_menu";
    }
    $scope.formState = {xlsUrl: 'practiceEnterprise/practiceStudentStatistics.xls'};
    QueryUtils.createQueryForm($scope, '/practiceEnterprise/studentStatistics', {}, clMapper.objectmapper);
    $q.all(clMapper.promises).then($scope.loadData);

}).controller('PracticeContractListController', function ($scope, $route, QueryUtils, Classifier, $q, $localStorage) {
    $scope.currentNavItem = 'practice.contracts';    
    $scope.auth = $route.current.locals.auth;
    var clMapper = Classifier.valuemapper({
        cancelCode: 'LEPING_TYH_POHJUS'
    });
    if ($localStorage.studyYearStatisticsReset) {
        $scope.studyYearParam = "?_menu";
    }
    $localStorage.contractStatisticsReset = false;
    $scope.formState = {xlsUrl: 'practiceEnterprise/practiceContractStatistics.xls'};
    QueryUtils.createQueryForm($scope, '/practiceEnterprise/contractStatistics', {}, clMapper.objectmapper);
    $q.all(clMapper.promises).then($scope.loadData);

}).controller('PracticeStudyYearListController', function ($scope, $route, QueryUtils, Classifier, $q, $localStorage) {
    $scope.currentNavItem = 'practice.studyYear';    
    $scope.auth = $route.current.locals.auth;
    var clMapper = Classifier.valuemapper({
        status: 'LEPING_STAATUS'
    });
    if ($localStorage.contractStatisticsReset) {
        $scope.contractParam = "?_menu";
    }
    $localStorage.studyYearStatisticsReset = false;
    $scope.formState = {xlsUrl: 'practiceEnterprise/practiceStudyYearStatistics.xls'};
    QueryUtils.createQueryForm($scope, '/practiceEnterprise/studyYearStatistics', {}, clMapper.objectmapper);
    $q.all(clMapper.promises).then($scope.loadData);

});