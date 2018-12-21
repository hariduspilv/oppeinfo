'use strict';

angular.module('hitsaOis').controller('DeclarationEditController', ['$scope', 'dialogService', 'QueryUtils', 'message', 'ArrayUtils', '$route', '$location', function ($scope, dialogService, QueryUtils, message, ArrayUtils, $route, $location) {

  var SubjectEndpoint = QueryUtils.endpoint('/declarations/subject');
  var ConfirmEndPoint = QueryUtils.endpoint('/declarations/confirm');

  var id = $route.current.params.id;
  $scope.formState = {};
  $scope.auth = $route.current.locals.auth;
  $scope.addCurriculumSubjectForm = false;
  $scope.addExtraCurriculumSubjectFrom = false;

  if($scope.auth.isStudent() && !angular.isDefined(id)) {
    QueryUtils.endpoint('/declarations/hasPrevious').search().$promise.then(function(response){
      $scope.currentNavItem = 'current';
      $scope.formState.showNavBar = response.hasPrevious;
    });
  }

  if(id) {
    $scope.declaration = QueryUtils.endpoint('/declarations').get({id: id});
  } else {
    $scope.declaration = QueryUtils.endpoint('/declarations/current').get();
  }

  $scope.confirm = function() {
    dialogService.confirmDialog({prompt: 'declaration.prompt.confirm'}, function() {
      new ConfirmEndPoint($scope.declaration).$update().then(function(response){
        message.info('declaration.message.confirmed');
        $location.path("/declarations/" + response.id + "/view");
      });
    });
  };

  $scope.removeConfirmation = function() {
    dialogService.confirmDialog({prompt: 'declaration.prompt.removeConfirmation'}, function() {
      var EndPoint = QueryUtils.endpoint('/declarations/removeConfirm');
      new EndPoint($scope.declaration).$update().then(function(response){
        message.info('declaration.message.confirmationRemoved');
        $scope.declaration = response;
      });
    });
  };

  $scope.removeSubject = function(subject) {
    new SubjectEndpoint(subject).$delete().then(function(){
      message.info('declaration.message.subjectRemoved');
      ArrayUtils.remove($scope.declaration.subjects, subject);
      $scope.loadData();
    });
  };

  QueryUtils.createQueryForm($scope, '/declarations/subjects/' + id);
  $scope.afterLoadData = function (resultData) {
    $scope.tabledata.content = resultData.content;
    $scope.tabledata.totalElements = resultData.totalElements;
  };
  $scope.criteria = { order: 'cvhm.' + $scope.currentLanguageNameField(), size: 10, page: 1};

  $scope.openAddCurriculumSubject = function () {
    $scope.addCurriculumSubject = true;
    $scope.addExtraCurriculumSubject = false;
    $scope.loadData();
  };

  $scope.addCurriculumSubject = function(subject) {
    var newSubject = new SubjectEndpoint({
      subjectStudyPeriod: subject.subjectStudyPeriod,
      declaration: $scope.declaration.id,
      curriculumVersionHigherModule: subject.module.id,
      isOptional: subject.isOptional
    });
    newSubject.$save().then(function(response){
      message.info('declaration.message.subjectAdded');
      response.newlyAdded = true;
      $scope.declaration.subjects.push(response);
      $scope.loadData();
    });
  };

  $scope.openAddCurriculumSubject = function () {
    $scope.addCurriculumSubjectForm = true;
    $scope.addExtraCurriculumSubjectForm = false;
    $scope.loadData();
  };

  function getExtraCurriculumSubjectsOptions() {
    $scope.subjects = QueryUtils.endpoint('/declarations/subjects/extracurriculum/' + id).query();
  } 

  $scope.openAddExtraCurriculumSubject = function() {
    $scope.addCurriculumSubjectForm = false;
    $scope.addExtraCurriculumSubjectForm = true;
    getExtraCurriculumSubjectsOptions();
  };

  $scope.addExtraCurriculumSubject = function() {
    $scope.extraCurriculumForm.$setSubmitted();
    if(!$scope.subject) {
      return;
    }
    var newSubject = new SubjectEndpoint({
      subjectStudyPeriod: $scope.subject.subjectStudyPeriod,
      declaration: $scope.declaration.id,
      curriculumVersionHigherModule: $scope.subject.module !== null ? $scope.subject.module.id : null,
      isOptional: true
    });
    newSubject.$save().then(function(response){
      message.info('declaration.message.subjectAdded');
      response.newlyAdded = true;
      $scope.declaration.subjects.push(response);
      getExtraCurriculumSubjectsOptions();
      $scope.subject = undefined;
    });
  };

  $scope.hasPrerequisites = function(subject) {
    return !ArrayUtils.isEmpty(subject.mandatoryPrerequisiteSubjects) || !ArrayUtils.isEmpty(subject.recommendedPrerequisiteSubjects);
  };

  $scope.seePrerequisites = function(subject) {
      var DialogController = function (scope) {
        scope.subject = subject;
      };

      dialogService.showDialog('declaration/declaration.subject.prerequisites.html', DialogController,
        function () {
      });
  };

}]).controller('DeclarationViewController', ['$scope', 'dialogService', 'QueryUtils', 'message', 'ArrayUtils', '$route', '$location', function ($scope, dialogService, QueryUtils, message, ArrayUtils, $route, $location) {

  var id = $route.current.params.id;
  $scope.currentDeclarationPage = $location.path() === '/declaration/current/view';
  $scope.auth = $route.current.locals.auth;
  var ConfirmEndPoint = QueryUtils.endpoint('/declarations/confirm');

  $scope.formState = {};
  if($scope.auth.isStudent() && !angular.isDefined(id)) {
    QueryUtils.endpoint('/declarations/hasPrevious').search().$promise.then(function(response){
      $scope.currentNavItem = 'current';
      $scope.formState.showNavBar = response.hasPrevious;
    });
  }

  if(id) {
      $scope.declaration = QueryUtils.endpoint('/declarations').get({id: id}, function(){
      $scope.formState.declaration = true;
    });
  } else {
    QueryUtils.endpoint('/declarations/current').get().$promise.then(function(response){
      if(!response.id) {
        $scope.formState.noDeclaration = true;

        QueryUtils.endpoint('/declarations/isDeclarationPeriod').search().$promise.then(function(response){
          $scope.formState.isDeclarationPeriod = response.isDeclarationPeriod;
          if (response.declarationPeriodStart) {
            $scope.formState.declarationPeriodStart = response.declarationPeriodStart;
          } else if (response.declarationPeriodEnd) {
            $scope.formState.declarationPeriodEnd = response.declarationPeriodEnd;
          }

          if ($scope.formState.isDeclarationPeriod) {
            QueryUtils.endpoint('/declarations/canCreate').search().$promise.then(function(response){
              $scope.formState.canCreateDeclaration = response.canCreate;
            });
          }
        });

      } else {
        $scope.declaration = response;
        $scope.formState.declaration = true;
      }
    });
  }

  $scope.addDeclaration = function() {
    var Endpoint = QueryUtils.endpoint('/declarations/create');
    new Endpoint().$save().then(function(response){
      $location.path("/declarations/" + response.id + "/edit");
    });
  };

  $scope.confirm = function() {
    dialogService.confirmDialog({prompt: 'declaration.prompt.confirm'}, function() {
      new ConfirmEndPoint($scope.declaration).$update().then(function(response){
        message.info('declaration.message.confirmed');
        $scope.declaration = response;
      });
    });
  };

  $scope.removeConfirmation = function() {
    dialogService.confirmDialog({prompt: 'declaration.prompt.removeConfirmation'}, function() {
      var EndPoint = QueryUtils.endpoint('/declarations/removeConfirm');
      new EndPoint($scope.declaration).$update().then(function(response){
        message.info('declaration.message.confirmationRemoved');
        $scope.declaration = response;
      });
    });
  };

  $scope.hasPrerequisites = function(subject) {
    return !ArrayUtils.isEmpty(subject.mandatoryPrerequisiteSubjects) || !ArrayUtils.isEmpty(subject.recommendedPrerequisiteSubjects);
  };

  $scope.seePrerequisites = function(subject) {
      var DialogController = function (scope) {
        scope.subject = subject;
      };

      dialogService.showDialog('declaration/declaration.subject.prerequisites.html', DialogController,
        function () {
      });
  };

}]).controller('DeclarationSearchController', ['$scope', '$route', '$q', '$timeout', 'Classifier', 'DataUtils', 'QueryUtils', 'message', 'dialogService', 'USER_ROLES', 'AuthService',
  function ($scope, $route, $q, $timeout, Classifier, DataUtils, QueryUtils, message, dialogService, USER_ROLES, AuthService) {
    var auth = $route.current.locals.auth;
    $scope.canConfirm = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_K_TEEMAOIGUS_OPINGUKAVA);
    var clMapper = Classifier.valuemapper({status: 'OPINGUKAVA_STAATUS'});
    QueryUtils.createQueryForm($scope, '/declarations', {order: 'dId'}, clMapper.objectmapper);
    DataUtils.convertStringToDates($scope.criteria, ['inserted', 'confirmDate']);

    $scope.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriodsWithYear').query();
    $scope.studyPeriods.$promise.then(function (response) {
      response.forEach(function (studyPeriod) {
        studyPeriod[$scope.currentLanguageNameField()] = $scope.currentLanguageNameField(studyPeriod.studyYear) + ' ' + $scope.currentLanguageNameField(studyPeriod);
      });
    });
    $scope.curriculumVersions = QueryUtils.endpoint('/autocomplete/curriculumversions').query({higher: true, valid: true});
    var promises = clMapper.promises;
    promises.push($scope.studyPeriods.$promise);
    promises.push($scope.curriculumVersions.$promise);

    $scope.formState = {canCreate: auth.authorizedRoles.indexOf('ROLE_OIGUS_M_TEEMAOIGUS_OPINGUKAVA') !== -1 };

    var loadData = $scope.loadData;
    $scope.loadData = function() {
      console.log($scope.criteria);
      $scope.declarationSearchForm.$setSubmitted();
      if(!$scope.declarationSearchForm.$valid) {
        message.error('main.messages.form-has-errors');
      } else {
        loadData();
      }
    };

    $q.all(promises).then(function() {
      if($scope.studyPeriods.length > 0 && !$scope.criteria.studyPeriod) {
        var currentStudyPeriod = DataUtils.getCurrentStudyYearOrPeriod($scope.studyPeriods);
        $scope.criteria.studyPeriod = currentStudyPeriod ? currentStudyPeriod.id : undefined;
      }
      if($scope.criteria.studyPeriod) {
        $timeout($scope.loadData);
      } else if($scope.studyPeriods.length === 0) {
        $scope.formState.canCreate = false;
        message.error('studyYear.studyPeriod.missing');
      }
    });

    $scope.confirmAll = function() {
      dialogService.confirmDialog({prompt: 'declaration.prompt.confirmAll'}, function() {
        QueryUtils.endpoint('/declarations/confirm/all').put().$promise.then(function(response){
          message.info('declaration.message.allConfirmed', {numberOfNewlyConfirmedDeclarations: response.numberOfNewlyConfirmedDeclarations});
          $scope.loadData();
        });
      });
    };

    $scope.showUncomposed = function() {
      var DialogController = function (scope) {
        QueryUtils.createQueryForm(scope, '/declarations/withoutDeclaration/', {order: 'p.lastname,p.firstname'});
        scope.clearCriteria();
        scope.loadData();
      };

      dialogService.showDialog('declaration/uncomposed.declarations.html', DialogController,
        function () {
      });
    };
  }
]).controller('DeclarationStudentSearchController', ['$scope', '$q', 'Classifier', 'QueryUtils',
  function ($scope, $q, Classifier, QueryUtils) {

    $scope.currentNavItem = 'previous';
    var clMapper = Classifier.valuemapper({status: 'OPINGUKAVA_STAATUS'});
    QueryUtils.createQueryForm($scope, '/declarations/previous', {order: 'dId'}, clMapper.objectmapper);

    $q.all(clMapper.promises).then($scope.loadData);
  }
]).controller('DeclarationNewController', ['$scope', '$location', 'FormUtils', 'QueryUtils', 'message',
  function ($scope, $location, FormUtils, QueryUtils, message) {

    function validStudyPeriod() {
      var valid = !!$scope.studyPeriod.id;
      if(!valid) {
        message.error('studyYear.studyPeriod.missingCurrent');
      }
      return valid;
    }
    $scope.studyPeriod = QueryUtils.endpoint('/declarations/currentStudyPeriod').search();
    $scope.studyPeriod.$promise.then(validStudyPeriod);

    $scope.save = function() {
      FormUtils.withValidForm($scope.declarationNewForm, function() {
        if(!validStudyPeriod()) {
          return;
        }
        var Endpoint = QueryUtils.endpoint('/declarations/create/' + $scope.student.id);
        new Endpoint().$save().then(function(response){
          message.info('main.messages.create.success');
          $location.url("/declarations/" + response.id + "/edit?_noback");
        }).catch(angular.noop);
      });
    };
  }
]);
