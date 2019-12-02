'use strict';

angular.module('hitsaOis').controller('RemarkListController', ['$scope', '$route', '$q', 'Classifier', 'DataUtils', 'QueryUtils', 'message',
  function ($scope, $route, $q, Classifier, DataUtils, QueryUtils, message) {
    $scope.auth = $route.current.locals.auth;
    $scope.teacherId = $scope.auth.teacher;
    var baseUrl = '/remarks';

    var clMapper = Classifier.valuemapper({ reason: 'MARKUS' });
    var promises = clMapper.promises;

    QueryUtils.createQueryForm($scope, baseUrl, {from: moment().startOf('isoweek').toDate(), order: '-remark_time'}, clMapper.objectmapper);
    DataUtils.convertStringToDates($scope.criteria, ['from', 'thru']);

    $scope.formState = {};
    $scope.formState.studyYears = QueryUtils.endpoint('/autocomplete/studyYears').query();
    $scope.formState.studyYears.$promise.then(function () {
      if (!$scope.criteria.studyYear) {
        var sy = DataUtils.getCurrentStudyYearOrPeriod($scope.formState.studyYears);
        if (sy) {
          $scope.criteria.studyYear = sy.id;
        }
      }
      if ($scope.criteria.studyYear) {
        $q.all(promises).then(function () {
          $scope.setStudyYearStartAndEnd();
          $scope.loadData();
        });
      }
    });

    QueryUtils.endpoint('/autocomplete/studentgroups').query({
      higher: false,
      studentGroupTeacherId: $scope.teacherId
    }).$promise.then(function (studentGroups) {
      $scope.formState.studentGroups = studentGroups;
      if (!$scope.criteria.studentGroup) {
        $scope.criteria.studentGroup = studentGroups.length === 1 ? studentGroups[0].id : null;
      }
    });

    $scope.setStudyYearStartAndEnd = function() {
      var studyYear = null;
      if ($scope.criteria.studyYear) {
        studyYear = $scope.formState.studyYears.filter(function (sy) { return sy.id === $scope.criteria.studyYear; })[0];
      }
      $scope.criteria.studyYearStart = studyYear ? studyYear.startDate : null;
      $scope.criteria.studyYearEnd = studyYear ? studyYear.endDate : null;
    };

    $scope.studentGroupChanged = function () {
      $scope.criteria.studentObject = null;
    };

    $scope.$watch('criteria.studentObject', function () {
      $scope.criteria.student = $scope.criteria.studentObject ? $scope.criteria.studentObject.id : null;
    });

    $scope.load = function() {
      if (!$scope.searchForm.$valid) {
        message.error('main.messages.form-has-errors');
        return false;
      } else {
        $scope.loadData();
      }
    };
  }
]).controller('RemarkController', ['$scope', '$route', 'FormUtils', 'QueryUtils',
  function ($scope, $route, FormUtils, QueryUtils) {
    $scope.auth = $route.current.locals.auth;
    $scope.teacherId = $scope.auth.teacher;
    var isJournalRemark = $route.current.params.type === 'journal';

    var baseUrl = '/remarks' + (isJournalRemark ? '/journal' : '');
    var RemarkEndpoint = QueryUtils.endpoint(baseUrl);
    var id = $route.current.params.id;

    if(id) {
      $scope.record = RemarkEndpoint.get({id: id});
    } else {
      $scope.record = new RemarkEndpoint({});
    }

    $scope.update = function() {
      FormUtils.saveRecord($scope.remarkForm, $scope.record, baseUrl);
      $scope.remarkForm.$setPristine();
    };

    $scope.delete = function() {
      FormUtils.deleteRecord($scope.record, '/remarks?_noback', {prompt: 'remark.deleteconfirm'});
    };
  }
]);