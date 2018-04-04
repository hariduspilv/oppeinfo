\c tahvel_10;

/* Create Tables */

CREATE TABLE "protocol_student_occupation"
(
	"id" bigserial NOT NULL ,
	"protocol_student_id" bigint NOT NULL,    -- viide õppurile
	"student_occupation_certificate_id" bigint NULL,    -- viide kutsetunnistusele
	"inserted" timestamp without time zone NOT NULL,
	"occupation_code" varchar(100)	 NOT NULL,    -- kutse, viide klassifikaatorile KUTSE
	"part_occupation_code" varchar(100)	 NULL,    -- osakutse, viide klassifikaatorile OSAKUTSE
	"inserted_by" varchar(100)	 NOT NULL,
	"version" integer NOT NULL,
	"changed" timestamp without time zone NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "protocol_student_occupation"	IS 'protokolliga seotud kutsed/osakutsed';
COMMENT ON COLUMN "protocol_student_occupation"."protocol_student_id"	IS 'viide õppurile';
COMMENT ON COLUMN "protocol_student_occupation"."student_occupation_certificate_id"	IS 'viide kutsetunnistusele';
COMMENT ON COLUMN "protocol_student_occupation"."occupation_code"	IS 'kutse, viide klassifikaatorile KUTSE';
COMMENT ON COLUMN "protocol_student_occupation"."part_occupation_code"	IS 'osakutse, viide klassifikaatorile OSAKUTSE';

/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE "protocol_student_occupation" ADD CONSTRAINT "PK_protocol_student_occupation"	PRIMARY KEY ("id");

CREATE INDEX "IXFK_protocol_student_occupation_classifier" ON "protocol_student_occupation" ("occupation_code" ASC);
CREATE INDEX "IXFK_protocol_student_occupation_classifier_02" ON "protocol_student_occupation" ("part_occupation_code" ASC);
CREATE INDEX "IXFK_protocol_student_occupation_protocol_student" ON "protocol_student_occupation" ("protocol_student_id" ASC);
CREATE INDEX "IXFK_protocol_student_occupation_student_occupation_certificate" ON "protocol_student_occupation" ("student_occupation_certificate_id" ASC);

/* Create Foreign Key Constraints */

ALTER TABLE "protocol_student_occupation" ADD CONSTRAINT "FK_protocol_student_occupation_classifier"	FOREIGN KEY ("occupation_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "protocol_student_occupation" ADD CONSTRAINT "FK_protocol_student_occupation_classifier_02"	FOREIGN KEY ("part_occupation_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "protocol_student_occupation" ADD CONSTRAINT "FK_protocol_student_occupation_protocol_student"	FOREIGN KEY ("protocol_student_id") REFERENCES "protocol_student" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "protocol_student_occupation" ADD CONSTRAINT "FK_protocol_student_occupation_student_occupation_certificate"	FOREIGN KEY ("student_occupation_certificate_id") REFERENCES "student_occupation_certificate" ("id") ON DELETE No Action ON UPDATE No Action;

INSERT INTO "public"."classifier" ("code", "value", "value2", "name_et", "name_en", "name_ru", "parent_class_code", "main_class_code", "inserted", "changed", "valid", "description", "valid_from", "valid_thru", "extraval1", "extraval2", "ehis_value", "is_vocational", "is_higher", "version", "inserted_by", "changed_by") 
select 'OPPEASTE_433', '433', NULL, '433 Kolmanda taseme kutseõpe', NULL, NULL, NULL, 'OPPEASTE', '2016-10-31 14:08:14.044233', '2017-05-03 08:07:48.185', 'f', NULL, NULL, NULL, NULL, NULL, '433', 't', 'f', '1', NULL, '48403150000'
WHERE  NOT EXISTS (   SELECT code FROM classifier WHERE code='OPPEASTE_433' );
INSERT INTO "public"."classifier" ("code", "value", "value2", "name_et", "name_en", "name_ru", "parent_class_code", "main_class_code", "inserted", "changed", "valid", "description", "valid_from", "valid_thru", "extraval1", "extraval2", "ehis_value", "is_vocational", "is_higher", "version", "inserted_by", "changed_by") 
select 'SISSEKANNE_P', 'P', NULL, 'Praktiline töö', NULL, NULL, NULL, 'SISSEKANNE', '2016-10-31 14:08:14.044233', '2017-05-03 08:07:48.185', 't', NULL, NULL, NULL, NULL, NULL, null, 't', 'f', '1', NULL, '48403150000'
WHERE  NOT EXISTS (   SELECT code FROM classifier WHERE code='SISSEKANNE_P' );
INSERT INTO "public"."classifier" ("code", "value", "value2", "name_et", "name_en", "name_ru", "parent_class_code", "main_class_code", "inserted", "changed", "valid", "description", "valid_from", "valid_thru", "extraval1", "extraval2", "ehis_value", "is_vocational", "is_higher", "version", "inserted_by", "changed_by") 
select 'SISSEKANNE_R', 'R', NULL, 'Perioodi hinne', NULL, NULL, NULL, 'SISSEKANNE', '2016-10-31 14:08:14.044233', '2017-05-03 08:07:48.185', 't', NULL, NULL, NULL, NULL, NULL, null, 't', 'f', '1', NULL, '48403150000'
WHERE  NOT EXISTS (   SELECT code FROM classifier WHERE code='SISSEKANNE_R' );

insert into classifier(code,value,value2,name_et,name_en,name_ru,parent_class_code,main_class_code,inserted,changed,valid,description,valid_from,valid_thru,extraval1,extraval2,ehis_value,is_vocational,is_higher,version,inserted_by,changed_by) 
select 'SEADMED_K','K',null,'Kõlarid',null,null,null,'SEADMED',current_timestamp(3),null,true,null,null,null,null,null,null,true,true,0,null,null
WHERE  NOT EXISTS (   SELECT code FROM classifier WHERE code='SEADMED_K' ); 
insert into classifier(code,value,value2,name_et,name_en,name_ru,parent_class_code,main_class_code,inserted,changed,valid,description,valid_from,valid_thru,extraval1,extraval2,ehis_value,is_vocational,is_higher,version,inserted_by,changed_by)
select 'SEADMED_DK','DK',null,'Dokumendikaamera',null,null,null,'SEADMED',current_timestamp(3),null,true,null,null,null,null,null,null,true,true,0,null,null
WHERE  NOT EXISTS (   SELECT code FROM classifier WHERE code='SEADMED_DK' ); 
insert into classifier(code,value,value2,name_et,name_en,name_ru,parent_class_code,main_class_code,inserted,changed,valid,description,valid_from,valid_thru,extraval1,extraval2,ehis_value,is_vocational,is_higher,version,inserted_by,changed_by)  
select 'SEADMED_PM','PM',null,'Paljundusmasin',null,null,null,'SEADMED',current_timestamp(3),null,true,null,null,null,null,null,null,true,true,0,null,null
WHERE  NOT EXISTS (   SELECT code FROM classifier WHERE code='SEADMED_PM' ); 
insert into classifier(code,value,value2,name_et,name_en,name_ru,parent_class_code,main_class_code,inserted,changed,valid,description,valid_from,valid_thru,extraval1,extraval2,ehis_value,is_vocational,is_higher,version,inserted_by,changed_by) 
select 'SEADMED_CD','CD',null,'CD mängija',null,null,null,'SEADMED',current_timestamp(3),null,true,null,null,null,null,null,null,true,true,0,null,null
WHERE  NOT EXISTS (   SELECT code FROM classifier WHERE code='SEADMED_CD' ); 
insert into classifier(code,value,value2,name_et,name_en,name_ru,parent_class_code,main_class_code,inserted,changed,valid,description,valid_from,valid_thru,extraval1,extraval2,ehis_value,is_vocational,is_higher,version,inserted_by,changed_by)
select 'SEADMED_VT','VT',null,'Valge tahvel',null,null,null,'SEADMED',current_timestamp(3),null,true,null,null,null,null,null,null,true,true,0,null,null
WHERE  NOT EXISTS (   SELECT code FROM classifier WHERE code='SEADMED_VT' ); 
insert into classifier(code,value,value2,name_et,name_en,name_ru,parent_class_code,main_class_code,inserted,changed,valid,description,valid_from,valid_thru,extraval1,extraval2,ehis_value,is_vocational,is_higher,version,inserted_by,changed_by) 
select 'SEADMED_MP','MP',null,'Multimeediaprojektor',null,null,null,'SEADMED',current_timestamp(3),null,true,null,null,null,null,null,null,true,true,0,null,null
WHERE  NOT EXISTS (   SELECT code FROM classifier WHERE code='SEADMED_MP' ); 
insert into classifier(code,value,value2,name_et,name_en,name_ru,parent_class_code,main_class_code,inserted,changed,valid,description,valid_from,valid_thru,extraval1,extraval2,ehis_value,is_vocational,is_higher,version,inserted_by,changed_by) 
select 'SEADMED_PP','PP',null,'Paberipurustaja (paberihunt)',null,null,null,'SEADMED',current_timestamp(3),null,true,null,null,null,null,null,null,true,true,0,null,null
WHERE  NOT EXISTS (   SELECT code FROM classifier WHERE code='SEADMED_PP' ); 
insert into classifier(code,value,value2,name_et,name_en,name_ru,parent_class_code,main_class_code,inserted,changed,valid,description,valid_from,valid_thru,extraval1,extraval2,ehis_value,is_vocational,is_higher,version,inserted_by,changed_by) 
select 'SEADMED_KL','KL',null,'Klaver',null,null,null,'SEADMED',current_timestamp(3),null,true,null,null,null,null,null,null,true,true,0,null,null
WHERE  NOT EXISTS (   SELECT code FROM classifier WHERE code='SEADMED_KL' ); 
insert into classifier(code,value,value2,name_et,name_en,name_ru,parent_class_code,main_class_code,inserted,changed,valid,description,valid_from,valid_thru,extraval1,extraval2,ehis_value,is_vocational,is_higher,version,inserted_by,changed_by) 
select 'SEADMED_PI','PI',null,'Pianiino',null,null,null,'SEADMED',current_timestamp(3),null,true,null,null,null,null,null,null,true,true,0,null,null
WHERE  NOT EXISTS (   SELECT code FROM classifier WHERE code='SEADMED_PI' ); 


DO
$$
BEGIN
IF not EXISTS (SELECT column_name 
               FROM information_schema.columns 
               WHERE table_schema='public' and table_name='student_absence' and column_name='is_rejected') THEN
alter table student_absence add column is_rejected boolean;
else
raise NOTICE 'Already exists';
END IF;
END;
$$;

INSERT INTO "public"."classifier" ("code", "value", "value2", "name_et", "name_en", "name_ru", "parent_class_code", "main_class_code", "inserted", "changed", "valid", "description", "valid_from", "valid_thru", "extraval1", "extraval2", "ehis_value", "is_vocational", "is_higher", "version", "inserted_by", "changed_by") 
select 'OPPEASTE_432', '432', NULL, '432 Kolmanda taseme kutseõpe', NULL, NULL, NULL, 'OPPEASTE', '2016-10-31 14:08:14.044233', '2017-05-03 08:07:48.185', 'f', NULL, NULL, NULL, NULL, NULL, '432', 't', 'f', '1', NULL, '48403150000'
WHERE  NOT EXISTS (   SELECT code FROM classifier WHERE code='OPPEASTE_432' );

INSERT INTO "public"."classifier" ("code", "value", "value2", "name_et", "name_en", "name_ru", "parent_class_code", "main_class_code", "inserted", "changed", "valid", "description", "valid_from", "valid_thru", "extraval1", "extraval2", "ehis_value", "is_vocational", "is_higher", "version", "inserted_by", "changed_by") 
 select 'OPPEKEEL_L', 'L', NULL, 'Läti keel', NULL, NULL, NULL, 'OPPEKEEL', '2016-10-31 14:08:14.044233', '2017-05-03 08:07:48.185', 'f', NULL, NULL, NULL, NULL, NULL, 'L', 't', 'f', '1', NULL, '48403150000'
 WHERE  NOT EXISTS (   SELECT code FROM classifier WHERE code='OPPEKEEL_L' );
 
alter table protocol_hdata add column curriculum_id bigint;
create index IXFK_protocol_hdata_curriculum on protocol_hdata(curriculum_id);
alter table protocol_hdata add constraint FK_protocol_hdata_curriculum foreign key(curriculum_id) references curriculum(id); 
alter table student_higher_result drop constraint IF EXISTS "student_higher_result_protocol_student_id_fkey";
alter table student_higher_result add constraint FK_student_higher_result_protocol_student foreign key(protocol_student_id) references protocol_student(id) on delete cascade; 
alter table protocol_student add column curriculum_grade_id bigint;
create index IXFK_protocol_student_curriculum_grade on protocol_student(curriculum_grade_id);
alter table protocol_student add constraint FK_protocol_student_curriculum_grade foreign key (curriculum_grade_id) references curriculum_grade(id);

alter table journal enable trigger all;
alter table journal_student enable trigger all;
alter table journal_entry enable trigger all;
alter table journal_entry_student enable trigger all;
alter table journal_entry_student_history enable trigger all;

update journal_entry_student set grade_code=null where grade_code='KUTSEHINDAMINE__';
alter table journal_entry_student drop constraint  IF EXISTS "journal_entry_student_grade_code_fkey";
alter table journal_entry_student add constraint journal_entry_student_grade_code_fkey foreign key(grade_code) references classifier(code); 





