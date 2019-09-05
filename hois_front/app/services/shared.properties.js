'use strict';

angular.module('hitsaOis')
       .service('sharedProperties', SharedPropertiesService);
       
function SharedPropertiesService() {
  var _properties = {};
  this.getProperties = getProperties;

  function getProperties() {
    return _properties;
  }
}