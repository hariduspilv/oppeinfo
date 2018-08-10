'use strict';

angular.module('hitsaOis').controller('ExamSearchController', ['$q', '$route', '$scope', '$timeout', 'message', 'Classifier', 'DataUtils', 'QueryUtils', 'USER_ROLES',
  function ($q, $route, $scope, $timeout, message, Classifier, DataUtils, QueryUtils, USER_ROLES) {

    $scope.auth = $route.current.locals.auth;
    var clMapper = Classifier.valuemapper({type: 'SOORITUS'});
    QueryUtils.createQueryForm($scope, '/exams', {order: 'te.start'}, clMapper.objectmapper);
    $scope.formState = {studyPeriods: QueryUtils.endpoint('/autocomplete/studyPeriods').query(),
                        canAdd: $scope.auth.authorizedRoles.indexOf(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_EKSAM) !== -1};
    var promises = clMapper.promises;
    promises.push($scope.formState.studyPeriods.$promise);

    $q.all(promises).then(function() {
      if($scope.formState.studyPeriods.length > 0 && !$scope.criteria.studyPeriod) {
        var currentStudyPeriod = DataUtils.getCurrentStudyYearOrPeriod($scope.formState.studyPeriods);
        $scope.criteria.studyPeriod = currentStudyPeriod ? currentStudyPeriod.id : undefined;
      }
      if($scope.criteria.studyPeriod) {
        $timeout($scope.loadData);
      } else if($scope.formState.studyPeriods.length === 0) {
        message.error('studyYear.studyPeriod.missing');
      }
    });
  }
]).controller('ExamStudentRegistrationController', ['$q', '$rootScope', '$scope', '$timeout', 'dialogService', 'message', 'Classifier', 'DataUtils', 'QueryUtils',
  function($q, $rootScope, $scope, $timeout, dialogService, message, Classifier, DataUtils, QueryUtils) {

   var baseUrl = '/exams';
   var clMapper = Classifier.valuemapper({assessment: 'HINDAMISVIIS', type: 'SOORITUS'});
   QueryUtils.createQueryForm($scope, baseUrl + '/forregistration', {order: 'te.start'});
   $scope.afterLoadData = function(result) {
     $scope.tabledata.content = result.content;
     $scope.tabledata.totalElements = result.totalElements;
     clMapper.objectmapper(result.content);
   };

   $scope.formState = {studyPeriods: QueryUtils.endpoint('/autocomplete/studyPeriods').query()};
   var promises = clMapper.promises;
   promises.push($scope.formState.studyPeriods.$promise);

   $q.all(promises).then(function() {
    if($scope.formState.studyPeriods.length > 0 && !$scope.criteria.studyPeriod) {
      var currentStudyPeriod = DataUtils.getCurrentStudyYearOrPeriod($scope.formState.studyPeriods);
      $scope.criteria.studyPeriod = currentStudyPeriod ? currentStudyPeriod.id : undefined;
    }
    if($scope.criteria.studyPeriod) {
      $timeout($scope.loadData);
    }
   });

   $scope.studyPeriodChanged = function() {
     if($scope.criteria.studyPeriod) {
       $scope.loadData();
     }
   };

   $scope.register = function(row) {
     dialogService.confirmDialog({prompt: 'exam.message.registerconfirmation', exam: $rootScope.currentLanguageNameField(row.subject)}, function() {
       QueryUtils.endpoint(baseUrl + '/register').update({id: row.id}).$promise.then(function() {
         message.info('exam.message.registered');
         $scope.loadData();
       }).catch(angular.noop);
     });
   };

   $scope.unregister = function(row) {
     dialogService.confirmDialog({prompt: 'exam.message.unregisterconfirmation', exam: $rootScope.currentLanguageNameField(row.subject)}, function() {
       QueryUtils.endpoint(baseUrl + '/unregister').update({id: row.id}).$promise.then(function() {
         message.info('exam.message.unregistered');
         $scope.loadData();
       }).catch(angular.noop);
     });
   };
  }
]).controller('ExamEditController', ['$location', '$route', '$scope', 'dialogService', 'message', 'Classifier', 'DataUtils', 'FormUtils', 'QueryUtils',
  function($location, $route, $scope, dialogService, message, Classifier, DataUtils, FormUtils, QueryUtils) {
    var id = $route.current.params.id;
    var baseUrl = '/exams';
    var Endpoint = QueryUtils.endpoint(baseUrl);

    var examTypes = Classifier.queryForDropdown({mainClassCode: 'SOORITUS'}), examTypeMap;
    examTypes.$promise.then(function() {
      examTypeMap = Classifier.toMap(examTypes);
    });
    $scope.formState = {minDate: moment().subtract(1, 'days').startOf('day').toDate()};

    function calculateName() {
      // subject name/code, exam type name
      var ssp = $scope.formState.subjectStudyPeriod;
      if(ssp && $scope.record.type) {
        examTypes.$promise.then(function() {
          $scope.record.name = ssp.subject.nameEt + ' ' + (examTypeMap[$scope.record.type].nameEt || '').toLowerCase();
        });
      } else {
        $scope.record.name = '';
      }
    }

    $scope.examTypeChanged = function() {
      var examType = $scope.record.type;
      if($scope.record.id && examType) {
        $scope.formState.availableStudents = QueryUtils.endpoint(baseUrl + '/studentsforregistration/' + id).query({type: examType});
      } else {
        $scope.formState.availableStudents = [];
      }
      calculateName();
    };

    function setEditable() {
      $scope.formState.allEditable = !$scope.record.id || !$scope.record.students || !$scope.record.students.length;
      $scope.formState.canDelete = !$scope.record.students || !$scope.record.students.length;
    }

    function afterLoad() {
      var students = $scope.record.studentRecords || [];
      students.forEach(function(it, i) { it.rowno = i + 1; });
      $scope.formState.students = students;
      delete $scope.record.studentRecords;
      $scope.formState.subjectStudyPeriod = $scope.record.subjectStudyPeriodDto;
      delete $scope.record.subjectStudyPeriodDto;
      setEditable();
    }

    if(id) {
      $scope.record = Endpoint.get({id: id}, afterLoad);
      $scope.record.$promise.then($scope.examTypeChanged);
    } else {
      $scope.record = new Endpoint({students: [], studentRecords: []});
      afterLoad();
    }

    $scope.update = function() {
      FormUtils.withValidForm($scope.examForm, function() {
        var occupiedQuery = timetableTimeOccupiedQuery();
        QueryUtils.endpoint('/timetableevents/timetableTimeOccupied').get(occupiedQuery).$promise.then(function (result) {
          if (result.occupied) {
            dialogService.confirmDialog(DataUtils.occupiedEventTimePrompts(result), function () {
              update();
            });
          } else {
            update();
          }
        });
      });
    };

    function timetableTimeOccupiedQuery() {
      var occupiedQuery = {rooms: $scope.record.room.id, subjectStudyPeriod: $scope.record.subjectStudyPeriod, exam: $scope.record.id};
      
      var date = moment($scope.record.startDate);
      var startTime = moment($scope.record.startTime, 'HH:mm');
      var endTime = moment($scope.record.endTime, 'HH:mm');
      occupiedQuery.startTime = new Date(date.year(), date.month(), date.date(), startTime.hours(), startTime.minutes());
      occupiedQuery.endTime = new Date(date.year(), date.month(), date.date(), endTime.hours(), endTime.minutes());
      
      return occupiedQuery;
    }

    $scope.getLocalDateTime = function (time) {
      var date = moment($scope.record.startDate);
      time = moment(time, 'HH:mm');
      return new Date(date.year(), date.month(), date.date(), time.hours(), time.minutes());
    };

    function update() {
      if($scope.record.id) {
        $scope.record.$update().then(message.updateSuccess).then(afterLoad).catch(angular.noop);
        $scope.examForm.$setPristine();
      } else {
        $scope.record.$save().then(function() {
          message.info('main.messages.create.success');
          $location.url(baseUrl + '/' + $scope.record.id + '/edit?_noback');
        }).catch(angular.noop);
      }
    }

    $scope.delete = function() {
      FormUtils.deleteRecord($scope.record, '/examTimes?_noback', {prompt: 'exam.message.deleteconfirm'});
    };

    $scope.lookupStudents = function(text) {
      // ignore ids already in record.students
      text = (text || '').toUpperCase();
      return $scope.formState.availableStudents.filter(function(it) { return it.fullname.toUpperCase().indexOf(text) !== -1 && $scope.record.students.indexOf(it.id) === -1; });
    };

    $scope.addStudent = function() {
      var studentId = $scope.formState.selectedStudent ? $scope.formState.selectedStudent.id : undefined;
      if(studentId) {
        var student = $scope.formState.availableStudents.find(function(it) { return it.id === studentId; });
        student.rowno = $scope.formState.students.length + 1;
        $scope.formState.students.push(student);
        $scope.record.students.push(student.id);
      }
      $scope.formState.selectedStudent = undefined;
      setEditable();
    };

    $scope.deleteStudent = function(row) {
      dialogService.confirmDialog({prompt: 'exam.message.deletestudentconfirm'}, function() {
        var rows = $scope.formState.students;
        for(var i = 0, cnt = rows.length; i < cnt; i++) {
          if(rows[i] === row) {
            rows.splice(i, 1);
            var j = $scope.record.students.indexOf(row.id);
            if(j !== -1) {
              $scope.record.students.splice(j, 1);
            }
            $scope.examForm.$setDirty();
            break;
          }
        }
        setEditable();
      });
    };

    if(!id) {
      var studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriods').query();
      var subjectStudyPeriodEndpoint = QueryUtils.endpoint(baseUrl + '/subjectstudyperiods');
      $scope.formState.studyPeriods = studyPeriods;
      $scope.formState.subjectStudyPeriods = [];

      $scope.studyPeriodChanged = function() {
        if($scope.formState.studyPeriod) {
          var query = {studyPeriod: $scope.formState.studyPeriod};
          $scope.formState.subjectStudyPeriods = subjectStudyPeriodEndpoint.query(query);
        } else {
          $scope.formState.subjectStudyPeriods = [];
        }
        $scope.formState.subjectStudyPeriodId = undefined;
        $scope.formState.subjectStudyPeriod = undefined;
        $scope.record.subjectStudyPeriod = undefined;
        $scope.subjectStudyPeriodChanged();
      };

      studyPeriods.$promise.then(function() {
        if(studyPeriods.length > 0) {
          var currentStudyPeriod = DataUtils.getCurrentStudyYearOrPeriod(studyPeriods);
          $scope.formState.studyPeriod = currentStudyPeriod ? currentStudyPeriod.id : undefined;
        }
        $scope.studyPeriodChanged();
      });

      $scope.subjectStudyPeriodChanged = function() {
        if($scope.formState.subjectStudyPeriodId) {
          var item = $scope.formState.subjectStudyPeriods.find(function(it) { return it.id === $scope.formState.subjectStudyPeriodId; });
          $scope.formState.subjectStudyPeriod = item;
          $scope.record.subjectStudyPeriod = item.id;
        }
        calculateName();
      };
    }
  }
]).controller('ExamViewController', ['$route', '$scope', 'QueryUtils',
  function($route, $scope, QueryUtils) {
    var Endpoint = QueryUtils.endpoint('/exams');
    var id = $route.current.params.id;

    $scope.formState = {};
    $scope.record = Endpoint.get({id: id}, function() {
      $scope.record.studentRecords.forEach(function(it, i) { it.rowno = i + 1; });
    });
  }
]);
