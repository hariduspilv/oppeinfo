'use strict';

angular.module('hitsaOis')
  .constant('config', {
    'apiUrl': 'https://devhois2/hois_back',
    'idCardLoginUrl': 'https://idlogin.devhois2',
    'timeoutDialogBeforeTimeoutInSeconds': 1200,
    'mobileIdInitialDelay': 5000,
    'mobileIdPollInterval': 4000,
    'mobileIdMaxPolls': 15,
    'ekisUrl': 'https://kis-test.hm.ee/?wdsturi=3Dpage%3=Dview_dynobj%26pid%3D',
    'schoolBoardRedirectInSeconds': 60,
    'schoolBoardRefreshInSeconds': 60
  });
