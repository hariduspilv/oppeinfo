'use strict';

function calculateSumsForFamilyBlock(familyData) {
  if (angular.isArray(familyData)) {
    familyData.forEach(function (row) {
      row.sum = [row.netSalary, row.pension, row.stateBenefit, row.otherIncome, row.unemployedBenefit].reduce(function (a, b) {
        if (isNaN(b)) {
          return a;
        }
        return a + b;
      }, 0);
    });
  }
}

angular.module('hitsaOis').controller('StudentScholarshipApplicationEditController', ['$location', '$scope', '$route', 'ArrayUtils', 'Classifier', 'DataUtils', 'QueryUtils', 'dialogService', 'message', 'oisFileService',
  function ($location, $scope, $route, ArrayUtils, Classifier, DataUtils, QueryUtils, dialogService, message, oisFileService) {
    var templateMap = {
      STIPTOETUS_POHI: 'scholarship/student/scholarship.application.pohi.edit.html',
      STIPTOETUS_ERI: 'scholarship/student/scholarship.application.eri.edit.html',
      STIPTOETUS_SOIDU: 'scholarship/student/scholarship.application.soidu.edit.html',
      STIPTOETUS_DOKTOR: 'scholarship/student/scholarship.application.doktor.edit.html',
      STIPTOETUS_ERIALA: 'scholarship/student/scholarship.application.scho.edit.html',
      STIPTOETUS_MUU: 'scholarship/student/scholarship.application.scho.edit.html',
      STIPTOETUS_TULEMUS: 'scholarship/student/scholarship.application.scho.edit.html'
    };
    $scope.application = {};
    $scope.formState = {};
    var baseUrl = '/scholarships';
    $scope.editable = true;
    var id = $route.current.params.id;
    QueryUtils.endpoint(baseUrl + '/' + id + '/application').get({}, function (result) {
      $scope.stipend = result.stipend;
      $scope.termCompliance = result.termCompliance;

      $scope.templateName = templateMap[result.stipend.type];
      if (result.stipend.type === 'STIPTOETUS_ERI') {
        calculateSumsForFamilyBlock($scope.application.family);
      }
      if(!result.application.compensationFrequency) {
        result.application.compensationFrequency = 'STIPTOETUS_HYVITAMINE_1';
      }
      afterLoad(result.application);
    });

    $scope.openAddFileDialog = function () {
      dialogService.showDialog('components/file.add.dialog.html', function (dialogScope) {
        dialogScope.addedFiles = $scope.application.files;
      }, function (submittedDialogScope) {
        var data = submittedDialogScope.data;
        oisFileService.getFromLfFile(data.file[0], function (file) {
          $scope.application.files.push(file);
        });
        //message.info('stipend.messages.filesWillBeAddedOnSubmit');
      });
    };

    $scope.removeFile = function(files, file) {
      ArrayUtils.remove(files, file);
      //message.info('stipend.messages.filesWillBeAddedOnSubmit');
    }

    $scope.compensationFrequencies = Classifier.queryForDropdown({
      mainClassCode: 'STIPTOETUS_HYVITAMINE'
    });

    $scope.getUrl = oisFileService.getUrl;
    $scope.removeFromArray = ArrayUtils.remove;
    $scope.isNumber = angular.isNumber;

    function formIsValid(form) {
      form.$setSubmitted();
      if ($scope.application.family && $scope.application.family.length > $scope.application.familyMembersAdult) {
        message.error('stipend.family.deleteRows');
        return false;
      }
      if (!form.$valid) {
        message.error('main.messages.form-has-errors');
        return false;
      }
      return true;
    }

    function afterLoad(result) {
      $scope.application = result;
      if ($scope.stipend.type === 'STIPTOETUS_ERI') {
        DataUtils.convertStringToDates($scope.stipend, ['paymentStart', 'paymentEnd']);
        DataUtils.convertStringToDates($scope.application, ['scholarshipFrom', 'scholarshipThru']);
        $scope.updateScholarshipDateValidation();
      }
    }

    $scope.updateScholarshipDateValidation = function () {
      var paymentStart = $scope.stipend.paymentStart ? moment($scope.stipend.paymentStart) : null;
      var paymentEnd = $scope.stipend.paymentEnd ? moment($scope.stipend.paymentEnd) : null;
      var scholarshipFrom = $scope.application.scholarshipFrom ? moment($scope.application.scholarshipFrom) : null;
      var scholarshipThru = $scope.application.scholarshipThru ? moment($scope.application.scholarshipThru) : null;
      
      $scope.formState.scholarshipFromMax = paymentEnd && scholarshipThru ? moment.min(paymentEnd, scholarshipThru).toDate() :
        $scope.stipend.paymentEnd || $scope.application.scholarshipThru;

      $scope.formState.scholarshipThruMin = paymentStart && scholarshipFrom ? moment.max(paymentStart, scholarshipFrom).toDate() :
        $scope.stipend.paymentStart || $scope.application.scholarshipFrom;
    };

    function loadTermCompliances(application) {
      QueryUtils.endpoint(baseUrl + '/studentTermCompliance/' + application.id).search(function (termCompliance) {
        $scope.termCompliance = termCompliance;
      });
    }

    $scope.apply = function (form) {
      dialogService.confirmDialog({prompt: 'stipend.confirmations.apply'}, function() {
        $scope.update(form, function (result) {
          QueryUtils.endpoint(baseUrl + '/apply').update({
            id: result.id
          }, function (result) {
            message.info('stipend.messages.applicationSuccessful');
            $location.url(baseUrl + '/applications/' + result.id + '?_noback');
          });
          loadTermCompliances(result);
        });
      });
    };

    $scope.update = function (form, callBack) {
      if (!formIsValid(form)) {
        return;
      }
      if ($scope.application.id) {
        QueryUtils.endpoint(baseUrl + '/' + id + '/application').update($scope.application, function (result) {
          if (callBack) {
            callBack(result);
          } else {
            afterLoad(result);
            message.updateSuccess();
            form.$setPristine();
          }
        });
      } else {
        QueryUtils.endpoint(baseUrl + '/' + id + '/application').save($scope.application, function (result) {
          if (callBack) {
            callBack(result);
          } else {
            message.info('main.messages.create.success');
            $location.url(baseUrl + '/' + $scope.stipend.id + '/application?_noback');
          }
        });
      }
    };

    $scope.addFamilyMember = function () {
      if (!angular.isArray($scope.application.family)) {
        $scope.application.family = [];
      }
      if ($scope.application.family.length < $scope.application.familyMembersAdult) {
        dialogService.showDialog('scholarship/student/scholarship.family.addMember.html', function () {}, function (submittedDialogScope) {
          var member = submittedDialogScope.member;
          submittedDialogScope.member.sum = [member.netSalary, member.pension, member.stateBenefit, member.otherIncome, member.unemployedBenefit].reduce(function (a, b) {
            if (isNaN(b)) {
              return a;
            }
            return a + b;
          }, 0);
          $scope.application.family.push(submittedDialogScope.member);
        });
      } else {
        message.error('stipend.family.errorWithMembers');
      }
    };
  }
]).controller('StudentScholarshipApplicationViewController', ['dialogService', 'Classifier', '$scope', '$location', 'message', 'QueryUtils', '$route', 'oisFileService', 'AuthService',
  function (dialogService, Classifier, $scope, $location, message, QueryUtils, $route, oisFileService, AuthService) {
    var templateMap = {
      STIPTOETUS_POHI: 'scholarship/student/scholarship.application.pohi.view.html',
      STIPTOETUS_ERI: 'scholarship/student/scholarship.application.eri.view.html',
      STIPTOETUS_SOIDU: 'scholarship/student/scholarship.application.soidu.view.html',
      STIPTOETUS_DOKTOR: 'scholarship/student/scholarship.application.doktor.view.html',
      STIPTOETUS_ERIALA: 'scholarship/student/scholarship.application.scho.view.html',
      STIPTOETUS_MUU: 'scholarship/student/scholarship.application.scho.view.html',
      STIPTOETUS_TULEMUS: 'scholarship/student/scholarship.application.scho.view.html'
    };

    $scope.getUrl = oisFileService.getUrl;
    $scope.isNumber = angular.isNumber;
    $scope.editable = false;
    var baseUrl = '/scholarships';
    var id = $route.current.params.id;
    $scope.isStudent = $route.current.locals.auth.isStudent;

    function loadApplication() {
      QueryUtils.endpoint(baseUrl + '/application/' + id).get({}, function (result) {
        $scope.stipend = result.stipend;
        $scope.application = result.application;

        $scope.templateName = templateMap[result.stipend.type];
        if (result.stipend.type === 'STIPTOETUS_ERI') {
          calculateSumsForFamilyBlock($scope.application.family);
        }
        $scope.canManage = AuthService.isAuthorized('ROLE_OIGUS_M_TEEMAOIGUS_STIPTOETUS') && result.application.status !== 'STIPTOETUS_STAATUS_A';
      });
    }
    loadApplication();

    $scope.compensationFrequencies = Classifier.queryForDropdown({
      mainClassCode: 'STIPTOETUS_HYVITAMINE'
    });

    //this is only used when viewing by the admin
    $scope.accept = function () {
      QueryUtils.endpoint(baseUrl + '/acceptApplications').put([id]
      , function () {
        loadApplication();
      });
    };
  }
]);
