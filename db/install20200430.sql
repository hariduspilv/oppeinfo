\c hois

/* Create Tables */

CREATE TABLE "student_curriculum_module_outcomes_result"
(
	"id" bigserial NOT NULL ,
	"student_id" bigint NOT NULL,    -- viide õppurile
	"curriculum_module_outcomes_id" bigint NOT NULL,    -- viide õpiväljundile
	"grade_code" varchar(100)	 NULL,    -- viide KUTSEHIDNAMINE
	"grade_date" date NULL,    -- hinde kp, kohustuslik kui on olemas tulemus
	"grade_inserted" timestamp without time zone NULL,    -- hinde lisamise kp
	"grade_inserted_by" varchar(100)	 NULL,    -- hinde lisaja
	"add_info" varchar(1000)	 NULL,    -- lisainfo
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

CREATE TABLE "student_curriculum_module_outcomes_result_history"
(
	"id" bigserial NOT NULL ,
	"student_curriculum_module_outcomes_result_id" bigint NOT NULL,
	"grade_code" varchar(100)	 NOT NULL,    --  viide klassifikaatorile KUTSEHINDAMINE
	"grade_date" date NULL,
	"grade_inserted" timestamp without time zone NULL,
	"grade_inserted_by" varchar(100)	 NULL,
	"add_info" varchar(1000)	 NULL,
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "student_curriculum_module_outcomes_result"
	IS 'õpiväljundite tulemused'
;

COMMENT ON COLUMN "student_curriculum_module_outcomes_result"."student_id"
	IS 'viide õppurile'
;

COMMENT ON COLUMN "student_curriculum_module_outcomes_result"."curriculum_module_outcomes_id"
	IS 'viide õpiväljundile'
;

COMMENT ON COLUMN "student_curriculum_module_outcomes_result"."grade_code"
	IS 'viide KUTSEHIDNAMINE'
;

COMMENT ON COLUMN "student_curriculum_module_outcomes_result"."grade_date"
	IS 'hinde kp, kohustuslik kui on olemas tulemus'
;

COMMENT ON COLUMN "student_curriculum_module_outcomes_result"."grade_inserted"
	IS 'hinde lisamise kp'
;

COMMENT ON COLUMN "student_curriculum_module_outcomes_result"."grade_inserted_by"
	IS 'hinde lisaja'
;

COMMENT ON COLUMN "student_curriculum_module_outcomes_result"."add_info"
	IS 'lisainfo'
;

COMMENT ON TABLE "student_curriculum_module_outcomes_result_history"
	IS 'õppuri õpiväljunditega seotud eelmised tulemused'
;

COMMENT ON COLUMN "student_curriculum_module_outcomes_result_history"."grade_code"
	IS ' viide klassifikaatorile KUTSEHINDAMINE'
;

/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE "student_curriculum_module_outcomes_result" ADD CONSTRAINT "PK_student_curriculum_module_outcomes_result"
	PRIMARY KEY ("id")
;

ALTER TABLE "student_curriculum_module_outcomes_result" ADD CONSTRAINT "UQ_student_curriculum_module_outcomes" UNIQUE ("student_id","curriculum_module_outcomes_id")
;

CREATE INDEX "IXFK_student_curriculum_module_outcomes_result_classifier" ON "student_curriculum_module_outcomes_result" ("grade_code" ASC)
;

CREATE INDEX "IXFK_student_curriculum_module_outcomes_result_curriculum_module_outcomes" ON "student_curriculum_module_outcomes_result" ("curriculum_module_outcomes_id" ASC)
;

CREATE INDEX "IXFK_student_curriculum_module_outcomes_result_student" ON "student_curriculum_module_outcomes_result" ("student_id" ASC)
;

ALTER TABLE "student_curriculum_module_outcomes_result_history" ADD CONSTRAINT "PK_student_curriculum_module_outcomes_result_history"
	PRIMARY KEY ("id")
;

CREATE INDEX "IXFK_student_curriculum_module_outcomes_result_history_classifier" ON "student_curriculum_module_outcomes_result_history" ("grade_code" ASC)
;

CREATE INDEX "IXFK_student_curriculum_module_outcomes_result_history_student_curriculum_module_outcomes_result" ON "student_curriculum_module_outcomes_result_history" ("student_curriculum_module_outcomes_result_id" ASC)
;

/* Create Foreign Key Constraints */

ALTER TABLE "student_curriculum_module_outcomes_result" ADD CONSTRAINT "FK_student_curriculum_module_outcomes_result_classifier"
	FOREIGN KEY ("grade_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "student_curriculum_module_outcomes_result" ADD CONSTRAINT "FK_student_curriculum_module_outcomes_result_curriculum_module_outcomes"
	FOREIGN KEY ("curriculum_module_outcomes_id") REFERENCES "public"."curriculum_module_outcomes" ("id") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "student_curriculum_module_outcomes_result" ADD CONSTRAINT "FK_student_curriculum_module_outcomes_result_student"
	FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "student_curriculum_module_outcomes_result_history" ADD CONSTRAINT "FK_student_curriculum_module_outcomes_result_history_classifier"
	FOREIGN KEY ("grade_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "student_curriculum_module_outcomes_result_history" ADD CONSTRAINT "FK_student_curriculum_module_outcomes_result_history_student_curriculum_module_outcomes_result"
	FOREIGN KEY ("student_curriculum_module_outcomes_result_id") REFERENCES "student_curriculum_module_outcomes_result" ("id") ON DELETE No Action ON UPDATE No Action
;


alter table curriculum_version_omodule_theme add is_module_outcomes boolean;
comment on column curriculum_version_omodule_theme.is_module_outcomes is 'kas on ÕV põhine hindamine';
alter table journal alter column assessment_code drop not null;

alter table student_curriculum_module_outcomes_result add column grade_inserted_teacher_id bigint;
comment on column student_curriculum_module_outcomes_result.grade_inserted_teacher_id is 'viide õpetajale';
create index IXFK_student_curriculum_module_outcomes_result_teacher on student_curriculum_module_outcomes_result(grade_inserted_teacher_id);
alter table student_curriculum_module_outcomes_result add constraint FK_rstudent_curriculum_module_outcomes_result_teacher foreign key(grade_inserted_teacher_id) references teacher(id);

alter table student_curriculum_module_outcomes_result add column apel_application_id bigint;
comment on column student_curriculum_module_outcomes_result.apel_application_id is 'viide VÕTA taolusele';
create index IXFK_student_curriculum_module_outcomes_result_apel_application on student_curriculum_module_outcomes_result(apel_application_id);
alter table student_curriculum_module_outcomes_result add constraint FK_rstudent_curriculum_module_outcomes_result_apel_application foreign key(apel_application_id) references apel_application(id);

alter TABLE "final_thesis" add column "language_code" varchar(100)	 NULL;    -- lõputöö keel, viide klassifikaatorile LOPUTOO_KEEL

CREATE TABLE "final_thesis_cercs"
(
	"id" bigserial NOT NULL,
	"final_thesis_id" bigint NOT NULL,
	"cercs_code" varchar(100)	 NOT NULL,    -- viide klassifikaatorile CERCS
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

COMMENT ON COLUMN "public"."final_thesis"."language_code"	IS 'lõputöö keel, viide klassifikaatorile LOPUTOO_KEEL';
COMMENT ON TABLE "final_thesis_cercs" 	IS 'teaduseriala klassifikaator, enamasti seotud lõputööga, saab olla mitu, viide klassifikaatorile CERCS';
COMMENT ON COLUMN "final_thesis_cercs"."cercs_code"	IS 'viide klassifikaatorile CERCS';

CREATE INDEX "IXFK_final_thesis_classifier_02" ON "public"."final_thesis" ("language_code" ASC);
ALTER TABLE "final_thesis_cercs" ADD CONSTRAINT "PK_final_thesis_cercs"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_final_thesis_cercs_classifier" ON "final_thesis_cercs" ("cercs_code" ASC);
CREATE INDEX "IXFK_final_thesis_cercs_final_thesis" ON "final_thesis_cercs" ("final_thesis_id" ASC);

/* Create Foreign Key Constraints */

ALTER TABLE "public"."final_thesis" ADD CONSTRAINT "FK_final_thesis_classifier_02"
	FOREIGN KEY ("language_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "final_thesis_cercs" ADD CONSTRAINT "FK_final_thesis_cercs_classifier"
	FOREIGN KEY ("cercs_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "final_thesis_cercs" ADD CONSTRAINT "FK_final_thesis_cercs_final_thesis"
	FOREIGN KEY ("final_thesis_id") REFERENCES "public"."final_thesis" ("id") ON DELETE No Action ON UPDATE No Action
;

alter TABLE "final_thesis" add column "curriculum_grade_id" bigint NULL;    -- viide õppekava kraadile
COMMENT ON COLUMN "public"."final_thesis"."curriculum_grade_id" 	IS 'viide õppekava kraadile';
CREATE INDEX "IXFK_final_thesis_curriculum_grade" ON "public"."final_thesis" ("curriculum_grade_id" ASC);
ALTER TABLE "public"."final_thesis" ADD CONSTRAINT "FK_final_thesis_curriculum_grade"	FOREIGN KEY ("curriculum_grade_id") REFERENCES "public"."curriculum_grade" ("id") ON DELETE No Action ON UPDATE No Action;


INSERT INTO classifier (code, value, name_et, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) 
VALUES ('LOPUTOO_KEEL', 'LOPUTOO_KEEL', 'Lõputöö keel', current_Timestamp(3), true, null, false, true, 0, 'Automaat');

INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_aa','aa','afari','LOPUTOO_KEEL',current_Timestamp(3), true, 'aa', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ab','ab','abhaasi','LOPUTOO_KEEL',current_Timestamp(3), true, 'ab', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ae','ae','avesta','LOPUTOO_KEEL',current_Timestamp(3), true, 'ae', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_af','af','afrikaani','LOPUTOO_KEEL',current_Timestamp(3), true, 'af', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_am','am','amhari','LOPUTOO_KEEL',current_Timestamp(3), true, 'am', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ar','ar','araabia','LOPUTOO_KEEL',current_Timestamp(3), true, 'ar', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_as','as','assami','LOPUTOO_KEEL',current_Timestamp(3), true, 'as', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_az','az','aserbaidzaani','LOPUTOO_KEEL',current_Timestamp(3), true, 'az', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ay','ay','aimaraa','LOPUTOO_KEEL',current_Timestamp(3), true, 'ay', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ba','ba','baškiiri','LOPUTOO_KEEL',current_Timestamp(3), true, 'ba', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_be','be','valgevene','LOPUTOO_KEEL',current_Timestamp(3), true, 'be', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_bg','bg','bulgaaria','LOPUTOO_KEEL',current_Timestamp(3), true, 'bg', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_bh','bh','bihaari','LOPUTOO_KEEL',current_Timestamp(3), true, 'bh', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_bi','bi','bislama','LOPUTOO_KEEL',current_Timestamp(3), true, 'bi', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_bn','bn','bengali','LOPUTOO_KEEL',current_Timestamp(3), true, 'bn', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_bo','bo','tiibeti','LOPUTOO_KEEL',current_Timestamp(3), true, 'bo', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_br','br','bretooni','LOPUTOO_KEEL',current_Timestamp(3), true, 'br', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_bs','bs','bosnia','LOPUTOO_KEEL',current_Timestamp(3), true, 'bs', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ca','ca','katalaani','LOPUTOO_KEEL',current_Timestamp(3), true, 'ca', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ce','ce','tsetseeni','LOPUTOO_KEEL',current_Timestamp(3), true, 'ce', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ch','ch','tžamorro','LOPUTOO_KEEL',current_Timestamp(3), true, 'ch', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_co','co','korsika','LOPUTOO_KEEL',current_Timestamp(3), true, 'co', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_cs','cs','tšehhi','LOPUTOO_KEEL',current_Timestamp(3), true, 'cs', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_cu','cu','kirikuslaavi','LOPUTOO_KEEL',current_Timestamp(3), true, 'cu', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_cv','cv','tšuvaši','LOPUTOO_KEEL',current_Timestamp(3), true, 'cv', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_cy','cy','kõmri','LOPUTOO_KEEL',current_Timestamp(3), true, 'cy', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_da','da','taani','LOPUTOO_KEEL',current_Timestamp(3), true, 'da', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_de','de','saksa','LOPUTOO_KEEL',current_Timestamp(3), true, 'de', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_dz','dz','dzongkha','LOPUTOO_KEEL',current_Timestamp(3), true, 'dz', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_el','el','kreeka','LOPUTOO_KEEL',current_Timestamp(3), true, 'el', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_en','en','inglise','LOPUTOO_KEEL',current_Timestamp(3), true, 'en', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_eo','eo','esperanto','LOPUTOO_KEEL',current_Timestamp(3), true, 'eo', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_es','es','hispaania','LOPUTOO_KEEL',current_Timestamp(3), true, 'es', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_et','et','eesti','LOPUTOO_KEEL',current_Timestamp(3), true, 'et', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_eu','eu','baski','LOPUTOO_KEEL',current_Timestamp(3), true, 'eu', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ev','ev','eesti / vene','LOPUTOO_KEEL',current_Timestamp(3), true, 'ev', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_fa','fa','pärsia','LOPUTOO_KEEL',current_Timestamp(3), true, 'fa', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_fi','fi','soome','LOPUTOO_KEEL',current_Timestamp(3), true, 'fi', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_fj','fj','fidži','LOPUTOO_KEEL',current_Timestamp(3), true, 'fj', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_fo','fo','fääri','LOPUTOO_KEEL',current_Timestamp(3), true, 'fo', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_fr','fr','prantsuse','LOPUTOO_KEEL',current_Timestamp(3), true, 'fr', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_fy','fy','friisi','LOPUTOO_KEEL',current_Timestamp(3), true, 'fy', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ga','ga','iiri','LOPUTOO_KEEL',current_Timestamp(3), true, 'ga', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_gd','gd','gaeli (šoti)','LOPUTOO_KEEL',current_Timestamp(3), true, 'gd', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_gl','gl','galeegi','LOPUTOO_KEEL',current_Timestamp(3), true, 'gl', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_gn','gn','guaranii','LOPUTOO_KEEL',current_Timestamp(3), true, 'gn', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_gu','gu','gudžarati','LOPUTOO_KEEL',current_Timestamp(3), true, 'gu', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_gv','gv','mänksi','LOPUTOO_KEEL',current_Timestamp(3), true, 'gv', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ha','ha','hausa','LOPUTOO_KEEL',current_Timestamp(3), true, 'ha', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_he','he','heebrea','LOPUTOO_KEEL',current_Timestamp(3), true, 'he', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_hi','hi','hindi','LOPUTOO_KEEL',current_Timestamp(3), true, 'hi', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ho','ho','hirimotu','LOPUTOO_KEEL',current_Timestamp(3), true, 'ho', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_hr','hr','horvaadi','LOPUTOO_KEEL',current_Timestamp(3), true, 'hr', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_hz','hz','herero','LOPUTOO_KEEL',current_Timestamp(3), true, 'hz', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_hu','hu','ungari','LOPUTOO_KEEL',current_Timestamp(3), true, 'hu', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_hy','hy','armeenia','LOPUTOO_KEEL',current_Timestamp(3), true, 'hy', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ia','ia','interlingua','LOPUTOO_KEEL',current_Timestamp(3), true, 'ia', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_id','id','indoneesia','LOPUTOO_KEEL',current_Timestamp(3), true, 'id', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ie','ie','interlingue','LOPUTOO_KEEL',current_Timestamp(3), true, 'ie', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ih','ih','inguši','LOPUTOO_KEEL',current_Timestamp(3), true, 'ih', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ik','ik','inupiaki','LOPUTOO_KEEL',current_Timestamp(3), true, 'ik', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_is','is','islandi','LOPUTOO_KEEL',current_Timestamp(3), true, 'is', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_it','it','itaalia','LOPUTOO_KEEL',current_Timestamp(3), true, 'it', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_iu','iu','inuktituti','LOPUTOO_KEEL',current_Timestamp(3), true, 'iu', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ja','ja','jaapani','LOPUTOO_KEEL',current_Timestamp(3), true, 'ja', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_jv','jv','jaava','LOPUTOO_KEEL',current_Timestamp(3), true, 'jv', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ka','ka','gruusia','LOPUTOO_KEEL',current_Timestamp(3), true, 'ka', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ki','ki','kikuju','LOPUTOO_KEEL',current_Timestamp(3), true, 'ki', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_kk','kk','kasahhi','LOPUTOO_KEEL',current_Timestamp(3), true, 'kk', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_kl','kl','grööni','LOPUTOO_KEEL',current_Timestamp(3), true, 'kl', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_km','km','khmeri','LOPUTOO_KEEL',current_Timestamp(3), true, 'km', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_kn','kn','kannada','LOPUTOO_KEEL',current_Timestamp(3), true, 'kn', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ko','ko','korea','LOPUTOO_KEEL',current_Timestamp(3), true, 'ko', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ks','ks','kašmiiri','LOPUTOO_KEEL',current_Timestamp(3), true, 'ks', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ku','ku','kurdi','LOPUTOO_KEEL',current_Timestamp(3), true, 'ku', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_kv','kv','komi','LOPUTOO_KEEL',current_Timestamp(3), true, 'kv', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_kw','kw','korni','LOPUTOO_KEEL',current_Timestamp(3), true, 'kw', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ky','ky','kirgiisi','LOPUTOO_KEEL',current_Timestamp(3), true, 'ky', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_la','la','ladina','LOPUTOO_KEEL',current_Timestamp(3), true, 'la', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_lb','lb','letseburgi','LOPUTOO_KEEL',current_Timestamp(3), true, 'lb', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ln','ln','lingala','LOPUTOO_KEEL',current_Timestamp(3), true, 'ln', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_lo','lo','lao','LOPUTOO_KEEL',current_Timestamp(3), true, 'lo', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_lt','lt','leedu','LOPUTOO_KEEL',current_Timestamp(3), true, 'lt', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_lv','lv','läti','LOPUTOO_KEEL',current_Timestamp(3), true, 'lv', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_mg','mg','malagassi','LOPUTOO_KEEL',current_Timestamp(3), true, 'mg', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_mh','mh','maršalli','LOPUTOO_KEEL',current_Timestamp(3), true, 'mh', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_mi','mi','maoori','LOPUTOO_KEEL',current_Timestamp(3), true, 'mi', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_mk','mk','makedoonia','LOPUTOO_KEEL',current_Timestamp(3), true, 'mk', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ml','ml','malajalami','LOPUTOO_KEEL',current_Timestamp(3), true, 'ml', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_mn','mn','mongoli','LOPUTOO_KEEL',current_Timestamp(3), true, 'mn', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_mo','mo','moldova','LOPUTOO_KEEL',current_Timestamp(3), true, 'mo', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_mr','mr','marathi','LOPUTOO_KEEL',current_Timestamp(3), true, 'mr', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ms','ms','malai','LOPUTOO_KEEL',current_Timestamp(3), true, 'ms', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_mt','mt','malta','LOPUTOO_KEEL',current_Timestamp(3), true, 'mt', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_mu','mu','mustlaskeel / romi','LOPUTOO_KEEL',current_Timestamp(3), true, 'mu', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_my','my','birma','LOPUTOO_KEEL',current_Timestamp(3), true, 'my', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_na','na','nauru','LOPUTOO_KEEL',current_Timestamp(3), true, 'na', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_nb','nb','norra kirjakeel','LOPUTOO_KEEL',current_Timestamp(3), true, 'nb', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_nd','nd','põhja-ndebele','LOPUTOO_KEEL',current_Timestamp(3), true, 'nd', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ne','ne','nepali','LOPUTOO_KEEL',current_Timestamp(3), true, 'ne', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ng','ng','ndonga','LOPUTOO_KEEL',current_Timestamp(3), true, 'ng', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_nl','nl','hollandi','LOPUTOO_KEEL',current_Timestamp(3), true, 'nl', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_nn','nn','uus-norra','LOPUTOO_KEEL',current_Timestamp(3), true, 'nn', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_no','no','norra','LOPUTOO_KEEL',current_Timestamp(3), true, 'no', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_nr','nr','lõuna-ndebele','LOPUTOO_KEEL',current_Timestamp(3), true, 'nr', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_nv','nv','navaho','LOPUTOO_KEEL',current_Timestamp(3), true, 'nv', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ny','ny','njandža','LOPUTOO_KEEL',current_Timestamp(3), true, 'ny', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_oc','oc','provansi','LOPUTOO_KEEL',current_Timestamp(3), true, 'oc', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_om','om','(afani) oromo','LOPUTOO_KEEL',current_Timestamp(3), true, 'om', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_or','or','oria','LOPUTOO_KEEL',current_Timestamp(3), true, 'or', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_os','os','osseedi','LOPUTOO_KEEL',current_Timestamp(3), true, 'os', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_pa','pa','pandžabi','LOPUTOO_KEEL',current_Timestamp(3), true, 'pa', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_pi','pi','pali','LOPUTOO_KEEL',current_Timestamp(3), true, 'pi', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_pl','pl','poola','LOPUTOO_KEEL',current_Timestamp(3), true, 'pl', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ps','ps','puštu','LOPUTOO_KEEL',current_Timestamp(3), true, 'ps', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_pt','pt','portugali','LOPUTOO_KEEL',current_Timestamp(3), true, 'pt', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_qu','qu','ketšua','LOPUTOO_KEEL',current_Timestamp(3), true, 'qu', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_rm','rm','retoromaani','LOPUTOO_KEEL',current_Timestamp(3), true, 'rm', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_rn','rn','rundi','LOPUTOO_KEEL',current_Timestamp(3), true, 'rn', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ro','ro','rumeenia','LOPUTOO_KEEL',current_Timestamp(3), true, 'ro', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ru','ru','vene','LOPUTOO_KEEL',current_Timestamp(3), true, 'ru', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_rw','rw','ruanda','LOPUTOO_KEEL',current_Timestamp(3), true, 'rw', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_sa','sa','sanskriti','LOPUTOO_KEEL',current_Timestamp(3), true, 'sa', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_sc','sc','sardi','LOPUTOO_KEEL',current_Timestamp(3), true, 'sc', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_sd','sd','sindhi','LOPUTOO_KEEL',current_Timestamp(3), true, 'sd', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_se','se','saami','LOPUTOO_KEEL',current_Timestamp(3), true, 'se', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_sg','sg','sango','LOPUTOO_KEEL',current_Timestamp(3), true, 'sg', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_si','si','singali','LOPUTOO_KEEL',current_Timestamp(3), true, 'si', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_sk','sk','slovaki','LOPUTOO_KEEL',current_Timestamp(3), true, 'sk', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_sl','sl','sloveeni','LOPUTOO_KEEL',current_Timestamp(3), true, 'sl', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_sm','sm','samoa','LOPUTOO_KEEL',current_Timestamp(3), true, 'sm', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_sn','sn','šona','LOPUTOO_KEEL',current_Timestamp(3), true, 'sn', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_so','so','somaali','LOPUTOO_KEEL',current_Timestamp(3), true, 'so', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_sq','sq','albaania','LOPUTOO_KEEL',current_Timestamp(3), true, 'sq', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_sr','sr','serbia','LOPUTOO_KEEL',current_Timestamp(3), true, 'sr', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ss','ss','svaasi','LOPUTOO_KEEL',current_Timestamp(3), true, 'ss', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_st','st','suuto (soto)','LOPUTOO_KEEL',current_Timestamp(3), true, 'st', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_su','su','sunda','LOPUTOO_KEEL',current_Timestamp(3), true, 'su', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_sv','sv','rootsi','LOPUTOO_KEEL',current_Timestamp(3), true, 'sv', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_sw','sw','suahiili','LOPUTOO_KEEL',current_Timestamp(3), true, 'sw', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_za','za','tžuangi','LOPUTOO_KEEL',current_Timestamp(3), true, 'za', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_zh','zh','hiina','LOPUTOO_KEEL',current_Timestamp(3), true, 'zh', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_zu','zu','suulu','LOPUTOO_KEEL',current_Timestamp(3), true, 'zu', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ta','ta','tamili','LOPUTOO_KEEL',current_Timestamp(3), true, 'ta', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_te','te','telugu','LOPUTOO_KEEL',current_Timestamp(3), true, 'te', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_tg','tg','tadziki','LOPUTOO_KEEL',current_Timestamp(3), true, 'tg', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_th','th','tai (siiami)','LOPUTOO_KEEL',current_Timestamp(3), true, 'th', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ti','ti','tigrinja','LOPUTOO_KEEL',current_Timestamp(3), true, 'ti', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_tk','tk','turkmeeni','LOPUTOO_KEEL',current_Timestamp(3), true, 'tk', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_tl','tl','tagalogi','LOPUTOO_KEEL',current_Timestamp(3), true, 'tl', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_tn','tn','tsvaana','LOPUTOO_KEEL',current_Timestamp(3), true, 'tn', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_to','to','tonga','LOPUTOO_KEEL',current_Timestamp(3), true, 'to', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_tr','tr','türgi','LOPUTOO_KEEL',current_Timestamp(3), true, 'tr', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ts','ts','tsonga','LOPUTOO_KEEL',current_Timestamp(3), true, 'ts', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_tt','tt','tatari','LOPUTOO_KEEL',current_Timestamp(3), true, 'tt', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_tw','tw','tvii','LOPUTOO_KEEL',current_Timestamp(3), true, 'tw', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ty','ty','tahiti','LOPUTOO_KEEL',current_Timestamp(3), true, 'ty', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ug','ug','uiguuri','LOPUTOO_KEEL',current_Timestamp(3), true, 'ug', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_uk','uk','ukraina','LOPUTOO_KEEL',current_Timestamp(3), true, 'uk', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ur','ur','urdu','LOPUTOO_KEEL',current_Timestamp(3), true, 'ur', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_uz','uz','usbeki','LOPUTOO_KEEL',current_Timestamp(3), true, 'uz', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_ve','ve','viipekeel (eesti)','LOPUTOO_KEEL',current_Timestamp(3), true, 've', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_vi','vi','vietnami','LOPUTOO_KEEL',current_Timestamp(3), true, 'vi', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_vo','vo','volapüki','LOPUTOO_KEEL',current_Timestamp(3), true, 'vo', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_wo','wo','volofi','LOPUTOO_KEEL',current_Timestamp(3), true, 'wo', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_vv','vv','viipekeel (vene)','LOPUTOO_KEEL',current_Timestamp(3), true, 'vv', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_xh','xh','koosa','LOPUTOO_KEEL',current_Timestamp(3), true, 'xh', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_xx','xx','muu','LOPUTOO_KEEL',current_Timestamp(3), true, 'xx', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_yi','yi','jidiži','LOPUTOO_KEEL',current_Timestamp(3), true, 'yi', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('LOPUTOO_KEEL_yo','yo','joruba','LOPUTOO_KEEL',current_Timestamp(3), true, 'yo', false, true, 0, 'Automaat');

INSERT INTO classifier (code, value, name_et, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE', 'CERCS_TYPE', 'Teaduseriala valdkond', current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS', 'CERCS', 'Teaduseriala (CERCS)', current_Timestamp(3), true, null, false, true, 0, 'Automaat');

INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_2_3','2_3','Ajalooteadused ja arheoloogia','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_4_1','4_1','Arhitektuur ja tööstusdisain','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_4_6','4_6','Arvutiteadused','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_1_12','1_12','Bio- ja keskkonnateadustega seotud uuringud, näiteks biotehnoloogia, molekulaarbioloogia, rakubioloogia, biofüüsika, majandus- ja tehnoloogiauuringud','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_1_1','1_1','Biokeemia','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_3_1','3_1','Biomeditsiin','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_4_16','4_16','Biotehnoloogia (loodusteadused ja tehnika)','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_4_15','4_15','Ehitus- ja kommunaaltehnika','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_4_8','4_8','Elektrotehnika ja elektroonika','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_4_17','4_17','Energeetikaalased uuringud','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_3_3','3_3','Farmaatsia','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_2_6','2_6','Filoloogia ja lingvistika','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_2_1','2_1','Filosoofia','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_4_10','4_10','Füüsika','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_1_3','1_3','Geneetika','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_1_10','1_10','Geograafia ja regionaaluuringud','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_4_7','4_7','Info- ja kommunikatsioonitehnoloogia','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_2_10','2_10','Kasvatusteadused','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_4_11','4_11','Keemia ja keemiatehnika','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_3_10','3_10','Keskkonna- ja töötervishoid','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_1_9','1_9','Keskkonnaohtlikke aineid käsitlevad uuringud','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_1_11','1_11','Keskkonnapoliitika, keskkonnamajandus, keskkonnaõigus','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_1_8','1_8','Keskkonnaseisundit ja keskkonnakaitset hõlmavad uuringud','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_3_7','3_7','Kliiniline meditsiin','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_2_14','2_14','Kommunikatsiooni- ja infoteadused','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_4_3','4_3','Kosmoseuuringud ja astronoomia','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_2_4','2_4','Kultuuriuuringud','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_2_5','2_5','Kunstiteadus','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_2_9','2_9','Logopeedia','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_4_2','4_2','Maateadused','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_2_12','2_12','Majandusteadus','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_4_4','4_4','Matemaatika','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_4_9','4_9','Meditsiinitehnika','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_4_13','4_13','Mehhanotehnika, automaatika, tööstustehnoloogia','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_1_5','1_5','Metsandusteadus','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_1_2','1_2','Mikrobioloogia','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_4_12','4_12','Protsessitehnoloogia ja materjaliteadus','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_2_8','2_8','Psühholoogia','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_1_6','1_6','Põllumajandusteadus','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_3_6','3_6','Rahvatervishoid','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_2_13','2_13','Riigiteadused','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_2_11','2_11','Sotsiaalteadused','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_3_8','3_8','Sporditeadus','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_4_5','4_5','Statistika','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_3_4','3_4','Stomatoloogia','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_4_18','4_18','Sõjateadus ja -tehnoloogia','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_2_2','2_2','Teoloogia','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_3_11','3_11','Terviseuuringutega seotud uuringud, näiteks biokeemia, geneetika, mikrobioloogia, biotehnoloogia, molekulaarbioloogia, rakubioloogia, biofüüsika ja bioinformaatika','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_1_7','1_7','Toiduteadused','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_3_9','3_9','Toitumisteadus','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_4_14','4_14','Tootmistehnika ja tootmisjuhtimine','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_3_2','3_2','Veterinaarmeditsiin','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_3_5','3_5','Õendusteadus/õendus','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_2_7','2_7','Õigusteadus','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_TYPE_1_4','1_4','Ökoloogia, biosüstemaatika ja -füsioloogia','CERCS_TYPE',current_Timestamp(3), true, null, false, true, 0, 'Automaat');


INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B240','B240','(Inimeste ja loomade) parasitoloogia (B240)','CERCS',current_Timestamp(3), true, 'B240', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H660','H660','Aafrika keeled ja kirjandus (H660)','CERCS',current_Timestamp(3), true, 'H660', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P230','P230','Aatomi- ja molekulaarfüüsika (P230)','CERCS',current_Timestamp(3), true, 'P230', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B434','B434','Agrokeemia (B434)','CERCS',current_Timestamp(3), true, 'B434', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H200','H200','Ajalooteooria (H200)','CERCS',current_Timestamp(3), true, 'H200', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B402','B402','Akvakultuuride kasvatamine, kalakasvatus (B402)','CERCS',current_Timestamp(3), true, 'B402', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H670','H670','Ameerika keeled ja kirjandus, kreoolikeeled (H670)','CERCS',current_Timestamp(3), true, 'H670', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P300','P300','Analüütiline keemia (P300)','CERCS',current_Timestamp(3), true, 'P300', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B590','B590','Anestesioloogia, intensiivravi (B590)','CERCS',current_Timestamp(3), true, 'B590', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P360','P360','Anorgaaniline keemia (P360)','CERCS',current_Timestamp(3), true, 'P360', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H210','H210','Antiikajalugu (H210)','CERCS',current_Timestamp(3), true, 'H210', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S195','S195','Arengualane koostöö (S195)','CERCS',current_Timestamp(3), true, 'S195', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B350','B350','Arengubioloogia, loomade kasv, ontogenees, embrüoloogia (B350)','CERCS',current_Timestamp(3), true, 'B350', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B450','B450','Arengubioloogia, teratoloogia, ontogenees, (inim)embrüoloogia (B450)','CERCS',current_Timestamp(3), true, 'B450', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S262','S262','Arengupsühholoogia (S262)','CERCS',current_Timestamp(3), true, 'S262', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S188','S188','Arenguökonoomika (S188)','CERCS',current_Timestamp(3), true, 'S188', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H340','H340','Arheoloogia (H340)','CERCS',current_Timestamp(3), true, 'H340', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T240','T240','Arhitektuur, sisekujundus (T240)','CERCS',current_Timestamp(3), true, 'T240', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P120','P120','Arvuteooria, väljateooria, algebraline geomeetria, algebra, rühmateooria (P120)','CERCS',current_Timestamp(3), true, 'P120', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S281','S281','Arvuti õpiprogrammide kasutamise metoodika ja pedagoogika (S281)','CERCS',current_Timestamp(3), true, 'S281', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P170','P170','Arvutiteadus, arvutusmeetodid, süsteemid, juhtimine (automaatjuhtimisteooria) (P170)','CERCS',current_Timestamp(3), true, 'P170', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P520','P520','Astronoomia, kosmoseuuringud, kosmosekeemia (P520)','CERCS',current_Timestamp(3), true, 'P520', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H650','H650','Austraalia ja Melaneesia ning paapua keeled ja kirjandus (H650)','CERCS',current_Timestamp(3), true, 'H650', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T125','T125','Automatiseerimine, robootika, control engineering (T125)','CERCS',current_Timestamp(3), true, 'T125', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S140','S140','Avalik õigus (S140)','CERCS',current_Timestamp(3), true, 'S140', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B401','B401','Avikultuuride kasvatamine (B401)','CERCS',current_Timestamp(3), true, 'B401', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H590','H590','Balti ja slaavi keeled ja kirjandus (H590)','CERCS',current_Timestamp(3), true, 'H590', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H105','H105','Bibliograafia (H105)','CERCS',current_Timestamp(3), true, 'H105', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P330','P330','Bioenergeetika (P330)','CERCS',current_Timestamp(3), true, 'P330', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B110','B110','Bioinformaatika, meditsiiniinformaatika, biomatemaatika, biomeetrika (B110)','CERCS',current_Timestamp(3), true, 'B110', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T360','T360','Biokeemiatehnoloogia (T360)','CERCS',current_Timestamp(3), true, 'T360', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B100','B100','Biomeditsiini ajalugu ja filosoofia, teoreetiline bioloogia, evolutsiooni üldised küsimused (B100)','CERCS',current_Timestamp(3), true, 'B100', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B115','B115','Biomehaanika, küberneetika (B115)','CERCS',current_Timestamp(3), true, 'B115', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T490','T490','Biotehnoloogia (T490)','CERCS',current_Timestamp(3), true, 'T490', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S250','S250','Demograafia (S250)','CERCS',current_Timestamp(3), true, 'S250', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B630','B630','Dermatoloogia, veneroloogia (B630)','CERCS',current_Timestamp(3), true, 'B630', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B725','B725','Diagnostika (B725)','CERCS',current_Timestamp(3), true, 'B725', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H401','H401','Dialektoloogia (H401)','CERCS',current_Timestamp(3), true, 'H401', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H100','H100','Dokumentatsioon, informatsioon, raamatukogundus, arhiivindus (H100)','CERCS',current_Timestamp(3), true, 'H100', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H330','H330','Draamakunst (H330)','CERCS',current_Timestamp(3), true, 'H330', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H341','H341','Eelajalugu (H341)','CERCS',current_Timestamp(3), true, 'H341', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H345','H345','Egüptoloogia (H345)','CERCS',current_Timestamp(3), true, 'H345', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S264','S264','Eksperimantaal- ja rakenduspsühholoogia (S264)','CERCS',current_Timestamp(3), true, 'S264', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S284','S284','Eksperimentaalpedagoogika (S284)','CERCS',current_Timestamp(3), true, 'S284', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P401','P401','Elektrokeemia (P401)','CERCS',current_Timestamp(3), true, 'P401', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P200','P200','Elektromagnetism, optika, akustika (P200)','CERCS',current_Timestamp(3), true, 'P200', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T170','T170','Elektroonika (T170)','CERCS',current_Timestamp(3), true, 'T170', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T190','T190','Elektrotehnika (T190)','CERCS',current_Timestamp(3), true, 'T190', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P210','P210','Elementaarosakeste füüsika, kvantväljade teooria (P210)','CERCS',current_Timestamp(3), true, 'P210', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B480','B480','Endokrinoloogia, sekretsioonisüsteemid, diabetoloogia (B480)','CERCS',current_Timestamp(3), true, 'B480', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T140','T140','Energeetika (T140)','CERCS',current_Timestamp(3), true, 'T140', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B250','B250','Entomoloogia, taimede parasitoloogia (B250)','CERCS',current_Timestamp(3), true, 'B250', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S261','S261','Eri- ja individuaalpsühholoogia (S261)','CERCS',current_Timestamp(3), true, 'S261', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H150','H150','Eriteaduste filosoofia (H150)','CERCS',current_Timestamp(3), true, 'H150', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S271','S271','Erivajadustega inimeste õpetamine, eripedagoogika (S271)','CERCS',current_Timestamp(3), true, 'S271', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H315','H315','Esteetika (H315)','CERCS',current_Timestamp(3), true, 'H315', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S190','S190','Ettevõtete juhtimine (S190)','CERCS',current_Timestamp(3), true, 'S190', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S155','S155','Euroopa õigus (S155)','CERCS',current_Timestamp(3), true, 'S155', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T410','T410','Farmaatsiatooted ja nende tehnoloogiad (T410)','CERCS',current_Timestamp(3), true, 'T410', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B740','B740','Farmakoloogia, farmakognoosia, farmaatsia, toksikoloogia (B740)','CERCS',current_Timestamp(3), true, 'B740', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H135','H135','Fenomenoloogia (H135)','CERCS',current_Timestamp(3), true, 'H135', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H130','H130','Filosoofia ajalugu (H130)','CERCS',current_Timestamp(3), true, 'H130', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H125','H125','Filosoofiline antropoloogia (H125)','CERCS',current_Timestamp(3), true, 'H125', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H140','H140','Filosoofiline loogika (H140)','CERCS',current_Timestamp(3), true, 'H140', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S182','S182','Finantsid ja kindlustus (S182)','CERCS',current_Timestamp(3), true, 'S182', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H400','H400','Folkloristika (H400)','CERCS',current_Timestamp(3), true, 'H400', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H351','H351','Foneetika, fonoloogia (H351)','CERCS',current_Timestamp(3), true, 'H351', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P402','P402','Fotokeemia (P402)','CERCS',current_Timestamp(3), true, 'P402', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H560','H560','Friisi keel ja kirjandus (H560)','CERCS',current_Timestamp(3), true, 'H560', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P130','P130','Funktsioonid, diferentsiaalvõrrandid (P130)','CERCS',current_Timestamp(3), true, 'P130', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B470','B470','Füsioloogia (B470)','CERCS',current_Timestamp(3), true, 'B470', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B130','B130','Füsioloogiline biofüüsika (B130)','CERCS',current_Timestamp(3), true, 'B130', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P400','P400','Füüsikaline keemia (P400)','CERCS',current_Timestamp(3), true, 'P400', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B460','B460','Füüsiline antropoloogia (B460)','CERCS',current_Timestamp(3), true, 'B460', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P510','P510','Füüsiline geograafia, geomorfoloogia, mullateadus, kartograafia, klimatoloogia (P510)','CERCS',current_Timestamp(3), true, 'P510', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P240','P240','Gaasid, vedelike dünaamika, plasma (P240)','CERCS',current_Timestamp(3), true, 'P240', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B550','B550','Gastro-enteroloogia (B550)','CERCS',current_Timestamp(3), true, 'B550', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H347','H347','Genealoogia ja heraldika (H347)','CERCS',current_Timestamp(3), true, 'H347', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B220','B220','Geneetika, tsütogeneetika (B220)','CERCS',current_Timestamp(3), true, 'B220', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P515','P515','Geodeesia (P515)','CERCS',current_Timestamp(3), true, 'P515', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P500','P500','Geofüüsika, füüsikaline okeanograafia, meteoroloogia (P500)','CERCS',current_Timestamp(3), true, 'P500', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P150','P150','Geomeetria, algebraline topoloogia (P150)','CERCS',current_Timestamp(3), true, 'P150', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H520','H520','Germaani filoloogia (H520)','CERCS',current_Timestamp(3), true, 'H520', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B670','B670','Gerontoloogia (B670)','CERCS',current_Timestamp(3), true, 'B670', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H352','H352','Grammatika, semantika, semiootika, süntaks (H352)','CERCS',current_Timestamp(3), true, 'H352', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S111','S111','Haldusõigus (S111)','CERCS',current_Timestamp(3), true, 'S111', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B490','B490','Hematoloogia (B490)','CERCS',current_Timestamp(3), true, 'B490', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B540','B540','Hingamissüsteemi haigused (B540)','CERCS',current_Timestamp(3), true, 'B540', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H490','H490','Hispaania ja portugali keel ja kirjandus (H490)','CERCS',current_Timestamp(3), true, 'H490', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B210','B210','Histoloogia, tsütokeemia, histokeemia, koekultuurid (B210)','CERCS',current_Timestamp(3), true, 'B210', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H540','H540','Hollandi keel (H540)','CERCS',current_Timestamp(3), true, 'H540', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H550','H550','Hollandi kirjandus (H550)','CERCS',current_Timestamp(3), true, 'H550', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T230','T230','Hooneehitus (T230)','CERCS',current_Timestamp(3), true, 'T230', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B260','B260','Hüdrobioloogia, mere-bioloogia, veeökoloogia, limnoloogia (B260)','CERCS',current_Timestamp(3), true, 'B260', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P470','P470','Hüdrogeoloogia, geoplaneering ja ehitusgeoloogia (P470)','CERCS',current_Timestamp(3), true, 'P470', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B432','B432','Ilutaimed (B432)','CERCS',current_Timestamp(3), true, 'B432', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B500','B500','Immunoloogia, seroloogia, transplantoloogia (B500)','CERCS',current_Timestamp(3), true, 'B500', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P175','P175','Informaatika, süsteemiteooria (P175)','CERCS',current_Timestamp(3), true, 'P175', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S123','S123','Informaatikaõigus (S123)','CERCS',current_Timestamp(3), true, 'S123', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H570','H570','Inglise keel ja kirjandus (H570)','CERCS',current_Timestamp(3), true, 'H570', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B440','B440','Inimanatoomia ja -morfoloogia (B440)','CERCS',current_Timestamp(3), true, 'B440', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B381','B381','Inimetoloogia (B381)','CERCS',current_Timestamp(3), true, 'B381', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S112','S112','Inimõigused (S112)','CERCS',current_Timestamp(3), true, 'S112', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T110','T110','Instrumentatsioonitehnoloogia (T110)','CERCS',current_Timestamp(3), true, 'T110', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H480','H480','Itaalia keel ja kirjandus (H480)','CERCS',current_Timestamp(3), true, 'H480', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P140','P140','Jadad, Fourier analüüs, funktsionaalanalüüs (P140)','CERCS',current_Timestamp(3), true, 'P140', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H230','H230','Kaasaja ajalugu (kuni umbes aastani 1800) (H230)','CERCS',current_Timestamp(3), true, 'H230', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T340','T340','Kaevandamine/mäendus (T340)','CERCS',current_Timestamp(3), true, 'T340', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H165','H165','Kanooniline õigus (H165)','CERCS',current_Timestamp(3), true, 'H165', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S185','S185','Kaubandus- ja tööstusökonoomika (S185)','CERCS',current_Timestamp(3), true, 'S185', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T181','T181','Kaugseire (T181)','CERCS',current_Timestamp(3), true, 'T181', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H355','H355','Keele ajalugu (H355)','CERCS',current_Timestamp(3), true, 'H355', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H350','H350','Keeleteadus (H350)','CERCS',current_Timestamp(3), true, 'H350', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T350','T350','Keemiatehnoloogia ja -masinaehitus (T350)','CERCS',current_Timestamp(3), true, 'T350', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S273','S273','Kehaline kasvatus ja motoorika, sport (S273)','CERCS',current_Timestamp(3), true, 'S273', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H510','H510','Keldi keeled ja kirjandus (H510)','CERCS',current_Timestamp(3), true, 'H510', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T153','T153','Keraamilised materjalid ja -pulbrid (T153)','CERCS',current_Timestamp(3), true, 'T153', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H220','H220','Keskaja ajalugu (H220)','CERCS',current_Timestamp(3), true, 'H220', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P305','P305','Keskkonnakeemia (P305)','CERCS',current_Timestamp(3), true, 'P305', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T270','T270','Keskkonnatehnoloogia, reostuskontroll (T270)','CERCS',current_Timestamp(3), true, 'T270', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B700','B700','Keskkonnatervis (B700)','CERCS',current_Timestamp(3), true, 'B700', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S120','S120','Keskkonnaõigus (S120)','CERCS',current_Timestamp(3), true, 'S120', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S137','S137','Kindlustusõigus (S137)','CERCS',current_Timestamp(3), true, 'S137', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B600','B600','Kirurgia, ortopeedia, traumatoloogia (B600)','CERCS',current_Timestamp(3), true, 'B600', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B726','B726','Kliiniline bioloogia (B726)','CERCS',current_Timestamp(3), true, 'B726', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B140','B140','Kliiniline füüsika, radioloogia, tomograafia, meditsiinitehnika (B140)','CERCS',current_Timestamp(3), true, 'B140', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B790','B790','Kliiniline geneetika (B790)','CERCS',current_Timestamp(3), true, 'B790', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B190','B190','Kliiniline keemia (B190)','CERCS',current_Timestamp(3), true, 'B190', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H280','H280','Kohalik ja piirkondlik ajalugu, ajalooline geograafia alates keskajast (H280)','CERCS',current_Timestamp(3), true, 'H280', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S142','S142','Kohtuõigus (S142)','CERCS',current_Timestamp(3), true, 'S142', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H290','H290','Koloniaalajalugu (H290)','CERCS',current_Timestamp(3), true, 'H290', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T152','T152','Komposiitmaterjalid (T152)','CERCS',current_Timestamp(3), true, 'T152', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S148','S148','Konstitutsiooniõigus (S148)','CERCS',current_Timestamp(3), true, 'S148', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T320','T320','Kosmosetehnoloogia (T320)','CERCS',current_Timestamp(3), true, 'T320', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H420','H420','Kreeka keel (H420)','CERCS',current_Timestamp(3), true, 'H420', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H430','H430','Kreeka kirjandus (H430)','CERCS',current_Timestamp(3), true, 'H430', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S149','S149','Kriminaalõigus ja -protsess (S149)','CERCS',current_Timestamp(3), true, 'S149', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S160','S160','Kriminoloogia (S160)','CERCS',current_Timestamp(3), true, 'S160', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H180','H180','Kristliku kiriku ajalugu (H180)','CERCS',current_Timestamp(3), true, 'H180', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T510','T510','Kronoloogia, vanusemääramise tehnoloogia (T510)','CERCS',current_Timestamp(3), true, 'T510', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S220','S220','Kultuuriantropoloogia, etnoloogia (S220)','CERCS',current_Timestamp(3), true, 'S220', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H310','H310','Kunstiajalugu (H310)','CERCS',current_Timestamp(3), true, 'H310', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H313','H313','Kunstikriitika (H313)','CERCS',current_Timestamp(3), true, 'H313', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H314','H314','Kunstiteoste säilitamine ja restaureerimine (H314)','CERCS',current_Timestamp(3), true, 'H314', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P211','P211','Kõrgenergeetiliste vastasmõjude uuringud, kosmiline kiirgus (P211)','CERCS',current_Timestamp(3), true, 'P211', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T191','T191','Kõrgsagedustehnoloogia, mikrolained (T191)','CERCS',current_Timestamp(3), true, 'T191', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H440','H440','Ladina keel (H440)','CERCS',current_Timestamp(3), true, 'H440', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H450','H450','Ladina kirjandus (H450)','CERCS',current_Timestamp(3), true, 'H450', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T165','T165','Lasertehnoloogia (T165)','CERCS',current_Timestamp(3), true, 'T165', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H353','H353','Leksikoloogia (H353)','CERCS',current_Timestamp(3), true, 'H353', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S240','S240','Linna ja maa planeerimine (S240)','CERCS',current_Timestamp(3), true, 'S240', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P340','P340','Lipiidid, steroidid, membraanid (P340)','CERCS',current_Timestamp(3), true, 'P340', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T380','T380','Looduslike kütteõlide, rasvade ja vahade tootmistehnoloogia (T380)','CERCS',current_Timestamp(3), true, 'T380', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B340','B340','Loomade anatoomia ja morfoloogia (B340)','CERCS',current_Timestamp(3), true, 'B340', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B380','B380','Loomaetoloogia ja -psühholoogia (B380)','CERCS',current_Timestamp(3), true, 'B380', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B360','B360','Loomafüsioloogia (B360)','CERCS',current_Timestamp(3), true, 'B360', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B280','B280','Loomaökoloogia (B280)','CERCS',current_Timestamp(3), true, 'B280', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H250','H250','Lähiajalugu (alates 1914) (H250)','CERCS',current_Timestamp(3), true, 'H250', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B433','B433','Lämmastikuomandamine (B433)','CERCS',current_Timestamp(3), true, 'B433', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H640','H640','Lõuna ja Kagu-Aasia ning Hiina keeled ja kirjandus (H640)','CERCS',current_Timestamp(3), true, 'H640', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H311','H311','Maalikunst (H311)','CERCS',current_Timestamp(3), true, 'H311', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T280','T280','Maanteetransporditehnoloogia (T280)','CERCS',current_Timestamp(3), true, 'T280', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T250','T250','Maastikukujundus (T250)','CERCS',current_Timestamp(3), true, 'T250', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P430','P430','Maavarad, majandusgeoloogia (P430)','CERCS',current_Timestamp(3), true, 'P430', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S180','S180','Majandus, ökonomeetrika, majandusteooria, majanduslikud süsteemid, majanduspoliitika (S180)','CERCS',current_Timestamp(3), true, 'S180', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S184','S184','Majanduslik planeerimine (S184)','CERCS',current_Timestamp(3), true, 'S184', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S183','S183','Majandustsüklid (S183)','CERCS',current_Timestamp(3), true, 'S183', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P370','P370','Makromolekulaarkeemia (P370)','CERCS',current_Timestamp(3), true, 'P370', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T210','T210','Masinaehitus, hüdraulika, vaakumtehnoloogia, vibratsioonakustiline tehnoloogia (T210)','CERCS',current_Timestamp(3), true, 'T210', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P190','P190','Matemaatiline ja üldine teoreetiline füüsika, klassikaline mehaanika, kvantmehaanika, relatiivsus, gravitatsioon, statistiline füüsika, termodünaamika (P190)','CERCS',current_Timestamp(3), true, 'P190', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P110','P110','Matemaatiline loogika, hulgateooria, kombinatoorika (P110)','CERCS',current_Timestamp(3), true, 'P110', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T150','T150','Materjalitehnoloogia (T150)','CERCS',current_Timestamp(3), true, 'T150', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T115','T115','Meditsiinitehnika (T115)','CERCS',current_Timestamp(3), true, 'T115', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B770','B770','Meditsiiniõigus (B770)','CERCS',current_Timestamp(3), true, 'B770', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S265','S265','Meedia ja kommunikatsiooniteadused (S265)','CERCS',current_Timestamp(3), true, 'S265', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S122','S122','Meediaõigus (S122)','CERCS',current_Timestamp(3), true, 'S122', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S151','S151','Mere-, õhusõidu- ja kosmoseõigus (S151)','CERCS',current_Timestamp(3), true, 'S151', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T450','T450','Metallitehnoloogia, metallurgia, metallitooted (T450)','CERCS',current_Timestamp(3), true, 'T450', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P180','P180','Metroloogia, instrumentatsioon (P180)','CERCS',current_Timestamp(3), true, 'P180', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B430','B430','Metsakasvatus, metsandus, metsandustehnoloogia (B430)','CERCS',current_Timestamp(3), true, 'B430', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B230','B230','Mikrobioloogia, bakterioloogia, viroloogia, mükoloogia (B230)','CERCS',current_Timestamp(3), true, 'B230', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T171','T171','Mikroelektroonika (T171)','CERCS',current_Timestamp(3), true, 'T171', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H190','H190','Mittekristlikud religioonid (H190)','CERCS',current_Timestamp(3), true, 'H190', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T440','T440','Mittemetalliliste mineraalide tehnoloogia (T440)','CERCS',current_Timestamp(3), true, 'T440', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B120','B120','Molekulaarne biofüüsika (B120)','CERCS',current_Timestamp(3), true, 'B120', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T455','T455','Mootorid ja ajamid (T455)','CERCS',current_Timestamp(3), true, 'T455', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H155','H155','Moraaliõpetus (H155)','CERCS',current_Timestamp(3), true, 'H155', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B410','B410','Mullateadus, põllumajanduslik hüdroloogia (B410)','CERCS',current_Timestamp(3), true, 'B410', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H320','H320','Musikoloogia (H320)','CERCS',current_Timestamp(3), true, 'H320', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T480','T480','Muude toodete tehnoloogia (T480)','CERCS',current_Timestamp(3), true, 'T480', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B510','B510','Nakkushaigused (B510)','CERCS',current_Timestamp(3), true, 'B510', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H361','H361','Neurolingvistika (H361)','CERCS',current_Timestamp(3), true, 'H361', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B640','B640','Neuroloogia, neuropsühholoogia, neurofüsioloogia (B640)','CERCS',current_Timestamp(3), true, 'B640', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S121','S121','Noorsooõigus (S121)','CERCS',current_Timestamp(3), true, 'S121', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S145','S145','Notariõigus (S145)','CERCS',current_Timestamp(3), true, 'S145', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B145','B145','Nukleaarmeditsiin, radiobioloogia (B145)','CERCS',current_Timestamp(3), true, 'B145', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P320','P320','Nukleiinhappesüntees, proteiinisüntees (P320)','CERCS',current_Timestamp(3), true, 'P320', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H346','H346','Numismaatika ja sigillograafia (H346)','CERCS',current_Timestamp(3), true, 'H346', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B620','B620','Oftalmoloogia (B620)','CERCS',current_Timestamp(3), true, 'B620', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H370','H370','Onomastika (H370)','CERCS',current_Timestamp(3), true, 'H370', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T151','T151','Optilised materjalid (T151)','CERCS',current_Timestamp(3), true, 'T151', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P390','P390','Orgaaniline keemia (P390)','CERCS',current_Timestamp(3), true, 'P390', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S189','S189','Organisatsiooniteadus (S189)','CERCS',current_Timestamp(3), true, 'S189', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P395','P395','Organometalliline keemia (P395)','CERCS',current_Timestamp(3), true, 'P395', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B730','B730','Ortodontia, stomatoloogia (B730)','CERCS',current_Timestamp(3), true, 'B730', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S286','S286','Ortopedagoogika (S286)','CERCS',current_Timestamp(3), true, 'S286', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B610','B610','Otolarüngoloogia, audioloogia, kõne ja kuulmishäired (B610)','CERCS',current_Timestamp(3), true, 'B610', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H630','H630','Paleo-Siberi keeled ja kirjandus, Korea ja Jaapani keel ja kirjandus (H630)','CERCS',current_Timestamp(3), true, 'H630', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B300','B300','Paleobotaanika, fülogenees, palünoloogia (B300)','CERCS',current_Timestamp(3), true, 'B300', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H110','H110','Paleograafia, raamatuteadus, epigraafia, papüroloogia (H110)','CERCS',current_Timestamp(3), true, 'H110', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B330','B330','Paleozooloogia, fülogenees (B330)','CERCS',current_Timestamp(3), true, 'B330', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S124','S124','Patendid, autoriõigus, kaubamärgid (S124)','CERCS',current_Timestamp(3), true, 'S124', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S270','S270','Pedagoogika ja didaktika (S270)','CERCS',current_Timestamp(3), true, 'S270', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B660','B660','Pediaatria (B660)','CERCS',current_Timestamp(3), true, 'B660', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B720','B720','Peremeditsiin (B720)','CERCS',current_Timestamp(3), true, 'B720', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P420','P420','Petroloogia, mineroloogia, geokeemia (P420)','CERCS',current_Timestamp(3), true, 'P420', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H170','H170','Piibliteadus (H170)','CERCS',current_Timestamp(3), true, 'H170', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T111','T111','Pilditehnika (T111)','CERCS',current_Timestamp(3), true, 'T111', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T155','T155','Pinded ja pinnatehnoloogia (T155)','CERCS',current_Timestamp(3), true, 'T155', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P352','P352','Pinna- ja piirpindade keemia (P352)','CERCS',current_Timestamp(3), true, 'P352', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S175','S175','Polemololoogia (S175)','CERCS',current_Timestamp(3), true, 'S175', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H271','H271','Poliitika ajalugu (H271)','CERCS',current_Timestamp(3), true, 'H271', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S170','S170','Poliitikateadused, administreerimine (S170)','CERCS',current_Timestamp(3), true, 'S170', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T390','T390','Polümeeride tehnoloogia, biopolümeerid (T390)','CERCS',current_Timestamp(3), true, 'T390', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P265','P265','Pooljuhtide füüsika (P265)','CERCS',current_Timestamp(3), true, 'P265', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H460','H460','Prantsuse keel (H460)','CERCS',current_Timestamp(3), true, 'H460', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H470','H470','Prantsuse kirjandus (H470)','CERCS',current_Timestamp(3), true, 'H470', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P310','P310','Proteiinid, ensümoloogia (P310)','CERCS',current_Timestamp(3), true, 'P310', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B235','B235','Protozooloogia (B235)','CERCS',current_Timestamp(3), true, 'B235', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B650','B650','Psühhiaatria, kliiniline psühholoogia, psühhosomaatika (B650)','CERCS',current_Timestamp(3), true, 'B650', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S260','S260','Psühholoogia (S260)','CERCS',current_Timestamp(3), true, 'S260', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B760','B760','Psühhonoomika (B760)','CERCS',current_Timestamp(3), true, 'B760', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S283','S283','Psühhopedagoogika (S283)','CERCS',current_Timestamp(3), true, 'S283', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T400','T400','Puhaste kemikaalide tootmistehnoloogia, värvid (T400)','CERCS',current_Timestamp(3), true, 'T400', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T460','T460','Puidu-, tselluloosi- ja paberitehnoloogia (T460)','CERCS',current_Timestamp(3), true, 'T460', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S216','S216','Puuetega inimeste eest hoolitsemine (S216)','CERCS',current_Timestamp(3), true, 'S216', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B435','B435','Põllumajanduse ajalugu (B435)','CERCS',current_Timestamp(3), true, 'B435', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B420','B420','Põllumajandusloomade söötmistehnoloogia ja ainevahetusuuringud (B420)','CERCS',current_Timestamp(3), true, 'B420', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T420','T420','Põllumajandustehnika, põllumajandusmasinad, põllumajanduslike hoonete ehitus (T420)','CERCS',current_Timestamp(3), true, 'T420', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S187','S187','Põllumajandusökonoomika (S187)','CERCS',current_Timestamp(3), true, 'S187', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S192','S192','Raamatupidamine (S192)','CERCS',current_Timestamp(3), true, 'S192', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T411','T411','Radiofarmaatsiatehnoloogia (T411)','CERCS',current_Timestamp(3), true, 'T411', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S181','S181','Rahandus (S181)','CERCS',current_Timestamp(3), true, 'S181', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S141','S141','Rahandusõigus (S141)','CERCS',current_Timestamp(3), true, 'S141', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B680','B680','Rahvatervishoid, epidemioloogia (B680)','CERCS',current_Timestamp(3), true, 'B680', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S150','S150','Rahvusvaheline era- ja avalik õigus (S150)','CERCS',current_Timestamp(3), true, 'S150', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S186','S186','Rahvusvaheline kaubandus (S186)','CERCS',current_Timestamp(3), true, 'S186', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H360','H360','Rakenduslingvistika, võõrkeelte õpetamine, sotsiolingvistika (H360)','CERCS',current_Timestamp(3), true, 'H360', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T290','T290','Raudteetransporditehnoloogia (T290)','CERCS',current_Timestamp(3), true, 'T290', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H530','H530','Saksa keel ja kirjandus (H530)','CERCS',current_Timestamp(3), true, 'H530', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P460','P460','Sedimentoloogia (P460)','CERCS',current_Timestamp(3), true, 'P460', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B370','B370','Selgrootute endokrinoloogia (B370)','CERCS',current_Timestamp(3), true, 'B370', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B361','B361','Selgrootute füsioloogia (B361)','CERCS',current_Timestamp(3), true, 'B361', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H610','H610','Semiidi-hamiidi keeled ja kirjandus (H610)','CERCS',current_Timestamp(3), true, 'H610', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T121','T121','Signaalitöötlus (T121)','CERCS',current_Timestamp(3), true, 'T121', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H580','H580','Skandinaavia keeled ja kirjandus (H580)','CERCS',current_Timestamp(3), true, 'H580', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B580','B580','Skeleti ja lihassüsteemi haigused, reumatoloogilised haigused (B580)','CERCS',current_Timestamp(3), true, 'B580', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H312','H312','Skulptuur ja arhitektuur (H312)','CERCS',current_Timestamp(3), true, 'H312', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T200','T200','Soojustehnika, rakenduslik termodünaamika (T200)','CERCS',current_Timestamp(3), true, 'T200', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B310','B310','Soontaimede füsioloogia (B310)','CERCS',current_Timestamp(3), true, 'B310', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S290','S290','Sotsiaalmeditsiin (S290)','CERCS',current_Timestamp(3), true, 'S290', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S230','S230','Sotsiaalne geograafia (S230)','CERCS',current_Timestamp(3), true, 'S230', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S285','S285','Sotsiaalpedagoogika (S285)','CERCS',current_Timestamp(3), true, 'S285', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S263','S263','Sotsiaalpsühholoogia (S263)','CERCS',current_Timestamp(3), true, 'S263', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S214','S214','Sotsiaalsed muutused, sotsiaaltöö teooria (S214)','CERCS',current_Timestamp(3), true, 'S214', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S215','S215','Sotsiaalsed probleemid ja heaolu, sotsiaalkindlustus (S215)','CERCS',current_Timestamp(3), true, 'S215', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S213','S213','Sotsiaalstruktuuride uuringud (S213)','CERCS',current_Timestamp(3), true, 'S213', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S100','S100','Sotsiaalteaduste ajalugu ja filosoofia (S100)','CERCS',current_Timestamp(3), true, 'S100', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S143','S143','Sotsiaalõigus (S143)','CERCS',current_Timestamp(3), true, 'S143', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S196','S196','Sotsiaalökonoomika (S196)','CERCS',current_Timestamp(3), true, 'S196', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S210','S210','Sotsioloogia (S210)','CERCS',current_Timestamp(3), true, 'S210', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P160','P160','Statistika, operatsioonanalüüs, programmeerimine, finants- ja kindlustusmatemaatika (P160)','CERCS',current_Timestamp(3), true, 'P160', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P450','P450','Stratigraafia (P450)','CERCS',current_Timestamp(3), true, 'P450', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P351','P351','Struktuurkeemia (P351)','CERCS',current_Timestamp(3), true, 'P351', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T330','T330','Sõjandus ja militaartehnoloogia (T330)','CERCS',current_Timestamp(3), true, 'T330', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B530','B530','Südame-veresoonkonna haigused (B530)','CERCS',current_Timestamp(3), true, 'B530', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B570','B570','Sünnitusabi, günekoloogia, androloogia, paljunemine, seksuaalsus (B570)','CERCS',current_Timestamp(3), true, 'B570', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T370','T370','Süsinikukeemia, naftakeemia, kütuste ja lõhkeainete tehnoloogia (T370)','CERCS',current_Timestamp(3), true, 'T370', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T120','T120','Süsteemitehnoloogia, arvutitehnoloogia (T120)','CERCS',current_Timestamp(3), true, 'T120', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B290','B290','Süstemaatiline botaanika, taksonoomia, morfoloogia, fütogeograafia, kemotaksonoomia, mittesoontaimede füsioloogia (B290)','CERCS',current_Timestamp(3), true, 'B290', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B320','B320','Süstemaatiline botaanika, zooloogia, zoogeograafia (B320)','CERCS',current_Timestamp(3), true, 'B320', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H120','H120','Süstemaatiline filosoofia, eetika, esteetika, metafüüsika, epistemoloogia, ideolooogia (H120)','CERCS',current_Timestamp(3), true, 'H120', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B710','B710','Taastusravi ja füsioteraapia (B710)','CERCS',current_Timestamp(3), true, 'B710', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P260','P260','Tahke aine: elektrooniline struktuur, elektrilised, magneetilised ja optilised omadused, ülijuhtivus, magnetresonants, spektroskoopia (P260)','CERCS',current_Timestamp(3), true, 'P260', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P250','P250','Tahke aine: struktuur, termilised ja mehhaanilised omadused, kristallograafia, phase equilibria (P250)','CERCS',current_Timestamp(3), true, 'P250', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B191','B191','Taimebiokeemia (B191)','CERCS',current_Timestamp(3), true, 'B191', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B225','B225','Taimegeneetika (B225)','CERCS',current_Timestamp(3), true, 'B225', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B390','B390','Taimekasvatus, aiandus, taimekaitsevahendid, taimehaigused (B390)','CERCS',current_Timestamp(3), true, 'B390', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B270','B270','Taimeökoloogia (B270)','CERCS',current_Timestamp(3), true, 'B270', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H260','H260','Teaduse ajalugu (H260)','CERCS',current_Timestamp(3), true, 'H260', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S211','S211','Teaduse sotsioloogia (S211)','CERCS',current_Timestamp(3), true, 'S211', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S274','S274','Teaduse uurimismetodoloogia (S274)','CERCS',current_Timestamp(3), true, 'S274', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P176','P176','Tehisintellekt (P176)','CERCS',current_Timestamp(3), true, 'P176', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T100','T100','Tehnika ajalugu ja filosoofia (T100)','CERCS',current_Timestamp(3), true, 'T100', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T470','T470','Tekstiilitehnoloogia (T470)','CERCS',current_Timestamp(3), true, 'T470', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P440','P440','Tektoonika (P440)','CERCS',current_Timestamp(3), true, 'P440', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T180','T180','Telekommunikatsioonitehnoloogia (T180)','CERCS',current_Timestamp(3), true, 'T180', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P410','P410','Teoreetiline ja kvantkeemia (P410)','CERCS',current_Timestamp(3), true, 'P410', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T260','T260','Territoriaalne planeerimine (T260)','CERCS',current_Timestamp(3), true, 'T260', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B685','B685','Tervishoiukorraldus (B685)','CERCS',current_Timestamp(3), true, 'B685', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T430','T430','Toiduainete ja jookide tehnoloogia (T430)','CERCS',current_Timestamp(3), true, 'T430', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T130','T130','Tootmistehnoloogia (T130)','CERCS',current_Timestamp(3), true, 'T130', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S136','S136','Transpordiõigus (S136)','CERCS',current_Timestamp(3), true, 'S136', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B780','B780','Troopikahaigused (B780)','CERCS',current_Timestamp(3), true, 'B780', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B431','B431','Troopikapõllumajandus (B431)','CERCS',current_Timestamp(3), true, 'B431', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T220','T220','Tsiviilehitus, Hüdrotehnoloogia, avameretehnoloogia, pinnasemehhaanika (T220)','CERCS',current_Timestamp(3), true, 'T220', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S130','S130','Tsiviilõigus: era-, perekonna-, abielu-, pärimis-, pärandus-, omandi-, pandi-, võlaõigus (S130)','CERCS',current_Timestamp(3), true, 'S130', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B200','B200','Tsütoloogia, onkoloogia, kantseroloogia (B200)','CERCS',current_Timestamp(3), true, 'B200', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S191','S191','Turu-uuringud (S191)','CERCS',current_Timestamp(3), true, 'S191', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P220','P220','Tuumafüüsika (P220)','CERCS',current_Timestamp(3), true, 'P220', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_P380','P380','Tuumaprotsesside keemia (P380)','CERCS',current_Timestamp(3), true, 'P380', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T160','T160','Tuumatehnoloogia (T160)','CERCS',current_Timestamp(3), true, 'T160', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S280','S280','Täiskasvanuharidus, elukestev õpe (S280)','CERCS',current_Timestamp(3), true, 'S280', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H240','H240','Tänapäeva ajalugu (umbes 1800 kuni 1914) (H240)','CERCS',current_Timestamp(3), true, 'H240', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H365','H365','Tõlkimine (H365)','CERCS',current_Timestamp(3), true, 'H365', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S212','S212','Tööjõu- ja ettevõtlussotsioloogia (S212)','CERCS',current_Timestamp(3), true, 'S212', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T500','T500','Tööohutustehnoloogia (T500)','CERCS',current_Timestamp(3), true, 'T500', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S144','S144','Tööstus- ja kaubandusõigus (S144)','CERCS',current_Timestamp(3), true, 'S144', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S266','S266','Tööstuspsühholoogia (S266)','CERCS',current_Timestamp(3), true, 'S266', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B690','B690','Töötervishoid (B690)','CERCS',current_Timestamp(3), true, 'B690', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S146','S146','Tööõigus (S146)','CERCS',current_Timestamp(3), true, 'S146', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H620','H620','Uraali-altai keeled ja kirjandus (H620)','CERCS',current_Timestamp(3), true, 'H620', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B560','B560','Uroloogia, nefroloogia (B560)','CERCS',current_Timestamp(3), true, 'B560', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T300','T300','Veetransporditehnoloogia (T300)','CERCS',current_Timestamp(3), true, 'T300', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H595','H595','Vene keel ja kirjandus (H595)','CERCS',current_Timestamp(3), true, 'H595', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B750','B750','Veterinaarmeditsiin, kirurgia, füsioloogia, patoloogia, kliinilised uuringud (B750)','CERCS',current_Timestamp(3), true, 'B750', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H600','H600','Väike-Aasia keeled ja kirjandus, Kaukaasia keeled ja kirjandus, baski ja sumeri keel ja kirjandus (H600)','CERCS',current_Timestamp(3), true, 'H600', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H410','H410','Väike-Aasia, indo-iraani ja indo-euroopa keeled ja kirjandus (H410)','CERCS',current_Timestamp(3), true, 'H410', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S282','S282','Võrdlev ja ajalooline pedagoogika (S282)','CERCS',current_Timestamp(3), true, 'S282', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H380','H380','Võrdlev keeleteadus (H380)','CERCS',current_Timestamp(3), true, 'H380', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S114','S114','Võrdlev õigusteadus (S114)','CERCS',current_Timestamp(3), true, 'S114', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B400','B400','Zootehnika, loomakasvatus, aretustegevus (B400)','CERCS',current_Timestamp(3), true, 'B400', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H500','H500','teised romaani keeled ja kirjandus (H500)','CERCS',current_Timestamp(3), true, 'H500', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_T310','T310','Õhutransporditehnoloogia (T310)','CERCS',current_Timestamp(3), true, 'T310', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H300','H300','Õiguse ajalugu (H300)','CERCS',current_Timestamp(3), true, 'H300', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S115','S115','Õigusfilosoofia ja õigusteooria (S115)','CERCS',current_Timestamp(3), true, 'S115', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S110','S110','Õigusteadus (S110)','CERCS',current_Timestamp(3), true, 'S110', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_S272','S272','Õpetajakoolitus (S272)','CERCS',current_Timestamp(3), true, 'S272', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H270','H270','Ühiskonna ja majanduse ajalugu (H270)','CERCS',current_Timestamp(3), true, 'H270', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H390','H390','Üldine ja võrdlev kirjandusteadus, kirjanduskriitika, kirjandusteooria (H390)','CERCS',current_Timestamp(3), true, 'H390', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_H160','H160','Üldine, süstemaatiline ja praktiline kristlik teoloogia (H160)','CERCS',current_Timestamp(3), true, 'H160', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('CERCS_B520','B520','Üldpatoloogia, patoloogiline anatoomia (B520)','CERCS',current_Timestamp(3), true, 'B520', false, true, 0, 'Automaat');


INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B100', 'CERCS_TYPE_3_1', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B110', 'CERCS_TYPE_3_11', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B115', 'CERCS_TYPE_3_11', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B120', 'CERCS_TYPE_3_11', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B130', 'CERCS_TYPE_3_11', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B140', 'CERCS_TYPE_4_9', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B145', 'CERCS_TYPE_3_11', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B190', 'CERCS_TYPE_3_11', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B191', 'CERCS_TYPE_1_1', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B200', 'CERCS_TYPE_3_11', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B210', 'CERCS_TYPE_3_11', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B220', 'CERCS_TYPE_1_3', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B225', 'CERCS_TYPE_1_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B230', 'CERCS_TYPE_1_2', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B235', 'CERCS_TYPE_1_2', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B240', 'CERCS_TYPE_1_2', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B250', 'CERCS_TYPE_1_4', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B260', 'CERCS_TYPE_1_4', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B270', 'CERCS_TYPE_1_4', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B280', 'CERCS_TYPE_1_4', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B290', 'CERCS_TYPE_1_4', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B300', 'CERCS_TYPE_1_4', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B310', 'CERCS_TYPE_1_4', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B320', 'CERCS_TYPE_1_4', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B330', 'CERCS_TYPE_1_4', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B340', 'CERCS_TYPE_1_4', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B350', 'CERCS_TYPE_1_4', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B360', 'CERCS_TYPE_1_4', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B361', 'CERCS_TYPE_1_4', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B370', 'CERCS_TYPE_1_4', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B380', 'CERCS_TYPE_1_4', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B381', 'CERCS_TYPE_1_4', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B390', 'CERCS_TYPE_1_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B400', 'CERCS_TYPE_1_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B401', 'CERCS_TYPE_1_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B402', 'CERCS_TYPE_1_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B410', 'CERCS_TYPE_4_2', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B420', 'CERCS_TYPE_1_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B430', 'CERCS_TYPE_1_5', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B431', 'CERCS_TYPE_1_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B432', 'CERCS_TYPE_1_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B433', 'CERCS_TYPE_1_12', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B434', 'CERCS_TYPE_1_12', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B435', 'CERCS_TYPE_1_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B440', 'CERCS_TYPE_3_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B450', 'CERCS_TYPE_3_1', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B460', 'CERCS_TYPE_1_4', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B470', 'CERCS_TYPE_3_1', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B480', 'CERCS_TYPE_3_1', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B490', 'CERCS_TYPE_3_1', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B500', 'CERCS_TYPE_3_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B510', 'CERCS_TYPE_3_1', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B520', 'CERCS_TYPE_3_1', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B530', 'CERCS_TYPE_3_1', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B540', 'CERCS_TYPE_3_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B550', 'CERCS_TYPE_3_9', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B560', 'CERCS_TYPE_3_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B570', 'CERCS_TYPE_3_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B580', 'CERCS_TYPE_3_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B590', 'CERCS_TYPE_3_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B600', 'CERCS_TYPE_3_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B610', 'CERCS_TYPE_3_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B620', 'CERCS_TYPE_3_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B630', 'CERCS_TYPE_3_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B640', 'CERCS_TYPE_3_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B650', 'CERCS_TYPE_3_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B660', 'CERCS_TYPE_3_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B670', 'CERCS_TYPE_3_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B680', 'CERCS_TYPE_3_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B685', 'CERCS_TYPE_3_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B690', 'CERCS_TYPE_3_10', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B700', 'CERCS_TYPE_3_10', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B710', 'CERCS_TYPE_3_5', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B720', 'CERCS_TYPE_3_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B725', 'CERCS_TYPE_3_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B726', 'CERCS_TYPE_3_1', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B730', 'CERCS_TYPE_3_4', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B740', 'CERCS_TYPE_3_3', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B750', 'CERCS_TYPE_3_2', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B760', 'CERCS_TYPE_3_1', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B770', 'CERCS_TYPE_2_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B780', 'CERCS_TYPE_3_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_B790', 'CERCS_TYPE_3_11', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H100', 'CERCS_TYPE_2_14', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H105', 'CERCS_TYPE_2_14', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H110', 'CERCS_TYPE_2_3', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H120', 'CERCS_TYPE_2_1', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H125', 'CERCS_TYPE_2_1', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H130', 'CERCS_TYPE_2_1', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H135', 'CERCS_TYPE_2_1', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H140', 'CERCS_TYPE_2_1', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H150', 'CERCS_TYPE_2_1', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H155', 'CERCS_TYPE_2_1', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H160', 'CERCS_TYPE_2_2', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H165', 'CERCS_TYPE_2_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H170', 'CERCS_TYPE_2_2', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H180', 'CERCS_TYPE_2_2', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H190', 'CERCS_TYPE_2_2', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H200', 'CERCS_TYPE_2_3', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H210', 'CERCS_TYPE_2_3', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H220', 'CERCS_TYPE_2_3', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H230', 'CERCS_TYPE_2_3', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H240', 'CERCS_TYPE_2_3', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H250', 'CERCS_TYPE_2_3', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H260', 'CERCS_TYPE_2_1', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H270', 'CERCS_TYPE_2_3', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H271', 'CERCS_TYPE_2_3', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H280', 'CERCS_TYPE_2_3', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H290', 'CERCS_TYPE_2_3', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H300', 'CERCS_TYPE_2_3', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H310', 'CERCS_TYPE_2_3', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H311', 'CERCS_TYPE_2_5', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H312', 'CERCS_TYPE_2_5', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H313', 'CERCS_TYPE_2_5', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H314', 'CERCS_TYPE_2_5', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H315', 'CERCS_TYPE_2_1', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H320', 'CERCS_TYPE_2_5', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H330', 'CERCS_TYPE_2_5', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H340', 'CERCS_TYPE_2_3', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H341', 'CERCS_TYPE_2_3', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H345', 'CERCS_TYPE_2_3', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H346', 'CERCS_TYPE_2_3', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H347', 'CERCS_TYPE_2_3', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H350', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H351', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H352', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H353', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H355', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H360', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H361', 'CERCS_TYPE_3_11', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H365', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H370', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H380', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H390', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H400', 'CERCS_TYPE_2_4', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H401', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H410', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H420', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H430', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H440', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H450', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H460', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H470', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H480', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H490', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H500', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H510', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H520', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H530', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H540', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H550', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H560', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H570', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H580', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H590', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H595', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H600', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H610', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H620', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H630', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H640', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H650', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H660', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_H670', 'CERCS_TYPE_2_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P110', 'CERCS_TYPE_4_4', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P120', 'CERCS_TYPE_4_4', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P130', 'CERCS_TYPE_4_4', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P140', 'CERCS_TYPE_4_4', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P150', 'CERCS_TYPE_4_4', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P160', 'CERCS_TYPE_4_5', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P170', 'CERCS_TYPE_4_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P175', 'CERCS_TYPE_4_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P176', 'CERCS_TYPE_4_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P180', 'CERCS_TYPE_4_10', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P190', 'CERCS_TYPE_4_10', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P200', 'CERCS_TYPE_4_10', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P210', 'CERCS_TYPE_4_10', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P211', 'CERCS_TYPE_4_10', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P220', 'CERCS_TYPE_4_10', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P230', 'CERCS_TYPE_4_10', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P240', 'CERCS_TYPE_4_10', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P250', 'CERCS_TYPE_4_10', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P260', 'CERCS_TYPE_4_10', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P265', 'CERCS_TYPE_4_10', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P300', 'CERCS_TYPE_4_11', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P305', 'CERCS_TYPE_1_9', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P310', 'CERCS_TYPE_1_1', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P320', 'CERCS_TYPE_1_1', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P330', 'CERCS_TYPE_1_12', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P340', 'CERCS_TYPE_1_1', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P351', 'CERCS_TYPE_4_11', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P352', 'CERCS_TYPE_4_11', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P360', 'CERCS_TYPE_4_11', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P370', 'CERCS_TYPE_4_11', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P380', 'CERCS_TYPE_4_11', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P390', 'CERCS_TYPE_4_11', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P395', 'CERCS_TYPE_4_11', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P400', 'CERCS_TYPE_4_11', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P401', 'CERCS_TYPE_4_11', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P402', 'CERCS_TYPE_4_11', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P410', 'CERCS_TYPE_4_11', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P420', 'CERCS_TYPE_4_2', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P430', 'CERCS_TYPE_4_2', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P440', 'CERCS_TYPE_4_2', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P450', 'CERCS_TYPE_4_2', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P460', 'CERCS_TYPE_4_2', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P470', 'CERCS_TYPE_4_2', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P500', 'CERCS_TYPE_4_2', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P510', 'CERCS_TYPE_1_10', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P515', 'CERCS_TYPE_4_2', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_P520', 'CERCS_TYPE_4_3', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S100', 'CERCS_TYPE_2_11', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S110', 'CERCS_TYPE_2_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S111', 'CERCS_TYPE_2_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S112', 'CERCS_TYPE_2_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S114', 'CERCS_TYPE_2_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S115', 'CERCS_TYPE_2_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S120', 'CERCS_TYPE_2_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S121', 'CERCS_TYPE_2_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S122', 'CERCS_TYPE_2_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S123', 'CERCS_TYPE_2_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S124', 'CERCS_TYPE_2_14', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S130', 'CERCS_TYPE_2_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S136', 'CERCS_TYPE_2_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S137', 'CERCS_TYPE_2_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S140', 'CERCS_TYPE_2_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S141', 'CERCS_TYPE_2_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S142', 'CERCS_TYPE_2_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S143', 'CERCS_TYPE_2_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S144', 'CERCS_TYPE_2_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S145', 'CERCS_TYPE_2_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S146', 'CERCS_TYPE_2_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S148', 'CERCS_TYPE_2_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S149', 'CERCS_TYPE_2_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S150', 'CERCS_TYPE_2_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S151', 'CERCS_TYPE_2_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S155', 'CERCS_TYPE_2_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S160', 'CERCS_TYPE_2_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S170', 'CERCS_TYPE_2_13', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S175', 'CERCS_TYPE_2_13', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S180', 'CERCS_TYPE_2_12', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S181', 'CERCS_TYPE_2_12', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S182', 'CERCS_TYPE_2_12', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S183', 'CERCS_TYPE_2_12', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S184', 'CERCS_TYPE_2_12', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S185', 'CERCS_TYPE_2_12', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S186', 'CERCS_TYPE_2_12', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S187', 'CERCS_TYPE_2_12', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S188', 'CERCS_TYPE_2_12', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S189', 'CERCS_TYPE_2_11', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S190', 'CERCS_TYPE_2_12', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S191', 'CERCS_TYPE_2_12', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S192', 'CERCS_TYPE_2_12', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S195', 'CERCS_TYPE_2_12', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S196', 'CERCS_TYPE_2_12', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S210', 'CERCS_TYPE_2_11', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S211', 'CERCS_TYPE_2_13', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S212', 'CERCS_TYPE_2_11', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S213', 'CERCS_TYPE_2_11', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S214', 'CERCS_TYPE_2_11', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S215', 'CERCS_TYPE_3_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S216', 'CERCS_TYPE_2_11', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S220', 'CERCS_TYPE_2_11', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S230', 'CERCS_TYPE_1_10', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S240', 'CERCS_TYPE_1_11', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S250', 'CERCS_TYPE_2_13', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S260', 'CERCS_TYPE_2_8', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S261', 'CERCS_TYPE_2_8', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S262', 'CERCS_TYPE_2_8', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S263', 'CERCS_TYPE_2_8', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S264', 'CERCS_TYPE_2_8', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S265', 'CERCS_TYPE_2_14', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S266', 'CERCS_TYPE_2_8', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S270', 'CERCS_TYPE_2_10', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S271', 'CERCS_TYPE_2_9', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S272', 'CERCS_TYPE_2_10', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S273', 'CERCS_TYPE_3_8', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S274', 'CERCS_TYPE_2_1', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S280', 'CERCS_TYPE_2_10', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S281', 'CERCS_TYPE_2_10', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S282', 'CERCS_TYPE_2_10', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S283', 'CERCS_TYPE_2_10', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S284', 'CERCS_TYPE_2_10', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S285', 'CERCS_TYPE_2_10', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S286', 'CERCS_TYPE_2_10', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_S290', 'CERCS_TYPE_3_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T100', 'CERCS_TYPE_2_1', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T110', 'CERCS_TYPE_4_12', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T111', 'CERCS_TYPE_4_12', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T115', 'CERCS_TYPE_4_9', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T120', 'CERCS_TYPE_4_13', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T121', 'CERCS_TYPE_4_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T125', 'CERCS_TYPE_4_13', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T130', 'CERCS_TYPE_4_14', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T140', 'CERCS_TYPE_4_17', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T150', 'CERCS_TYPE_4_12', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T151', 'CERCS_TYPE_4_12', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T152', 'CERCS_TYPE_4_12', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T153', 'CERCS_TYPE_4_12', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T155', 'CERCS_TYPE_4_12', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T160', 'CERCS_TYPE_4_12', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T165', 'CERCS_TYPE_4_12', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T170', 'CERCS_TYPE_4_8', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T171', 'CERCS_TYPE_4_8', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T180', 'CERCS_TYPE_4_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T181', 'CERCS_TYPE_4_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T190', 'CERCS_TYPE_4_8', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T191', 'CERCS_TYPE_4_8', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T200', 'CERCS_TYPE_4_8', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T210', 'CERCS_TYPE_4_13', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T220', 'CERCS_TYPE_4_15', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T230', 'CERCS_TYPE_4_15', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T240', 'CERCS_TYPE_4_1', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T250', 'CERCS_TYPE_4_1', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T260', 'CERCS_TYPE_4_15', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T270', 'CERCS_TYPE_1_9', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T280', 'CERCS_TYPE_4_15', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T290', 'CERCS_TYPE_4_15', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T300', 'CERCS_TYPE_4_15', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T310', 'CERCS_TYPE_4_15', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T320', 'CERCS_TYPE_4_15', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T330', 'CERCS_TYPE_4_18', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T340', 'CERCS_TYPE_4_14', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T350', 'CERCS_TYPE_4_11', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T360', 'CERCS_TYPE_4_16', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T370', 'CERCS_TYPE_4_14', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T380', 'CERCS_TYPE_4_11', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T390', 'CERCS_TYPE_4_11', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T400', 'CERCS_TYPE_4_11', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T410', 'CERCS_TYPE_4_9', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T411', 'CERCS_TYPE_3_3', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T420', 'CERCS_TYPE_1_6', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T430', 'CERCS_TYPE_1_7', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T440', 'CERCS_TYPE_4_12', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T450', 'CERCS_TYPE_4_12', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T455', 'CERCS_TYPE_4_13', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T460', 'CERCS_TYPE_1_5', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T470', 'CERCS_TYPE_4_12', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T480', 'CERCS_TYPE_4_12', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T490', 'CERCS_TYPE_4_16', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T500', 'CERCS_TYPE_4_14', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);
INSERT INTO "public"."classifier_connect" ("classifier_code", "connect_classifier_code", "inserted", "changed", "version", "main_classifier_code", "inserted_by", "changed_by") VALUES ('CERCS_T510', 'CERCS_TYPE_2_3', '2020-03-31 15:03:48.941', NULL, '0', 'CERCS_TYPE', NULL, NULL);


do $$
declare
	r record;
	i integer:=0;
	o_id integer:=0;
	s_id integer:=0;
	c_id integer:=0;
begin
	for r in (select distinct je.curriculum_module_outcomes_id, js.student_id,jes.grade_code, jes.grade_inserted,je.entry_date, jes.inserted, jes.grade_inserted_by, 1 as mis, jes.add_info,te.id as teacher_id,jes.inserted,jes.inserted_by,jes.version,jes.changed,jes.changed_by
						from journal_entry je
								 join journal_entry_student jes on je.id=jes.journal_entry_id
								 join journal_student js on jes.journal_student_id=js.id
								 join curriculum_module_outcomes cm on je.curriculum_module_outcomes_id=cm.id
								 join journal j on je.journal_id=j.id
								 left join (teacher te join person pp on te.person_id=pp.id) on j.school_id=te.school_id and jes.grade_inserted_by is not null and upper(jes.grade_inserted_by) like upper(pp.firstname||' '||pp.lastname||'%')
						where jes.grade_code is not null
						union
						select distinct je.curriculum_module_outcomes_id, js.student_id,jesh.grade_code,jesh.grade_inserted,je.entry_date, jesh.inserted, jesh.grade_inserted_by, 2 as mis, jes.add_info,te.id as teacher_id,jesh.inserted,jesh.inserted_by,jesh.version,jesh.changed,jesh.changed_by
						from journal_entry je
								 join journal_entry_student jes on je.id=jes.journal_entry_id
								 join journal_student js on jes.journal_student_id=js.id
								 join journal_entry_student_history jesh on jesh.journal_entry_student_id=jes.id
								 join curriculum_module_outcomes cm on je.curriculum_module_outcomes_id=cm.id
								join journal j on je.journal_id=j.id
								 left join (teacher te join person pp on te.person_id=pp.id) on j.school_id=te.school_id and jes.grade_inserted_by is not null and upper(jes.grade_inserted_by) like upper(pp.firstname||' '||pp.lastname||'%')
						where jesh.grade_code is not null
						order by 2, 1, 7, 4 desc nulls last, 5  desc nulls last, 6		  )
	loop
		if o_id!=r.curriculum_module_outcomes_id or s_id!=r.student_id then
			c_id:=0;
			insert into student_curriculum_module_outcomes_result(
									student_id, curriculum_module_outcomes_id, grade_Code, grade_date,grade_inserted, 
									grade_inserted_by, add_info,grade_inserted_teacher_id,apel_application_id,inserted,inserted_by,version,changed,changed_by)
			values(r.student_id, r.curriculum_module_outcomes_id, r.grade_Code, coalesce(coalesce(r.grade_inserted,r.entry_date),r.inserted),coalesce(coalesce(r.grade_inserted,r.entry_date),r.inserted), 
						r.grade_inserted_by, r.add_info,r.teacher_id,null,r.inserted,r.inserted_by,r.version,r.changed,r.changed_by) returning id into c_id;
		else
			insert into student_curriculum_module_outcomes_result_history(
									student_curriculum_module_outcomes_result_id, grade_Code, grade_date,grade_inserted, 
									grade_inserted_by, add_info,inserted,inserted_by,version,changed,changed_by)
			values(c_id, r.grade_Code, coalesce(coalesce(r.grade_inserted,r.entry_date),r.inserted),coalesce(coalesce(r.grade_inserted,r.entry_date),r.inserted), 
						r.grade_inserted_by, r.add_info,r.inserted,r.inserted_by,r.version,r.changed,r.changed_by);
		end if;
		o_id:=r.curriculum_module_outcomes_id;
		s_id:=r.student_id;
	end loop;
	update journal_entry 
	set entry_type_code='SISSEKANNE_T', name_et=substr('ÕV: '||coalesce(name_et,''),1,100),
			"content"='ÕV: '||coalesce("content",(select oo.outcome_et from curriculum_module_outcomes oo where oo.id=curriculum_module_outcomes_id)),
			curriculum_module_outcomes_id=null 
	where entry_type_code='SISSEKANNE_O';
with s as (select distinct jot.curriculum_version_omodule_theme_id
					from journal jj
							 join journal_omodule_theme jot on jj.id=jot.journal_id
					where jj.add_module_outcomes=true and (select count(*) from curriculum_version_omodule_outcomes oo where oo.curriculum_version_omodule_theme_id=jot.curriculum_version_omodule_theme_id) > 0)
update curriculum_version_omodule_theme ct
	set is_module_outcomes=true
from s
where s.curriculum_version_omodule_theme_id=ct.id;

	for r in (select distinct ap.student_id, apio.curriculum_module_outcomes_id, first_value(ap.id) over (partition by ap.student_id,apio.curriculum_module_outcomes_id order by ap.confirmed asc) as ap_id
		from apel_application ap
			 join apel_application_record apr on ap.id=apr.apel_application_id
			 join apel_application_informal_subject_or_module api on apr.id=api.apel_application_record_id and api.transfer=true
			 join apel_application_informal_subject_or_module_outcomes apio on api.id=apio.apel_application_informal_subject_or_module_id
		where ap.status_code='VOTA_STAATUS_C' and not exists (select 1 from student_curriculum_module_outcomes_result as scmor where scmor.curriculum_module_outcomes_id=apio.curriculum_module_outcomes_id and scmor.student_id=ap.student_id)
		)
	loop
		insert into student_curriculum_module_outcomes_result(
									student_id, curriculum_module_outcomes_id, grade_Code, grade_date,grade_inserted, 
 									grade_inserted_by, add_info,grade_inserted_teacher_id,apel_application_id,inserted,inserted_by,version,changed,changed_by)
		select r.student_id, r.curriculum_module_outcomes_id,'KUTSEHINDAMINE_A',confirmed,confirmed,null,'VÕTA',null,id,inserted,inserted_by,version,changed,changed_by
		from apel_application where id=r.ap_id;
	end loop;
	
end;
$$;

create trigger final_thesis_cercs_audit after insert or delete or update on final_thesis_cercs for each row execute procedure hois_audit();
create trigger student_curriculum_module_outcomes_result_audit after insert or delete or update on student_curriculum_module_outcomes_result for each row execute procedure hois_audit();
create trigger student_curriculum_module_outcomes_result_history_audit after insert or delete or update on student_curriculum_module_outcomes_result_history for each row execute procedure hois_audit();
create trigger subject_study_period_subgroup_audit after insert or delete or update on subject_study_period_subgroup for each row execute procedure hois_audit();
create trigger timetable_event_subgroup_audit after insert or delete or update on timetable_event_subgroup for each row execute procedure hois_audit();
create trigger user_curriculum_audit after insert or delete or update on user_curriculum for each row execute procedure hois_audit();
create trigger user_school_role_audit after insert or delete or update on user_school_role for each row execute procedure hois_audit();
create trigger user_school_role_rights_audit after insert or delete or update on user_school_role_rights for each row execute procedure hois_audit();

alter table final_thesis_supervisor add column birthdate date;
alter table final_thesis_supervisor add column sex_Code varchar(100);

comment on column final_thesis_supervisor.birthdate is 'sünnikuupäev, vajalik EHISesse andmete saatmiseks';
comment on column final_thesis_supervisor.sex_Code is 'sugu, viide klassifikaatorile SUGU, vajalik EHISesse andmete saatmiseks';
create index final_thesis_supervisor_classifier_01 on final_thesis_supervisor(sex_Code);
alter table final_thesis_supervisor add constraint FK_final_thesis_supervisor_classifier foreign key(sex_Code) references classifier(code);

INSERT INTO classifier (code, value, name_et, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) 
VALUES ('EHIS_STIPENDIUM', 'EHIS_STIPENDIUM', 'EHISe stipendiumite liigid', current_Timestamp(3), true, null, false, true, 0, 'Automaat');

INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('EHIS_STIPENDIUM_2','2','Tulemusstipendium','EHIS_STIPENDIUM',current_Timestamp(3), true, 'STIPENDIUM_2', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('EHIS_STIPENDIUM_6','6','Muu stipendium','EHIS_STIPENDIUM',current_Timestamp(3), true, 'STIPENDIUM_6', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('EHIS_STIPENDIUM_7','7','Doktoranditoetus','EHIS_STIPENDIUM',current_Timestamp(3), true, 'STIPENDIUM_7', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('EHIS_STIPENDIUM_8','8','Erialastipendium nutika spetsialiseerumise õppekavadel','EHIS_STIPENDIUM',current_Timestamp(3), true, 'STIPENDIUM_8', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('EHIS_STIPENDIUM_9','9','Õpetajakoolituse stipendium','EHIS_STIPENDIUM',current_Timestamp(3), true, 'STIPENDIUM_9', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('EHIS_STIPENDIUM_10','10','IT akadeemia stipendium','EHIS_STIPENDIUM',current_Timestamp(3), true, 'STIPENDIUM_10', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('EHIS_STIPENDIUM_11','11','Doktorandistipendium (NS valdkond)','EHIS_STIPENDIUM',current_Timestamp(3), true, 'STIPENDIUM_11', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('EHIS_STIPENDIUM_12','12','Doktorandistipendium (ettevõtluskoostöö)','EHIS_STIPENDIUM',current_Timestamp(3), true, 'STIPENDIUM_12', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('EHIS_STIPENDIUM_13','13','Dora välisdoktorandistipendium','EHIS_STIPENDIUM',current_Timestamp(3), true, 'STIPENDIUM_13', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('EHIS_STIPENDIUM_14','14','Dora välisdoktoranditoetus','EHIS_STIPENDIUM',current_Timestamp(3), true, 'STIPENDIUM_14', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('EHIS_STIPENDIUM_15','15','Dora välisdoktoranditoetuse sõidukulu','EHIS_STIPENDIUM',current_Timestamp(3), true, 'STIPENDIUM_15', false, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et,main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version, inserted_by) VALUES ('EHIS_STIPENDIUM_16','16','Dora välismagistrandistipendium','EHIS_STIPENDIUM',current_Timestamp(3), true, 'STIPENDIUM_16', false, true, 0, 'Automaat');

alter TABLE "scholarship_term" 
	add column "scholarship_ehis_code" varchar(100)	 NULL,    -- viide klassifikaatorile EHIS_STIPENDIUM, kasutatakse kõrg hariduse puhul
	add column "wag_mark" numeric(4,2) NULL,    -- KKH kõrgkoolides
	add column "wag_mark_priority_code" varchar(100)	 NULL,    -- KKH prioriteet
	add column "last_period_wag_mark" numeric(4,2) NULL,    -- eelmise perioodi KKH kõrgkoolis
	add column "last_period_wag_mark_priority_code" varchar(100)	 NULL,    -- eelmise perioodi KKH prioriteet
	add column "is_nominal_end" boolean NULL,    -- kas ületatud nominaalajaga taotlemine lubatu
	add column "is_negative" boolean NULL,    -- kas negatiivseid sooritusi arvestatakse
	add column "is_before_immat" boolean NULL,    -- kas enne immatrikuleerimist tehtud sooritused läheva arvesse
	add column "is_sais" boolean NULL    -- kas KKH puudumisel arvestatakse SAIS tulemust
;

CREATE TABLE "scholarship_no_application"
(
	"id" bigserial NOT NULL,
	"school_id" bigint NOT NULL,    -- viide õppeasutusel
	"inserted" timestamp without time zone NOT NULL,
	"scholarship_ehis_code" varchar(100) NOT NULL,    -- viide klassifikaatorile EHIS_STIPENDIUM
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

alter TABLE "directive" add column	"scholarship_ehis_code" varchar(100)	 NULL    -- viide klassiikaatorile EHIS_STIPENDIUM
;

COMMENT ON COLUMN "scholarship_term"."scholarship_ehis_code"	IS 'viide klassifikaatorile EHIS_STIPENDIUM, kasutatakse kõrg hariduse puhul';
COMMENT ON COLUMN "scholarship_term"."wag_mark"	IS 'KKH kõrgkoolides';
COMMENT ON COLUMN "scholarship_term"."wag_mark_priority_code"	IS 'KKH prioriteet';
COMMENT ON COLUMN "scholarship_term"."last_period_wag_mark"	IS 'eelmise perioodi KKH kõrgkoolis';
COMMENT ON COLUMN "scholarship_term"."last_period_wag_mark_priority_code"	IS 'eelmise perioodi KKH prioriteet';
COMMENT ON COLUMN "scholarship_term"."is_nominal_end"	IS 'kas ületatud nominaalajaga taotlemine lubatu';
COMMENT ON COLUMN "scholarship_term"."is_negative"	IS 'kas negatiivseid sooritusi arvestatakse';
COMMENT ON COLUMN "scholarship_term"."is_before_immat"	IS 'kas enne immatrikuleerimist tehtud sooritused läheva arvesse';
COMMENT ON COLUMN "scholarship_term"."is_sais"	IS 'kas KKH puudumisel arvestatakse SAIS tulemust';

COMMENT ON TABLE "scholarship_no_application"	IS 'õppeasutuse stip. liigid, mis ei vaja stip. taotlusi, vaid saab kohe käskkirja tegema hakata';
COMMENT ON COLUMN "scholarship_no_application"."school_id"	IS 'viide õppeasutusele';
COMMENT ON COLUMN "scholarship_no_application"."scholarship_ehis_code"	IS 'viide klassifikaatorile EHIS_STIPENDIUM';

COMMENT ON COLUMN "directive"."scholarship_ehis_code"	IS 'viide klassiikaatorile EHIS_STIPENDIUM';
/* Create Primary Keys, Indexes, Uniques, Checks */

CREATE INDEX "IXFK_scholarship_term_classifier_06" ON "scholarship_term" ("scholarship_ehis_code" ASC);
CREATE INDEX "IXFK_scholarship_term_classifier_07" ON "scholarship_term" ("wag_mark_priority_code" ASC);
CREATE INDEX "IXFK_scholarship_term_classifier_08" ON "scholarship_term" ("last_period_wag_mark_priority_code" ASC);

ALTER TABLE "scholarship_no_application" ADD CONSTRAINT "PK_scholarship_no_application"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_scholarship_no_application_classifier" ON "scholarship_no_application" ("scholarship_ehis_code" ASC);
CREATE INDEX "IXFK_scholarship_no_application_school" ON "scholarship_no_application" ("school_id" ASC);

CREATE INDEX "IXFK_directive_classifier_05" ON "directive" ("scholarship_ehis_code" ASC);

/* Create Foreign Key Constraints */
ALTER TABLE "scholarship_term" ADD CONSTRAINT "FK_scholarship_term_classifier_06"	FOREIGN KEY ("scholarship_ehis_code") REFERENCES "classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "scholarship_term" ADD CONSTRAINT "FK_scholarship_term_classifier_07"	FOREIGN KEY ("wag_mark_priority_code") REFERENCES "classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "scholarship_term" ADD CONSTRAINT "FK_scholarship_term_classifier_08"	FOREIGN KEY ("last_period_wag_mark_priority_code") REFERENCES "classifier" ("code") ON DELETE No Action ON UPDATE No Action;

ALTER TABLE "scholarship_no_application" ADD CONSTRAINT "FK_scholarship_no_application_classifier"	FOREIGN KEY ("scholarship_ehis_code") REFERENCES "classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "scholarship_no_application" ADD CONSTRAINT "FK_scholarship_no_application_school"	FOREIGN KEY ("school_id") REFERENCES "school" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "directive" ADD CONSTRAINT "FK_directive_classifier_05"	FOREIGN KEY ("scholarship_ehis_code") REFERENCES "classifier" ("code") ON DELETE No Action ON UPDATE No Action;

CREATE TABLE "school_query"
(
	"id" bigserial NOT NULL,
	"is_student_query" boolean NOT NULL,    -- kas tegemist on õppuri andmete päringuga
	"school_id" bigint NOT NULL,    -- viide õppeasutusel
	"name_et" varchar(255)	 NOT NULL,    -- päringu nimi
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL,
	"query_sub_type" varchar(100)	 NULL,    -- STUDENT_DATA_ALL jne
	"is_without_guests" boolean NULL,    -- külalisõpilased va
	"orderby1" varchar(100)	 NULL,    -- mille järgi järjestatakse 1
	"orderby2" varchar(100)	 NULL,    -- mille järgi järjestatakse 2
	"orderby3" varchar(100)	 NULL,    -- mille järgi järjestatakse 3
	"is_orderby1_desc" boolean NULL,    -- kas välja 1 järjestatakse kahanevas järjekorras
	"is_orderby2_desc" boolean NULL,    -- kas välja 2 järjestatakse kahanevas järjekorras
	"is_orderby3_desc" boolean NULL    -- kas välja 3 järjestatakse kahanevas järjekorras
)
;

CREATE TABLE "school_query_criteria"
(
	"id" bigserial NOT NULL,
	"criteria_code" varchar(100)	 NOT NULL,    -- otsinguparameetri kood
	"school_query_id" bigint NOT NULL,
	"show_in_results" boolean NOT NULL,    -- kas kuvada tulemustes
	"criteria_val1" varchar(255)	 NULL,
	"criteria_val2" varchar(255)	 NULL,
	"criteria_condition" varchar(10)	 NULL,    -- võrdlus, nt <, >, =
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "school_query"
	IS 'kooli salvestatud päringud'
;

COMMENT ON COLUMN "school_query"."is_student_query"
	IS 'kas tegemist on õppuri andmete päringuga'
;

COMMENT ON COLUMN "school_query"."school_id"
	IS 'viide õppeasutusel'
;

COMMENT ON COLUMN "school_query"."name_et"
	IS 'päringu nimi'
;

COMMENT ON COLUMN "school_query"."query_sub_type"
	IS 'STUDENT_DATA_ALL jne'
;

COMMENT ON COLUMN "school_query"."is_without_guests"
	IS 'külalisõpilased va'
;

COMMENT ON COLUMN "school_query"."orderby1"
	IS 'mille järgi järjestatakse 1'
;

COMMENT ON COLUMN "school_query"."orderby2"
	IS 'mille järgi järjestatakse 2'
;

COMMENT ON COLUMN "school_query"."orderby3"
	IS 'mille järgi järjestatakse 3'
;

COMMENT ON COLUMN "school_query"."is_orderby1_desc"
	IS 'kas välja 1 järjestatakse kahanevas järjekorras'
;

COMMENT ON COLUMN "school_query"."is_orderby2_desc"
	IS 'kas välja 2 järjestatakse kahanevas järjekorras'
;

COMMENT ON COLUMN "school_query"."is_orderby3_desc"
	IS 'kas välja 3 järjestatakse kahanevas järjekorras'
;

COMMENT ON TABLE "school_query_criteria"
	IS 'kooli pärngite väljad'
;

COMMENT ON COLUMN "school_query_criteria"."criteria_code"
	IS 'otsinguparameetri kood'
;

COMMENT ON COLUMN "school_query_criteria"."show_in_results"
	IS 'kas kuvada tulemustes'
;

COMMENT ON COLUMN "school_query_criteria"."criteria_condition"
	IS 'võrdlus, nt <, >, ='
;

/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE "school_query" ADD CONSTRAINT "PK_school_query"
	PRIMARY KEY ("id")
;

CREATE INDEX "IXFK_school_query_school" ON "school_query" ("school_id" ASC)
;

ALTER TABLE "school_query_criteria" ADD CONSTRAINT "PK_school_query_field"
	PRIMARY KEY ("id")
;

CREATE INDEX "IXFK_school_query_field_school_query" ON "school_query_criteria" ("school_query_id" ASC)
;

/* Create Foreign Key Constraints */

ALTER TABLE "school_query" ADD CONSTRAINT "FK_school_query_school"
	FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "school_query_criteria" ADD CONSTRAINT "FK_school_query_field_school_query"
	FOREIGN KEY ("school_query_id") REFERENCES "school_query" ("id") ON DELETE No Action ON UPDATE No Action
;

alter table base_module add column add_name_et varchar(255);
comment on column base_module.add_name_et is 'baasmooduli täiendav nimi (selgitus)';

alter table curriculum_module add column order_nr smallint;
alter table curriculum_version_hmodule add column order_nr smallint;
comment on column curriculum_version_hmodule.order_nr is 'järjekorra nr';
comment on column curriculum_module.order_nr is 'järjekorra nr';


CREATE TABLE "student_curriculum_completion_hmodule"
(
	"id" bigserial NOT NULL ,
	"student_id" bigint NOT NULL,    -- viide õppurile
	"curriculum_version_hmodule_id" bigint NOT NULL,    -- viide kõrghariduse moodulile
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "student_curriculum_completion_hmodule"
	IS 'õppuri õppekavatitmisel nö jõuga arvestatud moodul'
;

COMMENT ON COLUMN "student_curriculum_completion_hmodule"."student_id"
	IS 'viide õppurile'
;

COMMENT ON COLUMN "student_curriculum_completion_hmodule"."curriculum_version_hmodule_id"
	IS 'viide kõrghariduse moodulile'
;


/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE "student_curriculum_completion_hmodule" ADD CONSTRAINT "PK_student_curriculum_completion_hmodule"
	PRIMARY KEY ("id")
;

CREATE INDEX "IXFK_student_curriculum_completion_hmodule_curriculum_version_hmodule" ON "student_curriculum_completion_hmodule" ("curriculum_version_hmodule_id" ASC)
;

CREATE INDEX "IXFK_student_curriculum_completion_hmodule_student" ON "student_curriculum_completion_hmodule" ("student_id" ASC)
;

/* Create Foreign Key Constraints */

ALTER TABLE "student_curriculum_completion_hmodule" ADD CONSTRAINT "FK_student_curriculum_completion_hmodule_curriculum_version_hmodule"
	FOREIGN KEY ("curriculum_version_hmodule_id") REFERENCES "public"."curriculum_version_hmodule" ("id") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "student_curriculum_completion_hmodule" ADD CONSTRAINT "FK_student_curriculum_completion_hmodule_student"
	FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE No Action ON UPDATE No Action
;

INSERT INTO classifier (code, value, name_et, main_class_code, inserted, changed, valid,  is_vocational, is_higher, version, inserted_by, changed_by) 
VALUES ('TEEMAOIGUS_OKTAITMINE', 'OKTAITMINE', 'Õppekava täitmine (mooduli täidetuks märkimine)', 'TEEMAOIGUS', current_timestamp(3), current_timestamp(3), 't', 'f', 't', '1', 'Irina Kelder', 'Irina Kelder');

insert into user_role_default(object_code,permission_code,role_code) values('TEEMAOIGUS_OKTAITMINE','OIGUS_V','ROLL_A');

CREATE UNIQUE INDEX UQ_student_curriculum_completion_hmodule ON student_curriculum_completion_hmodule (student_id,curriculum_version_hmodule_id);

CREATE OR REPLACE FUNCTION public.upd_curriculum_completion_trgr2()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
declare i integer;
BEGIN
	if tg_op='DELETE' then
		i:=upd_student_curriculum_completion(OLD.STUDENT_ID);
	else
		i:=upd_student_curriculum_completion(NEW.STUDENT_ID);
	end if;
  return null;
end;
$function$
;

create trigger student_curriculum_completion_hmodule_trg after insert or update or delete on
public.student_curriculum_completion_hmodule for each row execute procedure upd_curriculum_completion_trgr2();

alter table student_curriculum_completion alter column average_mark type numeric(5,3);
alter table student_curriculum_completion add column study_optional_backlog numeric(4,1);


CREATE OR REPLACE FUNCTION public.upd_student_curriculum_completion(p_id bigint)
 RETURNS integer
 LANGUAGE plpgsql
AS $function$
declare 
  pb_exist boolean:=false;
	p_curr_modules bigint array;
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

  p_vcurr_modules bigint array;
	p_vcurr_modules2 bigint array;

	p_fcurr_modules bigint array;
	
	mod_id bigint;

	is_higher_curriculum boolean:=true;
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
		--Kõrgõppurid
		for r in (select distinct cm.id as m_id, cm.total_credits,cm.optional_study_credits,cm.compulsory_study_credits, cm.type_code, cc.is_higher, cc.optional_study_credits as total_optional_study_credits,
										 sch.curriculum_version_hmodule_id as ok_id, cc.credits as curriculum_credits
					  from curriculum_version cv
								 join curriculum_version_hmodule cm on cv.id=cm.curriculum_version_id
								 --join curriculum_module cm on cvo.curriculum_module_id=cm.id and cv.curriculum_id=cm.curriculum_id and coalesce(cm.is_additional,false)=false and cm.module_code!='KUTSEMOODUL_V' and coalesce(cm.is_additional,false)=false
								 join curriculum cc on cv.curriculum_id=cc.id
								 join student ss on cv.id=ss.curriculum_version_id
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
			p_curriculum_credits:=coalesce(r.curriculum_credits,0);
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
		end loop;

		if array_length(p_curr_modules,1) > 0 THEN
			for ii in 1..array_length(p_curr_modules,1)
			LOOP
				--vabaõppe ei pea siin kontrollima vist
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
			study_optional_backlog=-p_opt_credits
	where student_id=p_id;
	
	GET DIAGNOSTICS a_count = ROW_COUNT;
	if a_count=0 THEN
			insert into student_curriculum_completion(student_id,study_backlog,study_backlog_without_graduate,average_mark,credits,inserted,changed,study_optional_backlog)
			values(p_id,-p_abs_credits,-p_fabs_credits,case when p_avg_total_credits > 0 then floor(p_avg_credits*100/p_avg_total_credits)/100 else 0 end,p_total_credits,current_timestamp(3),current_timestamp(3),-p_opt_credits);
	end if;

	return 0;
exception when others THEN
	--raise notice '%, %',p_id,sqlerrm;
	return 0;
end;
$function$
;


alter table timetable_event add column juhan_event_id bigint;
comment on column timetable_event.juhan_event_id is 'JUHAN sündmuse ID';

alter table school add column is_hmodules boolean;
comment on column school.is_hmodules is 'kas kõrgkoolis on moodulite hindamine';

alter table curriculum_version_hmodule add column is_grade boolean;
comment on column curriculum_version_hmodule.is_grade is 'kas moodul hinnatakse ja see hiljem kuvatakse akad õiendil';

alter table directive_student add constraint FK_directive_student_application foreign key(application_id) references application(id);

alter table student_support_service add column is_ehis boolean;
comment on column student_support_service.is_ehis is 'kas EHISest tõmmatud';

with s as (select distinct st.*,sh.id as student_history_id2
from student st
	 join student_history sh on st.id=sh.student_id and sh.inserted_by !='DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') and sh.valid_thru is null
where sh.status_code!=st.status_code)
update student_history sh
	set curriculum_version_id=s.curriculum_version_id,
			study_form_code=s.study_form_code,
			student_group_id=s.student_group_id,
			email=s.email,
			language_code=s.language_code,
			is_special_need=s.is_special_need,
			is_representative_mandatory=s.is_representative_mandatory,
			special_need_code=s.special_need_code,
			student_card=s.student_card,
			previous_study_level_code=s.previous_study_level_code,
			status_code=s.status_code,
			ois_file_id=s.ois_file_id,
			curriculum_speciality_id=s.curriculum_speciality_id,
			study_start=s.study_start,
			study_end=s.study_end,
			nominal_study_end=s.nominal_study_end,
			study_load_code=s.study_load_code,
			fin_code=s.fin_code,
			fin_specific_code=s.fin_specific_code
from s
where s.student_history_id2=sh.id;


CREATE TABLE public.ws_juhan_log (
	id bigserial NOT NULL,
	request_url varchar(4000) NULL,
	request_param text NULL,
	response text NULL,
	has_errors bool NOT NULL,
	log_txt text NULL,
	inserted timestamp NULL,
	CONSTRAINT ws_juhan_log_pkey PRIMARY KEY (id)
);

with s as (select s.* 
from student s
     join student_history sh on s.student_history_id=sh.id
where (s.inserted_by ='DATA_TRANSFER_PROCESS' or upper(s.inserted_by) like upper('%andmete%kanne%')) and
(sh.inserted_by ='DATA_TRANSFER_PROCESS' or upper(sh.inserted_by) like upper('%andmete%kanne%')))
update student_history sh
  set inserted_by='DTP viimane'
from s
where sh.id=s.student_history_id;

with s as (select sh2.* 
from student s
     join student_history sh on s.student_history_id=sh.id
     join student_history sh2 on sh.prev_student_history_id=sh2.id
where (s.inserted_by ='DATA_TRANSFER_PROCESS' or upper(s.inserted_by) like upper('%andmete%kanne%')) and
not (sh.inserted_by ='DATA_TRANSFER_PROCESS' or upper(sh.inserted_by) like upper('%andmete%kanne%')) and
(sh2.inserted_by ='DATA_TRANSFER_PROCESS' or upper(sh2.inserted_by) like upper('%andmete%kanne%')))
update student_history sh
  set inserted_by='DTP viimane'
from s
where sh.id=s.id;

with s as (
select st.id,dd.confirm_date,dd.type_code,dh.valid_from,ds.student_history_id,dh.valid_from- (interval '1 day'*(dh.valid_from::date-dd.confirm_date::date)) as mis
from student st
     join directive_student ds on st.id=ds.student_id
		 join directive dd on ds.directive_id=dd.id
     join student_history dh on ds.student_history_id=dh.id 
where dd.type_code in ('KASKKIRI_IMMAT', 'KASKKIRI_IMMATV', 'KASKKIRI_ENNIST') and dd.confirm_date::date!=dh.valid_from::date
order by 1 desc)
update student_history sh
	set valid_from=s.mis
from s
where sh.id=s.student_history_id;

do $$
declare
	r record;
begin
	for r in (
select st.id,dd.confirm_date,dd.type_code,sh.valid_from,sh.id as student_history_id,sh.valid_from- (interval '1 day'*(sh.valid_from::date-dd.confirm_date::date)) as mis,-- sh2.id,sh2.valid_from as valid_From2,
       dh.id as prev_student_history_id
from student st
     join directive_student ds on st.id=ds.student_id
		 join directive dd on ds.directive_id=dd.id
     join student_history dh on ds.student_history_id=dh.id and dh.inserted_by != 'DATA_TRANSFER_PROCESS' AND UPPER (dh.inserted_by) NOT LIKE UPPER ('%andmete%kanne%')
     join student_history sh on dh.id=sh.prev_student_history_id
     left join student_history sh2 on sh.id=sh2.prev_student_history_id
		-- join student_history sh2 on st.id=sh2.student_id and sh2.valid_from < dh.valid_from and sh2.valid_from::date >=dd.confirm_date::date	
where dd.type_code in ('KASKKIRI_LOPET','KASKKIRI_EKSMAT') and dd.confirm_date::date!=sh.valid_from::date and dd.confirm_date::date!=sh.valid_from::date --and sh.valid_thru < sh.valid_from- (interval '1 day'*(sh.valid_from::date-dd.confirm_date::date))
order by 1 desc)
loop
	update student_history
		set valid_from=r.mis
	where id=r.student_history_id;
  update student_history
		set valid_thru=r.mis
	where id=r.prev_student_history_id;
end loop;
end;
$$;

do $$
declare
	r record;
begin
	for r in (
select distinct st.id,dd.confirm_date,dd.type_code,--dh.valid_from,dh.id as student_history_id,dh.valid_from- (interval '1 day'*(dh.valid_from::date-dd.confirm_date::date)) as mis,-- sh2.id,sh2.valid_from as valid_From2,
       --dh.id as prev_student_history_id
			first_value(dh.id) over (partition by ds.student_id order by dh.valid_from asc) as mis
from student st
     join directive_student ds on st.id=ds.student_id and ds.student_history_id is null
		 join directive dd on ds.directive_id=dd.id 
     join student_history dh on dh.student_id=ds.student_id and dh.inserted_by != 'DATA_TRANSFER_PROCESS' AND UPPER (dh.inserted_by) NOT LIKE UPPER ('%andmete%kanne%')
     --left join student_history sh on dh.id=sh.prev_student_history_id 
     --left join student_history sh2 on dh.id=sh2.prev_student_history_id
		-- join student_history sh2 on st.id=sh2.student_id and sh2.valid_from < dh.valid_from and sh2.valid_from::date >=dd.confirm_date::date	
where dd.type_code in ('KASKKIRI_LOPET','KASKKIRI_EKSMAT')  and dh.status_code in ('OPPURSTAATUS_L','OPPURSTAATUS_K')
and dd.confirm_date::date!=dh.valid_from::date --and sh.valid_thru < sh.valid_from- (interval '1 day'*(sh.valid_from::date-dd.confirm_date::date))
order by 1 desc)
loop
	update student_history
		set valid_from=(valid_from- (interval '1 day'*(valid_from::date-r.confirm_date::date)))
	where id=r.mis;
end loop;
end;
$$;

do $$
declare
	r record;
begin
	for r in (select * from student s where student_history_id is null and (select count(*) from student_history where student_id=s.id)=0)
	loop
		INSERT INTO public.student_history(student_id,curriculum_version_id,study_form_code,language_code,is_special_need,is_representative_mandatory,status_code,study_load_code,fin_code,valid_from,inserted,version,inserted_by) VALUES 
		(r.id,r.curriculum_version_id, r.study_form_code,r.language_code, false,  false, 'OPPURSTAATUS_O','OPPEKOORMUS_MTA', 'FINALLIKAS_RE',current_timestamp(3),current_timestamp(3),1, r.inserted_by);
	end loop;
	with s as (select distinct s.id,first_value(sh.id) over(partition by s.id order by sh.id desc) as sh_id
	from student s
			 join student_history sh on s.id=sh.student_id and s.status_code=sh.status_code
	where s.student_history_id is null)
	update student st
		set student_history_id=s.sh_id
	from s
	where s.id=st.id;
	with s as (select distinct s.id,first_value(sh.id) over(partition by s.id order by sh.id asc) as sh_id
	from student s
			 join student_history sh on s.id=sh.student_id --and s.status_code=sh.status_code
	where s.student_history_id is null)
	update student st
		set student_history_id=s.sh_id
	from s
	where s.id=st.id;
  with s as (select sh.id as history_id, ss.*
	from student ss
  join student_history sh on ss.student_history_id=sh.id
where sh.inserted_by = 'DATA_TRANSFER_PROCESS' or UPPER (sh.inserted_by) LIKE UPPER ('%andmete%kanne%'))
	update student_history sh
	set curriculum_version_id=s.curriculum_version_id,
			study_form_code=s.study_form_code,
			student_group_id=s.student_group_id,
			email=s.email,
			language_code=s.language_code,
			is_special_need=s.is_special_need,
			is_representative_mandatory=s.is_representative_mandatory,
			special_need_code=s.special_need_code,
			student_card=s.student_card,
			previous_study_level_code=s.previous_study_level_code,
			status_code=s.status_code,
			ois_file_id=s.ois_file_id,
			curriculum_speciality_id=s.curriculum_speciality_id,
			study_start=s.study_start,
			study_end=s.study_end,
			nominal_study_end=s.nominal_study_end,
			study_load_code=s.study_load_code,
			fin_code=s.fin_code,
			fin_specific_code=s.fin_specific_code
from s
where sh.id=s.history_id;
end;
$$;

do $$
declare
	r record;
	rr record;
	p_id integer;
	prev_id	integer;
begin
	for r in (select distinct sh.student_id
							from directive_student sh 
							where (sh.inserted_by = 'DATA_TRANSFER_PROCESS' or UPPER (sh.inserted_by) LIKE UPPER ('%andmete%kanne%')) and sh.student_id!=4808)
	loop
		p_id:=0;
    prev_id:=0;
		for rr in (select dd.type_code, dd.confirm_date, sh.id as student_history_id, sh.valid_from
							 from student st
										join student_history sh on st.student_history_id=sh.id
										join directive_student ds on st.id=ds.student_id and (ds.inserted_by = 'DATA_TRANSFER_PROCESS' or UPPER (ds.inserted_by) LIKE UPPER ('%andmete%kanne%'))
										join directive dd on ds.directive_id=dd.id 
							where st.id=r.student_id order by dd.confirm_date desc)
		loop
			if prev_id=0 then
				prev_id:=rr.student_history_id;
			end if;
			insert into student_history(student_id, curriculum_version_id,study_form_code,student_group_id,email,language_code,is_special_need,is_representative_mandatory,special_need_code,student_card,previous_study_level_code,status_code,
																	ois_file_id,curriculum_speciality_id,study_start,study_end,nominal_study_end,study_load_code,fin_code,fin_specific_code,valid_from,inserted,inserted_by,version)
			select student_id,curriculum_version_id,study_form_code,student_group_id,email,language_code,is_special_need,is_representative_mandatory,special_need_code,student_card,previous_study_level_code,status_code,
																	ois_file_id,curriculum_speciality_id,study_start,study_end,nominal_study_end,study_load_code,fin_code,fin_specific_code,valid_from,current_timestamp(3), inserted_by,0
			from student_history where id=rr.student_history_id returning id into p_id;
			update student_history
				set valid_from=rr.confirm_date,
						valid_thru=(select h.valid_from from student_history h where h.id=prev_id),
						inserted_by='DTP viimane2',
						status_code=case rr.type_code 
													when 'KASKKIRI_EKSMAT' then 'OPPURSTAATUS_K' 
													when 'KASKKIRI_LOPET' then 'OPPURSTAATUS_L' 
													when 'KASKKIRI_IMMAT' then 'OPPURSTAATUS_O' 
													when 'KASKKIRI_IMMATV' then 'OPPURSTAATUS_O' 
													when 'KASKKIRI_ENNIST' then 'OPPURSTAATUS_O' else 'OPPURSTAATUS_O' end
			where id=p_id;
			update student_history
				set inserted_by='DTP viimane2'
			where id=rr.student_history_id and (inserted_by = 'DATA_TRANSFER_PROCESS' or UPPER (inserted_by) LIKE UPPER ('%andmete%kanne%'));
			prev_id:=p_id;
		end loop;
		--INSERT INTO public.student_history(student_id,curriculum_version_id,study_form_code,language_code,is_special_need,is_representative_mandatory,status_code,study_load_code,fin_code,valid_from,inserted,version,inserted_by) VALUES 
		--(r.id,r.curriculum_version_id, r.study_form_code,r.language_code, false,  false, 'OPPURSTAATUS_O','OPPEKOORMUS_MTA', 'FINALLIKAS_RE',current_timestamp(3),current_timestamp(3),1, r.inserted_by);
	end loop;
end;
$$;



create trigger scholarship_no_application_audit after insert or delete or update on scholarship_no_application for each row execute procedure hois_audit();
create trigger school_query_audit after insert or delete or update on school_query for each row execute procedure hois_audit();
create trigger school_query_criteria_audit after insert or delete or update on school_query_criteria for each row execute procedure hois_audit();

update classifier set name_en='Certificate' where code like 'TOEND_LIIK_SOOR';