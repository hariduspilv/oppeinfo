'use strict';

angular.module('hitsaOis')
  .factory('Menu', function(config, $location, $rootScope, USER_ROLES, $route) {

    var sections = [];

    function getAdminSections(authenticatedUser) {
      sections.push({
        name: 'main.menu.academicCalendar.label',
        type: 'link',
        url: "/academicCalendar"
      });

      sections.push({
        name: 'main.menu.viewTimetable.label',
        type: 'link',
        url: "/timetable/generalTimetableByGroup"
      });

      sections.push({
        name: 'main.menu.curriculum.label', // todo curricula-vs-curriculums
        type: 'toggle',
        pages: [
          {
            name: 'main.menu.curriculum.schoolCurriculums',
            url: "/curriculum?_menu"
          },
          {
            name: 'main.menu.curriculum.subjects',
            url: "/subject?_menu",
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.curriculum.stateCurriculums',
            url: "/stateCurriculum?_menu",
            studyLevel: {
              vocational: true
            }
          },
        ]
      });

      sections.push({
        name: 'main.menu.student.label',
        type: 'toggle',
        pages: [
          {
            name: 'main.menu.student.studentInfo',
            url: '/students?_menu'
          },
          {
            name: 'main.menu.student.studentGroups',
            url: '/studentgroups?_menu'
          },
          {
            name: 'main.menu.student.absences',
            url: '/absences?_menu',
            studyLevel: {
              vocational: true
            }
          }
        ]
      });

      sections.push({
        name: 'main.menu.teachers.label',
        type: 'link',
        url: "/teachers?_menu"
      });

      sections.push({
        name: 'main.menu.documents.label',
        type: 'toggle',
        pages: [
          {
            name: 'main.menu.documents.applications',
            url: "/applications?_menu",
          },
          {
            name: 'main.menu.documents.directives',
            url: "/directives?_menu"
          },
          {
            name: 'main.menu.documents.certificates',
            url: '/certificate?_menu'
          },
          {
            name: 'main.menu.documents.practiceContracts',
            url: "/contracts?_menu"
          },
          {
            name: 'main.menu.documents.representativeApplications',
            url: "/studentrepresentatives/applications?_menu",
          },
          {
            name: 'main.menu.documents.apel',
            url: "/apelApplication?_menu"
          }
        ]
      });

      sections.push({
        name: 'main.menu.study.label',
        type: 'toggle',
        pages: [
          {
            name: 'main.menu.study.declarations',
            url: "/declarations?_menu",
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.study.journal.journalsVocational',
            url: "/journals?_menu",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.study.practiceJournal.label',
            url: "/practiceJournals?_menu"
          },
          {
            name: 'main.menu.study.examTimes',
            url: '/examTimes',
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.study.moduleProtocol.label',
            url: "/moduleProtocols?_menu",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.study.protocol.search',
            url: "/higherProtocols?_menu",
            studyLevel: {
              higher: true
            }
          }
        ]
      });

      sections.push({
        name: 'main.menu.studyPreparation.label',
        type: 'toggle',
        pages: [
          {
            name: 'main.menu.studyPreparation.subjectTeacher',
            url: '/subjectStudyPeriods?_menu',
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.studyPreparation.studyYearSchedule.legend',
            url: "/studyYearScheduleLegend?_menu",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.studyPreparation.studyYearSchedule.edit',
            url: "/studyYearSchedule?_menu",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.studyPreparation.lessonTime',
            id: 'timetableLessonTimeSearch',
            url: "/timetable/lessonTime/search?_menu"
          },
          {
            name: 'main.menu.studyPreparation.lessonplans.vocational',
            id: 'timetableLessonTimeSearch',
            url: "/lessonplans/vocational?_menu",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.studyPreparation.subjectStudyPeriods.plans',
            url: "/subjectStudyPeriodPlans?_menu",
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.studyPreparation.subjectStudyPeriods.label',
            url: "/subjectStudyPeriods/studentGroups?_menu",
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.studyPreparation.timetableManagement.label',
            url: "/timetable/timetableManagement?_menu"
          },
          {
            name: 'main.menu.studyPreparation.events.label',
            url: "/lessonplans/events?_menu"
          }
        ]
      });

      sections = sections.concat(getScholarshipSectionForAdmin(authenticatedUser));

      sections.push({
        name: 'main.menu.reports.label',
        type: 'toggle',
        pages: [
          {
            name: 'main.menu.reports.students',
            url: "/reports/students/students?_menu"
          },
          {
            name: 'main.menu.reports.studentstatistics',
            url: "/reports/students/statistics?_menu"
          },
          {
            name: 'main.menu.reports.studentstatisticsbyperiod',
            url: "/reports/students/statistics/byperiod?_menu"
          },
          {
            name: 'main.menu.reports.curriculumscompletion',
            url: "/reports/curriculums/completion?_menu"
          },
          {
            name: 'main.menu.reports.teachersloadvocational',
            url: "/reports/teachers/load/vocational?_menu",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.reports.teachersloadhigher',
            url: "/reports/teachers/load/higher?_menu",
            studyLevel: {
              higher: true
            }
          }
        ]
      });

      sections.push({
        name: 'main.menu.graduation.label',
        type: 'toggle',
        pages: [
          {
            name: 'main.menu.graduation.committees',
            url: "/committees?_menu"
          },
          {
            name: 'main.menu.graduation.thesisTopics',
            url: "/thesisTopics",
          },
          {
            name: 'main.menu.graduation.thesisProtocols',
            url: "/thesisProtocols",
          },
          {
            name: 'main.menu.graduation.forms',
            url: "/forms",
          },
          {
            name: 'main.menu.graduation.documentsPreparation',
            url: "/documentsPreparation"
          },
          {
            name: 'main.menu.graduation.documentsPrint',
            url: "/documentsPrint"
          }
        ]
      });

      sections.push({
        name: 'main.menu.dataexchange.label',
        type: 'toggle',
        pages: [
          {
            name: 'main.menu.dataexchange.teacherinfoExportHigher',
            id: 'teacherinfoExport',
            url: "/ehis/teacher/export/higher?_menu",
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.dataexchange.teacherinfoExportVocational',
            id: 'teacherinfoExport',
            url: "/ehis/teacher/export/vocational?_menu",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.dataexchange.studentinfoExport',
            url: "/ehis/student/export?_menu"
          },
          {
            name: 'main.menu.dataexchange.kutseregister',
            url: "/occupationcertificate/import?_menu",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.dataexchange.rtip',
            url: "/rtip/sync?_menu"
          },
          {
            name: 'main.menu.dataexchange.ehisLogs',
            url: "/ehis/logs?_menu"
          },
          {
            name: 'main.menu.dataexchange.ekisLogs',
            url: "/ekis/logs?_menu"
          },
          {
            name: 'main.menu.dataexchange.kutseregisterLogs',
            url: "/kutseregister/logs?_menu",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.dataexchange.rtipLogs',
            url: "/rtip/logs?_menu"
          },
          {
            name: 'main.menu.dataexchange.saisLogs',
            url: "/sais/logs?_menu"
          }
        ]
      });

      sections.push({
        name: 'main.menu.reception.label',
        type: 'toggle',
        pages: [
          {
            name: 'main.menu.reception.saisAdmission.label',
            id: 'receptionSaisAdmissionSearch',
            url: "/reception/saisAdmission/search?_menu"
          },
          {
            name: 'main.menu.reception.saisApplication.label',
            id: 'receptionSaisApplicationSearch',
            url: "/reception/saisApplication/search?_menu"
          },
          {
            name: 'main.menu.reception.saisAdmission.importSais',
            id: 'receptionSaisAdmissionImport',
            url: "/reception/saisAdmission/import?_menu"
          },
          {
            name: 'main.menu.reception.saisApplication.importSais',
            id: 'receptionSaisApplicationImport',
            url: "/reception/saisApplication/import?_menu"
          },
        ]
      });

      sections.push({
        name: 'main.menu.settings.label',
        type: 'toggle',
        pages: [
          {
            name: 'main.menu.settings.studyYears',
            url: "/school/studyYears"
          },
          {
            name: 'main.menu.settings.studyLevels',
            url: "/school/studyLevels"
          },
          {
            name: 'main.menu.settings.generalmessages',
            url: "/generalmessages?_menu"
          },
          {
            name: 'main.menu.settings.messageTemplates',
            url: "/messageTemplate?_menu"
          },
          {
            name: 'main.menu.settings.documentcoordinators',
            url: "/directives/coordinators?_menu"
          },
          {
            name: 'main.menu.settings.teacheroccupations',
            url: "/school/teacheroccupations?_menu"
          },
          {
            name: 'main.menu.settings.buildings',
            url: "/rooms/search?_menu"
          },
          {
            name: 'main.menu.settings.departments',
            url: "/school/departments?_menu"
          },
          {
            name: 'main.menu.settings.users',
            id: 'users',
            url: "/persons?_menu"
          },
          {
            name: 'main.menu.settings.apelSchools',
            url: "/apelSchools?_menu"
          }
        ]
      });
    }

    function getTeacherSections() {
      sections.push({
        name: 'main.menu.curriculums.label',
        url: "/curriculum?_menu",
        type: 'link',
      });

      sections.push({
        name: 'main.menu.subjects.label',
        url: "/subject?_menu",
        type: 'link',
        studyLevel: {
          higher: true
        }
      });

      sections.push({
        name: 'main.menu.timetableAndEvents.label',
        type: 'toggle',
        pages: [
          {
            name: 'main.menu.timetableAndEvents.timetable',
            url: "/timetable/personalGeneralTimetable?_menu"
          },
          {
            name: 'main.menu.timetableAndEvents.events',
            url: "/lessonplans/events?_menu"
          }
        ]
      });

      sections.push({
        name: 'main.menu.myData.label',
        url: "/teachers/myData",
        type: 'link'
      });

      sections.push({
        name: 'main.menu.academicCalendar.label',
        type: 'link',
        url: "/academicCalendar"
      });

      sections.push({
        name: 'main.menu.study.label',
        type: 'toggle',
        pages: [
          {
            name: 'main.menu.study.exams',
            url: "/examTimes",
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.study.absences',
            url: "/absences?_menu"
          },
          {
            name: 'main.menu.study.journal.journalsVocational',
            url: "/journals?_menu",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.study.journal.journalsHigher',
            url: "/subjectStudyPeriods?_menu",
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.study.loads',
            url: "/lessonplans/vocational"
          },
          {
            name: 'main.menu.study.students',
            url: "/students?_menu"
          },
          {
            name: 'main.menu.study.moduleProtocol.label',
            url: "/moduleProtocols?_menu",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.study.protocol.search',
            url: "/higherProtocols?_menu",
            studyLevel: {
              higher: true
            }
          }
        ]
      });

      sections.push({
        name: 'main.menu.practiceAndGraduation.label',
        type: 'toggle',
        pages: [
          {
            name: 'main.menu.practiceAndGraduation.practiceJournal',
            url: '/practiceJournals?_menu'
          },
          {
            name: 'main.menu.practiceAndGraduation.committees',
            url: '/committees?_menu'
          },
          {
            name: 'main.menu.practiceAndGraduation.thesisProtocols',
            url: '/thesisProtocols'
          },
          {
            name: 'main.menu.practiceAndGraduation.thesisTopics',
            url: '/thesisTopics'
          },
        ]
      });
    }

    function getMainAdminSections() {
      sections.push({
        name: 'main.menu.curriculum.label',
        type: 'toggle',
        pages: [
          {
            name: 'main.menu.curriculum.stateCurriculums',
            url: "/stateCurriculum?_menu"
          },
        ]
      });

      sections.push({
        name: 'main.menu.mainData.label',
        type: 'toggle',
        pages: [
          {
            name: 'main.menu.mainData.schools',
            url: "/school?_menu"
          },
          {
            name: 'main.menu.mainData.classifiers',
            url: "/classifier?_menu"
          },
          {
            name: 'main.menu.mainData.saisClassifiers',
            url: "/saisClassifier?_menu"
          },
          {
            name: 'main.menu.mainData.occupationStandards',
            url: "/occupationstandard?_menu"
          },
          {
            name: 'main.menu.mainData.users',
            url: "/persons?_menu"
          },
        ]
      });

      sections.push({
        name: 'main.menu.timetableLink.label',
        type: 'link',
        url: '/timetable/generalTimetableByGroup?_menu'
      });
    }

    function getStudentSections(authenticatedUser) {
      sections.push({
        name: 'main.menu.curriculums.label',
        url: "/curriculum?_menu",
        type: 'link'
      });

      sections.push({
        name: 'main.menu.subjects.label',
        url: "/subject?_menu",
        type: 'link'
      });

      sections.push({
        name: 'main.menu.timetableLink.label',
        type: 'link',
        url: '/timetable/personalGeneralTimetable?_menu'
      });

      sections.push({
        name: 'main.menu.myData.label',
        url: "/students/myData",
        type: 'link'
      });

      sections.push({
        name: 'main.menu.academicCalendar.label',
        type: 'link',
        url: "/academicCalendar"
      });

      sections.push({
        name: 'main.menu.myStudyInformation.label',
        type: 'toggle',
        pages: [
          {
            name: 'main.menu.myStudyInformation.journal',
            url: '/students/journals',
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.myStudyInformation.practiceJournal',
            url: '/practiceJournals?_menu'
          },
          {
            name: 'main.menu.myStudyInformation.results',
            url: '/students/myResults'
          },
          {
            name: 'main.menu.myStudyInformation.declaration',
            url: '/declaration/current/view',
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.myStudyInformation.examRegistration',
            url: '/examRegistration',
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.myStudyInformation.thesisTopicInput',
            url: '/thesisTopicInput'
          },
          {
            name: 'main.menu.myStudyInformation.otherStudents',
            url: '/students?_menu'
          }
        ]
      });

      sections = sections.concat(getScholarshipSectionForStudent(authenticatedUser));

      sections.push({
        name: 'main.menu.documents.label',
        type: 'toggle',
        pages: [
          {
            name: 'main.menu.documents.applications',
            url: "/applications/student?_menu",
          },
          {
            name: 'main.menu.documents.certificates',
            url: '/certificate?_menu'
          },
          {
            name: 'main.menu.documents.practiceContracts',
            url: "/contracts?_menu"
          },
          {
            name: 'main.menu.documents.apel',
            url: "/apelApplication?_menu"
          }
        ]
      });
    }

    function getExternalExpertSections() {
      sections.push({
        name: 'main.menu.curriculum.label', // todo curricula-vs-curriculums
        type: 'toggle',
        pages: [
          {
            name: 'main.menu.curriculum.schoolCurriculums',
            url: "/curriculum?_menu"
          },
          {
            name: 'main.menu.curriculum.stateCurriculums',
            url: "/stateCurriculum?_menu",
          },
        ]
      });
      sections.push({
        name: 'main.menu.teachers.label',
        type: 'link',
        url: "/teachers?_menu"
      });
    }

    function getParentSections() {
      sections.push({
        name: 'main.menu.curriculums.label',
        url: "/curriculum?_menu",
        type: 'link',
      });

      sections.push({
        name: 'main.menu.subjects.label',
        url: "/subject?_menu",
        type: 'link',
        studyLevel: {
          higher: true
        }
      });

      sections.push({
        name: 'main.menu.timetableLink.label',
        type: 'link',
        url: '/timetable/personalGeneralTimetable?_menu'
      });

      sections.push({
        name: 'main.menu.studentRepresentative.label',
        type: 'link',
        url: '/students/myData'
      });

      sections.push({
        name: 'main.menu.academicCalendar.label',
        type: 'link',
        url: "/academicCalendar"
      });

      sections.push({
        name: 'main.menu.studentStudyInformation.label',
        type: 'toggle',
        pages: [
          {
            name: 'main.menu.studentStudyInformation.journal',
            url: '/students/journals',
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.studentStudyInformation.practiceJournal',
            url: "/practiceJournals?_menu"
          },
          {
            name: 'main.menu.studentStudyInformation.results',
            url: '/students/myResults'
          }
        ]
      });

      sections.push({
        name: 'main.menu.documents.label',
        type: 'toggle',
        pages: [
          {
            name: 'main.menu.documents.applications',
            url: "/applications/student?_menu",
          },
          {
            name: 'main.menu.documents.certificates',
            url: '/certificate?_menu'
          }
        ]
      });
    }

    var self;
    var menu = [];

    function getScholarshipSectionForAdmin(authenticatedUser) {
      var result = [];
      if(authenticatedUser.higher && authenticatedUser.vocational) {
        result.push({
          name: 'main.menu.scholarships.label',
          type: 'toggle',
          pages: [
            {
              name: 'main.menu.scholarships.grants',
              url: "/scholarships/grants"
            },
            {
              name: 'main.menu.scholarships.grantApplications',
              url: "/scholarships/applications/grants"
            },
            {
              name: 'main.menu.scholarships.scholarships',
              url: "/scholarships/scholarships"
            },
            {
              name: 'main.menu.scholarships.scholarshipApplications',
              url: "/scholarships/applications/scholarships"
            },
            {
              name: 'main.menu.scholarships.drGrants',
              url: "/scholarships"
            }
          ]
        });
      } else if (authenticatedUser.higher) {
        result.push({
          name: 'main.menu.scholarships.scholarships',
          type: 'link',
          url: "/scholarships/scholarships"
        });
        result.push({
          name: 'main.menu.scholarships.scholarshipApplications',
          type: 'link',
          url: "/scholarships/applications/scholarships"
        });
        result.push({
          name: 'main.menu.scholarships.drGrants',
          type: 'link',
          url: "/scholarships"
        });
        result.push({
          name: 'main.menu.scholarships.grantApplications',
          type: 'link',
          url: "/scholarships/applications/grants"
        });
      } else {
        result.push({
          name: 'main.menu.scholarships.grants',
          type: 'link',
          url: "/scholarships/grants"
        });
        result.push({
          name: 'main.menu.scholarships.grantApplications',
          type: 'link',
          url: "/scholarships/applications/grants"
        });
      }
      return result;
    }

    function getScholarshipSectionForStudent(authenticatedUser) {
      var result = [];
      if (authenticatedUser.higher) {
        result.push({
          name: 'main.menu.scholarships.scholarships',
          type: 'link',
          url: "/scholarships/myData/scholarships"
        });
        result.push({
          name: 'main.menu.scholarships.drGrants',
          type: 'link',
          url: "/scholarships/myData/drGrants"
        });
      } else {
        result.push({
          name: 'main.menu.scholarships.grants',
          type: 'link',
          url: "/scholarships/myData/grants"
        });
      }
      return result;
    }


    function onLocationChange() {
      var path = $location.path();

      if (path === '/') {
        self.selectSection(null);
        self.selectPage(null, null);
        return;
      }
      var matchPage = function(section, page) {
        if (page.url.replace(/(\?_menu)$/, '') === path) {
          //console.log("matched path ", path, " to page url ", page.url);
          self.selectSection(section);
          self.selectPage(section, page);
          return true;
        }
        return false;
      };

      //sections.forEach(function(section) {
      var match = false;
      for(var i = 0; i < menu.length && !match; i++) {
        var section = menu[i];
        if (section.children) {
          // matches nested section toggles, such as API or Customization
          for (var childIndex = 0; childIndex < section.children.length && !match; childIndex++) {
            var childSection = section.children[childIndex];
            if(childSection.pages) {
              for (var childSectionPageIndex = 0; childSectionPageIndex < childSection.pages.length  && !match; childSectionPageIndex++) {
                match = matchPage(childSection, childSection.pages[childSectionPageIndex]);
              }
            }
          }
        }
        else if (section.pages) {
          // matches top-level section toggles, such as Demos
          for (var sectionPageIndex = 0; sectionPageIndex < section.pages.length && !match; sectionPageIndex++) {
            match = matchPage(section, section.pages[sectionPageIndex]);
          }
        }
        else if (section.type === 'link') {
          // matches top-level links, such as "Getting Started"
          match = matchPage(section, section);
        }
      }


    }
    $rootScope.$on('$locationChangeSuccess', onLocationChange);

    self = {
      sections: {},

      setMenu: function (authenticatedUser) {
        buildMenu(authenticatedUser);
      },
      selectSection: function(section) {
        self.openedSection = section;
      },
      toggleSelectSection: function(section) {
        self.openedSection = (self.openedSection === section ? null : section);
      },
      isSectionSelected: function(section) {
        return self.openedSection === section;
      },

      selectPage: function(section, page) {
        self.currentSection = section;
        self.currentPage = page;
      },
      isPageSelected: function(page) {
        return self.currentPage === page;
      }
    };
    return self;

    function _canAccess(section, roles) {
      var hasAccess = false;
      var uri = section.url.replace(/(\?_menu)$/, '');
      var routes = $route.routes;
      var rights = null;
      for (var key in routes) {
        if (routes.hasOwnProperty(key) && routes[key].regexp) {
          if (routes[key].regexp.test(uri)) {
            rights = routes[key];
            break;
          }
        }
      }
      if (rights && rights.hasOwnProperty('data')) {
        rights = rights.data.authorizedRoles;
        if (rights.lastIndexOf('') !== -1) {
          hasAccess = true;
        } else {
          for (var i = 0; i < rights.length; i++) {
            if (roles.indexOf(rights[i]) !== -1) {
              hasAccess = true;
              break;
            }
          }
        }
      } else {
        // all can see if no right
        hasAccess = true;
      }
      return hasAccess;
    }


    function studyLevelMatch(section, authenticatedUser) {
        if(angular.isDefined(section.studyLevel) && angular.isDefined(authenticatedUser.school) && authenticatedUser.school !== null) {
            return authenticatedUser.higher && section.studyLevel.higher ||
            authenticatedUser.vocational && section.studyLevel.vocational;
        }
        return true;
    }

    function addToggleItem(pages, section, roles, authenticatedUser) {
      if (section.pages.length > 0) {
        for (var j = 0; j < section.pages.length; j++) {
          addSubmenuItem(pages, section.pages[j], roles, authenticatedUser);
        }
      }
      if (pages.length > 0) {
        menu.push({
          name: section.name,
          type: 'toggle',
          pages: pages
        });
      }
    }

    function addLinkItem(section, roles, authenticatedUser) {
      if (!_canAccess(section, roles) || !section.url) {
        return;
      }
      if (!studyLevelMatch(section, authenticatedUser)) {
          return;
      }

      menu.push({
        name: section.name,
        type: section.type,
        url: section.url
      });
    }

    function addSubmenuItem(pages, section, roles, authenticatedUser) {
      if (!_canAccess(section, roles) || !section.url) {
        return;
      }
      if (!studyLevelMatch(section, authenticatedUser)) {
          return;
      }

      pages.push({
        name: section.name,
        id: section.id,
        url: section.url
      });
    }

    function getUserSections(authenticatedUser) {
      sections = [];

      switch(authenticatedUser.roleCode) {
        case "ROLL_A":
          getAdminSections(authenticatedUser);
          break;
        case "ROLL_P":
          getMainAdminSections();
          break;
        case "ROLL_O":
          getTeacherSections();
          break;
        case "ROLL_T":
          getStudentSections(authenticatedUser);
          break;
        case "ROLL_L":
          getParentSections();
          break;
        case "ROLL_V":
          getExternalExpertSections();
          break;
      }
    }

    // todo: generalize
    function buildMenu(authenticatedUser) {
      var roles = authenticatedUser.authorizedRoles || [''];
      menu = [];

      getUserSections(authenticatedUser);

      for (var i = 0; i < sections.length; i++) {
        var pages = [];

        if (sections[i].type === 'toggle') {
          addToggleItem(pages, sections[i], roles, authenticatedUser);
        } else if (sections[i].type === 'link') {
          addLinkItem(sections[i], roles, authenticatedUser);
        }
      }
      self.sections = menu;
      onLocationChange();
    }
  });
