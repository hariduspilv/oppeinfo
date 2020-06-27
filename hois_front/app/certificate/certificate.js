'use strict';

angular.module('hitsaOis')
.controller('CertificateSearchController', ['$scope', '$sessionStorage', 'Classifier', 'DataUtils', 'QueryUtils', '$q', '$route', 'CertificateType', 'CertificateUtil',
  function ($scope, $sessionStorage, Classifier, DataUtils, QueryUtils, $q, $route, CertificateType, CertificateUtil) {
    $scope.auth = $route.current.locals.auth;
    var clMapper = Classifier.valuemapper({type: 'TOEND_LIIK', status: 'TOEND_STAATUS'});
    QueryUtils.createQueryForm($scope, '/certificate', {order: '-c.inserted'}, clMapper.objectmapper);
    DataUtils.convertStringToDates($scope.criteria, ['insertedFrom', 'insertedThru']);

    if ($route.current.params.ordered) {
      $scope.criteria.status = 'TOEND_STAATUS_T';
    }

    if ($scope.auth.isStudent()) {
      // Do not show 'notFound' message
      $scope.afterLoadData = function (resultData) {
        $scope.tabledata.content = resultData.content;
        $scope.tabledata.totalElements = resultData.totalElements;
        clMapper.objectmapper(resultData.content);
      };

      //$scope.allCertificateTypes = Classifier.queryForDropdown({mainClassCode: 'TOEND_LIIK', filterValues: [CertificateType.TOEND_LIIK_MUU]});
      QueryUtils.endpoint('/certificate/student/status').get({id: $scope.auth.student}).$promise.then(function(response) {
          var forbiddenTypes = [];
          if ($scope.auth.isGuestStudent()) {
            forbiddenTypes = CertificateUtil.getGuestStudentForbiddenTypes();
          } else {
            forbiddenTypes = CertificateUtil.getForbiddenTypes(response.status);
          }
          Classifier.queryForDropdown({mainClassCode: 'TOEND_LIIK', filterValues: [CertificateType.TOEND_LIIK_MUU]}).$promise.then(function (allCertificateTypes) {
            $scope.certificateTypes = allCertificateTypes.filter(function(el){
              return forbiddenTypes.indexOf(el.code) === -1;
            });
          });
      }).catch(angular.noop);
    }
    $q.all(clMapper.promises).then($scope.loadData);
  }
]).controller('CertificateStudentOrderController', ['$scope', 'Classifier', 'QueryUtils', '$route', '$location', 'dialogService', 'message', '$rootScope', 'CertificateUtil', 'ArrayUtils',
  function ($scope, Classifier, QueryUtils, $route, $location, dialogService, message, $rootScope, CertificateUtil, ArrayUtils) {

    var baseUrl = '/certificate';
    var Endpoint = QueryUtils.endpoint(baseUrl + '/order');
    var id = $route.current.params.id;
    $scope.auth = $route.current.locals.auth;
    if ($scope.auth.isGuestStudent() && !ArrayUtils.includes(['TOEND_LIIK_OPI', 'TOEND_LIIK_SOOR'], $route.current.params.typeCode)) {
      message.error('main.messages.error.nopermission');
      $location.path('');
      return;
    }
    $scope.studentType = $scope.auth.type;
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
      var command = {
        type: $scope.record.type
      };
      if (CertificateUtil.isResultsCertificate($scope.record)) {
        command.addOutcomes = $scope.record.addOutcomes;
        command.showModules = $scope.record.showModules;
        command.showUncompleted = $scope.record.showUncompleted;
        command.estonian = $scope.record.estonian;
      }
      QueryUtils.endpoint(baseUrl + '/content').search(command).$promise.then(function(response) {
        $scope.record.content = response.content;
        setReadonlyContent($scope.record.content);
      }).catch(angular.noop);
    }

    if($scope.record.type) {
      if (CertificateUtil.isResultsCertificate($scope.record)) {
        $scope.record.estonian = true;
      } else {
        $scope.record.estonian = undefined;
      }
      loadContent();
    }

    $scope.reloadContent = function() {
      if ($scope.record.type) {
        loadContent();
      }
    };

    function ekisConfirm() {
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
    }

    $scope.save = function() {
      $scope.certificateEditForm.$setSubmitted();
      if(!$scope.certificateEditForm.$valid && !$scope.record.signatoryIdcode) {
        message.error('main.messages.form-has-errors');
        return;
      }

      if (!$route.current.locals.auth.school.withoutEkis && !$scope.record.whom) {
        dialogService.confirmDialog({prompt: 'certificate.emptyWhom'}, function() {
          ekisConfirm();
        });
      } else {
        ekisConfirm();
      }
    };
  }
]).controller('CertificateEditController', ['$location', '$timeout', '$q', '$rootScope', '$route', '$scope', 'Classifier', 'CertificateUtil', 'QueryUtils', 'config', 'dialogService', 'message', 'resourceErrorHandler',
  function ($location, $timeout, $q, $rootScope, $route, $scope, Classifier, CertificateUtil, QueryUtils, config, dialogService, message, resourceErrorHandler) {

    var baseUrl = '/certificate';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var OrderEndpoint = QueryUtils.endpoint(baseUrl + '/order');
    var CompleteEndpoit = QueryUtils.endpoint(baseUrl + '/complete');
    var id = $route.current.params.id;
    var changedByOtherIdCodeChange = false;
    $scope.formState = {};
    $scope.forbiddenTypes = [];
    $scope.auth = $route.current.locals.auth;

    Classifier.queryForDropdown({ mainClassCode: 'TOEND_LIIK' }, function (response) {
      $scope.typeMap = Classifier.toMap(response);
    });

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
          $scope.formState.student = {id: response.id, nameEt: name, nameEn: name};
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
      });
    } else {
      $scope.record = new Endpoint();
      afterLoad();
    }

    function getStudent() {
      var type = $scope.record.type;
      return QueryUtils.endpoint(baseUrl + '/otherStudent').search({id: $scope.record.student}).$promise.then(function(response) {
        // set data only if type hasn't changed while query was executed
        if (type === $scope.record.type) {
          $scope.record.otherName = response.fullname;
          $scope.record.otherIdcode = response.idcode;
          $scope.otherFound = true;
          $scope.studentType = response.type;
          $scope.isHigher = response.higher;
          $scope.record.showModules = response.higher && $scope.auth.school.hmodules;
          $scope.forbiddenTypes = CertificateUtil.getForbiddenTypes(response.status);
          return response;
        }
      }).catch(angular.noop);
    }

    $scope.signatories = QueryUtils.endpoint(baseUrl + '/signatories').query();

    function loadContent() {
      if($scope.record.id) {
        return;
      }
      var command = {
        type: $scope.record.type,
        student: $scope.record.student,
        otherIdcode: $scope.record.otherIdcode,
        otherName: $scope.record.otherName
      };
      if (!angular.equals(command, $scope.lastCommand)) {
        $scope.record.content = null;
      }
      if($scope.record.type && ($scope.record.student || $scope.record.otherIdcode && $scope.isOtherCertificate() && $scope.otherFound)) {
        if (CertificateUtil.isResultsCertificate($scope.record)) {
          command.addOutcomes = $scope.record.addOutcomes;
          command.showModules = $scope.record.showModules;
          command.showUncompleted = $scope.record.showUncompleted;
          command.estonian = $scope.record.estonian;
        }
        if (!angular.equals(command, $scope.lastCommand)) {
          QueryUtils.endpoint(baseUrl + '/content').search(command).$promise.then(function(response) {
            // set content only if type hasn't changed while query was executed
            if (command.type === $scope.record.type) {
              $scope.record.content = response.content;
              if(!$scope.contentEditable()) {
                setReadonlyContent($scope.record.content);
              }
            }
          }).catch(angular.noop);
        }
      }
      $scope.lastCommand = command;
    }

    $scope.$watch('record.type', function() {
      if (id !== undefined) { return; }
      if (CertificateUtil.isResultsCertificate($scope.record)) {
        $scope.record.estonian = true;
      } else {
        $scope.record.estonian = undefined;
      }
      $scope.record.showUncompleted = undefined;
      $scope.formState.student = undefined;
      $scope.record.student = undefined;
      $scope.record.content = undefined;
      $scope.record.otherName = undefined;
      $scope.record.otherIdcode = undefined;
      if(!$scope.contentEditable()) {
        setReadonlyContent("");
      }
    });

    $scope.$watch('record.student', function(){
      if(!$scope.record.student) {
        $scope.forbiddenTypes = [];
      } else {
        if (CertificateUtil.isResultsCertificate($scope.record)) {
          $scope.record.estonian = true;
        } else {
          $scope.record.estonian = undefined;
        }
        $scope.record.showUncompleted = undefined;
        setTypeName();
        loadContent();
      }
    });

    $scope.$watch('record.otherIdcode', function(){
      if($scope.record.otherIdcode) {
        loadContent();
      }
      if($scope.record && $scope.record.otherIdcode && $scope.certificateEditForm.idcode && $scope.certificateEditForm.idcode.$valid) {
        $scope.getNameByIdcode();
      }
    });

    $scope.$watch('record.otherName', function(){
      if($scope.record.otherName && $scope.record.otherIdcode) {
        loadContent();
      }
    });

    $scope.reloadContent = function() {
      if($scope.record.student) {
        setTypeName();
        loadContent();
      }
    };

    var lookup = QueryUtils.endpoint('/autocomplete/certificate/students');
    $scope.querySearch = function (text) {
      if(text.length >= 1) {
        var deferred = $q.defer();
        lookup.search(
          {
           showGuestStudent : (CertificateUtil.isResultsCertificate($scope.record) || CertificateUtil.isStudyingCertificate($scope.record)),
           onlyStudyingOrFinishedGuestStudent: (CertificateUtil.isResultsCertificate($scope.record) || CertificateUtil.isStudyingCertificate($scope.record)),
           showStudentGroup: true,
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
      if($scope.formState.student && $scope.record.student !== $scope.formState.student.id) {
        $scope.record.student = $scope.formState.student.id;
        getStudent();
      } else if(!$scope.formState.student) {
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
      if ($scope.certificateEditForm.certificateNr) {
        $scope.certificateEditForm.certificateNr.$setValidity("required", true);
        delete $scope.certificateEditForm.certificateNr.$validators.required;
      }

      if(!validationPassed()) {
        return;
      }
      if (!$route.current.locals.auth.school.withoutEkis && !$scope.record.whom && !$scope.record.id) {
        dialogService.confirmDialog({prompt: 'certificate.emptyWhom'}, function() {
          setSignatoryName();
          if($scope.record.id) {
            $scope.record.$update(afterLoad).then(message.updateSuccess).catch(angular.noop);
          } else {
            $scope.record.$save().then(afterCreation).catch(angular.noop);
          }
        });
      } else {
        setSignatoryName();
        if($scope.record.id) {
          $scope.record.$update(afterLoad).then(message.updateSuccess).catch(angular.noop);
        } else {
          $scope.record.$save().then(afterCreation).catch(angular.noop);
        }
      }
    };

    function displayFormError(response) {
      resourceErrorHandler.responseError(response, $scope.certificateEditForm).catch(angular.noop);
    }

    $scope.changedType = function () {
      setTypeName();
    };

    function setTypeName() {
      if ($scope.record.type) {
        var typeClassifier = $scope.typeMap[$scope.record.type];
        if (angular.isDefined($scope.record.estonian) && !$scope.record.estonian) {
          $scope.record.headline = typeClassifier.nameEn !== null ? typeClassifier.nameEn : typeClassifier.nameEt;
        } else {
          $scope.record.headline = typeClassifier.nameEt;
        }
      }
    }

    $scope.changedCertificateNr = function () {
      delete $scope.certificateEditForm.certificateNr.$validators.required;
    };

    $scope.printUrl = function () {
      return config.apiUrl + baseUrl + '/print/' + $scope.record.id + "/certificate.rtf?lang=" + $scope.currentLanguage().toUpperCase();
    };

    function complete() {
      $scope.certificateEditForm.$setSubmitted();
      dialogService.confirmDialog({prompt: $scope.certificateEditForm.$dirty ? 'certificate.operation.complete.messageSave' : 'certificate.operation.complete.message'}, function() {
        function toViewForm(record) {
          $location.url(baseUrl +'/' + record.id + '/view?_noback');
        }
        var record = new CompleteEndpoit($scope.record);
        record.$update2(toViewForm).then(function () {
          message.info('certificate.operation.complete.success');
        }).catch(displayFormError);
      });
    }

    $scope.complete = complete;

    function saveAndOrder() {
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
    }

    $scope.saveAndOrder = function() {
      if(!validationPassed()) {
        return;
      }
      if (!$route.current.locals.auth.school.withoutEkis && !$scope.record.whom && !$scope.record.id) {
        dialogService.confirmDialog({prompt: 'certificate.emptyWhom'}, function() {
          saveAndOrder();
        });
      } else {
        saveAndOrder();
      }
    };

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'certificate.deleteconfirm'}, function() {
        $scope.record.$delete().then(function() {
          message.info('main.messages.delete.success');
          $rootScope.back('#' + baseUrl + '?_noback');
        }).catch(angular.noop);
      });
    };

    // student's manual input

    $scope.otherFound = false;
    $scope.idcodePattern = '^[1-6][0-9]{2}[0-1][0-9][0-3][0-9][0-9]{4}';

    $scope.getNameByIdcode = function() {
      QueryUtils.endpoint(baseUrl + '/otherStudent', {get: {method: 'GET'}})
      .get({idcode: $scope.record.otherIdcode, hideGuestStudents: true}).$promise.then(function(result) {
        if(result.id) {
          $scope.record.student = result.id;
          $scope.record.otherName = result.fullname;
          $scope.otherFound = true;
          $scope.formState.student = {id: result.id, nameEt: result.fullname, nameEn: result.fullname, status: result.status};
          $scope.isHigher = result.higher;
          $scope.record.showModules = result.higher && $scope.auth.school.hmodules;
          $scope.forbiddenTypes = CertificateUtil.getForbiddenTypes(result.status);
        } else {
          changedByOtherIdCodeChange = true;
          $scope.formState.student = null;
          $scope.isHigher = null;
          $scope.record.showModules = null;
          $scope.forbiddenTypes = [];
          if(result && result.fullname) {
            $scope.record.otherName = result.fullname;
            $scope.otherFound = true;
          } else {
            $scope.otherFound = false;
            $scope.record.student = null;
          }
        }
        loadContent();
      }).catch(function(error) {
        if (error.data) {
          $scope.record.otherIdcode = undefined;
          message.error(error.data._errors[0].code);
        }
      });
    };
  }
]).controller('CertificateViewController', ['$location', '$route', '$scope', 'dialogService', 'ekisService', 'message', 'CertificateUtil', 'QueryUtils', 'config',
  function ($location, $route, $scope, dialogService, ekisService, message, CertificateUtil, QueryUtils, config) {
    var baseUrl = '/certificate';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var id = $route.current.params.id;
    $scope.auth = $route.current.locals.auth;

    $scope.getCertificateUrl = ekisService.getCertificateUrl;

    $scope.isOtherCertificate = function(){
      return CertificateUtil.isOtherCertificate($scope.record);
    };

    $scope.printUrl = function () {
      return config.apiUrl + baseUrl + '/print/' + $scope.record.id + "/certificate.rtf?lang=" + $scope.currentLanguage().toUpperCase();
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
    isResultsCertificate: function(record) {
      return record && record.type === CertificateType.TOEND_LIIK_SOOR;
    },
    isStudyingCertificate: function(record) {
      return record && record.type === CertificateType.TOEND_LIIK_OPI;
    },
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
             CertificateType.TOEND_LIIK_SESS === certificateTypeCode ||
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
    },
    getGuestStudentForbiddenTypes : function() {
      return [CertificateType.TOEND_LIIK_MUU, CertificateType.TOEND_LIIK_KONTAKT,
        CertificateType.TOEND_LIIK_LOPET, CertificateType.TOEND_LIIK_SESS];
    }
  };
});
