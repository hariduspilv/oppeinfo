'use strict';

/**
 * @ngdoc service
 * @name hitsaOis.DataUtils
 * @description
 * # DataUtils
 * Factory in the hitsaOis.
 */
angular.module('hitsaOis')
  .factory('DataUtils', function () {
    return {
      assign: function( path, obj, value ) {
        return path.split('.').reduce( function( prev, curr, currentIndex, array ) {
          if(currentIndex === array.length - 1) {
            prev[curr] = value;
          } else if(!prev[curr]) {
            prev[curr] = {};
          }
          return prev[curr];
        }, obj || {} );
      },
      convertStringToDates : function(object, dateProperties) {
        for(var i = 0, cnt = dateProperties.length; i < cnt; i++) {
          var property = dateProperties[i];
          if(object.hasOwnProperty(property) && typeof object[property] === 'string') {
            object[property] = moment(object[property], "YYYY-MM-DD'T'hh:mm:ss.SSS'Z'").toDate();
          }
        }
      },

      sexFromIdcode : function (idcode) {
        if (idcode.length !== 11  || isNaN(idcode)) {
          return null;
        }
        return Number(idcode.charAt(0)) % 2 === 1 ? 'SUGU_M' : 'SUGU_N';
      },

      birthdayFromIdcode : function (idcode) {
        if (idcode.length !== 11 || isNaN(idcode)) {
          return null;
        }
        var centuries = {"1": "18", "2": "18", "3": "19", "4": "19", "5": "20", "6": "20", "7": "21", "8": "21"};
        var date = centuries[idcode.charAt(0)] + idcode.substring(1,7);
        return moment(date).toDate();
      }
    };
  });
