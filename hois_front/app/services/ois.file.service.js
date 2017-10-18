
'use strict';

angular.module('hitsaOis')
  .factory('oisFileService', function(config) {
    var factory = {};

    factory.getFromLfFile = function(lfFile, loadedCallback) {
      var oisFile = {};
      var reader = new FileReader();
      reader.onloadend = function () {
        oisFile.fname = lfFile.lfFileName;
        oisFile.ftype =  lfFile.lfFile.type;
        if(lfFile.lfFile.size > 0) {
          oisFile.fdata = reader.result.replace('data:' + oisFile.ftype + ';base64,', '');
        } else {
          oisFile.fdata = '';
        }
        if(angular.isFunction(loadedCallback)) {
          loadedCallback(oisFile);
        }
      };
      reader.readAsDataURL(lfFile.lfFile);
      return oisFile;
    };

    factory.getFileUrl = function(oisFile) {
      return 'data:' + oisFile.ftype + ';base64,' + oisFile.fdata;
    };

    factory.getUrl = function(file) {
      if(!file) {
        return;
      }
      if (file && file.oisFile) {
        return file.id ? config.apiUrl + '/oisfile/get/' + file.oisFile.id : factory.getFileUrl(file.oisFile);
      } else {
        return file.id ? config.apiUrl + '/oisfile/get/' + file.id : factory.getFileUrl(file);
      }
    };

    return factory;
  });
