'use strict';

angular.module('hitsaOis')
  .constant('config', {
    'apiUrl': 'http://localhost:9000/hois_back',
    'idCardLoginUrl': 'https://idlogin.devhois',
    'mobileIdInitialDelay': 5000,
    'mobileIdPollInterval': 4000,
    'mobileIdMaxPolls': 15,
    'timeoutDialogBeforeTimeoutInSeconds': 180,
    'ekisUrl': 'https://kis-test.hm.ee/?wdsturi=3Dpage%3=Dview_dynobj%26pid%3D'
  });
