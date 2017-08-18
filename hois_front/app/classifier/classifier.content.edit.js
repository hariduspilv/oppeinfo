'use strict';

angular.module('hitsaOis')
  .controller('ClassifierContentEditController', function ($scope, $route, Classifier, classifierAutocomplete, $location, message, ClassifierConnect, dialogService, QueryUtils, DataUtils) {

    var code = $route.current.params.codeThis;
    var Endpoint = QueryUtils.endpoint('/classifier');

    function getThisClassifier() {
        $scope.parents = [];
        if(code) {
            $scope.classifier = Endpoint.get({id: code}, function() {
                DataUtils.convertStringToDates($scope.classifier, ['validFrom', 'validThru']);
                getParents($scope.classifier.code);
                getChildren($scope.classifier.code);
            });
        }
        getMainClassifier();
    }
    getThisClassifier();

    function getParents(code) {
        Classifier.getParents(code).$promise.then(function(result) {
          $scope.parents = result;
        });
    }

    function getChildren(code) {
        Classifier.getChildren(code).$promise.then(function(result) {
          $scope.children = result;
          $scope.hasChildren = result.length > 0;
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
      if($scope.classifierForm.$valid) {
        if($scope.classifier.code) {
          update();
        } else {
          create();
        }
      } else {
          message.error('main.messages.form-has-errors');
      }
    };

    function update() {
      ClassifierConnect.sendListOfParents($scope.classifier, $scope.parents);
      $scope.classifier.id = $scope.classifier.code;    // required for using QueryUtils methods
      $scope.classifier.$update().then(function() {
            message.info('main.messages.create.success');
          });
    }

    $scope.delete = function() {
        dialogService.confirmDialog({prompt: 'classifier.deleteconfirm'}, function() {
            $scope.classifier.id = $scope.classifier.code;    // required for using QueryUtils methods
            $scope.classifier.$delete().then(function() {
                message.info('main.messages.delete.success');
                $location.path( '/classifier/' + $scope.mainClassCode);
            });
        });
    };

    function create() {
      $scope.classifier.value = $scope.classifier.value.toUpperCase();
      $scope.classifier.mainClassCode = $scope.mainClassCode;
      $scope.classifier.code = $scope.classifier.mainClassCode + "_" + $scope.classifier.value;
      $scope.classifier.valid = true;
      $scope.classifier.id = $scope.classifier.code;    // required for using QueryUtils methods

      new Endpoint($scope.classifier).$save().then(function(result) {
          ClassifierConnect.sendListOfParents(result, $scope.parents);
          message.info('main.messages.create.success');
          $location.path( '/classifier/' + $scope.mainClassCode + '/' + result.code + '/edit');
        });
    }

    $scope.querySearch = function(queryName, mainClassCode) {
      return classifierAutocomplete.searchByName(queryName, mainClassCode);
    };

    $scope.addParent = function(item) {
      if(item && parentNotAlreadyAdded(item)) {
        $scope.parents.push(item);
        $scope.classifierForm.$setDirty();
      }
    };

    function parentNotAlreadyAdded(newParent) {
        return $scope.parents.filter(function(e) { return e.code === newParent.code; }).length === 0;
    }

    $scope.removeParent = function(parent) {
      dialogService.confirmDialog({prompt: 'classifier.prompt.deleteParent'}, function() {
        $scope.parents.splice($scope.parents.indexOf(parent), 1);
        $scope.classifierForm.$setDirty();
      });
    };
  });
