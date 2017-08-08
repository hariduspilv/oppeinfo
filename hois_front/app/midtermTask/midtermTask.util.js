'use strict';

angular.module('hitsaOis').factory('MidtermTaskUtil', ['$rootScope', 'DataUtils', 'orderByFilter', function ($rootScope, DataUtils, orderBy) {

    var MidtermTaskUtil = function() {

      var self = this;

      function getNumberWithZero(number) {
        return number < 10 ? "0" + number : number;
      }

      this.getMidtermTaskHeader = function(midtermTask) {
        var date = midtermTask.taskDate ?
          getNumberWithZero(midtermTask.taskDate.getDate()) + "." +
          getNumberWithZero(midtermTask.taskDate.getMonth() + 1) : "";
        return $rootScope.currentLanguageNameField(midtermTask) + " " + date + " (" + midtermTask.percentage + "%), max " + midtermTask.maxPoints;
      };

      this.getSortedMidtermTasks = function(midtermTasks) {
        midtermTasks.forEach(function(el){
          DataUtils.convertStringToDates(el, ["taskDate"]);
        });
        return orderBy(midtermTasks, ['taskDate', $rootScope.currentLanguageNameField()]);
      };

      function indexOfMidtermTask(midtermTaskId, midtermTasks) {
        var midtermTask = midtermTasks.find(function(el){
          return el.id === midtermTaskId;
        });
        return midtermTasks.indexOf(midtermTask);
      }

      this.sortStudentResults = function(studentResults, midtermTasks) {
        studentResults.sort(function(val1, val2){
          return indexOfMidtermTask(val1.midtermTask, midtermTasks) - indexOfMidtermTask(val2.midtermTask, midtermTasks);
        });
      };

    };
    return MidtermTaskUtil;
}]);
