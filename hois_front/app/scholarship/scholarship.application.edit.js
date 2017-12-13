'use strict';

angular.module('hitsaOis').controller('StudentScholarshipApplicationController', ['dialogService', 'Classifier', '$scope', '$location', 'message', 'QueryUtils', '$route', 'oisFileService', 'ArrayUtils',
  function (dialogService, Classifier, $scope, $location, message, QueryUtils, $route, oisFileService, ArrayUtils) {
    var templateMap = {
      STIPTOETUS_POHI: 'scholarship/scholarship.application.pohi.edit.html',
      STIPTOETUS_ERI: 'scholarship/scholarship.application.eri.edit.html',
      STIPTOETUS_SOIDU: 'scholarship/scholarship.application.soidu.edit.html',
      STIPTOETUS_DOKTOR: 'scholarship/scholarship.application.doktor.edit.html',
      STIPTOETUS_ERIALA: 'scholarship/scholarship.application.scho.edit.html',
      STIPTOETUS_MUU: 'scholarship/scholarship.application.scho.edit.html',
      STIPTOETUS_TULEMUS: 'scholarship/scholarship.application.scho.edit.html'
    };
    $scope.studentSubmitData = {};
    var baseUrl = '/scholarships';
    var id = $route.current.params.id;
    QueryUtils.endpoint(baseUrl + '/' + id + '/application').get({}, function (result) {
      $scope.stipend = result.stipend;
      $scope.studentInfo = result.studentInfo;
      $scope.studentSubmitData = result.studentSubmitData;
      $scope.templateName = templateMap[result.stipend.type];
      if (result.stipend.type === 'STIPTOETUS_ERI') {
        calculateSumsForFamilyBlock($scope.studentSubmitData.family);
      }
      $scope.studentSubmitData.canApply = result.studentSubmitData.status === 'STIPTOETUS_STAATUS_K';
    });

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

    $scope.openAddFileDialog = function () {
      dialogService.showDialog('components/file.add.dialog.html', function (dialogScope) {
        dialogScope.addedFiles = $scope.studentSubmitData.files;
      }, function (submittedDialogScope) {
        var data = submittedDialogScope.data;
        oisFileService.getFromLfFile(data.file[0], function (file) {
          data.oisFile = file;
          $scope.studentSubmitData.files.push(data);
        });
      });
    };

    $scope.compensationFrequencies = Classifier.queryForDropdown({
      mainClassCode: 'STIPTOETUS_HYVITAMINE'
    });

    $scope.getUrl = oisFileService.getUrl;
    $scope.removeFromArray = ArrayUtils.remove;


    function formIsValid(form) {
      form.$setSubmitted();
      if ($scope.studentSubmitData.family && $scope.studentSubmitData.family.length > $scope.studentSubmitData.familyMembersAdult) {
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
      result.studentSubmitData.canApply = result.studentSubmitData.status === 'STIPTOETUS_STAATUS_K';
      $scope.studentSubmitData = result;
    }

    $scope.apply = function(form) {
      $scope.update(form, function(result) {
        QueryUtils.endpoint(baseUrl + '/apply').update({id: result.id, derp: "derp"}, function() {
          message.info('stipend.messages.applicationSuccessful');
          $location.url(baseUrl + '/' + $scope.stipend.id + '/application?_noback');
        });
      });
    };

    $scope.update = function (form, callBack) {
      if (!formIsValid(form)) {
        return;
      }
      if ($scope.studentSubmitData.id) {
        QueryUtils.endpoint(baseUrl + '/' + id + '/application').update($scope.studentSubmitData, function (result) {
          if(callBack) {
            callBack(result);
          } else {
            afterLoad(result);
            message.info('message.updateSuccess');
          }
        });
      } else {
        QueryUtils.endpoint(baseUrl + '/' + id + '/application').save($scope.studentSubmitData, function (result) {
          if(callBack) {
            callBack(result);
          } else {
            message.info('main.messages.create.success');
            $location.url(baseUrl + '/' + $scope.stipend.id + '/application?_noback');
          }
        });
      }
    };

    $scope.addFamilyMember = function () {
      if (!angular.isArray($scope.studentSubmitData.family)) {
        $scope.studentSubmitData.family = [];
      }
      if ($scope.studentSubmitData.family.length < $scope.studentSubmitData.familyMembersAdult) {
        dialogService.showDialog('scholarship/scholarship.family.addMember.html', function () {}, function (submittedDialogScope) {
          var member = submittedDialogScope.member;
          submittedDialogScope.member.sum = [member.netSalary, member.pension, member.stateBenefit, member.otherIncome, member.unemployedBenefit].reduce(function (a, b) {
            if (isNaN(b)) {
              return a;
            }
            return a + b;
          }, 0);
          $scope.studentSubmitData.family.push(submittedDialogScope.member);
        });
      } else {
        message.error('stipend.family.errorWithMembers');
      }
    };
  }
]);
