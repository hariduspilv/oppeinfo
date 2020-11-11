'use strict';

function calculateSubjectCredits(subjects) {
  if (!subjects) {
    return 0;
  }
  return subjects.reduce(function (acc, subject) {
    return acc + subject.subject.credits;
  }, 0);
}

angular.module('hitsaOis').controller('DeclarationEditController',
['$q', '$location', '$scope', '$route', 'GRADING_SCHEMA_TYPE', 'ArrayUtils', 'DataUtils', 'FormUtils', 'GradingSchema', 'QueryUtils', 'dialogService', 'message',
function ($q, $location, $scope, $route, GRADING_SCHEMA_TYPE, ArrayUtils, DataUtils, FormUtils, GradingSchema, QueryUtils, dialogService, message) {

  var SubjectEndpoint = QueryUtils.endpoint('/declarations/subject');
  var ConfirmEndPoint = QueryUtils.endpoint('/declarations/confirm');

  var gradeMapper;
  var gradingSchema = new GradingSchema(GRADING_SCHEMA_TYPE.HIGHER);
  $q.all(gradingSchema.promises).then(function () {
    $scope.existsSchoolGradingSchema = gradingSchema.existsSchoolGradingSchema();
    gradeMapper = gradingSchema.gradeMapper(gradingSchema.gradeSelection(), ['grade']);
  });

  var id = $route.current.params.id;
  $scope.formState = {};
  $scope.auth = $route.current.locals.auth;
  $scope.addCurriculumSubjectForm = false;
  $scope.addExtraCurriculumSubjectFrom = false;
  $scope.calculateAll = calculateSubjectCredits;

  if($scope.auth.isStudent()) {
    QueryUtils.endpoint('/declarations/hasPrevious').search().$promise.then(function(response){
      $scope.currentNavItem = 'current';
      $scope.formState.showNavBar = response.hasPrevious;
    });
  }

  if(id) {
    $scope.declaration = QueryUtils.endpoint('/declarations').get({id: id}, function(response){
      if($scope.auth.isStudent() && response.isPrevious) {
        message.error('main.messages.error.nopermission');
        $scope.back("#/");
      }
      afterLoad(response);
      $scope.hasCurriculum = response.student.curriculumVersion !== null;
      // We need declaration period to show
      QueryUtils.endpoint('/declarations/declarationPeriod').get({id: response.id}).$promise.then(function(response){
        $scope.formState.isDeclarationPeriod = response.isDeclarationPeriod;
        if (response.declarationPeriodStart) {
          $scope.formState.declarationPeriodStart = new Date(response.declarationPeriodStart);
        }
        if (response.declarationPeriodEnd) {
          $scope.formState.declarationPeriodEnd = new Date(response.declarationPeriodEnd);
        }
      });
    });
  } else {
    $scope.declaration = QueryUtils.endpoint('/declarations/current').get({}, function(response){
      if ($scope.auth.isStudent() && !response.canBeChanged) {
        message.error('main.messages.error.nopermission');
        $scope.back("#/");
      }
      afterLoad(response);
      // We need declaration period to show
      QueryUtils.endpoint('/declarations/isDeclarationPeriod').search().$promise.then(function(response){
        $scope.formState.isDeclarationPeriod = response.isDeclarationPeriod;
        if (response.declarationPeriodStart) {
          $scope.formState.declarationPeriodStart = new Date(response.declarationPeriodStart);
        }
        if (response.declarationPeriodEnd) {
          $scope.formState.declarationPeriodEnd = new Date(response.declarationPeriodEnd);
        }
      });
    });
  }

  function afterLoad(declaration) {
    $scope.isGuestStudent = declaration.student.type === 'OPPUR_K';
    (declaration.subjects || []).forEach(function (subject) {
      subject.mandatoryPrerequisiteSubjects.forEach(function (prerequisiteSubject) {
        gradeMapper.objectmapper(prerequisiteSubject);
      });
      subject.recommendedPrerequisiteSubjects.forEach(function (prerequisiteSubject) {
        gradeMapper.objectmapper(prerequisiteSubject);
      });
    });
  }

  $scope.confirm = function() {
    dialogService.confirmDialog({prompt: 'declaration.prompt.confirm'}, function() {
      new ConfirmEndPoint($scope.declaration).$update().then(function(response){
        message.info('declaration.message.confirmed');
        $location.path("/declarations/" + response.id + "/view?_noback");
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
      if ($scope.addCurriculumSubjectForm) {
        $scope.loadData();
      } else {
        getExtraCurriculumSubjectsOptions();
      }
    });
  };

  QueryUtils.createQueryForm($scope, '/declarations/subjects/' + id);
  $scope.afterLoadData = function (resultData) {
    $scope.tabledata.content = resultData.content;
    $scope.tabledata.totalElements = resultData.totalElements;
  };
  $scope.criteria = { order: 'cvhm.' + $scope.currentLanguageNameField() +
                              ', s.' + $scope.currentLanguageNameField() +
                              ', s.code, teachers.teacher', size: 10, page: 1};

  $scope.openAddCurriculumSubject = function () {
    $scope.addCurriculumSubject = true;
    $scope.addExtraCurriculumSubject = false;
    $scope.loadData();
  };

  $scope.addCurriculumSubject = function(subject) {
    if (subject.subgroups && subject.subgroups.length > 0) {
      if (!subject.subgroup || !subject.subgroup.id) {
        message.error('declaration.error.noSubgroup');
        return;
      }
    }

    var newSubject = new SubjectEndpoint({
      subjectStudyPeriod: subject.subjectStudyPeriod,
      declaration: $scope.declaration.id,
      curriculumVersionHigherModule: subject.module.id,
      isOptional: subject.isOptional,
      subgroup: subject.subgroup
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
    $scope.subjects = QueryUtils.endpoint('/declarations/subjects/extracurriculum/' + id).query({}, function(result) {
      result.forEach(function (subject) {
        subject.display = $scope.currentLanguageNameField(subject.subject) + " (" + subject.subject.code + ") - " + subject.teachers.join(', ');
      });
    });
  }

  $scope.$watch('subject', function() {
    if ($scope.subject !== null && $scope.subject !== undefined) {
      $scope.subject.subgroups = QueryUtils.endpoint('/declarations/subjects/extracurriculum/subgroups/' + $scope.subject.subjectStudyPeriod).query();
    }
  });

  $scope.searchExtraCurriculumSubjects = function (text) {
    return DataUtils.filterArrayByText($scope.subjects, text, function (obj, regex) {
        return regex.test(obj.display.toUpperCase());
    });
  };

  $scope.openAddExtraCurriculumSubject = function() {
    $scope.addCurriculumSubjectForm = false;
    $scope.addExtraCurriculumSubjectForm = true;
    getExtraCurriculumSubjectsOptions();
  };

  $scope.addExtraCurriculumSubject = function() {
    FormUtils.withValidForm($scope.extraCurriculumForm, function () {
      if(!$scope.subject) {
        return;
      }
      var newSubject = new SubjectEndpoint({
        subjectStudyPeriod: $scope.subject.subjectStudyPeriod,
        declaration: $scope.declaration.id,
        curriculumVersionHigherModule: $scope.subject.module !== null ? $scope.subject.module.id : null,
        isOptional: true,
        subgroup: $scope.subject.subgroup
      });
      newSubject.$save().then(function(response){
        message.info('declaration.message.subjectAdded');
        response.newlyAdded = true;
        $scope.declaration.subjects.push(response);
        $scope.clearCriteria();
        getExtraCurriculumSubjectsOptions();
      });
    });
  };

  $scope.hasPrerequisites = function(subject) {
    return !ArrayUtils.isEmpty(subject.mandatoryPrerequisiteSubjects) || !ArrayUtils.isEmpty(subject.recommendedPrerequisiteSubjects);
  };

  $scope.seePrerequisites = function(subject) {
      var DialogController = function (scope) {
        scope.subject = subject;
        scope.existsSchoolGradingSchema = $scope.existsSchoolGradingSchema;
        scope.letterGrades = $scope.auth.school.letterGrades;
      };

      dialogService.showDialog('declaration/declaration.subject.prerequisites.html', DialogController,
        function () {
      });
  };

}]).controller('DeclarationViewController', ['$q', '$location', '$route', '$scope', 'GRADING_SCHEMA_TYPE', 'ArrayUtils', 'Classifier', 'GradingSchema', 'QueryUtils', 'dialogService', 'message',
  function ($q, $location, $route, $scope, GRADING_SCHEMA_TYPE, ArrayUtils, Classifier, GradingSchema, QueryUtils, dialogService, message) {

  var id = $route.current.params.id;
  $scope.formState = {};
  $scope.currentDeclarationPage = $location.path() === '/declaration/current/view';
  $scope.inStudentData = $location.path() === '/students/'+id+'/declaration';
  if ($route.current.params._fromStudentData || $location.path().endsWith('fromStudentData')) {
    $scope.fromStudentData = true;
  }
  if ($location.path().startsWith('/student')) {
    $scope.currentNavItem = 'current';
    $scope.studentId = ($route.current.locals.auth.isStudent() || $route.current.locals.auth.isParent() ? $route.current.locals.auth.student : $route.current.params.id);
    QueryUtils.endpoint('/students').get({ id: $scope.studentId, withHTM: true }).$promise.then(function (student) {
      $scope.student = student;
      $scope.formState.showNavBar = true;
    });
  }
  $scope.auth = $route.current.locals.auth;
  var ConfirmEndPoint = QueryUtils.endpoint('/declarations/confirm');

  var gradeMapper;
  var gradingSchema = new GradingSchema(GRADING_SCHEMA_TYPE.HIGHER);
  $q.all(gradingSchema.promises).then(function () {
    $scope.existsSchoolGradingSchema = gradingSchema.existsSchoolGradingSchema();
    gradeMapper = gradingSchema.gradeMapper(gradingSchema.gradeSelection(), ['grade']);
  });

  var currentWrapper = new DeclarationWrapper(undefined, {isNextPeriod: false});
  var nextWrapper = new DeclarationWrapper(undefined, {isNextPeriod: true});
  $scope.declarations = [currentWrapper, nextWrapper];
  $scope.DECLARATION_PERIOD_TYPE = Object.freeze({
    BEFORE: 0,
    CURRENT: 1,
    AFTER: 2
  });
  $scope.calculateAll = calculateSubjectCredits;

  if($scope.auth.isStudent()) {
    QueryUtils.endpoint('/declarations/hasPrevious').search().$promise.then(function(response){
      $scope.formState.showNavBar = response.hasPrevious;
    });
  }

  /**
   * Sets declaration and formState
   *
   * @param {DeclarationWrapper} wrapper
   * @param {boolean} next if true then looks for next period declaration.
   */
  function getDeclarationDataWithoutId(wrapper, next) {
    QueryUtils.endpoint('/declarations/' + (next ? 'next' : 'current')).get({id : $scope.studentId}).$promise.then(function(response){
      if(!response.id) {
        wrapper.formState.noDeclaration = true;
      } else {
        afterLoad(response);
        wrapper.declaration = response;
        wrapper.formState.declaration = true;
      }

      // We need declaration period to show
      QueryUtils.endpoint('/declarations/isDeclarationPeriod?next=' + !!next).search().$promise.then(function(response){
        wrapper.formState.type = response.isDeclarationPeriod ? $scope.DECLARATION_PERIOD_TYPE.CURRENT : undefined;
        var currentTime = new Date();
        if (response.declarationPeriodStart) {
          wrapper.formState.declarationPeriodStart = new Date(response.declarationPeriodStart);
          if (currentTime < wrapper.formState.declarationPeriodStart) {
            wrapper.formState.type = $scope.DECLARATION_PERIOD_TYPE.BEFORE;
          }
        }
        if (response.declarationPeriodEnd) {
          wrapper.formState.declarationPeriodEnd = new Date(response.declarationPeriodEnd);
          if (currentTime > wrapper.formState.declarationPeriodEnd) {
            wrapper.formState.type = $scope.DECLARATION_PERIOD_TYPE.AFTER;
          }
        }

        if (wrapper.formState.type === $scope.DECLARATION_PERIOD_TYPE.CURRENT && !response.id) {
          var endpointUrl = '/declarations/canCreate?next=' + !!next;
          if ($scope.inStudentData) {
            endpointUrl = '/declarations/canCreate/'+ id +'?next=' + !!next;
          }
          QueryUtils.endpoint(endpointUrl).search().$promise.then(function(response){
            wrapper.formState.canCreateDeclaration = response.canCreate;
          });
        }

        if (response.period && !wrapper.declaration.studyPeriod) {
          wrapper.declaration.studyPeriod = response.period;
        }
      });

      $scope.isGuestStudent = response.student !== undefined && response.student.type === 'OPPUR_K';
    });
  }

  if ($scope.studentId) {
    getDeclarationDataWithoutId(currentWrapper);
    getDeclarationDataWithoutId(nextWrapper, true);
  } else if(id) {
    currentWrapper.declaration = QueryUtils.endpoint('/declarations').get({id: id}, function(response){
      afterLoad(response);
      currentWrapper.formState.declaration = true;
      $scope.isGuestStudent = response.student.type === 'OPPUR_K';
      $scope.currentNavItem = response.isPrevious ? 'previous' : 'current';
    });
    nextWrapper.hide = true;
  } else {
    $scope.currentNavItem = 'current';
    getDeclarationDataWithoutId(currentWrapper);
    getDeclarationDataWithoutId(nextWrapper, true);
  }

  function afterLoad(declaration) {
    (declaration.subjects || []).forEach(function (subject) {
      subject.mandatoryPrerequisiteSubjects.forEach(function (prerequisiteSubject) {
        gradeMapper.objectmapper(prerequisiteSubject);
      });
      subject.recommendedPrerequisiteSubjects.forEach(function (prerequisiteSubject) {
        gradeMapper.objectmapper(prerequisiteSubject);
      });
    });
  }

  $scope.addDeclaration = function(next) {
    var endpoint = '/declarations/create?next=' + !!next;
    if ($scope.inStudentData) {
      endpoint = '/declarations/create/' + id + '?next=' + !!next;
    }
    var Endpoint = QueryUtils.endpoint(endpoint);
    new Endpoint().$save().then(function(response){
      $location.url("/declarations/" + response.id + "/edit");
    });
  };

  $scope.confirm = function(next) {
    var wrapper = next ? nextWrapper : currentWrapper;
    dialogService.confirmDialog({prompt: 'declaration.prompt.confirm'}, function() {
      new ConfirmEndPoint(wrapper.declaration).$update().then(function(response){
        message.info('declaration.message.confirmed');
        wrapper.declaration = response;
      });
    });
  };

  $scope.removeConfirmation = function(next) {
    var wrapper = next ? nextWrapper : currentWrapper;
    dialogService.confirmDialog({prompt: 'declaration.prompt.removeConfirmation'}, function() {
      var EndPoint = QueryUtils.endpoint('/declarations/removeConfirm');
      new EndPoint(wrapper.declaration).$update().then(function(response){
        message.info('declaration.message.confirmationRemoved');
        wrapper.declaration = response;
      });
    });
  };

  $scope.hasPrerequisites = function(subject) {
    return !ArrayUtils.isEmpty(subject.mandatoryPrerequisiteSubjects) || !ArrayUtils.isEmpty(subject.recommendedPrerequisiteSubjects);
  };

  $scope.seePrerequisites = function(subject) {
      var DialogController = function (scope) {
        scope.subject = subject;
        scope.existsSchoolGradingSchema = $scope.existsSchoolGradingSchema;
        scope.letterGrades = $scope.auth.school.letterGrades;
      };

      dialogService.showDialog('declaration/declaration.subject.prerequisites.html', DialogController,
        function () {
      });
  };

  /**
   * Holds declaration and formState for this declaration.
   * Needed for repeating the same form.
   */
  function DeclarationWrapper(declaration, formState) {
    this.declaration = (declaration || {});
    this.formState = (formState || {});
    this.hide = false;
  }

}]).controller('DeclarationSearchController', ['$scope', '$route', '$q', '$timeout', 'Classifier', 'DataUtils', 'QueryUtils', 'message', 'dialogService', 'USER_ROLES', 'AuthService',
  function ($scope, $route, $q, $timeout, Classifier, DataUtils, QueryUtils, message, dialogService, USER_ROLES, AuthService) {
    $scope.auth = $route.current.locals.auth;
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

    $scope.formState = {canCreate: $scope.auth.authorizedRoles.indexOf('ROLE_OIGUS_M_TEEMAOIGUS_OPINGUKAVA') !== -1 };

    $scope.searchCurriculumVersions = function (text) {
      return DataUtils.filterArrayByText($scope.curriculumVersions, text, function (obj, regex) {
        return regex.test($scope.currentLanguageNameField(obj).toUpperCase());
      });
    };

    var loadData = $scope.loadData;
    $scope.loadData = function() {
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

    $scope.addSubjectsToDeclaration = function() {
      var currentStudyPeriod = DataUtils.getCurrentStudyYearOrPeriod($scope.studyPeriods);
      if (!currentStudyPeriod) {
        message.error('studyYear.studyPeriod.missingCurrent');
        return;
      }
      dialogService.confirmDialog({prompt: 'declaration.prompt.addSubjectsToDeclaration'}, function() {
        var EndPoint = QueryUtils.endpoint('/declarations/addSubjectsToDeclaration');
        new EndPoint(currentStudyPeriod).$update().then(function(response) {
          if (response.subjectStudyPeriods !== null && response.subjectStudyPeriods.length > 0) {
            dialogService.showDialog('declaration/declaration.autofill.dialog.html', function (dialogScope) {
              dialogScope.response = response;
            }, null);
          }
          message.info('declaration.message.addedSubjectsToDeclaration', {numberOfNewlyAddedSubjectStudyPeriods: response.changedDeclarations});
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
]).controller('DeclarationStudentSearchController', ['$scope', '$q', 'Classifier', 'QueryUtils', '$location', '$route',
  function ($scope, $q, Classifier, QueryUtils, $location, $route) {
    var clMapper = Classifier.valuemapper({status: 'OPINGUKAVA_STAATUS'});
    if ($location.path().startsWith('/student')) {
      $scope.studentId = ($route.current.locals.auth.isStudent() || $route.current.locals.auth.isParent() ? $route.current.locals.auth.student : $route.current.params.id);
      QueryUtils.endpoint('/students').get({ id: $scope.studentId, withHTM: true }).$promise.then(function (student) {
        $scope.inStudentData = true;
        $scope.student = student;
        $scope.currentNavItem = 'previous';
      });
      QueryUtils.createQueryForm($scope, '/declarations/previous/' + $scope.studentId, {order: 'dId'}, clMapper.objectmapper);
    } else {
      $scope.currentNavItem = 'previous';
      QueryUtils.createQueryForm($scope, '/declarations/previous', {order: 'dId'}, clMapper.objectmapper);
    }
    $q.all(clMapper.promises).then($scope.loadData);
  }
]).controller('DeclarationNewController', ['$scope', '$location', 'FormUtils', 'QueryUtils', 'message',
  function ($scope, $location, FormUtils, QueryUtils, message) {

    $scope.periods = [];

    function validStudyPeriod(period) {
      var valid = !!period.id;
      if(!valid) {
        message.error('studyYear.studyPeriod.missingCurrent');
      }
      return valid;
    }

    $scope.studyPeriod = QueryUtils.endpoint('/declarations/currentStudyPeriod').search();
    $scope.studyPeriod.$promise.then(function () {
      if (!validStudyPeriod($scope.studyPeriod)) {
        return;
      }
      $scope.periods.unshift($scope.studyPeriod);
      $scope.controller.period = $scope.studyPeriod.id;
    });

    $scope.nextPeriod = QueryUtils.endpoint('/declarations/nextStudyPeriod').search();
    $scope.nextPeriod.$promise.then(function () {
      if (!$scope.nextPeriod.id) {
        return;
      }
      $scope.periods.push($scope.nextPeriod);
    });

    $scope.save = function() {
      FormUtils.withValidForm($scope.declarationNewForm, function() {
         // It is fine to check only the current period
         // If there is no current period then there should not be next period.
        if(!validStudyPeriod($scope.studyPeriod)) {
          return;
        }
        var isNextPeriod = !!$scope.nextPeriod.id && $scope.nextPeriod.id === $scope.controller.period;
        var Endpoint = QueryUtils.endpoint('/declarations/create/' + $scope.controller.student.id + "?next=" + isNextPeriod);
        new Endpoint().$save().then(function(response) {
          message.info('main.messages.create.success');
          $location.url("/declarations/" + response.id + "/edit?_noback");
        }).catch(angular.noop);
      });
    };
  }
]);
