'use strict';

angular.module('hitsaOis').controller('FinalExamVocationalProtocolEditController', 
function ($scope, $route, $filter, $timeout, $window, $q, QueryUtils, Classifier, ProtocolConfirmationService, oisFileService, config, message, dialogService, ArrayUtils, $location) {
  var endpoint = '/finalExamVocationalProtocols';
  $scope.auth = $route.current.locals.auth;
  var clMapper = Classifier.valuemapper({ grade: 'KUTSEHINDAMINE', status: 'PROTOKOLL_STAATUS', studyLevel: 'OPPEASTE' });
  var studentClMapper = Classifier.valuemapper({ journalResults: 'KUTSEHINDAMINE', status: 'OPPURSTAATUS' });
  var editForbiddenGrades = ['KUTSEHINDAMINE_1', 'KUTSEHINDAMINE_X'];
  var viewForbiddenGrades = ['KUTSEHINDAMINE_X'];
  var allGrades = Classifier.queryForDropdown({ mainClassCode: 'KUTSEHINDAMINE' });
  $scope.formState = {};

  allGrades.$promise.then(function (result) {
    $scope.grades = result.filter(function (it) {
      if ($route.current.locals.isView) {
        return viewForbiddenGrades.indexOf(it.code) === -1;
      } else {
        return editForbiddenGrades.indexOf(it.code) === -1;
      }
    });
  });

  function allProtocolStudentsGraded() {
    if (!angular.isDefined($scope.protocol) || !angular.isArray($scope.protocol.protocolStudents)) {
      return false;
    }

    var allGraded = true;
    for (var i = 0; i < $scope.protocol.protocolStudents.length; i++) {
      if (!angular.isString($scope.protocol.protocolStudents[i].grade) || $scope.protocol.protocolStudents[i].grade.trim().length === 0) {
        allGraded = false;
        break;
      }
    }
    return allGraded;
  }

  function allChangedGradesHaveAddInfo() {
    if (!angular.isDefined($scope.protocol) || !angular.isArray($scope.protocol.protocolStudents)) {
      return false;
    }

    var allHaveAddInfo = true;
    if ($scope.formState.canEditConfirmedProtocol) {
      for (var i = 0; i < $scope.protocol.protocolStudents.length; i++) {
        if ($scope.protocol.protocolStudents[i].gradeHasChanged) {
          var addInfo = $scope.protocol.protocolStudents[i].addInfo;
          addInfo = addInfo !== undefined && addInfo !== null ? addInfo.split(' ').join('') : null;
          
          if (!addInfo) {
            allHaveAddInfo = false;
            break;
          }
        }
      }
    }
    return allHaveAddInfo;
  }

  function canChangeConfirmedProtocolGrade() {
    return canEditConfirmedProtocol() && $scope.protocol.status.code === 'PROTOKOLL_STAATUS_K';
  }

  function canEditConfirmedProtocol() {
    return ($scope.auth.loginMethod === 'LOGIN_TYPE_I' || $scope.auth.loginMethod === 'LOGIN_TYPE_M') &&
      ArrayUtils.contains($scope.auth.authorizedRoles, "ROLE_OIGUS_K_TEEMAOIGUS_LOPMOODULPROTOKOLL");
  }

  function canConfirm() {
    return allProtocolStudentsGraded() && allChangedGradesHaveAddInfo() && 
      ($scope.auth.loginMethod === 'LOGIN_TYPE_I' || $scope.auth.loginMethod === 'LOGIN_TYPE_M')
      $scope.auth.authorizedRoles.indexOf("ROLE_OIGUS_K_TEEMAOIGUS_LOPMOODULPROTOKOLL") !== -1;
  }

  function entityToDto(entity) {
    $q.all(clMapper.promises).then(function () {
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

      if ($scope.protocol.occupations && $scope.protocol.occupationGrantOccupationCodes) {
        $scope.protocol.occupations.forEach(function (it) {
          it.isOccupationGrant = $scope.protocol.occupationGrantOccupationCodes.indexOf(it.code !== -1) ? true : false;
        });
      }
      loadProtocolStudentOccupations();
      $scope.formState = {
        canEditConfirmedProtocol: canEditConfirmedProtocol(),
        canAddDeleteStudents: $scope.protocol.status.code !== 'PROTOKOLL_STAATUS_K' && $scope.protocol.canBeEdited,
        canConfirm: $scope.protocol.status.code !== 'PROTOKOLL_STAATUS_K' && canConfirm(),
        canChangeConfirmedProtocolGrade: canChangeConfirmedProtocolGrade(),
        protocolPdfUrl: config.apiUrl + endpoint + '/' + entity.id + '/print/protocol.pdf'
      };
    });
  }

  $scope.gradeChanged = function(row) {
    if (row) {
      var savedResult = $filter('filter')($scope.savedStudents, {id: row.id}, true)[0];
      if (savedResult.grade !== row.grade) {
        $scope.finalExamProtocolForm.$setSubmitted();
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

    if ($scope.protocol.status.code === 'PROTOKOLL_STAATUS_K' && $scope.auth.isAdmin()) {
      $scope.formState.canConfirm = canConfirm();
    }
  };

  $scope.addInfoChanged = function (row) {
    if (row.addInfo && row.addInfo.charAt(0) === ' ') {
      row.addInfo = null;
    }
    
    if ($scope.protocol.status.code === 'PROTOKOLL_STAATUS_K' && $scope.auth.isAdmin()) {
      $scope.formState.canConfirm = canConfirm();
    }
  };

  var protocolStudentOccupations = {};
  function loadProtocolStudentOccupations() {
    var occupationResults = {};

    $scope.protocol.protocolStudents.forEach(function (protocolStudent) {
      protocolStudentOccupations[protocolStudent.id] = {};

      protocolStudent.curriculumOccupations.forEach(function (curriculumOccupation) {
        if (curriculumOccupation.partOccupationCode && !occupationResults[curriculumOccupation.partOccupationCode]) {
          occupationResults[curriculumOccupation.partOccupationCode] = ({
            id: curriculumOccupation.id,
            occupation: curriculumOccupation.occupationCode,
            partOccupation: curriculumOccupation.partOccupationCode,
          });
        } else if (!curriculumOccupation.partOccupationCode && !occupationResults[curriculumOccupation.occupationCode]) {
          occupationResults[curriculumOccupation.occupationCode] = ({
            id: curriculumOccupation.id,
            occupation: curriculumOccupation.occupationCode,
            partOccupation: null,
            certificateNr: curriculumOccupation.certificateNr
          });
        }

        if (curriculumOccupation.partOccupationCode && !protocolStudentOccupations[protocolStudent.id][curriculumOccupation.partOccupationCode]) {
          protocolStudentOccupations[protocolStudent.id][curriculumOccupation.partOccupationCode] = curriculumOccupation;
        } else if (!curriculumOccupation.partOccupationCode && !protocolStudentOccupations[protocolStudent.id][curriculumOccupation.occupationCode]) {
          protocolStudentOccupations[protocolStudent.id][curriculumOccupation.occupationCode] = curriculumOccupation;
        }
      });
    });
  }

  if ($route.current.locals.entity) {
    entityToDto($route.current.locals.entity);
  } else if ($scope.protocol) {
    loadGradesSelect();
  }

  $scope.selectedFinalExamDateChanged = function () {
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
    dialogService.confirmDialog({prompt: 'finalExamProtocol.prompt.deleteStudent'}, function() {
      var ProtocolStudentEndpoint = QueryUtils.endpoint(endpoint + '/' + $scope.protocol.id + '/removeStudent');
      var removedStudent = new ProtocolStudentEndpoint(protocolStudent);
      removedStudent.$delete().then(function (protocol) {
          message.info('main.messages.delete.success');
          entityToDto(protocol);
      }).catch(angular.noop);
    });
  };

  $scope.addProtocolStudents = function () {
    dialogService.showDialog('finalExamProtocol/templates/final.exam.protocol.add.student.dialog.html', function (dialogScope) {
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
        finalDate: $scope.protocol.finalDate,
        protocolStudents: submittedDialogScope.selectedStudents.map(function (it) { return { studentId: it }; })
      }, function (result) {
        message.info('finalExamProtocol.messages.studentSuccesfullyAdded');
        entityToDto(result);
      });
    });
  };

  $scope.occupationView = function (occupationCode, protocolStudent) {
    if (protocolStudentOccupations[protocolStudent.id] && protocolStudentOccupations[protocolStudent.id][occupationCode]) {
      return protocolStudentOccupations[protocolStudent.id][occupationCode];
    }
  };

  $scope.gradeValue = function (code) {
    var grade = clMapper.objectmapper({ grade: code }).grade;
    return grade ? grade.value : undefined;
  };

  $scope.confirm = function () {
    var data = {
      version: $scope.protocol.version,
      finalDate: $scope.protocol.finalDate,
      committeeId: $scope.protocol.committee,
      protocolCommitteeMembers: getPresentCommitteeMembers($scope.committeeMembers),
      protocolStudents: getStudentsWithOccupations($scope.protocol.protocolStudents)
    };

    if ($scope.auth.loginMethod === 'LOGIN_TYPE_I') {
      ProtocolConfirmationService.signBeforeConfirm(endpoint + '/' + $scope.protocol.id, data, entityToDto);
    } else if ($scope.auth.loginMethod === 'LOGIN_TYPE_M') {
      ProtocolConfirmationService.mobileSignBeforeConfirm(endpoint + '/' + $scope.protocol.id, data, entityToDto);
    }
  };

  var FinalExamVocationlProtocolEndpoint = QueryUtils.endpoint(endpoint);
  $scope.save = function () {
    new FinalExamVocationlProtocolEndpoint({ 
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
    $scope.protocol.protocolStudents.forEach(function (student) {
      var occupations = [];
      angular.forEach(student.occupations, function(value, key) {
        if (value) {
          occupations.push(key);
        }
      });
      student.occupations = occupations;
    });
    return $scope.protocol.protocolStudents;
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
    dialogService.confirmDialog({prompt: 'finalExamProtocol.prompt.delete'}, function() {
      new FinalExamVocationlProtocolEndpoint($scope.protocol).$delete().then(function(){
        message.info('finalExamProtocol.messages.deleted');
        $location.path(endpoint);
      }).catch(angular.noop);
    });
  };
});
