'use strict';

angular.module('hitsaOis')
  .controller('ClassifierContentEditController', function ($scope, $route, Classifier, classifierAutocomplete, $location, message, $mdDialog, ClassifierConnect) {

    function getThisClassifier() {
        $scope.parents = [];
        if($route.current.params.codeThis) {
          Classifier.get($route.current.params.codeThis).$promise.then(function(result){
            $scope.classifier = result;
            setDates();
            getParents($scope.classifier.code);
            getChildren($scope.classifier.code);
            //console.log("this classifier");
            //console.log($scope.classifier);
          });
        } else {
          $scope.parents = [];
        }
        getMainClassifier();
    }
    getThisClassifier();

    function setDates() {
      if($scope.classifier.validFrom) {
        $scope.classifier.validFrom = new Date($scope.classifier.validFrom);
      }
      if($scope.classifier.validThru) {
        $scope.classifier.validThru = new Date($scope.classifier.validThru);
      }
    }

    function getParents(code) {
        Classifier.getParents(code).$promise.then(function(result) {
          $scope.parents = result;
          //console.log("parents:");
          //console.log($scope.parents);
        });
    }

    function getChildren(code) {
        Classifier.getChildren(code).$promise.then(function(result) {
          $scope.children = result;
          $scope.hasChildren = result.length > 0;
          //console.log("children:");
          //console.log($scope.children);
        });
    }

    function getMainClassifier() {
        $scope.mainClassCode = $route.current.params.mainClassCode;
        $scope.mainClass = Classifier.get($scope.mainClassCode).$promise.then(function(response){
          $scope.mainClass = response;
          getPossibleConnections();
        });
    }

    function getPossibleConnections() {
        Classifier.getPossibleConnections($scope.mainClassCode).$promise.then(function(result) {
          $scope.possibleConnections = result;
        });
    }

    $scope.save = function() {
      $scope.classifierForm.$setSubmitted();
      //console.log('form is valid: '+ $scope.classifierForm.$valid);
      if($scope.classifierForm.$valid) {
        if($scope.classifier.code) {
          $scope.update();
        } else {
          $scope.create();
        }
      } else {
        //console.log("form invalid!");
      }
    };

    $scope.update = function() {
      ClassifierConnect.sendListOfParents($scope.classifier, $scope.parents);
      new Classifier($scope.classifier).save().$promise.then(function() {
        message.info('main.messages.update.success');
      });
    };

    $scope.delete = function() {
      new Classifier($scope.classifier).delete().$promise.then(function() {
        $location.path( '/classifier/' + $scope.mainClassCode);
      });
    };

    $scope.create = function() {

      $scope.classifier.value = $scope.classifier.value.toUpperCase();
      $scope.classifier.mainClassCode = $scope.mainClassCode;
      $scope.classifier.code = $scope.classifier.mainClassCode + "_" + $scope.classifier.value;
      $scope.classifier.valid = true;
      var promise = new Classifier($scope.classifier).create().$promise;
      promise.then(function(result) {
        message.info('main.messages.create.success');
        $scope.classifier = result;
        setDates();
        ClassifierConnect.sendListOfParents($scope.classifier, $scope.parents);
      });
    };

    $scope.querySearch = function(queryName, mainClassCode) {
      return classifierAutocomplete.searchByName(queryName, mainClassCode);
    };

    $scope.addParent = function(item) {
      if(item && parentNotAlreadyAdded(item)) {
        $scope.parents.push(item);
      }
    };

    function parentNotAlreadyAdded(newParent) {
        return $scope.parents.filter(function(e) { return e.code === newParent.code; }).length === 0;
    }

    $scope.removeParent = function(parent) {
      $scope.parents.splice($scope.parents.indexOf(parent), 1);
    };
  });
