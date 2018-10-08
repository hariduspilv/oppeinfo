\c hois

insert into user_role_default (object_code,permission_code,role_code) values('TEEMAOIGUS_PAEVIK','OIGUS_M','ROLL_O');
insert into user_role_default (object_code,permission_code,role_code) values('TEEMAOIGUS_MOODULPROTOKOLL','OIGUS_M','ROLL_O');
insert into user_role_default (object_code,permission_code,role_code) values('TEEMAOIGUS_PRAKTIKAPAEVIK','OIGUS_M','ROLL_O');
insert into user_role_default (object_code,permission_code,role_code) values('TEEMAOIGUS_OPPEMATERJAL','OIGUS_M','ROLL_O');
insert into user_role_default (object_code,permission_code,role_code) values('TEEMAOIGUS_SYNDMUS','OIGUS_M','ROLL_O');
insert into user_role_default (object_code,permission_code,role_code) values('TEEMAOIGUS_TEADE','OIGUS_M','ROLL_O');
insert into user_role_default (object_code,permission_code,role_code) values('TEEMAOIGUS_PUUDUMINE','OIGUS_M','ROLL_O');

/* Create Tables */

CREATE TABLE "curriculum_address"
(
	"id" bigserial NOT NULL,
	"curriculum_id" bigint NOT NULL,
	"address" varchar(100)	 NOT NULL,    -- aadress teksti kujul
	"address_ads" varchar(50)	 NOT NULL,    -- aadress ADS kujul
	"address_oid" varchar(50)	 NOT NULL,    -- aadress id inADS komponendi jaoks
	"address_ov" varchar(50)	 NOT NULL,    -- aadressi kohalik omavalituses kood
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL,
	"version" integer NOT NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "curriculum_address"	IS 'õppekava toimumise kohad';
COMMENT ON COLUMN "curriculum_address"."address"	IS 'aadress teksti kujul';
COMMENT ON COLUMN "curriculum_address"."address_ads"	IS 'aadress ADS kujul';
COMMENT ON COLUMN "curriculum_address"."address_oid"	IS 'aadress id inADS komponendi jaoks';
COMMENT ON COLUMN "curriculum_address"."address_ov"	IS 'aadressi kohalik omavalituses kood';

ALTER TABLE "curriculum_address" ADD CONSTRAINT "PK_curriculum_address"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_curriculum_address_curriculum" ON "curriculum_address" ("curriculum_id" ASC);
ALTER TABLE "curriculum_address" ADD CONSTRAINT "FK_curriculum_address_curriculum"	FOREIGN KEY ("curriculum_id") REFERENCES "public"."curriculum" ("id") ON DELETE No Action ON UPDATE No Action;

update classifier set ehis_value='ROK_030' where code='EHIS_ROK_15747'; --Aianduserialade RÕK
update classifier set ehis_value='ROK_037' where code='EHIS_ROK_15754'; --Arvutierialade RÕK
update classifier set ehis_value='ROK_016' where code='EHIS_ROK_15733'; --Autoerialade RÕK
update classifier set ehis_value='ROK_064' where code='EHIS_ROK_18230'; --Disaini ja käsitöö erialade RÕK
update classifier set ehis_value='ROK_033' where code='EHIS_ROK_15750'; --Ehituserialade RÕK
update classifier set ehis_value='ROK_043' where code='EHIS_ROK_15760'; --Elektriku eriala RÕK
update classifier set ehis_value='ROK_044' where code='EHIS_ROK_15761'; --Elektroonika ja automaatika erialade RÕK
update classifier set ehis_value='ROK_061' where code='EHIS_ROK_18034'; --Energeetika ja automaatika erialade RÕK
update classifier set ehis_value='ROK_046' where code='EHIS_ROK_16108'; --Erakorralise meditsiini tehniku eriala RÕK
update classifier set ehis_value='ROK_040' where code='EHIS_ROK_15757'; --Fotograafia RÕK
update classifier set ehis_value='ROK_041' where code='EHIS_ROK_15758'; --Helindaja RÕK
update classifier set ehis_value='ROK_006' where code='EHIS_ROK_15723'; --Hooldustöötaja RÕK
update classifier set ehis_value='ROK_015' where code='EHIS_ROK_15732'; --Iluteeninduse RÕK
update classifier set ehis_value='ROK_058' where code='EHIS_ROK_17953'; --Info- ja kommunikatsioonitehnoloogia erialade RÕK
update classifier set ehis_value='ROK_003' where code='EHIS_ROK_15720'; --Interpretatsioonierialade RÕK
update classifier set ehis_value='ROK_055' where code='EHIS_ROK_17059'; --Kalakasvataja eriala RÕK
update classifier set ehis_value='ROK_010' where code='EHIS_ROK_15727'; --Kaubanduserialade RÕK
update classifier set ehis_value='ROK_025' where code='EHIS_ROK_15742'; --Keemia ja protsessitehnoloogia RÕK
update classifier set ehis_value='ROK_047' where code='EHIS_ROK_16130'; --Keskkonnakaitse eriala RÕK
update classifier set ehis_value='ROK_001' where code='EHIS_ROK_15718'; --Klassikalise balleti eriala RÕK
update classifier set ehis_value='ROK_002' where code='EHIS_ROK_15719'; --Klaverite hooldusmeistri eriala RÕK
update classifier set ehis_value='ROK_032' where code='EHIS_ROK_15749'; --Koduteeninduse erialade RÕK
update classifier set ehis_value='ROK_005' where code='EHIS_ROK_15722'; --Kooridirigeerimise eriala RÕK
update classifier set ehis_value='ROK_038' where code='EHIS_ROK_15755'; --Kujundamise erialade RÕK
update classifier set ehis_value='ROK_007' where code='EHIS_ROK_15724'; --Lapsehoidja RÕK
update classifier set ehis_value='ROK_022' where code='EHIS_ROK_15739'; --Lennuliiklusteeninduse eriala RÕK
update classifier set ehis_value='ROK_017' where code='EHIS_ROK_15734'; --Liikurmasinate tehniku eriala RÕK
update classifier set ehis_value='ROK_019' where code='EHIS_ROK_15736'; --Logistika erialade RÕK
update classifier set ehis_value='ROK_054' where code='EHIS_ROK_17052'; --Loomaarsti abilise eriala RÕK
update classifier set ehis_value='ROK_034' where code='EHIS_ROK_15751'; --Maamõõtmise RÕK
update classifier set ehis_value='ROK_012' where code='EHIS_ROK_15729'; --Majandusarvestuse RÕK
update classifier set ehis_value='ROK_026' where code='EHIS_ROK_15743'; --Majutamise ja toitlustamise erialade RÕK
update classifier set ehis_value='ROK_008' where code='EHIS_ROK_15725'; --Massaaži eriala RÕK
update classifier set ehis_value='ROK_059' where code='EHIS_ROK_17958'; --Meediatehnoloogia erialade RÕK
update classifier set ehis_value='ROK_045' where code='EHIS_ROK_15762'; --Mehaanika ja metallitöö erialade RÕK
update classifier set ehis_value='ROK_023' where code='EHIS_ROK_15740'; --Meresõiduspetsialistide RÕK
update classifier set ehis_value='ROK_053' where code='EHIS_ROK_17007'; --Mesiniku eriala RÕK
update classifier set ehis_value='ROK_031' where code='EHIS_ROK_15748'; --Metsanduserialade RÕK
update classifier set ehis_value='ROK_020' where code='EHIS_ROK_15737'; --Mootorsõidukite juhtide kutseõppe RÕK
update classifier set ehis_value='ROK_042' where code='EHIS_ROK_15759'; --Multimeediumi RÕK
update classifier set ehis_value='ROK_004' where code='EHIS_ROK_15721'; --Muusikateooria ja kompositsiooni eriala RÕK
update classifier set ehis_value='ROK_051' where code='EHIS_ROK_16533'; --Politseiteenistuse eriala RÕK
update classifier set ehis_value='ROK_060' where code='EHIS_ROK_17986'; --Puhastus- ja kodumajanduse eriala RÕK
update classifier set ehis_value='ROK_057' where code='EHIS_ROK_17948'; --Puidutehnoloogia erialade RÕK
update classifier set ehis_value='ROK_056' where code='EHIS_ROK_17637'; --puudub
update classifier set ehis_value='ROK_029' where code='EHIS_ROK_15746'; --Põllumajanduserialade RÕK
update classifier set ehis_value='ROK_049' where code='EHIS_ROK_16531'; --Päästekorraldaja eriala RÕK
update classifier set ehis_value='ROK_050' where code='EHIS_ROK_16532'; --Päästespetsialisti eriala RÕK
update classifier set ehis_value='ROK_048' where code='EHIS_ROK_16530'; --Päästja eriala RÕK
update classifier set ehis_value='ROK_018' where code='EHIS_ROK_15735'; --Raudtee erialade RÕK
update classifier set ehis_value='ROK_011' where code='EHIS_ROK_15728'; --Sekretäritöö RÕK
update classifier set ehis_value='ROK_024' where code='EHIS_ROK_15741'; --Sõjaväelise juhtimise eriala vanemallohvitseridele RÕK
update classifier set ehis_value='ROK_013' where code='EHIS_ROK_15730'; --Tarbekunsti ja oskuskäsitöö erialade RÕK
update classifier set ehis_value='ROK_062' where code='EHIS_ROK_18035'; --Tekstiili-, rõiva- ja nahatöötluse erialade RÕK
update classifier set ehis_value='ROK_063' where code='EHIS_ROK_18036'; --Tervishoiu- ja sotsiaalteenuste erialade RÕK
update classifier set ehis_value='ROK_035' where code='EHIS_ROK_15752'; --Tisleritööde RÕK
update classifier set ehis_value='ROK_028' where code='EHIS_ROK_15745'; --Toiduainetööstuse erialade RÕK
update classifier set ehis_value='ROK_039' where code='EHIS_ROK_15756'; --Trükitehnoloogia RÕK
update classifier set ehis_value='ROK_027' where code='EHIS_ROK_15744'; --Turismierialade RÕK
update classifier set ehis_value='ROK_052' where code='EHIS_ROK_16744'; --Vanglaametniku eriala RÕK
update classifier set ehis_value='ROK_036' where code='EHIS_ROK_15753'; --Väikelaevade ehituse RÕK
update classifier set ehis_value='ROK_021' where code='EHIS_ROK_15738'; --Väikesadama spetsialisti eriala RÕK
update classifier set ehis_value='ROK_009' where code='EHIS_ROK_15731'; --Õmbluserialade RÕK
update classifier set ehis_value='ROK_014' where code='EHIS_ROK_15726'; --Ärierialade RÕK

update classifier set ehis_value='OK_FAIL_TYYP_KEX' where code='EHIS_FAIL_15775'; --Kutseeksam
update classifier set ehis_value='OK_FAIL_TYYP_PVM' where code='EHIS_FAIL_15774'; --Praktikavõimalused
update classifier set ehis_value='OK_FAIL_TYYP_POM' where code='EHIS_FAIL_17883'; --Põhiõpingute moodulid
update classifier set ehis_value='OK_FAIL_TYYP_KIR' where code='EHIS_FAIL_17885'; --Tööandjate toetuskiri
update classifier set ehis_value='OK_FAIL_TYYP_OKF' where code='EHIS_FAIL_15773'; --Õppekava koondfail
update classifier set ehis_value='OK_FAIL_TYYP_OMD' where code='EHIS_FAIL_15776'; --Õppekava moodulid
update classifier set ehis_value='OK_FAIL_TYYP_VAS' where code='EHIS_FAIL_17886'; --Õppekava moodulite ja kutsestandardite kompetentside vastavustabel
update classifier set ehis_value='OK_FAIL_TYYP_OVM' where code='EHIS_FAIL_15777'; --Õppekava valikmoodulid
update classifier set ehis_value='OK_FAIL_TYYP_YOM' where code='EHIS_FAIL_17884'; --Üldõpingute moodulid

insert into classifier(code,value,name_Et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('PUUDUMINE_PR','PR','Praktikal','PUUDUMINE',current_timestamp(3),true,true,false,0);

INSERT INTO "classifier" ("code", "value", "name_et", "inserted", "valid", "is_vocational", "is_higher", "version") VALUES ('EHIS_EMAKEEL', 'EHIS_EMAKEEL', 'EHISe õpetaja emakeel', current_timestamp(3), 't', 't', 't', '0');
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('EHIS_EMAKEEL_et', 'et', 'eesti', 'EHIS_EMAKEEL',current_timestamp(3), 't', 't', 't', '0','et');
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('EHIS_EMAKEEL_ev', 'ev', 'eesti / vene', 'EHIS_EMAKEEL',current_timestamp(3), 't', 't', 't', '0','ev');
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('EHIS_EMAKEEL_ru', 'ru', 'vene', 'EHIS_EMAKEEL',current_timestamp(3), 't', 't', 't', '0','ru');
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('EHIS_EMAKEEL_en', 'en', 'inglise', 'EHIS_EMAKEEL',current_timestamp(3), 't', 't', 't', '0','en');
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('EHIS_EMAKEEL_fr', 'fr', 'prantsuse', 'EHIS_EMAKEEL',current_timestamp(3), 't', 't', 't', '0','fr');
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('EHIS_EMAKEEL_lv', 'lv', 'läti', 'EHIS_EMAKEEL',current_timestamp(3), 't', 't', 't', '0','lv');
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('EHIS_EMAKEEL_fi', 'fi', 'soome', 'EHIS_EMAKEEL',current_timestamp(3), 't', 't', 't', '0','fi');
INSERT INTO "classifier" ("code", "value", "name_et", main_class_code, "inserted", "valid", "is_vocational", "is_higher", "version",ehis_value) VALUES ('EHIS_EMAKEEL_sv', 'sv', 'rootsi', 'EHIS_EMAKEEL',current_timestamp(3), 't', 't', 't', '0','sv');

alter table teacher add native_language_code varchar(100);
CREATE INDEX "IXFK_teacher_classifier" ON "teacher" ("native_language_code" ASC);
ALTER TABLE "teacher" ADD CONSTRAINT "FK_teacher_classifier" FOREIGN KEY ("native_language_code") REFERENCES "classifier" ("code") ON DELETE No Action ON UPDATE No Action;
comment on column teacher.native_language_code is 'kutseõpetaja emakeel, viide klassifikaatorile EHIS_EMAKEEL';

alter table teacher_qualification alter column year drop not null;

update teacher_qualification set end_date=to_date('30.06.'||year::text,'dd.mm.yyyy') where end_date is null;
alter table application add column other_text text;
comment on column application.other_text is 'Muu avalduse tekst';