'use strict';

angular.module('hitsaOis').controller('UsersSearchController', ['$scope', '$route','Classifier', 'QueryUtils', function ($scope, $route, Classifier, QueryUtils) {
  var clMapper = Classifier.valuemapper({role: 'ROLL'});
  QueryUtils.createQueryForm($scope, '/users', {order: 'p.lastname,p.firstname'}, clMapper.objectmapper);

  $scope.showSchool = $route.current.locals.auth.isMainAdmin();

  $scope.loadData();
}])
  .controller('PersonsEditController', ['$location', '$route', '$scope', '$rootScope', 'dialogService', 'DataUtils', 'message', 'PersonService', 'QueryUtils', function ($location, $route, $scope, $rootScope, dialogService, DataUtils, message, PersonService, QueryUtils) {
    var id = $route.current.params.id;
    var baseUrl = '/persons';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    $scope.showSchool = $route.current.locals.auth.isMainAdmin();
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
      $location.path(baseUrl + '/' + response.id + '/edit');
      message.info('person.exists');
    };

    function clearData() {
      $scope.person.firstname = '';
      $scope.person.lastname = '';
      $scope.person.email = '';
      $scope.person.phone = '';
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
      if (($scope.users && $scope.users.length > 0) || $scope.showSchool || !$scope.person.id) {
        if (!$scope.person.id) {
          $location.search('_noback', null);
          $location.path(baseUrl)
        } else {
          $rootScope.back('#' + baseUrl);
        }
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
            $location.path(baseUrl + '/' + response.id + '/edit');
            message.info('main.messages.create.success');
          });
        }
      }
    };

    $scope.delete = PersonService.deletePerson;
  }])
  .controller('PersonsViewController', ['$scope', '$route', 'PersonService','QueryUtils', function ($scope, $route, PersonService, QueryUtils) {
    var id = $route.current.params.id;
    var Endpoint = QueryUtils.endpoint('/persons');
    $scope.showSchool = $route.current.locals.auth.isMainAdmin();

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
  .factory('PersonService', ['$location', 'Classifier', 'DataUtils', 'dialogService', 'message', function ($location, Classifier, DataUtils, dialogService, message) {
    var clMapper = Classifier.valuemapper({role: "ROLL"});

    var baseUrl = '/persons';

    var deletePerson = function (person) {
      dialogService.confirmDialog({prompt: 'person.deleteconfirm'}, function () {
        person.$delete().then(function () {
          message.info('main.messages.delete.success');
          $location.path(baseUrl);
        });
      });
    };

    var usersAfterLoad = function(users) {
      if (users) {
        clMapper.objectmapper(users);
      }
      users.forEach(function (it) {
        DataUtils.convertStringToDates(it, ['validThru', 'validFrom']);
      });

      var now = new moment();
      for (var i = 0; i < users.length; i++) {
        var valid = true;
        var item = users[i];
        if (item.validThru !== null && moment(item.validThru).isBefore(now)) {
          valid = false;
        }
        if (item.validFrom !== null && moment(item.validFrom).isAfter(now)) {
          valid = false;
        }
        item.valid = valid;
      }
    };

    return {usersAfterLoad: usersAfterLoad, deletePerson: deletePerson};
  }]);
