'use strict';

angular.module('hitsaOis')
.controller('CertificateSearchController', ['$scope', '$sessionStorage', 'Classifier', 'DataUtils', 'QueryUtils', '$q', '$route', 'CertificateType', 'CertificateUtil',
  function ($scope, $sessionStorage, Classifier, DataUtils, QueryUtils, $q, $route, CertificateType, CertificateUtil) {
    $scope.auth = $route.current.locals.auth;
    var clMapper = Classifier.valuemapper({type: 'TOEND_LIIK', status: 'TOEND_STAATUS'});
    QueryUtils.createQueryForm($scope, '/certificate', {order: 'type.' + $scope.currentLanguageNameField()}, clMapper.objectmapper);
    DataUtils.convertStringToDates($scope.criteria, ['insertedFrom', 'insertedThru']);
    $q.all(clMapper.promises).then($scope.loadData);

    if($scope.auth.isStudent()) {
      $scope.types = Classifier.queryForDropdown({mainClassCode: 'TOEND_LIIK', filterValues: [CertificateType.TOEND_LIIK_MUU]}, function() {
       // TODO use cheaper backend call to fetch student status only
        QueryUtils.endpoint('/students').get({id: $scope.auth.student}).$promise.then(function(response) {
          var status = response.status;
          var forbiddenTypes = CertificateUtil.getForbiddenTypes(status);
          $scope.types = $scope.types.filter(function(el){
            return forbiddenTypes.indexOf(el.code) === -1;
          });
        });
      });
    }
  }
]).controller('CertificateStudentOrderController', ['$scope', 'Classifier', 'QueryUtils', '$route', '$location', 'message',
  function ($scope, Classifier, QueryUtils, $route, $location, message) {

    var baseUrl = '/certificate';
    var Endpoint = QueryUtils.endpoint(baseUrl);

    $scope.record = new Endpoint({type: $route.current.params.typeCode});

    Classifier.get($scope.record.type).$promise.then(function(response){
      $scope.record.headline = response.nameEt;
    });

    $scope.save = function() {
      $scope.certificateEditForm.$setSubmitted();
      if(!$scope.certificateEditForm.$valid && !$scope.record.signatoryIdcode) {
        message.error('main.messages.form-has-errors');
        return;
      }
      $scope.record.$save().then(function() {
        message.info('main.messages.create.success');
        $location.path(baseUrl +'/' + $scope.record.id + '/view');
      });
    };
  }
]).controller('CertificateEditController', ['$scope', 'QueryUtils', '$route', '$location', 'dialogService', 'message', '$resource', 'config', '$q', 'CertificateUtil',
  function ($scope, QueryUtils, $route, $location, dialogService, message, $resource, config, $q, CertificateUtil) {

    var baseUrl = '/certificate';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var OrderEndpoint = QueryUtils.endpoint(baseUrl + '/order');
    var id = $route.current.params.id;
    var changedByOtherIdCodeChange = false;
    $scope.forbiddenTypes = [];

    function getFullname(person) {
      return person.firstname + ' ' + person.lastname;
    }

    $scope.contentEditable = function() {
      return CertificateUtil.contentEditable($scope.record);
    };

    $scope.isOtherCertificate = function(){
      return CertificateUtil.isOtherCertificate($scope.record);
    };

    function afterLoad() {
      if($scope.record.student) {
        getStudent().then(function(response){
          var name = getFullname(response.person);
          $scope.student = {id: response.id, nameEt: name, nameEn: name};
        });
      }
      if(!$scope.contentEditable()) {
        setReadonlyContent($scope.record.content);
      }
      $scope.otherFound = true;
    }

    function setReadonlyContent(content) {
      var el = document.getElementById('content');
      if(el) {
        el.innerHTML = content;
      }
    }

    if(id) {
      $scope.record = Endpoint.get({id: id}, afterLoad);
    } else {
      $scope.record = new Endpoint();
      afterLoad();
    }

    function getStudent() {
      return QueryUtils.endpoint('/students').get({id: $scope.record.student}).$promise.then(function(response) {
         var name = getFullname(response.person);
         $scope.record.otherName = name;
         $scope.record.otherIdcode = response.person.idcode;
         $scope.otherFound = true;

         $scope.forbiddenTypes = CertificateUtil.getForbiddenTypes(response.status);

         return response;
      });
    }

    $scope.signatories = QueryUtils.endpoint(baseUrl + "/signatories").query();

    function loadContent() {
      if($scope.record.id) {
        return;
      }
      $scope.record.content = null;
      if($scope.record.student && $scope.record.type && !$scope.isOtherCertificate()) {
        QueryUtils.endpoint(baseUrl + "/content").search(
          {student: $scope.record.student, type: $scope.record.type}
        ).$promise.then(function(response) {
          $scope.record.content = response.content;
          if(!$scope.contentEditable()) {
            setReadonlyContent($scope.record.content);
          }
        });
      }
    }

    $scope.$watch('record.type', loadContent);
    $scope.$watch('record.student', function(){
      if(!$scope.record.student) {
        $scope.forbiddenTypes = [];
      }
      loadContent();
    });

    $scope.querySearch = function (text) {
      if(text.length >= 3) {
        var deferred = $q.defer();
        var lookup = QueryUtils.endpoint('/autocomplete/students');
        lookup.search(
          {
           name: text,
           active: CertificateUtil.onlyActiveStudents($scope.record.type),
           finished: CertificateUtil.onlyFinishedStudents($scope.record.type)
          }, function (data) {
          deferred.resolve(data.content);
        });
        return deferred.promise;
      } else {
        return {};
      }
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

    function afterCreation(respose) {
      message.info('main.messages.create.success');
      $location.path(baseUrl + '/' + respose.id + '/edit');
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
        $scope.record.$update(afterLoad).then(message.updateSuccess);
      } else {
        $scope.record.$save().then(afterCreation);
      }
    };

    $scope.saveAndOrder = function() {
      if(!validationPassed()) {
        return;
      }
      setSignatoryName();
      var record = new OrderEndpoint($scope.record);
      if($scope.record.id) {
        record.$update(afterLoad).then(message.updateSuccess);
      } else {
        record.$save().then(afterCreation);
      }
    };

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'certificate.deleteconfirm'}, function() {
        $scope.record.$delete().then(function() {
          message.info('main.messages.delete.success');
          $location.path(baseUrl);
        });
      });
    };

    // student's manual input

    $scope.otherFound = false;

    function clearStudent() {
      $scope.student = null;
      $scope.forbiddenTypes = [];
    }

    $scope.clearOther = function() {
      clearStudent();
      $scope.otherFound = false;
      $scope.record.otherName = null;
      $scope.record.otherIdcode = null;
    };

    $scope.$watch('record.otherIdcode', function() {
      if($scope.record && $scope.record.otherIdcode && $scope.certificateEditForm.idcode && $scope.certificateEditForm.idcode.$valid) {
        $scope.getNameByIdcode();
      }
    });
    $scope.idcodePattern = '^[1-6][0-9]{2}[0-1][0-9][0-3][0-9][0-9]{4}';

    $scope.getNameByIdcode = function() {
      $resource(config.apiUrl + baseUrl + '/otherStudent').get({idcode: $scope.record.otherIdcode})
        .$promise.then(function(result) {
          if(result.id) {
            $scope.record.student = result.id;
            $scope.record.otherName = result.fullname;
            $scope.otherFound = true;
            $scope.student = {id: result.id, nameEt: result.fullname, nameEn: result.fullname, status: result.status};
            $scope.forbiddenTypes = CertificateUtil.getForbiddenTypes(result.status);
          } else if(result.fullname) {
            clearStudent();
            $scope.record.otherName = result.fullname;
            $scope.otherFound = true;
            changedByOtherIdCodeChange = true;
          } else {
            changedByOtherIdCodeChange = true;
            clearStudent();
            $scope.otherFound = false;
          }
      });
    };
  }
]).controller('CertificateViewController', ['$scope', 'QueryUtils', '$route', 'ekisService', 'CertificateUtil',
  function ($scope, QueryUtils, $route, ekisService, CertificateUtil) {
    var baseUrl = '/certificate';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var id = $route.current.params.id;
    $scope.auth = $route.current.locals.auth;

    $scope.getCertificateUrl = ekisService.getCertificateUrl;

    function getFullname(person) {
      return person.firstname + ' ' + person.lastname;
    }

    function afterLoad() {
      if($scope.record.student) {
        getStudent().then(function(response){
          var name = getFullname(response.person);
          $scope.student = {id: response.id, nameEt: name, nameEn: name};
        });
      }
      var el = document.getElementById('content');
      if(el) {
        el.innerHTML = $scope.record.content;
      }
    }

    function getStudent() {
      return QueryUtils.endpoint('/students').get({id: $scope.record.student}).$promise.then(function(response) {
         var name = getFullname(response.person);
         $scope.record.otherName = name;
         $scope.record.otherIdcode = response.person.idcode;
         return response;
      });
    }

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
