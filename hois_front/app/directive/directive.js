'use strict';

angular.module('hitsaOis').controller('DirectiveSearchController', ['$q', '$scope', 'Classifier', 'QueryUtils',
  function ($q, $scope, Classifier, QueryUtils) {
    var clMapper = Classifier.valuemapper({type: 'KASKKIRI', status: 'KASKKIRI_STAATUS'});
    QueryUtils.createQueryForm($scope, '/directives', {order: 'headline'}, clMapper.objectmapper);

    $q.all(clMapper.promises).then($scope.loadData);
  }
]).controller('DirectiveEditController', ['$location', '$mdDialog', '$route', '$scope', 'dialogService', 'message', 'QueryUtils',
  function ($location, $mdDialog, $route, $scope, dialogService, message, QueryUtils) {
    var id = $route.current.params.id;
    var baseUrl = '/directives';

    $scope.formState = {state: (id ? 'EDIT' : 'CHOOSETYPE'), students: undefined, selectedStudents: []};

    function setTemplateUrl() {
      // TODO use $scope.record.type
      $scope.formState.templateUrl = 'directive/directive.type.'+'okoorm'+'.html';
    }

    function afterLoad() {
      setTemplateUrl();
    }

    var Endpoint = QueryUtils.endpoint(baseUrl);
    if(id) {
      $scope.record = Endpoint.get({id: id}, afterLoad);
    } else {
      $scope.record = new Endpoint();
      afterLoad();
    }

    $scope.update = function() {
      $scope.directiveForm.$setSubmitted();
      if(!$scope.directiveForm.$valid) {
        message.error('main.messages.form-has-errors');
        return;
      }
      var msg = $scope.record.id ? 'main.messages.update.success' : 'main.messages.create.success';
      function afterSave() {
        message.info(msg);
        afterLoad();
      }
      $scope.record.students = $scope.formState.selectedStudents.map(function(item) { return item.id; });
      if($scope.record.id) {
        $scope.record.$update().then(afterSave);
      }else{
        $scope.record.$save().then(afterSave);
      }
    };

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'directive.deleteconfirm'}, function() {
        $scope.record.$delete().then(function() {
          message.info('main.messages.delete.success');
          $location.path(baseUrl);
        });
      });
    };

    $scope.directiveTypeChanged = function() {
      var data = {type: $scope.record.type};
      QueryUtils.endpoint(baseUrl+'/findstudents').query(data, function(result) {
        $scope.formState.students = result;
        $scope.formState.selectedStudents = [];
        if(!result.length) {
          message('directive.nostudentsfound');
        }
      });
      setTemplateUrl();
    };

    $scope.addDirective = function() {
      $scope.formState.state = 'EDIT';
      var data = {type: $scope.record.type, students: $scope.formState.selectedStudents.map(function(i) { return i.id; })};
      QueryUtils.endpoint(baseUrl+'/directivedata').save(data, function(result) {
        angular.copy(result.toJSON(), $scope.record);
        $scope.formState.students = result.students;
      });
    };

    function storeStudents() {
    }

    $scope.addStudents = function() {
      var data = {type: $scope.record.type, directive: id};
      $mdDialog.show({
        controller: function($scope) {
          $scope.formState = {selectedStudents: []};

          QueryUtils.createQueryForm($scope, '/directives/findstudents', data);

          $scope.cancel = $mdDialog.hide;
          $scope.select = function() {
            storeStudents($scope.formState.selectedStudents);
            $mdDialog.hide();
          };

          $scope.loadData();
        },
        templateUrl: 'directive/student.select.dialog.html',
        clickOutsideToClose: true
      });
    };
  }
]);
