'use strict';

angular.module('hitsaOis').factory('SspCapacities', [function () {

    var SspCapacity = function(container) {

        var self = this;

        this.addEmptyCapacities = function(types) {
          for (var i = 0; i < types.length; i++) {
            var ssps = container.subjectStudyPeriodDtos;
            for (var j = 0; j < ssps.length; j++) {
                this.getCapacity(ssps[j], types[i].code);
                this.addTeacherEmptyCapacities(ssps[j].teachers, types);
            }
          }
        };

        this.addTeacherEmptyCapacities = function(teachers, types) {
            for (var i = 0; i < types.length; i++) {
                for (var j = 0; j < teachers.length; j++) {
                    this.getCapacity(teachers[j], types[i].code);
                }
            }
        };

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

        this.capacityBySsp = function(ssp, type) {
            return ssp.capacities.filter(filterByCapacityType(type)).reduce(countHours, 0);
        };

        function capacitiesSumBySsps (ssps) {
            var sum = 0;
            for(var i = 0; i < ssps.length; i++) {
                sum += self.capacitiesSumBySsp(ssps[i]);
            }
            return sum;
        }

        this.capacitiesSumOverall = function (teacherId) {
            var ssps = subjectStudyPeriods(teacherId);
            return capacitiesSumBySsps(ssps);
        };

        function filterByCapacityType(type) {
            return function(el) {
                return el.capacityType === type;
            };
        }

        this.getCapacitiesBySubject = function (subjectId, teacherId) {
            var ssps = getSubjectStudyPeriods().filter(function(el){
                return el.subject === subjectId;
            });

            if (teacherId) {
                ssps = accountForTeacherDifferentCapacities(ssps, teacherId);
            }
            return ssps;
        };

        function accountForTeacherDifferentCapacities(ssps, teacherId) {
            var teacherSsps = getSubjectCapacitiesByTeacher(ssps, teacherId);
            ssps = ssps.filter(function (el) { return !el.capacityDiff; });

            teacherSsps.forEach(function (teacherSsp) {
                ssps.push(teacherSsp);
            });
            return ssps;
        }

        function getSubjectCapacitiesByTeacher(ssps, teacherId) {
            var sspTeachers = [];
            for (var i = 0; i < ssps.length; i++) {
                if (ssps[i].capacityDiff) {
                    for (var j = 0; j < ssps[i].teachers.length; j++) {
                        sspTeachers.push(ssps[i].teachers[j]);
                    }
                }
            }
            return sspTeachers.filter(function(teacher){
                return teacher.teacherId === teacherId;
            });
        }

        function capacitiesSumByType (type, ssps) {
            var sum = 0;
            for(var i = 0; i < ssps.length; i++) {
                sum += ssps[i].capacities.filter(filterByCapacityType(type)).reduce(countHours, 0);
            }
            return sum;
        }

        this.capacitiesSumBySubjectAndType = function (subjectId, type, teacherId) {
            if(!container || !container.subjectStudyPeriodDtos) {
                return 0;
            }
            var ssps = self.getCapacitiesBySubject(subjectId, teacherId);
            return capacitiesSumByType(type, ssps);
        };

        this.capacitiesSumByType = function(type, teacherId) {
            var ssps = subjectStudyPeriods(teacherId);
            return capacitiesSumByType(type, ssps);
        };

        function subjectStudyPeriods(teacherId) {
            if(!container || !container.subjectStudyPeriodDtos) {
                return 0;
            }
            var ssps = container.subjectStudyPeriodDtos;
            if (teacherId) {
                ssps = accountForTeacherDifferentCapacities(ssps, teacherId);
            }
            return ssps;
        }

        this.capacitiesSumBySubject = function(subjectId, teacherId) {
            if(!container || !container.subjectStudyPeriodDtos) {
                return 0;
            }
            var ssps = self.getCapacitiesBySubject(subjectId, teacherId);
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

        this.filterEmptyCapacities = function() {
            var ssps = getSubjectStudyPeriods();
            for(var i = 0; i < ssps.length; i++) {
                ssps[i].capacities = ssps[i].capacities.filter(function(el){
                    return el.hours !== undefined && el.hours !== null && el.hours !== '';
                });
            }
        };

        this.subjectsLoadValid = function (subjectId, capacityTypes) {
            var sspLoadByType, sspsCapacities;
            var ssps = self.getCapacitiesBySubject(subjectId);
    
            var getCapacityByType = function (capacityTypeCode) {
                return function(el) {
                    return el.capacityType === capacityTypeCode;
                };
            };
    
            for (var i = 0; i < capacityTypes.length; i++) {
    
                sspsCapacities = self.getSubjectsCapacityByType(subjectId, capacityTypes[i].code);
    
                for (var j = 0; j < ssps.length; j++) {
                    var capacity = ssps[j].capacities.find(getCapacityByType(capacityTypes[i].code));
                    sspLoadByType = capacity ? capacity.hours : 0;
                    if(sspLoadByType > sspsCapacities) {
                        return false;
                    }
                }
            }
            return true;
        };

        this.teachersLoadOk = function(ssp) {
            return ssp.teachers.reduce(function(sum, val){
                return sum += val.scheduleLoad;
            }, 0) >=  self.capacitiesSumBySsp(ssp);
        };

        this.teacherLoadOk = function(teacher) {
            return teacher.scheduleLoad >=  self.capacitiesSumBySsp(teacher);
        };

        this.teacherPlannedLoad = function(teacher) {
            return {
                scheduleLoad: teacher.scheduleLoad,
                unplannedLessons: teacher.scheduleLoad - teacher.plannedLessons > 0 ? teacher.scheduleLoad - teacher.plannedLessons : 0
            };
        };

    };
    return SspCapacity;
}]);
