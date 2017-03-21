'use strict';

angular.module('hitsaOis').controller('StudentGroupSearchController', ['$q', '$scope', 'Classifier', 'QueryUtils',
  function ($q, $scope, Classifier, QueryUtils) {
    var clMapper = Classifier.valuemapper({studyForm: 'OPPEVORM'});
    QueryUtils.createQueryForm($scope, '/studentgroups', {order: 'code'}, clMapper.objectmapper);

    $q.all(clMapper.promises).then($scope.loadData);
  }
]).controller('StudentGroupEditController', ['$location', '$mdDialog', '$q', '$route', '$scope', 'dialogService', 'message', 'Classifier', 'QueryUtils',
  function ($location, $mdDialog, $q, $route, $scope, dialogService, message, Classifier, QueryUtils) {
    var id = $route.current.params.id;
    var baseUrl = '/studentgroups';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var clMapper = Classifier.valuemapper({studyForm: 'OPPEVORM', studyLevel: 'OPPEASTE'});

    $scope.formState = {curriculumVersions: [], languages: [], studyForms: [], specialities: [], selectedStudents: []};
    $scope.curriculumChanged = function() {
      var curriculumId = $scope.record.curriculum ? $scope.record.curriculum.id : null;
      if($scope.formState.curriculumId === curriculumId) {
        return;
      }
      $scope.formState.curriculumId = curriculumId;
      // store current values
      $scope.formState.language = $scope.record.language;
      $scope.formState.studyForm = $scope.record.studyForm;
      $scope.formState.speciality = $scope.record.speciality;

      var afterCurriculumChange = function(result) {
        $scope.formState.curriculumVersions = result.curriculumVersions;
        $scope.formState.languages = result.languages || [];
        $scope.formState.studyForms = result.studyForms || [];
        $scope.formState.origStudyLevel = result.origStudyLevel;
        $scope.formState.specialities = result.specialities || [];

        // try to restore values
        $scope.record.language = $scope.formState.languages.indexOf($scope.formState.language) !== -1 ? $scope.formState.language : null;
        $scope.record.studyForm = $scope.formState.studyForms.indexOf($scope.formState.studyForm) !== -1 ? $scope.formState.studyForm : null;
        $scope.record.speciality = $scope.formState.specialities.indexOf($scope.formState.speciality) !== -1 ? $scope.formState.speciality : null;
      };
      if(curriculumId) {
        QueryUtils.endpoint(baseUrl+'/curriculumdata').get({id: curriculumId}, afterCurriculumChange);
      } else {
        // curriculum cleared
        afterCurriculumChange({});
      }
    };

    function afterLoad() {
      $scope.formState.students = clMapper.objectmapper($scope.record.members);
      $scope.formState.selectedStudents = angular.copy($scope.formState.students);
      $scope.curriculumChanged();
    }

    if(id) {
      $scope.record = Endpoint.get({id: id}, afterLoad);
    } else {
      // new student group
      $scope.record = new Endpoint();
    }

    $scope.update = function() {
      $scope.studentGroupForm.$setSubmitted();
      if(!$scope.studentGroupForm.$valid) {
        message.error('main.messages.form-has-errors');
        return;
      }

      $scope.record.students = $scope.formState.selectedStudents.map(function(item) { return item.id; });
      if($scope.record.id) {
        $scope.record.$update().then(afterLoad).then(message.updateSuccess);
      }else{
        $scope.record.$save().then(function() {
          message.info('main.messages.create.success');
          $location.path(baseUrl + '/' + $scope.record.id + '/edit');
        });
      }
    };

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'studentGroup.deleteconfirm'}, function() {
        $scope.record.$delete().then(function() {
          message.info('main.messages.delete.success');
          $location.path(baseUrl);
        });
      });
    };

    function storeStudents(selectedStudents) {
      $scope.formState.selectedStudents = $scope.formState.selectedStudents.concat(selectedStudents);
      $scope.update();
    }

    $scope.addStudents = function() {
      var query = angular.extend({}, $scope.record);
      var findstudents = function(scope) {
        QueryUtils.endpoint(baseUrl+'/findstudents').query(query, function(result) {
          if(result.length === 0) {
            scope.formState.students = undefined;
            message.info('studentGroup.nostudentsfound');
            return;
          }
          $q.all(clMapper.promises).then(function() {
            scope.formState.students = clMapper.objectmapper(result);
          });
        });
      };

      if($scope.record.id) {
        $mdDialog.show({
          controller: function($scope) {
            $scope.formState = {selectedStudents: []};

            $scope.cancel = $mdDialog.hide;
            $scope.select = function() {
              storeStudents($scope.formState.selectedStudents);
              $mdDialog.hide();
            };

            findstudents($scope);
          },
          templateUrl: 'studentGroup/student.select.dialog.html',
          clickOutsideToClose: true
        });
        return;
      }
      $scope.studentGroupForm.$setSubmitted();
      if(!$scope.studentGroupForm.$valid) {
        message.info('studentGroup.mandatoryfields');
        return;
      }
      findstudents($scope);
    };

    $scope.removeStudent = function(id) {
      dialogService.confirmDialog({prompt: 'studentGroup.deletestudentconfirm'}, function() {
        function filter(i) {
          return i.id !== id;
        }
        $scope.formState.students = $scope.formState.students.filter(filter);
        $scope.formState.selectedStudents = $scope.formState.selectedStudents.filter(filter);
      });
    };
  }
]);
