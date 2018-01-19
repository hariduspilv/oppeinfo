\c hois;

alter table "protocol" add column "is_final" boolean NULL,    -- kas tegemist on lõputöö/lõpueksami protokolliga
											 add column "committee_id" bigint NULL    -- viide komisjonile 
;

update protocol set is_final=false;

alter table protocol alter COLUMN is_final set NOT NULL;

CREATE TABLE "protocol_committee_member"
(
	"id" bigserial NOT NULL,
	"protocol_id" bigint NOT NULL,    -- viide protokollile
	"committee_member_id" bigint NOT NULL,    -- viide liikmele
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL,
	"version" integer NOT NULL
)
;

COMMENT ON COLUMN "protocol"."is_final"	IS 'kas tegemist on lõputöö/lõpueksami protokolliga';
COMMENT ON COLUMN "protocol"."committee_id"	IS 'viide komisjonile';

COMMENT ON TABLE "protocol_committee_member"	IS 'lõpuprotokolli ja komisjoni seos';
COMMENT ON COLUMN "protocol_committee_member"."protocol_id"	IS 'viide protokollile';
COMMENT ON COLUMN "protocol_committee_member"."committee_member_id"	IS 'viide liikmele';

CREATE INDEX "IXFK_protocol_committee" ON "protocol" ("committee_id" ASC);

ALTER TABLE "protocol_committee_member" ADD CONSTRAINT "PK_protocol_committee_member"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_protocol_committee_member_committee_member" ON "protocol_committee_member" ("committee_member_id" ASC);
CREATE INDEX "IXFK_protocol_committee_member_protocol" ON "protocol_committee_member" ("protocol_id" ASC);

ALTER TABLE "protocol" ADD CONSTRAINT "FK_protocol_committee"	FOREIGN KEY ("committee_id") REFERENCES "committee" ("id") ON DELETE No Action ON UPDATE No Action;

ALTER TABLE "protocol_committee_member" ADD CONSTRAINT "FK_protocol_committee_member_committee_member"	FOREIGN KEY ("committee_member_id") REFERENCES "committee_member" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "protocol_committee_member" ADD CONSTRAINT "FK_protocol_committee_member_protocol"	FOREIGN KEY ("protocol_id") REFERENCES "protocol" ("id") ON DELETE No Action ON UPDATE No Action;

delete from user_rights where object_code like 'TEEMAOIGUS__';
-- for student some modification permissions by default
insert into user_role_default (object_code, permission_code, role_code) values ('TEEMAOIGUS_AVALDUS', 'OIGUS_M', 'ROLL_T');
insert into user_role_default (object_code, permission_code, role_code) values ('TEEMAOIGUS_EKSAM', 'OIGUS_M', 'ROLL_T');
insert into user_role_default (object_code, permission_code, role_code) values ('TEEMAOIGUS_LOPTEEMA', 'OIGUS_M', 'ROLL_T');
insert into user_role_default (object_code, permission_code, role_code) values ('TEEMAOIGUS_OPINGUKAVA', 'OIGUS_M', 'ROLL_T');
insert into user_role_default (object_code, permission_code, role_code) values ('TEEMAOIGUS_OPPUR', 'OIGUS_M', 'ROLL_T');
insert into user_role_default (object_code, permission_code, role_code) values ('TEEMAOIGUS_PRAKTIKAPAEVIK', 'OIGUS_M', 'ROLL_T');
insert into user_role_default (object_code, permission_code, role_code) values ('TEEMAOIGUS_TEADE', 'OIGUS_M', 'ROLL_T');
insert into user_role_default (object_code, permission_code, role_code) values ('TEEMAOIGUS_TOEND', 'OIGUS_M', 'ROLL_T');

insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('KASKKIRI_OTOET','OTOET','Õppetoetuse määramine','KASKKIRI',now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('KASKKIRI_OTOETL','OTOETL','Õppetoetuse maksmise lõpetamine','KASKKIRI',now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('KASKKIRI_STIP','STIP','Stipendiumi määramine','KASKKIRI',now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('KASKKIRI_STIPL','STIPL','Stipendiumi maksmise lõpetamine','KASKKIRI',now(),true,true,true,0);
update classifier set is_vocational=false where code like 'KASKKIRI_STIP%';

alter table directive add column scholarship_type_code varchar(100);
COMMENT ON COLUMN "directive"."scholarship_type_code"	IS 'stipend liik, viide klassifikaatorile STIPTOETUS';
CREATE INDEX "IXFK_directive_classifier_04" ON "directive" ("scholarship_type_code" ASC);
ALTER TABLE "directive" ADD CONSTRAINT "FK_directive_classifier_04"	FOREIGN KEY ("scholarship_type_code") REFERENCES "classifier" ("code") ON DELETE No Action ON UPDATE No Action;
alter table directive_student add column bank_account varchar(50);
alter table directive_student add column scholarship_application_id bigint;
alter table directive_student add column amount_paid numeric(6,2);	
comment on column directive_student.bank_account is 'arveldusarve';
comment on column directive_student.scholarship_application_id is 'viide õppetoet/stip taotlusele';
comment on column directive_student.amount_paid is 'makstav summa';
create index "IXFK_directive_student_scholarship_application" on directive_student (scholarship_application_id ASC);
alter table directive_student add constraint "FK_directive_student_scholarship_application" foreign key ("scholarship_application_id") references "scholarship_application" ("id");

delete from classifier where code in ('KASKKIRI_STIP','KASKKIRI_STIPL','KASKKIRI_OTOET','KASKKIRI_OTOETL');

insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('KASKKIRI_STIPTOET','STIPTOET','Stipendiumi/õppetoetuse määramine','KASKKIRI',now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('KASKKIRI_STIPTOETL','STIPTOETL','Stipendiumi/õppetoetuse maksmise lõpetamine','KASKKIRI',now(),true,true,true,0);

insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('KASKKIRI_STIPTOETL_POHJUS','KASKKIRI_STIPTOETL_POHJUS','Stipendiumi/õppetoetuse maksmise lõpetamise põhjus',null,now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('KASKKIRI_STIPTOETL_POHJUS_V','V','Väärandmete esitamine','KASKKIRI_STIPTOETL_POHJUS',now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('KASKKIRI_STIPTOETL_POHJUS_L','L','Maksmise perioodi lõpp','KASKKIRI_STIPTOETL_POHJUS',now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('KASKKIRI_STIPTOETL_POHJUS_M','M','Muu','KASKKIRI_STIPTOETL_POHJUS',now(),true,true,true,0);

alter table scholarship_application add column reject_comment varchar(100);
COMMENT ON COLUMN "scholarship_application"."reject_comment"	IS 'tagasilükkamise/tühistamise põhjus';

alter table timetable_event add column school_id bigint;
COMMENT ON COLUMN "timetable_event"."school_id"	IS 'viide õppeasutusele üksiksündmuste jaoks';
create index "FK_timetable_event_school" on timetable_event (school_id ASC);
alter table timetable_event add constraint "IXFK_timetable_event_school" foreign key ("school_id") references "school" ("id");

update timetable_event te set school_id=(select min(coalesce(bb.school_id,ttt.school_id))
from timetable_event_time tt
	   left join timetable_event_teacher tet on tet.timetable_event_time_id=tt."id"
		 left join teacher ttt on tet.teacher_id=ttt.id
		 left join timetable_event_room ter on ter.timetable_event_time_id=tt.id
		 left join room rr on ter.room_id=rr."id"
		 left JOIN building bb on bb."id"=rr.building_id
where tt.timetable_event_id=te.id and COALESCE(bb."id",ttt.id)>0)
where te.timetable_object_id is null and te.school_id is null;

alter table student_higher_result alter column curriculum_version_hmodule_id DROP NOT NULL;

alter table student_vocational_result add column study_year_id bigint;
COMMENT ON COLUMN "student_vocational_result"."study_year_id"	IS 'viide õppeaastale';
create index "IXFK_student_vocational_result_study_year" on student_vocational_result (study_year_id ASC);
alter table student_vocational_result add constraint "FK_student_vocational_result_study_year" foreign key ("study_year_id") references "study_year" ("id");

alter table student_higher_result add column study_period_id bigint;
COMMENT ON COLUMN "student_higher_result"."study_period_id"	IS 'viide õppeperioodile';
create index "IXFK_student_higher_result_study_period" on student_higher_result (study_period_id ASC);
alter table student_higher_result add constraint "FK_student_higher_result_study_period" foreign key ("study_period_id") references "study_period" ("id");

CREATE OR REPLACE FUNCTION upd_del_apel_result()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
declare
    u_count integer;
		b_count integer;
		r record;
begin
	if tg_op = 'UPDATE' THEN
		if new.status_code='VOTA_STAATUS_C' and OLD.status_code!='VOTA_STAATUS_C' THEN
			--lisame
			if new.is_vocational=true THEN
				null;
			ELSE
				for r in (SELECT subj.id as subject_id,clf.value as grade, --sp.study_period_id,
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
										WHERE ar.apel_application_id=NEW.id and afr.transfer=true
										union
										SELECT subj.id,clf.value as grade, --sp.study_period_id,
										subj.name_et,
										subj.name_en,
										subj.code,
										subj.credits ,air.grade_code,
										null, null, ar.id as record_id,air.is_optional,air.curriculum_version_hmodule_id, null as study_period_id, now()
										from apel_application_record ar
												 join apel_application_informal_subject_or_module air on ar.id=air.apel_application_record_id
												 join subject subj on air.subject_id=subj.id
												 join classifier clf on clf.code=air.grade_code
										WHERE ar.apel_application_id=NEW.id and air.transfer=true)
				LOOP
					insert into student_higher_result (
						student_id,			subject_id,				apel_application_record_id,				grade,				grade_date,				grade_mark,				grade_code,
										apel_school_id,				inserted,
						subject_name_et,				subject_name_en,				teachers,				credits,				subject_code,				is_optional,study_period_id,curriculum_version_hmodule_id) 
					values(
						NEW.student_id,r.subject_id,r.record_id,r.grade,r.grade_date,
						case r.grade when '0' then 0 when '1' then 1 when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else null end,
						r.grade_code,
						r.apel_school_id,--apel_school_id
						now(),
						r.name_et,coalesce(r.name_en,r.name_et),r.teachers,	r.credits,r.code,				r.is_optional,null,r.curriculum_version_hmodule_id); --is_optional
				--return null;
			end loop;
		end if;
	elsif new.status_code!='VOTA_STAATUS_C' and OLD.status_code='VOTA_STAATUS_C' THEN
			--kustutame kõik ära
			if new.is_vocational=true THEN
				delete from student_vocational_result where apel_application_record_id in (select aa.id from apel_application_record aa where aa.apel_application_id=NEW.id);
			ELSE
				delete from student_higher_result where apel_application_record_id in (select aa.id from apel_application_record aa where aa.apel_application_id=NEW.id);
			end if;
		end if;
	end if;
	return null;
end
$function$;

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
			return null;
		end loop;
    for r in (select crm.name_et, crm.name_en, crm.credits, vv.curriculum_version_omodule_id, crm.credits, pers.firstname||' '||pers.lastname as teachers, clf.value as grade,
							  vv.study_year_id
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
			return null;
		end loop;
  end if;
	return null;
end
$function$;


CREATE TRIGGER "apel_application_result" AFTER UPDATE ON "public"."apel_application"
FOR EACH ROW
EXECUTE PROCEDURE "upd_del_apel_result"();

CREATE TRIGGER "protocol_student_result" AFTER INSERT OR UPDATE OR DELETE ON "public"."protocol_student"
FOR EACH ROW
EXECUTE PROCEDURE "ins_upd_del_result"();

create unique index IXUQ_curriculum_version_omodule on curriculum_version_omodule (curriculum_module_id,curriculum_version_id);

insert into user_role_default (object_code,permission_code,role_code)
select object_code, 'OIGUS_M', role_code from user_role_default where role_code='ROLL_A';

update curriculum c
set inserted_by=(select p.firstname || ' ' || p.lastname || ' (' || p.idcode || ')' from person p where p.idcode=c.inserted_by)
where c.inserted_by in (select p.idcode from person p where p.idcode=c.inserted_by);

update curriculum c
set changed_by=(select p.firstname || ' ' || p.lastname || ' (' || p.idcode || ')' from person p where p.idcode=c.changed_by)
where c.changed_by in (select p.idcode from person p where p.idcode=c.changed_by);

update state_curriculum c
set inserted_by=(select p.firstname || ' ' || p.lastname || ' (' || p.idcode || ')' from person p where p.idcode=c.inserted_by)
where c.inserted_by in (select p.idcode from person p where p.idcode=c.inserted_by);

update state_curriculum c
set changed_by=(select p.firstname || ' ' || p.lastname || ' (' || p.idcode || ')' from person p where p.idcode=c.changed_by)
where c.changed_by in (select p.idcode from person p where p.idcode=c.changed_by);

update curriculum_version c
set inserted_by=(select p.firstname || ' ' || p.lastname || ' (' || p.idcode || ')' from person p where p.idcode=c.inserted_by)
where c.inserted_by in (select p.idcode from person p where p.idcode=c.inserted_by);

update curriculum_version c
set changed_by=(select p.firstname || ' ' || p.lastname || ' (' || p.idcode || ')' from person p where p.idcode=c.changed_by)
where c.changed_by in (select p.idcode from person p where p.idcode=c.changed_by);

insert into user_rights(useR_id,permission_code,object_code,inserted,version,inserted_by)
values (1,'OIGUS_M','TEEMAOIGUS_KASUTAJA',now(),0,'Automaat');