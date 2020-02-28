'use strict';

angular.module('hitsaOis')
  .directive('hoisWhiteframeWarning', function () {
    return {
      template:'<md-whiteframe class="md-whiteframe-1dp attention-whiteframe" flex="100" layout layout-align="left center">' +
          '<div md-colors="{color: mdColor ? mdColor : \'default-warn\'}" layout layout-align=\'left center\'>' +
            '<md-icon md-colors="{color: mdColor ? mdColor : \'default-warn\'}" md-font-set="material-icons">info_outline</md-icon>' +
            '<div ng-transclude></div>' +
          '</div>' +
        '</md-whiteframe>',
      replace: true,
      transclude: true,
      scope: {
        mdColor: '='
      }
    };
  });
