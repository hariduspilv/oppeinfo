'use strict';


angular.module('hitsaOis').controller('ApplicationListController', function ($scope, QueryUtils, Classifier) {
  var clMapper = Classifier.valuemapper({type: 'AVALDUS_LIIK', status: 'AVALDUS_STAATUS'});
  QueryUtils.createQueryForm($scope, '/application', {order: 'student.person.lastname,student.person.firstname'}, clMapper.objectmapper);
  $scope.loadData();
});
