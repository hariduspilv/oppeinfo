\c tahvel_20210104

alter table subject alter column credits type numeric(5,2);
alter table curriculum_version_omodule_theme alter column credits type numeric(5,2);
alter table curriculum_module alter column credits type numeric(5,2);
alter table apel_application_formal_subject_or_module alter column credits type numeric(5,2);
alter table base_module alter column credits type numeric(5,2);
alter table base_module_theme alter column credits type numeric(5,2);
alter table contract alter column credits type numeric(5,2);
alter table contract_module_subject alter column credits type numeric(5,2);
alter table curriculum_version_omodule_year_capacity alter column credits type numeric(5,2);
alter table diploma_supplement alter column credits type numeric(6,2);
alter table diploma_supplement_study_result alter column credits type numeric(5,2);
alter table practice_journal_module_subject alter column credits type numeric(5,2);
alter table state_curriculum alter column credits type numeric(5,2);
alter table state_curriculum_module alter column credits type numeric(5,2);
alter table student_curriculum_completion alter column credits type numeric(5,2);
alter table student_higher_result alter column credits type numeric(5,2);
alter table student_vocational_result alter column credits type numeric(5,2);

insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version)
values('TEATESTAATUS_K','K','Kustutatud','TEATESTAATUS',current_timestamp(3),true,true,true,0);

--ALTER SEQUENCE "application_planned_subject_equivalent _id_seq" RENAME TO "application_planned_subject_equivalent_id_seq";
ALTER SEQUENCE "scholarschip_terme_course_id_seq" RENAME TO "scholarschip_term_course_id_seq";
ALTER SEQUENCE "state_curriculum_state_curriculum_id_seq" RENAME TO "state_curriculum_id_seq";
ALTER SEQUENCE "scholarschip_term_course_id_seq" RENAME TO "scholarship_term_course_id_seq";

alter table student_curriculum_completion alter column study_backlog type numeric(5,2);
alter table student_curriculum_completion alter column study_backlog_without_graduate type numeric(5,2);
alter table student_curriculum_completion alter column credits_last_study_period type numeric(5,2);
alter table student_curriculum_completion alter column credits_before_current_study_period type numeric(5,2);
alter table student_curriculum_completion alter column study_optional_backlog type numeric(5,2);
alter table scholarship_application alter column credits type numeric(5,2);

alter table student 
add column "is_school_meal" boolean NULL,    -- koolilõuna toetus
add column "school_meal_changed" timestamp without time zone NULL,    -- koolilõuna toetuse muutmise kp
add column "school_meal_changed_by" varchar(100)	 NULL    -- koolilõuna toetuse muutja
;

COMMENT ON COLUMN "student"."is_school_meal"	IS 'koolilõuna toetus';
COMMENT ON COLUMN "student"."school_meal_changed"	IS 'koolilõuna toetuse muutmise kp';
COMMENT ON COLUMN "student"."school_meal_changed_by"	IS 'koolilõuna toetuse muutja';


CREATE TABLE "curriculum_version_nominal_capacity"
(
	"id" bigserial NOT NULL ,
	"curriculum_version_id" bigint NOT NULL,    -- viide õppekava versioonile
	"credits" numeric(5,2) NOT NULL,    -- EAP
	"curriculum_version_speciality_id" bigint NOT NULL,    -- viide peaerialae
	"period_nr" smallint NOT NULL,    -- semestri number (1, 2, 3, 4 jne), nt 2. õppeaasta sügissem on 3
	"inserted" timestamp without time zone NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"version" integer NOT NULL,
	"changed" timestamp without time zone NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "curriculum_version_nominal_capacity"	IS 'õppekava nominaaljaotus';
COMMENT ON COLUMN "curriculum_version_nominal_capacity"."curriculum_version_id"	IS 'viide õppekava versioonile';
COMMENT ON COLUMN "curriculum_version_nominal_capacity"."credits"	IS 'EAP';
COMMENT ON COLUMN "curriculum_version_nominal_capacity"."curriculum_version_speciality_id"	IS 'viide peaerialae';
COMMENT ON COLUMN "curriculum_version_nominal_capacity"."period_nr"	IS 'semestri number (1, 2, 3, 4 jne), nt 2. õppeaasta sügissem on 3';

/* Create Primary Keys, Indexes, Uniques, Checks */
ALTER TABLE "curriculum_version_nominal_capacity" ADD CONSTRAINT "PK_curriculum_version_nominal_capacity"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_curriculum_version_nominal_capacity_curriculum_version" ON "curriculum_version_nominal_capacity" ("curriculum_version_id" ASC);
CREATE INDEX "IXFK_curriculum_version_nominal_capacity_curriculum_version_speciality" ON "curriculum_version_nominal_capacity" ("curriculum_version_speciality_id" ASC);
/* Create Foreign Key Constraints */

ALTER TABLE "curriculum_version_nominal_capacity" ADD CONSTRAINT "FK_curriculum_version_nominal_capacity_curriculum_version"	FOREIGN KEY ("curriculum_version_id") REFERENCES "public"."curriculum_version" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "curriculum_version_nominal_capacity" ADD CONSTRAINT "FK_curriculum_version_nominal_capacity_curriculum_version_speciality"	FOREIGN KEY ("curriculum_version_speciality_id") REFERENCES "public"."curriculum_version_speciality" ("id") ON DELETE No Action ON UPDATE No Action;

alter table subject_study_period_teacher add column is_diploma_supplement boolean;
update subject_study_period_teacher set is_diploma_supplement=true;
comment on column subject_study_period_teacher.is_diploma_supplement is 'kas õppejõu nimi tuleb kuvada akad. õiendil';
--alter table subject_study_period_teacher alter column is_diploma_supplement set not null;

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
													where st.subject_study_period_id=sp.id and coalesce(st.is_diploma_supplement,true)=true) end as teachers, --teachers
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
				subject_name_et=coalesce(r.name_et,'-'),
				subject_name_en=coalesce(r.name_en,r.name_et),
				teachers=substr(r.teachers,1,255),
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
					coalesce(r.name_et,'-'),coalesce(r.name_en,r.name_et),substr(r.teachers,1,255),	r.credits,r.code,				coalesce(r.study_period_id, get_study_period(NEW.grade_date::date, r.school_id::int)),case when coalesce(r.cvh_id,0) > 0 then true else false end,
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
				teachers=substr(r.teachers,1,255),	
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

CREATE OR REPLACE FUNCTION public.upd_subject_study_period_teachers()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
declare
		p_id integer:=0;
		p_teachers varchar(1000):='';
		r record;
begin
	if tg_op in ('INSERT') then
		p_id:=new.subject_study_period_id;
	elseif tg_op in ('DELETE') then
	  p_id:=old.subject_study_period_id;
	elsif tg_op in ('UPDATE') and coalesce(new.is_diploma_supplement,true)!=coalesce(old.is_diploma_supplement,true) then
		p_id:=new.subject_study_period_id;
  end if; 

	if coalesce(p_id,0) > 0 then
		select string_agg(pers.firstname||' '||pers.lastname,', ') into p_teachers
		from subject_study_period_teacher st join teacher tt on st.teacher_id=tt.id join person pers on tt.person_id=pers.id
		where st.subject_study_period_id=p_id and coalesce(st.is_diploma_supplement,true)=true;
		
		for r in (select shr.id
							from student_higher_result shr
									 join protocol_student ps on shr.protocol_student_id = ps.id 
									 join protocol_hdata ph on ps.protocol_id = ph.protocol_id
							where ph.subject_study_period_id=p_id)
		loop
			update student_higher_result 
			set teachers=substr(p_teachers,1,255)
			where id=r.id;
		end loop;
	end if;
	return null;
end;
$function$
;

create trigger subject_study_period_teacher_upd_result after
insert or update or delete
    on
    public.subject_study_period_teacher for each row execute procedure upd_subject_study_period_teachers();
	
alter table teacher add column color varchar(7);
comment on column teacher.color is 'värvikood kujul #FFFFFF';

alter table sais_application add column "secondary_school_country_code" varchar(100)	 NULL    -- keskhariduse omandamise rikk, viide klassiifkaatorile RIIK
;
alter table directive_student add column "secondary_school_country_code" varchar(100)	 NULL    -- keskhariduse omandamise rikk, viide klassiifkaatorile RIIK
;
alter table student add column "secondary_school_country_code" varchar(100)	 NULL    -- keskhariduse omandamise rikk, viide klassiifkaatorile RIIK
;
alter table student_history add column "secondary_school_country_code" varchar(100)	 NULL    -- keskhariduse omandamise rikk, viide klassiifkaatorile RIIK
;

COMMENT ON COLUMN "sais_application"."secondary_school_country_code"	IS 'keskhariduse omandamise rikk, viide klassiifkaatorile RIIK';
COMMENT ON COLUMN "directive_student"."secondary_school_country_code"	IS 'keskhariduse omandamise rikk, viide klassiifkaatorile RIIK';
COMMENT ON COLUMN "student"."secondary_school_country_code"	IS 'keskhariduse omandamise rikk, viide klassiifkaatorile RIIK';
COMMENT ON COLUMN "student_history"."secondary_school_country_code"	IS 'keskhariduse omandamise rikk, viide klassiifkaatorile RIIK';

