(function () {
  'use strict';

  angular.module('hitsaOis').controller('StudyMaterialSubjectStudyPeriodController', ['$rootScope', '$route', '$scope', '$q', 'Classifier', 'QueryUtils', 'dialogService', 'message', 'oisFileService',
    function ($rootScope, $route, $scope, $q, Classifier, QueryUtils, dialogService, message, oisFileService) {
      $scope.auth = $route.current.locals.auth;
      $scope.canEdit = ($scope.auth !== undefined && $scope.auth !== null) && ($scope.auth.isAdmin() || $scope.auth.isTeacher());
      $scope.subjectStudyPeriod = $route.current.locals.subjectStudyPeriod;
      $scope.teachers = $scope.subjectStudyPeriod.teachers.map($scope.currentLanguageNameField).join(', ');
      $scope.studentGroups = $scope.subjectStudyPeriod.studentGroups.join(', ');
      $scope.getFileUrl = oisFileService.getUrl;

      if (!$route.current.locals.isView && !$scope.subjectStudyPeriod.canConnectStudyMaterials) {
        message.error('main.messages.error.nopermission');
        $rootScope.back('#/studyMaterial/higher/' + $scope.subjectStudyPeriod.id + '/view');
      }

      var ConnectEndpoint = QueryUtils.endpoint('/studyMaterial/subjectStudyPeriod/' + $scope.subjectStudyPeriod.id + '/connect');
      var clMapper = Classifier.valuemapper({typeCode: 'OPPEMATERJAL'});

      function loadMaterials() {
        var materialsEndpoint = ($scope.auth === null || angular.isUndefined($scope.auth) ? '/public' : '') +
          '/studyMaterial/subjectStudyPeriod/' + $scope.subjectStudyPeriod.id + '/materials';
        $scope.materials = QueryUtils.endpoint(materialsEndpoint).query();
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

      $scope.hAutocomplete = [];
      $scope.addExisting = function () {
        if (!$scope.existingMaterial) {
          message.error('main.messages.form-has-errors');
          return;
        }
        var connection = new ConnectEndpoint();
        connection.$save({studyMaterial: $scope.existingMaterial.id}).then(function () {
          $scope.hAutocomplete.forEach(function (r) { r.clear(); });
          loadMaterials();
        });
      };

      $scope.getDefaultBack = function () {
        switch ($route.current.params.backType) {
          case 'declaration':
            return $scope.auth.isAdmin() ? '/#/declarations' : '/#/declaration/current/view';
          case 'timetable':
            return $scope.auth === null || angular.isUndefined($scope.auth) || $scope.auth.school === null ? '/#/timetables' : '/#/timetable/generalTimetable/group';
          default:
            return '/#/studyMaterial/higher';
        }
      };

    }]).controller('StudyMaterialJournalController', ['$rootScope', '$route', '$scope', '$q', 'Classifier', 'QueryUtils', 'dialogService', 'message', 'oisFileService',
      function ($rootScope, $route, $scope, $q, Classifier, QueryUtils, dialogService, message, oisFileService) {
        $scope.auth = $route.current.locals.auth;
        $scope.journal = $route.current.locals.journal;
        $scope.teachers = $scope.journal.teachers.map($scope.currentLanguageNameField).join(', ');
        $scope.studentGroups = $scope.journal.studentGroups.join(', ');
        $scope.getFileUrl = oisFileService.getUrl;

        if (!$route.current.locals.isView && !$scope.journal.canConnectStudyMaterials) {
          message.error('main.messages.error.nopermission');
          $rootScope.back('#/studyMaterial/vocational/' + $scope.journal.id + '/view');
        }

        var ConnectEndpoint = QueryUtils.endpoint('/studyMaterial/journal/' + $scope.journal.id + '/connect');
        var clMapper = Classifier.valuemapper({typeCode: 'OPPEMATERJAL'});

        function loadMaterials() {
          var materialsEndpoint = ($scope.auth === null || angular.isUndefined($scope.auth) ? '/public' : '') +
            '/studyMaterial/journal/' + $scope.journal.id + '/materials';
          $scope.materials = QueryUtils.endpoint(materialsEndpoint).query();
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

        $scope.hAutocomplete = [];
        $scope.addExisting = function () {
          if (!$scope.existingMaterial) {
            message.error('main.messages.form-has-errors');
            return;
          }
          var connection = new ConnectEndpoint();
          connection.$save({studyMaterial: $scope.existingMaterial.id}).then(function () {
            $scope.hAutocomplete.forEach(function (r) { r.clear(); });
            loadMaterials();
          });
        };

        $scope.getDefaultBack = function () {
          switch ($route.current.params.backType) {
            case 'journal':
              return '/#/students/journals';
            case 'timetable':
              return $scope.auth === null || angular.isUndefined($scope.auth) || $scope.auth.school === null ? '/#/timetables' : '/#/timetable/generalTimetable/group';
            default:
              return '/#/studyMaterial/vocational';
          }
        };

    }]);
}());
