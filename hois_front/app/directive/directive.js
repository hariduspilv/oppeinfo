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
    var canceledDirective = $route.current.params.canceledDirective;
    var baseUrl = '/directives';

    $scope.formState = {state: (id || canceledDirective ? 'EDIT' : 'CHOOSETYPE'), students: undefined,
                        selectedStudents: [], excludedTypes: ['KASKKIRI_KYLALIS']};
    if(!canceledDirective) {
      $scope.formState.excludedTypes.push('KASKKIRI_TYHIST');
    }

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

    function afterLoad(result) {
      if(result && result.type === 'KASKKIRI_TYHIST') {
        var templateId = result.canceledDirectiveType ? result.canceledDirectiveType.substr(9).toLowerCase() : 'unknown';
        $scope.formState.templateUrl = 'directive/directive.type.'+templateId+'.view.html';
        $scope.record.students = result.canceledStudents;
        $scope.formState.canceledDirective = result.canceledDirectiveData;
        delete result.canceledDirectiveType;
        delete result.canceledDirectiveData;
        delete result.canceledStudents;
      } else {
        setTemplateUrl();
        $scope.record.students = studentConverter($scope.record.students);
      }
    }

    function loadFormData() {
      var type = $scope.record.type;
      if(type === 'KASKKIRI_ENNIST' || type === 'KASKKIRI_IMMAT' || type === 'KASKKIRI_IMMATV' || type === 'KASKKIRI_OKAVA') {
        $scope.formState.curriculumVersions = Curriculum.queryVersions();
        $scope.formState.curriculumVersions.$promise.then(function(result) {
          $scope.formState.curriculumVersionMap = result.reduce(function(acc, item) { acc[item.id] = item; return acc; }, {});
        });
      }
      if(type === 'KASKKIRI_ENNIST' || type === 'KASKKIRI_IMMAT' || type === 'KASKKIRI_IMMATV' || type === 'KASKKIRI_OKAVA' || type === 'KASKKIRI_OVORM') {
        $scope.formState.studentGroups = QueryUtils.endpoint('/autocomplete/studentgroups').query();
        if($scope.formState.curriculumVersions) {
          // create mapping curriculumversion -> all possible student groups
          $q.all([$scope.formState.studentGroups.$promise, $scope.formState.curriculumVersions.$promise]).then(function() {
            var groups = $scope.formState.studentGroups;
            $scope.formState.studentGroupMap = {};
            for(var i = 0; i < groups.length; i++) {
              var sg = groups[i];
              var cvids = sg.curriculumVersion ? [sg.curriculumVersion] : $scope.formState.curriculumVersions.filter(function(it) { return it.curriculum === sg.curriculum;}).map(function(it) { return it.id;});
              for(var j = 0; j < cvids.length; j++) {
                var cv = cvids[j];
                var cvgroups = $scope.formState.studentGroupMap[cv];
                if(!cvgroups) {
                  cvgroups = [];
                  $scope.formState.studentGroupMap[cv] = cvgroups;
                }
                cvgroups.push(sg);
              }
            }
          });
        }
      }
      if(type === 'KASKKIRI_AKAD') {
        $scope.formState.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriods').query();
      }
    }

    function studentsToDirective(students, callback) {
      var data = {type: $scope.record.type, canceledDirective: canceledDirective,
                  curriculumVersion: $scope.formState.curriculumVersion, studyLevel: $scope.formState.studyLevel,
                  students: students.map(function(i) { return i.id; })};

      QueryUtils.endpoint(baseUrl+'/directivedata').save(data, callback);
    }

    var Endpoint = QueryUtils.endpoint(baseUrl);
    if(id) {
      $scope.record = Endpoint.get({id: id});
      $scope.record.$promise.then(afterLoad).then(loadFormData);
    } else {
      $scope.record = new Endpoint({students: []});
      if(canceledDirective) {
        $scope.record.type = 'KASKKIRI_TYHIST';
        studentsToDirective([], function(result) {
          var data = result.toJSON();
          angular.copy(data, $scope.record);
          afterLoad(data);
        });
      } else {
        afterLoad();
      }
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
      if(data.type !== 'KASKKIRI_IMMAT' && data.type !== 'KASKKIRI_IMMATV') {
        QueryUtils.endpoint(baseUrl+'/findstudents').search(data, function(result) {
          $scope.formState.students = result.content;
          $scope.formState.selectedStudents = [];
          if(!$scope.formState.students.length) {
            message.info('directive.nostudentsfound');
          }
        });
      } else {
        $scope.formState.students = [];
        if(data.type === 'KASKKIRI_IMMATV' && !$scope.formState.saisCurriculumVersions) {
          $scope.formState.saisCurriculumVersions = Curriculum.queryVersions({sais: true});
        }
      }
      setTemplateUrl();
    };

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
]).controller('DirectiveViewController', ['$location', '$route', '$scope', 'dialogService', 'message', 'QueryUtils',
  function ($location, $route, $scope, dialogService, message, QueryUtils) {
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
        $location.url('/directives/new?canceledDirective=' + $scope.record.id);
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
