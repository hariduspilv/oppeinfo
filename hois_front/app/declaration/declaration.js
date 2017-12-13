'use strict';

angular.module('hitsaOis').controller('DeclarationEditController', ['$scope', 'dialogService', 'QueryUtils', 'message', 'ArrayUtils', '$route', '$location', function ($scope, dialogService, QueryUtils, message, ArrayUtils, $route, $location) {

  var SubjectEndpoint = QueryUtils.endpoint('/declarations/subject');
  var ConfirmEndPoint = QueryUtils.endpoint('/declarations/confirm');

  var id = $route.current.params.id;
  $scope.formState = {};
  $scope.auth = $route.current.locals.auth;

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
    });
  };

  $scope.openAddCurriculumSubject = function () {
    var DialogController = function (scope) {

      scope.subjects = QueryUtils.endpoint('/declarations/subjects/' + $scope.declaration.id).query();
      scope.modules = QueryUtils.endpoint('/declarations/modules/' + $scope.declaration.id).query();

      scope.filterSubjectsByModule = function(moduleId) {
        return function(subject) {
          return moduleId === subject.module.id;
        };
      };

      scope.filterNotAdded = function(subject) {
        return !angular.isDefined($scope.declaration.subjects.find(function(el){
          return el.subject.id === subject.subject.id;
        }));
      };

      scope.addSubject = function(subject) {
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
        });
      };
    };

    dialogService.showDialog('declaration/curriculum.subject.add.dialog.html', DialogController,
      function () {
      });
  };

  $scope.openAddExtraCurriculumSubject = function() {
      var DialogController = function (scope) {

        function getSubjects() {
          scope.subjects = QueryUtils.endpoint('/declarations/subjects/extracurriculum/' + $scope.declaration.id).query();
        }
        getSubjects();

        $scope.getTeachers = function(subject) {
          return subject.teachers.join(', ');
        };

        scope.addSubject = function() {
          scope.dialogForm.$setSubmitted();
          if(!scope.subject) {
            return;
          }
          var newSubject = new SubjectEndpoint({
            subjectStudyPeriod: scope.subject.subjectStudyPeriod,
            declaration: $scope.declaration.id,
            curriculumVersionHigherModule: scope.subject.module.id,
            isOptional: true
          });
          newSubject.$save().then(function(response){
            message.info('declaration.message.subjectAdded');
            response.newlyAdded = true;
            $scope.declaration.subjects.push(response);
            getSubjects();
            scope.subject = undefined;
          });
        };
      };

      dialogService.showDialog('declaration/extracurriculum.subject.add.dialog.html', DialogController,
        function () {
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

        QueryUtils.endpoint('/declarations/canCreate').search().$promise.then(function(response){
          $scope.formState.canCreateDeclaration = response.canCreate;
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

}]).controller('DeclarationSearchController', ['$scope', '$sessionStorage', 'ArrayUtils', 'Classifier', 'DataUtils', 'QueryUtils', '$q', 'message', 'dialogService',
  function ($scope, $sessionStorage, ArrayUtils, Classifier, DataUtils, QueryUtils, $q, message, dialogService) {

    var clMapper = Classifier.valuemapper({status: 'OPINGUKAVA_STAATUS'});
    QueryUtils.createQueryForm($scope, '/declarations', {order: 'dId'}, clMapper.objectmapper);
    $q.all(clMapper.promises);
    DataUtils.convertStringToDates($scope.criteria, ['inserted', 'confirmDate']);

    function setCurrentStudyPeriod() {
        if($scope.criteria && !$scope.criteria.studyPeriod) {
            $scope.criteria.studyPeriod = DataUtils.getCurrentStudyYearOrPeriod($scope.studyPeriods).id;
        }
    }

    var promises = clMapper.promises;
    $scope.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriods').query();
    promises.push($scope.studyPeriods.$promise);

    $q.all(promises).then(function() {
      setCurrentStudyPeriod();
      $scope.loadData();
    });

    $scope.$watch('criteria.studyPeriod', function() {
      if(!ArrayUtils.isEmpty($scope.studyPeriods) && !$scope.criteria.studyPeriod) {
        setCurrentStudyPeriod();
      }
    });

    $scope.curriculumVersions = QueryUtils.endpoint('/autocomplete/curriculumversions').query({higher: true, valid: true});
    $scope.studentGroups = QueryUtils.endpoint('/autocomplete/studentgroups').query();

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
]).controller('DeclarationStudentSearchController', ['$scope', '$sessionStorage', 'Classifier', 'QueryUtils', '$q',
  function ($scope, $sessionStorage, Classifier, QueryUtils, $q) {

    $scope.currentNavItem = 'previous';
    var clMapper = Classifier.valuemapper({status: 'OPINGUKAVA_STAATUS'});
    QueryUtils.createQueryForm($scope, '/declarations/previous', {order: 'dId'}, clMapper.objectmapper);

    $q.all(clMapper.promises).then($scope.loadData);
  }
]).controller('DeclarationNewController', ['$scope', 'QueryUtils', 'message', '$location',
  function ($scope, QueryUtils, message, $location) {

    $scope.studyPeriod = QueryUtils.endpoint('/declarations/currentStudyPeriod').search();

    $scope.save = function() {
      $scope.declarationNewForm.$setSubmitted();
      if(!$scope.declarationNewForm.$valid) {
        return;
      }
      var Endpoint = QueryUtils.endpoint('/declarations/create/' + $scope.student.id);
      new Endpoint().$save().then(function(response){
        message.info('main.messages.create.success');
        $location.url("/declarations/" + response.id + "/edit?_noback");
      });
    };
  }
]);
