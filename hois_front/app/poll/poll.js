(function () {
    'use strict';

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

    angular.module('hitsaOis').controller('PollListController', function ($scope, $route, QueryUtils, Classifier, $q, message) {
        $scope.auth = $route.current.locals.auth;
        var clMapper = Classifier.valuemapper({
            typeCode: 'KYSITLUS',
            status: 'KYSITLUS_STAATUS',
            targetCodes: 'KYSITLUS_SIHT'
        });

        $scope.testEmail = function () {
            QueryUtils.endpoint('/poll/testEmailService').put({}, function () {
                message.info('email saadetud');
            });
        };
        QueryUtils.createQueryForm($scope, '/poll', {order: 'p.validFrom'}, clMapper.objectmapper);
        $q.all(clMapper.promises).then($scope.loadData);
    }).controller('PollQuestionsController', function ($scope, $route, QueryUtils, dialogService, message, FormUtils, ArrayUtils, oisFileService, $rootScope) {
        $scope.auth = $route.current.locals.auth;
        $scope.currentNavItem = 'poll.questions';
        $scope.formState = {};
        $scope.criteria = {};
        if ($route.current.params.id) {
            $scope.criteria.id = $route.current.params.id;
            if ($route.current.templateUrl === "poll/poll.questions.edit.html") {
                $scope.formState.edit = true;
            }
        }

        $scope.saveThemePageable = function() {
            var PollThemeEndpoint = QueryUtils.endpoint('/poll/themes/pageable/' + $scope.criteria.id);
            var pollThemeEndpoint = new PollThemeEndpoint({isThemePageable: $scope.criteria.isThemePageable});
            pollThemeEndpoint.$update().then(function () {
                message.info('main.messages.create.success');
                $scope.refresh();
            }).catch(angular.noop);
        };

        $scope.saveThemeRepetitive = function(theme) {
            var PollThemeEndpoint = QueryUtils.endpoint('/poll/themes/repetitive/' + theme.id);
            var pollThemeEndpoint = new PollThemeEndpoint({isRepetitive: theme.isRepetitive});
            pollThemeEndpoint.$update().then(function () {
                message.info('main.messages.create.success');
                $scope.refresh();
            }).catch(angular.noop);
        };

        $scope.refresh = function() {
            QueryUtils.loadingWheel($scope, true, true);
            QueryUtils.endpoint("/poll/themes").get({id: $scope.criteria.id}).$promise.then(function (data) {
                if (data.confirmed && $scope.formState.edit) {
                    message.error("main.messages.error.nopermission");
                    $rootScope.back("#/poll/questions/" + $scope.criteria.id + "/view");
                } else {
                    $scope.formState.type = data.type;
                    data.type = undefined;
                    angular.extend($scope.criteria, data);
                    markImages($scope.criteria.themes);
                }
                QueryUtils.loadingWheel($scope, false, true);
            });
        };

        $scope.refresh();

        $scope.addNewTheme = function (row) {
            dialogService.showDialog('poll/poll.theme.add.dialog.html', function (dialogScope) {
                dialogScope.criteria = {};
                if (row) {
                    dialogScope.criteria.nameEt = angular.copy(row.nameEt);
                }
                dialogScope.checkTheme = function() {
                    var ctrl = dialogScope.dialogForm.nameEt;
                    var themes = $scope.criteria.themes;
                    if (themes !== undefined && row === undefined) {
                        var sameTheme = themes.filter(function (theme) {
                            return theme.nameEt === dialogScope.criteria.nameEt;
                        });
                        if (sameTheme === undefined || sameTheme.length > 0) {
                            ctrl.$setValidity('nameEtError', false);
                        } else {
                            ctrl.$setValidity('nameEtError', true);
                        }
                    }
                };
            }, function (submittedDialogScope) {
                FormUtils.withValidForm(submittedDialogScope.dialogForm, function() {
                    var PollThemeEndpoint = QueryUtils.endpoint('/poll/theme/' + $scope.criteria.id);
                    if (row) {
                        PollThemeEndpoint = QueryUtils.endpoint('/poll/theme/' + row.id);
                    }
                    if ($scope.criteria.themes === undefined) {
                        submittedDialogScope.criteria.orderNr = 1;
                    } else {
                        submittedDialogScope.criteria.orderNr = $scope.criteria.themes.length + 1;
                    }
                    var pollThemeEndpoint = new PollThemeEndpoint(submittedDialogScope.criteria);
                    if (row) {
                        pollThemeEndpoint.$update().then(function () {
                            message.info('main.messages.create.success');
                            $scope.refresh();
                        }).catch(angular.noop);
                    } else {
                        pollThemeEndpoint.$save().then(function () {
                            message.info('main.messages.create.success');
                            $scope.refresh();
                        }).catch(angular.noop);
                    }
                });
            });
        };

        $scope.clearRadio = function(question) {
            question.radio = null;
        };

        $scope.deselect = function(list, checkedItem) {
            list.forEach(function (item) {
            if (item.id !== checkedItem.id) {
                item.checked = false;
            }
            });
        };

        $scope.addQuestion = function (row, question) {
            dialogService.showDialog('poll/poll.question.add.dialog.html', function (dialogScope) {
                dialogScope.criteria = {};
                dialogScope.themes = $scope.criteria.themes;
                dialogScope.data = {};
                dialogScope.data.files = [];
                if (row) {
                    dialogScope.criteria.theme = angular.copy(row.id);
                }
                if (dialogScope.criteria.files === undefined) {
                    dialogScope.criteria.files = [];
                }

                if (question) {
                    angular.extend(dialogScope.criteria, angular.copy(question));
                } else {
                    dialogScope.criteria.isRequired = true;
                    dialogScope.criteria.isInRow = true;
                    dialogScope.new = true;
                }

                QueryUtils.endpoint('/poll/questions').query(function(response) {
                    dialogScope.questions = response.filter(function(item) {
                        var notSameId = true;
                        $scope.criteria.themes.forEach(function(theme) {
                            theme.questions.forEach(function(question) {
                                if (question.question === item.id) {
                                    if (question.id === dialogScope.criteria.id) {
                                        notSameId = true;
                                    } else {
                                        notSameId = false;
                                    }
                                }
                            });
                        });
                        return notSameId;
                    });
                });

                dialogScope.getUrl = oisFileService.getUrl;

                dialogScope.loadQuestion = function(questionId) {
                    if (questionId !== undefined && questionId !== "") {
                        var PollEndPoint = QueryUtils.endpoint('/poll/question');
                        PollEndPoint.get({id: questionId}, function (result) {
                            result.theme = dialogScope.criteria.theme;
                            result.question = dialogScope.criteria.question;
                            result.isRequired = dialogScope.criteria.isRequired;
                            result.isInRow = dialogScope.criteria.isInRow;
                            result.files = dialogScope.criteria.files;
                            result.orderNr = dialogScope.criteria.orderNr;
                            result.id = dialogScope.criteria.id;
                            if (dialogScope.criteria.isInRow !== undefined) {
                                result.isInRow = dialogScope.criteria.isInRow;
                            } else {
                                result.isInRow = true;
                            }
                            dialogScope.criteria = result;
                            dialogScope.new = true;
                            dialogScope.checkAnswer();
                        });
                    } else if (questionId === "") {
                        dialogScope.criteria.disabled = false;
                    }
                };

                dialogScope.$watch("criteria.nameEt", function() {
                    dialogScope.checkName(dialogScope.criteria.theme);
                });

                dialogScope.checkName = function(themeId) {
                    var ctrl = dialogScope.dialogForm.nameEt;
                    var themes = $scope.criteria.themes.filter(function (theme) {
                        return theme.id === themeId;
                    });
                    if (themes !== undefined && themes.length === 1) {
                        var theme = themes[0];
                        var sameQuestion = theme.questions.filter(function (question) {
                            return question.nameEt === dialogScope.criteria.nameEt && question.id !== dialogScope.criteria.id;
                        });
                        if (sameQuestion === undefined || sameQuestion.length > 0) {
                            ctrl.$setValidity('nameEtError', false);
                            if (dialogScope.criteria.nameEt !== undefined) {
                                ctrl.$setTouched();
                            }
                        } else {
                            ctrl.$setValidity('nameEtError', true);
                        }
                    }
                };

                dialogScope.checkAnswer = function() {
                    var falseFields = [];
                    for (var index = 0; index < dialogScope.criteria.answers.length; index++) {
                        var ctrl1 = dialogScope.dialogForm[index];
                        for (var index2 = 0; index2 < dialogScope.criteria.answers.length; index2++) {
                            var ctrl2 = dialogScope.dialogForm[index2];
                            if (index !== index2) {
                                var answer1 = dialogScope.criteria.answers[index];
                                var answer2 = dialogScope.criteria.answers[index2];
                                var sameAnswer = answer1.nameEt !== undefined && answer2.nameEt !== undefined &&
                                answer1.nameEt === answer2.nameEt;
                                if (sameAnswer) {
                                    if (!falseFields.includes(ctrl1)) {
                                        falseFields.push(ctrl1);
                                    }
                                    if (!falseFields.includes(ctrl2)) {
                                        falseFields.push(ctrl2);
                                    }
                                }
                                if (ctrl1 !== undefined && !falseFields.includes(ctrl1) && !sameAnswer) {
                                    ctrl1.$setValidity('answerEtError', true);
                                    ctrl1.$setTouched();
                                }
                                if (ctrl2 !== undefined && !falseFields.includes(ctrl2) && !sameAnswer) {
                                    ctrl2.$setValidity('answerEtError', true);
                                    ctrl2.$setTouched();
                                }
                            }
                        }
                    }
                    falseFields.forEach(function (item) {
                        if (item !== undefined) {
                            item.$setValidity('answerEtError', false);
                            item.$setTouched();
                        }
                    });
                };

                dialogScope.getFileUrl = function(file) {
                    return oisFileService.getFileUrl(file);
                };

                dialogScope.openAddFileDialog = function () {
                    dialogService.showDialog('components/file.add.dialog.html', function (dialogScope2) {
                        dialogScope2.addedFiles = dialogScope.criteria.files;
                    }, function (submittedDialogScope) {
                        var fileData = submittedDialogScope.data;
                        oisFileService.getFromLfFile(fileData.file[0], function (file) {
                            fileData = file;
                            dialogScope.criteria.files.push(fileData);
                        });
                    }, null, true);
                };

                dialogScope.addAnswer = function() {
                    var answer = {};
                    if (dialogScope.criteria.answers === undefined) {
                        dialogScope.criteria.answers = [];
                    }
                    dialogScope.criteria.answers.push(answer);
                };
                
                dialogScope.deleteFile = function(file) {
                    dialogService.confirmDialog({prompt: 'apel.deleteFileConfirm'}, function() {
                        ArrayUtils.remove(dialogScope.criteria.files, file);
                    });
                };

                dialogScope.swapCriteria = function(index1, index2) {
                    var temp = dialogScope.criteria.answers[index1];
                    dialogScope.criteria.answers[index1] = dialogScope.criteria.answers[index2];
                    dialogScope.criteria.answers[index2] = temp;
                    temp = dialogScope.criteria.answers[index1].orderNr;
                    dialogScope.criteria.answers[index1].orderNr = dialogScope.criteria.answers[index2].orderNr;
                    dialogScope.criteria.answers[index2].orderNr = temp;
                    dialogScope.checkAnswer();
                };

                dialogScope.deleteAnswer = function(answer) {
                    ArrayUtils.remove(dialogScope.criteria.answers, answer);
                    dialogScope.checkAnswer();
                };

                dialogScope.delete = function() {
                    $scope.deleteQuestion(dialogScope.criteria.id);
                };

            }, function (submittedDialogScope) {
                FormUtils.withValidForm(submittedDialogScope.dialogForm, function() {
                    var PollQuestionEndpoint = QueryUtils.endpoint('/poll/question');
                    if (question) {
                        if (!(submittedDialogScope.criteria.disabled || (submittedDialogScope.new && submittedDialogScope.criteria.question))) {
                            submittedDialogScope.criteria.question = undefined;
                        }
                        var PollQuestionEndpointForUpdate = new PollQuestionEndpoint(submittedDialogScope.criteria);
                        PollQuestionEndpointForUpdate.$update().then(function () {
                            $scope.refresh();
                        }).catch(angular.noop);
                    } else {
                        var theme = $scope.criteria.themes.filter(function (theme) {
                            return theme.id === submittedDialogScope.criteria.theme;
                        })[0];
                        if (theme) {
                            submittedDialogScope.criteria.orderNr = theme.questions.length + 1;
                        } else {
                            submittedDialogScope.criteria.orderNr = 1;
                        }
                        PollQuestionEndpoint = QueryUtils.endpoint('/poll/question/' + submittedDialogScope.criteria.theme);
                        var PollQuestionEndpointForSave = new PollQuestionEndpoint(submittedDialogScope.criteria);
                        PollQuestionEndpointForSave.$save().then(function () {
                            $scope.refresh();
                        }).catch(angular.noop);
                    }
                });
            }, null, true);
        };

        $scope.getUrl = function(photo) {
            return oisFileService.getUrl(photo, 'pollThemeQuestionFile');
        };

        $scope.deleteTheme = function(id) {
            if ($scope.criteria.confirmed && $scope.criteria.themes.length === 1) {
                message.info('poll.questions.confirmedError');
                return;
            }
            dialogService.confirmDialog({ prompt: 'poll.questions.deleteTheme' }, function () {
                var PollThemeEndpoint = QueryUtils.endpoint('/poll/theme/' + id);
                var pollThemeEndpointForDelete = new PollThemeEndpoint();
                    pollThemeEndpointForDelete.$delete().then(function () {
                    message.info('main.messages.delete.success');
                    $scope.refresh();
                }).catch(angular.noop);
            });
        };

        $scope.deleteQuestion = function(id) {
            dialogService.confirmDialog({ prompt: 'poll.questions.deleteQuestion' }, function () {
                var PollQuestionEndpoint = QueryUtils.endpoint('/poll/question/' + id);
                var PollQuestionEndpointForDelete = new PollQuestionEndpoint();
                    PollQuestionEndpointForDelete.$delete().then(function () {
                    message.info('main.messages.delete.success');
                    $scope.refresh();
                }).catch(angular.noop);
            });
        };

        $scope.test = function () {
            dialogService.showDialog('poll/poll.test.dialog.html', function (dialogScope) {
                dialogScope.formState = {};
                dialogScope.criteria = $scope.criteria;

                dialogScope.refresh = function() {
                    var PollEndPoint = QueryUtils.endpoint('/poll');
                    QueryUtils.loadingWheel(dialogScope, true, true);
                    PollEndPoint.get({id: $scope.criteria.id}, function (result) {
                        dialogScope.criteria.foreword = result.foreword;
                        dialogScope.criteria.afterword = result.afterword;
                        QueryUtils.loadingWheel(dialogScope, false, true);
                    });
                };

                dialogScope.clearRadio = function(question) {
                    question.radio1 = null;
                };

                dialogScope.refresh();

                dialogScope.getUrl = function(photo) {
                    return oisFileService.getUrl(photo, 'pollThemeQuestionFile');
                };
            }, null, null, true);
        };

        $scope.swapTheme = function(index1, index2) {
            var temp = $scope.criteria.themes[index1];
            $scope.criteria.themes[index1] = $scope.criteria.themes[index2];
            $scope.criteria.themes[index2] = temp;
            temp = $scope.criteria.themes[index1].orderNr;
            $scope.criteria.themes[index1].orderNr = $scope.criteria.themes[index2].orderNr;
            $scope.criteria.themes[index2].orderNr = temp;
            var PollEndPoint =  QueryUtils.endpoint('/poll/themes');
            var pollEndPoint = new PollEndPoint($scope.criteria);
            if ($scope.criteria.id) {
                pollEndPoint.$update().then(function () {
                    message.info('main.messages.create.success');
                    //refresh();
                }).catch(angular.noop);
            }
        };

        $scope.swapQuestion = function(theme, index1, index2) {
            var temp = theme.questions[index1];
            theme.questions[index1] = theme.questions[index2];
            theme.questions[index2] = temp;
            temp = theme.questions[index1].orderNr;
            theme.questions[index1].orderNr = theme.questions[index2].orderNr;
            theme.questions[index2].orderNr = temp;
            var PollEndPoint =  QueryUtils.endpoint('/poll/questions');
            var pollEndPoint = new PollEndPoint(theme);
            pollEndPoint.$update().then(function () {
                message.info('main.messages.create.success');
                //refresh();
            }).catch(angular.noop);
        };

    }).controller('PollResultsController', function ($scope, $route) {
        $scope.auth = $route.current.locals.auth;
        $scope.currentNavItem = 'poll.results';
        $scope.formState = {};
        $scope.criteria = {};
        if ($route.current.params.id) {
            $scope.criteria.id = $route.current.params.id;
        }
    }).controller('PollStatisticsController', function ($scope, $route) {
        $scope.auth = $route.current.locals.auth;
    }).controller('PollEditController', function ($scope, $route, QueryUtils, dialogService, message, FormUtils, Classifier, $location, oisFileService, $rootScope, $q, ArrayUtils) {
        $scope.auth = $route.current.locals.auth;
        $scope.currentNavItem = 'poll.data';
        $scope.formState = {};
        $scope.criteria = {};
        $scope.now = new Date();
        $scope.studentGroupAllowed = false;
        $scope.showOccupations = false;
        $scope.formState.names = QueryUtils.endpoint('/poll/pollNames').query();

        var PollEndPoint = QueryUtils.endpoint('/poll');
        var clMapper = Classifier.valuemapper({
            status: 'KYSITLUS_STAATUS'
        });

        $scope.changeDisplayDates = function() {
            if (!$scope.displayDates) {
                $scope.displayDates = true;
            }
        };
        $scope.saveDates = function() {
            FormUtils.withValidForm($scope.pollForm, function() {
                var Endpoint = QueryUtils.endpoint('/poll/changeDates/' + $scope.criteria.id);
                var pollEndPoint = new Endpoint({
                        validFrom: $scope.criteria.validFrom,
                        validThru: $scope.criteria.validThru,
                        reminderDt: $scope.criteria.reminderDt
                    });
                pollEndPoint.$update().then(function () {
                    message.info('main.messages.create.success');
                    $scope.displayDates = false;
                });
            });
        };

        $scope.$watch('formState.journal', function () {
            if (angular.isDefined($scope.formState.journal) && $scope.formState.journal !== null) {
                $scope.addJournal();
            }
        });

        $scope.addJournal = function() {
            if (!angular.isArray($scope.criteria.journals)) {
                $scope.criteria.journals = [];
            }
            if ($scope.criteria.journals.some(function (code) {
                return $scope.formState.journal !== undefined && 
                    $scope.formState.journal !== null && 
                    code.id === $scope.formState.journal.id;
            })) {
                $scope.formState.journal = undefined;
                message.error('poll.basicData.duplicateJournal');
                return;
            }
            $scope.criteria.journals.push(angular.copy($scope.formState.journal));
            $scope.formState.journal = undefined;
            $scope.pollForm.$setDirty();
        };

        $scope.removeJournal = function(journal) {
            ArrayUtils.remove($scope.criteria.journals, journal);
            $scope.pollForm.$setDirty();
        };

        $scope.checkNames = function(name) {
            var sameNames = $scope.formState.names.filter(function (item) {
                if ($scope.criteria.id !== undefined) {
                    return $scope.criteria.id !== item.id && name === item.nameEt;
                } else {
                    return name === item.nameEt;
                }
            });
            var ctrl = $scope.pollForm.nameEt;
            if (angular.isArray(sameNames) && sameNames.length > 0) {
                ctrl.$setValidity('incorrectName', false);
            } else {
                ctrl.$setValidity('incorrectName', true);
            }
        };

        $scope.refresh = function() {
            PollEndPoint.get({id: $scope.criteria.id}, function (result) {
                if (result.status.code === 'KYSITLUS_STAATUS_K' && $scope.formState.edit) {
                    message.error("main.messages.error.nopermission");
                    $rootScope.back("#/poll/" + $scope.criteria.id + "/view");
                }
                $scope.formState.targetCodes = result.targetCodes;
                $scope.formState.studentGroups = result.studentGroups;
                $scope.formState.status = result.status;
                $scope.formState.insertedBy = result.insertedBy;
                $scope.formState.changedBy = result.changedBy;
                $scope.formState.isTeacherComment = result.isTeacherComment;
                $scope.formState.isTeacherCommentVisible = result.isTeacherCommentVisible;
                $scope.formState.responded = result.responded;
                // Prevent readOnly fields from being sent to backend
                result.status = undefined;
                result.insertedBy = undefined;
                result.changedBy = undefined;

                result.reminderDt = new Date(result.reminderDt);
                result.validFrom = new Date(result.validFrom);
                result.validThru = new Date(result.validThru);
                $scope.criteria = result;
                $scope.disabled = true;
                $scope.checkCases();
                $scope.pollForm.$setPristine();
                $q.all(clMapper.promises).then(function () {
                    clMapper.objectmapper($scope.formState);
                });
            });
        };

        if ($route.current.params.id) {
            $scope.criteria.id = $route.current.params.id;
            if ($route.current.templateUrl === "poll/poll.edit.html") {
                $scope.formState.edit = true;
            }
            $scope.refresh();
        } else {
            $scope.formState.isTeacherComment = true;
            $scope.formState.isTeacherCommentVisible = true;
        }

        QueryUtils.endpoint('/autocomplete/classifiers').query({mainClassCode: "KYSITLUS_SIHT"}, function (targetCodes) {
            $scope.formState.aim = targetCodes;
            $scope.formState.aimBackup = targetCodes;
            $scope.formState.aimByCode = {}; 
            targetCodes.forEach(function (it) {
                $scope.formState.aimByCode[it.code] = it;
            });
            $scope.checkCases();
        });

        $scope.addAllTargetCodes = function () {
            $scope.formState.targetCodes = angular.copy($scope.formState.aim.filter($scope.targetCodeFilter));
            $scope.checkCases();
            $scope.pollForm.$setDirty();
        };

        $scope.test = function () {
            dialogService.showDialog('poll/poll.test.dialog.html', function (dialogScope) {
                dialogScope.formState = {};
                dialogScope.criteria = {};
                dialogScope.criteria.id = $scope.criteria.id;
                dialogScope.criteria.foreword = $scope.criteria.foreword;
                dialogScope.criteria.afterword = $scope.criteria.afterword;
                dialogScope.refresh = function() {
                    QueryUtils.loadingWheel(dialogScope, true, true);
                    QueryUtils.endpoint("/poll/themes").get({id: dialogScope.criteria.id}).$promise.then(function (data) {
                        angular.extend(dialogScope.criteria, data);
                        markImages(dialogScope.criteria.themes);
                        QueryUtils.loadingWheel(dialogScope, false, true);
                    });
                };

                dialogScope.clearRadio = function(question) {
                    question.radio1 = null;
                };

                dialogScope.refresh();

                dialogScope.getUrl = function(photo) {
                    return oisFileService.getUrl(photo, 'pollThemeQuestionFile');
                };
            }, null, null, true);
        };

        $scope.checkReminder = function () {
            var ctrl = $scope.pollForm.reminderDt;
            if (new Date($scope.criteria.reminderDt) < new Date($scope.criteria.validFrom) ||
            new Date($scope.criteria.reminderDt) > new Date($scope.criteria.validThru)) {
                ctrl.$setValidity('incorrect', false);
            } else {
                ctrl.$setValidity('incorrect', true);
            }
        };

        $scope.targetCodeFilter = function (value) {
            switch($scope.criteria.type) {
                case 'KYSITLUS_P':
                    return value.code === 'KYSITLUS_SIHT_T' || value.code === 'KYSITLUS_SIHT_O' || value.code === 'KYSITLUS_SIHT_E';
                default:
                    return true;
            }
        };

        $scope.saveAndLoad = function() {
            if ($scope.criteria.id) {
                var PollEndPoint = QueryUtils.endpoint('/poll/copy/' + $scope.criteria.id);
                var pollEndPoint = new PollEndPoint();
                pollEndPoint.$save().then(function (response) {
                    message.info('main.messages.create.success');
                    $location.url('/poll/' + response.id + '/edit?_noback');
                }).catch(angular.noop);
            }
        };

        function filterTargetCodes() {
            if ($scope.formState.aimByCode !== undefined) {
                if ($scope.criteria.type === 'KYSITLUS_V' || $scope.criteria.type === 'KYSITLUS_O') {
                    $scope.formState.targetCodes = [angular.copy($scope.formState.aimByCode.KYSITLUS_SIHT_O)];
                } else if ($scope.criteria.type === 'KYSITLUS_T') {
                    $scope.formState.targetCodes = [angular.copy($scope.formState.aimByCode.KYSITLUS_SIHT_T)];
                }
            }
        }

        function controlOccupations() {
            var filteredListOccupation = $scope.formState.targetCodes.filter(function(it) {return it.code === 'KYSITLUS_SIHT_T';});
            if (filteredListOccupation.length > 0) {
                $scope.showOccupations = true;
                if ($scope.formState.occupations === undefined || $scope.formState.occupations.length === 0) {
                    $scope.formState.occupations = QueryUtils.endpoint('/teachers/teacheroccupations').query();
                }
            } else {
                $scope.showOccupations = false;
                $scope.criteria.teacherOccupations = [];
            }
        }

        function controlJournalDates() {
            var filteredListJournalDates = $scope.formState.targetCodes.filter(function(it) {return it.code === 'KYSITLUS_SIHT_E';});
            if (filteredListJournalDates.length > 0) {
                $scope.showJournalDates = true;
            } else {
                $scope.showJournalDates = false;
            }
        }

        $scope.checkCases = function () {
            filterTargetCodes();
            if (!angular.isArray($scope.formState.targetCodes)) {
                $scope.studentGroupAllowed = false;
                $scope.showOccupations = false;
                $scope.showJournalDates = false;
                $scope.formState.studentGroups = [];
                return;
            }
            controlOccupations();
            controlJournalDates();
            var filteredListO = $scope.formState.targetCodes.filter(function(it) {return it.code === 'KYSITLUS_SIHT_O';});
            if ($scope.criteria.type === 'KYSITLUS_P' && filteredListO.length > 0) {
                $scope.studentGroupAllowed = true;
                return;
            }
            var filteredListY = $scope.formState.targetCodes.filter(function(it) {return it.code === 'KYSITLUS_SIHT_O' || it.code === 'KYSITLUS_SIHT_L';});
            if ($scope.criteria.type === 'KYSITLUS_Y' && filteredListY.length > 0) {
                $scope.studentGroupAllowed = true;
                return;
            }
            $scope.studentGroupAllowed = false;
            $scope.formState.studentGroups = [];
        };

        $scope.save = function (confirm) {
            if ((!angular.isArray($scope.criteria.journals) || $scope.criteria.journals.length === 0) && $scope.criteria.type === 'KYSITLUS_O' &&
                (!$scope.auth.higher || ($scope.auth.higher && $scope.auth.vocational && ($scope.criteria.studyPeriod === undefined || $scope.criteria.studyPeriod === null)))) {
                message.error('poll.basicData.atleastOneJournal');
                return;
            }
            if (!angular.isArray($scope.formState.targetCodes) && $scope.criteria.type !== 'KYSITLUS_O' && $scope.criteria.type !== 'KYSITLUS_T' && $scope.criteria.type !== 'KYSITLUS_V') {
                message.error('poll.basicData.atleastOne');
                return;
            }
            $scope.criteria.targetCodes = $scope.formState.targetCodes.map(function(it) { return it.code;});
            if ($scope.criteria.targetCodes.length === 0  && $scope.criteria.type !== 'KYSITLUS_O' && $scope.criteria.type !== 'KYSITLUS_T' && $scope.criteria.type !== 'KYSITLUS_V') {
                message.error('poll.basicData.atleastOne');
                return;
            }
            if (angular.isArray($scope.formState.studentGroups)) {
                $scope.criteria.studentGroups = $scope.formState.studentGroups.map(function(it) { return it.id;});
            }
            if(confirm) {
                $scope.criteria.status = 'KYSITLUS_STAATUS_K';
            }
            var pollEndPoint = new PollEndPoint($scope.criteria);
            FormUtils.withValidForm($scope.pollForm, function() {
                if ($scope.criteria.id) {
                    pollEndPoint.$update().then(function (result) {
                        message.info('main.messages.create.success');
                        if (confirm) {
                            $location.url('/poll/' + result.id + '/view?_noback');
                        } else {
                            $scope.refresh(); 
                        }
                    }).catch(angular.noop);
                } else {
                    pollEndPoint.$save().then(function (result) {
                        message.info('main.messages.create.success');
                        $location.url('/poll/' + result.id + '/edit?_noback');
                    }).catch(angular.noop);
                }
            });
        };

        $scope.deletePoll = function() {
            dialogService.confirmDialog({ prompt: 'poll.basicData.deleteConfirm' }, function () {
                var PollEndpoint = QueryUtils.endpoint('/poll/' + $scope.criteria.id);
                var PollEndpointForDelete = new PollEndpoint();
                    PollEndpointForDelete.$delete().then(function () {
                    message.info('main.messages.delete.success');
                    $location.url('/poll?_menu');
                }).catch(angular.noop);
            });
        };
        
        $scope.addTargetCode = function () {
            if ($scope.formState.targetCode === "") {
                return;
            }
            if (!angular.isArray($scope.formState.targetCodes)) {
            $scope.formState.targetCodes = [];
            }
            if ($scope.formState.targetCodes.some(function (code) {
                return code.code === $scope.formState.targetCode;
            })) {
                $scope.formState.targetCode = "";
                message.error('poll.basicData.duplicateTargetCode');
                return;
            }
            $scope.formState.targetCodes.push($scope.formState.aimByCode[$scope.formState.targetCode]);
            $scope.formState.targetCode = "";
            $scope.checkCases();
        };
        
        $scope.deleteTargetCode = function (targetCode) {
            var index = $scope.formState.targetCodes.indexOf(targetCode);
            if (index !== -1) {
            $scope.formState.targetCodes.splice(index, 1);
            $scope.pollForm.$setDirty();
            }
            $scope.checkCases();
        };

        $scope.removeAllTargetCodes = function () {
            $scope.formState.targetCodes = [];
            $scope.checkCases();
            $scope.pollForm.$setDirty();
        };

        $scope.addAllStudentgroups = function () {
            QueryUtils.endpoint('/autocomplete/studentgroups').query({valid: true}, function (studentgroups) {
                $scope.formState.studentGroups = studentgroups;
                $scope.pollForm.$setDirty();
            });
        };

        $scope.$watch('formState.studentgroup', function () {
            if (angular.isDefined($scope.formState.studentgroup) && $scope.formState.studentgroup !== null) {
                $scope.addStudentGroup();
            }
        });

        $scope.removeConfirm = function () {
            if ($scope.criteria.themes !== 0) {
                dialogService.confirmDialog({
                    prompt: 'poll.basicData.confirmRemoveConfirm'
                }, function () {
                    QueryUtils.endpoint('/poll/unConfirm/' + $scope.criteria.id).put({}, function () {
                        message.info('poll.basicData.removed');
                        $scope.refresh();
                    });
                });
            } else {
                message.error('poll.basicData.removeConfirmError');
            }
        };

        $scope.confirm = function () {
            if ($scope.criteria.themes !== 0) {
                dialogService.confirmDialog({
                    prompt: 'poll.basicData.confirm'
                }, function () {
                    $scope.save(true);
                });
            } else {
                message.error('poll.basicData.confirmError');
            }
        };

        $scope.addStudentGroup = function () {
            if (!angular.isArray($scope.formState.studentGroups)) {
            $scope.formState.studentGroups = [];
            }
            if ($scope.formState.studentGroups.some(function (code) {
                return $scope.formState.studentgroup !== undefined && 
                    $scope.formState.studentgroup !== null && 
                    code.id === $scope.formState.studentgroup.id;
            })) {
                $scope.formState.studentgroup = undefined;
                message.error('poll.basicData.duplicateTargetCode');
                return;
            }
            $scope.formState.studentGroups.push(angular.copy($scope.formState.studentgroup));
            $scope.formState.studentgroup = undefined;
            $scope.pollForm.$setDirty();
        };

        $scope.removeAllStudentgroups = function () {
            $scope.formState.studentGroups = [];
            $scope.pollForm.$setDirty();
        };

        $scope.deleteStudentgroup = function (studentgroup) {
            var index = $scope.formState.studentGroups.indexOf(studentgroup);
            if (index !== -1) {
            $scope.formState.studentGroups.splice(index, 1);
            $scope.pollForm.$setDirty();
            }
        };

        $scope.emptyLists = function() {
            $scope.formState.studentGroups = [];
            $scope.formState.targetCodes = [];
            $scope.formState.targetCode = "";
            $scope.pollForm.$setDirty();
            return true;
        };

    }).controller('PollSupervisorController', function ($scope, $route, oisFileService, FormUtils, $location, message, QueryUtils, dialogService) {
        $scope.formState = {};
        var Endpoint = QueryUtils.endpoint('/poll/supervisor/' + $route.current.params.uuid + '/saveAnswer/');
        QueryUtils.endpoint('/poll/supervisor').get({id: $route.current.params.uuid}).$promise.then(function(response) {
            $scope.criteria = response;
            markImages($scope.criteria.themes);
        }).catch(function (error) {
            $location.url('/');
        });

        $scope.getUrl = function(photo) {
            return oisFileService.getUrl(photo, 'pollThemeQuestionFile');
        };

        $scope.confirm = function() {
            var hasErrors = $scope.checkErrors();
            FormUtils.withValidForm($scope.responseForm, function() {
                if (!hasErrors) {
                    dialogService.confirmDialog({ prompt: 'poll.answer.confirm' }, function () {
                        var Endpoint = QueryUtils.endpoint('/poll/supervisor/' + $route.current.params.uuid + '/saveAnswer/final');
                        var pollEndPoint = new Endpoint($scope.criteria);
                        pollEndPoint.$update().then(function () {
                            dialogService.messageDialog({ prompt: 'poll.answer.confirmed' }, function () {
                                $location.url('/');
                            });
                        });
                    });
                } else {
                    message.error('main.messages.form-has-errors');
                }
            });
        };

        $scope.save = function () {
            var pollEndPoint = new Endpoint($scope.criteria);
            pollEndPoint.$postWithoutLoad();
        };

        $scope.clearRadio = function(question) {
            question.answerTxt = null;
            $scope.save();
        };

        $scope.checkErrors = function() {
            var hasErrors = false;
            $scope.criteria.themes.forEach(function(theme) {
              theme.questions.forEach(function (question) {
                if ((question.type === 'VASTUS_M' || question.type === 'VASTUS_S') && question.isRequired) {
                  var showError = true;
                  question.answers.forEach(function(answer) {
                    if (answer.chosen === true) {
                      showError = false;
                    }
                  });
                  if (showError) {
                    question.requiredError = true;
                    hasErrors = true;
                  } else {
                    question.requiredError = false;
                  }
                } else if (question.type === 'VASTUS_R'  && question.isRequired && (question.answerTxt === undefined || question.answerTxt === null)) {
                  question.requiredError = true;
                  hasErrors = true;
                } else {
                  question.requiredError = false;
                }
              });
            });
            return hasErrors;
          };
    });
}());