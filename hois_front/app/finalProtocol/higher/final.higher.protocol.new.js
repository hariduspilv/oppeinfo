'use strict';

angular.module('hitsaOis').controller('FinalHigherProtocolNewController', 
function ($scope, $route, $location, $q, QueryUtils, DataUtils, Classifier, message, ArrayUtils) {
  var endpoint = '/finalHigherProtocols';

  $scope.auth = $route.current.locals.auth;
  $scope.formState = {
    protocolType: 'PROTOKOLLI_LIIK_P',
    isFinalThesis: $route.current.params.moduleType === 'thesis' ? true : false,
    selectedStudents: [],
    subjects: []
  };

  var clMapper = Classifier.valuemapper({
    status: 'OPPURSTAATUS'
  });

  function getStudyPeriodSubjects() {
    if (!$scope.formState.isFinalThesis && $scope.formState.studyPeriod && $scope.formState.curriculum) {
      $scope.formState.subjects = QueryUtils.endpoint(endpoint + '/subjects/exam/' 
        + $scope.formState.studyPeriod + '/' + $scope.formState.curriculum).query();
    } else if ($scope.formState.isFinalThesis && $scope.formState.curriculum) {
      $scope.formState.subjects = QueryUtils.endpoint(endpoint + '/subjects/thesis/' + $scope.formState.curriculum).query();
    }
  }
  
  $scope.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriods').query();
  $scope.curriculums = QueryUtils.endpoint(endpoint + '/curriculums/' + $route.current.params.moduleType).query();
  
  var promises = [];
  promises.push($scope.studyPeriods.$promise);

  $q.all(promises).then(function() {
    $scope.formState.studyPeriod = DataUtils.getCurrentStudyYearOrPeriod($scope.studyPeriods).id;
  });

  $scope.studyPeriodChanged = function () {
    $scope.formState.subject = undefined;
    getStudyPeriodSubjects();
  };

  $scope.curriculumChanged = function () {
    $scope.formState.subject = undefined;
    getStudyPeriodSubjects();
  };

  $scope.subjectChanged = function () {
    var query = QueryUtils.endpoint(endpoint + '/subject/' + $route.current.params.moduleType + '/' + $scope.formState.curriculum + '/' + $scope.formState.subject.id).get();

    $scope.tabledata = {
      $promise: query.$promise
    };

    query.$promise.then(function (result) {
      $scope.formState.selectedStudents = [];
      $scope.tabledata.content = clMapper.objectmapper(result.subjectStudents);
      result.subjectStudents.forEach(function (it) {
        if (it.status.code === 'OPPURSTAATUS_O') {
          $scope.formState.selectedStudents.push(it.studentId);
        }
      });
    });
  };

  var FinalProtocolEndpoint = QueryUtils.endpoint(endpoint);
  $scope.submit = function () {
    if(!$scope.finalProtocolNewForm.$valid) {
      message.error('main.messages.form-has-errors');
      $scope.finalProtocolNewForm.$setSubmitted();
      return false;
    }

    if (ArrayUtils.isEmpty($scope.formState.selectedStudents)) {
      message.error("finalProtocol.error.noStudents");
      return;
    }

    var entity = {};
    if ($route.current.params.moduleType === 'thesis') {
      entity.isFinalThesis = true;
      entity.subject = $scope.formState.subject.id;
    } else if ($route.current.params.moduleType === 'exam') {
      entity.isFinalThesis = false;
      entity.subjectStudyPeriod = $scope.formState.subject.id;
    }

    entity.protocolType = $scope.formState.protocolType;
    entity.protocolStudents = $scope.formState.selectedStudents.map(function (it) {
      return {
        studentId: it
      };
    });
    entity.curriculumVersion = $scope.formState.curriculum;

    new FinalProtocolEndpoint(entity)
      .$save().then(function (result) {
        message.info('main.messages.create.success');
        $location.path(endpoint + '/' + result.id + '/edit');
      });
  };
});
