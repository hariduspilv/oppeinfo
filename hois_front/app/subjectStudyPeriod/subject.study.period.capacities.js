'use strict';

angular.module('hitsaOis').factory('SspCapacities', ['DataUtils', function (DataUtils) {

    var SspCapacity = function(container) {

        var self = this;

        this.addEmptyCapacities = function(types, onlyMainCapacity) {
          for (var i = 0; i < types.length; i++) {
            var ssps = container.subjectStudyPeriodDtos;
            for (var j = 0; j < ssps.length; j++) {
                this.getCapacity(ssps[j], types[i].code);
                if (onlyMainCapacity) {
                  this.addTeacherEmptyCapacities(ssps[j].teachers, types, true);
                } else {
                  this.addTeacherEmptyCapacities(ssps[j].teachers, types, false);
                  this.addTeacherEmptyCapacitiesWithSubgroups(ssps[j].teachers, ssps[j].subgroups, types);
                }
            }
          }
        };

        this.addTeacherEmptyCapacities = function(teachers, types, ignoreSubgroup) {
            for (var i = 0; i < types.length; i++) {
                for (var j = 0; j < teachers.length; j++) {
                    this.getCapacity(teachers[j], types[i].code, null, ignoreSubgroup);
                }
            }
        };

        this.addTeacherEmptyCapacitiesWithSubgroups = function(teachers, subgroups, types) {
          for (var i = 0; i < types.length; i++) {
            for (var j = 0; j < teachers.length; j++) {
              for (var k = 0; k < subgroups.length; k++) {
                this.getCapacity(teachers[j], types[i].code, subgroups[k].id);
              }
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

        this.capacitiesSumBySsp = function(ssp, subgroupId, ignoreSubgroup) {
            return ssp.capacities.filter(function (it) {
              return !!ignoreSubgroup || !subgroupId && !it.subgroup || it.subgroup === subgroupId;
            }).reduce(countHours, 0);
        };

        this.capacitiesSumBySspAndTeacher = function (ssp, teacherId, ignoreSubgroup) {
          return this.capacitiesSumBySsp(ssp.teachers.find(function (sspTeacher) {
            return sspTeacher.teacherId === teacherId;
          }), null, ignoreSubgroup);
        };

        this.capacityBySsp = function(ssp, type) {
            return ssp.capacities.filter(filterByCapacityType(type)).reduce(countHours, 0);
        };

        function capacitiesSumBySsps (ssps, isContact) {
            var mappedPlansBySubject = getMappedPlansBySsp();
            var sum = 0;
            for(var i = 0; i < ssps.length; i++) {
                sum += ssps[i].capacities
                  .filter(filterByIsContact(isContact, mappedPlansBySubject[ssps[i].id ? ssps[i].id : ssps[i].sspId]))
                  .reduce(countHours, 0);
            }
            return sum;
        }

        this.capacitiesSumOverall = function (teacherId, isContact) {
            var ssps = subjectStudyPeriods(teacherId);
            return capacitiesSumBySsps(ssps, isContact);
        };

        function filterByCapacityType(type, isContact, plan) {
            var mappedPlanCapacities = plan && angular.isArray(plan.capacities) ? plan.capacities.reduce(function (acc, cur) {
              acc[cur.capacityType] = cur.isContact;
              return acc;
            }, {}) : {};
            return function(el) {
                return el.capacityType === type && (!isContact || mappedPlanCapacities[el.capacityType]);
            };
        }

        function filterByIsContact(isContact, plan) {
          var mappedPlanCapacities = plan && angular.isArray(plan.capacities) ? plan.capacities.reduce(function (acc, cur) {
            acc[cur.capacityType] = cur.isContact;
            return acc;
          }, {}) : {};
          return function(el) {
            return !isContact || mappedPlanCapacities[el.capacityType];
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

        function capacitiesSumByType (type, ssps, isContact) {
            var mappedPlansBySubject = getMappedPlansBySsp();
            var sum = 0;
            for(var i = 0; i < ssps.length; i++) {
                sum += ssps[i].capacities.filter(filterByCapacityType(type)).filter(filterByIsContact(isContact,
                  mappedPlansBySubject[ssps[i].id ? ssps[i].id : ssps[i].sspId])).reduce(countHours, 0);
            }
            return sum;
        }

        function getMappedPlansBySsp() {
          var plans = container || container.subjectStudyPeriodPlans ? container.subjectStudyPeriodPlans : [];
          return angular.isArray(plans) ? plans.reduce(function (acc, cur) {
            acc[cur.sspId] = cur;
            return acc;
          }, {}) : {};
        }

        this.capacitiesSumBySubjectAndType = function (subjectId, type, teacherId) {
            if(!container || !container.subjectStudyPeriodDtos) {
                return 0;
            }
            var ssps = self.getCapacitiesBySubject(subjectId, teacherId);
            return capacitiesSumByType(type, ssps);
        };

        this.capacitiesSumByType = function(type, teacherId, isContact) {
            var ssps = subjectStudyPeriods(teacherId);
            return capacitiesSumByType(type, ssps, isContact);
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

        this.toEap = function (hours) {
          return DataUtils.hoursToCredits(hours, 2);
        };

        this.getCapacity = function(ssp, type, subgroup, ignoreSubgroup) {
            var sg = !subgroup ? null : subgroup;
            var capacity = ssp.capacities.find(function(el){
                return el.capacityType === type && (!!ignoreSubgroup || el.subgroup === sg);
            });
            if(!capacity) {
                capacity = {
                    capacityType: type,
                    subgroup: sg
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
                unplannedLessons: teacher.scheduleLoad - teacher.plannedLessons
            };
        };

        this.teacherContainerVocationalLoadTotal = function (capacitiesMap) {
          if (!capacitiesMap) {
            return 0;
          }
          // mapped by capacity type in backend
          var sum = 0;
          for (var key in capacitiesMap) {
            if (!capacitiesMap.hasOwnProperty(key)) {
              continue;
            }
            sum += capacitiesMap[key];
          }
          return sum;
        };

    };
    return SspCapacity;
}]);
