'use strict';

angular.module('hitsaOis').controller('DirectiveEditController', ['$location', '$mdDialog', '$q', '$route', '$scope', 'dialogService', 'message', 'resourceErrorHandler', 'Classifier', 'Curriculum', 'DataUtils', 'FormUtils', 'QueryUtils', 'Session', '$rootScope', 'ArrayUtils', '$timeout',
  function ($location, $mdDialog, $q, $route, $scope, dialogService, message, resourceErrorHandler, Classifier, Curriculum, DataUtils, FormUtils, QueryUtils, Session, $rootScope, ArrayUtils, $timeout) {
    var id = $route.current.params.id;
    var canceledDirective = $route.current.params.canceledDirective;
    var baseUrl = '/directives';
    var today;
    $scope.getMinDate = maxDate;

    $scope.formState = {state: (id || canceledDirective ? 'EDIT' : 'CHOOSETYPE'), students: undefined, changedStudents: [],
                        selectedStudents: [], excludedTypes: [], school: Session.school || {},
                        higherStudyForms: Classifier.queryForDropdown({mainClassCode: 'OPPEVORM', higher: true}),
                        scholarshipEditable: false};

    $scope.withoutInitialSelection = [
      'KASKKIRI_IMMAT', 'KASKKIRI_IMMATV', 'KASKKIRI_KIITUS', 'KASKKIRI_NOOMI', 'KASKKIRI_OTEGEVUS',
      'KASKKIRI_PRAKTIK', 'KASKKIRI_INDOK', 'KASKKIRI_KYLALIS'
    ];

    if(!canceledDirective) {
      $scope.formState.excludedTypes.push('KASKKIRI_TYHIST');
    }

    var occupationMap;
    var specialitiesMap;
    if(!$scope.formState.school.higher) {
      $scope.formState.excludedTypes.push('KASKKIRI_OKOORM');
      $scope.formState.excludedTypes.push('KASKKIRI_OVORM');
      var occupations = Classifier.queryForDropdown({mainClassCodes: 'KUTSE,OSAKUTSE'});
      occupations.$promise.then(function() {
        occupationMap = Classifier.toMap(occupations);
      });
      var specialities = Classifier.queryForDropdown({mainClassCodes: 'SPETSKUTSE'});
      specialities.$promise.then(function() {
        specialitiesMap = Classifier.toMap(specialities);
      });
    }
    if(!$scope.formState.school.vocational) {
      $scope.formState.excludedTypes.push('KASKKIRI_INDOK');
      $scope.formState.excludedTypes.push('KASKKIRI_INDOKLOP');
      $scope.formState.excludedTypes.push('KASKKIRI_KIITUS');
      $scope.formState.excludedTypes.push('KASKKIRI_NOOMI');
      $scope.formState.excludedTypes.push('KASKKIRI_OTEGEVUS');
      $scope.formState.excludedTypes.push('KASKKIRI_TUGI');
      $scope.formState.excludedTypes.push('KASKKIRI_TUGILOPP');
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

    function mapStudentSpecialities(student) {
      if (student.specialities) {
        Object.keys(student.specialities).forEach(function (occupation) {
          student.specialities[occupation] = student.specialities[occupation].map(function(code) {
            return specialitiesMap[code];
          });
        });
      }
    }

    function studentConverter(students) {
      var result = DataUtils.convertStringToDates(students, ['startDate', 'endDate', 'studentNominalStudyEnd']);
      if (angular.isArray(result)) {
        if (occupationMap) {
          result.forEach(mapStudentOccupations);
          result.forEach(mapStudentSpecialities);
        }
        result.forEach(typeSpecificConverts);
      } else {
        if (occupationMap) {
          mapStudentOccupations(result);
          mapStudentSpecialities(result);
        }
        typeSpecificConverts(result);
      }
      return result;
    }

    function typeSpecificConverts(student) {
      switch ($scope.record.type) {
        case 'KASKKIRI_INDOKLOP':
          if (!student.directiveStudent && student.existingDirectiveStudents && student.existingDirectiveStudents.length === 1) {
            student.directiveStudent = student.existingDirectiveStudents[0].id;
          }
          break;
        case 'KASKKIRI_STIPTOET':
          if (!student.scholarshipApplication && student.scholarshipApplications && student.scholarshipApplications.length === 1) {
            student.scholarshipApplication = student.scholarshipApplications[0].id;
            $scope.scholarshipApplicationChanged(student);
          }
          break;
        case 'KASKKIRI_STIPTOETL':
          if (!student.directiveStudent && student.existingDirectiveStudents && student.existingDirectiveStudents.length === 1) {
            student.directiveStudent = student.existingDirectiveStudents[0].id;
            $scope.scholarshipDirectiveChanged(student);
          }
          break;
        case 'KASKKIRI_TUGILOPP':
          $scope.mapStudentDirective(student);
          if (!student.directiveStudent && student.existingDirectiveStudents && student.existingDirectiveStudents.length === 1) {
            student.directiveStudent = student.existingDirectiveStudents[0].id;
          }
          break;
        case 'KASKKIRI_VALISKATK':
          $scope.mapStudentDirective(student);
          if (!student.directiveStudent && student.existingDirectiveStudents && student.existingDirectiveStudents.length === 1) {
            student.directiveStudent = student.existingDirectiveStudents[0].id;
          }
          break;
      }
    }

    function afterLoad(result) {
      today = new Date(); // After every reloading it should update `today` becuase
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
        mapForeign(result);
        $scope.cancelTypeChanged();
      } else {
        setTemplateUrl();
        setScholarshipEditable();
        $scope.record.students = studentConverter($scope.record.students);
        mapForeign(result);
        checkHasForeignStudent();
      }
    }

    function mapForeign(result) {
      if(result && (result.type === 'KASKKIRI_IMMAT' || result.type === 'KASKKIRI_KYLALIS' || result.type === 'KASKKIRI_TYHIST')) {
        for(var i = 0, cnt = $scope.record.students.length; i < cnt; i++) {
          $scope.record.students[i]._found = true;
          $scope.record.students[i]._idcode = $scope.record.students[i].idcode;
          $scope.record.students[i]._foreign = !$scope.record.students[i].idcode;
        }
      }
    }

    // load curriculums
    function loadCurriculums() {
      $scope.formState.curriculumVersionMap = {};
      $scope.formState.languageMap = {};
      var params = {languages: true, valid: true};
      if ($scope.record !== undefined && $scope.record.isHigher !== undefined) {
        params.higher = $scope.record.isHigher;
      }
      $scope.formState.curriculumVersions = Curriculum.queryVersions(params);
      $scope.formState.curriculumVersions.$promise.then(function(result) {
        $scope.formState.curriculumVersionMap = result.reduce(function(acc, item) { acc[item.id] = item; return acc; }, {});
        for(var i = 0; i < result.length; i++) {
          var cv = result[i];
          $scope.formState.languageMap[cv.id] = cv.studyLang || [];
        }
      });
    }

    loadCurriculums();

    $scope.$watchCollection('formState.selectedStudents', function (newValue) {
      $scope.formState.selectedStudentIds = (newValue || []).reduce(function (map, object) {
        map[object.id] = object;
        return map;
      }, {});
    });

    $scope.setApelSchoolCountry = function(row) {
      var apelSchool = $scope.formState.apelSchools.filter(function (apelSchool) {
        return apelSchool.id === row.apelSchoolId;
      })[0];
      if (apelSchool !== undefined) {
        row.country = apelSchool.country;
      }
    };

    function loadFormData() {
      var type = $scope.record.type;
      if(['KASKKIRI_ENNIST', 'KASKKIRI_IMMAT', 'KASKKIRI_IMMATV', 'KASKKIRI_OKAVA', 'KASKKIRI_OVORM', 'KASKKIRI_KYLALIS'].indexOf(type) !== -1) {
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
        if (type === 'KASKKIRI_KYLALIS') {
          $scope.formState.apelSchools = QueryUtils.endpoint('/autocomplete/apelschools').query();
          $scope.formState.apelSchoolStudyLevels = Classifier.queryForDropdown({
            mainClassCode: 'EHIS_KODU_OPPEASTE',
            order: 'code',
            vocational: $scope.formState.school.vocational ? true : undefined,
            higher: $scope.formState.school.higher ? true : undefined
          });
        }
      }
      if (type === 'KASKKIRI_VALIS') {
        $scope.formState.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriodsWithYear').query({}, function (response) {
          response.forEach(function (studyPeriod) {
            studyPeriod.display = $scope.currentLanguageNameField(studyPeriod.studyYear) + ' ' + $scope.currentLanguageNameField(studyPeriod);
          });
        });
      }
      if(type === 'KASKKIRI_AKAD') {
        $scope.formState.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriodsWithYear').query();
      }
    }

    /**
     *
     * @param {Array<Number>} students
     * @param {Map<Number, Number>=} studentApplicationRelationship
     * @param {Function} callback
     */
    function studentsToDirective(students, studentApplicationRelationship, callback) {
      var data = {id: $scope.record.id, type: $scope.record.type, scholarshipType: $scope.record.scholarshipType,
        canceledDirective: canceledDirective, curriculumVersion: $scope.formState.curriculumVersion,
        studyLevel: $scope.formState.studyLevel, students: students.map(function(i) { return i.id; }),
        isHigher: $scope.record.isHigher};

      if ($scope.record.type === 'KASKKIRI_TUGI') {
        if (!studentApplicationRelationship) {
          studentApplicationRelationship = {};
        }
        data.studentApplication = {};
        students.forEach(function (student) {
          data.studentApplication[student.id] = studentApplicationRelationship[student.id];
        });
      }
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
        studentsToDirective([], {}, function(result) {
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
      // wait for elements to render
      $timeout(function () {
        resourceErrorHandler.responseError(response, $scope.directiveForm).catch(angular.noop);
      });
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
      $scope.formState.students = [];
      $scope.formState.selectedStudents = [];

      var data = {type: $scope.record.type};
      if(data.type === 'KASKKIRI_EKSMAT') {
        data.application = true;
      } else if(data.type === 'KASKKIRI_LOPET' || data.type === 'KASKKIRI_KYLALIS') {
        if (!angular.isDefined($scope.record.isHigher)) {
          var school = $scope.formState.school;
          if (school.higher && school.vocational) {
            return; // let user choose
          } else {
            $scope.record.isHigher = school.higher;
          }
        }
        data.isHigher = $scope.record.isHigher;
        loadCurriculums();
      }

      var scholarship = data.type === 'KASKKIRI_STIPTOET' || data.type === 'KASKKIRI_STIPTOETL';
      if(scholarship) {
        data.scholarshipType = $scope.record.scholarshipType;
      }
      if($scope.withoutInitialSelection.indexOf(data.type) === -1) {
        if(!scholarship || data.scholarshipType) {
          QueryUtils.endpoint(baseUrl+'/findstudents').search(data, function(result) {
            $scope.formState.students = result.content;
            if(!$scope.formState.students.length) {
              message.info('directive.nostudentsfound');
            }
          });
        }
      } else {
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

    $scope.$watchCollection('formState.selectedStudents', function (newValues) {
      if ($scope.record !== undefined && $scope.record.type === 'KASKKIRI_TYHIST') {
        // If all students are selected, pick KASKKIRI_TYHISTAMISE_VIIS_T, else pick KASKKIRI_TYHISTAMISE_VIIS_O
        var allSelected = true;
        $scope.record.students.forEach(function(student) {
          if (!ArrayUtils.contains(newValues, student)) {
            allSelected = false;
          }
        });
        if (allSelected && $scope.formState.changedStudents.length === 0) {
          $scope.record.cancelType = 'KASKKIRI_TYHISTAMISE_VIIS_T';
        } else if (!allSelected && $scope.record.students.length !== 1){
          $scope.record.cancelType = 'KASKKIRI_TYHISTAMISE_VIIS_O';
        }
      }
    });

    $scope.addDirective = function() {
      if ($scope.record.type === 'KASKKIRI_TUGI' || $scope.record.type === 'KASKKIRI_KYLALIS') {
        $scope.directiveForm.$setSubmitted();
        if(!$scope.directiveForm.$valid) {
          message.error('main.messages.form-has-errors');
          return;
        }
      }
      $scope.formState.state = 'EDIT';
      studentsToDirective($scope.formState.selectedStudents, $scope.formState.studentApplicationRelationship, function(result) {
        angular.copy(result.toJSON(), $scope.record);
        $scope.record.students = studentConverter(result.students);
      });
      loadFormData();
    };

    /**
     *
     * @param {Array<Number>} students
     * @param {Map<Number, Number>=} studentApplicationRelationship
     */
    function storeStudents(students, studentApplicationRelationship) {
      studentsToDirective(students, studentApplicationRelationship, function(result) {
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
      if(directiveType === 'KASKKIRI_IMMAT' || directiveType === 'KASKKIRI_KYLALIS') {
        students.push({startDate: null});
        return;
      }
      var data = {type: directiveType, directive: id};
      if(directiveType === 'KASKKIRI_STIPTOET' || directiveType === 'KASKKIRI_STIPTOETL') {
        data.scholarshipType = $scope.record.scholarshipType;
      }
      if (directiveType === 'KASKKIRI_LOPET') {
        data.isHigher = $scope.record.isHigher;
      }
      $mdDialog.show({
        controller: function($scope) {
          var baseUrl = '/directives/findstudents';
          $scope.formState = {selectedStudents: [], type: data.type, studentApplicationRelationship: {}};

          QueryUtils.createQueryForm($scope, baseUrl);

          $scope.$watch('criteria.studentGroupObject', function () {
            $scope.criteria.studentGroup = $scope.criteria.studentGroupObject ? $scope.criteria.studentGroupObject.id : null;
          });

          $scope.$watchCollection('formState.selectedStudents', function (newValue) {
            $scope.formState.selectedStudentIds = (newValue || []).reduce(function (map, object) {
              map[object.id] = object;
              return map;
            }, {});
          });

          $scope.cancel = $mdDialog.hide;
          $scope.select = function() {
            storeStudents($scope.formState.selectedStudents, $scope.formState.studentApplicationRelationship);
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
      checkHasForeignStudent();
      if(row._foreign) {
        row.foreignIdcode = row.idcode;
        row.idcode = undefined;
        row._idcode = undefined;
      } else {
        row.idcode = row.foreignIdcode;
        row._idcode = undefined;
        row.foreignIdcode = undefined;
      }
      row._found = false;
      $timeout(function() {
        $scope.lookupStudent(row);
      });
    };

    function checkHasForeignStudent() {
      var isForeignStudent = false;
      $scope.record.students.forEach(function(student) {
        if (student._foreign) {
          isForeignStudent = true;
        }
      });
      $scope.hasForeignStudent = isForeignStudent;
    }

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
            displayFormErrors(response);
          } else {
            displayFormErrors(response);
          }
          row._found = false;
          if(!row._foreign && response.status === 404) {
            row.birthdate = DataUtils.birthdayFromIdcode(idcode);
            row.sex = DataUtils.sexFromIdcode(idcode);
            row.citizenship = 'RIIK_EST';
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

    $scope.curriculumVersionChanged = function(row) {
      var curriculumVersion = $scope.formState.curriculumVersionMap[row.curriculumVersion];
      if(curriculumVersion && curriculumVersion.isVocational) {
        row.studyForm = curriculumVersion.studyForm;
        row.dormitory = row.dormitory ? row.dormitory : 'YHISELAMU_E';
        row.studyLoad = null;
      } else {
        row.dormitory = null;
      }
      row.curriculumVersionObject = curriculumVersion;
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
        $scope.formState.selectedStudents = [];
        $scope.record.students.forEach(function(it) {
          it.selectable = ($scope.formState.changedStudents.indexOf(it.student) === -1);
        });
      }
    };

    $scope.sendToConfirm = function(ekis) {
      clearErrors();
      $scope.directiveForm.directiveCoordinator.$setValidity('required', !!$scope.record.directiveCoordinator);
      if ($scope.record.type === 'KASKKIRI_TYHIST') {
        $scope.directiveForm.cancelType.$setValidity('required', !!$scope.record.cancelType);
      }
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

    $scope.allStudyActivityChange = function(variable, newValue) {
      if (variable !== 'isAbsence') {
        if (!$scope.record.studyActivityStartDate || !$scope.record.studyActivityEndDate) {
          $scope.record.studyActivityIsAbsence = false;
          $scope.allStudyActivityChange("isAbsence", false);
        }
      }
      if (!newValue) {
        return;
      }
      $scope.record.students.forEach(function (s) {
        s[variable] = newValue;
      });
    };

    $scope.studyActivityDateChange = function(row) {
      if (!row.startDate || !row.endDate) {
        row.isAbsence = false;
      }
    };

    $scope.scholarshipApplicationChanged = function(row) {
      if (row.scholarshipApplication) {
        var application = row.scholarshipApplications.filter(function (sa) {
          return row.scholarshipApplication === sa.id;
        })[0];
        row.startDate = application.startDate;
        row.endDate = application.endDate;
        row.bankAccount = application.bankAccount;
        row.amountPaid = application.amountPaid;
      } else {
        row.startDate = null;
        row.endDate = null;
        row.bankAccount = null;
        row.amountPaid = null;
      }
    };

    $scope.scholarshipDirectiveChanged = function(row) {
      if (row.directiveStudent) {
        var directiveStudent = row.existingDirectiveStudents.filter(function (ds) {
          return row.directiveStudent === ds.id;
        })[0];
        row.startDate = directiveStudent.startDate;
        row.endDate = directiveStudent.endDate;
        row.scholarshipApplication = directiveStudent.scholarshipApplicationId;
      } else {
        row.startDate = null;
        row.endDate = null;
        row.scholarshipApplication = null;
      }
    };

    /**
     * Works for row.directiveStudent. We need to know object parameters as well. Not only ID.
     */
    $scope.mapStudentDirective = function(row) {
      row.directiveStudentObject = (row.existingDirectiveStudents || []).find(function (r) {
        return r.id === row.directiveStudent;
      });
      if (row.directiveStudentObject && typeof row.directiveStudentObject.endDate === 'string') {
        row.directiveStudentObject = DataUtils.convertStringToDates(row.directiveStudentObject, ['startDate', 'endDate']);
      }
    };

    $scope.individualCurriculumModules = function(student, studentIndex) {
      dialogService.showDialog('directive/indok.modules.dialog.html', function (dialogScope) {
        var studentId = student.student;
        dialogScope.formState = { viewForm: $scope.record.type === 'KASKKIRI_TYHIST', selectedModules: [] };
        dialogScope.student = student;
        dialogScope.existingIndividualCurriculums = false;

        QueryUtils.endpoint(baseUrl + '/studentIndividualCurriculumModules/:id').query({
          id: studentId,
          directiveId: id
        }).$promise.then(function (result) {
          dialogScope.curriculumVersionModules = result;

          (dialogScope.curriculumVersionModules || []).forEach(function (cvModule) {
            // should not exist on any other individual curriculum
            var valid = true;

            if (cvModule.existingModules) {
              dialogScope.existingIndividualCurriculums = true;

              for (var i = 0; i < cvModule.existingModules.length; i++) {
                var existingModuleStart = new Date(cvModule.existingModules[i].startDate);
                existingModuleStart.setHours(0, 0, 0, 0);
                var existingModuleEnd = new Date(cvModule.existingModules[i].endDate);
                existingModuleEnd.setHours(0, 0, 0, 0);

                if (DataUtils.isValidObject(dialogScope.student.startDate, dialogScope.student.endDate, existingModuleStart,
                    existingModuleEnd)) {
                  valid = false;
                  break;
                }
              }
            }
            cvModule.valid = valid;
          });

          if (dialogScope.student.modules) {
            dialogScope.student.modules.forEach(function (individualModule) {
              var curriculumModule = (dialogScope.curriculumVersionModules || []).filter(function (cvModule) {
                return cvModule.id === individualModule.curriculumVersionOmodule;
              })[0];

              if (curriculumModule) {
                if (curriculumModule.valid) {
                  dialogScope.formState.selectedModules.push(individualModule.curriculumVersionOmodule);
                }
                curriculumModule.directiveStudentModuleId = individualModule.id;
                curriculumModule.addInfo = individualModule.addInfo;
              }
            });
          }
        });

        dialogScope.changeModules = function () {
          dialogScope.submit();
        };
      }, function (submittedDialogScope) {
        var modules = [];
        submittedDialogScope.curriculumVersionModules.forEach(function (module) {
          if (submittedDialogScope.formState.selectedModules.indexOf(module.id) !== -1) {
            modules.push({
              id: module.directiveStudentModuleId,
              curriculumVersionOmodule: module.id,
              module: {id: module.id, nameEt: module.nameEt, nameEn: module.nameEn},
              addInfo: module.addInfo
            });
          }
        });
        $scope.record.students[studentIndex].modules = modules;
      });
    };

    /**
     * Compares dates and return the highest.
     *
     * Used for TUGI to set `mdMinDate` for `endDate`.
     * It should be not lesser than `today` or `startDate`. If `startDate` is lesser than `today` then it should return `today`
     *
     * @param {Date=} [date]
     * @returns `date` if exists and more than `today`. Otherwise `today`
     */
    function maxDate(date) {
      if (date instanceof Date && !isNaN(date)) {
        return date > today ? date : today;
      }
      return today;
    }
  }
]).controller('DirectiveViewController', ['$location', '$route', '$scope', 'dialogService', 'message', 'Classifier', 'QueryUtils', 'FormUtils', 'Session',
  function ($location, $route, $scope, dialogService, message, Classifier, QueryUtils, FormUtils, Session) {
    var id = $route.current.params.id;
    var baseUrl = '/directives';
    var clMapper = Classifier.valuemapper({status: 'KASKKIRI_STAATUS'});

    $scope.formState = {school: Session.school || {}};

    var occupationMap;
    var specialitiesMap;
    if(!$scope.formState.school.higher) {
      var occupations = Classifier.queryForDropdown({mainClassCodes: 'KUTSE,OSAKUTSE'});
      occupations.$promise.then(function() {
        occupationMap = Classifier.toMap(occupations);
      });
      var specialities = Classifier.queryForDropdown({mainClassCodes: 'SPETSKUTSE'});
      specialities.$promise.then(function() {
        specialitiesMap = Classifier.toMap(specialities);
      });
    }

    $scope.record = QueryUtils.endpoint(baseUrl + '/:id/view').search({id: id});

    $scope.record.$promise.then(function() {
      var templateId = $scope.record.type === 'KASKKIRI_TYHIST' ? $scope.record.canceledDirectiveType.substr(9).toLowerCase() : ($scope.record.type ? $scope.record.type.substr(9).toLowerCase() : 'unknown');
      $scope.formState.templateUrl = 'directive/directive.type.'+templateId+'.view.html';
      clMapper.objectmapper($scope.record.cancelingDirectives || []);
      $scope.record.students.forEach(function(it) {
        it._foreign = !it.idcode;
        mapStudentOccupations(it);
        mapStudentSpecialities(it);
      });
    });

    function mapStudentOccupations(student) {
      if (student.occupations) {
        student.occupations = student.occupations.map(function(code) {
          return occupationMap[code];
        });
      }
    }

    function mapStudentSpecialities(student) {
      if (student.specialities) {
        Object.keys(student.specialities).forEach(function (occupation) {
          student.specialities[occupation] = student.specialities[occupation].map(function(code) {
            return specialitiesMap[code];
          });
        });
      }
    }

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
          if ((check.validationError || []).length > 0) {
            message.error(check.validationError[0].code, check.validationError[0].params);
          } else {
            dialogService.confirmDialog({prompt: (check.templateExists ? 'directive.confirmconfirm' : 'directive.confirmconfirmTemplateMissing'), template: template}, function() {
              QueryUtils.endpoint(baseUrl + '/confirm/' + $scope.record.id).update($scope.confirmRecord).$promise.then(function() {
                message.info('directive.confirmed');
                $route.reload();
              }).catch(angular.noop);
            });
          }
        }).catch(angular.noop);
      });
    };

    $scope.individualCurriculumModules = function(student) {
      dialogService.showDialog('directive/indok.modules.dialog.html', function (dialogScope) {
        var studentId = student.student;
        dialogScope.formState = { viewForm: true, selectedModules: [] };
        dialogScope.student = student;

        QueryUtils.endpoint(baseUrl + '/studentIndividualCurriculumModules/:id').query({id: studentId, directiveId: id}).$promise.then(function (result) {
          dialogScope.curriculumVersionModules = result;

          if (dialogScope.student.modules) {
            dialogScope.student.modules.forEach(function (individualModule) {
              dialogScope.formState.selectedModules.push(individualModule.curriculumVersionOmodule);

              var curriculumModule = (dialogScope.curriculumVersionModules || []).filter(function (cvModule) {
                return cvModule.id === individualModule.curriculumVersionOmodule;
              })[0];
              if (curriculumModule) {
                curriculumModule.directiveStudentModuleId = individualModule.id;
                curriculumModule.addInfo = individualModule.addInfo;
              }
            });
          }
        });
      });
    };
  }
]).controller('DirectiveListController', ['$q', '$scope', 'Classifier', 'QueryUtils', 'Session',
  function ($q, $scope, Classifier, QueryUtils, Session) {

    $scope.formState = {excludedTypes: [], school: Session.school || {}};
    if(!$scope.formState.school.higher) {
      $scope.formState.excludedTypes.push('KASKKIRI_OKOORM');
      $scope.formState.excludedTypes.push('KASKKIRI_OVORM');
      $scope.formState.excludedTypes.push('KASKKIRI_VALIS');
    }
    if(!$scope.formState.school.vocational) {
      $scope.formState.excludedTypes.push('KASKKIRI_INDOK');
      $scope.formState.excludedTypes.push('KASKKIRI_INDOKLOP');
      $scope.formState.excludedTypes.push('KASKKIRI_KIITUS');
      $scope.formState.excludedTypes.push('KASKKIRI_NOOMI');
      $scope.formState.excludedTypes.push('KASKKIRI_OTEGEVUS');
      $scope.formState.excludedTypes.push('KASKKIRI_TUGI');
      $scope.formState.excludedTypes.push('KASKKIRI_TUGILOPP');
    }

    var clMapper = Classifier.valuemapper({type: 'KASKKIRI', status: 'KASKKIRI_STAATUS'});
    QueryUtils.createQueryForm($scope, '/directives', {order: '-inserted'}, clMapper.objectmapper);

    $q.all(clMapper.promises).then($scope.loadData);
  }
]);
