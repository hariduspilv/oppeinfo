'use strict';

angular.module('hitsaOis').controller('studyYearScheduleLegendController', ['$scope', 'QueryUtils', 'ArrayUtils', 'message', function ($scope, QueryUtils, ArrayUtils, message) {

    var DEFAULT_VALUES = [
        {code: "E", nameEt: "Eksam", color: "#FF0000"},
        {code: "Pr", nameEt: "Praktika ettevõtes", color: "#790FFC"},
        {code: "P", nameEt: "Praktika koolis", color: "#1FF47C"},
        {code: "V", nameEt: "Vaheaeg", color: "#E6E6E6"},
        {code: "X", nameEt: "ei osale õppetöös", color: "#8C8C8C"}
    ];
    
    $scope.newLegend = {};

    $scope.record = QueryUtils.endpoint('/school/studyYearScheduleLegends').search(function(response) {
        if(!response || !response.legends || response.legends.length === 0) {
            $scope.legends = DEFAULT_VALUES;
        } else {
            $scope.legends = response.legends;
        }
    });

    $scope.remove = function(item){
        ArrayUtils.remove($scope.legends, item);
        $scope.legends.forEach(function(l){l.edited = false;});
    };
    /**
     * Just one variant of error messages
     */
    $scope.save = function() {
        $scope.formSubmitted = true;
        $scope.legends.forEach(function(l){l.edited = false;});
        // if(!formIsValid()) {
        //     message.error('main.messages.form-has-errors');
        //     return;
        // } 
        if(codesNotAdded()) {
            message.error('studyYearScheduleLegend.error.codeRequired');
            return;
        } 
        if(codesWrongLength()) {
            message.error('studyYearScheduleLegend.error.codeLength');
            return;
        }
        if(nameEtNotAdded()) {
            message.error('studyYearScheduleLegend.error.nameEtRequired');
            return;
        }
        if(namesWrongLength()) {
            message.error('studyYearScheduleLegend.error.nameLength');
            return;
        }
        $scope.record.legends = $scope.legends;
        $scope.record.$put().then(function(response){
            message.updateSuccess();
            $scope.legends = response.legends;
        });
    };

    // function formIsValid() {
    //     var invalidElements = $scope.legends.filter(function(el){
    //         return !$scope.codeIsValid(el) || !$scope.nameEtIsValid(el) || !$scope.nameEnIsValid(el);
    //     });
    //     return invalidElements.length === 0;
    // }

    function codesNotAdded() {
        return $scope.legends.filter(function(el){
            return !el.code;
        }).length > 0;
    }

    function codesWrongLength() {
        return $scope.legends.filter(function(el){
            return el.code && el.code.length > 2;
        }).length > 0;
    }

    function nameEtNotAdded() {
        return $scope.legends.filter(function(el){
            return !el.nameEt;
        }).length > 0;
    }

    function namesWrongLength() {
        return $scope.legends.filter(function(el){
            return el.nameEt && el.nameEt.length > 50 ||  el.nameEn && el.nameEn.length > 50;
        }).length > 0;
    }

    $scope.codeIsValid = function(legend) {
        return legend.code && legend.code.length <= 2;
    };

    $scope.nameEtIsValid = function(legend) {
        return legend.nameEt && legend.nameEt.length <= 50;
    };

    $scope.nameEnIsValid = function(legend) {
        return !legend.nameEn || legend.nameEn.length <= 50;
    };

    $scope.changeEditable = function(legend) {
        $scope.legends.forEach(function(l){l.edited = false;});
        legend.edited = true;
    };

    $scope.addRow = function() {
        var newLegend = {
                color: '#FFFFFF'
            };
        $scope.legends.push(newLegend);
        $scope.legends.forEach(function(l){l.edited = false;});
        newLegend.edited = true;
    };
}]);