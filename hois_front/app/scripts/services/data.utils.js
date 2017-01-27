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
      }
    };
  });
