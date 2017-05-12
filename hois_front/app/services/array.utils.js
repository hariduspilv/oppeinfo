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
        return !angular.isDefined(array) || array === null || array.length === 0;
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
      },
      removeDuplicates: function(array, property) {
        var mySet = new Set();
        return array.filter(function(el) {
            var key = angular.isDefined(property) ? property : el;
            var isNew = !mySet.has(key);
            if (isNew) {
                mySet.add(key);
            } 
            return isNew;
        });
      }
    };
  });
