'use strict';

angular.module('hitsaOis').controller('UsersEditController', ['$location', '$q', '$rootScope', '$route', '$scope', 'dialogService', 'Classifier', 'message', 'QueryUtils',
  function ($location, $q, $rootScope, $route, $scope, dialogService, Classifier, message, QueryUtils) {
    var personId = $route.current.params.person;
    var userId = $route.current.params.user;
    var Endpoint = QueryUtils.endpoint('/persons/'+personId+'/users');

    $scope.auth = $route.current.locals.auth;
    $scope.noSchool = ['ROLL_P', 'ROLL_V'];
    $scope.filterValues = ['ROLL_L', 'ROLL_O', 'ROLL_T'];
    if (!$scope.auth.isMainAdmin()) {
      $scope.filterValues.push('ROLL_P');
    }
    if ($scope.auth.isAdmin()) {
      $scope.filterValues.push('ROLL_V');
    }

    $scope.objects = Classifier.queryForDropdown({mainClassCode: 'TEEMAOIGUS'});
    $scope.permissions = Classifier.queryForDropdown({mainClassCode: 'OIGUS'});
    $scope.userRoleDefaults = QueryUtils.endpoint('/users/rolesDefaults').search();
    var rightsForEditing = {};

    function afterLoad(rights) {
      if(!rights) {
        return;
      }
      rightsForEditing[$scope.user.role] = $scope.objects.reduce(function(roleMapping, object) {
        var objcode = object.code;
        roleMapping[objcode] = $scope.permissions.reduce(function(permissions, perm) {
          var permcode = perm.code;
          permissions[permcode] = (rights[objcode] || []).indexOf(permcode) !== -1;
          return permissions;
        }, {});
        return roleMapping;
      }, {});
    }

    $scope.roleChanged = function () {
      var role = $scope.user.role;
      if ($scope.noSchool.indexOf(role) !== -1) {
        $scope.user.school = null;
      }
      var objects = $scope.userRoleDefaults.defaultRights[role] || {};
      $scope.objectsForRole = $scope.objects.filter(function(it) { return objects[it.code]; });
      if(!rightsForEditing[role]) {
        afterLoad(objects);
      }
      $scope.rights = rightsForEditing[role];
    };

    $scope.delete = function () {
      dialogService.confirmDialog({prompt: 'user.deleteconfirm'}, function () {
        $scope.user.$delete().then(function () {
          message.info('main.messages.delete.success');
          $rootScope.back('#/persons/' + personId);
        });
      });
    };

    $scope.update = function () {
      $scope.userForm.$setSubmitted();
      if ($scope.userForm.$valid) {
        // set rights
        var code = function(it) {
          return it.code;
        };
        $scope.user.rights = $scope.objectsForRole.reduce(function(rights, object) {
          var objcode = object.code;
          rights[objcode] = $scope.permissions.filter(function (it) { return $scope.rights[objcode][it.code]; }).map(code);
          return rights;
        }, {});

        if ($scope.user.id) {
          $scope.user.$update().then(message.updateSuccess).then(function() {
            afterLoad($scope.user.rights);
          });
        } else {
          $scope.user.$save().then(function (response) {
            $location.url('/persons/' + response.person.id + '/users/' + response.id + '/edit?_noback');
            message.info('main.messages.create.success');
          });
        }
      }
    };

    if (userId) {
      $scope.user = Endpoint.get({id: userId});
    } else {
      $scope.user = Endpoint.search();
    }

    $q.all([$scope.objects.$promise, $scope.permissions.$promise, $scope.userRoleDefaults.$promise, $scope.user.$promise]).then(function() {
      afterLoad($scope.user.rights);
      $scope.roleChanged();
    });
  }
]).controller('UsersViewController', ['$q', '$route', '$scope', 'Classifier', 'QueryUtils',
  function ($q, $route, $scope, Classifier, QueryUtils) {
    var personId = $route.current.params.person;
    var userId = $route.current.params.user;
    var Endpoint = QueryUtils.endpoint('/persons/'+personId+'/users');

    $scope.objects = Classifier.queryForDropdown({mainClassCode: 'TEEMAOIGUS'});
    $scope.permissions = Classifier.queryForDropdown({mainClassCode: 'OIGUS'});
    $scope.userRoleDefaults = QueryUtils.endpoint('/users/rolesDefaults').search();
    $scope.user = Endpoint.get({id: userId});

    $q.all([$scope.objects.$promise, $scope.permissions.$promise, $scope.userRoleDefaults.$promise, $scope.user.$promise]).then(function() {
      var objects = $scope.userRoleDefaults.defaultRights[$scope.user.role] || {};
      $scope.objects = $scope.objects.filter(function(it) { return objects[it.code]; });
    });
  }
]);
