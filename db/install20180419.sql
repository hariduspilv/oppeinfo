\c hois

/* Drop Tables */
DROP TABLE IF EXISTS "student_group_year_transfer_log" CASCADE;
DROP TABLE IF EXISTS "student_group_year_transfer" CASCADE;

DROP SEQUENCE IF EXISTS "student_group_year_transfer_id_seq" cascade;
DROP SEQUENCE IF EXISTS student_group_year_transfer_log_id_seq cascade;


/* Create Tables */
CREATE TABLE "student_group_year_transfer"
(
	"id" bigserial NOT NULL,
	"student_group_id" bigint NOT NULL,    -- viide õpperühmale
	"inserted" timestamp without time zone NOT NULL,
	"study_year_id" bigint NOT NULL,    -- viide uuele õppeaastale
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL,
	"old_code" varchar(50)	 NOT NULL,    -- vana õpperühma kood
	"new_code" varchar(50)	 NOT NULL,    -- uus õpperühma kood
	"curriculum_id" bigint NOT NULL,    -- viide õppekavale
	"curriculum_version_id" bigint NULL,    -- viide õppekavaversioonile
	"old_course" smallint NOT NULL,    -- vana kursuse nr
	"new_course" smallint NOT NULL    -- uus kursuse nr
)
;

CREATE TABLE "student_group_year_transfer_log"
(
	"id" bigserial NOT NULL,
	"student_group_year_transfer_id" bigint NOT NULL,
	"is_matching" boolean NOT NULL,
	"student_id" bigint NOT NULL,
	"mismatch_code" varchar(100)	 NULL,    -- mittesobivuse põhjus, viide klassifikasatorile 'OPPERYHM_EISOBI'
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */
COMMENT ON TABLE "student_group_year_transfer"	IS 'õpperühmade keeramine';
COMMENT ON COLUMN "student_group_year_transfer"."student_group_id"	IS 'viide õpperühmale';
COMMENT ON COLUMN "student_group_year_transfer"."study_year_id"	IS 'viide uuele õppeaastale';
COMMENT ON COLUMN "student_group_year_transfer"."old_code"	IS 'vana õpperühma kood';
COMMENT ON COLUMN "student_group_year_transfer"."new_code"	IS 'uus õpperühma kood';
COMMENT ON COLUMN "student_group_year_transfer"."curriculum_id"	IS 'viide õppekavale';
COMMENT ON COLUMN "student_group_year_transfer"."curriculum_version_id"	IS 'viide õppekavaversioonile';
COMMENT ON COLUMN "student_group_year_transfer"."old_course"	IS 'vana kursuse nr';
COMMENT ON COLUMN "student_group_year_transfer"."new_course"	IS 'uus kursuse nr';
COMMENT ON TABLE "student_group_year_transfer_log"	IS 'õpperühma keeramise detailne logi';
COMMENT ON COLUMN "student_group_year_transfer_log"."mismatch_code"	IS 'mittesobivuse põhjus, viide klassifikasatorile ''OPPERYHM_EISOBI''';
/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE "student_group_year_transfer" ADD CONSTRAINT "PK_student_group_year_transfer" PRIMARY KEY ("id");
CREATE INDEX "IXFK_student_group_year_transfer_curriculum" ON "student_group_year_transfer" ("curriculum_id" ASC);
CREATE INDEX "IXFK_student_group_year_transfer_curriculum_version" ON "student_group_year_transfer" ("curriculum_version_id" ASC);
CREATE INDEX "IXFK_student_group_year_transfer_student_group" ON "student_group_year_transfer" ("student_group_id" ASC);
CREATE INDEX "IXFK_student_group_year_transfer_study_year" ON "student_group_year_transfer" ("study_year_id" ASC);
ALTER TABLE "student_group_year_transfer_log" ADD CONSTRAINT "PK_student_group_year_transfer_log"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_student_group_year_transfer_log_classifier" ON "student_group_year_transfer_log" ("mismatch_code" ASC);
CREATE INDEX "IXFK_student_group_year_transfer_log_student" ON "student_group_year_transfer_log" ("student_id" ASC);
CREATE INDEX "IXFK_student_group_year_transfer_log_student_group_year_transfer" ON "student_group_year_transfer_log" ("student_group_year_transfer_id" ASC);

/* Create Foreign Key Constraints */
ALTER TABLE "student_group_year_transfer" ADD CONSTRAINT "FK_student_group_year_transfer_curriculum"	FOREIGN KEY ("curriculum_id") REFERENCES "public"."curriculum" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "student_group_year_transfer" ADD CONSTRAINT "FK_student_group_year_transfer_curriculum_version"	FOREIGN KEY ("curriculum_version_id") REFERENCES "public"."curriculum_version" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "student_group_year_transfer" ADD CONSTRAINT "FK_student_group_year_transfer_student_group"	FOREIGN KEY ("student_group_id") REFERENCES "public"."student_group" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "student_group_year_transfer" ADD CONSTRAINT "FK_student_group_year_transfer_study_year"	FOREIGN KEY ("study_year_id") REFERENCES "public"."study_year" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "student_group_year_transfer_log" ADD CONSTRAINT "FK_student_group_year_transfer_log_classifier"	FOREIGN KEY ("mismatch_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "student_group_year_transfer_log" ADD CONSTRAINT "FK_student_group_year_transfer_log_student"	FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "student_group_year_transfer_log" ADD CONSTRAINT "FK_student_group_year_transfer_log_student_group_year_transfer"	FOREIGN KEY ("student_group_year_transfer_id") REFERENCES "student_group_year_transfer" ("id") ON DELETE No Action ON UPDATE No Action;

insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version,inserted_by)
values('OPPERYHM_EISOBI','OPPERYHM_EISOBI','Õpperühma keeramisel õppuri mittesobivuse põhjus',null,now(),true,true,true,0,'Automaat'),
			('OPPERYHM_EISOBI_A','A','Akadeemiline puhkus','OPPERYHM_EISOBI',now(),true,true,true,0,'Automaat'),
			('OPPERYHM_EISOBI_V','V','Välisõppes viibimine','OPPERYHM_EISOBI',now(),true,true,true,0,'Automaat'),
			('OPPERYHM_EISOBI_E','E','Eksmatrikuleerimine','OPPERYHM_EISOBI',now(),true,true,true,0,'Automaat'),
			('OPPERYHM_EISOBI_O','O','Õppekava vahetamine','OPPERYHM_EISOBI',now(),true,true,true,0,'Automaat');

alter table student_group_year_transfer add column is_transfered boolean not null;
comment on column student_group_year_transfer.is_transfered is 'kas on juba keeratud';