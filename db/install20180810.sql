\c hois

alter table school add column final_school_type varchar(100);
comment on column school.final_school_type is 'õppeasutuse tüüp lõpudok jaoks';

alter table curriculum add column final_21 varchar(255);
alter table curriculum add column final_31 varchar(255);
alter table curriculum add column final_33 varchar(255);
alter table curriculum add column final_51 varchar(255);
alter table curriculum add column final_52 varchar(255);
alter table curriculum add column final_61 varchar(255);
alter table school add column final_62 varchar(500);

comment on column curriculum.final_21 is 'akad. õiendi p. 2.1';
comment on column curriculum.final_31 is 'akad. õiendi p. 3.1';
comment on column curriculum.final_33 is 'akad. õiendi p. 3.3';
comment on column curriculum.final_51 is 'akad. õiendi p. 5.1';
comment on column curriculum.final_52 is 'akad. õiendi p. 5.2';
comment on column curriculum.final_61 is 'akad. õiendi p. 6.1';
comment on column school.final_62 is 'akad. õiendi p. 6.2';

alter table "diploma_supplement"
add COLUMN 	"final_21" varchar(255)	 NULL,    -- akad õiendi p. 2.1
add COLUMN 	"curriculum_mer_reg_date" date NULL,    -- Õppekava registreerimine HTMis 
	add COLUMN "school_type" varchar(100)	 NULL,    -- õppeasutuse tüüp
	add COLUMN "final_31" varchar(255)	 NULL,    -- akad õiendi p. 3.1
	add COLUMN "study_period" integer NULL,    -- õppeaeg kuudes
	add COLUMN "final_33" varchar(255)	 NULL,    -- akad õiendi p. 3.3
	add COLUMN "study_load_name_et" varchar(100)	 NULL,    -- õppekoormus e.k.
	add COLUMN "curriculum_completion" varchar(255)	 NULL,    -- õppekava täitmise tingimused kõrg õppes (p 4.2)
	add COLUMN "average_mark" numeric(4,2) NULL,    -- KKH
	add COLUMN "final_51" varchar(255)	 NULL,    -- akad õiendi p. 5.1
	add COLUMN "final_52" varchar(255)	 NULL,    -- akad õiendi p. 5.2
	add COLUMN "final_61" varchar(255)	 NULL,    -- akad õiendi p. 6.1
	add COLUMN "final_62" varchar(255)	 NULL,    -- akad õiendi p. 6.2
	add COLUMN "printed" date NULL    -- trükkimise kp
;

alter TABLE "diploma_supplement_study_result"
add COLUMN "subject_code" varchar(20)	 NULL,    -- õppeaine kood
add COLUMN 	"is_final_thesis" boolean NULL    -- kas tegemist on lõputööga
;

/* Create Table Comments, Sequences for Autonumber Columns */


COMMENT ON COLUMN "diploma_supplement"."final_21"	IS 'akad õiendi p. 2.1';
COMMENT ON COLUMN "diploma_supplement"."curriculum_mer_reg_date"	IS 'Õppekava registreerimine HTMis ';
COMMENT ON COLUMN "diploma_supplement"."school_type"	IS 'õppeasutuse tüüp';
COMMENT ON COLUMN "diploma_supplement"."final_31"	IS 'akad õiendi p. 3.1';
COMMENT ON COLUMN "diploma_supplement"."study_period"	IS 'õppeaeg kuudes';
COMMENT ON COLUMN "diploma_supplement"."final_33"	IS 'akad õiendi p. 3.3';
COMMENT ON COLUMN "diploma_supplement"."study_load_name_et"	IS 'õppekoormus e.k.';
COMMENT ON COLUMN "diploma_supplement"."curriculum_completion"	IS 'õppekava täitmise tingimused kõrg õppes (p 4.2)';
COMMENT ON COLUMN "diploma_supplement"."average_mark"	IS 'KKH';
COMMENT ON COLUMN "diploma_supplement"."final_51"	IS 'akad õiendi p. 5.1';
COMMENT ON COLUMN "diploma_supplement"."final_52"	IS 'akad õiendi p. 5.2';
COMMENT ON COLUMN "diploma_supplement"."final_61"	IS 'akad õiendi p. 6.1';
COMMENT ON COLUMN "diploma_supplement"."final_62"	IS 'akad õiendi p. 6.2';
COMMENT ON COLUMN "diploma_supplement"."printed"	IS 'trükkimise kp';
COMMENT ON COLUMN "diploma_supplement_study_result"."subject_code"	IS 'õppeaine kood';
COMMENT ON COLUMN "diploma_supplement_study_result"."is_final_thesis"	IS 'kas tegemist on lõputööga';

