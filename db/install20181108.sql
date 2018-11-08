\c hois


/* Create Tables */

CREATE TABLE "base_module"
(
	"id" bigserial NOT NULL ,
	"school_id" bigint NOT NULL,    -- viide õppeasutusele
	"name_et" varchar(255)	 NOT NULL,    -- nimetus e.k.
	"name_en" varchar(255)	 NULL,    -- nimetus. i.kk
	"credits" numeric(4,1) NOT NULL,    -- maht EKAP
	"objectives_et" varchar(10000)	 NOT NULL,    -- eesmärgid e.k.
	"objectives_en" varchar(10000)	 NULL,    -- eesmärgid i.k.
	"assessments_et" text NOT NULL,    -- hindamiskriteeriumid e.k.
	"assessments_en" text NULL,    -- hindamiskriteeriumid i.k.
	"cv_requirements_et" text NOT NULL,    -- nõuded mooduli alustamiseks
	"cv_assessments_et" text NOT NULL,    -- mooduli täpsemad hindamiskriteeriumid
	"cv_learning_methods_et" text NULL,    -- mooduli õppemeetodid
	"cv_assessment_methods_et" text NULL,    -- mooduli hindamismeetodid ja ülesanded
	"cv_independent_study_et" text NULL,    -- iseseisev töö
	"cv_study_materials" text NULL,    -- õppematerjalid
	"cv_total_grade_description" text NOT NULL,    -- kokkuvõtva hinde kujunemine
	"cv_pass_description" text NULL,    -- "A"  saamise tingimus
	"cv_grade3_description" text NULL,    -- "3"  saamise tingimus
	"cv_grade4_description" text NULL,    -- "4"  saamise tingimus
	"cv_grade5_description" text NULL,    -- "5"  saamise tingimus
	"cv_assessment_code" varchar(100)	 NULL,
	"teacher_id" bigint NOT NULL,    -- mooduli vastutaja (juhtivõpetaja), viide aktiivsele õpetajale
	"valid_from" date NOT NULL,    -- kehtivuse algus, vaikimisi jooksev kp
	"valid_thru" date NULL,    -- kehtiv kuni
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

CREATE TABLE "base_module_outcomes"
(
	"id" bigserial NOT NULL ,
	"base_module_id" bigint NOT NULL,    -- viide baasmoodulile
	"outcome_et" varchar(1000)	 NOT NULL,    -- õpiväljund e.k.
	"outcome_en" varchar(1000)	 NULL,    -- õpiväljund i.k.
	"order_nr" smallint NULL,    -- jrk nr
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

CREATE TABLE "base_module_theme"
(
	"id" bigserial NOT NULL ,
	"base_module_id" bigint NOT NULL,    -- viide baasmoodulile
	"name_et" varchar(255)	 NOT NULL,    -- nimetus
	"credits" numeric(4,1) NOT NULL,    -- teema maht EKAP
	"hours" smallint NOT NULL,    -- teema maht tundides, vaikimisi credits*26
	"subthemes" text NULL,    -- alateemad
	"total_grade_description" varchar(10000)	 NULL,
	"pass_description" varchar(10000)	 NULL,    -- "A"  saamise tingimus
	"grade3_description" varchar(10000)	 NULL,    -- "3"  saamise tingimus
	"grade4_description" varchar(10000)	 NULL,    -- "4"  saamise tingimus
	"grade5_description" varchar(10000)	 NULL,    -- "5"  saamise tingimus
	"assessment_code" varchar(100)	 NULL,    -- hindamisviis
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

CREATE TABLE "base_module_theme_outcomes"
(
	"id" bigserial NOT NULL ,
	"base_module_theme_id" bigint NOT NULL,    -- viide baasmooduli teemale
	"base_module_outcomes_id" bigint NOT NULL,    -- viide õpiväljnudile
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

CREATE TABLE "base_module_theme_capacity"
(
	"id" bigserial NOT NULL ,
	"base_module_theme_id" bigint NOT NULL,    -- viide baasmooduli teemale
	"capacity_type_code" varchar(100)	 NOT NULL,    -- mahu jaotuse liik, viide klassifikaatorile MAHT, peab arvestama ainult nende väärtustega, kus is_vocational=true
	"hours" smallint NOT NULL,    -- tunnid
	"is_contact" boolean NOT NULL,    -- kas on kontaktõpe
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

CREATE TABLE "base_module_capacity"
(
	"id" bigserial NOT NULL,
	"base_module_id" bigint NOT NULL,    -- viide baasmoodulile
	"capacity_type_code" varchar(100)	 NOT NULL,
	"hours" smallint NOT NULL,    -- tunnid
	"is_contact" boolean NOT NULL,    -- kas tegemist kontaktõppega
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "base_module"
	IS 'baasmoodulid'
;

COMMENT ON COLUMN "base_module"."school_id"
	IS 'viide õppeasutusele'
;

COMMENT ON COLUMN "base_module"."name_et"
	IS 'nimetus e.k.'
;

COMMENT ON COLUMN "base_module"."name_en"
	IS 'nimetus. i.kk'
;

COMMENT ON COLUMN "base_module"."credits"
	IS 'maht EKAP'
;

COMMENT ON COLUMN "base_module"."objectives_et"
	IS 'eesmärgid e.k.'
;

COMMENT ON COLUMN "base_module"."objectives_en"
	IS 'eesmärgid i.k.'
;

COMMENT ON COLUMN "base_module"."assessments_et"
	IS 'hindamiskriteeriumid e.k.'
;

COMMENT ON COLUMN "base_module"."assessments_en"
	IS 'hindamiskriteeriumid i.k.'
;

COMMENT ON COLUMN "base_module"."cv_requirements_et"
	IS 'nõuded mooduli alustamiseks'
;

COMMENT ON COLUMN "base_module"."cv_assessments_et"
	IS 'mooduli täpsemad hindamiskriteeriumid'
;

COMMENT ON COLUMN "base_module"."cv_learning_methods_et"
	IS 'mooduli õppemeetodid'
;

COMMENT ON COLUMN "base_module"."cv_assessment_methods_et"
	IS 'mooduli hindamismeetodid ja ülesanded'
;

COMMENT ON COLUMN "base_module"."cv_independent_study_et"
	IS 'iseseisev töö'
;

COMMENT ON COLUMN "base_module"."cv_study_materials"
	IS 'õppematerjalid'
;

COMMENT ON COLUMN "base_module"."cv_total_grade_description"
	IS 'kokkuvõtva hinde kujunemine'
;

COMMENT ON COLUMN "base_module"."cv_pass_description"
	IS '"A"  saamise tingimus'
;

COMMENT ON COLUMN "base_module"."cv_grade3_description"
	IS '"3"  saamise tingimus'
;

COMMENT ON COLUMN "base_module"."cv_grade4_description"
	IS '"4"  saamise tingimus'
;

COMMENT ON COLUMN "base_module"."cv_grade5_description"
	IS '"5"  saamise tingimus'
;

COMMENT ON COLUMN "base_module"."teacher_id"
	IS 'mooduli vastutaja (juhtivõpetaja), viide aktiivsele õpetajale'
;

COMMENT ON COLUMN "base_module"."valid_from"
	IS 'kehtivuse algus, vaikimisi jooksev kp'
;

COMMENT ON COLUMN "base_module"."valid_thru"
	IS 'kehtiv kuni'
;

COMMENT ON TABLE "base_module_outcomes"
	IS 'baasmooduli õpiväljundid'
;

COMMENT ON COLUMN "base_module_outcomes"."base_module_id"
	IS 'viide baasmoodulile'
;

COMMENT ON COLUMN "base_module_outcomes"."outcome_et"
	IS 'õpiväljund e.k.'
;

COMMENT ON COLUMN "base_module_outcomes"."outcome_en"
	IS 'õpiväljund i.k.'
;

COMMENT ON COLUMN "base_module_outcomes"."order_nr"
	IS 'jrk nr'
;

COMMENT ON TABLE "base_module_theme"
	IS 'baasmooduli teemad'
;

COMMENT ON COLUMN "base_module_theme"."base_module_id"
	IS 'viide baasmoodulile'
;

COMMENT ON COLUMN "base_module_theme"."name_et"
	IS 'nimetus'
;

COMMENT ON COLUMN "base_module_theme"."credits"
	IS 'teema maht EKAP'
;

COMMENT ON COLUMN "base_module_theme"."hours"
	IS 'teema maht tundides, vaikimisi credits*26'
;

COMMENT ON COLUMN "base_module_theme"."subthemes"
	IS 'alateemad'
;

COMMENT ON COLUMN "base_module_theme"."pass_description"
	IS '"A"  saamise tingimus'
;

COMMENT ON COLUMN "base_module_theme"."grade3_description"
	IS '"3"  saamise tingimus'
;

COMMENT ON COLUMN "base_module_theme"."grade4_description"
	IS '"4"  saamise tingimus'
;

COMMENT ON COLUMN "base_module_theme"."grade5_description"
	IS '"5"  saamise tingimus'
;

COMMENT ON COLUMN "base_module_theme"."assessment_code"
	IS 'hindamisviis'
;

COMMENT ON TABLE "base_module_theme_outcomes"
	IS 'Baasmooduili teema õpiväljundid'
;

COMMENT ON COLUMN "base_module_theme_outcomes"."base_module_theme_id"
	IS 'viide baasmooduli teemale'
;

COMMENT ON COLUMN "base_module_theme_outcomes"."base_module_outcomes_id"
	IS 'viide õpiväljnudile'
;

COMMENT ON TABLE "base_module_theme_capacity"
	IS 'baasmooduli teema mahujaotus'
;

COMMENT ON COLUMN "base_module_theme_capacity"."base_module_theme_id"
	IS 'viide baasmooduli teemale'
;

COMMENT ON COLUMN "base_module_theme_capacity"."capacity_type_code"
	IS 'mahu jaotuse liik, viide klassifikaatorile MAHT, peab arvestama ainult nende väärtustega, kus is_vocational=true'
;

COMMENT ON COLUMN "base_module_theme_capacity"."hours"
	IS 'tunnid'
;

COMMENT ON COLUMN "base_module_theme_capacity"."is_contact"
	IS 'kas on kontaktõpe'
;

COMMENT ON TABLE "base_module_capacity"
	IS 'baasmooduli maht'
;

COMMENT ON COLUMN "base_module_capacity"."base_module_id"
	IS 'viide baasmoodulile'
;

COMMENT ON COLUMN "base_module_capacity"."hours"
	IS 'tunnid'
;

COMMENT ON COLUMN "base_module_capacity"."is_contact"
	IS 'kas tegemist kontaktõppega'
;

/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE "base_module" ADD CONSTRAINT "PK_base_module"
	PRIMARY KEY ("id")
;

CREATE INDEX "IXFK_base_module_classifier" ON "base_module" ("cv_assessment_code" ASC)
;

CREATE INDEX "IXFK_base_module_school" ON "base_module" ("school_id" ASC)
;

CREATE INDEX "IXFK_base_module_teacher" ON "base_module" ("teacher_id" ASC)
;

ALTER TABLE "base_module_outcomes" ADD CONSTRAINT "PK_base_module_outcomes"
	PRIMARY KEY ("id")
;

CREATE INDEX "IXFK_base_module_outcomes_base_module" ON "base_module_outcomes" ("base_module_id" ASC)
;

ALTER TABLE "base_module_theme" ADD CONSTRAINT "PK_base_module_theme"
	PRIMARY KEY ("id")
;

CREATE INDEX "IXFK_base_module_theme_base_module" ON "base_module_theme" ("base_module_id" ASC)
;

CREATE INDEX "IXFK_base_module_theme_classifier" ON "base_module_theme" ("assessment_code" ASC)
;

ALTER TABLE "base_module_theme_outcomes" ADD CONSTRAINT "PK_base_module_theme_outcomes"
	PRIMARY KEY ("id")
;

ALTER TABLE "base_module_theme_outcomes" ADD CONSTRAINT "UQ_base_module_theme_outcomes" UNIQUE ("base_module_theme_id","base_module_outcomes_id")
;

CREATE INDEX "IXFK_base_module_theme_outcomes_base_module_outcomes" ON "base_module_theme_outcomes" ("base_module_outcomes_id" ASC)
;

CREATE INDEX "IXFK_base_module_theme_outcomes_base_module_theme" ON "base_module_theme_outcomes" ("base_module_theme_id" ASC)
;

ALTER TABLE "base_module_theme_capacity" ADD CONSTRAINT "PK_base_module_theme_capacity"
	PRIMARY KEY ("id")
;

CREATE INDEX "IXFK_base_module_theme_capacity_base_module_theme" ON "base_module_theme_capacity" ("base_module_theme_id" ASC)
;

CREATE INDEX "IXFK_base_module_theme_capacity_classifier" ON "base_module_theme_capacity" ("capacity_type_code" ASC)
;

ALTER TABLE "base_module_capacity" ADD CONSTRAINT "PK_base_module_capacity"
	PRIMARY KEY ("id")
;

CREATE INDEX "IXFK_base_module_capacity_base_module" ON "base_module_capacity" ("base_module_id" ASC)
;

CREATE INDEX "IXFK_base_module_capacity_classifier" ON "base_module_capacity" ("capacity_type_code" ASC)
;

/* Create Foreign Key Constraints */

ALTER TABLE "base_module" ADD CONSTRAINT "FK_base_module_classifier"
	FOREIGN KEY ("cv_assessment_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "base_module" ADD CONSTRAINT "FK_base_module_school"
	FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "base_module" ADD CONSTRAINT "FK_base_module_teacher"
	FOREIGN KEY ("teacher_id") REFERENCES "public"."teacher" ("id") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "base_module_outcomes" ADD CONSTRAINT "FK_base_module_outcomes_base_module"
	FOREIGN KEY ("base_module_id") REFERENCES "base_module" ("id") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "base_module_theme" ADD CONSTRAINT "FK_base_module_theme_base_module"
	FOREIGN KEY ("base_module_id") REFERENCES "base_module" ("id") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "base_module_theme" ADD CONSTRAINT "FK_base_module_theme_classifier"
	FOREIGN KEY ("assessment_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "base_module_theme_outcomes" ADD CONSTRAINT "FK_base_module_theme_outcomes_base_module_outcomes"
	FOREIGN KEY ("base_module_outcomes_id") REFERENCES "base_module_outcomes" ("id") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "base_module_theme_outcomes" ADD CONSTRAINT "FK_base_module_theme_outcomes_base_module_theme"
	FOREIGN KEY ("base_module_theme_id") REFERENCES "base_module_theme" ("id") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "base_module_theme_capacity" ADD CONSTRAINT "FK_base_module_theme_capacity_base_module_theme"
	FOREIGN KEY ("base_module_theme_id") REFERENCES "base_module_theme" ("id") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "base_module_theme_capacity" ADD CONSTRAINT "FK_base_module_theme_capacity_classifier"
	FOREIGN KEY ("capacity_type_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "base_module_capacity" ADD CONSTRAINT "FK_base_module_capacity_base_module"
	FOREIGN KEY ("base_module_id") REFERENCES "base_module" ("id") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "base_module_capacity" ADD CONSTRAINT "FK_base_module_capacity_classifier"
	FOREIGN KEY ("capacity_type_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action
;

alter table curriculum_module add base_module_id bigint;
alter table curriculum_version_omodule add base_module_id bigint;
CREATE INDEX "IXFK_curriculum_module_base_module" ON "curriculum_module" ("base_module_id" ASC);
CREATE INDEX "IXFK_curriculum_version_omodule_base_module" ON "curriculum_version_omodule" ("base_module_id" ASC);
ALTER TABLE "curriculum_module" ADD CONSTRAINT "FK_curriculum_module_base_module" FOREIGN KEY ("base_module_id") REFERENCES "base_module" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "curriculum_version_omodule" ADD CONSTRAINT "FK_curriculum_version_omodule_base_module" FOREIGN KEY ("base_module_id") REFERENCES "base_module" ("id") ON DELETE No Action ON UPDATE No Action;


update classifier set ehis_value='OK_STAATUS_ASD' where code='OPPEKAVA_EHIS_STAATUS_A';
update classifier set ehis_value='OK_STAATUS_TYH' where code='OPPEKAVA_EHIS_STAATUS_C';
update classifier set ehis_value='OK_STAATUS_EKS' where code='OPPEKAVA_EHIS_STAATUS_E';
update classifier set ehis_value='OK_STAATUS_EVN' where code='OPPEKAVA_EHIS_STAATUS_EI';
update classifier set ehis_value='OK_STAATUS_VNT' where code='OPPEKAVA_EHIS_STAATUS_EV';
update classifier set ehis_value='OK_STAATUS_TAG' where code='OPPEKAVA_EHIS_STAATUS_L';
update classifier set ehis_value='OK_STAATUS_MEN' where code='OPPEKAVA_EHIS_STAATUS_M';
update classifier set ehis_value='OK_STAATUS_REG' where code='OPPEKAVA_EHIS_STAATUS_R';
update classifier set ehis_value='OK_STAATUS_SIS' where code='OPPEKAVA_EHIS_STAATUS_S';
update classifier set ehis_value='OK_STAATUS_THD' where code='OPPEKAVA_EHIS_STAATUS_X';
INSERT INTO "classifier" ("code", "value", "value2", "name_et", "name_en", "name_ru", "parent_class_code", "main_class_code", "inserted", "changed", "valid", "description", "valid_from", "valid_thru", "extraval1", "extraval2", "ehis_value", "is_vocational", "is_higher", "version", "inserted_by", "changed_by") VALUES ('OPPEKAVA_EHIS_STAATUS_T', 'T', NULL, 'Tagastatud puudustega', NULL, NULL, NULL, 'OPPEKAVA_EHIS_STAATUS', '2016-11-17 22:08:17.42146', NULL, 't', NULL, NULL, NULL, NULL, NULL, 'OK_STAATUS_TGL', 't', 't', '0', NULL, NULL);

alter table curriculum_version_omodule_theme add base_module_theme_id bigint;
alter table curriculum_module_outcomes add base_module_outcomes_id bigint;
CREATE INDEX "IXFK_curriculum_version_omodule_3" ON "curriculum_version_omodule_theme" ("base_module_theme_id" ASC);
CREATE INDEX "IXFK_curriculum_omodule_outcomes_3" ON "curriculum_module_outcomes" ("base_module_outcomes_id" ASC);
ALTER TABLE "curriculum_version_omodule_theme" ADD CONSTRAINT "FK_curriculum_version_omodule_theme_3" FOREIGN KEY ("base_module_theme_id") REFERENCES "base_module_theme" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "curriculum_module_outcomes" ADD CONSTRAINT "FK_curriculum_omodule_outcomes_3" FOREIGN KEY ("base_module_outcomes_id") REFERENCES "base_module_outcomes" ("id") ON DELETE No Action ON UPDATE No Action;


alter table student_absence add column is_lesson_absence boolean;
alter table journal_entry_student add column is_lesson_absence boolean;
comment on column student_absence.is_lesson_absence is 'puudumistõendi aktsepteerimine tundide kaupa';
comment on column journal_entry_student.is_lesson_absence is 'kas puudumise sisestatakse tunni kaupa';
alter table contract add column is_practice_absence boolean;
comment on column contract.is_practice_absence is 'kas märkida praktikal puudumist';
alter table student_absence add column contract_id bigint;
comment on column student_absence.contract_id is 'viide lepingule kui puudumistõend on tekkinud lepingu alusel';
CREATE INDEX "IXFK_student_absence_contract" ON "student_absence" ("contract_id" ASC);
ALTER TABLE "student_absence" ADD CONSTRAINT "FK_student_absence_contract" FOREIGN KEY ("contract_id") REFERENCES "contract" ("id") ON DELETE No Action ON UPDATE No Action;


CREATE TABLE "journal_entry_student_lesson_absence"
(
	"id" bigserial NOT NULL,
	"journal_entry_student_id" bigint NOT NULL,    -- viide sissekandele
	"lesson_nr" smallint NOT NULL,    -- tunni nr kujul 1, 2, 3 jne
	"absence_code" varchar(100)	 NOT NULL,
	"absence_inserted" timestamp without time zone NOT NULL,    -- puudumise lisamise aeg
	"absence_accepted" timestamp without time zone NULL,    -- puudumise aktsepteerimise aeg
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

CREATE TABLE "student_absence_lesson"
(
	"id" bigserial NOT NULL,
	"student_absence_id" bigint NOT NULL,    -- viide puudumistõendile
	"absence" date NOT NULL,    -- puudumise kp
	"lesson_nr" smallint NOT NULL,    -- puudutud tund
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "journal_entry_student_lesson_absence"	IS 'õppija puudumised tunni kaupa';
COMMENT ON COLUMN "journal_entry_student_lesson_absence"."journal_entry_student_id"	IS 'viide sissekandele';
COMMENT ON COLUMN "journal_entry_student_lesson_absence"."lesson_nr"	IS 'tunni nr kujul 1, 2, 3 jne';
COMMENT ON COLUMN "journal_entry_student_lesson_absence"."absence_inserted"	IS 'puudumise lisamise aeg';
COMMENT ON COLUMN "journal_entry_student_lesson_absence"."absence_accepted"	IS 'puudumise aktsepteerimise aeg';
COMMENT ON TABLE "student_absence_lesson"	IS 'puudumistõend tundide kaupa';
COMMENT ON COLUMN "student_absence_lesson"."student_absence_id"	IS 'viide puudumistõendile';
COMMENT ON COLUMN "student_absence_lesson"."absence"	IS 'puudumise kp';
COMMENT ON COLUMN "student_absence_lesson"."lesson_nr"	IS 'puudutud tund';
ALTER TABLE "journal_entry_student_lesson_absence" ADD CONSTRAINT "PK_journal_entry_student_lesson_abcence"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_journal_entry_student_lesson_abcence_classifier" ON "journal_entry_student_lesson_absence" ("absence_code" ASC);
CREATE INDEX "IXFK_journal_entry_student_lesson_abcence_journal_entry_student" ON "journal_entry_student_lesson_absence" ("journal_entry_student_id" ASC);
ALTER TABLE "student_absence_lesson" ADD CONSTRAINT "PK_student_absence_lesson"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_student_absence_lesson_student_absence" ON "student_absence_lesson" ("student_absence_id" ASC);
ALTER TABLE "journal_entry_student_lesson_absence" ADD CONSTRAINT "FK_journal_entry_student_lesson_abcence_classifier"	FOREIGN KEY ("absence_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "journal_entry_student_lesson_absence" ADD CONSTRAINT "FK_journal_entry_student_lesson_abcence_journal_entry_student"	FOREIGN KEY ("journal_entry_student_id") REFERENCES "journal_entry_student" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "student_absence_lesson" ADD CONSTRAINT "FK_student_absence_lesson_student_absence"	FOREIGN KEY ("student_absence_id") REFERENCES "public"."student_absence" ("id") ON DELETE No Action ON UPDATE No Action;


CREATE TABLE "committee_curriculum"
(
	"id" bigserial NOT NULL,
	"committee_id" bigint NOT NULL,
	"curriculum_id" bigint NOT NULL,
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "committee_curriculum"	IS 'õppetoetuse komisjoni õppekavad';
ALTER TABLE "committee_curriculum" ADD CONSTRAINT "PK_committee_curriculum"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_committee_curriculum_committee" ON "committee_curriculum" ("committee_id" ASC);
CREATE INDEX "IXFK_committee_curriculum_curriculum" ON "committee_curriculum" ("curriculum_id" ASC);
ALTER TABLE "committee_curriculum" ADD CONSTRAINT "FK_committee_curriculum_committee"	FOREIGN KEY ("committee_id") REFERENCES "committee" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "committee_curriculum" ADD CONSTRAINT "FK_committee_curriculum_curriculum"	FOREIGN KEY ("curriculum_id") REFERENCES "public"."curriculum" ("id") ON DELETE No Action ON UPDATE No Action;
alter table committee_member add column person_id bigint;
create index IXFK_committee_member_person on committee_member(person_id);
alter table committee_member add constraint FK_committee_member_person foreign key (person_id) references person(id);
alter table committee add column name_et varchar(255);
alter table committee add column type_code varchar(100);
comment on column committee.type_code is 'komisjoni liik, viide klassifikaatorile KOMISJON';
create index IXFK_committee_classifier on committee(type_code);
alter table committee add constraint FK_committee_classifier foreign key (type_code) references classifier(code);

INSERT INTO classifier (code, value, name_et, inserted, valid, is_vocational, is_higher, version) VALUES ('KOMISJON', 'KOMISJON', 'Komisjoni liik', now(), 't',  't', 't', '0');
INSERT INTO classifier (code, value, name_et, main_class_code,inserted, valid, is_vocational, is_higher, version) VALUES ('KOMISJON_K', 'K', 'Kaitsmiskomisjon', 'KOMISJON', now(), 't',  't', 't', '0');
INSERT INTO classifier (code, value, name_et, main_class_code,inserted, valid, is_vocational, is_higher, version) VALUES ('KOMISJON_T', 'T', 'Õppetoetuse/stipendiumi komisjon', 'KOMISJON', now(), 't',  't', 't', '0');
INSERT INTO classifier (code, value, name_et, main_class_code,inserted, valid, is_vocational, is_higher, version) VALUES ('KOMISJON_V', 'V', 'VÕTA komisjon', 'KOMISJON', now(), 't',  't', 't', '0');

update committee set type_code='KOMISJON_K';
alter table committee ALTER COLUMN type_code SET NOT NULL;

CREATE TABLE "scholarship_decision"
(
	"id" bigserial NOT NULL ,
	"committee_id" bigint NOT NULL,    -- viide komisjonile
	"protocol_nr" varchar(50)	 NULL,    -- protokolli number
	"decided" date NOT NULL,    -- otsuse kuupäev
	"add_info" varchar(4000)	 NULL,    -- märkus
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "scholarship_decision"	IS 'õppetoetuse otsus';
COMMENT ON COLUMN "scholarship_decision"."committee_id"	IS 'viide komisjonile';
COMMENT ON COLUMN "scholarship_decision"."protocol_nr"	IS 'protokolli number';
COMMENT ON COLUMN "scholarship_decision"."decided"	IS 'otsuse kuupäev';
COMMENT ON COLUMN "scholarship_decision"."add_info"	IS 'märkus';
ALTER TABLE "scholarship_decision" ADD CONSTRAINT "PK_scholarship_decision"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_scholarship_decision_committee" ON "scholarship_decision" ("committee_id" ASC);
ALTER TABLE "scholarship_decision" ADD CONSTRAINT "FK_scholarship_decision_committee"	FOREIGN KEY ("committee_id") REFERENCES "committee" ("id") ON DELETE No Action ON UPDATE No Action;

alter table scholarship_application add column scholarship_decision_id bigint;
comment on column scholarship_application.scholarship_decision_id is 'viide komisjoni otsusele';
create index IXFK_scholarship_application_scholarship_decision on scholarship_application(scholarship_decision_id);
alter table scholarship_application add constraint FK_scholarship_application_scholarship_decision foreign key (scholarship_decision_id) references scholarship_decision(id);

insert into classifier (code, value, name_et, main_class_code, inserted, valid, is_vocational, is_higher, version) values ('TEATE_LIIK_OP_AVALDUS_KINNIT', 'OP_AVALDUS_KINNIT', 'Õppija avaldus on kinnitatud', 'TEATE_LIIK', now(), true,  true, true, 0);
insert into classifier (code, value, name_et, main_class_code, inserted, valid, is_vocational, is_higher, version) values('TEEMAOIGUS_KASKKIRI_EKISETA', 'KASKKIRI_EKISETA', 'Käskkirjad ilma EKISeta', 'TEEMAOIGUS', now(), true,  true, true, 0);
insert into user_role_default (object_code, permission_code, role_code) values ('TEEMAOIGUS_KASKKIRI_EKISETA', 'OIGUS_V', 'ROLL_A');
insert into user_rights(user_id,permission_code,object_code,inserted,version,inserted_by)
select u.id, 'OIGUS_V', 'TEEMAOIGUS_KASKKIRI_EKISETA', now(), 0, 'admin'
from user_ u where u.role_code in ('ROLL_A');

insert into classifier (code, value, name_et, main_class_code, inserted, valid, is_vocational, is_higher, version) values('AVALDUS_LIIK_MUU', 'MUU', 'Muu', 'AVALDUS_LIIK', now(), true,  true, true, 0);

create trigger base_module_audit after insert or delete or update on base_module for each row execute procedure hois_audit();
create trigger base_module_capacity_audit after insert or delete or update on base_module_capacity for each row execute procedure hois_audit();
create trigger base_module_outcomes_audit after insert or delete or update on base_module_outcomes for each row execute procedure hois_audit();
create trigger base_module_theme_audit after insert or delete or update on base_module_theme for each row execute procedure hois_audit();
create trigger base_module_theme_capacity_audit after insert or delete or update on base_module_theme_capacity for each row execute procedure hois_audit();
create trigger base_module_theme_outcomes_audit after insert or delete or update on base_module_theme_outcomes for each row execute procedure hois_audit();
create trigger committee_curriculum_audit after insert or delete or update on committee_curriculum for each row execute procedure hois_audit();
create trigger curriculum_address_audit after insert or delete or update on curriculum_address for each row execute procedure hois_audit();
create trigger journal_entry_student_lesson_absence_audit after insert or delete or update on journal_entry_student_lesson_absence for each row execute procedure hois_audit();
create trigger scholarship_decision_audit after insert or delete or update on scholarship_decision for each row execute procedure hois_audit();
create trigger school_capacity_type_audit after insert or delete or update on school_capacity_type for each row execute procedure hois_audit();
create trigger school_capacity_type_load_audit after insert or delete or update on school_capacity_type_load for each row execute procedure hois_audit();
create trigger student_absence_lesson_audit after insert or delete or update on student_absence_lesson for each row execute procedure hois_audit();
create trigger timetable_event_student_group_audit after insert or delete or update on timetable_event_student_group for each row execute procedure hois_audit();

alter table journal add column untis_code varchar(20);
alter table teacher add column untis_code varchar(20);
alter table timetable_event add column is_imported boolean;

alter table scholarship_term add column committee_id bigint;
comment on column scholarship_term.committee_id is 'viide komisjonile';
create index IXFK_scholarship_term_committee on scholarship_term(committee_id);
alter table scholarship_term add constraint FK_scholarship_term_committee foreign key (committee_id) references committee(id);


CREATE TABLE "scholarship_decision_committee_member"
(
	"id" bigserial NOT NULL,
	"scholarship_decision_id" bigint NOT NULL,
	"committee_member_id" bigint NOT NULL,
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "scholarship_decision_committee_member"	IS 'õppetotuse komisjoni kohalviibijad';
/* Create Primary Keys, Indexes, Uniques, Checks */
ALTER TABLE "scholarship_decision_committee_member" ADD CONSTRAINT "PK_scholarship_decision_committee_member"	PRIMARY KEY ("id");
ALTER TABLE "scholarship_decision_committee_member" ADD CONSTRAINT "UQ_scholarship_decision_commettee_member" UNIQUE ("scholarship_decision_id","committee_member_id");
CREATE INDEX "IXFK_scholarship_decision_committee_member_committee_member" ON "scholarship_decision_committee_member" ("committee_member_id" ASC);
CREATE INDEX "IXFK_scholarship_decision_committee_member_scholarship_decision" ON "scholarship_decision_committee_member" ("scholarship_decision_id" ASC);

/* Create Foreign Key Constraints */

ALTER TABLE "scholarship_decision_committee_member" ADD CONSTRAINT "FK_scholarship_decision_committee_member_committee_member"	FOREIGN KEY ("committee_member_id") REFERENCES "committee_member" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "scholarship_decision_committee_member" ADD CONSTRAINT "FK_scholarship_decision_committee_member_scholarship_decision"	FOREIGN KEY ("scholarship_decision_id") REFERENCES "scholarship_decision" ("id") ON DELETE No Action ON UPDATE No Action;

alter table curriculum_version_omodule add column teacher_id bigint;
CREATE INDEX "IXFK_curriculum_version_omodule_teacher" ON "curriculum_version_omodule" ("teacher_id" ASC);
ALTER TABLE "curriculum_version_omodule" ADD CONSTRAINT "FK_curriculum_version_omodule_teacher"	FOREIGN KEY ("teacher_id") REFERENCES "teacher" ("id") ON DELETE No Action ON UPDATE No Action;

alter table apel_application add column decision varchar(4000);
comment on column apel_application.decision is 'komisjoni otsus';
alter table apel_application add column committee_id bigint;
CREATE INDEX IXFK_apel_application_committee ON apel_application (committee_id ASC);
ALTER TABLE apel_application ADD CONSTRAINT "FK_apel_application_committee"	FOREIGN KEY ("committee_id") REFERENCES "committee" ("id") ON DELETE No Action ON UPDATE No Action;

INSERT INTO classifier (code, value, name_et, main_class_code, inserted, changed, valid, is_vocational, is_higher, version, inserted_by, changed_by) 
VALUES ('TEEMAOIGUS_VOTAKOM', 'VOTAKOM', 'VÕTA komisjon', 'TEEMAOIGUS', now(), now(), 't', 't', 't', '0', 'Automaat', 'Automaat');
insert into user_role_default(object_code,permission_code,role_code) values ('TEEMAOIGUS_VOTAKOM','OIGUS_M','ROLL_A');
insert into user_role_default(object_code,permission_code,role_code) values ('TEEMAOIGUS_VOTAKOM','OIGUS_M','ROLL_O');
INSERT INTO classifier (code, value, name_et, main_class_code, inserted, changed, valid, is_vocational, is_higher, version, inserted_by, changed_by) 
VALUES ('VOTA_STAATUS_V', 'V', 'Ülevaatamisel (komisjon)', 'VOTA_STAATUS', now(), now(), 't', 't', 't', '0', 'Automaat', 'Automaat');

insert into user_role_default (object_code, permission_code, role_code) values ('TEEMAOIGUS_STIPTOETUS', 'OIGUS_V', 'ROLL_O');

/* Create Tables */

CREATE TABLE "subject_program"
(
	"id" bigserial NOT NULL,
	"independent_study" text NULL,    -- iseseisev töö
	"subject_study_period_teacher_id" bigint NOT NULL,
	"assessment_description" text NULL,    -- hindamismeetodid
	"study_literature" text NULL,    -- kohustuslik kirjandus
	"study_description" text NULL,    -- õppetöö sisu
	"study_content_type_code" varchar(100)	 NOT NULL,    -- õppetöö sisu sisestamise viis, viide klassifikaatorile OPPETOOSISU
	"is_public_all" boolean NOT NULL,    -- kas avalik kõigile
	"is_public_hois" boolean NOT NULL,    -- kas avalik tahvli kasutajatele
	"is_public_student" boolean NOT NULL,    -- kas avalik õppuritele
	"confirmed" timestamp without time zone NULL,    -- kinnitamise kp
	"confirmed_by" varchar(100)	 NULL,    -- kinnitaja nimi
	"status_code" varchar(100)	 NOT NULL,    -- staatus, viide klassifikaatorile AINEPROGRAMM_STAATUS
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

CREATE TABLE "subject_program_study_content"
(
	"id" bigserial NOT NULL,
	"subject_program_id" bigint NOT NULL,
	"week_nr" smallint NULL,    -- nädala nr
	"study_dt" date NULL,    -- kuupäev
	"study_info" varchar(4000)	 NOT NULL,    -- sisu
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "subject_program"
	IS 'aineprogramm'
;

COMMENT ON COLUMN "subject_program"."independent_study"
	IS 'iseseisev töö'
;

COMMENT ON COLUMN "subject_program"."assessment_description"
	IS 'hindamismeetodid'
;

COMMENT ON COLUMN "subject_program"."study_literature"
	IS 'kohustuslik kirjandus'
;

COMMENT ON COLUMN "subject_program"."study_description"
	IS 'õppetöö sisu'
;

COMMENT ON COLUMN "subject_program"."study_content_type_code"
	IS 'õppetöö sisu sisestamise viis, viide klassifikaatorile OPPETOOSISU'
;

COMMENT ON COLUMN "subject_program"."is_public_all"
	IS 'kas avalik kõigile'
;

COMMENT ON COLUMN "subject_program"."is_public_hois"
	IS 'kas avalik tahvli kasutajatele'
;

COMMENT ON COLUMN "subject_program"."is_public_student"
	IS 'kas avalik õppuritele'
;

COMMENT ON COLUMN "subject_program"."confirmed"
	IS 'kinnitamise kp'
;

COMMENT ON COLUMN "subject_program"."confirmed_by"
	IS 'kinnitaja nimi'
;

COMMENT ON COLUMN "subject_program"."status_code"
	IS 'staatus, viide klassifikaatorile AINEPROGRAMM_STAATUS'
;


COMMENT ON TABLE "subject_program_study_content"
	IS 'aineprogrammi õppetöö sisu'
;

COMMENT ON COLUMN "subject_program_study_content"."week_nr"
	IS 'nädala nr'
;

COMMENT ON COLUMN "subject_program_study_content"."study_dt"
	IS 'kuupäev'
;

COMMENT ON COLUMN "subject_program_study_content"."study_info"
	IS 'sisu'
;

/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE "subject_program" ADD CONSTRAINT "PK_subject_program"
	PRIMARY KEY ("id")
;

CREATE INDEX "IXFK_subject_program_classifier" ON "subject_program" ("status_code" ASC)
;

CREATE INDEX "IXFK_subject_program_classifier_02" ON "subject_program" ("study_content_type_code" ASC)
;

CREATE INDEX "IXFK_subject_program_subject_study_period_teacher" ON "subject_program" ("subject_study_period_teacher_id" ASC)
;

ALTER TABLE "subject_program_study_content" ADD CONSTRAINT "PK_subject_program_study_content"
	PRIMARY KEY ("id")
;

CREATE INDEX "IXFK_subject_program_study_content_subject_program" ON "subject_program_study_content" ("subject_program_id" ASC)
;

/* Create Foreign Key Constraints */

ALTER TABLE "subject_program" ADD CONSTRAINT "FK_subject_program_classifier"
	FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "subject_program" ADD CONSTRAINT "FK_subject_program_classifier_02"
	FOREIGN KEY ("study_content_type_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "subject_program" ADD CONSTRAINT "FK_subject_program_subject_study_period_teacher"
	FOREIGN KEY ("subject_study_period_teacher_id") REFERENCES "public"."subject_study_period_teacher" ("id") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "subject_program_study_content" ADD CONSTRAINT "FK_subject_program_study_content_subject_program"
	FOREIGN KEY ("subject_program_id") REFERENCES "subject_program" ("id") ON DELETE No Action ON UPDATE No Action
;

INSERT INTO classifier (code, value, name_et, inserted, valid, is_vocational, is_higher, version) VALUES ('OPPETOOSISU', 'OPPETOOSISU', 'Aineprogrammi õppetöö sisu', now(), 't',  't', 't', '0');
INSERT INTO classifier (code, value, name_et, main_class_code,inserted, valid, is_vocational, is_higher, version) VALUES ('OPPETOOSISU_K', 'K', 'Kuupäevade kaupa', 'OPPETOOSISU', now(), 't',  't', 't', '0');
INSERT INTO classifier (code, value, name_et, main_class_code,inserted, valid, is_vocational, is_higher, version) VALUES ('OPPETOOSISU_N', 'N', 'Nädalate kaupa', 'OPPETOOSISU', now(), 't',  't', 't', '0');
INSERT INTO classifier (code, value, name_et, main_class_code,inserted, valid, is_vocational, is_higher, version) VALUES ('OPPETOOSISU_T', 'T', 'Tekstiväli', 'OPPETOOSISU', now(), 't',  't', 't', '0');


INSERT INTO classifier (code, value, name_et, inserted, valid, is_vocational, is_higher, version) VALUES ('AINEPROGRAMM_STAATUS', 'AINEPROGRAMM_STAATUS', 'Aineprogrammi staatus', now(), 't',  't', 't', '0');
INSERT INTO classifier (code, value, name_et, main_class_code,inserted, valid, is_vocational, is_higher, version) VALUES ('AINEPROGRAMM_STAATUS_L', 'L', 'Koostamata', 'AINEPROGRAMM_STAATUS', now(), 't',  't', 't', '0');
INSERT INTO classifier (code, value, name_et, main_class_code,inserted, valid, is_vocational, is_higher, version) VALUES ('AINEPROGRAMM_STAATUS_V', 'V', 'Valmis', 'AINEPROGRAMM_STAATUS', now(), 't',  't', 't', '0');
INSERT INTO classifier (code, value, name_et, main_class_code,inserted, valid, is_vocational, is_higher, version) VALUES ('AINEPROGRAMM_STAATUS_K', 'T', 'Kinnitatud', 'AINEPROGRAMM_STAATUS', now(), 't',  't', 't', '0');
INSERT INTO classifier (code, value, name_et, main_class_code,inserted, valid, is_vocational, is_higher, version) VALUES ('AINEPROGRAMM_STAATUS_I', 'I', 'Koostamisel', 'AINEPROGRAMM_STAATUS', now(), 't',  't', 't', '0');

insert into classifier (code, value, name_et, main_class_code, inserted, valid, is_vocational, is_higher, version) values('TEEMAOIGUS_BAASMOODUL', 'BAASMOODUL', 'Baasmoodulid', 'TEEMAOIGUS', now(), true,  true, true, 0);
insert into user_role_default (object_code, permission_code, role_code) values ('TEEMAOIGUS_BAASMOODUL', 'OIGUS_V', 'ROLL_A');

--ALTER TABLE "apel_school" DROP CONSTRAINT "apel_school_country_code_ehis_school_code_name_et_key";
--CREATE UNIQUE INDEX "UQ_apel_school" ON "apel_school" USING btree (country_code, ehis_school_code, name_et,school_id);

alter table subject_program 
add COLUMN "pass_description" text NULL,    -- "A"  saamise tingimus
add COLUMN 	"npass_description" text NULL,    -- "MA"  saamise tingimus
	add COLUMN "grade0_description" text NULL,    -- "0"  saamise tingimus
	add COLUMN "grade1_description" text NULL,    -- "1"  saamise tingimus
	add COLUMN "grade2_description" text NULL,    -- "2"  saamise tingimus
	add COLUMN "grade3_description" text NULL,    -- "3"  saamise tingimus
	add COLUMN "grade4_description" text NULL,    -- "4"  saamise tingimus
	add COLUMN "grade5_description" text NULL    -- "5"  saamise tingimus
;


COMMENT ON COLUMN "subject_program"."pass_description"	IS '"A"  saamise tingimus';
COMMENT ON COLUMN "subject_program"."npass_description"	IS '"MA"  saamise tingimus';
COMMENT ON COLUMN "subject_program"."grade0_description"	IS '"0"  saamise tingimus';
COMMENT ON COLUMN "subject_program"."grade1_description"	IS '"1"  saamise tingimus';
COMMENT ON COLUMN "subject_program"."grade2_description"	IS '"2"  saamise tingimus';
COMMENT ON COLUMN "subject_program"."grade3_description"	IS '"3"  saamise tingimus';
COMMENT ON COLUMN "subject_program"."grade4_description"	IS '"4"  saamise tingimus';
COMMENT ON COLUMN "subject_program"."grade5_description"	IS '"5"  saamise tingimus';

alter table timetable_event_teacher add column is_substitute boolean;

CREATE OR REPLACE FUNCTION upd_module_name_student_result()
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
	if tg_op = 'UPDATE' and new.name_et!=OLD.name_et THEN
		update student_vocational_result set module_name_et=NEW.name_et, changed=current_timestamp(3) where curriculum_version_omodule_id in (select id from curriculum_version_omodule where curriculum_module_id=NEW.id);
	end if;
	return null;
end;
$function$;

create trigger curriculum_module_upd_result after update on curriculum_module for each row execute procedure upd_module_name_student_result();

delete from classifier_connect where main_classifier_code='EKR' and classifier_code='OPPEASTE_410' and connect_classifier_code='EKR_1';





