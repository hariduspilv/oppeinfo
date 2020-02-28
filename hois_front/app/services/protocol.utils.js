'use strict';

angular.module('hitsaOis')
  .factory('ProtocolUtils', function ($mdDialog, $timeout, $rootScope, $window, QueryUtils, config, dialogService, message) {
    var protocolUtils = {};
    var CONFIRM_LOGIN_TYPES = ['LOGIN_TYPE_I', 'LOGIN_TYPE_M', 'LOGIN_TYPE_T', 'LOGIN_TYPE_H'];

    protocolUtils.signBeforeConfirm = function (auth, endpoint, data, confirmMessage, callback, failCallback) {
      if (auth.loginMethod === 'LOGIN_TYPE_I') {
        protocolUtils.idcardSignBeforeConfirm(endpoint, data, confirmMessage, callback, failCallback);
      } else if (auth.loginMethod === 'LOGIN_TYPE_M') {
        protocolUtils.mobileSignBeforeConfirm(endpoint, data, confirmMessage, callback, failCallback);
      } else if (auth.loginMethod === 'LOGIN_TYPE_T' || auth.loginMethod === 'LOGIN_TYPE_H') {
        dialogService.showDialog('components/tara.protocol.confirm.dialog.html', function (dialogScope) {
          dialogScope.signType = null;

          dialogScope.idcard = function () {
            dialogScope.signType = 'IDCARD';
            dialogScope.submit();
          };

          dialogScope.mobileid = function () {
            dialogScope.signType = 'MOBILE_ID';
            dialogScope.submit();
          };
        }, function (submittedDialogScope) {
          if (submittedDialogScope.signType === 'IDCARD') {
            protocolUtils.idcardSignBeforeConfirm(endpoint, data, confirmMessage, callback, failCallback);
          } else if (submittedDialogScope.signType === 'MOBILE_ID') {
            protocolUtils.mobileSignBeforeConfirm(endpoint, data, confirmMessage, callback, failCallback);
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

    protocolUtils.mobileSignBeforeConfirm = function (endpoint, data, confirmMessage, callback, failCallback) {
      QueryUtils.endpoint(endpoint + '/mobileSignToConfirm').save(data, function (result) {
        if (result.challengeID) {
          $rootScope.signVersion = result.version;
          $mdDialog.show({
            controller: function ($scope) {
              $scope.challengeID = result.challengeID;
            },
            templateUrl: 'finalProtocol/templates/final.protocol.mobile.sign.dialog.html',
            parent: angular.element(document.body),
            clickOutsideToClose: false
          });
          $rootScope.mobileIdPolls = 0;
          $timeout(pollMobileSignStatus(endpoint, confirmMessage, callback, failCallback), config.mobileIdInitialDelay);
        } else {
          message.error('main.messages.error.mobileIdSignFailed');
          if (angular.isFunction(failCallback)) {
            failCallback();
          }
        }
      });
    };

    function pollMobileSignStatus(endpoint, confirmMessage, callback, failCallback) {
      QueryUtils.endpoint(endpoint + '/mobileSignStatus').get(
        function (response) {
          if (response.status === 'SIGNATURE') {
            $mdDialog.hide();
            QueryUtils.endpoint(endpoint + '/mobileSignFinalize').save({
              version: $rootScope.signVersion
            }, function (result) {
              message.info(confirmMessage);
              callback(result);
            }, function (reason) {
              if (angular.isFunction(failCallback)) {
                failCallback(reason);
              }
            });
          } else if (response.status === 'OUTSTANDING_TRANSACTION') {
            $rootScope.mobileIdPolls++;
            if ($rootScope.mobileIdPolls < config.mobileIdMaxPolls) {
              $timeout(function () {
                pollMobileSignStatus(endpoint, confirmMessage, callback);
              }, config.mobileIdPollInterval);
            }
          } else {
            $mdDialog.hide();
            message.error('main.messages.error.mobileIdSignFailed');
            if (angular.isFunction(failCallback)) {
              failCallback();
            }
          }
        }
      );
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
        if (!angular.isString(protocol.protocolStudents[i].grade) || protocol.protocolStudents[i].grade.trim().length === 0) {
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

    return protocolUtils;
  });
