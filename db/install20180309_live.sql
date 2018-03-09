\c hois;


insert INTO curriculum_version_omodule_year_capacity(study_year_number,credits,curriculum_version_omodule_id,inserted,changed,version,inserted_by,changed_by)
select 4, 0,com.id,current_timestamp(3),current_timestamp(3),0,'DATA_TRANSFER_PROCESS','DATA_TRANSFER_PROCESS'
from curriculum cr
		join curriculum_version cv on cr.id=cv.curriculum_id
		join curriculum_version_omodule com on cv.id=com.curriculum_version_id
where cv.id=205 and
			(select count(*) from curriculum_version_omodule_year_capacity cy where cy.curriculum_version_omodule_id=com.id and study_year_number=4)=0 and
			(select count(*) from curriculum_version_omodule_theme cy where cy.curriculum_version_omodule_id=com.id)>0;