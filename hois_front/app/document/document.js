'use strict';

angular.module('hitsaOis').controller('DiplomaController', ['$scope', '$q', '$window', 'Classifier', 'config', 'QueryUtils', 'DataUtils', 'DocumentUtils', 'dialogService', 'message',
  function ($scope, $q, $window, Classifier, config, QueryUtils, DataUtils, DocumentUtils, dialogService, message) {
    var baseUrl = '/documents';

    $scope.currentNavItem = 'document.diplomas';

    $scope.criteria = {};

    var clMapper = Classifier.valuemapper({status: 'KASKKIRI_STAATUS'});

    $scope.formState = {
      directives: QueryUtils.endpoint(baseUrl + '/diploma/directives').query(function(result) {
        DataUtils.convertStringToDates(result, ['date']);
        $q.all(clMapper.promises).then(function() {
          clMapper.objectmapper(result);
          DocumentUtils.mapDirectiveDisplay(result);
        });
      }),
      students: []
    };

    $scope.directiveChanged = function() {
      QueryUtils.endpoint(baseUrl + '/formtypes').query($scope.criteria, function(result) {
        $scope.formState.formTypes = result;
        if (result.length === 1) {
          $scope.criteria.formType = result[0];
          $scope.formTypeChanged();
        }
      });
    };
    function querySigners() {
      QueryUtils.endpoint(baseUrl + '/signers').query(function(result) {
        DocumentUtils.mapSignerDisplay(result);
        $scope.formState.signers = result;
        $scope.formState.firstsigners = result.filter(function(signer) {
          return signer.isFirst;
        });
        if ($scope.formState.firstsigners.length === 1) {
          $scope.criteria.signer1Id = $scope.formState.firstsigners[0].id;
        }
      });
    }
    function mapToFullCode(form) {
      return form.fullCode;
    }
    function queryFreeForms() {
      QueryUtils.endpoint(baseUrl + '/diploma/forms').query({formType: $scope.criteria.formType}, function(result) {
        $scope.criteria.numeral = result.shift().numeral;
        $scope.formState.freeForms = result.map(mapToFullCode);
      });
    }
    $scope.formTypeChanged = function() {
      QueryUtils.endpoint(baseUrl + '/diploma/students').query($scope.criteria, function(result) {
        DataUtils.convertStringToDates(result, ['birthdate']);
        $scope.formState.students = result;
        if (result.length > 0) {
          $scope.updatePdfUrl();
          querySigners();
          queryFreeForms();
        }
      });
    };
    $scope.selectAllStudents = function(value) {
      $scope.formState.students.forEach(function(student) {
        student.selected = value;
      });
      $scope.updatePdfUrl();
    };
    
    function getSelectedStudentIds() {
      return $scope.formState.students.filter(function(student) {
        return student.selected;
      }).map(function(student) {
        return student.id;
      });
    }
    function getPdfUrlParams(selectedStudentIds) {
      return 'directiveId=' + $scope.criteria.directiveId +
        '&formType=' + $scope.criteria.formType + '&studentIds=' + selectedStudentIds.join(',') +
        '&signer1Id=' + $scope.criteria.signer1Id + '&signer2Id=' + $scope.criteria.signer2Id;
    }
    $scope.updatePdfUrl = function() {
      var selectedStudentIds = getSelectedStudentIds();
      if (selectedStudentIds.length === 0 || !$scope.criteria.signer1Id || !$scope.criteria.signer2Id) {
        $scope.viewPdfUrl = undefined;
        return;
      }
      $scope.viewPdfUrl = config.apiUrl + baseUrl + '/diploma/print/view.pdf?' + getPdfUrlParams(selectedStudentIds);
    };

    function getPrintUrl() {
      return config.apiUrl + baseUrl + '/diploma/print.pdf?' + getPdfUrlParams(getSelectedStudentIds()) +
        '&numeral=' + $scope.criteria.numeral;
    }

    $scope.print = function() {
      $scope.criteria.studentIds = getSelectedStudentIds();
      QueryUtils.endpoint(baseUrl + '/diploma/calculate').query($scope.criteria, function(result) {
        $window.location = getPrintUrl();
        dialogService.showDialog('document/templates/diploma.confirm.dialog.html', function(dialogScope) {
          dialogScope.forms = result;
          $scope.formState.students.filter(function(student) {
            return student.selected;
          }).forEach(function(student, i) {
            dialogScope.forms[i].fullname = student.fullname;
          });
        }, function() {
          QueryUtils.endpoint(baseUrl + '/diploma/confirm').save({
            directiveId: $scope.criteria.directiveId,
            formType: $scope.criteria.formType,
            studentIds: $scope.criteria.studentIds,
            numerals: result.map(function(form) {
              return form.numeral;
            })
          }, function() {
            message.updateSuccess();
            $scope.formTypeChanged();
          }).$promise.catch(angular.noop);
        });
      }).$promise.catch(angular.noop);
    };
  }]).controller('SupplementSearchController', ['$scope', '$q', 'Classifier', 'QueryUtils', 'DataUtils', 'DocumentUtils',
  function ($scope, $q, Classifier, QueryUtils, DataUtils, DocumentUtils) {
    var baseUrl = '/documents';
    $scope.currentNavItem = 'document.supplements';
    
    var clMapper = Classifier.valuemapper({status: 'KASKKIRI_STAATUS', 
      diplomaStatus: 'LOPUDOK_STAATUS', supplementStatus: 'LOPUDOK_STAATUS'});
    $scope.formState = {
      directives: QueryUtils.endpoint(baseUrl + '/supplement/directives').query(function(result) {
        DataUtils.convertStringToDates(result, ['date']);
        $q.all(clMapper.promises).then(function() {
          clMapper.objectmapper(result);
          DocumentUtils.mapDirectiveDisplay(result);
        });
      })
    };
    $scope.autocomplete = {};
    QueryUtils.createQueryForm($scope, baseUrl + '/supplement/students', {order: 'p.lastname, p.firstname'}, clMapper.objectmapper);
    var _clearCriteria = $scope.clearCriteria;
    $scope.clearCriteria = function() {
      _clearCriteria();
      $scope.autocomplete = {};
    };

    $q.all(clMapper.promises).then($scope.loadData);

    $scope.$watch('autocomplete.student', function () {
      $scope.criteria.studentId = $scope.autocomplete.student ? $scope.autocomplete.student.id : null;
    });
    $scope.$watch('autocomplete.curriculumVersion', function () {
      $scope.criteria.curriculumVersionId = $scope.autocomplete.curriculumVersion ? $scope.autocomplete.curriculumVersion.id : null;
    });
  }]).controller('SupplementController', ['$scope', '$route', '$window', 'config', 'QueryUtils', 'DocumentUtils', 'dialogService', 'message',
  function ($scope, $route, $window, config, QueryUtils, DocumentUtils, dialogService, message) {
    var baseUrl = '/documents';
    var id = $route.current.params.id;

    $scope.criteria = {};
    $scope.formState = {
      signers: QueryUtils.endpoint(baseUrl + '/signers').query(function(result) {
        DocumentUtils.mapSignerDisplay(result);
      })
    };

    function mapToFullCode(form) {
      return form.fullCode;
    }
    $scope.record = QueryUtils.endpoint(baseUrl + '/supplement').get({id: id}, function(result) {
      $scope.criteria.numeral = result.freeForms.shift().numeral;
      $scope.criteria.additionalNumeral = result.freeExtraForms.shift().numeral;
      result.freeForms = result.freeForms.map(mapToFullCode);
      result.freeExtraForms = result.freeExtraForms.map(mapToFullCode);
    });
    
    $scope.updatePdfUrl = function() {
      if (!$scope.criteria.signer1Id) {
        $scope.viewPdfUrl = undefined;
        return;
      }
      $scope.viewPdfUrl = config.apiUrl + baseUrl + '/supplement/' + id + '/print/view.pdf?signer1Id=' + $scope.criteria.signer1Id;
    };

    function getPrintUrl() {
      return config.apiUrl + baseUrl + '/supplement/' + id + '/print.pdf?signer1Id=' + $scope.criteria.signer1Id +
        '&numeral=' + $scope.criteria.numeral + '&additionalNumeral=' + $scope.criteria.additionalNumeral;
    }

    $scope.print = function() {
      QueryUtils.endpoint(baseUrl + '/supplement/' + id + '/calculate').query($scope.criteria, function(result) {
        $window.location = getPrintUrl();
        dialogService.confirmDialog({
          prompt: 'document.confirm.print', 
          forms: result.map(mapToFullCode).join(', '),
          accept: 'document.confirm.yes',
          cancel: 'document.confirm.no'
        }, function() {
          QueryUtils.endpoint(baseUrl + '/supplement/' + id + '/confirm').save(result.map(function(form) {
            return form.id;
          }), function() {
            message.updateSuccess();
            $window.location.href = '#/documents/supplements';
          });
        });
      }).$promise.catch(angular.noop);
    };
  }]).factory('DocumentUtils', ['$rootScope', '$filter',
  function ($rootScope, $filter) {
    return {
      mapDirectiveDisplay: function(directives) {
        var hoisDateFilter = $filter('hoisDate');
        directives.forEach(function(directive) {
          directive.display = (directive.number || '-') + '/' + hoisDateFilter(directive.date) + 
            '/' + $rootScope.currentLanguageNameField(directive.status);
        });
      },
      mapSignerDisplay: function(signers) {
        signers.forEach(function(signer) {
          signer.display = signer.name + ', ' + signer.position;
        });
      }
    };
  }
]);
