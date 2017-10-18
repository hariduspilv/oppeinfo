'use strict';

angular.module('hitsaOis')
  .controller('SubjectEditController',
    function ($scope, $rootScope, $route, $location, $translate, $q, dialogService, message, QueryUtils) {
      var id = $route.current.params.id;

      var backUrl = $route.current.params.backUrl;
      $scope.formState = {
        backUrl: backUrl ? '#/' + backUrl : '#/subject'
      };

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
        $scope.subject = new Endpoint({status: 'AINESTAATUS_S'});
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

      $scope.update = function () {
        if (formIsValid()) {
          if ($scope.subject.id) {
            $scope.subject.$update().then(message.updateSuccess);
          } else {
            $scope.subject.$save().then(function (response) {
              $location.path('/subject/' + response.id + '/edit');
              message.info('main.messages.create.success');
            });
          }
        }
      };

      function changeStatus(StatusChangeEndpoint, messages) {
        if (formIsValid()) {
          dialogService.confirmDialog({prompt: messages.prompt}, function () {
            new StatusChangeEndpoint($scope.subject).$update().then(function(response){
              $scope.subject = response;
              message.info(messages.success);
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
            $location.path('/subject');
          });
        });
      };
    })
  .controller('SubjectViewController',
    function ($scope, $route, QueryUtils) {
      var id = $route.current.params.id;
      var backUrl = $route.current.params.backUrl;

      $scope.auth = $route.current.locals.auth;
      $scope.formState = {backUrl: backUrl ? '#/' + backUrl : '#/subject'};
      $scope.subject = QueryUtils.endpoint('/subject').get({id: id});
  })
  .controller('SubjectListController', ['$q', '$scope', 'Classifier', 'QueryUtils','$route',
    function ($q, $scope, Classifier, QueryUtils, $route) {
      $scope.auth = $route.current.locals.auth;
      var clMapper = Classifier.valuemapper({status: 'AINESTAATUS', assessment: 'HINDAMISVIIS', languages: 'OPPEKEEL'});
      QueryUtils.createQueryForm($scope, '/subject', {order: $scope.currentLanguage() === 'en' ? 'nameEn' : 'nameEt'}, clMapper.objectmapper);

      // initialize selections on form
      QueryUtils.endpoint('/subject/initSearchFormData').search(function (result) {
        angular.extend($scope, result.toJSON());
      });

      $q.all(clMapper.promises).then($scope.loadData);

      $scope.subjectLanguages = function (row) {
        return row.map($scope.currentLanguageNameField).join(', ');
      };
  }]);
