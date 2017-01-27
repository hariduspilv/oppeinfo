'use strict';

angular.module('hitsaOis')
  .factory('Menu', function(config, $location, $rootScope) {

    var sections = [];
    sections.push({
      name: 'main.menu.curriculum.label', // todo curricula-vs-curriculums
      type: 'toggle',
      pages: [
        {name: 'main.menu.curriculum.label', url: "/curriculum", access: ['']},
        //{name: 'Õppejõud'},
        {name: 'Õppeained', url: "/subject?_menu"},
        //{name: 'Tunniplaan', access: ['']},
        //{name: 'Vilistlased'},
        {name: 'Riiklikud õppekavad', url: "/stateCurriculum"},
        //{name: 'Riikliku õppekava lisamine', url: "/stateCurriculum/new"},
        //{name: 'Õppekavade otsing', url: "/curriculum"},
        {name: 'Kutseõppe õppekava sisestamine', url: "/vocationalCurriculum/new"},
        {name: 'Kõrgharidusõppe õppekava sisestamine', url: "/higherEducationCurriculum/new"}
      ]
    });

    sections.push({
      name: 'main.menu.fixData.label', // todo rename fixData
      type: 'toggle',
      pages: [{
          name: 'main.menu.fixData.schools',
          id: 'schools',
          url: "/school?_menu"
        },
        {
          name: 'main.menu.fixData.buildings',
          url: "/buildings?_menu"
        },
        {
          name: 'main.menu.fixData.searchrooms',
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
          name: 'main.menu.fixData.studyLevels',
          url: "/school/studyLevels"
        },
        {
          name: 'main.menu.fixData.classifiers',
          id: 'classifiers',
          url: "/classifier?_menu"
        }
      ]
    });

    sections.push({
      name: 'main.menu.more.label',
      type: 'toggle',
      pages: [
      ]
    });

    var _specialMenuPoints = {
      ':{school.id}': function (url, auth) {
        if (auth.hasOwnProperty('school') && auth.school !== null) {
          return url.replace(':{school.id}', auth.school.id);
        } else {
          return null;
        }
      }
    };

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
        if (path.indexOf(page.url.replace(/(\?_menu)$/, '')) !== -1) {
          self.selectSection(section);
          self.selectPage(section, page);
        }
      };

      //sections.forEach(function(section) {
      menu.forEach(function(section) {
        if (section.children) {
          // matches nested section toggles, such as API or Customization
          section.children.forEach(function(childSection){
            if(childSection.pages){
              childSection.pages.forEach(function(page){
                matchPage(childSection, page);
              });
            }
          });
        }
        else if (section.pages) {
          // matches top-level section toggles, such as Demos
          section.pages.forEach(function(page) {
            matchPage(section, page);
          });
        }
        else if (section.type === 'link') {
          // matches top-level links, such as "Getting Started"
          matchPage(section, section);
        }
      });
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
      if (section.hasOwnProperty('access')) {
        if (section.access.lastIndexOf('') !== -1) {
          hasAccess = true;
        } else {
          for (var i = 0; i < section.access.length; i++) {
            if (roles.indexOf(section.access[i]) !== -1) {
              hasAccess = true;
              break;
            }
          }
        }
      } else {
        // todo this is currently broken if and should be fixed during general roles set
        if (roles.length === 1 && roles.indexOf('') === -1) {
          hasAccess = true;
        }
      }
      return hasAccess;
    }


    function addSubmenuItem(pages, section, roles, auth) {
      if (!_canAccess(section, roles)) {
        return;
      }

      // can we display the menu aka url does not contain special syntax
      var keys = Object.keys(_specialMenuPoints);
      var url = section.url;
      if (url) { // todo remove
        for (var i = 0; (i < keys.length && url !== null); i++) {
          if (url.includes(keys[i])) {
            url = _specialMenuPoints[keys[i]](url, auth);
          }
        }
        if (url === null) {
          return;
        }
      }

      pages.push({
        name: section.name,
        id: section.id,
        url: url
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
            addSubmenuItem(pages, sections[i].pages[j], roles, auth);
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
