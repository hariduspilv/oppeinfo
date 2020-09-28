\c hois

CREATE TABLE "journal_sub"
(
	"id" bigserial NOT NULL,
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL,
	"sub_journals" integer NOT NULL    -- osarühma päevikute arv
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "journal_sub"	IS 'osarühmade päevikud';
COMMENT ON COLUMN "journal_sub"."sub_journals"	IS 'osarühma päevikute arv';
/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE "journal_sub" ADD CONSTRAINT "PK_journal_sub"	PRIMARY KEY ("id");

alter table journal add column journal_sub_id bigint;
create index IXFK_journal_journal_sub on journal(journal_sub_id);
alter table journal add constraint FK_journal_journal_sub foreign key(journal_sub_id) references journal_sub(id);

INSERT INTO "public"."classifier" ("code", "value", "value2", "name_et", "name_en", "name_ru", "parent_class_code", "main_class_code", "inserted", "changed", "valid", "description", "valid_from", "valid_thru", "extraval1", "extraval2", "ehis_value", "is_vocational", "is_higher", "version", "inserted_by", "changed_by") 
VALUES ('KASKKIRI_EKSTERN', 'EKSTERN', NULL, 'Eksterniks vormistamine', NULL, NULL, NULL, 'KASKKIRI', current_timestamp(3), NULL, 't', NULL, NULL, NULL, NULL, NULL, NULL, 't', 't', '0', NULL, NULL);

INSERT INTO "public"."classifier" ("code", "value", "value2", "name_et", "name_en", "name_ru", "parent_class_code", "main_class_code", "inserted", "changed", "valid", "description", "valid_from", "valid_thru", "extraval1", "extraval2", "ehis_value", "is_vocational", "is_higher", "version", "inserted_by", "changed_by") 
VALUES ('OPPUR_E', 'E', NULL, 'Ekstern', NULL, NULL, NULL, 'OPPUR', current_timestamp(3), NULL, 't', NULL, NULL, NULL, NULL, NULL, NULL, 't', 't', '0', NULL, NULL);

INSERT INTO "public"."classifier" ("code", "value", "value2", "name_et", "name_en", "name_ru", "parent_class_code", "main_class_code", "inserted", "changed", "valid", "description", "valid_from", "valid_thru", "extraval1", "extraval2", "ehis_value", "is_vocational", "is_higher", "version", "inserted_by", "changed_by") 
VALUES ('KASKKIRI_EKSTERNKATK', 'EKSTERNKATK', NULL, 'Eksterni välja arvamine', NULL, NULL, NULL, 'KASKKIRI', current_timestamp(3), NULL, 't', NULL, NULL, NULL, NULL, NULL, NULL, 't', 't', '0', NULL, NULL);

insert into classifier(code,value,name_et,main_class_code,inserted,valid, is_vocational,is_higher, version) values('HARIDUSTASE_G','G','Keskharidusõpe','HARIDUSTASE',current_timestamp(3),false,false,false,1);
insert into classifier(code,value,name_et,main_class_code,inserted,valid, is_vocational,is_higher, version) values('HARIDUSTASE_PG','PG','Põhiharidusõpe','HARIDUSTASE',current_timestamp(3),false,false,false,1);
insert into classifier_connect(classifier_code,connect_classifier_code,inserted,version,main_classifier_code) values('OPPEASTE_310','HARIDUSTASE_G',current_timestamp(3),0,'HARIDUSTASE');
insert into classifier_connect(classifier_code,connect_classifier_code,inserted,version,main_classifier_code) values('OPPEASTE_210','HARIDUSTASE_PG',current_timestamp(3),0,'HARIDUSTASE');

/* Create Tables */

CREATE TABLE "grading_schema"
(
	"id" bigserial NOT NULL,
	"school_id" bigint NOT NULL,
	"inserted" timestamp without time zone NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"version" integer NOT NULL,
	"changed" timestamp without time zone NULL,
	"changed_by" varchar(100)	 NULL,
	"is_vocational" boolean NOT NULL,    -- kas kehtib kutseõppe jaoks
	"is_higher" boolean NOT NULL,    -- kas kehtib kõrgh jaoks
	"is_basic" boolean NOT NULL,    -- kas kehtib põhihariduse jaoks
	"is_secondary" boolean NOT NULL,    -- kas kehtib üldõppeh jaoks
	"is_verbal" boolean NOT NULL,    -- kas sõnaline hindamine on lubatud
	"is_grade" boolean NOT NULL,    -- kas kuvada hinne sõnalise hindamise puhul
	"is_valid" boolean NOT NULL    -- kas hindamissüsteem on kehtiv
)
;

CREATE TABLE "grading_schema_row"
(
	"id" bigserial NOT NULL ,
	"grading_schema_id" bigint NOT NULL,    -- viide hindamissüsteemile
	"grade" varchar(100)	 NOT NULL,    -- hinne (number või sõna)
	"grade_real_code" varchar(100)	 NOT NULL,    -- reaalse hinde vastavus, viide klassifikaatorile KORGHINDAMINE või KUTSEHINDAMINE
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL,
	"version" integer NOT NULL,
	"is_valid" boolean NOT NULL    -- kas vastav rida on kehtiv
)
;

CREATE TABLE "grading_schema_valid"
(
	"id" bigserial NOT NULL ,
	"grading_schema_id" bigint NOT NULL,
	"valid_from" date NOT NULL,
	"valid_thru" date NULL,
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "grading_schema"	IS 'hindamissüsteem';
COMMENT ON COLUMN "grading_schema"."is_vocational"	IS 'kas kehtib kutseõppe jaoks';
COMMENT ON COLUMN "grading_schema"."is_higher"	IS 'kas kehtib kõrgh jaoks';
COMMENT ON COLUMN "grading_schema"."is_basic"	IS 'kas kehtib põhihariduse jaoks';
COMMENT ON COLUMN "grading_schema"."is_secondary"	IS 'kas kehtib üldõppeh jaoks';
COMMENT ON COLUMN "grading_schema"."is_verbal"	IS 'kas sõnaline hindamine on lubatud';
COMMENT ON COLUMN "grading_schema"."is_grade"	IS 'kas kuvada hinne sõnalise hindamise puhul';

COMMENT ON COLUMN "grading_schema"."is_valid"	IS 'kas hindamissüsteem on kehtiv';

COMMENT ON TABLE "grading_schema_row"	IS 'hindamissüsteemi hinded';
COMMENT ON COLUMN "grading_schema_row"."grading_schema_id"	IS 'viide hindamissüsteemile';
COMMENT ON COLUMN "grading_schema_row"."grade"	IS 'hinne (number või sõna)';
COMMENT ON COLUMN "grading_schema_row"."grade_real_code"	IS 'reaalse hinde vastavus, viide klassifikaatorile KORGHINDAMINE või KUTSEHINDAMINE';
COMMENT ON COLUMN "grading_schema_row"."is_valid"	IS 'kas vastav rida on kehtiv';
COMMENT ON TABLE "grading_schema_valid"	IS 'hindamissüsteemi kehtivusajad';

ALTER TABLE "grading_schema" ADD CONSTRAINT "PK_grading_schema"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_grading_schema_school" ON "grading_schema" ("school_id" ASC);
ALTER TABLE "grading_schema_row" ADD CONSTRAINT "PK_grading_schema_row"	PRIMARY KEY ("id");

CREATE INDEX "IXFK_grading_schema_row_classifier" ON "grading_schema_row" ("grade_real_code" ASC);

CREATE INDEX "IXFK_grading_schema_row_grading_schema" ON "grading_schema_row" ("grading_schema_id" ASC);

ALTER TABLE "grading_schema_valid" ADD CONSTRAINT "PK_grading_schema_valid"	PRIMARY KEY ("id");

CREATE INDEX "IXFK_grading_schema_valid_grading_schema" ON "grading_schema_valid" ("grading_schema_id" ASC);

/* Create Foreign Key Constraints */

ALTER TABLE "grading_schema" ADD CONSTRAINT "FK_grading_schema_school"	FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "grading_schema_row" ADD CONSTRAINT "FK_grading_schema_row_classifier"	FOREIGN KEY ("grade_real_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "grading_schema_row" ADD CONSTRAINT "FK_grading_schema_row_grading_schema"	FOREIGN KEY ("grading_schema_id") REFERENCES "grading_schema" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "grading_schema_valid" ADD CONSTRAINT "FK_grading_schema_valid_grading_schema"	FOREIGN KEY ("grading_schema_id") REFERENCES "grading_schema" ("id") ON DELETE No Action ON UPDATE No Action
;

update classifier_connect set connect_classifier_code='HARIDUSTASE_X' where connect_classifier_code='HARIDUSTASE_B' and classifier_code = 'OPPEASTE_409';
delete from classifier_connect where connect_classifier_code='HARIDUSTASE_B' and classifier_code = 'OPPEASTE_410'; 

alter TABLE midterm_task_student_result
	add column grading_schema_row_id bigint NULL,    -- viide hindamissüsteemi tulemusele
	add column verbal_grade varchar(4000)	 NULL    -- sõnaline hinne
;

alter TABLE journal_entry_student
	add column grading_schema_row_id bigint NULL,
	add column verbal_grade varchar(4000)	 NULL    -- sõnaline hinne
;

alter TABLE journal_entry_student_history
	add column grading_schema_row_id bigint NULL,
	add column verbal_grade varchar(4000)	 NULL    -- sõnaline hinne
;

alter TABLE student_curriculum_module_outcomes_result
	add column grading_schema_row_id bigint NULL,
	add column verbal_grade varchar(4000)	 NULL    -- sõnaline hinne
;

alter TABLE student_curriculum_module_outcomes_result_history
	add column grading_schema_row_id bigint NULL,    -- viide kooli hindamisskeema hindele
	add column verbal_grade varchar(4000)	 NULL    -- sõnaline hinne
;

alter TABLE midterm_task
	add column is_grade_schema boolean NULL    -- kas hinnete sisestamisel kuvada hinnetesüsteemi
;

alter TABLE protocol_student
	add column grading_schema_row_id bigint NULL
;

alter TABLE protocol_student_history
	add column grading_schema_row_id bigint NULL
;

COMMENT ON COLUMN "midterm_task_student_result"."verbal_grade"	IS 'sõnaline hinne';
COMMENT ON COLUMN "journal_entry_student"."verbal_grade"	IS 'sõnaline hinne';
COMMENT ON COLUMN "journal_entry_student_history"."verbal_grade"	IS 'sõnaline hinne';
COMMENT ON COLUMN "student_curriculum_module_outcomes_result"."verbal_grade"	IS 'sõnaline hinne';
COMMENT ON COLUMN "student_curriculum_module_outcomes_result_history"."verbal_grade"	IS 'sõnaline hinne';
COMMENT ON COLUMN "midterm_task_student_result"."grading_schema_row_id"	IS 'viide hindamissüsteemi tulemusele';
COMMENT ON COLUMN "student_curriculum_module_outcomes_result_history"."grading_schema_row_id"	IS 'viide kooli hindamisskeema hindele';
CREATE INDEX "IXFK_midterm_task_student_result_grading_schema_row" ON "midterm_task_student_result" ("grading_schema_row_id" ASC);
CREATE INDEX "IXFK_journal_entry_student_grading_schema_row" ON "journal_entry_student" ("grading_schema_row_id" ASC);
CREATE INDEX "IXFK_journal_entry_student_history_grading_schema_row" ON "journal_entry_student_history" ("grading_schema_row_id" ASC);
CREATE INDEX "IXFK_student_curriculum_module_outcomes_result_grading_schema_row" ON "student_curriculum_module_outcomes_result" ("grading_schema_row_id" ASC);
CREATE INDEX "IXFK_student_curriculum_module_outcomes_result_history_grading_schema_row" ON "student_curriculum_module_outcomes_result_history" ("grading_schema_row_id" ASC);
CREATE INDEX "IXFK_protocol_student_grading_schema_row" ON "protocol_student" ("grading_schema_row_id" ASC);
CREATE INDEX "IXFK_protocol_student_history_grading_schema_row" ON "protocol_student_history" ("grading_schema_row_id" ASC);
ALTER TABLE "midterm_task_student_result" ADD CONSTRAINT "FK_midterm_task_student_result_grading_schema_row"	FOREIGN KEY ("grading_schema_row_id") REFERENCES "grading_schema_row" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "journal_entry_student" ADD CONSTRAINT "FK_journal_entry_student_grading_schema_row"	FOREIGN KEY ("grading_schema_row_id") REFERENCES "grading_schema_row" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "journal_entry_student_history" ADD CONSTRAINT "FK_journal_entry_student_history_grading_schema_row"	FOREIGN KEY ("grading_schema_row_id") REFERENCES "grading_schema_row" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "student_curriculum_module_outcomes_result" ADD CONSTRAINT "FK_student_curriculum_module_outcomes_result_grading_schema_row"	FOREIGN KEY ("grading_schema_row_id") REFERENCES "grading_schema_row" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "student_curriculum_module_outcomes_result_history" ADD CONSTRAINT "FK_student_curriculum_module_outcomes_result_history_grading_schema_row"	FOREIGN KEY ("grading_schema_row_id") REFERENCES "grading_schema_row" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "protocol_student" ADD CONSTRAINT "FK_protocol_student_grading_schema_row"	FOREIGN KEY ("grading_schema_row_id") REFERENCES "grading_schema_row" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "protocol_student_history" ADD CONSTRAINT "FK_protocol_student_history_grading_schema_row"	FOREIGN KEY ("grading_schema_row_id") REFERENCES "grading_schema_row" ("id") ON DELETE No Action ON UPDATE No Action;

do $$
declare
	r record;
	rr record;
	p_id	bigint:=0;
begin
	for r in (select count(*), is_higher, school_id, start_date
				from timetable
				group by is_higher, school_id, start_date
				having count(*) > 1)
	loop
		p_id:=0;
		for rr in (select id from timetable where is_higher=r.is_higher and school_id=r.school_id and start_date=r.start_date order by id)
		loop
			if p_id=0 then
				p_id:=rr.id;
			else
				update timetable_object set timetable_id=p_id where timetable_id=rr.id;
				delete from timetable where id=rr.id;
			end if;
		end loop;
	end loop;
end;
$$;

CREATE UNIQUE INDEX if not exists timetable_UQ ON timetable(is_higher,school_id,start_date);
CREATE UNIQUE INDEX grading_schema_UQ ON grading_schema((CASE WHEN is_valid=true THEN school_id::varchar||is_higher::varchar||is_vocational::varchar||is_basic::varchar||is_secondary::varchar||is_valid::varchar ELSE NULL END));

insert into classifier (code, value, name_et, main_class_code, inserted, "valid", is_vocational, is_higher, "version") values ('TEEMAOIGUS_HINDAMISSYSTEEM', 'HINDAMISSYSTEEM', 'Hindamissüsteemid', 'TEEMAOIGUS', now(), true, true, true, 0);
insert into user_role_default (object_code, permission_code, role_code) values
('TEEMAOIGUS_HINDAMISSYSTEEM', 'OIGUS_V', 'ROLL_A');

CREATE TABLE "grading_schema_study_year"
(
	"id" bigserial NOT NULL,
	"grading_schema_id" bigint NOT NULL,
	"inserted" timestamp without time zone NOT NULL,
	"study_year_id" bigint NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "grading_schema_study_year"	IS 'hindamissüsteem ja õppeaasta';
/* Create Primary Keys, Indexes, Uniques, Checks */
ALTER TABLE "grading_schema_study_year" ADD CONSTRAINT "PK_grading_schema_study_year"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_grading_schema_study_year_grading_schema" ON "grading_schema_study_year" ("grading_schema_id" ASC);
CREATE INDEX "IXFK_grading_schema_study_year_study_year" ON "grading_schema_study_year" ("study_year_id" ASC);

/* Create Foreign Key Constraints */

ALTER TABLE "grading_schema_study_year" ADD CONSTRAINT "FK_grading_schema_study_year_grading_schema"	FOREIGN KEY ("grading_schema_id") REFERENCES "grading_schema" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "grading_schema_study_year" ADD CONSTRAINT "FK_grading_schema_study_year_study_year"	FOREIGN KEY ("study_year_id") REFERENCES "public"."study_year" ("id") ON DELETE No Action ON UPDATE No Action;
drop index grading_schema_UQ;
drop table grading_schema_valid;
alter table grading_schema drop column is_valid;

alter table student add column job_occupation varchar(100);
comment on column student.job_occupation is 'ametikoht';
alter table student add column job varchar(200);
comment on column student.job is 'töökoht';
alter table student add column is_acad_study_allowed boolean;
comment on column student.is_acad_study_allowed is 'kas akadeemilisel puhkusel õppimine lubatud';
alter table student add column add_info text;
comment on column student.add_info is 'muu info';
alter table student add column other_contact varchar(300);
comment on column student.other_contact is 'muu kontakt';
alter table student add column representative_other_contact varchar(300);
comment on column student.representative_other_contact is 'täiendavad kontaktid';

alter table grading_schema_row add column grade_en varchar(100);
comment on column grading_schema_row.grade_en is 'hinne (number või sõna) i.k.';

/* Create Tables */

CREATE TABLE "student_languages"
(
	"id" bigserial NOT NULL,
	"student_id" bigint NOT NULL,    -- viide õppurile
	"foreign_lang_code" varchar(100)	 NOT NULL,    -- EHISe võõrkeel, viide klassifikaatorile EHIS_VOORKEEL
	"foreign_lang_type_code" varchar(100)	 NOT NULL,    -- võõrkeele liik, viide klassifikaatorile VOORKEEL_TYYP
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */
COMMENT ON COLUMN "student_languages"."student_id"	IS 'viide õppurile';
COMMENT ON COLUMN "student_languages"."foreign_lang_code"	IS 'EHISe võõrkeel, viide klassifikaatorile EHIS_VOORKEEL';
COMMENT ON COLUMN "student_languages"."foreign_lang_type_code"	IS 'võõrkeele liik, viide klassifikaatorile VOORKEEL_TYYP';

ALTER TABLE "student_languages" ADD CONSTRAINT "PK_student_languages"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_student_languages_classifier" ON "student_languages" ("foreign_lang_code" ASC);
CREATE INDEX "IXFK_student_languages_classifier_02" ON "student_languages" ("foreign_lang_type_code" ASC);
CREATE INDEX "IXFK_student_languages_student" ON "student_languages" ("student_id" ASC);

/* Create Foreign Key Constraints */

ALTER TABLE "student_languages" ADD CONSTRAINT "FK_student_languages_classifier"	FOREIGN KEY ("foreign_lang_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "student_languages" ADD CONSTRAINT "FK_student_languages_classifier_02"	FOREIGN KEY ("foreign_lang_type_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "student_languages" ADD CONSTRAINT "FK_student_languages_student"	FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE No Action ON UPDATE No Action;


INSERT INTO "classifier" ("code", "value", "value2", "name_et", "name_en", "name_ru", "parent_class_code", "main_class_code", "inserted", "changed", "valid", "description", "valid_from", "valid_thru", "extraval1", "extraval2", "ehis_value", "is_vocational", "is_higher", "version", "inserted_by", "changed_by") 
VALUES ('EHIS_VOORKEEL', 'EHIS_VOORKEEL', NULL, 'EHISe õppuri võõrkeeled', NULL, NULL, NULL, NULL, '2017-01-26 08:55:06.091289', NULL, 't', NULL, NULL, NULL, NULL, NULL, NULL, 't', 't', '0', NULL, NULL);
INSERT INTO "classifier" ("code", "value", "value2", "name_et", "name_en", "name_ru", "parent_class_code", "main_class_code", "inserted", "changed", "valid", "description", "valid_from", "valid_thru", "extraval1", "extraval2", "ehis_value", "is_vocational", "is_higher", "version", "inserted_by", "changed_by") 
VALUES ('EHIS_VOORKEEL_D', 'D', NULL, 'saksa keel', NULL, NULL, NULL, 'EHIS_VOORKEEL', '2017-01-26 09:09:19.496648', NULL, 't', NULL, NULL, NULL, NULL, NULL, 'D', 't', 'f', '0', NULL, NULL);
INSERT INTO "classifier" ("code", "value", "value2", "name_et", "name_en", "name_ru", "parent_class_code", "main_class_code", "inserted", "changed", "valid", "description", "valid_from", "valid_thru", "extraval1", "extraval2", "ehis_value", "is_vocational", "is_higher", "version", "inserted_by", "changed_by") 
VALUES ('EHIS_VOORKEEL_Y', 'Y', NULL, 'itaalia keel', NULL, NULL, NULL, 'EHIS_VOORKEEL', '2017-01-26 09:09:19.496648', NULL, 't', NULL, NULL, NULL, NULL, NULL, 'Y', 't', 'f', '0', NULL, NULL);
INSERT INTO "classifier" ("code", "value", "value2", "name_et", "name_en", "name_ru", "parent_class_code", "main_class_code", "inserted", "changed", "valid", "description", "valid_from", "valid_thru", "extraval1", "extraval2", "ehis_value", "is_vocational", "is_higher", "version", "inserted_by", "changed_by") 
VALUES ('EHIS_VOORKEEL_V', 'V', NULL, 'vene keel', NULL, NULL, NULL, 'EHIS_VOORKEEL', '2017-01-26 09:09:19.496648', NULL, 't', NULL, NULL, NULL, NULL, NULL, 'V', 't', 'f', '0', NULL, NULL);
INSERT INTO "classifier" ("code", "value", "value2", "name_et", "name_en", "name_ru", "parent_class_code", "main_class_code", "inserted", "changed", "valid", "description", "valid_from", "valid_thru", "extraval1", "extraval2", "ehis_value", "is_vocational", "is_higher", "version", "inserted_by", "changed_by") 
VALUES ('EHIS_VOORKEEL_S', 'S', NULL, 'soome keel', NULL, NULL, NULL, 'EHIS_VOORKEEL', '2017-01-26 09:09:19.496648', NULL, 't', NULL, NULL, NULL, NULL, NULL, 'S', 't', 'f', '0', NULL, NULL);
INSERT INTO "classifier" ("code", "value", "value2", "name_et", "name_en", "name_ru", "parent_class_code", "main_class_code", "inserted", "changed", "valid", "description", "valid_from", "valid_thru", "extraval1", "extraval2", "ehis_value", "is_vocational", "is_higher", "version", "inserted_by", "changed_by") 
VALUES ('EHIS_VOORKEEL_R', 'R', NULL, 'rootsi keel', NULL, NULL, NULL, 'EHIS_VOORKEEL', '2017-01-26 09:09:19.496648', NULL, 't', NULL, NULL, NULL, NULL, NULL, 'R', 't', 'f', '0', NULL, NULL);
INSERT INTO "classifier" ("code", "value", "value2", "name_et", "name_en", "name_ru", "parent_class_code", "main_class_code", "inserted", "changed", "valid", "description", "valid_from", "valid_thru", "extraval1", "extraval2", "ehis_value", "is_vocational", "is_higher", "version", "inserted_by", "changed_by") 
VALUES ('EHIS_VOORKEEL_P', 'P', NULL, 'prantsuse keel', NULL, NULL, NULL, 'EHIS_VOORKEEL', '2017-01-26 09:09:19.496648', NULL, 't', NULL, NULL, NULL, NULL, NULL, 'P', 't', 'f', '0', NULL, NULL);
INSERT INTO "classifier" ("code", "value", "value2", "name_et", "name_en", "name_ru", "parent_class_code", "main_class_code", "inserted", "changed", "valid", "description", "valid_from", "valid_thru", "extraval1", "extraval2", "ehis_value", "is_vocational", "is_higher", "version", "inserted_by", "changed_by") 
VALUES ('EHIS_VOORKEEL_I', 'I', NULL, 'inglise keel', NULL, NULL, NULL, 'EHIS_VOORKEEL', '2017-01-26 09:09:19.496648', NULL, 't', NULL, NULL, NULL, NULL, NULL, 'I', 't', 'f', '0', NULL, NULL);
INSERT INTO "classifier" ("code", "value", "value2", "name_et", "name_en", "name_ru", "parent_class_code", "main_class_code", "inserted", "changed", "valid", "description", "valid_from", "valid_thru", "extraval1", "extraval2", "ehis_value", "is_vocational", "is_higher", "version", "inserted_by", "changed_by") 
VALUES ('EHIS_VOORKEEL_E2', 'E2', NULL, 'Eesti keel teise keelena', NULL, NULL, NULL, 'EHIS_VOORKEEL', '2017-01-26 09:09:19.496648', NULL, 't', NULL, NULL, NULL, NULL, NULL, 'E2', 't', 'f', '0', NULL, NULL);
INSERT INTO "classifier" ("code", "value", "value2", "name_et", "name_en", "name_ru", "parent_class_code", "main_class_code", "inserted", "changed", "valid", "description", "valid_from", "valid_thru", "extraval1", "extraval2", "ehis_value", "is_vocational", "is_higher", "version", "inserted_by", "changed_by") 
VALUES ('EHIS_VOORKEEL_E', 'E', NULL, 'eesti keel', NULL, NULL, NULL, 'EHIS_VOORKEEL', '2017-01-26 09:09:19.496648', NULL, 't', NULL, NULL, NULL, NULL, NULL, 'E', 't', 'f', '0', NULL, NULL);


INSERT INTO "classifier" ("code", "value", "value2", "name_et", "name_en", "name_ru", "parent_class_code", "main_class_code", "inserted", "changed", "valid", "description", "valid_from", "valid_thru", "extraval1", "extraval2", "ehis_value", "is_vocational", "is_higher", "version", "inserted_by", "changed_by") 
VALUES ('VOORKEEL_TYYP', 'VOORKEEL_TYYP', NULL, 'Võõrkeele tüübid (A, B, C, D)', NULL, NULL, NULL, NULL, '2017-01-26 08:55:06.091289', NULL, 't', NULL, NULL, NULL, NULL, NULL, NULL, 't', 't', '0', NULL, NULL);
INSERT INTO "classifier" ("code", "value", "value2", "name_et", "name_en", "name_ru", "parent_class_code", "main_class_code", "inserted", "changed", "valid", "description", "valid_from", "valid_thru", "extraval1", "extraval2", "ehis_value", "is_vocational", "is_higher", "version", "inserted_by", "changed_by") 
VALUES ('VOORKEEL_TYYP_A', 'A', NULL, 'A-võõrkeel', NULL, NULL, NULL, 'VOORKEEL_TYYP', '2017-01-26 09:09:19.496648', NULL, 't', NULL, NULL, NULL, NULL, NULL, null, 't', 'f', '0', NULL, NULL);
INSERT INTO "classifier" ("code", "value", "value2", "name_et", "name_en", "name_ru", "parent_class_code", "main_class_code", "inserted", "changed", "valid", "description", "valid_from", "valid_thru", "extraval1", "extraval2", "ehis_value", "is_vocational", "is_higher", "version", "inserted_by", "changed_by") 
VALUES ('VOORKEEL_TYYP_B', 'B', NULL, 'B-võõrkeel', NULL, NULL, NULL, 'VOORKEEL_TYYP', '2017-01-26 09:09:19.496648', NULL, 't', NULL, NULL, NULL, NULL, NULL, null, 't', 'f', '0', NULL, NULL);
INSERT INTO "classifier" ("code", "value", "value2", "name_et", "name_en", "name_ru", "parent_class_code", "main_class_code", "inserted", "changed", "valid", "description", "valid_from", "valid_thru", "extraval1", "extraval2", "ehis_value", "is_vocational", "is_higher", "version", "inserted_by", "changed_by") 
VALUES ('VOORKEEL_TYYP_C', 'C', NULL, 'C-võõrkeel', NULL, NULL, NULL, 'VOORKEEL_TYYP', '2017-01-26 09:09:19.496648', NULL, 't', NULL, NULL, NULL, NULL, NULL, null, 't', 'f', '0', NULL, NULL);
INSERT INTO "classifier" ("code", "value", "value2", "name_et", "name_en", "name_ru", "parent_class_code", "main_class_code", "inserted", "changed", "valid", "description", "valid_from", "valid_thru", "extraval1", "extraval2", "ehis_value", "is_vocational", "is_higher", "version", "inserted_by", "changed_by") 
VALUES ('VOORKEEL_TYYP_D', 'D', NULL, 'D-võõrkeel', NULL, NULL, NULL, 'VOORKEEL_TYYP', '2017-01-26 09:09:19.496648', NULL, 't', NULL, NULL, NULL, NULL, NULL, null, 't', 'f', '0', NULL, NULL);


CREATE OR REPLACE FUNCTION public.upd_student_curriculum_completion(p_id bigint)
 RETURNS integer
 LANGUAGE plpgsql
AS $function$
declare 
  pb_exist boolean:=false;
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
		for r in (select coalesce(svm.curriculum_version_hmodule_id,sv.curriculum_version_hmodule_id) as curriculum_version_hmodule_id,
										 coalesce(svm.is_optional,sv.is_optional) is_optional,
										sv.grade_code, sv.credits,sv.subject_id, sv.grade_mark, sv.is_module, sv.grade_date
							from student_higher_result sv
									 left join student_higher_result_module svm on sv.id=svm.student_higher_result_id
							where sv.student_id=p_id and sv.is_module=false and sv.is_active=true and sv.grade_code in ('KORGHINDAMINE_1','KORGHINDAMINE_2','KORGHINDAMINE_3','KORGHINDAMINE_4','KORGHINDAMINE_5','KORGHINDAMINE_A') /*and sv.arr_modules is null*/
							union all
							select sv.curriculum_version_hmodule_id as curriculum_version_hmodule_id,
										 false is_optional,
										sv.grade_code, sv.credits,sv.subject_id, sv.grade_mark, sv.is_module, sv.grade_date
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
								if r.grade_code in ('KORGHINDAMINE_1','KORGHINDAMINE_2','KORGHINDAMINE_3','KORGHINDAMINE_4','KORGHINDAMINE_5') THEN
									p_avg_total_credits:=p_avg_total_credits+r.credits;
									p_avg_credits:=p_avg_credits+r.credits*(r.grade_mark::int);
								end if;
							end if;
						end loop;
			end if;
			pb_exist:=false;
			if r.is_module=false then
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

					p_total_credits:=p_total_credits+r.credits;
					if r.grade_code in ('KORGHINDAMINE_1','KORGHINDAMINE_2','KORGHINDAMINE_3','KORGHINDAMINE_4','KORGHINDAMINE_5') THEN
						p_avg_total_credits:=p_avg_total_credits+r.credits;
						p_avg_credits:=p_avg_credits+r.credits*(r.grade_mark::int);
					end if;
					if array_length(p_curr_modules,1) > 0 and r.curriculum_version_hmodule_id is not null then
						pb_exist:=false;
						for ii in 1..array_length(p_curr_modules,1)
						loop
							if p_curr_modules[ii]=coalesce(r.curriculum_version_hmodule_id,0) then
								if coalesce(r.is_optional,false)=true then
									p_curr_modules_opt_credits[ii]:=p_curr_modules_opt_credits[ii]-r.credits;
									if p_curr_modules[ii]=p_vcurr_modules[ii] then
										--raise notice 'SIIN: ';
										p_opt_credits:=p_opt_credits+r.credits;
									end if;
									--raise notice '%', p_curr_modules_opt_credits[ii];
								else
									p_curr_modules_credits[ii]:=p_curr_modules_credits[ii]-r.credits;
									--raise notice '-%', p_curr_modules_credits[ii];
								end if;
								pb_exist:=true;
							end if;
						end loop;
						--valikõpingud
						if not pb_exist then
							p_opt_credits:=p_opt_credits+r.credits;
						end if;
					else
						if not pb_exist then
							p_opt_credits:=p_opt_credits+r.credits;
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
							p_abs_credits:=p_abs_credits+p_curr_modules_opt_credits[ii];
						end if;
					else
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


alter table school_capacity_type add is_contact boolean;
comment on column school_capacity_type.is_contact is 'kas on kontakttunnid';
update school_capacity_type set is_contact=true where capacity_type_code!='MAHT_i';
update school_capacity_type set is_contact=false where capacity_type_code='MAHT_i';
alter table school_capacity_type alter column is_contact set not null;

alter table school add ekis_url varchar(4000);
comment on column school.ekis_url is 'Personaalse EKISe url';

update school_capacity_type set is_contact=false where is_usable=false;

alter TABLE practice_journal
	add column grading_schema_row_id bigint NULL
;

alter TABLE practice_journal_evaluation
	add column grading_schema_row_id bigint NULL
;

COMMENT ON COLUMN "practice_journal"."grading_schema_row_id"	IS 'viide hindamissüsteemi tulemusele';
COMMENT ON COLUMN "practice_journal_evaluation"."grading_schema_row_id"	IS 'viide hindamissüsteemi tulemusele';

CREATE INDEX "IXFK_practice_journal_grading_schema_row" ON "practice_journal" ("grading_schema_row_id" ASC);
CREATE INDEX "IXFK_practice_journal_evaluation_grading_schema_row" ON "practice_journal_evaluation" ("grading_schema_row_id" ASC);

ALTER TABLE "practice_journal" ADD CONSTRAINT "FK_practice_journal_grading_schema_row"	FOREIGN KEY ("grading_schema_row_id") REFERENCES "grading_schema_row" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "practice_journal_evaluation" ADD CONSTRAINT "FK_practice_journal_evaluation_grading_schema_row"	FOREIGN KEY ("grading_schema_row_id") REFERENCES "grading_schema_row" ("id") ON DELETE No Action ON UPDATE No Action;

create trigger grading_schema_audit after insert or delete or update on grading_schema for each row execute procedure hois_audit();
create trigger grading_schema_row_audit after insert or delete or update on grading_schema_row for each row execute procedure hois_audit();
create trigger grading_schema_study_year_audit after insert or delete or update on grading_schema_study_year for each row execute procedure hois_audit();
create trigger journal_sub_audit after insert or delete or update on journal_sub for each row execute procedure hois_audit();
create trigger student_languages_audit after insert or delete or update on student_languages for each row execute procedure hois_audit();


INSERT INTO "public"."classifier" ("code", "value", "value2", "name_et", "name_en", "name_ru", "parent_class_code", "main_class_code", "inserted", "changed", "valid", "description", "valid_from", "valid_thru", "extraval1", "extraval2", "ehis_value", "is_vocational", "is_higher", "version", "inserted_by", "changed_by") 
VALUES ('JOB_KEEL_EHIS', 'KEEL_EHIS', NULL, 'EHISesse andmete saatmine - võõrkeeled', NULL, NULL, NULL, 'JOB', '2017-08-25 09:53:19.165325', NULL, 't', NULL, NULL, NULL, NULL, NULL, NULL, 't', 't', '0', NULL, NULL);

insert into classifier_connect(classifier_code,connect_classifier_code,inserted,version,main_classifier_code) values('OPPEASTE_215','HARIDUSTASE_X',current_timestamp(3),0,'HARIDUSTASE') ON CONFLICT DO NOTHING;
insert into classifier_connect(classifier_code,connect_classifier_code,inserted,version,main_classifier_code) values('OPPEASTE_408','HARIDUSTASE_X',current_timestamp(3),0,'HARIDUSTASE') ON CONFLICT DO NOTHING;
insert into classifier_connect(classifier_code,connect_classifier_code,inserted,version,main_classifier_code) values('OPPEASTE_409','HARIDUSTASE_X',current_timestamp(3),0,'HARIDUSTASE') ON CONFLICT DO NOTHING;
insert into classifier_connect(classifier_code,connect_classifier_code,inserted,version,main_classifier_code) values('OPPEASTE_433','HARIDUSTASE_K',current_timestamp(3),0,'HARIDUSTASE') ON CONFLICT DO NOTHING;
insert into classifier_connect(classifier_code,connect_classifier_code,inserted,version,main_classifier_code) values('OPPEASTE_512','HARIDUSTASE_B',current_timestamp(3),0,'HARIDUSTASE') ON CONFLICT DO NOTHING;
