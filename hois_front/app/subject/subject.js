'use strict';

angular.module('hitsaOis')
  .controller('SubjectEditController',
    function ($scope, $rootScope, $route, $resource, $location, $translate, $q, config, dialogService, message, QueryUtils) {
      var id = $route.current.params.id;

      var backUrl = $route.current.params.backUrl;
      $scope.formState = {
        backUrl: backUrl ? '#/' + backUrl : '#/subject'
      };

      // $resource.search() caused an error
      $resource(config.apiUrl + '/subject/initEditFormData').get().$promise.then(function (result) {
        angular.extend($scope, result.toJSON());
      });

      var Endpoint = QueryUtils.endpoint('/subject');
      if (id) {
        $scope.subject = Endpoint.get({id: id});
      } else {
        $scope.subject = new Endpoint();
        $scope.subject.status = 'AINESTAATUS_S';
      }

      var checkConnections = function (ids, array) {
        var inValid = false;
        for (var i = 0; i < array.length; i++) {
          if (ids.indexOf(array[i].id) > -1) {
            $translate('subject.alreadyConnected', {subject: $rootScope.currentLanguageNameField(array[i])})
              .then(function (translated) {
                message.error(translated);
              });
            inValid = true;
          }
          ids.push(array[i].id);
        }
        return inValid;
      };

      $scope.update = function () {
        $scope.subjectForm.$setSubmitted();
        var ids = [];
        var inValid = checkConnections(ids, $scope.subject.mandatoryPrerequisiteSubjects) ||
            checkConnections(ids, $scope.subject.recommendedPrerequisiteSubjects) ||
            checkConnections(ids, $scope.subject.substituteSubjects)
        ;
        if ($scope.subjectForm.$valid && !inValid) {
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
    function ($scope, $route, $q, QueryUtils) {
      var id = $route.current.params.id;

      var backUrl = $route.current.params.backUrl;
      $scope.formState = {
        backUrl: backUrl ? '#/' + backUrl : '#/subject'
      };

      var Endpoint = QueryUtils.endpoint('/subject');

      $scope.subject = Endpoint.get({id: id});
  })
  .controller('SubjectListController', ['$q', '$scope', 'Classifier', 'QueryUtils',
    function ($q, $scope, Classifier, QueryUtils) {
      var clMapper = Classifier.valuemapper({status: 'AINESTAATUS', assessment: 'HINDAMISVIIS', languages: 'OPPEKEEL'});
      QueryUtils.createQueryForm($scope, '/subject', {order: $scope.currentLanguage() === 'en' ? 'nameEn' : 'nameEt'}, clMapper.objectmapper);

      // initialize selections on form
      QueryUtils.endpoint('/subject/initSearchFormData').search(function (result) {
        angular.extend($scope, result.toJSON());
      });

      $q.all(clMapper.promises).then($scope.loadData);

      $scope.subjectLanguages = function (row) {
        return row.map(function (it) {
          return $scope.currentLanguageNameField(it);
        }).join(', ');
      };
  }]);
