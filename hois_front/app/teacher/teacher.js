'use strict';

angular.module('hitsaOis').controller('TeacherEditController', ['$scope', '$route', '$location','message', 'DataUtils', 'dialogService', 'QueryUtils', function ($scope, $route, $location, message, DataUtils, dialogService,QueryUtils) {
  var id = $route.current.params.id;
  var Endpoint = QueryUtils.endpoint('/teachers');

  QueryUtils.endpoint('/school/teacheroccupations/all').query().$promise.then(function (response) {
    $scope.occupations = response;
  });

  function afterLoad() {
    if (!$scope.teacher.person.sex && ($scope.teacher.person.idcode && $scope.teacher.person.idcode.length === 11)) {
      $scope.teacher.person.sex = DataUtils.sexFromIdcode($scope.teacher.person.idcode);
    }
    if (!$scope.teacher.person.birthdate && ($scope.teacher.person.idcode && $scope.teacher.person.idcode.length === 11)) {
      $scope.teacher.person.birthdate = DataUtils.birthdayFromIdcode($scope.teacher.person.idcode);
    }
    DataUtils.convertStringToDates($scope.teacher.person, ['birthdate']);
    $scope.teacher.teacherPositionEhis.forEach(function (it) {
      DataUtils.convertStringToDates(it, ['contractStart', 'contractEnd']);
    });
    $scope.teacher.teacherMobility.forEach(function (it) {
      DataUtils.convertStringToDates(it, ['start', 'end']);
    });
    $scope.formState = {person: true};
  }

  $scope.lookupFailure = function() {
    if (!$scope.teacher.person.sex) {
      $scope.teacher.person.sex = DataUtils.sexFromIdcode($scope.teacher.person.idcode);
    }
    if (!$scope.teacher.person.birthdate) {
      $scope.teacher.person.birthdate = DataUtils.birthdayFromIdcode($scope.teacher.person.idcode);
    }
    $scope.formState = {person: false};
  };

  if(id) {
    $scope.teacher = Endpoint.get({id: id}, afterLoad);
  } else {
    $scope.teacher = new Endpoint();
    $scope.teacher.person = {citizenship: 'RIIK_EST'};
  }

  if (!angular.isArray($scope.teacher.teacherPositionEhis)) {
    $scope.teacher.teacherPositionEhis = [];
  }

  if (!angular.isArray($scope.teacher.teacherQualifications)) {
    $scope.teacher.teacherQualifications = [];
  }

  if (!angular.isArray($scope.teacher.teacherMobility)) {
    $scope.teacher.teacherMobility = [];
  }

  $scope.newTeacherPositionEhis = function () {
    $scope.teacher.teacherPositionEhis.push({
      isVocational: true,
      language: 'EHIS_OPETAJA_KEEL_E'
    });
  };

  $scope.newTeacherQualification = function () {
    $scope.teacher.teacherQualifications.push({});
  };

  $scope.newTeacherMobility = function () {
    $scope.teacher.teacherMobility.push({});
  };

  $scope.maxDate = new Date();

  $scope.removeFromCollection = function (collection, item) {
    $scope.teacher[collection] = $scope.teacher[collection].filter(function (it) {
      return it !== item;
    });
  };

  $scope.cleanReadOnly = function () {
    var idcode = $scope.teacher.person.idcode;
    if ($scope.formState.person && !(idcode && idcode.length === 11)) {
      $scope.formState = {person: false};
      $scope.teacher.person.citizenship = null;
      $scope.teacher.person.firstname = '';
      $scope.teacher.person.lastname = '';
      $scope.teacher.person.nativeLanguage = '';
      $scope.teacher.person.birthdate = null;
      $scope.teacher.person.sex = null;
    }
  };

  $scope.lookupPerson = function (response) {

    $scope.teacher.person.citizenship = response.citizenship;
    $scope.teacher.person.firstname = response.firstname;
    $scope.teacher.person.lastname = response.lastname;
    $scope.teacher.person.nativeLanguage = response.nativeLanguage;
    $scope.teacher.person.birthdate = response.birthdate;
    $scope.teacher.person.sex = response.sex;

    afterLoad();
  };

  $scope.delete = function () {
    dialogService.confirmDialog({prompt: 'teacher.deleteConfirm'}, function() {
      $scope.teacher.$delete().then(function () {
        message.info('main.messages.delete.success');
        $location.path('/teachers');
      });
    });
  };

  $scope.update = function() {
    $scope.teacherForm.$setSubmitted();
    var errors = false;
    if ($scope.teacher.teacherPositionEhis.length === 0) {
      message.error('teacher.teacherPositionEhis.error');
      errors = true;
    }
    console.log($scope.teacherForm.$valid);
    if($scope.teacherForm.$valid && !errors) {
      if($scope.teacher.id) {
        $scope.teacher.$update().then(function() {
          message.info('main.messages.update.success');
          afterLoad();
        });
      } else {
        $scope.teacher.$save().then(function(response) {
          $location.path('/teachers/'+ response.id +'/edit');
          message.info('main.messages.create.success');
        });
      }
    }
  };
}]).controller('TeacherListController', ['$scope', 'QueryUtils', function ($scope, QueryUtils) {
  QueryUtils.createQueryForm($scope, '/teachers', {order: 'person.lastname,person.firstname'});

  $scope.loadData();

}]);

