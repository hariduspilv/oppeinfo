'use strict';

angular.module('hitsaOis').config(function ($routeProvider, USER_ROLES) {
  var authorizedRoles = {authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]};

  $routeProvider
    .when('/students/:id/main', {
      templateUrl: 'views/student/view.main.html',
      controller: 'StudentViewMainController',
      controllerAs: 'controller',
      data: authorizedRoles
    }).when('/students/:id/documents', {
      templateUrl: 'views/student/view.documents.html',
      controller: 'StudentViewDocumentsController',
      controllerAs: 'controller',
      data: authorizedRoles
    }).when('/students/:id/edit', {
      templateUrl: 'views/student/edit.main.html',
      controller: 'StudentEditController',
      controllerAs: 'controller',
      data: authorizedRoles
    }).when('/students/:id/absences', {
      templateUrl: 'views/student/view.absences.html',
      controller: 'StudentAbsencesController',
      controllerAs: 'controller',
      data: authorizedRoles
    });

}).controller('StudentViewMainController', ['$mdDialog', '$q', '$route', '$scope', 'dialogService', 'message', 'Classifier', 'DataUtils', 'QueryUtils',

  function ($mdDialog, $q, $route, $scope, dialogService, message, Classifier, DataUtils, QueryUtils) {
    var studentId = $route.current.params.id;
    var Endpoint = QueryUtils.endpoint('/students');

    $scope.studentId = studentId;
    $scope.currentNavItem = 'student.main';
    var backUrl = $route.current.params.backUrl;
    $scope.formState = {backRef: backUrl ? '?backUrl='+backUrl : '', backUrl: backUrl === 'applications' ? '#/studentrepresentatives/applications' : '#/students'};

    $scope.student = Endpoint.get({id: studentId});
    $scope.student.$promise.then(function() {
      DataUtils.convertStringToDates($scope.student, ['studyStart', 'nominalStudyEnd']);
      if($scope.student.photo) {
        $scope.student.imageUrl = 'data:' + $scope.student.photo.ftype + ';base64,' + $scope.student.photo.fdata;
      } else {
        $scope.student.imageUrl = '?' + new Date().getTime();
      }
    });

    $scope.representativesCriteria = {order: 'person.lastname', size: 5, page: 1};
    $scope.representatives = {};
    var representativesMapper = Classifier.valuemapper({relation: 'OPPURESINDAJA'});

    $scope.afterRepresentativesLoad = function(result) {
      $scope.representatives.content = representativesMapper.objectmapper(result.content);
      $scope.representatives.totalElements = result.totalElements;
    };

    function loadRepresentatives() {
      var query = angular.extend({}, QueryUtils.getQueryParams($scope.representativesCriteria), {id: studentId});
      $scope.representatives.$promise = QueryUtils.endpoint('/studentrepresentatives/:id').search(query, $scope.afterRepresentativesLoad);
    }
    $scope.loadRepresentatives = loadRepresentatives;

    $q.all(representativesMapper.promises).then(loadRepresentatives);

    $scope.editRepresentative = function(representativeId) {
      var RepresentativeEndpoint = QueryUtils.endpoint('/studentrepresentatives/'+studentId);
      $mdDialog.show({
        controller: function($scope) {
          $scope.formState = {};

          if(representativeId) {
            $scope.record = RepresentativeEndpoint.get({id: representativeId});
          } else {
            $scope.record = new RepresentativeEndpoint({person: {}});
          }
          $scope.cancel = $mdDialog.hide;
          $scope.update = function() {
            $scope.studentRepresentativeEditForm.$setSubmitted();
            if(!$scope.studentRepresentativeEditForm.$valid) {
              message.error('main.messages.form-has-errors');
              return;
            }
            var msg = $scope.record.id ? 'main.messages.update.success' : 'main.messages.create.success';
            function afterSave() {
              message.info(msg);
              $mdDialog.hide();
              loadRepresentatives();
            }
            if($scope.record.id) {
              $scope.record.$update().then(afterSave);
            } else {
              $scope.record.$save().then(afterSave);
            }
          };
          $scope.delete = function() {
            dialogService.confirmDialog({prompt: 'student.representative.deleteconfirm'}, function() {
              $scope.record.$delete().then(function() {
                message.info('main.messages.delete.success');
                $mdDialog.hide();
                loadRepresentatives();
              });
            });
          };
          $scope.lookupPerson = function(response) {
            // fill first/lastname and make them readonly
            $scope.record.person.firstname = response.firstname;
            $scope.record.person.lastname = response.lastname;
            // fill other empty fields
            if(!$scope.record.person.phone && response.phone) {
              $scope.record.person.phone = response.phone;
            }
            if(!$scope.record.person.email && response.email) {
              $scope.record.person.email = response.email;
            }
            $scope.formState.idcode = response.idcode;
          };
          $scope.lookupFailure = function() {
            $scope.formState.idcode = undefined;
          };
        },
        templateUrl: 'views/student/representative.edit.dialog.html',
        clickOutsideToClose: true
      });
    };
  }
]).controller('StudentViewDocumentsController', ['$route', '$scope', 'QueryUtils', function ($route, $scope, QueryUtils) {
  $scope.studentId = $route.current.params.id;

  $scope.currentNavItem = 'student.documents';
  $scope.directivesCriteria = {order: 'title', studentId: $scope.studentId};
  $scope.directives = {};

  $scope.afterDirectivesLoad = function(result) {
    $scope.directives.content = result.content;
    $scope.directives.totalElements = result.totalElements;
  };

  $scope.loadDirectives = function() {
    var query = QueryUtils.getQueryParams($scope.directivesCriteria);
    $scope.directives.$promise = QueryUtils.endpoint('/students/:studentId/directives').search(query, $scope.afterDirectivesLoad);
  };

  $scope.loadDirectives();
}]).controller('StudentEditController', ['$location', '$route', '$scope', 'message', 'QueryUtils', function ($location, $route, $scope, message, QueryUtils) {
    var id = $route.current.params.id;

    $scope.student = QueryUtils.endpoint('/students').get({id: id});
    $scope.update = function() {
      $scope.studentEditForm.$setSubmitted();
      if(!$scope.studentEditForm.$valid) {
        message.error('main.messages.form-has-errors');
        return;
      }
      $scope.student.$update().then(function() {
        message.info('main.messages.update.success');
        $location.path('/students/'+id+'/main');
      });
    };
}]).controller('StudentAbsencesController', ['$mdDialog', '$route', '$scope', 'dialogService', 'message', 'DataUtils', 'QueryUtils',
  function ($mdDialog, $route, $scope, dialogService, message, DataUtils, QueryUtils) {
    $scope.studentId = $route.current.params.id;
    $scope.currentNavItem = 'student.absences';
    var backUrl = $route.current.params.backUrl;
    $scope.formState = {backRef: backUrl ? '?backUrl='+backUrl : '', backUrl: backUrl === 'applications' ? '#/studentrepresentatives/applications' : '#/students'};

    QueryUtils.createQueryForm($scope, '/students/' + $scope.studentId + '/absences', {order: 'validFrom'}, function(rows) {
      for(var rowNo = 0; rowNo < rows.length; rowNo++) {
        var row = rows[rowNo];
        DataUtils.convertStringToDates(row, ['validFrom', 'validThru']);
      }
    });

    $scope.editAbsence = function(absence) {
      var AbsenceEndpoint = QueryUtils.endpoint('/students/'+$scope.studentId+'/absences');
      var loadAbsences = $scope.loadData;

      $mdDialog.show({
        controller: function($scope) {
          $scope.record = new AbsenceEndpoint(absence || {});
          $scope.cancel = $mdDialog.hide;
          $scope.update = function() {
            $scope.studentAbsenceForm.$setSubmitted();
            if(!$scope.studentAbsenceForm.$valid) {
              message.error('main.messages.form-has-errors');
              return;
            }
            var msg = $scope.record.id ? 'main.messages.update.success' : 'main.messages.create.success';
            function afterSave() {
              message.info(msg);
              $mdDialog.hide();
              loadAbsences();
            }
            if($scope.record.id) {
              $scope.record.$update().then(afterSave);
            } else {
              $scope.record.$save().then(afterSave);
            }
          };
          $scope.delete = function() {
            dialogService.confirmDialog({prompt: 'student.absence.deleteconfirm'}, function() {
              $scope.record.$delete().then(function() {
                message.info('main.messages.delete.success');
                $mdDialog.hide();
                loadAbsences();
              });
            });
          };
        },
        templateUrl: 'views/student/absence.edit.dialog.html',
        clickOutsideToClose: true
      });
    };

    $scope.loadData();
}]);
