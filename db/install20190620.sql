\c hois

INSERT INTO classifier ("code", "value", "value2", "name_et", "main_class_code", "inserted", "valid", "is_vocational", "is_higher", "version", "inserted_by") 
VALUES ('TEATE_LIIK_SILT_kysitlus_url', 'kysitlus_url', '<kysitlus_url>', 'Küsitluse url','TEATE_LIIK_SILT', now(), 't', 't', 't', '0', 'Automaat');
alter table contract_supervisor add poll_url varchar(4000);
comment on column contract_supervisor.poll_url is 'Küsitluse url';

comment on column response_question_answer.answer_nr is 'vastuse kaal, on vajalik selleks et arvutada keskmist (sama mis question_answer)';
alter table student add dormitory_code varchar(100);
alter table student_history add dormitory_code varchar(100);
comment on column student.dormitory_code is 'viide klassifikaatorile YHISELAMU';
comment on column student_history.dormitory_code is 'viide klassifikaatorile YHISELAMU';
alter table student add CONSTRAINT FK_student_classifier11 foreign key (dormitory_code) REFERENCES "classifier" ("code") ON DELETE No Action ON UPDATE No Action;
alter table student_history add CONSTRAINT FK_student_history_classifier11 foreign key (dormitory_code) REFERENCES "classifier" ("code") ON DELETE No Action ON UPDATE No Action;
create index IXFK_student_classifier11 on student(dormitory_code asc);
create index IXFK_student_history_classifier11 on student(dormitory_code asc);

INSERT INTO "classifier" ("code", "value", "name_et", "inserted", "valid", "is_vocational", "is_higher", "version") VALUES ('YHISELAMU', 'YHISELAMU', 'Ühiselamu', current_timestamp(3), 't', 't', 't', '0');
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('YHISELAMU_E', 'E', 'Ei vaja ühiselamut', 'YHISELAMU',current_timestamp(3), 't', 't', 't', '0','E');
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('YHISELAMU_L', 'L', 'Elab ühiselamus', 'YHISELAMU',current_timestamp(3), 't', 't', 't', '0','L');
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('YHISELAMU_S', 'S', 'Kasutab ainult sessiooni ajal', 'YHISELAMU',current_timestamp(3), 't', 't', 't', '0','S');
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('YHISELAMU_V', 'V', 'Vajab ühiselamut, kuid ei ela selles', 'YHISELAMU',current_timestamp(3), 't', 't', 't', '0','V');



