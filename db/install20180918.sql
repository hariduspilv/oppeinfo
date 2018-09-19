\c hois


CREATE TABLE "timetable_event_student_group"
(
	"id" bigserial NOT NULL,
	"student_group_id" bigint NOT NULL,    -- viide rühmale
	"timetable_event_time_id" bigint NOT NULL,    -- viide üksiksündmusele
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "timetable_event_student_group"	IS 'üksiksündmus ja rühm';
COMMENT ON COLUMN "timetable_event_student_group"."student_group_id"	IS 'viide rühmale';
COMMENT ON COLUMN "timetable_event_student_group"."timetable_event_time_id"	IS 'viide üksiksündmusele';


/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE "timetable_event_student_group" ADD CONSTRAINT "PK_timetable_event_student_group1"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_timetable_event_student_group_student_group1" ON "timetable_event_student_group" ("student_group_id" ASC);
CREATE INDEX "IXFK_timetable_event_student_group_timetable_event_time1" ON "timetable_event_student_group" ("timetable_event_time_id" ASC);

/* Create Foreign Key Constraints */

ALTER TABLE "timetable_event_student_group" ADD CONSTRAINT "FK_timetable_event_student_group_student_group"
	FOREIGN KEY ("student_group_id") REFERENCES "public"."student_group" ("id") ON DELETE No Action ON UPDATE No Action;

ALTER TABLE "timetable_event_student_group" ADD CONSTRAINT "FK_timetable_event_student_group_timetable_event_time"
	FOREIGN KEY ("timetable_event_time_id") REFERENCES "timetable_event_time" ("id") ON DELETE No Action ON UPDATE No Action;

	
INSERT INTO classifier (code, value, name_et, main_class_code, inserted, valid, is_vocational, is_higher, version, inserted_by) 
VALUES ('TEATE_LIIK_TUNN_SYNDMUS', 'TUNN_SYNDMUS', 'Tunniplaani on lisatud sündmus', 'TEATE_LIIK', current_timestamp(3), true, true, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et, main_class_code, inserted, valid, is_vocational, is_higher, version, inserted_by) 
VALUES ('TEATE_LIIK_TOET_KATK', 'TOET_KATK', 'Õppetoetuse/stipendiumi katkestamine', 'TEATE_LIIK', current_timestamp(3), true, true, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et, main_class_code, inserted, valid, is_vocational, is_higher, version, inserted_by) 
VALUES ('TEATE_LIIK_OP_MARKUS', 'OP_MARKUS', 'Õppijale on lisatud märkus', 'TEATE_LIIK', current_timestamp(3), true, true, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et, main_class_code, inserted, valid, is_vocational, is_higher, version, inserted_by) 
VALUES ('TEATE_LIIK_OP_AVALDUS', 'OP_AVALDUS', 'Õppijale on koostatud avaldus', 'TEATE_LIIK', current_timestamp(3), true, true, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et, main_class_code, inserted, valid, is_vocational, is_higher, version, inserted_by) 
VALUES ('TEATE_LIIK_OP_KASKKIRI', 'OP_KASKKIRI', 'Õppija käskkiri on koostamisel', 'TEATE_LIIK', current_timestamp(3), true, true, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et, main_class_code, inserted, valid, is_vocational, is_higher, version, inserted_by) 
VALUES ('TEATE_LIIK_OP_ESINDAJA_LOPP', 'OP_ESINDAJA_LOPP', 'Esindaja roll kaob (esindatav saab 18)', 'TEATE_LIIK', current_timestamp(3), true, true, true, 0, 'Automaat');
INSERT INTO classifier (code, value, name_et, main_class_code, inserted, valid, is_vocational, is_higher, version, inserted_by) 
VALUES ('TEATE_LIIK_VOTA', 'VOTA', 'VÕTA taotlus on esitatud', 'TEATE_LIIK', current_timestamp(3), true, true, true, 0, 'Automaat');

INSERT INTO classifier (code, value, value2, name_et, main_class_code, inserted, valid, is_vocational, is_higher, version, inserted_by) 
VALUES ('TEATE_LIIK_SILT_sundmuse_nimetus', 'sundmuse_nimetus', '<sundmuse_nimetus>','Sündmuse nimetus', 'TEATE_LIIK_SILT', current_timestamp(3), true, true, true, 0, 'Automaat');
INSERT INTO classifier (code, value, value2, name_et, main_class_code, inserted, valid, is_vocational, is_higher, version, inserted_by) 
VALUES ('TEATE_LIIK_SILT_sundmuse_aeg', 'sundmuse_aeg', '<sundmuse_aeg>','Sündmuse toimumise aeg', 'TEATE_LIIK_SILT', current_timestamp(3), true, true, true, 0, 'Automaat');
INSERT INTO classifier (code, value, value2, name_et, main_class_code, inserted, valid, is_vocational, is_higher, version, inserted_by) 
VALUES ('TEATE_LIIK_SILT_oppetoetuse_stipendiumi_liik', 'oppetoetuse_stipendiumi_liik', '<oppetoetuse_stipendiumi_liik>','Õppetoetuse või stipendiumi liik', 'TEATE_LIIK_SILT', current_timestamp(3), true, true, true, 0, 'Automaat');
INSERT INTO classifier (code, value, value2, name_et, main_class_code, inserted, valid, is_vocational, is_higher, version, inserted_by) 
VALUES ('TEATE_LIIK_SILT_ruhma_tahis', 'ruhma_tahis', '<ruhma_tahis>','Õpperühma tähis', 'TEATE_LIIK_SILT', current_timestamp(3), true, true, true, 0, 'Automaat');
INSERT INTO classifier (code, value, value2, name_et, main_class_code, inserted, valid, is_vocational, is_higher, version, inserted_by) 
VALUES ('TEATE_LIIK_SILT_oppuri_sunnipaev', 'oppuri_sunnipaev', '<oppuri_sunnipaev>','Õppuri sünnikuupäev', 'TEATE_LIIK_SILT', current_timestamp(3), true, true, true, 0, 'Automaat');	

alter table journal add is_review_ok boolean;
alter table journal add review_date TIMESTAMP;
alter table journal add review_info	varchar(1000);

comment on column journal.is_review_ok is 'kas ülevaatamisel korras';
comment on column journal.review_date is 'ülevaatamise kp';
comment on column journal.review_info is 'märkus ülevaatamise kohta';

insert into classifier (code, value, name_et, main_class_code, inserted, valid, is_vocational, is_higher, version) values
('TEEMAOIGUS_PAEVIKYLE', 'PAEVIKYLE', 'Päeviku ülevaatamine', 'TEEMAOIGUS', now(), true,  true,  true, 0);
 insert into user_role_default (object_code, permission_code, role_code) values 
('TEEMAOIGUS_PAEVIKYLE', 'OIGUS_V', 'ROLL_A');

insert into classifier (code, value, name_et, main_class_code, inserted, valid, is_vocational, is_higher, version) values
('TEEMAOIGUS_OPPETOOLIIK', 'OPPETOOLIIK', 'Õppetöö liikide seadistamine', 'TEEMAOIGUS', now(), true,  true,  true, 0);
 insert into user_role_default (object_code, permission_code, role_code) values 
('TEEMAOIGUS_OPPETOOLIIK', 'OIGUS_V', 'ROLL_A');

/* Create Tables */

CREATE TABLE "school_capacity_type"
(
	"id" bigserial NOT NULL,
	"is_higher" boolean NOT NULL,    -- kas on kõrg seadistus
	"school_id" bigint NOT NULL,
	"capacity_type_code" varchar(100)	 NOT NULL,    -- viide klassifikaatorile MAHT
	"is_timetable" boolean NOT NULL,    -- kas kasutatakse tunniplaanis
	"is_usable" boolean NOT NULL,    -- kas kasutatakse sellist õppetöö liiki, nt kui õppetöö liiki ei kasutatam kuid koormust ei taheta kustutada, siis siia jääb false
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

CREATE TABLE "school_capacity_type_load"
(
	"id" bigserial NOT NULL,
	"school_capacity_type_id" bigint NOT NULL,    -- viide seadistatud õppetöö liigile
	"inserted" timestamp without time zone NOT NULL,
	"study_year_id" bigint NOT NULL,    -- viide õppeaastale
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "school_capacity_type"	IS 'õppeasutuse õppetöö liigid';
COMMENT ON COLUMN "school_capacity_type"."is_higher"	IS 'kas on kõrg seadistus';
COMMENT ON COLUMN "school_capacity_type"."capacity_type_code"	IS 'viide klassifikaatorile MAHT';
COMMENT ON COLUMN "school_capacity_type"."is_timetable"	IS 'kas kasutatakse tunniplaanis';
COMMENT ON COLUMN "school_capacity_type"."is_usable"	IS 'kas kasutatakse sellist õppetöö liiki, nt kui õppetöö liiki ei kasutatam kuid koormust ei taheta kustutada, siis siia jääb false';
COMMENT ON TABLE "school_capacity_type_load"	IS 'õppetöö liikide koormuse koefitsiendid';
COMMENT ON COLUMN "school_capacity_type_load"."school_capacity_type_id"	IS 'viide seadistatud õppetöö liigile';
COMMENT ON COLUMN "school_capacity_type_load"."study_year_id"	IS 'viide õppeaastale';

/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE "school_capacity_type" ADD CONSTRAINT "PK_school_capacity_type"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_school_capacity_type_classifier" ON "school_capacity_type" ("capacity_type_code" ASC);
CREATE INDEX "IXFK_school_capacity_type_school" ON "school_capacity_type" ("school_id" ASC);
ALTER TABLE "school_capacity_type_load" ADD CONSTRAINT "PK_school_capacity_type_load"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_school_capacity_type_load_school_capacity_type" ON "school_capacity_type_load" ("school_capacity_type_id" ASC);
CREATE INDEX "IXFK_school_capacity_type_load_study_year" ON "school_capacity_type_load" ("study_year_id" ASC);
/* Create Foreign Key Constraints */
ALTER TABLE "school_capacity_type" ADD CONSTRAINT "FK_school_capacity_type_classifier"	FOREIGN KEY ("capacity_type_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "school_capacity_type" ADD CONSTRAINT "FK_school_capacity_type_school"	FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "school_capacity_type_load" ADD CONSTRAINT "FK_school_capacity_type_load_school_capacity_type"	FOREIGN KEY ("school_capacity_type_id") REFERENCES "school_capacity_type" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "school_capacity_type_load" ADD CONSTRAINT "FK_school_capacity_type_load_study_year"	FOREIGN KEY ("study_year_id") REFERENCES "public"."study_year" ("id") ON DELETE No Action ON UPDATE No Action;
alter table school_capacity_type_load add column load_percentage smallint not null;

