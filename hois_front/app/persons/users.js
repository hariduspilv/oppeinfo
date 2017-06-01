'use strict';

angular.module('hitsaOis')
  .controller('UsersEditController', ['$scope', '$location', '$route', '$rootScope', 'dialogService', 'Classifier', 'DataUtils', 'message', 'QueryUtils', 'Session', function ($scope, $location, $route, $rootScope, dialogService, Classifier, DataUtils, message, QueryUtils, Session) {
    var id = $route.current.params.person;
    var code = $route.current.params.user;

    $scope.noSchool = ['ROLL_P', 'ROLL_V'];
    $scope.showSchool = $route.current.locals.auth.isMainAdmin();

    var Endpoint = QueryUtils.endpoint('/persons/'+id+'/users');
    var clMapper = Classifier.valuemapper({object: 'TEEMAOIGUS'});

    function loadDefaults(action) {
      QueryUtils.endpoint('/users/rolesDefaults').search().$promise.then(function (response) {
        $scope.userRoleDefaults = response;
        if (action) {
          action(response);
        }
      });
    }

    function afterLoad() {
      DataUtils.convertStringToDates($scope.user, ['validFrom', 'validThru']);
      if ($scope.user.school) {
        $scope.user.school.id = Session.school.id.toString();
      }
      loadDefaults();
    }

    function afterNewLoad() {
      $scope.user.validFrom = new Date();
      loadDefaults(function (response) {
        $scope.user.rights = response.userRoles;
      });
      if (!$scope.showSchool) {
        $scope.user.school = {id: (Session.school.id.toString())};
      }
    }

    $scope.filterValues = ['ROLL_T', 'ROLL_L'];

    if (!$scope.showSchool) {
      $scope.filterValues.push('ROLL_P');
    }

    if ($route.current.locals.auth.isAdmin()) {
      $scope.filterValues.push('ROLL_V');
    }

    if (code) {
      $scope.user = Endpoint.get({id: code}, afterLoad);
    } else {
      $scope.user = new Endpoint.get(afterNewLoad);
    }

    $scope.roleChange = function () {
      if ($scope.noSchool.indexOf($scope.user.role) !== -1) {
        $scope.user.school = null;
      }
      if ($scope.userRoleDefaults) {
        var rights = $scope.userRoleDefaults.defaultRights.filter(function (it) {
          return it.roleCode === $scope.user.role;
        });
        if (rights && rights.length) {
          var map = JSON.parse(rights[0].data).reduce(function (map, obj) {
            map[obj.object] = {oigusM: obj.oigusM, oigusK: obj.oigusK, oigusV: obj.oigusV};
            return map;
          }, {});
          for (var i = 0; i < $scope.user.rights.length; i++) {
            var key = $scope.user.rights[i].object;
            var values = map[key];
            if (values) {
              $scope.user.rights[i].oigusM = (values.oigusM || false);
              $scope.user.rights[i].oigusK = (values.oigusK || false);
              $scope.user.rights[i].oigusV = (values.oigusV || false);
            }
          }
        }
      }
    };

    $scope.orderValue = function (item) {
      return $scope.currentLanguageNameField(clMapper.objectmapper({object: item.object}).object);
    };

    $scope.delete = function () {
      dialogService.confirmDialog({prompt: 'user.deleteconfirm'}, function () {
        $scope.user.$delete().then(function () {
          message.info('main.messages.delete.success');
          $rootScope.back('#/persons/' + id);
        });
      });
    };

    $scope.update = function () {
      $scope.userForm.$setSubmitted();
      if ($scope.userForm.$valid) {
        if ($scope.user.id) {
          $scope.user.$update().then(message.updateSuccess);
        } else {
          $scope.user.$save().then(function (response) {
            $location.path('/persons/' + response.person.id + '/users/' + response.id + '/edit').search({_noback: ''});
            message.info('main.messages.create.success');
          });
        }
      }
    };
  }])
  .controller('UsersViewController', ['$scope', '$route', '$rootScope', 'DataUtils', 'dialogService', 'message', 'QueryUtils', function ($scope, $route, $rootScope, DataUtils, dialogService, message, QueryUtils) {
    var id = $route.current.params.person;
    var code = $route.current.params.user;
    var Endpoint = QueryUtils.endpoint('/persons/'+id+'/users');

    $scope.delete = function () {
      dialogService.confirmDialog({prompt: 'user.deleteconfirm'}, function () {
        $scope.user.$delete().then(function () {
          message.info('main.messages.delete.success');
          $rootScope.back('#/persons/' + id);
        });
      });
    };

    function afterLoad() {
      DataUtils.convertStringToDates($scope.user, ['validFrom', 'validThru']);
    }

    $scope.user = Endpoint.get({id: code}, afterLoad);
  }]);
