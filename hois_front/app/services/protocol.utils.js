'use strict';

angular.module('hitsaOis')
  .factory('ProtocolUtils', function ($mdDialog, $rootScope, $timeout, $window, DataUtils, QueryUtils, config, dialogService, message, resourceErrorHandler) {
    var protocolUtils = {};
    var CONFIRM_LOGIN_TYPES = ['LOGIN_TYPE_I', 'LOGIN_TYPE_M', 'LOGIN_TYPE_T', 'LOGIN_TYPE_H'];

    protocolUtils.signBeforeConfirm = function (auth, endpoint, data, confirmMessage, callback, failCallback) {
      if (auth.loginMethod === 'LOGIN_TYPE_I') {
        protocolUtils.idcardSignBeforeConfirm(endpoint, data, confirmMessage, callback, failCallback);
      } else if (auth.loginMethod === 'LOGIN_TYPE_M') {
        protocolUtils.mobileIdSignatureRequest(endpoint, data, confirmMessage, callback, failCallback);
      } else if (auth.loginMethod === 'LOGIN_TYPE_T' || auth.loginMethod === 'LOGIN_TYPE_H') {
        dialogService.showDialog('components/oauth.protocol.confirm.dialog.html', function (dialogScope) {
          dialogScope.formState = {
            signType: null,
            mobileNumber: '+372'
          };
        }, function (submittedDialogScope) {
          if (submittedDialogScope.formState.signType === 'IDCARD') {
            protocolUtils.idcardSignBeforeConfirm(endpoint, data, confirmMessage, callback, failCallback);
          } else if (submittedDialogScope.formState.signType === 'MOBILE_ID') {
            data.signerMobileNumber = submittedDialogScope.formState.mobileNumber;
            protocolUtils.mobileIdSignatureRequest(endpoint, data, confirmMessage, callback, failCallback);
          }
        }, function () {
          message.error('main.messages.error.signingCancelled');
          if (angular.isFunction(failCallback)) {
            failCallback();
          }
        });
      }
    };

    protocolUtils.idcardSignBeforeConfirm = function (endpoint, data, confirmMessage, callback, failCallback) {
      $window.hwcrypto.getCertificate({ lang: 'en' }).then(function (certificate) {
        data.certificate = certificate.hex;
        QueryUtils.endpoint(endpoint + '/signToConfirm').save(data, function (result) {
          $window.hwcrypto.sign(certificate, { type: 'SHA-256', hex: result.digestToSign }, { lang: 'en' }).then(function (signature) {
            QueryUtils.endpoint(endpoint + '/signToConfirmFinalize').save({
              signature: signature.hex,
              version: result.version
            }, function (result) {
              message.info(confirmMessage);
              callback(result);
            }, function (reason) {
              if (angular.isFunction(failCallback)) {
                failCallback(reason);
              }
            });
          });
        });
      }).catch(function (reason) {
        //no_implementation, no_certificates, user_cancel, technical_error
        if (reason.message === 'user_cancel') {
          message.error('main.messages.error.signingCancelled');
        } else {
          message.error('main.messages.error.readingIdCardFailed');
        }
        if (angular.isFunction(failCallback)) {
          failCallback(reason);
        }
      });
    };

    protocolUtils.mobileIdSignatureRequest = function (endpoint, data, confirmMessage, callback, failCallback) {
      var url = endpoint + '/mobileIdSignatureRequest?lang=' + $rootScope.currentLanguage().toUpperCase();
      QueryUtils.endpoint(url).save(data, function (result) {
        if (result.challengeID) {
          $rootScope.signVersion = result.version;
          $mdDialog.show({
            controller: function ($scope) {
              $scope.challengeID = result.challengeID;
            },
            templateUrl: 'components/protocol.mobile.sign.dialog.html',
            parent: angular.element(document.body),
            clickOutsideToClose: false
          });
          mobileIdSign(endpoint, confirmMessage, callback, failCallback);
        } else {
          resourceErrorHandler.responseError(result).catch(angular.noop);
          if (angular.isFunction(failCallback)) {
            failCallback();
          }
        }
      }, function () {
        if (angular.isFunction(failCallback)) {
          failCallback();
        }
      });
    };

    function mobileIdSign(endpoint, confirmMessage, callback, failCallback) {
      QueryUtils.endpoint(endpoint + '/mobileIdSign').save({
        version: $rootScope.signVersion
      }, function (result) {
        message.info(confirmMessage);
        callback(result);
        $mdDialog.hide();
      }, function (reason) {
        if (angular.isFunction(failCallback)) {
          failCallback(reason);
        }
        $timeout($mdDialog.hide, 500);
      });
    }

    protocolUtils.canEditProtocol = function (auth, protocol) {
      return protocol.status.code === 'PROTOKOLL_STAATUS_K' ? protocolUtils.canEditConfirmedProtocol(auth, protocol) : protocol.canBeEdited;
    };

    protocolUtils.canChangeConfirmedProtocolGrade = function (auth, protocol) {
      return protocolUtils.canEditProtocol(auth, protocol) && protocol.status.code === 'PROTOKOLL_STAATUS_K';
    };

    protocolUtils.canEditConfirmedProtocol = function (auth, protocol) {
      return protocol.canBeEdited && protocol.canBeConfirmed && CONFIRM_LOGIN_TYPES.indexOf(auth.loginMethod) !== -1;
    };

    protocolUtils.canAddDeleteStudents = function (auth, protocol) {
      return protocol.status.code !== 'PROTOKOLL_STAATUS_K' && protocolUtils.canEditProtocol(auth, protocol);
    };

    protocolUtils.canConfirm = function(auth, protocol) {
      return protocol.canBeConfirmed && protocolIncludingStudents(protocol) && allProtocolStudentsGraded(protocol) &&
        allChangedGradesHaveAddInfo(protocol) && CONFIRM_LOGIN_TYPES.indexOf(auth.loginMethod) !== -1;
    };

    function protocolIncludingStudents(protocol) {
      return angular.isDefined(protocol) && angular.isArray(protocol.protocolStudents) &&
        protocol.protocolStudents.length > 0;
    }

    function allProtocolStudentsGraded(protocol) {
      if (!angular.isDefined(protocol) || !angular.isArray(protocol.protocolStudents)) {
        return false;
      }

      var allGraded = true;
      for (var i = 0; i < protocol.protocolStudents.length; i++) {
        if (!angular.isObject(protocol.protocolStudents[i].grade)) {
          allGraded = false;
          break;
        }
      }
      return allGraded;
    }

    function allChangedGradesHaveAddInfo(protocol) {
      if (!angular.isDefined(protocol) || !angular.isArray(protocol.protocolStudents)) {
        return false;
      }

      var allHaveAddInfo = true;
      if (protocol.status.code === 'PROTOKOLL_STAATUS_K') {
        for (var i = 0; i < protocol.protocolStudents.length; i++) {
          if (protocol.protocolStudents[i].gradeHasChanged) {
            var addInfo = protocol.protocolStudents[i].addInfo;
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

    protocolUtils.gradeChanged = function (form, savedStudents, row) {
      if (row) {
        var savedResult = savedStudents.find(function (student) { return student.id === row.id; });
        if (!DataUtils.isSameGrade(savedResult.grade, row.grade)) {
          form.$setSubmitted();
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
    };

    return protocolUtils;
  });
