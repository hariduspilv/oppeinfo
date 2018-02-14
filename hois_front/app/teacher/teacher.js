(function () {
'use strict';

  function setIsVocationalOrIsHigher(scope, translate) {
    scope.vocationalHigher = '';
    var array = [];
    if(scope.teacher.isVocational) {
      array.push('teacher.isVocational');
    }
    if(scope.teacher.isHigher) {
      array.push('teacher.isHigher');
    }
    translate(array).then(function (value) {
      scope.vocationalHigher = Object.keys(value).map(function (key) {
        return value[key];
      }).join('; ');
    });
  }

  function removeFromCollection(wrapper, collection, item) {
    wrapper[collection] = wrapper[collection].filter(function (it) {
      return it !== item;
    });
  }

  angular.module('hitsaOis').controller('TeacherEditController', ['$location', '$route', '$translate', '$scope', 'dialogService', 'message', 'DataUtils', 'QueryUtils',
    function ($location, $route, $translate, $scope, dialogService, message, DataUtils, QueryUtils) {
      var id = $route.current.params.id;
      var baseUrl = '/teachers';
      var Endpoint = QueryUtils.endpoint(baseUrl);
      var TeacherPositionEhisEndpoint = QueryUtils.endpoint('/teachers/' + id + '/ehisPositions');
      var EmailGeneratorEndpoint = QueryUtils.endpoint('/school/generateEmail', {post: {method: 'POST'}});

      var school = $route.current.locals.auth.school;
      var onlyHigherSchool = school && school.higher && !school.vocational;
      $scope.occupations = QueryUtils.endpoint('/school/teacheroccupations/all').query();

      function afterLoad() {
        setIsVocationalOrIsHigher($scope, $translate);
        $scope.currentNavItem = 'teacher.data';

        if ($scope.teacher.person.idcode && $scope.teacher.person.idcode.length === 11) {
          $scope.teacher.person.sex = DataUtils.sexFromIdcode($scope.teacher.person.idcode);
        }
        if ($scope.teacher.person.idcode && $scope.teacher.person.idcode.length === 11) {
          $scope.teacher.person.birthdate = DataUtils.birthdayFromIdcode($scope.teacher.person.idcode);
        }
        DataUtils.convertStringToDates($scope.teacher.teacherPositionEhis, ['contractStart', 'contractEnd']);
        $scope.isHigher = $scope.teacher.isHigher;
        DataUtils.convertStringToDates($scope.teacher.person, ['birthdate']);
        $scope.formState = {person: true, id: true};
      }

      $scope.lookupFailure = function () {
        $scope.cleanReadOnly();
        $scope.formState = {person: false, id: true};
        $scope.teacher.person.sex = DataUtils.sexFromIdcode($scope.teacher.person.idcode);
        $scope.teacher.person.birthdate = DataUtils.birthdayFromIdcode($scope.teacher.person.idcode);
        $scope.teacherForm.idcode.$setValidity('teacherIdCode', true);
      };

      if (id) {
        $scope.teacher = Endpoint.get({id: id}, afterLoad);
      } else {
        $scope.teacher = new Endpoint({isActive: true, isStudyPeriodScheduleLoad: true, person: {citizenship: 'RIIK_EST'}});
      }

      if (!angular.isArray($scope.teacher.teacherPositionEhis)) {
        $scope.teacher.teacherPositionEhis = [];
      }

      $scope.newTeacherPositionEhis = function () {
        $scope.teacher.teacherPositionEhis.push({
          isVocational: !onlyHigherSchool,
          isTeacher: onlyHigherSchool,
          language: 'EHIS_OPETAJA_KEEL_E'
        });
      };

      $scope.maxDate = new Date();

      $scope.cleanReadOnly = function () {
        $scope.formState = {person: false, id:false};

        $scope.teacher.person.citizenship = 'RIIK_EST';
        $scope.teacher.person.firstname = '';
        $scope.teacher.person.lastname = '';
        $scope.teacher.person.nativeLanguage = '';
        $scope.teacher.person.birthdate = null;
        $scope.teacher.person.sex = null;
        $scope.teacher.email = undefined;

        if ($scope.oldFoundPerson && $scope.oldFoundPerson.idcode &&
              ($scope.oldFoundPerson.idcode === $scope.teacher.person.idcode)) {
          $scope.lookupPerson($scope.oldFoundPerson);
        }
      };

      $scope.lookupPerson = function (response) {
        $scope.oldFoundPerson = response;
        if(response.citizenship) {
          $scope.teacher.person.citizenship = response.citizenship;
        }
        $scope.teacher.person.firstname = response.firstname;
        $scope.teacher.person.lastname = response.lastname;
        $scope.teacher.person.nativeLanguage = response.nativeLanguage;
        $scope.teacher.person.birthdate = response.birthdate;
        $scope.teacher.person.sex = response.sex;
        $scope.teacher.email = response.schoolEmail || undefined;

        $scope.teacherForm.idcode.$setValidity('teacherIdCode', response.teacherId === null);
        afterLoad();
        $scope.generateEmail();
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
        if (!$scope.teacherForm.$valid) {
          message.error('main.messages.form-has-errors');
          return;
        }
        if ($scope.teacher.id) {
          $scope.teacher.$update().then(afterLoad).then(message.updateSuccess);
        } else {
          $scope.teacher.$save().then(function (response) {
            message.info('main.messages.create.success');
            $location.url(baseUrl + '/' + response.id + '/edit?_noback');
          });
        }
      };

      $scope.sendToEhis = function() {
        $scope.teacherForm.$setSubmitted();
        if (!$scope.teacherForm.$valid) {
          message.error('main.messages.form-has-errors');
          return;
        }
        dialogService.confirmDialog({ prompt: 'teacher.ehisconfirm' }, function () {
          QueryUtils.endpoint(baseUrl + '/' + $scope.teacher.id + '/sendToEhis').put({}, $scope.teacher).$promise.then(function() {
            message.info('teacher.sentToEhis');
            $location.url(baseUrl + '/' + $scope.teacher.id + '/edit?_noback');
          });
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
            removeFromCollection($scope.teacher, 'teacherPositionEhis', ehisPosition);
          }
        });
      };

      var generatedEmail;
      $scope.generateEmail = function() {
        if(!$scope.teacher.id && (generatedEmail === $scope.teacher.email || $scope.teacher.email === undefined) && $scope.teacher.person && $scope.teacher.person.lastname) {
          EmailGeneratorEndpoint.post({firstname: $scope.teacher.person.firstname, lastname: $scope.teacher.person.lastname}).$promise.then(function(result) {
            $scope.teacher.email = generatedEmail = result.email;
          }, function() { /* ignore errors */ });
        }
      };
  }]).controller('TeacherContinuingEducationEditController', ['$location', '$route', '$translate', '$scope', 'dialogService', 'message', 'QueryUtils',
    function ($location, $route, $translate, $scope, dialogService, message, QueryUtils) {
      var id = $route.current.params.id;
      var Endpoint = QueryUtils.endpoint('/teachers');
      var TeacherContinuingEducationsEndpoint = QueryUtils.endpoint('/teachers/' + id + '/continuingEducations');

      function afterLoad() {
        setIsVocationalOrIsHigher($scope, $translate);
        $scope.currentNavItem = 'teacher.continuingEducation';

        $scope.isHigher = $scope.teacher.isHigher;
        updateContinuingEducations($scope.teacher);
      }

      if (id) {
        $scope.teacher = Endpoint.get({id: id}, afterLoad);
      } else {
        $scope.teacher = new Endpoint({isActive: true, isStudyPeriodScheduleLoad: true, person: {citizenship: 'RIIK_EST'}});
      }

      function updateContinuingEducations(teacher) {
        if (teacher.teacherContinuingEducations) {
          $scope.teacherContinuingEducations = new TeacherContinuingEducationsEndpoint({continuingEducations: teacher.teacherContinuingEducations});
          teacher.teacherContinuingEducations = undefined;
        }
      }

      if (!$scope.teacher.teacherContinuingEducations || !angular.isArray($scope.continuingEducations)) {
        $scope.teacher.teacherContinuingEducations = [];
        updateContinuingEducations($scope.teacher);
      }

      $scope.newTeacherContinuingEducation = function () {
        $scope.teacherContinuingEducations.continuingEducations.push({});
      };

      $scope.updateContinuingEducations = function () {
        $scope.continuingEducationsForm.$setSubmitted();
        if ($scope.continuingEducationsForm.$valid) {
          $scope.teacherContinuingEducations.$update().then(function (response) {
            message.updateSuccess();
            updateContinuingEducations(response);
          });
        }
      };

      $scope.deleteContinuingEducation = function (continuingEducation) {
        dialogService.confirmDialog({prompt: 'teacher.continuingEducation.deleteConfirm'}, function () {
          if (continuingEducation.id) {
            continuingEducation = new TeacherContinuingEducationsEndpoint(continuingEducation);
            continuingEducation.$delete().then(function () {
              message.info('main.messages.delete.success');
              $scope.teacherContinuingEducations.continuingEducations = $scope.teacherContinuingEducations.continuingEducations.filter(function (it) {
                return it.id !== continuingEducation.id;
              });
            });
          } else {
            removeFromCollection($scope.teacherContinuingEducations, 'continuingEducations', continuingEducation);
          }
        });
      };
  }]).controller('TeacherQualificationEditController', ['$location', '$route', '$translate', '$scope', 'dialogService', 'message', 'QueryUtils',
    function ($location, $route, $translate, $scope, dialogService, message, QueryUtils) {
      var id = $route.current.params.id;
      var Endpoint = QueryUtils.endpoint('/teachers');
      var TeacherQualificationsEndpoint = QueryUtils.endpoint('/teachers/' + id + '/qualifications');

      function afterLoad() {
        setIsVocationalOrIsHigher($scope, $translate);
        $scope.currentNavItem = 'teacher.qualification';

        $scope.isHigher = $scope.teacher.isHigher;
        updateQualifications($scope.teacher);
      }

      if (id) {
        $scope.teacher = Endpoint.get({id: id}, afterLoad);
      } else {
        $scope.teacher = new Endpoint({isActive: true, isStudyPeriodScheduleLoad: true, person: {citizenship: 'RIIK_EST'}});
      }

      function updateQualifications(teacher) {
        if (teacher.teacherQualifications) {
          $scope.teacherQualifications = new TeacherQualificationsEndpoint({qualifications: teacher.teacherQualifications});
          teacher.teacherQualifications = undefined;
        }
      }

      if (!$scope.teacher.teacherQualifications || !angular.isArray($scope.qualifications)) {
        $scope.teacher.teacherQualifications = [];
        updateQualifications($scope.teacher);
      }

      $scope.newTeacherQualification = function () {
        $scope.teacherQualifications.qualifications.push({});
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
            removeFromCollection($scope.teacherQualifications, 'qualifications', qualification);
          }
        });
      };
  }]).controller('TeacherMobilityEditController', ['$location', '$route', '$translate', '$scope', 'dialogService', 'message', 'DataUtils', 'QueryUtils',
    function ($location, $route, $translate, $scope, dialogService, message, DataUtils, QueryUtils) {
      var id = $route.current.params.id;
      var Endpoint = QueryUtils.endpoint('/teachers');
      var TeacherMobilityEndpoint = QueryUtils.endpoint('/teachers/' + id + '/mobilities');

      function afterLoad() {
        setIsVocationalOrIsHigher($scope, $translate);
        $scope.currentNavItem = 'teacher.mobility';

        $scope.isHigher = $scope.teacher.isHigher;
        updateMobilities($scope.teacher);
      }

      if (id) {
        $scope.teacher = Endpoint.get({id: id}, afterLoad);
      } else {
        $scope.teacher = new Endpoint({isActive: true, isStudyPeriodScheduleLoad: true, person: {citizenship: 'RIIK_EST'}});
      }

      function updateMobilities(teacher) {
        if (teacher.teacherMobility) {
          DataUtils.convertStringToDates(teacher.teacherMobility, ['start', 'end']);

          $scope.teacherMobility = new TeacherMobilityEndpoint({mobilities: teacher.teacherMobility});
          teacher.teacherMobility = undefined;
        }
      }

      if (!$scope.teacher.teacherMobility || !angular.isArray($scope.mobilities)) {
        $scope.teacher.teacherMobility = [];
        updateMobilities($scope.teacher);
      }

      $scope.newTeacherMobility = function () {
        $scope.teacherMobility.mobilities.push({});
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
            removeFromCollection($scope.teacherMobility, 'mobilities', mobility);
          }
        });
      };
  }]).controller('TeacherListController', ['$scope', '$route', 'QueryUtils', 'USER_ROLES',
  function ($scope, $route, QueryUtils, USER_ROLES) {
    QueryUtils.createQueryForm($scope, '/teachers', {order: 'p.lastname,p.firstname'});

    $scope.auth = $route.current.locals.auth;
    $scope.showSchool = $scope.auth.isExternalExpert();
    $scope.schoolHigher = $scope.auth.school  && $scope.auth.school.higher;

    if(!$scope.auth.isExternalExpert()) {
      $scope.teacherOccupations = QueryUtils.endpoint('/teachers/teacheroccupations').query();
    }

    $scope.formState = {
      canCreate: $scope.auth.isAdmin() && $scope.auth.authorizedRoles.indexOf(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPETAJA) !== -1
    };

    $scope.loadData();
  }]).controller('TeacherViewController', ['$scope', '$route', '$translate', 'QueryUtils', function ($scope, $route, $translate, QueryUtils) {
    $scope.isShowingRtipTab = false;
    $scope.auth = $route.current.locals.auth;
    var auth = $route.current.locals.auth;

    var id = (auth.isTeacher() ? auth.teacher : $route.current.params.id);

    //var id = $route.current.params.id;
    $scope.teacherId = id;
    var Endpoint = QueryUtils.endpoint('/teachers');

    function setIsVocationalOrIsHigher($scope, $translate) {
      $scope.vocationalHigher = '';
      var array = [];
      if($scope.teacher.isVocational) {
        array.push('teacher.isVocational');
      }
      if($scope.teacher.isHigher) {
        array.push('teacher.isHigher');
      }
      $translate(array).then(function (value) {
        $scope.vocationalHigher = Object.keys(value).map(function (key) {
          return value[key];
        }).join('; ');
      });
    }

    function afterLoad() {
      $scope.teacher.scheduleLoad = angular.isNumber($scope.teacher.scheduleLoad) ? String($scope.teacher.scheduleLoad) : '';

      setIsVocationalOrIsHigher($scope, $translate);

      $scope.teacher.teacherPositionEhis.forEach(function (it) {
        it.load = angular.isNumber(it.load) ? String(it.load) : '';
      });
      $scope.teacher.teacherQualifications.forEach(function (it) {
        it.year = angular.isNumber(it.year) ? String(it.year) : '';
      });
    }

    QueryUtils.endpoint('/autocomplete/schooldepartments').query(function(result) {
      $scope.schoolDepartmentsById = result.reduce(function(map, it) {
        map[it.id] = it;
        return map;
      }, {});
    });

    $scope.teacher = Endpoint.get({id: id}, afterLoad);

    QueryUtils.createQueryForm($scope, '/teachers/' + id + '/absences');
    $scope.criteria.order = '-startDate';
    $scope.afterLoadData = function (resultData) {
      $scope.tabledata.content = resultData.content;
      $scope.tabledata.totalElements = resultData.totalElements;
    };

    $scope.changeIsShowingRtipTab = function () {
      $scope.isShowingRtipTab = !$scope.isShowingRtipTab;
      if ($scope.isShowingRtipTab) {
        $scope.loadData();
      }
    };

    $scope.updateData = function () {
      QueryUtils.endpoint('/teachers/' + id + '/rtip').save();
      $scope.loadData();
    };
    
  }]).controller('TeacherRtipAbsenceEditController', ['$scope', '$route', '$translate', 'QueryUtils', function ($scope, $route, $translate, QueryUtils) {
    var id = $route.current.params.id;
    var Endpoint = QueryUtils.endpoint('/teachers');
    $scope.rtipPage = true;

    function afterLoad() {
      setIsVocationalOrIsHigher($scope, $translate);
      $scope.currentNavItem = 'teacher.rtipAbsence';

      $scope.isHigher = $scope.teacher.isHigher;
      $scope.loadData();
    }

    if (id) {
      $scope.teacher = Endpoint.get({id: id}, afterLoad);
    } else {
      $scope.teacher = new Endpoint({isActive: true, isStudyPeriodScheduleLoad: true, person: {citizenship: 'RIIK_EST'}});
    }

    QueryUtils.createQueryForm($scope, '/teachers/' + id + '/absences');
    $scope.criteria.order = '-startDate';
    $scope.afterLoadData = function (resultData) {
      $scope.tabledata.content = resultData.content;
      $scope.tabledata.totalElements = resultData.totalElements;
    };

    $scope.updateData = function () {
      QueryUtils.endpoint('/teachers/' + id + '/rtip').save();
      $scope.loadData();
    };

  }]);
}());
