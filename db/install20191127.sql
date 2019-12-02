\c hois


ALTER TABLE user_sessions ALTER COLUMN inserted_by TYPE varchar(100);


insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version)
values('FOTOLISA','FOTOLISA','Foto lisamise õigus',null,now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version)
values('FOTOLISA_KOIK','KOIK','kõik õppurid saavad lisada','FOTOLISA',now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version)
values('FOTOLISA_TAIS','TAIS','ainult täisealised','FOTOLISA',now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version)
values('FOTOLISA_EI','EI','õppurid ei saa fotot lisada','FOTOLISA',now(),true,true,true,0);

alter table school add column student_photo_add_code varchar(100);
create index IXFK_school_classifier_02 on school(student_photo_add_code);
alter table school add constraint FK_school_classifier_02 foreign key(student_photo_add_code) references classifier(code);
comment on column school.student_photo_add_code is 'kas õppur saab ise fotot lisada, viide klassifikaatorile FOTOLISA';
update school set student_photo_add_code='FOTOLISA_EI';
alter table school alter column student_photo_add_code set not null;

insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version)
values('TEEMAOIGUS_PERSYNDMUS','PERSYNDMUS','Personaalsed sündmused','TEEMAOIGUS',now(),true,true,true,0);


alter table timetable_event add column is_personal boolean;
alter table timetable_event add column person_id bigint;
comment on column timetable_event.is_personal is 'kas tegemist personaalse/isikliku sündmusega';
comment on column timetable_event.person_id is 'viide isikule personaalse sündmuse puhul';
create index IXFK_timetable_event_person on timetable_event(person_id);
alter table timetable_event add constraint FK_timetable_event_person foreign key(person_id) references person(id);

insert into user_role_default(object_code,permission_code,role_code) values('TEEMAOIGUS_PERSYNDMUS','OIGUS_V','ROLL_A');

