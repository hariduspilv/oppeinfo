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
      scope.vocationalHigher = array.map(function (key) { return value[key]; }).join('; ');
    });
  }

  function removeFromCollection(wrapper, collection, item) {
    wrapper[collection] = wrapper[collection].filter(function (it) {
      return it !== item;
    });
  }

  angular.module('hitsaOis').controller('TeacherEditController', ['$location', '$route', '$scope', '$translate', 'dialogService', 'message', 'DataUtils', 'FormUtils', 'QueryUtils',
    function ($location, $route, $scope, $translate, dialogService, message, DataUtils, FormUtils, QueryUtils) {
      $scope.auth = $route.current.locals.auth;
      var id = $route.current.params.id;
      var baseUrl = '/teachers';
      var Endpoint = QueryUtils.endpoint(baseUrl);
      var teacherSavedOccupationId;
      var TeacherPositionEhisEndpoint = QueryUtils.endpoint('/teachers/' + id + '/ehisPositions');
      var EmailGeneratorEndpoint = QueryUtils.endpoint('/school/generateEmail', {post: {method: 'POST'}});

      var school = $scope.auth.school;
      var onlyHigherSchool = school && school.higher && !school.vocational;
      $scope.occupations = QueryUtils.endpoint('/school/teacheroccupations/all').query();
      $scope.orderOccupations = function (occupation) {
        if ($scope.currentLanguage() === 'en') {
          return occupation.occupationEn || occupation.occupationEt;
        }
        return occupation.occupationEt;
      };
      $scope.filterOccupations = function (occupation) {
        if (teacherSavedOccupationId === occupation.id) {
          return true;
        }
        return occupation.isValid;
      };

      function afterLoad() {
        setIsVocationalOrIsHigher($scope, $translate);
        $scope.currentNavItem = 'teacher.data';

        if ($scope.teacher.person.idcode && $scope.teacher.person.idcode.length === 11) {
          $scope.teacher.person.sex = DataUtils.sexFromIdcode($scope.teacher.person.idcode);
          $scope.teacher.person.birthdate = DataUtils.birthdayFromIdcode($scope.teacher.person.idcode);
        }
        DataUtils.convertStringToDates($scope.teacher.teacherPositionEhis, ['contractStart', 'contractEnd']);
        $scope.isHigher = $scope.teacher.isHigher;
        DataUtils.convertStringToDates($scope.teacher.person, ['birthdate']);
        $scope.formState = {person: true, id: true};
        teacherSavedOccupationId = $scope.teacher.teacherOccupation.id;
      }

      $scope.lookupFailure = function () {
        $scope.cleanReadOnly();
        $scope.formState = {person: false, id: true};
        $scope.teacher.person.sex = DataUtils.sexFromIdcode($scope.teacher.person.idcode);
        $scope.teacher.person.birthdate = DataUtils.birthdayFromIdcode($scope.teacher.person.idcode);
        $scope.teacherForm.idcode.$setValidity('teacherIdCode', true);
      };

      function loadTeacher (id) {
        if (angular.isDefined(id) && id !== null) {
          $scope.teacher = Endpoint.get({id: id}, afterLoad);
        } else {
          $scope.teacher = new Endpoint({isActive: true, isStudyPeriodScheduleLoad: true, scheduleLoad: 0, person: {citizenship: 'RIIK_EST'}});
        }
      }

      loadTeacher(id);

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
        FormUtils.deleteRecord($scope.teacher, baseUrl + '?_noback',
          {prompt: $scope.auth.higher ? 'teacher.deleteConfirmHigher' : 'teacher.deleteConfirmVocational'});
      };

      $scope.update = function () {
        FormUtils.withValidForm($scope.teacherForm, function() {
          if ($scope.teacher.id) {
            $scope.teacher.$update().then(function() {
              message.updateSuccess();
              if($scope.auth.isTeacher()) {
                $location.url(baseUrl + '/myData?_noback');
              } else {
                afterLoad();
                $scope.teacherForm.$setPristine();
              }
            }).catch(angular.noop);
          } else {
            $scope.teacher.$save().then(function (response) {
              message.info('main.messages.create.success');
              $location.url(baseUrl + '/' + response.id + '/edit?_noback');
            }).catch(angular.noop);
          }
        });
      };

      $scope.sendToEhis = function() {
        FormUtils.withValidForm($scope.teacherForm, function() {
          dialogService.confirmDialog({ prompt:  $scope.auth.higher ? 'teacher.ehisConfirmHigher' : 'teacher.ehisConfirmVocational' }, function () {
            QueryUtils.endpoint(baseUrl + '/' + $scope.teacher.id + '/sendToEhis').put({}, $scope.teacher).$promise.then(function() {
              message.info($scope.auth.higher ? 'teacher.sentToEhisHigher' : 'teacher.sentToEhisVocational');
              $location.url(baseUrl + '/' + $scope.teacher.id + '/edit?_noback'); // Didn't work for reloading for some reason. Added loadTeacher function to update data.
              loadTeacher($scope.teacher.id);
            }).catch(function () {
              $scope.teacher.ehisLastSuccessfulVDate = null;
              $scope.teacher.ehisLastSuccessfulHDate = null;
            });
          });
        });
      };

      $scope.deleteEhisPosition = function (ehisPosition) {
        dialogService.confirmDialog(
          {prompt: $scope.auth.higher ? 'teacher.teacherPositionEhis.deleteConfirmHigher' : 'teacher.teacherPositionEhis.deleteConfirmVocational'}, function () {
          if (ehisPosition.id) {
            ehisPosition = new TeacherPositionEhisEndpoint(ehisPosition);
            ehisPosition.$delete().then(function () {
              message.info('main.messages.delete.success');
              $scope.teacher.teacherPositionEhis = $scope.teacher.teacherPositionEhis.filter(function (it) {
                return it.id !== ehisPosition.id;
              });
            }).catch(angular.noop);
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
          }).catch(angular.noop);
        }
      };
  }]).controller('TeacherContinuingEducationEditController', ['$location', '$route', '$translate', '$scope', 'dialogService', 'message', 'QueryUtils',
    function ($location, $route, $translate, $scope, dialogService, message, QueryUtils) {
      $scope.auth = $route.current.locals.auth;
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
            $scope.continuingEducationsForm.$setPristine();
          }).catch(angular.noop);
        }
      };

      $scope.deleteContinuingEducation = function (continuingEducation) {
        dialogService.confirmDialog(
          {prompt: $scope.auth.higher ? 'teacher.continuingEducation.deleteConfirmHigher' : 'teacher.continuingEducation.deleteConfirmVocational'}, function () {
          if (continuingEducation.id) {
            continuingEducation = new TeacherContinuingEducationsEndpoint(continuingEducation);
            continuingEducation.$delete().then(function () {
              message.info('main.messages.delete.success');
              $scope.teacherContinuingEducations.continuingEducations = $scope.teacherContinuingEducations.continuingEducations.filter(function (it) {
                return it.id !== continuingEducation.id;
              });
            }).catch(angular.noop);
          } else {
            removeFromCollection($scope.teacherContinuingEducations, 'continuingEducations', continuingEducation);
          }
        });
      };
  }]).controller('TeacherQualificationEditController', ['$location', '$route', '$translate', '$scope', 'dialogService', 'message', 'QueryUtils',
    function ($location, $route, $translate, $scope, dialogService, message, QueryUtils) {
      $scope.auth = $route.current.locals.auth;
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
            $scope.qualificationsForm.$setPristine();
          }).catch(angular.noop);
        }
      };

      $scope.deleteQualification = function (qualification) {
        dialogService.confirmDialog(
          {prompt: $scope.auth.higher ? 'teacher.qualification.deleteConfirmHigher' : 'teacher.qualification.deleteConfirmVocational'}, function () {
          if (qualification.id) {
            qualification = new TeacherQualificationsEndpoint(qualification);
            qualification.$delete().then(function () {
              message.info('main.messages.delete.success');
              $scope.teacherQualifications.qualifications = $scope.teacherQualifications.qualifications.filter(function (it) {
                return it.id !== qualification.id;
              });
            }).catch(angular.noop);
          } else {
            removeFromCollection($scope.teacherQualifications, 'qualifications', qualification);
          }
        });
      };
  }]).controller('TeacherMobilityEditController', ['$location', '$route', '$translate', '$scope', 'dialogService', 'message', 'DataUtils', 'QueryUtils',
    function ($location, $route, $translate, $scope, dialogService, message, DataUtils, QueryUtils) {
      $scope.auth = $route.current.locals.auth;
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
            $scope.mobilityForm.$setPristine();
          }).catch(angular.noop);
        }
      };

      $scope.deleteMobility = function (mobility) {
        dialogService.confirmDialog(
          {prompt: $scope.auth.higher ? 'teacher.mobility.deleteConfirmHigher' : 'teacher.mobility.deleteConfirmVocational'}, function () {
          if (mobility.id) {
            mobility = new TeacherMobilityEndpoint(mobility);
            mobility.$delete().then(function () {
              message.info('main.messages.delete.success');
              $scope.teacherMobility.mobilities = $scope.teacherMobility.mobilities.filter(function (it) {
                return it.id !== mobility.id;
              });
            }).catch(angular.noop);
          } else {
            removeFromCollection($scope.teacherMobility, 'mobilities', mobility);
          }
        });
      };
  }]).controller('TeacherListController', ['$scope', '$route', 'QueryUtils', 'USER_ROLES',
  function ($scope, $route, QueryUtils, USER_ROLES) {
    QueryUtils.createQueryForm($scope, '/teachers', {order: 'p.lastname,p.firstname', isActive: true});

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
  }]).controller('TeacherViewController', ['$scope', '$route', '$translate', 'QueryUtils', 'message', '$localStorage',
    function ($scope, $route, $translate, QueryUtils, message, $localStorage) {
    var TABS = Object.freeze({
      DATA: "edit",
      CONTINUING_EDUCATION: "continuingEducation",
      QUALIFICATION: "qualification",
      MOBILITY: "mobility",
      RTIP_ABSENCE: "rtipAbsence",
      SUBJECT_PROGRAMS: "programs",
      LOAD: "load",
    });
    var currentTab = TABS.DATA;
    $scope.selectedIndex = 0;
    $scope.isShowingRtipTab = false;
    $scope.customBack = customBack;
    $route.current.locals = angular.extend($route.current.locals || {}, {
      params: {
        myData: true
      }
    });
    $scope.showEdit = showEdit;
    $scope.showBottomButtons = showBottomButtons;
    var auth = $route.current.locals.auth;
    $scope.auth = auth;
    var id = (auth.isTeacher() ? auth.teacher : $route.current.params.id);
    $scope.initialProgramSearch = false;

    $scope.teacherId = id;
    var Endpoint = QueryUtils.endpoint('/teachers');

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
        changeTabByValue(TABS.RTIP_ABSENCE);
        $scope.loadData();
      }
    };

    $scope.updateData = function () {
      QueryUtils.endpoint('/teachers/' + id + '/rtip').save().$promise.then(function () {
        $scope.loadData();
        message.info("main.messages.dataUpdated");
      });
    };

    function changeTabByValue(val) {
      for (var key in TABS) {
        if (!TABS.hasOwnProperty(key)) {
          continue;
        }
        if (val === TABS[key]) {
          $scope.changeTab(key);
          break;
        }
      }
    }

    $scope.changeTab = function (tab) {
      if (typeof tab === 'string' && TABS[tab] !== undefined) {
        currentTab = TABS[tab];
        if ($scope.teacher.$resolved) {
          var navItemIndex = currentSchoolUsersResultsNavItemIndex();
          var localStorageNavItem = {school: auth.school.id, user: auth.user, teacher: id, navItemName: currentTab, currentIndex: $scope.selectedIndex};
          if (navItemIndex) {
            $localStorage.teacherCurrentNavItems[navItemIndex-1] = localStorageNavItem;
          } else {
            if (!$localStorage.teacherCurrentNavItems) {
              $localStorage.teacherCurrentNavItems = [];
            }
            $localStorage.teacherCurrentNavItems.push(localStorageNavItem);
          }
        }
        if (currentTab === TABS.SUBJECT_PROGRAMS) {
          // case if from history taken tab, then we need to pass parameter
          $scope.initialProgramSearch = true;
          $scope.$broadcast('searchPrograms');
        }
        if (currentTab === TABS.LOAD) {
          $scope.initLoad = true;
        }
      }
    };

    $scope.getUrl = function () {
      return "#/teachers/" + $scope.teacher.id + "/" + currentTab + "?_noback";
    };

    $scope.loadData(); // HITSAOIS-219. Fixes the jumping button
    // (problem with md-dynamic-height because after changing tab it starts to change height but data is not loaded yet)

    function currentSchoolUsersResultsNavItemIndex() {
      if ($localStorage.teacherCurrentNavItems) {
        for (var i = 0; i < $localStorage.teacherCurrentNavItems.length; i++) {
          var navItem = $localStorage.teacherCurrentNavItems[i];

          if (auth.school.id === navItem.school && auth.user === navItem.user && id === navItem.teacher) {
            return i + 1;
          }
        }
      }
      return null;
    }

    $scope.teacher.$promise.then(function () {
      if ($localStorage.teacherCurrentNavItems) {
        var navItemIndex = currentSchoolUsersResultsNavItemIndex();
        if (navItemIndex) {
          $scope.selectedIndex = $localStorage.teacherCurrentNavItems[navItemIndex-1].currentIndex;
          changeTabByValue($localStorage.teacherCurrentNavItems[navItemIndex-1].navItemName);
        } else {
          $scope.selectedIndex = 0;
          changeTabByValue(TABS.DATA);
        }
      } else {
        $scope.selectedIndex = 0;
        changeTabByValue(TABS.DATA);
      }
    });

    function customBack(url) {
      $localStorage.teacherCurrentNavItems = [];
      $scope.back(url);
    }

    var HIDE_EDIT = [TABS.RTIP_ABSENCE, TABS.SUBJECT_PROGRAMS];
    function showEdit() {
      return HIDE_EDIT.indexOf(currentTab) === -1;
    }

    var HIDE_BOTTOM_BUTTONS = [TABS.LOAD];
    function showBottomButtons() {
      return HIDE_BOTTOM_BUTTONS.indexOf(currentTab) === -1;
    }
  }]).controller('TeacherRtipAbsenceEditController', ['$scope', '$route', '$translate', 'QueryUtils', 'message', function ($scope, $route, $translate, QueryUtils, message) {
    $scope.auth = $route.current.locals.auth;
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
      QueryUtils.endpoint('/teachers/' + id + '/rtip').save().$promise.then(function () {
        $scope.loadData();
        message.info("main.messages.dataUpdated");
      });
    };

  }]).controller('TeacherProgramController', ['$scope', '$route', 'QueryUtils', '$translate', function ($scope, $route, QueryUtils, $translate) {
    var id = $route.current.params.id;
    var Endpoint = QueryUtils.endpoint('/teachers');

    $scope.initialProgramSearch = true;

    $route.current.locals = angular.extend($route.current.locals || {}, {
      params: {
        myData: true
      }
    });
    $scope.separateController = true;
    $scope.auth = $route.current.locals.auth;

    function afterLoad() {
      setIsVocationalOrIsHigher($scope, $translate);
      $scope.currentNavItem = 'teacher.programs';
    }

    if (id) {
      $scope.teacher = Endpoint.get({id: id}, afterLoad);
    } else {
      $scope.teacher = new Endpoint({isActive: true, isStudyPeriodScheduleLoad: true, person: {citizenship: 'RIIK_EST'}});
    }
  }]);
}());
