'use strict';

angular.module('hitsaOis').controller('SubjectStudyPeriodStudentGroupEditController', ['$scope', 'QueryUtils', 'ArrayUtils', 'Classifier', '$route', 'message', 'dialogService', '$rootScope', 'SspCapacities', 'DataUtils', function ($scope, QueryUtils, ArrayUtils, Classifier, $route, message, dialogService, $rootScope, SspCapacities, DataUtils) {

    var studyPeriodId = $route.current.params.studyPeriodId ? parseInt($route.current.params.studyPeriodId, 10) : null;
    var studentGroup = $route.current.params.studentGroupId ? parseInt($route.current.params.studentGroupId, 10) : null;
    $scope.isEditing = studentGroup === null && studyPeriodId === null;
    var Endpoint = QueryUtils.endpoint('/subjectStudyPeriods/studentGroups/container');

    $scope.capacityTypes = QueryUtils.endpoint('/autocomplete/schoolCapacityTypes').query({ isHigher: true });

    $scope.record = {};

    function setCurrentStudyPeriod() {
      if(!$scope.record.studyPeriod) {
        $scope.record.studyPeriod = DataUtils.getCurrentStudyYearOrPeriod($scope.studyPeriods).id;
        studyPeriodId = $scope.record.studyPeriod;
      }
    }

    if(!studyPeriodId) {
      $scope.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriods').query(setCurrentStudyPeriod);
    }

    if(studentGroup) {
        $scope.container = {studyPeriod: studyPeriodId, studentGroup: studentGroup, subjectStudyPeriodDtos: []};
        $scope.record = Endpoint.search($scope.container);
        $scope.record.$promise.then(function(response){
            $scope.capacitiesUtil = new SspCapacities(response);
            $scope.capacityTypes = response.capacityTypes;
            $scope.capacitiesUtil.addEmptyCapacities($scope.capacityTypes);
        });
        $scope.studentGroup = QueryUtils.endpoint('/studentgroups/' + studentGroup).get(function(response) {
            $scope.studentGroup.nameEt = response.code;
            $scope.course = $scope.studentGroup.course.toString();
            getCurriculum();
        });
        $scope.formState = {xlsUrl: 'subjectStudyPeriods/studentGroups/subjectstudyperiodstudentgroup.xls'};
    }

    function loadStudentGroups() {
      if($scope.record.studyPeriod) {
        $scope.studentGroups = QueryUtils.endpoint('/subjectStudyPeriods/studentGroups/list/limited/' + $scope.record.studyPeriod).query();
      }
    }

    $scope.$watch('record.studyPeriod', loadStudentGroups);

    function getCurriculum() {
      $scope.curriculum = QueryUtils.endpoint('/subjectStudyPeriods/studentGroups/curriculum/' +  $scope.studentGroup.curriculum.id).get(getCurriculumStudyPeriod);
    }

    function getCurriculumStudyPeriod() {
        var sp = $scope.curriculum.studyPeriod;
        $scope.curriculumStudyPeriod = {
            years: Math.floor(sp / 12),
            months: sp % 12
        };
    }

    function selectStudentGroup() {
        $scope.studentGroup = $scope.studentGroups.find(function(el){
          return el.id === $scope.record.studentGroup;
        });
        if($scope.studentGroup) {
          // $scope.studentGroup = sg;
          $scope.course = $scope.studentGroup.course.toString();
          getCurriculum();
        }
    }

    if(!$scope.isEditing) {
      $scope.studyPeriod = QueryUtils.endpoint('/subjectStudyPeriods/studyPeriod').get({id: studyPeriodId});
    }

    $scope.$watch('record.studentGroup', function() {
            if(!ArrayUtils.isEmpty($scope.studentGroups) && $scope.record.studentGroup) {
                selectStudentGroup();
            }
        }
    );

    // load validation

    $scope.subjectsLoadValid = function(subjectId) {
        var sspLoadByType, sspsCapacities;
        var ssps = $scope.capacitiesUtil.getCapacitiesBySubject(subjectId);

        var getCapacityByType = function(capacityTypeCode) {
            return function(el) {
                return el.capacityType === capacityTypeCode;
            };
        };

        for (var i = 0; i < $scope.capacityTypes.length; i++) {

            sspsCapacities = $scope.capacitiesUtil.getSubjectsCapacityByType(subjectId, $scope.capacityTypes[i].code);

            for(var j = 0; j < ssps.length; j++) {
                var capacity = ssps[j].capacities.find(getCapacityByType($scope.capacityTypes[i].code));
                sspLoadByType = capacity ? capacity.hours : 0;
                if(sspLoadByType > sspsCapacities) {
                    return false;
                }
            }
        }
        return true;
    };

    $scope.save = function() {
      $scope.subjectStudyPeriodStudentGroupEditForm.$setSubmitted();
      if(!$scope.subjectStudyPeriodStudentGroupEditForm.$valid) {
        message.error('main.messages.form-has-errors');
        return;
      }
      var subjects = $scope.record.subjects;
      if(ArrayUtils.isEmpty(subjects) || ArrayUtils.isEmpty($scope.record.subjectStudyPeriodDtos)) {
          message.error('subjectStudyPeriod.error.noDataForSaving');
          return;
      }
      var wrongSubjects = $scope.record.subjects.filter(function(el){
          return !$scope.subjectsLoadValid(el.id);
      });
      if(!ArrayUtils.isEmpty(wrongSubjects)) {
          var subjectsNames = wrongSubjects.map(function(el){
              return $rootScope.currentLanguageNameField(el);
          }).join(", ");
          dialogService.confirmDialog({
              prompt: 'subjectStudyPeriod.error.subjectLoad',
              subject: subjectsNames
          }, save);
          return;
      }
      save();
    };

    function save() {
        $scope.record.studyPeriod = studyPeriodId;
        $scope.capacitiesUtil.filterEmptyCapacities();
        $scope.record.$put().then(function(response){
            message.updateSuccess();
            $scope.record = response;
            $scope.capacitiesUtil.addEmptyCapacities($scope.capacityTypes);
            $scope.subjectStudyPeriodStudentGroupEditForm.$setPristine();
        });
    }

}]);
