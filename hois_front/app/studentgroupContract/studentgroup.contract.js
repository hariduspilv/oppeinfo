"use strict";

angular.module('hitsaOis').controller('StudentgroupContractController', function ($scope, $route, $rootScope, QueryUtils, Classifier, $q, dialogService, $location, message, $timeout, FormUtils) {
    $scope.auth = $route.current.locals.auth;
    var clMapper = Classifier.valuemapper({ status: 'LEPING_STAATUS' });
    QueryUtils.createQueryForm($scope, '/contracts/studentGroup', {order: 'student_person.lastname,student_person.firstname'}, clMapper.objectmapper);
    var studentGroupChanged = false;
    
    $scope.reload = function() {
        QueryUtils.loadingWheel($scope, true);
        $q.all(clMapper.promises).then(function() {
            QueryUtils.loadingWheel($scope, false);
            $scope.loadData();
        });
    };

    $scope.loadContracts = function () {
      FormUtils.withValidForm($scope.searchForm, function() {
        $scope.reload();
      });
    };

    if ($route.current.params.studentGroupId) {
      QueryUtils.endpoint('/autocomplete/studentgroups').query({id: $route.current.params.studentGroupId}, function (studentGroups) {
        $scope.studentGroupObject = studentGroups[0];
      });
    }

    $scope.$watch('studentGroupObject', function (value) {
        if (angular.isObject(value)) {
            $timeout(function(){
              studentGroupChanged = true;
              $scope.criteria.studentGroup = value.id;
              $rootScope.replaceLastUrl("#/practice/studentgroup/contracts/" + $scope.criteria.studentGroup);
              if (value.curriculumVersion !== null) {
                QueryUtils.endpoint('/autocomplete/curriculumversions').query({id: value.curriculumVersion}, function (result) {
                  if (result && result.length === 1) {
                      $scope.criteria.curriculumVersion = result[0];
                    }
                  });
              } else if (value.curriculum !== null) {
                QueryUtils.endpoint('/autocomplete/curriculumsauto').query({id: value.curriculum}, function (result) {
                  if (result && result.length === 1) {
                      $scope.criteria.curriculumVersion = result[0];
                    }
                  });
              } else {
                $scope.criteria.curriculumVersion = undefined;
              }
              $scope.reload();
            });
        }
    });

    $scope.contractInWork = function(content) {
      var placeholder = content.filter(function(item) {
        if (item.status) {
          return item.status.code === 'LEPING_STAATUS_S';
        }
        return false;
      });
      return placeholder.length > 0;
    };

    $scope.studentActive = function(content) {
      var placeholder = content.filter(function(student) {
        return student.active;
      });
      return placeholder.length > 0;
    };

    $scope.$watch('criteria.active', function () {
        if (studentGroupChanged) {
            $scope.reload();
        }
    });

    $scope.sendToEkis = function() {
      $location.path("/practice/studentgroup/ekis").search({
        "":"_menu",
        studentGroup: $scope.criteria.studentGroup
      });
    };

  }).controller('StudentgroupEkisContractController', function ($scope, $route, message, QueryUtils, Classifier, $q, dialogService, FormUtils) {
    $scope.auth = $route.current.locals.auth;
    var clMapper = Classifier.valuemapper({ status: 'LEPING_STAATUS' });
    QueryUtils.createQueryForm($scope, '/contracts/ekis', {order: 'student_person.lastname,student_person.firstname'}, clMapper.objectmapper);
    
    $scope.reload = function() {
        QueryUtils.loadingWheel($scope, true);
        if (angular.isObject($scope.criteria)) {
          $scope.criteria.size = 9000;
          $scope.criteria.page = 1;
          if (angular.isObject($scope.studentGroupObject)) {
            $scope.criteria.studentGroup = $scope.studentGroupObject.id;
          } else {
            $scope.criteria.studentGroup = undefined;
          }
        }
        $q.all(clMapper.promises).then(function() {
            QueryUtils.loadingWheel($scope, false);
            $scope.loadData();
        });
    };

    $scope.checkAll = function() {
      if ($scope.allChecked === true) {
        $scope.tabledata.content.forEach(function(row) {
          row.checked = true;
        });
      } else if ($scope.allChecked === false) {
        $scope.tabledata.content.forEach(function(row) {
          row.checked = false;
        });
      }
    };

    $scope.loadContracts = function () {
      FormUtils.withValidForm($scope.searchForm, function() {
        $scope.reload();
      });
    };

    if ($route.current.params.studentGroup || $route.current.params.studentName) {
      if ($route.current.params.studentName) {
        $scope.criteria.studentName = $route.current.params.studentName;
      }
      if ($route.current.params.studentGroup) {
        QueryUtils.endpoint('/autocomplete/studentgroups').query({id: $route.current.params.studentGroup}, function (studentGroups) {
          $scope.studentGroupObject = studentGroups[0];
          $scope.reload();
        });
      } else {
        $scope.reload();
      }
    }

    $scope.contractInWork = function(content) {
      var placeholder = content.filter(function(item) {
        if (item.status) {
          return item.status.code === 'LEPING_STAATUS_S';
        }
        return false;
      });
      return placeholder.length > 0;
    };

    $scope.sendToEkis = function() {
      var contractParent = {
        contracts: []
      };
      $scope.tabledata.content.forEach(function(item) {
        if (item.id && item.checked) {
          return contractParent.contracts.push(item.id);
        }
      });
      if(contractParent.contracts.length === 0) {
        message.error('contract.atleastOne');
        return;
      }
      QueryUtils.endpoint('/contracts/checkForEkis').get().$promise.then(function(check) {
        dialogService.confirmDialog({ prompt: (check.templateExists ? 'contract.ekisconfirm' : 'contract.ekisconfirmTemplateMissing'), template: check.templateName }, function () {
          var EkisEndpoint = QueryUtils.endpoint('/contracts/sendToEkis');
          new EkisEndpoint(contractParent).$save().then(function (response) {
            $scope.allChecked = false;
            showResult(response);
          }).catch(angular.noop);
        });
      });
    };

    function showResult(response) {
        dialogService.showDialog('studentgroupContract/studentgroup.contract.result.dialog.html', function (dialogScope) {
          dialogScope.criteria = response;
        }, $scope.reload());
    }

  });