'use strict';

angular.module('hitsaOis')
  .controller('MainController', function ($scope, $translate, $location, Menu, AuthService, $mdSidenav, $mdUtil,$rootScope, $mdDateLocale, $filter, $timeout, USER_ROLES) {

    var self = this;

    function closeMenu() {
      $timeout(function() { $mdSidenav('left').close(); });
    }

    function openMenu() {
      $timeout(function() { $mdSidenav('left').open(); });
    }

    function path() {
      return $location.path();
    }

    function scrollTop() {
      $mdUtil.animateScrollTo(scrollContentEl, 0, 200);
    }

    function goHome() {
      Menu.selectPage(null, null);
      $location.path( '/' );
    }

    function openPage() {
      $scope.closeMenu();

      if (self.autoFocusContent) {
        focusMainContent();
        self.autoFocusContent = false;
      }
    }

    function focusMainContent($event) {
      // prevent skip link from redirecting
      if ($event) { $event.preventDefault(); }

      $timeout(function(){
        mainContentArea.focus();
      },90);

    }

    function isSelected(page) {
      return Menu.isPageSelected(page);
    }

    function isSectionSelected(section) {
      var selected = false;
      var openedSection = Menu.openedSection;
      if(openedSection === section){
        selected = true;
      }
      else if(section.children) {
        section.children.forEach(function(childSection) {
          if(childSection === openedSection){
            selected = true;
          }
        });
      }
      return selected;
    }

    function isOpen(section) {
      return Menu.isSectionSelected(section);
    }

    function toggleOpen(section) {
      Menu.toggleSelectSection(section);
    }

    $scope.changeLanguage = function(languageCode) {
      $translate.use(languageCode);
    };

    $scope.currentLanguage = function() {
      return $translate.use();
    };

    // todo: those 2 methods
    $rootScope.currentLanguageNameVariable = function () {
      switch ($scope.currentLanguage()) {
        case 'en':
          return 'nameEn';
        case 'et':
          return 'nameEt';
        case 'ru':
          return 'nameRu';
        default:
          return 'nameEt';
      }
    };

     var _currentLanguageNameField = function (nameField, item) {
       return angular.isObject(item) ? (item[nameField] || item.nameEt) : undefined;
     };

    $scope.currentLanguageNameField = function(item) {
      var nameField = $scope.currentLanguageNameVariable();
      if(arguments.length === 0) {
        return nameField;
      }
      if (angular.isArray(item)) {
        return item.map(function (it) {
          return _currentLanguageNameField(nameField, it);
        }).join(', ');
      }
      return _currentLanguageNameField(nameField, item);
    };
    //make this function available in root scope as well
    $rootScope.currentLanguageNameField = $scope.currentLanguageNameField;

    $scope.leftSideNavToggle = function() {
      $mdSidenav('left').open();
    };

    $scope.leftSideNavSelect = function(path) {
      $location.path( path );
      if(!$mdSidenav('left').isLockedOpen()) {
        $mdSidenav('left').close();
      }
    };

    $scope.Menu = Menu;

    $scope.path = path;
    $scope.goHome = goHome;
    $scope.openMenu = openMenu;
    $scope.closeMenu = closeMenu;
    $scope.isSectionSelected = isSectionSelected;
    $scope.scrollTop = scrollTop;
    $rootScope.$on('$locationChangeSuccess', openPage);
    var mainContentArea = document.querySelector("[role='main']");
    var scrollContentEl = mainContentArea.querySelector('md-content[md-scroll-y]');
    //var content = document.querySelector("[role='content']");
    $scope.focusMainContent = focusMainContent;

    //-- Define a fake model for the related page selector
    Object.defineProperty($rootScope, "relatedPage", {
      get: function () { return null; },
      set: angular.noop,
      enumerable: true,
      configurable: true
    });

    $rootScope.redirectToUrl = function(url) {
      $location.path(url);
      $timeout(function () { $rootScope.relatedPage = null; }, 100);
    };

    // Methods used by menuLink and menuToggle directives
    this.isOpen = isOpen;
    this.isSelected = isSelected;
    this.toggleOpen = toggleOpen;
    this.autoFocusContent = false;


    //$rootScope.currentUser = null;
    $rootScope.userRoles = USER_ROLES;
    $rootScope.isAuthorized = AuthService.isAuthorized;

    $rootScope.setCurrentUser = function (user) {
      $rootScope.currentUser = user;
    };

    //Date picker date formats
    $rootScope.$on('$translateChangeSuccess', function (event, data) {
      if(data.language === 'et'){
        $mdDateLocale.months = ['jaanuar', 'veebruar', 'märts', 'aprill', 'mai', 'juuni', 'juuli', 'august', 'september', 'oktoober', 'november', 'detsember'];
        $mdDateLocale.shortMonths = ['jaan', 'veebr', 'märts', 'apr', 'mai', 'juuni', 'juuli', 'aug', 'sept', 'okt', 'nov', 'dets'];
        $mdDateLocale.days = ['pühapäev', 'esmaspäev', 'teisipäev', 'kolmapäev', 'neljapäev', 'reede', 'laupäev'];
        $mdDateLocale.shortDays = ['P', 'E', 'T', 'K', 'N', 'R', 'L'];
        $mdDateLocale.firstDayOfWeek = 1;
        $mdDateLocale.formatDate = function (date) {
          if(angular.isDefined(date)) {
            var m = moment(date);
            return m.isValid() ? m.format('DD.MM.YYYY') : null;
          } else {
            return null;
          }
        };
        $mdDateLocale.parseDate = function(dateString) {
          if(angular.isDefined(dateString)) {
            var m = moment(dateString, 'DD.MM.YYYY');
            return m.isValid() ? m.toDate() : new Date(NaN);
          } else {
            return new Date(NaN);
          }
        };
      } else {
        $mdDateLocale.months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
        $mdDateLocale.shortMonths = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
        $mdDateLocale.days = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
        $mdDateLocale.shortDays = ["S", "M", "T", "W", "T", "F", "S"];
        $mdDateLocale.firstDayOfWeek = 0;
        $mdDateLocale.formatDate = function (date) {
          if(angular.isDefined(date)) {
            var m = moment(date);
            return m.isValid() ? m.format('M/D/YYYY') : null;
          } else {
            return null;
          }
        };
        $mdDateLocale.parseDate = function(dateString) {
          if(angular.isDefined(dateString)) {
            var m = moment(dateString, 'M/D/YYYY');
            return m.isValid() ? m.toDate() : new Date(NaN);
          } else {
            return new Date(NaN);
          }
        };
      }
    });
  })
  .filter('nospace', function () {
    return function (value) {
      return (!value) ? '' : value.replace(/ /g, '');
    };
  }).filter('encodeURIComponent', function () {
    return function (value) {
      return window.encodeURIComponent(value);
    };
  });
