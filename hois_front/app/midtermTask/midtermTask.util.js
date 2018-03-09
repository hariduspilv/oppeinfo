'use strict';

angular.module('hitsaOis').factory('MidtermTaskUtil', ['$rootScope', 'DataUtils', 'orderByFilter', function ($rootScope, DataUtils, orderBy) {

    var MidtermTaskUtil = function() {

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
        DataUtils.convertStringToDates(midtermTasks, ['taskDate']);
        return orderBy(midtermTasks, ['taskDate', $rootScope.currentLanguageNameField()]);
      };

      this.getMoodleTasks = function(midtermTasks) {
        var result = {};
        for (var i = 0; i < midtermTasks.length; i++) {
          var task = midtermTasks[i];
          if (task.moodleGradeItemId) {
            result[task.id] = true;
          }
        }
        return result;
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
