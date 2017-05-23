'use strict';

angular.module('hitsaOis').controller('TeacherEditController', ['$scope', '$route', '$location', 'message', 'DataUtils', 'dialogService', 'QueryUtils', function ($scope, $route, $location, message, DataUtils, dialogService, QueryUtils) {
  var id = $route.current.params.id;
  var baseUrl = '/teachers';
  var Endpoint = QueryUtils.endpoint(baseUrl);
  var TeacherQualificationsEndpoint = QueryUtils.endpoint('/teachers/' + id + '/qualifications');
  var TeacherMobilityEndpoint = QueryUtils.endpoint('/teachers/' + id + '/mobilities');
  var TeacherPositionEhisEndpoint = QueryUtils.endpoint('/teachers/' + id + '/ehisPositions');

  QueryUtils.endpoint('/school/teacheroccupations/all').query().$promise.then(function (response) {
    $scope.occupations = response;
  });

  function updateQualifications(teacher) {
    if (teacher.teacherQualifications) {
      $scope.teacherQualifications = new TeacherQualificationsEndpoint({qualifications: teacher.teacherQualifications});
      teacher.teacherQualifications = undefined;
    }
  }

  function updateMobilities(teacher) {
    if (teacher.teacherMobility) {
      teacher.teacherMobility.forEach(function (it) {
        DataUtils.convertStringToDates(it, ['start', 'end']);
      });
      $scope.teacherMobility = new TeacherMobilityEndpoint({mobilities: teacher.teacherMobility});
      teacher.teacherMobility = undefined;
    }
  }

  function afterLoad() {
    if ($scope.teacher.person.idcode && $scope.teacher.person.idcode.length === 11) {
      $scope.teacher.person.sex = DataUtils.sexFromIdcode($scope.teacher.person.idcode);
    }
    if ($scope.teacher.person.idcode && $scope.teacher.person.idcode.length === 11) {
      $scope.teacher.person.birthdate = DataUtils.birthdayFromIdcode($scope.teacher.person.idcode);
    }
    $scope.teacher.teacherPositionEhis.forEach(function (it) {
      DataUtils.convertStringToDates(it, ['contractStart', 'contractEnd']);
    });
    $scope.isHigher = $scope.teacher.isHigher;
    DataUtils.convertStringToDates($scope.teacher.person, ['birthdate']);
    updateQualifications($scope.teacher);
    updateMobilities($scope.teacher);
    $scope.displaySendEhis = ($scope.teacher.teacherPositionEhis.filter(function (it) {
      return it.id > 0;
    }).length > 0);
    $scope.formState = {
      person: true,
      id: true
    };
  }

  $scope.lookupFailure = function () {
    $scope.cleanReadOnly();
    $scope.formState = {
      person: false,
      id: true
    };
    $scope.teacher.person.sex = DataUtils.sexFromIdcode($scope.teacher.person.idcode);
    $scope.teacher.person.birthdate = DataUtils.birthdayFromIdcode($scope.teacher.person.idcode);
  };

  if (id) {
    $scope.teacher = Endpoint.get({id: id}, afterLoad);
  } else {
    $scope.teacher = new Endpoint({isActive: true});
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
    $scope.formState = {
      person: false,
      id:false
    };
    $scope.teacher.person.citizenship = 'RIIK_EST';
    $scope.teacher.person.firstname = '';
    $scope.teacher.person.lastname = '';
    $scope.teacher.person.nativeLanguage = '';
    $scope.teacher.person.birthdate = null;
    $scope.teacher.person.sex = null;

    if ($scope.oldFoundPerson && $scope.oldFoundPerson.idcode &&
          ($scope.oldFoundPerson.idcode === $scope.teacher.person.idcode)) {
      $scope.lookupPerson($scope.oldFoundPerson);
    }
  };

  $scope.lookupPerson = function (response) {
    $scope.oldFoundPerson = response;
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
    if ($scope.teacherForm.$valid) {
      if ($scope.teacher.id) {
        $scope.teacher.$update().then(afterLoad).then(message.updateSuccess);
      } else {
        $scope.teacher.$save().then(function (response) {
          message.info('main.messages.create.success');
          $location.path(baseUrl + '/' + response.id + '/edit');
        });
      }
    } else {
        message.error('main.messages.form-has-errors');
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

  $scope.deleteQualification = function (qualification) {
    dialogService.confirmDialog({prompt: 'teacher.qualification.deleteConfirm'}, function () {
      if (qualification.id) {
        qualification = new TeacherQualificationsEndpoint(qualification);
        qualification.$delete().then(function () {
          message.info('main.messages.delete.success');
          $scope.teacherQualifications.qualifications = $scope.teacherQualifications.qualifications.filter(function (it) {
            return it.id !== qualification.id;
          });
        });
      } else {
        $scope.removeFromCollection($scope.teacherQualifications, 'qualifications', qualification);
      }
    });
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

  $scope.deleteMobility = function (mobility) {
    dialogService.confirmDialog({prompt: 'teacher.mobility.deleteConfirm'}, function () {
      if (mobility.id) {
        mobility = new TeacherMobilityEndpoint(mobility);
        mobility.$delete().then(function () {
          message.info('main.messages.delete.success');
          $scope.teacherMobility.mobilities = $scope.teacherMobility.mobilities.filter(function (it) {
            return it.id !== mobility.id;
          });
        });
      } else {
        $scope.removeFromCollection($scope.teacherMobility, 'mobilities', mobility);
      }
    });
  };


  $scope.deleteEhisPosition = function (ehisPosition) {
    dialogService.confirmDialog({prompt: 'teacher.teacherPositionEhis.deleteConfirm'}, function () {
      if (ehisPosition.id) {
        ehisPosition = new TeacherPositionEhisEndpoint(ehisPosition);
        ehisPosition.$delete().then(function () {
          message.info('main.messages.delete.success');
          $scope.teacher.teacherPositionEhis = $scope.teacher.teacherPositionEhis.filter(function (it) {
            return it.id !== ehisPosition.id;
          });
        });
      } else {
        $scope.removeFromCollection($scope.teacher, 'teacherPositionEhis', ehisPosition);
      }
    });
  };
}]).controller('TeacherListController', ['$scope', '$route', 'QueryUtils', 'ArrayUtils', function ($scope, $route, QueryUtils, ArrayUtils) {
  QueryUtils.createQueryForm($scope, '/teachers', {order: 'person.lastname,person.firstname'});

  $scope.showSchool = $route.current.locals.auth.isExternalExpert();
  $scope.schoolHigher = $route.current.locals.auth.school  && $route.current.locals.auth.school.higher;

    //TODO: external expert has no school!
    QueryUtils.endpoint('/teachers/teacheroccupations').query(function(result) {
        $scope.teacherOccupations = result;
    });

  $scope.removeDuplicates = ArrayUtils.removeDuplicates;

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

