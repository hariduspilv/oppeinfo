'use strict';

angular.module('hitsaOis').controller('SubjectStudyPeriodStudentGroupEditController',
  ['$scope', 'QueryUtils', 'ArrayUtils', 'Classifier', '$route', 'message', 'dialogService',
   '$rootScope', 'SspCapacities', 'DataUtils', '$location', '$sessionStorage',
  function ($scope, QueryUtils, ArrayUtils, Classifier, $route, message,
    dialogService, $rootScope, SspCapacities, DataUtils, $location, $sessionStorage) {

    var studyPeriodId = $route.current.params.studyPeriodId ? parseInt($route.current.params.studyPeriodId, 10) : null;
    var studentGroup = $route.current.params.studentGroupId ? parseInt($route.current.params.studentGroupId, 10) : null;
    $scope.isEditing = studentGroup === null && studyPeriodId === null;
    var Endpoint = QueryUtils.endpoint('/subjectStudyPeriods/studentGroups/container');

    $scope.capacityTypes = QueryUtils.endpoint('/autocomplete/schoolCapacityTypes').query({ isHigher: true });

    $scope.record = {};
    $scope.selectedPair = selectedPair;
    $scope.getSubjectsBySemester = getSubjectsBySemester;
    $scope.unplannedSubjectOrderBy = unplannedSubjectOrderBy;

    function setCurrentStudyPeriod() {
      if(!$scope.record.studyPeriod) {
        $scope.record.studyPeriod = DataUtils.getCurrentStudyYearOrPeriod($scope.studyPeriods).id;
        studyPeriodId = $scope.record.studyPeriod;
      }
    }

    $scope.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriodsWithYear').query();
    
    if(!studyPeriodId) {
      $scope.studyPeriods.$promise.then(setCurrentStudyPeriod);
      $scope.studyPeriods.$promise.then(function (response) {
        response.forEach(function (studyPeriod) {
            studyPeriod[$scope.currentLanguageNameField()] = $scope.currentLanguageNameField(studyPeriod.studyYear) + ' ' + $scope.currentLanguageNameField(studyPeriod);
        });
      });
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
            getCurriculumProgram(studentGroup, studyPeriodId);
        });
        $scope.formState = {xlsUrl: 'subjectStudyPeriods/studentGroups/subjectstudyperiodstudentgroup.xls'};
    }

    function loadStudentGroups() {
      if($scope.record.studyPeriod) {
        $scope.studentGroups = QueryUtils.endpoint('/subjectStudyPeriods/studentGroups/list/limited/' + $scope.record.studyPeriod).query();
      }
    }

    $scope.$watch('record.studyPeriod', function() {
      if ($scope.isEditing) {
        // Reset paramteres after changing study period
        $scope.record.studentGroup = undefined;
        $scope.course = undefined;
        $scope.curriculum = undefined;
        $scope.curriculumStudyPeriod = undefined;
        $scope.semester = undefined;
      }
      loadStudentGroups()
    });
    $scope.$watch('record.studentGroup', function() {
      if(!ArrayUtils.isEmpty($scope.studentGroups) && $scope.record.studentGroup) {
        selectStudentGroup();
      }
    });
    $scope.$watchGroup(['record.studentGroup', 'record.studyPeriod'], function (newValues) {
      if (newValues[0] && newValues[1]) {
        getCurriculumProgram(newValues[0], newValues[1]);
      } else {
        $scope.curriculumProgram = [];
      }
    });

    function getCurriculumProgram(aStudentGroupId, aStudyPeriodId) {
      $scope.curriculumProgram = QueryUtils
        .endpoint('/subjectStudyPeriods/studentGroups/curriculumProgram/:studyPeriodId/:studentGroupId')
        .get({studyPeriodId: aStudyPeriodId, studentGroupId: aStudentGroupId}, function (response) {
          if (response) {
            for (var sem in response) {
              if (!isNaN(sem)) {
                for (var i = 0; i < response[sem].length; i++) {
                  response[sem][i].semester = parseInt(sem);
                }
              }
            }
          }
        });
    }

    function getCurriculum() {
      $scope.curriculum = QueryUtils.endpoint('/subjectStudyPeriods/studentGroups/curriculum/' + $scope.studentGroup.curriculum.id).get(getCurriculumStudyPeriod);
    }

    function getCurriculumStudyPeriod() {
        var sp = $scope.curriculum.studyPeriod;
        $scope.curriculumStudyPeriod = {
            years: Math.floor(sp / 12),
            months: sp % 12
        };

        $scope.semesters = [].constructor(Math.ceil(sp / 6));
        // set current semester
        var startYear = $scope.studentGroup.curriculumVersionAdmissinYear;
        if (startYear) {
          var selectedPeriod = $scope.studyPeriods.find(function (period) {
            return period.id === $scope.record.studyPeriod;
          });
          if (selectedPeriod) {
            $scope.semester = calculateCurrentSemester(startYear, selectedPeriod);
          }
        }
    }

    function selectStudentGroup() {
        $scope.studentGroup = $scope.studentGroups.find(function(el){
          return el.id === $scope.record.studentGroup;
        });
        if($scope.studentGroup) {
          $scope.course = $scope.studentGroup.course.toString();
          getCurriculum();
        }
    }

    if(!$scope.isEditing) {
      $scope.studyPeriod = QueryUtils.endpoint('/subjectStudyPeriods/studyPeriod').get({id: studyPeriodId});
    }

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
          return !$scope.capacitiesUtil.subjectsLoadValid(el.id, $scope.capacityTypes);
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

    $scope.teacherPlannedLoad = function (teacher) {
        return $scope.capacitiesUtil.teacherPlannedLoad(teacher);
    };

    $scope.teacherCapacities = function (subject) {
        dialogService.showDialog('subjectStudyPeriod/teacher.capacities.tmpl.html', function (dialogScope) {
            dialogScope.subject = subject;
            dialogScope.capacityTypes = $scope.capacityTypes;
            dialogScope.capacitiesUtil = $scope.capacitiesUtil;
        }, function () {
            $scope.save();
        });
    };

    function selectedPair(subject) {
      if (subject && !!subject.selectedPair) {
        dialogService.confirmDialog({prompt: 'subjectStudyPeriod.prompt.addGroupToPair'}, function () {
          QueryUtils.endpoint('/subjectStudyPeriods/studentGroups/connect/:subjectStudyPeriodId/:studentGroupId')
          .get({subjectStudyPeriodId: subject.selectedPair, studentGroupId: $scope.studentGroup.id}, function () {
            // If we have these values then they are readonly fields
            $scope.curriculumProgram = [];
            if (studentGroup && studyPeriodId) {
              $scope.record = Endpoint.search({studyPeriod: studyPeriodId, studentGroup: studentGroup});
              $scope.record.$promise.then(function(response){
                  $scope.capacitiesUtil = new SspCapacities(response);
                  $scope.capacityTypes = response.capacityTypes;
                  $scope.capacitiesUtil.addEmptyCapacities($scope.capacityTypes);
                  getCurriculumProgram(studentGroup, studyPeriodId);
              });
            } else {
              $location.url('/subjectStudyPeriods/studentGroups/' + $scope.studentGroup.id + '/' + $scope.record.studyPeriod + '/edit?_noback');
            }
          });
        }, function () {
          subject.selectedPair = undefined;
        });
      }
    }

    function getSubjectsBySemester() {
      if (!$scope.semester || !$scope.curriculumProgram) {
        return [];
      }

      var marked = {};
      var counter = $scope.semester;
      var subjects = [];
      while (counter > 0) {
        if ($scope.curriculumProgram[counter]) {
          for (var i = 0; i < $scope.curriculumProgram[counter].length; i++) {
            if ($scope.curriculumProgram[counter][i].alreadyExistsForGroup) {
              continue;
            }
            if (marked[$scope.curriculumProgram[counter][i].subject.id]) {
              continue;
            }
            marked[$scope.curriculumProgram[counter][i].subject.id] = true;
            subjects.push($scope.curriculumProgram[counter][i]);
          }
        }
        counter--;
      }
      return subjects;
    }

    function unplannedSubjectOrderBy(subjectContainer) {
      return $scope.currentLanguageNameField(subjectContainer.subject);
    }

    /**
     * Find a semester for selected period and group
     * 
     * @param {number} startYear 
     * @param {object} selectedStudyPeriod 
     */
    function calculateCurrentSemester(startYear, selectedStudyPeriod) {
      var historySemester = checkHistorySemester($scope.record.studyPeriod, $scope.record.studentGroup);

      if (historySemester) {
        return historySemester;
      }

      var periodYearStart = selectedStudyPeriod.studyYear.startDate;
      if (typeof periodYearStart === 'string') {
        periodYearStart = new Date(periodYearStart);
      }

      var yearDiff = periodYearStart.getFullYear() - startYear;
      var semester = yearDiff * 2 + (selectedStudyPeriod.type === 'OPPEPERIOOD_S' ? 1 : selectedStudyPeriod.type === 'OPPEPERIOOD_K' ? 2 : -999)

      if (semester < 1) {
        return 1;
      }
      if (semester > $scope.semesters.length) {
        return $scope.semesters.length;
      }

      return semester;
    }

    $scope.$watch('semester', function (newV, oldV) {
      if (newV && newV !== oldV && $scope.record.studentGroup && $scope.record.studyPeriod) {
        var key = "sspsg-pair-" + $scope.record.studyPeriod + "-" + $scope.record.studentGroup;
        $sessionStorage[key] = newV;
      }
    });

    function checkHistorySemester(studyPeriodId, studentGroudId) {
      if (studyPeriodId && studentGroudId) {
        var key = "sspsg-pair-" + studyPeriodId + "-" + studentGroudId;
        return $sessionStorage[key];
      }
    }
}]);
