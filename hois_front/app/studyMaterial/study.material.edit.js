(function () {
  'use strict';

  angular.module('hitsaOis').controller('StudyMaterialHigherEditController', ['$scope', '$route', 'QueryUtils', 'oisFileService',
    'dialogService', 'message', '$location',
    function ($scope, $route, QueryUtils, oisFileService, dialogService, message, $location) {
      $scope.auth = $route.current.locals.auth;
      $scope.subjectStudyPeriod = $route.current.locals.subjectStudyPeriod;
      $scope.backPath = '/studyMaterial/higher/' + $scope.subjectStudyPeriod.id + '/edit';
      var materialId = $route.current.params.materialId;
      var MaterialEndpoint = QueryUtils.endpoint('/studyMaterial');
      if (materialId) {
        $scope.material = MaterialEndpoint.get({ id: materialId });
        $scope.material.$promise.then(function (material) {
          $scope.teacher = material.teacher;
        });
      } else {
        $scope.material = new MaterialEndpoint({
          isVisibleToStudents: false,
          isPublic: false
        });
      }

      $scope.$watch('teacher', function (newTeacher) {
        $scope.material.teacher = newTeacher ? newTeacher.id : null;
      });
      $scope.$watch('material.typeCode', function (newTypeCode) {
        $scope.material.url = newTypeCode === 'OPPEMATERJAL_L' ? 'http://' : null;
      });

      $scope.delete = function () {
        dialogService.confirmDialog({
          prompt: 'apel.deleteConfirm'
        }, function () {
          $scope.material.$delete().then(function () {
            message.info('main.messages.delete.success');
            $location.path($scope.backPath);
          }).catch(angular.noop);
        });
      };

      $scope.save = function () {
        $scope.material.subjectStudyPeriod = $scope.subjectStudyPeriod.id;
        $scope.material.isVocational = false;

        $scope.studyMaterialForm.$setSubmitted();
        if (!$scope.studyMaterialForm.$valid) {
          message.error('main.messages.form-has-errors');
          return;
        }
        function doSave() {
          if ($scope.material.id) {
            $scope.material.$update().then(afterSave).catch(angular.noop);
          } else {
            $scope.material.$save().then(afterSave).catch(angular.noop);
          }
        }
        function afterSave() {
          message.info(materialId ? 'main.messages.update.success' : 'main.messages.create.success');
          $location.path($scope.backPath);
        }
        if ($scope.material.typeCode === 'OPPEMATERJAL_F' && !$scope.material.id) {
          if ($scope.files.length !== 1) {
            message.error('main.messages.form-has-errors');
            return;
          }
          oisFileService.getFromLfFile($scope.files[0], function (oisFile) {
            $scope.material.oisFile = oisFile;
            doSave();
          });
        } else {
          doSave();
        }
      };
    }]).controller('StudyMaterialVocationalEditController', ['$scope', '$route', 'QueryUtils', 'oisFileService',
      'dialogService', 'message', '$location',
      function ($scope, $route, QueryUtils, oisFileService, dialogService, message, $location) {
        $scope.auth = $route.current.locals.auth;
        $scope.journal = $route.current.locals.journal;
        $scope.backPath = '/studyMaterial/vocational/' + $scope.journal.id + '/edit';
        var materialId = $route.current.params.materialId;
        var MaterialEndpoint = QueryUtils.endpoint('/studyMaterial');
        if (materialId) {
          $scope.material = MaterialEndpoint.get({ id: materialId });
          $scope.material.$promise.then(function (material) {
            $scope.teacher = material.teacher;
          });
        } else {
          $scope.material = new MaterialEndpoint({
            isVisibleToStudents: false,
            isPublic: false
          });
        }

        $scope.$watch('teacher', function (newTeacher) {
          $scope.material.teacher = newTeacher ? newTeacher.id : null;
        });
        $scope.$watch('material.typeCode', function (newTypeCode) {
          $scope.material.url = newTypeCode === 'OPPEMATERJAL_L' ? 'http://' : null;
        });

        $scope.delete = function () {
          dialogService.confirmDialog({
            prompt: 'apel.deleteConfirm'
          }, function () {
            $scope.material.$delete().then(function () {
              message.info('main.messages.delete.success');
              $location.path($scope.backPath);
            }).catch(angular.noop);
          });
        };

        $scope.save = function () {
          $scope.material.journal = $scope.journal.id;
          $scope.material.isVocational = true;

          $scope.studyMaterialForm.$setSubmitted();
          if (!$scope.studyMaterialForm.$valid) {
            message.error('main.messages.form-has-errors');
            return;
          }
          function doSave() {
            if ($scope.material.id) {
              $scope.material.$update().then(afterSave).catch(angular.noop);
            } else {
              $scope.material.$save().then(afterSave).catch(angular.noop);
            }
          }
          function afterSave() {
            message.info(materialId ? 'main.messages.update.success' : 'main.messages.create.success');
            $location.path($scope.backPath);
          }
          if ($scope.material.typeCode === 'OPPEMATERJAL_F' && !$scope.material.id) {
            if ($scope.files.length !== 1) {
              message.error('main.messages.form-has-errors');
              return;
            }
            oisFileService.getFromLfFile($scope.files[0], function (oisFile) {
              $scope.material.oisFile = oisFile;
              doSave();
            });
          } else {
            doSave();
          }
        };
      }]);
}());
