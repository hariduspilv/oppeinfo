'use strict';

angular.module('hitsaOis').controller('PracticeEnterprisePersonsEditController', function ($scope, $route, QueryUtils, dialogService, message, FormUtils, Classifier, $q, $timeout) {
  $scope.currentNavItem = 'enterprise.persons';
  angular.element(document).ready(function() {
    $timeout(function() {
      var scrollAmount = document.getElementsByName($scope.currentNavItem)[0].offsetLeft;
      document.getElementById('scrolling_div').scrollLeft += scrollAmount;
    }, 0);
  });

  $scope.auth = $route.current.locals.auth;
  $scope.enterprise = {};
  $scope.enterprise.id = $route.current.params.id;
  $scope.enterprise.enterpriseSchoolId = $route.current.params.enterpriseSchoolId;
  $scope.enterprise.application = window.localStorage.getItem('enterpriseApplication');
  var EnterpriseEndpoint = QueryUtils.endpoint('/practiceEnterprise/' + $scope.enterprise.enterpriseSchoolId + '/persons');
  var clMapper = Classifier.valuemapper({
    country: 'RIIK'
  });

  var refresh = function() {
    QueryUtils.createQueryForm($scope, '/practiceEnterprise/' + $scope.enterprise.enterpriseSchoolId + '/persons', {}, clMapper.objectmapper);
    $q.all(clMapper.promises).then($scope.loadData);
  };

  refresh();

  $scope.viewPerson = function (row) {
    dialogService.showDialog('practiceEnterprise/practice.enterprise.person.view.dialog.html', function (dialogScope) {
      dialogScope.enterprise = row;
      dialogScope.enterprise.name = window.localStorage.getItem('enterpriseName');
      dialogScope.enterprise.regCode = window.localStorage.getItem('enterpriseRegCode');
    }, null);
  };

  $scope.addNewPerson = function (row) {
    dialogService.showDialog('practiceEnterprise/practice.enterprise.person.add.dialog.html', function (dialogScope) {
      dialogScope.delete = function () {
        dialogService.confirmDialog({ prompt: 'enterprise.personsTab.deleteConfirm' }, function () {
          var PersonEndpoint = QueryUtils.endpoint('/practiceEnterprise/person/' + row.id);
          var enterpriseLocation = new PersonEndpoint();
          enterpriseLocation.$delete().then(function () {
            message.info('main.messages.delete.success');
            refresh();
          }).catch(angular.noop);
        });
      };
      if (row) {
        dialogScope.enterprise = angular.copy(row);
        if (row.country !== undefined) {
          dialogScope.enterprise.country = row.country.code;
        }
        dialogScope.enterprise.name = window.localStorage.getItem('enterpriseName');
        dialogScope.enterprise.regCode = window.localStorage.getItem('enterpriseRegCode');
      } else {
        dialogScope.enterprise = {};
        dialogScope.enterprise.name = window.localStorage.getItem('enterpriseName');
        dialogScope.enterprise.regCode = window.localStorage.getItem('enterpriseRegCode');
        dialogScope.enterprise.supervisor = true;
      }
    }, function (submittedDialogScope) {
      FormUtils.withValidForm(submittedDialogScope.dialogForm, function() {
      if (row) {
        var PersonEndpoint = QueryUtils.endpoint('/practiceEnterprise/person/' + row.id);
        var personEndpoint = new PersonEndpoint(submittedDialogScope.enterprise);
        personEndpoint.$save().then(function () {
          message.info('main.messages.create.success');
          refresh();
        }).catch(angular.noop);
      } else {
        var enterpriseEndPoint = new EnterpriseEndpoint(submittedDialogScope.enterprise);
        enterpriseEndPoint.$save().then(function () {
          message.info('main.messages.create.success');
          refresh();
        }).catch(angular.noop);
      }
    });
    });
  };

}).controller('PracticeEnterpriseLocationsEditController', function ($scope, $route, QueryUtils, dialogService, message, FormUtils, Classifier, $q, $timeout) {
  $scope.currentNavItem = 'enterprise.locations';    
  angular.element(document).ready(function() {
    $timeout(function() {
      var scrollAmount = document.getElementsByName($scope.currentNavItem)[0].offsetLeft;
      document.getElementById('scrolling_div').scrollLeft += scrollAmount;
    }, 0);
  });
  $scope.auth = $route.current.locals.auth;
  $scope.enterprise = {};
  $scope.enterprise.id = $route.current.params.id;
  $scope.enterprise.enterpriseSchoolId = $route.current.params.enterpriseSchoolId;
  $scope.enterprise.application = window.localStorage.getItem('enterpriseApplication');
  var EnterpriseEndpoint = QueryUtils.endpoint('/practiceEnterprise/' + $scope.enterprise.enterpriseSchoolId + '/locations');
  var clMapper = Classifier.valuemapper({
    country: 'RIIK',
    language: 'OPPEKEEL'
  });

  var refresh = function() {
    QueryUtils.createQueryForm($scope, '/practiceEnterprise/' + $scope.enterprise.enterpriseSchoolId + '/locations', {}, clMapper.objectmapper);
    $q.all(clMapper.promises).then($scope.loadData);
  };

  refresh();


  $scope.addNewLocation = function (row) {
    dialogService.showDialog('practiceEnterprise/practice.enterprise.location.add.dialog.html', function (dialogScope) {
      dialogScope.delete = function () {
        dialogService.confirmDialog({ prompt: 'enterprise.locationsTab.deleteConfirm' }, function () {
          var LocationEndpoint = QueryUtils.endpoint('/practiceEnterprise/location/' + row.id);
          var enterpriseLocation = new LocationEndpoint();
          enterpriseLocation.$delete().then(function () {
            message.info('main.messages.delete.success');
            refresh();
          }).catch(angular.noop);
        });
      };
      if (row) {
        dialogScope.enterprise = angular.copy(row);
        if (row.language !== undefined) {
          dialogScope.enterprise.language = row.language.code;
        }
        if (row.country !== undefined) {
          dialogScope.enterprise.country = row.country.code;
        }
        dialogScope.enterprise.name = window.localStorage.getItem('enterpriseName');
        dialogScope.enterprise.regCode = window.localStorage.getItem('enterpriseRegCode');
      } else {
        dialogScope.enterprise = {};
        dialogScope.enterprise.language = 'OPPEKEEL_E';
        dialogScope.enterprise.country = 'RIIK_EST';
        dialogScope.enterprise.name = window.localStorage.getItem('enterpriseName');
        dialogScope.enterprise.regCode = window.localStorage.getItem('enterpriseRegCode');
      }
      dialogScope.isAddressFilled = function(enterprise) {
        if (enterprise.country === 'RIIK_EST') {
          return enterprise.address && enterprise.addressAds && enterprise.addressOid;
        }
        return enterprise.address;
      };
      dialogScope.checkAddress = function() {
        if (!dialogScope.isAddressFilled(dialogScope.enterprise)) {
          message.error('student.error.addressRequired');
          return false;
        } else {
          dialogScope.submit();
        }
      };
    }, function (submittedDialogScope) {
      FormUtils.withValidForm(submittedDialogScope.dialogForm, function() {
        if (row) {
          var LocationEndpoint = QueryUtils.endpoint('/practiceEnterprise/location/' + row.id);
          var locationEndPoint = new LocationEndpoint(submittedDialogScope.enterprise);
          locationEndPoint.$save().then(function () {
            message.info('main.messages.create.success');
            refresh();
          }).catch(angular.noop);
        } else {
          var enterpriseEndPoint = new EnterpriseEndpoint(submittedDialogScope.enterprise);
          enterpriseEndPoint.$save().then(function () {
            message.info('main.messages.create.success');
            refresh();
          }).catch(angular.noop);
        }
      });
    });
  };

})
.controller('PracticeEnterpriseStudentGroupsEditController', function ($scope, $route, QueryUtils, dialogService, message, FormUtils, Classifier, $q, $timeout) {
  $scope.currentNavItem = 'enterprise.studentGroups';
  angular.element(document).ready(function() {
    $timeout(function() {
      var scrollAmount = document.getElementsByName($scope.currentNavItem)[0].offsetLeft;
      document.getElementById('scrolling_div').scrollLeft += scrollAmount;
    }, 0);
  });
  $scope.auth = $route.current.locals.auth;
  $scope.enterprise = {};
  $scope.enterprise.id = $route.current.params.id;
  $scope.enterprise.enterpriseSchoolId = $route.current.params.enterpriseSchoolId;
  $scope.enterprise.application = window.localStorage.getItem('enterpriseApplication');
  var EnterpriseEndpoint = QueryUtils.endpoint('/practiceEnterprise/' + $scope.enterprise.enterpriseSchoolId + '/studentGroups');
  var clMapper = Classifier.valuemapper({
    iscedClass: 'ISCED_RYHM',
  });

  var refresh = function() {
    QueryUtils.createQueryForm($scope, '/practiceEnterprise/' + $scope.enterprise.enterpriseSchoolId + '/studentGroups', {}, clMapper.objectmapper);
    $q.all(clMapper.promises).then($scope.loadData);
  };

  refresh();

  $scope.addNewStudentGroup = function (row) {
    dialogService.showDialog('practiceEnterprise/practice.enterprise.studentGroups.add.dialog.html', function (dialogScope) {
      dialogScope.delete = function () {
        dialogService.confirmDialog({ prompt: 'enterprise.studentGroupTab.deleteConfirm' }, function () {
          var StudentGroupEndPoint = QueryUtils.endpoint('/practiceEnterprise/studentGroup/' + row.id);
          var enterpriseStudentGroup = new StudentGroupEndPoint();
          enterpriseStudentGroup.$delete().then(function () {
            message.info('main.messages.delete.success');
            refresh();
          }).catch(angular.noop);
        });
      };
      if (row) {
        dialogScope.enterprise = angular.copy(row);
        dialogScope.enterprise.iscedClass = row.iscedClass.code;
        dialogScope.enterprise.name = window.localStorage.getItem('enterpriseName');
        dialogScope.enterprise.regCode = window.localStorage.getItem('enterpriseRegCode');
      } else {
        dialogScope.enterprise = {};
        dialogScope.enterprise.name = window.localStorage.getItem('enterpriseName');
        dialogScope.enterprise.regCode = window.localStorage.getItem('enterpriseRegCode');
      }
    }, function (submittedDialogScope) {
      FormUtils.withValidForm(submittedDialogScope.dialogForm, function() {
      if (row) {
          var StudentGroupEndPoint = QueryUtils.endpoint('/practiceEnterprise/studentGroup/' + row.id);
          var studentGroupEndPoint = new StudentGroupEndPoint(submittedDialogScope.enterprise);
          studentGroupEndPoint.$save().then(function () {
            message.info('main.messages.create.success');
            refresh();
          }).catch(angular.noop);
      } else {
          var enterpriseEndPoint = new EnterpriseEndpoint(submittedDialogScope.enterprise);
          enterpriseEndPoint.$save().then(function () {
            message.info('main.messages.create.success');
            refresh();
          }).catch(angular.noop);
      }
    });
    });
  };
})
.controller('PracticeEnterpriseGradesEditController', function ($scope, $route, QueryUtils, Classifier, message, $q, $location, FormUtils, $timeout) {
  $scope.currentNavItem = 'enterprise.grades';
  angular.element(document).ready(function() {
    $timeout(function() {
      var scrollAmount = document.getElementsByName($scope.currentNavItem)[0].offsetLeft;
      document.getElementById('scrolling_div').scrollLeft += scrollAmount;
    }, 0);
  });
  $scope.auth = $route.current.locals.auth;
  $scope.enterprise = {};
  $scope.enterprise.id = $route.current.params.id;
  $scope.enterprise.enterpriseSchoolId = $route.current.params.enterpriseSchoolId;
  $scope.enterprise.application = window.localStorage.getItem('enterpriseApplication');
  var EnterpriseEndpoint = QueryUtils.endpoint('/practiceEnterprise/' + $scope.enterprise.enterpriseSchoolId + '/grades');

  $scope.getCurrentColor = function(code, ratingDate) {
    if (code === 'PR_HINNANG_A' && new Date(ratingDate) < new Date()) {
      return '#dadd88';
    } else if (code === 'PR_HINNANG_T' || code === 'PR_HINNANG_A') {
      return '#a4dba7';
    } else if (code === 'PR_HINNANG_E') {
      return '#dd9d9d';
    } else if (code === 'PR_HINNANG_X') {
      return '#dadd88';
    } else if (code === undefined || code === 'PR_HINNANG_P') {
      return '#c9c9c9';
    } else {
      return 'white';
    }
  };

  var clMapper = Classifier.valuemapper({
    ratingCode: 'PR_HINNANG'
  });

  var refresh = function() {
    QueryUtils.endpoint('/practiceEnterprise/' + $scope.enterprise.enterpriseSchoolId + '/grade').search().$promise.then(function(callBack) {
      $scope.grade = callBack;
    });
    QueryUtils.createQueryForm($scope, '/practiceEnterprise/' + $scope.enterprise.id + '/grades', {}, clMapper.objectmapper);
    $q.all(clMapper.promises).then($scope.loadData);
  };

  refresh();
  
  $scope.saveGrade = function () {
    var gradesEndPoint = new EnterpriseEndpoint($scope.grade);
    FormUtils.withValidForm($scope.gradeForm, function() {
      gradesEndPoint.$save().then(function () {
        message.info('main.messages.create.success');
        refresh();
      }).catch(angular.noop);
    });
  };

  $scope.deleteGrade = function () {
    var gradesEndPoint = new EnterpriseEndpoint();
    gradesEndPoint.$delete().then(function () {
      message.info('main.messages.delete.success');
      refresh();
    }).catch(angular.noop);
  };
  
})
.controller('PracticeEnterpriseContractsViewController', function ($scope, $route, $q, QueryUtils, Classifier, $timeout) {
  $scope.currentNavItem = 'enterprise.contracts';
  angular.element(document).ready(function() {
    $timeout(function() {
      var scrollAmount = document.getElementsByName($scope.currentNavItem)[0].offsetLeft;
      document.getElementById('scrolling_div').scrollLeft += scrollAmount;
    }, 0);
  });
  $scope.auth = $route.current.locals.auth;
  $scope.enterprise = {};
  $scope.enterprise.id = $route.current.params.id;
  $scope.enterprise.enterpriseSchoolId = $route.current.params.enterpriseSchoolId;
  $scope.enterprise.application = window.localStorage.getItem('enterpriseApplication');
  var clMapper = Classifier.valuemapper({
    status: 'LEPING_STAATUS',
  });

  var refresh = function() {
    QueryUtils.createQueryForm($scope, '/practiceEnterprise/' + $scope.enterprise.enterpriseSchoolId + '/contracts', {}, clMapper.objectmapper);
    $q.all(clMapper.promises).then($scope.loadData);
  };

  refresh();
})
.controller('PracticeEnterpriseAdmissionEditController', function ($scope, $route, QueryUtils, dialogService, message, FormUtils, $q, $timeout) {
  $scope.currentNavItem = 'enterprise.admission';
  angular.element(document).ready(function() {
    $timeout(function() {
      var scrollAmount = document.getElementsByName($scope.currentNavItem)[0].offsetLeft;
      document.getElementById('scrolling_div').scrollLeft += scrollAmount;
    }, 0);
  });
  $scope.auth = $route.current.locals.auth;
  $scope.enterprise = {};
  $scope.enterprise.id = $route.current.params.id;
  $scope.enterprise.enterpriseSchoolId = $route.current.params.enterpriseSchoolId;
  $scope.enterprise.application = window.localStorage.getItem('enterpriseApplication');
  var EnterpriseEndpoint = QueryUtils.endpoint('/practiceEnterprise/' + $scope.enterprise.enterpriseSchoolId + '/admissions');

  var refresh = function() {
    QueryUtils.createQueryForm($scope, '/practiceEnterprise/' + $scope.enterprise.enterpriseSchoolId + '/admissions');
    $q.all().then($scope.loadData);
  };

  refresh();


  $scope.addNewAdmission = function (row) {
    dialogService.showDialog('practiceEnterprise/practice.enterprise.admission.add.dialog.html', function (dialogScope) {
      dialogScope.now = new Date();

      dialogScope.addAllStudentGroups = function () {
        QueryUtils.endpoint('/autocomplete/studentgroups').query({valid: true}, function (studentGroups) {
          if (!angular.isArray(dialogScope.enterprise.studentGroups)) {
            dialogScope.enterprise.studentGroups = [];
          }
          dialogScope.enterprise.studentGroups = studentGroups;
        });
      };

      dialogScope.$watch('enterprise.studentGroup', function () {
        if (angular.isDefined(dialogScope.enterprise) && angular.isDefined(dialogScope.enterprise.studentGroup) && dialogScope.enterprise.studentGroup !== null) {
          dialogScope.addStudentGroup();
        }
      });
  
      dialogScope.addStudentGroup = function () {
        if (!angular.isArray(dialogScope.enterprise.studentGroups)) {
          dialogScope.enterprise.studentGroups = [];
        }
        if (dialogScope.enterprise.studentGroups.some(function (studentGroup) {
            return studentGroup.id === dialogScope.enterprise.studentGroup.id;
          })) {
          message.error('timetable.timetableEvent.error.duplicateStudentGroup');
          return;
        }
        dialogScope.enterprise.studentGroups.push(dialogScope.enterprise.studentGroup);
        dialogScope.enterprise.studentGroup = undefined;
      };
  
      dialogScope.deleteStudentGroup = function (studentGroup) {
        var index = dialogScope.enterprise.studentGroups.indexOf(studentGroup);
        if (index !== -1) {
          dialogScope.enterprise.studentGroups.splice(index, 1);
          dialogScope.dialogForm.$setDirty();
        }
      };

      dialogScope.delete = function () {
        dialogService.confirmDialog({ prompt: 'enterprise.admissionTab.deleteConfirm' }, function () {
          var LocationEndpoint = QueryUtils.endpoint('/practiceEnterprise/admission/' + row.id);
          var enterpriseLocation = new LocationEndpoint();
          enterpriseLocation.$delete().then(function (response) {
            if (response.nameEt === null) {
              message.info('main.messages.delete.success');
            refresh();
            } else {
              message.error(response.nameEt);
            }
          }).catch(angular.noop);
        });
      };
      if (row) {
        QueryUtils.endpoint('/practiceEnterprise/admission/' + row.id).search().$promise.then(function(response) {
          dialogScope.enterprise = response;
          dialogScope.enterprise.validFrom = new Date(response.validFrom);
          dialogScope.enterprise.validThru = new Date(response.validThru);
          dialogScope.enterprise.name = window.localStorage.getItem('enterpriseName');
          dialogScope.enterprise.regCode = window.localStorage.getItem('enterpriseRegCode');
        });
      } else {
        dialogScope.enterprise = {};
        dialogScope.enterprise.name = window.localStorage.getItem('enterpriseName');
        dialogScope.enterprise.regCode = window.localStorage.getItem('enterpriseRegCode');
      }
    }, function (submittedDialogScope) {
      FormUtils.withValidForm(submittedDialogScope.dialogForm, function() {
      if (row) {
          var LocationEndpoint = QueryUtils.endpoint('/practiceEnterprise/admission/' + row.id);
          var locationEndPoint = new LocationEndpoint(submittedDialogScope.enterprise);
          locationEndPoint.$save().then(function () {
            message.info('main.messages.create.success');
            refresh();
          }).catch(angular.noop);
      } else {
          var enterpriseEndPoint = new EnterpriseEndpoint(submittedDialogScope.enterprise);
          enterpriseEndPoint.$save().then(function () {
            message.info('main.messages.create.success');
            refresh();
          }).catch(angular.noop);
        }
      });
    });
  };

});