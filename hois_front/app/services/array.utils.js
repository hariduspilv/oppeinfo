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
      isEmpty: function(array) {
        return !angular.isArray(array) || array.length === 0;
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
      },
       /**
       * Checks whether two arrays have common elements
       */
      intersect: function(array1, array2) {
        if(this.isEmpty(array1) || this.isEmpty(array2)) {
          return false;
        }
        for(var i = 0; i < array1.length; i++) {
            if(this.includes(array2, array1[i])) {
                return true;
            }
        }
        return false;
      },
      /**
       * Finds common parts of two arrays!
       *
       * Note, that arrays with repeating elements are not considered here
       */
      intersection : function(array1, array2) {
        var commonPart = [];
        for(var i = 0; i < array1.length; i++) {
            if(this.includes(array2, array1[i])) {
                commonPart.push(array1[i]);
            }
        }
        return commonPart;
      }
    };
  });

/**
 * IE browser does not support array.find().
 * This is the polyfill from
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/find?v=example
 */
if (!Array.prototype.find) {
  Object.defineProperty(Array.prototype, 'find', {
    value: function(predicate) {
     // 1. Let O be ? ToObject(this value).
      if (this === null) {
        throw new TypeError('"this" is null or not defined');
      }

      var o = Object(this);

      // 2. Let len be ? ToLength(? Get(O, "length")).
      var len = o.length >>> 0;

      // 3. If IsCallable(predicate) is false, throw a TypeError exception.
      if (typeof predicate !== 'function') {
        throw new TypeError('predicate must be a function');
      }

      // 4. If thisArg was supplied, let T be thisArg; else let T be undefined.
      var thisArg = arguments[1];

      // 5. Let k be 0.
      var k = 0;

      // 6. Repeat, while k < len
      while (k < len) {
        // a. Let Pk be ! ToString(k).
        // b. Let kValue be ? Get(O, Pk).
        // c. Let testResult be ToBoolean(? Call(predicate, T, « kValue, k, O »)).
        // d. If testResult is true, return kValue.
        var kValue = o[k];
        if (predicate.call(thisArg, kValue, k, o)) {
          return kValue;
        }
        // e. Increase k by 1.
        k++;
      }

      // 7. Return undefined.
      return undefined;
    }
  });
}
