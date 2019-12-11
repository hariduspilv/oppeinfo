'use strict';

angular.module('hitsaOis').controller('FinalHigherProtocolEditController', ['$route', '$location', '$scope', '$filter', '$q', 'Classifier', 'HigherGradeUtil', 'ProtocolUtils', 'QueryUtils', 'config' ,'dialogService', 'message', 'oisFileService',
function ($route, $location, $scope, $filter, $q, Classifier, HigherGradeUtil, ProtocolUtils, QueryUtils, config, dialogService, message, oisFileService) {
  $scope.gradeUtil = HigherGradeUtil;
  var endpoint = "/finalHigherProtocols";
  $scope.auth = $route.current.locals.auth;
  $scope.letterGrades = $scope.auth.school.letterGrades;
  $scope.record = $route.current.locals.entity;
  var FORBIDDEN_GRADES = ['KORGHINDAMINE_MI'];
  var clMapper = Classifier.valuemapper({ grade: 'KORGHINDAMINE', status: 'PROTOKOLL_STAATUS', studyLevel: 'OPPEASTE' });
  $scope.formState = {};
  var deferredEntityToDto;

  function filterGrades() {
    $scope.grades = $scope.grades.filter(function(it) {
      if (!$route.current.locals.isView) {
        return FORBIDDEN_GRADES.indexOf(it.code) === -1;
      }
    });
  }

  $scope.grades = Classifier.queryForDropdown({ mainClassCode: 'KORGHINDAMINE' }, filterGrades);
  $scope.grades.$promise.then(function () {
    $scope.grades = HigherGradeUtil.orderedGrades($scope.grades);
  });

  $scope.gradeSelectShownValue = function (grade) {
    return HigherGradeUtil.gradeSelectShownValue(grade, $scope.auth.school.letterGrades);
  };

  if ($route.current.locals.entity) {
    entityToDto($route.current.locals.entity);
  }

  function resolveDeferredIfExists() {
    if (angular.isDefined(deferredEntityToDto) && deferredEntityToDto.promise.$$state.status === 0) {
      deferredEntityToDto.resolve();
    }
  }

  function entityToDto(entity) {
    $q.all(clMapper.promises).then(function () {
      $scope.finalProtocolForm.$setPristine();
      $scope.protocol = clMapper.objectmapper(entity);
      $scope.record.protocolStudents.forEach(function (student) {
        if ($route.current.locals.isView) {
          student = clMapper.objectmapper(student);
        }
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
  
  $scope.hideInvalid = function (cl) {
    return !Classifier.isValid(cl);
  }

  $scope.gradeChanged = function(row) {
    if (row) {
      var savedResult = $filter('filter')($scope.savedStudents, {id: row.id}, true)[0];
      if (savedResult.grade !== row.grade) {
        $scope.finalProtocolForm.$setSubmitted();
        row.gradeHasChanged = true;
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

  function validationPassed(confirmValidation) {
    if (!$scope.finalProtocolForm.$valid) {
      message.error('main.messages.form-has-errors');
      $scope.finalProtocolForm.$setSubmitted();
      return false;
    }

    if (confirmValidation && angular.isArray($scope.protocol.protocolStudents)) {
      for (var i = 0; i < $scope.protocol.protocolStudents.length; i++) {
        var student = $scope.protocol.protocolStudents[i];
        if (HigherGradeUtil.isPositive(student.grade) && !student.curriculumGrade) {
          message.error('finalProtocol.error.curriculumGradeRequired');
          return false;
        } else if (!HigherGradeUtil.isPositive(student.grade) && student.curriculumGrade) {
          message.error('finalProtocol.error.curriculumGradeNotAllowed');
          return false;
        }
      }
    }

    return true;
  }

  $scope.confirm = function () {
    deferredEntityToDto = $q.defer();
    if(!validationPassed(true)) {
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
      if ($scope.committees[i].id = $scope.protocol.committee) {
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
    if(!validationPassed(false)) {
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

  function getStudentsWithResults(students) {
    var protocolStudents = $scope.protocol.protocolStudents;

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

