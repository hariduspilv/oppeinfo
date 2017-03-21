'use strict';

angular.module('hitsaOis')
  .controller('HigherEducationCurriculumController', function ($scope, Classifier, Curriculum, Session, dialogService, ArrayUtils, message, $route, $location, QueryUtils, oisFileService, $translate, DataUtils, $rootScope) {

    $scope.formView = {
        curriculum: "curriculum",
        version: "version"
    };

    $scope.showMainForm = true;

    // TODO: finish readonly

    $scope.removeFromArray = ArrayUtils.remove;


    var baseUrl = '/curriculum';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var id = $route.current.params.id;

    var initialCurriculumScope = {
      files: [],
      specialities: [],
      grades: [],
      studyLanguages: [],
      departments: [],
      versions: [],
      jointPartners: [],
      higher: true,
      status: 'OPPEKAVA_STAATUS_S',
      consecution: 'OPPEKAVA_TYPE_E',
      draft: 'OPPEKAVA_LOOMISE_VIIS_PUUDUB',
      validFrom: new Date(),
      joint: false,
      abroad: false,
      optionalStudyCredits: 0   //TODO: which version to take when calculating this value? (probably current)
    };

    // --- Get and Set Data
    $scope.studyLevels = [];
    function getAllowedStudyLevels() {
        QueryUtils.endpoint('/school/studyLevels').get().$promise.then(function(response){
            var studyLevelCodes = response.studyLevels;

            studyLevelCodes.forEach(function(it) {
                if(isHigherStudyLevel(it)) {
                    $scope.studyLevels.push({code: it});
                }
            });
        });
    }
    getAllowedStudyLevels();

    function isHigherStudyLevel(studyLevelCode) {
        return parseInt(studyLevelCode.substring(studyLevelCode.length - 3)) >= 500;
    }

    $scope.getAreasOfStudy = function(listOfGroupsChanged) {
        if(listOfGroupsChanged) {
            $scope.curriculum.areaOfStudy = undefined;
        }
        if($scope.curriculum.group) {
            Curriculum.getAreasOfStudyByGroupOfStudy($scope.curriculum.group, setValds);
        }
    };

    $scope.clearIscedClass = function() {
        $scope.curriculum.fieldOfStudy = undefined;
    };

    $scope.areasOfStudy = [];
    function setValds(response) {
        $scope.areasOfStudy = response.map(function(a){return a.code;});
    }

    function getCurriculum() {
      if (id) {
        Endpoint.get({ id: id }).$promise.then(function (response) {
          $scope.curriculum = response;
          setVariablesForExistingCurriculum();
        });
      } else {
        $scope.curriculum = new Endpoint(initialCurriculumScope);
         getEhisSchoolsSelection();
      }
    }
    getCurriculum();

    function setVariablesForExistingCurriculum() {
        DataUtils.convertStringToDates($scope.curriculum, ["validFrom", "validThru", "approval", "ehisChanged", "accreditationDate", "accreditationValidDate", "merRegDate"]);
        setStudyPeriod();
        setAreaOfStudy();
        $scope.getAreasOfStudy();
        getJointPartners();
        setReadOnly();
        getEhisSchoolsSelection();
        $scope.curriculum.abroad = false;
    }

    function setReadOnly() {
        $scope.readOnly = $scope.curriculum.status === "OPPEKAVA_STAATUS_K" || $scope.curriculum.status === "OPPEKAVA_STAATUS_C" ||
        $route.current.$$route.originalPath.indexOf("view") !== -1;
    }

    function setStudyPeriod() {
      var MONTHS_IN_YEAR = 12;
      $scope.curriculum.studyPeriodYears = Math.floor($scope.curriculum.studyPeriod / MONTHS_IN_YEAR);
      $scope.curriculum.studyPeriodMonths = $scope.curriculum.studyPeriod % MONTHS_IN_YEAR;
    }

    function setAreaOfStudy() {

        if($scope.curriculum.iscedClass) {
            if($scope.curriculum.iscedClass.indexOf("ISCED_VALD") !== -1) {
                $scope.curriculum.areaOfStudy = $scope.curriculum.iscedClass;
            } else {

                Classifier.getParentsWithMainClass('ISCED_VALD', $scope.curriculum.iscedClass).$promise.then(function (response) {
                    $scope.curriculum.areaOfStudy = response[0].code;
                    $scope.curriculum.fieldOfStudy = $scope.curriculum.iscedClass;
                });
            }
        }
    }

    //TODO: method currently not used
    // function getMyEhisSchool() {
    //   if($rootScope.currentUser && Session.school.id) {
    //     QueryUtils.endpoint("/school").get({id: Session.school.id}).$promise.then(function(response) {
    //       $scope.myEhisSchool = response.ehisSchool;
    //     });
    //   }
    // }
    // getMyEhisSchool();


    $scope.removejointPartner = function(deletedPartner) {
        $scope.removeFromArray($scope.curriculum.jointPartners, deletedPartner);
        if(!deletedPartner.abroad) {
            $scope.removeFromArray($scope.jointPartnersEhisSchools, deletedPartner.ehisSchool);
            if($scope.curriculum.jointMentor === deletedPartner.ehisSchool) {
                $scope.curriculum.jointMentor = undefined;
            }
            deletePartersSubjects(deletedPartner.ehisSchool);
        }
    };
    // TODO: removal in iteration
    function deletePartersSubjects(partnersEhisSchool) {
        $scope.curriculum.versions.forEach(function(v){
            v.modules.forEach(function(m){
                var removedSubjects = m.subjects.filter(function(s){
                    return s.ehisSchoolCode === partnersEhisSchool;
                });
                removedSubjects.forEach(function(rs){
                    $scope.deleteSubject(m, rs);
                });
            });
        });
    }

    // --- Validation

    $scope.gradeRequired = function() {
        return !$scope.curriculum || !$scope.curriculum.origStudyLevel || $scope.curriculum.origStudyLevel !== 'OPPEASTE_514';
    };

    function curriculumFormIsValid() {
        return $scope.higherEducationCurriculumForm.$valid && $scope.curriculum.specialities.length > 0 && $scope.jointPartnersAdded() && allVersionsValid();
    }

    $scope.jointPartnersAdded = function() {
        return !$scope.curriculum.joint || $scope.curriculum.jointPartners.length > 0;
    };


    function allVersionsValid() {
        if(!$scope.curriculum.versions || $scope.curriculum.versions.length === 0) {
            return true;
        }
        for(var i = 0; i < $scope.curriculum.versions.length; i++) {
            if(!$scope.versionIsValid($scope.curriculum.versions[i])) {
                return false;
            }
        }
        return true;
    }

    $scope.codeUniqueQuery = {
      id: id,
      paramName: 'code',
      url: baseUrl + '/unique'
    };

    $scope.merCodeUniqueQuery = {
      id: $scope.codeUniqueQuery.id,
      paramName: 'merCode',
      url: $scope.codeUniqueQuery.url
    };

    // --- Save and Delete

    $scope.save = function () {
        if($scope.readOnly) {
            return;
        }
      $scope.higherEducationCurriculumForm.$setSubmitted();

      if($scope.curriculum.joint) {
          addSharedInformationToJointPartners();
      } else {
          $scope.curriculum.jointMentor = undefined;
          $scope.curriculum.jointPartners = [];
      }

      if (!curriculumFormIsValid()) {
        message.error('main.messages.form-has-errors');
        return;
      }
      clearGradesIfNecessary();
        $scope.curriculum.iscedClass = $scope.curriculum.fieldOfStudy ?
        $scope.curriculum.fieldOfStudy : $scope.curriculum.areaOfStudy;
        if($scope.curriculum.id) {
            $scope.curriculum.$update().then(function(){
                message.updateSuccess();
                setVariablesForExistingCurriculum();
            });
        } else {
            $scope.curriculum.$save().then(function(response){
                message.info('main.messages.create.success');
                $location.path('/higherEducationCurriculum/'+ response.id +'/edit');
            });
        }
    };

    $scope.changeJointMentors = function() {
        if($scope.curriculum.abroad) {
            $scope.jointPartnersEhisSchools = [];
        }
    };

    function clearGradesIfNecessary() {
        if(!$scope.gradeRequired()) {
            $scope.curriculum.grades = [];
        }
    }

    $scope.delete = function () {
        dialogService.confirmDialog({prompt: 'curriculum.deleteconfirmHigher'}, function() {
            $scope.curriculum.$delete().then(function () {
                message.info('main.messages.delete.success');
                $location.path(baseUrl);
            });
        });
    };

    function getEhisSchoolsSelection() {
        $scope.jointPartnersEhisSchools = [];
        $scope.myEhisSchool = $rootScope.currentUser.school.ehisSchool;
        $scope.jointPartnersEhisSchools.push($rootScope.currentUser.school.ehisSchool);
        $scope.curriculum.jointPartners.forEach(function(e){
            if(e.ehisSchool) {
                $scope.jointPartnersEhisSchools.push(e.ehisSchool);
            }
        });
    }

    $scope.addJointPartner = function () {
        var jointPartner = {abroad: $scope.curriculum.abroad};
        if (jointPartner.abroad) {
            jointPartner.nameEt = $scope.curriculum.jointPartnerForeignNameEt;
            jointPartner.nameEn = $scope.curriculum.jointPartnerForeignNameEn;
        } else {
            for(var i = 0; i < $scope.jointPartnersEhisSchools.length; i++) {
                if( $scope.jointPartnersEhisSchools.indexOf($scope.curriculum.jointPartnerEhisSchool) !== -1) {
                    $scope.clearJointPartnersFields();
                    return;
                }
            }
            jointPartner.ehisSchool = $scope.curriculum.jointPartnerEhisSchool;
            $scope.jointPartnersEhisSchools.push(jointPartner.ehisSchool);
        }
        $scope.curriculum.jointPartners.push(jointPartner);
        $scope.clearJointPartnersFields();
    };

    var addSharedInformationToJointPartners = function () {
      $scope.curriculum.jointPartners.forEach(function(it) {
        it.contractEt = $scope.curriculum.contractEt;
        it.contractEn = $scope.curriculum.contractEn;
        it.supervisor = $scope.curriculum.supervisor;
      });
    };

    $scope.clearJointPartnersFields = function() {
        $scope.curriculum.jointPartnerForeignNameEt = undefined;
        $scope.curriculum.jointPartnerForeignNameEn = undefined;
        $scope.curriculum.jointPartnerEhisSchool = undefined;
    };

    function getJointPartners() {
        if($scope.curriculum.jointPartners && $scope.curriculum.jointPartners.length > 0) {
            $scope.curriculum.abroad = false;
            $scope.curriculum.contractEt = $scope.curriculum.jointPartners[0].contractEt;
            $scope.curriculum.contractEn = $scope.curriculum.jointPartners[0].contractEn;
            $scope.curriculum.supervisor = $scope.curriculum.jointPartners[0].supervisor;
        }
    }

    // --- Dialog Windows

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
            }
        });
    };

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

    $scope.removeSpeciality = function(speciality) {
        $scope.curriculum.versions.forEach(function(v){
            $scope.removeFromArray(v.specialitiesReferenceNumbers, speciality.referenceNumber);
            v.modules.forEach(function(m){
                $scope.removeFromArray(m.specialitiesReferenceNumbers, speciality.referenceNumber);
            });
            deleteModulesWithNoSpeciality(v);
        });
        $scope.removeFromArray($scope.curriculum.specialities, speciality);
    };

    $scope.openAddGradeDialog = function (editingGrade) {
      var DialogController = null;
      if (editingGrade) {
        DialogController = function (scope) {
          scope.data = angular.extend({}, editingGrade);
        };
      }
      dialogService.showDialog('higherEducationCurriculum/higher.education.curriculum.grade.add.dialog.html', DialogController,
        function (submitScope) {
          var data = submitScope.data;
            if(editingGrade) {
                angular.extend(editingGrade, data);
            } else {
                $scope.curriculum.grades.push(data);
            }
        });
    };

    $scope.openAddFileDialog = function () {
      dialogService.showDialog('higherEducationCurriculum/vocationalCurriculum/file.add.dialog.html', null, function (submitScope) {
        var data = submitScope.data;
        data.sendEhis = false;
        data.ehis = false;
        data.oisFile = oisFileService.getFromLfFile(data.file[0], function(file) {
            data.oisFile = file;
            $scope.curriculum.files.push(data);
        });
      });
    };

    $scope.getUrl = function(oisFile) {
      return oisFileService.getFileUrl(oisFile);
    };

    // --- Statuses

    $scope.setStatus = function(newStatus) {
      $scope.curriculum.status = newStatus.code;
    };

    $scope.statuses = [
        {code: 'OPPEKAVA_STAATUS_S', button: 'main.button.status.edit'},
        {code: 'OPPEKAVA_STAATUS_M', button: 'main.button.status.approving'},
        {code: 'OPPEKAVA_STAATUS_K', button: 'main.button.status.confirm'},
        {code: 'OPPEKAVA_STAATUS_C', button: 'main.button.status.close'}
    ];

    $scope.filterStatusChangeOptions = function(status) {
        if(!$scope.curriculum || !$scope.curriculum.status || !status) {
            return false;
        }
        var currentStatusCode = $scope.curriculum.status;
        var enabledStatusesToChange = {
            'OPPEKAVA_STAATUS_S': ['OPPEKAVA_STAATUS_M', 'OPPEKAVA_STAATUS_C'],
            'OPPEKAVA_STAATUS_M': ['OPPEKAVA_STAATUS_S', 'OPPEKAVA_STAATUS_K'],
            'OPPEKAVA_STAATUS_K': ['OPPEKAVA_STAATUS_M', 'OPPEKAVA_STAATUS_C'],
            'OPPEKAVA_STAATUS_C': ['OPPEKAVA_STAATUS_S', 'OPPEKAVA_STAATUS_M', 'OPPEKAVA_STAATUS_K'],
        };
        return enabledStatusesToChange[currentStatusCode].indexOf(status.code) !== -1;
    };









    //      curriculum version controller




    // possible statuses and admission year's options

    var allowedVersionStatuses = {
        OPPEKAVA_VERSIOON_STAATUS_S: ['OPPEKAVA_VERSIOON_STAATUS_S', 'OPPEKAVA_VERSIOON_STAATUS_K'],
        OPPEKAVA_VERSIOON_STAATUS_K: ['OPPEKAVA_VERSIOON_STAATUS_K', 'OPPEKAVA_VERSIOON_STAATUS_S', 'OPPEKAVA_VERSIOON_STAATUS_C'],
        OPPEKAVA_VERSIOON_STAATUS_C: ['OPPEKAVA_VERSIOON_STAATUS_C']
    };

    $scope.allowerVersionStatuses = [];
    $scope.versionCodes = [];
    $scope.versionFormReadOnly = false;

    function setAdmissionYearsSelelction() {
        $scope.admissionYears = [];
        var currentYear = new Date().getFullYear();
        for(var year = currentYear - 10; year <= currentYear + 2; year++) {
            $scope.admissionYears.push(year);
        }
    }
    setAdmissionYearsSelelction();

    // view, save, delete

    $scope.goToVersionForm = function(version) {
        $scope.versionCodes = $scope.curriculum.versions.map(function(v){return v.code;});

        if(version) {
            $scope.version = angular.copy(version);
            $scope.editedVersion = version;
            $scope.versionCodes.splice($scope.versionCodes.indexOf(version.code), 1);
            setVersionFormReadOnly();
        } else {
            $scope.version = {
                status: 'OPPEKAVA_VERSIOON_STAATUS_S',
                modules: [],
                specialitiesReferenceNumbers: [],
                code: $scope.curriculum && $scope.curriculum.code ? $scope.curriculum.code + "/" + new Date().getFullYear() : null
            };
            setVersionFormReadOnly();
        }
        $scope.allowerVersionStatuses = allowedVersionStatuses[$scope.version.status];
        $scope.curriculumVersionCodeUniqueQuery = {
            fk: $scope.curriculum.id,
            paramName: 'code',
            url: baseUrl + '/version/unique'
        };
        $scope.showMainForm = false;
        $scope.higherEducationCurriculumVersionForm.$setPristine();
    };

    function setVersionFormReadOnly() {
        $scope.versionFormReadOnly = $scope.readOnly || $scope.version.status === 'OPPEKAVA_VERSIOON_STAATUS_C';
    }

    $scope.goToCurriculumForm = function() {
        $scope.showMainForm = true;
        $scope.versionFormReadOnly = false;
    };

    $scope.saveVersion = function() {
        $scope.higherEducationCurriculumVersionForm.$setSubmitted();
        if (!versionFormIsValid()) {
            message.error('main.messages.form-has-errors');
            return;
        }
        if (ArrayUtils.includes($scope.curriculum.versions, $scope.editedVersion)) {
            $scope.curriculum.versions.splice($scope.curriculum.versions.indexOf($scope.editedVersion), 1);
        }
        $scope.curriculum.versions.push($scope.version);

        $scope.goToCurriculumForm();
    };

    $scope.copyVersion = function() {
        $scope.version.id = undefined;
        $scope.version.version = undefined;
        $scope.version.occupationModules = undefined;

        $scope.version.modules.forEach(function(m){
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
        $scope.editedVersion = undefined;
        $scope.version.status = 'OPPEKAVA_VERSIOON_STAATUS_S';
        setVersionFormReadOnly();
        $scope.versionCodes = $scope.curriculum.versions.map(function(v){return v.code;});
        $scope.higherEducationCurriculumVersionForm.code.$setDirty();
        $scope.higherEducationCurriculumVersionForm.code.$setValidity("hoisFunctionValidation", $scope.validateVersionCode($scope.version.code));
    };

    $scope.deleteVersion = function() {
        dialogService.confirmDialog({prompt: 'curriculum.version.deleteconfirm'}, function() {
            $scope.removeFromArray($scope.curriculum.versions, $scope.editedVersion);
            $scope.editedVersion = undefined;
            $scope.goToCurriculumForm();
        });
    };

    // version form validation

    $scope.validateVersionCode = function(userInput) {
        return $scope.versionCodes.indexOf(userInput) === -1;
    };

    $scope.versionIsValid = function(version) {
        return version.specialitiesReferenceNumbers && version.specialitiesReferenceNumbers.length > 0 && $scope.anyModuleAdded(version) && allModulesValid(version);
    };

    function versionFormIsValid() {
        return $scope.higherEducationCurriculumVersionForm.$valid && $scope.versionIsValid($scope.version);
    }

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
        $scope.removeFromArray(module1.specialitiesReferenceNumbers, speciality.referenceNumber);
        deleteModulesWithNoSpeciality($scope.version);
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
        $scope.removeFromArray(module1.subjects, subject);
        module1.electiveModules.forEach(function(em){
            $scope.removeFromArray(em.subjects, subject.subjectId);
        });
        $scope.setCompulsoryAndTotalStudyCredits(module1);
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
                // TODO: enable adding subjects from partner schools
                var ehisSchools = $scope.jointPartnersEhisSchools;
                QueryUtils.endpoint('/curriculum/subjects')
                    .get({ lang: $translate.use().toUpperCase(), ehisSchools: ehisSchools}).$promise.then(function (result) {
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
                // TODO: enable adding subjects from partner schools
                QueryUtils.endpoint('/curriculum/subjects')
                    .get({ lang: $translate.use().toUpperCase() }).$promise.then(function (result) {
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
