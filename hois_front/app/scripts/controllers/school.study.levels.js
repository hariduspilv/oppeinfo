'use strict';

angular.module('hitsaOis').controller('SchoolStudyLevelsController',

  function (QueryUtils, Classifier, message, $q, $scope) {
    $scope.studyLevelDefs = Classifier.query({mainClassCode: 'OPPEASTE'});
    $scope.studyLevels = QueryUtils.endpoint('/school/studyLevels').get();

    $q.all([$scope.studyLevelDefs.$promise, $scope.studyLevels.$promise]).then(function() {
      // update model.selected based on current selected codes
      var defs = $scope.studyLevelDefs.content;
      var selected = $scope.studyLevels.studyLevels;
      for(var row_no = 0, row_cnt = defs.length;row_no < row_cnt;row_no++) {
        var level = defs[row_no];
        level.selected = selected.indexOf(level.code) !== -1;
      }
    });

    $scope.update = function() {
      // create list of selected codes based on model.selected values
      var defs = $scope.studyLevelDefs.content;
      var selected = [];
      for(var row_no = 0, row_cnt = defs.length;row_no < row_cnt;row_no++) {
        var level = defs[row_no];
        if(level.selected) {
          selected.push(level.code);
        }
      }
      $scope.studyLevels.studyLevels = selected;

      $scope.studyLevels.$put().then(function() {
        message.info('main.messages.update.success');
      });
    };
  }
);
