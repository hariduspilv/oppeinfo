'use strict';

angular.module('hitsaOis').controller('FinalVocationalProtocolEditController',
function ($location, $route, $scope, $q, GRADING_SCHEMA_TYPE, Classifier, GradingSchema, ProtocolUtils, QueryUtils, config, dialogService, message, oisFileService) {
  var endpoint = '/finalVocationalProtocols';
  $scope.auth = $route.current.locals.auth;
  var clMapper = Classifier.valuemapper({ status: 'PROTOKOLL_STAATUS', studyLevel: 'OPPEASTE' });
  var studentClMapper = Classifier.valuemapper({ status: 'OPPURSTAATUS' });
  var gradingSchema, gradeMapper;
  var hiddenGrades = ['KUTSEHINDAMINE_1', 'KUTSEHINDAMINE_X'];
  $scope.formState = {};
  var deferredEntityToDto;

  function resolveDeferredIfExists() {
    if (angular.isDefined(deferredEntityToDto) && deferredEntityToDto.promise.$$state.status === 0) {
      deferredEntityToDto.resolve();
    }
  }

  function setGradingSchema(entity) {
    gradingSchema = new GradingSchema(GRADING_SCHEMA_TYPE.VOCATIONAL);
    $q.all(gradingSchema.promises).then(function () {
      $scope.existsSchoolGradingSchema = gradingSchema.existsSchoolGradingSchema();
      $scope.grades = gradingSchema.gradeSelection(entity.protocolVdata.studyYear.id);
      $scope.grades.forEach(function (grade) {
        // hide classifier grades that were previously filtered
        if (!grade.gradingSchemaRowId && hiddenGrades.indexOf(grade.code) !== -1) {
          grade.valid = false;
        }
      });
      gradeMapper = gradingSchema.gradeMapper($scope.grades, ['grade']);
    });
  }

  function entityToDto(entity) {
    $q.all(clMapper.promises.concat(gradingSchema.promises)).then(function () {
      $scope.finalProtocolForm.$setPristine();
      $scope.protocol = clMapper.objectmapper(entity);
      $scope.protocol.protocolStudents.forEach(function (protocolStudent) {
        gradeMapper.objectmapper(protocolStudent);
      });
      $scope.savedStudents = angular.copy($scope.protocol.protocolStudents);

      if (!$route.current.locals.isView && $scope.protocol.finalDate) {
        $scope.committees = QueryUtils.endpoint(endpoint + '/committees').query({ finalDate: $scope.protocol.finalDate });
      }
      if ($scope.protocol.committee) {
        $scope.committeeMembers = $scope.protocol.committee.members;
        $scope.committeeMembers.forEach(function (it) {
          if ($scope.protocol.presentCommitteeMembers.indexOf(it.id) !== -1) {
            it.isPresent = true;
          }
        });
        $scope.protocol.committee = $scope.protocol.committee.id;
      }
      $scope.getUrl = oisFileService.getUrl;

      loadProtocolStudentOccupations();
      $scope.formState = {
        canEditProtocol: ProtocolUtils.canEditProtocol($scope.auth, $scope.protocol),
        canAddDeleteStudents: ProtocolUtils.canAddDeleteStudents($scope.auth, $scope.protocol),
        canConfirm: ProtocolUtils.canConfirm($scope.auth, $scope.protocol),
        canChangeConfirmedProtocolGrade: ProtocolUtils.canChangeConfirmedProtocolGrade($scope.auth, $scope.protocol),
        protocolPdfUrl: config.apiUrl + endpoint + '/' + entity.id + '/print/protocol.pdf'
      };
      resolveDeferredIfExists();
    }).catch(function () {
      resolveDeferredIfExists();
    });
  }

  $scope.gradeChanged = function(row) {
    ProtocolUtils.gradeChanged($scope.finalProtocolForm, $scope.savedStudents, row);
    $scope.formState.canConfirm = ProtocolUtils.canConfirm($scope.auth, $scope.protocol);
  };

  $scope.addInfoChanged = function () {
    $scope.formState.canConfirm = ProtocolUtils.canConfirm($scope.auth, $scope.protocol);
  };

  function loadProtocolStudentOccupations() {
    $scope.protocol.protocolStudents.forEach(function (protocolStudent) {
      if (!protocolStudent.occupations) {
        protocolStudent.occupations = [];
      }

      protocolStudent.curriculumOccupations.forEach(function (curriculumOccupation) {
        if (curriculumOccupation.partOccupationCode) {
          if (angular.isDefined(protocolStudent.occupations[curriculumOccupation.partOccupationCode])) {
            var addedCertificateNr = protocolStudent.occupations[curriculumOccupation.partOccupationCode].certificateNr;
            protocolStudent.occupations[curriculumOccupation.partOccupationCode].certificateNr = addedCertificateNr + ", " + curriculumOccupation.certificateNr;
          } else {
            protocolStudent.occupations[curriculumOccupation.partOccupationCode] = {
              code: curriculumOccupation.partOccupationCode,
              certificateNr: curriculumOccupation.certificateNr,
              ceritificateId: curriculumOccupation.studentOccupationCertificateId,
              granted: true
            };
          }
        } else if (!curriculumOccupation.partOccupationCode) {
          if (angular.isDefined(protocolStudent.occupations[curriculumOccupation.occupationCode])) {
            var addedCertificateNr = protocolStudent.occupations[curriculumOccupation.occupationCode].certificateNr;
            protocolStudent.occupations[curriculumOccupation.occupationCode].certificateNr = addedCertificateNr + ", " + curriculumOccupation.certificateNr;
          } else {
            protocolStudent.occupations[curriculumOccupation.occupationCode] = {
              code: curriculumOccupation.occupationCode,
              certificateNr: curriculumOccupation.certificateNr,
              ceritificateId: curriculumOccupation.studentOccupationCertificateId,
              granted: true
            };
          }
        }
      });
    });
  }

  if ($route.current.locals.entity) {
    setGradingSchema($route.current.locals.entity);
    entityToDto($route.current.locals.entity);
  }

  $scope.selectedFinalDateChanged = function () {
    QueryUtils.endpoint(endpoint + '/committees').query({ finalDate: $scope.protocol.finalDate }).$promise.then(function (committees) {
      $scope.committees = committees;
      if ($scope.protocol.committee && !isSelectedCommitteeAvailable()) {
        $scope.protocol.committee = null;
        $scope.committeeMembers = null;
      }
    });
  };

  function isSelectedCommitteeAvailable() {
    for (var i = 0; i < $scope.committees.length; i++) {
      if ($scope.committees[i].id === $scope.protocol.committee) {
        return true;
      }
    }
    return false;
  }

  $scope.selectedCommitteeChanged = function () {
    if ($scope.protocol.committee) {
      QueryUtils.endpoint("/committees").get({ id: $scope.protocol.committee }).$promise.then(function (committee) {
        $scope.committeeMembers = committee.members;
      });
    } else {
      $scope.committeeMembers = null;
    }
  };

  $scope.deleteProtocolStudent = function (protocolStudent) {
    dialogService.confirmDialog({prompt: 'finalProtocol.prompt.deleteStudent'}, function() {
      var ProtocolStudentEndpoint = QueryUtils.endpoint(endpoint + '/' + $scope.protocol.id + '/removeStudent');
      var removedStudent = new ProtocolStudentEndpoint(protocolStudent);
      removedStudent.$delete().then(function (protocol) {
          message.info('main.messages.delete.success');
          entityToDto(protocol);
      }).catch(angular.noop);
    });
  };

  function validationPassed() {
    if(!$scope.finalProtocolForm.$valid) {
      message.error('main.messages.form-has-errors');
      $scope.finalProtocolForm.$setSubmitted();
      return false;
    }
    return true;
  }

  $scope.addProtocolStudents = function () {
    dialogService.showDialog('finalProtocol/templates/final.protocol.add.student.dialog.html', function (dialogScope) {
      dialogScope.selectedStudents = [];
      var query = QueryUtils.endpoint(endpoint + '/' + $scope.protocol.id + '/otherStudents').query();
      dialogScope.tabledata = {
        $promise: query.$promise,
      };
      query.$promise.then(function (result) {
        dialogScope.tabledata.content = studentClMapper.objectmapper(result);
      });
    }, function (submittedDialogScope) {
      QueryUtils.endpoint(endpoint + '/' + $scope.protocol.id + '/addStudents').save({
        version: $scope.protocol.version,
        protocolStudents: submittedDialogScope.selectedStudents.map(function (it) { return { studentId: it }; })
      }, function (result) {
        message.info('finalProtocol.messages.studentSuccesfullyAdded');
        entityToDto(result);
      });
    });
  };

  $scope.confirm = function () {
    deferredEntityToDto = $q.defer();
    if(!validationPassed()) {
      resolveDeferredIfExists();
      return deferredEntityToDto.promise;
    }

    var data = {
      version: $scope.protocol.version,
      finalDate: $scope.protocol.finalDate,
      committeeId: $scope.protocol.committee,
      protocolCommitteeMembers: getPresentCommitteeMembers($scope.committeeMembers),
      protocolStudents: getStudentsWithOccupations()
    };

    ProtocolUtils.signBeforeConfirm($scope.auth, endpoint + '/' + $scope.protocol.id, data, 'finalProtocol.messages.confirmed',
      entityToDto, resolveDeferredIfExists);
    return deferredEntityToDto.promise;
  };

  var FinalVocationlProtocolEndpoint = QueryUtils.endpoint(endpoint);
  $scope.save = function () {
    if(!validationPassed()) {
      return false;
    }
    new FinalVocationlProtocolEndpoint({
      id: $scope.protocol.id,
      version: $scope.protocol.version,
      finalDate: $scope.protocol.finalDate,
      committeeId: $scope.protocol.committee,
      protocolCommitteeMembers: getPresentCommitteeMembers($scope.committeeMembers),
      protocolStudents: getStudentsWithOccupations() })
      .$update().then(function (result) {
        message.info('main.messages.create.success');
        entityToDto(result);
      }).catch(angular.noop);
  };

  function getStudentsWithOccupations() {
    var protocolStudents = $scope.protocol.protocolStudents;

    protocolStudents.forEach(function (student) {
      var occupationCodes = [];
      var codes = Object.keys(student.occupations);

      for (var i = 0; i < codes.length; i++) {
        if (student.occupations[codes[i]].granted) {
          occupationCodes.push(codes[i]);
        }
      }
      student.occupationCodes = occupationCodes;
    });

    return protocolStudents;
  }

  function getPresentCommitteeMembers(committeeMembers) {
    var presentCommitteeMembers = [];
    if (committeeMembers) {
      for (var i = 0; i < committeeMembers.length; i++) {
        if (committeeMembers[i].isPresent) {
          presentCommitteeMembers.push({committeeMemberId: committeeMembers[i].id});
        }
      }
    }
    return presentCommitteeMembers;
  }

  $scope.delete = function() {
    dialogService.confirmDialog({prompt: 'finalProtocol.prompt.delete'}, function() {
      new FinalVocationlProtocolEndpoint($scope.protocol).$delete().then(function(){
        message.info('finalProtocol.messages.deleted');
        $location.path(endpoint);
      }).catch(angular.noop);
    });
  };
});
