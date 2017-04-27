'use strict';

angular.module('hitsaOis').controller('TeacherEditController', ['$scope', '$route', '$location', 'message', 'DataUtils', 'dialogService', 'QueryUtils', function ($scope, $route, $location, message, DataUtils, dialogService, QueryUtils) {
  var id = $route.current.params.id;
  var baseUrl = '/teachers';
  var Endpoint = QueryUtils.endpoint(baseUrl);
  var TeacherQualificationsEndpoint = QueryUtils.endpoint('/teachers/' + id + '/qualifications');
  var TeacherMobilityEndpoint = QueryUtils.endpoint('/teachers/' + id + '/mobilities');

  QueryUtils.endpoint('/school/teacheroccupations/all').query().$promise.then(function (response) {
    $scope.occupations = response;
  });

  function updateQualifications(teacher) {
    teacher.teacherPositionEhis.forEach(function (it) {
      DataUtils.convertStringToDates(it, ['contractStart', 'contractEnd']);
    });
    $scope.teacherQualifications = new TeacherQualificationsEndpoint({qualifications: teacher.teacherQualifications});
    teacher.teacherQualifications = undefined;
  }

  function updateMobilities(teacher) {
    teacher.teacherMobility.forEach(function (it) {
      DataUtils.convertStringToDates(it, ['start', 'end']);
    });
    $scope.teacherMobility = new TeacherMobilityEndpoint({mobilities: teacher.teacherMobility});
    teacher.teacherMobility = undefined;
  }

  function afterLoad() {
    if (!$scope.teacher.person.sex && ($scope.teacher.person.idcode && $scope.teacher.person.idcode.length === 11)) {
      $scope.teacher.person.sex = DataUtils.sexFromIdcode($scope.teacher.person.idcode);
    }
    if (!$scope.teacher.person.birthdate && ($scope.teacher.person.idcode && $scope.teacher.person.idcode.length === 11)) {
      $scope.teacher.person.birthdate = DataUtils.birthdayFromIdcode($scope.teacher.person.idcode);
    }
    DataUtils.convertStringToDates($scope.teacher.person, ['birthdate']);
    updateQualifications($scope.teacher);
    updateMobilities($scope.teacher);
    $scope.formState = {person: true};
  }

  $scope.lookupFailure = function () {
    if (!$scope.teacher.person.sex) {
      $scope.teacher.person.sex = DataUtils.sexFromIdcode($scope.teacher.person.idcode);
    }
    if (!$scope.teacher.person.birthdate) {
      $scope.teacher.person.birthdate = DataUtils.birthdayFromIdcode($scope.teacher.person.idcode);
    }
    $scope.formState = {person: false};
  };

  if (id) {
    $scope.teacher = Endpoint.get({id: id}, afterLoad);
  } else {
    $scope.teacher = new Endpoint();
    $scope.teacher.isStudyPeriodScheduleLoad = true;
    $scope.teacher.person = {citizenship: 'RIIK_EST'};
  }

  if (!angular.isArray($scope.teacher.teacherPositionEhis)) {
    $scope.teacher.teacherPositionEhis = [];
  }

  if (!$scope.teacher.teacherQualifications || !angular.isArray($scope.qualifications)) {
    $scope.teacher.teacherQualifications = [];
    updateQualifications($scope.teacher);
  }

  if (!$scope.teacher.teacherMobility || !angular.isArray($scope.mobilities)) {
    $scope.teacher.teacherMobility = [];
    updateMobilities($scope.teacher);
  }

  $scope.newTeacherPositionEhis = function () {
    $scope.teacher.teacherPositionEhis.push({
      isVocational: true,
      language: 'EHIS_OPETAJA_KEEL_E'
    });
  };

  $scope.newTeacherQualification = function () {
    $scope.teacherQualifications.qualifications.push({});
  };

  $scope.newTeacherMobility = function () {
    $scope.teacherMobility.mobilities.push({});
  };

  $scope.maxDate = new Date();

  $scope.removeFromCollection = function (wrapper, collection, item) {
    wrapper[collection] = wrapper[collection].filter(function (it) {
      return it !== item;
    });
  };

  $scope.cleanReadOnly = function () {
    var idcode = $scope.teacher.person.idcode;
    if ($scope.formState && $scope.formState.person && !(idcode && idcode.length === 11)) {
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
    dialogService.confirmDialog({prompt: 'teacher.deleteConfirm'}, function () {
      $scope.teacher.$delete().then(function () {
        message.info('main.messages.delete.success');
        $location.path(baseUrl);
      });
    });
  };

  $scope.update = function () {
    $scope.teacherForm.$setSubmitted();
    var errors = false;
    if ($scope.teacher.teacherPositionEhis.length === 0) {
      message.error('teacher.teacherPositionEhis.error');
      errors = true;
    }
    if ($scope.teacherForm.$valid && !errors) {
      if ($scope.teacher.id) {
        $scope.teacher.$update().then(afterLoad).then(message.updateSuccess);
      } else {
        $scope.teacher.$save().then(function (response) {
          message.info('main.messages.create.success');
          $location.path(baseUrl + '/' + response.id + '/edit');
        });
      }
    }
  };

  $scope.updateQualifications = function () {
    $scope.qualificationsForm.$setSubmitted();
    if ($scope.qualificationsForm.$valid) {
      $scope.teacherQualifications.$update().then(function (response) {
        message.updateSuccess();
        updateQualifications(response);
      });
    }
  };

  $scope.updateTeacherMobility = function () {
    $scope.mobilityForm.$setSubmitted();
    if ($scope.mobilityForm.$valid) {
      $scope.teacherMobility.$update().then(function (response) {
        message.updateSuccess();
        updateMobilities(response);
      });
    }
  };
}]).controller('TeacherListController', ['$scope', '$route', 'QueryUtils', function ($scope, $route, QueryUtils) {
  QueryUtils.createQueryForm($scope, '/teachers', {order: 'person.lastname,person.firstname'});

  $scope.showSchool = $route.current.locals.auth.isExternalExpert();
  $scope.loadData();
}]).controller('TeacherViewController', ['$scope', '$route', '$translate', 'QueryUtils', function ($scope, $route, $translate, QueryUtils) {
  var id = $route.current.params.id;
  $scope.teacherId = id;
  var Endpoint = QueryUtils.endpoint('/teachers');

  function afterLoad() {
    $scope.teacher.scheduleLoad = angular.isNumber($scope.teacher.scheduleLoad) ? String($scope.teacher.scheduleLoad) : '';
    $scope.vocationalHigher = '';
    $translate(['teacher.isVocational', 'teacher.isHigher']).then(function (value) {
      $scope.vocationalHigher = Object.keys(value).map(function (key) {
        return value[key];
      }).join('; ');
    });
    $scope.teacher.teacherPositionEhis.forEach(function (it) {
      it.load = angular.isNumber(it.load) ? String(it.load) : '';
    });
    $scope.teacher.teacherQualifications.forEach(function (it) {
      it.year = angular.isNumber(it.year) ? String(it.year) : '';
    });
  }

  $scope.teacher = Endpoint.get({id: id}, afterLoad);
}]);

