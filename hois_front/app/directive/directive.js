'use strict';

angular.module('hitsaOis').controller('DirectiveSearchController', ['$location', '$q', '$scope', 'Classifier', 'QueryUtils',
  function ($location, $q, $scope, Classifier, QueryUtils) {
    var clMapper = Classifier.valuemapper({type: 'KASKKIRI', status: 'KASKKIRI_STAATUS'});
    QueryUtils.createQueryForm($scope, '/directives', {order: 'headline'}, clMapper.objectmapper);

    $q.all(clMapper.promises).then($scope.loadData);
  }
]).controller('DirectiveEditController', ['$location', '$mdDialog', '$route', '$scope', 'dialogService', 'message', 'Curriculum', 'QueryUtils',
  function ($location, $mdDialog, $route, $scope, dialogService, message, Curriculum, QueryUtils) {
    var id = $route.current.params.id;
    var baseUrl = '/directives';

    $scope.formState = {state: (id ? 'EDIT' : 'CHOOSETYPE'), students: undefined, selectedStudents: []};

    function setTemplateUrl() {
      var templateId = $scope.record.type ? $scope.record.type.substr(9).toLowerCase() : 'unknown';
      $scope.formState.templateUrl = 'directive/directive.type.'+templateId+'.edit.html';
    }

    function afterLoad() {
      setTemplateUrl();
    }

    var Endpoint = QueryUtils.endpoint(baseUrl);
    if(id) {
      $scope.record = Endpoint.get({id: id}, afterLoad);
    } else {
      $scope.record = new Endpoint({students: []});
      afterLoad();
    }

    $scope.update = function() {
      $scope.directiveForm.$setSubmitted();
      if(!$scope.directiveForm.$valid) {
        message.error('main.messages.form-has-errors');
        return;
      }
      function afterSave() {
        message.info(id ? 'main.messages.update.success' : 'main.messages.create.success');
        if(!id) {
          $location.path(baseUrl + '/' + $scope.record.id + '/edit');
        } else {
          afterLoad();
        }
      }
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
      QueryUtils.endpoint(baseUrl+'/findstudents').search(data, function(result) {
        $scope.formState.students = result.content;
        $scope.formState.selectedStudents = [];
        if(!$scope.formState.students.length) {
          message.info('directive.nostudentsfound');
        }
      });
      setTemplateUrl();
    };

    function studentsToDirective(students, callback) {
      var data = {type: $scope.record.type, students: students.map(function(i) { return i.id; })};
      QueryUtils.endpoint(baseUrl+'/directivedata').save(data, callback);
    }

    $scope.addDirective = function() {
      $scope.formState.state = 'EDIT';
      studentsToDirective($scope.formState.selectedStudents, function(result) {
        angular.copy(result.toJSON(), $scope.record);
        $scope.record.students = result.students;
      });
      var type = $scope.record.type;
      if(type === 'KASKKIRI_OKAVA' || type === 'KASKKIRI_IMMAT') {
        $scope.formState.curriculumVersions = Curriculum.queryVersions();
      }
      if(type === 'KASKKIRI_ENNIST' || type === 'KASKKIRI_IMMAT' || type === 'KASKKIRI_OKAVA' || type === 'KASKKIRI_OVORM') {
        $scope.formState.studentGroups = QueryUtils.endpoint('/autocomplete/studentgroups').search();
      }
    };

    function storeStudents(students) {
      studentsToDirective(students, function(result) {
        for(var i = 0, cnt = result.students.length; i < cnt; i++) {
          $scope.record.students.push(result.students[i]);
        }
      });
    }

    $scope.deleteStudent = function(idcode) {
      var rows = $scope.record.students;
      for(var i = 0, cnt = rows.length; i < cnt; i++) {
        if(rows[i].idcode === idcode) {
          rows.splice(i, 1);
          break;
        }
      }
    };

    $scope.addStudents = function() {
      var directiveType = $scope.record.type;
      if(directiveType === 'KASKKIRI_IMMAT') {
        $scope.record.students.push({startDate: null});
        return;
      }
      var data = {type: directiveType, directive: id};
      $mdDialog.show({
        controller: function($scope) {
          $scope.formState = {selectedStudents: [], type: data.type};

          QueryUtils.createQueryForm($scope, '/directives/findstudents');
          $scope.getCriteria = function() {
            return angular.extend(QueryUtils.getQueryParams($scope.criteria), data);
          };
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
]).controller('DirectiveViewController', ['$route', '$scope', 'dialogService', 'QueryUtils',
  function ($route, $scope, dialogService, QueryUtils) {
    var id = $route.current.params.id;
    var baseUrl = '/directives';

    $scope.formState = {backUrl: $route.current.params.backUrl};
    $scope.record = QueryUtils.endpoint(baseUrl + '/:id/view').search({id: id});

    $scope.record.$promise.then(function() {
      var templateId = $scope.record.type ? $scope.record.type.substr(9).toLowerCase() : 'unknown';
      $scope.formState.templateUrl = 'directive/directive.type.'+templateId+'.view.html';
    });

    $scope.cancelDirective = function() {
      dialogService.confirmDialog({prompt: 'directive.cancelconfirm'}, function() {
      });
    };
  }
]);
