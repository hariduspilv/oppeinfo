\c hois;

alter table journal add moodle_course_id bigint;
alter table subject_study_period add moodle_course_id bigint;
comment on column journal.moodle_course_id is 'MOODLE kursuse id';
comment on column subject_study_period.moodle_course_id is 'MOODLE kursuse id';
alter table student add previous_school_name varchar(255);
alter table student add previous_school_end_date date;
comment on column student.previous_school_name is 'eelmise lõpetatud kooli nimi';
comment on column student.previous_school_end_date is 'previous_school_end_date';
alter table journal_entry add column moodle_grade_item_id bigint;
comment on column journal_entry.moodle_grade_item_id is 'viide Moodle hindamisskeemile';
alter table midterm_task add column moodle_grade_item_id bigint;
comment on column midterm_task.moodle_grade_item_id is 'viide Moodle hindamisskeemile';
alter table journal_student add column is_moodle_registered boolean;
comment on column journal_student.is_moodle_registered is 'kas Moodle registreering olemas';
alter table declaration_subject add column is_moodle_registered boolean;
comment on column declaration_subject.is_moodle_registered is 'kas Moodle registreering olemas';

insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('SOORITUS','SOORITUS','Soorituse liik',null,now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('SOORITUS_P','P','Põhisooritus','SOORITUS',now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('SOORITUS_K','K','Kordussooritus','SOORITUS',now(),true,true,true,0);

CREATE TABLE "subject_study_period_exam"
(
	"id" bigserial NOT NULL,
	"subject_study_period_id" bigint NOT NULL,    -- viide aine-õppejõu paarile
	"timetable_event_id" bigint NOT NULL,    -- viide sündmusele
	"deadline" timestamp without time zone NULL,    -- registreerimise tähtaeg
	"type_code" varchar(100)	 NOT NULL,    -- viide klassifikaatorile SOORITUS
	"places" smallint NULL,    -- kohtade arv
	"add_info" varchar(4000)	 NULL,    -- lisainfo
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

CREATE TABLE "subject_study_period_exam_student"
(
	"id" bigserial NOT NULL,
	"subject_study_period_exam_id" bigint NOT NULL,    -- viide eksamile
	"declaration_subject_id" bigint NOT NULL,    -- viide deklaratsioonile
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "subject_study_period_exam"	IS 'eksamiajad';
COMMENT ON COLUMN "subject_study_period_exam"."subject_study_period_id"	IS 'viide aine-õppejõu paarile';
COMMENT ON COLUMN "subject_study_period_exam"."timetable_event_id"	IS 'viide sündmusele';
COMMENT ON COLUMN "subject_study_period_exam"."deadline"	IS 'registreerimise tähtaeg';
COMMENT ON COLUMN "subject_study_period_exam"."type_code"	IS 'viide klassifikaatorile SOORITUS';
COMMENT ON COLUMN "subject_study_period_exam"."places"	IS 'kohtade arv';
COMMENT ON COLUMN "subject_study_period_exam"."add_info"	IS 'lisainfo';

COMMENT ON TABLE "subject_study_period_exam_student"	IS 'õppurite registreeringud eksamile';
COMMENT ON COLUMN "subject_study_period_exam_student"."subject_study_period_exam_id"	IS 'viide eksamile';
COMMENT ON COLUMN "subject_study_period_exam_student"."declaration_subject_id"	IS 'viide deklaratsioonile';
ALTER TABLE "subject_study_period_exam" ADD CONSTRAINT "PK_subject_study_period_exam"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_subject_study_period_exam_classifier" ON "subject_study_period_exam" ("type_code" ASC);
CREATE INDEX "IXFK_subject_study_period_exam_subject_study_period" ON "subject_study_period_exam" ("subject_study_period_id" ASC);
CREATE INDEX "IXFK_subject_study_period_exam_timetable_event" ON "subject_study_period_exam" ("timetable_event_id" ASC);
ALTER TABLE "subject_study_period_exam_student" ADD CONSTRAINT "PK_subject_study_period_exam_student"	PRIMARY KEY ("id");
ALTER TABLE "subject_study_period_exam_student" ADD CONSTRAINT "UQ_subject_study_period_exam_student" UNIQUE ("subject_study_period_exam_id","declaration_subject_id");
CREATE INDEX "IXFK_subject_study_period_exam_student_declaration_subject" ON "subject_study_period_exam_student" ("declaration_subject_id" ASC);
CREATE INDEX "IXFK_subject_study_period_exam_student_subject_study_period_exam" ON "subject_study_period_exam_student" ("subject_study_period_exam_id" ASC);

ALTER TABLE "subject_study_period_exam" ADD CONSTRAINT "FK_subject_study_period_exam_classifier"	FOREIGN KEY ("type_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "subject_study_period_exam" ADD CONSTRAINT "FK_subject_study_period_exam_subject_study_period"	FOREIGN KEY ("subject_study_period_id") REFERENCES "public"."subject_study_period" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "subject_study_period_exam" ADD CONSTRAINT "FK_subject_study_period_exam_timetable_event"	FOREIGN KEY ("timetable_event_id") REFERENCES "timetable_event" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "subject_study_period_exam_student" ADD CONSTRAINT "FK_subject_study_period_exam_student_declaration_subject"	FOREIGN KEY ("declaration_subject_id") REFERENCES "declaration_subject" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "subject_study_period_exam_student" ADD CONSTRAINT "FK_subject_study_period_exam_student_subject_study_period_exam"	FOREIGN KEY ("subject_study_period_exam_id") REFERENCES "subject_study_period_exam" ("id") ON DELETE No Action ON UPDATE No Action;

alter table protocol_student add column subject_study_period_exam_student_id bigint;
comment on column protocol_student.subject_study_period_exam_student_id is 'viide eksamile';
ALTER TABLE "protocol_student" ADD CONSTRAINT "FK_protocol_student_subject_study_period_exam_student"	FOREIGN KEY ("subject_study_period_exam_student_id") REFERENCES "public"."subject_study_period_exam_student" ("id") ON DELETE No Action ON UPDATE No Action;
CREATE INDEX "IXFK_protocol_student_subject_study_period_exam_student" ON "protocol_student" ("subject_study_period_exam_student_id" ASC);

insert into user_role_default(object_code, permission_code, role_code) values ('TEEMAOIGUS_LOPMOODULPROTOKOLL', 'OIGUS_V', 'ROLL_A');
alter table protocol add final_date date;
comment on column protocol.final_date is 'lõpueksami kuupäev';

/* Create Tables */

CREATE TABLE "ws_moodle_log"
(
	"id" bigserial NOT NULL ,
	"school_id" bigint NOT NULL,
	"ws_name" varchar(255)	 NOT NULL,    -- teenuse nimi
	"request" text NULL,    -- sisend
	"response" text NULL,    -- väljund
	"has_errors" boolean NOT NULL,    -- kas on vigum vaikimisi false
	"log_txt" text NULL,    -- logi
	"inserted" timestamp without time zone NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"version" integer NOT NULL,
	"changed" timestamp without time zone NULL,
	"changed_by" varchar(100)	 NULL,
	"ip_address" varchar(50)	 NULL,    -- päringu käivitaja IP
	"journal_id" bigint NULL,    -- viide päevikule
	"subject_study_period_id" bigint NULL,    -- viide aine-õppejõu paarile
	"role_code" varchar(100)	 NOT NULL    -- käivitaja roll, viide klassifikaatorile ROLL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "ws_moodle_log"	IS 'Moodle andmevahetuse logi tabel';
COMMENT ON COLUMN "ws_moodle_log"."ws_name"	IS 'teenuse nimi';
COMMENT ON COLUMN "ws_moodle_log"."request"	IS 'sisend';
COMMENT ON COLUMN "ws_moodle_log"."response"	IS 'väljund';
COMMENT ON COLUMN "ws_moodle_log"."has_errors"	IS 'kas on vigum vaikimisi false';
COMMENT ON COLUMN "ws_moodle_log"."log_txt"	IS 'logi';
COMMENT ON COLUMN "ws_moodle_log"."ip_address"	IS 'päringu käivitaja IP';
COMMENT ON COLUMN "ws_moodle_log"."journal_id"	IS 'viide päevikule';
COMMENT ON COLUMN "ws_moodle_log"."subject_study_period_id"	IS 'viide aine-õppejõu paarile';
COMMENT ON COLUMN "ws_moodle_log"."role_code"	IS 'käivitaja roll, viide klassifikaatorile ROLL';


ALTER TABLE "ws_moodle_log" ADD CONSTRAINT "PK_ws_moodle_log"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_ws_moodle_log_classifier" ON "ws_moodle_log" ("role_code" ASC);
CREATE INDEX "IXFK_ws_moodle_log_school" ON "ws_moodle_log" ("school_id" ASC);


ALTER TABLE "ws_moodle_log" ADD CONSTRAINT "FK_ws_moodle_log_classifier"	FOREIGN KEY ("role_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "ws_moodle_log" ADD CONSTRAINT "FK_ws_moodle_log_school"	FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE No Action ON UPDATE No Action;

alter table student_occupation_certificate ALTER COLUMN valid_thru DROP NOT NULL;

update student set ois_file_id=null, changed_by=coalesce(changed_by,'DATA_TRANSFER_PROCESS'),changed=current_timestamp(3) where ois_file_id=1;

