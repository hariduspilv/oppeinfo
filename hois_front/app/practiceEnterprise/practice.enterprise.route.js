'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/practice/enterprise', {
      templateUrl: 'practiceEnterprise/practice.enterprise.list.html',
      controller: 'PracticeEnterpriseListController',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_ETTEVOTE]
      },
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); } ,
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      }
    })
    .when('/practice/enterprise/new', {
      templateUrl: 'practiceEnterprise/practice.enterprise.edit.html',
      controller: 'PracticeEnterpriseEditController',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_ETTEVOTE]
      },
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); } 
      }
    })
    .when('/practice/enterprise/:id/view', {
      templateUrl: 'practiceEnterprise/practice.enterprise.view.html',
      controller: 'PracticeEnterpriseViewController',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_ETTEVOTE]
      },
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      }
    })
    .when('/practice/enterprise/:id/edit', {
      templateUrl: 'practiceEnterprise/practice.enterprise.edit.html',
      controller: 'PracticeEnterpriseEditController',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_ETTEVOTE]
      },
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); } 
      }
    })
    .when('/practice/enterprise/:id/:enterpriseSchoolId/persons/view', {
      templateUrl: 'practiceEnterprise/practice.enterprise.persons.view.html',
      controller: 'PracticeEnterprisePersonsEditController',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_ETTEVOTE]
      },
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      }
    })
    .when('/practice/enterprise/:id/:enterpriseSchoolId/persons/edit', {
      templateUrl: 'practiceEnterprise/practice.enterprise.persons.edit.html',
      controller: 'PracticeEnterprisePersonsEditController',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_ETTEVOTE]
      },
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      }
    })
    .when('/practice/enterprise/:id/:enterpriseSchoolId/locations/edit', {
      templateUrl: 'practiceEnterprise/practice.enterprise.locations.edit.html',
      controller: 'PracticeEnterpriseLocationsEditController',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_ETTEVOTE]
      },
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); } 
      }
    })
    .when('/practice/enterprise/:id/:enterpriseSchoolId/locations/view', {
      templateUrl: 'practiceEnterprise/practice.enterprise.locations.view.html',
      controller: 'PracticeEnterpriseLocationsEditController',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_ETTEVOTE]
      },
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); } 
      }
    })
    .when('/practice/enterprise/:id/:enterpriseSchoolId/studentGroups/edit', {
      templateUrl: 'practiceEnterprise/practice.enterprise.studentGroups.edit.html',
      controller: 'PracticeEnterpriseStudentGroupsEditController',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_ETTEVOTE]
      },
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); } 
      }
    })
    .when('/practice/enterprise/:id/:enterpriseSchoolId/studentGroups/view', {
      templateUrl: 'practiceEnterprise/practice.enterprise.studentGroups.view.html',
      controller: 'PracticeEnterpriseStudentGroupsEditController',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_ETTEVOTE]
      },
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); } 
      }
    })
    .when('/practice/enterprise/:id/:enterpriseSchoolId/contracts/view', {
      templateUrl: 'practiceEnterprise/practice.enterprise.contracts.view.html',
      controller: 'PracticeEnterpriseContractsViewController',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_ETTEVOTE]
      },
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); } 
      }
    })
    .when('/practice/enterprise/:id/:enterpriseSchoolId/contracts/edit', {
      templateUrl: 'practiceEnterprise/practice.enterprise.contracts.edit.html',
      controller: 'PracticeEnterpriseContractsViewController',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_ETTEVOTE]
      },
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); } 
      }
    })
    .when('/practice/enterprise/:id/:enterpriseSchoolId/grades/view', {
      templateUrl: 'practiceEnterprise/practice.enterprise.grades.view.html',
      controller: 'PracticeEnterpriseGradesEditController',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_ETTEVOTE]
      },
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); } 
      }
    })
    .when('/practice/enterprise/:id/:enterpriseSchoolId/grades/edit', {
      templateUrl: 'practiceEnterprise/practice.enterprise.grades.edit.html',
      controller: 'PracticeEnterpriseGradesEditController',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_ETTEVOTE]
      },
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); } 
      }
    })
    .when('/practice/enterprise/:id/:enterpriseSchoolId/admission/edit', {
      templateUrl: 'practiceEnterprise/practice.enterprise.admission.edit.html',
      controller: 'PracticeEnterpriseAdmissionEditController',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_ETTEVOTE]
      },
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); } 
      }
    })
    .when('/practice/enterprise/:id/:enterpriseSchoolId/admission/view', {
      templateUrl: 'practiceEnterprise/practice.enterprise.admission.view.html',
      controller: 'PracticeEnterpriseAdmissionEditController',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_ETTEVOTE]
      },
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); } 
      }
    });
}]);
