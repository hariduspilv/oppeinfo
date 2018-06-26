(function () {
  'use strict';

  function canEdit(auth) {
    return auth.isAdmin() || auth.isTeacher();
  }

  angular.module('hitsaOis').controller('StudyMaterialSubjectStudyPeriodController', ['$scope', '$route', 'QueryUtils', 'ArrayUtils', 'oisFileService',
    'dialogService', 'message', 'Classifier', '$q', function ($scope, $route, QueryUtils, ArrayUtils, oisFileService,
      dialogService, message, Classifier, $q) {
      $scope.auth = $route.current.locals.auth;
      $scope.canEdit = canEdit($scope.auth);
      $scope.subjectStudyPeriod = $route.current.locals.subjectStudyPeriod;
      $scope.teachers = $scope.subjectStudyPeriod.teachers.map($scope.currentLanguageNameField).join(', ');
      $scope.getFileUrl = oisFileService.getUrl;

      var ConnectEndpoint = QueryUtils.endpoint('/studyMaterial/connect');

      var clMapper = Classifier.valuemapper({
        typeCode: 'OPPEMATERJAL'
      });

      function loadMaterials() {
        $scope.materials = QueryUtils.endpoint('/studyMaterial/subjectStudyPeriod/' + $scope.subjectStudyPeriod.id + '/materials').query();
        $scope.materials.$promise.then(function (materials) {
          $q.all(clMapper.promises).then(function () {
            clMapper.objectmapper(materials);
          });
        });
      }

      loadMaterials();

      $scope.deleteConnection = function (materialConnect, connections) {
        dialogService.confirmDialog({
          prompt: (connections > 1) ? 'studyMaterial.deleteconfirm' : 'studyMaterial.deletelastconfirm'
        }, function () {
          var connection = new ConnectEndpoint(materialConnect);
          connection.$delete().then(function () {
            message.info('main.messages.delete.success');
            loadMaterials();
          }).catch(angular.noop);
        });
      };

      $scope.addExisting = function () {
        if (!$scope.existingMaterial) {
          message.error('main.messages.form-has-errors');
          return;
        }
        var connection = new ConnectEndpoint();
        connection.studyMaterial = $scope.existingMaterial.id;
        connection.subjectStudyPeriod = $scope.subjectStudyPeriod.id;
        connection.$save().then(function () {
          $scope.existingMaterial = undefined;
          loadMaterials();
        });
      };
    }]).controller('StudyMaterialJournalController', ['$scope', '$route', 'QueryUtils', 'ArrayUtils', 'oisFileService',
      'dialogService', 'message', 'Classifier', '$q', function ($scope, $route, QueryUtils, ArrayUtils, oisFileService,
        dialogService, message, Classifier, $q) {

        $scope.auth = $route.current.locals.auth;
        $scope.journal = $route.current.locals.journal;
        $scope.teachers = $scope.journal.teachers.map($scope.currentLanguageNameField).join(', ');
        $scope.getFileUrl = oisFileService.getUrl;

        var ConnectEndpoint = QueryUtils.endpoint('/studyMaterial/connect');

        var clMapper = Classifier.valuemapper({
          typeCode: 'OPPEMATERJAL'
        });

        function loadMaterials() {
          $scope.materials = QueryUtils.endpoint('/studyMaterial/journal/' + $scope.journal.id + '/materials').query();
          $scope.materials.$promise.then(function (materials) {
            $q.all(clMapper.promises).then(function () {
              clMapper.objectmapper(materials);
            });
          });
        }

        loadMaterials();

        $scope.deleteConnection = function (materialConnect, connections) {
          dialogService.confirmDialog({
            prompt: (connections > 1) ? 'studyMaterial.deleteconfirm' : 'studyMaterial.deletelastconfirm'
          }, function () {
            var connection = new ConnectEndpoint(materialConnect);
            connection.$delete().then(function () {
              message.info('main.messages.delete.success');
              loadMaterials();
            }).catch(angular.noop);
          });
        };

        $scope.addExisting = function () {
          if (!$scope.existingMaterial) {
            message.error('main.messages.form-has-errors');
            return;
          }
          var connection = new ConnectEndpoint();
          connection.studyMaterial = $scope.existingMaterial.id;
          connection.journal = $scope.journal.id;
          connection.$save().then(function () {
            $scope.existingMaterial = undefined;
            loadMaterials();
          });
        };

      }]).controller('StudyMaterialSubjectStudyPeriodPublicController', ['$scope', '$route', 'QueryUtils', 'oisFileService',
    'dialogService', 'message', 'Classifier', '$q', function ($scope, $route, QueryUtils, oisFileService,
      dialogService, message, Classifier, $q) {
      $scope.subjectStudyPeriod = $route.current.locals.subjectStudyPeriod;
      $scope.teachers = $scope.subjectStudyPeriod.teachers.map($scope.currentLanguageNameField).join(', ');
      $scope.getFileUrl = oisFileService.getUrl;

      var clMapper = Classifier.valuemapper({
        typeCode: 'OPPEMATERJAL'
      });

      function loadMaterials() {
        $scope.materials = QueryUtils.endpoint('/public/studyMaterial/subjectStudyPeriod/' + $scope.subjectStudyPeriod.id + '/materials').query();
        $scope.materials.$promise.then(function (materials) {
          $q.all(clMapper.promises).then(function () {
            clMapper.objectmapper(materials);
          });
        });
      }

      loadMaterials();

    }]).controller('StudyMaterialJournalPublicController', ['$scope', '$route', 'QueryUtils', 'oisFileService',
    'dialogService', 'message', 'Classifier', '$q', function ($scope, $route, QueryUtils, oisFileService,
      dialogService, message, Classifier, $q) {
      $scope.journal = $route.current.locals.journal;
      $scope.teachers = $scope.journal.teachers.map($scope.currentLanguageNameField).join(', ');
      $scope.getFileUrl = oisFileService.getUrl;

      var clMapper = Classifier.valuemapper({
        typeCode: 'OPPEMATERJAL'
      });

      function loadMaterials() {
        $scope.materials = QueryUtils.endpoint('/public/studyMaterial/journal/' + $scope.journal.id + '/materials').query();
        $scope.materials.$promise.then(function (materials) {
          $q.all(clMapper.promises).then(function () {
            clMapper.objectmapper(materials);
          });
        });
      }

      loadMaterials();

    }]);
}());