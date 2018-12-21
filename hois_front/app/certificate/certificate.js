'use strict';

angular.module('hitsaOis')
.controller('CertificateSearchController', ['$scope', '$sessionStorage', 'Classifier', 'DataUtils', 'QueryUtils', '$q', '$route', 'CertificateType', 'CertificateUtil',
  function ($scope, $sessionStorage, Classifier, DataUtils, QueryUtils, $q, $route, CertificateType, CertificateUtil) {
    $scope.auth = $route.current.locals.auth;
    var clMapper = Classifier.valuemapper({type: 'TOEND_LIIK', status: 'TOEND_STAATUS'});
    QueryUtils.createQueryForm($scope, '/certificate', {order: 'type.' + $scope.currentLanguageNameField()}, clMapper.objectmapper);
    DataUtils.convertStringToDates($scope.criteria, ['insertedFrom', 'insertedThru']);

    if ($scope.auth.isStudent()) {
      // Do not show 'notFound' message
      $scope.afterLoadData = function (resultData) {
        $scope.tabledata.content = resultData.content;
        $scope.tabledata.totalElements = resultData.totalElements;
        clMapper.objectmapper(resultData.content);
      };

      $scope.certificateTypes = Classifier.queryForDropdown({mainClassCode: 'TOEND_LIIK', filterValues: [CertificateType.TOEND_LIIK_MUU]});
      QueryUtils.endpoint('/certificate/student/status').get({id: $scope.auth.student}).$promise.then(function(response) {
        $scope.certificateTypes.$promise.then(function() {
          var forbiddenTypes = CertificateUtil.getForbiddenTypes(response.status);
          $scope.certificateTypes = $scope.certificateTypes.filter(function(el){
            return forbiddenTypes.indexOf(el.code) === -1;
          });
        });
      }).catch(angular.noop);
    }
    $q.all(clMapper.promises).then($scope.loadData);
  }
]).controller('CertificateStudentOrderController', ['$scope', 'Classifier', 'QueryUtils', '$route', '$location', 'dialogService', 'message', '$rootScope',
  function ($scope, Classifier, QueryUtils, $route, $location, dialogService, message, $rootScope) {

    var baseUrl = '/certificate';
    var Endpoint = QueryUtils.endpoint(baseUrl + '/order');
    var id = $route.current.params.id;

    $rootScope.removeLastUrlFromHistory(function(lastUrl){
      return lastUrl && (lastUrl.indexOf('certificate/' + id + '/view') !== -1 || lastUrl.indexOf('certificate/new') !== -1);
    });

    $scope.record = new Endpoint({type: $route.current.params.typeCode});

    Classifier.queryForDropdown({mainClassCode: 'TOEND_LIIK'}).$promise.then(function(response) {
      var type = $scope.record.type;
      var cl = response.find(function(it) { return it.code === type; });
      $scope.record.headline = cl ? cl.nameEt : '';
    });

    function setReadonlyContent(content) {
      var el = document.getElementById('content');
      if(el) {
        el.innerHTML = content;
      }
    }

    function loadContent() {
      QueryUtils.endpoint(baseUrl + '/content').search({
        type: $scope.record.type,
        addOutcomes: $scope.record.addOutcomes
      }).$promise.then(function(response) {
        $scope.record.content = response.content;
        setReadonlyContent($scope.record.content);
      }).catch(angular.noop);
    }

    if($scope.record.type) {
      loadContent();
    }

    $scope.addOutcomesChanged = function() {
      if($scope.record.type) {
        loadContent();
      }
    };

    $scope.save = function() {
      $scope.certificateEditForm.$setSubmitted();
      if(!$scope.certificateEditForm.$valid && !$scope.record.signatoryIdcode) {
        message.error('main.messages.form-has-errors');
        return;
      }

      dialogService.confirmDialog({prompt: 'certificate.ekisconfirm'}, function() {
        $scope.record.$save().then(function() {
          message.info('main.messages.create.success');
          $location.url(baseUrl +'/' + $scope.record.id + '/view?_noback');
        }).catch(function(response) {
          if(response && response.data && response.data.data && response.data.data.id) {
            $location.url(baseUrl +'/' + response.data.data.id + '/view?_noback');
          }
        });
      });
    };
  }
]).controller('CertificateEditController', ['$scope', 'QueryUtils', '$route', '$location', 'dialogService', 'message', '$q', 'CertificateUtil', '$rootScope', '$timeout',
  function ($scope, QueryUtils, $route, $location, dialogService, message, $q, CertificateUtil, $rootScope, $timeout) {

    var baseUrl = '/certificate';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var OrderEndpoint = QueryUtils.endpoint(baseUrl + '/order');
    var id = $route.current.params.id;
    var changedByOtherIdCodeChange = false;
    $scope.forbiddenTypes = [];

    $rootScope.removeLastUrlFromHistory(function(lastUrl){
      return lastUrl && (lastUrl.indexOf('certificate/' + id + '/view') !== -1 || lastUrl.indexOf('certificate/new') !== -1);
    });

    $scope.contentEditable = function() {
      return CertificateUtil.contentEditable($scope.record);
    };

    $scope.isOtherCertificate = function(){
      return CertificateUtil.isOtherCertificate($scope.record);
    };

    function afterLoad() {
      if($scope.record.student) {
        getStudent().then(function(response){
          var name = response.fullname;
          $scope.student = {id: response.id, nameEt: name, nameEn: name};
        });
      }
      if(!$scope.contentEditable()) {
        setReadonlyContent($scope.record.content);
      }
      $scope.otherFound = true;
      if($scope.certificateEditForm) {
        $scope.certificateEditForm.$setPristine();
      }
    }

    function setReadonlyContent(content) {
      var el = document.getElementById('content');
      if(el) {
        el.innerHTML = content;
      }
    }

    if(id) {
      $scope.record = Endpoint.get({id: id}, afterLoad);
      $scope.record.$promise.then(function (response) {
        if (response.canBeChanged === false) {
          message.error('main.messages.error.nopermission');
          $scope.back("#/");
        }
      })
    } else {
      $scope.record = new Endpoint();
      afterLoad();
    }

    function getStudent() {
      return QueryUtils.endpoint(baseUrl + '/otherStudent').search({id: $scope.record.student}).$promise.then(function(response) {
         $scope.record.otherName = response.fullname;
         $scope.record.otherIdcode = response.idcode;
         $scope.otherFound = true;
         $scope.forbiddenTypes = CertificateUtil.getForbiddenTypes(response.status);
         return response;
      }).catch(angular.noop);
    }

    $scope.signatories = QueryUtils.endpoint(baseUrl + '/signatories').query();

    function loadContent() {
      if($scope.record.id) {
        return;
      }
      $scope.record.content = null;
      if($scope.record.type && ($scope.record.student || $scope.record.otherIdcode && $scope.isOtherCertificate())) {
        QueryUtils.endpoint(baseUrl + '/content').search(
          {
            student: $scope.record.student,
            type: $scope.record.type,
            otherIdcode: $scope.record.otherIdcode,
            otherName: $scope.record.otherName,
            addOutcomes: $scope.record.addOutcomes
          }
        ).$promise.then(function(response) {
          $scope.record.content = response.content;
          if(!$scope.contentEditable()) {
            setReadonlyContent($scope.record.content);
          }
        }).catch(angular.noop);
      }
    }

    $scope.$watch('record.type', loadContent);
    $scope.$watch('record.student', function(){
      if(!$scope.record.student) {
        $scope.forbiddenTypes = [];
      }
      loadContent();
    });


    $scope.$watch('record.otherIdcode', function(){
      if($scope.record.otherIdcode) {
        $timeout(loadContent, 1000);
      }
      if($scope.record && $scope.record.otherIdcode && $scope.certificateEditForm.idcode && $scope.certificateEditForm.idcode.$valid) {
        $scope.getNameByIdcode();
      }
    });

    $scope.$watch('record.otherName', function(){
      if($scope.record.otherName && $scope.record.otherIdcode) {
        $timeout(loadContent, 1000);
      }
    });

    $scope.addOutcomesChanged = function() {
      if($scope.record.student) {
        $timeout(loadContent, 1000);
      }
    };

    var lookup = QueryUtils.endpoint('/autocomplete/students');
    $scope.querySearch = function (text) {
      if(text.length >= 3) {
        var deferred = $q.defer();
        lookup.search(
          {
           name: text,
           active: CertificateUtil.onlyActiveStudents($scope.record.type),
           finished: CertificateUtil.onlyFinishedStudents($scope.record.type)
          }, function (data) {
          deferred.resolve(data.content);
        });
        return deferred.promise;
      }
      return {};
    };

    $scope.studentChanged = function() {
      if($scope.student && $scope.record.student !== $scope.student.id) {
        $scope.record.student = $scope.student.id;
        getStudent();
      } else if(!$scope.student) {
        $scope.record.student = null;
        $scope.otherFound = false;
        if(!changedByOtherIdCodeChange) {
          $scope.record.otherName = null;
          $scope.record.otherIdcode = null;
        }
        changedByOtherIdCodeChange = false;
      }
    };

    function validationPassed() {
      $scope.certificateEditForm.$setSubmitted();
      if(!$scope.certificateEditForm.$valid) {
        message.error('main.messages.form-has-errors');
        return false;
      }
      return true;
    }

    function afterCreation(response) {
      message.info('main.messages.create.success');
      $location.url(baseUrl + '/' + response.id + '/edit?_noback');
    }

    function setSignatoryName() {
      $scope.record.signatoryName = $scope.signatories.filter(function(e){return e.idcode === $scope.record.signatoryIdcode;})[0].name;
    }

    $scope.save = function() {
      if(!validationPassed()) {
        return;
      }
      setSignatoryName();
      if($scope.record.id) {
        $scope.record.$update(afterLoad).then(message.updateSuccess).catch(angular.noop);
      } else {
        $scope.record.$save().then(afterCreation).catch(angular.noop);
      }
    };

    $scope.saveAndOrder = function() {
      if(!validationPassed()) {
        return;
      }
      dialogService.confirmDialog({prompt: 'certificate.ekisconfirm'}, function() {
        setSignatoryName();
        function toViewForm(record) {
          $location.url(baseUrl +'/' + record.id + '/view?_noback');
        }
        var record = new OrderEndpoint($scope.record);
        if($scope.record.id) {
          record.$update(toViewForm).then(message.updateSuccess).catch(angular.noop);
        } else {
          record.$save(toViewForm).then(function() {
            message.info('main.messages.create.success');
          }).catch(function(response) {
            if(response && response.data && response.data.data && response.data.data.id) {
              $location.url(baseUrl +'/' + response.data.data.id + '/edit?_noback');
            }
          });
        }
      });
    };

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'certificate.deleteconfirm'}, function() {
        $scope.record.$delete().then(function() {
          message.info('main.messages.delete.success');
          $rootScope.back(baseUrl + '?_noback');
        }).catch(angular.noop);
      });
    };

    // student's manual input

    $scope.otherFound = false;
    $scope.idcodePattern = '^[1-6][0-9]{2}[0-1][0-9][0-3][0-9][0-9]{4}';

    $scope.getNameByIdcode = function() {
      QueryUtils.endpoint(baseUrl + '/otherStudent', {get: {method: 'GET'}}).get({idcode: $scope.record.otherIdcode}).$promise.then(function(result) {
        if(result.id) {
          $scope.record.student = result.id;
          $scope.record.otherName = result.fullname;
          $scope.otherFound = true;
          $scope.student = {id: result.id, nameEt: result.fullname, nameEn: result.fullname, status: result.status};
          $scope.forbiddenTypes = CertificateUtil.getForbiddenTypes(result.status);
        } else {
          changedByOtherIdCodeChange = true;
          $scope.student = null;
          $scope.forbiddenTypes = [];
          if(result && result.fullname) {
            $scope.record.otherName = result.fullname;
            $scope.otherFound = true;
          } else {
            $scope.otherFound = false;
          }
        }
      });
    };
  }
]).controller('CertificateViewController', ['$location', '$route', '$scope', 'dialogService', 'ekisService', 'message', 'CertificateUtil', 'QueryUtils',
  function ($location, $route, $scope, dialogService, ekisService, message, CertificateUtil, QueryUtils) {
    var baseUrl = '/certificate';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var id = $route.current.params.id;
    $scope.auth = $route.current.locals.auth;

    $scope.getCertificateUrl = ekisService.getCertificateUrl;

    $scope.isOtherCertificate = function(){
      return CertificateUtil.isOtherCertificate($scope.record);
    };

    function afterLoad() {
      if($scope.record.student) {
        QueryUtils.endpoint(baseUrl + '/otherStudent').search({id: $scope.record.student}).$promise.then(function(response) {
          var name = response.fullname;
          $scope.record.otherName = name;
          $scope.record.otherIdcode = response.idcode;
          $scope.student = {id: response.id, nameEt: name, nameEn: name};
        });
      }
      var el = document.getElementById('content');
      if(el) {
        el.innerHTML = $scope.record.content;
      }
    }

    $scope.order = function() {
      dialogService.confirmDialog({prompt: 'certificate.ekisconfirm'}, function() {
        QueryUtils.endpoint(baseUrl + '/orderFromEkis').update({id: id}).$promise.then(function() {
          $route.reload();
        }).catch(angular.noop);
      });
    };

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'certificate.deleteconfirm'}, function() {
        $scope.record.$delete().then(function() {
          message.info('main.messages.delete.success');
          $scope.back('#' + baseUrl + '?_noback');
        });
      });
    };

    $scope.contentEditable = function() {
      return CertificateUtil.contentEditable($scope.record);
    };

    $scope.record = Endpoint.get({id: id}, afterLoad);
  }
]).constant('CertificateType', {
  'TOEND_LIIK_SOOR': 'TOEND_LIIK_SOOR',
  'TOEND_LIIK_OPI' : 'TOEND_LIIK_OPI',
  'TOEND_LIIK_SESS': 'TOEND_LIIK_SESS',
  'TOEND_LIIK_KONTAKT': 'TOEND_LIIK_KONTAKT',
  'TOEND_LIIK_LOPET': 'TOEND_LIIK_LOPET',
  'TOEND_LIIK_MUU': 'TOEND_LIIK_MUU'

}).factory('CertificateUtil', function (CertificateType, StudentUtil) {

  return {
    isOtherCertificate: function(record) {
      return record && record.type === CertificateType.TOEND_LIIK_MUU;
    },
    contentEditable: function(record) {
      return record && (
      record.type === CertificateType.TOEND_LIIK_MUU ||
      record.type === CertificateType.TOEND_LIIK_KONTAKT ||
      record.type === CertificateType.TOEND_LIIK_SESS);
    },
    onlyActiveStudents: function(certificateTypeCode) {
      return CertificateType.TOEND_LIIK_KONTAKT === certificateTypeCode ||
             CertificateType.TOEND_LIIK_OPI === certificateTypeCode;
    },
    onlyFinishedStudents: function(certificateTypeCode) {
      return CertificateType.TOEND_LIIK_LOPET === certificateTypeCode;
    },
    getForbiddenTypes: function(studentStatus) {
      if(StudentUtil.isActive(studentStatus)) {
        return [CertificateType.TOEND_LIIK_LOPET];
      } else if(StudentUtil.hasFinished(studentStatus)) {
        return [CertificateType.TOEND_LIIK_OPI, CertificateType.TOEND_LIIK_KONTAKT];
      } else {
        return [CertificateType.TOEND_LIIK_OPI, CertificateType.TOEND_LIIK_KONTAKT, CertificateType.TOEND_LIIK_LOPET];
      }
    }
  };
});
