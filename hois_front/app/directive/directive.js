'use strict';

angular.module('hitsaOis').controller('DirectiveEditController', ['$location', '$mdDialog', '$q', '$route', '$scope', 'dialogService', 'message', 'Classifier', 'Curriculum', 'DataUtils', 'QueryUtils', 'Session',
  function ($location, $mdDialog, $q, $route, $scope, dialogService, message, Classifier, Curriculum, DataUtils, QueryUtils, Session) {
    var id = $route.current.params.id;
    var canceledDirective = $route.current.params.canceledDirective;
    var baseUrl = '/directives';

    $scope.formState = {state: (id || canceledDirective ? 'EDIT' : 'CHOOSETYPE'), students: undefined, changedStudents: [],
                        selectedStudents: [], excludedTypes: ['KASKKIRI_KYLALIS'], school: Session.school || {},
                        higherStudyForms: Classifier.queryForDropdown({mainClassCode: 'OPPEVORM', higher: true})};

    if(!canceledDirective) {
      $scope.formState.excludedTypes.push('KASKKIRI_TYHIST');
    }

    if(!$scope.formState.school.higher) {
      $scope.formState.excludedTypes.push('KASKKIRI_OKOORM');
      $scope.formState.excludedTypes.push('KASKKIRI_OVORM');
      $scope.formState.excludedTypes.push('KASKKIRI_VALIS');
    }

    function setTemplateUrl() {
      var templateId = $scope.record.type ? $scope.record.type.substr(9).toLowerCase() : 'unknown';
      $scope.formState.templateUrl = 'directive/directive.type.'+templateId+'.edit.html';
    }

    function studentConverter(student) {
      return DataUtils.convertStringToDates(student, ['startDate', 'endDate']);
    }

    function afterLoad(result) {
      $scope.formState.cancelSelect = (result && result.type === 'KASKKIRI_TYHIST');
      if($scope.formState.cancelSelect) {
        var templateId = result.canceledDirectiveType ? result.canceledDirectiveType.substr(9).toLowerCase() : 'unknown';
        $scope.formState.templateUrl = 'directive/directive.type.'+templateId+'.view.html';
        $scope.formState.canceledDirective = result.canceledDirectiveData;
        $scope.formState.changedStudents = result.changedStudents || [];
        var selectedStudents = result.selectedStudents || [];
        $scope.record.students = (result.canceledStudents || []).map(function(it) {it.selectable = $scope.formState.changedStudents.indexOf(it.student) === -1; return it;});
        $scope.formState.selectedStudents = $scope.record.students.filter(function(it) { return selectedStudents.indexOf(it.student) !== -1;});
        delete result.canceledDirectiveType;
        delete result.canceledDirectiveData;
        delete result.changedStudents;
        delete result.canceledStudents;
        delete result.selectedStudents;

        $scope.cancelTypeChanged();
      } else {
        setTemplateUrl();
        $scope.record.students = studentConverter($scope.record.students);
        if(result && result.type === 'KASKKIRI_IMMAT') {
          for(var i = 0, cnt = $scope.record.students.length; i < cnt; i++) {
            $scope.record.students[i]._found = true;
            $scope.record.students[i]._idcode = $scope.record.students[i].idcode;
          }
        }
      }
    }

    // load curriculums
    $scope.formState.curriculumVersionMap = {};
    $scope.formState.languageMap = {};
    $scope.formState.curriculumVersions = Curriculum.queryVersions({valid: true, languages: true});
    $scope.formState.curriculumVersions.$promise.then(function(result) {
      $scope.formState.curriculumVersionMap = result.reduce(function(acc, item) { acc[item.id] = item; return acc; }, {});
      for(var i = 0; i < result.length; i++) {
        var cv = result[i];
        $scope.formState.languageMap[cv.id] = cv.studyLang || [];
      }
    });

    function loadFormData() {
      var type = $scope.record.type;
      if(['KASKKIRI_ENNIST', 'KASKKIRI_IMMAT', 'KASKKIRI_IMMATV', 'KASKKIRI_OKAVA', 'KASKKIRI_OVORM'].indexOf(type) !== -1) {
        $scope.formState.studentGroups = QueryUtils.endpoint('/autocomplete/studentgroups').query({valid: true});
        if($scope.formState.curriculumVersions) {
          // create mapping curriculumversion -> all possible student groups
          $q.all([$scope.formState.studentGroups.$promise, $scope.formState.curriculumVersions.$promise]).then(function() {
            var groups = $scope.formState.studentGroups;

            function getCurriculumVersionIds(sg) {
              return sg.curriculumVersion ? [sg.curriculumVersion] : $scope.formState.curriculumVersions.filter(function(it) {
                return it.curriculum === sg.curriculum;
              }).map(function(it) { return it.id; });
            }

            $scope.formState.studentGroupMap = {};
            for(var i = 0; i < groups.length; i++) {
              var sg = groups[i];
              var cvids = getCurriculumVersionIds(sg);

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
      if(type === 'KASKKIRI_AKAD' || type === 'KASKKIRI_VALIS') {
        $scope.formState.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriods').query();
      }
    }

    function studentsToDirective(students, callback) {
      var data = {type: $scope.record.type, scholarshipType: $scope.record.scholarshipType, canceledDirective: canceledDirective,
                  curriculumVersion: $scope.formState.curriculumVersion, studyLevel: $scope.formState.studyLevel,
                  students: students.map(function(i) { return i.id; })};

      QueryUtils.endpoint(baseUrl+'/directivedata').save(data, callback);
    }

    var Endpoint = QueryUtils.endpoint(baseUrl);
    if(id) {
      $scope.formState.curriculumVersions.$promise.then(function() {
        $scope.record = Endpoint.get({id: id});
        $scope.record.$promise.then(afterLoad).then(loadFormData);
      });
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

    function beforeSave() {
      if($scope.record.type === 'KASKKIRI_TYHIST') {
        $scope.record.selectedStudents = $scope.formState.selectedStudents.map(function(it) { return it.student;});
      }
    }

    $scope.update = function() {
      if(!formIsValid()) {
        return;
      }

      beforeSave();
      if($scope.record.id) {
        $scope.record.$update().then(afterLoad).then(message.updateSuccess);
      }else{
        $scope.record.$save().then(function() {
          message.info('main.messages.create.success');
          $location.url(baseUrl + '/' + $scope.record.id + '/edit?_noback');
        });
      }
    };

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'directive.deleteconfirm'}, function() {
        $scope.record.$delete().then(function() {
          message.info('main.messages.delete.success');
          $location.url(baseUrl + '?_noback');
        });
      });
    };

    $scope.directiveTypeChanged = function() {
      var data = {type: $scope.record.type};
      if(data.type === 'KASKKIRI_EKSMAT') {
        data.application = true;
      }
      var scholarship = data.type === 'KASKKIRI_STIPTOET' || data.type === 'KASKKIRI_STIPTOETL';
      if(scholarship) {
        data.scholarshipType = $scope.record.scholarshipType;
      }
      if(data.type !== 'KASKKIRI_IMMAT' && data.type !== 'KASKKIRI_IMMATV') {
        if(!scholarship || data.scholarshipType) {
          QueryUtils.endpoint(baseUrl+'/findstudents').search(data, function(result) {
            $scope.formState.students = result.content;
            $scope.formState.selectedStudents = [];
            if(!$scope.formState.students.length) {
              message.info('directive.nostudentsfound');
            }
          });
        }
      } else {
        $scope.formState.students = [];
        if(data.type === 'KASKKIRI_IMMATV' && !$scope.formState.saisCurriculumVersions) {
          $scope.formState.saisCurriculumVersions = Curriculum.queryVersions({sais: true});
        }
      }
      setTemplateUrl();
    };

    $scope.scholarshipTypeChanged = function() {
      if($scope.record.scholarshipType) {
        $scope.directiveTypeChanged();
      }
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
      if(directiveType === 'KASKKIRI_STIPTOET' || directiveType === 'KASKKIRI_STIPTOETL') {
        data.scholarshipType = $scope.record.scholarshipType;
      }
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
        QueryUtils.endpoint('/autocomplete/persons', {search: {method: 'GET'}}).search({idcode: idcode, role: 'person'}).$promise.then(function(response) {
          row._found = true;
          row.firstname = response.firstname;
          row.lastname = response.lastname;
        }).catch(function(response) {
          message.info('directive.newperson');
          row._found = false;
          row.firstname = undefined;
          row.lastname = undefined;
          return $q.reject(response);
        });
      } else if(idcode !== row._idcode) {
        row._found = false;
      }
    };

    $scope.abroadChanged = function(row) {
      var est = 'RIIK_EST';
      if(!row.isAbroad) {
        row.country = est;
      } else if(row.country === est) {
        row.country = null;
      }
    };

    $scope.curriculumVersionChanged = function(row) {
      var curriculumVersion = $scope.formState.curriculumVersionMap[row.curriculumVersion];
      if(curriculumVersion && curriculumVersion.isVocational) {
        row.studyForm = curriculumVersion.studyForm;
      }
    };

    $scope.visibleStudyForms = function(curriculumVersion) {
      if(!curriculumVersion) {
        return undefined;
      }
      curriculumVersion = $scope.formState.curriculumVersionMap[curriculumVersion];
      if(curriculumVersion && curriculumVersion.isVocational) {
        return [curriculumVersion.studyForm];
      }
      // higher, choose from classifier
      return $scope.formState.higherStudyForms;
    };

    $scope.cancelTypeChanged = function() {
      if($scope.record.cancelType === 'KASKKIRI_TYHISTAMISE_VIIS_T') {
        var selected = [];
        $scope.record.students.forEach(function(it) {
          it.selectable = false;
          selected.push(it);
        });
        $scope.formState.selectedStudents = selected;
      } else if($scope.record.cancelType === 'KASKKIRI_TYHISTAMISE_VIIS_O') {
        $scope.record.students.forEach(function(it) {
          it.selectable = ($scope.formState.changedStudents.indexOf(it.student) === -1);
        });
      }
    };

    $scope.sendToConfirm = function() {
      $scope.directiveForm.directiveCoordinator.$setValidity('required', !!$scope.record.directiveCoordinator);
      if(!formIsValid()) {
        return;
      }

      dialogService.confirmDialog({prompt: 'directive.ekisconfirm'}, function() {
        // save first
        beforeSave();
        $scope.record.$update().then(afterLoad).then(function() {
          QueryUtils.endpoint(baseUrl + '/sendtoconfirm').update({id: $scope.record.id, form: function() { return $scope.directiveForm;}}).$promise.then(function() {
            message.info('directive.sentToConfirm');
            $location.url(baseUrl + '/' + $scope.record.id + '/view?_noback');
          });
        });
      });
    };
  }
]).controller('DirectiveViewController', ['$location', '$route', '$scope', 'dialogService', 'message', 'Classifier', 'QueryUtils', 'Session',
  function ($location, $route, $scope, dialogService, message, Classifier, QueryUtils, Session) {
    var id = $route.current.params.id;
    var baseUrl = '/directives';
    var clMapper = Classifier.valuemapper({status: 'KASKKIRI_STAATUS'});

    $scope.formState = {school: Session.school || {}};
    $scope.record = QueryUtils.endpoint(baseUrl + '/:id/view').search({id: id});

    $scope.record.$promise.then(function() {
      var templateId = $scope.record.type === 'KASKKIRI_TYHIST' ? $scope.record.canceledDirectiveType.substr(9).toLowerCase() : ($scope.record.type ? $scope.record.type.substr(9).toLowerCase() : 'unknown');
      $scope.formState.templateUrl = 'directive/directive.type.'+templateId+'.view.html';
      clMapper.objectmapper($scope.record.cancelingDirectives || []);
    });

    $scope.cancelDirective = function() {
      dialogService.confirmDialog({prompt: 'directive.cancelconfirm'}, function() {
        $location.url('/directives/new?canceledDirective=' + $scope.record.id + '&_noback');
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
]).controller('DirectiveListController', ['$q', '$route', '$scope', 'Classifier', 'QueryUtils', 'Session',
  function ($q, $route, $scope, Classifier, QueryUtils, Session) {

    $scope.formState = {excludedTypes: ['KASKKIRI_KYLALIS'], school: Session.school || {}};
    if(!$scope.formState.school.higher) {
      $scope.formState.excludedTypes.push('KASKKIRI_OKOORM');
      $scope.formState.excludedTypes.push('KASKKIRI_OVORM');
      $scope.formState.excludedTypes.push('KASKKIRI_VALIS');
    }

    var clMapping = $route.current.locals.clMapping;
    var clMapper = clMapping ? Classifier.valuemapper(clMapping) : undefined;
    QueryUtils.createQueryForm($scope, $route.current.locals.url, $route.current.locals.params, clMapper ? clMapper.objectmapper : undefined);

    if(clMapper) {
      $q.all(clMapper.promises).then($scope.loadData);
    } else {
      $scope.loadData();
    }
  }
]);
