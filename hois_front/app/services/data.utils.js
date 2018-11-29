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

    var HOURS_PER_CREDIT_POINT = 26;

    function convert(object, dateProperties, pattern) {
      if (angular.isArray(object)) {
        return object.map(function (it) { return convert(it, dateProperties, pattern); });
      }

      for (var i = 0, cnt = dateProperties.length; i < cnt; i++) {
        var property = dateProperties[i];
        if (object.hasOwnProperty(property) && typeof object[property] === 'string') {
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

    function convertObjectToIdentifier(entity, properties) {
      if (angular.isArray(properties)) {
        properties.forEach(function (property) {
          if (angular.isObject(entity[property])) {
            entity[property] = entity[property].id;
          }
        });
      }
    }

    function sortStudyYearsOrPeriods(list) {
      for (var i = 0; i < list.length; i++) {
        convertStringToDates(list[i], ["startDate", "endDate"]);
      }
      list.sort(function (el1, el2) {
        return el1.endDate - el2.endDate;   //el1.endDate >= el2.endDate did not work in IE11
      });
    }
    /**
     * Beware that using this method changes the order of initial array
     */
    function getStudyYearOrPeriodAt(date, list) {
      sortStudyYearsOrPeriods(list);
      return list.find(function (item) {
        return date <= item.endDate;
      });
    }
    /**
     * Beware that using this method changes the order of initial array
     */
    function getCurrentStudyYearOrPeriod(list) {
      return getStudyYearOrPeriodAt(new Date().withoutTime(), list);
    }
    /**
     * Beware that using this method changes the order of initial array
     */
    function isPastStudyYearOrPeriod(period) {
      convertStringToDates(period, ["endDate"]);
      return new Date().withoutTime() > period.endDate;
    }

    function periodsOverlap(period1, period2) {
      return (period1.startDate <= period2.startDate) && (period1.endDate >= period2.startDate) ||
             (period1.startDate >= period2.startDate) && (period1.startDate <= period2.endDate);
    }

    function occupiedEventTimePrompts(scope, isHigherSchool, occupiedTime) {
      var rooms = occupiedTime.rooms.map(function(room) {
        return scope.currentLanguageNameField(room);
      }).join(', ');

      var teachers = occupiedTime.teachers.map(function(teacher) {
        return scope.currentLanguageNameField(teacher);
      }).join(', ');

      var studentGroups = occupiedTime.studentGroups.map(function(studentGroup) {
        return scope.currentLanguageNameField(studentGroup);
      }).join(', ');

      var prompts = [];
      if (rooms.length > 0) {
        prompts.push('timetable.roomIsOccupied');
      }
      if (teachers.length > 0) {
        prompts.push(isHigherSchool ? 'timetable.teacherIsOccupiedHigher' : 'timetable.teacherIsOccupiedVocational');
      }
      if (studentGroups.length > 0) {
        prompts.push('timetable.studentGroupIsOccupied');
      }
      prompts.push('timetable.continue');

      return {extraPrompts: prompts, rooms: rooms, teachers: teachers, studentGroups: studentGroups};
    }

    return {
      assign: function (path, obj, value) {
        return path.split('.').reduce(function (prev, curr, currentIndex, array) {
          if (currentIndex === array.length - 1) {
            prev[curr] = value;
          } else if (!prev[curr]) {
            prev[curr] = {};
          }
          return prev[curr];
        }, obj || {});
      },
      convertStringToDates: convertStringToDates,
      convertStringToTime: convertStringToTime,
      convertObjectToIdentifier: convertObjectToIdentifier,
      getCurrentStudyYearOrPeriod: getCurrentStudyYearOrPeriod,
      getStudyYearOrPeriodAt: getStudyYearOrPeriodAt,
      sortStudyYearsOrPeriods: sortStudyYearsOrPeriods,
      isPastStudyYearOrPeriod: isPastStudyYearOrPeriod,
      periodsOverlap: periodsOverlap,
      occupiedEventTimePrompts: occupiedEventTimePrompts,

      sexFromIdcode: function (idcode) {
        if (idcode.length !== 11 || isNaN(idcode)) {
          return null;
        }
        return Number(idcode.charAt(0)) % 2 === 1 ? 'SUGU_M' : 'SUGU_N';
      },

      birthdayFromIdcode: function (idcode) {
        if (idcode.length !== 11 || isNaN(idcode)) {
          return null;
        }
        var centuries = { "1": "18", "2": "18", "3": "19", "4": "19", "5": "20", "6": "20", "7": "21", "8": "21" };
        var date = centuries[idcode.charAt(0)] + idcode.substring(1, 7);
        return moment(date).toDate();
      },

      creditsToHours: function(credits) {
        return Math.round(HOURS_PER_CREDIT_POINT * credits);
      },

      hoursToCredits: function(hours) {
        return Math.round((hours / HOURS_PER_CREDIT_POINT) * 10) / 10;
      },

      isSameDay: function(date1, date2) {
        return date1.getFullYear() === date2.getFullYear() && date1.getMonth() === date2.getMonth() && 
          date1.getDate() === date2.getDate();
      },

      isWeekend: function(date) {
        var day = date.getDay();
        return day === 6 || day === 0;
      }
    };
  }
);

Date.prototype.withoutTime = function () {
  var date = new Date(this);
  date.setHours(0, 0, 0, 0);
  return date;
};
