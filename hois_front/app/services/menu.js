'use strict';

angular.module('hitsaOis')
  .factory('Menu', function(config, $location, $rootScope, USER_ROLES, $route) {

    var sections = [];
    sections.push({
      name: 'main.menu.curriculum.label', // todo curricula-vs-curriculums
      type: 'toggle',
      pages: [
        {
          name: 'main.menu.curriculum.label',
          url: "/curriculum?_menu"
        },
        //{name: 'Õppejõud'},
        {
          name: 'Õppeained',
          url: "/subject?_menu",
          studyLevel: {
              higher: true
          }
        },
        //{name: 'Tunniplaan', access: ['']},
        //{name: 'Vilistlased'},
        {
          name: 'Riiklikud õppekavad',
          url: "/stateCurriculum?_menu"
        },
        //{name: 'Riikliku õppekava lisamine', url: "/stateCurriculum/new"},
        //{name: 'Õppekavade otsing', url: "/curriculum"},
        {
          name: 'Kutseõppe õk sisestamine',
          url: "/vocationalCurriculum/new",
          studyLevel: {
              vocational: true
          }
        },
        {
          name: 'Kõrgharidusõppe õk sisestamine',
          url: "/higherCurriculum/new",
          studyLevel: {
              higher: true
          }
        }
      ]
    });

    sections.push({
        name: 'main.menu.directives.label',
        type: 'toggle',
        pages: [
          {
            name: 'main.menu.directives.search',
            url: "/directives?_menu"
          }
        ]
      });

    sections.push({
        name: 'main.menu.studentGroup.label',
        type: 'toggle',
        pages: [
          {
            name: 'main.menu.studentGroup.search',
            url: "/studentgroups?_menu"
          }
        ]
      });

    sections.push({
        name: 'main.menu.student.label',
        type: 'toggle',
        pages: [
          {
            name: 'main.menu.student.search',
            url: "/students?_menu"
          },
          {
            name: 'Esindajate avaldused',
            url: "/studentrepresentatives/applications?_menu",
            studyLevel: {
                vocational: true
            }
          },
          {
            name: 'Esindajaks saamise avaldus',
            url: "/studentrepresentatives/applications/new?_menu",
            studyLevel: {
                vocational: true
            }
          }
        ]
      });

    sections.push({
      name: 'main.menu.teachers.label',
      type: 'toggle',
      pages: [
        {
          name: 'main.menu.teachers.search',
          url: "/teachers?_menu"
        },
        {
          name: 'main.menu.subjectStudyPeriods.search',
          url: "/subjectStudyPeriods?_menu"
        }]
    });

    sections.push({
      name: 'main.menu.fixData.label', // todo rename fixData
      type: 'toggle',
      pages: [
        {
          name: 'main.menu.fixData.schools',
          id: 'schools',
          url: "/school?_menu"
        },
        {
          name: 'main.menu.fixData.buildings',
          url: "/rooms/search?_menu"
        },
        {
          name: 'main.menu.fixData.departments',
          url: "/school/departments?_menu"
        },
        {
          name: 'main.menu.fixData.teacheroccupations',
          url: "/school/teacheroccupations?_menu"
        },
        {
          name: 'main.menu.fixData.directivecoordinators',
          url: "/directives/coordinators?_menu"
        },
        {
          name: 'main.menu.fixData.generalmessages',
          url: "/generalmessages?_menu"
        },
        {
          name: 'main.menu.fixData.messageTemplates',
          url: "/messageTemplate?_menu"
        },
        {
          name: 'main.menu.fixData.studyLevels',
          url: "/school/studyLevels"
        },
        {
          name: 'main.menu.fixData.studyYears',
          url: "/school/studyYears"
        },
        {
          name: 'main.menu.fixData.classifiers',
          id: 'classifiers',
          url: "/classifier?_menu"
        },
        {
          name: 'main.menu.fixData.saisClassifiers',
          id: 'saisClassifier',
          url: "/saisClassifier?_menu"
        },
        {
          name: 'main.menu.fixData.users',
          id: 'users',
          url: "/persons?_menu"
        }
      ]
    });

    sections.push({
      name: 'main.menu.applications.label',
      type: 'toggle',
      pages: [
        {
          name: 'main.menu.applications.search',
          id: 'applications',
          url: "/applications?_menu"
        },
        {
          name: 'main.menu.applications.student.list',
          id: 'studentApplicationsList',
          url: "/applications/student?_menu"
        }
      ]
    });

    sections.push({
      name: 'main.menu.study.label',
      type: 'toggle',
      pages: [
        {
          name: 'main.menu.study.journal.search',
          url: "/journals?_menu"
        },
        {
          name: 'main.menu.certificates.label',
          url: "/certificate?_menu"
        },
        {
          name: 'main.menu.study.notifications',
          url: "/messages/received?_menu"
        },
        {
          name: 'main.menu.studyYearSchedule.edit',
          url: "/studyYearSchedule"
        },
        {
          name: 'main.menu.studyYearSchedule.legend',
          url: "/studyYearScheduleLegend"
        }
      ]
    });


    sections.push({
      name: 'main.menu.reception.label',
      type: 'toggle',
      pages: [
        {
          name: 'main.menu.reception.saisAdmission.search',
          id: 'receptionSaisAdmissionSearch',
          url: "/reception/saisAdmission/search?_menu"
        },
        {
          name: 'main.menu.reception.saisAdmission.importSais',
          id: 'receptionSaisAdmissionImport',
          url: "/reception/saisAdmission/import?_menu"
        },
        {
          name: 'main.menu.reception.saisApplication.search',
          id: 'receptionSaisApplicationSearch',
          url: "/reception/saisApplication/search?_menu"
        },
        {
          name: 'main.menu.reception.saisApplication.importSais',
          id: 'receptionSaisApplicationImport',
          url: "/reception/saisApplication/import?_menu"
        },
      ]
    });

    sections.push({
      name: 'main.menu.timetable.label',
      type: 'toggle',
      pages: [
        {
          name: 'main.menu.timetable.lessonTime.search',
          id: 'timetableLessonTimeSearch',
          url: "/timetable/lessonTime/search?_menu"
        },
        {
          name: 'main.menu.lessonplan.label',
          id: 'timetableLessonPlanSearch',
          url: "/lessonplans/vocational?_menu"
        },
        {
          name: 'main.menu.subjectStudyPeriods.plans',
          url: "/subjectStudyPeriodPlans?_menu"
        },
        {
          name: 'main.menu.subjectStudyPeriods.label',
          url: "/subjectStudyPeriods/studentGroups?_menu"
        }
      ]
    });

    sections.push({
      name: 'main.menu.more.label',
      type: 'toggle',
      pages: [
      ]
    });

    var self;
    var menu = [];

    function onLocationChange() {
      var path = $location.path();

      if (path === '/') {
        self.selectSection(null);
        self.selectPage(null, null);
        return;
      }
      var matchPage = function(section, page) {
        if (page.url.replace(/(\?_menu)$/, '').indexOf(path) === 0) {
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

      setMenu: function (auth) {
        buildMenu(auth);
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


    function studyLevelMatch(section, school) { 
        if(angular.isDefined(section.studyLevel) && angular.isDefined(school)) {
            return school.higher && section.studyLevel.higher ||
                   school.vocational && section.studyLevel.vocational;
        }
        return true;
    }


    function addSubmenuItem(pages, section, roles, school) {
      if (!_canAccess(section, roles) || !section.url) {
        return;
      } 
      if (!studyLevelMatch(section, school)) {
          return;
      }

      pages.push({
        name: section.name,
        id: section.id,
        url: section.url
      });
    }

    // todo: generalize
    function buildMenu(auth) {
      var roles = auth.authorizedRoles || [''];
      menu = [];
      for (var i = 0; i < sections.length; i++) {
        var pages = [];
        if (sections[i].pages.length > 0) {
          for (var j = 0; j < sections[i].pages.length; j++) {
            addSubmenuItem(pages, sections[i].pages[j], roles, auth.school);
          }
        }
        if (pages.length > 0) {
          menu.push({
            name: sections[i].name,
            type: 'toggle',
            pages: pages
          });
        }
      }
      self.sections = menu;
      onLocationChange();
    }
  });
