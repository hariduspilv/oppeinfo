\c hois

insert into classifier(code, value, name_et, name_en, main_class_code, "valid", ehis_value, is_vocational, is_higher, inserted, "version") values
('NOM_PIKEND', 'NOM_PIKEND', 'Nominaalaja pikendamine', null, null, true, null, true, true, now(), 0),
('NOM_PIKEND_0', '0', 'Ei pikenda', 'No extending', 'NOM_PIKEND', true, 0, true, true, now(), 0),
('NOM_PIKEND_1', '1', '1 semester', '1 semester', 'NOM_PIKEND', true, 1, true, true, now(), 0),
('NOM_PIKEND_2', '2', '2 semesterit', '1 semesters', 'NOM_PIKEND', true, 2, true, true, now(), 0); 

alter table poll_theme add column is_teacher boolean;
comment on column poll_theme.is_teacher is 'kas kuvada küsimused iga õpetaja kohta';

alter table poll_journal alter column journal_id drop not null;
alter table poll_journal add column subject_study_period_id bigint;
comment on column poll_journal.subject_study_period_id is 'viide aine-õppejõu paarile';
create index IXFK_poll_journal_subject_study_period on poll_journal(subject_study_period_id);
alter table poll_journal add constraint FK_poll_journal_subject_study_period foreign key(subject_study_period_id) references subject_study_period(id);


alter table poll_teacher_comment add column subject_study_period_id bigint;
comment on column poll_teacher_comment.subject_study_period_id is 'viide aine-õppejõu paarile';
create index IXFK_poll_teacher_comment_subject_study_period on poll_teacher_comment(subject_study_period_id);
alter table poll_teacher_comment add constraint FK_poll_teacher_comment_subject_study_period foreign key(subject_study_period_id) references subject_study_period(id);

alter table response_subject add column subject_study_period_id bigint;
comment on column response_subject.subject_study_period_id is 'viide aine-õppejõu paarile';
create index IXFK_response_subject_subject_study_period on response_subject(subject_study_period_id);
alter table response_subject add constraint FK_response_subject_subject_study_period foreign key(subject_study_period_id) references subject_study_period(id);

alter table response_question_answer add column teacher_id bigint;
comment on column response_question_answer.teacher_id is 'viide õpetajale';
create index IXFK_rresponse_question_answer_teacher_id on response_question_answer(teacher_id);
alter table response_question_answer add constraint FK_response_question_answer_teacher foreign key(teacher_id) references teacher(id);

alter table apel_application_record add column application_planned_subject_id bigint;
comment on column apel_application_record.application_planned_subject_id is 'viide avaldusel planeeritud ainele';
create index IXFK_apel_application_record_application_planned_subject_id on apel_application_record(application_planned_subject_id);
alter table apel_application_record add constraint FK_apel_application_record_application_planned_subject foreign key(application_planned_subject_id) references application_planned_subject(id);

alter table apel_application_formal_subject_or_module alter column grade_code drop not null;
alter table apel_application_formal_subject_or_module alter column credits drop not null;

alter TABLE "apel_application"
add column "new_nominal_study_end" date NULL,    -- uus nominaalaja lõpp
add column 	"nominal_type_code" varchar(100)	 NULL,    -- viide klassifikaatorile nominaalaja pikendamise kohta
add column 	"old_nominal_study_end" date NULL,    -- enne kinnitamist kehtinud nominaalaja lõpp
add column 	"is_ehis_sent" boolean NULL    -- kas ehisesse edastatud
;

COMMENT ON COLUMN "public"."apel_application"."new_nominal_study_end"	IS 'uus nominaalaja lõpp';
COMMENT ON COLUMN "public"."apel_application"."nominal_type_code"	IS 'viide klassifikaatorile nominaalaja pikendamise kohta';
COMMENT ON COLUMN "public"."apel_application"."old_nominal_study_end"	IS 'enne kinnitamist kehtinud nominaalaja lõpp';
COMMENT ON COLUMN "public"."apel_application"."is_ehis_sent"	IS 'kas ehisesse edastatud';

