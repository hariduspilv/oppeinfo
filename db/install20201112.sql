\c tahvel_20201023

INSERT INTO "public"."classifier" ("code", "value", "value2", "name_et", "name_en", "name_ru", "parent_class_code", "main_class_code", "inserted", "changed", "valid", "description", "valid_from", "valid_thru", "extraval1", "extraval2", "ehis_value", "is_vocational", "is_higher", "version", "inserted_by", "changed_by") 
VALUES ('OIGUS_T', 'T', NULL, 'Teated', NULL, NULL, NULL, 'OIGUS', '2016-11-24 12:14:58.612906', NULL, 't', NULL, NULL, NULL, NULL, NULL, NULL, 't', 't', '0', NULL, NULL);

alter table journal add column is_individual boolean;
comment on column journal.is_individual is 'kas tegemist on idividuaalpäevikuga';

alter table journal_entry add column journal_student_id bigint;
comment on column journal_entry.journal_student_id is 'viide päeviku õppurile, kasutatakse individ päevikute puhul';
create index IXFK_journal_entry_journal_student on journal_entry(journal_student_id);
alter table journal_entry add constraint FK_journal_entry_journal_student foreign key(journal_student_id) references journal_student(id);

CREATE TABLE "timetable_event_student"
(
	"id" bigserial NOT NULL,
	"timetable_event_time_id" bigint NOT NULL,    -- viide sündmusele
	"inserted" timestamp without time zone NOT NULL,
	"student_id" bigint NOT NULL,    -- viide õppijale
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

COMMENT ON TABLE "timetable_event_student"	IS 'õppijatega seotud sündmused';
COMMENT ON COLUMN "timetable_event_student"."timetable_event_time_id"	IS 'viide sündmusele';
COMMENT ON COLUMN "timetable_event_student"."student_id"	IS 'viide õppijale';
ALTER TABLE "timetable_event_student" ADD CONSTRAINT "PK_timetable_event_student"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_timetable_event_student_student" ON "timetable_event_student" ("student_id" ASC);
CREATE INDEX "IXFK_timetable_event_student_timetable_event_time" ON "timetable_event_student" ("timetable_event_time_id" ASC);

ALTER TABLE "timetable_event_student" ADD CONSTRAINT "FK_timetable_event_student_student"	FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "timetable_event_student" ADD CONSTRAINT "FK_timetable_event_student_timetable_event_time"	FOREIGN KEY ("timetable_event_time_id") REFERENCES "public"."timetable_event_time" ("id") ON DELETE No Action ON UPDATE No Action;


alter table midterm_task drop column is_grade_schema;
alter table midterm_task_student_result drop column grading_schema_row_id;
alter table midterm_task_student_result drop column verbal_grade;
alter table student_curriculum_module_outcomes_result drop column verbal_grade;
alter table student_curriculum_module_outcomes_result_history drop column verbal_grade;

INSERT INTO "public"."classifier" ("code", "value", "value2", "name_et", "name_en", "name_ru", "parent_class_code", "main_class_code", "inserted", "changed", "valid", "description", "valid_from", "valid_thru", "extraval1", "extraval2", "ehis_value", "is_vocational", "is_higher", "version", "inserted_by", "changed_by") 
VALUES ('TEATE_LIIK_TUNN_OPTEADE', 'TUNN_OPTEADE', NULL, 'Tunniplaani automaatteavitus', NULL, NULL, NULL, 'TEATE_LIIK', '2018-08-29 13:41:10.715', NULL, 't', NULL, NULL, NULL, NULL, NULL, NULL, 't', 't', '0', 'Automaat', NULL);


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
								insert into journal_entry_student_history(journal_entry_student_id,grade_code,grade_inserted,inserted,version,inserted_by,grade_inserted_by,grading_schema_row_id,verbal_grade)
								select rr.id,grade_code,grade_inserted,now(),0,NEW.changed_by,grade_inserted_by,grading_schema_row_id,verbal_grade
								from journal_entry_student where id=rr.id and coalesce(grade_code,'x')!='x';
								update journal_entry_student set grading_schema_row_id=null, verbal_grade=null, grade_code=case when cardinality(r.arr) > 1 then 'KUTSEHINDAMINE_A' else r.grade_code end,grade_inserted=now(),grade_inserted_by=NEW.changed_by where id=rr.id;
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
										afr.teachers, afr.apel_school_id, ar.id as record_id,afr.is_optional,afr.curriculum_version_hmodule_id,null as study_period_id,coalesce(afr.grade_date,aa.confirmed) as grade_date
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
					p_id:=get_study_period(r.grade_date::date, NEW.school_id::int);
					if coalesce(p_id,0)=0 then
							p_id:=null;
					end if;
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
						coalesce(r.name_et,'-'),coalesce(coalesce(r.name_en,r.name_et),'-'),r.teachers,	r.credits,r.code,				r.is_optional,p_id,r.curriculum_version_hmodule_id) returning id into p_id; --is_optional

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

