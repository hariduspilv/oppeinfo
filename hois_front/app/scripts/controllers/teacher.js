'use strict';

angular.module('hitsaOis').config(function ($routeProvider, USER_ROLES) {
  var authorizedRoles = {authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]};

  $routeProvider
    .when('/teachers', {
      templateUrl: 'views/teacher.list.html',
      controller: 'TeacherListController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
      data: authorizedRoles
    })
    .when('/teachers/new', {
      templateUrl: 'views/teacher.edit.html',
      controller: 'TeacherEditController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
      data: authorizedRoles
    })
    .when('/teachers/:id/edit', {
      templateUrl: 'views/teacher.edit.html',
      controller: 'TeacherEditController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
      data: authorizedRoles
    });
}).controller('TeacherEditController', ['$scope', '$route', '$location','message','DataUtils','QueryUtils', function ($scope, $route, $location, message, DataUtils, QueryUtils) {
  var id = $route.current.params.id;
  var Endpoint = QueryUtils.endpoint('/teachers');

  QueryUtils.endpoint('/school/teacheroccupations/all').query().$promise.then(function (response) {
    $scope.occupations = response;
  });

  function afterLoad() {
    DataUtils.convertStringToDates($scope.teacher.person, ['birthdate']);
    $scope.teacher.teacherPositionEhis.forEach(function (it) {
      DataUtils.convertStringToDates(it, ['contractStart', 'contractEnd']);
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

  $scope.newTeacherPositionEhis = function () {
    $scope.teacher.teacherPositionEhis.push({
      isVocational: true,
      language: 'EHIS_OPETAJA_KEEL_E'
    });
  };

  $scope.maxDate = new Date();

  $scope.remove = function(position) {
    $scope.teacher.teacherPositionEhis = $scope.teacher.teacherPositionEhis.filter(function (item) {
      return item !== position;
    });
  };

  $scope.lookupPerson = function (response) {
    console.log(response);

    $scope.teacher.person.citizenship = response.citizenship;
    $scope.teacher.person.firstname = response.firstname;
    $scope.teacher.person.lastname = response.lastname;
    $scope.teacher.person.nativeLanguage = response.nativeLanguage;
    $scope.teacher.person.birthdate = response.birthdate;
    $scope.teacher.person.sex = response.sex;

    afterLoad();
  };

  $scope.update = function() {
    $scope.teacherForm.$setSubmitted();

    //console.log($scope.teacherPositionEhis);
    console.log($scope.teacherForm.$valid);
    if($scope.teacherForm.$valid) {
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

