\c hois

alter table timetable_event add column is_public boolean;
comment on column timetable_event.is_public is 'kas on avalik sündmus';



CREATE TABLE "apel_application_committee"
(
	"id" bigserial NOT NULL,
	"committee_id" bigint NOT NULL,    -- viide komsjonile
	"apel_application_id" bigint NOT NULL,    -- viide VÕTA taotlusele
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" bigint NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "apel_application_committee"	IS 'VÕTA taotlusega seotud komisjonid';
COMMENT ON COLUMN "apel_application_committee"."committee_id"	IS 'viide komsjonile';
COMMENT ON COLUMN "apel_application_committee"."apel_application_id"	IS 'viide VÕTA taotlusele';

/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE "apel_application_committee" ADD CONSTRAINT "PK_apel_application_committee"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_apel_application_committee_apel_application" ON "apel_application_committee" ("apel_application_id" ASC);
CREATE INDEX "IXFK_apel_application_committee_committee" ON "apel_application_committee" ("committee_id" ASC);
/* Create Foreign Key Constraints */

ALTER TABLE "apel_application_committee" ADD CONSTRAINT "FK_apel_application_committee_apel_application"	FOREIGN KEY ("apel_application_id") REFERENCES "public"."apel_application" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "apel_application_committee" ADD CONSTRAINT "FK_apel_application_committee_committee"	FOREIGN KEY ("committee_id") REFERENCES "public"."committee" ("id") ON DELETE No Action ON UPDATE No Action;


insert into apel_application_committee (committee_id,apel_application_id,inserted,version,inserted_by)
select committee_id, id,inserted,version,inserted_by
from apel_application
where committee_id is not null;

alter table apel_application_comment add column is_student boolean;
comment on column apel_application_comment.is_student is 'kas kommentaar on õppijale nähtav';

update apel_application_comment set is_student=true;

alter table timetable_event add column inserted_teacher_id bigint;
comment on column timetable_event.inserted_teacher_id is 'sündmuse lisanud õpetaja';
CREATE INDEX "IXFK_timetable_event_teacher" ON "public"."timetable_event" ("inserted_teacher_id" ASC);
ALTER TABLE "public"."timetable_event" ADD CONSTRAINT "FK_timetable_event_teacher"	FOREIGN KEY ("inserted_teacher_id") REFERENCES "public"."teacher" ("id") ON DELETE No Action ON UPDATE No Action;


with s as (select te.*, tt.id as teacher_id
from timetable_event te
	   join school sc on te.school_id=sc.id
     join person pp on upper(pp.firstname||' '||pp.lastname||' ('||pp.idcode||')')=upper(te.inserted_by) 
     join teacher tt on pp.id=tt.person_id and sc.id=tt.school_id and tt.is_active=true
where te.timetable_object_id is null and te.juhan_event_id is null and te.person_id is null and
(select count(*) from timetable_event_time tet join timetable_event_teacher tt on tet.id=tt.timetable_event_time_id where tet.timetable_event_id=te.id)=1)
update timetable_event te
	set inserted_teacher_id=s.teacher_id
from s
where te.id=s.id;



/* Create Tables */

CREATE TABLE "journal_entry_teacher"
(
	"id" bigserial NOT NULL ,
	"journal_entry_id" bigint NOT NULL,    -- viide sissekandele
	"teacher_id" bigint NOT NULL,    -- viide õpetajale
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "journal_entry_teacher"	IS 'sissekandega seotud õpetajad'
;

COMMENT ON COLUMN "journal_entry_teacher"."journal_entry_id"	IS 'viide sissekandele'
;

COMMENT ON COLUMN "journal_entry_teacher"."teacher_id"	IS 'viide õpetajale'
;


/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE "journal_entry_teacher" ADD CONSTRAINT "PK_journal_entry_teacher"
	PRIMARY KEY ("id")
;

CREATE INDEX "IXFK_journal_entry_teacher_journal_entry" ON "journal_entry_teacher" ("journal_entry_id" ASC)
;

CREATE INDEX "IXFK_journal_entry_teacher_teacher" ON "journal_entry_teacher" ("teacher_id" ASC)
;

CREATE INDEX "UQ_journal_entry_teacher" ON "journal_entry_teacher" ("journal_entry_id" ASC)
;

/* Create Foreign Key Constraints */

ALTER TABLE "journal_entry_teacher" ADD CONSTRAINT "FK_journal_entry_teacher_journal_entry"
	FOREIGN KEY ("journal_entry_id") REFERENCES "public"."journal_entry" ("id") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "journal_entry_teacher" ADD CONSTRAINT "FK_journal_entry_teacher_teacher"
	FOREIGN KEY ("teacher_id") REFERENCES "public"."teacher" ("id") ON DELETE No Action ON UPDATE No Action
;


do $$
declare
	r record;
begin
	for r in (select * from journal_teacher)
	loop
		insert into journal_entry_teacher(journal_entry_id,teacher_id,inserted,version,inserted_by)
		select je.id,r.teacher_id,je.inserted,0,je.inserted_by
		from journal_entry je
		where je.journal_id=r.journal_id;
	end loop;
end;
$$;

do $$
declare
	r record;
	rr record;
	i integer:=0;
	p_last	integer:=0;
begin
	for r in (select order_nr, curriculum_module_id,id from curriculum_module_outcomes
				where (order_nr is null or order_nr is not null and curriculum_module_id in (21750,12486))and base_module_outcomes_id is null
				order by 2 desc, 1 asc nulls last)
	loop
		if p_last!=r.curriculum_module_id then
			i:=0;
		end if;
		update curriculum_module_outcomes set order_nr=i where id=r.id;
		i:=i+1;
	end loop;

  for r in (select count(*), order_nr, curriculum_module_id from curriculum_module_outcomes
				where base_module_outcomes_id is null
				group by order_nr, curriculum_module_id
				having count(*) > 1
				order by 2 desc, 1 asc nulls last)
	loop
		i:=0;
		for rr in (select order_nr, curriculum_module_id,id from curriculum_module_outcomes
				where curriculum_module_id=r.curriculum_module_id
				order by 1 asc nulls last)
		loop
			update curriculum_module_outcomes set order_nr=i where id=rr.id;
			i:=i+1;
		end loop;
	end loop;
end;
$$;

alter table student add column reg_nr integer;
comment on column student.reg_nr is 'õppuri unikaalne number kooli piires';
alter table student_higher_result alter column teachers type varchar(1000);


/* Create Tables */

CREATE TABLE "school_student_reg_nr"
(
	"id" bigserial NOT NULL,
	"school_id" bigint NOT NULL,
	"reg_nr" integer NOT NULL,
	"inserted" timestamp without time zone NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"version" integer NOT NULL,
	"changed" timestamp without time zone NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "school_student_reg_nr"	IS 'õppeasutuse õppurite reg_nr hoidmine';
/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE "school_student_reg_nr" ADD CONSTRAINT "PK_school_student_reg_nr"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_school_student_reg_nr_school" ON "school_student_reg_nr" ("school_id" ASC);

/* Create Foreign Key Constraints */
ALTER TABLE "school_student_reg_nr" ADD CONSTRAINT "FK_school_student_reg_nr_school"	FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE Cascade ON UPDATE No Action;


alter table subject_study_period add column places smallint;
comment on column subject_study_period.places is 'kohtade arv';

alter table timetable_event_time add column add_info varchar(4000);



/* Create Tables */

alter TABLE "student_occupation_certificate" add column "language_code" varchar(100)	 NULL;   -- eksami keel, viide klassifikaatorile OPPEKEEL
alter TABLE "protocol_student" add column "language_code" varchar(100)	 NULL;
COMMENT ON COLUMN "public"."student_occupation_certificate"."language_code" 	IS 'eksami keel, viide klassifikaatorile OPPEKEEL';


CREATE INDEX "IXFK_student_occupation_certificate_classifier_04" ON "public"."student_occupation_certificate" ("language_code" ASC);
CREATE INDEX "IXFK_protocol_student_classifier_02" ON "public"."protocol_student" ("language_code" ASC);

ALTER TABLE "student_occupation_certificate" ADD CONSTRAINT "FK_student_occupation_certificate_classifier_04"	FOREIGN KEY ("language_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "protocol_student" ADD CONSTRAINT "FK_protocol_student_classifier_02"	FOREIGN KEY ("language_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;


INSERT INTO "classifier" ("code", "value", "name_et", "inserted", "valid", "is_vocational", "is_higher", "version", "inserted_by") 
VALUES ('KOEFITSIENT', 'KOEFITSIENT', 'Koormuse koefitsiendid', current_timestamp(3), 't', 't', 't', '0', 'Automaat');
INSERT INTO "classifier" ("code", "value", "name_et", main_Class_code,"inserted", "valid", "is_vocational", "is_higher", "version", "inserted_by") 
VALUES ('KOEFITSIENT_K1', 'K1', 'K1', 'KOEFITSIENT', current_timestamp(3), 't', 't', 't', '0', 'Automaat');
INSERT INTO "classifier" ("code", "value", "name_et", main_Class_code, "inserted", "valid", "is_vocational", "is_higher", "version", "inserted_by") 
VALUES ('KOEFITSIENT_K2', 'K2', 'K2', 'KOEFITSIENT', current_timestamp(3), 't', 't', 't', '0', 'Automaat');
INSERT INTO "classifier" ("code", "value", "name_et", main_Class_code, "inserted", "valid", "is_vocational", "is_higher", "version", "inserted_by") 
VALUES ('KOEFITSIENT_K3', 'K3', 'K3', 'KOEFITSIENT', current_timestamp(3), 't', 't', 't', '0', 'Automaat');

update student_occupation_certificate
	set language_code=case when upper(language) like upper('inglise%') then 'OPPEKEEL_I' when upper(language) like upper('vene%') then 'OPPEKEEL_V'
									  else 'OPPEKEEL_E' end
where coalesce(language_code,'x')='x';

alter table school_capacity_type_load add column coefficient_code varchar(100);

create index IXFK_school_capacity_type_load_classifier on school_capacity_type_load(coefficient_code);
alter table school_capacity_type_load add constraint FK_school_capacity_type_load_classifier foreign key (coefficient_code) references classifier(code);

alter table lesson_plan add column coefficient_code varchar(100);
create index IXFK_lesson_plan_classifier on lesson_plan(coefficient_code);
alter table lesson_plan add constraint FK_lesson_plan_classifier foreign key (coefficient_code) references classifier(code);
comment on column lesson_plan.coefficient_code is 'viide koefits klassifikaatorile';
comment on column school_capacity_type_load.coefficient_code is 'viide koefits klassifikaatorile';

alter table journal add column is_free boolean;
comment on column journal.is_free is 'kas päeviku on tasuta tunnid';

alter table apel_application add column is_new boolean;
comment on column apel_application.is_new is 'kas tegemist on uue VÕTA taotlusega';

alter table subject_study_period_plan alter column study_period_id drop not null;

do $$
declare
	r record;
	rr record;
	p_last_id bigint:=0;
	i integer:=0;
	p_id bigint:=0;
begin
	for r in (select sp.*
			  from subject_study_period_plan sp 
			  	   join study_period sd on sp.study_period_id =sd.id 
			  where (select count(*) from subject_study_period_plan_curriculum ssppc where ssppc .subject_study_period_plan_id =sp.id)=0 and 
			  		  (select count(*) from subject_study_period_plan_studyform ssppc where ssppc .subject_study_period_plan_id =sp.id)=0 
			  order by sp.subject_id,sd.start_date desc)
	loop 
		if p_last_id!=r.subject_id then
			insert into subject_study_period_plan(subject_id,inserted,version,inserted_by)
			values(r.subject_id,r.inserted,r.version,r.inserted_by) returning id into p_id;
			insert into subject_study_period_plan_capacity(inserted,subject_study_period_plan_id,capacity_type_code,version,inserted_by,is_contact,hours)
			select inserted,p_id,capacity_type_code,version,inserted_by,is_contact,hours
			from subject_study_period_plan_capacity where subject_study_period_plan_id=r.id;
		end if;
		p_last_id:=r.subject_id;
	end loop;
end;
$$;

do $$
declare
	r record;
	rr record;
	p_last_id varchar(1000):='0';
	i integer:=0;
	p_id bigint:=0;
begin
	for r in (select sp.*,coalesce((select string_agg(curriculum_id::varchar ,',' order by curriculum_id) from subject_study_period_plan_curriculum ssppc where ssppc .subject_study_period_plan_id =sp.id),'x') as mis1,
			coalesce((select string_agg(study_form_code::varchar ,',' order by study_form_code) from subject_study_period_plan_studyform ssppc where ssppc .subject_study_period_plan_id =sp.id),'x') as mis2
			  from subject_study_period_plan sp 
			  	   join study_period sd on sp.study_period_id =sd.id 
			  where (select count(*) from subject_study_period_plan_curriculum ssppc where ssppc .subject_study_period_plan_id =sp.id) > 0 or  
			  		  (select count(*) from subject_study_period_plan_studyform ssppc where ssppc .subject_study_period_plan_id =sp.id)> 0 
			  order by sp.subject_id,mis1,mis2,sd.start_date desc)
	loop 
		if p_last_id!=r.subject_id::varchar||'_'||r.mis1||'_'||r.mis2 then
			insert into subject_study_period_plan(subject_id,inserted,version,inserted_by)
			values(r.subject_id,r.inserted,r.version,r.inserted_by) returning id into p_id;
			insert into subject_study_period_plan_capacity(inserted,subject_study_period_plan_id,capacity_type_code,version,inserted_by,is_contact,hours)
			select inserted,p_id,capacity_type_code,version,inserted_by,is_contact,hours
			from subject_study_period_plan_capacity where subject_study_period_plan_id=r.id;
			insert into subject_study_period_plan_curriculum(inserted,subject_study_period_plan_id,curriculum_id,version,inserted_by)
			select inserted,p_id,curriculum_id,version,inserted_by
			from subject_study_period_plan_curriculum where subject_study_period_plan_id=r.id;
			insert into subject_study_period_plan_studyform(inserted,subject_study_period_plan_id,study_form_code,version,inserted_by)
			select inserted,p_id,study_form_code,version,inserted_by
			from subject_study_period_plan_studyform where subject_study_period_plan_id=r.id;
		end if;
		p_last_id:=r.subject_id::varchar||'_'||r.mis1||'_'||r.mis2;
	end loop;
end;
$$;

alter table subject_study_period add column coefficient_code varchar(100);

create index IXFK_subject_study_period_classifier_03 on subject_study_period(coefficient_code);
alter table subject_study_period add constraint FK_subject_study_period_classifier_03 foreign key (coefficient_code) references classifier(code);

alter table subject_study_period_teacher_capacity add column subject_study_period_subgroup_id bigint;
comment on column subject_study_period_teacher_capacity.subject_study_period_subgroup_id is 'viide osarühmale kui tegemist on osarühma kaupa koormuse planeeringuga';
create index IXFK_subject_study_period_teacher_capacity_subject_study_period_subgroup on subject_study_period_teacher_capacity(subject_study_period_subgroup_id);
alter table subject_study_period_teacher_capacity add constraint FK_subject_study_period_teacher_capacity_subject_study_period_subgroup foreign key (subject_study_period_subgroup_id) references subject_study_period_subgroup(id);



CREATE TABLE "ws_ad_log"
(
	"id" bigserial NOT NULL,
	"request_url" varchar(4000)	 NULL,
	"request_param" text NULL,
	"response" text NULL,
	"has_errors" boolean NOT NULL,
	"log_txt" text NULL,
	"inserted" timestamp without time zone NOT NULL
)
;

COMMENT ON TABLE "ws_ad_log"IS 'log tabel AD jaoks';
ALTER TABLE "ws_ad_log" ADD CONSTRAINT "PK_ws_ad_log"	PRIMARY KEY ("id");


/* Create Tables */

CREATE TABLE "student_higher_result_replaced_subject"
(
	"id" bigserial NOT NULL,
	"student_higher_result_id" bigint NOT NULL,    -- viide võta suurele ainele, mille kohta antud asendused kehtivad
	"subject_id" bigint NOT NULL,    -- viide õppeainele
	"curriculum_version_hmodule_id" bigint NULL,    -- viide moodulile
	"is_optional" boolean NOT NULL    -- kas tegemist on valik või kohustusliku ainega
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "student_higher_result_replaced_subject"	IS 'võta suurte ainete asendamiseks mõeldud tabel';
COMMENT ON COLUMN "student_higher_result_replaced_subject"."student_higher_result_id"	IS 'viide võta suurele ainele, mille kohta antud asendused kehtivad';
COMMENT ON COLUMN "student_higher_result_replaced_subject"."subject_id"	IS 'viide õppeainele';
COMMENT ON COLUMN "student_higher_result_replaced_subject"."curriculum_version_hmodule_id"	IS 'viide moodulile';
COMMENT ON COLUMN "student_higher_result_replaced_subject"."is_optional"	IS 'kas tegemist on valik või kohustusliku ainega';

ALTER TABLE "student_higher_result_replaced_subject" ADD CONSTRAINT "PK_student_higher_result_replaced_subject"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_student_higher_result_replaced_subject_curriculum_version_hmodule" ON "student_higher_result_replaced_subject" ("curriculum_version_hmodule_id" ASC);
CREATE INDEX "IXFK_student_higher_result_replaced_subject_student_higher_result" ON "student_higher_result_replaced_subject" ("student_higher_result_id" ASC);
CREATE INDEX "IXFK_student_higher_result_replaced_subject_subject" ON "student_higher_result_replaced_subject" ("subject_id" ASC);

ALTER TABLE "student_higher_result_replaced_subject" ADD CONSTRAINT "FK_student_higher_result_replaced_subject_curriculum_version_hmodule"	FOREIGN KEY ("curriculum_version_hmodule_id") REFERENCES "public"."curriculum_version_hmodule" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "student_higher_result_replaced_subject" ADD CONSTRAINT "FK_student_higher_result_replaced_subject_student_higher_result"	FOREIGN KEY ("student_higher_result_id") REFERENCES "public"."student_higher_result" ("id") ON DELETE Cascade  ON UPDATE No Action;
ALTER TABLE "student_higher_result_replaced_subject" ADD CONSTRAINT "FK_student_higher_result_replaced_subject_subject"	FOREIGN KEY ("subject_id") REFERENCES "public"."subject" ("id") ON DELETE No Action ON UPDATE No Action;

CREATE OR REPLACE FUNCTION public.get_lessons(start_date timestamp without time zone, end_date timestamp without time zone)
 RETURNS integer
 LANGUAGE plpgsql
AS $function$
begin
	return round((extract(epoch from (end_date - start_date)) / 60) / 45);
end
$function$
;

create or replace function upd_apel_replaced_subjects(s_id bigint, p_id bigint, record_id bigint) returns integer LANGUAGE plpgsql AS $function$
declare
	rr record;
	rrr record;
	pm_id bigint:=0;
	pm_is_optional boolean:=false;
begin
	delete from student_higher_result_replaced_subject where student_higher_result_id=p_id;
	for rr in (select * from apel_application_formal_replaced_subject_or_module ar where ar.apel_application_record_id=record_id)
	loop
		pm_id:=0;
		pm_is_optional:=true;
		for rrr in (select cm.id, hms.is_optional
								from student st
										 join curriculum_version_hmodule cm on st.curriculum_version_id=cm.curriculum_version_id
										 join curriculum_version_hmodule_subject hms on cm.id=hms.curriculum_version_hmodule_id
								where st.id=s_id and hms.subject_id=rr.subject_id and 
											 (coalesce(st.curriculum_speciality_id,0)=0 or 
												coalesce(st.curriculum_speciality_id,0) > 0 and 
												exists(
														select 1 
														from curriculum_version_hmodule_speciality hs
																	join curriculum_version_speciality cvs on hs.curriculum_version_speciality_id=cvs.id
														where hs.curriculum_version_hmodule_id=cm.id and
														cvs.curriculum_speciality_id=st.curriculum_speciality_id))
									order by cm.id)
		LOOP
			pm_id:=rrr.id;
			pm_is_optional:=rrr.is_optional;
			exit;
		end loop;
							--leaime mooduli ja kohustuslikkuse
		insert into student_higher_result_replaced_subject(student_higher_result_id,subject_id,curriculum_version_hmodule_id,is_optional)
		values(p_id,rr.subject_id,case when pm_id > 0 then pm_id else null end,pm_is_optional);
					 --raise notice 's % %', record_id, rr.subject_id;
	end loop;
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
		rrr record;
		p_id integer;
		x integer;
		pm_id bigint;
		pm_is_optional boolean;
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
				--siin midagi peaks tegema hakkama suurte võta ainetega
				for r in (SELECT subj.id as subject_id,aa.is_new,
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
										SELECT subj.id,false,
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
						coalesce(r.name_et,'-'),coalesce(coalesce(r.name_en,r.name_et),'-'),r.teachers,	r.credits,r.code,				coalesce(r.is_optional,true),p_id,r.curriculum_version_hmodule_id) returning id into p_id; --is_optional

					if coalesce(r.subject_id,0) > 0 then
						--select distinct first_value(id)over(partition by r.subject_id order by grade_date desc nulls last) into p_id from student_higher_result where student_id=NEW.student_id and subject_id=r.subject_id;
						update student_higher_result set is_active=false where student_id=NEW.student_id and subject_id=r.subject_id and id!=p_id;
					end if;
					--nüüd midagi peaks tegema asendusainetega
					--ideaalis peaks ained paigutama hetkel kehtiva õppekava ver järgi
					--seeega leiame iga aine jaoks temale sobivat moodulit
					if coalesce(r.is_new,false)=true then
						x:=upd_apel_replaced_subjects(NEW.student_id,p_id,r.record_id);
--s_id bigint, p_id bigint
						/*for rr in (select * from apel_application_formal_replaced_subject_or_module ar where ar.apel_application_record_id=r.record_id)
						loop
							pm_id:=0;
							pm_is_optional:=true;
							for rrr in (select cm.id, hms.is_optional
									from student st
											 join curriculum_version_hmodule cm on st.curriculum_version_id=cm.curriculum_version_id
											 join curriculum_version_hmodule_subject hms on cm.id=hms.curriculum_version_hmodule_id
								  where st.id=NEW.student_id and hms.subject_id=rr.subject_id and 
											 (coalesce(st.curriculum_speciality_id,0)=0 or 
												coalesce(st.curriculum_speciality_id,0) > 0 and 
												exists(
														select 1 
														from curriculum_version_hmodule_speciality hs
																	join curriculum_version_speciality cvs on hs.curriculum_version_speciality_id=cvs.id
														where hs.curriculum_version_hmodule_id=cm.id and
														cvs.curriculum_speciality_id=st.curriculum_speciality_id))
									order by cm.id)
							LOOP
								pm_id:=rrr.id;
								pm_is_optional:=rrr.is_optional;
								exit;
							end loop;
							--leaime mooduli ja kohustuslikkuse
							insert into student_higher_result_replaced_subject(student_higher_result_id,subject_id,curriculum_version_hmodule_id,is_optional)
							values(p_id,rr.subject_id,case when pm_id > 0 then pm_id else null end,pm_is_optional);
							raise notice 's % %', r.record_id, rr.subject_id;
						end loop;*/
					end if;
				--return null;
			end loop;
			x:=upd_student_curriculum_completion(new.student_id);
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

CREATE OR REPLACE FUNCTION public.upd_curriculum_completion_trgr()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
declare 
	i integer;
	r record;
BEGIN
	for r in (select sr.id, sr.apel_application_record_id
            from apel_application app
								 join apel_application_record apr on app.id=apr.apel_application_id
								 join student_higher_result sr on app.student_id=sr.student_id and apr.id=sr.apel_application_record_id
						where app.student_id=new.id and app.is_new=true)
	loop
		i:=upd_apel_replaced_subjects(NEW.id,r.id,r.apel_application_record_id);
	end loop;
	i:=upd_student_curriculum_completion(NEW.ID);
  return null;
end;
$function$
;

DO
$do$
DECLARE
       school integer;
       schools_without_reg_nr integer[] := (select array_agg(s.id) from school s left join school_student_reg_nr ssrn on ssrn.school_id = s.id where ssrn.id is null);
BEGIN
       FOREACH school IN ARRAY schools_without_reg_nr
       LOOP
             insert into school_student_reg_nr(school_id, reg_nr, inserted, inserted_by, version) values(school, 0, now(), 'ESIALGNE', 0);
       END LOOP;
END
$do$;

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
	
	mod_id bigint;

	is_higher_curriculum boolean:=true;
	
	pb_modules_ok boolean:=true;
	p_modules_count integer:=0; --mitu moodulit on ette nähtud hindamiseks
	p_id2 bigint;
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
--
				if p_vcurr_modules[ii] = 0 then
					if p_curr_modules_credits[ii] > 0 then
						-- siin kontrollime, kas moodul on jõuga täidetud; kui jah, siis nn võlga ei tohi tekkida
						if p_curr_modules_ok[ii]=false then
							--raise notice '% ',p_curr_modules_credits[ii];
							p_abs_credits:=p_abs_credits+p_curr_modules_credits[ii];
						end if;
						-- lõputöö/eksam ei lähe arvesse, nende jaoks võla arvutamine jääb endiseks
						if p_curr_modules[ii]=p_fcurr_modules[ii] then
							p_fabs_credits:=p_fabs_credits+p_curr_modules_credits[ii];
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

	--	raise NOTICE 'Fopt: %/%', p_fabs_credits, p_abs_credits;
	--	raise NOTICE 'opt: %/%', p_opt_credits, p_optional;

		--raise notice '% %', p_optional, p_opt_credits;

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




