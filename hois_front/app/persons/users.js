'use strict';

angular.module('hitsaOis').controller('UsersEditController', ['$location', '$q', '$rootScope', '$route', '$scope', 'dialogService', 'Classifier', 'message', 'QueryUtils', 'ArrayUtils', 'AuthService', 'School',
  function ($location, $q, $rootScope, $route, $scope, dialogService, Classifier, message, QueryUtils, ArrayUtils, AuthService, School) {
    $scope.currentNavItem = "users";
    var personId = $route.current.params.person;
    var userId = $route.current.params.user;
    var Endpoint = QueryUtils.endpoint('/persons/'+personId+'/users');

    $scope.auth = $route.current.locals.auth;
    $scope.multiSelects = {"OIGUS_V": false, "OIGUS_M": false, "OIGUS_K": false};
    $scope.noSchool = ['ROLL_P', 'ROLL_V'];
    $scope.filterValues = ['ROLL_L', 'ROLL_O', 'ROLL_T', 'ROLL_X'];
    if (!$scope.auth.isMainAdmin()) {
      $scope.filterValues.push('ROLL_P');
    }
    if ($scope.auth.isAdmin()) {
      $scope.filterValues.push('ROLL_V');
    }

    $scope.objects = Classifier.queryForDropdown({mainClassCode: 'TEEMAOIGUS'});
    $scope.permissions = Classifier.queryForDropdown({mainClassCode: 'OIGUS'});
    $scope.userRoleDefaults = QueryUtils.endpoint('/users/rolesDefaults').search();
    $scope.userRoleChanged = userRoleChanged;
    var userRoleRights;
    if ($scope.auth.isAdmin()) {
      $scope.userRoles = QueryUtils.endpoint("/users/userSchoolRoles?onlyAdmin=true").query();
      userRoleRights = QueryUtils.endpoint("/users/userSchoolRoleRights").get();
    } else {
      $scope.userRoles = [];
      userRoleRights = {};
    }
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

    $scope.$watch('user.curriculum', function () {
      if (angular.isDefined($scope.user.curriculum) && $scope.user.curriculum !== null) {
        if (!angular.isArray($scope.user.curriculums)) {
          $scope.user.curriculums = [];
        }
        if ($scope.user.curriculums.some(function (curriculum) {
            return curriculum.id === $scope.user.curriculum.id;
          })) {
          message.error('user.duplicateCurriculum');
          return;
        }
        $scope.user.curriculums.push($scope.user.curriculum);
        $scope.user.curriculum = undefined;
      }
    });

    $scope.deleteCurriculum = function (curriculum) {
      var index = $scope.user.curriculums.indexOf(curriculum);
      if (index !== -1) {
        $scope.user.curriculums.splice(index, 1);
        $scope.userForm.$setDirty();
      }
    };

    /**
     * @param {string} object TEEMAOIGUS
     * @param {string} permission OIGUS
     * @return true if not leading teacher or has this permission in default rights.
     */
    function isLeadingTeacherRight(object, permission) {
      return $scope.user.role !== 'ROLL_J' || ($scope.userRoleDefaults.defaultRights.ROLL_J[object] || []).indexOf(permission) !== -1 ||
        ($scope.userRoleDefaults.extraRights.ROLL_J[object] || []).indexOf(permission) !== -1;
    }

    function addRights(source) {
      for (var objcode in source) {
        for (var permcode in source[objcode]) {
          if (!$scope.rights[objcode]) {
            $scope.rights[objcode] = {};
          }
          $scope.rights[objcode][source[objcode][permcode]] = true;
        }
      }
    }

    function removeRights(source) {
      for (var objcode in source) {
        for (var permcode in source[objcode]) {
          if (($scope.rights[objcode] || [])[source[objcode][permcode]]) {
            $scope.rights[objcode][source[objcode][permcode]] = false;
          }
        }
      }
    }

    function resetRights() {
      for (var right in $scope.rights) {
        for (var code in $scope.rights[right]) {
          $scope.rights[right][code] = false;
        }
      }
    }

    function transformDate(response) {
      response.validFrom = new Date(response.validFrom);
      if (response.validThru) {
        response.validThru = new Date(response.validThru);
      }
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
          $scope.rights[right][code] = AuthService.isValidRolePermission($scope.user.role, right, code) && isLeadingTeacherRight(right, code) ?
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
          AuthService.isValidRolePermission($scope.user.role, right, code) &&
          isLeadingTeacherRight(right, code) &&
          !$scope.rights[right][code]) {
            $scope.multiSelects[code] = false;
            break;
          }
        }
      }
    }

    $scope.showPermission = function(objectCode, permCode) {
      return AuthService.isValidRolePermission($scope.user.role, objectCode, permCode) &&
              isLeadingTeacherRight(objectCode, permCode);
    };

    // Main admin can change school so with this it should consider school type (higher/vocational)
    $scope.schoolChanged = function () {
      var school = $scope.user.school;
      var role = $scope.user.role;
      if (!role || !school) {
        return;
      }

      var higher = (mappedSchoolsWithType[school.id] || {}).higher ? true : undefined;
      var vocational = (mappedSchoolsWithType[school.id] || {}).vocational ? true : undefined;
      var filteredObjects = $scope.objects.filter(function(it) {
        if ((higher !== undefined && vocational === undefined && higher !== it.higher) ||
            (vocational !== undefined && higher === undefined && vocational !== it.vocational) ||
            (vocational !== undefined && higher !== undefined && (vocational !== it.vocational && higher !== it.higher))) {
          return ($scope.user.id && ($scope.user.rights || {})[it.code]); // Allow already used for user.
        }
        return true;
      });

      var objects = $scope.userRoleDefaults.defaultRights[role] || {};
      $scope.objectsForRole = filteredObjects.filter(function(it) { return objects[it.code]; });

      if(!rightsForEditing[role]) {
        afterLoad(objects);
      }
      $scope.rights = rightsForEditing[role];
      setMultiSelects();
    };

    $scope.roleChanged = function () {
      var role = $scope.user.role;
      if ($scope.noSchool.indexOf(role) !== -1) {
        $scope.user.school = null;
      }

      var filteredObjects;
      if ($scope.user.school) {
        var higher = (mappedSchoolsWithType[$scope.user.school.id] || {}).higher ? true : undefined;
        var vocational = (mappedSchoolsWithType[$scope.user.school.id] || {}).vocational ? true : undefined;
        filteredObjects = $scope.objects.filter(function(it) {
          if ((higher !== undefined && vocational === undefined && higher !== it.higher) ||
              (vocational !== undefined && higher === undefined && vocational !== it.vocational) ||
              (vocational !== undefined && higher !== undefined && (vocational !== it.vocational && higher !== it.higher))) {
            return ($scope.user.id && ($scope.user.rights || {})[it.code]); // Allow already used for user.
          }
          return true;
        });
      } else {
        filteredObjects = $scope.objects;
      }
      var objects = $scope.userRoleDefaults.defaultRights[role] || {};
      $scope.objectsForRole = filteredObjects.filter(function(it) { return objects[it.code]; });
      if(!rightsForEditing[role]) {
        afterLoad(objects);
      }
      $scope.rights = rightsForEditing[role];
      setMultiSelects();
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
      if (!$scope.userForm.$valid) {
        message.error('main.messages.form-has-errors');
        return;
      }
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
          transformDate($scope.user);
        });
      } else {
        $scope.user.$save().then(function (response) {
          $location.url('/persons/' + response.person.id + '/users/' + response.id + '/edit?_noback');
          message.info('main.messages.create.success');
        });
      }
    };

    if (userId) {
      $scope.user = Endpoint.get({id: userId}, transformDate);
    } else {
      $scope.user = Endpoint.search({}, transformDate);
    }

    /**
     *
     * @param {object} newRole
     * @param {string} oldRole user role ID as string or "" if nothing was
     */
    function userRoleChanged(newRole, oldRole) {
      if (newRole === undefined && oldRole === "") {
        return;
      }
      if (newRole && newRole.id === parseInt(oldRole)) { // Same
        return;
      }
      var ONLY_ROLE = 'ROLL_A';
      if ($scope.user.role === ONLY_ROLE) {
        if (!$scope.user.id) {
          if (newRole)  {
            resetRights();
            addRights(userRoleRights[newRole.id].rights);
          } else {
            removeRights(userRoleRights[oldRole].rights);
          }
          return;
        }
        dialogService.showDialog('persons/user.role.confirm.dialog.html', function (dialogScope) {
          var _cancel = dialogScope.cancel;
          dialogScope.yes = overwriteRights;
          dialogScope.no = addRole;
          dialogScope.cancel = cancel;
          dialogScope.messages = {
            prompt: "user.dialog.userRoleChangeConfirm.message",
            yes: "main.yes",
            no: newRole ? "user.dialog.userRoleChangeConfirm.no" : "main.no",
            cancel: "user.dialog.userRoleChangeConfirm.cancel"
          };

          function overwriteRights() {
            if (newRole)  {
              resetRights();
              addRights(userRoleRights[newRole.id].rights);
            } else {
              // Reset to default values
              afterLoad($scope.userRoleDefaults.defaultRights[ONLY_ROLE]);
              $scope.roleChanged();
            }
            _cancel();
          }

          function addRole() {
            if (newRole)  {
              addRights(userRoleRights[newRole.id].rights);
            }
            _cancel();
          }

          function cancel() {
            var oldRoleId = parseInt(oldRole);
            $scope.user.userRole = oldRoleId ? ($scope.userRoles || []).find(function (r) { return r.id === oldRoleId; }) : undefined;
            _cancel();
          }

        });
      }
    }

    $scope.deleteCurriculum = function (curriculum) {
      for(var i = 0; i < $scope.user.curriculums.length; i++){
        if ($scope.user.curriculums[i].id === curriculum.id) {
          $scope.user.curriculums.splice(i, 1);
        }
      }
    };

    $scope.$watch('autoCurriculum', function (newV, oldV) {
      if (!newV || newV === oldV) {
        return;
      }

      if (!$scope.user.curriculums) {
        $scope.user.curriculums = [];
      }
      var found = $scope.user.curriculums.find(function (r) {
        return r.id === newV.id;
      });
      if (!found) {
        $scope.user.curriculums.push(newV);
      }

      $scope.autoCurriculum = null;
    });

    var mappedSchoolsWithType = {};
    var schoolsWithType = School.getSchoolsWithType().$promise.then(function (result) {
      mappedSchoolsWithType = (result || []).reduce(function (obj, r) {
        obj[r.id] = r;
        return obj;
      }, {});
    });

    var allRoles = Classifier.queryForDropdown({mainClassCode: 'ROLL'});

    $q.all([$scope.objects.$promise, $scope.permissions.$promise, $scope.userRoleDefaults.$promise,
            $scope.user.$promise, $scope.userRoles.$promise, userRoleRights.$promise, schoolsWithType, allRoles.$promise]).then(function() {
      if (!$scope.user.id && $scope.user.person.hasSchoolAdminRole) {
        $scope.filterValues.push('ROLL_A');
      }
      if (!$scope.user.role) {
        var allowedValues = allRoles.filter(function (r) {
          return $scope.filterValues.indexOf(r.code) === -1;
        });
        if (allowedValues.length === 1 && allowedValues[0].code === 'ROLL_A') {
          $scope.user.role = 'ROLL_A';
        }
      }
      afterLoad($scope.user.rights);
      $scope.roleChanged();
    });
  }
]).controller('UsersViewController', ['$q', '$route', '$scope', 'Classifier', 'QueryUtils', 'AuthService',
  function ($q, $route, $scope, Classifier, QueryUtils, AuthService) {
    $scope.currentNavItem = "users";
    var personId = $route.current.params.person;
    var userId = $route.current.params.user;
    var Endpoint = QueryUtils.endpoint('/persons/'+personId+'/users');

    $scope.objects = Classifier.queryForDropdown({mainClassCode: 'TEEMAOIGUS'});
    $scope.permissions = Classifier.queryForDropdown({mainClassCode: 'OIGUS'});
    $scope.userRoleDefaults = QueryUtils.endpoint('/users/rolesDefaults').search();
    $scope.user = Endpoint.get({id: userId});

    $scope.showPermission = function(objectCode, permCode) {
      return AuthService.isValidRolePermission($scope.user.role, objectCode, permCode) &&
              ($scope.user.role !== 'ROLL_J' || ($scope.userRoleDefaults.defaultRights.ROLL_J[objectCode] || []).indexOf(permCode) !== -1);
    };

    $q.all([$scope.objects.$promise, $scope.permissions.$promise, $scope.userRoleDefaults.$promise, $scope.user.$promise]).then(function() {
      var higher = $scope.user.higher ? true : undefined;
      var vocational = $scope.user.vocational ? true : undefined;
      var objects = $scope.userRoleDefaults.defaultRights[$scope.user.role] || {};
      $scope.objects = $scope.objects.filter(function(it) {
        if ((higher !== undefined && vocational === undefined && higher !== it.higher) ||
            (vocational !== undefined && higher === undefined && vocational !== it.vocational) ||
            (vocational !== undefined && higher !== undefined && (vocational !== it.vocational && higher !== it.higher))) {
          return objects[it.code] && ($scope.user.id && ($scope.user.rights || {})[it.code]); // Allow already used for user.
        }
        return objects[it.code];
      });
    });
  }
]);
