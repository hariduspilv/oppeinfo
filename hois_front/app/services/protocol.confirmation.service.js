'use strict';

angular.module('hitsaOis')
  .factory('ProtocolConfirmationService', function ($mdDialog, $timeout, $rootScope, $window, QueryUtils, config, message) {
    var protocolConfirmationService = {};

    protocolConfirmationService.signBeforeConfirm = function (endpoint, data, callback) {
      $window.hwcrypto.getCertificate({ lang: 'en' }).then(function (certificate) {
        data.certificate = certificate.hex;
        QueryUtils.endpoint(endpoint + '/signToConfirm').save(data, function (result) {
          $window.hwcrypto.sign(certificate, { type: 'SHA-256', hex: result.digestToSign }, { lang: 'en' }).then(function (signature) {
            QueryUtils.endpoint(endpoint + '/signToConfirmFinalize').save({
              signature: signature.hex,
              version: result.version
            }, function (result) {
              message.info('finalExamProtocol.messages.confirmed');
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

    protocolConfirmationService.mobileSignBeforeConfirm = function (endpoint, data, callback) {
      QueryUtils.endpoint(endpoint + '/mobileSignToConfirm').save(data, function (result) {
        if (result.challengeID) {
          $rootScope.signVersion = result.version;
          $mdDialog.show({
            controller: function ($rootScope) {
              $rootScope.challengeID = result.challengeID;
            },
            templateUrl: 'finalExamProtocol/final.exam.protocol.mobile.sign.dialog.html',
            parent: angular.element(document.body),
            clickOutsideToClose: false
          });
          $rootScope.mobileIdPolls = 0;
          $timeout(pollMobileSignStatus(endpoint, callback), config.mobileIdInitialDelay);
        } else {
          message.error('main.messages.error.mobileIdSignFailed');
        }
      }).catch(angular.noop);
    };

    function pollMobileSignStatus(endpoint, callback) {
      QueryUtils.endpoint(endpoint + '/mobileSignStatus').get(
        function (response) {
          if (response.status === 'SIGNATURE') {
            $mdDialog.hide();
            QueryUtils.endpoint(endpoint + '/mobileSignFinalize').save({
              version: $rootScope.signVersion
            }, function (result) {
              message.info('finalExamProtocol.messages.confirmed');
              callback(result);
            });
          } else if (response.status === 'OUTSTANDING_TRANSACTION') {
            $rootScope.mobileIdPolls++;
            if ($rootScope.mobileIdPolls < config.mobileIdMaxPolls) {
              $timeout(pollMobileSignStatus, config.mobileIdPollInterval);
            }
          } else {
            $mdDialog.hide();
            message.error('main.messages.error.mobileIdSignFailed');
          }
        }
      );
    }

    return protocolConfirmationService;
  });
