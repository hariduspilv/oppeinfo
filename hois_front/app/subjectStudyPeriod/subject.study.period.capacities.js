'use strict';

angular.module('hitsaOis').factory('SspCapacities', [function () {

    var SspCapacity = function(container) {

        var self = this;

        function getSubjectStudyPeriods() {
            return !container || !container.subjectStudyPeriodDtos ? [] : container.subjectStudyPeriodDtos;
        }

        this.getSubjectsCapacityByType = function(subjectId, typeCode) {
            if(!container || !container.subjectStudyPeriodPlans) {
                return 0;
            }
            var plan = container.subjectStudyPeriodPlans.find(function(el){
                return el.subject === subjectId;
            });
            if(!plan) {
                return 0;
            }
            var capacity = plan.capacities.find(function(el){
                return el.capacityType === typeCode;
            });
            if(!capacity) {
                return 0;
            }
            return capacity.hours? capacity.hours : 0;
        };

        this.getSubjectsTotalCapacity = function (subjectId, capacityTypes) {
            if(!container || !container.subjectStudyPeriodPlans || !capacityTypes) {
                return 0;
            }
            var sum = 0;
            for(var i = 0; i < capacityTypes.length; i++) {
                sum += self.getSubjectsCapacityByType(subjectId, capacityTypes[i].code);
            }
            return sum;
        };

        function countHours(sum, val) {
            var newVal = val && val.hours ? val.hours : 0;
            return sum += newVal;
        }

        this.capacitiesSumBySsp = function(ssp) {
            return ssp.capacities.reduce(countHours, 0);
        };

        function capacitiesSumBySsps (ssps) {
            var sum = 0;
            for(var i = 0; i < ssps.length; i++) {
                sum += self.capacitiesSumBySsp(ssps[i]);
            }
            return sum;
        }

        this.capacitiesSumOverall = function () {
            if(!container || !container.subjectStudyPeriodDtos) {
                return 0;
            }
            var ssps = container.subjectStudyPeriodDtos;
            return capacitiesSumBySsps(ssps);
        };

        function filterByCapacityType(type) {
            return function(el) {
                return el.capacityType === type;
            };
        }

        this.getCapacitiesBySubject = function (subjectId) {
            return getSubjectStudyPeriods().filter(function(el){
                return el.subject === subjectId;
            });
        };

        function capacitiesSumByType (type, ssps) {
            var sum = 0;
            for(var i = 0; i < ssps.length; i++) {
                sum += ssps[i].capacities.filter(filterByCapacityType(type)).reduce(countHours, 0);
            }
            return sum;
        }

        this.capacitiesSumBySubjectAndType = function (subjectId, type) {
            if(!container || !container.subjectStudyPeriodDtos) {
                return 0;
            }
            var ssps = self.getCapacitiesBySubject(subjectId);
            return capacitiesSumByType(type, ssps);
        };

        this.capacitiesSumByType = function(type) {
            if(!container || !container.subjectStudyPeriodDtos) {
                return 0;
            }
            var ssps = container.subjectStudyPeriodDtos;
            return capacitiesSumByType(type, ssps);
        };

        this.capacitiesSumBySubject = function(subjectId) {
            if(!container || !container.subjectStudyPeriodDtos) {
                return 0;
            }
            var ssps = self.getCapacitiesBySubject(subjectId);
            return capacitiesSumBySsps(ssps);
        };

        this.getCapacity = function(ssp, type) {
            var capacity = ssp.capacities.find(function(el){
                return el.capacityType === type;
            });
            if(!capacity) {
                capacity = {
                    capacityType: type
                };
                ssp.capacities.push(capacity);
            }
            return capacity;
        };

        this.setCapacityEditable = function(ssp, type) {
            var capacity = self.getCapacity(ssp, type);
            capacity.editable = true;
        };

        this.unsetCapacityEditable = function(ssp, type) {
            var capacity = self.getCapacity(ssp, type);
            capacity.editable = false;
        };

        this.filterEmptyCapacities = function() {
            var ssps = getSubjectStudyPeriods();
            for(var i = 0; i < ssps.length; i++) {
                ssps[i].capacities = ssps[i].capacities.filter(function(el){
                    return el.hours !== undefined && el.hours !== null && el.hours !== '';
                });
            }
        };
    };
    return SspCapacity;
}]);
