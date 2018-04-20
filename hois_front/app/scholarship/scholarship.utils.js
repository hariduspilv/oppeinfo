'use strict';

angular.module('hitsaOis').factory('ScholarshipUtils', ['$location', 'dialogService',
  function ($location, dialogService) {

    var baseUrl = '/scholarships';

    function gotoEdit(id) {
      $location.url(baseUrl + '/' + id + '/edit');
    }

    return {
      changeStipend: function (id, typeCode, isOpen) {
        var messageText;
        if (['STIPTOETUS_POHI', 'STIPTOETUS_ERI', 'STIPTOETUS_SOIDU', 'STIPTOETUS_DOKTOR'].indexOf(typeCode) !== -1) {
          messageText = 'stipend.confirmations.grantIsPublishedChange';
        } else {
          messageText = 'stipend.confirmations.scholarshipIsPublishedChange';
        }
        if (isOpen) {
          dialogService.confirmDialog({prompt: messageText}, function () {
            gotoEdit(id);
          });
        } else {
          gotoEdit(id);
        }
      }
    };
  }
]);
