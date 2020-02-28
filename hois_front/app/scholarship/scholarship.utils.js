'use strict';

angular.module('hitsaOis').factory('ScholarshipUtils', ['$filter', '$location', '$translate', 'dialogService',
  function ($filter, $location, $translate, dialogService) {

    var baseUrl = '/scholarships';

    var STIPEND_TYPES = {
      'grants': ['STIPTOETUS_POHI', 'STIPTOETUS_ERI', 'STIPTOETUS_SOIDU'],
      'scholarships': ['STIPTOETUS_TULEMUS', 'STIPTOETUS_ERIALA', 'STIPTOETUS_MUU'],
      'drGrants': ['STIPTOETUS_DOKTOR']
    };

    function gotoEdit(id) {
      $location.url(baseUrl + '/' + id + '/edit');
    }

    function applicationPeriod(stipend) {
      var start = $filter('hoisDate')(stipend.applicationStart);
      var end = $filter('hoisDate')(stipend.applicationEnd);
      if (start && end) {
        return start + ' - ' + end;
      } else if (start && !end) {
        return $translate.instant('main.from') + ' ' + start;
      } else if (!start && end) {
        return $translate.instant('main.thru') + ' ' + end;
      }
      return null;
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
      },
      getScholarshipTypeGroup: function (typeCode) {
        if (STIPEND_TYPES.grants.indexOf(typeCode) !== -1) {
          return 'grants';
        } else if (STIPEND_TYPES.scholarships.indexOf(typeCode) !== -1) {
          return 'scholarships';
        } else if (STIPEND_TYPES.drGrants.indexOf(typeCode) !== -1) {
          return 'drGrants';
        }
      },
      getScholarshipGroupTypes: function (group) {
        return STIPEND_TYPES[group];
      },
      applicationPeriod: applicationPeriod
    };
  }
]);
