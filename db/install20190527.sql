\c hois

INSERT INTO classifier (code, value, name_et, main_class_code, inserted, "valid", is_vocational, is_higher, version) VALUES ('TEEMAOIGUS_PILET', 'PILET', 'Õpilaspiletite haldamine', 'TEEMAOIGUS', now(), true, true, true, 0);
INSERT INTO user_role_default (object_code, permission_code, role_code) VALUES ('TEEMAOIGUS_PILET', 'OIGUS_V', 'ROLL_A'), ('TEEMAOIGUS_PILET', 'OIGUS_M', 'ROLL_A');

alter table student 
add column "is_student_card_repetitive" boolean NULL,    -- kas on korduv őpilaspilet
add column 	"student_card_status_code" varchar(100)	 NULL,    -- őpilaspileti staatus, vaikimisi puudub, klassifikaator OPILASPILET_STAATUS
add column "student_card_valid_thru" date NULL,    -- őpilaspilet kehtib kuni
add column 	"is_student_card_given" boolean NULL,    -- kas őpilaspilet on őpilase käes
add column 	"student_card_given_dt" date NULL,    -- őpilaspileti kätte andmise kp
add column 	"is_student_card_returned" boolean NULL,    -- kas őpilaspilet on tagastatud
add column 	"student_card_returned_dt" date NULL;    -- őpilaspileti tagastamise kp

alter table student_history 
add column "is_student_card_repetitive" boolean NULL,
add column	"student_card_status_code" varchar(100)	 NULL,
add column "student_card_valid_thru" date NULL,
add column "is_student_card_given" boolean NULL,
add column	"student_card_given_dt" date NULL,
add column	"is_student_card_returned" boolean NULL,
add column	"student_card_returned_dt" date NULL;


COMMENT ON COLUMN "student"."is_student_card_repetitive"	IS 'kas on korduv őpilaspilet';
COMMENT ON COLUMN "student"."student_card_status_code"	IS 'őpilaspileti staatus, vaikimisi puudub, klassifikaator OPILASPILET_STAATUS';
COMMENT ON COLUMN "student"."student_card_valid_thru"	IS 'őpilaspilet kehtib kuni';
COMMENT ON COLUMN "student"."is_student_card_given"	IS 'kas őpilaspilet on őpilase käes';
COMMENT ON COLUMN "student"."student_card_given_dt"	IS 'őpilaspileti kätte andmise kp';
COMMENT ON COLUMN "student"."is_student_card_returned"	IS 'kas őpilaspilet on tagastatud';
COMMENT ON COLUMN "student"."student_card_returned_dt"	IS 'őpilaspileti tagastamise kp';
CREATE INDEX "IXFK_student_classifier_10" ON "student" ("student_card_status_code" ASC);
CREATE INDEX "IXFK_student_history_classifier_09" ON "student_history" ("student_card_status_code" ASC);
ALTER TABLE "student" ADD CONSTRAINT "FK_student_classifier_09"	FOREIGN KEY ("student_card_status_code") REFERENCES "classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "student_history" ADD CONSTRAINT "FK_student_history_classifier_09"	FOREIGN KEY ("student_card_status_code") REFERENCES "classifier" ("code") ON DELETE No Action ON UPDATE No Action;




INSERT INTO "classifier" ("code", "value", "name_et", "inserted", "valid", "is_vocational", "is_higher", "version") VALUES ('OPILASPILET_STAATUS', 'OPILASPILET_STAATUS', 'Õpilaspileti staatus', current_timestamp(3), 't', 't', 't', '0');
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('OPILASPILET_STAATUS_P', 'P', 'Puudub', 'OPILASPILET_STAATUS',current_timestamp(3), 't', 't', 't', '0',null);
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('OPILASPILET_STAATUS_T', 'T', 'Tellitud', 'OPILASPILET_STAATUS',current_timestamp(3), 't', 't', 't', '0',null);
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('OPILASPILET_STAATUS_K', 'K', 'Kehtiv', 'OPILASPILET_STAATUS',current_timestamp(3), 't', 't', 't', '0',null);
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('OPILASPILET_STAATUS_G', 'G', 'Kätte antud', 'OPILASPILET_STAATUS',current_timestamp(3), 't', 't', 't', '0',null);
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('OPILASPILET_STAATUS_R', 'R', 'Tagastatud', 'OPILASPILET_STAATUS',current_timestamp(3), 't', 't', 't', '0',null);

update student set student_card_status_code='OPILASPILET_STAATUS_K' where coalesce(student_card,'x')!='x' and status_code in ('OPPURSTAATUS_O','OPPURSTAATUS_V','OPPURSTAATUS_A');



----------------------------------------------------------------------------------------------Ja veel uued alates 24.04


/* Create Tables */