CREATE TABLE "student_support_service"
(
	"id" bigserial NOT NULL ,
	"entry_date" date NOT NULL,    -- sissekande kp
	"student_id" bigint NOT NULL,
	"name_et" varchar(255)	 NOT NULL,    -- pealkiri
	"content" text NOT NULL,    -- sisu
	"is_public" boolean NOT NULL,
	"validity_code" varchar(100)	 NULL,    -- viide klassifikaator TUGIKEHTIV
	"ois_file_id" bigint NULL,    -- viide failile
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "student_support_service"	IS 'őppuri tugimeetmete arengukaart';
COMMENT ON COLUMN "student_support_service"."entry_date"	IS 'sissekande kp';
COMMENT ON COLUMN "student_support_service"."name_et"	IS 'pealkiri';
COMMENT ON COLUMN "student_support_service"."content"	IS 'sisu';
COMMENT ON COLUMN "student_support_service"."validity_code"	IS 'viide klassifikaator TUGIKEHTIV';

COMMENT ON COLUMN "student_support_service"."ois_file_id"	IS 'viide failile';


/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE "student_support_service" ADD CONSTRAINT "PK_student_support_service"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_student_support_service_classifier" ON "student_support_service" ("validity_code" ASC);
CREATE INDEX "IXFK_student_support_service_ois_file" ON "student_support_service" ("ois_file_id" ASC);

CREATE INDEX "IXFK_student_support_service_student" ON "student_support_service" ("student_id" ASC);

/* Create Foreign Key Constraints */

ALTER TABLE "student_support_service" ADD CONSTRAINT "FK_student_support_service_classifier"	FOREIGN KEY ("validity_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "student_support_service" ADD CONSTRAINT "FK_student_support_service_ois_file"	FOREIGN KEY ("ois_file_id") REFERENCES "public"."ois_file" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "student_support_service" ADD CONSTRAINT "FK_student_support_service_student"	FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE No Action ON UPDATE No Action;


INSERT INTO "classifier" ("code", "value", "name_et", "inserted", "valid", "is_vocational", "is_higher", "version") VALUES ('TUGIKEHTIV', 'TUGIKEHTIV', 'Tugimeetme kehtivus', current_timestamp(3), 't', 't', 't', '0');
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version") VALUES ('TUGIKEHTIV_K', 'K', 'Kehtiv', 'TUGIKEHTIV',current_timestamp(3), 't', 't', 't', '0');
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version") VALUES ('TUGIKEHTIV_L', 'L', 'Lõpetatud', 'TUGIKEHTIV',current_timestamp(3), 't', 't', 't', '0');

alter table directive_student add dormitory_code varchar(100);
comment on column directive_student.dormitory_code is 'viide klassifikaatorile YHISELAMU';
alter table directive_student add CONSTRAINT FK_directive_student_classifier11 foreign key (dormitory_code) REFERENCES "classifier" ("code") ON DELETE No Action ON UPDATE No Action;
create index IXFK_directive_student_classifier11 on directive_student(dormitory_code asc);

update student set dormitory_code = 'YHISELAMU_E' where dormitory_code is null ;

with s as (select cp.*, cpt2.id as uus_id
						from journal jj
								 join journal_capacity cp on jj.id=cp.journal_id
								 join journal_capacity_type cpt on cpt.id=cp.journal_capacity_type_id
								 join journal_capacity_type cpt2 on cpt2.journal_id=jj.id and cpt.journal_id!=cpt2.journal_id and cpt.capacity_type_code=cpt2.capacity_type_code
						where jj.id!=cpt.journal_id)
update journal_capacity cp
	set journal_capacity_type_id=s.uus_id
from s
where cp.id=s.id;

alter table response_object add poll_url varchar(4000);
comment on column response_object.poll_url is 'Küsitluse url';

alter table contract_supervisor drop column poll_url;

INSERT INTO classifier (code, value, name_et, main_class_code, inserted, "valid", is_vocational, is_higher, version) VALUES ('TEEMAOIGUS_TUGITEENUS', 'TUGITEENUS', 'Tugiteenus', 'TEEMAOIGUS', now(), true, true, true, 0);
INSERT INTO user_role_default (object_code, permission_code, role_code) VALUES ('TEEMAOIGUS_TUGITEENUS', 'OIGUS_V', 'ROLL_A'), ('TEEMAOIGUS_TUGITEENUS', 'OIGUS_M', 'ROLL_A');

alter TABLE "poll"
	add column "is_student_visible" boolean NULL,    -- kas őppija  näeb vastused
	add column "is_theme_pageable" boolean NULL,    -- kas teemad kuvatakse eraldi lehetedel
	add column "poll_url" varchar(4000)	 NULL,    -- küsitluse url, mida kasutatakse väliste isikue puhul
	add column "study_period_id" bigint NULL    -- viide deklareerimise őppeperioodile, kasutatakse kőrghariduse puhul
;

CREATE TABLE "poll_teacher_occupation"
(
	"id" bigserial NOT NULL,
	"poll_id" bigint NOT NULL,    -- viide sihtgrupile (őpetaja)
	"inserted" timestamp without time zone NOT NULL,
	"teacher_occupation_id" bigint NOT NULL,    -- viide őpetajale
	"inserted_by" varchar(100)	 NOT NULL,
	"version" integer NOT NULL,
	"changed" timestamp without time zone NULL,
	"changed_by" varchar(100)	 NULL
)
;

CREATE TABLE "poll_journal"
(
	"id" bigserial NOT NULL ,
	"poll_id" bigint NOT NULL,    -- viide küsitlusele
	"inserted" timestamp without time zone NOT NULL,
	"journal_id" bigint NOT NULL,    -- viide päevikule
	"changed" timestamp without time zone	 NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100) NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON COLUMN "poll"."is_student_visible"
	IS 'kas őppija  näeb vastused'
;

COMMENT ON COLUMN "poll"."is_theme_pageable"
	IS 'kas teemad kuvatakse eraldi lehetedel'
;

COMMENT ON COLUMN "poll"."poll_url"
	IS 'küsitluse url, mida kasutatakse väliste isikue puhul'
;

COMMENT ON COLUMN "poll"."study_period_id"
	IS 'viide deklareerimise őppeperioodile, kasutatakse kőrghariduse puhul'
;

COMMENT ON TABLE "poll_teacher_occupation"
	IS 'őpetaja ametid, kellele see küsitlus mőeldud on'
;

COMMENT ON COLUMN "poll_teacher_occupation"."poll_id"	IS 'viide sihtgrupile (őpetaja)';

COMMENT ON COLUMN "poll_teacher_occupation"."teacher_occupation_id"	IS 'viide őpetajale';

COMMENT ON TABLE "poll_journal"	IS 'küsitlusega seotud päevikud';

COMMENT ON COLUMN "poll_journal"."poll_id"	IS 'viide küsitlusele';
COMMENT ON COLUMN "poll_journal"."journal_id"	IS 'viide päevikule';

/* Create Primary Keys, Indexes, Uniques, Checks */

CREATE INDEX "IXFK_poll_study_period" ON "poll" ("study_period_id" ASC);
ALTER TABLE "poll_teacher_occupation" ADD CONSTRAINT "PK_poll_teacher_occupation"	PRIMARY KEY ("id");

CREATE INDEX "IXFK_poll_teacher_occupation_poll" ON "poll_teacher_occupation" ("poll_id" ASC);
CREATE INDEX "IXFK_poll_teacher_occupation_teacher_occupation" ON "poll_teacher_occupation" ("teacher_occupation_id" ASC);
ALTER TABLE "poll_journal" ADD CONSTRAINT "PK_Table1"	PRIMARY KEY ("id");

CREATE INDEX "IXFK_poll_journal_journal" ON "poll_journal" ("journal_id" ASC);
CREATE INDEX "IXFK_poll_journal_poll" ON "poll_journal" ("poll_id" ASC);

/* Create Foreign Key Constraints */

ALTER TABLE "poll" ADD CONSTRAINT "FK_poll_study_period"	FOREIGN KEY ("study_period_id") REFERENCES "public"."study_period" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "poll_teacher_occupation" ADD CONSTRAINT "FK_poll_teacher_occupation_poll"	FOREIGN KEY ("poll_id") REFERENCES "poll" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "poll_teacher_occupation" ADD CONSTRAINT "FK_poll_teacher_occupation_teacher_occupation"	FOREIGN KEY ("teacher_occupation_id") REFERENCES "public"."teacher_occupation" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "poll_journal" ADD CONSTRAINT "FK_poll_journal_journal"	FOREIGN KEY ("journal_id") REFERENCES "public"."journal" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "poll_journal" ADD CONSTRAINT "FK_poll_journal_poll"	FOREIGN KEY ("poll_id") REFERENCES "poll" ("id") ON DELETE No Action ON UPDATE No Action;

alter TABLE "response_object"
add column 	"journal_id" bigint NULL,    -- viide päevikule
add column 	"subject_id" bigint NULL,    -- viide őppeainele
add column 	"practice_journal_id" bigint NULL    -- viide praktikapäevikule
;


COMMENT ON COLUMN "response_object"."journal_id"	IS 'viide päevikule';
COMMENT ON COLUMN "response_object"."subject_id"	IS 'viide őppeainele';
COMMENT ON COLUMN "response_object"."practice_journal_id"	IS 'viide praktikapäevikule';

CREATE INDEX "IXFK_response_object_journal" ON "response_object" ("journal_id" ASC);
CREATE INDEX "IXFK_response_object_practice_journal" ON "response_object" ("practice_journal_id" ASC);
CREATE INDEX "IXFK_response_object_subject" ON "response_object" ("subject_id" ASC);

ALTER TABLE "response_object" ADD CONSTRAINT "FK_response_object_journal"	FOREIGN KEY ("journal_id") REFERENCES "public"."journal" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "response_object" ADD CONSTRAINT "FK_response_object_practice_journal"	FOREIGN KEY ("practice_journal_id") REFERENCES "practice_journal" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "response_object" ADD CONSTRAINT "FK_response_object_subject"	FOREIGN KEY ("subject_id") REFERENCES "public"."subject" ("id") ON DELETE No Action ON UPDATE No Action;


alter TABLE "response_question_answer" add column	"response_subject_id" bigint NULL;    -- viide őppeainele/päevikule

CREATE TABLE "response_subject"
(
	"id" bigserial NOT NULL ,
	"response_id" bigint NOT NULL,
	"subject_id" bigint NULL,    -- viide őpepainele
	"inserted" timestamp without time zone NOT NULL,
	"journal_id" bigint NULL,    -- viide päevikule
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

COMMENT ON TABLE "response_subject"	IS 'vastamisega seotud päevik/őppeaine';
COMMENT ON COLUMN "response_subject"."subject_id"	IS 'viide őpepainele';
COMMENT ON COLUMN "response_subject"."journal_id"	IS 'viide päevikule';

CREATE INDEX "IXFK_response_question_answer_response_subject" ON "response_question_answer" ("response_subject_id" ASC);
ALTER TABLE "response_subject" ADD CONSTRAINT "PK_response_subject"	PRIMARY KEY ("id");
ALTER TABLE "response_subject" ADD CONSTRAINT "UQ_response_subject" UNIQUE ("response_id","subject_id","journal_id");
CREATE INDEX "IXFK_response_subject_journal" ON "response_subject" ("journal_id" ASC);
CREATE INDEX "IXFK_response_subject_response" ON "response_subject" ("response_id" ASC);
CREATE INDEX "IXFK_response_subject_subject" ON "response_subject" ("subject_id" ASC);
ALTER TABLE "response_question_answer" ADD CONSTRAINT "FK_response_question_answer_response_subject"	FOREIGN KEY ("response_subject_id") REFERENCES "response_subject" ("id") ON DELETE No Action ON UPDATE No Action;

ALTER TABLE "response_subject" ADD CONSTRAINT "FK_response_subject_journal"	FOREIGN KEY ("journal_id") REFERENCES "public"."journal" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "response_subject" ADD CONSTRAINT "FK_response_subject_response"	FOREIGN KEY ("response_id") REFERENCES "response" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "response_subject" ADD CONSTRAINT "FK_response_subject_subject"	FOREIGN KEY ("subject_id") REFERENCES "public"."subject" ("id") ON DELETE No Action ON UPDATE No Action;

alter table poll_theme add column is_repetitive boolean;

update classifier set name_et='Välised isikud' where code='KYSITLUS_SIHT_V';