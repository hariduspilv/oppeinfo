'use strict';

angular.module('hitsaOis').controller('FinalHigherProtocolEditController', ['$route', '$location', '$scope', '$q', 'GRADING_SCHEMA_TYPE', 'Classifier', 'DataUtils', 'GradingSchema', 'HigherGradeUtil', 'ProtocolUtils', 'QueryUtils', 'config' ,'dialogService', 'message', 'oisFileService',
function ($route, $location, $scope, $q, GRADING_SCHEMA_TYPE, Classifier, DataUtils, GradingSchema, HigherGradeUtil, ProtocolUtils, QueryUtils, config, dialogService, message, oisFileService) {
  var endpoint = "/finalHigherProtocols";
  $scope.auth = $route.current.locals.auth;
  $scope.letterGrades = $scope.auth.school.letterGrades;
  $scope.record = $route.current.locals.entity;
  var FORBIDDEN_GRADES = ['KORGHINDAMINE_MI'];
  var clMapper = Classifier.valuemapper({ status: 'PROTOKOLL_STAATUS', studyLevel: 'OPPEASTE' });
  var gradingSchema, gradeMapper;
  $scope.formState = {};
  var deferredEntityToDto;

  function setGradingSchema(entity) {
    gradingSchema = new GradingSchema(GRADING_SCHEMA_TYPE.HIGHER);
    $q.all(gradingSchema.promises).then(function () {
      $scope.existsSchoolGradingSchema = gradingSchema.existsSchoolGradingSchema();
      $scope.grades = gradingSchema.gradeSelection(entity.studyYearId);
      $scope.grades.forEach(function (grade) {
        // hide classifier grades that were previously filtered
        if (!grade.gradingSchemaRowId && FORBIDDEN_GRADES.indexOf(grade.code) !== -1) {
          grade.valid = false;
        }
      });
      gradeMapper = gradingSchema.gradeMapper($scope.grades, ['grade']);
    });
  }

  if ($route.current.locals.entity) {
    setGradingSchema($route.current.locals.entity);
    entityToDto($route.current.locals.entity);
  }

  function resolveDeferredIfExists() {
    if (angular.isDefined(deferredEntityToDto) && deferredEntityToDto.promise.$$state.status === 0) {
      deferredEntityToDto.resolve();
    }
  }

  function entityToDto(entity) {
    $q.all(clMapper.promises.concat(gradingSchema.promises)).then(function () {
      $scope.finalProtocolForm.$setPristine();
      $scope.protocol = clMapper.objectmapper(entity);
      $scope.record.protocolStudents.forEach(function (student) {
        gradeMapper.objectmapper(student);
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

      for (var i = 0; i < $scope.protocol.protocolStudents.length; i++) {
        var student = $scope.protocol.protocolStudents[i];
        var canGrantOccupation = true;

        for (var j = 0; j < student.curriculumOccupations.length; j++) {
          if (student.curriculumOccupations[j].studentOccupationCertificateId) {
            canGrantOccupation = false;
            break;
          }
        }
        student.canGrantOccupation = canGrantOccupation;
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
    if (row) {
      var savedResult = $scope.savedStudents.find(function (student) { return student.id === row.id; });
      if (!DataUtils.isSameGrade(savedResult.grade, row.grade)) {
        $scope.finalProtocolForm.$setSubmitted();
        row.gradeHasChanged = true;
        if (HigherGradeUtil.isPositive(row.grade) && !!row.finalThesisCurriculumGrade && !(row.curriculumGrade || {}).id) {
          row.curriculumGrade = {id: row.finalThesisCurriculumGrade};
        }
      } else {
        row.gradeHasChanged = false;

        if (!savedResult.addInfo) {
          row.addInfo = null;
        } else {
          row.addInfo = savedResult.addInfo;
        }
      }
    }
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
        if (curriculumOccupation.occupationCode) {
          protocolStudent.occupations[curriculumOccupation.occupationCode] = {
            code: curriculumOccupation.occupationCode,
            certificateNr: curriculumOccupation.certificateNr,
            ceritificateId: curriculumOccupation.studentOccupationCertificateId,
            granted: true
          };
        }
      });
    });
  }

  function validationPassed() {
    if (!$scope.finalProtocolForm.$valid) {
      message.error('main.messages.form-has-errors');
      $scope.finalProtocolForm.$setSubmitted();
      return false;
    }
    return true;
  }

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
      protocolStudents: getStudentsWithResults($scope.protocol.protocolStudents)
    };

    ProtocolUtils.signBeforeConfirm($scope.auth, endpoint + '/' + $scope.protocol.id, data, 'finalProtocol.messages.confirmed',
      entityToDto, resolveDeferredIfExists);
    return deferredEntityToDto.promise;
  };

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

  $scope.setCurriculumGrade = function (protocolStudent, gradeId) {
    $scope.finalProtocolForm.$setDirty();
    if (protocolStudent.curriculumGrade && protocolStudent.curriculumGrade.id === gradeId) {
      protocolStudent.curriculumGrade = null;
    } else {
      protocolStudent.curriculumGrade = {id: gradeId};
    }
  };

  $scope.setOccupation = function (protocolStudent, occupationCode) {
    if (!protocolStudent.canGrantOccupation) {
      return;
    }

    for (var occupation in protocolStudent.occupations) {
      if (occupation !== occupationCode) {
        protocolStudent.occupations[occupation] = false;
      }
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

  var FinalHigherProtocolEndpoint = QueryUtils.endpoint(endpoint);
  $scope.save = function () {
    if(!validationPassed()) {
      return false;
    }

    new FinalHigherProtocolEndpoint({
      id: $scope.protocol.id,
      version: $scope.protocol.version,
      finalDate: $scope.protocol.finalDate,
      committeeId: $scope.protocol.committee,
      protocolCommitteeMembers: getPresentCommitteeMembers($scope.committeeMembers),
      protocolStudents: getStudentsWithResults($scope.protocol.protocolStudents) })
      .$update().then(function (result) {
        message.info('main.messages.create.success');
        entityToDto(result);
      }).catch(angular.noop);
  };

  function getStudentsWithResults(protocolStudents) {
    protocolStudents.forEach(function (student) {
      var occupation = null;
      var codes = Object.keys(student.occupations);

      for (var i = 0; i < codes.length; i++) {
        if (student.occupations[codes[i]].granted) {
          occupation = codes[i];
        }
      }
      student.occupationCode = occupation;
      student.curriculumGradeId = student.curriculumGrade ? student.curriculumGrade.id : null;
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
      new FinalHigherProtocolEndpoint($scope.protocol).$delete().then(function(){
        message.info('finalProtocol.messages.deleted');
        $location.path(endpoint);
      }).catch(angular.noop);
    });
  };

}]);