CREATE INDEX "IXFK_apel_application_classifier_02" ON "public"."apel_application" ("nominal_type_code" ASC);
ALTER TABLE "public"."apel_application" ADD CONSTRAINT "FK_apel_application_classifier_02"	FOREIGN KEY ("nominal_type_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;

alter table practice_journal_entry alter column hours type numeric(10,2);
update practice_journal_entry set hours=((hours*0.75)*60)::int;

update classifier set is_vocational = true where code = 'OPPURSTAATUS_V';

update study_year_schedule sys set
       week_nr = sys.week_nr + 1,
       study_period_id = coalesce((select (select distinct first_value(sp2.id) over (partition by sp2.study_year_id order by sp2.start_date asc)
             from study_period sp2 where sp2.study_year_id = sy2.id and sp2.end_date > sy2.start_Date + 7 * sys2.week_nr) as spId
       from
             study_year_schedule sys2
       join study_year sy2 on
             sys2.school_id = sy2.school_id
             and sy2.year_code = 'OPPEAASTA_2019_20'
       where
             sys2.id = sys.id),study_period_id)
where sys.id in (select sys3.id from study_year_schedule sys3
       join study_period sp3 on sp3.id = sys3.study_period_id
       join study_year sy3 on sy3.id = sp3.study_year_id
       where sy3.year_code = 'OPPEAASTA_2019_20' and sys3.school_id in (1, 2, 18)) ;

update classifier_connect set connect_classifier_code='LOPUBLANKETT_E' where classifier_code='OPPEASTE_514' and connect_classifier_code='LOPUBLANKETT_M';
update classifier_connect set connect_classifier_code='LOPUBLANKETT_E' where classifier_code='OPPEASTE_513' and connect_classifier_code='LOPUBLANKETT_M';

CREATE OR REPLACE FUNCTION public.upd_student_curriculum_completion(p_id bigint)
 RETURNS integer
 LANGUAGE plpgsql
AS $function$
declare 
  pb_exist boolean:=false;
	p_curr_modules bigint array;
	p_curr_module_types bigint array;
  p_curr_modules_credits numeric array;
	p_curr_modules_opt_credits numeric array;
	p_curr_modules2 bigint array;
  p_study_modules bigint array;
  p_vstudy_modules bigint array;
  p_optional numeric:=0;
	r record;
	i int:=0;
  ii int:=0;
  p_total int:=0;
	p_opt_credits numeric:=0;
	p_avg_credits numeric:=0;
	p_avg_total_credits numeric:=0;
	p_total_credits numeric:=0;
	p_abs_credits numeric:=0;
	p_fabs_credits numeric:=0;
	a_count int:=0;

  p_vcurr_modules bigint array;
	p_vcurr_modules2 bigint array;

	p_fcurr_modules bigint array;
	
	mod_id bigint;

	is_higher_curriculum boolean:=true;
BEGIN

	--raise notice 'Tere %',to_char(current_timestamp(3),'mi:ss.ms');
	for r in (select distinct cvo.id,cm.id as m_id, cm.credits, cc.optional_study_credits, cm.module_code, cc.is_higher
					  from curriculum_version cv
								 join curriculum_version_omodule cvo on cv.id=cvo.curriculum_version_id
								 join curriculum_module cm on cvo.curriculum_module_id=cm.id and cv.curriculum_id=cm.curriculum_id and coalesce(cm.is_additional,false)=false and cm.module_code!='KUTSEMOODUL_V' and coalesce(cm.is_additional,false)=false
								 join curriculum cc on cv.curriculum_id=cc.id
								 join student ss on cv.id=ss.curriculum_version_id
								 left join student_group sg on ss.student_group_id=sg.id
					 where ss.id=p_id and (sg.id is null or 
																 coalesce(sg.speciality_code,'x')='x' or 
																 coalesce(sg.speciality_code,'x')!='x' and exists(
																					select 1 
																					from curriculum_module_occupation cmo 
																							 left join classifier_connect ccc on cmo.occupation_code=ccc.connect_classifier_code
																					where cmo.curriculum_module_id=cm.id and (cmo.occupation_code=sg.speciality_code or ccc.classifier_code=sg.speciality_code))))
	LOOP
		i:=i+1;
		p_curr_modules[i]:=r.id;
		p_vcurr_modules[i]:=r.m_id;
		p_curr_modules2[i]:=r.id;
		p_vcurr_modules2[i]:=r.m_id;
		p_curr_modules_credits[i]:=r.credits;
		p_optional:=coalesce(r.optional_study_credits,0);
		if r.module_code='KUTSEMOODUL_L' then
			p_fcurr_modules[i]:=r.id;
		ELSE
			p_fcurr_modules[i]:=0;
		end if;
		is_higher_curriculum:=r.is_higher;
	end loop;

	if is_higher_curriculum then
		i:=0;
		--KĆµrgĆµppurid
		for r in (select distinct cm.id as m_id, cm.total_credits,cm.optional_study_credits,cm.compulsory_study_credits, cm.type_code, cc.is_higher, cc.optional_study_credits as total_optional_study_credits
					  from curriculum_version cv
								 join curriculum_version_hmodule cm on cv.id=cm.curriculum_version_id
								 --join curriculum_module cm on cvo.curriculum_module_id=cm.id and cv.curriculum_id=cm.curriculum_id and coalesce(cm.is_additional,false)=false and cm.module_code!='KUTSEMOODUL_V' and coalesce(cm.is_additional,false)=false
								 join curriculum cc on cv.curriculum_id=cc.id
								 join student ss on cv.id=ss.curriculum_version_id
					 where ss.id=p_id and (coalesce(ss.curriculum_speciality_id,0)=0 or 
															coalesce(ss.curriculum_speciality_id,0) > 0 and exists(
																	select 1 
																	from curriculum_version_hmodule_speciality hs
									 										 join curriculum_version_speciality cvs on hs.curriculum_version_speciality_id=cvs.id
																	where hs.curriculum_version_hmodule_id=cm.id and
																				cvs.curriculum_speciality_id=ss.curriculum_speciality_id
																				)))
		LOOP
			i:=i+1;
			p_curr_modules[i]:=r.m_id;
			--p_curr_module_types[i]:=r.type_code;
			--p_vcurr_modules[i]:=r.m_id;
			p_curr_modules2[i]:=r.m_id;
			--p_vcurr_modules2[i]:=r.m_id;
			p_curr_modules_credits[i]:=coalesce(r.compulsory_study_credits,0);
			p_curr_modules_opt_credits[i]:=coalesce(r.optional_study_credits,0);
			if r.type_code in ('KORGMOODUL_V') then
				p_optional:=coalesce(r.optional_study_credits,0)+p_optional;
				p_vcurr_modules[i]:=r.m_id;
			else
				p_vcurr_modules[i]:=0;
			end if;
			if r.type_code in ('KORGMOODUL_F','KORGMOODUL_L') then
				p_fcurr_modules[i]:=r.m_id;
			ELSE
				p_fcurr_modules[i]:=0;
			end if;
		end loop;
		--raise notice '%', array_length(p_curr_modules,1);
		--Ć•ppuri positiived tulemused
		for r in (select coalesce(svm.curriculum_version_hmodule_id,sv.curriculum_version_hmodule_id) as curriculum_version_hmodule_id,
										 coalesce(svm.is_optional,sv.is_optional) is_optional,
										sv.grade_code, sv.credits,sv.subject_id, sv.grade_mark
							from student_higher_result sv
									 left join student_higher_result_module svm on sv.id=svm.student_higher_result_id
							where sv.student_id=p_id and sv.is_active=true and sv.grade_code in ('KORGHINDAMINE_1','KORGHINDAMINE_2','KORGHINDAMINE_3','KORGHINDAMINE_4','KORGHINDAMINE_5','KORGHINDAMINE_A') /*and sv.arr_modules is null*/
							order by sv.grade_date desc nulls last) 
		LOOP
			pb_exist:=false;
			if r.subject_id is not null then
				if array_length(p_study_modules,1) > 0 then
					for ii in 1..array_length(p_study_modules,1)
					LOOP
						if p_study_modules[ii]=r.subject_id THEN
							pb_exist:=true;
							exit;
						end if;
					end loop;
				end if;
				if not pb_exist THEN
					p_study_modules[case when p_study_modules is null then 0 else array_length(p_study_modules,1) end+1]:=r.subject_id;
				end if;
			ELSE
				pb_exist:=false;
			end if;
			if not pb_exist THEN
				--p_study_modules[case when p_study_modules is null then 0 else array_length(p_study_modules,1) end+1]:=r.curriculum_version_omodule_id;
				p_total_credits:=p_total_credits+r.credits;
				if r.grade_code in ('KORGHINDAMINE_1','KORGHINDAMINE_2','KORGHINDAMINE_3','KORGHINDAMINE_4','KORGHINDAMINE_5') THEN
 					p_avg_total_credits:=p_avg_total_credits+r.credits;
 					p_avg_credits:=p_avg_credits+r.credits*(r.grade_mark::int);
 				end if;
 				if array_length(p_curr_modules,1) > 0 and r.curriculum_version_hmodule_id is not null then
 					pb_exist:=false;
					for ii in 1..array_length(p_curr_modules,1)
					loop
						if p_curr_modules[ii]=r.curriculum_version_hmodule_id then
							if coalesce(r.is_optional,false)=true then
								p_curr_modules_opt_credits[ii]:=p_curr_modules_opt_credits[ii]-r.credits;
								if p_curr_modules[ii]=p_vcurr_modules[ii] then
									raise notice 'SIIN: ';
									p_opt_credits:=p_opt_credits+r.credits;
								end if;
								raise notice '%', p_curr_modules_opt_credits[ii];
							else
								p_curr_modules_credits[ii]:=p_curr_modules_credits[ii]-r.credits;
								raise notice '-%', p_curr_modules_credits[ii];
							end if;
							pb_exist:=true;
						end if;
					end loop;
					--valikĆµpingud
					if not pb_exist then
 						p_opt_credits:=p_opt_credits+r.credits;
 					end if;
 				end if; 
			end if;
		end loop;

		if array_length(p_curr_modules,1) > 0 THEN
			for ii in 1..array_length(p_curr_modules,1)
			LOOP
				if p_curr_modules_credits[ii] > 0 then
					--raise notice '%', p_curr_modules[ii];
					p_abs_credits:=p_abs_credits+p_curr_modules_credits[ii];
					if p_curr_modules[ii]=p_fcurr_modules[ii] then
						p_fabs_credits:=p_fabs_credits+p_curr_modules_credits[ii];
					end if;
				else
					p_opt_credits:=p_opt_credits+abs(p_curr_modules_credits[ii]);
				end if;
				if p_curr_modules_opt_credits[ii] > 0 then
					raise notice 'o %', p_curr_modules[ii];
					p_abs_credits:=p_abs_credits+p_curr_modules_opt_credits[ii];
				else
					p_opt_credits:=p_opt_credits+abs(p_curr_modules_opt_credits[ii]);
				end if;
			end loop;
		end if;

		raise NOTICE 'Fopt: %/%', p_fabs_credits, p_abs_credits;
		raise NOTICE 'opt: %/%', p_opt_credits, p_optional;

		if p_opt_credits > p_optional THEN
			p_opt_credits:=0;
		ELSE
			p_opt_credits:=p_optional-p_opt_credits;
		end if;

		raise NOTICE 'opt: %/%', p_opt_credits, p_optional;


		p_abs_credits:=coalesce(p_abs_credits,0)+p_opt_credits;
		p_fabs_credits:=p_abs_credits-p_fabs_credits;
		
	else
		--Ä†Āµppuri positiivsed tulemused
		for r in (select case when sv.arr_modules is null then sv.curriculum_version_omodule_id else null end curriculum_version_omodule_id, 
										 case when sv.arr_modules is null then cmm.curriculum_module_id else null end curriculum_module_id,
										sv.grade, sv.credits, sv.arr_modules
							from student_vocational_result sv
									 left join curriculum_version_omodule cmm on sv.curriculum_version_omodule_id=cmm.id
									 --left join student_vocational_result_omodule svm on sv.id=svm.student_vocational_result_id
							where sv.student_id=p_id and grade in ('A','3','4','5') /*and sv.arr_modules is null*/
							order by sv.grade_date desc) 
		LOOP
			pb_exist:=false;
			if r.curriculum_version_omodule_id is not null then
				if array_length(p_study_modules,1) > 0 then
					for ii in 1..array_length(p_study_modules,1)
					LOOP
						if p_study_modules[ii]=r.curriculum_version_omodule_id or p_vstudy_modules[ii]=r.curriculum_module_id THEN
							pb_exist:=true;
							exit;
						end if;
					end loop;
				end if;
				if not pb_exist THEN
					p_study_modules[case when p_study_modules is null then 0 else array_length(p_study_modules,1) end+1]:=r.curriculum_version_omodule_id;
					p_vstudy_modules[case when p_vstudy_modules is null then 0 else array_length(p_vstudy_modules,1) end+1]:=r.curriculum_module_id;
				end if;
			ELSE
				pb_exist:=false;
			end if;
			
			if not pb_exist THEN
				--p_study_modules[case when p_study_modules is null then 0 else array_length(p_study_modules,1) end+1]:=r.curriculum_version_omodule_id;
				p_total_credits:=p_total_credits+r.credits;
				if r.grade in ('3','4','5') THEN
					p_avg_total_credits:=p_avg_total_credits+r.credits;
					p_avg_credits:=p_avg_credits+r.credits*(r.grade::int);
				end if;
				if array_length(p_curr_modules,1) > 0 then
					pb_exist:=false;
					--kahte tÄ†Ā¼Ä†Ā¼pi moodulid
					if r.curriculum_version_omodule_id is not null then
						for ii in 1..array_length(p_curr_modules,1)
						LOOP
							if p_curr_modules[ii]=r.curriculum_version_omodule_id or p_vcurr_modules[ii]=r.curriculum_module_id THEN
								p_curr_modules2[ii]=0;
								p_vcurr_modules2[ii]=0;
								p_total:=p_total+1;
								pb_exist:=true;
								exit;
							end if;
						end loop;
					elsif array_length(r.arr_modules,1) > 0 THEN
						for i in 1..array_length(r.arr_modules,1)
						LOOP
								select curriculum_module_id into mod_id from curriculum_version_omodule where id=r.arr_modules[i];
								for ii in 1..array_length(p_curr_modules,1)
								LOOP
									if p_curr_modules[ii]=r.arr_modules[i] or p_vcurr_modules[ii]=mod_id THEN
										p_curr_modules2[ii]=0;
										p_vcurr_modules2[ii]=0;
										p_total:=p_total+1;
										pb_exist:=true;
										exit;
									end if;
								end loop;
						end loop;
					end if;
					--valikÄ†Āµpingud
					if not pb_exist then
						p_opt_credits:=p_opt_credits+r.credits;
					end if;
				end if; 
			end if;
		end loop;

		if array_length(p_curr_modules,1) > 0 THEN
			for ii in 1..array_length(p_curr_modules,1)
			LOOP
				if p_curr_modules2[ii] > 0 then
					p_abs_credits:=p_abs_credits+p_curr_modules_credits[ii];
					if p_curr_modules[ii]=p_fcurr_modules[ii] then
						p_fabs_credits:=p_fabs_credits+p_curr_modules_credits[ii];
					end if;
				end if;
			end loop;
		end if;

		--raise NOTICE 'Fopt: %/%', p_fabs_credits, p_abs_credits;
		--raise NOTICE 'opt: %/%', p_opt_credits, p_optional;

		--kokku vÄ†Āµlg
		if p_opt_credits > p_optional THEN
			p_opt_credits:=0;
		ELSE
			p_opt_credits:=p_optional-p_opt_credits;
		end if;

		--raise NOTICE 'opt: %/%', p_opt_credits, p_optional;


		p_abs_credits:=coalesce(p_abs_credits,0)+p_opt_credits;
		p_fabs_credits:=p_abs_credits-p_fabs_credits;

		--raise NOTICE 'opt: %/%', p_fabs_credits, p_abs_credits;
		--raise notice 'Tere %, %, %, %', p_abs_credits, p_avg_credits, p_avg_total_credits,to_char(current_timestamp(3),'mi:ss.ms');
	end if;

	update student_curriculum_completion 
	set study_backlog=-p_abs_credits, study_backlog_without_graduate=-p_fabs_credits,
				average_mark=case when p_avg_total_credits > 0 then floor(p_avg_credits*100/p_avg_total_credits)/100 else 0 end, credits=p_total_credits, changed=current_timestamp(3)
	where student_id=p_id;
	
	GET DIAGNOSTICS a_count = ROW_COUNT;
	if a_count=0 THEN
			insert into student_curriculum_completion(student_id,study_backlog,study_backlog_without_graduate,average_mark,credits,inserted,changed)
			values(p_id,-p_abs_credits,-p_fabs_credits,case when p_avg_total_credits > 0 then floor(p_avg_credits*100/p_avg_total_credits)/100 else 0 end,p_total_credits,current_timestamp(3),current_timestamp(3));
	end if;

	return 0;
exception when others THEN
	--raise notice '%, %',p_id,p_abs_credits;
	return 0;
end;
$function$
;


CREATE OR REPLACE FUNCTION public.upd_del_apel_result()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
declare
    u_count integer;
		b_count integer;
		r record;
		rr record;
		p_id integer;
		x integer;
begin
	if tg_op = 'UPDATE' THEN
		if new.status_code='VOTA_STAATUS_C' and OLD.status_code!='VOTA_STAATUS_C' THEN
			--lisame
			if new.is_vocational=true THEN
				for r in (SELECT cmo.id,ar.id as apel_id, clf.value as grade, afr.apel_school_id, --sp.study_period_id,
												case when afr.apel_school_id is not null then afr.name_et else cm.name_et end as name_et,
												case when afr.apel_school_id is not null then afr.name_en else cm.name_en end as name_en,
												case when afr.apel_school_id is not null then afr.credits else cm.credits end as credits, afr.grade_code,
												afr.teachers, afr.apel_school_id, ar.id as record_id,afr.is_optional,afr.curriculum_version_hmodule_id,null as study_period_id,afr.grade_date,
                        array(select sm.curriculum_version_omodule_id from apel_application_formal_replaced_subject_or_module sm where sm.apel_application_record_id=ar.id and sm.curriculum_version_omodule_theme_id is null) as arr
									from apel_application_record ar
											 join apel_application_formal_subject_or_module afr on ar.id=afr.apel_application_record_id
											 join classifier clf on clf.code=afr.grade_code
											 left join apel_school aps on afr.apel_school_id=aps.id
											 left join curriculum_version_omodule cmo on afr.curriculum_version_omodule_id=cmo.id
											 left join curriculum_module cm on cmo.curriculum_module_id=cm.id
									WHERE ar.apel_application_id=NEW.id and afr.transfer=true	and (select count(sm.curriculum_version_omodule_id) from apel_application_formal_replaced_subject_or_module sm where sm.apel_application_record_id=ar.id and sm.curriculum_version_omodule_theme_id is null)>0														
									union
									SELECT cmo.id,ar.id as apel_id,clf.value as grade, null,--sp.study_period_id,
										cm.name_et,
										cm.name_en,
										cm.credits ,air.grade_code,
										null, null, ar.id as record_id,air.is_optional,air.curriculum_version_hmodule_id, null as study_period_id, now(), null
										from apel_application_record ar
												 join apel_application_informal_subject_or_module air on ar.id=air.apel_application_record_id
												 join curriculum_version_omodule cmo on air.curriculum_version_omodule_id=cmo.id
												 join curriculum_module cm on cmo.curriculum_module_id=cm.id
												 join classifier clf on clf.code=air.grade_code
										WHERE ar.apel_application_id=NEW.id and air.transfer=true and air.curriculum_version_omodule_theme_id is null 
				)
				LOOP
					p_id:=get_study_year(r.grade_date::date,NEW.school_id::int);
					insert into student_vocational_result (
							student_id,			
							curriculum_version_omodule_id,	
						  apel_application_record_id, apel_school_id,	
							grade,	grade_date,grade_mark,grade_code,
							credits,	teachers,	inserted, 
							module_name_et,	module_name_en,study_year_id,arr_modules)
					values(NEW.student_id,
								 r.id,r.apel_id,	r.apel_school_id, r.grade,r.grade_date,	
								case r.grade when '0' then 0 when '1' then 1 when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else null end,
							r.grade_code,	r.credits,	r.teachers,	now(), r.name_et,	r.name_en, case when p_id > 0 then p_id else null end, r.arr);
					--päevikud, kus on olemas õppur, aga lõpptulemus puudub
					for rr in (select distinct jes.id, jes.grade_code, js.id as s_id, je.id as e_id
										from journal jj
												 join journal_entry je on jj.id=je.journal_id
												 join journal_student js on jj.id=js.journal_id
												 join journal_omodule_theme jot on jj.id=jot.journal_id
												 join curriculum_version_omodule_theme cot on jot.curriculum_version_omodule_theme_id=cot.id
												 left join journal_entry_student jes on je.id=jes.journal_entry_id and js.id=jes.journal_student_id
										where je.entry_type_code='SISSEKANNE_L' and js.student_id=NEW.student_id and 
													((r.arr is null or cardinality(r.arr) = 0)  and cot.curriculum_version_omodule_id=r.id or 
													 cardinality(r.arr)  > 0 and cot.curriculum_version_omodule_id=any(r.arr)))
					loop
						if rr.id is null THEN
							insert into journal_entry_student(journal_entry_id,journal_student_id,grade_code,grade_inserted,inserted,version,inserted_by,grade_inserted_by)
							values(rr.e_id,rr.s_id,case when cardinality(r.arr) > 1 then 'KUTSEHINDAMINE_A' else r.grade_code end,now(),now(),0,NEW.changed_by,NEW.changed_by);
						ELSE
							if (case when cardinality(r.arr) > 1 then 'KUTSEHINDAMINE_A' else r.grade_code end) !=coalesce(rr.grade_code,'x') then
								insert into journal_entry_student_history(journal_entry_student_id,grade_code,grade_inserted,inserted,version,inserted_by,grade_inserted_by)
								select rr.id,grade_code,grade_inserted,now(),0,NEW.changed_by,grade_inserted_by
								from journal_entry_student where id=rr.id and coalesce(grade_code,'x')!='x';
								update journal_entry_student set grade_code=case when cardinality(r.arr) > 1 then 'KUTSEHINDAMINE_A' else r.grade_code end,grade_inserted=now(),grade_inserted_by=NEW.changed_by where id=rr.id;
							end if;
						end if;
					end loop;
				end loop;
				x:=upd_student_curriculum_completion(new.student_id);
			ELSE
				for r in (SELECT subj.id as subject_id,
										case when s.is_letter_grade then clf.value2 else clf.value end as grade, --sp.study_period_id,
										case when afr.apel_school_id is not null then afr.name_et else subj.name_et end as name_et,
										case when afr.apel_school_id is not null then afr.name_en else subj.name_en end as name_en,
										case when afr.apel_school_id is not null then afr.subject_code else subj.code end as code,
										case when afr.apel_school_id is not null then afr.credits else subj.credits end as credits, afr.grade_code,
										afr.teachers, afr.apel_school_id, ar.id as record_id,afr.is_optional,afr.curriculum_version_hmodule_id,null as study_period_id,afr.grade_date
										from apel_application_record ar
												 join apel_application_formal_subject_or_module afr on ar.id=afr.apel_application_record_id
												 join classifier clf on clf.code=afr.grade_code
												 left join apel_school aps on afr.apel_school_id=aps.id
												 left join subject subj on afr.subject_id=subj.id
		 										 join apel_application aa on ar.apel_application_id = aa.id
		 										 join school s on aa.school_id = s.id
										WHERE ar.apel_application_id=NEW.id and afr.transfer=true
										union
										SELECT subj.id,
										case when s.is_letter_grade then clf.value2 else clf.value end as grade,
										subj.name_et,
										subj.name_en,
										subj.code,
										subj.credits ,air.grade_code,
										null, null, ar.id as record_id,air.is_optional,air.curriculum_version_hmodule_id, null as study_period_id, coalesce(aa.confirmed,now())
										from apel_application_record ar
												 join apel_application_informal_subject_or_module air on ar.id=air.apel_application_record_id
												 join subject subj on air.subject_id=subj.id
												 join classifier clf on clf.code=air.grade_code
		 										 join apel_application aa on ar.apel_application_id = aa.id
		 										 join school s on aa.school_id = s.id
										WHERE ar.apel_application_id=NEW.id and air.transfer=true)
				LOOP
					insert into student_higher_result (
						student_id,			subject_id,				apel_application_record_id,				grade,				grade_date,				grade_mark,				grade_code,
										apel_school_id,				inserted,
						subject_name_et,				subject_name_en,				teachers,				credits,				subject_code,				is_optional,study_period_id,curriculum_version_hmodule_id) 
					values(
						NEW.student_id,r.subject_id,r.record_id,r.grade,r.grade_date,
						case when (r.grade='0' or r.grade='F') then 0 when (r.grade='1' or r.grade='E') then 1 when (r.grade='2' or r.grade='D') then 2 when (r.grade='3' or r.grade='C') then 3 when (r.grade='4' or r.grade='B') then 4 when (r.grade='5' or r.grade='A' and r.grade_code = 'KORGHINDAMINE_5') then 5 else null end,
						r.grade_code,
						r.apel_school_id,--apel_school_id
						now(),
						r.name_et,coalesce(r.name_en,r.name_et),r.teachers,	r.credits,r.code,				r.is_optional,null,r.curriculum_version_hmodule_id) returning id into p_id; --is_optional

				if coalesce(r.subject_id,0) > 0 then
					--select distinct first_value(id)over(partition by r.subject_id order by grade_date desc nulls last) into p_id from student_higher_result where student_id=NEW.student_id and subject_id=r.subject_id;
					update student_higher_result set is_active=false where student_id=NEW.student_id and subject_id=r.subject_id and id!=p_id;
				end if;
				x:=upd_student_curriculum_completion(new.student_id);
				--return null;
			end loop;
		end if;
	elsif new.status_code!='VOTA_STAATUS_C' and OLD.status_code='VOTA_STAATUS_C' THEN
			--kustutame kõik ära
			if new.is_vocational=true THEN
				delete from student_vocational_result where apel_application_record_id in (select aa.id from apel_application_record aa where aa.apel_application_id=NEW.id);
				x:=upd_student_curriculum_completion(new.student_id);
			ELSE
				delete from student_higher_result_module where student_higher_result_id in (select id from student_higher_result where apel_application_record_id in (select aa.id from apel_application_record aa where aa.apel_application_id=NEW.id));
				delete from student_higher_result where apel_application_record_id in (select aa.id from apel_application_record aa where aa.apel_application_id=NEW.id);
				for r in (select distinct first_value(id)over(partition by subject_id order by grade_date desc nulls last) as id, subject_id 
								from student_higher_result 
								where student_id=NEW.student_id)
				loop
					update student_higher_result ss 
							set is_active=case when ss.id=r.id then true else false end
					where ss.student_id=NEW.student_id and r.subject_id=ss.subject_id;
				end LOOP;
				x:=upd_student_curriculum_completion(new.student_id);
			end if;
		end if;
	end if;
	return null;
end;
$function$
;


CREATE OR REPLACE FUNCTION public.ins_upd_del_result()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
declare
    u_count integer;
		b_count integer;
		r record;
		x integer;
		p_id bigint;
	st_id bigint;
begin
	if tg_op in ('DELETE') then
		st_id:=old.student_id;
	else
		st_id:=new.student_id;
	end if;
	if tg_op in ('INSERT','UPDATE') and NEW.id is not null and COALESCE(NEW.grade,'x')='x' or tg_op in ('DELETE') THEN
		if tg_op in ('INSERT','UPDATE') THEN	
			delete from student_higher_result_module where student_higher_result_id in (select id from student_higher_result where protocol_student_id=NEW.id);
			delete from student_higher_result where protocol_student_id=NEW.id;
			delete from student_vocational_result where protocol_student_id=NEW.id;
		ELSE
			delete from student_higher_result_module where student_higher_result_id in (select id from student_higher_result where protocol_student_id=old.id);
			delete from student_higher_result where protocol_student_id=old.id;
			delete from student_vocational_result where protocol_student_id=old.id;
		end if;
		for r in (select distinct first_value(id)over(partition by subject_id order by case when coalesce(apel_application_record_id,0)=0 then 1 else 0 end, grade_date desc nulls last) as id, subject_id 
								from student_higher_result 
								where student_id=st_id)
		loop
			update student_higher_result ss 
				set is_active=case when ss.id=r.id then true else false end
			where ss.student_id=st_id and r.subject_id=ss.subject_id;
		end LOOP;
		x:=upd_student_curriculum_completion(st_id);
	elsif NEW.id is not null then
		for r in (SELECT coalesce(ph.final_subject_id, sp.subject_id) as subject_id,clf.value as grade, sp.study_period_id,pp.school_id,
									coalesce(hn.id,ds.curriculum_version_hmodule_id) as curriculum_version_hmodule_id,coalesce(shs.is_optional,ds.is_optional) is_optional,
											--curriculum_version_hmodule_id
											subj.name_et,subj.name_en,subj.code,subj.credits,
											--is_optional
											(select string_agg(pers.firstname||' '||pers.lastname,', ') 
												from subject_study_period_teacher st join teacher tt on st.teacher_id=tt.id join person pers on tt.person_id=pers.id
													where st.subject_study_period_id=sp.id) as teachers --teachers
							from protocol pp 
									 join protocol_hdata ph on pp.id=ph.protocol_id
									 join student st on st.id=new.student_id
									 left join subject_study_period sp on ph.subject_study_period_id=sp.id
									 left join (declaration_subject ds join declaration  dd on ds.declaration_id=dd.id and dd.student_id=new.student_id) on sp.id=ds.subject_study_period_id 
									 join subject subj on sp.subject_id=subj.id or ph.final_subject_id = subj.id
									 join classifier clf on clf.code=NEW.grade_code
									 left join (curriculum_version_hmodule_subject shs join curriculum_version_hmodule hn on shs.curriculum_version_hmodule_id=hn.id) 
															on shs.subject_id=ph.final_subject_id and 
																hn.curriculum_version_id=st.curriculum_version_id and hn.type_code in ('KORGMOODUL_F','KORGMOODUL_L')
							where new.protocol_id=pp.id)
		LOOP
			update student_higher_result set 
				grade=coalesce(NEW.grade,r.grade),
				grade_date=NEW.grade_date,
				grade_mark=coalesce(NEW.grade_mark,case r.grade when '0' then 0 when '1' then 1 when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else null end),
				grade_code=NEW.grade_code,
				apel_school_id=null,
				subject_name_et=r.name_et,
				subject_name_en=coalesce(r.name_en,r.name_et),
				teachers=r.teachers,
				credits=r.credits,
				subject_code=r.code,
				curriculum_version_hmodule_id=r.curriculum_version_hmodule_id,
				is_optional=coalesce(r.is_optional,false),
				--is_optional=false,
				subject_id=r.subject_id,
				changed=now(),
				study_period_id=coalesce(r.study_period_id, get_study_period(NEW.grade_date::date, r.school_id::int))
			where protocol_student_id=NEW.id;
			GET DIAGNOSTICS u_count = ROW_COUNT;

			if coalesce(u_count,0)=0 THEN
				insert into student_higher_result (
					student_id,			subject_id,				protocol_student_id,				grade,				grade_date,				grade_mark,				grade_code,
					apel_application_record_id,				apel_school_id,				inserted, curriculum_version_hmodule_id,is_optional,
					subject_name_et,				subject_name_en,				teachers,				credits,				subject_code,				study_period_id) 
				values(
					NEW.student_id,r.subject_id,NEW.id,coalesce(NEW.grade,r.grade),NEW.grade_date,
					coalesce(NEW.grade_mark,case r.grade when '0' then 0 when '1' then 1 when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else null end),				NEW.grade_code,
					null,--apel_application_record_id,
					null,--apel_school_id
					now(),r.curriculum_version_hmodule_id,coalesce(r.is_optional,false),
					r.name_et,coalesce(r.name_en,r.name_et),r.teachers,	r.credits,r.code,				r.study_period_id); --is_optional
			end if;

			if coalesce(r.subject_id,0) > 0 then
				--select distinct first_value(id)over(partition by r.subject_id order by case when coalesce(apel_application_record_id,0)=0 then 1 else 0 end, grade_date desc nulls last, ) into p_id 
				--from student_higher_result where student_id=case when tg_op='DELETE' then old.student_id else NEW.student_id end and subject_id=r.subject_id;
				select distinct first_value(sr.id)over(partition by sr.subject_id order by case when coalesce(sr.apel_application_record_id,0)=0 then 1 else 0 end, sr.grade_date desc nulls last,ph.type_code asc,ph.inserted desc) into p_id 
				from student_higher_result sr
						 left join protocol_student ps on sr.protocol_student_id=ps.id
						 left join protocol_hdata ph on ps.protocol_id=ph.protocol_id
				where sr.student_id=case when tg_op='DELETE' then old.student_id else NEW.student_id end and sr.subject_id=r.subject_id;
				update student_higher_result set is_active=false where student_id=st_id and subject_id=r.subject_id and id!=p_id;
			end if;

			/*--kustutame Ć¼leliigset eelmist rida
			delete from student_higher_result
WHERE id IN (SELECT id
              FROM (SELECT id,
                             ROW_NUMBER() OVER (partition BY column1, column2, column3 ORDER BY id) AS rnum
                     FROM tablename) t
              WHERE t.rnum > 1);*/
			x:=upd_student_curriculum_completion(new.student_id);
			return null;
		end loop;
    for r in (select crm.name_et, crm.name_en, crm.credits, vv.curriculum_version_omodule_id, crm.credits, pers.firstname||' '||pers.lastname as teachers, clf.value as grade,
							  vv.study_year_id, (select count(*) from student_vocational_result sr where sr.curriculum_version_omodule_id=vv.curriculum_version_omodule_id and sr.student_id=NEW.student_id) as total
							from protocol pp 
									 join protocol_vdata vv on pp.id=vv.protocol_id
									 join curriculum_version_omodule cro on vv.curriculum_version_omodule_id=cro.id
									 join curriculum_module crm on cro.curriculum_module_id=crm.id
									 join teacher tt on vv.teacher_id=tt.id 
									 join person pers on tt.person_id=pers.id
									 join classifier clf on clf.code=NEW.grade_code
							where new.protocol_id=pp.id)
		LOOP
			update student_vocational_result set 
				curriculum_version_omodule_id=r.curriculum_version_omodule_id,				
				grade=coalesce(NEW.grade,r.grade),				
				grade_date=NEW.grade_date,				
				grade_mark=coalesce(NEW.grade_mark,case r.grade when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else null end),				
				grade_code=NEW.grade_code,
				credits=r.credits,	
				teachers=r.teachers,	
				changed=now(), 
				module_name_et=r.name_et,	
				module_name_en=r.name_en,
				study_year_id=r.study_year_id
			where protocol_student_id=NEW.id;
			GET DIAGNOSTICS u_count = ROW_COUNT;

			if coalesce(u_count,0)=0 THEN
				insert into student_vocational_result (
					student_id,			curriculum_version_omodule_id,				protocol_student_id,		grade,				grade_date,				grade_mark,				grade_code,
					credits,	teachers,	inserted, module_name_et,	module_name_en,study_year_id)
				values(NEW.student_id,r.curriculum_version_omodule_id,NEW.id,		coalesce(NEW.grade,r.grade),	NEW.grade_date,	
							coalesce(NEW.grade_mark,case r.grade when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else null end),	NEW.grade_code,
					r.credits,	r.teachers,	now(), r.name_et,	r.name_en, r.study_year_id);
			end if;
			
--raise notice 'siin %', r.total;
			-- kustutame Ć¼leliigsed vanad read
			if r.total > 1 THEN
					DELETE FROM student_vocational_result 
						WHERE id IN (SELECT id
              FROM (SELECT id,
                             ROW_NUMBER() OVER (partition BY curriculum_version_omodule_id, student_id ORDER BY grade_date desc nulls last, id desc) AS rnum
                     FROM student_vocational_result where student_id=NEW.student_id and curriculum_version_omodule_id=r.curriculum_version_omodule_id) t
              WHERE t.rnum > 1);
			end if;
			x:=upd_student_curriculum_completion(new.student_id);
			return null;
		end loop;
  end if;
	return null;
end;
$function$
;

do $$
declare
	r record;
	t_code varchar(100):='';
	p_lastn varchar(100):='';
	p_firstn varchar(100):='';
	p_max int:=12;
	p_kokku int:=0;
	pb_olemas boolean:=false;
	i int:=1;
	arr varchar(1000) array;
  p1 varchar(1000);
begin
	for r in (select t.id, t.school_id, p.firstname,p.lastname,p.birthdate from teacher t join person p on t.person_id=p.id where t.is_active=true and t.untis_code is null)
	loop
		p_lastn:=substr(coalesce(r.lastname,'x'),1,4);
		p_firstn:=substr(coalesce(r.firstname,'x'),1,i);
		t_code:=p_lastn||p_firstn;
		while(true)
		loop
			i:=i+1;
			select count(t.*) into p_kokku from teacher t join person p on t.person_id=p.id where t.school_id=r.school_id and upper(p.lastname) like upper(p_lastn)||'%' and upper(p.firstname) like upper(p_firstn)||'%';
			if p_kokku > 0 then
				pb_olemas:=true;
				p_firstn:=substr(coalesce(r.firstname,'x'),1,i);
				t_code:=p_lastn||p_firstn;
			else
				pb_olemas:=false;
				exit;
			end if;
			if i >= 9 then
				pb_olemas:=true;
				exit;
			end if;
		end loop;
		if pb_olemas and length(t_code)>=12 then
			if r.birthdate is not null then
				t_code:=substr(t_code,1,5)||to_Char(r.birthdate,'ddmmyy');
				--raise notice '1. % ',t_code;
			else
				t_code:=substr(t_code,1,5)||r.id::text;
				--raise notice '2. % ',t_code;
			end if;
		end if;
		update teacher set untis_code=t_code where id=r.id;
	end loop;

	--journals
	for r in (select j.id, trim(j.name_et) as name_et,j.school_id,j.study_year_id
						from journal j
								 join study_year sy on j.study_year_id=sy.id
						where sy.end_date > now() and j.untis_code is null)
	loop
		t_code:='';
		if length(r.name_Et) < 4 then
			t_code:=r.name_et;
		else
			arr:=string_to_array(r.name_et,' ');
			for i in 1..array_length(arr,1)
			loop
				p1:=arr[i];
				t_code:=t_code||substr(trim(p1),1,2);
				if length(t_code) > 11 then
					exit;
				end if;
			end loop;
		end if;
		select count(*) into p_kokku from journal where school_id=r.school_id and study_year_id=r.study_year_id and id!=r.id and upper(untis_code)=upper(t_code);
		if p_kokku > 0 then
			t_code:=t_code||'_'||r.id::text;
		end if;
		update journal set untis_code=t_code where id=r.id;
	end loop;
end;
$$;