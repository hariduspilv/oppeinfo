create database hois;
\c hois;


-- ----------------------------
-- Sequence structure for building_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."building_id_seq";
CREATE SEQUENCE "public"."building_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;
SELECT setval('"public"."building_id_seq"', 8, true);

-- ----------------------------
-- Sequence structure for curriculum_department_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_department_id_seq";
CREATE SEQUENCE "public"."curriculum_department_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;
SELECT setval('"public"."curriculum_department_id_seq"', 33, true);

-- ----------------------------
-- Sequence structure for curriculum_files_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_files_id_seq";
CREATE SEQUENCE "public"."curriculum_files_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;
SELECT setval('"public"."curriculum_files_id_seq"', 9, true);

-- ----------------------------
-- Sequence structure for curriculum_grade_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_grade_id_seq";
CREATE SEQUENCE "public"."curriculum_grade_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 8
 CACHE 1;
SELECT setval('"public"."curriculum_grade_id_seq"', 8, true);

-- ----------------------------
-- Sequence structure for curriculum_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_id_seq";
CREATE SEQUENCE "public"."curriculum_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 148
 CACHE 1;
SELECT setval('"public"."curriculum_id_seq"', 148, true);

-- ----------------------------
-- Sequence structure for curriculum_joint_partners_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_joint_partners_id_seq";
CREATE SEQUENCE "public"."curriculum_joint_partners_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;
SELECT setval('"public"."curriculum_joint_partners_id_seq"', 1, true);

-- ----------------------------
-- Sequence structure for curriculum_module_competence_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_module_competence_id_seq";
CREATE SEQUENCE "public"."curriculum_module_competence_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;

-- ----------------------------
-- Sequence structure for curriculum_module_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_module_id_seq";
CREATE SEQUENCE "public"."curriculum_module_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;
SELECT setval('"public"."curriculum_module_id_seq"', 1, true);

-- ----------------------------
-- Sequence structure for curriculum_module_occupation_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_module_occupation_id_seq";
CREATE SEQUENCE "public"."curriculum_module_occupation_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 3
 CACHE 1;
SELECT setval('"public"."curriculum_module_occupation_id_seq"', 3, true);

-- ----------------------------
-- Sequence structure for curriculum_occupation_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_occupation_id_seq";
CREATE SEQUENCE "public"."curriculum_occupation_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;
SELECT setval('"public"."curriculum_occupation_id_seq"', 1, true);

-- ----------------------------
-- Sequence structure for curriculum_speciality_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_speciality_id_seq";
CREATE SEQUENCE "public"."curriculum_speciality_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 9
 CACHE 1;
SELECT setval('"public"."curriculum_speciality_id_seq"', 9, true);

-- ----------------------------
-- Sequence structure for curriculum_study_form_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_study_form_id_seq";
CREATE SEQUENCE "public"."curriculum_study_form_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 3
 CACHE 1;
SELECT setval('"public"."curriculum_study_form_id_seq"', 3, true);

-- ----------------------------
-- Sequence structure for curriculum_study_lang_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_study_lang_id_seq";
CREATE SEQUENCE "public"."curriculum_study_lang_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 232
 CACHE 1;
SELECT setval('"public"."curriculum_study_lang_id_seq"', 232, true);

-- ----------------------------
-- Sequence structure for ois_file_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."ois_file_id_seq";
CREATE SEQUENCE "public"."ois_file_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 166
 CACHE 1;
SELECT setval('"public"."ois_file_id_seq"', 166, true);

-- ----------------------------
-- Sequence structure for person_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."person_id_seq";
CREATE SEQUENCE "public"."person_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 6
 CACHE 1;
SELECT setval('"public"."person_id_seq"', 6, true);

-- ----------------------------
-- Sequence structure for room_equipment_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."room_equipment_id_seq";
CREATE SEQUENCE "public"."room_equipment_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 2
 CACHE 1;
SELECT setval('"public"."room_equipment_id_seq"', 2, true);

-- ----------------------------
-- Sequence structure for room_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."room_id_seq";
CREATE SEQUENCE "public"."room_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 5
 CACHE 1;
SELECT setval('"public"."room_id_seq"', 5, true);

-- ----------------------------
-- Sequence structure for school_department_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."school_department_id_seq";
CREATE SEQUENCE "public"."school_department_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 176
 CACHE 1;
SELECT setval('"public"."school_department_id_seq"', 176, true);

-- ----------------------------
-- Sequence structure for school_school_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."school_school_id_seq";
CREATE SEQUENCE "public"."school_school_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 940
 CACHE 1;
SELECT setval('"public"."school_school_id_seq"', 940, true);

-- ----------------------------
-- Sequence structure for school_study_level_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."school_study_level_id_seq";
CREATE SEQUENCE "public"."school_study_level_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 42
 CACHE 1;
SELECT setval('"public"."school_study_level_id_seq"', 42, true);

-- ----------------------------
-- Sequence structure for state_curriculum_module_occup_state_curriculum_module_occup_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."state_curriculum_module_occup_state_curriculum_module_occup_seq";
CREATE SEQUENCE "public"."state_curriculum_module_occup_state_curriculum_module_occup_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1455
 CACHE 1;
SELECT setval('"public"."state_curriculum_module_occup_state_curriculum_module_occup_seq"', 1455, true);

-- ----------------------------
-- Sequence structure for state_curriculum_module_occupation_state_curriculum_module_occu
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."state_curriculum_module_occupation_state_curriculum_module_occu";
CREATE SEQUENCE "public"."state_curriculum_module_occupation_state_curriculum_module_occu"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;

-- ----------------------------
-- Sequence structure for state_curriculum_module_outco_state_curriculum_module_outco_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."state_curriculum_module_outco_state_curriculum_module_outco_seq";
CREATE SEQUENCE "public"."state_curriculum_module_outco_state_curriculum_module_outco_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1418
 CACHE 1;
SELECT setval('"public"."state_curriculum_module_outco_state_curriculum_module_outco_seq"', 1418, true);

-- ----------------------------
-- Sequence structure for state_curriculum_module_outcomes_state_curriculum_module_outcom
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."state_curriculum_module_outcomes_state_curriculum_module_outcom";
CREATE SEQUENCE "public"."state_curriculum_module_outcomes_state_curriculum_module_outcom"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;

-- ----------------------------
-- Sequence structure for state_curriculum_module_state_curriculum_module_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."state_curriculum_module_state_curriculum_module_id_seq";
CREATE SEQUENCE "public"."state_curriculum_module_state_curriculum_module_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1453
 CACHE 1;
SELECT setval('"public"."state_curriculum_module_state_curriculum_module_id_seq"', 1453, true);

-- ----------------------------
-- Sequence structure for state_curriculum_occupation_state_curriculum_occupation_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."state_curriculum_occupation_state_curriculum_occupation_id_seq";
CREATE SEQUENCE "public"."state_curriculum_occupation_state_curriculum_occupation_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1489
 CACHE 1;
SELECT setval('"public"."state_curriculum_occupation_state_curriculum_occupation_id_seq"', 1489, true);

-- ----------------------------
-- Sequence structure for state_curriculum_state_curriculum_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."state_curriculum_state_curriculum_id_seq";
CREATE SEQUENCE "public"."state_curriculum_state_curriculum_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1471
 CACHE 1;
SELECT setval('"public"."state_curriculum_state_curriculum_id_seq"', 1471, true);

-- ----------------------------
-- Sequence structure for subject_connect_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."subject_connect_id_seq";
CREATE SEQUENCE "public"."subject_connect_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 6
 CACHE 1;
SELECT setval('"public"."subject_connect_id_seq"', 6, true);

-- ----------------------------
-- Sequence structure for subject_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."subject_id_seq";
CREATE SEQUENCE "public"."subject_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 35
 CACHE 1;
SELECT setval('"public"."subject_id_seq"', 35, true);

-- ----------------------------
-- Sequence structure for subject_lang_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."subject_lang_id_seq";
CREATE SEQUENCE "public"."subject_lang_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 8
 CACHE 1;
SELECT setval('"public"."subject_lang_id_seq"', 8, true);

-- ----------------------------
-- Sequence structure for teacher_occupation_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."teacher_occupation_id_seq";
CREATE SEQUENCE "public"."teacher_occupation_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 88
 CACHE 1;
SELECT setval('"public"."teacher_occupation_id_seq"', 88, true);

-- ----------------------------
-- Sequence structure for user__id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."user__id_seq";
CREATE SEQUENCE "public"."user__id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 10
 CACHE 1;
SELECT setval('"public"."user__id_seq"', 10, true);

-- ----------------------------
-- Sequence structure for user_rights_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."user_rights_id_seq";
CREATE SEQUENCE "public"."user_rights_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 9
 CACHE 1;
SELECT setval('"public"."user_rights_id_seq"', 9, true);

-- ----------------------------
-- Table structure for building
-- ----------------------------
DROP TABLE IF EXISTS "public"."building";
CREATE TABLE "public"."building" (
"id" int8 DEFAULT nextval('building_id_seq'::regclass) NOT NULL,
"name" varchar(255) COLLATE "default" NOT NULL,
"school_id" int4 NOT NULL,
"code" varchar(20) COLLATE "default" NOT NULL,
"address" varchar(255) COLLATE "default",
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6) NOT NULL,
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."building" IS 'hooned';
COMMENT ON COLUMN "public"."building"."name" IS 'hoone nimetus';
COMMENT ON COLUMN "public"."building"."school_id" IS 'viide õppeasutusele';
COMMENT ON COLUMN "public"."building"."code" IS 'hoone kood';
COMMENT ON COLUMN "public"."building"."address" IS 'aadress, peaks olema viide ADS klassifikaatorile';

