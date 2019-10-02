\c hois

CREATE TABLE "student_special_need"
(
	"id" bigserial NOT NULL,
	"student_id" bigint NOT NULL,    -- viide õppurile
	"inserted" timestamp without time zone NOT NULL,
	"special_need_code" varchar(100)	 NOT NULL,    -- õppuri erivajadus, viide klassifikaatorile ERIVAJADUS
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "student_special_need"	IS 'õppuri erivajadused';
COMMENT ON COLUMN "student_special_need"."student_id"	IS 'viide õppurile';
COMMENT ON COLUMN "student_special_need"."special_need_code"	IS 'õppuri erivajadus, viide klassifikaatorile ERIVAJADUS';

/* Create Primary Keys, Indexes, Uniques, Checks */
ALTER TABLE "student_special_need" ADD CONSTRAINT "PK_student_special_need"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_student_special_need_classifier" ON "student_special_need" ("special_need_code" ASC);
CREATE INDEX "IXFK_student_special_need_student" ON "student_special_need" ("student_id" ASC);

/* Create Foreign Key Constraints */

ALTER TABLE "student_special_need" ADD CONSTRAINT "FK_student_special_need_classifier"	FOREIGN KEY ("special_need_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "student_special_need" ADD CONSTRAINT "FK_student_special_need_student"	FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE No Action ON UPDATE No Action;
create trigger student_special_need_audit after insert or delete or update on student_special_need for each row execute procedure hois_audit();

insert into student_special_need(student_id,special_need_code,inserted,inserted_by,version)
select id, special_need_code,current_timestamp(3),coalesce(coalesce(changed_by,inserted_by),'Skript'),0
from student where coalescE(special_need_code,'x')!='x';


update classifier set is_higher=false where code='ERIVAJADUS_1';
update classifier set is_higher=false where code='ERIVAJADUS_6';
update classifier set is_higher=false where code='ERIVAJADUS_7';
update classifier set is_higher=false where code='ERIVAJADUS_9';
update classifier set is_higher=false where code='ERIVAJADUS_11';
update classifier set valid_thru=to_date('01.09.2019','dd.mm.yyyy') where code='ERIVAJADUS_5';
update classifier set ehis_value='8' where code='ERIVAJADUS_1';
INSERT INTO "public"."classifier" ("code", "value", "name_et", "ehis_value", "is_vocational", "is_higher", "valid_thru", "inserted", "inserted_by",valid,version) 
VALUES ('ERIVAJADUS_14', '14', 'terviseseisund', '14', 't', 'f', NULL, '2017-01-24 09:50:23.126488', NULL,'t',0);

insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version)
values('TEEMAOIGUS_KOORM','KOORM','Koormuste haldamine','TEEMAOIGUS',now(),true,false,true,0);

insert into user_role_default(object_code,permission_code,role_code) values('TEEMAOIGUS_KOORM','OIGUS_V','ROLL_A');

update classifier set ehis_value='1' where code = 'ERIVAJADUS_1';
update classifier set valid_thru=to_Date('01.09.2019','dd.mm.yyyy') where code = 'ERIVAJADUS_10';
update classifier set ehis_value='13' where code = 'ERIVAJADUS_11';
update classifier set ehis_value='2' where code = 'ERIVAJADUS_2';
update classifier set ehis_value='3' where code = 'ERIVAJADUS_3';
update classifier set ehis_value='4' where code = 'ERIVAJADUS_4';
update classifier set ehis_value='6' where code = 'ERIVAJADUS_6';
update classifier set ehis_value='7' where code = 'ERIVAJADUS_7';
update classifier set ehis_value='8', is_vocational=false where code = 'ERIVAJADUS_8';
update classifier set ehis_value='9' where code = 'ERIVAJADUS_9';