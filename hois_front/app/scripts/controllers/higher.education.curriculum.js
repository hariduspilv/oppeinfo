'use strict';

angular.module('hitsaOis')
  .controller('HigherEducationCurriculumController', function ($scope, Classifier, Curriculum, Session, dialogService, ArrayUtils, message, $route, $location, QueryUtils, oisFileService, $translate, DataUtils, $rootScope) {

    $scope.formView = {
        curriculum: "curriculum",
        version: "version"
    };

    $scope.displayedForm = $scope.formView.curriculum;


    // TODO: finish readonly
    // TODO: saving area and field of study

    $scope.removeFromArray = ArrayUtils.remove;


    var baseUrl = '/curriculum';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var id = $route.current.params.id;

    // TODO: when editing, curriculum still may not have some of these variables
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

    function getAllowedStudyLevels() {
        QueryUtils.endpoint('/school/studyLevels').get().$promise.then(function(response){
            var studyLevelCodes = response.studyLevels;
            $scope.studyLevels = [];
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

    // var previouAreaOfStudyCode = null;

    // $scope.getAreasOfStudy = function() {
    //     if(!previouAreaOfStudyCode || $scope.curriculum.areaOfStudy.code !== previouAreaOfStudyCode) {
    //         $scope.curriculum.areaOfStudy = undefined;
    //         $scope.curriculum.fieldOfStudy = undefined;
    //         previouAreaOfStudyCode = $scope.curriculum.areaOfStudy.code;
    //     }
    //     if($scope.curriculum.group) {
    //         Curriculum.getAreasOfStudyByGroupOfStudy($scope.curriculum.group.code, setValds);
    //     }
    // };

    $scope.getAreasOfStudy = function(listOfGroupsChanged) {
        if(listOfGroupsChanged) {
            $scope.curriculum.areaOfStudy = undefined;
        }
        if($scope.curriculum.group) {
            Curriculum.getAreasOfStudyByGroupOfStudy($scope.curriculum.group, setValds);
        }
    };

    $scope.clearIscedClass = function() {
        $scope.curriculum.iscedClass = undefined;
    };

    function setValds(response) {
        $scope.areasOfStudy = response;
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

        // if($scope.curriculum.iscedClass) {
        //     //TODO: does not work
        //     if($scope.curriculum.iscedClass.mainClassCode === "ISCED_VALD") {
        //         $scope.curriculum.areaOfStudy = $scope.curriculum.iscedClass;
        //         // $scope.curriculum.fieldOfStudy = null;
        //     } else {
        //         $scope.curriculum.fieldOfStudy = $scope.curriculum.iscedClass;
        //         Classifier.getParentsWithMainClass('ISCED_VALD', $scope.curriculum.iscedClass.code).$promise.then(function (response) {
        //             $scope.curriculum.areaOfStudy = response[0];
        //         });
        //     }
        // }
        if($scope.curriculum.iscedClass) {
            Classifier.getParentsWithMainClass('ISCED_VALD', $scope.curriculum.iscedClass).$promise.then(function (response) {
                $scope.curriculum.areaOfStudy = response[0];
            });
        }
    }


    //TODO: method currently not used
    function getDepartments() {
      QueryUtils.endpoint('/autocomplete/schooldepartments')
        .get({ lang: $translate.use().toUpperCase() }).$promise.then(function (result) {
          $scope.schoolDepartments = result.content;
        });
    }
    getDepartments();

    function getMyEhisSchool() {
      if($rootScope.currentUser && Session.school.id) {
        QueryUtils.endpoint("/school").get({id: Session.school.id}).$promise.then(function(response) {
          $scope.myEhisSchool = {code: response.ehisSchool};
        });
      }
    }
    getMyEhisSchool();


    $scope.removejointPartner = function(deletedPartner) {
        $scope.removeFromArray($scope.curriculum.jointPartners, deletedPartner);
        if(!deletedPartner.abroad) {
            $scope.removeFromArray($scope.curriculum.jointPartnersEhisSchools, deletedPartner.ehisSchool);
            if($scope.curriculum.jointMentor === deletedPartner.ehisSchool) {
                $scope.curriculum.jointMentor = undefined;
            }
        }
    };

    // --- Validation

    $scope.gradeRequired = function() {
        return !$scope.curriculum || !$scope.curriculum.origStudyLevel || $scope.curriculum.origStudyLevel !== 'OPPEASTE_514';
    };

    // TODO: when there are no parter schools, fields are still reqiured
    function curriculumFormIsValid() {
        return $scope.higherEducationCurriculumForm.$valid && $scope.curriculum.specialities.length > 0 && $scope.jointPartnersAdded();
    }

    $scope.jointPartnersAdded = function() {
        return !$scope.curriculum.joint || $scope.curriculum.jointPartners.length > 0;
    };

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
      // Saving works, but presentation do not
    //   if($scope.curriculum.areaOfStudy && !$scope.curriculum.iscedClass) {
    //       $scope.curriculum.iscedClass = $scope.curriculum.areaOfStudy;
    //   }
    // $scope.curriculum.iscedClass = $scope.curriculum.fieldOfStudy ? $scope.curriculum.fieldOfStudy : $scope.curriculum.areaOfStudy;
      createOrUpdate().then(function() {
         setVariablesForExistingCurriculum();
         message.info('main.messages.create.success');
      });
    };

    $scope.changeJointMentors = function() {
        if($scope.curriculum.abroad) {
            $scope.curriculum.jointPartnersEhisSchools = [];
        }
    };

    function createOrUpdate() {
        return $scope.curriculum.id ? $scope.curriculum.$update() : $scope.curriculum.$save();
    }

    function clearGradesIfNecessary() {
        if(!$scope.gradeRequired()) {
            $scope.curriculum.grades = [];
        }
    }

    $scope.delete = function () {
      $scope.curriculum.$delete().then(function () {
        message.info('main.messages.delete.success');
        $location.path(baseUrl);
      });
    };

    //  Joint Partners
    //TODO: fix exception
    function getEhisSchoolsSelection() {
        $scope.curriculum.jointPartnersEhisSchools = [];
        $rootScope.$watch('auth', function(){
            if($rootScope.auth) {
                QueryUtils.endpoint("/school").get({id: $rootScope.auth.school.id}).$promise.then(function(response){
                    $scope.myEhisSchool = {code: response.ehisSchool};
                    $scope.curriculum.jointPartnersEhisSchools.push($scope.myEhisSchool);
                    return;
                }).then(function(){
                    if($scope.curriculum.id && $scope.curriculum.joint) {
                        $scope.curriculum.jointPartners.forEach(function(it){
                            if(!it.abroad) {
                                $scope.curriculum.jointPartnersEhisSchools.push(it.ehisSchool);
                            }
                        });
                    }
                });
            }
        });
    }

    $scope.addJointPartner = function () {
        var jointPartner = {abroad: $scope.curriculum.abroad};
        if (jointPartner.abroad) {
            jointPartner.nameEt = $scope.curriculum.jointPartnerForeignNameEt;
            jointPartner.nameEn = $scope.curriculum.jointPartnerForeignNameEn;
        } else {
            jointPartner.ehisSchool = $scope.curriculum.jointPartnerEhisSchool;
            $scope.curriculum.jointPartnersEhisSchools.push(jointPartner.ehisSchool);
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

    $scope.openAddSpecialtyDialog = function (newSpecialty) {
      var DialogController = null;
      if (newSpecialty) {
        DialogController = function (scope) {
          scope.data = newSpecialty;
        };
      }
      dialogService.showDialog('views/templates/higher.education.curriculum.specialty.add.dialog.html', DialogController,
        function (submitScope) {
          var data = submitScope.data;
          if (!ArrayUtils.includes($scope.curriculum.specialities, data)) {
              data.referenceNumber = getReferenceNumber($scope.curriculum.specialities);
            $scope.curriculum.specialities.push(data);
          }
        });
    };

    $scope.removeSpeciality = function(speciality) {
        $scope.curriculum.versions.forEach(function(v){ 
            $scope.removeFromArray(v.specialitiesReferenceNumbers, speciality.referenceNumber);
            v.modules.forEach(function(m){
                $scope.removeFromArray(m.specialitiesReferenceNumbers, speciality.referenceNumber);
            });
        });
        $scope.removeFromArray($scope.curriculum.specialities, speciality);
    };

    $scope.openAddGradeDialog = function (newGrade) {
      var DialogController = null;
      if (newGrade) {
        DialogController = function (scope) {
          scope.data = newGrade;
        };
      }
      dialogService.showDialog('views/templates/higher.education.curriculum.grade.add.dialog.html', DialogController,
        function (submitScope) {
          var data = submitScope.data;
          if (!ArrayUtils.includes($scope.curriculum.grades, data)) {
            $scope.curriculum.grades.push(data);
          }
        });
    };

    $scope.openAddFileDialog = function () {
      dialogService.showDialog('views/templates/vocational.curriculum.file.add.dialog.html', null, function (submitScope) {
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
// TODO: version (?) can be edited only once

    function getReferenceNumber(list) {
        var existingReferenceNumbers = list.map(function(el){
            return el.referenceNumber;
        });
        var rand;
        while(true) {
            rand = Math.floor((Math.random() * 1000) + 1);
            if(!ArrayUtils.includes(existingReferenceNumbers), rand) {
                break;
            }
        }
        return rand;
    }

    $scope.goToVersionForm = function(version) {
        if(version) {
            $scope.version = version;
        } else {
            $scope.version = {
                modules: [],
                specialities: [],
                code: $scope.curriculum && $scope.curriculum.code ? $scope.curriculum.code + "/" + new Date().getFullYear() : null
            };
        }
        $scope.curriculumVersionCodeUniqueQuery = {
            fk: $scope.curriculum.id,
            paramName: 'code',
            url: baseUrl + '/version/unique'
        };
        $scope.displayedForm = $scope.formView.version;
    };

    $scope.goToCurriculumForm = function() {
        $scope.displayedForm = $scope.formView.curriculum;
    };

    $scope.saveVersion = function() {
        $scope.higherEducationCurriculumVersionForm.$setSubmitted();
        if (!versionFormIsValid()) {
            message.error('main.messages.form-has-errors');
            return;
        }
        if (!ArrayUtils.includes($scope.curriculum.versions, $scope.version)) {
            $scope.curriculum.versions.push($scope.version);
        }
        $scope.displayedForm = $scope.formView.curriculum;
    };

    function versionFormIsValid() {
        return $scope.higherEducationCurriculumVersionForm.$valid && $scope.anyModuleAdded();
    }

    $scope.anyModuleAdded = function() {
        return $scope.version.modules.filter(function(el){return !el.minorSpeciality;}).length > 0;
    };

    $scope.deleteSubject = function(module1, subject) {
        $scope.removeFromArray(module1.subjects, subject);
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

    //TODO: when editing and pressing back still saves all changes
    //TODO: when removing elective module, remove its subjects as well;
    $scope.openAddModuleDialog = function (newModule) {
      var DialogController = function (scope) {

            scope.specialities = $scope.curriculum.specialities.filter(function(s){
                return $scope.version.specialitiesReferenceNumbers.indexOf(s.referenceNumber) !== -1;
            });

            scope.addElectiveModule = function() {
                var newElectiveModule = {
                    nameEt: scope.data.optionalNameEt,
                    nameEn: scope.data.optionalNameEn,
                    referenceNumber: getReferenceNumber(scope.data.electiveModules),
                    subjects: []
                };
                scope.data.electiveModules.push(newElectiveModule);
                scope.data.optionalNameEt = undefined;
                scope.data.optionalNameEn = undefined;
            };
            scope.editingElectiveModule = false;
            var editedElectiveModule = null;

            scope.editElectiveModule = function (electiveModule) {
                editedElectiveModule = electiveModule;
                scope.editingElectiveModule = true;
                scope.data.optionalNameEt = electiveModule.nameEt;
                scope.data.optionalNameEn = electiveModule.nameEn;
            };

            scope.finishEditElectiveModule = function() {
                scope.editingElectiveModule = false;
                editedElectiveModule.nameEt = scope.data.optionalNameEt;
                editedElectiveModule.nameEn = scope.data.optionalNameEn;
                scope.data.optionalNameEt = undefined;
                scope.data.optionalNameEn = undefined;
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
                QueryUtils.endpoint('/subject')
                    .get({ lang: $translate.use().toUpperCase() }).$promise.then(function (result) {
                    scope.subjects = result.content;
                });
            }
            getSubjects();

            scope.deleteElectiveModule = function(electiveModule) {
                scope.data.subjects.forEach(function(e){
                    if(e.electiveModule === electiveModule.referenceNumber) {
                        e.electiveModule = undefined;
                    }
                });
                $scope.removeFromArray(scope.data.electiveModules, electiveModule);
            };

            scope.addSubject = function() {
                var newSubject = {
                    subjectId: scope.data.selectedSubject.id,
                    nameEt: scope.data.selectedSubject.nameEt,
                    nameEn: scope.data.selectedSubject.nameEn,
                    code: scope.data.selectedSubject.code,
                    credits: scope.data.selectedSubject.credits,
                    school: scope.data.selectedSubject.school,
                    optional: false
                };
                scope.data.subjects.push(newSubject);
                scope.data.selectedSubject = undefined;
                scope.setCompulsoryStudyCredits();
            };

            scope.setCompulsoryStudyCredits = function() {
                $scope.setCompulsoryAndTotalStudyCredits(scope.data);
            };

            scope.removeSubject = function(subject) {
                $scope.removeFromArray(scope.data.subjects, subject);
                scope.setCompulsoryStudyCredits();
            };

            if (newModule) {
                scope.data = newModule;
            } else {
                scope.data = {
                    electiveModules: [],
                    specialities: [],
                    subjects: [],
                    minorSpeciality: false,
                    optionalStudyCredits: 0,
                    totalCredits: 0,
                    compulsoryStudyCredits: 0,
                };
            }
        };
      dialogService.showDialog('views/templates/higher.education.curriculum.version.module.html', DialogController,
        function (submitScope) {
          var data = submitScope.data;
            if(data.type !== 'KORGMOODUL_M') {
                data.typeNameEt = null;
                data.typeNameEn = null;
            }
            if (!ArrayUtils.includes($scope.version.modules, data)) {
                $scope.version.modules.push(data);
            }
        });
    };


    //TODO: code duplication
    $scope.openAddMinorSpecialtyDialog = function (newMinorSpecialty) {

      var DialogController = function (scope) {

            scope.addElectiveModule = function() {
                var newElectiveModule = {
                    nameEt: scope.data.optionalNameEt,
                    nameEn: scope.data.optionalNameEn,
                    referenceNumber: getReferenceNumber(scope.data.electiveModules),
                    subjects: []
                };
                scope.data.electiveModules.push(newElectiveModule);
                scope.data.optionalNameEt = undefined;
                scope.data.optionalNameEn = undefined;
            };
            scope.editingElectiveModule = false;
            var editedElectiveModule = null;

            scope.editElectiveModule = function (electiveModule) {
                editedElectiveModule = electiveModule;
                scope.editingElectiveModule = true;
                scope.data.optionalNameEt = electiveModule.nameEt;
                scope.data.optionalNameEn = electiveModule.nameEn;
            };

            scope.finishEditElectiveModule = function() {
                scope.editingElectiveModule = false;
                editedElectiveModule.nameEt = scope.data.optionalNameEt;
                editedElectiveModule.nameEn = scope.data.optionalNameEn;
                scope.data.optionalNameEt = undefined;
                scope.data.optionalNameEn = undefined;
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
                QueryUtils.endpoint('/subject')
                    .get({ lang: $translate.use().toUpperCase() }).$promise.then(function (result) {
                    scope.subjects = result.content;
                });
            }
            getSubjects();

            scope.deleteElectiveModule = function(electiveModule) {
                scope.data.subjects.forEach(function(e){
                    if(e.electiveModule === electiveModule.referenceNumber) {
                        e.electiveModule = undefined;
                    }
                });
                $scope.removeFromArray(scope.data.electiveModules, electiveModule);
            };

            scope.addSubject = function() {
                var newSubject = {
                    subjectId: scope.data.selectedSubject.id,
                    nameEt: scope.data.selectedSubject.nameEt,
                    nameEn: scope.data.selectedSubject.nameEn,
                    code: scope.data.selectedSubject.code,
                    credits: scope.data.selectedSubject.credits,
                    school: scope.data.selectedSubject.school,
                    optional: false
                };
                scope.data.subjects.push(newSubject);
                scope.data.selectedSubject = undefined;
                scope.setCompulsoryStudyCredits();
            };

            scope.setCompulsoryStudyCredits = function() {
                $scope.setCompulsoryAndTotalStudyCredits(scope.data);
            };

            scope.removeSubject = function(subject) {
                $scope.removeFromArray(scope.data.subjects, subject);
                scope.setCompulsoryStudyCredits();
            };

            if (newMinorSpecialty) {
                scope.data = newMinorSpecialty;
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
      dialogService.showDialog('views/templates/higher.education.curriculum.version.minorSpecialty.html', DialogController,
        function (submitScope) {
          var data = submitScope.data;
          if (!ArrayUtils.includes($scope.version.modules, data)) {
            $scope.version.modules.push(data);
          }
        });
    };
  });
