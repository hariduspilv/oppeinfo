\c hois;

alter table building add column address_ads varchar(50);
alter table school add column address_ads varchar(50);


CREATE TABLE "study_material"
(
	"id" bigserial NOT NULL,
	"school_id" bigint NOT NULL,    -- viide õppeasutusele
	"teacher_id" bigint NOT NULL,    -- viide õpetajale
	"name_et" varchar(255)	 NOT NULL,    -- nimetus
	"type_code" varchar(100)	 NOT NULL,    -- viide klassifikaatorile OPPEMATERJAL
	"description_et" varchar(4000)	 NULL,    -- kirjeldus
	"is_public" boolean NOT NULL,    -- kas on avalik
	"is_visible_to_students" boolean NOT NULL,    -- kas nähtav õppuritele
	"url" varchar(4000)	 NULL,    -- viide
	"ois_file_id" bigint NULL,    -- viide failile
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

CREATE TABLE "study_material_connect"
(
	"id" bigserial NOT NULL,
	"study_material_id" bigint NOT NULL,    -- viide õppematerjalile
	"journal_id" bigint NULL,    -- viide päevikule
	"subject_study_period_id" bigint NULL,    -- viide aine-õppejõu paarile
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "study_material"	IS 'õppematerjal';
COMMENT ON COLUMN "study_material"."school_id"	IS 'viide õppeasutusele';
COMMENT ON COLUMN "study_material"."teacher_id"	IS 'viide õpetajale';
COMMENT ON COLUMN "study_material"."name_et"	IS 'nimetus';
COMMENT ON COLUMN "study_material"."type_code"	IS 'viide klassifikaatorile OPPEMATERJAL';
COMMENT ON COLUMN "study_material"."description_et"	IS 'kirjeldus';
COMMENT ON COLUMN "study_material"."is_public"	IS 'kas on avalik';
COMMENT ON COLUMN "study_material"."is_visible_to_students"	IS 'kas nähtav õppuritele';
COMMENT ON COLUMN "study_material"."url"	IS 'viide';
COMMENT ON COLUMN "study_material"."ois_file_id"	IS 'viide failile';

COMMENT ON TABLE "study_material_connect"	IS 'õppematerjalide seosed';
COMMENT ON COLUMN "study_material_connect"."study_material_id"	IS 'viide õppematerjalile';
COMMENT ON COLUMN "study_material_connect"."journal_id"	IS 'viide päevikule';
COMMENT ON COLUMN "study_material_connect"."subject_study_period_id"	IS 'viide aine-õppejõu paarile';

/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE "study_material" ADD CONSTRAINT "PK_study_material"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_study_material_classifier" ON "study_material" ("type_code" ASC);
CREATE INDEX "IXFK_study_material_ois_file" ON "study_material" ("ois_file_id" ASC);
CREATE INDEX "IXFK_study_material_school" ON "study_material" ("school_id" ASC);
CREATE INDEX "IXFK_study_material_teacher" ON "study_material" ("teacher_id" ASC);
ALTER TABLE "study_material_connect" ADD CONSTRAINT "PK_study_material_connection"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_study_material_connect_journal" ON "study_material_connect" ("journal_id" ASC);
CREATE INDEX "IXFK_study_material_connect_study_material" ON "study_material_connect" ("study_material_id" ASC);
CREATE INDEX "IXFK_study_material_connect_subject_study_period" ON "study_material_connect" ("subject_study_period_id" ASC);

/* Create Foreign Key Constraints */

ALTER TABLE "study_material" ADD CONSTRAINT "FK_study_material_classifier"	FOREIGN KEY ("type_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "study_material" ADD CONSTRAINT "FK_study_material_ois_file"	FOREIGN KEY ("ois_file_id") REFERENCES "public"."ois_file" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "study_material" ADD CONSTRAINT "FK_study_material_school"	FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "study_material" ADD CONSTRAINT "FK_study_material_teacher"	FOREIGN KEY ("teacher_id") REFERENCES "public"."teacher" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "study_material_connect" ADD CONSTRAINT "FK_study_material_connect_journal"	FOREIGN KEY ("journal_id") REFERENCES "public"."journal" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "study_material_connect" ADD CONSTRAINT "FK_study_material_connect_study_material"	FOREIGN KEY ("study_material_id") REFERENCES "study_material" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "study_material_connect" ADD CONSTRAINT "FK_study_material_connect_subject_study_period"	FOREIGN KEY ("subject_study_period_id") REFERENCES "public"."subject_study_period" ("id") ON DELETE No Action ON UPDATE No Action;

insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('OPPEMATERJAL','OPPEMATERJAL','Õppematerjali tüüp',null,now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('OPPEMATERJAL_F','F','Fail','OPPEMATERJAL',now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('OPPEMATERJAL_L','L','Link','OPPEMATERJAL',now(),true,true,true,0);

alter table study_material add column is_vocational boolean not null;

insert into user_role_default (object_code,permission_code,role_code)values('TEEMAOIGUS_KASKKIRI','OIGUS_V','ROLL_O');
insert into user_role_default (object_code,permission_code,role_code)values('TEEMAOIGUS_AVALDUS','OIGUS_V','ROLL_O');

insert into user_rights(user_id,permission_code,object_code,inserted,version,inserted_by)
select uu.id, 'OIGUS_V','TEEMAOIGUS_KASKKIRI',now(),0,'Automaat'
from user_ uu
where uu.role_code='ROLL_O';

insert into user_rights(user_id,permission_code,object_code,inserted,version,inserted_by)
select uu.id, 'OIGUS_V','TEEMAOIGUS_AVALDUS',now(),0,'Automaat'
from user_ uu
where uu.role_code='ROLL_O';