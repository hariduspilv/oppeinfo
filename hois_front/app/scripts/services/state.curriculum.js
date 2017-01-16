'use strict';


angular.module('hitsaOis')
  .factory('StateCurriculum', function ($http, $resource, config, QueryUtils) {

    function StateCurriculum(args) {
				// TODO: remove this variables?
      	this.id = args.id;
				this.nameEt = args.nameEt || null;
				this.nameEn = args.nameEn || null;
				this.iscedClassCode = args.iscedClassCode || null;
				this.objectivesEt = args.objectivesEt || null;
				this.objectivesEn = args.objectivesEn || null;
				this.outcomesEt = args.outcomesEt || null;
				this.outcomesEn = args.outcomesEn || null;
				this.admissionRequirementsEt = args.admissionRequirementsEt || null;
				this.admissionRequirementsEn = args.admissionRequirementsEn;
				this.graduationRequirementsEt = args.graduationRequirementsEt || null;
				this.graduationRequirementsEn = args.graduationRequirementsEn || null;
				this.credits = args.credits || null;
				this.practiceDescription = args.practiceDescription || null;
				this.stateCurrClassCode = args.stateCurrClassCode || null;
				this.finalExamDescription = args.finalExamDescription || null;
				this.optionalStudyCredits = args.optionalStudyCredits || null;
				this.validFrom = args.validFrom || null;
				this.validThru = args.validThru || null;
				this.description = args.description || null;
				this.riigiteatajaUrl = args.riigiteatajaUrl || null;
				this.statusCode = args.statusCode || null;

				this.iscedVald = args.iscedVald || null;
				this.iscedSuun = args.iscedSuun || null;

      this.save = function () {
          return $resource(config.apiUrl+'/stateCurriculum').save(this);
      };
    }

		StateCurriculum.get = function(id) {
      var resource = $resource(config.apiUrl+'/stateCurriculum/:id');
      return resource.get({id: id});
    };

			StateCurriculum.create = function(newStateCurriculum) {
				return $http({
            method: 'POST',
            url: config.apiUrl + '/stateCurriculum',
            data: newStateCurriculum
          });
			};

			StateCurriculum.delete = function(id) {
				return $http({
          method: 'DELETE',
          url: config.apiUrl + '/stateCurriculum/' + id
        });
			};

			StateCurriculum.update = function(stateCurriculum) {
				return $http({
            method: 'PUT',
            url: config.apiUrl + '/stateCurriculum/' + stateCurriculum.id,
            data: stateCurriculum
          });
			};

		StateCurriculum.query = function(params, successCallback) {
      var resource = $resource(config.apiUrl+'/stateCurriculum');
      var queryParams = QueryUtils.getQueryParams(params);
      return resource.get(queryParams, successCallback);
    };

		StateCurriculum.current = undefined;
		StateCurriculum.editableModule = undefined;
		StateCurriculum.cameFromModuleForm = false;

    return StateCurriculum;
  });
