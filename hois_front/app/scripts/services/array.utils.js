'use strict';

/**
 * @ngdoc service
 * @name hitsaOis.ArrayUtils
 * @description
 * # ArrayUtils
 * Factory in the hitsaOis.
 */
angular.module('hitsaOis')
  .factory('ArrayUtils', function () {

    return {
      contains: function(array, item) {
        return array.indexOf(item) !== -1;
      },
      remove: function(array, item) {
        var index = array.indexOf(item);
        if(index > -1) {
          array.splice(index, 1);
        }
      },
      includes: function(array, searchElement , fromIndex) {
        // based on example from
        // https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/includes#Polyfill
        if (fromIndex===undefined){
            var i = array.length;
            while(i--){
                if (array[i]===searchElement){
                  return true;
                }
            }
        } else {
            var j = fromIndex, len=array.length;
            while(j++!==len){
                if (array[j]===searchElement){
                  return true;
                }
            }
        }
        return false;
      }
    };
  });
