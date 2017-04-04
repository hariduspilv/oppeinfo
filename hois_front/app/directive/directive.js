'use strict';

angular.module('hitsaOis').controller('DirectiveSearchController', ['$location', '$q', '$scope', 'Classifier', 'QueryUtils',
  function ($location, $q, $scope, Classifier, QueryUtils) {
    var clMapper = Classifier.valuemapper({type: 'KASKKIRI', status: 'KASKKIRI_STAATUS'});
    QueryUtils.createQueryForm($scope, '/directives', {order: '-inserted'}, clMapper.objectmapper);

    $q.all(clMapper.promises).then($scope.loadData);
  }
]).controller('DirectiveEditController', ['$location', '$mdDialog', '$q', '$route', '$scope', 'dialogService', 'message', 'Curriculum', 'DataUtils', 'QueryUtils', 'Session',
  function ($location, $mdDialog, $q, $route, $scope, dialogService, message, Curriculum, DataUtils, QueryUtils, Session) {
    var id = $route.current.params.id;
    var baseUrl = '/directives';

    $scope.formState = {state: (id ? 'EDIT' : 'CHOOSETYPE'), students: undefined, selectedStudents: [],
                        excludedTypes: ['KASKKIRI_TYHIST', 'KASKKIRI_KYLALIS', 'KASKKIRI_IMMATV']};

    var school = Session.school || {};
    if(!school.higher) {
      $scope.formState.excludedTypes.push('KASKKIRI_OKOORM');
    }

    function setTemplateUrl() {
      var templateId = $scope.record.type ? $scope.record.type.substr(9).toLowerCase() : 'unknown';
      $scope.formState.templateUrl = 'directive/directive.type.'+templateId+'.edit.html';
    }

    function studentConverter(student) {
      return DataUtils.convertStringToDates(student, ['startDate', 'endDate']);
    }

    function afterLoad() {
      setTemplateUrl();
      $scope.record.students = studentConverter($scope.record.students);
    }

    function loadFormData() {
      var type = $scope.record.type;
      if(type === 'KASKKIRI_OKAVA' || type === 'KASKKIRI_IMMAT') {
        $scope.formState.curriculumVersions = Curriculum.queryVersions();
      }
      if(type === 'KASKKIRI_ENNIST' || type === 'KASKKIRI_IMMAT' || type === 'KASKKIRI_OKAVA' || type === 'KASKKIRI_OVORM') {
        $scope.formState.studentGroups = QueryUtils.endpoint('/autocomplete/studentgroups').query();
      }
      if(type === 'KASKKIRI_AKAD') {
        $scope.formState.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriods').query();
      }
    }

    var Endpoint = QueryUtils.endpoint(baseUrl);
    if(id) {
      $scope.record = Endpoint.get({id: id});
      $scope.record.$promise.then(afterLoad).then(loadFormData);
    } else {
      $scope.record = new Endpoint({students: []});
      afterLoad();
    }

    function formIsValid() {
      $scope.directiveForm.$setSubmitted();
      if(!$scope.directiveForm.$valid) {
        message.error('main.messages.form-has-errors');
        return false;
      }
      return true;
    }

    $scope.update = function() {
      if(!formIsValid()) {
        return;
      }

      if($scope.record.id) {
        $scope.record.$update().then(afterLoad).then(message.updateSuccess);
      }else{
        $scope.record.$save().then(function() {
          message.info('main.messages.create.success');
          $location.path(baseUrl + '/' + $scope.record.id + '/edit');
        });
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
        $scope.record.students = studentConverter(result.students);
      });
      loadFormData();
    };

    function storeStudents(students) {
      studentsToDirective(students, function(result) {
        for(var i = 0, cnt = result.students.length; i < cnt; i++) {
          $scope.record.students.push(studentConverter(result.students[i]));
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
      var students = $scope.record.students;
      if(directiveType === 'KASKKIRI_IMMAT') {
        students.push({startDate: null});
        return;
      }
      var data = {type: directiveType, directive: id};
      $mdDialog.show({
        controller: function($scope) {
          var baseUrl = '/directives/findstudents';
          $scope.formState = {selectedStudents: [], type: data.type};

          QueryUtils.createQueryForm($scope, baseUrl);

          $scope.cancel = $mdDialog.hide;
          $scope.select = function() {
            storeStudents($scope.formState.selectedStudents);
            $mdDialog.hide();
          };

          students = students.map(function(s) { return s.student; });
          $scope.loadData = function() {
            var query = angular.extend(QueryUtils.getQueryParams($scope.criteria), data);
            $scope.tabledata.$promise = QueryUtils.endpoint(baseUrl).search(query, function(resultData) {
              // filter these already on directive
              resultData.content = resultData.content.filter(function(r) { return students.indexOf(r.id) === -1;});
              $scope.afterLoadData(resultData);
            });
          };

          $scope.loadData();
        },
        templateUrl: 'directive/student.select.dialog.html',
        clickOutsideToClose: true
      });
    };

    $scope.lookupStudent = function(row) {
      var idcode = row.idcode;
      if(idcode && idcode.length === 11 && idcode !== row._idcode) {
        row._idcode = idcode;
        QueryUtils.endpoint('/autocomplete/persons').search({idcode: idcode, role: 'person'}).$promise.then(function(response) {
          row._found = true;
          row.firstname = response.firstname;
          row.lastname = response.lastname;
        }).catch(function(response) {
          row._found = false;
          return $q.reject(response);
        });
      }
    };

    $scope.sendToConfirm = function() {
      $scope.directiveForm.directiveCoordinator.$setValidity('required', !!$scope.record.directiveCoordinator);
      if(!formIsValid()) {
        return;
      }

      // save first
      $scope.record.$update().then(afterLoad).then(function() {
        QueryUtils.endpoint(baseUrl + '/sendtoconfirm').update({id: $scope.record.id}).$promise.then(function() {
          message.info('directive.sentToConfirm');
          $location.path(baseUrl + '/' + $scope.record.id + '/view');
        });
      });
    };
  }
]).controller('DirectiveViewController', ['$route', '$scope', 'dialogService', 'message', 'QueryUtils',
  function ($route, $scope, dialogService, message, QueryUtils) {
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

    // for testing only
    $scope.confirmDirective = function() {
      dialogService.confirmDialog({prompt: 'directive.confirmconfirm'}, function() {
        QueryUtils.endpoint(baseUrl + '/confirm').update($scope.record).$promise.then(function() {
          message.info('directive.confirmed');
          $route.reload();
        });
      });
    };
  }
]);
