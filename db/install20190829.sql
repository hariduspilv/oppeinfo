\c hois


CREATE TABLE "dormitory"
(
	"id" bigserial NOT NULL,
	"student_id" bigint NOT NULL,    -- viide õppurile
	"room_id" bigint NOT NULL,    -- viide ruumile
	"valid_from" date NOT NULL,    -- kehtib alates
	"valid_thru" date NOT NULL,    -- kehtib kuni
	"add_info" varchar(4000)	 NULL,    -- kommentaar
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "dormitory"	IS 'õpilaskodu';
COMMENT ON COLUMN "dormitory"."student_id"	IS 'viide õppurile';
COMMENT ON COLUMN "dormitory"."room_id"	IS 'viide ruumile';
COMMENT ON COLUMN "dormitory"."valid_from"	IS 'kehtib alates';
COMMENT ON COLUMN "dormitory"."valid_thru"	IS 'kehtib kuni';
COMMENT ON COLUMN "dormitory"."add_info"	IS 'kommentaar';

ALTER TABLE "dormitory" ADD CONSTRAINT "PK_dormitory"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_dormitory_room" ON "dormitory" ("room_id" ASC);
CREATE INDEX "IXFK_dormitory_student" ON "dormitory" ("student_id" ASC);
ALTER TABLE "dormitory" ADD CONSTRAINT "FK_dormitory_room"	FOREIGN KEY ("room_id") REFERENCES "public"."room" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "dormitory" ADD CONSTRAINT "FK_dormitory_student"	FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE No Action ON UPDATE No Action;

alter table building add column is_dormitory boolean;
comment on column building.is_dormitory is 'kas on õpilaskodu';

INSERT INTO "public"."classifier" ("code", "value", "value2", "name_et", "name_en", "name_ru", "parent_class_code", "main_class_code", "inserted", "changed", "valid", "description", "valid_from", "valid_thru", "extraval1", "extraval2", "ehis_value", "is_vocational", "is_higher", "version", "inserted_by", "changed_by") 
VALUES ('TEATE_LIIK_KYSI_MEELDETULETUS', 'KYSI_MEELDETULETUS', NULL, 'Küsitluse meeldetuletus', NULL, NULL, NULL, 'TEATE_LIIK', now(), NULL, 't', NULL, NULL, NULL, NULL, NULL, NULL, 't', 'f', '0', NULL, NULL);

INSERT INTO "public"."classifier" ("code", "value", "value2", "name_et", "name_en", "name_ru", "parent_class_code", "main_class_code", "inserted", "changed", "valid", "description", "valid_from", "valid_thru", "extraval1", "extraval2", "ehis_value", "is_vocational", "is_higher", "version", "inserted_by", "changed_by") 
VALUES ('TEATE_LIIK_SILT_kysitlus_nimi', 'kysitlus_nimi', '<kysitlus_nimi>', 'Küsitluse nimi', NULL, NULL, NULL, 'TEATE_LIIK_SILT', now(), NULL, 't', NULL, NULL, NULL, NULL, NULL, NULL, 't', 't', '0', 'Automaat', NULL);


CREATE TABLE "poll_teacher_comment"
(
	"id" bigserial NOT NULL,
	"poll_id" bigint NOT NULL,
	"teacher_id" bigint NOT NULL,
	"add_info" text NOT NULL,    -- kommentaar
	"subject_id" bigint NOT NULL,
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "poll_teacher_comment"	IS 'õpetaja kommentaarid';
COMMENT ON COLUMN "poll_teacher_comment"."add_info"	IS 'kommentaar';
ALTER TABLE "poll_teacher_comment" ADD CONSTRAINT "PK_poll_teacher_comment"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_poll_teacher_comment_poll" ON "poll_teacher_comment" ("poll_id" ASC);
CREATE INDEX "IXFK_poll_teacher_comment_subject" ON "poll_teacher_comment" ("subject_id" ASC);
CREATE INDEX "IXFK_poll_teacher_comment_teacher" ON "poll_teacher_comment" ("teacher_id" ASC);

/* Create Foreign Key Constraints */

ALTER TABLE "poll_teacher_comment" ADD CONSTRAINT "FK_poll_teacher_comment_poll"	FOREIGN KEY ("poll_id") REFERENCES "poll" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "poll_teacher_comment" ADD CONSTRAINT "FK_poll_teacher_comment_subject"	FOREIGN KEY ("subject_id") REFERENCES "public"."subject" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "poll_teacher_comment" ADD CONSTRAINT "FK_poll_teacher_comment_teacher"	FOREIGN KEY ("teacher_id") REFERENCES "public"."teacher" ("id") ON DELETE No Action ON UPDATE No Action;

alter table subject_program_study_content alter column week_nr set data type varchar(50);

CREATE TABLE "application_omodule_theme"
(
	"id" bigserial NOT NULL,
	"application_id" bigint NOT NULL,
	"curriculum_version_omodule_theme_id" bigint NOT NULL,    -- viide vanale või uuele teemale
	"is_old" boolean NOT NULL,    -- true - tegemist on nö eelmise versiooni sooritusega, mida pannakse vastavusse, sellisel juhul peab olema täidetud ka journal_id
	"curriculum_module_id" bigint NOT NULL,    -- õppekava moodul
	"inserted" timestamp without time zone NOT NULL,
	"journal_id" bigint NULL,    -- viide päevikule
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "application_omodule_theme"
	IS 'rakenduskava vahetamisega seotud moodulite teemad'
;

COMMENT ON COLUMN "application_omodule_theme"."curriculum_version_omodule_theme_id"
	IS 'viide vanale või uuele teemale'
;

COMMENT ON COLUMN "application_omodule_theme"."is_old"
	IS 'true - tegemist on nö eelmise versiooni sooritusega, mida pannakse vastavusse, sellisel juhul peab olema täidetud ka journal_id'
;

COMMENT ON COLUMN "application_omodule_theme"."curriculum_module_id"
	IS 'õppekava moodul'
;

COMMENT ON COLUMN "application_omodule_theme"."journal_id"
	IS 'viide päevikule'
;

/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE "application_omodule_theme" ADD CONSTRAINT "PK_application_omodule_theme"
	PRIMARY KEY ("id")
;

CREATE INDEX "IXFK_application_omodule_theme_application" ON "application_omodule_theme" ("application_id" ASC)
;

CREATE INDEX "IXFK_application_omodule_theme_curriculum_module" ON "application_omodule_theme" ("curriculum_module_id" ASC)
;

CREATE INDEX "IXFK_application_omodule_theme_curriculum_version_omodule_theme" ON "application_omodule_theme" ("curriculum_version_omodule_theme_id" ASC)
;

CREATE INDEX "IXFK_application_omodule_theme_journal" ON "application_omodule_theme" ("journal_id" ASC)
;

/* Create Foreign Key Constraints */

ALTER TABLE "application_omodule_theme" ADD CONSTRAINT "FK_application_omodule_theme_application"
	FOREIGN KEY ("application_id") REFERENCES "public"."application" ("id") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "application_omodule_theme" ADD CONSTRAINT "FK_application_omodule_theme_curriculum_module"
	FOREIGN KEY ("curriculum_module_id") REFERENCES "public"."curriculum_module" ("id") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "application_omodule_theme" ADD CONSTRAINT "FK_application_omodule_theme_curriculum_version_omodule_theme"
	FOREIGN KEY ("curriculum_version_omodule_theme_id") REFERENCES "public"."curriculum_version_omodule_theme" ("id") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "application_omodule_theme" ADD CONSTRAINT "FK_application_omodule_theme_journal"
	FOREIGN KEY ("journal_id") REFERENCES "journal" ("id") ON DELETE No Action ON UPDATE No Action
;

alter table subject_program add column add_info text;
alter table school add column is_without_ekis boolean;
comment on column school.is_without_ekis is 'kas kool on ilma EKISeta';

alter table application_omodule_theme add column practice_journal_id bigint NULL;
CREATE INDEX "IXFK_application_omodule_theme_practice_journal" ON "application_omodule_theme" ("practice_journal_id" ASC);
ALTER TABLE "application_omodule_theme" ADD CONSTRAINT "FK_application_omodule_theme_practice_journal"	FOREIGN KEY ("practice_journal_id") REFERENCES "practice_journal" ("id") ON DELETE No Action ON UPDATE No Action
;

alter table general_message alter column school_id drop not null;

alter table poll_teacher_comment add journal_id bigint null;
CREATE INDEX "IXFK_poll_teacher_comment_journal" ON "poll_teacher_comment" ("journal_id" ASC);
ALTER TABLE "poll_teacher_comment" ADD CONSTRAINT "FK_poll_teacher_comment_journal"	FOREIGN KEY ("journal_id") REFERENCES "public"."journal" ("id") ON DELETE No Action ON UPDATE No Action;

alter table poll_teacher_comment alter column subject_id drop not null;
create trigger application_omodule_theme_audit after insert or delete or update on application_omodule_theme for each row execute procedure hois_audit();
create trigger dormitory_audit after insert or delete or update on dormitory for each row execute procedure hois_audit();
create trigger poll_journal_audit after insert or delete or update on poll_journal for each row execute procedure hois_audit();
create trigger poll_teacher_comment_audit after insert or delete or update on poll_teacher_comment for each row execute procedure hois_audit();
create trigger poll_teacher_occupation_audit after insert or delete or update on poll_teacher_occupation for each row execute procedure hois_audit();
create trigger response_subject_audit after insert or delete or update on response_subject for each row execute procedure hois_audit();

create trigger student_support_service_audit after insert or delete or update on student_support_service for each row execute procedure hois_audit();

insert into classifier (code, value, name_et, main_class_code, inserted, valid, is_vocational, is_higher, version)
values ('TEEMAOIGUS_OPILASKODU', 'OPILASKODU', 'Õpilaskodud', 'TEEMAOIGUS', now(), true, true, true, 0);
 
insert into user_role_default (object_code, permission_code, role_code) values 
('TEEMAOIGUS_OPILASKODU', 'OIGUS_V', 'ROLL_A'),
('TEEMAOIGUS_OPILASKODU', 'OIGUS_M', 'ROLL_A') ;


