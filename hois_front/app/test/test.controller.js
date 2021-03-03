'use strict';

angular.module('hitsaOis')
  .controller('TestController', function ($scope, $timeout, $q) {
    $scope.name =  "this is a test controller";
    $scope.arbitraryFunction = function(value) {
      console.log('arbitraryFunction ', value);
      return value && value.length > 3;
    };
    $scope.customFunction = function(value) {
      console.log('customFunction ', value);
      return value && value.length < 6;
    };

    $scope.testMultiAC = function () {
      console.log($scope.acMultiForm.$valid);
    };

    $scope.testSingleAC = function () {
      $scope.acSingleForm.$setSubmitted();
      console.log($scope.acSingleForm.$valid);
    };

    $scope.testColorForm = function () {
      $scope.colorForm.$setSubmitted();
      console.log($scope.colorForm);
      console.log($scope.colorForm.$valid);
    };

    $scope.myColor = "FFFFFF";
    $scope.option = {
      required: false,
      allowEmpty: false
    };

    $scope.myColor2 = "000000";
    $scope.options2 = {
      required: true,
      allowEmpty: false,
      inputClass: 'fit-content'
    };

    $scope.testClick = function () {
      console.log("Clicked");
    };

    $scope.testClickPromise = function () {
      var deferred = $q.defer();
      console.log("Timeouted for 3000ms");
      $timeout(function () {
        console.log("Clicked");
        deferred.resolve();
      }, 3000);
      return deferred.promise;
    };

    var counter = 0;
    function createRandRow () {
      var uId = counter++;
      return {
        id: uId,
        name: "TEST" + uId
      };
    }

    $scope.testRows = [
      createRandRow(), createRandRow(), createRandRow(), createRandRow()
    ];

    $scope.dummyModelClassifier = 'RIIK_FIN';
  })


  .directive('testHoisValidator', function () {
    return {
      restrict: 'A',
      require: ['ngModel'],
      scope: {
        value: "=ngModel",
        validatorNames: "@testHoisValidator"
      },
      link: function(scope, elem, attrs, ngModelController) {
        Object.keys(attrs).forEach(function(attr) {
          var prefix = 'testValidator';
          if (attr.indexOf(prefix) === 0 && attr.length > prefix.length) {
            var validatorName = attr.substring(prefix.length).charAt(0).toLowerCase() + attr.substring(prefix.length + 1);
            var validatorFunction = scope.$parent[attrs[attr]];
            scope.$watch('value', function(newValue) {
              if (angular.isFunction(validatorFunction)) {
                ngModelController[0].$setValidity(validatorName, validatorFunction(newValue));
              }
            });
          }
        });
      }
    };
  });
