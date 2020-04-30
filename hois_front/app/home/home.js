'use strict';
// todo this to main controller? and use this data on auth?
angular.module('hitsaOis').controller('HomeController', ['$scope', 'School', '$location', 'QueryUtils', 'DataUtils',
  function ($scope, School, $location, QueryUtils, DataUtils) {
    $scope.schools = School.getSchoolsWithLogo();
    $scope.linkify = DataUtils.linkifyText;

    $scope.openSchoolCurriculumSearch = function (schoolId) {
      $location.path('curriculums/' + schoolId);
    };

    $scope.siteMessages = QueryUtils.endpoint("/generalmessages/showsitemessages").query();
  }
]).controller('AuthenticatedHomeController', ['$rootScope', '$scope', '$timeout', 'AUTH_EVENTS', 'AuthService', 'USER_ROLES',
                                              'ArrayUtils', 'QueryUtils', '$resource', 'config', 'Session', '$filter', '$mdDialog',
                                              'message', 'dialogService', 'oisFileService', 'FormUtils', 'Classifier', '$q',
  function ($rootScope, $scope, $timeout, AUTH_EVENTS, AuthService, USER_ROLES, ArrayUtils, QueryUtils, $resource,
      config, Session, $filter, $mdDialog, message, dialogService, oisFileService, FormUtils, Classifier, $q) {
    /**
     * Still under question if we need to add a delay for timeout.
     */
    $scope.finish = function () {
      $timeout(function () {
        $scope.balance();
      }, 100);
      return false;
    };

    /**
     * Handler.
     *
     * @param {Function} finishEvent
     */
    function PageLoadingHandler(finishEvent) {
      this.promises = {};
      this.lock = true;

      this.finishEvent = finishEvent;

      /**
       * Adds a promise and gives functions to this promise.
       *
       * @param {String} sName
       * @param {Promise} pPromise
       * @param {Funciton} fThen
       * @param {Function} fCatch
       */
      this.addPromise = function (sName, pPromise, fThen, fCatch) {
        var isPromise = typeof pPromise.then === 'function';
        if (isPromise) {
          pPromise.then(fThen);
          if (fCatch !== null || fCatch !== undefined) {
            pPromise.catch(function (error) {
              this.setFinish(sName);
              throw error;
            });
            pPromise.catch(fCatch);
          } else {
            pPromise.catch(function () {
              this.setFinish(sName);
            });
          }
          this.promises[sName] = pPromise;
        }
      };

      this.setFinish = function (sPromiseName) {
        if (sPromiseName in this.promises) {
          delete this.promises[sPromiseName];
        }
        this.finish();
      };

      /**
       * Allows to run finish function.
       */
      this.enable = function() {
        this.lock = false;
        this.finish();
      };

      /**
       * Resets handler.
       */
      this.reset = function () {
        this.lock = true;
        this.promises = {};
      };

      this.finish = function () {
        if (!this.lock && angular.equals(this.promises, {})) {
          this.finishEvent();
          this.lock = true;
        }
      };
    }

    $scope.pageLoadingHandler = new PageLoadingHandler($scope.finish);

    $scope.criteria = {size: 5, page: 1}; // Sorting is defined in backend.
    $scope.generalmessages = {};
    $scope.loadGeneralMessages = function() {
      $scope.showGeneralMessages = ['ROLL_P', 'ROLL_V'].indexOf(Session.roleCode) === -1;
      if(!$scope.showGeneralMessages) {
          return;
      }
      var query = QueryUtils.getQueryParams($scope.criteria);
      // Not sure if it should be QueryUtils.endpoint('/generalmessages/show').search(query).$promise or just QueryUtils.endpoint('/generalmessages/show').search(query)
      // Before md-progress used the second one, so I will leave it so as it was before.
      $scope.generalmessages.$promise = QueryUtils.endpoint('/generalmessages/show').search(query);
      $scope.pageLoadingHandler.addPromise("generalMessages", $scope.generalmessages.$promise.$promise, function(result) {
        $scope.generalmessages.content = result.content;
        $scope.generalmessages.totalElements = result.totalElements;
        if (result.totalElements === 0) {
          $scope.pageLoadingHandler.setFinish("generalMessages");
        }
      });
    };
    $scope.unreadMessageCriteria = {size: 5, page: 1, order: '-inserted'};
    $scope.unreadMessages = {};

    $scope.loadUnreadMessages = function() {
      var acceptedRoles = ['ROLL_P', 'ROLL_L', 'ROLL_O', 'ROLL_T', 'ROLL_A', 'ROLL_J'];
      $scope.showUnreadMessages = acceptedRoles.indexOf(Session.roleCode) !== -1;
      if(!$scope.showUnreadMessages) {
          return;
      }
      var query = QueryUtils.getQueryParams($scope.unreadMessageCriteria);
      $scope.unreadMessages.$promise = QueryUtils.endpoint('/message/received/mainPage').search(query);
      $scope.pageLoadingHandler.addPromise("unreadMessages", $scope.unreadMessages.$promise.$promise, function(result) {
        $scope.unreadMessages.content = result.content;
        $scope.unreadMessages.totalElements = result.totalElements;
        if (result.totalElements === 0) {
          $scope.pageLoadingHandler.setFinish("unreadMessages");
        }
      });
    };

    $scope.loadPolls = function() {
      var clMapper = Classifier.valuemapper({status: 'KYSITVASTUSSTAATUS', type: 'KYSITLUS'});
      /** Student representative, Teacher, Student, Admin */
      var acceptedRoles = ['ROLL_L', 'ROLL_O', 'ROLL_T', 'ROLL_A'];
      $scope.displayPoll = false;
      $scope.canAnswerPoll = acceptedRoles.indexOf(Session.roleCode) !== -1;
      if(!$scope.canAnswerPoll) {
          return;
      }
      QueryUtils.endpoint("/poll/polls").query({}, function(response) {
        $scope.polls = response;
        if ($scope.polls !== undefined && $scope.polls.length !== 0) {
          $scope.displayPoll = true;
          $q.all(clMapper.promises).then(function () {
            clMapper.objectmapper($scope.polls);
          });
        }
      });
    };

    $scope.openPoll = function() {
      if ($scope.polls.length === 1) {
        var PollEndPoint;
        if ($scope.polls[0].type.code === 'KYSITLUS_O') {
          PollEndPoint = QueryUtils.endpoint('/poll/withAnswers/journalOrSubject');
        } else {
          PollEndPoint = QueryUtils.endpoint('/poll/withAnswers');
        }
        QueryUtils.loadingWheel($scope, true);
        PollEndPoint.get({id: $scope.polls[0].id}).$promise.then(function (response) {
          QueryUtils.loadingWheel($scope, false);
          markImages(response.themes);
          $scope.openResponse($scope.polls[0], response);
        });
      } else if ($scope.polls.length > 1) {
        $scope.openResponseList($scope.polls);
      }
    };

    function markImages(themes) {
      for (var theme = 0; theme < themes.length; theme++) {
          for (var question = 0; question < themes[theme].questions.length; question++) {
              var pictures = themes[theme].questions[question].files.filter(function (file) {
                  return file.ftype.indexOf('image') !== -1;
              });
              themes[theme].questions[question].pictures = pictures;
          }
      }
    }

    $scope.openResponseList = function(polls) {
      dialogService.showDialog('poll/poll.response.list.dialog.html', function (dialogScope) {
        dialogScope.polls = polls;
        dialogScope.openResponse = function(row) {
          dialogScope.row = row;
          dialogScope.submit();
        };
      }, function(submittedDialogScope) {
        var PollEndPoint;
        if (submittedDialogScope.row.type.code === 'KYSITLUS_O') {
          PollEndPoint = QueryUtils.endpoint('/poll/withAnswers/journalOrSubject');
        } else {
          PollEndPoint = QueryUtils.endpoint('/poll/withAnswers');
        }
        QueryUtils.loadingWheel($scope, true, true, undefined, false, function () {
          PollEndPoint.get({id: submittedDialogScope.row.id}).$promise.then(function (response) {
            markImages(response.themes);
            QueryUtils.loadingWheel($scope, false, true);
            $scope.openResponse(submittedDialogScope.row, response);
          });
        });
      }, null, true);
    };


    $scope.openResponse = function(row, response) {
      dialogService.showDialog(
        row.isThemePageable ? 'poll/poll.response.by.theme.dialog.html' :
        (response.type === 'KYSITLUS_O' ? 'poll/poll.response.by.subjectOrJournal.dialog.html' :
        'poll/poll.response.dialog.html'), function (dialogScope) {
        dialogScope.showForeword = true;
        dialogScope.showAfterword = false;
        dialogScope.formState = {};
        dialogScope.formState.isThemePageable = row.isThemePageable;
        dialogScope.formState.name = response.name;
        var themesWithSubjectOrJournal = response.themes.filter(function(theme) {
          return theme.journal !== null || theme.subject !== null;
        });
        dialogScope.hasSubjectOrJournal = themesWithSubjectOrJournal.length !== 0;
        dialogScope.criteria = response;
        dialogScope.getUrl = function(photo) {
            return oisFileService.getUrl(photo, 'pollThemeQuestionFile');
        };

        function mapJournalOrSubjects() {
          dialogScope.formState.themeBySubjectOrJournalId = [];
          dialogScope.criteria.themes.forEach(function (theme) {

            if (theme.journal !== null && theme.isRepetitive) {
              var listJournal = dialogScope.formState.themeBySubjectOrJournalId.find(function(journal) {
                  return journal.id === theme.journal.id;
              });
              if (listJournal === undefined) {
                  dialogScope.formState.themeBySubjectOrJournalId.push({id: theme.journal.id, list: [theme]});
              } else {
                  listJournal.list.push(theme);
              }
            } else if (theme.subject !== null && theme.isRepetitive) {
              var listSubject = dialogScope.formState.themeBySubjectOrJournalId.find(function(subject) {
                  return subject.id === theme.subject.id;
              });
              if (listSubject === undefined) {
                  dialogScope.formState.themeBySubjectOrJournalId.push({id: theme.subject.id, list: [theme]});
              } else {
                  listSubject.list.push(theme);
              }
            } else if (!theme.isRepetitive) {
              var listNonRepetetive = dialogScope.formState.themeBySubjectOrJournalId.find(function(nonRepetetive) {
                  return nonRepetetive.id === null;
              });
              if (listNonRepetetive === undefined) {
                  dialogScope.formState.themeBySubjectOrJournalId.push({id: null, list: [theme]});
              } else {
                  listNonRepetetive.list.push(theme);
              }
            }
          });
        }

        // Map journals or subjects with repedetive themes
        if (dialogScope.criteria.type === 'KYSITLUS_O') {
            if (row.isThemePageable) {
              if (dialogScope.criteria.foreword === null) {
                dialogScope.criteria.themes[0].show = true;
                if (dialogScope.criteria.themes.length < 2) {
                  dialogScope.showConfirm = true;
                }
              }
            } else {
              mapJournalOrSubjects();
            }
            if (!row.isThemePageable && dialogScope.criteria.foreword === null) {
              dialogScope.formState.themeBySubjectOrJournalId[0].show = true;
              if (dialogScope.formState.themeBySubjectOrJournalId.length < 2) {
                dialogScope.showConfirm = true;
              }
            }
        } else {
          dialogScope.criteria.themes[0].show = true;
          if (dialogScope.criteria.themes.length < 2) {
            dialogScope.showConfirm = true;
          }
        }

        dialogScope.previousSubjectOrJournal = function(index) {
          dialogScope.showConfirm = false;
          if (index === 0) {
            dialogScope.showForeword = true;
          } else {
            dialogScope.formState.themeBySubjectOrJournalId[index - 1].show = true;
          }
          if (index === dialogScope.subjectListLength()) {
            dialogScope.showAfterword = false;
          } else {
            dialogScope.formState.themeBySubjectOrJournalId[index].show = false;
          }
        };

        dialogScope.last = function(index) {
          return index === dialogScope.formState.themeBySubjectOrJournalId.length - 1;
        };

        dialogScope.first = function(index) {
          return index === 0;
        };

        dialogScope.subjectListLength = function() {
          return dialogScope.formState.themeBySubjectOrJournalId.length;
        };

        dialogScope.nextSubjectOrJournal = function(index) {
          if (index === -1) {
            dialogScope.showForeword = false;
          } else {
            dialogScope.formState.themeBySubjectOrJournalId[index].show = false;
          }
          // display confirm button when last journal/subject theme is displayed
          if ((((dialogScope.criteria.type === 'KYSITLUS_O' && dialogScope.criteria.afterword === null) || 
            dialogScope.criteria.type !== 'KYSITLUS_O') && 
            index + 1 === dialogScope.formState.themeBySubjectOrJournalId.length - 1) ||
            index === dialogScope.formState.themeBySubjectOrJournalId.length - 1) {
              dialogScope.showConfirm = true;
          }
          
          if (index === dialogScope.formState.themeBySubjectOrJournalId.length - 1) {
            dialogScope.showAfterword = true;
          } else {
            dialogScope.formState.themeBySubjectOrJournalId[index + 1].show = true;
          }
        };

        dialogScope.previousTheme = function(index) {
          dialogScope.showConfirm = false;
          if (index === 0) {
            dialogScope.showForeword = true;
          } else {
            dialogScope.criteria.themes[index - 1].show = true;
          }
          if (index === dialogScope.criteria.themes.length) {
            dialogScope.showAfterword = false;
          } else {
            dialogScope.criteria.themes[index].show = false;
          }
        };

        dialogScope.firstPage = function(themeList) {
          return dialogScope.formState.themeBySubjectOrJournalId[0] === themeList;
        };

        dialogScope.nextTheme = function(index) {
          if (index === -1) {
            dialogScope.showForeword = false;
          } else {
            dialogScope.criteria.themes[index].show = false;
          }
          // display confirm button when last journal/subject theme is displayed
          if ((((dialogScope.criteria.type === 'KYSITLUS_O' && dialogScope.criteria.afterword === null) || 
            dialogScope.criteria.type !== 'KYSITLUS_O') && 
            index + 1 === dialogScope.criteria.themes.length - 1) ||
            index === dialogScope.criteria.themes.length - 1) {
              dialogScope.showConfirm = true;
          }

          if (index === dialogScope.criteria.themes.length - 1) {
            dialogScope.showAfterword = true;
          } else {
            dialogScope.criteria.themes[index + 1].show = true;
          }
        };

        function pushToError(errorThemes, theme) {
          if (theme.journal !== null && errorThemes.indexOf(theme.journal.nameEt + ': ' + theme.nameEt) === -1) {
              errorThemes.push(theme.journal.nameEt + ': ' + theme.nameEt);
          } else if (theme.subject !== null && errorThemes.indexOf(theme.subject.nameEt + ': ' + theme.nameEt) === -1) {
              errorThemes.push(theme.subject.nameEt + ': ' + theme.nameEt);
          } else if (theme.journal === null && theme.subject === null && errorThemes.indexOf(theme.nameEt) === -1) {
              errorThemes.push(theme.nameEt);
          }
        }

        dialogScope.checkErrors = function() {
          var hasErrors = false;
          var errorThemes = [];
          dialogScope.criteria.themes.forEach(function(theme) {
            var studentCouncilAtleastOne = false;
            var studentCouncilOneRequired = false;
            theme.questions.forEach(function (question) {
              if (question.type === 'VASTUS_S') {
                // student council type should have 1 answer
                // check if theme has 1 question answered
                if (question.answers[0].chosen === true) {
                  studentCouncilAtleastOne = true;
                }
                if (question.isRequired) {
                  studentCouncilOneRequired = true;
                }
              } else if (question.type === 'VASTUS_M' && question.isRequired) {
                var showError = true;
                question.answers.forEach(function(answer) {
                  if (answer.chosen === true) {
                    showError = false;
                  }
                });
                if (showError) {
                  question.requiredError = true;
                  hasErrors = true;
                  pushToError(errorThemes, theme);
                } else {
                  question.requiredError = false;
                }
              } else if ((question.type === 'VASTUS_R' || question.type === 'VASTUS_T' || question.type === 'VASTUS_V' ) && question.isRequired && (question.answerTxt === undefined || question.answerTxt === null)) {
                question.requiredError = true;
                hasErrors = true;
                pushToError(errorThemes, theme);
              } else {
                question.requiredError = false;
              }
            });
            if (!studentCouncilAtleastOne && studentCouncilOneRequired) {
              hasErrors = true;
              pushToError(errorThemes, theme);
            }
          });
          return [hasErrors, errorThemes];
        };

        dialogScope.confirm = function() {
          var errorObject = dialogScope.checkErrors();
          var hasErrors = errorObject[0];
          FormUtils.withValidForm(dialogScope.responseForm, function() {
              if (!hasErrors) {
                dialogService.confirmDialog({ prompt: 'poll.answer.confirm' }, function () {
                  var Endpoint = QueryUtils.endpoint('/poll/' + row.id + '/saveAnswer/final');
                  var pollEndPoint = new Endpoint();
                  pollEndPoint.$update().then(function () {
                      dialogService.messageDialog({ prompt: 'poll.answer.confirmed' }, function () {
                        $scope.loadPolls();
                        dialogScope.cancel();
                      });
                  });
                });
              } else {
                if (dialogScope.formState.isThemePageable || dialogScope.criteria.type === "KYSITLUS_V") {
                  message.error('poll.messages.required', {themes: errorObject[1].join("\n")});
                } else {
                  message.error('main.messages.form-has-errors');
                }
              }
          });
        };

        dialogScope.save = function (question) {
            var Endpoint = QueryUtils.endpoint('/poll/' + dialogScope.criteria.responseId + '/saveAnswer');
            var pollEndPoint = new Endpoint(question);
            pollEndPoint.$putWithoutLoad();
        };

        dialogScope.deselectOther = function(question, theme) {
          dialogScope.save(question);
          if (response.type === 'KYSITLUS_V') {
            if (question.answers[0].chosen) {
              theme.questions.forEach(function (themeQuestion) {
                if (themeQuestion !== question && themeQuestion.type === 'VASTUS_S' && themeQuestion.answers[0].chosen === true) {
                  themeQuestion.answers[0].chosen = false;
                  dialogScope.save(themeQuestion);
                }
              });
            }
          }
          return true;
        };

        dialogScope.clearRadio = function(question) {
            question.answerTxt = null;
            dialogScope.save(question);
        };

        dialogScope.close = function() {
          $scope.loadPolls();
          dialogScope.cancel();
        };
      }, null, null, true);
    };

    $scope.readMessage = function(message) {
        message.clicked = !message.clicked;
        if(!message.isRead) {
            $resource(config.apiUrl + '/message/' + message.id).update(null).$promise.then(function(){
                message.isRead = true;
                QueryUtils.endpoint('/message/received/new').get().$promise.then(function (result) {
                  $rootScope.unreadMessages = result.unread;
                });
            });
        }
    };

    $scope.openCloseDetails = function(message) {
      message.clicked = !message.clicked;
    };

    function afterAuthentication() {
      $scope.pageLoadingHandler.reset();
      $scope.isAdmin = Session.roleCode === 'ROLL_A';
      if (Session.mustAgreeWithToS) {
        return;
      }
      $scope.loadGeneralMessages();
      $scope.loadUnreadMessages();
      $scope.loadPolls();
      checkIfCanCreateAbsence();
      checkIfCanApplyForPractice();
      checkIfHasSubjectProgramNotification();
      checkIfHasOrderedCertificates();
      checkIfHasExpiredBaseModules();
      checkIfHasUnacceptedAbsences();
      checkIfHasTodaysAbsences();
      checkUnconfirmedJournals();
      checkIfHasRecentRRChanges();
      checkIfOpenDeclarationPeriod();
      expiringOccupationStandards();
      unappliedScholarships();
      studentGroupRemarks();
      studentAfterAuthentication();
      $scope.pageLoadingHandler.enable();
    }

    function studentAfterAuthentication() {
      if (Session.studentId) {
        $scope.sessionStudentId = Session.studentId;
        getStudentInformation();
      } else {
        $scope.sessionStudentId = null;
        $scope.tasks = [];
        $scope.studentAbsences = [];
        $scope.absenceGroups = [];
        $scope.lastResults = [];
        $scope.remarks = [];
      }
    }

    if (AuthService.isAuthenticated()) {
      afterAuthentication();
    }
    $scope.$on(AUTH_EVENTS.loginSuccess, afterAuthentication);
    $scope.$on(AUTH_EVENTS.userChanged, afterAuthentication);

    function checkIfHasRecentRRChanges() {
      $scope.rrHasRecentChangeLogs = undefined;
      if (['ROLL_A', 'ROLL_J'].indexOf(Session.roleCode) !== -1 && ArrayUtils.includes(Session.authorizedRoles, USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_RR)) {
        QueryUtils.endpoint("/logs/rr/hasrecentchangelogs").get({}, function (response) {
          $scope.rrHasRecentChangeLogs = response.hasRecentChangeLogs; // TODO maybe should be emptined?
        });
      }
    }

    function checkIfOpenDeclarationPeriod() {
      $scope.currentDeclaration = undefined;
      $scope.nextDeclaration = undefined;
      if (['ROLL_T'].indexOf(Session.roleCode) !== -1) {
        $scope.currentDeclaration = QueryUtils.endpoint('/declarations/isDeclarationPeriod').search();
        $scope.nextDeclaration = QueryUtils.endpoint('/declarations/isDeclarationPeriod?next=true').search();
      }
    }

    function checkIfCanCreateAbsence() {
      $scope.canCreateAbsence = false;
      $scope.studentName = undefined;
      $scope.studentGroup = undefined;
      if (['ROLL_L', 'ROLL_T'].indexOf(Session.roleCode) !== -1 && Session.vocational) {
        QueryUtils.endpoint("/students/" + Session.studentId + "/canCreateAbsence").get({}, function(response) {
          $scope.canCreateAbsence = response.canCreate;
          $scope.studentName = response.studentName;
          $scope.studentGroup = response.studentGroup;
        });
      }
    }

    function checkIfCanApplyForPractice() {
      $scope.canApplyForPractice = false;
      if (['ROLL_T'].indexOf(Session.roleCode) !== -1) {
        QueryUtils.endpoint("/practiceApplication/canApply").get({}, function(response) {
          $scope.canApplyForPractice = response.canApply;
        });
      }
    }

    function unappliedScholarships() {
      $scope.unappliedScholarships = undefined;
      if (['ROLL_T'].indexOf(Session.roleCode) !== -1) {
        $scope.unappliedScholarships = QueryUtils.endpoint('/scholarships/studentUnappliedScholarships').query();
      }
    }

    function checkIfHasOrderedCertificates() {
      if (['ROLL_A'].indexOf(Session.roleCode) !== -1 && Session.school.withoutEkis) {
        $scope.hasOrderedCertificates = QueryUtils.endpoint("/certificate/hasorderedcertificates").get();
      } else {
        $scope.hasOrderedCertificates = undefined;
      }
    }

    function checkIfHasSubjectProgramNotification() {
      if (['ROLL_O', 'ROLL_A'].indexOf(Session.roleCode) !== -1 && Session.higher) {
        if (Session.roleCode === 'ROLL_O') {
          $scope.hasUncompletedPrograms = QueryUtils.endpoint("/subject/subjectProgram/hasuncompleted").get();
        }
        $scope.hasUnconfirmedPrograms = QueryUtils.endpoint("/subject/subjectProgram/hasunconfirmed").get();
      } else {
        $scope.hasUncompletedPrograms = undefined;
        $scope.hasUnconfirmedPrograms = undefined;
      }
    }

    function checkIfHasExpiredBaseModules() {
      if (['ROLL_A'].indexOf(Session.roleCode) !== -1 && ArrayUtils.includes(Session.authorizedRoles, USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_BAASMOODUL)) {
        $scope.hasExpiredBaseModules = QueryUtils.endpoint('/basemodule/expiredhascurriculums').query();
      } else {
        $scope.hasExpiredBaseModules = undefined;
      }
    }

    function checkIfHasUnacceptedAbsences() {
      if(['ROLL_A', 'ROLL_O'].indexOf(Session.roleCode) !== -1) {
        QueryUtils.endpoint('/absences/hasUnaccepted').search().$promise.then(function(response) {
          $scope.hasUnacceptedAbsences = response.hasUnaccepted;
        });
      } else {
        $scope.hasUnacceptedAbsences = undefined;
      }
    }

    function checkIfHasTodaysAbsences() {
      if(['ROLL_O'].indexOf(Session.roleCode) !== -1) {
        QueryUtils.endpoint('/groupAbsences/teacherHasTodaysAbsences').search().$promise.then(function(response) {
          $scope.hasTodaysAbsences = response.hasTodaysAbsences;
        });
      } else {
        $scope.hasTodaysAbsences = undefined;
      }
    }

    function checkUnconfirmedJournals() {
      if(['ROLL_A', 'ROLL_O'].indexOf(Session.roleCode) !== -1) {
        QueryUtils.endpoint('/journals/unconfirmedJournalsInfo').search().$promise.then(function(response) {
          $scope.unconfirmedJournals = {
            count: response.unconfirmedJournalCount,
            anyFinished: response.hasEndedUnconfirmedJournals
          };
        });
      } else {
        $scope.unconfirmedJournals = undefined;
      }
    }

    var initialNumberOfOccupationStandards = 3;
    $scope.numberOfOccupationStandards = initialNumberOfOccupationStandards;
    function expiringOccupationStandards() {
      if(['ROLL_A'].indexOf(Session.roleCode) !== -1) {
        $scope.pageLoadingHandler.addPromise("expiringOccupationStandards",
          QueryUtils.endpoint('/curriculumOccupation/expiringOccupationStandards').query().$promise,
          function (result) {
            $scope.expiringOccupationStandards = result;
            if (result.length === 0) {
              $scope.pageLoadingHandler.setFinish("expiringOccupationStandards");
            }
          }
        );
      } else {
        $scope.expiringOccupationStandards = undefined;
      }
    }

    $scope.toggleNumberOfOccupationStandards = function() {
      $scope.numberOfOccupationStandards = $scope.numberOfOccupationStandards ? undefined : initialNumberOfOccupationStandards;
    };

    function studentGroupRemarks() {
      if(['ROLL_O'].indexOf(Session.roleCode) !== -1) {
        $scope.pageLoadingHandler.addPromise("studentGroupRemarks",
          QueryUtils.endpoint('/remarks/studentGroupRemarks').query().$promise,
          function (result) {
            $scope.studentGroupRemarks = result;
            if (result.length === 0) {
              $scope.pageLoadingHandler.setFinish("studentGroupRemarks");
            }
          }
        );
      } else {
        $scope.studentGroupRemarks = undefined;
      }
    }

    function getStudentInformation() {
      $scope.pageLoadingHandler.addPromise("studentTasks",
        QueryUtils.endpoint('/journals/studentJournalTasks/').search({studentId: Session.studentId}).$promise,
        function (result) {
          $scope.tasks = {todayTasks: [], tomorrowTasks: [], laterTasks: []};
          if (angular.isDefined(result.tasks) && result.tasks.length > 0) {
            getTodayAndTomorrowDate();
            sortTasksByDate(result.tasks.reverse());
            if (!($scope.tasks.todayTasks.length > 0 || $scope.tasks.tomorrowTasks.length > 0 || $scope.tasks.laterTasks.length > 0)) {
              $scope.pageLoadingHandler.setFinish("studentTasks");
            }
          } else {
            $scope.pageLoadingHandler.setFinish("studentTasks");
          }
        }
      );

      $scope.testTaskTypes = ['SISSEKANNE_H', 'SISSEKANNE_L', 'SISSEKANNE_E', 'SISSEKANNE_I', 'SISSEKANNE_P', 'SISSEKANNE_R'];

      $scope.pageLoadingHandler.addPromise("studentAbsences",
        QueryUtils.endpoint('/journals/studentJournalAbsences/').query({studentId: Session.studentId}).$promise,
        function (result) {
          $scope.studentAbsences = result;
          $scope.absenceGroups = $scope.getGroupedValues($scope.studentAbsences, 'entryDate', 'hoisDate');
          for (var key in $scope.absenceGroups) {
            var PUUDUMINE_H = 0;
            var PUUDUMINE_P = 0;
            for (var i in $scope.absenceGroups[key]) {
              if ($scope.absenceGroups[key][i].absenceCode === "PUUDUMINE_H") {
                PUUDUMINE_H++;
              } else if ($scope.absenceGroups[key][i].absenceCode === "PUUDUMINE_P") {
                PUUDUMINE_P++;
              }
            }
            $scope.absenceGroups[key].PUUDUMINE_H = PUUDUMINE_H > 0 ? PUUDUMINE_H : null;
            $scope.absenceGroups[key].PUUDUMINE_P = PUUDUMINE_P > 0 ? PUUDUMINE_P : null;
          }
          if (result.length === 0) {
            $scope.pageLoadingHandler.setFinish("studentAbsences");
          }
        }
      );

      $scope.pageLoadingHandler.addPromise("lastResults",
        QueryUtils.endpoint('/journals/studentJournalLastResults/').query({studentId: Session.studentId}).$promise,
        function (result) {
          $scope.lastResults = result;

          var clMapper = Classifier.valuemapper({grade: 'KUTSEHINDAMINE'});
          $q.all(clMapper.promises).then(function () {
            $scope.lastResults.forEach(function (lastResult) {
              lastResult = clMapper.objectmapper(lastResult);
            });
          });
          if (result.length === 0) {
            $scope.pageLoadingHandler.setFinish("lastResults");
          }
        }
      );

      $scope.pageLoadingHandler.addPromise("remarks",
      QueryUtils.endpoint('/remarks/student/recent/' + Session.studentId).query().$promise,
      function (result) {
        $scope.remarks = result;
        if (result.length === 0) {
          $scope.pageLoadingHandler.setFinish("remarks");
        }
      }
    );

      $scope.declaration = undefined;
      if (Session.roleCode !== 'ROLL_L') {
        $scope.pageLoadingHandler.addPromise("declaration",
          QueryUtils.endpoint('/declarations/current').get().$promise,
          function (result) {
            $scope.declaration = result;
            if (angular.isUndefined(result.subjects) || result.subjects.length === 0) {
              $scope.pageLoadingHandler.setFinish("declaration");
            }
          }
        );
      }
    }

    function getTodayAndTomorrowDate() {
      var today, tomorrow;
      today = new Date();
      today.setHours(0, 0, 0, 0);
      tomorrow = new Date();
      tomorrow.setDate(today.getDate() + 1);
      tomorrow.setHours(0, 0, 0, 0);

      $scope.todaysDate = today;
      $scope.tomorrowsDate = tomorrow;
    }

    function sortTasksByDate(tasks) {
      $scope.tasks = {todayTasks: [], tomorrowTasks: [], laterTasks: []};
      for (var i = 0; i < tasks.length; i++) {
        var taskDate = new Date(tasks[i].date);
        taskDate.setHours(0, 0, 0, 0);

        if (taskDate.getTime() === $scope.todaysDate.getTime()) {
          $scope.tasks.todayTasks.push(tasks[i]);
        } else if (taskDate.getTime() === $scope.tomorrowsDate.getTime()) {
          $scope.tasks.tomorrowTasks.push(tasks[i]);
        } else if (taskDate > $scope.tomorrowsDate) {
          $scope.tasks.laterTasks.push(tasks[i]);
        }
      }
    }

    $scope.addAbsence = function() {
      var AbsenceEndpoint = QueryUtils.endpoint('/students/' + Session.studentId + '/absences');
      var absence = {};
      absence.studentName = $scope.studentName;
      absence.studentGroup = $scope.studentGroup;

      $mdDialog.show({
        controller: function ($scope) {
          $scope.record = new AbsenceEndpoint(absence || {});

          $scope.cancel = $mdDialog.hide;
          $scope.update = function () {
            $scope.studentAbsenceForm.$setSubmitted();
            if (!$scope.studentAbsenceForm.$valid) {
              message.error('main.messages.form-has-errors');
              return;
            }
            function afterSave() {
              message.info('main.messages.create.success');
              $mdDialog.hide();
            }
            $scope.record.$save().then(afterSave);
          };
        },
        templateUrl: 'student/absence.edit.dialog.html',
        clickOutsideToClose: false
      });
    };

    /**
     * Returns a grouped arrays by given key.
     *
     * @param {Array} source Array of objects
     * @param {String} key Key which is used for grouping
     * @param {String} filterName Filter name from $filter if needed. Applied to key's value
     */
    $scope.getGroupedValues = function (source, key, filterName) {
      var groupedResult = {};
      for (var i = 0; i < source.length; i++) {
        var groupKey = (filterName === null) ? source[i][key] : $filter(filterName)(source[i][key]);
        if (groupKey === null) {
          groupKey = "";
        }
        if (groupKey in groupedResult) {
          groupedResult[groupKey].push(source[i]);
        } else {
          groupedResult[groupKey] = [source[i]];
        }
      }
      return groupedResult;
    };

    /**
     * Returns array. Removes an unnecessary inner arrays.
     *
     * @param {Array} source
     */
    $scope.translateObjectToArray = function (source) {
      var array = [];
      for (var key in source) {
        for (var value in source[key]) {
          array.push(source[key][value]);
        }
      }
      return array;
    };

    /*
    * Hack: Allows to load all the page and render it.
    * Requires to use ng-if (or other check on every $digest) and
    * ng-init inside of dummy html which is included using ng-include.
    */

    $scope.balance = function () {
      var div1 = document.getElementById("column-1");
      var div2 = document.getElementById("column-2");
      if (div1 !== null && div2 !== null) {
        if (div2.children.length !== 0) {
          var itemCount = div2.children.length;
          for (var _ = 0; _ < itemCount; _++) {
            div1.appendChild(div2.children[0]);
          }
        }
        var count = div1.children.length;
        var maxLength = sumChildrenHeight(div1);
        var balance = maxLength / 2;
        for (var i = count - 1; i >= 0; i--) {
          var div2Height = sumChildrenHeight(div2);
          if (div2Height + div1.children[i].clientHeight < balance) {
            addElementAtTop(div1.children[i], div2);
          } else {
            var diffBefore = Math.abs(div2Height - balance);
            var diffAfter = Math.abs(div2Height + div1.children[i].clientHeight - balance);
            if (diffAfter < diffBefore) {
              addElementAtTop(div1.children[i], div2);
            }
            break;
          }
        }
      }
      return false;
    };

    /**
     * Places at top of the container an element.
     *
     * @param {Element} elem Element to be placed
     * @param {Element} container  Element where elem should be placed.
     */
    function addElementAtTop(elem, container) {
      if (container.children.length === 0) {
        container.appendChild(elem);
      } else {
        container.insertBefore(elem, container.children[0]);
      }
    }

    /**
     * Used to determine the real height of the div block.
     * If it was checked by div.clientHeight then it would return any value but 0
     * in case if we had 2 columns because its height would be the same as the height of his parent.
     * For the view with 1 column (xs, sm, md) it would return 0 as it should
     * with 0 children.
     *
     * @param {Element} elem
     */
    function sumChildrenHeight(elem) {
      var sum = 0;
      for (var i = 0; i < elem.children.length; i++) {
        sum += elem.children[i].clientHeight;
      }
      return sum;
    }
  }
]);
