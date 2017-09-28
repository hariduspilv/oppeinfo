'use strict';

angular.module('hitsaOis')
.constant('StudentStatus', {
  'OPPURSTAATUS_A': 'OPPURSTAATUS_A',
  'OPPURSTAATUS_K': 'OPPURSTAATUS_K',
  'OPPURSTAATUS_O': 'OPPURSTAATUS_O',
  'OPPURSTAATUS_V': 'OPPURSTAATUS_V',
  'OPPURSTAATUS_L': 'OPPURSTAATUS_L'

}).factory('StudentUtil', function (StudentStatus) {

    return {
      isActive: function(statusCode) {
        return StudentStatus.OPPURSTAATUS_O === statusCode ||
               StudentStatus.OPPURSTAATUS_A === statusCode ||
               StudentStatus.OPPURSTAATUS_V === statusCode;
      },
      hasFinished: function(statusCode) {
        return StudentStatus.OPPURSTAATUS_L === statusCode;
      }
    };
  });
