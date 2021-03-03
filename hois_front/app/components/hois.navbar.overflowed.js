'use strict';

/**
 * @ngdoc directive
 * @name hitsaOis.directive:hoisNavbarOverflowed
 * @description
 *
 * Used to fix a bug with overflowed navbar.
 * Created issue about it in github:
 * https://github.com/angular/material/issues/11747
 *
 * @since 25.02.2021 removed bug-fix with focused tab (angular material 1.1.22 fixed)
 */
angular.module('hitsaOis').directive('hoisNavbarOverflowed', function () {
  return {
    restrict: 'A',
    require: ['^mdNavBar'],
    link: {
      post: function(scope, element, attrs, controllers) {
        var mdNavBar = controllers[0];
        if (mdNavBar) {
          // Sets an active element at left side.
          if (mdNavBar._navBarEl.parentNode.nodeName === "MD-CONTENT") {
            mdNavBar._$timeout(function () {
              var selectedTab = mdNavBar._getSelectedTab();
              if (selectedTab) {
                mdNavBar._navBarEl.parentNode.scrollLeft = selectedTab._$element[0].offsetLeft;
              }
            }, 200);
          }
        }
      }
    }
  };
});
