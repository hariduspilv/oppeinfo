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
        for(var property in object) {
          if(object.hasOwnProperty(property)) {
            if(object[property] !== null && typeof object[property] === "string" && dateProperties !== null && dateProperties.indexOf(property) !== -1) {
              object[property] = moment(object[property], "YYYY-MM-DD'T'hh:mm:ss.SSS'Z'").toDate();
            }
          }
        }
      }
    };
  });