CREATE TABLE "poll"
(
	"id" bigserial NOT NULL,
	"school_id" bigint NOT NULL,    -- viide õppeasutusele
	"name_et" varchar(255)	 NOT NULL,    -- küsitluse nimetus e.k.
	"name_en" varchar(255)	 NULL,    -- küsitluse nimetus i.k.
	"type_code" varchar(100)	 NOT NULL,    -- küsitluse tüüp, viide klassifikaatorile KYSITLUS
	"status_code" varchar(100)	 NOT NULL,    -- küsitluse staatus, viide klassifikaatorile KYSITLUS_STAATUS
	"valid_thru" date NOT NULL,    -- kuvatakse kuni
	"valid_from" date NOT NULL,    -- kuvatakse alates
	"foreword" varchar(4000)	 NULL,    -- eessõna
	"afterword" varchar(4000)	 NULL,    -- järelsõna
	"reminder_dt" date NULL,    -- meeldetuletuse saatmise kuupäev
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

CREATE TABLE "poll_target"
(
	"id" bigserial NOT NULL,
	"poll_id" bigint NOT NULL,    -- viide küsitlusele
	"inserted" timestamp without time zone NOT NULL,
	"target_code" varchar(100)	 NOT NULL,    -- küsitluse sihtgrupp, viide klassifikaatorile KYSITLUS_SIHT
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "poll"	IS 'küsitlus';
COMMENT ON COLUMN "poll"."school_id"	IS 'viide õppeasutusele';
COMMENT ON COLUMN "poll"."name_et"	IS 'küsitluse nimetus e.k.';
COMMENT ON COLUMN "poll"."name_en"	IS 'küsitluse nimetus i.k.';
COMMENT ON COLUMN "poll"."type_code"	IS 'küsitluse tüüp, viide klassifikaatorile KYSITLUS';
COMMENT ON COLUMN "poll"."status_code"	IS 'küsitluse staatus, viide klassifikaatorile KYSITLUS_STAATUS';
COMMENT ON COLUMN "poll"."valid_thru"	IS 'kuvatakse kuni';
COMMENT ON COLUMN "poll"."valid_from"	IS 'kuvatakse alates';
COMMENT ON COLUMN "poll"."foreword"	IS 'eessõna';
COMMENT ON COLUMN "poll"."afterword"	IS 'järelsõna';
COMMENT ON COLUMN "poll"."reminder_dt"	IS 'meeldetuletuse saatmise kuupäev';
COMMENT ON TABLE "poll_target"	IS 'küsitluse sihtrühm';
COMMENT ON COLUMN "poll_target"."poll_id"	IS 'viide küsitlusele';
COMMENT ON COLUMN "poll_target"."target_code"	IS 'küsitluse sihtgrupp, viide klassifikaatorile KYSITLUS_SIHT';


/* Create Primary Keys, Indexes, Uniques, Checks */
ALTER TABLE "poll" ADD CONSTRAINT "PK_poll"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_poll_classifier" ON "poll" ("type_code" ASC);
CREATE INDEX "IXFK_poll_classifier_02" ON "poll" ("status_code" ASC);
CREATE INDEX "IXFK_poll_school" ON "poll" ("school_id" ASC);
ALTER TABLE "poll_target" ADD CONSTRAINT "PK_poll_target"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_poll_target_classifier" ON "poll_target" ("target_code" ASC);
CREATE INDEX "IXFK_poll_target_poll" ON "poll_target" ("poll_id" ASC);

/* Create Foreign Key Constraints */

ALTER TABLE "poll" ADD CONSTRAINT "FK_poll_classifier"	FOREIGN KEY ("type_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "poll" ADD CONSTRAINT "FK_poll_classifier_02"	FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "poll" ADD CONSTRAINT "FK_poll_school"	FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "poll_target" ADD CONSTRAINT "FK_poll_target_classifier"	FOREIGN KEY ("target_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "poll_target" ADD CONSTRAINT "FK_poll_target_poll"	FOREIGN KEY ("poll_id") REFERENCES "poll" ("id") ON DELETE No Action ON UPDATE No Action;

INSERT INTO "classifier" ("code", "value", "name_et", "inserted", "valid", "is_vocational", "is_higher", "version") VALUES ('KYSITLUS', 'KYSITLUS', 'Küsitluse tüüp', current_timestamp(3), 't', 't', 't', '0');
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('KYSITLUS_O', 'O', 'Õppeaine või päeviku tagasiside', 'KYSITLUS',current_timestamp(3), 't', 't', 't', '0',null);
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('KYSITLUS_T', 'T', 'Õpetaja või õppejõu tagasiside', 'KYSITLUS',current_timestamp(3), 't', 't', 't', '0',null);
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('KYSITLUS_P', 'P', 'Praktika tagasiside', 'KYSITLUS',current_timestamp(3), 't', 't', 't', '0',null);
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('KYSITLUS_Y', 'Y', 'Üldine tagasiside', 'KYSITLUS',current_timestamp(3), 't', 't', 't', '0',null);
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('KYSITLUS_V', 'V', 'Õpilasesinduse valimised', 'KYSITLUS',current_timestamp(3), 't', 't', 't', '0',null);


INSERT INTO "classifier" ("code", "value", "name_et", "inserted", "valid", "is_vocational", "is_higher", "version") VALUES ('KYSITLUS_SIHT', 'KYSITLUS_SIHT', 'Küsitluse sihtrühm', current_timestamp(3), 't', 't', 't', '0');
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('KYSITLUS_SIHT_O', 'O', 'Õppijad', 'KYSITLUS_SIHT',current_timestamp(3), 't', 't', 't', '0',null);
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('KYSITLUS_SIHT_T', 'T', 'Õpetajad/õppejõud', 'KYSITLUS_SIHT',current_timestamp(3), 't', 't', 't', '0',null);
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('KYSITLUS_SIHT_E', 'E', 'Ettevõtte juhendajad', 'KYSITLUS_SIHT',current_timestamp(3), 't', 't', 't', '0',null);
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('KYSITLUS_SIHT_L', 'L', 'Õppija esindajad', 'KYSITLUS_SIHT',current_timestamp(3), 't', 't', 't', '0',null);
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('KYSITLUS_SIHT_V', 'V', 'Välised eksperdid', 'KYSITLUS_SIHT',current_timestamp(3), 't', 't', 't', '0',null);
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('KYSITLUS_SIHT_A', 'A', 'Administratiivsed töötajad', 'KYSITLUS_SIHT',current_timestamp(3), 't', 't', 't', '0',null);

INSERT INTO "classifier" ("code", "value", "name_et", "inserted", "valid", "is_vocational", "is_higher", "version") VALUES ('KYSITLUS_STAATUS', 'KYSITLUS_STAATUS', 'Küsitluse staatus', current_timestamp(3), 't', 't', 't', '0');
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('KYSITLUS_STAATUS_E', 'E', 'Koostamisel', 'KYSITLUS_STAATUS',current_timestamp(3), 't', 't', 't', '0',null);
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('KYSITLUS_STAATUS_K', 'K', 'Kinnitatud', 'KYSITLUS_STAATUS',current_timestamp(3), 't', 't', 't', '0',null);
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('KYSITLUS_STAATUS_L', 'L', 'Lõppenud', 'KYSITLUS_STAATUS',current_timestamp(3), 't', 't', 't', '0',null);

INSERT INTO "classifier" ("code", "value", "name_et", "main_class_code", "inserted", "valid", "is_vocational", "is_higher", "version", "inserted_by") VALUES ('KASKKIRI_KIITUS', 'KIITUS', 'Kiitus', 'KASKKIRI', current_timestamp(3), 't', 't', 'f', '0', NULL);
INSERT INTO "classifier" ("code", "value", "name_et", "main_class_code", "inserted", "valid", "is_vocational", "is_higher", "version", "inserted_by") VALUES ('KASKKIRI_NOOMI', 'NOOMI', 'Noomitus', 'KASKKIRI', current_timestamp(3), 't', 't', 'f', '0', NULL);
INSERT INTO "classifier" ("code", "value", "name_et", "main_class_code", "inserted", "valid", "is_vocational", "is_higher", "version", "inserted_by") VALUES ('KASKKIRI_PRAKTIK', 'PRAKTIK', 'Praktikale lubamine', 'KASKKIRI', current_timestamp(3), 't', 't', 'f', '0', NULL);
INSERT INTO "classifier" ("code", "value", "name_et", "main_class_code", "inserted", "valid", "is_vocational", "is_higher", "version", "inserted_by") VALUES ('KASKKIRI_OTEGEVUS', 'OTEGEVUS', 'Õppetegevus', 'KASKKIRI', current_timestamp(3), 't', 't', 'f', '0', NULL);
INSERT INTO "classifier" ("code", "value", "name_et", "main_class_code", "inserted", "valid", "is_vocational", "is_higher", "version", "inserted_by") VALUES ('KASKKIRI_INDOK', 'INDOK', 'Individuaalne õppekava', 'KASKKIRI', current_timestamp(3), 't', 't', 'f', '0', NULL);

alter table directive_student add column add_info text;
comment on column directive_student.add_info is 'lisainfo';

CREATE TABLE "poll_student_group"
(
	"id" bigserial NOT NULL,
	"poll_id" bigint NOT NULL,    -- viide küstlusele
	"inserted" timestamp without time zone NOT NULL,
	"student_group_id" bigint NOT NULL,    -- viide õpperühmale
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "poll_student_group"	IS 'küsitlusega seotud õpperühmad';
COMMENT ON COLUMN "poll_student_group"."poll_id"	IS 'viide küstlusele';
COMMENT ON COLUMN "poll_student_group"."student_group_id"	IS 'viide õpperühmale';

ALTER TABLE "poll_student_group" ADD CONSTRAINT "PK_poll_student_group"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_poll_student_group_poll" ON "poll_student_group" ("poll_id" ASC);
CREATE INDEX "IXFK_poll_student_group_student_group" ON "poll_student_group" ("student_group_id" ASC);

ALTER TABLE "poll_student_group" ADD CONSTRAINT "FK_poll_student_group_poll"	FOREIGN KEY ("poll_id") REFERENCES "poll" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "poll_student_group" ADD CONSTRAINT "FK_poll_student_group_student_group"	FOREIGN KEY ("student_group_id") REFERENCES "public"."student_group" ("id") ON DELETE No Action ON UPDATE No Action;

alter table poll
add column "is_teacher_comment" boolean NULL,    -- kas õpetaja saab kommenteerida
add column	"is_teacher_comment_visible" boolean NULL;    -- kas õpetaja kommentaar on õppurile nähtav

COMMENT ON COLUMN "poll"."is_teacher_comment" IS 'kas õpetaja saab kommenteerida';
COMMENT ON COLUMN "poll"."is_teacher_comment_visible"	IS 'kas õpetaja kommentaar on õppurile nähtav';


CREATE TABLE "poll_theme"
(
	"id" bigserial NOT NULL,
	"poll_id" bigint NOT NULL,
	"name_et" varchar(255)	 NOT NULL,    -- teema e.k.
	"name_en" varchar(255)	 NULL,    -- teema i.k.
	"order_nr" smallint NOT NULL,    -- jrk nr
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "poll_theme"	IS 'küsitluse teemad';
COMMENT ON COLUMN "poll_theme"."name_et"	IS 'teema e.k.';
COMMENT ON COLUMN "poll_theme"."name_en"	IS 'teema i.k.';
COMMENT ON COLUMN "poll_theme"."order_nr"	IS 'jrk nr';

/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE "poll_theme" ADD CONSTRAINT "PK_poll_theme"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_poll_theme_poll" ON "poll_theme" ("poll_id" ASC);

/* Create Foreign Key Constraints */

ALTER TABLE "poll_theme" ADD CONSTRAINT "FK_poll_theme_poll"	FOREIGN KEY ("poll_id") REFERENCES "poll" ("id") ON DELETE No Action ON UPDATE No Action;


CREATE TABLE "question"
(
	"id" bigserial NOT NULL,
	"school_id" bigint NOT NULL,
	"name_et" varchar(1000)	 NOT NULL,    -- küsimus e.k.
	"type_code" varchar(100)	 NOT NULL,    -- küsimuse vastuse tüüp, viide klassifikaatorile VASTUS
	"name_en" varchar(1000)	 NULL,    -- küsimus i.k.
	"add_info_et" varchar(4000)	 NULL,    -- lisainfo e.k.
	"add_info_en" varchar(4000)	 NULL,    -- lisainfo i.k.
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

CREATE TABLE "poll_theme_question"
(
	"id" bigserial NOT NULL,
	"poll_theme_id" bigint NOT NULL,    -- viide teemale
	"order_nr" smallint NOT NULL,    -- jrk nr
	"question_id" bigint NOT NULL,    -- viide küsimusele
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL,
	"is_required" boolean NOT NULL,    -- kas küsimus on vaba või kohustuslik
	"is_in_row" boolean NULL    -- kas kuvada reas
)
;

CREATE TABLE "poll_theme_question_file"
(
	"id" bigserial NOT NULL,
	"poll_theme_question_id" bigint NOT NULL,    -- viide küsimusele
	"inserted" timestamp without time zone NOT NULL,
	"ois_file_id" bigint NOT NULL,    -- viide failile
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "question" IS 'küsitluse küsimus';
COMMENT ON COLUMN "question"."name_et"	IS 'küsimus e.k.';
COMMENT ON COLUMN "question"."type_code"	IS 'küsimuse vastuse tüüp, viide klassifikaatorile VASTUS';
COMMENT ON COLUMN "question"."name_en"	IS 'küsimus i.k.';
COMMENT ON COLUMN "question"."add_info_et"	IS 'lisainfo e.k.';
COMMENT ON COLUMN "question"."add_info_en"	IS 'lisainfo i.k.';

COMMENT ON TABLE "poll_theme_question" IS 'küsitluse küsimused';
COMMENT ON COLUMN "poll_theme_question"."poll_theme_id"	IS 'viide teemale';
COMMENT ON COLUMN "poll_theme_question"."order_nr"	IS 'jrk nr';
COMMENT ON COLUMN "poll_theme_question"."question_id"	IS 'viide küsimusele';
COMMENT ON COLUMN "poll_theme_question"."is_required"	IS 'kas küsimus on vaba või kohustuslik';
COMMENT ON COLUMN "poll_theme_question"."is_in_row"	IS 'kas kuvada reas';

COMMENT ON TABLE "poll_theme_question_file"	IS 'küsimustega seotud failid';
COMMENT ON COLUMN "poll_theme_question_file"."poll_theme_question_id"	IS 'viide küsimusele';
COMMENT ON COLUMN "poll_theme_question_file"."ois_file_id"	IS 'viide failile';

ALTER TABLE "question" ADD CONSTRAINT "PK_question"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_question_classifier" ON "question" ("type_code" ASC);
CREATE INDEX "IXFK_question_school" ON "question" ("school_id" ASC);
ALTER TABLE "poll_theme_question" ADD CONSTRAINT "PK_poll_theme_question"	PRIMARY KEY ("id");

CREATE INDEX "IXFK_poll_theme_question_poll_theme" ON "poll_theme_question" ("poll_theme_id" ASC);
CREATE INDEX "IXFK_poll_theme_question_question" ON "poll_theme_question" ("question_id" ASC);
ALTER TABLE "poll_theme_question_file" ADD CONSTRAINT "PK_poll_theme_question_file"	PRIMARY KEY ("id");

CREATE INDEX "IXFK_poll_theme_question_file_ois_file" ON "poll_theme_question_file" ("ois_file_id" ASC);
CREATE INDEX "IXFK_poll_theme_question_file_poll_theme_question" ON "poll_theme_question_file" ("poll_theme_question_id" ASC);

ALTER TABLE "question" ADD CONSTRAINT "FK_question_classifier"	FOREIGN KEY ("type_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "question" ADD CONSTRAINT "FK_question_school"	FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "poll_theme_question" ADD CONSTRAINT "FK_poll_theme_question_poll_theme"	FOREIGN KEY ("poll_theme_id") REFERENCES "poll_theme" ("id") ON DELETE No Action ON UPDATE No Action;

ALTER TABLE "poll_theme_question" ADD CONSTRAINT "FK_poll_theme_question_question"	FOREIGN KEY ("question_id") REFERENCES "question" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "poll_theme_question_file" ADD CONSTRAINT "FK_poll_theme_question_file_ois_file"	FOREIGN KEY ("ois_file_id") REFERENCES "public"."ois_file" ("id") ON DELETE No Action ON UPDATE No Action;

ALTER TABLE "poll_theme_question_file" ADD CONSTRAINT "FK_poll_theme_question_file_poll_theme_question"	FOREIGN KEY ("poll_theme_question_id") REFERENCES "poll_theme_question" ("id") ON DELETE No Action ON UPDATE No Action;

alter table directive_student add column is_absence boolean;
comment on column directive_student.is_absence is 'kas märkida vabandatav puudumine';

alter table student_absence add column directive_student_id bigint;
create index IXFK_student_absence_directive_student on student_absence(directive_student_id);
alter table student_absence ADD CONSTRAINT FK_student_absence_directive_student foreign key (directive_student_id) references directive_student(id);


CREATE TABLE "directive_student_module"
(
	"id" bigserial NOT NULL,
	"curriculum_version_omodule_id" bigint NOT NULL,    -- viide moodulile
	"directive_student_id" bigint NOT NULL,    -- viide őppurile
	"add_info" varchar(4000)	 NOT NULL,    -- erisus
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "directive_student_module"	IS 'individuaalse őppekava moodulid';
COMMENT ON COLUMN "directive_student_module"."curriculum_version_omodule_id"	IS 'viide moodulile';
COMMENT ON COLUMN "directive_student_module"."directive_student_id"	IS 'viide őppurile';
COMMENT ON COLUMN "directive_student_module"."add_info"	IS 'erisus';
ALTER TABLE "directive_student_module" ADD CONSTRAINT "PK_directive_student_module"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_directive_student_module_curriculum_version_omodule" ON "directive_student_module" ("curriculum_version_omodule_id" ASC);
CREATE INDEX "IXFK_directive_student_module_directive_student" ON "directive_student_module" ("directive_student_id" ASC);
ALTER TABLE "directive_student_module" ADD CONSTRAINT "FK_directive_student_module_curriculum_version_omodule"	FOREIGN KEY ("curriculum_version_omodule_id") REFERENCES "public"."curriculum_version_omodule" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "directive_student_module" ADD CONSTRAINT "FK_directive_student_module_directive_student"	FOREIGN KEY ("directive_student_id") REFERENCES "public"."directive_student" ("id") ON DELETE No Action ON UPDATE No Action;

INSERT INTO "classifier" ("code", "value", "name_et", "main_class_code", "inserted", "valid", "is_vocational", "is_higher", "version", "inserted_by") VALUES ('KOMISJON_A', 'A', 'Tugiteenuste komisjon', 'KOMISJON', current_timestamp(3), 't', 't', 'f', '0', NULL);
INSERT INTO "classifier" ("code", "value", "name_et", "main_class_code", "inserted", "valid", "is_vocational", "is_higher", "version", "inserted_by") VALUES ('KASKKIRI_INDOKLOP', 'INDOKLOP', 'Individuaalse õppekava lõpetamine', 'KASKKIRI', current_timestamp(3), 't', 't', 'f', '0', NULL);

alter table directive_student add column directive_student_id bigint;
CREATE INDEX "IXFK_directive_student_directive_student" ON "directive_student" ("directive_student_id" ASC);
ALTER TABLE "directive_student" ADD CONSTRAINT "FK_directive_student_directive_student"	FOREIGN KEY ("directive_student_id") REFERENCES "public"."directive_student" ("id") ON DELETE No Action ON UPDATE No Action;


INSERT INTO "classifier" ("code", "value", "name_et", "inserted", "valid", "is_vocational", "is_higher", "version") VALUES ('VASTUS', 'VASTUS', 'Küsimuse vastuse tüüp', current_timestamp(3), 't', 't', 't', '0');
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('VASTUS_T', 'T', 'Tekst', 'VASTUS',current_timestamp(3), 't', 't', 't', '0',null);
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('VASTUS_M', 'M', 'Märkeruut', 'VASTUS',current_timestamp(3), 't', 't', 't', '0',null);
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('VASTUS_R', 'R', 'Raadionupp', 'VASTUS',current_timestamp(3), 't', 't', 't', '0',null);
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('VASTUS_V', 'V', 'Valikmenüü', 'VASTUS',current_timestamp(3), 't', 't', 't', '0',null);
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('VASTUS_S', 'S', 'Õpilasesinduse valik', 'VASTUS',current_timestamp(3), 't', 't', 't', '0',null);



CREATE TABLE "question_answer"
(
	"id" bigserial NOT NULL,
	"order_nr" smallint NOT NULL,    -- jrk nr
	"question_id" bigint NOT NULL,    -- viide küsimusele
	"name_et" varchar(255)	 NOT NULL,    -- vastus e.k.
	"name_en" varchar(255)	 NULL,    -- vastus i.k.
	"answer_nr" smallint NULL,    -- vastuse kaal, on vajalik selleks et arvutada keskmist
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "question_answer"	IS 'küsimuse vastusevariandid';
COMMENT ON COLUMN "question_answer"."order_nr"	IS 'jrk nr';
COMMENT ON COLUMN "question_answer"."question_id"	IS 'viide küsimusele';
COMMENT ON COLUMN "question_answer"."name_et"	IS 'vastus e.k.';
COMMENT ON COLUMN "question_answer"."name_en"	IS 'vastus i.k.';
COMMENT ON COLUMN "question_answer"."answer_nr"	IS 'vastuse kaal, on vajalik selleks et arvutada keskmist';

ALTER TABLE "question_answer" ADD CONSTRAINT "PK_question_answer"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_question_answer_question" ON "question_answer" ("question_id" ASC);
ALTER TABLE "question_answer" ADD CONSTRAINT "FK_question_answer_question"	FOREIGN KEY ("question_id") REFERENCES "question" ("id") ON DELETE No Action ON UPDATE No Action;

insert into classifier (code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version,inserted_by) values('AVALDUS_LIIK_TUGI','TUGI','Tugiteenus','AVALDUS_LIIK',current_timestamp(3),true,true,false,0,'Automaat');


alter table "application"
add column "committee_id" bigint NULL,    -- viide komisjonile, tugiteenuste avaldus
add column 	"decision" text NULL,    -- komisjoni otsus
add column 	"is_representative_confirmed" boolean NULL,    -- kas esindaja nőus
add column 	"implementation_plan" text NULL    -- rakendusplaan tugiteenuste puhul
;

CREATE TABLE "application_support_service"
(
	"id" bigserial NOT NULL,
	"application_id" bigint NOT NULL,    -- viide avaldusele
	"inserted" timestamp without time zone NOT NULL,
	"support_service_code" varchar(100)	 NOT NULL,    -- TUGITEENUS klassifikaator
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

CREATE TABLE "application_support_service_module"
(
	"id" bigserial NOT NULL,
	"application_support_service_id" bigint NOT NULL,    -- viide tugiteenusele
	"curriculum_version_omodule_id" bigint NOT NULL,    -- viide moodulile
	"add_info" varchar(4000)	 NOT NULL,    -- erisus
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON COLUMN "application"."committee_id"	IS 'viide komisjonile, tugiteenuste avaldus';
COMMENT ON COLUMN "application"."decision"	IS 'komisjoni otsus';
COMMENT ON COLUMN "application"."is_representative_confirmed"	IS 'kas esindaja nőus';
COMMENT ON COLUMN "application"."implementation_plan"	IS 'rakendusplaan tugiteenuste puhul';

COMMENT ON TABLE "application_support_service"	IS 'avaldusega seotud tugiteenused';
COMMENT ON COLUMN "application_support_service"."application_id"	IS 'viide avaldusele';
COMMENT ON COLUMN "application_support_service"."support_service_code"	IS 'TUGITEENUS klassifikaator';

COMMENT ON TABLE "application_support_service_module"	IS 'individ őppekava moodulid ja erisused';
COMMENT ON COLUMN "application_support_service_module"."application_support_service_id"	IS 'viide tugiteenusele';
COMMENT ON COLUMN "application_support_service_module"."curriculum_version_omodule_id"	IS 'viide moodulile';
COMMENT ON COLUMN "application_support_service_module"."add_info"	IS 'erisus';

CREATE INDEX "IXFK_application_committee" ON"application" ("committee_id" ASC);

ALTER TABLE "application_support_service" ADD CONSTRAINT "PK_application_support_service"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_application_support_service_application" ON "application_support_service" ("application_id" ASC);
CREATE INDEX "IXFK_application_support_service_classifier" ON "application_support_service" ("support_service_code" ASC);
ALTER TABLE "application_support_service_module" ADD CONSTRAINT "PK_application_support_service_module"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_application_support_service_module_application_support_service" ON "application_support_service_module" ("application_support_service_id" ASC);
CREATE INDEX "IXFK_application_support_service_module_curriculum_version_omodule" ON "application_support_service_module" ("curriculum_version_omodule_id" ASC);

ALTER TABLE"application" ADD CONSTRAINT "FK_application_committee"	FOREIGN KEY ("committee_id") REFERENCES "committee" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "application_support_service" ADD CONSTRAINT "FK_application_support_service_application"	FOREIGN KEY ("application_id") REFERENCES"application" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "application_support_service" ADD CONSTRAINT "FK_application_support_service_classifier"	FOREIGN KEY ("support_service_code") REFERENCES"classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "application_support_service_module" ADD CONSTRAINT "FK_application_support_service_module_application_support_service"	FOREIGN KEY ("application_support_service_id") REFERENCES "application_support_service" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "application_support_service_module" ADD CONSTRAINT "FK_application_support_service_module_curriculum_version_omodule"	FOREIGN KEY ("curriculum_version_omodule_id") REFERENCES "curriculum_version_omodule" ("id") ON DELETE No Action ON UPDATE No Action;

INSERT INTO "classifier" ("code", "value", "name_et", "inserted", "valid", "is_vocational", "is_higher", "version") VALUES ('TUGITEENUS', 'TUGITEENUS', 'Tugiteenused', current_timestamp(3), 't', 't', 'f', '0');
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('TUGITEENUS_1', '1', 'Individuaalse õppekava koostamine ja rakendamine', 'TUGITEENUS',current_timestamp(3), 't', 't', 'f', '0','1');
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('TUGITEENUS_2', '2', 'Individuaalse üleminekuplaani koostamine', 'TUGITEENUS',current_timestamp(3), 't', 't', 'f', '0','2');
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('TUGITEENUS_3', '3', 'Õpe 4 – 8 õppijaga väikerühmas (TÕKilt kutseõppesse siirdunud)', 'TUGITEENUS',current_timestamp(3), 't', 't', 'f', '0','3');
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('TUGITEENUS_4', '4', 'Õpe vähemalt 12 õppijaga väikerühmas (LÕKilt kutseõppesse siirdunud)', 'TUGITEENUS',current_timestamp(3), 't', 't', 'f', '0','4');
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('TUGITEENUS_5', '5', 'Mõõdukas pedagoogiline sekkumine', 'TUGITEENUS',current_timestamp(3), 't', 't', 'f', '0','5');
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('TUGITEENUS_6', '6', 'Põhjalik pedagoogiline sekkumine', 'TUGITEENUS',current_timestamp(3), 't', 't', 'f', '0','6');
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('TUGITEENUS_7', '7', 'Täiendav eesti keele õpe', 'TUGITEENUS',current_timestamp(3), 't', 't', 'f', '0','7');

alter table application add column is_decided boolean;
comment on column application.is_decided is 'kas tugiteenuse andmise otsus on positiivne';

INSERT INTO "classifier" ("code", "value", "value2", "name_et", "name_en", "name_ru", "parent_class_code", "main_class_code", "inserted", "changed", "valid", "description", "valid_from", "valid_thru", "extraval1", "extraval2", "ehis_value", "is_vocational", "is_higher", "version", "inserted_by", "changed_by") VALUES ('LOGIN_TYPE_T', 'T', NULL, 'TARA', NULL, NULL, NULL, 'LOGIN_TYPE', '2017-11-03 22:37:18.745868', NULL, 't', NULL, NULL, NULL, NULL, NULL, NULL, 't', 't', '0', NULL, NULL);
INSERT INTO "classifier" ("code", "value", "value2", "name_et", "name_en", "name_ru", "parent_class_code", "main_class_code", "inserted", "changed", "valid", "description", "valid_from", "valid_thru", "extraval1", "extraval2", "ehis_value", "is_vocational", "is_higher", "version", "inserted_by", "changed_by") VALUES ('LOGIN_TYPE_H', 'H', NULL, 'HarID', NULL, NULL, NULL, 'LOGIN_TYPE', '2017-11-03 22:37:18.745868', NULL, 't', NULL, NULL, NULL, NULL, NULL, NULL, 't', 't', '0', NULL, NULL);


CREATE TABLE "ws_tara_log"
(
	"id" bigserial NOT NULL,
	"uid" varchar(1000)	 NOT NULL,
	"type_code" varchar(100)	 NOT NULL,    -- viide klassifikaatorile TARA_PARING
	"request_url" varchar(4000)	 NOT NULL,
	"request_param" varchar(4000)	 NULL,
	"response" text NULL,
	"has_errors" boolean NOT NULL,
	"log_txt" text NULL,
	"inserted" timestamp without time zone NOT NULL
)
;
COMMENT ON TABLE "ws_tara_log"	IS 'TARA päringute logi';
COMMENT ON COLUMN "ws_tara_log"."type_code"	IS 'viide klassifikaatorile TARA_PARING';
ALTER TABLE "ws_tara_log" ADD CONSTRAINT "PK_ws_tara_log"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_ws_tara_log_classifier" ON "ws_tara_log" ("type_code" ASC);
CREATE INDEX "IX_ws_tara_log" ON "ws_tara_log" ("uid" ASC);
ALTER TABLE "ws_tara_log" ADD CONSTRAINT "FK_ws_tara_log_classifier"	FOREIGN KEY ("type_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;

INSERT INTO "classifier" ("code", "value", "name_et", "inserted", "valid", "is_vocational", "is_higher", "version") VALUES ('TARA_PARING', 'TARA_PARING', 'TARA päringu liik', current_timestamp(3), 't', 't', 'f', '0');
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('TARA_PARING_A', 'A', 'Autentimispäring', 'TARA_PARING',current_timestamp(3), 't', 't', 't', '0','1');
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('TARA_PARING_T', 'T', 'Tagasisuunamispäring', 'TARA_PARING',current_timestamp(3), 't', 't', 't', '0','2');
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('TARA_PARING_I', 'I', 'Identsustõendipäring', 'TARA_PARING',current_timestamp(3), 't', 't', 't', '0','3');

INSERT INTO classifier (code, value, name_et, main_class_code, inserted, "valid", is_vocational, is_higher, version) VALUES ('TEEMAOIGUS_INDIVID', 'INDIVID', 'Individ. õppekava statistika', 'TEEMAOIGUS', now(), true, true, true, 0);
INSERT INTO user_role_default (object_code, permission_code, role_code) VALUES ('TEEMAOIGUS_INDIVID', 'OIGUS_V', 'ROLL_A');


alter TABLE "application"
add column "committee_added" timestamp without time zone NULL,    -- komisjoni lisamise aeg
add column "committee_decision_added" timestamp without time zone NULL,    -- komisjoni otsuse lisamise kp
add column "representative_confirmed" timestamp without time zone NULL,    -- esindaja/őppija nőusoleku lisamise kp
add column "committee_add_info" varchar(4000)	 NULL,    -- kimosjoni lisainfo
add column "representative_decision_add_info" varchar(4000)	 NULL    -- őppija/esindaja nőusoleku lisainfo
;

COMMENT ON COLUMN "application"."committee_added"	IS 'komisjoni lisamise aeg';
COMMENT ON COLUMN "application"."committee_decision_added"	IS 'komisjoni otsuse lisamise kp';
COMMENT ON COLUMN "application"."representative_confirmed"	IS 'esindaja/őppija nőusoleku lisamise kp';
COMMENT ON COLUMN "application"."committee_add_info"	IS 'kimosjoni lisainfo';
COMMENT ON COLUMN "application"."representative_decision_add_info"	IS 'őppija/esindaja nőusoleku lisainfo';
insert into classifier (code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('TEATE_LIIK_OP_AVALDUS_YL','OP_AVALDUS_YL','Õppija avaldus on ülevaatamisel','TEATE_LIIK',CURRENT_TIMESTAMP(3),true,true,false,0);
insert into classifier (code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('TEATE_LIIK_TUGI_LOPP','TUGI_LOPP','Tugimeetmete rakendamise lõpp','TEATE_LIIK',CURRENT_TIMESTAMP(3),true,true,false,0);
INSERT INTO "classifier" ("code", "value", "name_et", "main_class_code", "inserted", "valid", "is_vocational", "is_higher", "version", "inserted_by") 
VALUES ('KASKKIRI_TUGI', 'TUGI', 'Tugimeetmete rakendamine', 'KASKKIRI', current_timestamp(3), 't', 't', 'f', '0', NULL);
INSERT INTO "classifier" ("code", "value", "name_et", "main_class_code", "inserted", "valid", "is_vocational", "is_higher", "version", "inserted_by") 
VALUES ('KASKKIRI_TUGILOPP', 'TUGILOPP', 'Tugimeetmete rakendamise lõpp', 'KASKKIRI', current_timestamp(3), 't', 't', 'f', '0', NULL);
INSERT INTO classifier (code, value, name_et, main_class_code, inserted, "valid", is_vocational, is_higher, version) VALUES ('TEEMAOIGUS_KYSITLUS', 'KYSITLUS', 'Küsitlused', 'TEEMAOIGUS', now(), true, true, true, 0);
INSERT INTO user_role_default (object_code, permission_code, role_code) VALUES ('TEEMAOIGUS_KYSITLUS', 'OIGUS_V', 'ROLL_A'), ('TEEMAOIGUS_KYSITLUS', 'OIGUS_M', 'ROLL_A');


CREATE TABLE "response"
(
	"id" bigserial NOT NULL,
	"poll_id" bigint NOT NULL,
	"inserted" timestamp without time zone NOT NULL,
	"status_code" varchar(100)	 NOT NULL,    -- vastuse staatus, viide klassifikaatorile KYSITVASTUSSTAATUS
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

CREATE TABLE "response_object"
(
	"response_id" bigint NOT NULL,    -- viide vastusele
  "student_id" bigint NULL,    -- viide őppurile
	"teacher_id" bigint NULL,    -- viide őpetajale
	"poll_target_id" bigint NOT NULL,    -- viide sihtgrupile
	"person_id" bigint NULL,    -- viide isikule
	"contract_supervisor_id" bigint NULL,    -- viide ettevőtte juhendajale
	"curriculum_version_id" bigint NULL,    -- őppekava versioon őppuri puhul, pannakse lisamise hetkel, vajalik hiljem statistika tegemiseks
	"study_form_code" varchar(100)	 NULL,    -- őppuri őppevorm, viide klassifikaatorile OPPEVORM, pannakse lisamsie htekel, vajalik statistika tegemiseks
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

CREATE TABLE "response_question_answer"
(
	"id" bigserial NOT NULL,
	"response_id" bigint NOT NULL,    -- viide vastamisele
	"answer_txt" varchar(4000)	 NULL,
	"question_id" bigint NOT NULL,    -- viide küsimusele
	"answer_nr" smallint NULL,    -- vastuse nr (sama mis question_answer)
	"inserted" timestamp without time zone NOT NULL,
	"question_answer_id" bigint NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "response"	IS 'küsitluse vastamised';
COMMENT ON COLUMN "response"."status_code"	IS 'vastuse staatus, viide klassifikaatorile KYSITVASTUSSTAATUS';
COMMENT ON TABLE "response_object"	IS 'kes vastas, eraldi tabelis';
COMMENT ON COLUMN "response_object"."student_id"	IS 'viide őppurile';
COMMENT ON COLUMN "response_object"."response_id"	IS 'viide vastusele';
COMMENT ON COLUMN "response_object"."teacher_id"	IS 'viide őpetajale';
COMMENT ON COLUMN "response_object"."poll_target_id"	IS 'viide sihtgrupile';
COMMENT ON COLUMN "response_object"."person_id"	IS 'viide isikule';
COMMENT ON COLUMN "response_object"."contract_supervisor_id"	IS 'viide ettevőtte juhendajale';
COMMENT ON COLUMN "response_object"."curriculum_version_id"	IS 'őppekava versioon őppuri puhul, pannakse lisamise hetkel, vajalik hiljem statistika tegemiseks';
COMMENT ON COLUMN "response_object"."study_form_code"	IS 'őppuri őppevorm, viide klassifikaatorile OPPEVORM, pannakse lisamsie htekel, vajalik statistika tegemiseks';
COMMENT ON TABLE "response_question_answer"	IS 'küsimuse vastused';
COMMENT ON COLUMN "response_question_answer"."response_id"	IS 'viide vastamisele';
COMMENT ON COLUMN "response_question_answer"."question_id"	IS 'viide küsimusele';
COMMENT ON COLUMN "response_question_answer"."answer_nr"	IS 'vastuse nr (sama mis question_answer)';

ALTER TABLE "response" ADD CONSTRAINT "PK_response"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_response_classifier" ON "response" ("status_code" ASC);
CREATE INDEX "IXFK_response_poll" ON "response" ("poll_id" ASC);
ALTER TABLE "response_object" ADD CONSTRAINT "PK_response_object"	PRIMARY KEY ("response_id");
CREATE INDEX "IXFK_response_object_classifier" ON "response_object" ("study_form_code" ASC);
CREATE INDEX "IXFK_response_object_contract_supervisor" ON "response_object" ("contract_supervisor_id" ASC);
CREATE INDEX "IXFK_response_object_curriculum_version" ON "response_object" ("curriculum_version_id" ASC);
CREATE INDEX "IXFK_response_object_person" ON "response_object" ("person_id" ASC);
CREATE INDEX "IXFK_response_object_poll_target" ON "response_object" ("poll_target_id" ASC);
CREATE INDEX "IXFK_response_object_response" ON "response_object" ("response_id" ASC);
CREATE INDEX "IXFK_response_object_student" ON "response_object" ("student_id" ASC);
CREATE INDEX "IXFK_response_object_teacher" ON "response_object" ("teacher_id" ASC);
CREATE INDEX "UQ_response_object" ON "response_object" ("response_id" ASC,"student_id" ASC,"teacher_id" ASC,"poll_target_id" ASC,"person_id" ASC,"contract_supervisor_id" ASC);
ALTER TABLE "response_question_answer" ADD CONSTRAINT "PK_response_question_answer"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_response_question_answer_question" ON "response_question_answer" ("question_id" ASC);
CREATE INDEX "IXFK_response_question_answer_question_answer" ON "response_question_answer" ("question_answer_id" ASC);
CREATE INDEX "IXFK_response_question_answer_response" ON "response_question_answer" ("response_id" ASC);

/* Create Foreign Key Constraints */

ALTER TABLE "response" ADD CONSTRAINT "FK_response_classifier"	FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "response" ADD CONSTRAINT "FK_response_poll"	FOREIGN KEY ("poll_id") REFERENCES "poll" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "response_object" ADD CONSTRAINT "FK_response_object_classifier"	FOREIGN KEY ("study_form_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "response_object" ADD CONSTRAINT "FK_response_object_contract_supervisor"	FOREIGN KEY ("contract_supervisor_id") REFERENCES "contract_supervisor" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "response_object" ADD CONSTRAINT "FK_response_object_curriculum_version"	FOREIGN KEY ("curriculum_version_id") REFERENCES "public"."curriculum_version" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "response_object" ADD CONSTRAINT "FK_response_object_person"	FOREIGN KEY ("person_id") REFERENCES "public"."person" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "response_object" ADD CONSTRAINT "FK_response_object_poll_target"	FOREIGN KEY ("poll_target_id") REFERENCES "poll_target" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "response_object" ADD CONSTRAINT "FK_response_object_response"	FOREIGN KEY ("response_id") REFERENCES "response" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "response_object" ADD CONSTRAINT "FK_response_object_student"	FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "response_object" ADD CONSTRAINT "FK_response_object_teacher"	FOREIGN KEY ("teacher_id") REFERENCES "public"."teacher" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "response_question_answer" ADD CONSTRAINT "FK_response_question_answer_question"	FOREIGN KEY ("question_id") REFERENCES "question" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "response_question_answer" ADD CONSTRAINT "FK_response_question_answer_question_answer"	FOREIGN KEY ("question_answer_id") REFERENCES "question_answer" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "response_question_answer" ADD CONSTRAINT "FK_response_question_answer_response"	FOREIGN KEY ("response_id") REFERENCES "response" ("id") ON DELETE No Action ON UPDATE No Action;

INSERT INTO "classifier" ("code", "value", "name_et", "inserted", "valid", "is_vocational", "is_higher", "version") VALUES ('KYSITVASTUSSTAATUS', 'KYSITVASTUSSTAATUS', 'Märkuse põhjus', current_timestamp(3), 't', 't', 't', '0');
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('KYSITVASTUSSTAATUS_A', 'A', 'Alustamata', 'KYSITVASTUSSTAATUS',current_timestamp(3), 't', 't', 't', '0',null);
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('KYSITVASTUSSTAATUS_P', 'P', 'Pooleli', 'KYSITVASTUSSTAATUS',current_timestamp(3), 't', 't', 't', '0',null);
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('KYSITVASTUSSTAATUS_V', 'V', 'Vastatud', 'KYSITVASTUSSTAATUS',current_timestamp(3), 't', 't', 't', '0',null);

insert into classifier (code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('TEATE_LIIK_KYSI_EV_JUHENDAJA','KYSI_EV_JUHENDAJA','Küsitlus: ettevõtte juhendajale saadetav teade','TEATE_LIIK',CURRENT_TIMESTAMP(3),true,true,false,0);

create trigger application_support_service_audit after insert or delete or update on application_support_service for each row execute procedure hois_audit();
create trigger application_support_service_module_audit after insert or delete or update on application_support_service_module for each row execute procedure hois_audit();
create trigger directive_student_module_audit after insert or delete or update on directive_student_module for each row execute procedure hois_audit();
create trigger poll_audit after insert or delete or update on poll for each row execute procedure hois_audit();
create trigger poll_student_group_audit after insert or delete or update on poll_student_group for each row execute procedure hois_audit();
create trigger poll_target_audit after insert or delete or update on poll_target for each row execute procedure hois_audit();
create trigger poll_theme_audit after insert or delete or update on poll_theme for each row execute procedure hois_audit();
create trigger poll_theme_question_audit after insert or delete or update on poll_theme_question for each row execute procedure hois_audit();
create trigger poll_theme_question_file_audit after insert or delete or update on poll_theme_question_file for each row execute procedure hois_audit();
create trigger practice_journal_evaluation_audit after insert or delete or update on practice_journal_evaluation for each row execute procedure hois_audit();
create trigger question_answer_audit after insert or delete or update on question_answer for each row execute procedure hois_audit();
create trigger question_audit after insert or delete or update on question for each row execute procedure hois_audit();
create trigger response_audit after insert or delete or update on response for each row execute procedure hois_audit();
create trigger response_object_audit after insert or delete or update on response_object for each row execute procedure hois_audit();
create trigger response_question_answer_audit after insert or delete or update on response_question_answer for each row execute procedure hois_audit();
create trigger student_remark_audit after insert or delete or update on student_remark for each row execute procedure hois_audit();

alter table poll add column journal_from date;
alter table poll add column journal_thru date;