(function () {
    'use strict';

    function openAllResults(scope, QueryUtils, dialogService, pollId, journalOrSubjectId, subjectStudyPeriodId, isSubject) {
        QueryUtils.loadingWheel(scope, true);
        var pollableObject = {id: pollId};
        if (journalOrSubjectId !== undefined) {
            if (isSubject) {
                pollableObject.subjectId = journalOrSubjectId;
                pollableObject.subjectStudyPeriodId = subjectStudyPeriodId;
            } else {
                pollableObject.journalId = journalOrSubjectId;
            }
        }
        QueryUtils.endpoint("/poll/results/all").get(pollableObject).$promise.then(function (response) {
            QueryUtils.loadingWheel(scope, false);
            dialogService.showDialog('poll/poll.results.all.dialog.html', function (dialogScope) {
                dialogScope.content = response.content;
                dialogScope.calculate = function(answers, question, theme) {
                    var allAnswers = question.allAnswers;
                    if (question.type === 'VASTUS_S') {
                        // student council answers should calculate percentage over theme not question
                        allAnswers = theme.questions.flatMap(function (question) {
                            return question.allAnswers;
                        }).reduce(function (a, b) {
                            return a + b;
                        }, 0);
                    }
                    if (allAnswers === null || allAnswers === undefined || allAnswers === 0) { return 0; }
                    var percentage = answers / allAnswers * 100;
                    return percentage.toFixed(1);
                };
            });
        });
    }
    
    function getScales(graphTheme) {
        return {
            xAxes: [{
                ticks: {
                    beginAtZero: true,
                    max: graphTheme.max,
                    callback: function(value) {
                        if (!value.toString().includes('.')) {
                            return value;
                        }
                    }   
                }
                
            }],
            yAxes: [{
                ticks: {
                    display:false
                }
            }]
        };
    }

    function prepareTheme(graphTheme, scope, average, responseCount) {
        var translatedLabels = [];
        graphTheme.labels.forEach(function (label) {
            translatedLabels.push(scope.currentLanguageNameField(label));
        });
        var translatedLabelOverride = [];
        if (scope.formState.type !== 'KYSITLUS_V') {
            graphTheme.labelOverride.forEach(function (labelOverride) {
                var completeString = "";
                labelOverride.pointBorderColor.forEach(function (pointBorderColor){
                    completeString += scope.currentLanguageNameField(pointBorderColor.answer) + 
                    "(" + pointBorderColor.answerNr + ")" + 
                    ": " + pointBorderColor.answers + "\n";
                });
                // checkboxes
                if (graphTheme.isCheckbox) {
                    completeString += average + ": " + labelOverride.average + "\n";
                }
                completeString += responseCount + ": " + labelOverride.sum;
                translatedLabelOverride.push(completeString);
            });
        }
        graphTheme.labelOverride.forEach(function (labelOverride) {
            labelOverride.pointBorderColor = translatedLabelOverride;
        });
        graphTheme.labels = translatedLabels;
        graphTheme.options.scales = getScales(graphTheme);
        graphTheme.options.tooltips = tooltips;
        graphTheme.options.layout = { padding: { top: 5 } };
    }

    function addQuestion(ArrayUtils, dialogService, QueryUtils, oisFileService, FormUtils, scope, message, row, question, formThemes, type) {
        dialogService.showDialog('poll/poll.question.add.dialog.html', function (dialogScope) {
            dialogScope.criteria = {};
            dialogScope.onlyQuestion = scope.new;

            dialogScope.checkType = function(type) {
                if (type !== 'VASTUS_S' && scope.independent) {
                    dialogScope.answerAddingDisabled = false;
                } else if (type === 'VASTUS_S' && scope.independent){
                    dialogScope.criteria.answers = [{}];
                    dialogScope.criteria.answers[0].answerNr = 1;
                    dialogScope.answerAddingDisabled = true;
                }
            };
            
            dialogScope.addAnswer = function() {
                var answer = {};
                if (dialogScope.criteria.answers === undefined) {
                    dialogScope.criteria.answers = [];
                }
                dialogScope.criteria.answers.push(answer);
            };

            dialogScope.typeKysitlusV = function() {
                dialogScope.criteria.type = 'VASTUS_S';
                dialogScope.pickingAnswerTypeDisabled = true;
                dialogScope.criteria.answers = [{}];
                dialogScope.criteria.answers[0].answerNr = 1;
                dialogScope.answerAddingDisabled = true;
            };

            if (type !== 'KYSITLUS_V' && !scope.independent) {
                dialogScope.answerTypeFilter = ['VASTUS_S'];
            } else if (type === 'KYSITLUS_V' && scope.independent) {
                dialogScope.criteria.answers = [{}];
                dialogScope.criteria.answers[0].answerNr = 1;
                dialogScope.answerAddingDisabled = true;
            } else if (type === 'KYSITLUS_V') {
                dialogScope.typeKysitlusV();
            }

            dialogScope.themes = formThemes;
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

            if (type !== undefined) {
                QueryUtils.endpoint('/poll/questions').query({type: type},function(response) {
                    dialogScope.questions = response.filter(function(item) {
                        var notSameId = true;
                        formThemes.forEach(function(theme) {
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
            }

            dialogScope.getUrl = oisFileService.getUrl;

            dialogScope.loadQuestion = function(questionId) {
                if (questionId !== undefined && questionId !== "") {
                    var PollEndPoint = QueryUtils.endpoint('/poll/question');
                    PollEndPoint.get({id: questionId}, function (result) {
                        // Student council should only have 1 answer
                        // Older student council questions might have over 1 answer
                        if (result.answers !== undefined && result.answers.length > 1 && result.type === "VASTUS_S") {
                            message.error("poll.messages.studentCouncilAnswersError");
                            return;
                        }
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
                if (formThemes) {
                    var ctrl = dialogScope.dialogForm.nameEt;
                    var themes = formThemes.filter(function (theme) {
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
                scope.deleteQuestion(dialogScope.criteria.id);
            };

            dialogScope.filterAnswerType = function () {
                return dialogScope.apelSchools.filter(function (it) { return it.ehisSchool; }).map(function(it) {
                  if (dialogScope.record.newTransferableSubjectOrModule.newApelSchool.ehisSchool !== it.ehisSchool) {
                    return it.ehisSchool;
                  }
                });
              };

            dialogScope.checkAnswers = function() {
                if (dialogScope.criteria.isRequired && dialogScope.criteria.type !== 'VASTUS_T' && 
                (dialogScope.criteria.answers === undefined || dialogScope.criteria.answers.length === 0)) {
                    message.error('poll.questions.noAnswersError');
                    return false;
                }
                return true;
            };

        }, function (submittedDialogScope) {
            FormUtils.withValidForm(submittedDialogScope.dialogForm, function() {
                var PollQuestionEndpoint = QueryUtils.endpoint('/poll/pollThemeQuestion');
                if (question) {
                    if (!(submittedDialogScope.criteria.disabled || (submittedDialogScope.new && submittedDialogScope.criteria.question))) {
                        submittedDialogScope.criteria.question = undefined;
                    }
                    var PollQuestionEndpointForUpdate = new PollQuestionEndpoint(submittedDialogScope.criteria);
                    PollQuestionEndpointForUpdate.$update().then(function () {
                        scope.refresh();
                    }).catch(angular.noop);
                } else {
                    if (formThemes) {
                        var theme = formThemes.filter(function (theme) {
                            return theme.id === submittedDialogScope.criteria.theme;
                        })[0];
                        if (theme) {
                            submittedDialogScope.criteria.orderNr = theme.questions.length + 1;
                        } else {
                            submittedDialogScope.criteria.orderNr = 1;
                        }
                    }
                    if (submittedDialogScope.criteria.theme) {
                        PollQuestionEndpoint = QueryUtils.endpoint('/poll/pollThemeQuestion/' + submittedDialogScope.criteria.theme);
                    } else {
                        PollQuestionEndpoint = QueryUtils.endpoint('/poll/question');
                    }
                    var PollQuestionEndpointForSave = new PollQuestionEndpoint(submittedDialogScope.criteria);
                    PollQuestionEndpointForSave.$save().then(function () {
                        scope.refresh();
                    }).catch(angular.noop);
                }
            });
        }, null, true);
    }

    function openResponse(row, response, dialogService, oisFileService) {
        dialogService.showDialog(
          row.isThemePageable ? 'poll/poll.response.by.theme.dialog.html' : 
          (response.type === 'KYSITLUS_O' ? 
          'poll/poll.response.by.subjectOrJournal.dialog.html' : 
          'poll/poll.response.dialog.html'), function (dialogScope) {
          dialogScope.formState = {};
          dialogScope.formState.viewOnly = true;
          dialogScope.formState.isThemePageable = row.isThemePageable;
          dialogScope.formState.name = row.name;
          var themesWithSubjectOrJournal = response.themes.filter(function(theme) {
            return theme.journal !== null || theme.subject !== null;
          });
          dialogScope.hasSubjectOrJournal = themesWithSubjectOrJournal.length !== 0;
          dialogScope.criteria = response;
          dialogScope.getUrl = function(photo) {
              return oisFileService.getUrl(photo, 'pollThemeQuestionFile');
          };
  
          // Map journals or subjects with repedetive themes
          if (!row.isThemePageable && response.type === 'KYSITLUS_O') {
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
            if (dialogScope.criteria.type === 'KYSITLUS_O' && dialogScope.criteria.foreword !== null) {
                dialogScope.showForeword = true;
            } else {
                dialogScope.formState.themeBySubjectOrJournalId[0].show = true;
            }
          } else {
            if (dialogScope.criteria.type === 'KYSITLUS_O' && dialogScope.criteria.foreword !== null) {
                dialogScope.showForeword = true;
            } else {
                dialogScope.criteria.themes[0].show = true;
            }
          }
  
          dialogScope.previousSubjectOrJournal = function(index) {
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

          dialogScope.firstPage = function(themeList) {
            return dialogScope.formState.themeBySubjectOrJournalId[0] === themeList;
          };
  
          dialogScope.nextSubjectOrJournal = function(index) {
            if (index === -1) {
              dialogScope.showForeword = false;
            } else {
              dialogScope.formState.themeBySubjectOrJournalId[index].show = false;
            }
            if (index === Object.values(dialogScope.formState.themeBySubjectOrJournalId).length - 1) {
              dialogScope.showAfterword = true;
            } else {
              dialogScope.formState.themeBySubjectOrJournalId[index + 1].show = true;
            }
          };

          dialogScope.subjectListLength = function() {
            return dialogScope.formState.themeBySubjectOrJournalId.length;
          };
  
          dialogScope.previousTheme = function(index) {
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
  
          dialogScope.nextTheme = function(index) {
            if (index === -1) {
              dialogScope.showForeword = false;
            } else {
              dialogScope.criteria.themes[index].show = false;
            }
            if (index === dialogScope.criteria.themes.length - 1) {
              dialogScope.showAfterword = true;
            } else {
              dialogScope.criteria.themes[index + 1].show = true;
            }
          };
  
          dialogScope.close = function() {
            dialogScope.cancel();
          };
        }, null, null, true);
    }

    var customTooltips = function(tooltip) {
        // Tooltip Element
        var tooltipEl = document.getElementById('chartjs-tooltip');
        if (!tooltipEl) {
            tooltipEl = document.createElement('div');
            tooltipEl.id = 'chartjs-tooltip';
            tooltipEl.innerHTML = '<table style="table-layout: fixed"></table>';
            this._chart.canvas.parentNode.appendChild(tooltipEl);
        }
        // Hide if no tooltip
        if (tooltip.opacity === 0) {
            tooltipEl.style.opacity = 0;
            return;
        }
        // Set caret Position
        tooltipEl.classList.remove('above', 'below', 'no-transform');
        if (tooltip.yAlign) {
            tooltipEl.classList.add(tooltip.yAlign);
        } else {
            tooltipEl.classList.add('no-transform');
        }
        function getBody(bodyItem) {
            return bodyItem.lines;
        }
        // Set Text
        if (tooltip.body) {
            var titleLines = tooltip.title || [];
            var bodyLines = tooltip.body.map(getBody);
            var innerHtml = '<thead>';
            titleLines.forEach(function(title) {
                innerHtml += '<tr><th style="width: 100%; word-break: break-all">' + title + '</th></tr>';
            });
            innerHtml += '</thead><tbody>';
            tooltip.beforeBody.forEach(function(addInfo) {
                innerHtml += '<tr><td style="width: 100%; word-break: break-all">' + addInfo + '</td></tr>';
            });
            bodyLines.forEach(function(body, i) {
                var colors = tooltip.labelColors[i];
                var style = 'background:' + colors.backgroundColor.replace(0.2, 1);
                style += '; border-color:' + colors.borderColor.replace(0.2, 1);
                style += '; border-width: 2px;';
                var span = '<span class="chartjs-tooltip-key" style="' + style + '"></span>';
                innerHtml += '<tr><td>' + span + body + '</td></tr>';
            });
            innerHtml += '</tbody>';
            var tableRoot = tooltipEl.querySelector('table');
            tableRoot.innerHTML = innerHtml;
        }
        var positionY = this._chart.canvas.offsetTop;
        // offsetTop doesnt take scrolling into account
        positionY -= document.getElementsByTagName("md-dialog-content")[0].scrollTop;
        var positionX = this._chart.canvas.offsetLeft;
        var max = tooltipEl.offsetParent.offsetHeight;
        // Display, position, and set styles for font
        tooltipEl.style.opacity = 1;
        tooltipEl.style.left = positionX + 100 + 'px';
        // Calculate max height of parent and make sure
        // the lower part of tooltip wont go out of the screen
        if (positionY + tooltip.caretY + tooltipEl.offsetHeight > max) {
            positionY = max - tooltipEl.offsetHeight - 10;
        } else {
            positionY = positionY + tooltip.caretY;
        }
        tooltipEl.style.top = positionY + 'px';
        tooltipEl.style.fontFamily = tooltip._bodyFontFamily;
        tooltipEl.style.fontSize = tooltip.bodyFontSize + 'px';
        tooltipEl.style.fontStyle = tooltip._bodyFontStyle;
        tooltipEl.style.padding = tooltip.yPadding + 'px ' + tooltip.xPadding + 'px';
    };

    var tooltips = {
        callbacks: {
            beforeBody: function(t, d) {
                // Use pointBorderColor to hook additional information
                // Chose pointBorderColor because its already a defined variable in dataset and it's not being used
                return d.datasets[0].pointBorderColor[t[0].index];
            }
         }, 
        enabled: false,
        mode: 'index',
        position: 'nearest',
        custom: customTooltips
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

    function pushToError(errorThemes, theme) {
        if (theme.journal !== null && !errorThemes.includes(theme.journal.nameEt + ': ' + theme.nameEt)) {
            errorThemes.push(theme.journal.nameEt + ': ' + theme.nameEt);
        } else if (theme.subject !== null && !errorThemes.includes(theme.subject.nameEt + ': ' + theme.nameEt)) {
            errorThemes.push(theme.subject.nameEt + ': ' + theme.nameEt);
        } else if (!errorThemes.includes(theme.nameEt)) {
            errorThemes.push(theme.nameEt);
        }
    }

    function loadThemes(scope, dialogScope, QueryUtils, criteria, dialogService, oisFileService, formState) {
        QueryUtils.loadingWheel(scope, true);
        QueryUtils.endpoint("/poll/themes").get({id: dialogScope.criteria.id}).$promise.then(function (data) {
            angular.extend(dialogScope.criteria, data);
            if (criteria.higher) {
                dialogScope.criteria.subjects = [{id: -1, nameEt: "õppeaine kood - Õppeaine nimetus (X EAP)", nameEn: "subject code - subject name (X EAP)"}];
                dialogScope.criteria.journals = [];
                dialogScope.criteria.teacher = {id: -1, nameEt: "Õppejõud", nameEn: "Teacher"};
            } else {
                dialogScope.criteria.subjects = [];
                dialogScope.criteria.journals = [{id: -1, nameEt: "päevik (õpperühm)", nameEn: "journal (student group)"}];
                dialogScope.criteria.teacher = {id: -1, nameEt: "Õpetaja", nameEn: "Teacher"};
            }
            markImages(dialogScope.criteria.themes);
            QueryUtils.loadingWheel(scope, false);
            var repetitiveThemes = dialogScope.criteria.themes.filter(function (theme) {
                return theme.isRepetitive;
            });
            dialogScope.hasSubjectOrJournal = ((dialogScope.criteria.subjects !== null && dialogScope.criteria.subjects.length !== 0) ||
                                              (dialogScope.criteria.journals !== null && dialogScope.criteria.journals.length !== 0)) && repetitiveThemes.length !== 0;
            // Map journals or subjects with repedetive themes
            dialogScope.formState.themeBySubjectOrJournalId = {};
            if (!data.isThemePageable && data.type === 'KYSITLUS_O') {
                if (angular.isDefined(dialogScope.criteria.journals) && dialogScope.criteria.journals.length !== 0 && repetitiveThemes.length !== 0) {
                    dialogScope.formState.themeBySubjectOrJournalId = 
                    mapItems(dialogScope.formState.themeBySubjectOrJournalId, dialogScope.criteria.journals, repetitiveThemes, true);
                }
                if (angular.isDefined(dialogScope.criteria.subjects) && dialogScope.criteria.subjects.length !== 0 && repetitiveThemes.length !== 0) {
                    dialogScope.formState.themeBySubjectOrJournalId = 
                    mapItems(dialogScope.formState.themeBySubjectOrJournalId, dialogScope.criteria.subjects, repetitiveThemes, false);
                }
                dialogScope.criteria.themes.forEach(function (theme) {
                    if (!theme.isRepetitive) {
                        if (dialogScope.formState.themeBySubjectOrJournalId[null] === undefined) {
                            dialogScope.formState.themeBySubjectOrJournalId[null] = [];
                        }
                        dialogScope.formState.themeBySubjectOrJournalId[null].push(theme);
                    }
                });
                if (dialogScope.criteria.type === 'KYSITLUS_O' && dialogScope.criteria.foreword !== null) {
                    dialogScope.showForeword = true;
                } else {
                    Object.values(dialogScope.formState.themeBySubjectOrJournalId)[0].show = true;
                }
            } else if (data.isThemePageable && data.type === 'KYSITLUS_O'){
                var placeHolderList = [];
                dialogScope.criteria.journals.forEach(function (journal) {
                    repetitiveThemes.forEach(function (theme) {
                        theme.journal = angular.copy(journal);
                    });
                    placeHolderList.push.apply(placeHolderList, angular.copy(repetitiveThemes));
                });
                repetitiveThemes.forEach(function (theme) {
                    theme.journal = null;
                });
                if (dialogScope.criteria.subjects !== null) {
                    dialogScope.criteria.subjects.forEach(function (subject) {
                        repetitiveThemes.forEach(function (theme) {
                            theme.subject = angular.copy(subject);
                        });
                        placeHolderList.push.apply(placeHolderList, angular.copy(repetitiveThemes));
                    });
                }
                placeHolderList.push.apply(placeHolderList, dialogScope.criteria.themes.filter(function (theme) {
                    return !theme.isRepetitive;
                }));
                dialogScope.criteria.themes = placeHolderList;
                if (dialogScope.criteria.themes.length !== 0) {
                    if (dialogScope.criteria.type === 'KYSITLUS_O' && dialogScope.criteria.foreword !== null) {
                        dialogScope.showForeword = true;
                    } else {
                        dialogScope.criteria.themes[0].show = true;
                    }
                }
            } else {
                if (dialogScope.criteria.themes.length !== 0) {
                    if (dialogScope.criteria.type === 'KYSITLUS_O' && dialogScope.criteria.foreword !== null) {
                        dialogScope.showForeword = true;
                    } else {
                        dialogScope.criteria.themes[0].show = true;
                    }
                }
            }
            showThemeDialog(dialogService, criteria, dialogScope, formState, oisFileService);
        });
    }

    function mapItems(itemById, list, repetitiveThemes, journal) {
        list.forEach(function (item) {
            if (itemById[item.id] === undefined) {
                itemById[item.id] = [];
                if (journal) {
                    itemById[item.id].journal = item;
                } else {
                    itemById[item.id].subject = item;
                }
            }
            itemById[item.id].push.apply(itemById[item.id], angular.copy(repetitiveThemes));
        });
        return itemById;
    }

    function showThemeDialog(dialogService, criteria, savedScope, formState, oisFileService) {
        dialogService.showDialog(criteria.isThemePageable ? 'poll/poll.test.by.theme.dialog.html' : 
        ((criteria.type === 'KYSITLUS_O' || (formState !== undefined && formState.type === 'KYSITLUS_O')) ? 'poll/poll.test.by.subjectOrJournal.dialog.html' : 
        'poll/poll.test.dialog.html'), function (dialogScope) {
            angular.extend(dialogScope, savedScope);
    
            dialogScope.previousSubjectOrJournal = function(index) {
                if (index === 0) {
                  dialogScope.showForeword = true;
                } else {
                  Object.values(dialogScope.formState.themeBySubjectOrJournalId)[index - 1].show = true;
                }
                if (index === dialogScope.subjectListLength()) {
                  dialogScope.showAfterword = false;
                } else {
                  Object.values(dialogScope.formState.themeBySubjectOrJournalId)[index].show = false;
                }
              };
              
            dialogScope.subjectListLength = function() {
                return Object.values(dialogScope.formState.themeBySubjectOrJournalId).length;
            };

            dialogScope.last = function(index) {
                return index === Object.values(dialogScope.formState.themeBySubjectOrJournalId).length - 1;
            };
    
            dialogScope.first = function(index) {
                return index === 0;
            };

            dialogScope.firstPage = function(themeList) {
                return Object.values(dialogScope.formState.themeBySubjectOrJournalId)[0] === themeList;
            };
    
            dialogScope.nextSubjectOrJournal = function(index) {
                if (index === -1) {
                  dialogScope.showForeword = false;
                } else {
                  Object.values(dialogScope.formState.themeBySubjectOrJournalId)[index].show = false;
                }
                if (index === Object.values(dialogScope.formState.themeBySubjectOrJournalId).length - 1) {
                  dialogScope.showAfterword = true;
                } else {
                  Object.values(dialogScope.formState.themeBySubjectOrJournalId)[index + 1].show = true;
                }
              };

            dialogScope.deselectOther = function(question, theme) {
                if (formState.type === 'KYSITLUS_V') {
                    if (question.answers[0].chosen) {
                        theme.questions.forEach(function (themeQuestion) {
                            if (themeQuestion !== question && themeQuestion.type === 'VASTUS_S') {
                                themeQuestion.answers[0].chosen = false;
                            }
                        });
                    }
                }
            };

            dialogScope.previousTheme = function(index) {
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

            dialogScope.nextTheme = function(index) {
                if (index === -1) {
                  dialogScope.showForeword = false;
                } else {
                  dialogScope.criteria.themes[index].show = false;
                }
                if (index === dialogScope.criteria.themes.length - 1) {
                  dialogScope.showAfterword = true;
                } else {
                  dialogScope.criteria.themes[index + 1].show = true;
                }
              };

            dialogScope.clearRadio = function(question) {
                question.radio1 = null;
            };

            dialogScope.getUrl = function(photo) {
                return oisFileService.getUrl(photo, 'pollThemeQuestionFile');
            };
        }, null, null, true);
    }

    function test(scope, dialogService, criteria, QueryUtils, oisFileService, formState) {
        var savedScope = {};
        savedScope.formState = {};
        savedScope.criteria = {};
        savedScope.criteria.id = criteria.id;
        savedScope.criteria.foreword = criteria.foreword;
        savedScope.criteria.afterword = criteria.afterword;
        loadThemes(scope, savedScope, QueryUtils, criteria, dialogService, oisFileService, formState);
    }

    function checkErrorsFromThemes(themes) {
        var hasErrors = false;
        var errorThemes = [];
        themes.forEach(function(theme) {
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
                pushToError(errorThemes, theme);
              } else {
                question.requiredError = false;
              }
            } else if ((question.type === 'VASTUS_R' || question.type === 'VASTUS_T' || question.type === 'VASTUS_V' )  && question.isRequired && (question.answerTxt === undefined || question.answerTxt === null)) {
              question.requiredError = true;
              hasErrors = true;
              pushToError(errorThemes, theme);
            } else {
              question.requiredError = false;
            }
          });
        });
        return [hasErrors, errorThemes];
    }

    angular.module('hitsaOis').controller('PollListController', function ($scope, $route, QueryUtils, Classifier, $q) {
        $scope.auth = $route.current.locals.auth;
        var clMapper = Classifier.valuemapper({
            typeCode: 'KYSITLUS',
            status: 'KYSITLUS_STAATUS',
            targetCodes: 'KYSITLUS_SIHT'
        });
        /*
        $scope.testEmail = function() {
            QueryUtils.endpoint('/poll/testEmailService').put({}, function () {
                message.info('email saadetud');
            });
        };
        $scope.testStatus = function() {
            QueryUtils.endpoint('/poll/testPollStatusJob').put({}, function () {
                message.info('staatused vahetatud');
            });
        };

        $scope.testGuestStudent = function () {
            QueryUtils.endpoint('/students/testGuestJob').put({}, function () {
                message.info('külalisõpilaste staatused vahetatud');
            });
        };

        $scope.testDirectiveJobs = function () {
            QueryUtils.endpoint('/poll/testDirectiveJobs').put({}, function () {
                message.info('Käskkirjade job\'id tehtud');
            });
        };
        */
        
        $scope.pollNotEnded = function(row) {
            return row.status.code !== 'KYSITLUS_STAATUS_K' && row.status.code !== 'KYSITLUS_STAATUS_L';
        };

        QueryUtils.createQueryForm($scope, '/poll', {order: 'p.validFrom desc, p.inserted desc'}, clMapper.objectmapper);
        $q.all(clMapper.promises).then($scope.loadData);
    }).controller('PollQuestionEditController', function ($scope, $route, QueryUtils, Classifier, $q, dialogService, oisFileService, message, FormUtils) {
        $scope.auth = $route.current.locals.auth;
        $scope.questionId = $route.current.params.id;
        $scope.formState = {};

        var clMapper = Classifier.valuemapper({
            typeCode: 'KYSITLUS',
            status: 'KYSITLUS_STAATUS',
            targetCodes: 'KYSITLUS_SIHT',
            type: 'VASTUS'
        });

        $scope.saveQuestion = function() {
            FormUtils.withValidForm($scope.searchForm, function() {
                var QuestionEndpoint = QueryUtils.endpoint('/poll/updateQuestion');
                // type is not according to the dto
                $scope.formState.question.type = undefined;
                QuestionEndpoint = new QuestionEndpoint($scope.formState.question);
                QuestionEndpoint.$update().then(function (response) {
                    message.info('main.messages.create.success');
                    clMapper.objectmapper(response);
                    $scope.formState.question = response;
                    $scope.searchForm.$setPristine();
                }).catch(angular.noop);
            });
        };

        $scope.checkAnswer = function() {
            var falseFields = [];
            for (var index = 0; index < $scope.formState.question.answers.length; index++) {
                var ctrl1 = $scope.searchForm[index];
                for (var index2 = 0; index2 < $scope.formState.question.answers.length; index2++) {
                    var ctrl2 = $scope.searchForm[index2];
                    if (index !== index2) {
                        var answer1 = $scope.formState.question.answers[index];
                        var answer2 = $scope.formState.question.answers[index2];
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

        $scope.test = function(row) {
            test($scope, dialogService, row, QueryUtils, oisFileService, {type: row.typeCode.code});
        };

        $scope.refresh = function() {
            var QuestionEndPoint = QueryUtils.endpoint('/poll/question');
            QueryUtils.createQueryForm($scope, '/poll/questionPolls/' + $scope.questionId, {order: 'p.validThru desc'}, clMapper.objectmapper);
            $q.all(clMapper.promises).then(function() {
                $scope.loadData();
                QuestionEndPoint.get({id: $scope.questionId}).$promise.then(function (response) {
                    clMapper.objectmapper(response);
                    $scope.formState.question = response;
                    $scope.searchForm.$setPristine();
                });
            });
        };

        $scope.refresh();
    }).controller('PollQuestionsListController', function ($scope, $route, QueryUtils, Classifier, $q, oisFileService, dialogService, FormUtils, ArrayUtils, message) {
        $scope.auth = $route.current.locals.auth;
        $scope.new = true;
        $scope.independent = true;

        $scope.addNewQuestion = function() {
            addQuestion(ArrayUtils, dialogService, QueryUtils, oisFileService, FormUtils, $scope, message);
        };

        var clMapper = Classifier.valuemapper({
            type: 'VASTUS'
        });
        QueryUtils.createQueryForm($scope, '/poll/questionsList', {}, clMapper.objectmapper);

        $scope.refresh = function () {
            $q.all(clMapper.promises).then($scope.loadData);
        };
        $scope.refresh();
    }).controller('PollSubjectsController', function ($scope, $route, QueryUtils, Classifier, $q, dialogService, oisFileService, $translate, FormUtils, message, $location, $timeout) {
        $scope.auth = $route.current.locals.auth;
        $scope.formState = {};
        if (!$scope.auth.isTeacher()) {
            message.error('main.messages.error.nopermission');
            return $location.path('');
        }
        $scope.now = new Date();

        $scope.canViewResult = function(endDate) {
            return $scope.now >= new Date(endDate);
        };

        var clMapper = Classifier.valuemapper({
            type: 'KYSITLUS',
            status: 'KYSITVASTUSSTAATUS'
        });

        $scope.showJournalGraph = function(row) {
            var newScope = {};
            var name = angular.copy(row.name);
            if (row.teacher) {
                name.nameEt = name.nameEt + (row.teacher.nameEt !== undefined && row.teacher.nameEt !== null ? " (" + row.teacher.nameEt + ")" : "");
                name.nameEt = name.nameEn + (row.teacher.nameEn !== undefined && row.teacher.nameEn !== null ? " (" + row.teacher.nameEn + ")" : "");
            }
            newScope.graphName = name;
            newScope.graphName = row.name;
            newScope.journal = true;
            newScope.teacherViewing = true;
            newScope.pollId = row.poll.id;
            var GraphEndpoint = QueryUtils.endpoint('/poll/graphWithoutStudentAnswer');
            GraphEndpoint = new GraphEndpoint({pollId: row.poll.id, journalId: row.name.id});
            loadEndpoint(GraphEndpoint, newScope);
        };

        $scope.showSubjectGraph = function(row) {
            var newScope = {};
            newScope.graphName = row.name;
            newScope.journal = false;
            newScope.teacherViewing = true;
            newScope.pollId = row.poll.id;
            var GraphEndpoint = QueryUtils.endpoint('/poll/graphWithoutStudentAnswer');
            GraphEndpoint = new GraphEndpoint({pollId: row.poll.id, subjectStudyPeriodId: row.teacher.id});
            loadEndpoint(GraphEndpoint, newScope, row.teacher);
        };

        function loadEndpoint(endPoint, newScope, teacher) {
            QueryUtils.loadingWheel($scope, true);
            endPoint.$putWithoutLoad().then(function (response) {
                QueryUtils.loadingWheel($scope, false);
                dialogService.showDialog('poll/poll.answers.dialog.html', function (dialogScope) {
                    dialogScope.graphName = newScope.graphName;
                    dialogScope.canComment = response.canComment;
                    dialogScope.higher = $scope.auth.higher;
                    dialogScope.journal = newScope.journal;
                    dialogScope.teacherViewing = newScope.teacherViewing;
                    dialogScope.pollId = newScope.pollId;
                    dialogScope.formState = {};
                    dialogScope.showGraph = true;
                    dialogScope.criteria = {};
                    // translations
                    var responseCount = $translate.instant('poll.answers.dialog.responseCount');
                    var average = $translate.instant('poll.answers.dialog.average');
                    var individualResponses = $translate.instant('poll.answers.dialog.individualResponseCount');
                    var myAnswers = $translate.instant('poll.answers.dialog.myAnswer');

                    dialogScope.graph = response;
                    if (response.comments !== undefined && response.comments.length === 1 && dialogScope.teacherViewing) {
                        dialogScope.criteria.addInfo = response.comments[0].addInfo;
                        dialogScope.commentRef = response.comments[0].commentRef;
                    }
                    dialogScope.graph.theme.forEach(function (theme) {
                        theme.journalOrSubject.forEach(function (subject) {
                            subject.teachers.forEach(function (teacher) {
                                teacher.graph.forEach(function (graph) {
                                    prepareTheme(graph, $scope, average, responseCount);
                                    graph.labelOverride[1] = {
                                        label: myAnswers
                                    };
                                    // Set legend name (average or response count)
                                    if (graph.isCheckbox) {
                                        setLabelOverride(graph, individualResponses);
                                    } else {
                                        setLabelOverride(graph, average);
                                    }
                                });
                            });
                    });});

                    function setLabelOverride(theme, translatedVal) {
                        if (theme.labelOverride[0] === undefined) {
                            theme.labelOverride[0] = {label: translatedVal};
                        } else {
                            theme.labelOverride[0].label = translatedVal;
                        }
                    }

                    dialogScope.saveComment = function(addInfo) {
                        FormUtils.withValidForm(dialogScope.dialogForm, function() {
                            var GraphEndpoint = QueryUtils.endpoint('/poll/comment/' + dialogScope.pollId);
                            if (dialogScope.journal) {
                                GraphEndpoint = new GraphEndpoint({journalId: dialogScope.graphName.id, comment: addInfo, commentRef: dialogScope.commentRef});
                            } else {
                                GraphEndpoint = new GraphEndpoint({subjectStudyPeriodId: teacher.id, comment: addInfo, commentRef: dialogScope.commentRef});
                            }
                            GraphEndpoint.$putWithoutLoad().then(function (response) {
                                if (response.commentRef === undefined || response.commentRef === null)  {
                                    // comment was deleted
                                    dialogScope.commentRef = undefined;
                                } else {
                                    // comment was saved
                                    dialogScope.commentRef = response.commentRef;
                                }
                            });
                        });
                    };
                    
                }, null, null);
            });
        }

        $scope.openAnswers = function(row, isSubject) {
            if (isSubject) {
                $scope.showSubjectGraph(row);
            } else {
                $scope.showJournalGraph(row);
            }
        };

        QueryUtils.createQueryForm($scope, '/poll/answers/subjects', {order: 'p.validThru desc'}, clMapper.objectmapper);
        $q.all(clMapper.promises).then($scope.loadData);

        $scope.openResponse = function(row) {
            var PollEndPoint;
            if (row.type.code === 'KYSITLUS_O') {
                PollEndPoint = QueryUtils.endpoint('/poll/withAnswersForView/journalOrSubject');
            } else {
                PollEndPoint = QueryUtils.endpoint('/poll/withAnswersForView');
            }
          QueryUtils.loadingWheel($scope, true, true);
          PollEndPoint.get({id: row.id}).$promise.then(function (response) {
            markImages(response.themes);
            QueryUtils.loadingWheel($scope, false, true);
            $timeout(function () {
                openResponse(row, response, dialogService, oisFileService);
            });
          });
        };
    }).controller('PollAnswersController', function ($scope, $route, QueryUtils, Classifier, $q, dialogService, oisFileService, $translate, message, $location, $timeout) {
        $scope.auth = $route.current.locals.auth;
        if ($scope.auth.isLeadingTeacher() || $scope.auth.isMainAdmin()) {
            message.error('main.messages.error.nopermission');
            return $location.path('');
        }

        $scope.canViewResult = function(row) {
            if ($scope.auth.isStudent() && ((((row.isTeacherComment && row.isTeacherCommentVisible) || 
            row.isStudentVisible) && (row.type === 'KYSITLUS_O' || row.type.code === 'KYSITLUS_O')) || 
            row.type === 'KYSITLUS_V' || row.type.code === 'KYSITLUS_V') && 
            $scope.isInThePast(row.validThru)) {
                return true;
            }
            return false;
        };

        $scope.isInThePast = function(validThru) {
            var validThruDate = new Date(validThru);
            var now = new Date();
            validThruDate.setHours(0, 0, 0, 0);
            now.setHours(0, 0, 0, 0);
            if (validThruDate < now) {
                return true;
            }
            return false;
        };

        var clMapper = Classifier.valuemapper({
            type: 'KYSITLUS',
            status: 'KYSITVASTUSSTAATUS'
        });

        QueryUtils.createQueryForm($scope, '/poll/answers', {order: 'p.validThru desc'}, clMapper.objectmapper);
        $q.all(clMapper.promises).then($scope.loadData);

        $scope.openAnswers = function(row) {
            openDialog(row, dialogService, QueryUtils);
        };

        function openDialog(row, dialogService, QueryUtils) {
            QueryUtils.loadingWheel($scope, true);
            QueryUtils.endpoint('/poll/answers').get({id: row.id}).$promise.then(function (response) {
                QueryUtils.loadingWheel($scope, false);

                dialogService.showDialog('poll/poll.answers.dialog.html', function (dialogScope) {
                    dialogScope.formState = {};
                    dialogScope.formState = row;
                    dialogScope.criteria = response;
                    dialogScope.higher = $scope.auth.higher;
                    // translations
                    var responseCount = $translate.instant('poll.answers.dialog.responseCount');
                    var average = $translate.instant('poll.answers.dialog.average');
                    var individualResponses = $translate.instant('poll.answers.dialog.individualResponseCount');
                    var myAnswers = $translate.instant('poll.answers.dialog.myAnswer');
                    var responded = $translate.instant('poll.answers.dialog.responded');

                    function loadEndpoint(endPoint, dialogScope) {
                        endPoint.$putWithoutLoad().then(function (response) {
                            dialogScope.graph = response;
                            if (response.comments !== undefined) {
                                dialogScope.comments = response.comments;
                            }
                            dialogScope.canStudentView = response.canStudentView;
                            dialogScope.studentViewing = $scope.auth.isStudent;
                            dialogScope.showGraph = true;
                            dialogScope.graph.theme.forEach(function (theme) {
                                theme.journalOrSubject.forEach(function(subject) {
                                    subject.teachers.forEach(function (teacher) {
                                        teacher.graph.forEach(function (graph) {
                                            var translatedLabels = [];
                                            graph.labels.forEach(function (label) {
                                                translatedLabels.push($scope.currentLanguageNameField(label));
                                            });
                                            var translatedLabelOverride = [];
                                            graph.labelOverride.forEach(function (labelOverride) {
                                                var completeString = "";
                                                if (dialogScope.formState.type.code !== 'KYSITLUS_V') {
                                                    labelOverride.pointBorderColor.forEach(function (pointBorderColor){
                                                        completeString += $scope.currentLanguageNameField(pointBorderColor.answer) + 
                                                        "(" + pointBorderColor.answerNr + ")" + 
                                                        ": " + pointBorderColor.answers + "\n";
                                                    });
                                                    // checkboxes
                                                    if (graph.isCheckbox) {
                                                        completeString += average + ": " + labelOverride.average + "\n";
                                                    }
                                                    completeString += responseCount + ": " + labelOverride.sum;
                                                    translatedLabelOverride.push(completeString);
                                                } else {
                                                    var overrideIndex = graph.labelOverride.indexOf(labelOverride);
                                                    if (graph.data[1][overrideIndex] === 1) {
                                                        completeString += responded;
                                                        translatedLabelOverride.push(completeString);
                                                    }
                                                }
                                            });
                                            graph.labelOverride.forEach(function (labelOverride) {
                                                labelOverride.pointBorderColor = translatedLabelOverride;
                                            });
                                            graph.labels = translatedLabels;
                                            graph.options.scales = getScales(graph);
                                            graph.options.tooltips = tooltips;
                                            graph.options.layout = { padding: { top: 5 } };
                                            graph.labelOverride[1] = {
                                                label: myAnswers
                                            };
                                            // Set legend name (average or response count)
                                            if (graph.isCheckbox) {
                                                setLabelOverride(graph, individualResponses);
                                            } else {
                                                setLabelOverride(graph, average);
                                            }
                                            if (dialogScope.formState.type.code === 'KYSITLUS_V') {
                                                setLabelOverride(graph, responseCount);
                                                graph.labelOverride[1] = {
                                                    hidden: true,
                                                    label: myAnswers
                                                };
                                            } else {
                                                graph.labelOverride[1] = {
                                                    label: myAnswers
                                                };
                                            }
                                        });
                                    });
                                });
                            });
    
                            function setLabelOverride(theme, translatedVal) {
                                if (theme.labelOverride[0] === undefined) {
                                    theme.labelOverride[0] = {label: translatedVal};
                                } else {
                                    theme.labelOverride[0].label = translatedVal;
                                }
                            }
                        });
                    }

                    dialogScope.showOtherGraph = function() {
                        dialogScope.overall = true;
                        var GraphEndpoint = QueryUtils.endpoint('/poll/graph');
                        GraphEndpoint = new GraphEndpoint({responseId: dialogScope.formState.id, themes: true});
                        loadEndpoint(GraphEndpoint, dialogScope);
                    };

                    dialogScope.showJournalGraph = function(journal) {
                        dialogScope.graphName = journal;
                        dialogScope.journal = true;
                        var GraphEndpoint = QueryUtils.endpoint('/poll/graph');
                        GraphEndpoint = new GraphEndpoint({responseId: dialogScope.formState.id, journalId: journal.id});
                        loadEndpoint(GraphEndpoint, dialogScope);
                    };

                    dialogScope.showSubjectGraph = function(subject) {
                        dialogScope.graphName = subject;
                        dialogScope.journal = false;
                        var GraphEndpoint = QueryUtils.endpoint('/poll/graph');
                        GraphEndpoint = new GraphEndpoint({responseId: dialogScope.formState.id, subjectStudyPeriodId: subject.id});
                        loadEndpoint(GraphEndpoint, dialogScope);
                    };

                    dialogScope.showStudentCouncil = function(responseId, isThemePageable) {
                        dialogScope.studentCouncil = true;
                        var GraphEndpoint = QueryUtils.endpoint('/poll/graph');
                        GraphEndpoint = new GraphEndpoint({responseId: responseId, themes: !isThemePageable});
                        loadEndpoint(GraphEndpoint, dialogScope);
                    };

                    if (row.type.code === 'KYSITLUS_V') {
                        dialogScope.showStudentCouncil(row.id, row.isThemePageable);
                    }

                    dialogScope.hideGraph = function() {
                        dialogScope.overall = false;
                        dialogScope.showGraph = false;
                    };
                }, null, null);
            });
        }

        $scope.openResponse = function(row) {
            var PollEndPoint;
            if (row.type.code === 'KYSITLUS_O') {
                PollEndPoint = QueryUtils.endpoint('/poll/withAnswersForView/journalOrSubject');
            } else {
                PollEndPoint = QueryUtils.endpoint('/poll/withAnswersForView');
            }
          QueryUtils.loadingWheel($scope, true, true);
          PollEndPoint.get({id: row.id}).$promise.then(function (response) {
            markImages(response.themes);
            QueryUtils.loadingWheel($scope, false, true);
            $timeout(function () {
                openResponse(row, response, dialogService, oisFileService);
            });
          });
        };

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
            if (!theme.isRepetitive) {
                theme.isTeacher = false;
            }
            var pollThemeEndpoint = new PollThemeEndpoint({isRepetitive: theme.isRepetitive, isTeacher: theme.isTeacher});
            pollThemeEndpoint.$update().then(function () {
                message.info('main.messages.create.success');
                $scope.refresh();
            }).catch(angular.noop);
        };

        $scope.confirmedOrEnded = function() {
            return $scope.formState.status === 'KYSITLUS_STAATUS_K' || $scope.formState.status === 'KYSITLUS_STAATUS_L';
        };

        $scope.refresh = function() {
            QueryUtils.loadingWheel($scope, true, true);
            QueryUtils.endpoint("/poll/themes").get({id: $scope.criteria.id}).$promise.then(function (data) {
                $scope.formState.status = data.status;
                if ($scope.confirmedOrEnded() && $scope.formState.edit) {
                    message.error("poll.messages.confirmedOrFinished");
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
            $scope.new = false;
            addQuestion(ArrayUtils, dialogService, QueryUtils, oisFileService, FormUtils, $scope, message, row, question, $scope.criteria.themes, $scope.formState.type);
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
                deleteEntity(PollThemeEndpoint);
            });
        };

        $scope.deleteQuestion = function(id) {
            dialogService.confirmDialog({ prompt: 'poll.questions.deleteQuestion' }, function () {
                var PollQuestionEndpoint = QueryUtils.endpoint('/poll/pollThemeQuestion/' + id);
                deleteEntity(PollQuestionEndpoint);
            });
        };

        function deleteEntity(Endpoint) {
            var PollQuestionEndpointForDelete = new Endpoint();
            PollQuestionEndpointForDelete.$delete().then(function () {
                message.info('main.messages.delete.success');
                $scope.refresh();
            }).catch(angular.noop);
        }

        $scope.test = function () {
            $scope.criteria.higher = $scope.auth.higher;
            test($scope, dialogService, $scope.criteria, QueryUtils, oisFileService, $scope.formState);
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
            }).catch(angular.noop);
        };

    }).controller('PollResultsController', function ($scope, $route, QueryUtils, $translate, dialogService, $q, Classifier, $window, $rootScope, message) {
        $scope.auth = $route.current.locals.auth;
        $scope.currentNavItem = 'poll.results';
        $scope.formState = {};
        $scope.criteria = {};
        if ($route.current.params.id) {
            $scope.id = $route.current.params.id;
        }

        $scope.calculate = function(allResponses, targetCount) {
            if (targetCount === null || targetCount === undefined || targetCount === 0) { return 0; }
            var percentage = allResponses / targetCount * 100;
            return percentage.toFixed(1);
        };

        $scope.confirmedOrEnded = function() {
            return $scope.formState.status === 'KYSITLUS_STAATUS_K' || $scope.formState.status === 'KYSITLUS_STAATUS_L';
        };

        $scope.refresh = function() {
            QueryUtils.loadingWheel($scope, true);
            QueryUtils.endpoint("/poll/results").get({id: $scope.id}).$promise.then(function (response) {
                $scope.formState = response;
                if (!$scope.confirmedOrEnded()) {
                    message.error("main.messages.error.nopermission");
                    $rootScope.back("#/poll/" + $scope.id + "/view");
                }
                QueryUtils.loadingWheel($scope, false);
                if ($scope.formState.type === 'KYSITLUS_O') {
                    if ($scope.auth.vocational) {
                        $scope.journalCriteria = { size: 10, page: 1, order: 'jj.name_et', id: $scope.id };
                        $scope.journals = {};
                        $scope.afterJournalsLoad = function (result) {
                            $scope.journals.content = result.content;
                            $scope.journals.totalElements = result.totalElements;
                        };

                        $scope.loadJournals = function () {
                            var query = QueryUtils.getQueryParams($scope.journalCriteria);
                            $scope.journals.$promise = QueryUtils.endpoint('/poll/results/journals/:id').search(query, $scope.afterJournalsLoad);
                        };
                        $scope.loadJournals();
                    }
                    if ($scope.auth.higher) {
                        $scope.subjectCriteria = { size: 10, page: 1, order: 's.name_et, teacher, s.code', id: $scope.id };
                        $scope.subjects = {};
                        $scope.afterSubjectsLoad = function (result) {
                            $scope.subjects.content = result.content;
                            $scope.subjects.totalElements = result.totalElements;
                        };

                        $scope.loadSubjects = function () {
                            var query = QueryUtils.getQueryParams($scope.subjectCriteria);
                            $scope.subjects.$promise = QueryUtils.endpoint('/poll/results/subjects/:id').search(query, $scope.afterSubjectsLoad);
                        };
                        $scope.loadSubjects();
                    }
                } else if ($scope.formState.type === 'KYSITLUS_P') {
                    QueryUtils.createQueryForm($scope, '/poll/results/enterprises/' + $scope.id, {}, null, null, null, true);
                    $scope.loadData();
                    var PollEndPoint = QueryUtils.endpoint('/poll/results/enterprises/studentOrTeacher');
                    PollEndPoint.get({id: $scope.id}).$promise.then(function (result) {
                        $scope.studentResponse = result.studentResponse;
                        $scope.teacherResponse = result.teacherResponse;
                    });
                }
            });
        };

        $scope.refresh();

        $scope.showJournalGraph = function(row) {
            var newScope = {};
            var name = angular.copy(row.name);
            if (row.teacher) {
                name.nameEt = name.nameEt + (row.teacher.nameEt !== undefined && row.teacher.nameEt !== null ? " (" + row.teacher.nameEt + ")" : "");
                name.nameEn = name.nameEn + (row.teacher.nameEn !== undefined && row.teacher.nameEn !== null ? " (" + row.teacher.nameEn + ")" : "");
            }
            newScope.graphName = name;
            newScope.journal = true;
            newScope.adminViewing = true;
            newScope.pollId = $scope.id;
            var GraphEndpoint = QueryUtils.endpoint('/poll/graphWithoutStudentAnswer');
            GraphEndpoint = new GraphEndpoint({pollId: newScope.pollId, journalId: row.name.id});
            loadEndpoint(GraphEndpoint, newScope);
        };

        $scope.enterpriseToExcel = function(params) {
            $window.location.href = $rootScope.excel('poll/results/exportSubject.xls', {pollId: $scope.id, enterpriseId: params.id});
        };

        $scope.studentToExcel = function() {
            $window.location.href = $rootScope.excel('poll/results/exportSubject.xls', {pollId: $scope.id, students: true});
        };

        $scope.teacherToExcel = function() {
            $window.location.href = $rootScope.excel('poll/results/exportSubject.xls', {pollId: $scope.id, teachers: true});
        };

        $scope.subjectToExcel = function(params) {
            var data = {
                pollId: $scope.id,
                subjectStudyPeriodId: params.teacher.id
            };
            $window.location.href = $rootScope.excel('poll/results/exportSubject.xls', data);
        };

        $scope.journalToExcel = function(params) {
            var data = {
                pollId: $scope.id,
                journalId: params.name.id
            };
            $window.location.href = $rootScope.excel('poll/results/exportSubject.xls', data);
        };

        $scope.allToExcel = function() {
            $window.location.href = $rootScope.excel('poll/results/exportSubject.xls', {pollId: $scope.id});
        };

        $scope.openAllGraph = function() {
            var newScope = {};
            newScope.adminViewing = true;
            newScope.pollId = $scope.id;
            newScope.poll = $scope.formState.poll;
            var GraphEndpoint = QueryUtils.endpoint('/poll/graphWithoutStudentAnswer');
            GraphEndpoint = new GraphEndpoint({pollId: newScope.pollId});
            loadEndpoint(GraphEndpoint, newScope);
        };

        $scope.openAllResults = function(journalOrSubjectId, teacherId, isSubject) {
            openAllResults($scope, QueryUtils, dialogService, $scope.id, journalOrSubjectId, teacherId, isSubject);
        };

        $scope.openEnterpriseAnswer = function(row) {
            var newScope = {};
            newScope.graphName = row;
            newScope.adminViewing = true;
            newScope.enterprise = true;
            newScope.pollId = $scope.id;
            var GraphEndpoint = QueryUtils.endpoint('/poll/graphWithoutStudentAnswer');
            GraphEndpoint = new GraphEndpoint({pollId: newScope.pollId, enterpriseId:row.id});
            loadEndpoint(GraphEndpoint, newScope);
        };

        $scope.openStudentAnswers = function() {
            var newScope = {};
            newScope.adminViewing = true;
            newScope.student = true;
            newScope.pollId = $scope.id;
            var GraphEndpoint = QueryUtils.endpoint('/poll/graphWithoutStudentAnswer');
            GraphEndpoint = new GraphEndpoint({pollId: newScope.pollId, students: true});
            loadEndpoint(GraphEndpoint, newScope);
        };

        $scope.openTeacherAnswers = function() {
            var newScope = {};
            newScope.adminViewing = true;
            newScope.teacher = true;
            newScope.pollId = $scope.id;
            var GraphEndpoint = QueryUtils.endpoint('/poll/graphWithoutStudentAnswer');
            GraphEndpoint = new GraphEndpoint({pollId: newScope.pollId, teachers: true});
            loadEndpoint(GraphEndpoint, newScope);
        };

        $scope.showSubjectGraph = function(row) {
            var newScope = {};
            var name = angular.copy(row.name);
            if (row.teacher) {
                name.nameEt = name.nameEt + " (" + row.teacher.nameEt + ")";
                name.nameEn = name.nameEn + " (" + row.teacher.nameEn + ")";
            }
            newScope.graphName = name;
            newScope.journal = false;
            newScope.adminViewing = true;
            newScope.pollId = $scope.id;
            var GraphEndpoint = QueryUtils.endpoint('/poll/graphWithoutStudentAnswer');
            GraphEndpoint = new GraphEndpoint({pollId: newScope.pollId, subjectStudyPeriodId: row.teacher.id});
            loadEndpoint(GraphEndpoint, newScope);
        };

        $scope.checkAnswer = function(row, higher) {
            dialogService.showDialog('poll/poll.results.student.dialog.html', function (dialogScope) {
                dialogScope.higher = higher;
                var clMapper = Classifier.valuemapper({
                    status: 'KYSITVASTUSSTAATUS'
                });
                QueryUtils.createQueryForm(dialogScope, '/poll/results/student', {}, clMapper.objectmapper);
                dialogScope.criteria.pollId = $scope.id;
                dialogScope.criteria.studentName = undefined;
                dialogScope.criteria.subjectId = undefined;
                if (dialogScope.higher) {
                    dialogScope.criteria.subjectStudyPeriodId = row.teacher.id;
                    dialogScope.criteria.subject = $scope.currentLanguageNameField(row.name);
                    dialogScope.criteria.journalId = undefined;
                } else {
                    dialogScope.criteria.journalId = row.name.id;
                    dialogScope.criteria.journal = $scope.currentLanguageNameField(row.name);
                    dialogScope.criteria.subjectStudyPeriodId = undefined;
                }

                dialogScope.search = function() {
                    $q.all(clMapper.promises).then(dialogScope.loadData);
                };

                dialogScope.search();
            });
        };

        function loadEndpoint(endPoint, newScope) {
            QueryUtils.loadingWheel($scope, true);
            endPoint.$putWithoutLoad().then(function (response) {
                QueryUtils.loadingWheel($scope, false);
                dialogService.showDialog('poll/poll.answers.dialog.html', function (dialogScope) {
                    dialogScope.auth = $scope.auth;
                    dialogScope.graphName = newScope.graphName;
                    dialogScope.journal = newScope.journal;
                    dialogScope.adminViewing = newScope.adminViewing;
                    dialogScope.type = response.type;
                    dialogScope.higher = $scope.auth.higher;
                    dialogScope.pollId = newScope.pollId;
                    dialogScope.enterprise = newScope.enterprise;
                    dialogScope.student = newScope.student;
                    dialogScope.teacher = newScope.teacher;
                    dialogScope.poll = newScope.poll;
                    dialogScope.formState = {};
                    dialogScope.showGraph = true;
                    dialogScope.criteria = {};
                    // translations
                    var responseCount = $translate.instant('poll.answers.dialog.responseCount');
                    var average = $translate.instant('poll.answers.dialog.average');
                    var individualResponses = $translate.instant('poll.answers.dialog.individualResponseCount');
                    var myAnswers = $translate.instant('poll.answers.dialog.myAnswer');

                    dialogScope.graph = response;
                    if (response.comments !== undefined) {
                        dialogScope.comments = response.comments;
                    }

                    dialogScope.graph.theme.forEach(function (theme) {
                        theme.journalOrSubject.forEach(function (subject) {
                            subject.teachers.forEach(function (teacher) {
                                teacher.graph.forEach(function (graph) {
                                    prepareTheme(graph, $scope, average, responseCount);
                                    graph.labelOverride[1] = {
                                        label: myAnswers
                                    };
                                    // Set legend name (average or response count)
                                    if (graph.isCheckbox) {
                                        setLabelOverride(graph, individualResponses);
                                    } else {
                                        setLabelOverride(graph, average);
                                    }
                                    if ($scope.formState.type === 'KYSITLUS_V') {
                                        graph.labelOverride[0].label = responseCount;
                                    }
                                });
                            });
                        });
                    });

                    function setLabelOverride(theme, translatedVal) {
                        if (theme.labelOverride[0] === undefined) {
                            theme.labelOverride[0] = {label: translatedVal};
                        } else {
                            theme.labelOverride[0].label = translatedVal;
                        }
                    }
                });
            });
        }

        $scope.openAnswers = function(row, isSubject) {
            if (isSubject) {
                $scope.showSubjectGraph(row);
            } else {
                $scope.showJournalGraph(row);
            }
        };

    }).controller('PollStatisticsController', function ($scope, $route, QueryUtils, ArrayUtils, message, $rootScope, ExcelUtils) {
        $scope.auth = $route.current.locals.auth;
        $scope.reset = resetCriteria;

        resetCriteria();

        $scope.loadPolls = function() {
            $scope.polls = QueryUtils.endpoint('/autocomplete/polls').query({type: $scope.criteria.typeCode});
            $scope.criteria.questions = [];
            $scope.criteria.polls = [];
            $scope.criteria.poll = undefined;
            $scope.criteria.question = undefined;
        };
        
        var loadQuestions = function() {
            var pollIds = $scope.criteria.polls.map(function (poll) {
                return poll.id;
            });
            if (pollIds.length !== 0) {
                $scope.questions = QueryUtils.endpoint('/autocomplete/polls/questions').query({polls: pollIds});
            } else {
                $scope.questions = [];
            }
            $scope.criteria.questions = [];
            $scope.criteria.question = undefined;
            $scope.criteria.allQuestions = false;
        };

        $scope.questionChanged = function () {
            if (angular.isDefined($scope.criteria.question) && $scope.criteria.question !== null) {
                $scope.addList($scope.criteria.questions, $scope.criteria.question, 'poll.statistics.duplicateQuestion');
            }
        };

        $scope.pollChanged = function () {
            if (angular.isDefined($scope.criteria.poll) && $scope.criteria.poll !== null) {
                $scope.addList($scope.criteria.polls, $scope.criteria.poll, 'poll.statistics.duplicatePoll', true);
            }
        };

        $scope.allToExcel = function() {
            var data = {
                pollIds: $scope.criteria.polls.map(function (poll) {
                    return poll.id;
                }),
                questions: $scope.criteria.questions.map(function (question) {
                    return question.id;
                })
            };
            ExcelUtils.send($scope, data, 'pollStatistics.xlsx');
        };

        $scope.addList = function(list, element, error, pollMode) {
            if (list.some(function (poll) {
                return element !== undefined && 
                element !== null && 
                    poll.id === element.id;
            })) {
                element = undefined;
                message.error(error);
                return;
            }
            list.push(angular.copy(element));
            if (pollMode) {
                loadQuestions();
            }
            element = undefined;
        };

        $scope.removeElement = function(list, element, pollMode) {
            ArrayUtils.remove(list, element);
            if (pollMode) {
                loadQuestions();
            } else {
                if ($scope.criteria.allQuestions) {
                    $scope.criteria.allQuestions = false;
                }
            }
        };

        $scope.pickAllQuestions = function(checked) {
            if (checked) {
                $scope.criteria.questions = angular.copy($scope.questions);
            } else {
                $scope.criteria.questions = [];
            }
        };

        function resetCriteria() {
            $scope.criteria = {};
            $scope.criteria.questions = [];
            $scope.criteria.polls = [];
        }

    }).controller('PollEditController', function ($scope, $route, QueryUtils, dialogService, message, FormUtils, Classifier, $location, oisFileService, $rootScope, $q, ArrayUtils) {
        $scope.auth = $route.current.locals.auth;
        $scope.currentNavItem = 'poll.data';
        $scope.formState = {};
        $scope.criteria = {};
        $scope.now = new Date();
        $scope.studentGroupAllowed = false;
        $scope.showOccupations = false;
        $scope.formState.names = QueryUtils.endpoint('/poll/pollNames').query();
        if ($route.current.templateUrl === "poll/poll.edit.html") {
            $scope.formState.edit = true;
        }

        $scope.confirmedOrEnded = function() {
            return $scope.formState.status !== undefined && $scope.formState.status.code !== undefined && 
            ($scope.formState.status.code === 'KYSITLUS_STAATUS_K' || $scope.formState.status.code === 'KYSITLUS_STAATUS_L');
        };

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

        $scope.$watch('formState.subjectStudyPeriod', function () {
            if (angular.isDefined($scope.formState.subjectStudyPeriod) && $scope.formState.subjectStudyPeriod !== null) {
                $scope.addSubjectStudyPeriod();
            }
        });

        $scope.addAllSubjectStudyPeriods = function () {
            QueryUtils.endpoint('/autocomplete/subjectStudyPeriods').query({studyPeriod: $scope.criteria.studyPeriod}, function (subjectStudyPeriods) {
                $scope.criteria.subjectStudyPeriods = subjectStudyPeriods;
                $scope.pollForm.$setDirty();
            });
        };

        $scope.removeSubjectStudyPeriod = function(item) {
            ArrayUtils.remove($scope.criteria.subjectStudyPeriods, item);
            $scope.pollForm.$setDirty();
        };

        $scope.addSubjectStudyPeriod = function() {
            $scope.criteria.subjectStudyPeriods = addElementToList($scope.criteria.subjectStudyPeriods, $scope.formState.subjectStudyPeriod, 'poll.basicData.duplicateStudyPeriod');
            $scope.formState.subjectStudyPeriod = undefined;
        };

        $scope.removeAllSubjectStudyPeriods = function () {
            $scope.criteria.subjectStudyPeriods = [];
            $scope.pollForm.$setDirty();
        };

        $scope.viewMoreOfList = function(modifiable, journal,edit) {
            dialogService.showDialog('poll/poll.edit.modifiable.list.dialog.html', function (dialogScope) {
                dialogScope.fullList = modifiable;
                dialogScope.edit = edit;
                dialogScope.journal = journal;
                dialogScope.searchedFullList = dialogScope.fullList;

                dialogScope.query = {
                    page: 1,
                    limit: 20
                };

                dialogScope.paginate = function() {
                    dialogScope.modifiable = dialogScope.searchedFullList.slice((dialogScope.query.page - 1) * 20, dialogScope.query.page * 20);
                };

                dialogScope.removeItem = function(item) {
                    ArrayUtils.remove(dialogScope.fullList, item);
                    dialogScope.search();
                };

                dialogScope.search = function() {
                    if (dialogScope.name !== undefined) {
                        dialogScope.searchedFullList = dialogScope.fullList.filter(function(el) {
                            return el.literalName.toLowerCase().indexOf(dialogScope.name.toLowerCase()) !== -1;
                        });
                    } else {
                        dialogScope.searchedFullList = dialogScope.fullList;
                    }
                    dialogScope.paginate();
                };

                }, function (submittedDialogScope) {
                    $scope.criteria.journals = submittedDialogScope.fullList;
                }, null, true);
        };

        $scope.addJournal = function() {
            $scope.criteria.journals = addElementToList($scope.criteria.journals, $scope.formState.journal, 'poll.basicData.duplicateJournal');
            $scope.formState.journal = undefined;
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
                $scope.formState.type = result.type;
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
                    if ($scope.confirmedOrEnded() && $scope.formState.edit) {
                        message.error("poll.messages.confirmedOrFinished");
                        $rootScope.back("#/poll/" + $scope.criteria.id + "/view");
                    }
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

        $scope.test = function() {
            $scope.criteria.higher = $scope.auth.higher;
            test($scope, dialogService, $scope.criteria, QueryUtils, oisFileService, $scope.formState);
        };

        $scope.checkReminder = function () {
            var ctrl = $scope.pollForm.reminderDt;
            if (ctrl !== undefined) {
                if (new Date($scope.criteria.reminderDt) < new Date($scope.criteria.validFrom) ||
                new Date($scope.criteria.reminderDt) > new Date($scope.criteria.validThru)) {
                    ctrl.$setValidity('incorrect', false);
                } else {
                    ctrl.$setValidity('incorrect', true);
                }
            }
        };

        $scope.earlierDate = function(dateOne, dateTwo) {
            if (dateOne === undefined) {
                return dateTwo;
            }
            if (dateTwo === undefined) {
                return dateOne;
            }
            if (new Date(dateOne) < new Date(dateTwo)) {
                return dateOne;
            }
            return dateTwo;
        };

        $scope.laterDate = function(dateOne, dateTwo) {
            if (dateOne === undefined) {
                return dateTwo;
            }
            if (dateTwo === undefined) {
                return dateOne;
            }
            if (new Date(dateOne) < new Date(dateTwo)) {
                return dateTwo;
            }
            return dateOne;
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

        function controlExpertUrl() {
            var filteredExpert = $scope.formState.targetCodes.filter(function(it) {return it.code === 'KYSITLUS_SIHT_V';});
            if (filteredExpert.length > 0) {
                $scope.showExpertUrl = true;
            } else {
                $scope.showExpertUrl = false;
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
            controlExpertUrl();
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
            if (confirm && $scope.criteria.type === 'KYSITLUS_O') {
                if (!$scope.auth.vocational && $scope.criteria.subjectStudyPeriods.length === 0) {
                    message.error('poll.basicData.atleastOneStudyPeriod');
                    return;
                }
                if ($scope.auth.vocational && $scope.auth.higher && 
                    (!angular.isArray($scope.criteria.journals) || $scope.criteria.journals.length === 0) && 
                    (!angular.isArray($scope.criteria.subjectStudyPeriods) || $scope.criteria.subjectStudyPeriods.length === 0)) {
                    message.error('poll.basicData.atleastOneStudyPeriodOrJournal');
                    return;
                }
            }
            if ((!angular.isArray($scope.criteria.journals) || $scope.criteria.journals.length === 0) && 
                $scope.criteria.type === 'KYSITLUS_O' && !$scope.auth.higher) {
                message.error('poll.basicData.atleastOneJournal');
                return;
            }
            if ((!angular.isArray($scope.criteria.journals) || $scope.criteria.journals.length === 0) && $scope.criteria.type === 'KYSITLUS_O' &&
                $scope.auth.higher && $scope.auth.vocational && ($scope.criteria.studyPeriod === undefined || $scope.criteria.studyPeriod === null || $scope.criteria.studyPeriod === "")) {
                message.error('poll.basicData.studyPeriodError');
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
            var pollEndPoint;
            if (confirm) {
                var EndPoint = QueryUtils.endpoint('/poll/confirm');
                pollEndPoint = new EndPoint($scope.criteria);
            } else {
                pollEndPoint = new PollEndPoint($scope.criteria);
            }
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

        $scope.addAllJournals = function () {
            QueryUtils.endpoint('/autocomplete/journalsAndStudentGroups').query({}, function (journals) {
                $scope.criteria.journals = journals;
                $scope.pollForm.$setDirty();
            });
        };

        $scope.removeAllJournals = function () {
            $scope.criteria.journals = [];
            $scope.pollForm.$setDirty();
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
                    prompt: $scope.criteria.themeEmpty ? 'poll.basicData.themeEmpty' : 'poll.basicData.confirm'
                }, function () {
                    $scope.save(true);
                });
            } else {
                message.error('poll.basicData.confirmError');
            }
        };

        $scope.addAllStudentgroups = function () {
            QueryUtils.endpoint('/autocomplete/studentgroups').query({valid: true}, function (studentgroups) {
                $scope.formState.studentGroups = studentgroups;
                $scope.pollForm.$setDirty();
            });
        };

        $scope.removeAllStudentgroups = function () {
            $scope.formState.studentGroups = [];
            $scope.pollForm.$setDirty();
        };

        function addElementToList(list, addable, duplicateError) {
            if (!angular.isArray(list)) {
                list = [];
            }
            if (list.some(function (item) { return addable !== undefined && addable !== null && item.id === addable.id;})) {
                if (!duplicateError) {
                    message.error('poll.basicData.duplicate');
                } else {
                    message.error(duplicateError);
                }
                return list;
            }
            list.push(angular.copy(addable));
            $scope.pollForm.$setDirty();
            return list.sort(function(a, b){
                var lowerA = a.nameEt.toLowerCase(), lowerB = b.nameEt.toLowerCase();
                if(lowerA < lowerB) { return -1; }
                if(lowerA > lowerB) { return 1; }
                return 0;
            });
        }

        $scope.addStudentGroup = function () {
            $scope.formState.studentGroups = addElementToList($scope.formState.studentGroups, $scope.formState.studentgroup, 'poll.basicData.duplicateStudentGroup');
            $scope.formState.studentgroup = undefined;
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
        var Endpoint;
        QueryUtils.endpoint('/poll/supervisor').get({id: $route.current.params.uuid}).$promise.then(function(response) {
            $scope.criteria = response;
            $scope.criteria.themes[0].show = true;
            markImages($scope.criteria.themes);
            Endpoint = QueryUtils.endpoint('/poll/supervisor/' + $scope.criteria.responseId + '/saveAnswer');
        }).catch(function () {
            $location.url('/');
        });

        $scope.getUrl = function(photo) {
            return oisFileService.getUrl(photo, 'pollThemeQuestionFile');
        };

        $scope.confirm = function() {
            var errorObject = checkErrorsFromThemes($scope.criteria.themes);
            var hasErrors = errorObject[0];
            FormUtils.withValidForm($scope.responseForm, function() {
                if (!hasErrors) {
                    dialogService.confirmDialog({ prompt: 'poll.answer.confirm' }, function () {
                        var Endpoint = QueryUtils.endpoint('/poll/supervisor/' + $scope.criteria.responseId + '/saveAnswer/final');
                        var pollEndPoint = new Endpoint();
                        pollEndPoint.$update().then(function () {
                            dialogService.messageDialog({ prompt: 'poll.answer.confirmed' }, function () {
                                $location.url('/');
                            });
                        });
                    });
                } else {
                    if ($scope.criteria.isThemePageable) {
                        message.error('poll.messages.required', {themes: errorObject[1].join(", ")});
                    } else {
                        message.error('main.messages.form-has-errors');
                    }
                }
            });
        };

        $scope.save = function (question) {
            var pollEndPoint = new Endpoint(question);
            pollEndPoint.$putWithoutLoad();
        };

        $scope.clearRadio = function(question) {
            question.answerTxt = null;
            $scope.save(question);
        };

        $scope.previousTheme = function(index) {
            $scope.criteria.themes[index].show = false;
            $scope.criteria.themes[index - 1].show = true;
        };

        $scope.nextTheme = function(index) {
            $scope.criteria.themes[index].show = false;
            $scope.criteria.themes[index + 1].show = true;
        };

    }).controller('PollExpertController', function ($scope, $route, oisFileService, FormUtils, $location, message, QueryUtils, dialogService) {
        $scope.formState = {};
        var Endpoint;
        QueryUtils.endpoint('/poll/expert').get({id: $route.current.params.uuid}).$promise.then(function(response) {
            $scope.criteria = response;
            $scope.criteria.themes[0].show = true;
            markImages($scope.criteria.themes);
            Endpoint = QueryUtils.endpoint('/poll/expert/' + response.responseId + '/saveAnswer');
        }).catch(function () {
            $location.url('/');
        });

        $scope.previousTheme = function(index) {
            $scope.criteria.themes[index].show = false;
            $scope.criteria.themes[index - 1].show = true;
        };

        $scope.nextTheme = function(index) {
            $scope.criteria.themes[index].show = false;
            $scope.criteria.themes[index + 1].show = true;
        };

        $scope.getUrl = function(photo) {
            return oisFileService.getUrl(photo, 'pollThemeQuestionFile');
        };

        $scope.confirm = function() {
            var errorObject = checkErrorsFromThemes($scope.criteria.themes);
            var hasErrors = errorObject[0];
            FormUtils.withValidForm($scope.responseForm, function() {
                if (!hasErrors) {
                    dialogService.confirmDialog({ prompt: 'poll.answer.confirm' }, function () {
                        var Endpoint = QueryUtils.endpoint('/poll/expert/' + $scope.criteria.responseId + '/saveAnswer/final');
                        var pollEndPoint = new Endpoint();
                        pollEndPoint.$update().then(function () {
                            dialogService.messageDialog({ prompt: 'poll.answer.confirmed' }, function () {
                                $location.url('/');
                            });
                        });
                    });
                } else {
                    if ($scope.criteria.isThemePageable) {
                        message.error('poll.messages.required', {themes: errorObject[1].join(", ")});
                    } else {
                        message.error('main.messages.form-has-errors');
                    }
                }
            });
        };

        $scope.save = function (question) {
            var pollEndPoint = new Endpoint(question);
            pollEndPoint.$putWithoutLoad();
        };

        $scope.clearRadio = function(question) {
            question.answerTxt = null;
            $scope.save(question);
        };
    });
}());