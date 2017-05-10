'use strict';

angular.module('hitsaOis')
  .controller('HigherEducationCurriculumVersionController', function ($scope, Classifier, Curriculum, Session, dialogService, ArrayUtils, message, $route, $location, QueryUtils, oisFileService, $translate, DataUtils, $rootScope, $routeParams) {

    var baseUrl = '/curriculum';
    $scope.curriculum = $route.current.locals.curriculum;
    $scope.myEhisSchool = $rootScope.currentUser.school ? $rootScope.currentUser.school.ehisSchool : null;
    $scope.ehisSchools = $scope.curriculum.jointPartners.map(function(p){return p.ehisSchool;});
    $scope.ehisSchools.push($scope.myEhisSchool);

    var entity = {curriculum: $scope.curriculum.id, modules: []};

    var initialVersion = {
        status: 'OPPEKAVA_VERSIOON_STAATUS_S',
        validFrom: new Date(),
        code: $scope.curriculum.code + "/" + new Date().getFullYear(),
        curriculum: $scope.curriculum.id,
        modules: []
    };

    $scope.removeFromArray = ArrayUtils.remove;

    $scope.removeModule = function(versionModules, deletedModule){
        dialogService.confirmDialog({prompt: 'curriculum.itemDeleteConfirm'}, function() {
            ArrayUtils.remove(versionModules, deletedModule);
        });
    };

    if (angular.isDefined($route.current.params.versionId)) {
      $scope.curriculum.versions.forEach(function(it){
        if (it.id.toString() === $route.current.params.versionId) {
          angular.extend(entity, it);
        }
      });
      $scope.version = entity;
    } else if (angular.isDefined($route.current.locals.copy)) {
      angular.extend(entity, initialVersion, $route.current.locals.copy);
      $scope.version = entity;
    } else {
      angular.extend(entity, initialVersion);
      $scope.version = entity;
    }
    var CurriculumVersionEndpoint = QueryUtils.endpoint('/curriculum/' + $scope.version.curriculum + '/versions');

    $scope.readOnly = $route.current.$$route.originalPath.indexOf("view") !== -1;





    // --- Dialog Windows

    function getReferenceNumber(list) {
        var existingReferenceNumbers = list.map(function(el){
            return el.referenceNumber;
        });
        var rand;
        while(true) {
            rand = - Math.floor((Math.random() * 1000) + 1);
            if(!ArrayUtils.includes(existingReferenceNumbers, rand)) {
                break;
            }
        }
        return rand;
    }


    $scope.openAddSpecialtyDialog = function (editedSpecialty) {
      var DialogController = function (scope) {
        if (editedSpecialty) {
            scope.data =  angular.extend({}, editedSpecialty);
        }
        scope.maxCredits = $scope.curriculum.credits ? $scope.curriculum.credits : 0;
      };

      dialogService.showDialog('higherEducationCurriculum/higher.education.curriculum.specialty.add.dialog.html', DialogController,
        function (submitScope) {
            var data = submitScope.data;
            if(!data.occupation) {
                data.occupationEt = undefined;
                data.occupationEn = undefined;
            }
            if(editedSpecialty) {
                angular.extend(editedSpecialty, data);
            } else {
                data.referenceNumber = getReferenceNumber($scope.curriculum.specialities);
                $scope.curriculum.specialities.push(data);
                if(!$scope.version.newCurriculumSpecialities) {
                    $scope.version.newCurriculumSpecialities = [];
                }
                $scope.version.newCurriculumSpecialities.push(data);
            }
        });
    };



    // possible statuses and admission year's options

    var allowedVersionStatuses = {
        OPPEKAVA_VERSIOON_STAATUS_S: ['OPPEKAVA_VERSIOON_STAATUS_S', 'OPPEKAVA_VERSIOON_STAATUS_K'],
        OPPEKAVA_VERSIOON_STAATUS_K: ['OPPEKAVA_VERSIOON_STAATUS_K', 'OPPEKAVA_VERSIOON_STAATUS_S', 'OPPEKAVA_VERSIOON_STAATUS_C'],
        OPPEKAVA_VERSIOON_STAATUS_C: ['OPPEKAVA_VERSIOON_STAATUS_C']
    };

    $scope.allowerVersionStatuses = allowedVersionStatuses[$scope.version.status];
    $scope.versionCodes = $scope.curriculum.versions.filter(function(v){return v.id !== $scope.version.id;}).map(function(v){return v.code;});
    $scope.versionFormReadOnly = false;

    function setAdmissionYearsSelelction() {
        $scope.admissionYears = [];
        var currentYear = new Date().getFullYear();
        for(var year = currentYear - 10; year <= currentYear + 2; year++) {
            $scope.admissionYears.push(year);
        }
    }
    setAdmissionYearsSelelction();

    $scope.curriculumVersionCodeUniqueQuery = {
        fk: $scope.curriculum.id,
        paramName: 'code',
        url: baseUrl + '/version/unique'
    };

    $scope.save = function() {
        $scope.higherEducationCurriculumVersionForm.$setSubmitted();
        if (!versionFormIsValid()) {
            message.error('main.messages.form-has-errors');
            return;
        }
        var curriculumVersion = new CurriculumVersionEndpoint($scope.version);

        if (angular.isDefined($scope.version.id)) {
          curriculumVersion.$update().then(function(response) {
            message.updateSuccess();
            $scope.version = response;
            $scope.version.curriculum = $scope.curriculum.id;
            $scope.version.newCurriculumSpecialities = [];
            getCurriculum();
          });
        } else {
          curriculumVersion.$save().then(function(response) {
            message.info('main.messages.create.success');
            $location.path('/higherEducationCurriculum/' + $scope.version.curriculum + '/version/' + response.id + '/edit');
          });
        }
    };

    /**
     * When saving curriculum version, newly created specialities return with new referenceNumbers
     * And these numbers should be updated in curriculum as well
     */
    function getCurriculum() {
        $scope.curriculum = QueryUtils.endpoint('/curriculum').get({id: $scope.curriculum.id});
    }

    function versionFormIsValid() {
        return $scope.higherEducationCurriculumVersionForm.$valid
        //  && $scope.versionIsValid($scope.version)
         ;
    }

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'curriculum.version.deleteconfirm'}, function() {
        var curriculumVersion = new CurriculumVersionEndpoint($scope.version);
        curriculumVersion.$delete().then(function() {
          message.info('main.messages.delete.success');
          $location.path('higherEducationCurriculum/' + $scope.version.curriculum + "/edit");
        });
      });
    };

    $scope.copy = function() {
      var copy = angular.merge({}, $scope.version);
      ['id', 'version', 'changed', 'changedBy', 'inserted', 'insertedBy', 'status', 'validFrom', 'validThru']
      .forEach(function(property) {
        delete copy[property];
      });
      copy.modules.forEach(function(m){
          m.id = undefined;
          m.version = undefined;
          m.subjects.forEach(function(s){
              s.id = undefined;
              s.version = undefined;
          });
          m.electiveModules.forEach(function(e){
              e.id = undefined;
              e.version = undefined;
          });
      });
      $location.path('/higherEducationCurriculum/' + $scope.version.curriculum + '/version/new');
      $routeParams.versionCopy = copy;
      $route.current.locals.versionCopy = copy;
      $route.current.params.versionCopy = copy;
    };

    // version form validation

    $scope.validateVersionCode = function(userInput) {
        return $scope.versionCodes.indexOf(userInput) === -1;
    };

    $scope.versionIsValid = function(version) {
        return version.specialitiesReferenceNumbers && version.specialitiesReferenceNumbers.length > 0 && $scope.anyModuleAdded(version) && allModulesValid(version);
    };

    function allModulesValid(version) {
        if(!version.modules) {
            return false;
        }
        for(var i = 0; i < version.modules.length; i++) {
            if(!$scope.moduleValid(version.modules[i])) {
                return false;
            }
        }
        return true;
    }

    $scope.moduleValid = function(module1) {
        return $scope.moduleHasSubjects(module1);
    };

    $scope.anyModuleAdded = function(version) {
        return version.modules.filter(function(el){return !el.minorSpeciality;}).length > 0;
    };

    $scope.moduleHasSubjects = function(module1) {
        if(module1.minorSpeciality) {
            return module1.subjects && module1.subjects.length > 0;
        } else {
            return module1.type === 'KORGMOODUL_L' || module1.type === 'KORGMOODUL_V' || module1.subjects && module1.subjects.length > 0;
        }
    };


    // modules and specialities

    $scope.removeModuleFromSpeciality = function (module1, speciality) {
        dialogService.confirmDialog({prompt: 'curriculum.itemDeleteConfirm'}, function() {
            $scope.removeFromArray(module1.specialitiesReferenceNumbers, speciality.referenceNumber);
            deleteModulesWithNoSpeciality($scope.version);
        });
    };

    function deleteModulesWithNoSpeciality(version) {
        version.modules = version.modules.filter(function(m){
            return moduleHasSpeciality(m);
        });
    }

    function moduleHasSpeciality(m) {
        return m.minorSpeciality || (m.specialitiesReferenceNumbers && m.specialitiesReferenceNumbers.length > 0);
    }

    $scope.filterModulesBySpeciality = function(spec) {
        // return true;
        return function(module1) {
            return ArrayUtils.includes(module1.specialitiesReferenceNumbers, spec.referenceNumber);
        };
    };

    $scope.filterSpecialities = function(spec) {
        if(!$scope.curriculum || !$scope.version) {
            return false;
        }
        return ArrayUtils.includes($scope.version.specialitiesReferenceNumbers, spec.referenceNumber);
    };

    $scope.removeSpecsFromModules = function() {
        $scope.version.modules.forEach(function(m){
            if(!m.minorSpeciality) {
                m.specialitiesReferenceNumbers = m.specialitiesReferenceNumbers.filter(function(n){
                    return $scope.version.specialitiesReferenceNumbers &&
                    $scope.version.specialitiesReferenceNumbers.indexOf(n) !== -1;
                });
            }
        });
        deleteModulesWithNoSpeciality($scope.version);
    };


    // subjects
    $scope.deleteSubject = function(module1, subject) {
        dialogService.confirmDialog({prompt: 'curriculum.itemDeleteConfirm'}, function() {
            $scope.removeFromArray(module1.subjects, subject);
            module1.electiveModules.forEach(function(em){
                $scope.removeFromArray(em.subjects, subject.subjectId);
            });
            $scope.setCompulsoryAndTotalStudyCredits(module1);
        });
    };

    $scope.setCompulsoryAndTotalStudyCredits = function(module1) {
        module1.compulsoryStudyCredits = 0;
        module1.subjects.forEach(function(e){
            if(!e.optional) {
                module1.compulsoryStudyCredits += e.credits;
            }
        });
        module1.totalCredits = module1.compulsoryStudyCredits + module1.optionalStudyCredits;
    };

    $scope.filterOutSubjectsAddedToOtherModules = function(subject, excludedModule) {
        var otherModules = getAllVersionModulesExcept(excludedModule);
        var subjectsFromOtherModules = getAllSubjectIdsFromModules(otherModules);
        return subjectsFromOtherModules.indexOf(subject.subjectId) === -1;
    };

    function getAllVersionModulesExcept(excludedModule) {
        return $scope.version.modules.filter(function(m){return m !== excludedModule;});
    }

    function getAllSubjectIdsFromModules(modules) {
        var subjects = [];
        for(var i = 0; i < modules.length; i++) {
            for(var j = 0; j < modules[i].subjects.length; j++) {
                subjects.push(modules[i].subjects[j].subjectId);
            }
        }
        return subjects;
    }

    // dialog windows

    $scope.openAddModuleDialog = function (editingModule) {
      var DialogController = function (scope) {

            scope.specialities = $scope.curriculum.specialities.filter(function(s){
                return $scope.version.specialitiesReferenceNumbers && $scope.version.specialitiesReferenceNumbers.indexOf(s.referenceNumber) !== -1;
            });

            scope.myEhisSchool = $scope.myEhisSchool;
            scope.readOnly = $scope.readOnly;

            scope.addElectiveModule = function() {
                var newElectiveModule = {
                    nameEt: scope.optionalNameEt,
                    nameEn: scope.optionalNameEn,
                    referenceNumber: getReferenceNumber(scope.data.electiveModules),
                    subjects: []
                };
                scope.data.electiveModules.push(newElectiveModule);
                scope.optionalNameEt = undefined;
                scope.optionalNameEn = undefined;
            };
            scope.editingElectiveModule = false;
            var editedElectiveModule = null;

            scope.editElectiveModule = function (electiveModule) {
                editedElectiveModule = electiveModule;
                scope.editingElectiveModule = true;
                scope.optionalNameEt = electiveModule.nameEt;
                scope.optionalNameEn = electiveModule.nameEn;
            };

            scope.finishEditElectiveModule = function() {
                scope.editingElectiveModule = false;
                editedElectiveModule.nameEt = scope.optionalNameEt;
                editedElectiveModule.nameEn = scope.optionalNameEn;
                scope.optionalNameEt = undefined;
                scope.optionalNameEn = undefined;
            };

            scope.addSubjectToElectiveModule = function(subject) {
                console.log(subject);
                var electiveModule = scope.data.electiveModules.filter(function(e){
                    return e.referenceNumber === subject.electiveModule;
                })[0];
                electiveModule.subjects.push(subject.subjectId);
            };

            function getSubjects() {
                var ehisSchools = $scope.ehisSchools;
                QueryUtils.endpoint('/curriculum/subjects')
                    .search({ lang: $translate.use().toUpperCase(), ehisSchools: ehisSchools}).$promise.then(function (result) {
                    scope.subjectsUnfiltered = result.content.filter(function(s){
                        return $scope.filterOutSubjectsAddedToOtherModules(s, editingModule);
                    });
                    filterSubjects();
                });
            }
            getSubjects();

            function filterSubjects() {
                scope.subjects = scope.subjectsUnfiltered.filter(function(s){
                    for(var i = 0; i < scope.data.subjects.length; i++) {
                        if(scope.data.subjects[i].subjectId === s.subjectId) {
                            return false;
                        }
                    }
                    return true;
                });
            }

            scope.deleteElectiveModule = function(electiveModule) {
                scope.data.subjects.forEach(function(e){
                    if(e.electiveModule === electiveModule.referenceNumber) {
                        e.electiveModule = undefined;
                    }
                });
                $scope.removeFromArray(scope.data.electiveModules, electiveModule);
            };

            scope.addSubject = function() {
                var newSubject = angular.copy(scope.data.selectedSubject);
                newSubject.optional = false;
                scope.data.subjects.push(newSubject);
                scope.data.selectedSubject = undefined;
                scope.setCompulsoryStudyCredits();
                filterSubjects();
                validateSubjects();
            };

            scope.setCompulsoryStudyCredits = function(subject) {
                $scope.setCompulsoryAndTotalStudyCredits(scope.data);
                if(subject && !subject.optional) {
                    subject.electiveModule = undefined;
                }
            };

            scope.removeSubject = function(subject) {
                $scope.removeFromArray(scope.data.subjects, subject);
                scope.setCompulsoryStudyCredits();
                filterSubjects();
                validateSubjects();
            };

            function validateSubjects() {
                scope.data.subjectAdded = scope.data.subjects.length > 0 ? 'true' : null;
            }

            if (editingModule) {
                scope.data = angular.copy(editingModule);
                validateSubjects();
            } else {
                scope.data = {
                    electiveModules: [],
                    specialitiesReferenceNumbers: [],
                    subjects: [],
                    minorSpeciality: false,
                    optionalStudyCredits: 0,
                    totalCredits: 0,
                    compulsoryStudyCredits: 0
                };
            }
        };
      dialogService.showDialog('higherEducationCurriculum/higher.education.curriculum.version.module.html', DialogController,
        function (submitScope) {
          var data = submitScope.data;
            if(data.type !== 'KORGMOODUL_M') {
                data.typeNameEt = null;
                data.typeNameEn = null;
            }
            if(editingModule) {
                $scope.version.modules.splice($scope.version.modules.indexOf(editingModule), 1);
            }
            $scope.version.modules.push(data);
        });
    };


    //TODO: code duplication
    $scope.openAddMinorSpecialtyDialog = function (editingModule) {

      var DialogController = function (scope) {

            scope.myEhisSchool = $scope.myEhisSchool;
            scope.readOnly = $scope.readOnly;
            scope.addElectiveModule = function() {
                var newElectiveModule = {
                    nameEt: scope.optionalNameEt,
                    nameEn: scope.optionalNameEn,
                    referenceNumber: getReferenceNumber(scope.data.electiveModules),
                    subjects: []
                };
                scope.data.electiveModules.push(newElectiveModule);
                scope.optionalNameEt = undefined;
                scope.optionalNameEn = undefined;
            };
            scope.editingElectiveModule = false;
            var editedElectiveModule = null;

            scope.editElectiveModule = function (electiveModule) {
                editedElectiveModule = electiveModule;
                scope.editingElectiveModule = true;
                scope.optionalNameEt = electiveModule.nameEt;
                scope.optionalNameEn = electiveModule.nameEn;
            };

            scope.finishEditElectiveModule = function() {
                scope.editingElectiveModule = false;
                editedElectiveModule.nameEt = scope.optionalNameEt;
                editedElectiveModule.nameEn = scope.optionalNameEn;
                scope.optionalNameEt = undefined;
                scope.optionalNameEn = undefined;
            };
            scope.addSubjectToElectiveModule = function(subject) {
                console.log(subject);
                var electiveModule = scope.data.electiveModules.filter(function(e){
                    return e.referenceNumber === subject.electiveModule;
                })[0];
                electiveModule.subjects.push(subject.subjectId);
            };

            function getSubjects() {
                var ehisSchools = $scope.ehisSchools;
                QueryUtils.endpoint('/curriculum/subjects')
                    .search({ lang: $translate.use().toUpperCase(), ehisSchools: ehisSchools}).$promise.then(function (result) {
                    scope.subjectsUnfiltered = result.content.filter(function(s){
                        return $scope.filterOutSubjectsAddedToOtherModules(s, editingModule);
                    });
                    filterSubjects();
                });
            }
            getSubjects();

            function filterSubjects() {
                scope.subjects = scope.subjectsUnfiltered.filter(function(s){
                    for(var i = 0; i < scope.data.subjects.length; i++) {
                        if(scope.data.subjects[i].subjectId === s.subjectId) {
                            return false;
                        }
                    }
                    return true;
                });
            }

            scope.deleteElectiveModule = function(electiveModule) {
                scope.data.subjects.forEach(function(e){
                    if(e.electiveModule === electiveModule.referenceNumber) {
                        e.electiveModule = undefined;
                    }
                });
                $scope.removeFromArray(scope.data.electiveModules, electiveModule);
            };

            scope.addSubject = function() {
                var newSubject = angular.copy(scope.data.selectedSubject);
                newSubject.optional = false;
                scope.data.subjects.push(newSubject);
                scope.data.selectedSubject = undefined;
                scope.setCompulsoryStudyCredits();
                filterSubjects();
                validateSubjects();
            };

            function validateSubjects() {
                scope.data.subjectAdded = $scope.moduleHasSubjects(scope.data) ? 'true' : null;
            }

            scope.setCompulsoryStudyCredits = function(subject) {
                $scope.setCompulsoryAndTotalStudyCredits(scope.data);
                if(subject && !subject.optional) {
                    subject.electiveModule = undefined;
                }
            };

            scope.removeSubject = function(subject) {
                $scope.removeFromArray(scope.data.subjects, subject);
                scope.setCompulsoryStudyCredits();
                filterSubjects();
                validateSubjects();
            };

            if (editingModule) {
                scope.data = angular.copy(editingModule);
                validateSubjects();
            } else {
                scope.data = {
                    electiveModules: [],
                    subjects: [],
                    minorSpeciality: true, // different from module controller

                    optionalStudyCredits: 0,
                    totalCredits: 0,
                    compulsoryStudyCredits: 0,
                    type: 'KORGMOODUL_M' // different from module controller
                };
            }
      };
      dialogService.showDialog('higherEducationCurriculum/higher.education.curriculum.version.minorSpecialty.html', DialogController,
        function (submitScope) {
          var data = submitScope.data;
            if(editingModule) {
                $scope.version.modules.splice($scope.version.modules.indexOf(editingModule), 1);
            }
            $scope.version.modules.push(data);
        });
    };
  });