CREATE INDEX "IXFK_sais_application_classifier_09" ON "public"."sais_application" ("secondary_school_country_code" ASC);
CREATE INDEX "IXFK_directive_student_classifier_13" ON "public"."directive_student" ("secondary_school_country_code" ASC);
CREATE INDEX "IXFK_student_classifier_12" ON "public"."student" ("secondary_school_country_code" ASC);
CREATE INDEX "IXFK_student_history_classifier_10" ON "public"."student_history" ("secondary_school_country_code" ASC);
ALTER TABLE "sais_application" ADD CONSTRAINT "FK_sais_application_classifier_09"	FOREIGN KEY ("secondary_school_country_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "directive_student" ADD CONSTRAINT "FK_directive_student_classifier_13"	FOREIGN KEY ("secondary_school_country_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "student" ADD CONSTRAINT "FK_student_classifier_11"	FOREIGN KEY ("secondary_school_country_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "student_history" ADD CONSTRAINT "FK_student_history_classifier_10"	FOREIGN KEY ("secondary_school_country_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
	

alter table curriculum_version_hmodule alter column total_credits type numeric(5,2);
alter table curriculum_version_hmodule alter column optional_study_credits type numeric(5,2);
alter table curriculum_version_hmodule alter column compulsory_study_credits type numeric(5,2);
alter table curriculum_speciality alter column credits type numeric(5,2);
alter table practice_journal alter column credits type numeric(5,2);

CREATE UNIQUE INDEX final_thesis_uq ON final_thesis (student_id);

ALTER SEQUENCE "state_curriculum_module_state_curriculum_module_id_seq" RENAME TO "state_curriculum_module_id_seq";
ALTER SEQUENCE "state_curriculum_module_occup_state_curriculum_module_occup_seq" RENAME TO "state_curriculum_module_occupation_id_seq";
ALTER SEQUENCE "state_curriculum_module_outco_state_curriculum_module_outco_seq" RENAME TO "state_curriculum_module_outcomes_id_seq";
ALTER SEQUENCE "state_curriculum_occupation_state_curriculum_occupation_id_seq" RENAME TO "state_curriculum_occupation_id_seq";



CREATE OR REPLACE FUNCTION public.upd_student_curriculum_completion(p_id bigint)
 RETURNS integer
 LANGUAGE plpgsql
AS $function$
declare 
  pb_exist boolean:=false;
	pb_exist2 boolean:=false;
	p_curr_modules bigint array;
  p_is_grade_modules boolean array;
	p_is_positive_grade_modules boolean array;
	p_curr_modules_ok boolean array;
	p_curr_module_types bigint array;
  p_curr_modules_credits numeric array;
	p_curr_modules_opt_credits numeric array;
	p_curr_modules2 bigint array;
  p_study_modules bigint array;
  p_vstudy_modules bigint array;
  p_optional numeric:=0;
	pb_modules boolean:=false; --kas on olemas jõuga moodulite täitmist
	r record;
	i int:=0;
  ii int:=0;
  iii int:=0;
  p_total int:=0;
	p_opt_credits numeric:=0;
	p_avg_credits numeric:=0;
	p_x_credits numeric:=0;
	p_avg_total_credits numeric:=0;
	p_total_credits numeric:=0;
	p_abs_credits numeric:=0;
	p_fabs_credits numeric:=0;
	p_curriculum_credits numeric:=0;
	a_count int:=0;
	pb_is_hgrade boolean:=false;

  p_vcurr_modules bigint array;
	p_vcurr_modules2 bigint array;

	p_fcurr_modules bigint array;
	p_fcurr_modules_ok bigint array;
	
	mod_id bigint;

	is_higher_curriculum boolean:=true;
	
	pb_modules_ok boolean:=true;
	p_modules_count integer:=0; --mitu moodulit on ette nähtud hindamiseks
	p_id2 bigint;
	p_final_ok boolean:=false;
BEGIN
	for r in (select distinct cvo.id,cm.id as m_id, cm.credits, cc.optional_study_credits, cm.module_code, cc.is_higher, cc.credits as curriculum_credits
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
		p_x_credits:=0;
		
		p_curr_modules_credits[i]:=r.credits;
		p_optional:=coalesce(r.optional_study_credits,0);
		p_curriculum_credits:=coalesce(r.curriculum_credits,0);
		if r.module_code='KUTSEMOODUL_L' then
			p_fcurr_modules[i]:=r.id;
		ELSE
			p_fcurr_modules[i]:=0;
		end if;
		is_higher_curriculum:=r.is_higher;
	end loop;

	if is_higher_curriculum then
		i:=0;
		--moodulite hindamise puhul mitte õppekava moodulid ei tohi arvesse minna
		for r in (select sr.id, cm.id as id2
								from student_higher_result sr
										 join student s on sr.student_id=s.id
										 join curriculum_version cv on s.curriculum_version_id=cv.id
										 left join curriculum_version_hmodule cm on cv.id=cm.curriculum_version_id and (coalesce(s.curriculum_speciality_id,0)=0 or 
																							coalesce(s.curriculum_speciality_id,0) > 0 and exists(
																									select 1 
																									from curriculum_version_hmodule_speciality hs
																											 join curriculum_version_speciality cvs on hs.curriculum_version_speciality_id=cvs.id
																									where hs.curriculum_version_hmodule_id=cm.id and
																												cvs.curriculum_speciality_id=s.curriculum_speciality_id
																												)) and sr.curriculum_version_hmodule_id=cm.id
								where sr.student_id=p_id and sr.is_module=true )
		loop
			if r.id2 is null then
				update student_higher_result sr set is_active=false where sr.id=r.id;
			else
				select distinct first_value(sr.id)over(partition by sr.curriculum_version_hmodule_id order by case when coalesce(sr.apel_application_record_id,0)=0 then 1 else 0 end, sr.grade_date desc nulls last,ph.type_code asc,ph.inserted desc) into p_id2 
				from student_higher_result sr
						 left join protocol_student ps on sr.protocol_student_id=ps.id
						 left join protocol_hdata ph on ps.protocol_id=ph.protocol_id
				where sr.student_id=p_id and sr.curriculum_version_hmodule_id=r.id2 and sr.is_module=true;
				update student_higher_result set is_active=false where student_id=p_id and curriculum_version_hmodule_id=r.id2 and is_module=true and id!=p_id2;
				update student_higher_result set is_active=true where student_id=p_id and curriculum_version_hmodule_id=r.id2 and is_module=true and id=p_id2;
			end if;
		end loop;
		
		
		--Kõrgõppurid
		for r in (select distinct cm.id as m_id, cm.total_credits,cm.optional_study_credits,cm.compulsory_study_credits, cm.type_code, cc.is_higher, cc.optional_study_credits as total_optional_study_credits,
										 sch.curriculum_version_hmodule_id as ok_id, cc.credits as curriculum_credits, cm.is_grade, shh.is_hmodules
					  from curriculum_version cv
								 join curriculum_version_hmodule cm on cv.id=cm.curriculum_version_id
								 --join curriculum_module cm on cvo.curriculum_module_id=cm.id and cv.curriculum_id=cm.curriculum_id and coalesce(cm.is_additional,false)=false and cm.module_code!='KUTSEMOODUL_V' and coalesce(cm.is_additional,false)=false
								 join curriculum cc on cv.curriculum_id=cc.id
								 join student ss on cv.id=ss.curriculum_version_id
								 join school shh on ss.school_id=shh.id 
								--jõuga täidetud moodule
								 left join student_curriculum_completion_hmodule sch on ss.id=sch.student_id and cm.id=sch.curriculum_version_hmodule_id
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
			pb_is_hgrade:=coalesce(r.is_hmodules,false);
			p_is_grade_modules[i]:=coalesce(r.is_grade,false);
			if p_is_grade_modules[i]=true then
				p_modules_count:=p_modules_count+1;
			end if;
			p_curriculum_credits:=coalesce(r.curriculum_credits,0);
			p_is_positive_grade_modules[i]:=false;
			p_curr_modules_ok[i]:=case when r.ok_id is not null and r.m_id=r.ok_id then true else false end;
			if p_curr_modules_ok[i]=true then
				pb_modules:=true; --nende puhul EAP võlg vaadetakse pisut teistmoodi
			end if;
			p_curr_modules2[i]:=r.m_id;
			-- mooduli kohustuslikud EAP
			p_curr_modules_credits[i]:=coalesce(r.compulsory_study_credits,0);
			-- mooduli valik EAP
			p_curr_modules_opt_credits[i]:=coalesce(r.optional_study_credits,0);
			if r.type_code in ('KORGMOODUL_V') then
				p_optional:=coalesce(r.optional_study_credits,0)+p_optional;
				p_vcurr_modules[i]:=r.m_id;
			else
				p_vcurr_modules[i]:=0;
			end if;
			if r.type_code in ('KORGMOODUL_F','KORGMOODUL_L') then
				p_fcurr_modules[i]:=r.m_id;
				p_fcurr_modules_ok[i]:=0;
			ELSE
				p_fcurr_modules[i]:=0;
			end if;
		end loop;
		--raise notice '%', array_length(p_curr_modules,1);
		--Õppuri positiived tulemused
		for r in (/*select coalesce(svm.curriculum_version_hmodule_id,sv.curriculum_version_hmodule_id) as curriculum_version_hmodule_id,
										 coalesce(svm.is_optional,sv.is_optional) is_optional,
										sv.grade_code, sv.credits,sv.subject_id, sv.grade_mark, sv.is_module, sv.grade_date
							from student_higher_result sv
									 left join student_higher_result_module svm on sv.id=svm.student_higher_result_id
							where sv.student_id=p_id and sv.is_module=false and sv.is_active=true and sv.grade_code in ('KORGHINDAMINE_1','KORGHINDAMINE_2','KORGHINDAMINE_3','KORGHINDAMINE_4','KORGHINDAMINE_5','KORGHINDAMINE_A') 
              */
							select coalesce(svm.curriculum_version_hmodule_id,sv.curriculum_version_hmodule_id) as curriculum_version_hmodule_id,
										 coalesce(svm.is_optional,sv.is_optional) is_optional,
										sv.grade_code, sv.credits,sv.subject_id, sv.grade_mark, sv.is_module, sv.grade_date, 0 as asend_kokku, 0 as ylek_kokku, 0 as vaba_kokku, 0 as vota
							from student_higher_result sv
									 left join student_higher_result_module svm on sv.id=svm.student_higher_result_id
									 left join apel_application_record ar on sv.apel_application_record_id=ar.id
									 left join apel_application ap on ar.apel_application_id=ap.id
							where sv.student_id=p_id  and sv.is_module=false and sv.is_active=true 
										and sv.grade_code in ('KORGHINDAMINE_1','KORGHINDAMINE_2','KORGHINDAMINE_3','KORGHINDAMINE_4','KORGHINDAMINE_5','KORGHINDAMINE_A') 
										and (ar.id is null or coalesce(ar.is_formal_learning,false)=false or coalesce(ap.is_new,false)=false)
							union all
							select x.chm_id,x.is_optional,sv.grade_code, sv.credits,coalesce(sv.subject_id,-sv.id), sv.grade_mark, sv.is_module, sv.grade_date,
							(select sum(sb.credits)
							 from student_higher_result_replaced_subject rs join subject sb on rs.subject_id=sb.id 
							 where rs.student_higher_result_id=x.id and rs.curriculum_version_hmodule_id=x.chm_id and rs.is_optional=x.is_optional) as asend_kokku,
							(select sum(sr.credits)
							 from student_higher_result sr
							where sr.is_Active=true and sr.apel_application_record_id=x.apel_application_record_id) as ylek_kokku, 
						(select sum(sb.credits)
						 from student_higher_result_replaced_subject rs join subject sb on rs.subject_id=sb.id 
						 where rs.student_higher_result_id=x.id and rs.curriculum_version_hmodule_id is not null) as vaba_kokku, 1 as vota
							from (
							select distinct sv.id,sv.apel_application_record_id,chm.id as chm_id,rs.is_optional--sv.credits,sb.id,sb.credits as sb_credits
							from student_higher_result sv
									 join apel_application_record ar on sv.apel_application_record_id=ar.id and coalesce(ar.is_formal_learning,false)=true
									 join apel_application ap on ar.apel_application_id=ap.id and coalesce(ap.is_new,false)=true
									 left join student_higher_result_replaced_subject rs on sv.id=rs.student_higher_result_id
									 --left join subject sb on rs.subject_id=sb.id
									 left join curriculum_version_hmodule chm on rs.curriculum_version_hmodule_id=chm.id
							where sv.student_id=p_id  and sv.is_module=false and sv.is_active=true 
										and sv.grade_code in ('KORGHINDAMINE_1','KORGHINDAMINE_2','KORGHINDAMINE_3','KORGHINDAMINE_4','KORGHINDAMINE_5','KORGHINDAMINE_A')) x
								join student_higher_result sv on x.id=sv.id
							union all
							select sv.curriculum_version_hmodule_id as curriculum_version_hmodule_id,
										 false is_optional,
										sv.grade_code, sv.credits,sv.subject_id, sv.grade_mark, sv.is_module, sv.grade_date, 0 as asend_kokku, 0 as ylek_kokku, 0 as vaba_kokku,  0 as vota
							from student_higher_result sv
							where sv.student_id=p_id and sv.is_module=true and sv.is_active=true and sv.grade_code in ('KORGHINDAMINE_1','KORGHINDAMINE_2','KORGHINDAMINE_3','KORGHINDAMINE_4','KORGHINDAMINE_5','KORGHINDAMINE_A') 
							order by is_module desc, grade_date desc nulls last) 
		LOOP
			--korjame kõige pealt positiivsed moodulid kokku 
			--märgime positiivset tulemust
			if pb_is_hgrade and r.is_module and array_length(p_curr_modules,1) > 0 THEN
						for i in 1..array_length(p_curr_modules,1)
						loop
							if p_curr_modules[i]=r.curriculum_version_hmodule_id then
								p_modules_count:=p_modules_count-1;
								p_is_positive_grade_modules[i]:=true;
								p_total_credits:=p_total_credits+r.credits;
								p_x_credits:=p_x_credits+r.credits;
								if r.grade_code in ('KORGHINDAMINE_1','KORGHINDAMINE_2','KORGHINDAMINE_3','KORGHINDAMINE_4','KORGHINDAMINE_5') THEN
									p_avg_total_credits:=p_avg_total_credits+r.credits;
									p_avg_credits:=p_avg_credits+r.credits*(r.grade_mark::int);
								end if;
							end if;
						end loop;
			end if;
			pb_exist:=false;
			pb_exist2:=false;
			if r.is_module=false then
				if r.subject_id is not null or r.vota=1 then
					if array_length(p_study_modules,1) > 0 then
						for ii in 1..array_length(p_study_modules,1)
						LOOP
							if p_study_modules[ii]=r.subject_id THEN
								pb_exist:=true;
								pb_exist2:=true;
								exit;
							end if;
						end loop;
					end if;
					if not pb_exist THEN
						p_study_modules[case when p_study_modules is null then 0 else array_length(p_study_modules,1) end+1]:= r.subject_id;
					end if;
				ELSE
					pb_exist:=false;
				end if;
				if r.vota=1 then
					pb_exist:=false;
				end if;
			end if;
			if not pb_exist THEN
				--ja siin on nüüd uus asi - kui aine kuulub minu positiivselt hinnatud moodulisse, siis me ei arvesta ainet EAP ja KKH jne arvutamisel
				if coalesce(r.curriculum_version_hmodule_id,0) > 0 and array_length(p_curr_modules,1) > 0 THEN
						for i in 1..array_length(p_curr_modules,1)
						loop
							if p_curr_modules[i]=r.curriculum_version_hmodule_id and p_is_positive_grade_modules[i]=true then 
								--jätame vahele
								p_curr_modules_opt_credits[i]:=0;
								p_curr_modules_credits[i]:=0;
								pb_exist:=true;
								exit;
							end if;
						end loop;
				end if;

				if not pb_exist THEN --aine ei kuulu positiivselt hinnatud moodulisse
					
					if not pb_exist2 then
						p_total_credits:=p_total_credits+r.credits;
						if r.vota=1 and r.ylek_kokku > 0 and r.ylek_kokku > r.vaba_kokku then
							p_opt_credits:=p_opt_credits+(r.credits*(r.ylek_kokku-r.vaba_kokku))/r.ylek_kokku;
						end if;
						--p_x_credits:=p_x_credits+r.credits;
						--raise notice '1 % % ',p_total_credits,p_x_credits;
					elsif r.vota=1 and r.asend_kokku > 0 and r.ylek_kokku > 0  then
						--p_x_credits:=p_x_credits+((r.asend_kokku*r.credits)/r.ylek_kokku);
						--raise notice '2 % % ',p_total_credits,p_x_credits;
					end if;
					
					if r.grade_code in ('KORGHINDAMINE_1','KORGHINDAMINE_2','KORGHINDAMINE_3','KORGHINDAMINE_4','KORGHINDAMINE_5') and not pb_exist2 THEN
						p_avg_total_credits:=p_avg_total_credits+r.credits;
						p_avg_credits:=p_avg_credits+r.credits*(r.grade_mark::int);
					end if;
					if array_length(p_curr_modules,1) > 0 and r.curriculum_version_hmodule_id is not null then
						pb_exist:=false;
						for ii in 1..array_length(p_curr_modules,1)
						loop
							if p_curr_modules[ii]=coalesce(r.curriculum_version_hmodule_id,0) then
								if coalesce(r.is_optional,false)=true then
									if r.vota=1 and r.asend_kokku > 0 and r.ylek_kokku > 0 then
										p_curr_modules_opt_credits[ii]:=p_curr_modules_opt_credits[ii]-((r.asend_kokku*r.credits)/r.ylek_kokku);
										--raise notice '1: %, % %',r.credits,p_curr_modules_opt_credits[ii],((r.asend_kokku*r.credits)/r.ylek_kokku);
									else
										p_curr_modules_opt_credits[ii]:=p_curr_modules_opt_credits[ii]-r.credits;
									end if;
									if p_curr_modules[ii]=p_vcurr_modules[ii] then
										--raise notice 'SIIN: ';
										if r.vota=1  then
											--p_opt_credits:=p_opt_credits+((r.asend_kokku*r.credits)/r.ylek_kokku);
											null;
										else
										  p_opt_credits:=p_opt_credits+r.credits;
										end if;
									end if;
									--raise notice '%', p_curr_modules_opt_credits[ii];
								else
									if r.vota=1 and r.asend_kokku > 0 and r.ylek_kokku > 0 then
										p_curr_modules_credits[ii]:=p_curr_modules_credits[ii]-((r.asend_kokku*r.credits)/r.ylek_kokku);
									else
										p_curr_modules_credits[ii]:=p_curr_modules_credits[ii]-r.credits;
									end if;
									--raise notice '-%', p_curr_modules_credits[ii];
								end if;
								pb_exist:=true;
							end if;
						end loop;
						--valikõpingud
						if not pb_exist then
							if r.vota=1  then
								--p_opt_credits:=p_opt_credits+((r.asend_kokku*r.credits)/r.ylek_kokku);
								null;
							else
								p_opt_credits:=p_opt_credits+r.credits;
							end if;
						end if;
					else
						if not pb_exist then
							if r.vota=1 then
								--p_opt_credits:=p_opt_credits+((r.asend_kokku*r.credits)/r.ylek_kokku);
								null;
							else
								p_opt_credits:=p_opt_credits+r.credits;
							end if;
						end if;
					end if; 
				end if;
			end if;
		end loop;

		if array_length(p_curr_modules,1) > 0 THEN
			for ii in 1..array_length(p_curr_modules,1)
			LOOP
				--vabaõppe ei pea siin kontrollima vist
				p_final_ok:=false;
				if p_vcurr_modules[ii] = 0 then
					if p_curr_modules_credits[ii] > 0 then
						-- siin kontrollime, kas moodul on jõuga täidetud; kui jah, siis nn võlga ei tohi tekkida
						if p_curr_modules_ok[ii]=false then
							--raise notice '% ',p_curr_modules_credits[ii];
							if p_curr_modules[ii]!=p_fcurr_modules[ii] then
								p_abs_credits:=p_abs_credits+p_curr_modules_credits[ii];
							end if;
						end if;
						-- lõputöö/eksam ei lähe arvesse, nende jaoks võla arvutamine jääb endiseks
						if p_curr_modules[ii]=p_fcurr_modules[ii] then
							if p_curr_modules_credits[ii]!=0 then --kui ei ole korras
								for iii in 1..array_length(p_fcurr_modules,1)
								loop
									if p_fcurr_modules[iii] > 0 and p_curr_modules_credits[iii]=0 then --midagi tehtud, ei liida
										p_final_ok:=true;
									end if;
								end loop;
								if not p_final_ok and p_fabs_credits=0 then
									p_fabs_credits:=p_fabs_credits+p_curr_modules_credits[ii];
								end if;
							end if;
						end if;
					else
						p_opt_credits:=p_opt_credits+abs(p_curr_modules_credits[ii]);
					end if;
					if p_curr_modules_opt_credits[ii] > 0 then
						--raise notice 'o %', p_curr_modules[ii];
						-- siin kontrollime, kas moodul on jõuga täidetud; kui jah, siis nn võlga ei tohi tekkida
						if p_curr_modules_ok[ii]=false then
							--raise notice 'x% ',p_curr_modules_opt_credits[ii];
							p_abs_credits:=p_abs_credits+p_curr_modules_opt_credits[ii];
						end if;
					else
						--raise notice 'xx% ',p_curr_modules_opt_credits[ii];
						p_opt_credits:=p_opt_credits+abs(p_curr_modules_opt_credits[ii]);
					end if;
					--raise notice '3. %: % % ', p_curr_modules[ii],p_abs_credits,p_opt_credits;
				end if;
			end loop;
		end if;

		--raise notice 'p_abs: %, p_fabs: %',p_abs_credits,p_fabs_credits;

		p_abs_credits:=p_abs_credits+p_fabs_credits;

	--	raise NOTICE 'Fopt: %/%', p_fabs_credits, p_abs_credits;
	--	raise NOTICE 'opt: %/%', p_opt_credits, p_optional;

		

		if p_opt_credits > p_optional THEN
			p_opt_credits:=0;
		ELSE
			p_opt_credits:=p_optional-p_opt_credits;
		end if;

		--raise NOTICE 'opt: %/%', p_opt_credits, p_optional;

		--kui kõik modulid on jõuga märgitud täidetuks, siis ikkagi kogutud EAP peab olema vähemalt sama suur kui õppekava EAP
		--raise notice '% - % - %', p_curriculum_credits, p_total_credits, pb_modules;
		if p_curriculum_credits > p_total_credits and pb_modules then
			p_abs_credits:=p_curriculum_credits-p_total_credits;
			--raise notice 'var 1';
		else
			p_abs_credits:=coalesce(p_abs_credits,0)+p_opt_credits;
			--raise notice 'var 2';
		end if;
		p_fabs_credits:=p_abs_credits-p_fabs_credits;
		if p_modules_count > 0 then
			pb_modules_ok:=false;
		end if;
		
	else
		--Õppuri positiivsed tulemused
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
					--kahte tüüpi moodulid
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
					--valikõpingud
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

		--kokku võlg
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
	set study_backlog=-p_abs_credits, 
			study_backlog_without_graduate=-p_fabs_credits,
			average_mark=case when p_avg_total_credits > 0 then floor(p_avg_credits*1000/p_avg_total_credits)/1000 else 0 end, 
			credits=p_total_credits, 
			changed=current_timestamp(3),
			study_optional_backlog=-p_opt_credits,
			is_modules_ok=pb_modules_ok
	where student_id=p_id;
	
	GET DIAGNOSTICS a_count = ROW_COUNT;
	if a_count=0 THEN
			insert into student_curriculum_completion(student_id,study_backlog,study_backlog_without_graduate,average_mark,credits,inserted,changed,study_optional_backlog,is_modules_ok)
			values(p_id,-p_abs_credits,-p_fabs_credits,case when p_avg_total_credits > 0 then floor(p_avg_credits*100/p_avg_total_credits)/100 else 0 end,p_total_credits,current_timestamp(3),current_timestamp(3),-p_opt_credits,pb_modules_ok);
	end if;

	return 0;
exception when others THEN
	raise notice '%, %',p_id,sqlerrm;
	return -1;
end;
$function$
;


/* Create Tables */

CREATE TABLE "subject_program_teacher"
(
	"id" bigserial NOT NULL ,
	"subject_program_id" bigint NOT NULL,
	"inserted" timestamp without time zone NOT NULL,
	"subject_study_period_teacher_id" bigint NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL,
	"is_ready" boolean NULL,    -- kas valmis
	"is_ready_dt" timestamp without time zone NULL    -- millal märgiti valmis
)
;

CREATE TABLE "subject_program_study_content_teacher"
(
	"id" bigserial NOT NULL,
	"subject_program_study_content_id" bigint NOT NULL,
	"inserted" timestamp without time zone NOT NULL,
	"subject_program_teacher_id" bigint NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "subject_program_teacher"	IS 'aineprogrammiga seotud õppejõud';
COMMENT ON COLUMN "subject_program_teacher"."is_ready"	IS 'kas valmis';
COMMENT ON COLUMN "subject_program_teacher"."is_ready_dt"	IS 'millal märgiti valmis';

COMMENT ON TABLE "subject_program_study_content_teacher"	IS 'sisuga seotud õppejõud';

/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE "subject_program_teacher" ADD CONSTRAINT "PK_subject_program_teacher"	PRIMARY KEY ("id");

CREATE INDEX "IXFK_subject_program_teacher_subject_program" ON "subject_program_teacher" ("subject_program_id" ASC);
CREATE INDEX "IXFK_subject_program_teacher_subject_study_period_teacher" ON "subject_program_teacher" ("subject_study_period_teacher_id" ASC);
ALTER TABLE "subject_program_study_content_teacher" ADD CONSTRAINT "PK_subject_program_study_content_teacher"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_subject_program_study_content_teacher_subject_program_study_content" ON "subject_program_study_content_teacher" ("subject_program_study_content_id" ASC);
CREATE INDEX "IXFK_subject_program_study_content_teacher_subject_program_teacher" ON "subject_program_study_content_teacher" ("subject_program_teacher_id" ASC);

/* Create Foreign Key Constraints */

ALTER TABLE "subject_program_teacher" ADD CONSTRAINT "FK_subject_program_teacher_subject_program"	FOREIGN KEY ("subject_program_id") REFERENCES "public"."subject_program" ("id") ON DELETE No Action ON UPDATE No Action;

ALTER TABLE "subject_program_teacher" ADD CONSTRAINT "FK_subject_program_teacher_subject_study_period_teacher"	FOREIGN KEY ("subject_study_period_teacher_id") REFERENCES "public"."subject_study_period_teacher" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "subject_program_study_content_teacher" ADD CONSTRAINT "FK_subject_program_study_content_teacher_subject_program_study_content"	FOREIGN KEY ("subject_program_study_content_id") REFERENCES "public"."subject_program_study_content" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "subject_program_study_content_teacher" ADD CONSTRAINT "FK_subject_program_study_content_teacher_subject_program_teacher"	FOREIGN KEY ("subject_program_teacher_id") REFERENCES "subject_program_teacher" ("id") ON DELETE No Action ON UPDATE No Action;

alter table subject_program add column is_joint boolean;
comment on column subject_program.is_joint is 'kas on ühine aineprogramm';

insert into subject_program_teacher(subject_program_id,subject_study_period_teacher_id,inserted,changed,version,inserted_by,changed_by)
select id,subject_study_period_teacher_id,inserted,changed,version,inserted_by,changed_by from subject_program;

alter table subject_program drop column subject_study_period_teacher_id;

do $$
declare 
	r record;
	rr record;
begin
	for r in (select distinct ds.id,ds.start_date,ds.application_id ,ds.directive_student_id, ds.directive_id,ds.student_id, d.confirm_date 
				, ds3.id as akad_id, ds.application_id 
			from directive d
				 join directive_student ds on d.id=ds.directive_id
				 left join application a2 on ds.application_id =a2.id 
				 left join directive_student ds3 on a2.directive_id = ds3.directive_id and ds3.student_id =ds.student_id 
				-- join directive_student ds2 on ds.student_id =ds2.student_id 
				-- join directive d2 on ds2.directive_id =d2.id and d2.type_code = 'KASKKIRI_AKAD'
				-- left join study_period sp on ds2.study_period_start_id =sp.id 
				-- left join study_period spe on ds2.study_period_end_id =spe.id
			where d.type_code = 'KASKKIRI_AKADK' -- and ds.start_date <= coalesce (spe.end_date,ds2.end_date) and ds.start_date >= coalesce(sp.start_date,ds2.start_date) and d.confirm_date >=d2.confirm_date 
			order by ds.student_id )
	loop 
		if r.akad_id is not null then 
			update directive_student ds
				set directive_student_id=r.akad_id
			where ds.id=r.id;
		else
			for rr in (select ds2.id
					   from directive_student ds2  
							 join directive d2 on ds2.directive_id =d2.id and d2.type_code = 'KASKKIRI_AKAD'
							 left join study_period sp on ds2.study_period_start_id =sp.id 
							 left join study_period spe on ds2.study_period_end_id =spe.id
					 	where  r.student_id =ds2.student_id and r.start_date <= coalesce (spe.end_date,ds2.end_date) and r.start_date >= coalesce(sp.start_date,ds2.start_date) and r.confirm_date >=d2.confirm_date
					 	order by d2.confirm_date desc)
			loop 
				update directive_student ds
					set directive_student_id=rr.id
				where ds.id=r.id;
				exit;
			end loop;
		end if;
	end loop;
end;
$$;

alter table subject_program
	add column 	"accessed" timestamp without time zone NULL,    -- aine programmi viimane avamine muutmiseks
	add column "accessed_by" varchar(100)	 NULL,    -- aine programmi viimane avaja
	add column 	"accessed_user_id" bigint NULL,    -- aine programmine viimase avaja user_id
	add column "subject_study_period_id" bigint null;
	
COMMENT ON COLUMN "subject_program"."accessed" IS 'aine programmi viimane avamine muutmiseks';
COMMENT ON COLUMN "subject_program"."accessed_by"	IS 'aine programmi viimane avaja';
COMMENT ON COLUMN "subject_program"."accessed_user_id"	IS 'aine programmine viimase avaja user_id';

CREATE INDEX "IXFK_subject_program_subject_study_period" ON "public"."subject_program" ("subject_study_period_id" ASC);
ALTER TABLE "public"."subject_program" ADD CONSTRAINT "FK_subject_program_subject_study_period" 	FOREIGN KEY ("subject_study_period_id") REFERENCES "public"."subject_study_period" ("id") ON DELETE No Action ON UPDATE No Action;


with s as (select distinct sp.id, sspt.subject_study_period_id 
from subject_program sp 
	 join subject_program_teacher st on sp.id=st.subject_program_id 
	 join subject_study_period_teacher sspt on st.subject_study_period_teacher_id =sspt.id)
update  subject_program sp
	set subject_study_period_id =s.subject_study_period_id
from s
where sp.id=s.id;
	 
alter table subject_program alter column subject_study_period_id  SET NOT null;


CREATE TABLE "midterm_task_student_result_history"
(
	"id" bigserial NOT NULL,
	"midterm_task_student_result_id" bigint NOT NULL,
	"points" numeric(5,2) NULL,
	"points_txt" varchar(10)	 NULL,
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "midterm_task_student_result_history"	IS 'vahetulemuste ajalugu';
ALTER TABLE "midterm_task_student_result_history" ADD CONSTRAINT "PK_Table2"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_midterm_task_student_result_history_midterm_task_student_result" ON "midterm_task_student_result_history" ("midterm_task_student_result_id" ASC);
ALTER TABLE "midterm_task_student_result_history" ADD CONSTRAINT "FK_midterm_task_student_result_history_midterm_task_student_result"	FOREIGN KEY ("midterm_task_student_result_id") REFERENCES "public"."midterm_task_student_result" ("id") ON DELETE No Action ON UPDATE No Action;

CREATE OR REPLACE FUNCTION public.is_leave_period(p_student bigint, p_period_start date, p_period_end date, p_directive_type character varying, p_cancel_directive_type character varying)
 RETURNS boolean
 LANGUAGE plpgsql
AS $function$
	declare
		sp_days integer;
		leave_days integer;
		leave_period record;
	begin
		sp_days := p_period_end - p_period_start + 1;
		leave_days := 0;	

		for leave_period in select ds.student_id, coalesce(sps.start_date, ds.start_date) start_date, coalesce(ds_katk.start_date, spe.end_date, ds.end_date) end_date
			from directive_student ds 
			join directive d on ds.directive_id = d.id and ds.canceled = false 
			join student s on ds.student_id = s.id 
			left join study_period sps on ds.study_period_start_id = sps.id 
			left join study_period spe on ds.study_period_end_id = spe.id 
			left join (directive_student ds_katk join directive d_katk on d_katk.id = ds_katk.directive_id 
				and d_katk.type_code = p_cancel_directive_type and d_katk.status_code = 'KASKKIRI_STAATUS_KINNITATUD') 
				on ds_katk.directive_student_id = ds.id and ds_katk.canceled = false
			where s.id = p_student and d.type_code = p_directive_type and d.status_code = 'KASKKIRI_STAATUS_KINNITATUD'
		loop
			if p_period_start <= leave_period.end_date and p_period_end >= leave_period.start_date then
				leave_days := leave_days + (least(p_period_end, leave_period.end_date) - greatest(p_period_start, leave_period.start_date) + 1);
			end if;
		end loop;
	
		return (leave_days::float / sp_days::float) >= 0.5;
	end;
$function$
;

CREATE OR REPLACE FUNCTION public.get_cumulative_credits(p_student bigint, p_periods_participated_in_study integer, p_collected_credits numeric DEFAULT NULL::numeric)
 RETURNS numeric
 LANGUAGE plpgsql
AS $function$
declare
	expected_credits numeric := 0;
	r record;
begin
	for period_counter IN 1 .. p_periods_participated_in_study loop
		select cvnc.credits into r from student s
			join curriculum_version cv on cv.id = s.curriculum_version_id
			join curriculum_version_nominal_capacity cvnc on cvnc.curriculum_version_id = cv.id
			join curriculum_version_speciality cvs on cvs.id = cvnc.curriculum_version_speciality_id
			where s.id = p_student and cvnc.period_nr = period_counter and cvs.curriculum_speciality_id = coalesce(s.curriculum_speciality_id,
				(select case when (select count(cvs2.id) from curriculum_version cv2
					join curriculum_version_speciality cvs2 on cvs2.curriculum_version_id = cv2.id
					where cv2.id = s.curriculum_version_id) > 1
				then null
				else (select cvs2.curriculum_speciality_id from curriculum_version_speciality cvs2 where cvs2.curriculum_version_id = s.curriculum_version_id) end));

		if r is not null then 
			expected_credits := expected_credits + r.credits;
		else
			expected_credits := expected_credits + 30;
		end if;
	end loop;

	if expected_credits = 0 then
		return 100;
	end if;

	if p_collected_credits is null then
		select coalesce(c.credits, 0) + scc.study_backlog into p_collected_credits from student s
			join student_curriculum_completion scc on scc.student_id = s.id
			left join (curriculum_version cv join curriculum c on c.id = cv.curriculum_id) on cv.id = s.curriculum_version_id
			where s.id = p_student;
	end if;
	
	return p_collected_credits / expected_credits * 100;
end;
$function$
;

CREATE OR REPLACE FUNCTION public.get_leave_period_count(p_student bigint, p_directive_type character varying, p_cancel_directive_type character varying)
 RETURNS integer
 LANGUAGE plpgsql
AS $function$
	declare
		leave_periods integer := 0;
		study_period record;
	begin
		for study_period in select sp.id, sp.start_date, sp.end_date from student s
			join study_year sy on sy.school_id = s.school_id
			join study_period sp on sp.study_year_id = sy.id
			where s.id = p_student and sp.end_date > s.study_start and sp.start_date <= now()
		loop		
			if is_leave_period(p_student, study_period.start_date, study_period.end_date, p_directive_type, p_cancel_directive_type) then
				leave_periods := leave_periods + 1;
			end if;
		end loop;
	
		return leave_periods;
	end;
$function$
;

CREATE OR REPLACE FUNCTION public.get_student_last_period_study_period_count(p_student bigint, p_last_period bigint DEFAULT NULL::bigint)
 RETURNS integer
 LANGUAGE plpgsql
AS $function$
	declare
		last_period_end date;
		student_nominal_study_end date;
		leave_period record;	
		academic_days integer := 0;
		sp_count integer := 0;
	begin
		if p_last_period is null then
			p_last_period := get_student_last_study_period(p_student);
		end if;

		if p_last_period is not null then
			select sp.end_date from study_period sp into last_period_end where sp.id = p_last_period;
		
			select coalesce(s.nominal_study_end, s.study_start + interval '1 month' * c.study_period - interval '1 day') into student_nominal_study_end from student s 
				join curriculum_version cv on cv.id = s.curriculum_version_id 
				join curriculum c on c.id = cv.curriculum_id 
				where s.id = p_student;
		
			for leave_period in select ds.student_id, coalesce(sps.start_date, ds.start_date) start_date, coalesce(ds_katk.start_date, spe.end_date, ds.end_date) end_date
				from directive_student ds 
				join directive d on ds.directive_id = d.id and ds.canceled = false 
				join student s on ds.student_id = s.id 
				left join study_period sps on ds.study_period_start_id = sps.id 
				left join study_period spe on ds.study_period_end_id = spe.id 
				left join (directive_student ds_katk join directive d_katk on d_katk.id = ds_katk.directive_id 
					and d_katk.type_code = 'KASKKIRI_AKADK' and d_katk.status_code = 'KASKKIRI_STAATUS_KINNITATUD')
					on ds_katk.directive_student_id = ds.id and ds_katk.canceled = false
				where s.id = p_student and d.type_code = 'KASKKIRI_AKAD' and d.status_code = 'KASKKIRI_STAATUS_KINNITATUD'
			loop
				if last_period_end <= leave_period.end_date and student_nominal_study_end >= leave_period.start_date then
					academic_days := academic_days + (least(student_nominal_study_end, leave_period.end_date) - greatest(last_period_end, leave_period.start_date) + 1);
				end if;
			end loop;
		
		
			select case when jaak_sem > nom_sem then 0 else nom_sem - jaak_sem end into sp_count from 
				(select round(c.study_period / 6.0) nom_sem, coalesce(round((student_nominal_study_end::date - last_period_end::date - academic_days) / 30.5 / 6), 0) jaak_sem
					from student s 
					join curriculum_version cv on cv.id = s.curriculum_version_id 
					join curriculum c on c.id = cv.curriculum_id 
					where s.id = p_student) x;
		end if;

		if sp_count is null then
			sp_count := 0;
		end if;
		return sp_count;
	
		exception
			when others then
				return 0;
	end;
$function$
;


CREATE OR REPLACE FUNCTION public.get_student_last_study_period(p_student bigint)
 RETURNS bigint
 LANGUAGE plpgsql
AS $function$
	declare
		study_period record;
	begin
		for study_period in select sp.id, sp.start_date, sp.end_date from student s
			join study_year sy on sy.school_id = s.school_id
			join study_period sp on sp.study_year_id = sy.id
			where s.id = p_student and sp.end_date > s.study_start and sp.end_date <= now()
			order by sp.end_date desc
		loop		
			if is_leave_period(p_student, study_period.start_date, study_period.end_date, 'KASKKIRI_AKAD', 'KASKKIRI_AKADK') = false then
				return study_period.id;
			end if;
		end loop;
	
		return null;
	end;
$function$
;

CREATE OR REPLACE FUNCTION public.get_student_participated_study_period_count(p_student bigint)
 RETURNS integer
 LANGUAGE plpgsql
AS $function$
	declare
		participated_periods integer := 0;
		sp_days integer;
		study_days integer;
		r1 record;
		r2 record;
	begin
		select s.study_start, now()::date study_end from student s into r1 where s.id = p_student;
		
		for r2 in select sp.id, sp.start_date, sp.end_date from student s
			join study_year sy on sy.school_id = s.school_id
			join study_period sp on sp.study_year_id = sy.id
			where s.id = p_student and sp.end_date > s.study_start and sp.start_date <= now()
		loop
			sp_days := r2.end_date - r2.start_date + 1;
			study_days := 0;
	
			if r1.study_start <= r2.end_date and r1.study_end >= r2.start_date then
				study_days := least(r1.study_end, r2.end_date) - greatest(r1.study_start, r2.start_date) + 1;
			end if;

			if (study_days::float / sp_days::float) >= 0.5 then
				participated_periods := participated_periods + 1;
			end if;
		end loop;
	
		return participated_periods;
	end;
$function$
;



CREATE OR REPLACE PROCEDURE get_higher_student_cc(in p_id bigint, in is_last_period boolean, inout p_abs_credits numeric, inout p_fabs_credits numeric,
inout p_avg_total_credits numeric, inout p_total_credits numeric, inout p_opt_credits numeric, inout pb_modules_ok boolean)
 LANGUAGE plpgsql
AS $$
declare 
  pb_exist boolean:=false;
	pb_exist2 boolean:=false;
	p_curr_modules bigint array;
  p_is_grade_modules boolean array;
	p_is_positive_grade_modules boolean array;
	p_curr_modules_ok boolean array;
	p_curr_module_types bigint array;
  p_curr_modules_credits numeric array;
	p_curr_modules_opt_credits numeric array;
	p_curr_modules2 bigint array;
  p_study_modules bigint array;
  p_vstudy_modules bigint array;
  p_optional numeric:=0;
	pb_modules boolean:=false; --kas on olemas jõuga moodulite täitmist
	r record;
	i int:=0;
  ii int:=0;
  iii int:=0;
  p_total int:=0;
	--p_opt_credits numeric:=0;
	p_avg_credits numeric:=0;
	p_x_credits numeric:=0;
	--p_avg_total_credits numeric:=0;
	--p_total_credits numeric:=0;
	--p_abs_credits numeric:=0;
	--p_fabs_credits numeric:=0;
	p_curriculum_credits numeric:=0;
	a_count int:=0;
	pb_is_hgrade boolean:=false;

  p_vcurr_modules bigint array;
	p_vcurr_modules2 bigint array;

	p_fcurr_modules bigint array;
	p_fcurr_modules_ok bigint array;
	
	mod_id bigint;

	is_higher_curriculum boolean:=true;
	
	--pb_modules_ok boolean:=true;
	p_modules_count integer:=0; --mitu moodulit on ette nähtud hindamiseks
	p_id2 bigint;
	p_final_ok boolean:=false;

	p_last_date date:='2100-12-31';
	p_last_id bigint:=0;
BEGIN

		if is_last_period then
			p_last_id:=get_student_last_study_period(p_id);
			if coalesce(p_last_id,0) > 0 then
				select end_date into p_last_date from study_period where id=p_last_id;
			end if;
		end if;
	
		i:=0;
		--moodulite hindamise puhul mitte õppekava moodulid ei tohi arvesse minna
		for r in (select sr.id, cm.id as id2
								from student_higher_result sr
										 join student s on sr.student_id=s.id
										 join curriculum_version cv on s.curriculum_version_id=cv.id
										 left join curriculum_version_hmodule cm on cv.id=cm.curriculum_version_id and (coalesce(s.curriculum_speciality_id,0)=0 or 
																							coalesce(s.curriculum_speciality_id,0) > 0 and exists(
																									select 1 
																									from curriculum_version_hmodule_speciality hs
																											 join curriculum_version_speciality cvs on hs.curriculum_version_speciality_id=cvs.id
																									where hs.curriculum_version_hmodule_id=cm.id and
																												cvs.curriculum_speciality_id=s.curriculum_speciality_id
																												)) and sr.curriculum_version_hmodule_id=cm.id
								where sr.student_id=p_id and sr.is_module=true )
		loop
			if r.id2 is null then
				update student_higher_result sr set is_active=false where sr.id=r.id;
			else
				select distinct first_value(sr.id)over(partition by sr.curriculum_version_hmodule_id order by case when coalesce(sr.apel_application_record_id,0)=0 then 1 else 0 end, sr.grade_date desc nulls last,ph.type_code asc,ph.inserted desc) into p_id2 
				from student_higher_result sr
						 left join protocol_student ps on sr.protocol_student_id=ps.id
						 left join protocol_hdata ph on ps.protocol_id=ph.protocol_id
				where sr.student_id=p_id and sr.curriculum_version_hmodule_id=r.id2 and sr.is_module=true;
				update student_higher_result set is_active=false where student_id=p_id and curriculum_version_hmodule_id=r.id2 and is_module=true and id!=p_id2;
				update student_higher_result set is_active=true where student_id=p_id and curriculum_version_hmodule_id=r.id2 and is_module=true and id=p_id2;
			end if;
		end loop;
		
		
		--Kõrgõppurid
		for r in (select distinct cm.id as m_id, cm.total_credits,cm.optional_study_credits,cm.compulsory_study_credits, cm.type_code, cc.is_higher, cc.optional_study_credits as total_optional_study_credits,
										 sch.curriculum_version_hmodule_id as ok_id, cc.credits as curriculum_credits, cm.is_grade, shh.is_hmodules
					  from curriculum_version cv
								 join curriculum_version_hmodule cm on cv.id=cm.curriculum_version_id
								 --join curriculum_module cm on cvo.curriculum_module_id=cm.id and cv.curriculum_id=cm.curriculum_id and coalesce(cm.is_additional,false)=false and cm.module_code!='KUTSEMOODUL_V' and coalesce(cm.is_additional,false)=false
								 join curriculum cc on cv.curriculum_id=cc.id
								 join student ss on cv.id=ss.curriculum_version_id
								 join school shh on ss.school_id=shh.id 
								--jõuga täidetud moodule
								 left join student_curriculum_completion_hmodule sch on ss.id=sch.student_id and cm.id=sch.curriculum_version_hmodule_id
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
			pb_is_hgrade:=coalesce(r.is_hmodules,false);
			p_is_grade_modules[i]:=coalesce(r.is_grade,false);
			if p_is_grade_modules[i]=true then
				p_modules_count:=p_modules_count+1;
			end if;
			p_curriculum_credits:=coalesce(r.curriculum_credits,0);
			p_is_positive_grade_modules[i]:=false;
			p_curr_modules_ok[i]:=case when r.ok_id is not null and r.m_id=r.ok_id then true else false end;
			if p_curr_modules_ok[i]=true then
				pb_modules:=true; --nende puhul EAP võlg vaadetakse pisut teistmoodi
			end if;
			p_curr_modules2[i]:=r.m_id;
			-- mooduli kohustuslikud EAP
			p_curr_modules_credits[i]:=coalesce(r.compulsory_study_credits,0);
			-- mooduli valik EAP
			p_curr_modules_opt_credits[i]:=coalesce(r.optional_study_credits,0);
			if r.type_code in ('KORGMOODUL_V') then
				p_optional:=coalesce(r.optional_study_credits,0)+p_optional;
				p_vcurr_modules[i]:=r.m_id;
			else
				p_vcurr_modules[i]:=0;
			end if;
			if r.type_code in ('KORGMOODUL_F','KORGMOODUL_L') then
				p_fcurr_modules[i]:=r.m_id;
				p_fcurr_modules_ok[i]:=0;
			ELSE
				p_fcurr_modules[i]:=0;
			end if;
		end loop;
		--raise notice '%', array_length(p_curr_modules,1);
		--Õppuri positiived tulemused
		for r in (/*select coalesce(svm.curriculum_version_hmodule_id,sv.curriculum_version_hmodule_id) as curriculum_version_hmodule_id,
										 coalesce(svm.is_optional,sv.is_optional) is_optional,
										sv.grade_code, sv.credits,sv.subject_id, sv.grade_mark, sv.is_module, sv.grade_date
							from student_higher_result sv
									 left join student_higher_result_module svm on sv.id=svm.student_higher_result_id
							where sv.student_id=p_id and sv.is_module=false and sv.is_active=true and sv.grade_code in ('KORGHINDAMINE_1','KORGHINDAMINE_2','KORGHINDAMINE_3','KORGHINDAMINE_4','KORGHINDAMINE_5','KORGHINDAMINE_A') 
              */
							select coalesce(svm.curriculum_version_hmodule_id,sv.curriculum_version_hmodule_id) as curriculum_version_hmodule_id,
										 coalesce(svm.is_optional,sv.is_optional) is_optional,
										sv.grade_code, sv.credits,sv.subject_id, sv.grade_mark, sv.is_module, sv.grade_date, 0 as asend_kokku, 0 as ylek_kokku, 0 as vaba_kokku, 0 as vota
							from student_higher_result sv
									 left join student_higher_result_module svm on sv.id=svm.student_higher_result_id
									 left join apel_application_record ar on sv.apel_application_record_id=ar.id
									 left join apel_application ap on ar.apel_application_id=ap.id
							where sv.student_id=p_id and coalesce(sv.grade_date,'2100-12-31') <=p_last_date   and sv.is_module=false and sv.is_active=true 
										and sv.grade_code in ('KORGHINDAMINE_1','KORGHINDAMINE_2','KORGHINDAMINE_3','KORGHINDAMINE_4','KORGHINDAMINE_5','KORGHINDAMINE_A') 
										and (ar.id is null or coalesce(ar.is_formal_learning,false)=false or coalesce(ap.is_new,false)=false)
							union all
							select x.chm_id,x.is_optional,sv.grade_code, sv.credits,coalesce(sv.subject_id,-sv.id), sv.grade_mark, sv.is_module, sv.grade_date,
							(select sum(sb.credits)
							 from student_higher_result_replaced_subject rs join subject sb on rs.subject_id=sb.id 
							 where rs.student_higher_result_id=x.id and rs.curriculum_version_hmodule_id=x.chm_id and rs.is_optional=x.is_optional) as asend_kokku,
							(select sum(sr.credits)
							 from student_higher_result sr
							where sr.is_Active=true and sr.apel_application_record_id=x.apel_application_record_id) as ylek_kokku, 
						(select sum(sb.credits)
						 from student_higher_result_replaced_subject rs join subject sb on rs.subject_id=sb.id 
						 where rs.student_higher_result_id=x.id and rs.curriculum_version_hmodule_id is not null) as vaba_kokku, 1 as vota
							from (
							select distinct sv.id,sv.apel_application_record_id,chm.id as chm_id,rs.is_optional--sv.credits,sb.id,sb.credits as sb_credits
							from student_higher_result sv
									 join apel_application_record ar on sv.apel_application_record_id=ar.id and coalesce(ar.is_formal_learning,false)=true
									 join apel_application ap on ar.apel_application_id=ap.id and coalesce(ap.is_new,false)=true
									 left join student_higher_result_replaced_subject rs on sv.id=rs.student_higher_result_id
									 --left join subject sb on rs.subject_id=sb.id
									 left join curriculum_version_hmodule chm on rs.curriculum_version_hmodule_id=chm.id
							where sv.student_id=p_id  and coalesce(sv.grade_date,'2100-12-31') <=p_last_date and sv.is_module=false and sv.is_active=true 
										and sv.grade_code in ('KORGHINDAMINE_1','KORGHINDAMINE_2','KORGHINDAMINE_3','KORGHINDAMINE_4','KORGHINDAMINE_5','KORGHINDAMINE_A')) x
								join student_higher_result sv on x.id=sv.id
							union all
							select sv.curriculum_version_hmodule_id as curriculum_version_hmodule_id,
										 false is_optional,
										sv.grade_code, sv.credits,sv.subject_id, sv.grade_mark, sv.is_module, sv.grade_date, 0 as asend_kokku, 0 as ylek_kokku, 0 as vaba_kokku,  0 as vota
							from student_higher_result sv
							where sv.student_id=p_id and sv.is_module=true and sv.is_active=true and sv.grade_code in ('KORGHINDAMINE_1','KORGHINDAMINE_2','KORGHINDAMINE_3','KORGHINDAMINE_4','KORGHINDAMINE_5','KORGHINDAMINE_A') 
							order by is_module desc, grade_date desc nulls last) 
		LOOP
			--korjame kõige pealt positiivsed moodulid kokku 
			--märgime positiivset tulemust
			if pb_is_hgrade and r.is_module and array_length(p_curr_modules,1) > 0 THEN
						for i in 1..array_length(p_curr_modules,1)
						loop
							if p_curr_modules[i]=r.curriculum_version_hmodule_id then
								p_modules_count:=p_modules_count-1;
								p_is_positive_grade_modules[i]:=true;
								p_total_credits:=p_total_credits+r.credits;
								p_x_credits:=p_x_credits+r.credits;
								if r.grade_code in ('KORGHINDAMINE_1','KORGHINDAMINE_2','KORGHINDAMINE_3','KORGHINDAMINE_4','KORGHINDAMINE_5') THEN
									p_avg_total_credits:=p_avg_total_credits+r.credits;
									p_avg_credits:=p_avg_credits+r.credits*(r.grade_mark::int);
								end if;
							end if;
						end loop;
			end if;
			pb_exist:=false;
			pb_exist2:=false;
			if r.is_module=false then
				if r.subject_id is not null or r.vota=1 then
					if array_length(p_study_modules,1) > 0 then
						for ii in 1..array_length(p_study_modules,1)
						LOOP
							if p_study_modules[ii]=r.subject_id THEN
								pb_exist:=true;
								pb_exist2:=true;
								exit;
							end if;
						end loop;
					end if;
					if not pb_exist THEN
						p_study_modules[case when p_study_modules is null then 0 else array_length(p_study_modules,1) end+1]:= r.subject_id;
					end if;
				ELSE
					pb_exist:=false;
				end if;
				if r.vota=1 then
					pb_exist:=false;
				end if;
			end if;
			if not pb_exist THEN
				--ja siin on nüüd uus asi - kui aine kuulub minu positiivselt hinnatud moodulisse, siis me ei arvesta ainet EAP ja KKH jne arvutamisel
				if coalesce(r.curriculum_version_hmodule_id,0) > 0 and array_length(p_curr_modules,1) > 0 THEN
						for i in 1..array_length(p_curr_modules,1)
						loop
							if p_curr_modules[i]=r.curriculum_version_hmodule_id and p_is_positive_grade_modules[i]=true then 
								--jätame vahele
								p_curr_modules_opt_credits[i]:=0;
								p_curr_modules_credits[i]:=0;
								pb_exist:=true;
								exit;
							end if;
						end loop;
				end if;

				if not pb_exist THEN --aine ei kuulu positiivselt hinnatud moodulisse
					
					if not pb_exist2 then
						p_total_credits:=p_total_credits+r.credits;
						if r.vota=1 and r.ylek_kokku > 0 and r.ylek_kokku > r.vaba_kokku then
							p_opt_credits:=p_opt_credits+(r.credits*(r.ylek_kokku-r.vaba_kokku))/r.ylek_kokku;
						end if;
						--p_x_credits:=p_x_credits+r.credits;
						--raise notice '1 % % ',p_total_credits,p_x_credits;
					elsif r.vota=1 and r.asend_kokku > 0 and r.ylek_kokku > 0  then
						--p_x_credits:=p_x_credits+((r.asend_kokku*r.credits)/r.ylek_kokku);
						--raise notice '2 % % ',p_total_credits,p_x_credits;
					end if;
					
					if r.grade_code in ('KORGHINDAMINE_1','KORGHINDAMINE_2','KORGHINDAMINE_3','KORGHINDAMINE_4','KORGHINDAMINE_5') and not pb_exist2 THEN
						p_avg_total_credits:=p_avg_total_credits+r.credits;
						p_avg_credits:=p_avg_credits+r.credits*(r.grade_mark::int);
					end if;
					if array_length(p_curr_modules,1) > 0 and r.curriculum_version_hmodule_id is not null then
						pb_exist:=false;
						for ii in 1..array_length(p_curr_modules,1)
						loop
							if p_curr_modules[ii]=coalesce(r.curriculum_version_hmodule_id,0) then
								if coalesce(r.is_optional,false)=true then
									if r.vota=1 and r.asend_kokku > 0 and r.ylek_kokku > 0 then
										p_curr_modules_opt_credits[ii]:=p_curr_modules_opt_credits[ii]-((r.asend_kokku*r.credits)/r.ylek_kokku);
										--raise notice '1: %, % %',r.credits,p_curr_modules_opt_credits[ii],((r.asend_kokku*r.credits)/r.ylek_kokku);
									else
										p_curr_modules_opt_credits[ii]:=p_curr_modules_opt_credits[ii]-r.credits;
									end if;
									if p_curr_modules[ii]=p_vcurr_modules[ii] then
										--raise notice 'SIIN: ';
										if r.vota=1  then
											--p_opt_credits:=p_opt_credits+((r.asend_kokku*r.credits)/r.ylek_kokku);
											null;
										else
										  p_opt_credits:=p_opt_credits+r.credits;
										end if;
									end if;
									--raise notice '%', p_curr_modules_opt_credits[ii];
								else
									if r.vota=1 and r.asend_kokku > 0 and r.ylek_kokku > 0 then
										p_curr_modules_credits[ii]:=p_curr_modules_credits[ii]-((r.asend_kokku*r.credits)/r.ylek_kokku);
									else
										p_curr_modules_credits[ii]:=p_curr_modules_credits[ii]-r.credits;
									end if;
									--raise notice '-%', p_curr_modules_credits[ii];
								end if;
								pb_exist:=true;
							end if;
						end loop;
						--valikõpingud
						if not pb_exist then
							if r.vota=1  then
								--p_opt_credits:=p_opt_credits+((r.asend_kokku*r.credits)/r.ylek_kokku);
								null;
							else
								p_opt_credits:=p_opt_credits+r.credits;
							end if;
						end if;
					else
						if not pb_exist then
							if r.vota=1 then
								--p_opt_credits:=p_opt_credits+((r.asend_kokku*r.credits)/r.ylek_kokku);
								null;
							else
								p_opt_credits:=p_opt_credits+r.credits;
							end if;
						end if;
					end if; 
				end if;
			end if;
		end loop;

		if array_length(p_curr_modules,1) > 0 THEN
			for ii in 1..array_length(p_curr_modules,1)
			LOOP
				--vabaõppe ei pea siin kontrollima vist
				p_final_ok:=false;
				if p_vcurr_modules[ii] = 0 then
					if p_curr_modules_credits[ii] > 0 then
						-- siin kontrollime, kas moodul on jõuga täidetud; kui jah, siis nn võlga ei tohi tekkida
						if p_curr_modules_ok[ii]=false then
							--raise notice '% ',p_curr_modules_credits[ii];
							if p_curr_modules[ii]!=p_fcurr_modules[ii] then
								p_abs_credits:=p_abs_credits+p_curr_modules_credits[ii];
							end if;
						end if;
						-- lõputöö/eksam ei lähe arvesse, nende jaoks võla arvutamine jääb endiseks
						if p_curr_modules[ii]=p_fcurr_modules[ii] then
							if p_curr_modules_credits[ii]!=0 then --kui ei ole korras
								for iii in 1..array_length(p_fcurr_modules,1)
								loop
									if p_fcurr_modules[iii] > 0 and p_curr_modules_credits[iii]=0 then --midagi tehtud, ei liida
										p_final_ok:=true;
									end if;
								end loop;
								if not p_final_ok and p_fabs_credits=0 then
									p_fabs_credits:=p_fabs_credits+p_curr_modules_credits[ii];
								end if;
							end if;
						end if;
					else
						p_opt_credits:=p_opt_credits+abs(p_curr_modules_credits[ii]);
					end if;
					if p_curr_modules_opt_credits[ii] > 0 then
						--raise notice 'o %', p_curr_modules[ii];
						-- siin kontrollime, kas moodul on jõuga täidetud; kui jah, siis nn võlga ei tohi tekkida
						if p_curr_modules_ok[ii]=false then
							--raise notice 'x% ',p_curr_modules_opt_credits[ii];
							p_abs_credits:=p_abs_credits+p_curr_modules_opt_credits[ii];
						end if;
					else
						--raise notice 'xx% ',p_curr_modules_opt_credits[ii];
						p_opt_credits:=p_opt_credits+abs(p_curr_modules_opt_credits[ii]);
					end if;
					--raise notice '3. %: % % ', p_curr_modules[ii],p_abs_credits,p_opt_credits;
				end if;
			end loop;
		end if;

		--raise notice 'p_abs: %, p_fabs: %',p_abs_credits,p_fabs_credits;

		p_abs_credits:=p_abs_credits+p_fabs_credits;

	--	raise NOTICE 'Fopt: %/%', p_fabs_credits, p_abs_credits;
		--raise NOTICE 'opt: %/%', p_opt_credits, p_optional;

		

		if p_opt_credits > p_optional THEN
			p_opt_credits:=0;
		ELSE
			p_opt_credits:=p_optional-p_opt_credits;
		end if;

		--raise NOTICE 'opt: %/%', p_opt_credits, p_optional;

		--kui kõik modulid on jõuga märgitud täidetuks, siis ikkagi kogutud EAP peab olema vähemalt sama suur kui õppekava EAP
		--raise notice '% - % - %', p_curriculum_credits, p_total_credits, pb_modules;
		if p_curriculum_credits > p_total_credits and pb_modules then
			p_abs_credits:=p_curriculum_credits-p_total_credits;
			--raise notice 'var 1';
		else
			p_abs_credits:=coalesce(p_abs_credits,0)+p_opt_credits;
			--raise notice 'var 2';
		end if;
		p_fabs_credits:=p_abs_credits-p_fabs_credits;
		if p_modules_count > 0 then
			pb_modules_ok:=false;
		end if;

		if is_last_period then
			p_abs_credits:=p_curriculum_credits-p_abs_credits;
			if p_abs_credits < 0 then
				p_abs_credits:=0;
			end if;
		end if;
		--raise notice 'siin %', p_abs_credits;

exception when others THEN
	raise notice '%, %',p_id,sqlerrm;
end;
$$
;

CREATE OR REPLACE FUNCTION public.upd_student_curriculum_completion(p_id bigint)
 RETURNS integer
 LANGUAGE plpgsql
AS $function$
declare 
  pb_exist boolean:=false;
	pb_exist2 boolean:=false;
	p_curr_modules bigint array;
  p_is_grade_modules boolean array;
	p_is_positive_grade_modules boolean array;
	p_curr_modules_ok boolean array;
	p_curr_module_types bigint array;
  p_curr_modules_credits numeric array;
	p_curr_modules_opt_credits numeric array;
	p_curr_modules2 bigint array;
  p_study_modules bigint array;
  p_vstudy_modules bigint array;
  p_optional numeric:=0;
	pb_modules boolean:=false; --kas on olemas jõuga moodulite täitmist
	r record;
	i int:=0;
  ii int:=0;
  iii int:=0;
  p_total int:=0;
	p_opt_credits numeric:=0;
	p_avg_credits numeric:=0;
	p_x_credits numeric:=0;
	p_avg_total_credits numeric:=0;
	p_total_credits numeric:=0;
	p_abs_credits numeric:=0;
	p_fabs_credits numeric:=0;
	p_curriculum_credits numeric:=0;
	a_count int:=0;
	pb_is_hgrade boolean:=false;

  p_vcurr_modules bigint array;
	p_vcurr_modules2 bigint array;

	p_fcurr_modules bigint array;
	p_fcurr_modules_ok bigint array;
	
	mod_id bigint;

	is_higher_curriculum boolean:=true;
	
	pb_modules_ok boolean:=true;
	p_modules_count integer:=0; --mitu moodulit on ette nähtud hindamiseks
	p_id2 bigint;
	p_final_ok boolean:=false;

	lp_abs_credits numeric:=0;
	lp_fabs_credits numeric:=0;
	lp_avg_total_credits numeric:=0;
	lp_total_credits numeric:=0; 
	lp_opt_credits  numeric:=0;
	lpb_modules_ok boolean:=true;
	p_last_period_credits numeric:=null;
BEGIN
	for r in (select distinct cvo.id,cm.id as m_id, cm.credits, cc.optional_study_credits, cm.module_code, cc.is_higher, cc.credits as curriculum_credits
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
		p_x_credits:=0;
		
		p_curr_modules_credits[i]:=r.credits;
		p_optional:=coalesce(r.optional_study_credits,0);
		p_curriculum_credits:=coalesce(r.curriculum_credits,0);
		if r.module_code='KUTSEMOODUL_L' then
			p_fcurr_modules[i]:=r.id;
		ELSE
			p_fcurr_modules[i]:=0;
		end if;
		is_higher_curriculum:=r.is_higher;
	end loop;

	if is_higher_curriculum then
		call get_higher_student_cc(p_id, false, p_abs_credits, p_fabs_credits, p_avg_total_credits, p_total_credits, p_opt_credits, pb_modules_ok);
		--raise notice '% % % % % %', p_abs_credits, p_fabs_credits, p_avg_total_credits, p_total_credits, p_opt_credits, pb_modules_ok;
		call get_higher_student_cc(p_id, true, lp_abs_credits, lp_fabs_credits, lp_avg_total_credits, lp_total_credits, lp_opt_credits, lpb_modules_ok);
		p_last_period_credits:=lp_abs_credits;
		--raise notice '%', p_last_period_credits;
		i:=0;
		
	else
		--Õppuri positiivsed tulemused
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
					--kahte tüüpi moodulid
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
					--valikõpingud
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

		--kokku võlg
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
	set study_backlog=-p_abs_credits, 
			study_backlog_without_graduate=-p_fabs_credits,
			average_mark=case when p_avg_total_credits > 0 then floor(p_avg_credits*1000/p_avg_total_credits)/1000 else 0 end, 
			credits=p_total_credits, 
			changed=current_timestamp(3),
			study_optional_backlog=-p_opt_credits,
			credits_before_current_study_period = p_last_period_credits,
			is_modules_ok=pb_modules_ok
	where student_id=p_id;
	
	GET DIAGNOSTICS a_count = ROW_COUNT;
	if a_count=0 THEN
			insert into student_curriculum_completion(student_id,study_backlog,study_backlog_without_graduate,average_mark,credits,inserted,changed,study_optional_backlog,is_modules_ok)
			values(p_id,-p_abs_credits,-p_fabs_credits,case when p_avg_total_credits > 0 then floor(p_avg_credits*100/p_avg_total_credits)/100 else 0 end,p_total_credits,current_timestamp(3),current_timestamp(3),-p_opt_credits,pb_modules_ok);
	end if;

	return 0;
exception when others THEN
	raise notice '%, %',p_id,sqlerrm;
	return -1;
end;
$function$
;

do $$
declare 
	r record;
	i integer:=0;
begin
	raise notice '%', current_timestamp(3);
	for r in (select s.id
						from student s
						where s.type_code='OPPUR_T' and s.status_code in ('OPPURSTAATUS_A','OPPURSTAATUS_V','OPPURSTAATUS_O'))
	loop
		i:=upd_student_curriculum_completion(r.id);
	end loop;
	raise notice '%', current_timestamp(3);
end;
$$;

UPDATE classifier SET is_vocational = true WHERE code = 'AVALDUS_LIIK_OVORM';

insert into classifier(code, value, name_et, main_class_code, valid, is_vocational, is_higher, inserted, version) 
values ('TEEMAOIGUS_LOUNATOETUS', 'LOUNATOETUS', 'Koolilõunatoetus', 'TEEMAOIGUS', true, true, false, current_timestamp(3), 1);

 

insert into user_role_default(object_code, permission_code, role_code) 
values('TEEMAOIGUS_LOUNATOETUS', 'OIGUS_V', 'ROLL_A'),
('TEEMAOIGUS_LOUNATOETUS', 'OIGUS_M', 'ROLL_A'),
('TEEMAOIGUS_LOUNATOETUS', 'OIGUS_V', 'ROLL_J');


CREATE OR REPLACE FUNCTION public.get_student_study_period_count(p_student bigint)
 RETURNS integer
 LANGUAGE plpgsql
AS $function$
	declare
		sp_count integer;
	begin
		select case when jaak_sem > nom_sem then 0 else nom_sem - jaak_sem end into sp_count from 
			(select nom_sem, coalesce(round(extract (day from nominal_study_end - now()::date) / 30.5 / 6), 0) jaak_sem from 
				(select round(c.study_period / 6.0) nom_sem, coalesce(s.nominal_study_end,
					s.study_start + interval '1 month' * c.study_period - interval '1 day') nominal_study_end from student s 
				join curriculum_version cv on cv.id = s.curriculum_version_id 
				join curriculum c on c.id = cv.curriculum_id 
				where s.id = p_student) x) y;
		if sp_count is null then
			sp_count := 0;
		end if;
		return sp_count;
	
		exception
			when others then
				return 0;
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
									coalesce(cvh.id,coalesce(hn.id,ds.curriculum_version_hmodule_id)) as curriculum_version_hmodule_id,coalesce(shs.is_optional,ds.is_optional) is_optional,
											--curriculum_version_hmodule_id
											coalesce(cvh.name_et,subj.name_et) as name_et,coalesce(cvh.name_en,subj.name_en) name_en,subj.code,
											coalesce(cvh.total_credits,subj.credits) credits,
											--is_optional
											case when ph.final_subject_id is not null then (select string_agg(coalesce(pers.firstname,fts.firstname )||' '||coalesce(pers.lastname,fts.lastname ),', ' order by fts.is_primary desc) from final_thesis ft
											join final_thesis_supervisor fts on ft.id=final_thesis_id 
											left join teacher ttt on fts.teacher_id =ttt.id 
											left join person pers on ttt.person_id =pers.id
											where ft.student_id=st.id )
											when ppp.id is not null then ppp.firstname||' '||ppp.lastname else (select string_agg(pers.firstname||' '||pers.lastname,', ') 
												from subject_study_period_teacher st join teacher tt on st.teacher_id=tt.id join person pers on tt.person_id=pers.id
													where st.subject_study_period_id=sp.id and coalesce(st.is_diploma_supplement,true)=true) end as teachers, --teachers
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
				subject_name_et=coalesce(r.name_et,'-'),
				subject_name_en=coalesce(r.name_en,r.name_et),
				teachers=substr(r.teachers,1,255),
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
					coalesce(r.name_et,'-'),coalesce(r.name_en,r.name_et),substr(r.teachers,1,255),	r.credits,r.code,				coalesce(r.study_period_id, get_study_period(NEW.grade_date::date, r.school_id::int)),case when coalesce(r.cvh_id,0) > 0 then true else false end,
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

			/*--kustutame üleliigset eelmist rida
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
				teachers=substr(r.teachers,1,255),	
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
			-- kustutame üleliigsed vanad read
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

update protocol_student set student_id=student_id,changed=current_timestamp(3), grade=grade, changed_by=coalesce(changed_by,'Automaat') where protocol_id in (select ph.protocol_id from protocol_hdata ph where ph.final_subject_id is not null) and grade is not null;

alter table diploma_supplement alter column curriculum_completion type varchar(1000);
alter table diploma_supplement alter column curriculum_completion_en type varchar(1000);

create trigger apel_application_committee_audit after insert or delete or update on apel_application_committee for each row execute procedure hois_audit();
create trigger curriculum_version_nominal_capacity_audit after insert or delete or update on curriculum_version_nominal_capacity for each row execute procedure hois_audit();
create trigger journal_entry_teacher_audit after insert or delete or update on journal_entry_teacher for each row execute procedure hois_audit();
create trigger midterm_task_student_result_history_audit after insert or delete or update on midterm_task_student_result_history for each row execute procedure hois_audit();
create trigger subject_program_study_content_teacher_audit after insert or delete or update on subject_program_study_content_teacher for each row execute procedure hois_audit();
create trigger subject_program_teacher_audit after insert or delete or update on subject_program_teacher for each row execute procedure hois_audit();
create trigger timetable_event_student_audit after insert or delete or update on timetable_event_student for each row execute procedure hois_audit();

