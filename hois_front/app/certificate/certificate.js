'use strict';

angular.module('hitsaOis').controller('CertificateSearchController', ['$scope', '$sessionStorage', 'Classifier', 'DataUtils', 'QueryUtils', '$q', '$route',
  function ($scope, $sessionStorage, Classifier, DataUtils, QueryUtils, $q, $route) {
    $scope.auth = $route.current.locals.auth;
    var clMapper = Classifier.valuemapper({type: 'TOEND_LIIK'});
    QueryUtils.createQueryForm($scope, '/certificate', {order: 'type.' + $scope.currentLanguageNameField()}, clMapper.objectmapper);
    DataUtils.convertStringToDates($scope.criteria, ['insertedFrom', 'insertedThru']);
    $q.all(clMapper.promises).then($scope.loadData);

    if($scope.auth.isStudent()) {
      Classifier.queryForDropdown({mainClassCode: 'TOEND_LIIK'}, function(result) {
        $scope.types = result.filter(function(t) {return t.code !== 'TOEND_LIIK_MUU';});
      });
    }
  }
]).controller('CertificateStudentOrderController', ['$scope', 'Classifier', 'QueryUtils', '$route', '$location', 'message',
  function ($scope, Classifier, QueryUtils, $route, $location, message) {

    var baseUrl = '/certificate';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var id = $route.current.params.id;

    if(id) {
      $scope.record = Endpoint.get({id: id});
    } else {
      $scope.record = new Endpoint({status: 'TOEND_STAATUS_T', content: 'sisu...', type: $route.current.params.typeCode});

      Classifier.queryForDropdown({mainClassCode: 'TOEND_LIIK'}, function(result) {
        var c = result.filter(function(t) {return t.code === $scope.record.type;});
        if(c.length > 0) {
          $scope.record.headline = c[0].nameEt;
        }
      });

      QueryUtils.endpoint('/directives/coordinators').get().$promise.then(function(response) {
        var signatories = response.content.filter(function(s) {return s.isCertificateDefault;});
        if(signatories.length > 0) {
          $scope.record.signatoryIdcode = signatories[0].idcode;
          $scope.record.signatoryName = signatories[0].name;
        } else {
          message.error('certificate.signatoriesMissing');
        }
      });
    }

    QueryUtils.endpoint('/autocomplete/students').get().$promise.then(function(response) {
      if(response.content && response.content.length > 0) {
        $scope.student = response.content[0];
      }
    });

    $scope.save = function() {
      $scope.certificateEditForm.$setSubmitted();
      if(!$scope.certificateEditForm.$valid && !$scope.record.signatoryIdcode) {
        message.error('main.messages.form-has-errors');
        return;
      }
      if($scope.record.id) {
        $scope.record.$update().then(message.updateSuccess);
      }else{
        $scope.record.$save().then(function() {
          message.info('main.messages.create.success');
          $location.path(baseUrl + '/' + $scope.record.type + '/' + $scope.record.id + '/edit');
        });
      }
    };
  }
]).controller('CertificateEditController', ['$scope', 'QueryUtils', '$route', '$location', 'dialogService', 'message', '$resource', 'config', '$q',
  function ($scope, QueryUtils, $route, $location, dialogService, message, $resource, config, $q) {

    var baseUrl = '/certificate';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var id = $route.current.params.id;
    var changedByOtherIdCodeChange = false;

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
      $scope.otherFound = true;
    }

    if(id) {
      $scope.record = Endpoint.get({id: id}, afterLoad);
    } else {
      $scope.record = new Endpoint({status: 'TOEND_STAATUS_T', content: 'sisu...'});
      afterLoad();
    }

    function getStudent() {
      return QueryUtils.endpoint('/students').get({id: $scope.record.student}).$promise.then(function(response) {
         var name = getFullname(response.person);
         $scope.record.otherName = name;
         $scope.record.otherIdcode = response.person.idcode;
         $scope.otherFound = true;
         return response;
      });
    }

    $scope.signatories = [];
    QueryUtils.endpoint("/directives/coordinators").search().$promise.then(function(response) {
      $scope.signatories = response.content.filter(function(it) { return it.isCertificate;});
    });

    $scope.querySearch = function (text) {
      if(text.length >= 3) {
        var deferred = $q.defer();
        var lookup = QueryUtils.endpoint('/autocomplete/students');
        lookup.search({name: text}, function (data) {
          deferred.resolve(data.content);
        });
        return deferred.promise;
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

    $scope.save = function() {
      $scope.certificateEditForm.$setSubmitted();
      if(!$scope.certificateEditForm.$valid) {
        message.error('main.messages.form-has-errors');
        return;
      }
      $scope.record.signatoryName = $scope.signatories.filter(function(e){return e.idcode === $scope.record.signatoryIdcode;})[0].name;
      if($scope.record.student) {
        $scope.record.otherName = null;
        $scope.record.otherIdcode = null;
      }
      if($scope.record.id) {
        $scope.record.$update(afterLoad).then(message.updateSuccess);
      } else {
        $scope.record.$save().then(function() {
          message.info('main.messages.create.success');
          $location.path(baseUrl + '/' + $scope.record.id + '/edit');
        });
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

    $scope.clearOther = function() {
      $scope.student = null;
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
            $scope.student = {id: result.id, nameEt: result.fullname, nameEn: result.fullname};
          } else if(result.fullname) {
            $scope.student = null;
            $scope.record.otherName = result.fullname;
            $scope.otherFound = true;
            changedByOtherIdCodeChange = true;
          } else {
            changedByOtherIdCodeChange = true;
            $scope.student = null;
            $scope.otherFound = false;
          }
      });
    };
  }
]).controller('CertificateViewController', ['$scope', 'QueryUtils', '$route',
  function ($scope, QueryUtils, $route) {
    var baseUrl = '/certificate';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var id = $route.current.params.id;

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
      $scope.otherFound = true;
    }

    function getStudent() {
      return QueryUtils.endpoint('/students').get({id: $scope.record.student}).$promise.then(function(response) {
         var name = getFullname(response.person);
         $scope.record.otherName = name;
         $scope.record.otherIdcode = response.person.idcode;
         $scope.otherFound = true;
         return response;
      });
    }

    $scope.record = Endpoint.get({id: id}, afterLoad);
  }
]);
