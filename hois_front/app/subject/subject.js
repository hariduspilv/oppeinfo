'use strict';

angular.module('hitsaOis')
  .controller('SubjectEditController',
    function ($scope, $rootScope, $route, $location, $translate, $q, dialogService, message, QueryUtils) {
      var id = $route.current.params.id;

      var backUrl = $route.current.params.backUrl;
      $scope.formState = {
        backUrl: backUrl ? '#/' + backUrl : '#/subject'
      };

      $scope.SUBJECT_TYPE = Object.freeze({
        MANDATORY_PREPEQUISITE: 0,
        RECOMMENDED_PREREQUISITE: 1,
        SUBSTITUTE: 2
      });

      $scope.subjectCodeUniqueQuery = {
        id: id,
        url: '/subject/unique/code'
      };

      QueryUtils.endpoint('/subject/initEditFormData').search(function (result) {
        angular.extend($scope, result.toJSON());
      });

      var Endpoint = QueryUtils.endpoint('/subject');
      var ConfirmEndpoint = QueryUtils.endpoint('/subject/saveAndConfirm');
      var UnconfirmEndpoint = QueryUtils.endpoint('/subject/saveAndUnconfirm');

      if (id) {
        $scope.subject = Endpoint.get({id: id});
      } else {
        $scope.subject = new Endpoint({
          status: 'AINESTAATUS_S',
          mandatoryPrerequisiteSubjects: [],
          recommendedPrerequisiteSubjects: [],
          substituteSubjects: []
        });
      }

      function afterTranslate(translated) {
        message.error(translated);
      }

      var checkConnections = function (ids, array) {
        var inValid = false;
        for (var i = 0; i < array.length; i++) {
          if (ids.indexOf(array[i].id) > -1) {
            $translate('subject.alreadyConnected', {subject: $rootScope.currentLanguageNameField(array[i])})
              .then(afterTranslate);
            inValid = true;
          }
          ids.push(array[i].id);
        }
        return inValid;
      };

      function formIsValid() {
        $scope.subjectForm.$setSubmitted();
        var ids = [];
        var inValid = checkConnections(ids, $scope.subject.mandatoryPrerequisiteSubjects) ||
            checkConnections(ids, $scope.subject.recommendedPrerequisiteSubjects) ||
            checkConnections(ids, $scope.subject.substituteSubjects)
        ;
        if($scope.subjectForm.$valid && !inValid) {
          return true;
        }
        message.error("main.messages.form-has-errors");
        return false;
      }

      function indexOfSubject(array, subject) {
        for (var idx = 0; idx < array.length; idx++) {
          if (angular.isObject(array[idx]) && array[idx].id === subject.id) {
            return idx;
          }
        }
        return -1;
      }

      function addSubject(array, subject) {
        if (!subject) {
          return;
        }
        if (!angular.isArray(array)) {
          array = [];
        }
        if (indexOfSubject(array, subject) === -1) {
          array.push(subject);
        }
      }

      function removeSubject(array, subject) {
        if (!angular.isArray(array)) {
          return;
        }
        var index = indexOfSubject(array, subject);
        if (index !== -1) {
          array.splice(index, 1);
        }
      }

      $scope.addSubject = function (type) {
        switch(type) {
          case $scope.SUBJECT_TYPE.MANDATORY_PREPEQUISITE:
            addSubject($scope.subject.mandatoryPrerequisiteSubjects, $scope.mandatoryPrerequisiteSubject);
            $scope.mandatoryPrerequisiteSubject = undefined;
            break;
          case $scope.SUBJECT_TYPE.RECOMMENDED_PREREQUISITE:
            addSubject($scope.subject.recommendedPrerequisiteSubjects, $scope.recommendedPrerequisiteSubject);
            $scope.recommendedPrerequisiteSubject = undefined;
            break;
          case $scope.SUBJECT_TYPE.SUBSTITUTE:
            addSubject($scope.subject.substituteSubjects, $scope.substituteSubject);
            $scope.substituteSubject = undefined;
            break;
        }
      };

      $scope.deleteSubject = function (type, subject) {
        if (!subject) {
          return;
        }
        switch(type) {
          case $scope.SUBJECT_TYPE.MANDATORY_PREPEQUISITE:
            removeSubject($scope.subject.mandatoryPrerequisiteSubjects, subject);
            break;
          case $scope.SUBJECT_TYPE.RECOMMENDED_PREREQUISITE:
            removeSubject($scope.subject.recommendedPrerequisiteSubjects, subject);
            break;
          case $scope.SUBJECT_TYPE.SUBSTITUTE:
            removeSubject($scope.subject.substituteSubjects, subject);
            break;
        }
      };

      $scope.update = function () {
        if (formIsValid()) {
          if ($scope.subject.id) {
            $scope.subject.$update().then(function(response){
              $scope.subject = new Endpoint(response);
              message.updateSuccess();
              $scope.subjectForm.$setPristine();
            });
          } else {
            $scope.subject.$save().then(function (response) {
              $location.url('/subject/' + response.id + '/edit?_noback');
              message.info('main.messages.create.success');
            });
          }
        }
      };

      function changeStatus(StatusChangeEndpoint, messages) {
        if (formIsValid()) {
          dialogService.confirmDialog({prompt: messages.prompt}, function () {
            new StatusChangeEndpoint($scope.subject).$update().then(function(response){
              $scope.subject = new Endpoint(response);
              message.info(messages.success);
              $scope.subjectForm.$setPristine();
            });
          });
        }
      }

      $scope.saveAndConfirm = function () {
        var messages = {
          prompt: 'subject.prompt.saveAndConfirm',
          success: 'subject.message.savedAndConfirmed'
        };
        changeStatus(ConfirmEndpoint, messages);
      };

      $scope.saveAndUnconfirm = function () {
        var messages = {
          prompt: 'subject.prompt.saveAndUnconfirm',
          success: 'subject.message.savedAndUnconfirmed'
        };
        changeStatus(UnconfirmEndpoint, messages);
      };

      $scope.delete = function () {
        dialogService.confirmDialog({prompt: 'subject.deleteconfirm'}, function () {
          $scope.subject.$delete().then(function () {
            message.info('main.messages.delete.success');
            $location.url('/subject?_noback');
          });
        });
      };
    })
  .controller('SubjectViewController', ['$scope', '$route', 'config', 'QueryUtils',
    function ($scope, $route, config, QueryUtils) {
      $scope.isPublic = $route.current.locals.params && $route.current.locals.params.isPublic;
      var id = $route.current.params.id;
      var backUrl = $route.current.params.backUrl;
      $scope.auth = $route.current.locals.auth;

      $scope.formState = {backUrl: backUrl ? '#/' + backUrl : ($scope.isPublic ? '#/subject/public' : '#/subject')};
      $scope.subject = QueryUtils.endpoint($scope.isPublic ? '/public/subject/view' : '/subject').get({id: id});
      $scope.publicUrl = config.apiUrl + '/public/subject/' + id + '?format=json';
  }])
  .controller('SubjectListController', ['$q', '$scope', '$route', 'Classifier', 'QueryUtils',
    function ($q, $scope, $route, Classifier, QueryUtils) {
      $scope.isPublic = $route.current.locals.params && $route.current.locals.params.isPublic;

      var clMapper = Classifier.valuemapper({status: 'AINESTAATUS', assessment: 'HINDAMISVIIS', languages: 'OPPEKEEL'});
      QueryUtils.createQueryForm($scope, $scope.isPublic ? '/public/subjectsearch' : '/subject', 
        {order: $scope.currentLanguage() === 'en' ? 'nameEn' : 'nameEt'}, clMapper.objectmapper);
      
      $q.all(clMapper.promises).then($scope.loadData);
      
      $scope.subjectLanguages = function (row) {
        return row.map($scope.currentLanguageNameField).join(', ');
      };
      
      $scope.formState = {};

      if ($scope.isPublic) {
        $scope.$watch('criteria.schoolId', function(newSchoolId) {
          if (newSchoolId) {
            $scope.curricula = QueryUtils.endpoint('/public/curriculumversions').query({schoolId: newSchoolId});
            $scope.departments = QueryUtils.endpoint('/public/schooldepartments').query({schoolId: newSchoolId});
          } else {
            $scope.curricula = [];
            $scope.departments = [];
          }
        });
      } else {
        // initialize selections on form
        QueryUtils.endpoint('/subject/initSearchFormData').search(function (result) {
          angular.extend($scope, result.toJSON());
        });
        QueryUtils.endpoint('/subject/getPermissions').search(function (result) {
          $scope.formState.canCreate = result.canCreate;
          $scope.formState.canViewAll = result.canViewAll;
        });
      }
  }]);
