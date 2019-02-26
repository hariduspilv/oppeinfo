\c hois

/* Create Tables */

CREATE TABLE "enterprise_school"
(
	"id" bigserial NOT NULL,
	"enterprise_id" bigint NOT NULL,    -- viide ettevõttele
	"is_active" boolean NOT NULL,    -- kas on aktiivne
	"address_oid" varchar(50)	 NULL,    -- aadress OID kujul
	"address_ads" varchar(50)	 NULL,    -- aadress ADS kujul
	"address" varchar(100)	 NULL,    -- aadress tekst. kujul
	"postcode" varchar(20)	 NULL,    -- postiindeks
	"email" varchar(100)	 NULL,    -- e-mail
	"phone" varchar(100)	 NULL,    -- kontakttelefon
	"language_code" varchar(100),    -- kollektiivi keel, viide klassifikaatorile õppekeel, vaikimisi eesti
	"places" smallint NULL,    -- praktika kohtade arv
	"places_descr" varchar(4000)	 NULL,    -- praktika kohtade selgitus
	"add_info" varchar(4000)	 NULL,    -- märkused
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

CREATE TABLE "enterprise_school_person"
(
	"id" bigserial NOT NULL,
	"enterprise_school_id" bigint NOT NULL,    -- viide ettevõttele
	"firstname" varchar(100)	 NOT NULL,    -- eesnimi
	"lastname" varchar(100)	 NOT NULL,    -- perekonnanimi
	"phone" varchar(100)	 NULL,    -- kontakttelefon
	"email" varchar(100)	 NULL,    -- e-mail
	"idcode" varchar(50)	 NULL,    -- isikukood
	"idcode_country_code" varchar(100)	 NULL,    -- isikukoodi riik, viide klassifikaatorile RIIK
	"position" varchar(100)	 NULL,    -- ametikoht
	"is_supervisor" boolean NOT NULL,    -- kas on juhendaja
	"is_contact" boolean NOT NULL,    -- kas on kontaktisik
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

CREATE TABLE "enterprise_school_location"
(
	"id" bigserial NOT NULL,
	"enterprise_school_id" bigint NOT NULL,    -- viide ettevõttele
	"language_code" varchar(100)	 NULL,    -- kollektiivi keel, viide klassifikaatorile õppekeel
	"address_oid" varchar(50)	 NULL,    -- aadress oid kujul
	"country_code" varchar(100)	 NOT NULL,    -- riik, viide klassifikaatorile RIIK
	"address_ads" varchar(50)	 NULL,    -- aadress ads kujul
	"address" varchar(100)	 NULL,    -- aadress tekst
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

CREATE TABLE "enterprise_school_isced_class"
(
	"id" bigserial NOT NULL,
	"enterprise_school_id" bigint NOT NULL,    -- viide ettevõttele
	"isced_class_code" varchar(100)	 NOT NULL,    -- viide klassifikaatorile ISCED_RYHM
	"places" smallint NULL,    -- kohtade arv
	"add_info" varchar(255)	 NULL,    -- selgitus
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

alter table enterprise_school add column school_id bigint not null;
create index IXFK_enterprise_school_school on enterprise_school(school_id);
alter table enterprise_school add CONSTRAINT FK_enterprise_school_school foreign key(school_id) references school(id);


/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "enterprise_school"
	IS 'ettevõtte seos kooliga'
;

COMMENT ON COLUMN "enterprise_school"."enterprise_id"
	IS 'viide ettevõttele'
;

COMMENT ON COLUMN "enterprise_school"."is_active"
	IS 'kas on aktiivne'
;

COMMENT ON COLUMN "enterprise_school"."address_oid"
	IS 'aadress OID kujul'
;

COMMENT ON COLUMN "enterprise_school"."address_ads"
	IS 'aadress ADS kujul'
;

COMMENT ON COLUMN "enterprise_school"."address"
	IS 'aadress tekst. kujul'
;

COMMENT ON COLUMN "enterprise_school"."postcode"
	IS 'postiindeks'
;

COMMENT ON COLUMN "enterprise_school"."email"
	IS 'e-mail'
;

COMMENT ON COLUMN "enterprise_school"."phone"
	IS 'kontakttelefon'
;

COMMENT ON COLUMN "enterprise_school"."language_code"
	IS 'kollektiivi keel, viide klassifikaatorile õppekeel, vaikimisi eesti'
;

COMMENT ON COLUMN "enterprise_school"."places"
	IS 'praktika kohtade arv'
;

COMMENT ON COLUMN "enterprise_school"."places_descr"
	IS 'praktika kohtade selgitus'
;

COMMENT ON COLUMN "enterprise_school"."add_info"
	IS 'märkused'
;


COMMENT ON TABLE "enterprise_school_person"
	IS 'ettevõttega seotud isikud, koolipõhised'
;

COMMENT ON COLUMN "enterprise_school_person"."enterprise_school_id"
	IS 'viide ettevõttele'
;

COMMENT ON COLUMN "enterprise_school_person"."firstname"
	IS 'eesnimi'
;

COMMENT ON COLUMN "enterprise_school_person"."lastname"
	IS 'perekonnanimi'
;

COMMENT ON COLUMN "enterprise_school_person"."phone"
	IS 'kontakttelefon'
;

COMMENT ON COLUMN "enterprise_school_person"."email"
	IS 'e-mail'
;

COMMENT ON COLUMN "enterprise_school_person"."idcode"
	IS 'isikukood'
;

COMMENT ON COLUMN "enterprise_school_person"."idcode_country_code"
	IS 'isikukoodi riik, viide klassifikaatorile RIIK'
;

COMMENT ON COLUMN "enterprise_school_person"."position"
	IS 'ametikoht'
;

COMMENT ON COLUMN "enterprise_school_person"."is_supervisor"
	IS 'kas on juhendaja'
;

COMMENT ON COLUMN "enterprise_school_person"."is_contact"
	IS 'kas on kontaktisik'
;


COMMENT ON TABLE "enterprise_school_location"
	IS 'ettevõtte praktika asukohad'
;

COMMENT ON COLUMN "enterprise_school_location"."enterprise_school_id"
	IS 'viide ettevõttele'
;

COMMENT ON COLUMN "enterprise_school_location"."language_code"
	IS 'kollektiivi keel, viide klassifikaatorile õppekeel'
;

COMMENT ON COLUMN "enterprise_school_location"."address_oid"
	IS 'aadress oid kujul'
;

COMMENT ON COLUMN "enterprise_school_location"."country_code"
	IS 'riik, viide klassifikaatorile RIIK'
;

COMMENT ON COLUMN "enterprise_school_location"."address_ads"
	IS 'aadress ads kujul'
;

COMMENT ON COLUMN "enterprise_school_location"."address"
	IS 'aadress tekst'
;

COMMENT ON TABLE "enterprise_school_isced_class"
	IS 'ettevõtte seos õppekava rühmadega'
;

COMMENT ON COLUMN "enterprise_school_isced_class"."enterprise_school_id"
	IS 'viide ettevõttele'
;

COMMENT ON COLUMN "enterprise_school_isced_class"."isced_class_code"
	IS 'viide klassifikaatorile ISCED_RYHM'
;

COMMENT ON COLUMN "enterprise_school_isced_class"."places"
	IS 'kohtade arv'
;

COMMENT ON COLUMN "enterprise_school_isced_class"."add_info"
	IS 'selgitus'
;


/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE "enterprise_school" ADD CONSTRAINT "PK_enterprise_school"
	PRIMARY KEY ("id")
;

CREATE INDEX "IXFK_enterprise_school_classifier" ON "enterprise_school" ("language_code" ASC)
;

CREATE INDEX "IXFK_enterprise_school_enterprise" ON "enterprise_school" ("enterprise_id" ASC)
;

ALTER TABLE "enterprise_school_person" ADD CONSTRAINT "PK_enterprise_school_person"
	PRIMARY KEY ("id")
;

CREATE INDEX "IXFK_enterprise_school_person_classifier" ON "enterprise_school_person" ("idcode_country_code" ASC)
;

CREATE INDEX "IXFK_enterprise_school_person_enterprise_school" ON "enterprise_school_person" ("enterprise_school_id" ASC)
;

ALTER TABLE "enterprise_school_location" ADD CONSTRAINT "PK_enterprise_school_location"
	PRIMARY KEY ("id")
;

CREATE INDEX "IXFK_enterprise_school_location_classifier" ON "enterprise_school_location" ("language_code" ASC)
;

CREATE INDEX "IXFK_enterprise_school_location_classifier_02" ON "enterprise_school_location" ("country_code" ASC)
;

CREATE INDEX "IXFK_enterprise_school_location_enterprise_school" ON "enterprise_school_location" ("enterprise_school_id" ASC)
;

ALTER TABLE "enterprise_school_isced_class" ADD CONSTRAINT "PK_enterprise_school_isced"	PRIMARY KEY ("id");

CREATE INDEX "IXFK_enterprise_school_isced_class_classifier" ON "enterprise_school_isced_class" ("isced_class_code" ASC);
CREATE INDEX "IXFK_enterprise_school_isced_enterprise_school" ON "enterprise_school_isced_class" ("enterprise_school_id" ASC);

/* Create Foreign Key Constraints */

ALTER TABLE "enterprise_school" ADD CONSTRAINT "FK_enterprise_school_classifier"	FOREIGN KEY ("language_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "enterprise_school" ADD CONSTRAINT "FK_enterprise_school_enterprise"	FOREIGN KEY ("enterprise_id") REFERENCES "enterprise" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "enterprise_school_person" ADD CONSTRAINT "FK_enterprise_school_person_classifier"	FOREIGN KEY ("idcode_country_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "enterprise_school_person" ADD CONSTRAINT "FK_enterprise_school_person_enterprise_school"	FOREIGN KEY ("enterprise_school_id") REFERENCES "enterprise_school" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "enterprise_school_location" ADD CONSTRAINT "FK_enterprise_school_location_classifier"	FOREIGN KEY ("language_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "enterprise_school_location" ADD CONSTRAINT "FK_enterprise_school_location_classifier_02"	FOREIGN KEY ("country_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "enterprise_school_location" ADD CONSTRAINT "FK_enterprise_school_location_enterprise_school"	FOREIGN KEY ("enterprise_school_id") REFERENCES "enterprise_school" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "enterprise_school_isced_class" ADD CONSTRAINT "FK_enterprise_school_isced_class_classifier"	FOREIGN KEY ("isced_class_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "enterprise_school_isced_class" ADD CONSTRAINT "FK_enterprise_school_isced_enterprise_school"	FOREIGN KEY ("enterprise_school_id") REFERENCES "enterprise_school" ("id") ON DELETE No Action ON UPDATE No Action;


alter table enterprise 
	add column country_code varchar(100),
	add column is_person boolean,
	add column  address_oid varchar(50),
	add column address_ads varchar(50),
	add column address varchar(100);

comment on column enterprise.country_code is 'viide klassifikaatorile RIIK';
comment on column enterprise.is_person is 'kas tegemist on eraisikuga';
comment on column enterprise.address_oid is 'äriregistri aadress oid';
comment on column enterprise.address_ads is 'äriregistri aadress ads';
comment on column enterprise.address is 'aadress tekstilisel kujul';

alter table enterprise add constraint FK_enterprise_classifier foreign key (country_code) references classifier(code);
create index IXFK_enterprise_classifier on enterprise(country_code);

do $$
declare r record;
	p_id integer;
BEGIN
	for r in (select distinct s.school_id,e.id as e_id,cc.* from enterprise e join contract cc on e.id=cc.enterprise_id join student s on cc.student_id=s.id )
	loop
		insert into enterprise_school(enterprise_id,is_Active,language_code,inserted,version,inserted_by,school_id)
		values(r.e_id,true,'OPPEKEEL_E',current_Timestamp(3),0,'Automaat',r.school_id) returning id into p_id;
		insert into enterprise_school_person(enterprise_school_id,firstname,lastname,is_supervisor,is_contact,email,phone,inserted,version,inserted_by)
		values(p_id,case when position(' ' in r.contact_person_name) > 1 then substring(r.contact_person_name,1,position(' ' in r.contact_person_name)-1) else ' ' end ,
		case when position(' ' in r.contact_person_name) > 1 then coalesce(substring(r.contact_person_name,position(' ' in r.contact_person_name)+1),' ') else ' ' end,false,true,
		r.contact_person_email,r.contact_person_phone,current_Timestamp(3),0,'Automaat');
	end loop;
end;
$$;


CREATE TABLE "practice_admission"
(
	"id" bigserial NOT NULL,
	"enterprise_school_id" bigint NOT NULL,
	"valid_from" date NOT NULL,
	"valid_thru" date NOT NULL,
	"places" smallint NULL,    -- kohti
	"add_info" varchar(4000)	 NULL,    -- selgitav info
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

CREATE TABLE "practice_admission_student_group"
(
	"id" bigserial NOT NULL,
	"practice_admission_id" bigint NOT NULL,
	"student_group_id" bigint NOT NULL,
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

CREATE TABLE "practice_application"
(
	"id" bigserial NOT NULL,
	"student_id" bigint NOT NULL,    -- viide õppurile
	"practice_admission_id" bigint NOT NULL,
	"add_info" varchar(4000)	 NULL,    -- õppuri lisainfo
	"status_code" varchar(100)	 NOT NULL,    -- taotluse staatus, viide klassifikaatorile PR_TAOTLUS
	"submitted" date NULL,
	"reject_reason" varchar(4000)	 NULL,
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "practice_admission"	IS 'praktika taotluste seaded';
COMMENT ON COLUMN "practice_admission"."places"	IS 'kohti';
COMMENT ON COLUMN "practice_admission"."add_info"	IS 'selgitav info';
COMMENT ON TABLE "practice_admission_student_group"	IS 'kes tohib taotleda';
COMMENT ON TABLE "practice_application"	IS 'praktika avaldus';
COMMENT ON COLUMN "practice_application"."student_id"	IS 'viide õppurile';
COMMENT ON COLUMN "practice_application"."add_info"	IS 'õppuri lisainfo';
COMMENT ON COLUMN "practice_application"."status_code"	IS 'taotluse staatus, viide klassifikaatorile PR_TAOTLUS';

ALTER TABLE "practice_admission" ADD CONSTRAINT "PK_enterprise_school_admission"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_enterprise_school_admission_enterprise_school" ON "practice_admission" ("enterprise_school_id" ASC);
ALTER TABLE "practice_admission_student_group" ADD CONSTRAINT "PK_enterprise_school_student_group"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_enterprise_school_student_group_enterprise_school_admission" ON "practice_admission_student_group" ("practice_admission_id" ASC);
CREATE INDEX "IXFK_enterprise_school_student_group_student_group" ON "practice_admission_student_group" ("student_group_id" ASC);
ALTER TABLE "practice_application" ADD CONSTRAINT "PK_practice_application"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_practice_application_classifier" ON "practice_application" ("status_code" ASC);
CREATE INDEX "IXFK_practice_application_practice_admission" ON "practice_application" ("practice_admission_id" ASC);
CREATE INDEX "IXFK_practice_application_student" ON "practice_application" ("student_id" ASC);

/* Create Foreign Key Constraints */

ALTER TABLE "practice_admission" ADD CONSTRAINT "FK_enterprise_school_admission_enterprise_school"	FOREIGN KEY ("enterprise_school_id") REFERENCES "enterprise_school" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "practice_admission_student_group" ADD CONSTRAINT "FK_enterprise_school_student_group_enterprise_school_admission"	FOREIGN KEY ("practice_admission_id") REFERENCES "practice_admission" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "practice_admission_student_group" ADD CONSTRAINT "FK_enterprise_school_student_group_student_group"	FOREIGN KEY ("student_group_id") REFERENCES "public"."student_group" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "practice_application" ADD CONSTRAINT "FK_practice_application_classifier"	FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "practice_application" ADD CONSTRAINT "FK_practice_application_practice_admission"	FOREIGN KEY ("practice_admission_id") REFERENCES "practice_admission" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "practice_application" ADD CONSTRAINT "FK_practice_application_student"	FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE No Action ON UPDATE No Action;

alter table contract add practice_application_id bigint;
ALTER TABLE "contract" ADD CONSTRAINT "FK_contract_practice_application"	FOREIGN KEY ("practice_application_id") REFERENCES "public"."practice_application" ("id") ON DELETE No Action ON UPDATE No Action;
CREATE INDEX "IXFK_contract_practice_application" ON "contract" ("practice_application_id" ASC);

INSERT INTO "classifier" ("code", "value", "name_et", "inserted", "valid", "is_vocational", "is_higher", "version") VALUES ('PR_TAOTLUS', 'PR_TAOTLUS', 'Praktika taotluse staatus', current_timestamp(3), 't', 't', 't', '0');
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('PR_TAOTLUS_E', 'E', 'Esitatud', 'PR_TAOTLUS',current_timestamp(3), 't', 't', 't', '0',null);
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('PR_TAOTLUS_T', 'T', 'Tagasi lükatud', 'PR_TAOTLUS',current_timestamp(3), 't', 't', 't', '0',null);


alter table enterprise_school_location add column name_et varchar(255);
alter table enterprise add column ebusiness_updated TIMESTAMP;

alter table enterprise_school add column is_application boolean;
comment on column enterprise_school.is_application is 'Kas saab taotlusi esitada';
alter table enterprise_school add column rating_code varchar(100);
comment on column enterprise_school.rating_code is 'hinnang, viide klassifikaatorile PR_HINNANG';
alter table enterprise_school add constraint FK_enterprise_school_classifier_02 foreign key(rating_code) references classifier(code);
create index IXFK_enterprise_school_classifier_02 on enterprise_school(rating_code);
alter table enterprise_school add column rating_thru date;
comment on column enterprise_school.rating_thru is 'kui kaua tunnustatud';
alter table enterprise_school add column rating_info varchar(4000);
comment on column enterprise_school.rating_info is 'hinnangu selgitav info';

INSERT INTO "classifier" ("code", "value", "name_et", "inserted", "valid", "is_vocational", "is_higher", "version") VALUES ('PR_HINNANG', 'PR_HINNANG', 'Praktika ettevõtte hinnang', current_timestamp(3), 't', 't', 't', '0');
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('PR_HINNANG_T', 'T', 'Tunnustatud', 'PR_HINNANG',current_timestamp(3), 't', 't', 't', '0',null);
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('PR_HINNANG_E', 'E', 'Ei vasta tingimustele', 'PR_HINNANG',current_timestamp(3), 't', 't', 't', '0',null);
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('PR_HINNANG_A', 'A', 'Tunnustatud tähtajaga', 'PR_HINNANG',current_timestamp(3), 't', 't', 't', '0',null);
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('PR_HINNANG_P', 'P', 'Puudub', 'PR_HINNANG',current_timestamp(3), 't', 't', 't', '0',null);
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('PR_HINNANG_X', 'X', 'Tunnustus aegunud', 'PR_HINNANG',current_timestamp(3), 't', 't', 't', '0',null);

CREATE TABLE "journal_teacher_capacity"
(
	"id" bigserial NOT NULL,
	"journal_teacher_id" bigint NOT NULL,
	"journal_capacity_type_id" bigint NOT NULL,
	"study_period_id" bigint NOT NULL,    -- viide perioodile
	"week_nr" smallint NULL,    -- nädala nr
	"hours" smallint NOT NULL,    -- kui mitu tundi
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL,
	"version" integer NOT NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "journal_teacher_capacity"
	IS 'õpetaja täpsem planeeritud maht'
;

COMMENT ON COLUMN "journal_teacher_capacity"."study_period_id"
	IS 'viide perioodile'
;

COMMENT ON COLUMN "journal_teacher_capacity"."week_nr"
	IS 'nädala nr'
;

COMMENT ON COLUMN "journal_teacher_capacity"."hours"
	IS 'kui mitu tundi'
;

/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE "journal_teacher_capacity" ADD CONSTRAINT "PK_journal_teacher_capacity"
	PRIMARY KEY ("id")
;

CREATE INDEX "IXFK_journal_teacher_capacity_journal_capacity_type" ON "journal_teacher_capacity" ("journal_capacity_type_id" ASC)
;

CREATE INDEX "IXFK_journal_teacher_capacity_journal_teacher" ON "journal_teacher_capacity" ("journal_teacher_id" ASC)
;

CREATE INDEX "IXFK_journal_teacher_capacity_study_period" ON "journal_teacher_capacity" ("study_period_id" ASC)
;

/* Create Foreign Key Constraints */

ALTER TABLE "journal_teacher_capacity" ADD CONSTRAINT "FK_journal_teacher_capacity_journal_capacity_type"
	FOREIGN KEY ("journal_capacity_type_id") REFERENCES "public"."journal_capacity_type" ("id") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "journal_teacher_capacity" ADD CONSTRAINT "FK_journal_teacher_capacity_journal_teacher"
	FOREIGN KEY ("journal_teacher_id") REFERENCES "public"."journal_teacher" ("id") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "journal_teacher_capacity" ADD CONSTRAINT "FK_journal_teacher_capacity_study_period"
	FOREIGN KEY ("study_period_id") REFERENCES "public"."study_period" ("id") ON DELETE No Action ON UPDATE No Action
;


alter table journal add column is_capacity_diff boolean;

comment on column journal.is_capacity_diff is 'märgitakse mitme õpetaja korral kas neil on erinevad mahud või mitte, vaikimisi mahud on identsed, need samad mis on märgitud journal_capacity tabelisse';


INSERT INTO "classifier" ("code", "value", "value2", "name_et", "name_en", "name_ru", "parent_class_code", "main_class_code", "inserted", "changed", "valid", "description", "valid_from", "valid_thru", "extraval1", "extraval2", "ehis_value", "is_vocational", "is_higher", "version", "inserted_by", "changed_by") VALUES ('TEEMAOIGUS_ETTEVOTE', 'ETTEVOTE', NULL, 'Praktika ettevõtted', NULL, NULL, NULL, 'TEEMAOIGUS', '2017-09-08 13:49:14.223437', '2017-09-08 14:13:34.858', 't', NULL, NULL, NULL, NULL, NULL, NULL, 't', 't', '1', NULL, 'Tiina Kasutaja1 (48403150000)');
insert into user_role_default(object_code,permission_code,role_code) values ('TEEMAOIGUS_ETTEVOTE','OIGUS_V','ROLL_A');

INSERT INTO "classifier" ("code", "value", "name_et", "inserted", "valid", "is_vocational", "is_higher", "version") VALUES ('LEPING_TYH_POHJUS', 'LEPING_TYH_POHJUS', 'Praktika lepingu tühistamise põhjus', current_timestamp(3), 't', 't', 't', '0');
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('LEPING_TYH_POHJUS_L', 'L', 'Ettevõte lõpetas tegevuse', 'LEPING_TYH_POHJUS',current_timestamp(3), 't', 't', 't', '0',null);
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('LEPING_TYH_POHJUS_A', 'A', 'Ettevõtte algatusel', 'LEPING_TYH_POHJUS',current_timestamp(3), 't', 't', 't', '0',null);
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('LEPING_TYH_POHJUS_O', 'O', 'Õppija algatusel', 'LEPING_TYH_POHJUS',current_timestamp(3), 't', 't', 't', '0',null);
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('LEPING_TYH_POHJUS_S', 'S', 'Õppeasutuse algatusel', 'LEPING_TYH_POHJUS',current_timestamp(3), 't', 't', 't', '0',null);
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('LEPING_TYH_POHJUS_M', 'M', 'Muu', 'LEPING_TYH_POHJUS',current_timestamp(3), 't', 't', 't', '0',null);
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('LEPING_STAATUS_T', 'T', 'Tühistatud', 'LEPING_STAATUS',current_timestamp(3), 't', 't', 't', '0',null);


alter table contract 
   add column "cancel_reason_code" varchar(100)	 NULL,    -- tühistamise põhjus, viide klassifikaatorile LEPING_TYH_POHJUS
	add column "canceled" timestamp without time zone NULL,    -- tühistamise kp
	add column "canceled_by" varchar(100)	 NULL,    -- tühistaja
	add column "cancel_desc" varchar(4000)	 NULL;    -- tühistamise lisainfo

COMMENT ON COLUMN "contract"."cancel_reason_code"	IS 'tühistamise põhjus, viide klassifikaatorile LEPING_TYH_POHJUS';
COMMENT ON COLUMN "contract"."canceled"	IS 'tühistamise kp';
COMMENT ON COLUMN "contract"."canceled_by"	IS 'tühistaja';
COMMENT ON COLUMN "contract"."cancel_desc"	IS 'tühistamise lisainfo';

CREATE INDEX "IXFK_contract_classifier_02" ON "contract" ("cancel_reason_code" ASC);
ALTER TABLE "contract" ADD CONSTRAINT "FK_contract_classifier_02"	FOREIGN KEY ("cancel_reason_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;


CREATE TABLE "ws_arireg_log"
(
	"id" bigserial NOT NULL,
	"school_id" bigint NOT NULL,    -- viide õppeasutusele
	"ws_name" varchar(255)	 NOT NULL,
	"request" text NOT NULL,
	"response" text NULL,
	"has_errors" boolean NOT NULL,
	"log_txt" text NULL,
	"inserted" timestamp without time zone NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "ws_arireg_log"	IS 'Äriregistri veebiteenuse logitabel';
COMMENT ON COLUMN "ws_arireg_log"."school_id"	IS 'viide õppeasutusele';
ALTER TABLE "ws_arireg_log" ADD CONSTRAINT "PK_ws_arireg_log"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_ws_arireg_log_school" ON "ws_arireg_log" ("school_id" ASC);
ALTER TABLE "ws_arireg_log" ADD CONSTRAINT "FK_ws_arireg_log_school"	FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE No Action ON UPDATE No Action;


alter table enterprise alter column reg_code drop not null;

/* Create Tables */

alter TABLE "contract"
add column	"is_practice_school" boolean NULL,    -- praktika sooritatakse koolis
add column	"is_practice_telework" boolean NULL,    -- praktika sooritatakse kaugtööna
add column	"is_practice_enterprise" boolean NULL,    -- praktika sooritatakse ettevõttes
add column	"is_practice_other" boolean NULL,    -- praktika sooritamise asukoht "muu"
add column	"practice_evaluation_id" bigint NULL    -- viide hindamisvormile
;

CREATE TABLE "contract_supervisor"
(
	"id" bigserial NOT NULL,
	"contract_id" bigint NOT NULL,
	"supervisor_name" varchar(100)	 NOT NULL,    -- juhendaja nimi
	"supervisor_phone" varchar(100)	 NULL,    -- juhendaja telefon
	"supervisor_email" varchar(100)	 NOT NULL,    -- juhendaja e-mail
	"supervisor_url" varchar(4000)	 NULL,    -- juhendaja url
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

CREATE TABLE "practice_evaluation"
(
	"id" bigserial NOT NULL,
	"name_et" varchar(255)	 NOT NULL,
	"add_info" varchar(400)	 NULL,
	"is_active" boolean NOT NULL,
	"target_code" varchar(100)	 NOT NULL,    -- sihtrühm, viide klassifikaatorile PRAKTIKA_SIHTRYHM
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

CREATE TABLE "practice_evaluation_criteria"
(
	"id" bigserial NOT NULL,
	"name_et" varchar(100)	 NOT NULL,
	"practice_evaluation_id" bigint NOT NULL,
	"add_info" varchar(255)	 NULL,
	"order_nr" smallint NOT NULL,
	"inserted" timestamp without time zone NOT NULL,
	"type_code" varchar(100)	 NOT NULL,    -- kkriteeriumi väärtus või tüüp, viida klassifikaatorile PRAKTIKA_KRITEERIUM
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

COMMENT ON COLUMN "contract"."is_practice_school"	IS 'praktika sooritatakse koolis';
COMMENT ON COLUMN "contract"."is_practice_telework"	IS 'praktika sooritatakse kaugtööna';
COMMENT ON COLUMN "contract"."is_practice_enterprise"	IS 'praktika sooritatakse ettevõttes';
COMMENT ON COLUMN "contract"."is_practice_other"	IS 'praktika sooritamise asukoht "muu"';
COMMENT ON COLUMN "contract"."practice_evaluation_id"	IS 'viide hindamisvormile';

COMMENT ON TABLE "contract_supervisor"	IS 'praktika lepinguga seotud juhendajad';
COMMENT ON COLUMN "contract_supervisor"."supervisor_name"	IS 'juhendaja nimi';
COMMENT ON COLUMN "contract_supervisor"."supervisor_phone"	IS 'juhendaja telefon';
COMMENT ON COLUMN "contract_supervisor"."supervisor_email"	IS 'juhendaja e-mail';
COMMENT ON COLUMN "contract_supervisor"."supervisor_url"	IS 'juhendaja url';

COMMENT ON TABLE "practice_evaluation"	IS 'praktika hindamisvorm';
COMMENT ON COLUMN "practice_evaluation"."target_code"	IS 'sihtrühm, viide klassifikaatorile PRAKTIKA_SIHTRYHM';

COMMENT ON TABLE "practice_evaluation_criteria"	IS 'praktika hindamisvormi krtiteeriumid';
COMMENT ON COLUMN "practice_evaluation_criteria"."type_code"	IS 'kkriteeriumi väärtus või tüüp, viida klassifikaatorile PRAKTIKA_KRITEERIUM';

CREATE INDEX "IXFK_contract_practice_evaluation" ON "contract" ("practice_evaluation_id" ASC);
ALTER TABLE "contract_supervisor" ADD CONSTRAINT "PK_contract_supervisor"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_contract_supervisor_contract" ON "contract_supervisor" ("contract_id" ASC);
ALTER TABLE "practice_evaluation" ADD CONSTRAINT "PK_practice_evaluation"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_practice_evaluation_classifier" ON "practice_evaluation" ("target_code" ASC);
ALTER TABLE "practice_evaluation_criteria" ADD CONSTRAINT "PK_practice_evaluation_criteria"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_practice_evaluation_criteria_classifier" ON "practice_evaluation_criteria" ("type_code" ASC);
CREATE INDEX "IXFK_practice_evaluation_criteria_practice_evaluation" ON "practice_evaluation_criteria" ("practice_evaluation_id" ASC);

ALTER TABLE "contract" ADD CONSTRAINT "FK_contract_practice_evaluation"	FOREIGN KEY ("practice_evaluation_id") REFERENCES "practice_evaluation" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "contract_supervisor" ADD CONSTRAINT "FK_contract_supervisor_contract"	FOREIGN KEY ("contract_id") REFERENCES "contract" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "practice_evaluation" ADD CONSTRAINT "FK_practice_evaluation_classifier"	FOREIGN KEY ("target_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "practice_evaluation_criteria" ADD CONSTRAINT "FK_practice_evaluation_criteria_classifier"	FOREIGN KEY ("type_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "practice_evaluation_criteria" ADD CONSTRAINT "FK_practice_evaluation_criteria_practice_evaluation"	FOREIGN KEY ("practice_evaluation_id") REFERENCES "practice_evaluation" ("id") ON DELETE No Action ON UPDATE No Action;

INSERT INTO "classifier" ("code", "value", "name_et", "inserted", "valid", "is_vocational", "is_higher", "version") VALUES ('PRAKTIKA_SIHTRYHM', 'PRAKTIKA_SIHTRYHM', 'Praktika hindamisvormi sihtrühm', current_timestamp(3), 't', 't', 't', '0');
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('PRAKTIKA_SIHTRYHM_O', 'O', 'Õppija', 'PRAKTIKA_SIHTRYHM',current_timestamp(3), 't', 't', 't', '0',null);
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('PRAKTIKA_SIHTRYHM_E', 'E', 'Ettevõtte juhendaja', 'PRAKTIKA_SIHTRYHM',current_timestamp(3), 't', 't', 't', '0',null);


INSERT INTO "classifier" ("code", "value", "name_et", "inserted", "valid", "is_vocational", "is_higher", "version") VALUES ('PRAKTIKA_KRITEERIUM', 'PRAKTIKA_KRITEERIUM', 'Praktika hindamisvormi kriteeriumi tüüp', current_timestamp(3), 't', 't', 't', '0');
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('PRAKTIKA_KRITEERIUM_T', 'T', 'Tekst', 'PRAKTIKA_KRITEERIUM',current_timestamp(3), 't', 't', 't', '0',null);
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('PRAKTIKA_KRITEERIUM_N', 'N', 'Number', 'PRAKTIKA_KRITEERIUM',current_timestamp(3), 't', 't', 't', '0',null);
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('PRAKTIKA_KRITEERIUM_H', 'H', 'Hinne', 'PRAKTIKA_KRITEERIUM',current_timestamp(3), 't', 't', 't', '0',null);


INSERT INTO "classifier" ("code", "value", "name_et", "main_class_code", "inserted", "changed", "valid", "is_vocational", "is_higher", "version", "inserted_by", "changed_by") 
VALUES ('TEEMAOIGUS_PRSTATISTIKA', 'PRSTATISTIKA', 'Praktika statistika', 'TEEMAOIGUS', current_timestamp(3), current_timestamp(3), 't','t', 't', '1', 'Automaat', 'Automaat');
insert into user_role_default(object_code,permission_code,role_code) values ('TEEMAOIGUS_PRSTATISTIKA','OIGUS_V','ROLL_A');

insert into contract_supervisor (contract_id, supervisor_name,supervisor_phone,supervisor_email,supervisor_url, inserted, version, inserted_by)
select id, supervisor_name,supervisor_phone,supervisor_email,supervisor_url, inserted, 1, inserted_by
from contract;


insert into classifier (code, value, name_et, main_class_code, inserted, valid, is_vocational, is_higher, version) values ('TEEMAOIGUS_PRAKTIKAAVALDUS', 'PRAKTIKAAVALDUS', 'Praktika avaldused', 'TEEMAOIGUS', now(), true,  true, true, 0);
insert into user_role_default (object_code, permission_code, role_code) values ('TEEMAOIGUS_PRAKTIKAAVALDUS', 'OIGUS_V', 'ROLL_A');

insert into classifier (code, value, name_et, main_class_code, inserted, valid, is_vocational, is_higher, version) values
('PR_TAOTLUS_C', 'C', 'Tühistatud', 'PR_TAOTLUS', now(), true,  true,  true, 0),
('PR_TAOTLUS_A', 'A', 'Aktsepteeritud', 'PR_TAOTLUS', now(), true,  true,  true, 0);


alter table curriculum_version_hmodule_subject
add column 	"study_year_number" smallint NULL,    -- mitmendal õppeaastal õppeaine läbitakse, nt 1, 2 , 3 jne
add column 	"is_autumn" boolean NULL,    -- kas õpetataks esügisel
add column 	"is_spring" boolean NULL    -- kas õpetatakse kevadel
;

COMMENT ON COLUMN "curriculum_version_hmodule_subject"."study_year_number"	IS 'mitmendal õppeaastal õppeaine läbitakse, nt 1, 2 , 3 jne';
COMMENT ON COLUMN "curriculum_version_hmodule_subject"."is_autumn"	IS 'kas õpetatakse sügisel';
COMMENT ON COLUMN "curriculum_version_hmodule_subject"."is_spring"	IS 'kas õpetatakse kevadel';

alter TABLE "practice_evaluation" add column	"school_id" bigint NOT NULL    -- viide õppeasutusele
;
CREATE INDEX "IXFK_practice_evaluation_school" ON "practice_evaluation" ("school_id" ASC);
ALTER TABLE "practice_evaluation" ADD CONSTRAINT "FK_practice_evaluation_school" 	FOREIGN KEY ("school_id") REFERENCES "school" ("id") ON DELETE No Action ON UPDATE No Action;

alter table practice_admission add column is_strict BOOLEAN;
comment on column practice_admission.is_strict is 'kas kohtade arv on rangelt piiratud';

alter table contract alter column practice_place drop not null;

alter table practice_journal add practice_evaluation_id bigint;
comment on column practice_journal.practice_evaluation_id is 'viide hindamisvormile';
CREATE INDEX "IXFK_practice_journal_practice_evaluation" ON "practice_journal" ("practice_evaluation_id" ASC);
ALTER TABLE "practice_journal" ADD CONSTRAINT "FK_practice_journal_practice_evaluation"	FOREIGN KEY ("practice_evaluation_id") REFERENCES "practice_evaluation" ("id") ON DELETE No Action ON UPDATE No Action;



CREATE TABLE "contract_module_subject"
(
	"id" bigserial NOT NULL,
	"contract_id" bigint NOT NULL,
	"curriculum_version_omodule_id" bigint NULL,    -- viide moodulile
	"curriculum_version_omodule_theme_id" bigint NULL,    -- viide teemale
	"subject_id" bigint NULL,    -- viide õppeainele
	"credits" numeric(4,1) NOT NULL,
	"hours" smallint NOT NULL,
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "contract_module_subject"	IS 'lepinguga seotud moodulid/teemad/ained';
COMMENT ON COLUMN "contract_module_subject"."curriculum_version_omodule_id"	IS 'viide moodulile';
COMMENT ON COLUMN "contract_module_subject"."curriculum_version_omodule_theme_id"	IS 'viide teemale';
COMMENT ON COLUMN "contract_module_subject"."subject_id"	IS 'viide õppeainele';

/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE "contract_module_subject" ADD CONSTRAINT "PK_contract_module_subject"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_contract_module_subject_contract" ON "contract_module_subject" ("contract_id" ASC);
CREATE INDEX "IXFK_contract_module_subject_curriculum_version_omodule" ON "contract_module_subject" ("curriculum_version_omodule_id" ASC);
CREATE INDEX "IXFK_contract_module_subject_curriculum_version_omodule_theme" ON "contract_module_subject" ("curriculum_version_omodule_theme_id" ASC);
CREATE INDEX "IXFK_contract_module_subject_subject" ON "contract_module_subject" ("subject_id" ASC);

/* Create Foreign Key Constraints */

ALTER TABLE "contract_module_subject" ADD CONSTRAINT "FK_contract_module_subject_contract"	FOREIGN KEY ("contract_id") REFERENCES "contract" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "contract_module_subject" ADD CONSTRAINT "FK_contract_module_subject_curriculum_version_omodule"	FOREIGN KEY ("curriculum_version_omodule_id") REFERENCES "curriculum_version_omodule" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "contract_module_subject" ADD CONSTRAINT "FK_contract_module_subject_curriculum_version_omodule_theme"	FOREIGN KEY ("curriculum_version_omodule_theme_id") REFERENCES "curriculum_version_omodule_theme" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "contract_module_subject" ADD CONSTRAINT "FK_contract_module_subject_subject"	FOREIGN KEY ("subject_id") REFERENCES "subject" ("id") ON DELETE No Action ON UPDATE No Action;


/* Create Tables */

CREATE TABLE "practice_journal_module_subject"
(
	"id" bigserial NOT NULL,
	"practice_journal_id" bigint NOT NULL,
	"curriculum_version_omodule_theme_id" bigint NULL,
	"curriculum_version_omodule_id" bigint NULL,
	"subject_id" bigint NULL,
	"credits" numeric(4,1) NOT NULL,
	"hours" smallint NOT NULL,
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "practice_journal_module_subject"	IS 'praktika päevikuga seotud moodulid/teemad/ained';
/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE "practice_journal_module_subject" ADD CONSTRAINT "PK_practice_journal_module_subject"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_practice_journal_module_curriculum_version_omodule" ON "practice_journal_module_subject" ("curriculum_version_omodule_id" ASC);
CREATE INDEX "IXFK_practice_journal_module_curriculum_version_omodule_theme" ON "practice_journal_module_subject" ("curriculum_version_omodule_theme_id" ASC);
CREATE INDEX "IXFK_practice_journal_module_practice_journal" ON "practice_journal_module_subject" ("practice_journal_id" ASC);
CREATE INDEX "IXFK_practice_journal_module_subject" ON "practice_journal_module_subject" ("subject_id" ASC);

/* Create Foreign Key Constraints */

ALTER TABLE "practice_journal_module_subject" ADD CONSTRAINT "FK_practice_journal_module_curriculum_version_omodule"	FOREIGN KEY ("curriculum_version_omodule_id") REFERENCES "curriculum_version_omodule" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "practice_journal_module_subject" ADD CONSTRAINT "FK_practice_journal_module_curriculum_version_omodule_theme"	FOREIGN KEY ("curriculum_version_omodule_theme_id") REFERENCES "curriculum_version_omodule_theme" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "practice_journal_module_subject" ADD CONSTRAINT "FK_practice_journal_module_practice_journal"	FOREIGN KEY ("practice_journal_id") REFERENCES "practice_journal" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "practice_journal_module_subject" ADD CONSTRAINT "FK_practice_journal_module_subject"	FOREIGN KEY ("subject_id") REFERENCES "subject" ("id") ON DELETE No Action ON UPDATE No Action;


insert into contract_module_subject(contract_id, curriculum_version_omodule_id, curriculum_version_omodule_theme_id, subject_id, inserted, version, inserted_by,credits, hours)
select id, curriculum_version_omodule_id, curriculum_version_omodule_theme_id, subject_id, inserted, 1, inserted_by, credits, hours
from contract;


insert into practice_journal_module_subject(practice_journal_id, curriculum_version_omodule_id, curriculum_version_omodule_theme_id, subject_id, inserted, version, inserted_by,credits, hours)
select id, curriculum_version_omodule_id, curriculum_version_omodule_theme_id, subject_id, inserted, 1, inserted_by, credits, hours
from practice_journal;

alter table contract alter column supervisor_name drop not null;
alter table contract alter column supervisor_email drop not null;

alter table contract add is_practice_hidden boolean;
comment on column contract.is_practice_hidden is 'kas peita päevikutes PR märge';

alter table contract_supervisor alter column supervisor_email drop not null;

insert into classifier (code, value, name_et, main_class_code, inserted, valid, is_vocational, is_higher, version) values
('TEEMAOIGUS_PRHINDAMISVORM', 'PRHINDAMISVORM', 'Praktika hindamisvormid', 'TEEMAOIGUS', now(), true,  true, true, 0);

insert into user_role_default (object_code, permission_code, role_code) values 
('TEEMAOIGUS_PRHINDAMISVORM', 'OIGUS_V', 'ROLL_A');

insert into user_rights(user_id,permission_code,object_code,inserted,version,inserted_by)
select u.id, 'OIGUS_V', 'TEEMAOIGUS_PRHINDAMISVORM', now(), 0, 'admin'
from user_ u where u.role_code in ('ROLL_A');

create trigger contract_module_subject_audit after insert or delete or update on contract_module_subject for each row execute procedure hois_audit();
create trigger contract_supervisor_audit after insert or delete or update on contract_supervisor for each row execute procedure hois_audit();
create trigger enterprise_school_audit after insert or delete or update on enterprise_school for each row execute procedure hois_audit();
create trigger enterprise_school_isced_class_audit after insert or delete or update on enterprise_school_isced_class for each row execute procedure hois_audit();
create trigger enterprise_school_location_audit after insert or delete or update on enterprise_school_location for each row execute procedure hois_audit();
create trigger enterprise_school_person_audit after insert or delete or update on enterprise_school_person for each row execute procedure hois_audit();
create trigger journal_teacher_capacity_audit after insert or delete or update on journal_teacher_capacity for each row execute procedure hois_audit();
create trigger practice_admission_audit after insert or delete or update on practice_admission for each row execute procedure hois_audit();
create trigger practice_admission_student_group_audit after insert or delete or update on practice_admission_student_group for each row execute procedure hois_audit();
create trigger practice_application_audit after insert or delete or update on practice_application for each row execute procedure hois_audit();
create trigger practice_evaluation_audit after insert or delete or update on practice_evaluation for each row execute procedure hois_audit();
create trigger practice_evaluation_criteria_audit after insert or delete or update on practice_evaluation_criteria for each row execute procedure hois_audit();
create trigger practice_journal_module_subject_audit after insert or delete or update on practice_journal_module_subject for each row execute procedure hois_audit();
create trigger scholarship_decision_committee_member_audit after insert or delete or update on scholarship_decision_committee_member for each row execute procedure hois_audit();
create trigger subject_program_audit after insert or delete or update on subject_program for each row execute procedure hois_audit();
create trigger subject_program_study_content_audit after insert or delete or update on subject_program_study_content for each row execute procedure hois_audit();


do $$
DECLARE 
	r record;
	p_id bigint:=0;
	p_reg	varchar(100):='x';
BEGIN
	for r in (select *
			from enterprise e
			join (select count(*) as kokku, reg_code, name
			from enterprise
			where coalesce(reg_code,'x')!='x'
			group by reg_code, name
			having count(*) > 1) x on x.reg_code=e.reg_code and x.name=e.name
			order by e.reg_code,e.id asc)
   loop
			if p_reg!=r.reg_code THEN
				p_id:=r.id;
        p_reg:=r.reg_code;
      else
				update enterprise_school set enterprise_id=p_id where enterprise_id=r.id;
				--raise notice 'X: %, %', p_id, r.id;
      end if;
   end loop;
   for r in (SELECT count(*), enterprise_id, school_id, min(id) as id
							from enterprise_school
							group by enterprise_id, school_id
							having count(*) > 1)
	LOOP
		update enterprise_school_person set enterprise_school_id=r.id where enterprise_school_id in (select id from enterprise_school where id!=r.id and enterprise_id=r.enterprise_id and school_id=r.school_id);
		delete from enterprise_school where id!=r.id and enterprise_id=r.enterprise_id and school_id=r.school_id;
	end loop;
  for r in (select count(*), enterprise_school_id,firstname,lastname, min(id) id
						from enterprise_school_person 
						group by enterprise_school_id,firstname,lastname
						having count(*) > 1
						order by 1 desc
						)
	loop
		delete from enterprise_school_person where enterprise_school_id=r.enterprise_school_id and id!=r.id and firstname=r.firstname and lastname=r.lastname;
	end loop;
end;
$$;


update enterprise set country_code='RIIK_EST' where coalesce(country_code,'x')='x';