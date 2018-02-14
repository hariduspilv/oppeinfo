\c hois;

DROP TABLE IF EXISTS "student_vocational_result_omodule" CASCADE;
CREATE TABLE "student_vocational_result_omodule"
(
	"student_vocational_result_id" bigint NOT NULL,
	"curriculum_version_omodule_id" bigint NOT NULL,
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "student_vocational_result_omodule"IS 'moodulite vahetus kutseõppes';
ALTER TABLE "student_vocational_result_omodule" ADD CONSTRAINT "PK_protocol_student_omodule"	PRIMARY KEY ("student_vocational_result_id");
CREATE INDEX "IXFK_protocol_student_omodule_curriculum_version_omodule" ON "student_vocational_result_omodule" ("curriculum_version_omodule_id" ASC);
CREATE INDEX "IXFK_protocol_student_omodule_student_vocational_result" ON "student_vocational_result_omodule" ("student_vocational_result_id" ASC);
ALTER TABLE "student_vocational_result_omodule" ADD CONSTRAINT "FK_protocol_student_omodule_curriculum_version_omodule"	FOREIGN KEY ("curriculum_version_omodule_id") REFERENCES "public"."curriculum_version_omodule" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "student_vocational_result_omodule" ADD CONSTRAINT "FK_protocol_student_omodule_student_vocational_result"	FOREIGN KEY ("student_vocational_result_id") REFERENCES "student_vocational_result" ("id") ON DELETE Cascade ON UPDATE No Action;


insert into classifier (code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version,inserted_by) select 'OPPEAASTA_2011_12','2011_12','2011/2012','OPPEAASTA',now(),TRUE,TRUE,true,0,'DATA_TRANSFER_PROCESS'
WHERE NOT EXISTS (SELECT code FROM classifier WHERE code='OPPEAASTA_2011_12');
insert into classifier (code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version,inserted_by) select 'OPPEAASTA_2010_11','2010_11','2010/2011','OPPEAASTA',now(),TRUE,TRUE,true,0,'DATA_TRANSFER_PROCESS'
WHERE NOT EXISTS (SELECT code FROM classifier WHERE code='OPPEAASTA_2010_11');
insert into classifier (code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version,inserted_by) select 'OPPEAASTA_2009_10','2009_10','2009/2010','OPPEAASTA',now(),TRUE,TRUE,true,0,'DATA_TRANSFER_PROCESS'
WHERE NOT EXISTS (SELECT code FROM classifier WHERE code='OPPEAASTA_2009_10');
insert into classifier (code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version,inserted_by) select 'KUTSEHINDAMINE_1','1','Puudulik','KUTSEHINDAMINE',now(),TRUE,TRUE,true,0,'DATA_TRANSFER_PROCESS'
WHERE NOT EXISTS (SELECT code FROM classifier WHERE code='KUTSEHINDAMINE_1');

insert into classifier (code, value, name_et, inserted, valid, is_vocational, is_higher, version) 
values ('TEEMAOIGUS_OPPEMATERJAL', 'OPPEMATERJAL', 'Õppematerjalid', now(), true,  true,  true, 0);

insert into user_role_default (object_code, permission_code, role_code) values ('TEEMAOIGUS_OPPEMATERJAL', 'OIGUS_V', 'ROLL_A');
insert into user_role_default (object_code, permission_code, role_code) values ('TEEMAOIGUS_OPPEMATERJAL', 'OIGUS_V', 'ROLL_O');

insert into user_rights(user_id,permission_code,object_code,inserted,version,inserted_by)
select u.id, 'OIGUS_V', 'TEEMAOIGUS_OPPEMATERJAL', now(), 0, 'Automaat' from user_ u where u.role_code in ('ROLL_A','ROLL_O');

insert into user_rights(user_id,permission_code,object_code,inserted,version,inserted_by)
select u.id, 'OIGUS_M', 'TEEMAOIGUS_OPPEMATERJAL', now(), 0, 'Automaat' from user_ u where u.role_code in ('ROLL_A','ROLL_O');

update classifier set main_class_code = 'TEEMAOIGUS' where code = 'TEEMAOIGUS_OPPEMATERJAL' ;


alter table student_vocational_result
drop constraint if exists "FK_student_vocational_result_apel_application_record",
drop constraint if exists "FK_student_vocational_result_protocol_student",
add CONSTRAINT "FK_student_vocational_result_apel_application_record" FOREIGN KEY (apel_application_record_id) REFERENCES public.apel_application_record(id) on delete cascade,
add CONSTRAINT "FK_student_vocational_result_protocol_student" FOREIGN KEY (protocol_student_id) REFERENCES public.protocol_student(id) on delete cascade;

CREATE OR REPLACE FUNCTION ins_upd_del_result()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
declare
    u_count integer;
		b_count integer;
		r record;
begin
	if tg_op in ('INSERT','UPDATE') and NEW.id is not null and COALESCE(NEW.grade,'x')='x' or tg_op in ('DELETE') THEN
		if tg_op in ('INSERT','UPDATE') THEN	
			delete from student_higher_result where protocol_student_id=NEW.id;
			delete from student_vocational_result where protocol_student_id=NEW.id;
		ELSE
			delete from student_higher_result where protocol_student_id=old.id;
			delete from student_vocational_result where protocol_student_id=old.id;
		end if;
	elsif NEW.id is not null then
		for r in (SELECT sp.subject_id,clf.value as grade, sp.study_period_id,
											--curriculum_version_hmodule_id
											subj.name_et,subj.name_en,subj.code,subj.credits,
											--is_optional
											(select string_agg(pers.firstname||' '||pers.lastname,', ') 
												from subject_study_period_teacher st join teacher tt on st.teacher_id=tt.id join person pers on tt.person_id=pers.id
													where st.subject_study_period_id=sp.id) as teachers --teachers
							from protocol pp 
									 join protocol_hdata ph on pp.id=ph.protocol_id
									 join subject_study_period sp on ph.subject_study_period_id=sp.id
									 join subject subj on sp.subject_id=subj.id
									 join classifier clf on clf.code=NEW.grade_code
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
				is_optional=false,
				subject_id=r.subject_id,
				changed=now(),
				study_period_id=r.study_period_id
			where protocol_student_id=NEW.id;
			GET DIAGNOSTICS u_count = ROW_COUNT;

			if coalesce(u_count,0)=0 THEN
				insert into student_higher_result (
					student_id,			subject_id,				protocol_student_id,				grade,				grade_date,				grade_mark,				grade_code,
					apel_application_record_id,				apel_school_id,				inserted,
					subject_name_et,				subject_name_en,				teachers,				credits,				subject_code,				is_optional,study_period_id) 
				values(
					NEW.student_id,r.subject_id,NEW.id,coalesce(NEW.grade,r.grade),NEW.grade_date,
					coalesce(NEW.grade_mark,case r.grade when '0' then 0 when '1' then 1 when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else null end),				NEW.grade_code,
					null,--apel_application_record_id,
					null,--apel_school_id
					now(),
					r.name_et,coalesce(r.name_en,r.name_et),r.teachers,	r.credits,r.code,				FALSE,r.study_period_id); --is_optional
			end if;

			/*--kustutame üleliigset eelmist rida
			delete from student_higher_result
WHERE id IN (SELECT id
              FROM (SELECT id,
                             ROW_NUMBER() OVER (partition BY column1, column2, column3 ORDER BY id) AS rnum
                     FROM tablename) t
              WHERE t.rnum > 1);*/
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
			-- kustutame üleliigsed vanad read
			if r.total > 1 THEN
					DELETE FROM student_vocational_result 
						WHERE id IN (SELECT id
              FROM (SELECT id,
                             ROW_NUMBER() OVER (partition BY curriculum_version_omodule_id, student_id ORDER BY grade_date desc nulls last, id desc) AS rnum
                     FROM student_vocational_result where student_id=NEW.student_id and curriculum_version_omodule_id=r.curriculum_version_omodule_id) t
              WHERE t.rnum > 1);
			end if;
			return null;
		end loop;
  end if;
	return null;
end
$function$;

update protocol_student set changed=current_timestamp(3), changed_by=coalesce(changed_by,'Automaat');

