\c hois

alter table journal_entry_student add column is_remark boolean;
comment on column journal_entry_student.is_remark is 'märkus';

/* Create Tables */

CREATE TABLE "student_remark"
(
	"id" bigserial NOT NULL,
	"student_id" bigint NOT NULL,    -- viide õppurile
	"reason_code" varchar(100)	 NOT NULL,    -- põhjus, viide klassifikaatorile MARKUS
	"remark" text NOT NULL,    -- märkus
	"remark_time" timestamp without time zone NOT NULL,    -- märkuse kp ja kellaaeg, kellaaja puudumisel 00:00
	"inserted" timestamp without time zone NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"version" integer NOT NULL,
	"changed" timestamp without time zone NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "student_remark"	IS 'õppija märkused';
COMMENT ON COLUMN "student_remark"."student_id"	IS 'viide õppurile';
COMMENT ON COLUMN "student_remark"."reason_code"	IS 'põhjus, viide klassifikaatorile MARKUS';
COMMENT ON COLUMN "student_remark"."remark"	IS 'märkus';
COMMENT ON COLUMN "student_remark"."remark_time"	IS 'märkuse kp ja kellaaeg, kellaaja puudumisel 00:00';

ALTER TABLE "student_remark" ADD CONSTRAINT "PK_student_remark"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_student_remark_classifier" ON "student_remark" ("reason_code" ASC);
CREATE INDEX "IXFK_student_remark_student" ON "student_remark" ("student_id" ASC);
ALTER TABLE "student_remark" ADD CONSTRAINT "FK_student_remark_classifier"	FOREIGN KEY ("reason_code") REFERENCES "classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "student_remark" ADD CONSTRAINT "FK_student_remark_student"	FOREIGN KEY ("student_id") REFERENCES "student" ("id") ON DELETE No Action ON UPDATE No Action;

INSERT INTO "classifier" ("code", "value", "name_et", "inserted", "valid", "is_vocational", "is_higher", "version") VALUES ('MARKUS', 'MARKUS', 'Märkuse põhjus', current_timestamp(3), 't', 't', 't', '0');
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('MARKUS_O', 'O', 'Õppija segas', 'MARKUS',current_timestamp(3), 't', 't', 't', '0',null);
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('MARKUS_M', 'M', 'Muu', 'MARKUS',current_timestamp(3), 't', 't', 't', '0',null);


alter table journal_entry_student add column remark_inserted_by varchar(100);
alter table journal_entry_student add column remark_inserted timestamp;

ALTER TABLE practice_journal_entry ALTER COLUMN hours TYPE numeric(4,2);

INSERT INTO classifier (code, value, name_et, main_class_code, inserted, "valid", is_vocational, is_higher, version) VALUES ('TEEMAOIGUS_MARKUS', 'MARKUS', 'Märkus', 'TEEMAOIGUS', now(), true, true, true, 0);
INSERT INTO user_role_default (object_code, permission_code, role_code) VALUES ('TEEMAOIGUS_MARKUS', 'OIGUS_V', 'ROLL_O'), ('TEEMAOIGUS_MARKUS', 'OIGUS_M', 'ROLL_O');

update journal_entry_student jes set is_remark = true, remark_inserted = inserted, remark_inserted_by = inserted_by where jes.grade_code is null and coalesce(jes.add_info, 'x') != 'x';
alter table sais_application add column postcode varchar(20);
alter table sais_admission add column is_full_load boolean;
alter table sais_admission add column is_partial_load boolean;
alter table sais_admission add column is_undefined_load boolean;

update sais_admission set is_undefined_load=case when study_load_code='OPPEKOORMUS_MTA' then true else false end, 
													is_partial_load=case when study_load_code='OPPEKOORMUS_OSA' then true else false end, 
													is_full_load=case when study_load_code='OPPEKOORMUS_TAIS' then true else false end;
													
CREATE INDEX log_table_data_table_id_idx ON log_table_data USING btree (table_id);
CREATE INDEX log_table_data_table_name_idx ON log_table_data USING btree (table_name);		


CREATE TABLE "ws_rr_log"
(
	"id" bigserial NOT NULL,
	"ws_name" varchar(255)	 NOT NULL,    -- päringu nimi
	"idcode" varchar(11)	 NOT NULL,    -- kelle kohta päring esitatakse
	"request" text NOT NULL,    -- päringu sisend
	"response" text NULL,    -- päringu väljund
	"has_errors" boolean NOT NULL,
	"log_txt" text NULL,    -- veateade
	"inserted" timestamp without time zone NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL
)
;

CREATE TABLE "ws_rr_log_school"
(
	"id" bigserial NOT NULL,
	"school_id" bigint NOT NULL,
	"inserted" timestamp without time zone NOT NULL,
	"ws_rr_log_id" bigint NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "ws_rr_log"	IS 'rr päringute logi';
COMMENT ON COLUMN "ws_rr_log"."ws_name"	IS 'päringu nimi';
COMMENT ON COLUMN "ws_rr_log"."idcode"	IS 'kelle kohta päring esitatakse';
COMMENT ON COLUMN "ws_rr_log"."request"	IS 'päringu sisend';
COMMENT ON COLUMN "ws_rr_log"."response"	IS 'päringu väljund';
COMMENT ON COLUMN "ws_rr_log"."log_txt"	IS 'veateade';
COMMENT ON TABLE "ws_rr_log_school"	IS 'seos isiku ja kooli vahel';


/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE "ws_rr_log" ADD CONSTRAINT "PK_ws_rr_log"	PRIMARY KEY ("id");
ALTER TABLE "ws_rr_log_school" ADD CONSTRAINT "PK_ws_rr_log_school"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_ws_rr_log_school_school" ON "ws_rr_log_school" ("school_id" ASC);
CREATE INDEX "IXFK_ws_rr_log_school_ws_rr_log" ON "ws_rr_log_school" ("ws_rr_log_id" ASC);

/* Create Foreign Key Constraints */

ALTER TABLE "ws_rr_log_school" ADD CONSTRAINT "FK_ws_rr_log_school_school"	FOREIGN KEY ("school_id") REFERENCES "school" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "ws_rr_log_school" ADD CONSTRAINT "FK_ws_rr_log_school_ws_rr_log"	FOREIGN KEY ("ws_rr_log_id") REFERENCES "ws_rr_log" ("id") ON DELETE No Action ON UPDATE No Action;

/* Create Tables */

CREATE TABLE "ws_rr_change_log"
(
	"id" bigserial NOT NULL,
	"person_id" bigint NOT NULL,    -- viide isikule
	"old_firstname" varchar(100)	 NOT NULL,
	"old_lastname" varchar(100)	 NULL,
	"new_firstname" varchar(100)	 NOT NULL,
	"new_lastname" varchar(100)	 NULL,
	"old_address_ads_oid" varchar(100)	 NULL,
	"new_address_ads_oid" varchar(100)	 NULL,
	"old_address" varchar(100)	 NULL,
	"new_address" varchar(100)	 NULL,
	"inserted" timestamp without time zone NULL,
	"inserted_by" varchar(100)	 NULL
)
;

CREATE TABLE "ws_rr_change_log_school"
(
	"id" bigserial NOT NULL,
	"ws_rr_change_log_id" bigint NOT NULL,
	"school_id" bigint NOT NULL,    -- viide õppeasutusel
	"inserted" timestamp without time zone NOT NULL,
	"student_id" bigint NOT NULL,    -- viide õppijale
	"inserted_by" varchar(100)
)
;

COMMENT ON TABLE "ws_rr_change_log"	IS 'muutuste logi';
COMMENT ON COLUMN "ws_rr_change_log"."person_id"	IS 'viide isikule';

COMMENT ON TABLE "ws_rr_change_log_school"	IS 'täpne rr muutuste seos isiku ja kooli vahel';
COMMENT ON COLUMN "ws_rr_change_log_school"."school_id"	IS 'viide õppeasutusel';
COMMENT ON COLUMN "ws_rr_change_log_school"."student_id"	IS 'viide õppijale';

ALTER TABLE "ws_rr_change_log" ADD CONSTRAINT "PK_ws_rr_change_log"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_ws_rr_change_log_person" ON "ws_rr_change_log" ("person_id" ASC);
ALTER TABLE "ws_rr_change_log_school" ADD CONSTRAINT "PK_ws_rr_change_log_school"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_ws_rr_change_log_school_school" ON "ws_rr_change_log_school" ("school_id" ASC);
CREATE INDEX "IXFK_ws_rr_change_log_school_student" ON "ws_rr_change_log_school" ("student_id" ASC);
CREATE INDEX "IXFK_ws_rr_change_log_school_ws_rr_change_log" ON "ws_rr_change_log_school" ("ws_rr_change_log_id" ASC);

ALTER TABLE "ws_rr_change_log" ADD CONSTRAINT "FK_ws_rr_change_log_person"	FOREIGN KEY ("person_id") REFERENCES "person" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "ws_rr_change_log_school" ADD CONSTRAINT "FK_ws_rr_change_log_school_school"	FOREIGN KEY ("school_id") REFERENCES "school" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "ws_rr_change_log_school" ADD CONSTRAINT "FK_ws_rr_change_log_school_student"	FOREIGN KEY ("student_id") REFERENCES "student" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "ws_rr_change_log_school" ADD CONSTRAINT "FK_ws_rr_change_log_school_ws_rr_change_log"	FOREIGN KEY ("ws_rr_change_log_id") REFERENCES "ws_rr_change_log" ("id") ON DELETE No Action ON UPDATE No Action;

insert into classifier (code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version,inserted_by)
values('AVALDUS_LIIK_RAKKAVA','RAKKAVA','Rakenduskava muutmine','AVALDUS_LIIK',current_timestamp(3),true,true,false,0,'Automaat');
insert into classifier (code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version,inserted_by)
values('AVALDUS_LIIK_OVERSKAVA','OVERSKAVA','Õppekava versiooni muutmine','AVALDUS_LIIK',current_timestamp(3),true,false,true,0,'Automaat');

INSERT INTO classifier (code, value, name_et, main_class_code, inserted, "valid", is_vocational, is_higher, version) VALUES ('TEEMAOIGUS_RR', 'RR', 'RR (rahvastikuregister) päring', 'TEEMAOIGUS', now(), true, true, true, 0);
INSERT INTO user_role_default (object_code, permission_code, role_code) VALUES ('TEEMAOIGUS_RR', 'OIGUS_V', 'ROLL_A'), ('TEEMAOIGUS_RR', 'OIGUS_M', 'ROLL_A');

alter table application add student_group_id bigint;
create index IXFK_application_student_group on application(student_group_id);
alter table application add constraint FK_application_student_group foreign key(student_group_id) references student_group(id);

