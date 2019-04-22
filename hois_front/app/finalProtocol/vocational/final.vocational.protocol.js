'use strict';

angular.module('hitsaOis').controller('FinalVocationalProtocolEditController', 
function ($scope, $route, $filter, $q, QueryUtils, Classifier, ProtocolUtils, VocationalGradeUtil, oisFileService, config, message, dialogService, $location) {
  var endpoint = '/finalVocationalProtocols';
  $scope.gradeUtil = VocationalGradeUtil;
  $scope.auth = $route.current.locals.auth;
  var clMapper = Classifier.valuemapper({ grade: 'KUTSEHINDAMINE', status: 'PROTOKOLL_STAATUS', studyLevel: 'OPPEASTE' });
  var studentClMapper = Classifier.valuemapper({ journalResults: 'KUTSEHINDAMINE', status: 'OPPURSTAATUS' });
  var editForbiddenGrades = ['KUTSEHINDAMINE_1', 'KUTSEHINDAMINE_X'];
  var viewForbiddenGrades = ['KUTSEHINDAMINE_X'];
  var allGrades = Classifier.queryForDropdown({ mainClassCode: 'KUTSEHINDAMINE' });
  $scope.formState = {};
  var deferredEntityToDto;

  allGrades.$promise.then(function (result) {
    $scope.grades = result.filter(function (it) {
      if ($route.current.locals.isView) {
        return viewForbiddenGrades.indexOf(it.code) === -1;
      } else {
        return editForbiddenGrades.indexOf(it.code) === -1;
      }
    });
  });

  function resolveDeferredIfExists() {
    if (angular.isDefined(deferredEntityToDto) && deferredEntityToDto.promise.$$state.status === 0) {
      deferredEntityToDto.resolve();
    }
  }

  function entityToDto(entity) {
    $q.all(clMapper.promises).then(function () {
      $scope.finalProtocolForm.$setPristine();
      $scope.protocol = clMapper.objectmapper(entity);
      $scope.savedStudents = angular.copy($scope.protocol.protocolStudents);

      if ($scope.protocol.finalDate) {
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
        if (curriculumOccupation.partOccupationCode) {
          protocolStudent.occupations[curriculumOccupation.partOccupationCode] = {
            code: curriculumOccupation.partOccupationCode,
            certificateNr: curriculumOccupation.certificateNr,
            ceritificateId: curriculumOccupation.studentOccupationCertificateId,
            granted: true
          };
        } else if (!curriculumOccupation.partOccupationCode) {
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

  if ($route.current.locals.entity) {
    entityToDto($route.current.locals.entity);
  } else if ($scope.protocol) {
    loadGradesSelect();
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
      if(!validationPassed()) {
        return false;
      }

      if (submittedDialogScope.selectedStudents.length === 0) {
        return false;
      }
      
      var addedStudents = submittedDialogScope.selectedStudents.map(function (it) { return { studentId: it, occupations: [] }; });
      addedStudents.forEach(function (addedStudent) {
        $scope.protocol.protocolStudents.push(addedStudent);
      });
      $scope.save();
    });
  };

  $scope.gradeValue = function (code) {
    var grade = clMapper.objectmapper({ grade: code }).grade;
    return grade ? grade.value : undefined;
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
      protocolStudents: getStudentsWithOccupations($scope.protocol.protocolStudents)
    };

    if ($scope.auth.loginMethod === 'LOGIN_TYPE_I') {
      ProtocolUtils.signBeforeConfirm(endpoint + '/' + $scope.protocol.id, data, 'finalProtocol.messages.confirmed', entityToDto, resolveDeferredIfExists);
    } else if ($scope.auth.loginMethod === 'LOGIN_TYPE_M') {
      ProtocolUtils.mobileSignBeforeConfirm(endpoint + '/' + $scope.protocol.id, data, 'finalProtocol.messages.confirmed', entityToDto, resolveDeferredIfExists);
    }
    return deferredEntityToDto.promise;
  };

  var FinalVocationlProtocolEndpoint = QueryUtils.endpoint(endpoint);
  $scope.save = function () {
    var protocolStudents = getStudentsWithOccupations($scope.protocol.protocolStudents);
    if(!validationPassed()) {
      return false;
    }
    new FinalVocationlProtocolEndpoint({ 
      id: $scope.protocol.id, 
      version: $scope.protocol.version,
      finalDate: $scope.protocol.finalDate,
      committeeId: $scope.protocol.committee,
      protocolCommitteeMembers: getPresentCommitteeMembers($scope.committeeMembers),
      protocolStudents: getStudentsWithOccupations($scope.protocol.protocolStudents) })
      .$update().then(function (result) {
        message.info('main.messages.create.success');
        entityToDto(result);
      }).catch(angular.noop);
  };
  
  function getStudentsWithOccupations(students) {
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
