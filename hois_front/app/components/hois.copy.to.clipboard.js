'use strict';

angular.module('hitsaOis').directive('copyToClipboard', function ($window, message) {
    return {
        restrict: 'A',
        link: function (scope, elem, attrs) {
            elem.on('click', function () {
                var value = attrs.copyToClipboard;
                var body = angular.element($window.document.body),
                    textarea = angular.element('<textarea/>');
                var copy = function() {
                    textarea.val(value);
                    body.append(textarea);
                    textarea[0].select();
                    var copied = document.execCommand('copy');
                    if (copied) {
                        message.info('main.copied');
                    }
                    textarea.remove();
                };
                copy(value);
            });
        }
    };
});