alter table curriculum add column final_en_31 varchar(255);
alter table curriculum add column final_en_33 varchar(255);
alter table curriculum add column final_en_51 varchar(255);
alter table curriculum add column final_en_52 varchar(255);
alter table curriculum add column final_en_61 varchar(255);
alter table school add column final_en_62 varchar(500);

comment on column curriculum.final_en_31 is 'akad. õiendi p. 3.1 i.k.';
comment on column curriculum.final_en_33 is 'akad. õiendi p. 3.3 i.k.';
comment on column curriculum.final_en_51 is 'akad. õiendi p. 5.1 i.k.';
comment on column curriculum.final_en_52 is 'akad. õiendi p. 5.2 i.k.';
comment on column curriculum.final_en_61 is 'akad. õiendi p. 6.1 i.k.';
comment on column school.final_en_62 is 'akad. õiendi p. 6.2 i.k.';

alter table school add column final_school_type_en varchar(100);
comment on column school.final_school_type_en is 'õppeasutuse tüüp lõpudok jaoks i.k.';

alter table "diploma_supplement"
add COLUMN 	"final_en_21" varchar(255)	 NULL,    -- akad õiendi p. 2.1
	add COLUMN "school_type_en" varchar(100)	 NULL,    -- õppeasutuse tüüp
	add COLUMN "final_en_31" varchar(255)	 NULL,    -- akad õiendi p. 3.1
	add COLUMN "final_en_33" varchar(255)	 NULL,    -- akad õiendi p. 3.3
	add COLUMN "study_load_name_en" varchar(100)	 NULL,    -- õppekoormus e.k.
	add COLUMN "curriculum_completion_en" varchar(255)	 NULL,    -- õppekava täitmise tingimused kõrg õppes (p 4.2)
	add COLUMN "final_en_51" varchar(255)	 NULL,    -- akad õiendi p. 5.1
	add COLUMN "final_en_52" varchar(255)	 NULL,    -- akad õiendi p. 5.2
	add COLUMN "final_en_61" varchar(255)	 NULL,    -- akad õiendi p. 6.1
	add COLUMN "final_en_62" varchar(255)	 NULL,    -- akad õiendi p. 6.2
add COLUMN "outcomes_en" varchar(20000)	 NULL, 
add COLUMN "signer2_name" varchar(100)	,
add COLUMN "signer2_position" varchar(255)
;

alter table "diploma_supplement"
add COLUMN "signer1_position_en" varchar(255),
add COLUMN "signer2_position_en" varchar(255);


/* Create Table Comments, Sequences for Autonumber Columns */


