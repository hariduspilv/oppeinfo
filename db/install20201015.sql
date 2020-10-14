\c hois

alter TABLE student_higher_result add column IF NOT EXISTS grading_schema_row_id bigint NULL;
alter TABLE student_vocational_result add column IF NOT EXISTS grading_schema_row_id bigint NULL;
COMMENT ON COLUMN "student_higher_result"."grading_schema_row_id"	IS 'viide hindamissüsteemi tulemusele';
COMMENT ON COLUMN "student_vocational_result"."grading_schema_row_id"	IS 'viide hindamissüsteemi tulemusele';

CREATE INDEX IF NOT EXISTS "IXFK_student_vocational_result_grading_schema_row" ON "student_vocational_result" ("grading_schema_row_id" ASC);
CREATE INDEX IF NOT EXISTS "IXFK_student_higher_result_grading_schema_row" ON "student_higher_result" ("grading_schema_row_id" ASC);

ALTER TABLE student_higher_result DROP CONSTRAINT IF EXISTS "FK_student_higher_result_grading_schema_row";
ALTER TABLE student_vocational_result DROP CONSTRAINT IF EXISTS "FK_student_vocational_result_grading_schema_row";

ALTER TABLE "student_higher_result" ADD CONSTRAINT "FK_student_higher_result_grading_schema_row"	FOREIGN KEY ("grading_schema_row_id") REFERENCES "grading_schema_row" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "student_vocational_result" ADD CONSTRAINT "FK_student_vocational_result_grading_schema_row"	FOREIGN KEY ("grading_schema_row_id") REFERENCES "grading_schema_row" ("id") ON DELETE No Action ON UPDATE No Action;

alter table journal_entry_student_history alter column grade_code drop not null;

ALTER TABLE scholarship_application ALTER COLUMN wag_mark TYPE numeric(6,3);
ALTER TABLE scholarship_application ALTER COLUMN last_period_wag_mark TYPE numeric(6,3);

alter table study_year_schedule_legend alter column code type varchar(4); 

alter table school_query_criteria alter column criteria_val1 type text;
alter table school_query_criteria alter column criteria_val2 type text;

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
									coalesce(cvh.id,coalesce(hn.id,ds.curriculum_version_hmodule_id)) as curriculum_version_hmodule_id,coalesce(shs.is_optional,ds.is_optional) is_optional,
											--curriculum_version_hmodule_id
											coalesce(cvh.name_et,subj.name_et) as name_et,coalesce(cvh.name_en,subj.name_en) name_en,subj.code,
											coalesce(cvh.total_credits,subj.credits) credits,
											--is_optional
											case when ppp.id is not null then ppp.firstname||' '||ppp.lastname else (select string_agg(pers.firstname||' '||pers.lastname,', ') 
												from subject_study_period_teacher st join teacher tt on st.teacher_id=tt.id join person pers on tt.person_id=pers.id
													where st.subject_study_period_id=sp.id) end as teachers, --teachers
									cvh.id as cvh_id
							from protocol pp 
									 join protocol_hdata ph on pp.id=ph.protocol_id
									 join student st on st.id=new.student_id
									 left join subject_study_period sp on ph.subject_study_period_id=sp.id
									 left join (declaration_subject ds join declaration  dd on ds.declaration_id=dd.id and dd.student_id=new.student_id) on sp.id=ds.subject_study_period_id 
									 left join subject subj on ph.curriculum_version_hmodule_id is null and (sp.subject_id=subj.id or ph.final_subject_id = subj.id)
									 left join curriculum_version_hmodule cvh on ph.curriculum_version_hmodule_id=cvh.id
									 left join (teacher ttt join person ppp on ttt.person_id=ppp.id) on ph.teacher_id=ttt.id
									 join classifier clf on clf.code=NEW.grade_code
									 left join (curriculum_version_hmodule_subject shs join curriculum_version_hmodule hn on shs.curriculum_version_hmodule_id=hn.id) 
															on shs.subject_id=ph.final_subject_id and 
																hn.curriculum_version_id=st.curriculum_version_id and hn.type_code in ('KORGMOODUL_F','KORGMOODUL_L')
							where new.protocol_id=pp.id and (subj.id is not null or cvh.id is not null))
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
				study_period_id=coalesce(r.study_period_id, get_study_period(NEW.grade_date::date, r.school_id::int)),
				is_module=case when coalesce(r.cvh_id,0) > 0 then true else false end,
			  grading_schema_row_id=new.grading_schema_row_id
			where protocol_student_id=NEW.id;
			GET DIAGNOSTICS u_count = ROW_COUNT;

			if coalesce(u_count,0)=0 THEN
				insert into student_higher_result (
					student_id,			subject_id,				protocol_student_id,				grade,				grade_date,				grade_mark,				grade_code,
					apel_application_record_id,				apel_school_id,				inserted, curriculum_version_hmodule_id,is_optional,
					subject_name_et,				subject_name_en,				teachers,				credits,				subject_code,				study_period_id, is_module,
			  grading_schema_row_id) 
				values(
					NEW.student_id,r.subject_id,NEW.id,coalesce(NEW.grade,r.grade),NEW.grade_date,
					coalesce(NEW.grade_mark,case r.grade when '0' then 0 when '1' then 1 when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else null end),				NEW.grade_code,
					null,--apel_application_record_id,
					null,--apel_school_id
					now(),r.curriculum_version_hmodule_id,coalesce(r.is_optional,false),
					r.name_et,coalesce(r.name_en,r.name_et),r.teachers,	r.credits,r.code,				coalesce(r.study_period_id, get_study_period(NEW.grade_date::date, r.school_id::int)),case when coalesce(r.cvh_id,0) > 0 then true else false end,
         new.grading_schema_row_id); --is_optional
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
			elsif coalesce(r.cvh_id,0) > 0 then
				select distinct first_value(sr.id)over(partition by sr.curriculum_version_hmodule_id order by case when coalesce(sr.apel_application_record_id,0)=0 then 1 else 0 end, sr.grade_date desc nulls last,ph.type_code asc,ph.inserted desc) into p_id 
				from student_higher_result sr
						 left join protocol_student ps on sr.protocol_student_id=ps.id
						 left join protocol_hdata ph on ps.protocol_id=ph.protocol_id
				where sr.student_id=case when tg_op='DELETE' then old.student_id else NEW.student_id end and sr.curriculum_version_hmodule_id=r.cvh_id and sr.is_module=true;
				update student_higher_result set is_active=false where student_id=st_id and curriculum_version_hmodule_id=r.cvh_id and is_module=true and id!=p_id;
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
				study_year_id=r.study_year_id,
			  grading_schema_row_id=new.grading_schema_row_id
			where protocol_student_id=NEW.id;
			GET DIAGNOSTICS u_count = ROW_COUNT;

			if coalesce(u_count,0)=0 THEN
				insert into student_vocational_result (
					student_id,			curriculum_version_omodule_id,				protocol_student_id,		grade,				grade_date,				grade_mark,				grade_code,
					credits,	teachers,	inserted, module_name_et,	module_name_en,study_year_id,grading_schema_row_id)
				values(NEW.student_id,r.curriculum_version_omodule_id,NEW.id,		coalesce(NEW.grade,r.grade),	NEW.grade_date,	
							coalesce(NEW.grade_mark,case r.grade when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else null end),	NEW.grade_code,
					r.credits,	r.teachers,	now(), r.name_et,	r.name_en, r.study_year_id,NEW.grading_schema_row_id);
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
