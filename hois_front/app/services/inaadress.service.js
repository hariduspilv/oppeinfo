'use strict';

/**
 * @ngdoc service
 * @name hitsaOis.inAadressService
 * @description
 * # inAadressService
 * Service in the hitsaOis.
 */
angular.module('hitsaOis')
  .service('inAadressService', function ($translate) {
    var container = document.createElement('div');
    container.id = 'InAadressDiv';

    var inads = null;
    this.getInAds = function() {
      if (!inads) {
        inads = new InAadress({"container":"InAadressDiv","mode":"3","nocss":true,"appartment":1,"ihist":"1993","lang":$translate.use()});
      }
      return inads;
    };
    this.isLoaded = function() {
      return !!inads;
    };
    this.append = function(element) {
      element.appendChild(container);
    };
  });
