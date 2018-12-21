'use strict';

angular.module('hitsaOis').controller('DirectiveEditController', ['$location', '$mdDialog', '$q', '$route', '$scope', 'dialogService', 'message', 'resourceErrorHandler', 'Classifier', 'Curriculum', 'DataUtils', 'FormUtils', 'QueryUtils', 'Session', '$rootScope',
  function ($location, $mdDialog, $q, $route, $scope, dialogService, message, resourceErrorHandler, Classifier, Curriculum, DataUtils, FormUtils, QueryUtils, Session, $rootScope) {
    var id = $route.current.params.id;
    var canceledDirective = $route.current.params.canceledDirective;
    var baseUrl = '/directives';

    $scope.formState = {state: (id || canceledDirective ? 'EDIT' : 'CHOOSETYPE'), students: undefined, changedStudents: [],
                        selectedStudents: [], excludedTypes: ['KASKKIRI_KYLALIS'], school: Session.school || {},
                        higherStudyForms: Classifier.queryForDropdown({mainClassCode: 'OPPEVORM', higher: true}),
                        scholarshipEditable: false};

    if(!canceledDirective) {
      $scope.formState.excludedTypes.push('KASKKIRI_TYHIST');
    }

    var occupationMap;
    if(!$scope.formState.school.higher) {
      $scope.formState.excludedTypes.push('KASKKIRI_OKOORM');
      $scope.formState.excludedTypes.push('KASKKIRI_OVORM');
      $scope.formState.excludedTypes.push('KASKKIRI_VALIS');
      var occupations = Classifier.queryForDropdown({mainClassCodes: 'KUTSE,OSAKUTSE'});
      occupations.$promise.then(function() {
        occupationMap = Classifier.toMap(occupations);
      });
    }

    function setScholarshipEditable() {
        $scope.formState.scholarshipEditable = ['STIPTOETUS_ERI', 'STIPTOETUS_SOIDU', 'STIPTOETUS_DOKTOR'].indexOf($scope.record.scholarshipType) !== -1;
    }

    function setTemplateUrl() {
      var templateId = $scope.record.type ? $scope.record.type.substr(9).toLowerCase() : 'unknown';
      $scope.formState.templateUrl = 'directive/directive.type.'+templateId+'.edit.html';
    }

    function mapStudentOccupations(student) {
      if (student.occupations) {
        student.occupations = student.occupations.map(function(code) {
          return occupationMap[code];
        });
      }
    }

    function studentConverter(students) {
      var result = DataUtils.convertStringToDates(students, ['startDate', 'endDate']);
      if (occupationMap) {
        if (angular.isArray(result)) {
          result.forEach(mapStudentOccupations);
        } else {
          mapStudentOccupations(result);
        }
      }
      return result;
    }

    function afterLoad(result) {
      if (angular.isDefined(result) && result.canEditDirective === false) {
        message.error("main.messages.error.nopermission");
        $rootScope.back("#/directives/" + result.id + "/view");
      }
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
        setScholarshipEditable();
        $scope.record.students = studentConverter($scope.record.students);
        if(result && result.type === 'KASKKIRI_IMMAT') {
          for(var i = 0, cnt = $scope.record.students.length; i < cnt; i++) {
            $scope.record.students[i]._found = true;
            $scope.record.students[i]._idcode = $scope.record.students[i].idcode;
            $scope.record.students[i]._foreign = !$scope.record.students[i].idcode;
          }
        }
      }
    }

    // load curriculums
    $scope.formState.curriculumVersionMap = {};
    $scope.formState.languageMap = {};
    $scope.formState.curriculumVersions = Curriculum.queryVersions({languages: true});
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
                  students: students.map(function(i) { return i.id; }), isHigher: $scope.record.isHigher};

      QueryUtils.endpoint(baseUrl+'/directivedata').save(data, callback);
    }

    var Endpoint = QueryUtils.endpoint(baseUrl);
    if(id) {
      $scope.formState.curriculumVersions.$promise.then(function() {
        $scope.record = Endpoint.get({id: id});
        $scope.record.$promise.then(afterLoad).then(loadFormData).catch(function () {
          $scope.back("#/");
        });
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

    function displayFormErrors(response) {
      // failure, required fields are not filled
      resourceErrorHandler.responseError(response, $scope.directiveForm).catch(angular.noop);
    }

    function beforeSave() {
      if($scope.record.type === 'KASKKIRI_TYHIST') {
        $scope.record.selectedStudents = $scope.formState.selectedStudents.map(function(it) { return it.student;});
      }
    }

    function clearCtrlError(ctrl, name) {
      ctrl.$serverError = undefined;
      ctrl.$setValidity(name, null);
    }

    function clearErrors() {
      var invalidCtrls = $scope.directiveForm.$error;
      if(invalidCtrls) {
        Object.keys(invalidCtrls).forEach(function(name) {
          var ctrls = invalidCtrls[name].slice();
            ctrls.forEach(function(ctrl) { clearCtrlError(ctrl, name); });
        });
      }
    }

    $scope.update = function() {
      clearErrors();
      FormUtils.withValidForm($scope.directiveForm, function() {
        beforeSave();
        if($scope.record.id) {
          $scope.record.$update2().then(function(record) {
            afterLoad(record);
            $scope.directiveForm.$setPristine();
            message.updateSuccess();
          }).catch(displayFormErrors);
        }else{
          $scope.record.$save2().then(function() {
            message.info('main.messages.create.success');
            $location.url(baseUrl + '/' + $scope.record.id + '/edit?_noback');
          }).catch(displayFormErrors);
        }
      });
    };

    $scope.delete = function() {
      FormUtils.deleteRecord($scope.record, baseUrl + '?_noback', {prompt: 'directive.deleteconfirm'});
    };

    $scope.directiveTypeChanged = function() {
      var data = {type: $scope.record.type};
      if(data.type === 'KASKKIRI_EKSMAT') {
        data.application = true;
      } else if(data.type === 'KASKKIRI_LOPET') {
        if (!angular.isDefined($scope.record.isHigher)) {
          var school = $scope.formState.school;
          if (school.higher && school.vocational) {
            return; // let user choose
          } else {
            $scope.record.isHigher = school.higher;
          }
        }
        data.isHigher = $scope.record.isHigher;
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
      setScholarshipEditable();
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

    $scope.deleteStudent = function(row) {
      var rows = $scope.record.students;
      for(var i = 0, cnt = rows.length; i < cnt; i++) {
        if(rows[i] === row) {
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

          $scope.$watch('criteria.studentGroupObject', function () {
            $scope.criteria.studentGroup = $scope.criteria.studentGroupObject ? $scope.criteria.studentGroupObject.id : null;
          });

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
        clickOutsideToClose: false
      });
    };

    $scope.foreignChanged = function(row) {
      if(row._foreign) {
        row.foreignIdcode = row.idcode;
        row.idcode = undefined;
      } else {
        row.idcode = row.foreignIdcode;
        row.foreignIdcode = undefined;
      }
      row._idcode = undefined;
      $scope.lookupStudent(row);
    };

    $scope.lookupStudent = function(row) {
      var ctrlName = 'students[' + $scope.record.students.indexOf(row) +'].idcode';
      var ctrl = $scope.directiveForm[ctrlName];
      if(ctrl) {
        clearCtrlError(ctrl, 'serverside');
      }
      var idcode = row._foreign ? row.foreignIdcode : row.idcode;
      if(idcode && (idcode.length === 11 || row._foreign) && idcode !== row._idcode) {
        row._idcode = idcode;
        QueryUtils.endpoint('/autocomplete/persons', {search: {method: 'GET'}}).search(row._foreign ? {foreignIdcode: idcode, role: 'foreignidcode'} : {idcode: idcode, role: 'person'}).$promise.then(function(response) {
          row._found = true;
          row.firstname = response.firstname;
          row.lastname = response.lastname;
          row.birthdate = response.birthdate || DataUtils.birthdayFromIdcode(idcode);
          row.sex = response.sex || DataUtils.sexFromIdcode(idcode);
          row.citizenship = response.citizenship;
        }).catch(function(response) {
          if(response.status === 404) {
            message.info('directive.newperson');
          } else if(response.status === 412) {
            ctrl.$serverError = ctrl.$serverError || [];
            ctrl.$serverError.push({code: 'InvalidEstonianIdCode'});
            ctrl.$setValidity('serverside', false);
          } else {
            displayFormErrors(response);
          }
          row._found = false;
          row.firstname = undefined;
          row.lastname = undefined;
          if(!row._foreign && response.status === 404) {
            row.birthdate = DataUtils.birthdayFromIdcode(idcode);
            row.sex = DataUtils.sexFromIdcode(idcode);
            row.citizenship = 'RIIK_EST';
          } else {
            row.birthdate = undefined;
            row.sex = undefined;
            row.citizenship = undefined;
          }
        });
      } else if(idcode !== row._idcode) {
        row._found = false;
        if(!row._foreign && idcode && ctrl) {
          row.birthdate = undefined;
          row.sex = undefined;
          ctrl.$serverError = ctrl.$serverError || [];
          ctrl.$serverError.push({code: 'InvalidEstonianIdCode'});
          ctrl.$setValidity('serverside', false);
        }
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

    $scope.sendToConfirm = function(ekis) {
      clearErrors();
      $scope.directiveForm.directiveCoordinator.$setValidity('required', !!$scope.record.directiveCoordinator);
      FormUtils.withValidForm($scope.directiveForm, function() {
        var deletedInvalid = false;
        function sendToConfirm() {
          // save first
          beforeSave();
          $scope.record.$update2().then(function(record) {
            afterLoad(record);
            $scope.directiveForm.$setPristine();

            QueryUtils.endpoint(baseUrl + '/sendtoconfirm/' + $scope.record.id + '?ekis=' + ekis, {put: {method: 'PUT'}}).put().$promise.then(function(response) {
              var invalidStudents = response ? response.invalidStudents : undefined;
              if(invalidStudents && invalidStudents.length > 0) {
                if(!deletedInvalid) {
                  // invalid students on directive, ask confirmation for delete
                  dialogService.showDialog('directive/invalid.student.delete.dialog.html', function(scope) {
                    scope.students = invalidStudents;
                  }, function() {
                    // remove invalid students and do the whole process again
                    invalidStudents.forEach(function(it) {
                      var row = $scope.record.students.find(function(s) { return s.student === it.student; });
                      if(row) {
                        $scope.deleteStudent(row);
                      }
                    });
                    deletedInvalid = true;
                    sendToConfirm();
                  });
                }
              } else {
                message.info('directive.sentToConfirm');
                $location.url(baseUrl + '/' + $scope.record.id + '/view?_noback');
              }
            }).catch(displayFormErrors);
          }).catch(displayFormErrors);
        }
        QueryUtils.endpoint(baseUrl + '/checkForConfirm').get({id: $scope.record.id}).$promise.then(function(check) {
          var template = (check.templateName || []).join('", "');
          dialogService.confirmDialog({prompt: (check.templateExists ? 'directive.ekisconfirm' : 'directive.ekisconfirmTemplateMissing'), template: template}, sendToConfirm);
        }).catch(angular.noop);
      });
    };
  }
]).controller('DirectiveViewController', ['$location', '$route', '$scope', 'dialogService', 'message', 'Classifier', 'QueryUtils', 'FormUtils', 'Session',
  function ($location, $route, $scope, dialogService, message, Classifier, QueryUtils, FormUtils, Session) {
    var id = $route.current.params.id;
    var baseUrl = '/directives';
    var clMapper = Classifier.valuemapper({status: 'KASKKIRI_STAATUS'});

    $scope.formState = {school: Session.school || {}};
    
    var occupations;
    var occupationMap;
    if(!$scope.formState.school.higher) {
      occupations = Classifier.queryForDropdown({mainClassCodes: 'KUTSE,OSAKUTSE'});
      occupations.$promise.then(function() {
        occupationMap = Classifier.toMap(occupations);
      });
    }

    $scope.record = QueryUtils.endpoint(baseUrl + '/:id/view').search({id: id});

    $scope.record.$promise.then(function() {
      var templateId = $scope.record.type === 'KASKKIRI_TYHIST' ? $scope.record.canceledDirectiveType.substr(9).toLowerCase() : ($scope.record.type ? $scope.record.type.substr(9).toLowerCase() : 'unknown');
      $scope.formState.templateUrl = 'directive/directive.type.'+templateId+'.view.html';
      clMapper.objectmapper($scope.record.cancelingDirectives || []);
      $scope.record.students.forEach(function(it) {
        it._foreign = !it.idcode;
      });
      if (occupations) {
        occupations.$promise.then(function() {
          if (occupationMap) {
            $scope.record.students.forEach(function(it) {
              if (it.occupations) {
                it.occupations = it.occupations.map(function(code) {
                  return occupationMap[code];
                });
              }
            });
          }
        });
      }
    });

    $scope.cancelDirective = function() {
      dialogService.confirmDialog({prompt: 'directive.cancelconfirm'}, function() {
        $location.url('/directives/new?canceledDirective=' + $scope.record.id + '&_noback');
      });
    };

    $scope.userCanConfirm = function() {
      return $scope.record.status === 'KASKKIRI_STAATUS_KINNITAMISEL' && $scope.record.userCanConfirm;
    };

    // for testing only
    $scope.confirmDirective = function() {
      FormUtils.withValidForm($scope.directiveForm, function() {
        QueryUtils.endpoint(baseUrl + '/checkForConfirm').get({id: $scope.record.id}).$promise.then(function(check) {
          var template = (check.templateName || []).join('", "');
          dialogService.confirmDialog({prompt: (check.templateExists ? 'directive.confirmconfirm' : 'directive.confirmconfirmTemplateMissing'), template: template}, function() {
            QueryUtils.endpoint(baseUrl + '/confirm/' + $scope.record.id).update($scope.confirmRecord).$promise.then(function() {
              message.info('directive.confirmed');
              $route.reload();
            }).catch(angular.noop);
          });
        }).catch(angular.noop);
      });
    };
  }
]).controller('DirectiveListController', ['$q', '$scope', 'Classifier', 'QueryUtils', 'Session',
  function ($q, $scope, Classifier, QueryUtils, Session) {

    $scope.formState = {excludedTypes: ['KASKKIRI_KYLALIS'], school: Session.school || {}};
    if(!$scope.formState.school.higher) {
      $scope.formState.excludedTypes.push('KASKKIRI_OKOORM');
      $scope.formState.excludedTypes.push('KASKKIRI_OVORM');
      $scope.formState.excludedTypes.push('KASKKIRI_VALIS');
    }

    var clMapper = Classifier.valuemapper({type: 'KASKKIRI', status: 'KASKKIRI_STAATUS'});
    QueryUtils.createQueryForm($scope, '/directives', {order: '-inserted'}, clMapper.objectmapper);

    $q.all(clMapper.promises).then($scope.loadData);
  }
]);