COMMENT ON COLUMN "diploma_supplement"."final_en_21"	IS 'akad õiendi p. 2.1 i.k.';
COMMENT ON COLUMN "diploma_supplement"."school_type_en"	IS 'õppeasutuse tüüp i.k.';
COMMENT ON COLUMN "diploma_supplement"."final_en_31"	IS 'akad õiendi p. 3.1 i.k.';
COMMENT ON COLUMN "diploma_supplement"."final_en_33"	IS 'akad õiendi p. 3.3 i.k.';
COMMENT ON COLUMN "diploma_supplement"."study_load_name_en"	IS 'õppekoormus i.k.';
COMMENT ON COLUMN "diploma_supplement"."curriculum_completion_en"	IS 'õppekava täitmise tingimused kõrg õppes (p 4.2) i.k.';
COMMENT ON COLUMN "diploma_supplement"."final_en_51"	IS 'akad õiendi p. 5.1 i.k.';
COMMENT ON COLUMN "diploma_supplement"."final_en_52"	IS 'akad õiendi p. 5.2 i.k.';
COMMENT ON COLUMN "diploma_supplement"."final_en_61"	IS 'akad õiendi p. 6.1 i.k.';
COMMENT ON COLUMN "diploma_supplement"."final_en_62"	IS 'akad õiendi p. 6.2 i.k.';
COMMENT ON COLUMN "diploma_supplement"."outcomes_en"	IS 'õppekava õpiväljundid i.k.';
COMMENT ON COLUMN "diploma_supplement"."signer2_name"	IS '2. allkirjastaja nimi i.k.';
COMMENT ON COLUMN "diploma_supplement"."signer2_position"	IS '2. allkirjastaja ametikoht e.k.';
COMMENT ON COLUMN "diploma_supplement"."signer1_position_en"	IS '2. allkirjastaja ametikoht i.k.';
COMMENT ON COLUMN "diploma_supplement"."signer2_position_en"	IS '2. allkirjastaja ametikoht i.k.';

alter table "final_doc_signer"
add COLUMN 	"position_en" varchar(255)	 NULL;

COMMENT ON COLUMN "final_doc_signer"."position_en"	IS 'ametikoht i.k.';

alter table "diploma_supplement" add COLUMN 	"status_en_code" varchar(100)	 NULL;
COMMENT ON COLUMN "diploma_supplement"."status_en_code"	IS 'ingliskeelse akad õiendi staatus, viide klassifikaatorile LOPUDOK_STAATUS';
create index IXFK_diploma_supplement_classifier_2 on diploma_supplement (status_en_code);
alter table diploma_supplement add CONSTRAINT FK_diploma_supplement_classifier_2 foreign key (status_en_code) references classifier(code)  ON DELETE No Action ON UPDATE No Action;

alter table diploma_supplement add column printed_en date;
COMMENT ON COLUMN "diploma_supplement"."printed_en"	IS 'ingliskeelse akad. õiendi trükkimise kp';

INSERT INTO classifier ("code", "value", "value2", "name_et", "name_en", "name_ru", "parent_class_code", "main_class_code", "inserted", "changed", "valid", "description", "valid_from", "valid_thru", "extraval1", "extraval2", "ehis_value", "is_vocational", "is_higher", "version", "inserted_by", "changed_by") VALUES ('LOPUBLANKETT_DS', 'DS', NULL, 'DS - ingliskeelne akadeemiline õiend', NULL, NULL, NULL, 'LOPUBLANKETT', '2018-05-08 22:10:45.85636', NULL, 't', NULL, NULL, NULL, NULL, NULL, NULL, 't', 't', '0', NULL, NULL);
update form set type_code='LOPUBLANKETT_DS' where type_code='LOPUBLANKETT_DE';
delete from classifier where code='LOPUBLANKETT_DE';

insert into user_role_default (object_code, permission_code, role_code) values 
('TEEMAOIGUS_ANDMEVAHETUS_SAIS', 'OIGUS_V', 'ROLL_P'),
('TEEMAOIGUS_ANDMEVAHETUS_KUTSEREGISTER', 'OIGUS_V', 'ROLL_P');

insert into user_rights(user_id,permission_code,object_code,inserted,version,inserted_by)
select u.id, 'OIGUS_V', 'TEEMAOIGUS_ANDMEVAHETUS_SAIS', now(), 0, 'admin'
from user_ u where u.role_code in ('ROLL_P');

insert into user_rights(user_id,permission_code,object_code,inserted,version,inserted_by)
select u.id, 'OIGUS_V', 'TEEMAOIGUS_ANDMEVAHETUS_KUTSEREGISTER', now(), 0, 'admin'
from user_ u where u.role_code in ('ROLL_P');


