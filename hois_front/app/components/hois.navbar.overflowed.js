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
 * @deprecated after angular-material 1.1.21 version this has been fixed.
 */
angular.module('hitsaOis').directive('hoisNavbarOverflowed', function () {
  return {
    restrict: 'A',
    require: ['^mdNavBar'],
    link: {
      post: function(scope, element, attrs, controllers) {
        var mdNavBar = controllers[0];
        if (mdNavBar) {
          // Copied from 1.1.18 angular-material
          //var _moveFocus = mdNavBar._moveFocus;
          mdNavBar._moveFocus = function(oldTab, newTab) {
            if (oldTab) {
              oldTab.setFocused(false);
            }
            newTab.setFocused(true);
          };

          // Copied from 1.1.18 angular-material
          //var _updateTabs = mdNavBar._updateTabs;
          mdNavBar._updateTabs = function(newValue, oldValue) {
            var self = this;
            var tabs = this._getTabs();
            var sameTab = newValue === oldValue;
          
            if (!tabs) {
              return;
            }
          
            var oldIndex = -1;
            var newIndex = -1;
            var newTab = this._getTabByName(newValue);
            var oldTab = this._getTabByName(oldValue);
          
            if (oldTab) {
              oldTab.setSelected(false);
              oldIndex = tabs.indexOf(oldTab);
            }
          
            if (newTab) {
              newTab.setSelected(true);
              newIndex = tabs.indexOf(newTab);
            }
          
            this._$timeout(function() {
              self._updateInkBarStyles(newTab, newIndex, oldIndex);
              if (newTab && !sameTab) {
                self._moveFocus(oldTab, newTab);
              }
            });
          };

          // Sets an active element at left side.
          if (mdNavBar._navBarEl.parentNode.nodeName === "MD-CONTENT") {
            mdNavBar._$timeout(function () {
              var selectedTab = mdNavBar._getSelectedTab();
              if (selectedTab) {
                mdNavBar._navBarEl.parentNode.scrollLeft = selectedTab._$element[0].offsetLeft;
              }
            }, 200);
          }

          mdNavBar._$timeout(function () {
            mdNavBar._getTabs().forEach(function (mdNavItemController) {
              angular.element(mdNavItemController.getButtonEl()).off('blur');
            });
          }, 300); // We need some time to wait until everything is set up
        }
      }
    }
  };
});