-- ----------------------------
-- Table structure for classifier
-- ----------------------------
DROP TABLE IF EXISTS "public"."classifier";
CREATE TABLE "public"."classifier" (
"code" varchar(100) COLLATE "default" NOT NULL,
"value" varchar(50) COLLATE "default" NOT NULL,
"value2" varchar(50) COLLATE "default",
"name_et" varchar(1000) COLLATE "default" NOT NULL,
"name_en" varchar(1000) COLLATE "default",
"name_ru" varchar(1000) COLLATE "default",
"parent_class_code" varchar(100) COLLATE "default",
"main_class_code" varchar(100) COLLATE "default",
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"valid" bool NOT NULL,
"description" varchar(1000) COLLATE "default",
"valid_from" date,
"valid_thru" date,
"extraval1" varchar(100) COLLATE "default",
"extraval2" varchar(100) COLLATE "default",
"ehis_value" varchar(100) COLLATE "default",
"is_vocational" bool NOT NULL,
"is_higher" bool NOT NULL,
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."classifier" IS 'Permanent data storage';
COMMENT ON COLUMN "public"."classifier"."code" IS 'klassifikaatori kood, nt HINDAMISVIIS, HINDMISVIIS_A jne';
COMMENT ON COLUMN "public"."classifier"."value" IS 'klassifikaatori väärtus (HINDMISVIIS, A, B jne)';
COMMENT ON COLUMN "public"."classifier"."value2" IS 'teine klassifikaatori väärtus, nr tiikide puhul võib olla kahetäheline riigi kood';
COMMENT ON COLUMN "public"."classifier"."parent_class_code" IS 'seos teise klassifikaatoriga, nt OPEPVALDKOND_A jne';
COMMENT ON COLUMN "public"."classifier"."main_class_code" IS 'peaklassifikaatori kood või liik, nt OPPEVALDKOND, HINDAMISVIIS';
COMMENT ON COLUMN "public"."classifier"."valid_from" IS 'kehtib alates';
COMMENT ON COLUMN "public"."classifier"."valid_thru" IS 'kehtib kuni';
COMMENT ON COLUMN "public"."classifier"."extraval1" IS 'lisaväli lisaväärtuste hoidmiseks';
COMMENT ON COLUMN "public"."classifier"."extraval2" IS 'lisaväli lisaväärtuste hoidmiseks';
COMMENT ON COLUMN "public"."classifier"."ehis_value" IS 'klassifikaatori väärtus EHISe jaoks';
COMMENT ON COLUMN "public"."classifier"."is_vocational" IS 'kas on kutseõppe klassifikaator';
COMMENT ON COLUMN "public"."classifier"."is_higher" IS 'kas on kõrgharidusõppe klassifikaator';

-- ----------------------------
-- Table structure for classifier_connect
-- ----------------------------
DROP TABLE IF EXISTS "public"."classifier_connect";
CREATE TABLE "public"."classifier_connect" (
"classifier_code" varchar(100) COLLATE "default" NOT NULL,
"connect_classifier_code" varchar(100) COLLATE "default" NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"main_classifier_code" varchar(100) COLLATE "default",
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."classifier_connect" IS 'klassifikaatorite mitu-mitmele seosed, kasutatakse eeskätt kompetentside juures';
COMMENT ON COLUMN "public"."classifier_connect"."classifier_code" IS 'peaklassifikaatori kood, nt kompetents';
COMMENT ON COLUMN "public"."classifier_connect"."connect_classifier_code" IS 'seotud klassi kood, nt osakutse, kutse, spetsialiseerumine';
COMMENT ON COLUMN "public"."classifier_connect"."main_classifier_code" IS 'klassifikaatori liik, millega see seotud on';

-- ----------------------------
-- Table structure for curriculum
-- ----------------------------
DROP TABLE IF EXISTS "public"."curriculum";
CREATE TABLE "public"."curriculum" (
"id" int4 DEFAULT nextval('curriculum_id_seq'::regclass) NOT NULL,
"is_higher" bool NOT NULL,
"name_et" varchar(255) COLLATE "default" NOT NULL,
"state_curriculum_id" int4,
"name_en" varchar(255) COLLATE "default" NOT NULL,
"name_ru" varchar(255) COLLATE "default",
"code" varchar(25) COLLATE "default" NOT NULL,
"consecution_code" varchar(100) COLLATE "default" NOT NULL,
"orig_study_level_code" varchar(100) COLLATE "default" NOT NULL,
"is_occupation" bool NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"isced_class_code" varchar(100) COLLATE "default",
"mer_code" varchar(10) COLLATE "default",
"approval" date,
"approval_dok_nr" varchar(50) COLLATE "default",
"outcomes_et" varchar(20000) COLLATE "default",
"outcomes_en" varchar(20000) COLLATE "default",
"sturcture" varchar(4000) COLLATE "default",
"admission_requirements_et" varchar(20000) COLLATE "default",
"admission_requirements_en" varchar(20000) COLLATE "default",
"graduation_requirements_et" varchar(20000) COLLATE "default",
"graduation_requirements_en" varchar(20000) COLLATE "default",
"credits" numeric(4,1),
"study_period" int4 NOT NULL,
"draft_text" varchar(4000) COLLATE "default",
"specialization" varchar(4000) COLLATE "default",
"is_joint" bool NOT NULL,
"optional_study_credits" numeric(4,1) NOT NULL,
"practice_description" varchar(20000) COLLATE "default",
"final_exam_description" varchar(4000) COLLATE "default",
"optional_study_descr" varchar(4000) COLLATE "default",
"ehis_changed" date,
"description" varchar(20000) COLLATE "default",
"ehis_status_code" varchar(100) COLLATE "default",
"contact_person" varchar(1000) COLLATE "default",
"valid_from" date NOT NULL,
"valid_thru" date,
"status_code" varchar(100) COLLATE "default" NOT NULL,
"joint_mentor_code" varchar(100) COLLATE "default",
"school_id" int4 NOT NULL,
"draft_code" varchar(100) COLLATE "default" NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default",
"name_genitive_et" varchar(255) COLLATE "default",
"name_genitive_en" varchar(255) COLLATE "default",
"group_code" varchar(100) COLLATE "default",
"language_description" varchar(1000) COLLATE "default",
"other_languages" varchar(1000) COLLATE "default",
"objectives_et" varchar(20000) COLLATE "default",
"objectives_en" varchar(20000) COLLATE "default",
"add_info" varchar(20000) COLLATE "default",
"MER_reg_date" date,
"accreditation_date" date,
"accreditation_resolution" varchar(1000) COLLATE "default",
"accreditation_valid_date" date,
"accreditation_nr" varchar(1000) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."curriculum" IS 'õppekava';
COMMENT ON COLUMN "public"."curriculum"."is_higher" IS 'õppekava liik: kutseõppekava (false) või kõrgharidusõppe õppekava (true)';
COMMENT ON COLUMN "public"."curriculum"."name_et" IS 'õppekava nimi e.k.';
COMMENT ON COLUMN "public"."curriculum"."state_curriculum_id" IS 'viide riiklikule õppekavale';
COMMENT ON COLUMN "public"."curriculum"."name_en" IS 'õppekava nimi i.k.';
COMMENT ON COLUMN "public"."curriculum"."name_ru" IS 'õppekava nimi v.k.';
COMMENT ON COLUMN "public"."curriculum"."code" IS 'õppekava kood sisemiseks kasutamiseks';
COMMENT ON COLUMN "public"."curriculum"."consecution_code" IS 'õppekava järgnevus: 1) esmaõppe õppekava 2) jätkuõppe õppekava 0) puudub';
COMMENT ON COLUMN "public"."curriculum"."orig_study_level_code" IS 'õppeastme kklassifikaator, nt 412, 511 jne';
COMMENT ON COLUMN "public"."curriculum"."is_occupation" IS 'kas omandatakse vähemalt 1 kutse: false - ei omandata true - omandatakse';
COMMENT ON COLUMN "public"."curriculum"."isced_class_code" IS 'ISCED 2013 klassifikaator, õppekavarühma kood';
COMMENT ON COLUMN "public"."curriculum"."mer_code" IS 'HTM või EHISe kood';
COMMENT ON COLUMN "public"."curriculum"."approval" IS 'õppekava õppeasutuse skinnitamise kp';
COMMENT ON COLUMN "public"."curriculum"."approval_dok_nr" IS 'õppekava õppeasutuses kinnitamise dok. nr';
COMMENT ON COLUMN "public"."curriculum"."outcomes_et" IS 'õpiväljundid';
COMMENT ON COLUMN "public"."curriculum"."outcomes_en" IS 'õpiväljundid i.k.';
COMMENT ON COLUMN "public"."curriculum"."sturcture" IS 'õppekava struktuur';
COMMENT ON COLUMN "public"."curriculum"."admission_requirements_et" IS 'nõuded õpingute alustamiseks  või juurdepääsu tingimused';
COMMENT ON COLUMN "public"."curriculum"."admission_requirements_en" IS 'nõuded õpingute alustamiseks i.k. või juurdepääsu tingimused';
COMMENT ON COLUMN "public"."curriculum"."graduation_requirements_et" IS 'nõuded õpingute lõpetamiseks või lõpetamistingimused ';
COMMENT ON COLUMN "public"."curriculum"."graduation_requirements_en" IS 'nõuded õpingute lõpetamiseks i.k. või lõpetamistingimused ';
COMMENT ON COLUMN "public"."curriculum"."credits" IS 'õppekava maht EKAP/EAP';
COMMENT ON COLUMN "public"."curriculum"."study_period" IS 'õppeaeg kuudes';
COMMENT ON COLUMN "public"."curriculum"."draft_text" IS 'õppekava koostamise alus tekstina';
COMMENT ON COLUMN "public"."curriculum"."specialization" IS 'spetsialiseerumise võimalused või -	valikuvõimalused õppekava läbimiseks';
COMMENT ON COLUMN "public"."curriculum"."is_joint" IS 'kas tegemist on ühisõppekavaga false - ei true - jah';
COMMENT ON COLUMN "public"."curriculum"."optional_study_credits" IS 'valikõpingute maht kokku';
COMMENT ON COLUMN "public"."curriculum"."practice_description" IS 'praktika kirjeldus';
COMMENT ON COLUMN "public"."curriculum"."final_exam_description" IS 'lõpueksami lühikirjeldus';
COMMENT ON COLUMN "public"."curriculum"."optional_study_descr" IS 'valikõpingute valimise võimalused';
COMMENT ON COLUMN "public"."curriculum"."ehis_changed" IS 'EHISe staatuse mutumise viimane kuupäev';
COMMENT ON COLUMN "public"."curriculum"."description" IS 'märkused';
COMMENT ON COLUMN "public"."curriculum"."ehis_status_code" IS 'õppekava EHISe staatus';
COMMENT ON COLUMN "public"."curriculum"."contact_person" IS 'kontaktisiku nimi, telefon, e-mail';
COMMENT ON COLUMN "public"."curriculum"."valid_from" IS 'kehtivuse algus';
COMMENT ON COLUMN "public"."curriculum"."valid_thru" IS 'kehtivuse lõpp';
COMMENT ON COLUMN "public"."curriculum"."status_code" IS 'õppekava staatus, viide klassifikaatorile';
COMMENT ON COLUMN "public"."curriculum"."joint_mentor_code" IS 'ühisõppekava hoidja, täidetakse juhul kui tegemist on ühisõppekavaga ja on olemas vähemalt 1 mitte välismaine partner, viide ehise koolide klassifikaatorile, valida saab kas enda õppeasutuse või eelnevalt sisestatud partnerite hulgast';
COMMENT ON COLUMN "public"."curriculum"."school_id" IS 'õppekavaga seotud nö peaõppeasutus';
COMMENT ON COLUMN "public"."curriculum"."draft_code" IS 'koostamise alus, viide õppekava loomise aluse klassifikaatorile: tööandjate toetuskiri, riiklik õppekava, kutsestandard, puudub';
COMMENT ON COLUMN "public"."curriculum"."name_genitive_en" IS 'Õppekava nimetus omastavas inglise keeles';
COMMENT ON COLUMN "public"."curriculum"."group_code" IS 'õppekava grupp, klassifikaator OPPEKAVAGRUPP';
COMMENT ON COLUMN "public"."curriculum"."language_description" IS 'õppekeele lisainfo';
COMMENT ON COLUMN "public"."curriculum"."other_languages" IS 'Õpiväljundite saavutamiseks vajalikud teised keeled ';
COMMENT ON COLUMN "public"."curriculum"."objectives_et" IS 'õppekava eesmärgid eesti keeles';
COMMENT ON COLUMN "public"."curriculum"."objectives_en" IS 'õppekava eesmärgid inglise keeles';
COMMENT ON COLUMN "public"."curriculum"."add_info" IS 'täiendav informatsioon';
COMMENT ON COLUMN "public"."curriculum"."MER_reg_date" IS 'Õppekava registreerimine HTMis ';
COMMENT ON COLUMN "public"."curriculum"."accreditation_date" IS 'Õppekava akrediteerimise kuupäev';
COMMENT ON COLUMN "public"."curriculum"."accreditation_resolution" IS 'Õppekava akrediteerimise otsus';
COMMENT ON COLUMN "public"."curriculum"."accreditation_valid_date" IS 'Õppekava akrediteerimise kehtivuskuupäev';
COMMENT ON COLUMN "public"."curriculum"."accreditation_nr" IS 'Õppekava akrediteerimise otsuse number';

-- ----------------------------
-- Table structure for curriculum_department
-- ----------------------------
DROP TABLE IF EXISTS "public"."curriculum_department";
CREATE TABLE "public"."curriculum_department" (
"id" int4 DEFAULT nextval('curriculum_department_id_seq'::regclass) NOT NULL,
"curriculum_id" int4 NOT NULL,
"inserted" timestamp(6) DEFAULT now() NOT NULL,
"school_department_id" int4 NOT NULL,
"changed" timestamp(6),
"version" int4,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."curriculum_department" IS 'õppekava seos õppeasutuse struktuuriüksusega';
COMMENT ON COLUMN "public"."curriculum_department"."curriculum_id" IS 'viide õppekavale';
COMMENT ON COLUMN "public"."curriculum_department"."school_department_id" IS 'viide struktuuriüksusele';

-- ----------------------------
-- Table structure for curriculum_files
-- ----------------------------
DROP TABLE IF EXISTS "public"."curriculum_files";
CREATE TABLE "public"."curriculum_files" (
"id" int4 DEFAULT nextval('curriculum_files_id_seq'::regclass) NOT NULL,
"is_ehis" bool NOT NULL,
"send_ehis" bool NOT NULL,
"ehis_file_code" varchar(100) COLLATE "default" NOT NULL,
"ois_file_id" int4 NOT NULL,
"curriculum_id" int4 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."curriculum_files"."is_ehis" IS 'kas EHISest tulnuv või ÕISi käsitsi üles laetud: false - käsitsi üles laetud true - EHISest tulnud';
COMMENT ON COLUMN "public"."curriculum_files"."send_ehis" IS 'kas fail saadetakse EHISesse või mitte false - ei saadeta EHIS - saadetakse';
COMMENT ON COLUMN "public"."curriculum_files"."ehis_file_code" IS 'EHISe faili liikide klassifikaator';
COMMENT ON COLUMN "public"."curriculum_files"."ois_file_id" IS 'viide failile';
COMMENT ON COLUMN "public"."curriculum_files"."curriculum_id" IS 'viide õppekavale';

-- ----------------------------
-- Table structure for curriculum_grade
-- ----------------------------
DROP TABLE IF EXISTS "public"."curriculum_grade";
CREATE TABLE "public"."curriculum_grade" (
"id" int8 DEFAULT nextval('curriculum_grade_id_seq'::regclass) NOT NULL,
"curriculum_id" int8 NOT NULL,
"name_et" varchar(255) COLLATE "default" NOT NULL,
"name_en" varchar(255) COLLATE "default" NOT NULL,
"name_genitive_et" varchar(255) COLLATE "default",
"ehis_grade_code" varchar(100) COLLATE "default" NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
"version" int4 NOT NULL
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."curriculum_grade" IS 'õppekava kraadid';
COMMENT ON COLUMN "public"."curriculum_grade"."curriculum_id" IS 'viide õppekavale';
COMMENT ON COLUMN "public"."curriculum_grade"."name_et" IS 'kraadi nimi e.k.';
COMMENT ON COLUMN "public"."curriculum_grade"."name_en" IS 'kraadi nimi i.k.';
COMMENT ON COLUMN "public"."curriculum_grade"."name_genitive_et" IS 'kraadi nimi omastavas';
COMMENT ON COLUMN "public"."curriculum_grade"."ehis_grade_code" IS 'EHISe kraad,viide klassifikaatorile (AKAD_KRAAD)';

-- ----------------------------
-- Table structure for curriculum_joint_partners
-- ----------------------------
DROP TABLE IF EXISTS "public"."curriculum_joint_partners";
CREATE TABLE "public"."curriculum_joint_partners" (
"id" int4 DEFAULT nextval('curriculum_joint_partners_id_seq'::regclass) NOT NULL,
"is_abroad" bool NOT NULL,
"contract_et" varchar(1000) COLLATE "default" NOT NULL,
"contract_en" varchar(1000) COLLATE "default",
"supervisor" varchar(255) COLLATE "default",
"name_et" varchar(255) COLLATE "default",
"name_en" varchar(255) COLLATE "default",
"curriculum_id" int4 NOT NULL,
"ehis_school_code" varchar(100) COLLATE "default",
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."curriculum_joint_partners"."id" IS 'ühisõppekava partnerid';
COMMENT ON COLUMN "public"."curriculum_joint_partners"."is_abroad" IS 'kas partner on eesti või välismaa kool false - eesti kool true - välismaa';
COMMENT ON COLUMN "public"."curriculum_joint_partners"."contract_et" IS 'koostöölepingu info';
COMMENT ON COLUMN "public"."curriculum_joint_partners"."contract_en" IS 'koostöölepingu info i.k.';
COMMENT ON COLUMN "public"."curriculum_joint_partners"."supervisor" IS 'vastutav isik';
COMMENT ON COLUMN "public"."curriculum_joint_partners"."name_et" IS 'partnerkooli nimi, sisestatakse ainult juhul kui is_abroad=true';
COMMENT ON COLUMN "public"."curriculum_joint_partners"."name_en" IS 'partnerkooli nimi i.k., sisestatakse ainult juhul kui is_abroad=true';
COMMENT ON COLUMN "public"."curriculum_joint_partners"."curriculum_id" IS 'viide õppekavale';
COMMENT ON COLUMN "public"."curriculum_joint_partners"."ehis_school_code" IS 'viide eesti õppeasutuste klassifikaatorile';

-- ----------------------------
-- Table structure for curriculum_module
-- ----------------------------
DROP TABLE IF EXISTS "public"."curriculum_module";
CREATE TABLE "public"."curriculum_module" (
"id" int4 DEFAULT nextval('curriculum_module_id_seq'::regclass) NOT NULL,
"name_et" varchar(255) COLLATE "default" NOT NULL,
"name_en" varchar(255) COLLATE "default",
"credits" numeric(4,1) NOT NULL,
"objectives_et" varchar(10000) COLLATE "default" NOT NULL,
"objectives_en" varchar(10000) COLLATE "default",
"outcomes_et" varchar(10000) COLLATE "default" NOT NULL,
"outcomes_en" varchar(10000) COLLATE "default",
"inserted" timestamp(6) NOT NULL,
"module_code" varchar(100) COLLATE "default" NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"curriculum_id" int4 NOT NULL,
"is_practice" bool NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."curriculum_module" IS 'õppekava moodulid';
COMMENT ON COLUMN "public"."curriculum_module"."name_et" IS 'mooduli nimetus';
COMMENT ON COLUMN "public"."curriculum_module"."name_en" IS 'mooduli nimetus i.k.';
COMMENT ON COLUMN "public"."curriculum_module"."credits" IS 'mooduli maht EKAP';
COMMENT ON COLUMN "public"."curriculum_module"."objectives_et" IS 'mooduli eesmärk';
COMMENT ON COLUMN "public"."curriculum_module"."objectives_en" IS 'mooduli eesmärk i.k.';
COMMENT ON COLUMN "public"."curriculum_module"."outcomes_et" IS 'mooduli õpiväljundid';
COMMENT ON COLUMN "public"."curriculum_module"."outcomes_en" IS 'mooduli õpiväljundid i.k.';
COMMENT ON COLUMN "public"."curriculum_module"."module_code" IS 'kutseõppekava mooduli klassifikaator';
COMMENT ON COLUMN "public"."curriculum_module"."is_practice" IS 'kas tegemist on praktikaga false - ei ole praktika moodul true - praktika moodul';

-- ----------------------------
-- Table structure for curriculum_module_competence
-- ----------------------------
DROP TABLE IF EXISTS "public"."curriculum_module_competence";
CREATE TABLE "public"."curriculum_module_competence" (
"id" int4 DEFAULT nextval('curriculum_module_competence_id_seq'::regclass) NOT NULL,
"curriculum_module_id" int4 NOT NULL,
"competence_code" varchar(100) COLLATE "default" NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."curriculum_module_competence" IS 'õppekava mooduli kompetentsid';
COMMENT ON COLUMN "public"."curriculum_module_competence"."curriculum_module_id" IS 'viide õppekava moodulile';
COMMENT ON COLUMN "public"."curriculum_module_competence"."competence_code" IS 'viide kompetentside klassifikaatorile';

-- ----------------------------
-- Table structure for curriculum_module_occupation
-- ----------------------------
DROP TABLE IF EXISTS "public"."curriculum_module_occupation";
CREATE TABLE "public"."curriculum_module_occupation" (
"id" int4 DEFAULT nextval('curriculum_module_occupation_id_seq'::regclass) NOT NULL,
"curriculum_module_id" int4 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"occupation_code" varchar(100) COLLATE "default" NOT NULL,
"changed" timestamp(6),
"version" varchar(50) COLLATE "default" NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."curriculum_module_occupation" IS 'õppekava mooduli ja õppekava kutse/spetsialiseerumise/osakutse seos';
COMMENT ON COLUMN "public"."curriculum_module_occupation"."curriculum_module_id" IS 'viide õppekava moodulile';
COMMENT ON COLUMN "public"."curriculum_module_occupation"."occupation_code" IS 'viide kutse/osakutse/spetsialiseerumise klassifikaatorile';

-- ----------------------------
-- Table structure for curriculum_occupation
-- ----------------------------
DROP TABLE IF EXISTS "public"."curriculum_occupation";
CREATE TABLE "public"."curriculum_occupation" (
"id" int4 DEFAULT nextval('curriculum_occupation_id_seq'::regclass) NOT NULL,
"curriculum_id" int4 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"occupation_code" varchar(100) COLLATE "default" NOT NULL,
"changed" timestamp(6),
"version" varchar(50) COLLATE "default" NOT NULL,
"is_occupation_grant" bool NOT NULL,
"specialization_list" varchar(1000) COLLATE "default",
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."curriculum_occupation" IS 'õppekava seos kutsete või osakutsetega';
COMMENT ON COLUMN "public"."curriculum_occupation"."occupation_code" IS 'viide kutse või osakutse klaasifikaatorile';
COMMENT ON COLUMN "public"."curriculum_occupation"."is_occupation_grant" IS 'kas õppeasutusel on kutse andmise õigus false - ei ole true - saab anda kutset';
COMMENT ON COLUMN "public"."curriculum_occupation"."specialization_list" IS 'antud kutse raames õpetatavad spetsialiseerumised (spetsialiseerumiste klassifikaatorite koodid eraldatud komadega)';

-- ----------------------------
-- Table structure for curriculum_speciality
-- ----------------------------
DROP TABLE IF EXISTS "public"."curriculum_speciality";
CREATE TABLE "public"."curriculum_speciality" (
"id" int8 DEFAULT nextval('curriculum_speciality_id_seq'::regclass) NOT NULL,
"curriculum_id" int8 NOT NULL,
"name_et" varchar(100) COLLATE "default" NOT NULL,
"name_en" varchar(100) COLLATE "default" NOT NULL,
"credits" numeric(4,1) NOT NULL,
"description" varchar(1000) COLLATE "default",
"occupation_code" varchar(100) COLLATE "default",
"occupation_et" varchar(255) COLLATE "default",
"occupation_en" varchar(255) COLLATE "default",
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"inserted_by" varchar(50) COLLATE "default" NOT NULL,
"changed_by" varchar(50) COLLATE "default",
"version" int4 NOT NULL
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."curriculum_speciality" IS 'õppekava peaerialad';
COMMENT ON COLUMN "public"."curriculum_speciality"."curriculum_id" IS 'viide õppekavale';
COMMENT ON COLUMN "public"."curriculum_speciality"."name_et" IS 'nimi e.k.';
COMMENT ON COLUMN "public"."curriculum_speciality"."name_en" IS 'nimi i.k.';
COMMENT ON COLUMN "public"."curriculum_speciality"."credits" IS 'maht eap-des';
COMMENT ON COLUMN "public"."curriculum_speciality"."description" IS 'lisainfo';
COMMENT ON COLUMN "public"."curriculum_speciality"."occupation_code" IS 'antavkutse, klassifikaator (KUTSE)';
COMMENT ON COLUMN "public"."curriculum_speciality"."occupation_et" IS 'Akadeemilisele õiendile trükitav kutse eesti keeles';
COMMENT ON COLUMN "public"."curriculum_speciality"."occupation_en" IS 'Akadeemilisele õiendile trükitav kutse inglise keeles ';

-- ----------------------------
-- Table structure for curriculum_study_form
-- ----------------------------
DROP TABLE IF EXISTS "public"."curriculum_study_form";
CREATE TABLE "public"."curriculum_study_form" (
"id" int4 DEFAULT nextval('curriculum_study_form_id_seq'::regclass) NOT NULL,
"curriculum_id" int4 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"study_form_code" varchar(100) COLLATE "default" NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."curriculum_study_form" IS 'õppekava õppevormid';
COMMENT ON COLUMN "public"."curriculum_study_form"."curriculum_id" IS 'viide õppekavale';
COMMENT ON COLUMN "public"."curriculum_study_form"."study_form_code" IS 'viide õppevormi klassifikaatorile';

-- ----------------------------
-- Table structure for curriculum_study_lang
-- ----------------------------
DROP TABLE IF EXISTS "public"."curriculum_study_lang";
CREATE TABLE "public"."curriculum_study_lang" (
"id" int4 DEFAULT nextval('curriculum_study_lang_id_seq'::regclass) NOT NULL,
"curriculum_id" int4 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"study_lang_code" varchar(100) COLLATE "default" NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."curriculum_study_lang" IS 'õppekava õppekeeled';
COMMENT ON COLUMN "public"."curriculum_study_lang"."curriculum_id" IS 'viide õppekavale';
COMMENT ON COLUMN "public"."curriculum_study_lang"."study_lang_code" IS 'viide õppekeele klassifikaatorile';

-- ----------------------------
-- Table structure for ois_file
-- ----------------------------
DROP TABLE IF EXISTS "public"."ois_file";
CREATE TABLE "public"."ois_file" (
"id" int4 DEFAULT nextval('ois_file_id_seq'::regclass) NOT NULL,
"fdata" bytea NOT NULL,
"fname" varchar(1000) COLLATE "default" NOT NULL,
"ftype" varchar(100) COLLATE "default" NOT NULL,
"fdescription" varchar(1000) COLLATE "default",
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."ois_file" IS 'Failide tabel';
COMMENT ON COLUMN "public"."ois_file"."fdata" IS 'üles laetud fail';
COMMENT ON COLUMN "public"."ois_file"."fname" IS 'faili nimi (originaal)';
COMMENT ON COLUMN "public"."ois_file"."ftype" IS 'faili andmeüüp, nt  application/json application/x-www-form-urlencoded application/pdf multipart/form-data text/html image/png image/jpeg image/bmp image/gif';
COMMENT ON COLUMN "public"."ois_file"."fdescription" IS 'täiendav kirjeldus';

-- ----------------------------
-- Table structure for person
-- ----------------------------
DROP TABLE IF EXISTS "public"."person";
CREATE TABLE "public"."person" (
"id" int4 DEFAULT nextval('person_id_seq'::regclass) NOT NULL,
"firstname" varchar(100) COLLATE "default",
"lastname" varchar(100) COLLATE "default" NOT NULL,
"idcode" varchar(11) COLLATE "default",
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."person" IS 'isikute tabel';
COMMENT ON COLUMN "public"."person"."firstname" IS 'eesnimi';
COMMENT ON COLUMN "public"."person"."lastname" IS 'perekonnanimi';
COMMENT ON COLUMN "public"."person"."idcode" IS 'eesti isikukood';

-- ----------------------------
-- Table structure for room
-- ----------------------------
DROP TABLE IF EXISTS "public"."room";
CREATE TABLE "public"."room" (
"id" int8 DEFAULT nextval('room_id_seq'::regclass) NOT NULL,
"building_id" int8 NOT NULL,
"name" varchar(255) COLLATE "default",
"code" varchar(20) COLLATE "default" NOT NULL,
"seats" int4,
"is_study" bool NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."room" IS 'ruumid';
COMMENT ON COLUMN "public"."room"."building_id" IS 'viide hoonele';
COMMENT ON COLUMN "public"."room"."name" IS 'ruumi nimetus';
COMMENT ON COLUMN "public"."room"."code" IS 'ruumi kood';
COMMENT ON COLUMN "public"."room"."seats" IS 'kohtade arv';
COMMENT ON COLUMN "public"."room"."is_study" IS 'ruumi kasutatakse õppetöös';

-- ----------------------------
-- Table structure for room_equipment
-- ----------------------------
DROP TABLE IF EXISTS "public"."room_equipment";
CREATE TABLE "public"."room_equipment" (
"id" int8 DEFAULT nextval('room_equipment_id_seq'::regclass) NOT NULL,
"room_id" int8 NOT NULL,
"equipment_code" varchar(100) COLLATE "default" NOT NULL,
"equipment_count" int4 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."room_equipment"."room_id" IS 'viide ruumile';
COMMENT ON COLUMN "public"."room_equipment"."equipment_code" IS 'seade, viide SEADMED klassifikaatorile';
COMMENT ON COLUMN "public"."room_equipment"."equipment_count" IS 'seadmete arv';

-- ----------------------------
-- Table structure for school
-- ----------------------------
DROP TABLE IF EXISTS "public"."school";
CREATE TABLE "public"."school" (
"id" int4 DEFAULT nextval('school_school_id_seq'::regclass) NOT NULL,
"name_et" varchar(255) COLLATE "default" NOT NULL,
"name_en" varchar(255) COLLATE "default" NOT NULL,
"email" varchar(255) COLLATE "default" NOT NULL,
"logo_f" varchar(255) COLLATE "default",
"version" int4 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"code" varchar(10) COLLATE "default",
"ois_file_id" int4,
"ehis_school_code" varchar(100) COLLATE "default" NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default",
"address" varchar(1000) COLLATE "default",
"phone" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."school" IS 'HÕISiga liitunud õppeasutused';
COMMENT ON COLUMN "public"."school"."logo_f" IS 'logo faili nimi';
COMMENT ON COLUMN "public"."school"."code" IS 'kooli lühend';
COMMENT ON COLUMN "public"."school"."ois_file_id" IS 'viide logo file''le';
COMMENT ON COLUMN "public"."school"."phone" IS 'telefon';

-- ----------------------------
-- Table structure for school_department
-- ----------------------------
DROP TABLE IF EXISTS "public"."school_department";
CREATE TABLE "public"."school_department" (
"id" int4 DEFAULT nextval('school_department_id_seq'::regclass) NOT NULL,
"name_et" varchar(255) COLLATE "default" NOT NULL,
"name_en" varchar(255) COLLATE "default",
"code" varchar(50) COLLATE "default",
"school_id" int4 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"parent_school_department_id" int4,
"changed" timestamp(6),
"version" int4 NOT NULL,
"valid_from" date NOT NULL,
"valid_thru" date,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."school_department" IS 'õppeasutuse struktuuriüksused';
COMMENT ON COLUMN "public"."school_department"."name_et" IS 'nimi';
COMMENT ON COLUMN "public"."school_department"."name_en" IS 'nimi i.k.';
COMMENT ON COLUMN "public"."school_department"."code" IS 'struktuuriüksuse kood';
COMMENT ON COLUMN "public"."school_department"."school_id" IS 'viide õppeasutusele';
COMMENT ON COLUMN "public"."school_department"."parent_school_department_id" IS 'viide ülemstruktuuriüksusele';
COMMENT ON COLUMN "public"."school_department"."valid_from" IS 'kehtiv alates';
COMMENT ON COLUMN "public"."school_department"."valid_thru" IS 'kehtiv kuni';

-- ----------------------------
-- Table structure for school_study_level
-- ----------------------------
DROP TABLE IF EXISTS "public"."school_study_level";
CREATE TABLE "public"."school_study_level" (
"id" int4 DEFAULT nextval('school_study_level_id_seq'::regclass) NOT NULL,
"school_id" int4 NOT NULL,
"inserted" date NOT NULL,
"study_level_code" varchar(100) COLLATE "default" NOT NULL,
"changed" date,
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."school_study_level" IS 'Õppeasutuse ja õppeastmete vastavus';
COMMENT ON COLUMN "public"."school_study_level"."school_id" IS 'viide koolile';
COMMENT ON COLUMN "public"."school_study_level"."study_level_code" IS 'viide õppeastmele (HARIDUSTASE)';

-- ----------------------------
-- Table structure for state_curriculum
-- ----------------------------
DROP TABLE IF EXISTS "public"."state_curriculum";
CREATE TABLE "public"."state_curriculum" (
"id" int4 DEFAULT nextval('state_curriculum_state_curriculum_id_seq'::regclass) NOT NULL,
"name_et" varchar(255) COLLATE "default" NOT NULL,
"name_en" varchar(255) COLLATE "default",
"isced_class_code" varchar(100) COLLATE "default",
"objectives_et" text COLLATE "default" NOT NULL,
"objectives_en" text COLLATE "default",
"outcomes_et" text COLLATE "default" NOT NULL,
"outcomes_en" text COLLATE "default",
"admission_requirements_et" text COLLATE "default" NOT NULL,
"admission_requirements_en" text COLLATE "default",
"graduation_requirements_et" varchar(50) COLLATE "default",
"graduation_requirements_en" varchar(50) COLLATE "default",
"credits" numeric(4,1) NOT NULL,
"practice_description" text COLLATE "default",
"state_curr_class_code" varchar(100) COLLATE "default" NOT NULL,
"final_exam_descriptioon" text COLLATE "default",
"optional_study_credits" numeric(4,1) NOT NULL,
"valid_from" date NOT NULL,
"valid_thru" date,
"description" text COLLATE "default",
"riigiteataja_url" varchar(4000) COLLATE "default",
"inserted" timestamp(6) NOT NULL,
"status_code" varchar(100) COLLATE "default",
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."state_curriculum" IS 'State curriculum';
COMMENT ON COLUMN "public"."state_curriculum"."name_et" IS 'state curriculum name in estonian';
COMMENT ON COLUMN "public"."state_curriculum"."name_en" IS 'state curriculum name in english';
COMMENT ON COLUMN "public"."state_curriculum"."isced_class_code" IS 'ISCED 2013 classifier,detailed (sometimes narrow) field value';
COMMENT ON COLUMN "public"."state_curriculum"."objectives_et" IS 'eesmärk eesti keeles';
COMMENT ON COLUMN "public"."state_curriculum"."outcomes_et" IS 'õpiväljundid';
COMMENT ON COLUMN "public"."state_curriculum"."outcomes_en" IS 'õpiväljundid i.k.';
COMMENT ON COLUMN "public"."state_curriculum"."admission_requirements_et" IS 'nõuded õpingute alustamiseks';
COMMENT ON COLUMN "public"."state_curriculum"."admission_requirements_en" IS 'nõuded õpingute alustamiseks i.k.';
COMMENT ON COLUMN "public"."state_curriculum"."graduation_requirements_et" IS 'nõuded õpingute lõpetamiseks ';
COMMENT ON COLUMN "public"."state_curriculum"."credits" IS 'õppekava maht EKAP-des';
COMMENT ON COLUMN "public"."state_curriculum"."practice_description" IS 'praktika kirjeldus';
COMMENT ON COLUMN "public"."state_curriculum"."state_curr_class_code" IS 'viide RÕK EHISe klassifikaatorile';
COMMENT ON COLUMN "public"."state_curriculum"."final_exam_descriptioon" IS 'lõpueksami kirjeldus';
COMMENT ON COLUMN "public"."state_curriculum"."optional_study_credits" IS 'valikõpingute moodulite maht kokku';
COMMENT ON COLUMN "public"."state_curriculum"."valid_from" IS 'kehtivuse algus';
COMMENT ON COLUMN "public"."state_curriculum"."valid_thru" IS 'kehtivuse lõpp';
COMMENT ON COLUMN "public"."state_curriculum"."description" IS 'märkused';
COMMENT ON COLUMN "public"."state_curriculum"."status_code" IS 'riikliku õppekava staatus';

-- ----------------------------
-- Table structure for state_curriculum_module
-- ----------------------------
DROP TABLE IF EXISTS "public"."state_curriculum_module";
CREATE TABLE "public"."state_curriculum_module" (
"id" int4 DEFAULT nextval('state_curriculum_module_state_curriculum_module_id_seq'::regclass) NOT NULL,
"state_curriculum_id" int4 NOT NULL,
"module_code" varchar(100) COLLATE "default" NOT NULL,
"name_et" varchar(255) COLLATE "default" NOT NULL,
"name_en" varchar(255) COLLATE "default",
"credits" numeric(4,1) NOT NULL,
"objectives_et" text COLLATE "default" NOT NULL,
"objectives_en" text COLLATE "default",
"assessments_et" text COLLATE "default" NOT NULL,
"assessments_en" text COLLATE "default",
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."state_curriculum_module" IS 'riikliku õppekava moodulid';
COMMENT ON COLUMN "public"."state_curriculum_module"."state_curriculum_id" IS 'viide riiklikule õppekavale';
COMMENT ON COLUMN "public"."state_curriculum_module"."module_code" IS 'mooduli liik, klassifikaator';
COMMENT ON COLUMN "public"."state_curriculum_module"."name_et" IS 'mooduli nimetus eesti keeles';
COMMENT ON COLUMN "public"."state_curriculum_module"."name_en" IS 'mooduli niemtus i.k.';
COMMENT ON COLUMN "public"."state_curriculum_module"."credits" IS 'mooduli maht EKAP';
COMMENT ON COLUMN "public"."state_curriculum_module"."objectives_et" IS 'mooduli eesmärk';
COMMENT ON COLUMN "public"."state_curriculum_module"."objectives_en" IS 'mooduli eesmärk i.k.';
COMMENT ON COLUMN "public"."state_curriculum_module"."assessments_et" IS 'hindamiskriteeriumid';
COMMENT ON COLUMN "public"."state_curriculum_module"."assessments_en" IS 'hindamiskriteeriumid i.k.';

-- ----------------------------
-- Table structure for state_curriculum_module_occupation
-- ----------------------------
DROP TABLE IF EXISTS "public"."state_curriculum_module_occupation";
CREATE TABLE "public"."state_curriculum_module_occupation" (
"id" int4 DEFAULT nextval('state_curriculum_module_occup_state_curriculum_module_occup_seq'::regclass) NOT NULL,
"state_curriculum_module_id" int4 NOT NULL,
"occupation_code" varchar(100) COLLATE "default" NOT NULL,
"type" char(1) COLLATE "default" NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."state_curriculum_module_occupation" IS 'riikliku õppekava moodulite ja kutsete/osakutsete/spetsialiseerumiste seos';
COMMENT ON COLUMN "public"."state_curriculum_module_occupation"."occupation_code" IS 'kutse, osakutse või spetsialiseerumine';
COMMENT ON COLUMN "public"."state_curriculum_module_occupation"."type" IS 'liik (äkki läheb vaja) O - osakutse K - kutse S - spetsialiseerumine';

-- ----------------------------
-- Table structure for state_curriculum_module_outcomes
-- ----------------------------
DROP TABLE IF EXISTS "public"."state_curriculum_module_outcomes";
CREATE TABLE "public"."state_curriculum_module_outcomes" (
"id" int4 DEFAULT nextval('state_curriculum_module_outco_state_curriculum_module_outco_seq'::regclass) NOT NULL,
"outcomes_et" varchar(4000) COLLATE "default" NOT NULL,
"state_curriculum_module_id" int4 NOT NULL,
"outcomes_en" varchar(4000) COLLATE "default",
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."state_curriculum_module_outcomes"."outcomes_et" IS 'õpiväljund eesti keeles';
COMMENT ON COLUMN "public"."state_curriculum_module_outcomes"."outcomes_en" IS 'õpiväljund i.k.';

-- ----------------------------
-- Table structure for state_curriculum_occupation
-- ----------------------------
DROP TABLE IF EXISTS "public"."state_curriculum_occupation";
CREATE TABLE "public"."state_curriculum_occupation" (
"id" int4 DEFAULT nextval('state_curriculum_occupation_state_curriculum_occupation_id_seq'::regclass) NOT NULL,
"state_curriculum_id" int4 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"occupation_code" varchar(100) COLLATE "default" NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."state_curriculum_occupation" IS 'riikliku õppekava kutsed';
COMMENT ON COLUMN "public"."state_curriculum_occupation"."state_curriculum_id" IS 'riikliku õppekava id';
COMMENT ON COLUMN "public"."state_curriculum_occupation"."occupation_code" IS 'kutse klassifikaatori kood';

-- ----------------------------
-- Table structure for subject
-- ----------------------------
DROP TABLE IF EXISTS "public"."subject";
CREATE TABLE "public"."subject" (
"id" int4 DEFAULT nextval('subject_id_seq'::regclass) NOT NULL,
"school_id" int4 NOT NULL,
"code" varchar(20) COLLATE "default" NOT NULL,
"name_et" varchar(255) COLLATE "default" NOT NULL,
"name_en" varchar(255) COLLATE "default" NOT NULL,
"description" varchar(4000) COLLATE "default",
"credits" numeric(4,1) NOT NULL,
"assessment_code" varchar(100) COLLATE "default" NOT NULL,
"assessment_descr" varchar(10000) COLLATE "default",
"objectives_et" varchar(10000) COLLATE "default" NOT NULL,
"objectives_en" varchar(10000) COLLATE "default" NOT NULL,
"outcomes_et" varchar(10000) COLLATE "default" NOT NULL,
"outcomes_en" varchar(10000) COLLATE "default" NOT NULL,
"description_et" varchar(4000) COLLATE "default",
"description_en" varchar(4000) COLLATE "default",
"study_literature" varchar(4000) COLLATE "default",
"evaluation_et" varchar(10000) COLLATE "default",
"evaluation_en" varchar(10000) COLLATE "default",
"independent_study_et" varchar(4000) COLLATE "default",
"independent_study_en" varchar(4000) COLLATE "default",
"additional_info" varchar(4000) COLLATE "default",
"school_department_id" int4,
"status_code" varchar(100) COLLATE "default" NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."subject" IS 'ainete tabel';
COMMENT ON COLUMN "public"."subject"."school_id" IS 'viide õppeasutusele';
COMMENT ON COLUMN "public"."subject"."code" IS 'õppeaine kood';
COMMENT ON COLUMN "public"."subject"."name_et" IS 'nimetus e.k.';
COMMENT ON COLUMN "public"."subject"."name_en" IS 'nimetus i.k.';
COMMENT ON COLUMN "public"."subject"."assessment_code" IS 'hindamisviis, klassifikaator HINDAMISVIIS';
COMMENT ON COLUMN "public"."subject"."assessment_descr" IS 'hindamisviisi kirjeldus';
COMMENT ON COLUMN "public"."subject"."objectives_et" IS 'eesmärgid e.k.';
COMMENT ON COLUMN "public"."subject"."objectives_en" IS 'eesmärgid i.k.';
COMMENT ON COLUMN "public"."subject"."outcomes_et" IS 'õpiväljundid e.k.';
COMMENT ON COLUMN "public"."subject"."outcomes_en" IS 'õpiväljundid i.k.';
COMMENT ON COLUMN "public"."subject"."description_et" IS 'sisu e.k.';
COMMENT ON COLUMN "public"."subject"."description_en" IS 'sisu e.n.';
COMMENT ON COLUMN "public"."subject"."study_literature" IS 'kirjandus';
COMMENT ON COLUMN "public"."subject"."evaluation_et" IS 'hindamiskriteeriumid e. k.';
COMMENT ON COLUMN "public"."subject"."evaluation_en" IS 'hindamiskriteeriumid i. k.';
COMMENT ON COLUMN "public"."subject"."independent_study_et" IS 'iseseisev töö e. k.';
COMMENT ON COLUMN "public"."subject"."independent_study_en" IS 'iseseisev töö i. k.';
COMMENT ON COLUMN "public"."subject"."additional_info" IS 'lisainfo';
COMMENT ON COLUMN "public"."subject"."school_department_id" IS 'viide õppeasutuse struktuuriüksusele';
COMMENT ON COLUMN "public"."subject"."status_code" IS 'staatus, klassifikaator AINESTAATUS';

-- ----------------------------
-- Table structure for subject_connect
-- ----------------------------
DROP TABLE IF EXISTS "public"."subject_connect";
CREATE TABLE "public"."subject_connect" (
"id" int4 DEFAULT nextval('subject_connect_id_seq'::regclass) NOT NULL,
"primary_subject_id" int4 NOT NULL,
"connect_subject_id" int4 NOT NULL,
"connection_code" varchar(100) COLLATE "default" NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."subject_connect" IS 'ainete seosed: eeldusained (kohustuslikud ja soovituslikud) asendusained';
COMMENT ON COLUMN "public"."subject_connect"."primary_subject_id" IS 'viide peaainele ehk aine, mille kohta sisestatakse eeldus- ja asendusained';
COMMENT ON COLUMN "public"."subject_connect"."connect_subject_id" IS 'viide eeldus- või asendusainele';
COMMENT ON COLUMN "public"."subject_connect"."connection_code" IS 'seose liik, klassifikaator (AINESEOS)';

-- ----------------------------
-- Table structure for subject_lang
-- ----------------------------
DROP TABLE IF EXISTS "public"."subject_lang";
CREATE TABLE "public"."subject_lang" (
"id" int4 DEFAULT nextval('subject_lang_id_seq'::regclass) NOT NULL,
"subject_id" int4 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"lang_code" varchar(100) COLLATE "default" NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."subject_lang" IS 'õppeaine õppetöö keeled';
COMMENT ON COLUMN "public"."subject_lang"."subject_id" IS 'viide õppeainele';
COMMENT ON COLUMN "public"."subject_lang"."lang_code" IS 'viide õppekeelele, klassifikaator (OPPEKEEL)';

-- ----------------------------
-- Table structure for teacher_occupation
-- ----------------------------
DROP TABLE IF EXISTS "public"."teacher_occupation";
CREATE TABLE "public"."teacher_occupation" (
"id" int8 DEFAULT nextval('teacher_occupation_id_seq'::regclass) NOT NULL,
"occupation_et" varchar(100) COLLATE "default" NOT NULL,
"school_id" int4 NOT NULL,
"occupation_en" varchar(100) COLLATE "default" NOT NULL,
"is_valid" bool NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."teacher_occupation" IS 'ühe õppeasutuse õpetaja ametid';
COMMENT ON COLUMN "public"."teacher_occupation"."occupation_et" IS 'amet eesti keeles';
COMMENT ON COLUMN "public"."teacher_occupation"."school_id" IS 'viide õppeasutusele';
COMMENT ON COLUMN "public"."teacher_occupation"."occupation_en" IS 'amet inglise keeles';
COMMENT ON COLUMN "public"."teacher_occupation"."is_valid" IS 'kas amet on kehtiv';

-- ----------------------------
-- Table structure for user_
-- ----------------------------
DROP TABLE IF EXISTS "public"."user_";
CREATE TABLE "public"."user_" (
"id" int4 DEFAULT nextval('user__id_seq'::regclass) NOT NULL,
"school_id" int4,
"person_id" int4 NOT NULL,
"role_code" varchar(100) COLLATE "default" NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."user_" IS 'kasutajate tabel';
COMMENT ON COLUMN "public"."user_"."school_id" IS 'viide õppeasutusele';
COMMENT ON COLUMN "public"."user_"."person_id" IS 'viide isikule';
COMMENT ON COLUMN "public"."user_"."role_code" IS 'kasutaja roll, nt peaadministraator, admin. töötaja jne';

-- ----------------------------
-- Table structure for user_rights
-- ----------------------------
DROP TABLE IF EXISTS "public"."user_rights";
CREATE TABLE "public"."user_rights" (
"id" int4 DEFAULT nextval('user_rights_id_seq'::regclass) NOT NULL,
"user_id" int4 NOT NULL,
"permission_code" varchar(100) COLLATE "default" NOT NULL,
"object_code" varchar(100) COLLATE "default" NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."user_rights" IS 'kasutaja õiguste tabel';
COMMENT ON COLUMN "public"."user_rights"."user_id" IS 'viide kasutajale';
COMMENT ON COLUMN "public"."user_rights"."permission_code" IS 'kasutaja õigus vastava teema raames, nt muuda, vaata, kustuta';
COMMENT ON COLUMN "public"."user_rights"."object_code" IS 'teema kood, nt õppekava, ruumid, klassifikaatorid';

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------
ALTER SEQUENCE "public"."building_id_seq" OWNED BY "building"."id";
ALTER SEQUENCE "public"."curriculum_department_id_seq" OWNED BY "curriculum_department"."id";
ALTER SEQUENCE "public"."curriculum_files_id_seq" OWNED BY "curriculum_files"."id";
ALTER SEQUENCE "public"."curriculum_grade_id_seq" OWNED BY "curriculum_grade"."id";
ALTER SEQUENCE "public"."curriculum_id_seq" OWNED BY "curriculum"."id";
ALTER SEQUENCE "public"."curriculum_joint_partners_id_seq" OWNED BY "curriculum_joint_partners"."id";
ALTER SEQUENCE "public"."curriculum_module_competence_id_seq" OWNED BY "curriculum_module_competence"."id";
ALTER SEQUENCE "public"."curriculum_module_id_seq" OWNED BY "curriculum_module"."id";
ALTER SEQUENCE "public"."curriculum_module_occupation_id_seq" OWNED BY "curriculum_module_occupation"."id";
ALTER SEQUENCE "public"."curriculum_occupation_id_seq" OWNED BY "curriculum_occupation"."id";
ALTER SEQUENCE "public"."curriculum_speciality_id_seq" OWNED BY "curriculum_speciality"."id";
ALTER SEQUENCE "public"."curriculum_study_form_id_seq" OWNED BY "curriculum_study_form"."id";
ALTER SEQUENCE "public"."curriculum_study_lang_id_seq" OWNED BY "curriculum_study_lang"."id";
ALTER SEQUENCE "public"."ois_file_id_seq" OWNED BY "ois_file"."id";
ALTER SEQUENCE "public"."person_id_seq" OWNED BY "person"."id";
ALTER SEQUENCE "public"."room_equipment_id_seq" OWNED BY "room_equipment"."id";
ALTER SEQUENCE "public"."room_id_seq" OWNED BY "room"."id";
ALTER SEQUENCE "public"."school_department_id_seq" OWNED BY "school_department"."id";
ALTER SEQUENCE "public"."school_school_id_seq" OWNED BY "school"."id";
ALTER SEQUENCE "public"."school_study_level_id_seq" OWNED BY "school_study_level"."id";
ALTER SEQUENCE "public"."state_curriculum_module_occup_state_curriculum_module_occup_seq" OWNED BY "state_curriculum_module_occupation"."id";
ALTER SEQUENCE "public"."state_curriculum_module_outco_state_curriculum_module_outco_seq" OWNED BY "state_curriculum_module_outcomes"."id";
ALTER SEQUENCE "public"."state_curriculum_module_state_curriculum_module_id_seq" OWNED BY "state_curriculum_module"."id";
ALTER SEQUENCE "public"."state_curriculum_occupation_state_curriculum_occupation_id_seq" OWNED BY "state_curriculum_occupation"."id";
ALTER SEQUENCE "public"."state_curriculum_state_curriculum_id_seq" OWNED BY "state_curriculum"."id";
ALTER SEQUENCE "public"."subject_connect_id_seq" OWNED BY "subject_connect"."id";
ALTER SEQUENCE "public"."subject_id_seq" OWNED BY "subject"."id";
ALTER SEQUENCE "public"."subject_lang_id_seq" OWNED BY "subject_lang"."id";
ALTER SEQUENCE "public"."teacher_occupation_id_seq" OWNED BY "teacher_occupation"."id";
ALTER SEQUENCE "public"."user__id_seq" OWNED BY "user_"."id";
ALTER SEQUENCE "public"."user_rights_id_seq" OWNED BY "user_rights"."id";

-- ----------------------------
-- Indexes structure for table building
-- ----------------------------
CREATE INDEX "IXFK_building_school" ON "public"."building" USING btree ("school_id");

-- ----------------------------
-- Primary Key structure for table building
-- ----------------------------
ALTER TABLE "public"."building" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table classifier
-- ----------------------------
CREATE INDEX "IXFK_classifier_code" ON "public"."classifier" USING btree ("code");
CREATE INDEX "IXFK_classifier_main" ON "public"."classifier" USING btree ("main_class_code");
CREATE INDEX "IXFK_classifier_parent_code" ON "public"."classifier" USING btree ("parent_class_code");

-- ----------------------------
-- Primary Key structure for table classifier
-- ----------------------------
ALTER TABLE "public"."classifier" ADD PRIMARY KEY ("code");

-- ----------------------------
-- Indexes structure for table classifier_connect
-- ----------------------------
CREATE INDEX "IXFK_classifier_connect_classifier" ON "public"."classifier_connect" USING btree ("classifier_code");
CREATE INDEX "IXFK_classifier_connect_classifier_02" ON "public"."classifier_connect" USING btree ("connect_classifier_code");

-- ----------------------------
-- Primary Key structure for table classifier_connect
-- ----------------------------
ALTER TABLE "public"."classifier_connect" ADD PRIMARY KEY ("classifier_code", "connect_classifier_code");

-- ----------------------------
-- Indexes structure for table curriculum
-- ----------------------------
CREATE INDEX "IXFK_curriculum_classifier" ON "public"."curriculum" USING btree ("orig_study_level_code");
CREATE INDEX "IXFK_curriculum_classifier_02" ON "public"."curriculum" USING btree ("joint_mentor_code");
CREATE INDEX "IXFK_curriculum_classifier_ehis" ON "public"."curriculum" USING btree ("ehis_status_code");
CREATE INDEX "IXFK_curriculum_classifier_isced" ON "public"."curriculum" USING btree ("isced_class_code");
CREATE INDEX "IXFK_curriculum_classifier_status" ON "public"."curriculum" USING btree ("status_code");
CREATE INDEX "IXFK_curriculum_school" ON "public"."curriculum" USING btree ("school_id");
CREATE INDEX "IXFK_curriculum_state_curriculum" ON "public"."curriculum" USING btree ("state_curriculum_id");
CREATE INDEX "IXFK_curriculum_classifier_03" ON "public"."curriculum" USING btree ("draft_code");
CREATE INDEX "IXFK_curriculum_consecution" ON "public"."curriculum" USING btree ("consecution_code");

-- ----------------------------
-- Primary Key structure for table curriculum
-- ----------------------------
ALTER TABLE "public"."curriculum" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table curriculum_department
-- ----------------------------
CREATE INDEX "IXFK_curriculum_department_curriculum" ON "public"."curriculum_department" USING btree ("curriculum_id");
CREATE INDEX "IXFK_curriculum_department_school_department" ON "public"."curriculum_department" USING btree ("school_department_id");

-- ----------------------------
-- Uniques structure for table curriculum_department
-- ----------------------------
ALTER TABLE "public"."curriculum_department" ADD UNIQUE ("curriculum_id", "school_department_id");

-- ----------------------------
-- Primary Key structure for table curriculum_department
-- ----------------------------
ALTER TABLE "public"."curriculum_department" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table curriculum_files
-- ----------------------------
CREATE INDEX "IXFK_curriculum_files_classifier" ON "public"."curriculum_files" USING btree ("ehis_file_code");
CREATE INDEX "IXFK_curriculum_files_curriculum" ON "public"."curriculum_files" USING btree ("curriculum_id");
CREATE INDEX "IXFK_curriculum_files_ois_file" ON "public"."curriculum_files" USING btree ("ois_file_id");

-- ----------------------------
-- Primary Key structure for table curriculum_files
-- ----------------------------
ALTER TABLE "public"."curriculum_files" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table curriculum_grade
-- ----------------------------
CREATE INDEX "IXFK_curriculum_grade_classifier" ON "public"."curriculum_grade" USING btree ("ehis_grade_code");
CREATE INDEX "IXFK_curriculum_grade_curriculum" ON "public"."curriculum_grade" USING btree ("curriculum_id");

-- ----------------------------
-- Primary Key structure for table curriculum_grade
-- ----------------------------
ALTER TABLE "public"."curriculum_grade" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table curriculum_joint_partners
-- ----------------------------
CREATE INDEX "IXFK_curriculum_joint_partners_classifier" ON "public"."curriculum_joint_partners" USING btree ("ehis_school_code");
CREATE INDEX "IXFK_curriculum_joint_partners_curriculum" ON "public"."curriculum_joint_partners" USING btree ("curriculum_id");

-- ----------------------------
-- Primary Key structure for table curriculum_joint_partners
-- ----------------------------
ALTER TABLE "public"."curriculum_joint_partners" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table curriculum_module
-- ----------------------------
CREATE INDEX "IXFK_curriculum_module_classifier" ON "public"."curriculum_module" USING btree ("module_code");
CREATE INDEX "IXFK_curriculum_module_curriculum" ON "public"."curriculum_module" USING btree ("curriculum_id");

-- ----------------------------
-- Primary Key structure for table curriculum_module
-- ----------------------------
ALTER TABLE "public"."curriculum_module" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table curriculum_module_competence
-- ----------------------------
CREATE INDEX "IXFK_curriculum_module_competence_classifier" ON "public"."curriculum_module_competence" USING btree ("competence_code");
CREATE INDEX "IXFK_curriculum_module_competence_curriculum_module" ON "public"."curriculum_module_competence" USING btree ("curriculum_module_id");

-- ----------------------------
-- Uniques structure for table curriculum_module_competence
-- ----------------------------
ALTER TABLE "public"."curriculum_module_competence" ADD UNIQUE ("curriculum_module_id", "competence_code");

-- ----------------------------
-- Primary Key structure for table curriculum_module_competence
-- ----------------------------
ALTER TABLE "public"."curriculum_module_competence" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table curriculum_module_occupation
-- ----------------------------
CREATE INDEX "IXFK_curriculum_module_occupation_classifier" ON "public"."curriculum_module_occupation" USING btree ("occupation_code");
CREATE INDEX "IXFK_curriculum_module_occupation_curriculum_module" ON "public"."curriculum_module_occupation" USING btree ("curriculum_module_id");

-- ----------------------------
-- Uniques structure for table curriculum_module_occupation
-- ----------------------------
ALTER TABLE "public"."curriculum_module_occupation" ADD UNIQUE ("curriculum_module_id", "occupation_code");

-- ----------------------------
-- Primary Key structure for table curriculum_module_occupation
-- ----------------------------
ALTER TABLE "public"."curriculum_module_occupation" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table curriculum_occupation
-- ----------------------------
CREATE INDEX "IXFK_curriculum_occupation_classifier" ON "public"."curriculum_occupation" USING btree ("occupation_code");
CREATE INDEX "IXFK_curriculum_occupation_curriculum" ON "public"."curriculum_occupation" USING btree ("curriculum_id");

-- ----------------------------
-- Uniques structure for table curriculum_occupation
-- ----------------------------
ALTER TABLE "public"."curriculum_occupation" ADD UNIQUE ("curriculum_id", "occupation_code");

-- ----------------------------
-- Primary Key structure for table curriculum_occupation
-- ----------------------------
ALTER TABLE "public"."curriculum_occupation" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table curriculum_speciality
-- ----------------------------
CREATE INDEX "IXFK_curriculum_speciality_classifier" ON "public"."curriculum_speciality" USING btree ("occupation_code");
CREATE INDEX "IXFK_curriculum_speciality_curriculum_grade" ON "public"."curriculum_speciality" USING btree ("curriculum_id");

-- ----------------------------
-- Primary Key structure for table curriculum_speciality
-- ----------------------------
ALTER TABLE "public"."curriculum_speciality" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table curriculum_study_form
-- ----------------------------
CREATE INDEX "IXFK_curriculum_study_form_classifier" ON "public"."curriculum_study_form" USING btree ("study_form_code");
CREATE INDEX "IXFK_curriculum_study_form_curriculum" ON "public"."curriculum_study_form" USING btree ("curriculum_id");

-- ----------------------------
-- Uniques structure for table curriculum_study_form
-- ----------------------------
ALTER TABLE "public"."curriculum_study_form" ADD UNIQUE ("curriculum_id", "study_form_code");

-- ----------------------------
-- Primary Key structure for table curriculum_study_form
-- ----------------------------
ALTER TABLE "public"."curriculum_study_form" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table curriculum_study_lang
-- ----------------------------
CREATE INDEX "IXFK_curriculum_study_lang_classifier" ON "public"."curriculum_study_lang" USING btree ("study_lang_code");
CREATE INDEX "IXFK_curriculum_study_lang_curriculum" ON "public"."curriculum_study_lang" USING btree ("curriculum_id");

-- ----------------------------
-- Uniques structure for table curriculum_study_lang
-- ----------------------------
ALTER TABLE "public"."curriculum_study_lang" ADD UNIQUE ("curriculum_id", "study_lang_code");

-- ----------------------------
-- Primary Key structure for table curriculum_study_lang
-- ----------------------------
ALTER TABLE "public"."curriculum_study_lang" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table ois_file
-- ----------------------------
ALTER TABLE "public"."ois_file" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table person
-- ----------------------------
ALTER TABLE "public"."person" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table room
-- ----------------------------
CREATE INDEX "IXFK_room_building" ON "public"."room" USING btree ("building_id");

-- ----------------------------
-- Primary Key structure for table room
-- ----------------------------
ALTER TABLE "public"."room" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table room_equipment
-- ----------------------------
CREATE INDEX "IXFK_room_equipment_classifier" ON "public"."room_equipment" USING btree ("equipment_code");
CREATE INDEX "IXFK_room_equipment_room" ON "public"."room_equipment" USING btree ("room_id");

-- ----------------------------
-- Primary Key structure for table room_equipment
-- ----------------------------
ALTER TABLE "public"."room_equipment" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table school
-- ----------------------------
CREATE INDEX "IXFK_school_OIS_FILE" ON "public"."school" USING btree ("ois_file_id");
CREATE INDEX "IXFK_school_classifier" ON "public"."school" USING btree ("ehis_school_code");

-- ----------------------------
-- Primary Key structure for table school
-- ----------------------------
ALTER TABLE "public"."school" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table school_department
-- ----------------------------
CREATE INDEX "IXFK_school_department_school" ON "public"."school_department" USING btree ("school_id");
CREATE INDEX "IXFK_school_department_school_department" ON "public"."school_department" USING btree ("parent_school_department_id");

-- ----------------------------
-- Primary Key structure for table school_department
-- ----------------------------
ALTER TABLE "public"."school_department" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table school_study_level
-- ----------------------------
CREATE INDEX "IXFK_school_study_level_classifier" ON "public"."school_study_level" USING btree ("study_level_code");
CREATE INDEX "IXFK_school_study_level_school" ON "public"."school_study_level" USING btree ("school_id");

-- ----------------------------
-- Primary Key structure for table school_study_level
-- ----------------------------
ALTER TABLE "public"."school_study_level" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table state_curriculum
-- ----------------------------
CREATE INDEX "IXFK_state_curriculum_classifier_isced" ON "public"."state_curriculum" USING btree ("isced_class_code");
CREATE INDEX "IXFK_state_curriculum_classifier_state_curr_class" ON "public"."state_curriculum" USING btree ("state_curr_class_code");
CREATE INDEX "IXFK_state_curriculum_classifier_status" ON "public"."state_curriculum" USING btree ("status_code");

-- ----------------------------
-- Primary Key structure for table state_curriculum
-- ----------------------------
ALTER TABLE "public"."state_curriculum" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table state_curriculum_module
-- ----------------------------
CREATE INDEX "IXFK_state_curriculum_module_classifier" ON "public"."state_curriculum_module" USING btree ("module_code");
CREATE INDEX "IXFK_state_curriculum_module_state_curriculum" ON "public"."state_curriculum_module" USING btree ("state_curriculum_id");

-- ----------------------------
-- Primary Key structure for table state_curriculum_module
-- ----------------------------
ALTER TABLE "public"."state_curriculum_module" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table state_curriculum_module_occupation
-- ----------------------------
CREATE INDEX "IXFK_state_curriculum_module_occupation_classifier" ON "public"."state_curriculum_module_occupation" USING btree ("occupation_code");
CREATE INDEX "IXFK_state_curriculum_module_occupation_state_curriculum_module" ON "public"."state_curriculum_module_occupation" USING btree ("state_curriculum_module_id");

-- ----------------------------
-- Checks structure for table state_curriculum_module_occupation
-- ----------------------------
ALTER TABLE "public"."state_curriculum_module_occupation" ADD CHECK ((type = ANY (ARRAY['O'::bpchar, 'K'::bpchar, 'S'::bpchar])));

-- ----------------------------
-- Primary Key structure for table state_curriculum_module_occupation
-- ----------------------------
ALTER TABLE "public"."state_curriculum_module_occupation" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table state_curriculum_module_outcomes
-- ----------------------------
CREATE INDEX "IXFK_state_curriculum_module_outcomes_state_curriculum_module" ON "public"."state_curriculum_module_outcomes" USING btree ("state_curriculum_module_id");

-- ----------------------------
-- Primary Key structure for table state_curriculum_module_outcomes
-- ----------------------------
ALTER TABLE "public"."state_curriculum_module_outcomes" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table state_curriculum_occupation
-- ----------------------------
CREATE INDEX "IXFK_state_curriculum_occupation_classifier" ON "public"."state_curriculum_occupation" USING btree ("occupation_code");
CREATE INDEX "IXFK_state_curriculum_occupation_state_curriculum" ON "public"."state_curriculum_occupation" USING btree ("state_curriculum_id");

-- ----------------------------
-- Primary Key structure for table state_curriculum_occupation
-- ----------------------------
ALTER TABLE "public"."state_curriculum_occupation" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table subject
-- ----------------------------
CREATE INDEX "IXFK_subject_classifier" ON "public"."subject" USING btree ("assessment_code");
CREATE INDEX "IXFK_subject_classifier_02" ON "public"."subject" USING btree ("status_code");
CREATE INDEX "IXFK_subject_school" ON "public"."subject" USING btree ("school_id");
CREATE INDEX "IXFK_subject_school_department" ON "public"."subject" USING btree ("school_department_id");

-- ----------------------------
-- Primary Key structure for table subject
-- ----------------------------
ALTER TABLE "public"."subject" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table subject_connect
-- ----------------------------
CREATE INDEX "IXFK_subject_connect_classifier" ON "public"."subject_connect" USING btree ("connection_code");
CREATE INDEX "IXFK_subject_connect_subject" ON "public"."subject_connect" USING btree ("primary_subject_id");
CREATE INDEX "IXFK_subject_connect_subject_02" ON "public"."subject_connect" USING btree ("connect_subject_id");

-- ----------------------------
-- Primary Key structure for table subject_connect
-- ----------------------------
ALTER TABLE "public"."subject_connect" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table subject_lang
-- ----------------------------
CREATE INDEX "IXFK_subject_lang_classifier" ON "public"."subject_lang" USING btree ("lang_code");
CREATE INDEX "IXFK_subject_lang_subject" ON "public"."subject_lang" USING btree ("subject_id");

-- ----------------------------
-- Primary Key structure for table subject_lang
-- ----------------------------
ALTER TABLE "public"."subject_lang" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table teacher_occupation
-- ----------------------------
CREATE INDEX "IXFK_teacher_occupation_school" ON "public"."teacher_occupation" USING btree ("school_id");

-- ----------------------------
-- Primary Key structure for table teacher_occupation
-- ----------------------------
ALTER TABLE "public"."teacher_occupation" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table user_
-- ----------------------------
CREATE INDEX "IXFK_user__classifier" ON "public"."user_" USING btree ("role_code");
CREATE INDEX "IXFK_user__person" ON "public"."user_" USING btree ("person_id");
CREATE INDEX "IXFK_user__school" ON "public"."user_" USING btree ("school_id");

-- ----------------------------
-- Primary Key structure for table user_
-- ----------------------------
ALTER TABLE "public"."user_" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table user_rights
-- ----------------------------
CREATE INDEX "IXFK_user_rights_classifier" ON "public"."user_rights" USING btree ("permission_code");
CREATE INDEX "IXFK_user_rights_classifier_02" ON "public"."user_rights" USING btree ("object_code");
CREATE INDEX "IXFK_user_rights_user_" ON "public"."user_rights" USING btree ("user_id");

-- ----------------------------
-- Uniques structure for table user_rights
-- ----------------------------
ALTER TABLE "public"."user_rights" ADD UNIQUE ("permission_code", "object_code", "user_id");

-- ----------------------------
-- Primary Key structure for table user_rights
-- ----------------------------
ALTER TABLE "public"."user_rights" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Foreign Key structure for table "public"."building"
-- ----------------------------
ALTER TABLE "public"."building" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."classifier"
-- ----------------------------
ALTER TABLE "public"."classifier" ADD FOREIGN KEY ("main_class_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."classifier" ADD FOREIGN KEY ("parent_class_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."classifier_connect"
-- ----------------------------
ALTER TABLE "public"."classifier_connect" ADD FOREIGN KEY ("connect_classifier_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."classifier_connect" ADD FOREIGN KEY ("classifier_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."classifier_connect" ADD FOREIGN KEY ("main_classifier_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum"
-- ----------------------------
ALTER TABLE "public"."curriculum" ADD FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum" ADD FOREIGN KEY ("draft_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum" ADD FOREIGN KEY ("state_curriculum_id") REFERENCES "public"."state_curriculum" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum" ADD FOREIGN KEY ("consecution_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum" ADD FOREIGN KEY ("orig_study_level_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum" ADD FOREIGN KEY ("joint_mentor_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum" ADD FOREIGN KEY ("ehis_status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum" ADD FOREIGN KEY ("isced_class_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum_department"
-- ----------------------------
ALTER TABLE "public"."curriculum_department" ADD FOREIGN KEY ("school_department_id") REFERENCES "public"."school_department" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_department" ADD FOREIGN KEY ("curriculum_id") REFERENCES "public"."curriculum" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum_files"
-- ----------------------------
ALTER TABLE "public"."curriculum_files" ADD FOREIGN KEY ("ois_file_id") REFERENCES "public"."ois_file" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_files" ADD FOREIGN KEY ("curriculum_id") REFERENCES "public"."curriculum" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_files" ADD FOREIGN KEY ("ehis_file_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum_grade"
-- ----------------------------
ALTER TABLE "public"."curriculum_grade" ADD FOREIGN KEY ("ehis_grade_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_grade" ADD FOREIGN KEY ("curriculum_id") REFERENCES "public"."curriculum" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum_joint_partners"
-- ----------------------------
ALTER TABLE "public"."curriculum_joint_partners" ADD FOREIGN KEY ("curriculum_id") REFERENCES "public"."curriculum" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_joint_partners" ADD FOREIGN KEY ("ehis_school_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum_module"
-- ----------------------------
ALTER TABLE "public"."curriculum_module" ADD FOREIGN KEY ("curriculum_id") REFERENCES "public"."curriculum" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_module" ADD FOREIGN KEY ("module_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum_module_competence"
-- ----------------------------
ALTER TABLE "public"."curriculum_module_competence" ADD FOREIGN KEY ("competence_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_module_competence" ADD FOREIGN KEY ("curriculum_module_id") REFERENCES "public"."curriculum_module" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum_module_occupation"
-- ----------------------------
ALTER TABLE "public"."curriculum_module_occupation" ADD FOREIGN KEY ("curriculum_module_id") REFERENCES "public"."curriculum_module" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_module_occupation" ADD FOREIGN KEY ("occupation_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum_occupation"
-- ----------------------------
ALTER TABLE "public"."curriculum_occupation" ADD FOREIGN KEY ("occupation_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_occupation" ADD FOREIGN KEY ("curriculum_id") REFERENCES "public"."curriculum" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum_speciality"
-- ----------------------------
ALTER TABLE "public"."curriculum_speciality" ADD FOREIGN KEY ("occupation_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_speciality" ADD FOREIGN KEY ("curriculum_id") REFERENCES "public"."curriculum" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum_study_form"
-- ----------------------------
ALTER TABLE "public"."curriculum_study_form" ADD FOREIGN KEY ("study_form_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum_study_lang"
-- ----------------------------
ALTER TABLE "public"."curriculum_study_lang" ADD FOREIGN KEY ("study_lang_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_study_lang" ADD FOREIGN KEY ("curriculum_id") REFERENCES "public"."curriculum" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."room"
-- ----------------------------
ALTER TABLE "public"."room" ADD FOREIGN KEY ("building_id") REFERENCES "public"."building" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."room_equipment"
-- ----------------------------
ALTER TABLE "public"."room_equipment" ADD FOREIGN KEY ("equipment_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."room_equipment" ADD FOREIGN KEY ("room_id") REFERENCES "public"."room" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."school"
-- ----------------------------
ALTER TABLE "public"."school" ADD FOREIGN KEY ("ehis_school_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."school" ADD FOREIGN KEY ("ois_file_id") REFERENCES "public"."ois_file" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."school_department"
-- ----------------------------
ALTER TABLE "public"."school_department" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."school_department" ADD FOREIGN KEY ("parent_school_department_id") REFERENCES "public"."school_department" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."school_study_level"
-- ----------------------------
ALTER TABLE "public"."school_study_level" ADD FOREIGN KEY ("study_level_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."school_study_level" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."state_curriculum"
-- ----------------------------
ALTER TABLE "public"."state_curriculum" ADD FOREIGN KEY ("isced_class_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."state_curriculum" ADD FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."state_curriculum" ADD FOREIGN KEY ("state_curr_class_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."state_curriculum_module"
-- ----------------------------
ALTER TABLE "public"."state_curriculum_module" ADD FOREIGN KEY ("module_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."state_curriculum_module" ADD FOREIGN KEY ("state_curriculum_id") REFERENCES "public"."state_curriculum" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."state_curriculum_module_occupation"
-- ----------------------------
ALTER TABLE "public"."state_curriculum_module_occupation" ADD FOREIGN KEY ("state_curriculum_module_id") REFERENCES "public"."state_curriculum_module" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."state_curriculum_module_occupation" ADD FOREIGN KEY ("occupation_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."state_curriculum_module_outcomes"
-- ----------------------------
ALTER TABLE "public"."state_curriculum_module_outcomes" ADD FOREIGN KEY ("state_curriculum_module_id") REFERENCES "public"."state_curriculum_module" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."state_curriculum_occupation"
-- ----------------------------
ALTER TABLE "public"."state_curriculum_occupation" ADD FOREIGN KEY ("occupation_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."state_curriculum_occupation" ADD FOREIGN KEY ("state_curriculum_id") REFERENCES "public"."state_curriculum" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."subject"
-- ----------------------------
ALTER TABLE "public"."subject" ADD FOREIGN KEY ("assessment_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."subject" ADD FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."subject" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."subject" ADD FOREIGN KEY ("school_department_id") REFERENCES "public"."school_department" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."subject_connect"
-- ----------------------------
ALTER TABLE "public"."subject_connect" ADD FOREIGN KEY ("connect_subject_id") REFERENCES "public"."subject" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."subject_connect" ADD FOREIGN KEY ("primary_subject_id") REFERENCES "public"."subject" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."subject_connect" ADD FOREIGN KEY ("connection_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."subject_lang"
-- ----------------------------
ALTER TABLE "public"."subject_lang" ADD FOREIGN KEY ("subject_id") REFERENCES "public"."subject" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."subject_lang" ADD FOREIGN KEY ("lang_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."teacher_occupation"
-- ----------------------------
ALTER TABLE "public"."teacher_occupation" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."user_"
-- ----------------------------
ALTER TABLE "public"."user_" ADD FOREIGN KEY ("person_id") REFERENCES "public"."person" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."user_" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."user_" ADD FOREIGN KEY ("role_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."user_rights"
-- ----------------------------
ALTER TABLE "public"."user_rights" ADD FOREIGN KEY ("object_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;


--INSERT DATA
\i db_data.sql;
