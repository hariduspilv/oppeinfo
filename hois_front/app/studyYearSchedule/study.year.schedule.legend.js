'use strict';

angular.module('hitsaOis').controller('studyYearScheduleLegendController', 
  function ($scope, QueryUtils, ArrayUtils, message, dialogService, USER_ROLES, AuthService) {
    $scope.removedInUseLegend = false;
    $scope.canEdit = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPETOOGRAAFIK);
    $scope.colorOptions = {
      disabled: !$scope.canEdit
    };

    function colorSetter(row) {
      row.colorPicker = function (newColor) {
        return arguments.length ? (row.color = "#" + newColor) : row.color;
      };
    }

    var DEFAULT_VALUES = [{
        code: "E",
        nameEt: "Eksam",
        color: "#FF0000"
      },
      {
        code: "Pr",
        nameEt: "Praktika ettevõtes",
        color: "#790FFC"
      },
      {
        code: "P",
        nameEt: "Praktika koolis",
        color: "#1FF47C"
      },
      {
        code: "V",
        nameEt: "Vaheaeg",
        color: "#E6E6E6"
      },
      {
        code: "X",
        nameEt: "ei osale õppetöös",
        color: "#8C8C8C"
      }
    ];
    DEFAULT_VALUES.forEach(colorSetter);

    $scope.newLegend = {};

    $scope.record = QueryUtils.endpoint('/school/studyYearScheduleLegends').search(function (response) {
      if (!response || !response.legends || response.legends.length === 0) {
        $scope.legends = DEFAULT_VALUES;
        $scope.defaultLegends = true;
      } else {
        $scope.legends = response.legends;
        $scope.legends.forEach(colorSetter);
        $scope.defaultLegends = false;
      }
    });

    $scope.remove = function (item) {
      if (item.inUse) {
        $scope.removedInUseLegend = true;
      }
      ArrayUtils.remove($scope.legends, item);
      $scope.legends.forEach(function (l) {
        l.edited = false;
      });
    };
    /**
     * Just one variant of error messages
     */
    $scope.save = function () {
      $scope.formSubmitted = true;
      $scope.legends.forEach(function (l) {
        l.edited = false;
      });
      // if(!formIsValid()) {
      //     message.error('main.messages.form-has-errors');
      //     return;
      // } 
      $scope.record.legends = $scope.legends;
      $scope.record.$put().then(function (response) {
        message.updateSuccess();
        $scope.legends = response.legends;
        $scope.legends.forEach(colorSetter);
        if (angular.isArray(response.legends) && response.legends.length > 0) {
          $scope.defaultLegends = false;
        } else {
          $scope.defaultLegends = true;
        }
        $scope.removedInUseLegend = false;
      });
    };

    $scope.checkErrorsAndSave = function () {
      if (codesNotAdded()) {
        message.error('studyYearScheduleLegend.error.codeRequired');
        return true;
      }
      if (codesWrongLength()) {
        message.error('studyYearScheduleLegend.error.codeLength');
        return true;
      }
      if (nameEtNotAdded()) {
        message.error('studyYearScheduleLegend.error.nameEtRequired');
        return true;
      }
      if (namesWrongLength()) {
        message.error('studyYearScheduleLegend.error.nameLength');
        return true;
      }
      if ($scope.removedInUseLegend) {
        dialogService.confirmDialog({
            prompt: 'studyYearScheduleLegend.error.inUseLegendRemove',
            accept: 'main.yes',
            cancel: 'main.no'
          },
          function () {
            $scope.save();
          });
      } else {
        $scope.save();
      }
    };

    // function formIsValid() {
    //     var invalidElements = $scope.legends.filter(function(el){
    //         return !$scope.codeIsValid(el) || !$scope.nameEtIsValid(el) || !$scope.nameEnIsValid(el);
    //     });
    //     return invalidElements.length === 0;
    // }

    function codesNotAdded() {
      return $scope.legends.filter(function (el) {
        return !el.code;
      }).length > 0;
    }

    function codesWrongLength() {
      return $scope.legends.filter(function (el) {
        return el.code && el.code.length > 4;
      }).length > 0;
    }

    function nameEtNotAdded() {
      return $scope.legends.filter(function (el) {
        return !el.nameEt;
      }).length > 0;
    }

    function namesWrongLength() {
      return $scope.legends.filter(function (el) {
        return el.nameEt && el.nameEt.length > 50 || el.nameEn && el.nameEn.length > 50;
      }).length > 0;
    }

    $scope.codeIsValid = function (legend) {
      return legend.code && legend.code.length <= 4;
    };

    $scope.nameEtIsValid = function (legend) {
      return legend.nameEt && legend.nameEt.length <= 50;
    };

    $scope.nameEnIsValid = function (legend) {
      return !legend.nameEn || legend.nameEn.length <= 50;
    };

    $scope.changeEditable = function (legend) {
      if ($scope.canEdit) {
        $scope.legends.forEach(function (l) {
          l.edited = false;
        });
        legend.edited = true;
      }
    };

    $scope.addRow = function () {
      var newLegend = {
        color: '#FFFFFF'
      };
      $scope.legends.push(newLegend);
      colorSetter(newLegend);
      $scope.legends.forEach(function (l) {
        l.edited = false;
      });
      newLegend.edited = true;
    };
  });