CREATE TABLE "user_school_role"
(
	"id" bigserial NOT NULL ,
	"school_id" bigint NOT NULL,
	"name_en" varchar(50)	 NULL,
	"name_et" varchar(50)	 NOT NULL,    -- kasutajarolli nimetus
	"teacher_occupation_id" bigint NULL,    -- õpetaja ametikoha õigus
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "user_school_role"	IS 'koolide poolt seadistatud kasutajarollid ';
COMMENT ON COLUMN "user_school_role"."name_et"	IS 'kasutajarolli nimetus';
COMMENT ON COLUMN "user_school_role"."teacher_occupation_id"	IS 'õpetaja ametikoha õigus';


/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE "user_school_role" ADD CONSTRAINT "PK_user_school_role"	PRIMARY KEY ("id");
ALTER TABLE "user_school_role" ADD CONSTRAINT "UQ_user_school_role" UNIQUE ("school_id","name_en");

CREATE INDEX "IXFK_user_school_role_school" ON "user_school_role" ("school_id" ASC);
CREATE INDEX "IXFK_user_school_role_teacher_occupation" ON "user_school_role" ("teacher_occupation_id" ASC);

/* Create Foreign Key Constraints */

ALTER TABLE "user_school_role" ADD CONSTRAINT "FK_user_school_role_school"	FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "user_school_role" ADD CONSTRAINT "FK_user_school_role_teacher_occupation"	FOREIGN KEY ("teacher_occupation_id") REFERENCES "public"."teacher_occupation" ("id") ON DELETE No Action ON UPDATE No Action;

alter table user_ add column user_school_role_id bigint;
COMMENT ON COLUMN "user_"."user_school_role_id"	IS 'viide kitsamale kasutajarollile';

CREATE INDEX "IXFK_user__user_school_role" ON "user_" ("user_school_role_id" ASC);
ALTER TABLE "user_" ADD CONSTRAINT "FK_user__user_school_role"	FOREIGN KEY ("user_school_role_id") REFERENCES "public"."user_school_role" ("id") ON DELETE No Action ON UPDATE No Action;



CREATE TABLE "user_school_role_rights"
(
	"id" bigserial NOT NULL,
	"user_school_role_id" bigint NOT NULL,    -- viide kasutajarollile
	"object_code" varchar(100)	 NOT NULL,    -- viide klassifikaatorile TEEMAOIGUS
	"permission_code" varchar(100)	 NOT NULL,    -- viide klassifikaatorile OIGUS
	"inserted" timestamp without time zone NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "user_school_role_rights"	IS 'kasutajarollide õigused';
COMMENT ON COLUMN "user_school_role_rights"."user_school_role_id"	IS 'viide kasutajarollile';
COMMENT ON COLUMN "user_school_role_rights"."object_code"	IS 'viide klassifikaatorile TEEMAOIGUS';
COMMENT ON COLUMN "user_school_role_rights"."permission_code"	IS 'viide klassifikaatorile OIGUS';

/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE "user_school_role_rights" ADD CONSTRAINT "PK_user_school_role_rights"	PRIMARY KEY ("id");
ALTER TABLE "user_school_role_rights" ADD CONSTRAINT "UQ_user_school_role_rights" UNIQUE ("user_school_role_id","permission_code","object_code");
CREATE INDEX "IXFK_user_school_role_rights_classifier" ON "user_school_role_rights" ("permission_code" ASC);
CREATE INDEX "IXFK_user_school_role_rights_classifier_02" ON "user_school_role_rights" ("object_code" ASC);
CREATE INDEX "IXFK_user_school_role_rights_user_school_role" ON "user_school_role_rights" ("user_school_role_id" ASC);

/* Create Foreign Key Constraints */

ALTER TABLE "user_school_role_rights" ADD CONSTRAINT "FK_user_school_role_rights_classifier"	FOREIGN KEY ("permission_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "user_school_role_rights" ADD CONSTRAINT "FK_user_school_role_rights_classifier_02"	FOREIGN KEY ("object_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "user_school_role_rights" ADD CONSTRAINT "FK_user_school_role_rights_user_school_role"	FOREIGN KEY ("user_school_role_id") REFERENCES "user_school_role" ("id") ON DELETE No Action ON UPDATE No Action;

insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('ROLL_J','J','Juhtivõpetaja','ROLL',now(),true,true,true,0);



CREATE TABLE "user_curriculum"
(
	"id" bigserial NOT NULL,
	"user_id" bigint NOT NULL,
	"curriculum_id" bigint NOT NULL,
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "user_curriculum"	IS 'kasutaja õppekavad (juhtiv õpetaja puhul)';

/* Create Primary Keys, Indexes, Uniques, Checks */
ALTER TABLE "user_curriculum" ADD CONSTRAINT "PK_user_curriculum"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_user_curriculum_curriculum" ON "user_curriculum" ("curriculum_id" ASC);
CREATE INDEX "IXFK_user_curriculum_user_" ON "user_curriculum" ("user_id" ASC);

/* Create Foreign Key Constraints */

ALTER TABLE "user_curriculum" ADD CONSTRAINT "FK_user_curriculum_curriculum"	FOREIGN KEY ("curriculum_id") REFERENCES "public"."curriculum" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "user_curriculum" ADD CONSTRAINT "FK_user_curriculum_user_"	FOREIGN KEY ("user_id") REFERENCES "public"."user_" ("id") ON DELETE No Action ON UPDATE No Action;
insert into user_role_default (object_code, permission_code, role_code) values 
('TEEMAOIGUS_KOORM', 'OIGUS_V', 'ROLL_O') ;

alter table poll_target add column target_count integer;
comment on column poll_target.target_count is 'kui mitmele inimesele küsitlus saadeti';

alter table student_higher_result alter column grade type varchar(25);

INSERT INTO classifier (code, value, name_et, main_class_code, inserted,  valid, is_vocational, is_higher, version) 
VALUES ('KASKKIRI_KYLALIS', 'KYLALIS', 'Külalisõpilaseks vormistamine', 'KASKKIRI', current_timestamp(3), 't','t', 't', '0');


INSERT INTO classifier (code, value, name_et, main_class_code, inserted,  valid, is_vocational, is_higher, version) 
VALUES ('OPPUR', 'OPPUR', 'Õppija liik', null, current_timestamp(3), 't','t', 't', '0');

INSERT INTO classifier (code, value, name_et, main_class_code, inserted,  valid, is_vocational, is_higher, version) 
VALUES ('OPPUR_T', 'T', 'Tavaline õppija', 'OPPUR', current_timestamp(3), 't','t', 't', '0'); 

INSERT INTO classifier (code, value, name_et, main_class_code, inserted,  valid, is_vocational, is_higher, version) 
VALUES ('OPPUR_K', 'K', 'Külalisõpilane', 'OPPUR', current_timestamp(3), 't','t', 't', '0'); 

INSERT INTO classifier (code, value, name_et, main_class_code, inserted,  valid, is_vocational, is_higher, version) 
VALUES ('OPPUR_O', 'O', 'Eksternõppija', 'OPPUR', current_timestamp(3), 't','t', 't', '0'); 

alter table student add column type_code varchar(100) not null default 'OPPUR_T';
comment on column student.type_code is 'õppuri liik, viide klassifikaatorile OPPUR, vaikimisi tavaline õppur';
create index IXFK_student_classifier_11 on student(type_code);
alter table student add constraint FK_student_classifier_10 foreign key(type_code) references classifier(code);

INSERT INTO classifier (code, value, name_et, main_class_code, inserted,  valid, is_vocational, is_higher, version) 
VALUES ('EHIS_KODU_OPPEASTE', 'EHIS_KODU_OPPEASTE', 'Külalisõpilase koduõppeasutuse õppeaste', null, current_timestamp(3), 't','t', 't', '0');

INSERT INTO classifier (code, value, name_et, main_class_code, inserted,  valid, is_vocational, is_higher, version, ehis_value) 
VALUES ('EHIS_KODU_OPPEASTE_1', '1', 'Kõrghariduse esimene aste', 'EHIS_KODU_OPPEASTE', current_timestamp(3), 't','t', 't', '0','LY_OP_OPPEASTE_1'); 

INSERT INTO classifier (code, value, name_et, main_class_code, inserted,  valid, is_vocational, is_higher, version, ehis_value) 
VALUES ('EHIS_KODU_OPPEASTE_2', '2', 'Kõrghariduse teine aste', 'EHIS_KODU_OPPEASTE', current_timestamp(3), 't','t', 't', '0','LY_OP_OPPEASTE_2'); 

INSERT INTO classifier (code, value, name_et, main_class_code, inserted,  valid, is_vocational, is_higher, version, ehis_value) 
VALUES ('EHIS_KODU_OPPEASTE_3', '3', 'Kõrghariduse kolmas aste', 'EHIS_KODU_OPPEASTE', current_timestamp(3), 't','t', 't', '0','LY_OP_OPPEASTE_3'); 

alter table directive_student add column apel_school_id bigint;
comment on column directive_student.apel_school_id is 'külalisõpilase puhul viide VÕTA koolile';
create index IXFK_directive_student_apel_school on directive_student(apel_school_id);
alter table directive_student add constraint FK_directive_student_apel_school foreign key(apel_school_id) references apel_school(id);

alter table student alter column curriculum_version_id drop not null;
alter table student_history alter column curriculum_version_id drop not null;

alter table student_group alter column curriculum_id drop not null;
alter table student_group alter column study_form_code drop not null;
alter table student_group add column is_guest boolean;

alter table school add is_student_terms boolean;
comment on column school.is_student_terms is 'kas tuleks nõustuda kasutustingimstega';
alter table school add contract_text text;
comment on column school.contract_text is 'kasutustingimuste tekst';

alter table student add is_contract_agreed boolean default false not null;
alter table student add contract_agreed timestamp;
alter table student add contract_text text;
comment on column student.is_contract_agreed is 'kas õppur on kasutustingimstega nõus';
comment on column student.contract_agreed is 'nõusoleku kinnitamise kuupäev';
comment on column student.contract_text is 'nõusoleku tekst';

insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version)
values('TEEMAOIGUS_TINGIMUS','TINGIMUS','Kasutustingimused','TEEMAOIGUS',now(),true,true,true,0);

insert into user_role_default(object_code,permission_code,role_code) values('TEEMAOIGUS_TINGIMUS','OIGUS_V','ROLL_A');

update classifier set name_et=trim(name_et),name_en=replace(trim(name_en),chr(160),'') where main_class_code='RIIK';
update classifier set name_et='Juhtivõpetaja' where code='ROLL_J';

do $$
declare
	p_school_id bigint:=-1;
  p_last_dt date;
	r record;
begin
	for r in (select distinct lg.valid_from, lg.valid_thru, lg.id,bb.school_id
						from lesson_time_building_group lg
								join lesson_time_building lb on lb.lesson_time_building_group_id=lg.id
								join building bb on lb.building_id=bb.id
						order by 4, 1 desc, 2 desc, 3 desc)
	LOOP
		if p_school_id != r.school_id then
			p_school_id:=r.school_id;
			p_last_dt:=r.valid_from;
			continue;
		end if;
		update lesson_time_building_group set valid_thru=(p_last_dt-interval '1 day') where id=r.id and valid_thru is null;
--			
		--raise notice '% %',p_last_dt, (p_last_dt-interval '1 day');
		p_last_dt:=r.valid_from;
	end loop;
end;
$$;

update classifier set name_en='EU Lifelong Learning Programme' where code='VALISKOOL_PROGRAMM_1';
update classifier set name_en='Erasmus+ program' where code='VALISKOOL_PROGRAMM_1_1';
update classifier set name_en='European research programmes' where code='VALISKOOL_PROGRAMM_2';
update classifier set name_en='International agreements' where code='VALISKOOL_PROGRAMM_3';
update classifier set name_en='Kristjan Jaak''s programme' where code='VALISKOOL_PROGRAMM_4';
update classifier set name_en='Nord Plus programme' where code='VALISKOOL_PROGRAMM_5';
update classifier set name_en='Compatriots programme' where code='VALISKOOL_PROGRAMM_6';
update classifier set name_en='Structural funds 2007-2015' where code='VALISKOOL_PROGRAMM_7';
update classifier set name_en='Structural funds 2014-2020' where code='VALISKOOL_PROGRAMM_8';
update classifier set name_en='Other scholarships' where code='VALISKOOL_PROGRAMM_9';
update classifier set name_en='No scholarship' where code='VALISKOOL_PROGRAMM_10';
update classifier set name_en='Foreign state grant' where code='VALISKOOL_PROGRAMM_11';

insert into user_role_default(object_code, permission_code, role_code) values
/* Akadeemiline kalender */
('TEEMAOIGUS_AKADKALENDER', 'OIGUS_V', 'ROLL_J'),
('TEEMAOIGUS_AKADKALENDER', 'OIGUS_M', 'ROLL_J'),
/* Avaldused */
('TEEMAOIGUS_AVALDUS', 'OIGUS_V', 'ROLL_J'),
/* Baasmoodulid */
('TEEMAOIGUS_BAASMOODUL', 'OIGUS_V', 'ROLL_J'),
('TEEMAOIGUS_BAASMOODUL', 'OIGUS_M', 'ROLL_J'),
/* EHIS andmevahetus */
('TEEMAOIGUS_ANDMEVAHETUS_EHIS', 'OIGUS_V', 'ROLL_J'),
/* EKIS andmevahetus */
('TEEMAOIGUS_ANDMEVAHETUS_EKIS', 'OIGUS_V', 'ROLL_J'),
/* Eksamid */
('TEEMAOIGUS_EKSAM', 'OIGUS_V', 'ROLL_J'),
/* Individ. õppekava statistika */
('TEEMAOIGUS_INDIVID', 'OIGUS_V', 'ROLL_J'),
/* Komisjonid */
('TEEMAOIGUS_KOMISJON', 'OIGUS_V', 'ROLL_J'),
/* Kutseregister andmevahetus */
('TEEMAOIGUS_ANDMEVAHETUS_KUTSEREGISTER', 'OIGUS_V', 'ROLL_J'),
/* Käskkirjad */
('TEEMAOIGUS_KASKKIRI', 'OIGUS_V', 'ROLL_J'),
/* Küsitlused */
('TEEMAOIGUS_KYSITLUS', 'OIGUS_V', 'ROLL_J'),
('TEEMAOIGUS_KYSITLUS', 'OIGUS_M', 'ROLL_J'),
/* Lepingud */
('TEEMAOIGUS_LEPING', 'OIGUS_V', 'ROLL_J'),
('TEEMAOIGUS_LEPING', 'OIGUS_M', 'ROLL_J'),
/* Lõputöö moodulite protokollid */
('TEEMAOIGUS_LOPMOODULPROTOKOLL', 'OIGUS_V', 'ROLL_J'),
/* Lõputöö protokollid */
('TEEMAOIGUS_LOPPROTOKOLL', 'OIGUS_V', 'ROLL_J'),
/* Lõputöö teemad */
('TEEMAOIGUS_LOPTEEMA', 'OIGUS_V', 'ROLL_J'),
/* Moodulite protokollid */
('TEEMAOIGUS_MOODULPROTOKOLL', 'OIGUS_V', 'ROLL_J'),
/* Personaalne sündmus */
('TEEMAOIGUS_PERSYNDMUS', 'OIGUS_V', 'ROLL_J'),
('TEEMAOIGUS_PERSYNDMUS', 'OIGUS_M', 'ROLL_J'),
/* Praktika avaldused */
('TEEMAOIGUS_PRAKTIKAAVALDUS', 'OIGUS_V', 'ROLL_J'),
('TEEMAOIGUS_PRAKTIKAAVALDUS', 'OIGUS_M', 'ROLL_J'),
/* Praktika ettevõtted */
('TEEMAOIGUS_ETTEVOTE', 'OIGUS_V', 'ROLL_J'),
('TEEMAOIGUS_ETTEVOTE', 'OIGUS_M', 'ROLL_J'),
/* Praktika hindamisvormid */
('TEEMAOIGUS_PRHINDAMISVORM', 'OIGUS_V', 'ROLL_J'),
('TEEMAOIGUS_PRHINDAMISVORM', 'OIGUS_M', 'ROLL_J'),
/* Praktika päevikud */
('TEEMAOIGUS_PRAKTIKAPAEVIK', 'OIGUS_V', 'ROLL_J'),
/* Praktika statistika */
('TEEMAOIGUS_PRSTATISTIKA', 'OIGUS_V', 'ROLL_J'),
/* Protokollid */
('TEEMAOIGUS_PROTOKOLL', 'OIGUS_V', 'ROLL_J'),
/* Puudumistõendid */
('TEEMAOIGUS_PUUDUMINE', 'OIGUS_V', 'ROLL_J'),
/* Päeviku ülevaatamine */
('TEEMAOIGUS_PAEVIKYLE', 'OIGUS_V', 'ROLL_J'),
/* Päevikud */
('TEEMAOIGUS_PAEVIK', 'OIGUS_V', 'ROLL_J'),
/* Päringud */
('TEEMAOIGUS_PARING', 'OIGUS_V', 'ROLL_J'),
('TEEMAOIGUS_PARING', 'OIGUS_M', 'ROLL_J'),
/* Riiklikud õppekavad */
('TEEMAOIGUS_RIIKLIKOPPEKAVA', 'OIGUS_V', 'ROLL_J'),
/* RR (rahvastikuregister) päring */
('TEEMAOIGUS_RR', 'OIGUS_V', 'ROLL_J'),
/* Rühmajuhataja aruanne */
('TEEMAOIGUS_RYHMAJUHATAJA', 'OIGUS_V', 'ROLL_J'),
('TEEMAOIGUS_RYHMAJUHATAJA', 'OIGUS_M', 'ROLL_J'),
/* Stipendiumid ja toetused */
('TEEMAOIGUS_STIPTOETUS', 'OIGUS_V', 'ROLL_J'),
/* Sündmused */
('TEEMAOIGUS_SYNDMUS', 'OIGUS_V', 'ROLL_J'),
('TEEMAOIGUS_SYNDMUS', 'OIGUS_M', 'ROLL_J'),
/* Teated */
('TEEMAOIGUS_TEADE', 'OIGUS_V', 'ROLL_J'),
('TEEMAOIGUS_TEADE', 'OIGUS_M', 'ROLL_J'),
/* Tunnijaotusplaan */
('TEEMAOIGUS_TUNNIJAOTUSPLAAN', 'OIGUS_V', 'ROLL_J'),
/* Tõendid */
('TEEMAOIGUS_TOEND', 'OIGUS_V', 'ROLL_J'),
/* VÕTA */
('TEEMAOIGUS_VOTA', 'OIGUS_V', 'ROLL_J'),
/* VÕTA komisjon */
('TEEMAOIGUS_VOTAKOM', 'OIGUS_V', 'ROLL_J'),
/* Õpilaskodud */
('TEEMAOIGUS_OPILASKODU', 'OIGUS_V', 'ROLL_J'),
/* Õpilaspiletite haldamine */
('TEEMAOIGUS_PILET', 'OIGUS_V', 'ROLL_J'),
/* Õppekavad */
('TEEMAOIGUS_OPPEKAVA', 'OIGUS_V', 'ROLL_J'),
('TEEMAOIGUS_OPPEKAVA', 'OIGUS_M', 'ROLL_J'),
('TEEMAOIGUS_OPPEKAVA', 'OIGUS_K', 'ROLL_J'),
/* Õppematerjalid */
('TEEMAOIGUS_OPPEMATERJAL', 'OIGUS_V', 'ROLL_J'),
('TEEMAOIGUS_OPPEMATERJAL', 'OIGUS_M', 'ROLL_J'),
/* Õppeperioodid */
('TEEMAOIGUS_OPPEPERIOOD', 'OIGUS_V', 'ROLL_J'),
('TEEMAOIGUS_OPPEPERIOOD', 'OIGUS_M', 'ROLL_J'),
/* Õpperühmad */
('TEEMAOIGUS_OPPERYHM', 'OIGUS_V', 'ROLL_J'),
/* Õppetöögraafik */
('TEEMAOIGUS_OPPETOOGRAAFIK', 'OIGUS_V', 'ROLL_J'),
('TEEMAOIGUS_OPPETOOGRAAFIK', 'OIGUS_M', 'ROLL_J'),
/* Õppurid */
('TEEMAOIGUS_OPPUR', 'OIGUS_V', 'ROLL_J'),
('TEEMAOIGUS_OPPUR', 'OIGUS_M', 'ROLL_J'),
/* Üldteated */
('TEEMAOIGUS_YLDTEADE', 'OIGUS_V', 'ROLL_J'),
('TEEMAOIGUS_YLDTEADE', 'OIGUS_M', 'ROLL_J');


/* Õiguste klassifikaatorite uuendused */
update classifier set is_higher = true, is_vocational = true where code = 'TEEMAOIGUS_VOTA';
update classifier set is_higher = true, is_vocational = true where code = 'TEEMAOIGUS_ANDMEVAHETUS_KUTSEREGISTER';

update classifier set name_en='Very good' where code='KUTSEHINDAMINE_5';
update classifier set name_en='Good' where code='KUTSEHINDAMINE_4';
update classifier set name_en='Satisfactory' where code='KUTSEHINDAMINE_3';
update classifier set name_en='Insufficient' where code='KUTSEHINDAMINE_2';
update classifier set name_en='Insufficient' where code='KUTSEHINDAMINE_1';
update classifier set name_en='Overdue' where code='KUTSEHINDAMINE_X';
update classifier set name_en='Passed' where code='KUTSEHINDAMINE_A';
update classifier set name_en='Failed' where code='KUTSEHINDAMINE_MA';

update classifier set name_en='Excellent' where code='KORGHINDAMINE_5';
update classifier set name_en='Very good' where code='KORGHINDAMINE_4';
update classifier set name_en='Good' where code='KORGHINDAMINE_3';
update classifier set name_en='Satisfactory' where code='KORGHINDAMINE_2';
update classifier set name_en='Sufficient' where code='KORGHINDAMINE_1';
update classifier set name_en='Failed' where code='KORGHINDAMINE_0';

update classifier set name_en='Final examination' where code='KUTSEMOODUL_L';
update classifier set name_en='Basic studies' where code='KUTSEMOODUL_P';
update classifier set name_en='General studies' where code='KUTSEMOODUL_Y';
update classifier set name_en='Elective studies' where code='KUTSEMOODUL_V';

alter table curriculum alter column final_21 set data type varchar(4000);
alter table curriculum alter column final_31 set data type varchar(4000);
alter table curriculum alter column final_33 set data type varchar(4000);
alter table curriculum alter column final_51 set data type varchar(4000);
alter table curriculum alter column final_52 set data type varchar(4000);
alter table curriculum alter column final_61 set data type varchar(4000);
alter table curriculum alter column final_en_31 set data type varchar(4000);
alter table curriculum alter column final_en_33 set data type varchar(4000);
alter table curriculum alter column final_en_51 set data type varchar(4000);
alter table curriculum alter column final_en_52 set data type varchar(4000);
alter table curriculum alter column final_en_61 set data type varchar(4000);

alter table diploma_supplement alter column final_21 set data type varchar(4000);
alter table diploma_supplement alter column final_31 set data type varchar(4000);
alter table diploma_supplement alter column final_33 set data type varchar(4000);
alter table diploma_supplement alter column final_51 set data type varchar(4000);
alter table diploma_supplement alter column final_52 set data type varchar(4000);
alter table diploma_supplement alter column final_61 set data type varchar(4000);
alter table diploma_supplement alter column final_en_21 set data type varchar(4000);
alter table diploma_supplement alter column final_en_31 set data type varchar(4000);
alter table diploma_supplement alter column final_en_33 set data type varchar(4000);
alter table diploma_supplement alter column final_en_51 set data type varchar(4000);
alter table diploma_supplement alter column final_en_52 set data type varchar(4000);
alter table diploma_supplement alter column final_en_61 set data type varchar(4000);

update classifier set is_vocational=true where code='AVALDUS_LIIK_VALIS';
alter table application add column apel_school_id bigint;
create index IXFK_application_apel_school on apel_school(id);
alter table application add constraint FK_application_apel_school foreign key(apel_school_id) references apel_school(id);
comment on column application.apel_school_id is 'viide VÕTA koolile välisõpingute puhul';

alter table application_planned_subject_equivalent add column curriculum_version_omodule_id bigint;
create index IXFK_application_planned_subject_equivalent_curriculum_version_omodule on application_planned_subject_equivalent(curriculum_version_omodule_id);
alter table application_planned_subject_equivalent add constraint FK_application_planned_subject_equivalent_curriculum_version_omodule foreign key(curriculum_version_omodule_id) references curriculum_version_omodule(id);
comment on column application_planned_subject_equivalent.curriculum_version_omodule_id is 'viide asendatavale moodulile';

alter table application_planned_subject_equivalent add column curriculum_version_omodule_theme_id bigint;
create index IXFK_application_planned_subject_equivalent_7 on application_planned_subject_equivalent(curriculum_version_omodule_theme_id);
alter table application_planned_subject_equivalent add constraint FK_application_planned_subject_equivalent_7 foreign key(curriculum_version_omodule_theme_id) references curriculum_version_omodule_theme(id);
comment on column application_planned_subject_equivalent.curriculum_version_omodule_theme_id is 'viide asendatavale teemale';

alter table application_planned_subject_equivalent alter column subject_id drop not null;

alter table application_planned_subject add constraint FK_application_planned_subject_application foreign key(application_id) references application(id);
INSERT INTO classifier (code, value, name_et, main_class_code, inserted,  valid, is_vocational, is_higher, version) 
VALUES ('KASKKIRI_VALISKATK', 'VALISKATK', 'Välisõpingute katkestamine/varem lõpetamine', 'KASKKIRI', current_timestamp(3), 't','t', 't', '0');

CREATE OR REPLACE FUNCTION public.ins_upd_del_result()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
declare
    u_count integer;
		b_count integer;
		r record;
		x integer;
		p_id bigint;
	st_id bigint;
begin
	if tg_op in ('DELETE') then
		st_id:=old.student_id;
	else
		st_id:=new.student_id;
	end if;
	if tg_op in ('INSERT','UPDATE') and NEW.id is not null and COALESCE(NEW.grade,'x')='x' or tg_op in ('DELETE') THEN
		if tg_op in ('INSERT','UPDATE') THEN	
			delete from student_higher_result_module where student_higher_result_id in (select id from student_higher_result where protocol_student_id=NEW.id);
			delete from student_higher_result where protocol_student_id=NEW.id;
			delete from student_vocational_result where protocol_student_id=NEW.id;
		ELSE
			delete from student_higher_result_module where student_higher_result_id in (select id from student_higher_result where protocol_student_id=old.id);
			delete from student_higher_result where protocol_student_id=old.id;
			delete from student_vocational_result where protocol_student_id=old.id;
		end if;
		for r in (select distinct first_value(id)over(partition by subject_id order by case when coalesce(apel_application_record_id,0)=0 then 1 else 0 end, grade_date desc nulls last) as id, subject_id 
								from student_higher_result 
								where student_id=st_id)
		loop
			update student_higher_result ss 
				set is_active=case when ss.id=r.id then true else false end
			where ss.student_id=st_id and r.subject_id=ss.subject_id;
		end LOOP;
		x:=upd_student_curriculum_completion(st_id);
	elsif NEW.id is not null then
		for r in (SELECT coalesce(ph.final_subject_id, sp.subject_id) as subject_id,clf.value as grade, sp.study_period_id,pp.school_id,
									ds.curriculum_version_hmodule_id,ds.is_optional,
											--curriculum_version_hmodule_id
											subj.name_et,subj.name_en,subj.code,subj.credits,
											--is_optional
											(select string_agg(pers.firstname||' '||pers.lastname,', ') 
												from subject_study_period_teacher st join teacher tt on st.teacher_id=tt.id join person pers on tt.person_id=pers.id
													where st.subject_study_period_id=sp.id) as teachers --teachers
							from protocol pp 
									 join protocol_hdata ph on pp.id=ph.protocol_id
									 left join subject_study_period sp on ph.subject_study_period_id=sp.id
									 left join (declaration_subject ds join declaration  dd on ds.declaration_id=dd.id and dd.student_id=new.student_id) on sp.id=ds.subject_study_period_id 
									 join subject subj on sp.subject_id=subj.id or ph.final_subject_id = subj.id
									 join classifier clf on clf.code=NEW.grade_code
							where new.protocol_id=pp.id)
		LOOP
			update student_higher_result set 
				grade=coalesce(NEW.grade,r.grade),
				grade_date=NEW.grade_date,
				grade_mark=coalesce(NEW.grade_mark,case r.grade when '0' then 0 when '1' then 1 when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else null end),
				grade_code=NEW.grade_code,
				apel_school_id=null,
				subject_name_et=r.name_et,
				subject_name_en=coalesce(r.name_en,r.name_et),
				teachers=r.teachers,
				credits=r.credits,
				subject_code=r.code,
				curriculum_version_hmodule_id=r.curriculum_version_hmodule_id,
				is_optional=coalesce(r.is_optional,false),
				--is_optional=false,
				subject_id=r.subject_id,
				changed=now(),
				study_period_id=coalesce(r.study_period_id, get_study_period(NEW.grade_date::date, r.school_id::int))
			where protocol_student_id=NEW.id;
			GET DIAGNOSTICS u_count = ROW_COUNT;

			if coalesce(u_count,0)=0 THEN
				insert into student_higher_result (
					student_id,			subject_id,				protocol_student_id,				grade,				grade_date,				grade_mark,				grade_code,
					apel_application_record_id,				apel_school_id,				inserted, curriculum_version_hmodule_id,is_optional,
					subject_name_et,				subject_name_en,				teachers,				credits,				subject_code,				study_period_id) 
				values(
					NEW.student_id,r.subject_id,NEW.id,coalesce(NEW.grade,r.grade),NEW.grade_date,
					coalesce(NEW.grade_mark,case r.grade when '0' then 0 when '1' then 1 when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else null end),				NEW.grade_code,
					null,--apel_application_record_id,
					null,--apel_school_id
					now(),r.curriculum_version_hmodule_id,coalesce(r.is_optional,false),
					r.name_et,coalesce(r.name_en,r.name_et),r.teachers,	r.credits,r.code,				r.study_period_id); --is_optional
			end if;

			if coalesce(r.subject_id,0) > 0 then
				--select distinct first_value(id)over(partition by r.subject_id order by case when coalesce(apel_application_record_id,0)=0 then 1 else 0 end, grade_date desc nulls last, ) into p_id 
				--from student_higher_result where student_id=case when tg_op='DELETE' then old.student_id else NEW.student_id end and subject_id=r.subject_id;
				select distinct first_value(sr.id)over(partition by sr.subject_id order by case when coalesce(sr.apel_application_record_id,0)=0 then 1 else 0 end, sr.grade_date desc nulls last,ph.type_code asc,ph.inserted desc) into p_id 
				from student_higher_result sr
						 left join protocol_student ps on sr.protocol_student_id=ps.id
						 left join protocol_hdata ph on ps.protocol_id=ph.protocol_id
				where sr.student_id=case when tg_op='DELETE' then old.student_id else NEW.student_id end and sr.subject_id=r.subject_id;
				update student_higher_result set is_active=false where student_id=st_id and subject_id=r.subject_id and id!=p_id;
			end if;

			/*--kustutame Ć¼leliigset eelmist rida
			delete from student_higher_result
WHERE id IN (SELECT id
              FROM (SELECT id,
                             ROW_NUMBER() OVER (partition BY column1, column2, column3 ORDER BY id) AS rnum
                     FROM tablename) t
              WHERE t.rnum > 1);*/
			x:=upd_student_curriculum_completion(new.student_id);
			return null;
		end loop;
    for r in (select crm.name_et, crm.name_en, crm.credits, vv.curriculum_version_omodule_id, crm.credits, pers.firstname||' '||pers.lastname as teachers, clf.value as grade,
							  vv.study_year_id, (select count(*) from student_vocational_result sr where sr.curriculum_version_omodule_id=vv.curriculum_version_omodule_id and sr.student_id=NEW.student_id) as total
							from protocol pp 
									 join protocol_vdata vv on pp.id=vv.protocol_id
									 join curriculum_version_omodule cro on vv.curriculum_version_omodule_id=cro.id
									 join curriculum_module crm on cro.curriculum_module_id=crm.id
									 join teacher tt on vv.teacher_id=tt.id 
									 join person pers on tt.person_id=pers.id
									 join classifier clf on clf.code=NEW.grade_code
							where new.protocol_id=pp.id)
		LOOP
			update student_vocational_result set 
				curriculum_version_omodule_id=r.curriculum_version_omodule_id,				
				grade=coalesce(NEW.grade,r.grade),				
				grade_date=NEW.grade_date,				
				grade_mark=coalesce(NEW.grade_mark,case r.grade when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else null end),				
				grade_code=NEW.grade_code,
				credits=r.credits,	
				teachers=r.teachers,	
				changed=now(), 
				module_name_et=r.name_et,	
				module_name_en=r.name_en,
				study_year_id=r.study_year_id
			where protocol_student_id=NEW.id;
			GET DIAGNOSTICS u_count = ROW_COUNT;

			if coalesce(u_count,0)=0 THEN
				insert into student_vocational_result (
					student_id,			curriculum_version_omodule_id,				protocol_student_id,		grade,				grade_date,				grade_mark,				grade_code,
					credits,	teachers,	inserted, module_name_et,	module_name_en,study_year_id)
				values(NEW.student_id,r.curriculum_version_omodule_id,NEW.id,		coalesce(NEW.grade,r.grade),	NEW.grade_date,	
							coalesce(NEW.grade_mark,case r.grade when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else null end),	NEW.grade_code,
					r.credits,	r.teachers,	now(), r.name_et,	r.name_en, r.study_year_id);
			end if;
			
--raise notice 'siin %', r.total;
			-- kustutame Ć¼leliigsed vanad read
			if r.total > 1 THEN
					DELETE FROM student_vocational_result 
						WHERE id IN (SELECT id
              FROM (SELECT id,
                             ROW_NUMBER() OVER (partition BY curriculum_version_omodule_id, student_id ORDER BY grade_date desc nulls last, id desc) AS rnum
                     FROM student_vocational_result where student_id=NEW.student_id and curriculum_version_omodule_id=r.curriculum_version_omodule_id) t
              WHERE t.rnum > 1);
			end if;
			x:=upd_student_curriculum_completion(new.student_id);
			return null;
		end loop;
  end if;
	return null;
end;
$function$
;

CREATE OR REPLACE FUNCTION public.upd_del_apel_result()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
declare
    u_count integer;
		b_count integer;
		r record;
		rr record;
		p_id integer;
		x integer;
begin
	if tg_op = 'UPDATE' THEN
		if new.status_code='VOTA_STAATUS_C' and OLD.status_code!='VOTA_STAATUS_C' THEN
			--lisame
			if new.is_vocational=true THEN
				for r in (SELECT cmo.id,ar.id as apel_id, clf.value as grade, afr.apel_school_id, --sp.study_period_id,
												case when afr.apel_school_id is not null then afr.name_et else cm.name_et end as name_et,
												case when afr.apel_school_id is not null then afr.name_en else cm.name_en end as name_en,
												case when afr.apel_school_id is not null then afr.credits else cm.credits end as credits, afr.grade_code,
												afr.teachers, afr.apel_school_id, ar.id as record_id,afr.is_optional,afr.curriculum_version_hmodule_id,null as study_period_id,afr.grade_date,
                        array(select sm.curriculum_version_omodule_id from apel_application_formal_replaced_subject_or_module sm where sm.apel_application_record_id=ar.id and sm.curriculum_version_omodule_theme_id is null) as arr
									from apel_application_record ar
											 join apel_application_formal_subject_or_module afr on ar.id=afr.apel_application_record_id
											 join classifier clf on clf.code=afr.grade_code
											 left join apel_school aps on afr.apel_school_id=aps.id
											 left join curriculum_version_omodule cmo on afr.curriculum_version_omodule_id=cmo.id
											 left join curriculum_module cm on cmo.curriculum_module_id=cm.id
									WHERE ar.apel_application_id=NEW.id and afr.transfer=true	and (select count(sm.curriculum_version_omodule_id) from apel_application_formal_replaced_subject_or_module sm where sm.apel_application_record_id=ar.id and sm.curriculum_version_omodule_theme_id is null)>0														
									union
									SELECT cmo.id,ar.id as apel_id,clf.value as grade, null,--sp.study_period_id,
										cm.name_et,
										cm.name_en,
										cm.credits ,air.grade_code,
										null, null, ar.id as record_id,air.is_optional,air.curriculum_version_hmodule_id, null as study_period_id, now(), null
										from apel_application_record ar
												 join apel_application_informal_subject_or_module air on ar.id=air.apel_application_record_id
												 join curriculum_version_omodule cmo on air.curriculum_version_omodule_id=cmo.id
												 join curriculum_module cm on cmo.curriculum_module_id=cm.id
												 join classifier clf on clf.code=air.grade_code
										WHERE ar.apel_application_id=NEW.id and air.transfer=true and air.curriculum_version_omodule_theme_id is null 
				)
				LOOP
					p_id:=get_study_year(r.grade_date::date,NEW.school_id::int);
					insert into student_vocational_result (
							student_id,			
							curriculum_version_omodule_id,	
						  apel_application_record_id, apel_school_id,	
							grade,	grade_date,grade_mark,grade_code,
							credits,	teachers,	inserted, 
							module_name_et,	module_name_en,study_year_id,arr_modules)
					values(NEW.student_id,
								 r.id,r.apel_id,	r.apel_school_id, r.grade,r.grade_date,	
								case r.grade when '0' then 0 when '1' then 1 when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else null end,
							r.grade_code,	r.credits,	r.teachers,	now(), r.name_et,	r.name_en, case when p_id > 0 then p_id else null end, r.arr);
					--päevikud, kus on olemas õppur, aga lõpptulemus puudub
					for rr in (select distinct jes.id, jes.grade_code, js.id as s_id, je.id as e_id
										from journal jj
												 join journal_entry je on jj.id=je.journal_id
												 join journal_student js on jj.id=js.journal_id
												 join journal_omodule_theme jot on jj.id=jot.journal_id
												 join curriculum_version_omodule_theme cot on jot.curriculum_version_omodule_theme_id=cot.id
												 left join journal_entry_student jes on je.id=jes.journal_entry_id and js.id=jes.journal_student_id
										where je.entry_type_code='SISSEKANNE_L' and js.student_id=NEW.student_id and 
													((r.arr is null or cardinality(r.arr) = 0)  and cot.curriculum_version_omodule_id=r.id or 
													 cardinality(r.arr)  > 0 and cot.curriculum_version_omodule_id=any(r.arr)))
					loop
						if rr.id is null THEN
							insert into journal_entry_student(journal_entry_id,journal_student_id,grade_code,grade_inserted,inserted,version,inserted_by,grade_inserted_by)
							values(rr.e_id,rr.s_id,case when cardinality(r.arr) > 1 then 'KUTSEHINDAMINE_A' else r.grade_code end,now(),now(),0,NEW.changed_by,NEW.changed_by);
						ELSE
							if (case when cardinality(r.arr) > 1 then 'KUTSEHINDAMINE_A' else r.grade_code end) !=coalesce(rr.grade_code,'x') then
								insert into journal_entry_student_history(journal_entry_student_id,grade_code,grade_inserted,inserted,version,inserted_by,grade_inserted_by)
								select rr.id,grade_code,grade_inserted,now(),0,NEW.changed_by,grade_inserted_by
								from journal_entry_student where id=rr.id and coalesce(grade_code,'x')!='x';
								update journal_entry_student set grade_code=case when cardinality(r.arr) > 1 then 'KUTSEHINDAMINE_A' else r.grade_code end,grade_inserted=now(),grade_inserted_by=NEW.changed_by where id=rr.id;
							end if;
						end if;
					end loop;
				end loop;
				x:=upd_student_curriculum_completion(new.student_id);
			ELSE
				for r in (SELECT subj.id as subject_id,
										case when s.is_letter_grade then clf.value2 else clf.value end as grade, --sp.study_period_id,
										case when afr.apel_school_id is not null then afr.name_et else subj.name_et end as name_et,
										case when afr.apel_school_id is not null then afr.name_en else subj.name_en end as name_en,
										case when afr.apel_school_id is not null then afr.subject_code else subj.code end as code,
										case when afr.apel_school_id is not null then afr.credits else subj.credits end as credits, afr.grade_code,
										afr.teachers, afr.apel_school_id, ar.id as record_id,afr.is_optional,afr.curriculum_version_hmodule_id,null as study_period_id,afr.grade_date
										from apel_application_record ar
												 join apel_application_formal_subject_or_module afr on ar.id=afr.apel_application_record_id
												 join classifier clf on clf.code=afr.grade_code
												 left join apel_school aps on afr.apel_school_id=aps.id
												 left join subject subj on afr.subject_id=subj.id
		 										 join apel_application aa on ar.apel_application_id = aa.id
		 										 join school s on aa.school_id = s.id
										WHERE ar.apel_application_id=NEW.id and afr.transfer=true
										union
										SELECT subj.id,
										case when s.is_letter_grade then clf.value2 else clf.value end as grade,
										subj.name_et,
										subj.name_en,
										subj.code,
										subj.credits ,air.grade_code,
										null, null, ar.id as record_id,air.is_optional,air.curriculum_version_hmodule_id, null as study_period_id, coalesce(aa.confirmed,now())
										from apel_application_record ar
												 join apel_application_informal_subject_or_module air on ar.id=air.apel_application_record_id
												 join subject subj on air.subject_id=subj.id
												 join classifier clf on clf.code=air.grade_code
		 										 join apel_application aa on ar.apel_application_id = aa.id
		 										 join school s on aa.school_id = s.id
										WHERE ar.apel_application_id=NEW.id and air.transfer=true)
				LOOP
					insert into student_higher_result (
						student_id,			subject_id,				apel_application_record_id,				grade,				grade_date,				grade_mark,				grade_code,
										apel_school_id,				inserted,
						subject_name_et,				subject_name_en,				teachers,				credits,				subject_code,				is_optional,study_period_id,curriculum_version_hmodule_id) 
					values(
						NEW.student_id,r.subject_id,r.record_id,r.grade,r.grade_date,
						case when (r.grade='0' or r.grade='F') then 0 when (r.grade='1' or r.grade='E') then 1 when (r.grade='2' or r.grade='D') then 2 when (r.grade='3' or r.grade='C') then 3 when (r.grade='4' or r.grade='B') then 4 when (r.grade='5' or r.grade='A' and r.grade_code = 'KORGHINDAMINE_5') then 5 else null end,
						r.grade_code,
						r.apel_school_id,--apel_school_id
						now(),
						r.name_et,coalesce(r.name_en,r.name_et),r.teachers,	r.credits,r.code,				r.is_optional,null,r.curriculum_version_hmodule_id) returning id into p_id; --is_optional

				if coalesce(r.subject_id,0) > 0 then
					--select distinct first_value(id)over(partition by r.subject_id order by grade_date desc nulls last) into p_id from student_higher_result where student_id=NEW.student_id and subject_id=r.subject_id;
					update student_higher_result set is_active=false where student_id=NEW.student_id and subject_id=r.subject_id and id!=p_id;
				end if;
				--return null;
			end loop;
		end if;
	elsif new.status_code!='VOTA_STAATUS_C' and OLD.status_code='VOTA_STAATUS_C' THEN
			--kustutame kõik ära
			if new.is_vocational=true THEN
				delete from student_vocational_result where apel_application_record_id in (select aa.id from apel_application_record aa where aa.apel_application_id=NEW.id);
				x:=upd_student_curriculum_completion(new.student_id);
			ELSE
				delete from student_higher_result_module where student_higher_result_id in (select id from student_higher_result where apel_application_record_id in (select aa.id from apel_application_record aa where aa.apel_application_id=NEW.id));
				delete from student_higher_result where apel_application_record_id in (select aa.id from apel_application_record aa where aa.apel_application_id=NEW.id);
				for r in (select distinct first_value(id)over(partition by subject_id order by grade_date desc nulls last) as id, subject_id 
								from student_higher_result 
								where student_id=NEW.student_id)
				loop
					update student_higher_result ss 
							set is_active=case when ss.id=r.id then true else false end
					where ss.student_id=NEW.student_id and r.subject_id=ss.subject_id;
				end LOOP;
				x:=upd_student_curriculum_completion(new.student_id);
			end if;
		end if;
	end if;
	return null;
end;
$function$
;
