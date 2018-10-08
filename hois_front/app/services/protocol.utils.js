'use strict';

angular.module('hitsaOis')
  .factory('ProtocolUtils', function ($mdDialog, $timeout, $rootScope, $window, QueryUtils, config, message) {
    var protocolUtils = {};

    protocolUtils.signBeforeConfirm = function (endpoint, data, confirmMessage, callback) {
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
            });
          });
        });
      }).catch(function (reason) {
        //no_implementation, no_certificates, user_cancel, technical_error
        if (reason.message === 'user_cancel') {
          message.error('main.messages.error.idCardSigningCancelled');
        } else {
          message.error('main.messages.error.readingIdCardFailed');
        }
      });
    };

    protocolUtils.mobileSignBeforeConfirm = function (endpoint, data, confirmMessage, callback) {
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
          $timeout(pollMobileSignStatus(endpoint, confirmMessage, callback), config.mobileIdInitialDelay);
        } else {
          message.error('main.messages.error.mobileIdSignFailed');
        }
      });
    };

    function pollMobileSignStatus(endpoint, confirmMessage, callback) {
      QueryUtils.endpoint(endpoint + '/mobileSignStatus').get(
        function (response) {
          if (response.status === 'SIGNATURE') {
            $mdDialog.hide();
            QueryUtils.endpoint(endpoint + '/mobileSignFinalize').save({
              version: $rootScope.signVersion
            }, function (result) {
              message.info(confirmMessage);
              callback(result);
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
      return protocol.canBeEdited && protocol.canBeConfirmed && (auth.loginMethod === 'LOGIN_TYPE_I' || auth.loginMethod === 'LOGIN_TYPE_M');
    };

    protocolUtils.canAddDeleteStudents = function (auth, protocol) {
      return protocol.status.code !== 'PROTOKOLL_STAATUS_K' && protocolUtils.canEditProtocol(auth, protocol);
    };

    protocolUtils.canConfirm = function(auth, protocol) {
      return protocol.canBeConfirmed && allProtocolStudentsGraded(protocol) && allChangedGradesHaveAddInfo(protocol) && 
        (auth.loginMethod === 'LOGIN_TYPE_I' || auth.loginMethod === 'LOGIN_TYPE_M');
    };

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
