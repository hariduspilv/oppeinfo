'use strict';

/**
 * @ngdoc service
 * @name hitsaOis.DataUtils
 * @description
 * # DataUtils
 * Factory in the hitsaOis.
 */
angular.module('hitsaOis').factory('DataUtils',
  function () {

    function convert(object, dateProperties, pattern) {
      if (angular.isArray(object)) {
        return object.map(function (it) { return convertStringToDates(it, dateProperties); });
      }

      for(var i = 0, cnt = dateProperties.length; i < cnt; i++) {
        var property = dateProperties[i];
        if(object.hasOwnProperty(property) && typeof object[property] === 'string') {
          object[property] = moment(object[property], pattern).toDate();
        }
      }
      return object;
    }

    function convertStringToDates(object, dateProperties) {
      return convert(object, dateProperties, "YYYY-MM-DD'T'hh:mm:ss.SSS'Z'");
    }

    function convertStringToTime(object, dateProperties) {
      return convert(object, dateProperties, "hh:mm");
    }

    function sortStudyYearsOrPeriods(list) {
        for(var i = 0; i < list.length; i++) {
            convertStringToDates(list[i], ["startDate", "endDate"]);
        }
        // return list.sort(function(el1, el2){
        list.sort(function(el1, el2){
            return el1.startDate >= el2.startDate;
        });
    }
    /**
     * Beware that using this method changes the order of initial array
     */
    function getStudyYearOrPeriodAt(date, list) {
        sortStudyYearsOrPeriods(list);
        return list.find(function(item){
            return date <= item.endDate;
        });
    }

    function getCurrentStudyYearOrPeriod(list) {
        return getStudyYearOrPeriodAt(new Date(), list);
    }

    function isPastStudyYearOrPeriod(period) {
        convertStringToDates(period, ["endDate"]);
        return new Date() >= period.endDate;
    }

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
      convertStringToDates: convertStringToDates,
      convertStringToTime: convertStringToTime,
      getCurrentStudyYearOrPeriod: getCurrentStudyYearOrPeriod,
      getStudyYearOrPeriodAt: getStudyYearOrPeriodAt,
      sortStudyYearsOrPeriods: sortStudyYearsOrPeriods,
      isPastStudyYearOrPeriod: isPastStudyYearOrPeriod,

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
  }
);