alter table apel_application add column submitted timestamp without time zone NULL,
add column submitted_by varchar(100);
comment on column apel_application.submitted is 'esitamise kp';
comment on column apel_application.submitted_by is 'esitaja nimi';


do $$
declare
	r record;
begin
	for r in (select ap.id, ld.new_value, ld.inserted, ld.inserted_by
						from apel_application ap
								 left join log_table_data ld on ap.id=ld.table_id and ld.table_name='apel_application' and (ld.new_value->>'status_code')='VOTA_STAATUS_E'
						where ap.status_code!='VOTA_STAATUS_K'
						order by ld.inserted)
	loop
		update apel_application ap set submitted=coalesce(r.inserted,ap.inserted), submitted_by=coalesce(r.inserted_by,ap.inserted_by) where id=r.id;
	end loop;
end;
$$;

insert into user_role_default(object_code,permission_code,role_code) values('TEEMAOIGUS_AVALDUS','OIGUS_T','ROLL_A');
insert into user_role_default(object_code,permission_code,role_code) values('TEEMAOIGUS_ESINDAVALDUS','OIGUS_T','ROLL_A');
insert into user_role_default(object_code,permission_code,role_code) values('TEEMAOIGUS_MARKUS','OIGUS_T','ROLL_A');
insert into user_role_default(object_code,permission_code,role_code) values('TEEMAOIGUS_PUUDUMINE','OIGUS_T','ROLL_A');
insert into user_role_default(object_code,permission_code,role_code) values('TEEMAOIGUS_TUGITEENUS','OIGUS_T','ROLL_A');
insert into user_role_default(object_code,permission_code,role_code) values('TEEMAOIGUS_VOTA','OIGUS_T','ROLL_A');

insert into user_role_default(object_code,permission_code,role_code) values('TEEMAOIGUS_AVALDUS','OIGUS_T','ROLL_O');
insert into user_role_default(object_code,permission_code,role_code) values('TEEMAOIGUS_PUUDUMINE','OIGUS_T','ROLL_O');
insert into user_role_default(object_code,permission_code,role_code) values('TEEMAOIGUS_MARKUS','OIGUS_T','ROLL_O');

insert into user_rights(user_id,permission_code,object_code,inserted,version,inserted_by)
select ur.user_id,'OIGUS_T',ur.object_code,current_timestamp(3),0,'Skript'
from user_ uu
     join user_rights ur on uu.id=ur.user_id
where uu.role_code='ROLL_A' and ur.permission_code='OIGUS_V' and ur.object_code in ('TEEMAOIGUS_AVALDUS','TEEMAOIGUS_ESINDAVALDUS','TEEMAOIGUS_MARKUS','TEEMAOIGUS_PUUDUMINE','TEEMAOIGUS_TUGITEENUS','TEEMAOIGUS_VOTA');

insert into user_rights(user_id,permission_code,object_code,inserted,version,inserted_by)
select ur.user_id,'OIGUS_T',ur.object_code,current_timestamp(3),0,'Skript'
from user_ uu
     join user_rights ur on uu.id=ur.user_id
where uu.role_code='ROLL_O' and ur.permission_code='OIGUS_V' and ur.object_code in ('TEEMAOIGUS_AVALDUS','TEEMAOIGUS_MARKUS','TEEMAOIGUS_PUUDUMINE');

do $$
declare
	r record;
begin
	for r in (
select ss.*
from student_curriculum_module_outcomes_result as ss 
	 join apel_application aa on ss.apel_application_id=aa.id
	 join apel_application_record acr on aa.id=acr.apel_application_id
	 join apel_application_informal_subject_or_module acrm on acr.id=acrm.apel_application_record_id
	 --in apel_application_informal_subject_or_module_outcomes oo on acrm.id=oo.apel_application_informal_subject_or_module_id
where aa.status_code='VOTA_STAATUS_C' and (
	select count (*) from apel_application_informal_subject_or_module_outcomes oo where oo.curriculum_module_outcomes_id=ss.curriculum_module_outcomes_id and acrm.transfer=true) =0)
loop
	delete from student_curriculum_module_outcomes_result_history where student_curriculum_module_outcomes_result_id=r.id;
	delete from student_curriculum_module_outcomes_result where id=r.id;
end loop;
end;
$$;
