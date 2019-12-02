(function() {
  'use strict';

  angular
    .module('hitsaOis')
    .controller('TeacherRoleSearchController', TeacherRoleSearchController)
    .controller('TeacherRoleEditController', TeacherRoleEditController)
    .controller('TeacherRoleViewController', TeacherRoleViewController);

	TeacherRoleSearchController.$inject = ['$q', '$route', '$scope', 'Classifier', 'QueryUtils'];
  function TeacherRoleSearchController($q, $route, $scope, Classifier, QueryUtils) {
    $scope.currentNavItem = "teacherRoles";
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

  TeacherRoleEditController.$inject = [
    '$location', '$q', '$rootScope', '$route', '$scope', 'dialogService',
    'Classifier', 'message', 'QueryUtils', 'ArrayUtils', 'AuthService', 'resourceErrorHandler'
  ];
  function TeacherRoleEditController($location, $q, $rootScope, $route, $scope, dialogService, Classifier, message, QueryUtils, ArrayUtils, AuthService, resourceErrorHandler) {
    $scope.currentNavItem = "teacherRoles";
    var roleId = $route.current.params.role; // Edit
    var occupationId = $route.current.params.occupation; // New
    var Endpoint = QueryUtils.endpoint('/users/teacher-roles');
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
      rightsForEditing['ROLL_O'] = $scope.objects.reduce(function(roleMapping, object) {
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
          $scope.rights[right][code] = AuthService.isValidRolePermission('ROLL_O', right, code) ? 
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
          AuthService.isValidRolePermission('ROLL_O', right, code) && 
          !$scope.rights[right][code]) {
            $scope.multiSelects[code] = false;
            break;
          }
        }
      }
    } 

    $scope.showPermission = function(objectCode, permCode) {
      return AuthService.isValidRolePermission('ROLL_O', objectCode, permCode);
    };

    $scope.setRoleRights = function () {
      var objects = $scope.userRoleDefaults.defaultRights['ROLL_O'] || {};
      $scope.objectsForRole = $scope.objects.filter(function(it) { return objects[it.code]; });
      if(!rightsForEditing['ROLL_O']) {
        afterLoad(objects);
      }
      $scope.rights = rightsForEditing['ROLL_O'];
      setMultiSelects();
    };

    $scope.delete = function () {
      // School is set in backend
      QueryUtils.endpoint('/users/teacher-roles/check').get({
        teacherOccupationId: $scope.role.teacherOccupation.id,
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
          prompt: "userRole.dialog.teacher.deleteConfirm.message",
          yes: "main.yes",
          no: "main.no",
          cancel: "userRole.dialog.teacher.deleteConfirm.cancel"
        }

        function deleteUserRights() {
          fDelete(true);
        }

        function fDelete(deleteUserRights) {
          dialogScope.cancel();
          QueryUtils.endpoint('/users/teacher-roles/' + $scope.role.id + '/?deleteRights=' + (deleteUserRights ? true : false)).delete({}, function () {
            message.info('main.messages.delete.success');
            $rootScope.back('#/persons/teacher-roles?_menu');
          });
        }
      });
    }

    function askUpdateConfirmation() {
      dialogService.showDialog('persons/user.role.confirm.dialog.html', function (dialogScope) {
        dialogScope.yes = overwriteRights;
        dialogScope.no = update;
        dialogScope.messages = {
          prompt: "userRole.dialog.teacher.changeConfirm.message",
          yes: "main.yes",
          no: "main.no",
          cancel: "userRole.dialog.teacher.changeConfirm.cancel"
        };

        function overwriteRights() {
          $scope.role.overwriteRights = true;
          update();
        }

        function update() {
          dialogScope.cancel();
					if ($scope.role.id) {
						$scope.role.$update().then(message.updateSuccess).then(function() {
							afterLoad($scope.role.rights);
						});
					} else {
						$scope.role.$save().then(function (response) {
							$location.url('/persons/teacher-roles/' + response.id + '/edit?_noback');
							message.info('main.messages.create.success');
						});
					}
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

      // if ($scope.checkboxChanged) {

      // } else {

      // }

      // School is set in backend
      QueryUtils.endpoint('/users/teacher-roles/check').get({
        roleId: $scope.role.id,
        nameEt: $scope.role.nameEt,
        nameEn: $scope.role.nameEn,
        teacherOccupationId: occupationId ? occupationId : $scope.role.teacherOccupation.id
      }, function (result) {
        if (result.existsSameOccupation) {
          message.error("userRole.occupationExists");
        } else if (result.hasUsers) {
          askUpdateConfirmation();
        } else {
          if ($scope.role.id) {
            $scope.role.$update2().then(message.updateSuccess).then(function() {
              afterLoad($scope.role.rights);
            }).catch(setFormErrors);
          } else {
            $scope.role.$save2().then(function (response) {
              $location.url('/persons/teacher-roles/' + response.id + '/edit?_noback');
              message.info('main.messages.create.success');
            }).catch(setFormErrors);
          }
        }
      });
		};
		
		// only for new page
		var occupation = occupationId ? QueryUtils.endpoint('/school/teacheroccupations').get({id: occupationId}, function(result) {
			$scope.occupation = {
				id: result.id,
				nameEt: result.occupationEt,
				nameEn: result.occupationEn
			};
			$scope.role.nameEt = $scope.occupation.nameEt;
			$scope.role.nameEn = $scope.occupation.nameEn;
		}) : {};

    function setFormErrors(response) {
      resourceErrorHandler.responseError(response, $scope.roleForm).catch(angular.noop);
    }

    if (roleId) {
			$scope.role = Endpoint.get({id: roleId}, function (result) {
				$scope.occupation = result.teacherOccupation;
				QueryUtils.createQueryForm($scope, '/users/usersByOccupation/' + result.teacherOccupation.id, undefined, undefined, undefined, true, true);
			});
    } else {
			$scope.role = new Endpoint();
			// `occupationId` itself is a string
			var parsedId = parseInt(occupationId);
			if (parsedId) {
				$scope.role.teacherOccupation = parsedId;
				QueryUtils.createQueryForm($scope, '/users/usersByOccupation/' + parsedId, undefined, undefined, undefined, true, true);
			}
		}

    $q.all([$scope.objects.$promise, $scope.permissions.$promise, $scope.userRoleDefaults.$promise, $scope.role.$promise, occupation.$promise]).then(function() {
			$scope.loadData();
      var higher = school.higher ? true : undefined;
      var vocational = school.vocational ? true : undefined;
      var objects = $scope.userRoleDefaults.defaultRights['ROLL_O'] || {};
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
  
  TeacherRoleViewController.$inject = ['$q', '$route', '$scope', 'Classifier', 'QueryUtils',  'AuthService'];
  function TeacherRoleViewController($q, $route, $scope, Classifier, QueryUtils, AuthService) {
    $scope.currentNavItem = "teacherRoles";
    var roleId = $route.current.params.role;
    var Endpoint = QueryUtils.endpoint('/users/teacher-roles');
    var school = $route.current.locals.auth.school;

    $scope.objects = Classifier.queryForDropdown({mainClassCode: 'TEEMAOIGUS'});
    $scope.permissions = Classifier.queryForDropdown({mainClassCode: 'OIGUS'});
    $scope.userRoleDefaults = QueryUtils.endpoint('/users/rolesDefaults').search();
    $scope.role = Endpoint.get({id: roleId}, function (result) {
			QueryUtils.createQueryForm($scope, '/users/usersByOccupation/' + result.teacherOccupation.id, undefined, undefined, undefined, true, true);
		});

    $scope.showPermission = function(objectCode, permCode) {
      return AuthService.isValidRolePermission('ROLL_O', objectCode, permCode);
    };

    $q.all([$scope.objects.$promise, $scope.permissions.$promise, $scope.userRoleDefaults.$promise, $scope.role.$promise]).then(function() {
			$scope.loadData();
			
      var higher = school.higher ? true : undefined;
      var vocational = school.vocational ? true : undefined;
      var objects = $scope.userRoleDefaults.defaultRights['ROLL_O'] || {};
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
