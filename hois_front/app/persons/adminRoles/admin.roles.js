(function() {
  'use strict';

  angular
    .module('hitsaOis')
    .controller('AdminRoleSearchController', AdminRoleSearchController)
    .controller('AdminRoleEditController', AdminRoleEditController)
    .controller('AdminRoleViewController', AdminRoleViewController);

  AdminRoleSearchController.$inject = ['$q', '$route', '$scope', 'Classifier', 'QueryUtils'];
  function AdminRoleSearchController($q, $route, $scope, Classifier, QueryUtils) {
    $scope.currentNavItem = "adminRoles";
    $scope.auth = $route.current.locals.auth;

    var clMapping = $route.current.locals.clMapping;
    var clMapper = clMapping ? Classifier.valuemapper(clMapping) : undefined;
    QueryUtils.createQueryForm($scope, $route.current.locals.url, $route.current.locals.params, clMapper ? clMapper.objectmapper : undefined);

    if(clMapper) {
      $q.all(clMapper.promises).then($scope.loadData);
    } else {
      $scope.loadData();
    }

    $scope.directiveControllers = [];
    var clearCriteria = $scope.clearCriteria;
    $scope.clearCriteria = function () {
      clearCriteria();
      $scope.directiveControllers.forEach(function (c) {
        c.clear();
      });
    };
  }

  AdminRoleEditController.$inject = [
    '$location', '$q', '$rootScope', '$route', '$scope', 'dialogService',
    'Classifier', 'message', 'QueryUtils', 'ArrayUtils', 'AuthService', 'resourceErrorHandler'
  ];
  function AdminRoleEditController($location, $q, $rootScope, $route, $scope, dialogService, Classifier, message, QueryUtils, ArrayUtils, AuthService, resourceErrorHandler) {
    $scope.currentNavItem = "adminRoles";
    var roleId = $route.current.params.role;
    var Endpoint = QueryUtils.endpoint('/users/admin-roles');
    var school = $route.current.locals.auth.school;

    $scope.auth = $route.current.locals.auth;
    $scope.multiSelects = {"OIGUS_V": false, "OIGUS_M": false, "OIGUS_K": false};

    $scope.objects = Classifier.queryForDropdown({mainClassCode: 'TEEMAOIGUS'});
    $scope.permissions = Classifier.queryForDropdown({mainClassCode: 'OIGUS'});
    $scope.userRoleDefaults = QueryUtils.endpoint('/users/rolesDefaults').search();
    var rightsForEditing = {};

    function afterLoad(rights) {
      if(!rights) {
        return;
      }
      rightsForEditing['ROLL_A'] = $scope.objects.reduce(function(roleMapping, object) {
        var objcode = object.code;
        roleMapping[objcode] = $scope.permissions.reduce(function(permissions, perm) {
          permissions[perm.code] = roleId ? (rights[objcode] || []).indexOf(perm.code) !== -1: false;
          return permissions;
        }, {});
        return roleMapping;
      }, {});
    }

    function getRightsForRole() {
      var rightsForRole = [];

      for (var key in $scope.objectsForRole) {
        rightsForRole.push($scope.objectsForRole[key].code);
      }
      return rightsForRole;
    }

    $scope.multiSelectPermission = function (code, permissionValue) {
      var rightsForRole = getRightsForRole();

      for (var right in $scope.rights) {
        if (ArrayUtils.contains(rightsForRole, right)) {
          $scope.rights[right][code] = AuthService.isValidRolePermission('ROLL_A', right, code) ? 
            permissionValue : false;
        }
      }
    };

    function setMultiSelects() {
      var rightsForRole = getRightsForRole();

      for (var code in $scope.multiSelects) {
        $scope.multiSelects[code] = true;
        for (var right in $scope.rights) {
          if (ArrayUtils.contains(rightsForRole, right) && 
          AuthService.isValidRolePermission('ROLL_A', right, code) && 
          !$scope.rights[right][code]) {
            $scope.multiSelects[code] = false;
            break;
          }
        }
      }
    } 

    $scope.showPermission = function(objectCode, permCode) {
      return AuthService.isValidRolePermission('ROLL_A', objectCode, permCode);
    };

    $scope.setRoleRights = function () {
      var objects = $scope.userRoleDefaults.defaultRights['ROLL_A'] || {};
      $scope.objectsForRole = $scope.objects.filter(function(it) { return objects[it.code]; });
      if(!rightsForEditing['ROLL_A']) {
        afterLoad(objects);
      }
      $scope.rights = rightsForEditing['ROLL_A'];
      setMultiSelects();
    };

    $scope.delete = function () {
      // School is set in backend
      QueryUtils.endpoint('/users/admin-roles/check').get({
        roleId: $scope.role.id,
        nameEt: $scope.role.nameEt,
        nameEn: $scope.role.nameEn
      }, function (result) {
        if (result.hasUsers) {
          askDeleteConfirmation();
        } else {
          dialogService.confirmDialog({prompt: 'userRole.deleteConfirm'}, function () {
            $scope.role.$delete().then(function () {
              message.info('main.messages.delete.success');
              $rootScope.back('#/persons/admin-roles?_menu');
            });
          });
        }
      });
    };

    function askDeleteConfirmation() {
      dialogService.showDialog('persons/user.role.confirm.dialog.html', function (dialogScope) {
        dialogScope.yes = deleteUserRights;
        dialogScope.no = fDelete;
        dialogScope.messages = {
          prompt: "userRole.dialog.admin.deleteConfirm.message",
          yes: "main.yes",
          no: "main.no",
          cancel: "userRole.dialog.admin.deleteConfirm.cancel"
        };

        function deleteUserRights() {
          fDelete(true);
        }

        function fDelete(deleteUserRights) {
          dialogScope.cancel();
          QueryUtils.endpoint('/users/admin-roles/' + $scope.role.id + '/?deleteRights=' + (deleteUserRights ? true : false)).delete({}, function () {
            message.info('main.messages.delete.success');
            $rootScope.back('#/persons/admin-roles?_menu');
          });
        }
      });
    }

    function askUpdateConfirmation() {
      dialogService.showDialog('persons/user.role.confirm.dialog.html', function (dialogScope) {
        dialogScope.yes = overwriteRights;
        dialogScope.no = update;
        dialogScope.messages = {
          prompt: "userRole.dialog.admin.changeConfirm.message",
          yes: "main.yes",
          no: "main.no",
          cancel: "userRole.dialog.admin.changeConfirm.cancel"
        };

        function overwriteRights() {
          $scope.role.overwriteRights = true;
          update();
        }

        function update() {
          dialogScope.cancel();
          $scope.role.$update().then(message.updateSuccess).then(function() {
            afterLoad($scope.role.rights);
          });
        }
      });
    }

    $scope.update = function () {
      $scope.roleForm.nameEt.$setValidity('serverside', true);
      $scope.roleForm.nameEn.$setValidity('serverside', true);
      $scope.roleForm.$setSubmitted();
      if (!$scope.roleForm.$valid) {
        message.error('main.messages.form-has-errors');
        return;
      }
      // set rights
      var code = function(it) {
        return it.code;
      };
      $scope.role.rights = $scope.objectsForRole.reduce(function(rights, object) {
        var objcode = object.code;
        rights[objcode] = $scope.permissions.filter(function (it) { return $scope.rights[objcode][it.code]; }).map(code);
        return rights;
      }, {});

      if ($scope.role.id) {
        if ($scope.checkboxChanged) {
          // School is set in backend
          QueryUtils.endpoint('/users/admin-roles/check').get({
            roleId: $scope.role.id,
            nameEt: $scope.role.nameEt,
            nameEn: $scope.role.nameEn
          }, function (result) {
            if (result.hasUsers) {
              askUpdateConfirmation();
            } else {
              $scope.role.$update2().then(message.updateSuccess).then(function() {
                afterLoad($scope.role.rights);
              }).catch(setFormErrors);
            }
          });
        } else {
          $scope.role.$update2().then(message.updateSuccess).then(function() {
            afterLoad($scope.role.rights);
          }).catch(setFormErrors);
        }
      } else {
        $scope.role.$save2().then(function (response) {
          $location.url('/persons/admin-roles/' + response.id + '/edit?_noback');
          message.info('main.messages.create.success');
        }).catch(setFormErrors);
      }
    };

    function setFormErrors(response) {
      resourceErrorHandler.responseError(response, $scope.roleForm).catch(angular.noop);
    }

    if (roleId) {
      $scope.role = Endpoint.get({id: roleId});
      QueryUtils.createQueryForm($scope, '/users/usersByRole/' + roleId, undefined, undefined, undefined, true, true);
      $scope.loadData();
    } else {
      $scope.role = new Endpoint();
    }

    $q.all([$scope.objects.$promise, $scope.permissions.$promise, $scope.userRoleDefaults.$promise, $scope.role.$promise]).then(function() {
      var higher = school.higher ? true : undefined;
      var vocational = school.vocational ? true : undefined;
      var objects = $scope.userRoleDefaults.defaultRights['ROLL_A'] || {};
      $scope.objects = $scope.objects.filter(function(it) {
        if ((higher !== undefined && vocational === undefined && higher !== it.higher)
            || (vocational !== undefined && higher === undefined && vocational !== it.vocational)
            || (vocational !== undefined && higher !== undefined && (vocational !== it.vocational && higher !== it.higher))) {
          return objects[it.code] && ($scope.role.id && ($scope.role.rights || {})[it.code]); // Allow already used for role.
        }
        return objects[it.code];
      });
      afterLoad($scope.role.rights);
      $scope.setRoleRights();
    });
  }
  
  AdminRoleViewController.$inject = ['$q', '$route', '$scope', 'Classifier', 'QueryUtils',  'AuthService'];
  function AdminRoleViewController($q, $route, $scope, Classifier, QueryUtils, AuthService) {
    $scope.currentNavItem = "adminRoles";
    var roleId = $route.current.params.role;
    var Endpoint = QueryUtils.endpoint('/users/admin-roles');
    var school = $route.current.locals.auth.school;

    $scope.objects = Classifier.queryForDropdown({mainClassCode: 'TEEMAOIGUS'});
    $scope.permissions = Classifier.queryForDropdown({mainClassCode: 'OIGUS'});
    $scope.userRoleDefaults = QueryUtils.endpoint('/users/rolesDefaults').search();
    $scope.role = Endpoint.get({id: roleId}, function () {
      QueryUtils.createQueryForm($scope, '/users/usersByRole/' + roleId, undefined, undefined, undefined, true, true);
      $scope.loadData();
    });

    $scope.showPermission = function(objectCode, permCode) {
      return AuthService.isValidRolePermission('ROLL_A', objectCode, permCode);
    };

    $q.all([$scope.objects.$promise, $scope.permissions.$promise, $scope.userRoleDefaults.$promise, $scope.role.$promise]).then(function() {
      var higher = school.higher ? true : undefined;
      var vocational = school.vocational ? true : undefined;
      var objects = $scope.userRoleDefaults.defaultRights['ROLL_A'] || {};
      $scope.objects = $scope.objects.filter(function(it) {
        if ((higher !== undefined && vocational === undefined && higher !== it.higher)
            || (vocational !== undefined && higher === undefined && vocational !== it.vocational)
            || (vocational !== undefined && higher !== undefined && (vocational !== it.vocational && higher !== it.higher))) {
          return objects[it.code] && ($scope.role.id && ($scope.role.rights || {})[it.code]); // Allow already used for role.
        }
        return objects[it.code];
      });
    });
  }
})();
