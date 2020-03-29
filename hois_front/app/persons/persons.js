'use strict';

angular.module('hitsaOis').controller('PersonsEditController', ['$location', '$route', '$scope', '$rootScope', 'dialogService', 'DataUtils', 'message', 'PersonService', 'QueryUtils',
  function ($location, $route, $scope, $rootScope, dialogService, DataUtils, message, PersonService, QueryUtils) {
    $scope.currentNavItem = "users";
    var id = $route.current.params.id;
    var baseUrl = '/persons';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    $scope.auth = $route.current.locals.auth;
    $scope.maxDate = new Date();

    function afterLoad() {

      DataUtils.convertStringToDates($scope.person, ['birthdate']);

      $scope.users = $scope.person.users;
      $scope.person.users = null;
      if ($scope.person.idcode && $scope.person.idcode.length === 11) {
        $scope.person.sex = DataUtils.sexFromIdcode($scope.person.idcode);
        $scope.person.birthdate = DataUtils.birthdayFromIdcode($scope.person.idcode);
        $scope.idcodeReadonly = true;
      }
      if ($scope.users) {
        PersonService.usersAfterLoad($scope.users);
      }
    }

    $scope.lookupPerson = function (response) {
      if (id) {
        $scope.person.idcode = undefined;
        message.error('person.exists');
      } else {
        $location.path(baseUrl + '/' + response.id + '/edit');
        message.info('person.exists');
      }
      $scope.person.birthdate = response.birthdate;
      $scope.person.sex = response.sex;
    };

    function clearData() {
      if (!id) {
        $scope.person.firstname = '';
        $scope.person.lastname = '';
        $scope.person.email = '';
        $scope.person.phone = '';
      }
    }

    $scope.lookupFailure = function () {
      $scope.person.sex = DataUtils.sexFromIdcode($scope.person.idcode);
      $scope.person.birthdate = DataUtils.birthdayFromIdcode($scope.person.idcode);
      clearData();
    };

    $scope.cleanReadOnly = function () {
      clearData();
      $scope.person.sex = undefined;
      $scope.person.birthdate = undefined;
    };

    if (id) {
      $scope.person = Endpoint.get({id: id}, afterLoad);
    } else {
      $scope.person = new Endpoint();
    }

    $scope.orderValue = function (item) {
      return $scope.currentLanguageNameField(item.role);
    };

    $scope.personBack = function () {
      if (($scope.users && $scope.users.length > 0) || $scope.auth.isMainAdmin() || !$scope.person.id) {
        $rootScope.back('#' + baseUrl);
      } else {
        dialogService.confirmDialog({prompt: 'person.back'}, function () {
          $rootScope.back('#' + baseUrl);
        });
      }
    };

    $scope.update = function () {
      $scope.personForm.$setSubmitted();
      if ($scope.personForm.$valid) {
        if ($scope.person.id) {
          $scope.person.$update().then(message.updateSuccess);
        } else {
          $scope.person.$save().then(function (response) {
            $location.url(baseUrl + '/' + response.id + '/edit?_noback');
            message.info('main.messages.create.success');
          });
        }
      }
    };

    $scope.delete = PersonService.deletePerson;
  }
]).controller('PersonsViewController', ['$scope', '$route', 'PersonService','QueryUtils', function ($scope, $route, PersonService, QueryUtils) {
    $scope.currentNavItem = "users";
    var id = $route.current.params.id;
    var Endpoint = QueryUtils.endpoint('/persons');
    $scope.auth = $route.current.locals.auth;

    var afterLoad = function () {
      PersonService.usersAfterLoad($scope.person.users);
      $scope.users = $scope.person.users;
    };

    $scope.orderValue = function (item) {
      return $scope.currentLanguageNameField(item.role);
    };

    $scope.person = Endpoint.get({id: id}, afterLoad);

    $scope.delete = PersonService.deletePerson;
  }])
  .controller('PersonSearchController', ['$q', '$route', '$scope', 'Classifier', 'QueryUtils',
    function ($q, $route, $scope, Classifier, QueryUtils) {
      $scope.currentNavItem = "users";
      $scope.auth = $route.current.locals.auth;
      // Endpoint only for school admin.
      $scope.userRoles = $scope.auth.isAdmin() ? QueryUtils.endpoint("/users/userSchoolRoles").query() : {};
      $scope.filterValues = [];
      if (!$scope.auth.isMainAdmin()) {
        $scope.filterValues.push('ROLL_X');
      }

      var clMapping = $route.current.locals.clMapping;
      var clMapper = clMapping ? Classifier.valuemapper(clMapping) : undefined;
      QueryUtils.createQueryForm($scope, $route.current.locals.url, $route.current.locals.params, clMapper ? clMapper.objectmapper : undefined);

      var _loadData = $scope.loadData;
      $scope.loadData = function () {
        if ($scope.criteria.userRole) {
          var occupation = ($scope.userRoles || []).find(function (r) {
            return r.id === $scope.criteria.userRole && !r.created;
          });
          $scope.criteria.occupation = occupation ? occupation.id : undefined;
        } else {
          $scope.criteria.occupation = undefined; // Reset in case if it was set before
        }
        _loadData();
      };

      if(clMapper) {
        $q.all(clMapper.promises, $scope.userRoles.$promise).then($scope.loadData);
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
  ])
  .factory('PersonService', ['$rootScope', '$location', 'Classifier', 'DataUtils', 'dialogService', 'message',
  function ($rootScope, $location, Classifier, DataUtils, dialogService, message) {
    var clMapper = Classifier.valuemapper({role: "ROLL"});

    var baseUrl = '/persons';

    function deletePerson(person) {
      dialogService.confirmDialog({prompt: 'person.deleteconfirm'}, function () {
        person.$delete().then(function () {
          message.info('main.messages.delete.success');
          $location.url(baseUrl + '?_noback');
        });
      });
    }

    function usersAfterLoad(users) {
      if (users) {
        clMapper.objectmapper(users);
      }
      DataUtils.convertStringToDates(users, ['validThru', 'validFrom']);

      var now = moment();
      for (var i = 0; i < users.length; i++) {
        var row = users[i];
        row.valid = (!row.validFrom || moment(row.validFrom).isSameOrBefore(now, 'day')) && (!row.validThru || moment(row.validThru).isSameOrAfter(now, 'day'));
      }
    }

    return {usersAfterLoad: usersAfterLoad, deletePerson: deletePerson};
  }
]);
