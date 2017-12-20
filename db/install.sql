\c postgres;

SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE datname = 'hois';

drop database if exists hois;

create database hois; 
\c hois;

CREATE SEQUENCE apel_application_comment_id_seq START 1;
CREATE SEQUENCE apel_application_file_id_seq START 1;
CREATE SEQUENCE apel_application_formal_replaced_subject_or_module_id_seq START 1;
CREATE SEQUENCE apel_application_formal_subject_or_module_id_seq START 1;
CREATE SEQUENCE apel_application_id_seq START 1;
CREATE SEQUENCE apel_application_informal_experience_id_seq START 1;
CREATE SEQUENCE apel_application_informal_subject_or_module_id_seq START 1;
CREATE SEQUENCE apel_application_informal_subject_or_module_outcomes_id_seq START 1;
CREATE SEQUENCE apel_application_record_id_seq START 1;
CREATE SEQUENCE apel_school_id_seq START 1;
CREATE SEQUENCE application_file_id_seq START 1;
CREATE SEQUENCE application_id_seq START 1;
CREATE SEQUENCE application_planned_subject_equivalent _id_seq START 1;
CREATE SEQUENCE application_planned_subject_id_seq START 1;
CREATE SEQUENCE building_id_seq START 1;
CREATE SEQUENCE certificate_id_seq START 1;
CREATE SEQUENCE committee_id_seq START 1;
CREATE SEQUENCE committee_member_id_seq START 1;
CREATE SEQUENCE contract_id_seq START 1;
CREATE SEQUENCE curriculum_department_id_seq START 1;
CREATE SEQUENCE curriculum_files_id_seq START 1;
CREATE SEQUENCE curriculum_grade_id_seq START 1;
CREATE SEQUENCE curriculum_id_seq START 1;
CREATE SEQUENCE curriculum_joint_partners_id_seq START 1;
CREATE SEQUENCE curriculum_module_competence_id_seq START 1;
CREATE SEQUENCE curriculum_module_id_seq START 1;
CREATE SEQUENCE curriculum_module_occupation_id_seq START 1;
CREATE SEQUENCE curriculum_module_outcomes_id_seq START 1;
CREATE SEQUENCE curriculum_occupation_id_seq START 1;
CREATE SEQUENCE curriculum_occupation_speciality_id_seq START 1;
CREATE SEQUENCE curriculum_speciality_id_seq START 1;
CREATE SEQUENCE curriculum_study_form_id_seq START 1;
CREATE SEQUENCE curriculum_study_lang_id_seq START 1;
CREATE SEQUENCE curriculum_version_elective_module_id_seq START 1;
CREATE SEQUENCE curriculum_version_hmodule_id_seq START 1;
CREATE SEQUENCE curriculum_version_hmodule_speciality_id_seq START 1;
CREATE SEQUENCE curriculum_version_hmodule_subject_id_seq START 1;
CREATE SEQUENCE curriculum_version_id_seq START 1;
CREATE SEQUENCE curriculum_version_omodule_capacity_id_seq START 1;
CREATE SEQUENCE curriculum_version_omodule_id_seq START 1;
CREATE SEQUENCE curriculum_version_omodule_outcomes_id_seq START 1;
CREATE SEQUENCE curriculum_version_omodule_theme_capacity_id_seq START 1;
CREATE SEQUENCE curriculum_version_omodule_theme_id_seq START 1;
CREATE SEQUENCE curriculum_version_omodule_year_capacity_id_seq START 1;
CREATE SEQUENCE curriculum_version_speciality_id_seq START 1;
CREATE SEQUENCE declaration_id_seq START 1;
CREATE SEQUENCE declaration_subject_id_seq START 1;
CREATE SEQUENCE directive_coordinator_id_seq START 1;
CREATE SEQUENCE directive_id_seq START 1;
CREATE SEQUENCE directive_student_id_seq START 1;
CREATE SEQUENCE enterprise_id_seq START 1;
CREATE SEQUENCE general_message_id_seq START 1;
CREATE SEQUENCE general_message_target_id_seq START 1;
CREATE SEQUENCE job_id_seq START 1;
CREATE SEQUENCE journal_capacity_id_seq START 1;
CREATE SEQUENCE journal_capacity_type_id_seq START 1;
CREATE SEQUENCE journal_entry_capacity_type_id_seq START 1;
CREATE SEQUENCE journal_entry_id_seq START 1;
CREATE SEQUENCE journal_entry_student_history_id_seq START 1;
CREATE SEQUENCE journal_entry_student_id_seq START 1;
CREATE SEQUENCE journal_id_seq START 1;
CREATE SEQUENCE journal_omodule_theme_id_seq START 1;
CREATE SEQUENCE journal_room_id_seq START 1;
CREATE SEQUENCE journal_student_id_seq START 1;
CREATE SEQUENCE journal_teacher_id_seq START 1;
CREATE SEQUENCE lesson_plan_id_seq START 1;
CREATE SEQUENCE lesson_plan_module_id_seq START 1;
CREATE SEQUENCE lesson_time_building_group_id_seq START 1;
CREATE SEQUENCE lesson_time_building_id_seq START 1;
CREATE SEQUENCE lesson_time_id_seq START 1;
CREATE SEQUENCE log_table_data_id_seq START 1;
CREATE SEQUENCE message_id_seq START 1;
CREATE SEQUENCE message_receiver_id_seq START 1;
CREATE SEQUENCE message_template_id_seq START 1;
CREATE SEQUENCE midterm_task_id_seq START 1;
CREATE SEQUENCE midterm_task_student_result_id_seq START 1;
CREATE SEQUENCE ois_file_id_seq START 1;
CREATE SEQUENCE person_id_seq START 1;
CREATE SEQUENCE practice_journal_entry_id_seq START 1;
CREATE SEQUENCE practice_journal_file_id_seq START 1;
CREATE SEQUENCE practice_journal_id_seq START 1;
CREATE SEQUENCE protocol_id_seq START 1;
CREATE SEQUENCE protocol_student_history_id_seq START 1;
CREATE SEQUENCE protocol_student_id_seq START 1;
CREATE SEQUENCE room_equipment_id_seq START 1;
CREATE SEQUENCE room_id_seq START 1;
CREATE SEQUENCE sais_admission_id_seq START 1;
CREATE SEQUENCE sais_application_grade_id_seq START 1;
CREATE SEQUENCE sais_application_graduated_school_id_seq START 1;
CREATE SEQUENCE sais_application_id_seq START 1;
CREATE SEQUENCE sais_application_other_data_id_seq START 1;
CREATE SEQUENCE scholarschip_terme_course_id_seq START 1;
CREATE SEQUENCE scholarship_application_family_id_seq START 1;
CREATE SEQUENCE scholarship_application_file_id_seq START 1;
CREATE SEQUENCE scholarship_application_id_seq START 1;
CREATE SEQUENCE scholarship_term_curriculum_id_seq START 1;
CREATE SEQUENCE scholarship_term_id_seq START 1;
CREATE SEQUENCE scholarship_term_study_form_id_seq START 1;
CREATE SEQUENCE scholarship_term_study_load_id_seq START 1;
CREATE SEQUENCE school_department_id_seq START 1;
CREATE SEQUENCE school_school_id_seq START 1;
CREATE SEQUENCE school_study_level_id_seq START 1;
CREATE SEQUENCE state_curriculum_module_occupation_state_curriculum_module_occu START 1;
CREATE SEQUENCE state_curriculum_module_occup_state_curriculum_module_occup_seq START 1;
CREATE SEQUENCE state_curriculum_module_outcomes_state_curriculum_module_outcom START 1;
CREATE SEQUENCE state_curriculum_module_outco_state_curriculum_module_outco_seq START 1;
CREATE SEQUENCE state_curriculum_module_state_curriculum_module_id_seq START 1;
CREATE SEQUENCE state_curriculum_occupation_state_curriculum_occupation_id_seq START 1;
CREATE SEQUENCE state_curriculum_state_curriculum_id_seq START 1;
CREATE SEQUENCE student_absence_id_seq START 1;
CREATE SEQUENCE student_curriculum_completion_id_seq START 1;
CREATE SEQUENCE student_group_id_seq START 1;
CREATE SEQUENCE student_higher_result_id_seq START 1;
CREATE SEQUENCE student_history_id_seq START 1;
CREATE SEQUENCE student_id_seq START 1;
CREATE SEQUENCE student_occupation_certificate_id_seq START 1;
CREATE SEQUENCE student_representative_application_id_seq START 1;
CREATE SEQUENCE student_representative_id_seq START 1;
CREATE SEQUENCE student_vocational_result_id_seq START 1;
CREATE SEQUENCE study_period_event_id_seq START 1;
CREATE SEQUENCE study_period_id_seq START 1;
CREATE SEQUENCE study_year_id_seq START 1;
CREATE SEQUENCE study_year_schedule_id_seq START 1;
CREATE SEQUENCE study_year_schedule_legend_id_seq START 1;
CREATE SEQUENCE subject_connect_id_seq START 1;
CREATE SEQUENCE subject_id_seq START 1;
CREATE SEQUENCE subject_lang_id_seq START 1;
CREATE SEQUENCE subject_study_period_capacity_id_seq START 1;
CREATE SEQUENCE subject_study_period_id_seq START 1;
CREATE SEQUENCE subject_study_period_plan_capacity_id_seq START 1;
CREATE SEQUENCE subject_study_period_plan_curriculum_id_seq START 1;
CREATE SEQUENCE subject_study_period_plan_id_seq START 1;
CREATE SEQUENCE subject_study_period_plan_studyform_id_seq START 1;
CREATE SEQUENCE subject_study_period_student_group_id_seq START 1;
CREATE SEQUENCE subject_study_period_teacher_id_seq START 1;
CREATE SEQUENCE teacher_absence_id_seq START 1;
CREATE SEQUENCE teacher_continuing_education_id_seq START 1;
CREATE SEQUENCE teacher_id_seq START 1;
CREATE SEQUENCE teacher_mobility_id_seq START 1;
CREATE SEQUENCE teacher_occupation_id_seq START 1;
CREATE SEQUENCE teacher_position_ehis_id_seq START 1;
CREATE SEQUENCE teacher_qualification_id_seq START 1;
CREATE SEQUENCE timetable_event_id_seq START 1;
CREATE SEQUENCE timetable_event_room_id_seq START 1;
CREATE SEQUENCE timetable_event_teacher_id_seq START 1;
CREATE SEQUENCE timetable_event_time_id_seq START 1;
CREATE SEQUENCE timetable_id_seq START 1;
CREATE SEQUENCE timetable_object_id_seq START 1;
CREATE SEQUENCE timetable_object_student_group_id_seq START 1;
CREATE SEQUENCE user__id_seq START 1;
CREATE SEQUENCE user_rights_id_seq START 1;
CREATE SEQUENCE user_sessions_id_seq START 1;
CREATE SEQUENCE ws_ehis_curriculum_log_id_seq START 1;
CREATE SEQUENCE ws_ehis_student_log_id_seq START 1;
CREATE SEQUENCE ws_ehis_teacher_log_id_seq START 1;
CREATE SEQUENCE ws_ekis_log_id_seq START 1;
CREATE SEQUENCE ws_qf_log_id_seq START 1;
CREATE SEQUENCE ws_rtip_log_id_seq START 1;
CREATE SEQUENCE ws_sais_log_detail_id_seq START 1;
CREATE SEQUENCE ws_sais_log_id_seq START 1;

-- ----------------------------
-- Table structure for log_table_data
-- ----------------------------
DROP TABLE IF EXISTS "public"."log_table_data";
CREATE TABLE "public"."log_table_data" (
"id" int8 DEFAULT nextval('log_table_data_id_seq'::regclass) NOT NULL,
"table_name" varchar(255) COLLATE "default" NOT NULL,
"table_id" int8 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"action" varchar(50) COLLATE "default" NOT NULL,
"old_value" jsonb,
"new_value" jsonb
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."log_table_data" IS 'log tabel';
COMMENT ON COLUMN "public"."log_table_data"."table_name" IS 'tabeli nimi';
COMMENT ON COLUMN "public"."log_table_data"."table_id" IS 'tabeli ID';
COMMENT ON COLUMN "public"."log_table_data"."inserted" IS 'kirje lisamise aeg';
COMMENT ON COLUMN "public"."log_table_data"."inserted_by" IS 'kellena lisatud. Enamasti võrdub vastava tabeli inserted_by või changed_by isikuga. Muude skriptide korral peaks olema nt Automaat';
COMMENT ON COLUMN "public"."log_table_data"."action" IS 'INSERT, UPDATE, DELETE';
COMMENT ON COLUMN "public"."log_table_data"."old_value" IS 'vana väärtus';
COMMENT ON COLUMN "public"."log_table_data"."new_value" IS 'uus väärtus';



CREATE OR REPLACE FUNCTION public.hois_audit()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
declare
    table_id bigint;
    inserted_by text;
    oldv jsonb;
    newv jsonb;
begin
    if tg_op = 'DELETE' then
        table_id = old.id;
        begin
            inserted_by = current_setting('hois.username');
        exception when others then
            inserted_by = '*';
        end;
        oldv = to_jsonb(old);
        newv = null;
    elsif tg_op = 'INSERT' then
        table_id = new.id;
        inserted_by = new.inserted_by;
        oldv = null;
        newv = to_jsonb(new);
    else
        table_id = new.id;
        inserted_by = new.changed_by;
        oldv = to_jsonb(old);
        newv = to_jsonb(new);
    end if;

    insert into log_table_data
       (table_name, table_id, inserted, inserted_by, action, old_value, new_value)
    values
       (tg_table_name, table_id, now(), inserted_by, tg_op, oldv, newv);
    return null; -- result is ignored since this is an AFTER trigger
end
$function$;

/*
Navicat PGSQL Data Transfer

Source Server         : devhois
Source Server Version : 90505
Source Host           : 141.192.105.96:5432
Source Database       : hois
Source Schema         : public

Target Server Type    : PGSQL
Target Server Version : 90505
File Encoding         : 65001

Date: 2017-12-15 11:19:29
*/


-- ----------------------------
-- Table structure for apel_application
-- ----------------------------
DROP TABLE IF EXISTS "public"."apel_application";
CREATE TABLE "public"."apel_application" (
"id" int8 DEFAULT nextval('apel_application_id_seq'::regclass) NOT NULL,
"school_id" int8 NOT NULL,
"student_id" int8 NOT NULL,
"inserted" timestamptz(6) NOT NULL,
"status_code" varchar(100) COLLATE "default" NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"version" int4 NOT NULL,
"changed" timestamptz(6),
"changed_by" varchar(100) COLLATE "default",
"confirmed" timestamptz(6),
"confirmed_by" varchar(100) COLLATE "default",
"is_vocational" bool NOT NULL
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."apel_application" IS 'VÕTA taotlus';
COMMENT ON COLUMN "public"."apel_application"."school_id" IS 'viide õppeasutusele';
COMMENT ON COLUMN "public"."apel_application"."student_id" IS 'viide tudengile';
COMMENT ON COLUMN "public"."apel_application"."status_code" IS 'staatus, viide klassifikaatorile VOTA_STAATUS';
COMMENT ON COLUMN "public"."apel_application"."is_vocational" IS 'kas on kutse või kõrg VÕTA';

-- ----------------------------
-- Table structure for apel_application_comment
-- ----------------------------
DROP TABLE IF EXISTS "public"."apel_application_comment";
CREATE TABLE "public"."apel_application_comment" (
"id" int8 DEFAULT nextval('apel_application_comment_id_seq'::regclass) NOT NULL,
"apel_application_id" int8 NOT NULL,
"add_info" text COLLATE "default" NOT NULL,
"inserted" timestamp(6) NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"version" int4 NOT NULL,
"changed" timestamp(6),
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."apel_application_comment" IS 'võta taotlusega seotud kommentaarid';
COMMENT ON COLUMN "public"."apel_application_comment"."apel_application_id" IS 'viide võt taotlusele';
COMMENT ON COLUMN "public"."apel_application_comment"."add_info" IS 'kommentaar';

-- ----------------------------
-- Table structure for apel_application_file
-- ----------------------------
DROP TABLE IF EXISTS "public"."apel_application_file";
CREATE TABLE "public"."apel_application_file" (
"id" int8 DEFAULT nextval('apel_application_file_id_seq'::regclass) NOT NULL,
"apel_application_id" int8 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"ois_file_id" int8 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"version" int4 NOT NULL,
"changed" timestamp(6),
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."apel_application_file" IS 'VÕTA taoluse failid';
COMMENT ON COLUMN "public"."apel_application_file"."apel_application_id" IS 'viide võta taotlusele';
COMMENT ON COLUMN "public"."apel_application_file"."ois_file_id" IS 'viide failile';

-- ----------------------------
-- Table structure for apel_application_formal_replaced_subject_or_module
-- ----------------------------
DROP TABLE IF EXISTS "public"."apel_application_formal_replaced_subject_or_module";
CREATE TABLE "public"."apel_application_formal_replaced_subject_or_module" (
"id" int8 DEFAULT nextval('apel_application_formal_replaced_subject_or_module_id_seq'::regclass) NOT NULL,
"apel_application_record_id" int8 NOT NULL,
"subject_id" int8,
"curriculum_version_omodule_id" int8,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."apel_application_formal_replaced_subject_or_module" IS 'formaalse õppe asendatav aine või moodul';
COMMENT ON COLUMN "public"."apel_application_formal_replaced_subject_or_module"."apel_application_record_id" IS 'viide komplektile';
COMMENT ON COLUMN "public"."apel_application_formal_replaced_subject_or_module"."subject_id" IS 'viide asendatavale õppeainele';
COMMENT ON COLUMN "public"."apel_application_formal_replaced_subject_or_module"."curriculum_version_omodule_id" IS 'viide ülekantavle moodulile';

-- ----------------------------
-- Table structure for apel_application_formal_subject_or_module
-- ----------------------------
DROP TABLE IF EXISTS "public"."apel_application_formal_subject_or_module";
CREATE TABLE "public"."apel_application_formal_subject_or_module" (
"id" int8 DEFAULT nextval('apel_application_formal_subject_or_module_id_seq'::regclass) NOT NULL,
"apel_application_record_id" int8 NOT NULL,
"is_my_school" bool NOT NULL,
"type_code" varchar(100) COLLATE "default" NOT NULL,
"apel_school_id" int8,
"is_optional" bool,
"subject_id" int8,
"curriculum_version_omodule_id" int8,
"grade_date" date,
"grade_code" varchar(100) COLLATE "default" NOT NULL,
"teachers" varchar(255) COLLATE "default",
"transfer" bool NOT NULL,
"name_et" varchar(255) COLLATE "default",
"name_en" varchar(255) COLLATE "default",
"subject_code" varchar(20) COLLATE "default",
"credits" numeric(4,1) NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
"assessment_code" varchar(100) COLLATE "default" NOT NULL,
"curriculum_version_hmodule_id" int8
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."apel_application_formal_subject_or_module" IS 'formaalse õppele vastav moodul või aine';
COMMENT ON COLUMN "public"."apel_application_formal_subject_or_module"."apel_application_record_id" IS 'viide komplektile';
COMMENT ON COLUMN "public"."apel_application_formal_subject_or_module"."is_my_school" IS 'minu õppeasutus?';
COMMENT ON COLUMN "public"."apel_application_formal_subject_or_module"."type_code" IS 'ülekantava õppeaine liik, viide klassifikaatorile VOTA_AINE_LIIK';
COMMENT ON COLUMN "public"."apel_application_formal_subject_or_module"."apel_school_id" IS 'viide õppeasutusele';
COMMENT ON COLUMN "public"."apel_application_formal_subject_or_module"."is_optional" IS 'kas on valik või kohustuslik aine';
COMMENT ON COLUMN "public"."apel_application_formal_subject_or_module"."subject_id" IS 'viide õppeainele';
COMMENT ON COLUMN "public"."apel_application_formal_subject_or_module"."curriculum_version_omodule_id" IS 'ülekantav moodul';
COMMENT ON COLUMN "public"."apel_application_formal_subject_or_module"."grade_date" IS 'soorituse kp';
COMMENT ON COLUMN "public"."apel_application_formal_subject_or_module"."grade_code" IS 'viide';
COMMENT ON COLUMN "public"."apel_application_formal_subject_or_module"."teachers" IS 'õpetaja(d) vabatekstina';
COMMENT ON COLUMN "public"."apel_application_formal_subject_or_module"."transfer" IS 'kas üle kanda';
COMMENT ON COLUMN "public"."apel_application_formal_subject_or_module"."name_et" IS 'õppeaine/mooduli nimi';
COMMENT ON COLUMN "public"."apel_application_formal_subject_or_module"."name_en" IS 'õppeaine/mooduli nimi i.k.';
COMMENT ON COLUMN "public"."apel_application_formal_subject_or_module"."subject_code" IS 'õppeaine kood';
COMMENT ON COLUMN "public"."apel_application_formal_subject_or_module"."credits" IS 'EAP/EKAP';
COMMENT ON COLUMN "public"."apel_application_formal_subject_or_module"."assessment_code" IS 'hindamisviis, klassifikaator HINDAMISVIIS';
COMMENT ON COLUMN "public"."apel_application_formal_subject_or_module"."curriculum_version_hmodule_id" IS 'viide õppekava versiooni kõrg moodulile';

-- ----------------------------
-- Table structure for apel_application_informal_experience
-- ----------------------------
DROP TABLE IF EXISTS "public"."apel_application_informal_experience";
CREATE TABLE "public"."apel_application_informal_experience" (
"id" int8 DEFAULT nextval('apel_application_informal_experience_id_seq'::regclass) NOT NULL,
"apel_application_record_id" int8 NOT NULL,
"name_et" varchar(255) COLLATE "default" NOT NULL,
"place_time" varchar(255) COLLATE "default" NOT NULL,
"hours" int2 NOT NULL,
"documents" varchar(255) COLLATE "default" NOT NULL,
"inserted" timestamp(6) NOT NULL,
"type_code" varchar(100) COLLATE "default" NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"version" int4 NOT NULL,
"changed" timestamp(6),
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."apel_application_informal_experience" IS 'VÕTAga seotud töökogemuse/täiendkoolituse kirjeldus';
COMMENT ON COLUMN "public"."apel_application_informal_experience"."name_et" IS 'töökogemuse/täiendkoolituse lühinimetus';
COMMENT ON COLUMN "public"."apel_application_informal_experience"."place_time" IS 'koht/aeg';
COMMENT ON COLUMN "public"."apel_application_informal_experience"."hours" IS 'maht tundides';
COMMENT ON COLUMN "public"."apel_application_informal_experience"."documents" IS 'tõendusdokumendid';
COMMENT ON COLUMN "public"."apel_application_informal_experience"."type_code" IS 'informaalse õppe liik, viide klassifikaatorile VOTA_INFORMAAL_LIIK';

-- ----------------------------
-- Table structure for apel_application_informal_subject_or_module
-- ----------------------------
DROP TABLE IF EXISTS "public"."apel_application_informal_subject_or_module";
CREATE TABLE "public"."apel_application_informal_subject_or_module" (
"id" int8 DEFAULT nextval('apel_application_informal_subject_or_module_id_seq'::regclass) NOT NULL,
"apel_application_record_id" int8 NOT NULL,
"subject_id" int8,
"curriculum_version_hmodule_id" int8,
"is_optional" bool,
"curriculum_version_omodule_theme_id" int8,
"curriculum_version_omodule_id" int8,
"skills" varchar(4000) COLLATE "default" NOT NULL,
"grade_code" varchar(100) COLLATE "default" NOT NULL,
"transfer" bool NOT NULL,
"inserted" timestamp(6) NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"version" int4 NOT NULL,
"changed" timestamp(6),
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."apel_application_informal_subject_or_module" IS 'informaalse õppele vastav moodul või aine';
COMMENT ON COLUMN "public"."apel_application_informal_subject_or_module"."apel_application_record_id" IS 'viide VÕTA komplektile';
COMMENT ON COLUMN "public"."apel_application_informal_subject_or_module"."subject_id" IS 'viide ülekantavale õppeainele';
COMMENT ON COLUMN "public"."apel_application_informal_subject_or_module"."curriculum_version_hmodule_id" IS 'viide kõrg õppekava moodulile';
COMMENT ON COLUMN "public"."apel_application_informal_subject_or_module"."is_optional" IS 'kas aine on kohustuslik';
COMMENT ON COLUMN "public"."apel_application_informal_subject_or_module"."curriculum_version_omodule_theme_id" IS 'viide kutseõppekava teemale';
COMMENT ON COLUMN "public"."apel_application_informal_subject_or_module"."curriculum_version_omodule_id" IS 'viide kutseõppekava moodulile';
COMMENT ON COLUMN "public"."apel_application_informal_subject_or_module"."skills" IS 'oskused';
COMMENT ON COLUMN "public"."apel_application_informal_subject_or_module"."grade_code" IS 'viide klassifikaatorile KORGHINDAMINE või KUTSEHINDAMINE sõltuvalt õppetasemest';
COMMENT ON COLUMN "public"."apel_application_informal_subject_or_module"."transfer" IS 'kas üle kanda, vaikimisi false';

-- ----------------------------
-- Table structure for apel_application_informal_subject_or_module_outcomes
-- ----------------------------
DROP TABLE IF EXISTS "public"."apel_application_informal_subject_or_module_outcomes";
CREATE TABLE "public"."apel_application_informal_subject_or_module_outcomes" (
"id" int8 DEFAULT nextval('apel_application_informal_subject_or_module_outcomes_id_seq'::regclass) NOT NULL,
"curriculum_module_outcomes_id" int8 NOT NULL,
"apel_application_informal_subject_or_module_id" int8 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"version" int4 NOT NULL,
"changed" timestamp(6),
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."apel_application_informal_subject_or_module_outcomes" IS 'kutseõppes VÕTA ülekandmisega seotud õpiväljundid';
COMMENT ON COLUMN "public"."apel_application_informal_subject_or_module_outcomes"."curriculum_module_outcomes_id" IS 'viide õpiväljundile';
COMMENT ON COLUMN "public"."apel_application_informal_subject_or_module_outcomes"."apel_application_informal_subject_or_module_id" IS 'viide informaase õppega ülekantavale moodulile';

-- ----------------------------
-- Table structure for apel_application_record
-- ----------------------------
DROP TABLE IF EXISTS "public"."apel_application_record";
CREATE TABLE "public"."apel_application_record" (
"id" int8 DEFAULT nextval('apel_application_record_id_seq'::regclass) NOT NULL,
"apel_application_id" int8 NOT NULL,
"is_formal_learning" bool NOT NULL,
"inserted" timestamp(6) NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"version" int4 NOT NULL,
"changed" timestamp(6),
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."apel_application_record" IS 'VÕTAga seotud komplekt';
COMMENT ON COLUMN "public"."apel_application_record"."apel_application_id" IS 'viide VÕTA taotlusele';
COMMENT ON COLUMN "public"."apel_application_record"."is_formal_learning" IS 'kas tegemist on formaalse või informaalse õppega';

-- ----------------------------
-- Table structure for apel_school
-- ----------------------------
DROP TABLE IF EXISTS "public"."apel_school";
CREATE TABLE "public"."apel_school" (
"id" int8 DEFAULT nextval('apel_school_id_seq'::regclass) NOT NULL,
"country_code" varchar(100) COLLATE "default" NOT NULL,
"ehis_school_code" varchar(100) COLLATE "default",
"name_et" varchar(100) COLLATE "default" NOT NULL,
"name_en" varchar(100) COLLATE "default",
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(50) COLLATE "default",
"school_id" int8 NOT NULL
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."apel_school" IS 'VÕTA koolide klassifikaator';
COMMENT ON COLUMN "public"."apel_school"."country_code" IS 'viide klassifikaatorile RIIK';
COMMENT ON COLUMN "public"."apel_school"."ehis_school_code" IS 'viide klassifikaatorile EHIS_KOOL, täidetakse ainult juhul kui riigiks valitud Eesti';
COMMENT ON COLUMN "public"."apel_school"."name_et" IS 'õppeasutuse nimi';
COMMENT ON COLUMN "public"."apel_school"."name_en" IS 'õppeasutuse nimi i.k.';
COMMENT ON COLUMN "public"."apel_school"."school_id" IS 'viide õppeasutusele';

-- ----------------------------
-- Table structure for application
-- ----------------------------
DROP TABLE IF EXISTS "public"."application";
CREATE TABLE "public"."application" (
"id" int8 DEFAULT nextval('application_id_seq'::regclass) NOT NULL,
"student_id" int8 NOT NULL,
"status_code" varchar(100) COLLATE "default" NOT NULL,
"type_code" varchar(100) COLLATE "default" NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
"submitted" timestamp(6),
"is_period" bool,
"start_date" date,
"end_date" date,
"add_info" varchar(4000) COLLATE "default",
"reason_code" varchar(100) COLLATE "default",
"old_curriculum_version_id" int8,
"new_curriculum_version_id" int8,
"old_study_form_code" varchar(100) COLLATE "default",
"new_study_form_code" varchar(100) COLLATE "default",
"old_fin_code" varchar(100) COLLATE "default",
"new_fin_code" varchar(100) COLLATE "default",
"old_fin_specific_code" varchar(100) COLLATE "default",
"new_fin_specific_code" varchar(100) COLLATE "default",
"is_abroad" bool,
"country_code" varchar(100) COLLATE "default",
"ehis_school_code" varchar(100) COLLATE "default",
"abroad_purpose_code" varchar(100) COLLATE "default",
"abroad_programme_code" varchar(100) COLLATE "default",
"needs_representative_confirm" bool NOT NULL,
"abroad_school" varchar(255) COLLATE "default",
"academic_application_id" int8,
"study_period_end_id" int8,
"study_period_start_id" int8,
"reject_reason" varchar(4000) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."application" IS 'avaldused';
COMMENT ON COLUMN "public"."application"."student_id" IS 'viide õppurile';
COMMENT ON COLUMN "public"."application"."status_code" IS 'avalduse staatus, viide klassdifikaatorile AVALDUS_STAATUS';
COMMENT ON COLUMN "public"."application"."type_code" IS 'avalduse liike, viide klassifikaatorile AVALDUS_LIIK';
COMMENT ON COLUMN "public"."application"."submitted" IS 'esitamise kp';
COMMENT ON COLUMN "public"."application"."is_period" IS 'kas akad puhkuse/välisõpingute avaldusel on õppeperiood või kp aluseks true - õppeperiood false - kuupäev';
COMMENT ON COLUMN "public"."application"."start_date" IS 'akadeemilise puhkuse alguskuupäev või välisõppesse siirdumise alguskuupäev';
COMMENT ON COLUMN "public"."application"."end_date" IS 'akadeemilise puhkuse lõppkuupäev või välisõppes viibimise lõppkuupäev';
COMMENT ON COLUMN "public"."application"."add_info" IS 'lisainfo';
COMMENT ON COLUMN "public"."application"."reason_code" IS ' põhjus, viide klassifikaatorile AKADPUHKUS_POHJUS või EKSMAT_POHJUS';
COMMENT ON COLUMN "public"."application"."old_curriculum_version_id" IS 'vana õppekava versioon/rakenduskava, viide curriculum_version tabelile';
COMMENT ON COLUMN "public"."application"."new_curriculum_version_id" IS 'uus õppekava versioon/rakenduskava, viide curriculum_version tabelile';
COMMENT ON COLUMN "public"."application"."old_study_form_code" IS 'vana õppevorm, viide klassifikaatorile OPPEVORM';
COMMENT ON COLUMN "public"."application"."new_study_form_code" IS 'uus õppevorm, viide klassifikaatorile OPPEVORM';
COMMENT ON COLUMN "public"."application"."old_fin_code" IS 'vana finantsallikas, viide klassifikaatorile FINALLIKAS';
COMMENT ON COLUMN "public"."application"."new_fin_code" IS 'uus finantsallikas, viide klassifikaatorile FINALLIKAS';
COMMENT ON COLUMN "public"."application"."old_fin_specific_code" IS 'vana finantsallika täpsustus, viide klassifikaatorile FINTAPSUSTUS';
COMMENT ON COLUMN "public"."application"."new_fin_specific_code" IS 'uus finantsallika täpsustus, viide klassifikaatorile FINTAPSUSTUS';
COMMENT ON COLUMN "public"."application"."country_code" IS 'riik, viide klassifikaatorile RIIK';
COMMENT ON COLUMN "public"."application"."ehis_school_code" IS 'viide Eesti õppeastusele, klassifikaator EHIS_KOOL';
COMMENT ON COLUMN "public"."application"."abroad_purpose_code" IS 'väliskoolis õpingute eesmärk ,viide klassifikaatorile VALISOPE_EESMARK';
COMMENT ON COLUMN "public"."application"."abroad_programme_code" IS 'väliskoolis õpingute programm, viide klassifikaatoril VALISKOOL_PROGRAMM';
COMMENT ON COLUMN "public"."application"."needs_representative_confirm" IS 'kas vajab esindaja kinnitamist (alaealise või erivajaduse puhul) vaikimisi false  false - ei vaja true - vajab kinnitamist';
COMMENT ON COLUMN "public"."application"."abroad_school" IS 'välismaa õppeasutus';
COMMENT ON COLUMN "public"."application"."academic_application_id" IS 'viide akad puhkuse avaldusele mida katkestatakse';
COMMENT ON COLUMN "public"."application"."reject_reason" IS 'tagasilükkamise põhjus';

-- ----------------------------
-- Table structure for application_file
-- ----------------------------
DROP TABLE IF EXISTS "public"."application_file";
CREATE TABLE "public"."application_file" (
"id" int8 DEFAULT nextval('application_file_id_seq'::regclass) NOT NULL,
"application_id" int8 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"ois_file_id" int8 NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."application_file" IS 'avalduse failid';
COMMENT ON COLUMN "public"."application_file"."application_id" IS 'viide avaldusele';
COMMENT ON COLUMN "public"."application_file"."ois_file_id" IS 'viide failile';

-- ----------------------------
-- Table structure for application_planned_subject
-- ----------------------------
DROP TABLE IF EXISTS "public"."application_planned_subject";
CREATE TABLE "public"."application_planned_subject" (
"id" int8 DEFAULT nextval('application_planned_subject_id_seq'::regclass) NOT NULL,
"application_id" int8 NOT NULL,
"name" varchar(1000) COLLATE "default" NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."application_planned_subject" IS 'planeeritav õppeaine';
COMMENT ON COLUMN "public"."application_planned_subject"."application_id" IS 'viide avaldusele';
COMMENT ON COLUMN "public"."application_planned_subject"."name" IS 'planeeritud aine';

-- ----------------------------
-- Table structure for application_planned_subject_equivalent
-- ----------------------------
DROP TABLE IF EXISTS "public"."application_planned_subject_equivalent";
CREATE TABLE "public"."application_planned_subject_equivalent" (
"id" int8 DEFAULT nextval('"application_planned_subject_equivalent _id_seq"'::regclass) NOT NULL,
"application_planned_subject_id" int8 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"subject_id" int8 NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."application_planned_subject_equivalent" IS 'avalduse planeeritud aine vastavusained';

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
COMMENT ON COLUMN "public"."building"."school_id" IS 'viide õpepasutusele';
COMMENT ON COLUMN "public"."building"."code" IS 'hoone kood';
COMMENT ON COLUMN "public"."building"."address" IS 'aadress, peaks olema viide ADS klassifikaatorile';

-- ----------------------------
-- Table structure for certificate
-- ----------------------------
DROP TABLE IF EXISTS "public"."certificate";
CREATE TABLE "public"."certificate" (
"id" int8 DEFAULT nextval('certificate_id_seq'::regclass) NOT NULL,
"school_id" int8 NOT NULL,
"student_id" int8,
"headline" varchar(1000) COLLATE "default" NOT NULL,
"type_code" varchar(100) COLLATE "default" NOT NULL,
"whom" varchar(1000) COLLATE "default",
"content" text COLLATE "default" NOT NULL,
"certificate_nr" varchar(20) COLLATE "default",
"signatory_name" varchar(100) COLLATE "default" NOT NULL,
"signatory_idcode" varchar(11) COLLATE "default" NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
"wd_id" int8,
"wd_url" text COLLATE "default",
"status_code" varchar(100) COLLATE "default" NOT NULL,
"other_idcode" varchar(11) COLLATE "default",
"other_name" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."certificate" IS 'tõendid';
COMMENT ON COLUMN "public"."certificate"."school_id" IS 'viide õppeasutusele';
COMMENT ON COLUMN "public"."certificate"."student_id" IS 'viide õppurile';
COMMENT ON COLUMN "public"."certificate"."headline" IS 'pealkiri';
COMMENT ON COLUMN "public"."certificate"."type_code" IS 'viide klassifikaatorile TOEND_LIIK';
COMMENT ON COLUMN "public"."certificate"."whom" IS 'kellele antud tõend mõeldud esitamiseks';
COMMENT ON COLUMN "public"."certificate"."certificate_nr" IS 'tõendi nr, tuleb EKISest';
COMMENT ON COLUMN "public"."certificate"."signatory_name" IS 'allkirjastaja nimi';
COMMENT ON COLUMN "public"."certificate"."signatory_idcode" IS 'allkirjastaja isikukood';
COMMENT ON COLUMN "public"."certificate"."wd_id" IS 'web desktopi unikaalne ID';
COMMENT ON COLUMN "public"."certificate"."wd_url" IS 'web desktopi url õppuri jaoks';
COMMENT ON COLUMN "public"."certificate"."status_code" IS 'viide klassifikaatorile TOEND_STAATUS';
COMMENT ON COLUMN "public"."certificate"."other_idcode" IS '"muu" tõendi isikukood';
COMMENT ON COLUMN "public"."certificate"."other_name" IS '"muu" tõendi isiku nimi';

-- ----------------------------
-- Table structure for classifier
-- ----------------------------
DROP TABLE IF EXISTS "public"."classifier";
CREATE TABLE "public"."classifier" (
"code" varchar(100) COLLATE "default" NOT NULL,
"value" varchar(50) COLLATE "default" NOT NULL,
"value2" varchar(50) COLLATE "default",
"name_et" varchar(1000) COLLATE "et_EE" NOT NULL,
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
-- Table structure for committee
-- ----------------------------
DROP TABLE IF EXISTS "public"."committee";
CREATE TABLE "public"."committee" (
"id" int8 DEFAULT nextval('committee_id_seq'::regclass) NOT NULL,
"valid_from" date NOT NULL,
"valid_thru" date NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
"add_info" varchar(4000) COLLATE "default",
"school_id" int8 NOT NULL
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."committee" IS 'komisjon';
COMMENT ON COLUMN "public"."committee"."valid_from" IS 'kehtiv alates';
COMMENT ON COLUMN "public"."committee"."valid_thru" IS 'kehtiv kuni';
COMMENT ON COLUMN "public"."committee"."add_info" IS 'märkused';
COMMENT ON COLUMN "public"."committee"."school_id" IS 'viide õppeasutusele';

-- ----------------------------
-- Table structure for committee_member
-- ----------------------------
DROP TABLE IF EXISTS "public"."committee_member";
CREATE TABLE "public"."committee_member" (
"id" int8 DEFAULT nextval('committee_member_id_seq'::regclass) NOT NULL,
"committee_id" int8 NOT NULL,
"is_chairman" bool NOT NULL,
"is_external" bool NOT NULL,
"member_name" varchar(100) COLLATE "default",
"teacher_id" int8,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."committee_member" IS 'komisjoni liige';
COMMENT ON COLUMN "public"."committee_member"."is_chairman" IS 'kas on esimees';
COMMENT ON COLUMN "public"."committee_member"."is_external" IS 'kas kooliväline või koolisisene';
COMMENT ON COLUMN "public"."committee_member"."member_name" IS 'koolivälise komisjoni liikme nimi';
COMMENT ON COLUMN "public"."committee_member"."teacher_id" IS 'viide kooli õpetajale';

-- ----------------------------
-- Table structure for contract
-- ----------------------------
DROP TABLE IF EXISTS "public"."contract";
CREATE TABLE "public"."contract" (
"id" int8 DEFAULT nextval('contract_id_seq'::regclass) NOT NULL,
"student_id" int8 NOT NULL,
"curriculum_version_omodule_id" int8,
"curriculum_version_omodule_theme_id" int8,
"credits" numeric(4,1) NOT NULL,
"hours" int2 NOT NULL,
"start_date" date NOT NULL,
"practice_place" varchar(255) COLLATE "default" NOT NULL,
"contact_person_name" varchar(100) COLLATE "default" NOT NULL,
"enterprise_id" int8 NOT NULL,
"contact_person_phone" varchar(100) COLLATE "default",
"contact_person_email" varchar(100) COLLATE "default" NOT NULL,
"supervisor_name" varchar(100) COLLATE "default" NOT NULL,
"supervisor_phone" varchar(100) COLLATE "default",
"supervisor_email" varchar(100) COLLATE "default" NOT NULL,
"supervisor_url" varchar(4000) COLLATE "default",
"other_supervisor" varchar(255) COLLATE "default",
"practice_plan" text COLLATE "default" NOT NULL,
"teacher_id" int8 NOT NULL,
"contract_coordinator_id" int8,
"status_code" varchar(100) COLLATE "default" NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
"ekis_date" timestamp(6),
"confirm_date" date,
"wd_id" int4,
"contract_nr" varchar(20) COLLATE "default",
"end_date" date,
"subject_id" int8
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."contract" IS 'leping, esialgu ainult praktika leping';
COMMENT ON COLUMN "public"."contract"."student_id" IS 'viide õppurile';
COMMENT ON COLUMN "public"."contract"."curriculum_version_omodule_id" IS 'viide praktika moodulile';
COMMENT ON COLUMN "public"."contract"."curriculum_version_omodule_theme_id" IS 'viide teemale';
COMMENT ON COLUMN "public"."contract"."credits" IS 'mah EKAP';
COMMENT ON COLUMN "public"."contract"."hours" IS 'maht tundides';
COMMENT ON COLUMN "public"."contract"."start_date" IS 'lepingu algus';
COMMENT ON COLUMN "public"."contract"."practice_place" IS 'praktika koht';
COMMENT ON COLUMN "public"."contract"."contact_person_name" IS 'kontaktisiku nimi';
COMMENT ON COLUMN "public"."contract"."enterprise_id" IS 'ettevõte';
COMMENT ON COLUMN "public"."contract"."contact_person_phone" IS 'kontaktisiku telefon';
COMMENT ON COLUMN "public"."contract"."contact_person_email" IS 'kontaktisiku e-mail';
COMMENT ON COLUMN "public"."contract"."supervisor_name" IS 'juhendaja nimi';
COMMENT ON COLUMN "public"."contract"."supervisor_phone" IS 'juhendaja telefon';
COMMENT ON COLUMN "public"."contract"."supervisor_email" IS 'juhendaja e-mail';
COMMENT ON COLUMN "public"."contract"."supervisor_url" IS 'juhendaja url, genereeritakse automaatselt';
COMMENT ON COLUMN "public"."contract"."other_supervisor" IS 'teised juhendajad';
COMMENT ON COLUMN "public"."contract"."practice_plan" IS 'praktika kava';
COMMENT ON COLUMN "public"."contract"."teacher_id" IS 'viide õpetajale, õpepasutuse juhendaja';
COMMENT ON COLUMN "public"."contract"."contract_coordinator_id" IS 'EKISe menetleja';
COMMENT ON COLUMN "public"."contract"."status_code" IS 'viide klassifikaatorile LEPING_STAATUS';
COMMENT ON COLUMN "public"."contract"."ekis_date" IS 'EKISesse edastamise kuupäev, mitmekordsel edastamisel kantud väärtus kirjutatakse üle';
COMMENT ON COLUMN "public"."contract"."confirm_date" IS 'kinnitamise kp';
COMMENT ON COLUMN "public"."contract"."contract_nr" IS 'lepingu number';
COMMENT ON COLUMN "public"."contract"."end_date" IS 'lepingu lõpp';
COMMENT ON COLUMN "public"."contract"."subject_id" IS 'viide praktika ainele';

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
"structure" varchar(20000) COLLATE "default",
"admission_requirements_et" varchar(20000) COLLATE "default",
"admission_requirements_en" varchar(20000) COLLATE "default",
"graduation_requirements_et" varchar(20000) COLLATE "default",
"graduation_requirements_en" varchar(20000) COLLATE "default",
"credits" numeric(4,1),
"study_period" int4 NOT NULL,
"draft_text" varchar(4000) COLLATE "default",
"specialization" varchar(4000) COLLATE "default",
"is_joint" bool NOT NULL,
"optional_study_credits" numeric(4,1),
"practice_description" varchar(20000) COLLATE "default",
"final_exam_description" varchar(4000) COLLATE "default",
"optional_study_description" varchar(4000) COLLATE "default",
"ehis_changed" date,
"description" varchar(20000) COLLATE "default",
"ehis_status_code" varchar(100) COLLATE "default",
"contact_person" varchar(1000) COLLATE "default",
"valid_from" date,
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
"mer_reg_date" date,
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
COMMENT ON COLUMN "public"."curriculum"."structure" IS 'õppekava struktuur';
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
COMMENT ON COLUMN "public"."curriculum"."optional_study_description" IS 'valikõpingute valimise võimalused';
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
COMMENT ON COLUMN "public"."curriculum"."mer_reg_date" IS 'Õppekava registreerimine HTMis ';
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
"contract_et" varchar(1000) COLLATE "default",
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
"inserted" timestamp(6) NOT NULL,
"module_code" varchar(100) COLLATE "default" NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"curriculum_id" int4 NOT NULL,
"is_practice" bool NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default",
"is_additional" bool,
"assessments_et" text COLLATE "default",
"assessments_en" text COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."curriculum_module" IS 'õppekava moodulid';
COMMENT ON COLUMN "public"."curriculum_module"."name_et" IS 'mooduli nimetus';
COMMENT ON COLUMN "public"."curriculum_module"."name_en" IS 'mooduli nimetus i.k.';
COMMENT ON COLUMN "public"."curriculum_module"."credits" IS 'mooduli maht EKAP';
COMMENT ON COLUMN "public"."curriculum_module"."objectives_et" IS 'mooduli eesmärk';
COMMENT ON COLUMN "public"."curriculum_module"."objectives_en" IS 'mooduli eesmärk i.k.';
COMMENT ON COLUMN "public"."curriculum_module"."module_code" IS 'kutseõppekava mooduli klassifikaator';
COMMENT ON COLUMN "public"."curriculum_module"."is_practice" IS 'kas tegemist on praktikaga false - ei ole praktika moodul true - praktika moodul';
COMMENT ON COLUMN "public"."curriculum_module"."is_additional" IS 'kas on lisamoodul';
COMMENT ON COLUMN "public"."curriculum_module"."assessments_et" IS 'hindamiskriteeriumid';
COMMENT ON COLUMN "public"."curriculum_module"."assessments_en" IS 'hindamiskriteeriumid i.k.';

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
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default",
"version" int4 NOT NULL
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."curriculum_module_occupation" IS 'õppekava mooduli ja õppekava kutse/spetsialiseerumise/osakutse seos';
COMMENT ON COLUMN "public"."curriculum_module_occupation"."curriculum_module_id" IS 'viide õppekava moodulile';
COMMENT ON COLUMN "public"."curriculum_module_occupation"."occupation_code" IS 'viide kutse/osakutse/spetsialiseerumise klassifikaatorile';

-- ----------------------------
-- Table structure for curriculum_module_outcomes
-- ----------------------------
DROP TABLE IF EXISTS "public"."curriculum_module_outcomes";
CREATE TABLE "public"."curriculum_module_outcomes" (
"id" int8 DEFAULT nextval('curriculum_module_outcomes_id_seq'::regclass) NOT NULL,
"outcome_et" varchar(1000) COLLATE "default" NOT NULL,
"outcome_en" varchar(1000) COLLATE "default",
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default",
"curriculum_module_id" int4,
"order_nr" int2
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."curriculum_module_outcomes" IS 'mooduli õpiväljundid';
COMMENT ON COLUMN "public"."curriculum_module_outcomes"."outcome_et" IS 'õpiväljund e.k.';
COMMENT ON COLUMN "public"."curriculum_module_outcomes"."outcome_en" IS 'õpiväljund i.k.';
COMMENT ON COLUMN "public"."curriculum_module_outcomes"."order_nr" IS 'õpiväljundi jrk nr';

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
"is_occupation_grant" bool NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default",
"version" int4 NOT NULL
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."curriculum_occupation" IS 'õppekava seos kutsete või osakutsetega';
COMMENT ON COLUMN "public"."curriculum_occupation"."occupation_code" IS 'viide kutse või osakutse klaasifikaatorile';
COMMENT ON COLUMN "public"."curriculum_occupation"."is_occupation_grant" IS 'kas õppeasutusel on kutse andmise õigus false - ei ole true - saab anda kutset';

-- ----------------------------
-- Table structure for curriculum_occupation_speciality
-- ----------------------------
DROP TABLE IF EXISTS "public"."curriculum_occupation_speciality";
CREATE TABLE "public"."curriculum_occupation_speciality" (
"id" int8 DEFAULT nextval('curriculum_occupation_speciality_id_seq'::regclass) NOT NULL,
"curriculum_occupation_id" int4 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"speciality_code" varchar(100) COLLATE "default" NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."curriculum_occupation_speciality" IS 'antud kutse raames õpetatavad spetsialiseerumised (spetsialiseerumise klassifikaator)
';
COMMENT ON COLUMN "public"."curriculum_occupation_speciality"."curriculum_occupation_id" IS 'viide õppekava kutsele';
COMMENT ON COLUMN "public"."curriculum_occupation_speciality"."speciality_code" IS 'spetsialiseerumise kood, viide spetsialiseerumise klassifikaatorile';

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
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
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
-- Table structure for curriculum_version
-- ----------------------------
DROP TABLE IF EXISTS "public"."curriculum_version";
CREATE TABLE "public"."curriculum_version" (
"id" int8 DEFAULT nextval('curriculum_version_id_seq'::regclass) NOT NULL,
"code" varchar(255) COLLATE "default" NOT NULL,
"curriculum_id" int4 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default",
"type_code" varchar(100) COLLATE "default" NOT NULL,
"admission_year" int2,
"status_code" varchar(100) COLLATE "default" NOT NULL,
"target_group" varchar(4000) COLLATE "default",
"teachers" varchar(4000) COLLATE "default",
"school_department_id" int8,
"curriculum_study_form_id" int8,
"is_individual" bool,
"valid_from" timestamp(6),
"valid_thru" timestamp(6),
"description" text COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."curriculum_version" IS 'rakenduskava kutseõppes või õppekava versioon kõrghariduses';
COMMENT ON COLUMN "public"."curriculum_version"."code" IS 'kood või lühinimetus';
COMMENT ON COLUMN "public"."curriculum_version"."curriculum_id" IS 'viide õppekavale';
COMMENT ON COLUMN "public"."curriculum_version"."type_code" IS 'rakenduskava/õppekava versiooni liik, viide klassifikaatorile OPPEKAVA_VERSIOON_LIIK';
COMMENT ON COLUMN "public"."curriculum_version"."admission_year" IS 'vastuvõtu aasta';
COMMENT ON COLUMN "public"."curriculum_version"."status_code" IS 'rakenduskava/õppekava staatus, viide klassifikaatorile OPPEKAVA_VERSIOON_STAATUS';
COMMENT ON COLUMN "public"."curriculum_version"."target_group" IS 'rakenduskava sihtrühm';
COMMENT ON COLUMN "public"."curriculum_version"."teachers" IS 'rakenduskava õpetajad';
COMMENT ON COLUMN "public"."curriculum_version"."school_department_id" IS 'rakenduskava õpetav struktuuriüksus';
COMMENT ON COLUMN "public"."curriculum_version"."curriculum_study_form_id" IS 'rakenduskava õppevorm';
COMMENT ON COLUMN "public"."curriculum_version"."is_individual" IS 'kas tegemist individuaalse rakenduskavaga, vaikimisi false false - ei ole individuaalne true - on individuaalne';
COMMENT ON COLUMN "public"."curriculum_version"."valid_from" IS 'rakenduskava kehtivuse algus';
COMMENT ON COLUMN "public"."curriculum_version"."valid_thru" IS 'rakenduskava kehtivuse lõpp';
COMMENT ON COLUMN "public"."curriculum_version"."description" IS 'rakenduskava märkused';

-- ----------------------------
-- Table structure for curriculum_version_elective_module
-- ----------------------------
DROP TABLE IF EXISTS "public"."curriculum_version_elective_module";
CREATE TABLE "public"."curriculum_version_elective_module" (
"id" int8 DEFAULT nextval('curriculum_version_elective_module_id_seq'::regclass) NOT NULL,
"name_et" varchar(255) COLLATE "default" NOT NULL,
"name_en" varchar(255) COLLATE "default" NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default",
"curriculum_version_hmodule_id" int8 NOT NULL
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."curriculum_version_elective_module" IS 'valikmoodulid';
COMMENT ON COLUMN "public"."curriculum_version_elective_module"."name_et" IS 'nimi e.k.';
COMMENT ON COLUMN "public"."curriculum_version_elective_module"."name_en" IS 'nimi i.k.';
COMMENT ON COLUMN "public"."curriculum_version_elective_module"."curriculum_version_hmodule_id" IS 'viide õppekavaversiooni moodulile';

-- ----------------------------
-- Table structure for curriculum_version_hmodule
-- ----------------------------
DROP TABLE IF EXISTS "public"."curriculum_version_hmodule";
CREATE TABLE "public"."curriculum_version_hmodule" (
"id" int8 DEFAULT nextval('curriculum_version_hmodule_id_seq'::regclass) NOT NULL,
"type_code" varchar(100) COLLATE "default" NOT NULL,
"name_et" varchar(255) COLLATE "default" NOT NULL,
"name_en" varchar(255) COLLATE "default" NOT NULL,
"objectives_et" text COLLATE "default",
"objectives_en" text COLLATE "default",
"outcomes_et" text COLLATE "default",
"outcomes_en" text COLLATE "default",
"total_credits" numeric(4,1) NOT NULL,
"optional_study_credits" numeric(4,1) NOT NULL,
"compulsory_study_credits" numeric(4,1) NOT NULL,
"curriculum_version_id" int8 NOT NULL,
"elective_modules_number" int2 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default",
"type_name_et" varchar(255) COLLATE "default",
"type_name_en" varchar(255) COLLATE "default",
"is_minor_speciality" bool NOT NULL
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."curriculum_version_hmodule" IS 'õppekava versiooni moodulid (kõrgharidusõpe)';
COMMENT ON COLUMN "public"."curriculum_version_hmodule"."type_code" IS 'mooduli liik, viide klassifikaatorile KORGMOODUL';
COMMENT ON COLUMN "public"."curriculum_version_hmodule"."name_et" IS 'mooduli  nimi e.k.';
COMMENT ON COLUMN "public"."curriculum_version_hmodule"."name_en" IS 'mooduli nimi i.k.';
COMMENT ON COLUMN "public"."curriculum_version_hmodule"."objectives_et" IS 'eesmärk e.k.';
COMMENT ON COLUMN "public"."curriculum_version_hmodule"."objectives_en" IS 'eesmärk i.k.';
COMMENT ON COLUMN "public"."curriculum_version_hmodule"."outcomes_et" IS 'õpiväljundid e.k.';
COMMENT ON COLUMN "public"."curriculum_version_hmodule"."outcomes_en" IS 'õpiväljundid i.k.';
COMMENT ON COLUMN "public"."curriculum_version_hmodule"."total_credits" IS 'kokku EAP, vaikimisi 0';
COMMENT ON COLUMN "public"."curriculum_version_hmodule"."optional_study_credits" IS 'valikainete maht EAP, vaikimisi 0';
COMMENT ON COLUMN "public"."curriculum_version_hmodule"."compulsory_study_credits" IS 'kohustuslike ainete EAP, vaikimisi 0';
COMMENT ON COLUMN "public"."curriculum_version_hmodule"."curriculum_version_id" IS 'viide õppekava versioonile';
COMMENT ON COLUMN "public"."curriculum_version_hmodule"."elective_modules_number" IS 'Valikmoodulite arv õppekava täitmiseks, vaikimisi 0';
COMMENT ON COLUMN "public"."curriculum_version_hmodule"."type_name_et" IS 'mooduli liigi nimi e.k. kui type_code=KORGMOODUL_M';
COMMENT ON COLUMN "public"."curriculum_version_hmodule"."type_name_en" IS 'mooduli liigi nimi i.k. kui type_code=KORGMOODUL_M';
COMMENT ON COLUMN "public"."curriculum_version_hmodule"."is_minor_speciality" IS 'kas tegemist on kõrvaleriala mooduliga või mitte, vaikimisi false false - peaerialaga seotud moodul true - kõrvaleriala moodul';

-- ----------------------------
-- Table structure for curriculum_version_hmodule_speciality
-- ----------------------------
DROP TABLE IF EXISTS "public"."curriculum_version_hmodule_speciality";
CREATE TABLE "public"."curriculum_version_hmodule_speciality" (
"id" int8 DEFAULT nextval('curriculum_version_hmodule_speciality_id_seq'::regclass) NOT NULL,
"curriculum_version_hmodule_id" int8 NOT NULL,
"curriculum_version_speciality_id" int8 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."curriculum_version_hmodule_speciality" IS 'mooduli ja peaeriala seos (kõrgharidusõpe)';
COMMENT ON COLUMN "public"."curriculum_version_hmodule_speciality"."curriculum_version_hmodule_id" IS 'viide moodulile';
COMMENT ON COLUMN "public"."curriculum_version_hmodule_speciality"."curriculum_version_speciality_id" IS 'viide peaerialale';

-- ----------------------------
-- Table structure for curriculum_version_hmodule_subject
-- ----------------------------
DROP TABLE IF EXISTS "public"."curriculum_version_hmodule_subject";
CREATE TABLE "public"."curriculum_version_hmodule_subject" (
"id" int8 DEFAULT nextval('curriculum_version_hmodule_subject_id_seq'::regclass) NOT NULL,
"curriculum_version_hmodule_id" int8 NOT NULL,
"is_optional" bool NOT NULL,
"subject_id" int8 NOT NULL,
"curriculum_version_elective_module_id" int8,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default",
"version" int4 NOT NULL
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."curriculum_version_hmodule_subject" IS 'mooduliga seotud ained (kõrgharidusõpe)';
COMMENT ON COLUMN "public"."curriculum_version_hmodule_subject"."curriculum_version_hmodule_id" IS 'viide moodulile';
COMMENT ON COLUMN "public"."curriculum_version_hmodule_subject"."is_optional" IS 'kas tegemist valik/vaba ainega true - valikaine false - kohustuslik aine';
COMMENT ON COLUMN "public"."curriculum_version_hmodule_subject"."subject_id" IS 'viide õppeainele';
COMMENT ON COLUMN "public"."curriculum_version_hmodule_subject"."curriculum_version_elective_module_id" IS 'viide valikmoodulile';

-- ----------------------------
-- Table structure for curriculum_version_omodule
-- ----------------------------
DROP TABLE IF EXISTS "public"."curriculum_version_omodule";
CREATE TABLE "public"."curriculum_version_omodule" (
"id" int8 DEFAULT nextval('curriculum_version_omodule_id_seq'::regclass) NOT NULL,
"curriculum_version_id" int8 NOT NULL,
"curriculum_module_id" int8 NOT NULL,
"requirements_et" text COLLATE "default" NOT NULL,
"assessments_et" text COLLATE "default" NOT NULL,
"learning_methods_et" text COLLATE "default",
"assessment_methods_et" text COLLATE "default",
"assessment_code" varchar(100) COLLATE "default" NOT NULL,
"total_grade_description" text COLLATE "default" NOT NULL,
"pass_description" text COLLATE "default",
"grade3_description" text COLLATE "default",
"grade4_description" text COLLATE "default",
"grade5_description" text COLLATE "default",
"independent_study_et" text COLLATE "default",
"study_materials" text COLLATE "default",
"supervisor" varchar(100) COLLATE "default" NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default",
"is_additional" bool
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."curriculum_version_omodule" IS 'rakenduskava moodulid';
COMMENT ON COLUMN "public"."curriculum_version_omodule"."curriculum_version_id" IS 'viide rakenduskavale';
COMMENT ON COLUMN "public"."curriculum_version_omodule"."curriculum_module_id" IS 'viide õppekava moodulile';
COMMENT ON COLUMN "public"."curriculum_version_omodule"."requirements_et" IS 'nõuded mooduli alustamiseks e.k.';
COMMENT ON COLUMN "public"."curriculum_version_omodule"."assessments_et" IS 'mooduli hindamiskriteeriumid e.k.';
COMMENT ON COLUMN "public"."curriculum_version_omodule"."learning_methods_et" IS 'mooduli õppemeetodid';
COMMENT ON COLUMN "public"."curriculum_version_omodule"."assessment_methods_et" IS 'hindamismeetodid ja ülesanded';
COMMENT ON COLUMN "public"."curriculum_version_omodule"."assessment_code" IS 'hindamisviis, viide klassifikaatorile KUTSEHINDAMISVIIS';
COMMENT ON COLUMN "public"."curriculum_version_omodule"."total_grade_description" IS 'kokkuvõtva hinde kujunemine';
COMMENT ON COLUMN "public"."curriculum_version_omodule"."pass_description" IS '"A"  saamise tingimus';
COMMENT ON COLUMN "public"."curriculum_version_omodule"."grade3_description" IS '"3"  saamise tingimus';
COMMENT ON COLUMN "public"."curriculum_version_omodule"."grade4_description" IS '"4"  saamise tingimus';
COMMENT ON COLUMN "public"."curriculum_version_omodule"."grade5_description" IS '"5"  saamise tingimus';
COMMENT ON COLUMN "public"."curriculum_version_omodule"."independent_study_et" IS 'iseseisva töö kirjeldus';
COMMENT ON COLUMN "public"."curriculum_version_omodule"."study_materials" IS 'õppematerjalid';
COMMENT ON COLUMN "public"."curriculum_version_omodule"."supervisor" IS 'vastutaja';
COMMENT ON COLUMN "public"."curriculum_version_omodule"."is_additional" IS 'kas on lisamoodul';

-- ----------------------------
-- Table structure for curriculum_version_omodule_capacity
-- ----------------------------
DROP TABLE IF EXISTS "public"."curriculum_version_omodule_capacity";
CREATE TABLE "public"."curriculum_version_omodule_capacity" (
"id" int8 DEFAULT nextval('curriculum_version_omodule_capacity_id_seq'::regclass) NOT NULL,
"curriculum_version_omodule_id" int8 NOT NULL,
"capacity_type_code" varchar(100) COLLATE "default" NOT NULL,
"hours" int2 NOT NULL,
"is_contact" bool NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."curriculum_version_omodule_capacity" IS 'mooduli mahu jaotus';
COMMENT ON COLUMN "public"."curriculum_version_omodule_capacity"."curriculum_version_omodule_id" IS 'viide rakenduskava moodulile';
COMMENT ON COLUMN "public"."curriculum_version_omodule_capacity"."capacity_type_code" IS 'mahu jaotuse liik, viide klassifikaatorile MAHT, peab arvestama ainult nende väärtustega, kus is_vocational=true';
COMMENT ON COLUMN "public"."curriculum_version_omodule_capacity"."hours" IS 'mooduli maht tundides';
COMMENT ON COLUMN "public"."curriculum_version_omodule_capacity"."is_contact" IS 'kas on kontaktõpe, vaikimisi true true - kontaktõpe false - ei ole kontaktõpe';

-- ----------------------------
-- Table structure for curriculum_version_omodule_outcomes
-- ----------------------------
DROP TABLE IF EXISTS "public"."curriculum_version_omodule_outcomes";
CREATE TABLE "public"."curriculum_version_omodule_outcomes" (
"id" int8 DEFAULT nextval('curriculum_version_omodule_outcomes_id_seq'::regclass) NOT NULL,
"curriculum_version_omodule_theme_id" int8 NOT NULL,
"curriculum_module_outcomes_id" int8 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."curriculum_version_omodule_outcomes" IS 'rakenduskava mooduli teema ja õpiväljundi seos';
COMMENT ON COLUMN "public"."curriculum_version_omodule_outcomes"."curriculum_version_omodule_theme_id" IS 'viide rakenduskava mooduli teemale';
COMMENT ON COLUMN "public"."curriculum_version_omodule_outcomes"."curriculum_module_outcomes_id" IS 'viide mooduli õpiväljundile';

-- ----------------------------
-- Table structure for curriculum_version_omodule_theme
-- ----------------------------
DROP TABLE IF EXISTS "public"."curriculum_version_omodule_theme";
CREATE TABLE "public"."curriculum_version_omodule_theme" (
"id" int8 DEFAULT nextval('curriculum_version_omodule_theme_id_seq'::regclass) NOT NULL,
"curriculum_version_omodule_id" int8 NOT NULL,
"name_et" varchar(255) COLLATE "default" NOT NULL,
"credits" numeric(4,1) NOT NULL,
"hours" int2 NOT NULL,
"proportion" numeric(4,1),
"subthemes" text COLLATE "default",
"study_year_number" int2,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default",
"assessment_code" varchar(100) COLLATE "default",
"total_grade_description" varchar(10000) COLLATE "default",
"pass_description" varchar(10000) COLLATE "default",
"grade3_description" varchar(10000) COLLATE "default",
"grade4_description" varchar(10000) COLLATE "default",
"grade5_description" varchar(10000) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."curriculum_version_omodule_theme" IS 'rakenduskava mooduli teemad';
COMMENT ON COLUMN "public"."curriculum_version_omodule_theme"."curriculum_version_omodule_id" IS 'viide rakenduskava moodulile';
COMMENT ON COLUMN "public"."curriculum_version_omodule_theme"."name_et" IS 'teema nimetus e.k.';
COMMENT ON COLUMN "public"."curriculum_version_omodule_theme"."credits" IS 'teema maht EKAP''tes';
COMMENT ON COLUMN "public"."curriculum_version_omodule_theme"."hours" IS 'teema maht tundides, vaikimisi credits*26';
COMMENT ON COLUMN "public"."curriculum_version_omodule_theme"."proportion" IS 'teema osakaal moodulis';
COMMENT ON COLUMN "public"."curriculum_version_omodule_theme"."subthemes" IS 'alamteemad';
COMMENT ON COLUMN "public"."curriculum_version_omodule_theme"."study_year_number" IS 'mitmendal õppeaastal teema läbitakse, nt 1, 2 , 3 jne';
COMMENT ON COLUMN "public"."curriculum_version_omodule_theme"."total_grade_description" IS 'kokkuvõtva hinde kujunemine';
COMMENT ON COLUMN "public"."curriculum_version_omodule_theme"."grade3_description" IS '"3"  saamise tingimus';
COMMENT ON COLUMN "public"."curriculum_version_omodule_theme"."grade4_description" IS '"4"  saamise tingimus';
COMMENT ON COLUMN "public"."curriculum_version_omodule_theme"."grade5_description" IS '"5"  saamise tingimus';

-- ----------------------------
-- Table structure for curriculum_version_omodule_theme_capacity
-- ----------------------------
DROP TABLE IF EXISTS "public"."curriculum_version_omodule_theme_capacity";
CREATE TABLE "public"."curriculum_version_omodule_theme_capacity" (
"id" int8 DEFAULT nextval('curriculum_version_omodule_theme_capacity_id_seq'::regclass) NOT NULL,
"curriculum_version_omodule_theme_id" int8 NOT NULL,
"capacity_type_code" varchar(100) COLLATE "default" NOT NULL,
"hours" int2 NOT NULL,
"is_contact" bool NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."curriculum_version_omodule_theme_capacity" IS 'teema mahu jaotus';
COMMENT ON COLUMN "public"."curriculum_version_omodule_theme_capacity"."curriculum_version_omodule_theme_id" IS 'viide rakenduskava mooduli teemale';
COMMENT ON COLUMN "public"."curriculum_version_omodule_theme_capacity"."capacity_type_code" IS 'mahu jaotuse liik, viide klassifikaatorile MAHT, peab arvestama ainult nende väärtustega, kus is_vocational=true';
COMMENT ON COLUMN "public"."curriculum_version_omodule_theme_capacity"."hours" IS 'teema maht tundides';
COMMENT ON COLUMN "public"."curriculum_version_omodule_theme_capacity"."is_contact" IS 'kas on kontaktõpe, vaikimis true true - kontaktõpe false - ei ole kontaktõpe';

-- ----------------------------
-- Table structure for curriculum_version_omodule_year_capacity
-- ----------------------------
DROP TABLE IF EXISTS "public"."curriculum_version_omodule_year_capacity";
CREATE TABLE "public"."curriculum_version_omodule_year_capacity" (
"id" int8 DEFAULT nextval('curriculum_version_omodule_year_capacity_id_seq'::regclass) NOT NULL,
"study_year_number" int2 NOT NULL,
"credits" numeric(4,1) NOT NULL,
"curriculum_version_omodule_id" int8 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."curriculum_version_omodule_year_capacity" IS 'rakenduskava mooduli mahtude jaotus õppeaastate lõikes';
COMMENT ON COLUMN "public"."curriculum_version_omodule_year_capacity"."study_year_number" IS 'õppeaasta kujul 1, 2, 3 jne';
COMMENT ON COLUMN "public"."curriculum_version_omodule_year_capacity"."credits" IS 'maht EKAPdes, nt 10, 12.5 jne';
COMMENT ON COLUMN "public"."curriculum_version_omodule_year_capacity"."curriculum_version_omodule_id" IS 'viide rakenduskava moodulile';

-- ----------------------------
-- Table structure for curriculum_version_speciality
-- ----------------------------
DROP TABLE IF EXISTS "public"."curriculum_version_speciality";
CREATE TABLE "public"."curriculum_version_speciality" (
"id" int8 DEFAULT nextval('curriculum_version_speciality_id_seq'::regclass) NOT NULL,
"curriculum_speciality_id" int8 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default",
"curriculum_version_id" int8 NOT NULL
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."curriculum_version_speciality" IS 'õppekava versiooni peaerialad';
COMMENT ON COLUMN "public"."curriculum_version_speciality"."curriculum_speciality_id" IS 'viide õppekava peaerialale';
COMMENT ON COLUMN "public"."curriculum_version_speciality"."curriculum_version_id" IS 'viide õppekava versioonile';

-- ----------------------------
-- Table structure for declaration
-- ----------------------------
DROP TABLE IF EXISTS "public"."declaration";
CREATE TABLE "public"."declaration" (
"id" int8 DEFAULT nextval('declaration_id_seq'::regclass) NOT NULL,
"student_id" int8 NOT NULL,
"status_code" varchar(100) COLLATE "default" NOT NULL,
"study_period_id" int8 NOT NULL,
"confirm_date" date,
"confirmer" varchar(100) COLLATE "default",
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."declaration" IS 'õpingukava ehk õppeainete deklaratsioon';
COMMENT ON COLUMN "public"."declaration"."student_id" IS 'viide õppurile';
COMMENT ON COLUMN "public"."declaration"."status_code" IS 'viide klassifikaatorile OPINGUKAVA_STAATUS';
COMMENT ON COLUMN "public"."declaration"."study_period_id" IS 'viide õppeperioodile';
COMMENT ON COLUMN "public"."declaration"."confirm_date" IS 'kinnitamise kp';
COMMENT ON COLUMN "public"."declaration"."confirmer" IS 'kinnitaja nimi';

-- ----------------------------
-- Table structure for declaration_subject
-- ----------------------------
DROP TABLE IF EXISTS "public"."declaration_subject";
CREATE TABLE "public"."declaration_subject" (
"id" int8 DEFAULT nextval('declaration_subject_id_seq'::regclass) NOT NULL,
"declaration_id" int8 NOT NULL,
"subject_study_period_id" int8 NOT NULL,
"curriculum_version_hmodule_id" int8,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
"is_optional" bool NOT NULL
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."declaration_subject"."declaration_id" IS 'viide deklaratsioonile';
COMMENT ON COLUMN "public"."declaration_subject"."subject_study_period_id" IS 'viide aine-õppeju paarile';
COMMENT ON COLUMN "public"."declaration_subject"."curriculum_version_hmodule_id" IS 'moodul, täidetakse automaatselt lisamisel';
COMMENT ON COLUMN "public"."declaration_subject"."is_optional" IS 'kas tegemist on valik või kohustusliku ainega true - ei ole kohustuslik false - kohustuslik';

-- ----------------------------
-- Table structure for directive
-- ----------------------------
DROP TABLE IF EXISTS "public"."directive";
CREATE TABLE "public"."directive" (
"id" int8 DEFAULT nextval('directive_id_seq'::regclass) NOT NULL,
"school_id" int8 NOT NULL,
"headline" varchar(500) COLLATE "default" NOT NULL,
"type_code" varchar(100) COLLATE "default" NOT NULL,
"directive_nr" varchar(20) COLLATE "default",
"confirm_date" date,
"add_info" varchar(4000) COLLATE "default",
"status_code" varchar(100) COLLATE "default" NOT NULL,
"directive_coordinator_id" int8,
"ekis_date" timestamp(6),
"preamble" varchar(4000) COLLATE "default",
"wd_id" int8,
"confirmer" varchar(100) COLLATE "default",
"canceled_directive_id" int8,
"cancel_type_code" varchar(100) COLLATE "default",
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."directive" IS 'käskkirjad';
COMMENT ON COLUMN "public"."directive"."school_id" IS 'viide õppeasutusele, kes tegi käskkirja';
COMMENT ON COLUMN "public"."directive"."headline" IS 'pealkiri';
COMMENT ON COLUMN "public"."directive"."type_code" IS 'käskkirja liik, viide klassifikaatorile KASKKIRI';
COMMENT ON COLUMN "public"."directive"."directive_nr" IS 'käskkirja number, võib sisaldada tähti jms, tuleb EKISest';
COMMENT ON COLUMN "public"."directive"."confirm_date" IS 'kinitamise kuupäev';
COMMENT ON COLUMN "public"."directive"."add_info" IS 'lisainfo';
COMMENT ON COLUMN "public"."directive"."status_code" IS 'käskkirja staatus, viide klassifikaatorile KASKKIRI_STAATUS';
COMMENT ON COLUMN "public"."directive"."directive_coordinator_id" IS 'käskkirja kooskõlastaja EKISes, viide tabelile directive_coordinator';
COMMENT ON COLUMN "public"."directive"."ekis_date" IS 'EKISesse edastamise kuupäev, mitmekordsel edastamisel kantud väärtus kirjutatakse üle';
COMMENT ON COLUMN "public"."directive"."preamble" IS 'preambula, täidetakse EKISes';
COMMENT ON COLUMN "public"."directive"."wd_id" IS 'webdesktopi käskkirja ID, saadakse EKISest';
COMMENT ON COLUMN "public"."directive"."confirmer" IS 'kinnitaja nimi, täidetakse EKISes';
COMMENT ON COLUMN "public"."directive"."canceled_directive_id" IS 'tühistatav käskkiri, viide directive tabelile';
COMMENT ON COLUMN "public"."directive"."cancel_type_code" IS 'käskkirja tühistamise viis, viide klassifikaatorile KASKKIRI_TYHISTAMISE_VIIS';

-- ----------------------------
-- Table structure for directive_coordinator
-- ----------------------------
DROP TABLE IF EXISTS "public"."directive_coordinator";
CREATE TABLE "public"."directive_coordinator" (
"id" int8 DEFAULT nextval('directive_coordinator_id_seq'::regclass) NOT NULL,
"name" varchar(100) COLLATE "default" NOT NULL,
"idcode" varchar(11) COLLATE "default" NOT NULL,
"school_id" int4 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default",
"is_directive" bool,
"is_certificate" bool,
"is_certificate_default" bool
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."directive_coordinator" IS 'käskkirjade kooskõlastajad';
COMMENT ON COLUMN "public"."directive_coordinator"."name" IS 'kooskõlastaja nimi';
COMMENT ON COLUMN "public"."directive_coordinator"."idcode" IS 'kooskõlastaja isikukood';
COMMENT ON COLUMN "public"."directive_coordinator"."school_id" IS 'viide õppeasutusele';
COMMENT ON COLUMN "public"."directive_coordinator"."is_directive" IS 'kas on käskkirja kooskõlastaja
true - jah
false - ei
';
COMMENT ON COLUMN "public"."directive_coordinator"."is_certificate" IS 'kas on tõendi koostaja
true - jah
false - ei
';
COMMENT ON COLUMN "public"."directive_coordinator"."is_certificate_default" IS 'kas kuvatakse vaikimisi tõendi koostamisel
true - jah
false - ei
';

-- ----------------------------
-- Table structure for directive_student
-- ----------------------------
DROP TABLE IF EXISTS "public"."directive_student";
CREATE TABLE "public"."directive_student" (
"id" int8 DEFAULT nextval('directive_student_id_seq'::regclass) NOT NULL,
"student_id" int8,
"directive_id" int8 NOT NULL,
"start_date" date,
"reason_code" varchar(100) COLLATE "default",
"study_load_code" varchar(100) COLLATE "default",
"curriculum_version_id" int8,
"study_form_code" varchar(100) COLLATE "default",
"student_group_id" int8,
"fin_code" varchar(100) COLLATE "default",
"fin_specific_code" varchar(100) COLLATE "default",
"language_code" varchar(100) COLLATE "default",
"curriculum_grade_id" int8,
"is_period" bool,
"study_period_end_id" int8,
"study_period_start_id" int8,
"is_abroad" bool,
"ehis_school_code" varchar(100) COLLATE "default",
"country_code" varchar(100) COLLATE "default",
"abroad_purpose_code" varchar(100) COLLATE "default",
"abroad_programme_code" varchar(100) COLLATE "default",
"email" varchar(100) COLLATE "default",
"previous_study_level_code" varchar(100) COLLATE "default",
"is_cum_laude" bool,
"is_occupation_exam_passed" bool,
"inserted" timestamp(6) NOT NULL,
"state_language_ects_code" varchar(100) COLLATE "default",
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
"end_date" date,
"abroad_school" varchar(255) COLLATE "default",
"application_id" int8,
"person_id" int8,
"student_history_id" int8,
"sais_application_id" int8,
"nominal_study_end" date,
"canceled" bool NOT NULL
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."directive_student" IS 'käskkirjaga seotud õppurid';
COMMENT ON COLUMN "public"."directive_student"."start_date" IS 'akadeemilise puhkuse alguskuupäev või välisõppesse siirdumise alguskuupäev, täidetakse järgmiste käskkirjade liikide puhul: AKAD - Akadeemilisele puhkusele lubamine,  VALIS - Välisõpilaseks vormistamine';
COMMENT ON COLUMN "public"."directive_student"."reason_code" IS 'põhjus, viide klassifikaatorile AKADPUHKUS_POHJUS või EKSMAT_POHJUS';
COMMENT ON COLUMN "public"."directive_student"."study_load_code" IS 'õppekoormus, viide klassifikaatorile OPPEKOORMUS';
COMMENT ON COLUMN "public"."directive_student"."curriculum_version_id" IS 'õppekava versioon/rakenduskava, viide curriculum_version tabelile';
COMMENT ON COLUMN "public"."directive_student"."study_form_code" IS 'õppevorm, viide klassifikaatorile OPPEVORM';
COMMENT ON COLUMN "public"."directive_student"."student_group_id" IS 'rühm, viide student_group tabelile';
COMMENT ON COLUMN "public"."directive_student"."fin_code" IS 'finantsallikas, viide klassifikaatorile FINALLIKAS';
COMMENT ON COLUMN "public"."directive_student"."fin_specific_code" IS 'finantsallika täpsustus, viide klassifikaatorile FINTAPSUSTUS';
COMMENT ON COLUMN "public"."directive_student"."language_code" IS 'õppekeel, viide klassifikaatorile OPPEKEEL';
COMMENT ON COLUMN "public"."directive_student"."curriculum_grade_id" IS 'kraad, viide curriculum_grade tabelile';
COMMENT ON COLUMN "public"."directive_student"."is_period" IS 'kas akad puhkuse käskkirjal on õppeperiood või kp aluseks true - õppeperiood false - kuupäev';
COMMENT ON COLUMN "public"."directive_student"."study_period_end_id" IS 'perioodi lõpp (akad puhkus)';
COMMENT ON COLUMN "public"."directive_student"."study_period_start_id" IS 'perioodi algus (akad puhkus)';
COMMENT ON COLUMN "public"."directive_student"."is_abroad" IS 'kas välismaa õppeasutus või Eesti oma (käskkiri VALIS) true - välismaa false - Eesti';
COMMENT ON COLUMN "public"."directive_student"."ehis_school_code" IS 'viide Eesti õppeastusele, klassifikaator EHIS_KOOL';
COMMENT ON COLUMN "public"."directive_student"."country_code" IS 'riik, viide klassifikaatorile RIIK';
COMMENT ON COLUMN "public"."directive_student"."abroad_purpose_code" IS 'väliskoolis õpingute eesmärk ,viide klassifikaatorile VALISOPE_EESMARK';
COMMENT ON COLUMN "public"."directive_student"."abroad_programme_code" IS 'väliskoolis õpingute programm, viide klassifikaatoril VALISKOOL_PROGRAMM';
COMMENT ON COLUMN "public"."directive_student"."email" IS 'e-posti aadress';
COMMENT ON COLUMN "public"."directive_student"."is_cum_laude" IS 'kas lõpetab kiitusega / cum laude  true - kiitusega/cum laude false - ei ';
COMMENT ON COLUMN "public"."directive_student"."is_occupation_exam_passed" IS 'kas kutseeksam on sooritatud või mitte';
COMMENT ON COLUMN "public"."directive_student"."state_language_ects_code" IS 'riigikeele süvaõppe kogutavad eap,viide klassifikaatorile RIIGIKEELSYVA_EAP';
COMMENT ON COLUMN "public"."directive_student"."end_date" IS 'akadeemilise puhkuse lõppkuupäev või välisõppes viibimise lõppkuupäev, täidetakse järgmiste käskkirjade liikide puhul: AKAD - Akadeemilisele puhkusele lubamine, AKADK - Akadeemilise puhkuse katkestamine, VALIS - Välisõpilaseks vormistamine';
COMMENT ON COLUMN "public"."directive_student"."abroad_school" IS 'välismaa õppeasutus';
COMMENT ON COLUMN "public"."directive_student"."application_id" IS 'viide avaldusele';
COMMENT ON COLUMN "public"."directive_student"."person_id" IS 'viide isikule, kasutatakse immatr. käskkirja puhul';
COMMENT ON COLUMN "public"."directive_student"."student_history_id" IS 'viide õppuri ajaloo tabelile';
COMMENT ON COLUMN "public"."directive_student"."sais_application_id" IS 'viide sais avaldusele';
COMMENT ON COLUMN "public"."directive_student"."canceled" IS 'kas tühistatud';

-- ----------------------------
-- Table structure for enterprise
-- ----------------------------
DROP TABLE IF EXISTS "public"."enterprise";
CREATE TABLE "public"."enterprise" (
"id" int8 DEFAULT nextval('enterprise_id_seq'::regclass) NOT NULL,
"reg_code" varchar(20) COLLATE "default" NOT NULL,
"name" varchar(100) COLLATE "default" NOT NULL,
"contact_person_name" varchar(100) COLLATE "default",
"contact_person_phone" varchar(100) COLLATE "default",
"contact_person_email" varchar(100) COLLATE "default",
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."enterprise" IS 'ettevõtete register';
COMMENT ON COLUMN "public"."enterprise"."reg_code" IS 'ettevõtte reg. kood';
COMMENT ON COLUMN "public"."enterprise"."name" IS 'ettevõtte nimi';
COMMENT ON COLUMN "public"."enterprise"."contact_person_name" IS 'kontaktisiku nimi';
COMMENT ON COLUMN "public"."enterprise"."contact_person_phone" IS 'kontaktisiku telefon';
COMMENT ON COLUMN "public"."enterprise"."contact_person_email" IS 'kontaktisiku e-mail';

-- ----------------------------
-- Table structure for general_message
-- ----------------------------
DROP TABLE IF EXISTS "public"."general_message";
CREATE TABLE "public"."general_message" (
"id" int8 DEFAULT nextval('general_message_id_seq'::regclass) NOT NULL,
"school_id" int4 NOT NULL,
"title" varchar(1000) COLLATE "default" NOT NULL,
"content" varchar(4000) COLLATE "default" NOT NULL,
"valid_from" timestamp(6),
"valid_thru" timestamp(6),
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."general_message" IS 'üldteated';
COMMENT ON COLUMN "public"."general_message"."school_id" IS 'viide õppeasutusele';
COMMENT ON COLUMN "public"."general_message"."title" IS 'pealkiri';
COMMENT ON COLUMN "public"."general_message"."valid_from" IS 'kuvamise algus kuupäev';
COMMENT ON COLUMN "public"."general_message"."valid_thru" IS 'kuvamise lõppkuupäev';

-- ----------------------------
-- Table structure for general_message_target
-- ----------------------------
DROP TABLE IF EXISTS "public"."general_message_target";
CREATE TABLE "public"."general_message_target" (
"id" int8 DEFAULT nextval('general_message_target_id_seq'::regclass) NOT NULL,
"general_message_id" int8 NOT NULL,
"role_code" varchar(100) COLLATE "default" NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."general_message_target" IS 'üldteate sihtgrupid';
COMMENT ON COLUMN "public"."general_message_target"."general_message_id" IS 'viide üldteatele';
COMMENT ON COLUMN "public"."general_message_target"."role_code" IS 'rollide klassifikaator';

-- ----------------------------
-- Table structure for job
-- ----------------------------
DROP TABLE IF EXISTS "public"."job";
CREATE TABLE "public"."job" (
"id" int8 DEFAULT nextval('job_id_seq'::regclass) NOT NULL,
"school_id" int8,
"type_code" varchar(100) COLLATE "default" NOT NULL,
"status_code" varchar(100) COLLATE "default" NOT NULL,
"directive_id" int8,
"job_time" timestamp(6) NOT NULL,
"student_id" int8,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"contract_id" int8
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."job" IS 'töötabel';
COMMENT ON COLUMN "public"."job"."school_id" IS 'viide õppeasutusele';
COMMENT ON COLUMN "public"."job"."type_code" IS 'viide klassifikaatorile JOB';
COMMENT ON COLUMN "public"."job"."status_code" IS 'viide klassifikaatorile JOB_STATUS';
COMMENT ON COLUMN "public"."job"."directive_id" IS 'viide käskkirjale';
COMMENT ON COLUMN "public"."job"."job_time" IS 'töö täitmise aeg';
COMMENT ON COLUMN "public"."job"."student_id" IS 'viide õppurile';
COMMENT ON COLUMN "public"."job"."inserted" IS 'töö lisamise aeg';
COMMENT ON COLUMN "public"."job"."changed" IS 'töö muutmise aeg';
COMMENT ON COLUMN "public"."job"."contract_id" IS 'viide lepingule';

-- ----------------------------
-- Table structure for journal
-- ----------------------------
DROP TABLE IF EXISTS "public"."journal";
CREATE TABLE "public"."journal" (
"id" int8 DEFAULT nextval('journal_id_seq'::regclass) NOT NULL,
"school_id" int8 NOT NULL,
"name_et" varchar(255) COLLATE "default" NOT NULL,
"study_year_id" int8 NOT NULL,
"assessment_code" varchar(100) COLLATE "default" NOT NULL,
"group_proportion_code" varchar(100) COLLATE "default" NOT NULL,
"inserted" timestamp(6) NOT NULL,
"status_code" varchar(100) COLLATE "default" NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
"end_date" date
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."journal" IS 'päevik';
COMMENT ON COLUMN "public"."journal"."school_id" IS 'viide õppeasutusele';
COMMENT ON COLUMN "public"."journal"."name_et" IS 'päeviku nimi';
COMMENT ON COLUMN "public"."journal"."study_year_id" IS 'viide õppeaastale';
COMMENT ON COLUMN "public"."journal"."assessment_code" IS 'viide klassifikaatorile KUTSEHINDAMISVIIS';
COMMENT ON COLUMN "public"."journal"."group_proportion_code" IS 'viide klassifikaatorile PAEVIK_GRUPI_JAOTUS';
COMMENT ON COLUMN "public"."journal"."status_code" IS 'viide klassifikaatorile PAEVIK_STAATUS';
COMMENT ON COLUMN "public"."journal"."end_date" IS 'päeviku lõppkuupäev';

-- ----------------------------
-- Table structure for journal_capacity
-- ----------------------------
DROP TABLE IF EXISTS "public"."journal_capacity";
CREATE TABLE "public"."journal_capacity" (
"id" int8 DEFAULT nextval('journal_capacity_id_seq'::regclass) NOT NULL,
"journal_id" int8 NOT NULL,
"journal_capacity_type_id" int8 NOT NULL,
"study_period_id" int8 NOT NULL,
"week_nr" int2,
"hours" int2 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
"version" int4 NOT NULL
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."journal_capacity" IS 'päeviku mahud';
COMMENT ON COLUMN "public"."journal_capacity"."journal_id" IS 'viide päevikule';
COMMENT ON COLUMN "public"."journal_capacity"."journal_capacity_type_id" IS 'viide päeviku mahtude tüüpidele';
COMMENT ON COLUMN "public"."journal_capacity"."study_period_id" IS 'viide õppeperioodile';
COMMENT ON COLUMN "public"."journal_capacity"."week_nr" IS 'nädala nr';
COMMENT ON COLUMN "public"."journal_capacity"."hours" IS 'tundide arv';

-- ----------------------------
-- Table structure for journal_capacity_type
-- ----------------------------
DROP TABLE IF EXISTS "public"."journal_capacity_type";
CREATE TABLE "public"."journal_capacity_type" (
"id" int8 DEFAULT nextval('journal_capacity_type_id_seq'::regclass) NOT NULL,
"journal_id" int8 NOT NULL,
"capacity_type_code" varchar(100) COLLATE "default" NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."journal_capacity_type" IS 'päeviku mahtude tüübid/õppetöö liigid';
COMMENT ON COLUMN "public"."journal_capacity_type"."journal_id" IS 'viide päevikule';
COMMENT ON COLUMN "public"."journal_capacity_type"."capacity_type_code" IS 'viide klassifikaatorile MAHT';

-- ----------------------------
-- Table structure for journal_entry
-- ----------------------------
DROP TABLE IF EXISTS "public"."journal_entry";
CREATE TABLE "public"."journal_entry" (
"id" int8 DEFAULT nextval('journal_entry_id_seq'::regclass) NOT NULL,
"journal_id" int8 NOT NULL,
"entry_type_code" varchar(100) COLLATE "default" NOT NULL,
"name_et" varchar(100) COLLATE "default",
"entry_date" date,
"start_lesson_nr" int2,
"lessons" int2,
"content" text COLLATE "default",
"homework" text COLLATE "default",
"homework_duedate" date,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."journal_entry" IS 'päeviku sissekanne';
COMMENT ON COLUMN "public"."journal_entry"."journal_id" IS 'viide päevikule';
COMMENT ON COLUMN "public"."journal_entry"."entry_type_code" IS 'sissekande liik, viide klassifikaatorile SISSEKANNE';
COMMENT ON COLUMN "public"."journal_entry"."name_et" IS 'sissekande nimetus';
COMMENT ON COLUMN "public"."journal_entry"."entry_date" IS 'sissekande kuupäev';
COMMENT ON COLUMN "public"."journal_entry"."start_lesson_nr" IS 'algustund';
COMMENT ON COLUMN "public"."journal_entry"."lessons" IS 'tundide arv';
COMMENT ON COLUMN "public"."journal_entry"."content" IS 'sisu';
COMMENT ON COLUMN "public"."journal_entry"."homework" IS 'kodutöö';
COMMENT ON COLUMN "public"."journal_entry"."homework_duedate" IS 'kodutöö tähtaeg';

-- ----------------------------
-- Table structure for journal_entry_capacity_type
-- ----------------------------
DROP TABLE IF EXISTS "public"."journal_entry_capacity_type";
CREATE TABLE "public"."journal_entry_capacity_type" (
"id" int8 DEFAULT nextval('journal_entry_capacity_type_id_seq'::regclass) NOT NULL,
"journal_entry_id" int8 NOT NULL,
"capacity_type_code" varchar(100) COLLATE "default" NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."journal_entry_capacity_type" IS 'sissekande ja mahtude seos';
COMMENT ON COLUMN "public"."journal_entry_capacity_type"."journal_entry_id" IS 'viide päeviku sissekandele';

-- ----------------------------
-- Table structure for journal_entry_student
-- ----------------------------
DROP TABLE IF EXISTS "public"."journal_entry_student";
CREATE TABLE "public"."journal_entry_student" (
"id" int8 DEFAULT nextval('journal_entry_student_id_seq'::regclass) NOT NULL,
"journal_entry_id" int8 NOT NULL,
"journal_student_id" int8 NOT NULL,
"absence_code" varchar(100) COLLATE "default",
"absence_inserted" timestamp(6),
"absence_accepted" timestamp(6),
"grade_code" varchar(100) COLLATE "default",
"grade_inserted" timestamp(6),
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
"add_info" varchar(1000) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."journal_entry_student" IS 'sissekande ja õppuri seos';
COMMENT ON COLUMN "public"."journal_entry_student"."journal_entry_id" IS 'viide päeviku sissekandele';
COMMENT ON COLUMN "public"."journal_entry_student"."journal_student_id" IS 'viide päevikuga seotud õppurile';
COMMENT ON COLUMN "public"."journal_entry_student"."absence_code" IS 'puudumise kood, viide klassifikaatorile PUUDUMINE';
COMMENT ON COLUMN "public"."journal_entry_student"."absence_inserted" IS 'puudumise lisamise aeg';
COMMENT ON COLUMN "public"."journal_entry_student"."absence_accepted" IS 'puudumise aktsepteerimise aeg';
COMMENT ON COLUMN "public"."journal_entry_student"."grade_code" IS 'viimane kehtiv hinne, viide klassifikaatorile KUTSEHINDAMINE';
COMMENT ON COLUMN "public"."journal_entry_student"."grade_inserted" IS 'hinde lisamise kuupäev';
COMMENT ON COLUMN "public"."journal_entry_student"."inserted_by" IS 'selgitus';

-- ----------------------------
-- Table structure for journal_entry_student_history
-- ----------------------------
DROP TABLE IF EXISTS "public"."journal_entry_student_history";
CREATE TABLE "public"."journal_entry_student_history" (
"id" int8 DEFAULT nextval('journal_entry_student_history_id_seq'::regclass) NOT NULL,
"journal_entry_student_id" int8 NOT NULL,
"grade_code" varchar(100) COLLATE "default" NOT NULL,
"grade_inserted" timestamp(6) NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."journal_entry_student_history" IS 'õppuri sissekandega seotud eelmised tulemused';
COMMENT ON COLUMN "public"."journal_entry_student_history"."journal_entry_student_id" IS 'viide õppuri sissekandele';
COMMENT ON COLUMN "public"."journal_entry_student_history"."grade_code" IS 'viide klassifikaatorile KUTSEHINDAMINE';
COMMENT ON COLUMN "public"."journal_entry_student_history"."grade_inserted" IS 'hinde lisamise kuupäev';

-- ----------------------------
-- Table structure for journal_omodule_theme
-- ----------------------------
DROP TABLE IF EXISTS "public"."journal_omodule_theme";
CREATE TABLE "public"."journal_omodule_theme" (
"id" int8 DEFAULT nextval('journal_omodule_theme_id_seq'::regclass) NOT NULL,
"journal_id" int8 NOT NULL,
"lesson_plan_module_id" int8 NOT NULL,
"curriculum_version_omodule_theme_id" int8 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."journal_omodule_theme" IS 'päeviku ja õpetatavate teemade/moodulite/rühmade seos';
COMMENT ON COLUMN "public"."journal_omodule_theme"."journal_id" IS 'viide päevikule';
COMMENT ON COLUMN "public"."journal_omodule_theme"."lesson_plan_module_id" IS 'viide planeeritud moodulile ja sealt kaudu ka rühmale';
COMMENT ON COLUMN "public"."journal_omodule_theme"."curriculum_version_omodule_theme_id" IS 'viide rakenduskava teemale';

-- ----------------------------
-- Table structure for journal_room
-- ----------------------------
DROP TABLE IF EXISTS "public"."journal_room";
CREATE TABLE "public"."journal_room" (
"id" int8 DEFAULT nextval('journal_room_id_seq'::regclass) NOT NULL,
"journal_id" int8 NOT NULL,
"room_id" int8 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."journal_room" IS 'päeviku planeerimisel soovitud ruumid';
COMMENT ON COLUMN "public"."journal_room"."journal_id" IS 'viide päevikule';
COMMENT ON COLUMN "public"."journal_room"."room_id" IS 'viide ruumile';

-- ----------------------------
-- Table structure for journal_student
-- ----------------------------
DROP TABLE IF EXISTS "public"."journal_student";
CREATE TABLE "public"."journal_student" (
"id" int8 DEFAULT nextval('journal_student_id_seq'::regclass) NOT NULL,
"journal_id" int8 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"student_id" int8 NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."journal_student" IS 'päevikuga seotud õppurid';
COMMENT ON COLUMN "public"."journal_student"."journal_id" IS 'viide päevikule';
COMMENT ON COLUMN "public"."journal_student"."student_id" IS 'viide õppurile';

-- ----------------------------
-- Table structure for journal_teacher
-- ----------------------------
DROP TABLE IF EXISTS "public"."journal_teacher";
CREATE TABLE "public"."journal_teacher" (
"id" int8 DEFAULT nextval('journal_teacher_id_seq'::regclass) NOT NULL,
"journal_id" int8 NOT NULL,
"teacher_id" int8 NOT NULL,
"is_filler" bool NOT NULL,
"is_confirmer" bool NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."journal_teacher" IS 'päeviku õpetajad';
COMMENT ON COLUMN "public"."journal_teacher"."journal_id" IS 'viide päevikule';
COMMENT ON COLUMN "public"."journal_teacher"."teacher_id" IS 'viide õpetajale';
COMMENT ON COLUMN "public"."journal_teacher"."is_filler" IS 'kas on päeviku täitja true - jah false - ei';
COMMENT ON COLUMN "public"."journal_teacher"."is_confirmer" IS 'kas on päeviku kinnitaja true - jah false - ei';

-- ----------------------------
-- Table structure for lesson_plan
-- ----------------------------
DROP TABLE IF EXISTS "public"."lesson_plan";
CREATE TABLE "public"."lesson_plan" (
"id" int8 DEFAULT nextval('lesson_plan_id_seq'::regclass) NOT NULL,
"study_year_id" int8 NOT NULL,
"is_usable" bool NOT NULL,
"school_id" int8 NOT NULL,
"show_weeks" bool NOT NULL,
"student_group_id" int8 NOT NULL,
"curriculum_version_id" int8 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."lesson_plan" IS 'tunnijaotusplaan';
COMMENT ON COLUMN "public"."lesson_plan"."study_year_id" IS 'viide õppeaastale';
COMMENT ON COLUMN "public"."lesson_plan"."is_usable" IS 'kas on kasutatav true - jah false - ei';
COMMENT ON COLUMN "public"."lesson_plan"."school_id" IS 'viide õppeasutusele';
COMMENT ON COLUMN "public"."lesson_plan"."show_weeks" IS 'kas kuvada nädalate nr true - jah false - ei';
COMMENT ON COLUMN "public"."lesson_plan"."student_group_id" IS 'viide õpperühmale';
COMMENT ON COLUMN "public"."lesson_plan"."curriculum_version_id" IS 'viide rakenduskavale';

-- ----------------------------
-- Table structure for lesson_plan_module
-- ----------------------------
DROP TABLE IF EXISTS "public"."lesson_plan_module";
CREATE TABLE "public"."lesson_plan_module" (
"id" int8 DEFAULT nextval('lesson_plan_module_id_seq'::regclass) NOT NULL,
"lesson_plan_id" int8 NOT NULL,
"curriculum_version_omodule_id" int8 NOT NULL,
"teacher_id" int8,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
"version" int4 NOT NULL
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."lesson_plan_module" IS 'tunnijaotusplaanis planeeritud moodulid';
COMMENT ON COLUMN "public"."lesson_plan_module"."lesson_plan_id" IS 'viide tunnijaotusplaanile';
COMMENT ON COLUMN "public"."lesson_plan_module"."curriculum_version_omodule_id" IS 'viide planeeritud moodulile';
COMMENT ON COLUMN "public"."lesson_plan_module"."teacher_id" IS 'vastutaja, viide õpetajale';

-- ----------------------------
-- Table structure for lesson_time
-- ----------------------------
DROP TABLE IF EXISTS "public"."lesson_time";
CREATE TABLE "public"."lesson_time" (
"id" int8 DEFAULT nextval('lesson_time_id_seq'::regclass) NOT NULL,
"school_id" int8 NOT NULL,
"lesson_time_building_group_id" int8 NOT NULL,
"start_time" time(6) NOT NULL,
"end_time" time(6) NOT NULL,
"lesson_nr" int2 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
"day_mon" bool NOT NULL,
"day_tue" bool NOT NULL,
"day_wed" bool NOT NULL,
"day_thu" bool NOT NULL,
"day_fri" bool NOT NULL,
"day_sat" bool NOT NULL,
"day_sun" bool NOT NULL
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."lesson_time" IS 'tundide toimumise ajad';
COMMENT ON COLUMN "public"."lesson_time"."school_id" IS 'viide õppeasutusele';
COMMENT ON COLUMN "public"."lesson_time"."lesson_time_building_group_id" IS 'viide tundide aegade plokile';
COMMENT ON COLUMN "public"."lesson_time"."start_time" IS 'algus kellaaeg';
COMMENT ON COLUMN "public"."lesson_time"."end_time" IS 'lõpp kellaaeg';
COMMENT ON COLUMN "public"."lesson_time"."lesson_nr" IS 'tunni nr';
COMMENT ON COLUMN "public"."lesson_time"."day_mon" IS 'kas kehtib esmasp. true - jah false - ei';
COMMENT ON COLUMN "public"."lesson_time"."day_tue" IS 'kas kehtib teisip. true - jah false - ei';
COMMENT ON COLUMN "public"."lesson_time"."day_wed" IS 'kas kehtib kolmap. true - jah false - ei';
COMMENT ON COLUMN "public"."lesson_time"."day_thu" IS 'kas kehtib neljap. true - jah false - ei';
COMMENT ON COLUMN "public"."lesson_time"."day_fri" IS 'kas kehtib reedel true - jah false - ei';
COMMENT ON COLUMN "public"."lesson_time"."day_sat" IS 'kas kehtib laup. true - jah false - ei';
COMMENT ON COLUMN "public"."lesson_time"."day_sun" IS 'kas kehtib pühap. true - jah false - ei';

-- ----------------------------
-- Table structure for lesson_time_building
-- ----------------------------
DROP TABLE IF EXISTS "public"."lesson_time_building";
CREATE TABLE "public"."lesson_time_building" (
"id" int8 DEFAULT nextval('lesson_time_building_id_seq'::regclass) NOT NULL,
"building_id" int8 NOT NULL,
"lesson_time_building_group_id" int8 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."lesson_time_building" IS 'tunni toimumise koht ja kehtivus';
COMMENT ON COLUMN "public"."lesson_time_building"."building_id" IS 'viide hoonele';
COMMENT ON COLUMN "public"."lesson_time_building"."lesson_time_building_group_id" IS 'viide tundide aegade plokile';

-- ----------------------------
-- Table structure for lesson_time_building_group
-- ----------------------------
DROP TABLE IF EXISTS "public"."lesson_time_building_group";
CREATE TABLE "public"."lesson_time_building_group" (
"id" int8 DEFAULT nextval('lesson_time_building_group_id_seq'::regclass) NOT NULL,
"valid_from" date NOT NULL,
"valid_thru" date,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."lesson_time_building_group" IS 'tundide aegade kehtivuse plokk';
COMMENT ON COLUMN "public"."lesson_time_building_group"."valid_from" IS 'kehtivuse algus';
COMMENT ON COLUMN "public"."lesson_time_building_group"."valid_thru" IS 'kehtivuse lõpp';


-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS "public"."message";
CREATE TABLE "public"."message" (
"id" int8 DEFAULT nextval('message_id_seq'::regclass) NOT NULL,
"subject" varchar(1000) COLLATE "default" NOT NULL,
"content" text COLLATE "default" NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
"person_id" int8 NOT NULL,
"role_code" varchar(100) COLLATE "default",
"school_id" int8,
"message_id" int8
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."message" IS 'teade';
COMMENT ON COLUMN "public"."message"."subject" IS 'pealkiri/teema';
COMMENT ON COLUMN "public"."message"."content" IS 'sisu';
COMMENT ON COLUMN "public"."message"."person_id" IS 'teate saatja';
COMMENT ON COLUMN "public"."message"."role_code" IS 'teate saatja roll';
COMMENT ON COLUMN "public"."message"."school_id" IS 'teate saatja õppeasutus, täidetakse õppuri, lapsevanema, admin töötaja, õpetaja puhul';
COMMENT ON COLUMN "public"."message"."message_id" IS 'viide teatele, millele vastati';

-- ----------------------------
-- Table structure for message_receiver
-- ----------------------------
DROP TABLE IF EXISTS "public"."message_receiver";
CREATE TABLE "public"."message_receiver" (
"id" int8 DEFAULT nextval('message_receiver_id_seq'::regclass) NOT NULL,
"message_id" int8 NOT NULL,
"person_id" int8 NOT NULL,
"read" timestamp(6),
"inserted" timestamp(6) NOT NULL,
"status_code" varchar(100) COLLATE "default" NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."message_receiver" IS 'teate saaja';
COMMENT ON COLUMN "public"."message_receiver"."message_id" IS 'viide teatele';
COMMENT ON COLUMN "public"."message_receiver"."person_id" IS 'viide isikule';
COMMENT ON COLUMN "public"."message_receiver"."read" IS 'lugemise aeg';
COMMENT ON COLUMN "public"."message_receiver"."status_code" IS 'viide klassifikaatorile TEATESTAATUS, vaikimisi TEATESTAATUS_U';

-- ----------------------------
-- Table structure for message_template
-- ----------------------------
DROP TABLE IF EXISTS "public"."message_template";
CREATE TABLE "public"."message_template" (
"id" int8 DEFAULT nextval('message_template_id_seq'::regclass) NOT NULL,
"school_id" int8 NOT NULL,
"type_code" varchar(100) COLLATE "default" NOT NULL,
"headline" varchar(1000) COLLATE "default" NOT NULL,
"content" text COLLATE "default" NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
"valid_from" date,
"valid_thru" date
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."message_template" IS 'teate mall';
COMMENT ON COLUMN "public"."message_template"."school_id" IS 'viide õppeasutusele';
COMMENT ON COLUMN "public"."message_template"."type_code" IS 'viide klassifikaatori TEATE_LIIK';
COMMENT ON COLUMN "public"."message_template"."headline" IS 'pealkiri';
COMMENT ON COLUMN "public"."message_template"."content" IS 'sisu';
COMMENT ON COLUMN "public"."message_template"."valid_from" IS 'kehtivuse algus';
COMMENT ON COLUMN "public"."message_template"."valid_thru" IS 'kehtivuse lõpp';

-- ----------------------------
-- Table structure for midterm_task
-- ----------------------------
DROP TABLE IF EXISTS "public"."midterm_task";
CREATE TABLE "public"."midterm_task" (
"id" int8 DEFAULT nextval('midterm_task_id_seq'::regclass) NOT NULL,
"subject_study_period_id" int8 NOT NULL,
"name_et" varchar(255) COLLATE "default" NOT NULL,
"name_en" varchar(255) COLLATE "default",
"description_et" varchar(4000) COLLATE "default" NOT NULL,
"description_en" varchar(4000) COLLATE "default",
"max_points" numeric(4,1) NOT NULL,
"percentage" int2 NOT NULL,
"task_date" date,
"threshold" bool,
"threshold_percentage" int2,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."midterm_task" IS 'vahetööd';
COMMENT ON COLUMN "public"."midterm_task"."subject_study_period_id" IS 'viide aine-õppejõu paarile';
COMMENT ON COLUMN "public"."midterm_task"."name_et" IS 'nimetus';
COMMENT ON COLUMN "public"."midterm_task"."name_en" IS 'nimetus i.k.';
COMMENT ON COLUMN "public"."midterm_task"."description_et" IS 'kirjeldus';
COMMENT ON COLUMN "public"."midterm_task"."description_en" IS 'kirjeldus i.k.';
COMMENT ON COLUMN "public"."midterm_task"."max_points" IS 'maksimaalsed punktid';
COMMENT ON COLUMN "public"."midterm_task"."percentage" IS 'osakaal';
COMMENT ON COLUMN "public"."midterm_task"."task_date" IS 'vahesoorituse kuupäev';
COMMENT ON COLUMN "public"."midterm_task"."threshold" IS 'lävend';
COMMENT ON COLUMN "public"."midterm_task"."threshold_percentage" IS 'lävendi %';

-- ----------------------------
-- Table structure for midterm_task_student_result
-- ----------------------------
DROP TABLE IF EXISTS "public"."midterm_task_student_result";
CREATE TABLE "public"."midterm_task_student_result" (
"id" int8 DEFAULT nextval('midterm_task_student_result_id_seq'::regclass) NOT NULL,
"midterm_task_id" int8 NOT NULL,
"declaration_subject_id" int8 NOT NULL,
"points" numeric(5,2),
"points_txt" varchar(10) COLLATE "default",
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
"version" int4 NOT NULL
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."midterm_task_student_result"."midterm_task_id" IS 'viide vahetööle';
COMMENT ON COLUMN "public"."midterm_task_student_result"."declaration_subject_id" IS 'viide õppuri deklaratsioonile';
COMMENT ON COLUMN "public"."midterm_task_student_result"."points" IS 'punktid numbrilisel kujul';
COMMENT ON COLUMN "public"."midterm_task_student_result"."points_txt" IS 'punktid tekstilisel kujul';

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
"changed_by" varchar(100) COLLATE "default",
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"foreign_idcode" varchar(50) COLLATE "default",
"sex_code" varchar(100) COLLATE "default",
"citizenship_code" varchar(100) COLLATE "default",
"bankaccount" varchar(50) COLLATE "default",
"language_code" varchar(100) COLLATE "default",
"phone" varchar(100) COLLATE "default",
"address" varchar(100) COLLATE "default",
"residence_country_code" varchar(100) COLLATE "default",
"postcode" varchar(20) COLLATE "default",
"email" varchar(100) COLLATE "default",
"native_language" varchar(100) COLLATE "default",
"birthdate" date,
"address_ads" varchar(50) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."person" IS 'isikute tabel';
COMMENT ON COLUMN "public"."person"."firstname" IS 'eesnimi';
COMMENT ON COLUMN "public"."person"."lastname" IS 'perekonnanimi';
COMMENT ON COLUMN "public"."person"."idcode" IS 'eesti isikukood';
COMMENT ON COLUMN "public"."person"."foreign_idcode" IS 'välismaa isikukood';
COMMENT ON COLUMN "public"."person"."sex_code" IS 'soo koo, klassifikaator SUGU';
COMMENT ON COLUMN "public"."person"."citizenship_code" IS 'kodakondsuse kood, viid riikide klassifikaatorile';
COMMENT ON COLUMN "public"."person"."bankaccount" IS 'kono number';
COMMENT ON COLUMN "public"."person"."language_code" IS 'suhtluskeele kood, klassifikaator õppekeel, vaikimisi EESTI';
COMMENT ON COLUMN "public"."person"."phone" IS 'kontakttelefon';
COMMENT ON COLUMN "public"."person"."address" IS 'postiaadress ADS kujul';
COMMENT ON COLUMN "public"."person"."residence_country_code" IS 'elukohamaa, viide riikide klassifikaatorile';
COMMENT ON COLUMN "public"."person"."postcode" IS 'postiindeks';
COMMENT ON COLUMN "public"."person"."email" IS 'isiku isiklik e-mail, õppuril ja õpetajal võib olla hoopis teine e-mail';
COMMENT ON COLUMN "public"."person"."native_language" IS 'emakeel';
COMMENT ON COLUMN "public"."person"."address_ads" IS 'aadress ADS kujul';

-- ----------------------------
-- Table structure for practice_journal
-- ----------------------------
DROP TABLE IF EXISTS "public"."practice_journal";
CREATE TABLE "public"."practice_journal" (
"id" int8 DEFAULT nextval('practice_journal_id_seq'::regclass) NOT NULL,
"school_id" int8 NOT NULL,
"study_year_id" int8 NOT NULL,
"student_id" int8 NOT NULL,
"contract_id" int8,
"curriculum_version_omodule_id" int8,
"curriculum_version_omodule_theme_id" int8,
"credits" numeric(4,1) NOT NULL,
"hours" int2 NOT NULL,
"start_date" date NOT NULL,
"end_date" date NOT NULL,
"practice_place" varchar(255) COLLATE "default" NOT NULL,
"practice_plan" text COLLATE "default" NOT NULL,
"teacher_id" int8 NOT NULL,
"status_code" varchar(100) COLLATE "default" NOT NULL,
"grade_code" varchar(100) COLLATE "default",
"grade_inserted" timestamp(6),
"practice_report" text COLLATE "default",
"supervisor_comment" text COLLATE "default",
"supervisor_opinion" text COLLATE "default",
"teacher_comment" text COLLATE "default",
"teacher_opinion" text COLLATE "default",
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
"subject_id" int8
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."practice_journal" IS 'praktika päevik';
COMMENT ON COLUMN "public"."practice_journal"."student_id" IS 'viide õppurile';
COMMENT ON COLUMN "public"."practice_journal"."contract_id" IS 'viide lepingule';
COMMENT ON COLUMN "public"."practice_journal"."curriculum_version_omodule_id" IS 'viide moodulile';
COMMENT ON COLUMN "public"."practice_journal"."curriculum_version_omodule_theme_id" IS 'viide teemale';
COMMENT ON COLUMN "public"."practice_journal"."credits" IS 'maht EKAP';
COMMENT ON COLUMN "public"."practice_journal"."hours" IS 'maht tundides';
COMMENT ON COLUMN "public"."practice_journal"."start_date" IS 'praktika algus';
COMMENT ON COLUMN "public"."practice_journal"."end_date" IS 'praktika lõpp';
COMMENT ON COLUMN "public"."practice_journal"."grade_code" IS 'hinne, viide klassifikaatorile KUTSEHINDAMINE';
COMMENT ON COLUMN "public"."practice_journal"."practice_report" IS 'õppuri praktika aruanne';
COMMENT ON COLUMN "public"."practice_journal"."supervisor_comment" IS 'juhendaja üldine kommentaar';
COMMENT ON COLUMN "public"."practice_journal"."supervisor_opinion" IS 'juhendaja arvamus';
COMMENT ON COLUMN "public"."practice_journal"."teacher_comment" IS 'õpetaja üldine kommentaar';
COMMENT ON COLUMN "public"."practice_journal"."teacher_opinion" IS 'õpetaja arvamus';
COMMENT ON COLUMN "public"."practice_journal"."subject_id" IS 'viide õppeainele';

-- ----------------------------
-- Table structure for practice_journal_entry
-- ----------------------------
DROP TABLE IF EXISTS "public"."practice_journal_entry";
CREATE TABLE "public"."practice_journal_entry" (
"id" int8 DEFAULT nextval('practice_journal_entry_id_seq'::regclass) NOT NULL,
"description" text COLLATE "default",
"practice_journal_id" int8 NOT NULL,
"practice_date" date NOT NULL,
"hours" int2,
"supervisor_comment" text COLLATE "default",
"teacher_comment" text COLLATE "default",
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."practice_journal_entry" IS 'praktika päeviku kanne';
COMMENT ON COLUMN "public"."practice_journal_entry"."description" IS 'praktika sisu';
COMMENT ON COLUMN "public"."practice_journal_entry"."practice_journal_id" IS 'viide praktika päevikule';
COMMENT ON COLUMN "public"."practice_journal_entry"."practice_date" IS 'praktika kuupäev';
COMMENT ON COLUMN "public"."practice_journal_entry"."hours" IS 'tundide arv';
COMMENT ON COLUMN "public"."practice_journal_entry"."supervisor_comment" IS 'juhendaja kommentaar';
COMMENT ON COLUMN "public"."practice_journal_entry"."teacher_comment" IS 'õppeasutuse juhendaja kommentaar';

-- ----------------------------
-- Table structure for practice_journal_file
-- ----------------------------
DROP TABLE IF EXISTS "public"."practice_journal_file";
CREATE TABLE "public"."practice_journal_file" (
"id" int8 DEFAULT nextval('practice_journal_file_id_seq'::regclass) NOT NULL,
"ois_file_id" int8 NOT NULL,
"practice_journal_id" int8 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."practice_journal_file" IS 'praktika päevikuga seotud failid';
COMMENT ON COLUMN "public"."practice_journal_file"."ois_file_id" IS 'viide failile';
COMMENT ON COLUMN "public"."practice_journal_file"."practice_journal_id" IS 'viide praktika päevikule';

-- ----------------------------
-- Table structure for protocol
-- ----------------------------
DROP TABLE IF EXISTS "public"."protocol";
CREATE TABLE "public"."protocol" (
"id" int8 DEFAULT nextval('protocol_id_seq'::regclass) NOT NULL,
"is_vocational" bool NOT NULL,
"inserted" timestamp(6) NOT NULL,
"school_id" int8 NOT NULL,
"status_code" varchar(100) COLLATE "default" NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
"protocol_nr" varchar(20) COLLATE "default" NOT NULL,
"confirm_date" date,
"confirmer" varchar(100) COLLATE "default",
"ois_file_id" int8
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."protocol" IS 'protokoll';
COMMENT ON COLUMN "public"."protocol"."is_vocational" IS 'kas on kutseõppe või kõrghtaidusõppe protokoll: true - kutse false - kõrghraidus';
COMMENT ON COLUMN "public"."protocol"."school_id" IS 'viide õppeasutusele';
COMMENT ON COLUMN "public"."protocol"."status_code" IS 'viide klassifikaatorile PROTOKOLL_STAATUS';
COMMENT ON COLUMN "public"."protocol"."protocol_nr" IS 'protokolli number';
COMMENT ON COLUMN "public"."protocol"."confirm_date" IS 'kinnitamise kuupäev';
COMMENT ON COLUMN "public"."protocol"."confirmer" IS 'kinnitaja isiku nimi';
COMMENT ON COLUMN "public"."protocol"."ois_file_id" IS 'viide digiallkirjastatud pdf-le';

-- ----------------------------
-- Table structure for protocol_hdata
-- ----------------------------
DROP TABLE IF EXISTS "public"."protocol_hdata";
CREATE TABLE "public"."protocol_hdata" (
"protocol_id" int8 NOT NULL,
"subject_study_period_id" int8 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
"type_code" varchar(100) COLLATE "default" NOT NULL
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."protocol_hdata" IS 'kõrgharidusõppe protokolli andmed';

-- ----------------------------
-- Table structure for protocol_student
-- ----------------------------
DROP TABLE IF EXISTS "public"."protocol_student";
CREATE TABLE "public"."protocol_student" (
"id" int8 DEFAULT nextval('protocol_student_id_seq'::regclass) NOT NULL,
"protocol_id" int8 NOT NULL,
"student_id" int8 NOT NULL,
"grade" varchar(3) COLLATE "default",
"grade_code" varchar(100) COLLATE "default",
"grade_mark" int2,
"grade_date" date,
"add_info" varchar(255) COLLATE "default",
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."protocol_student" IS 'protokolliga seotud õppurid';
COMMENT ON COLUMN "public"."protocol_student"."protocol_id" IS 'viide protokollile';
COMMENT ON COLUMN "public"."protocol_student"."grade" IS 'tulemus kujul ''A'', ''B'', ''5'' jne';
COMMENT ON COLUMN "public"."protocol_student"."grade_code" IS 'viide klassifikaatorile KORGHINDAMINE või KUTSEHINDAMINE sõltuvalt õppetasemest ja protokollile valitud hindamisviisist';
COMMENT ON COLUMN "public"."protocol_student"."grade_mark" IS 'numbriline hinne, vajalik KKH arvutamiseks';
COMMENT ON COLUMN "public"."protocol_student"."grade_date" IS 'hinde kuupäev, vaikimisi hinde lisamise kuupäev';
COMMENT ON COLUMN "public"."protocol_student"."add_info" IS 'kommentaar hinde kohta';

-- ----------------------------
-- Table structure for protocol_student_history
-- ----------------------------
DROP TABLE IF EXISTS "public"."protocol_student_history";
CREATE TABLE "public"."protocol_student_history" (
"id" int8 DEFAULT nextval('protocol_student_history_id_seq'::regclass) NOT NULL,
"grade_code" varchar(100) COLLATE "default" NOT NULL,
"protocol_student_id" int8 NOT NULL,
"add_info" varchar(255) COLLATE "default",
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."protocol_student_history" IS 'protokollil õppuri tulemuste muutmise ajalugu';
COMMENT ON COLUMN "public"."protocol_student_history"."add_info" IS 'kommentaar hinde muutmise kohta';

-- ----------------------------
-- Table structure for protocol_vdata
-- ----------------------------
DROP TABLE IF EXISTS "public"."protocol_vdata";
CREATE TABLE "public"."protocol_vdata" (
"curriculum_version_omodule_id" int8 NOT NULL,
"protocol_id" int8 NOT NULL,
"curriculum_version_id" int8 NOT NULL,
"study_year_id" int8 NOT NULL,
"teacher_id" int8 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."protocol_vdata" IS 'kutseõppe protokolli info';
COMMENT ON COLUMN "public"."protocol_vdata"."curriculum_version_omodule_id" IS 'viide rakenduskava moodulile';
COMMENT ON COLUMN "public"."protocol_vdata"."curriculum_version_id" IS 'viide rakenduskavale';
COMMENT ON COLUMN "public"."protocol_vdata"."study_year_id" IS 'viide õpepaastale';
COMMENT ON COLUMN "public"."protocol_vdata"."teacher_id" IS 'viide õpetajale';

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
-- Table structure for sais_admission
-- ----------------------------
DROP TABLE IF EXISTS "public"."sais_admission";
CREATE TABLE "public"."sais_admission" (
"id" int8 DEFAULT nextval('sais_admission_id_seq'::regclass) NOT NULL,
"code" varchar(100) COLLATE "default" NOT NULL,
"name" varchar(1000) COLLATE "default" NOT NULL,
"sais_id" varchar(50) COLLATE "default" NOT NULL,
"curriculum_version_id" int8 NOT NULL,
"fin_code" varchar(100) COLLATE "default" NOT NULL,
"language_code" varchar(100) COLLATE "default" NOT NULL,
"places" int4,
"period_start" date,
"period_end" date NOT NULL,
"study_form_code" varchar(100) COLLATE "default" NOT NULL,
"study_level_code" varchar(100) COLLATE "default",
"study_load_code" varchar(100) COLLATE "default",
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
"is_archived" bool
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."sais_admission" IS 'SAISi konkursid';
COMMENT ON COLUMN "public"."sais_admission"."code" IS 'konkursi kood';
COMMENT ON COLUMN "public"."sais_admission"."name" IS 'konkursi nimi';
COMMENT ON COLUMN "public"."sais_admission"."sais_id" IS 'konkursi SAISi id';
COMMENT ON COLUMN "public"."sais_admission"."curriculum_version_id" IS 'viide rakenduskavale/õppekava versioonile';
COMMENT ON COLUMN "public"."sais_admission"."fin_code" IS 'viide klassifikaatorile FINALLIKAS';
COMMENT ON COLUMN "public"."sais_admission"."language_code" IS 'viide klassifikaatorile õppekeel';
COMMENT ON COLUMN "public"."sais_admission"."places" IS 'kohtade arv';
COMMENT ON COLUMN "public"."sais_admission"."period_start" IS 'konkursi algus';
COMMENT ON COLUMN "public"."sais_admission"."period_end" IS 'konkursi lõpp';
COMMENT ON COLUMN "public"."sais_admission"."study_form_code" IS 'viide klaasifikaatorile OPPEVORM';
COMMENT ON COLUMN "public"."sais_admission"."study_level_code" IS 'viide klassifikaatorile OPPEASTE';
COMMENT ON COLUMN "public"."sais_admission"."study_load_code" IS 'viide klassifikaatorile OPPEKOORMUS';
COMMENT ON COLUMN "public"."sais_admission"."is_archived" IS 'kas arhiveeritud, vaikimisi false
false - ei 
true - arhiveeritud';

-- ----------------------------
-- Table structure for sais_application
-- ----------------------------
DROP TABLE IF EXISTS "public"."sais_application";
CREATE TABLE "public"."sais_application" (
"id" int8 DEFAULT nextval('sais_application_id_seq'::regclass) NOT NULL,
"sais_admission_id" int8 NOT NULL,
"submitted" date NOT NULL,
"sais_changed" date,
"firstname" varchar(100) COLLATE "default" NOT NULL,
"lastname" varchar(100) COLLATE "default" NOT NULL,
"birthdate" date NOT NULL,
"idcode" varchar(11) COLLATE "default",
"foreign_idcode" varchar(50) COLLATE "default",
"address" varchar(100) COLLATE "default",
"sex_code" varchar(100) COLLATE "default",
"phone" varchar(100) COLLATE "default",
"email" varchar(100) COLLATE "default",
"fin_code" varchar(100) COLLATE "default" NOT NULL,
"points" numeric(6,2),
"status_code" varchar(100) COLLATE "default" NOT NULL,
"application_nr" varchar(100) COLLATE "default" NOT NULL,
"citizenship_code" varchar(100) COLLATE "default" NOT NULL,
"study_load_code" varchar(100) COLLATE "default",
"residence_country_code" varchar(100) COLLATE "default" NOT NULL,
"study_form_code" varchar(100) COLLATE "default" NOT NULL,
"language_code" varchar(100) COLLATE "default" NOT NULL,
"sais_id" varchar(50) COLLATE "default",
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
"is_archived" bool,
"address_ads" varchar(50) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."sais_application"."sais_admission_id" IS 'viide konkursile';
COMMENT ON COLUMN "public"."sais_application"."submitted" IS 'esitamise kuupäev';
COMMENT ON COLUMN "public"."sais_application"."sais_changed" IS 'muutmise kp SAISis';
COMMENT ON COLUMN "public"."sais_application"."firstname" IS 'eesnimi';
COMMENT ON COLUMN "public"."sais_application"."lastname" IS 'perekonnanimi';
COMMENT ON COLUMN "public"."sais_application"."birthdate" IS 'sünnikuupäev';
COMMENT ON COLUMN "public"."sais_application"."idcode" IS 'Eesti isikukood';
COMMENT ON COLUMN "public"."sais_application"."foreign_idcode" IS 'välismaa isikukood';
COMMENT ON COLUMN "public"."sais_application"."address" IS 'aadress';
COMMENT ON COLUMN "public"."sais_application"."sex_code" IS 'viide klassifikaatorile SUGU';
COMMENT ON COLUMN "public"."sais_application"."phone" IS 'telefon';
COMMENT ON COLUMN "public"."sais_application"."email" IS 'e-mail';
COMMENT ON COLUMN "public"."sais_application"."fin_code" IS 'viide klassifikaatorile FINALLIKAS';
COMMENT ON COLUMN "public"."sais_application"."points" IS 'SAISi avalduse punktide arv';
COMMENT ON COLUMN "public"."sais_application"."status_code" IS 'viide klassifikaatorile SAIS_AVALDUSESTAATUS';
COMMENT ON COLUMN "public"."sais_application"."application_nr" IS 'SAISi avalduse nr';
COMMENT ON COLUMN "public"."sais_application"."citizenship_code" IS 'kodakondsus, viide klassifikaatorile RIIK';
COMMENT ON COLUMN "public"."sais_application"."study_load_code" IS 'õppekoormus, viide klassifikaatorile OPPEKORMUS';
COMMENT ON COLUMN "public"."sais_application"."residence_country_code" IS 'elukohamaa, viide klassifikaatorile RIIK';
COMMENT ON COLUMN "public"."sais_application"."study_form_code" IS 'õppevorm, viide klassifikaatorile OPPEVORM';
COMMENT ON COLUMN "public"."sais_application"."language_code" IS 'õppekeel, viide klassifikaatorile OPPEKEEL';
COMMENT ON COLUMN "public"."sais_application"."sais_id" IS 'SAISi avalduse id';
COMMENT ON COLUMN "public"."sais_application"."is_archived" IS 'kas arhiveeritud, vaikimisi false
false - ei 
true - arhiveeritud';
COMMENT ON COLUMN "public"."sais_application"."address_ads" IS 'aadress ADS kujul';

-- ----------------------------
-- Table structure for sais_application_grade
-- ----------------------------
DROP TABLE IF EXISTS "public"."sais_application_grade";
CREATE TABLE "public"."sais_application_grade" (
"id" int8 DEFAULT nextval('sais_application_grade_id_seq'::regclass) NOT NULL,
"subject_name" varchar(255) COLLATE "default" NOT NULL,
"sais_application_id" int8 NOT NULL,
"subject_type" varchar(100) COLLATE "default",
"grade" varchar(50) COLLATE "default" NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."sais_application_grade" IS 'SAISi avaldustega seotud hinded';
COMMENT ON COLUMN "public"."sais_application_grade"."subject_name" IS 'õppeaine nimetus e. k.';
COMMENT ON COLUMN "public"."sais_application_grade"."subject_type" IS 'õppeaine liik (ei ole klassifikaator)';
COMMENT ON COLUMN "public"."sais_application_grade"."grade" IS 'hinne, võib olla ka tekst';

-- ----------------------------
-- Table structure for sais_application_graduated_school
-- ----------------------------
DROP TABLE IF EXISTS "public"."sais_application_graduated_school";
CREATE TABLE "public"."sais_application_graduated_school" (
"id" int8 DEFAULT nextval('sais_application_graduated_school_id_seq'::regclass) NOT NULL,
"name" varchar(255) COLLATE "default",
"sais_application_id" int8 NOT NULL,
"start_date" date,
"end_date" date,
"reg_code" varchar(20) COLLATE "default",
"is_abroad" bool NOT NULL,
"study_level_code" varchar(100) COLLATE "default" NOT NULL,
"inserted" timestamp(6) NOT NULL,
"study_form_code" varchar(100) COLLATE "default",
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."sais_application_graduated_school" IS 'SAISi avaldusega seotud lõpetatud koolid';
COMMENT ON COLUMN "public"."sais_application_graduated_school"."name" IS 'kooli nimi';
COMMENT ON COLUMN "public"."sais_application_graduated_school"."sais_application_id" IS 'viide SAIS avaldusele';
COMMENT ON COLUMN "public"."sais_application_graduated_school"."start_date" IS 'õpingute algus';
COMMENT ON COLUMN "public"."sais_application_graduated_school"."end_date" IS 'õpingute lõpp';
COMMENT ON COLUMN "public"."sais_application_graduated_school"."reg_code" IS 'kooli reg. kood';
COMMENT ON COLUMN "public"."sais_application_graduated_school"."is_abroad" IS 'kas lõpetas välismaal, vaikimisi false false - ei ole välismaa true - välismaa kool';
COMMENT ON COLUMN "public"."sais_application_graduated_school"."study_level_code" IS 'viide klaasifikaatorile OPPEASTE';
COMMENT ON COLUMN "public"."sais_application_graduated_school"."study_form_code" IS 'viide klassifikaatorile OPPEVORM';

-- ----------------------------
-- Table structure for sais_application_other_data
-- ----------------------------
DROP TABLE IF EXISTS "public"."sais_application_other_data";
CREATE TABLE "public"."sais_application_other_data" (
"id" int8 DEFAULT nextval('sais_application_other_data_id_seq'::regclass) NOT NULL,
"other_data_name" varchar(255) COLLATE "default" NOT NULL,
"sais_application_id" int8 NOT NULL,
"other_data_value" varchar(4000) COLLATE "default" NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."sais_application_other_data" IS 'saisi avalduse täiendav informatsioon';
COMMENT ON COLUMN "public"."sais_application_other_data"."other_data_name" IS 'andmevälja nimetus sais avaldusel';
COMMENT ON COLUMN "public"."sais_application_other_data"."sais_application_id" IS 'viide saisi avaldusele';
COMMENT ON COLUMN "public"."sais_application_other_data"."other_data_value" IS 'andmevälja väärtus sais avaldusel';

-- ----------------------------
-- Table structure for sais_classifier
-- ----------------------------
DROP TABLE IF EXISTS "public"."sais_classifier";
CREATE TABLE "public"."sais_classifier" (
"code" varchar(50) COLLATE "default" NOT NULL,
"parent_code" varchar(50) COLLATE "default",
"value" varchar(100) COLLATE "default" NOT NULL,
"name_et" varchar(1000) COLLATE "default" NOT NULL,
"name_en" varchar(1000) COLLATE "default",
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."sais_classifier" IS 'SAIS klaaisifkaatori tabel';
COMMENT ON COLUMN "public"."sais_classifier"."code" IS 'sais klassifikaatori id';
COMMENT ON COLUMN "public"."sais_classifier"."parent_code" IS 'viide sais ülem klassifikaatorile';
COMMENT ON COLUMN "public"."sais_classifier"."value" IS 'väärtus';
COMMENT ON COLUMN "public"."sais_classifier"."name_et" IS 'nimetus e.k.';
COMMENT ON COLUMN "public"."sais_classifier"."name_en" IS 'nimetus i.k.';

-- ----------------------------
-- Table structure for scholarship_application
-- ----------------------------
DROP TABLE IF EXISTS "public"."scholarship_application";
CREATE TABLE "public"."scholarship_application" (
"id" int8 DEFAULT nextval('scholarship_application_id_seq'::regclass) NOT NULL,
"scholarship_term_id" int8 NOT NULL,
"average_mark" numeric(4,2),
"curriculum_completion" numeric(4,1),
"address_ads" varchar(50) COLLATE "default",
"address" varchar(255) COLLATE "default",
"bank_account" varchar(50) COLLATE "default" NOT NULL,
"last_period_mark" numeric(4,2),
"absences" int2,
"add_info" varchar(4000) COLLATE "default",
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
"bank_account_owner_idcode" varchar(20) COLLATE "default",
"bank_account_owner_name" varchar(100) COLLATE "default",
"status_code" varchar(100) COLLATE "default" NOT NULL,
"decision_date" date,
"student_id" int8 NOT NULL,
"phone" varchar(100) COLLATE "default",
"email" varchar(100) COLLATE "default",
"student_group_id" int8 NOT NULL,
"credits" numeric(4,1) NOT NULL,
"curriculum_version_id" int8 NOT NULL,
"is_teacher_confirmed" bool,
"scholarship_from" date,
"scholarship_thru" date,
"family_members" int2,
"family_members_adult" int2,
"compensation_reason_code" varchar(100) COLLATE "default",
"route_km" numeric(5,1),
"compensation_add_info" varchar(4000) COLLATE "default",
"compensation_frequency_code" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."scholarship_application" IS 'stipendium/õppetoetuse taotlus';
COMMENT ON COLUMN "public"."scholarship_application"."average_mark" IS 'KKH';
COMMENT ON COLUMN "public"."scholarship_application"."curriculum_completion" IS 'õppekava täitmise %';
COMMENT ON COLUMN "public"."scholarship_application"."address_ads" IS 'aadress ADS kujul';
COMMENT ON COLUMN "public"."scholarship_application"."address" IS 'aadress';
COMMENT ON COLUMN "public"."scholarship_application"."bank_account" IS 'konto number';
COMMENT ON COLUMN "public"."scholarship_application"."last_period_mark" IS 'viimaee perioodi keskmine hinne';
COMMENT ON COLUMN "public"."scholarship_application"."absences" IS 'puudumiste arv';
COMMENT ON COLUMN "public"."scholarship_application"."add_info" IS 'lisainfo, erialased saavutused jms';
COMMENT ON COLUMN "public"."scholarship_application"."status_code" IS 'viide klassifikaatorile STIPTOETUS_STAATUS';
COMMENT ON COLUMN "public"."scholarship_application"."decision_date" IS 'otsuse kuupäev';
COMMENT ON COLUMN "public"."scholarship_application"."student_id" IS 'viide õppurile';
COMMENT ON COLUMN "public"."scholarship_application"."phone" IS 'kontakt tel';
COMMENT ON COLUMN "public"."scholarship_application"."email" IS 'kontakt e-mail (student.email koopia)';
COMMENT ON COLUMN "public"."scholarship_application"."student_group_id" IS 'viide õpperühmale taotluse loomise hetkeseisuga';
COMMENT ON COLUMN "public"."scholarship_application"."credits" IS 'EAP/EKAP arv';
COMMENT ON COLUMN "public"."scholarship_application"."curriculum_version_id" IS 'viide õppekava versioonile taotluse loomise hetkeseisuga';
COMMENT ON COLUMN "public"."scholarship_application"."is_teacher_confirmed" IS 'kas õpetaja kinnitas';
COMMENT ON COLUMN "public"."scholarship_application"."scholarship_from" IS 'eritoet saamise periood alates';
COMMENT ON COLUMN "public"."scholarship_application"."scholarship_thru" IS 'eritoet saamise periood kuni';
COMMENT ON COLUMN "public"."scholarship_application"."family_members" IS 'pereliikmete arv';
COMMENT ON COLUMN "public"."scholarship_application"."family_members_adult" IS 'täisealiste  pereliikmete arv';
COMMENT ON COLUMN "public"."scholarship_application"."compensation_reason_code" IS 'hüvitamise põhjus, viide klassifikaatorile STIPTOETUS_HYVITAMINE_POHJUS';
COMMENT ON COLUMN "public"."scholarship_application"."route_km" IS 'marsruut km';
COMMENT ON COLUMN "public"."scholarship_application"."compensation_add_info" IS 'kompenseerimise täpsustus';
COMMENT ON COLUMN "public"."scholarship_application"."compensation_frequency_code" IS 'kompenseerimise sageuds, viide klassifikaatorile STIPTOETUS_HYVITAMINE';

-- ----------------------------
-- Table structure for scholarship_application_family
-- ----------------------------
DROP TABLE IF EXISTS "public"."scholarship_application_family";
CREATE TABLE "public"."scholarship_application_family" (
"id" int8 DEFAULT nextval('scholarship_application_family_id_seq'::regclass) NOT NULL,
"name" varchar(100) COLLATE "default" NOT NULL,
"scholarship_application_id" int8 NOT NULL,
"net_salary" numeric(10,2),
"pension" numeric(10,2),
"state_benefit" numeric(10,2),
"other_income" numeric(10,2),
"unemployed_benefit" numeric(10,2),
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."scholarship_application_family" IS 'stip/toetuse pereliikmete info';
COMMENT ON COLUMN "public"."scholarship_application_family"."name" IS 'pereliige';
COMMENT ON COLUMN "public"."scholarship_application_family"."net_salary" IS 'netopalk';
COMMENT ON COLUMN "public"."scholarship_application_family"."pension" IS 'pension';
COMMENT ON COLUMN "public"."scholarship_application_family"."state_benefit" IS 'riiklik toetus';
COMMENT ON COLUMN "public"."scholarship_application_family"."other_income" IS 'muu sissetulek';
COMMENT ON COLUMN "public"."scholarship_application_family"."unemployed_benefit" IS 'töötu töötuskindlustushüvitis';

-- ----------------------------
-- Table structure for scholarship_application_file
-- ----------------------------
DROP TABLE IF EXISTS "public"."scholarship_application_file";
CREATE TABLE "public"."scholarship_application_file" (
"id" int8 DEFAULT nextval('scholarship_application_file_id_seq'::regclass) NOT NULL,
"scholarship_application_id" int8 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"ois_file_id" int8 NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."scholarship_application_file" IS 'stip/õppetoetusega seotud failid';

-- ----------------------------
-- Table structure for scholarship_term
-- ----------------------------
DROP TABLE IF EXISTS "public"."scholarship_term";
CREATE TABLE "public"."scholarship_term" (
"id" int8 DEFAULT nextval('scholarship_term_id_seq'::regclass) NOT NULL,
"school_id" int8 NOT NULL,
"type_code" varchar(100) COLLATE "default" NOT NULL,
"application_start" date,
"study_period_id" int8,
"application_end" date,
"payment_start" date,
"payment_end" date,
"places" int2,
"amount_paid" numeric(6,2),
"average_mark" numeric(4,2),
"average_mark_priority_code" varchar(100) COLLATE "default",
"last_period_mark" numeric(4,2),
"curriculum_completion" numeric(4,1),
"last_period_mark_priority_code" varchar(100) COLLATE "default",
"curriculum_completion_priority_code" varchar(100) COLLATE "default",
"study_start_period_start" date,
"study_start_period_end" date,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
"version" int4 NOT NULL,
"name_et" varchar(255) COLLATE "default",
"max_absences" int2,
"max_absences_priority_code" varchar(100) COLLATE "default",
"is_academic_leave" bool NOT NULL,
"is_study_backlog" bool NOT NULL,
"is_teacher_confirm" bool NOT NULL,
"is_family_incomes" bool NOT NULL,
"is_open" bool NOT NULL
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."scholarship_term" IS 'stip/toetuste tingimused';
COMMENT ON COLUMN "public"."scholarship_term"."school_id" IS 'viide koolil';
COMMENT ON COLUMN "public"."scholarship_term"."type_code" IS 'stipend liik, viide klassifikaatorile STIPTOETUS';
COMMENT ON COLUMN "public"."scholarship_term"."application_start" IS 'taotlemise algus';
COMMENT ON COLUMN "public"."scholarship_term"."application_end" IS 'taotlemise lõpp';
COMMENT ON COLUMN "public"."scholarship_term"."payment_start" IS 'maksmise algus';
COMMENT ON COLUMN "public"."scholarship_term"."payment_end" IS 'maksmise lõpp';
COMMENT ON COLUMN "public"."scholarship_term"."places" IS 'kohtade arv';
COMMENT ON COLUMN "public"."scholarship_term"."amount_paid" IS 'makstav summa';
COMMENT ON COLUMN "public"."scholarship_term"."curriculum_completion" IS '񰰥kava t媴mise %';
COMMENT ON COLUMN "public"."scholarship_term"."study_start_period_start" IS 'immatrikuleerimise algus';
COMMENT ON COLUMN "public"."scholarship_term"."study_start_period_end" IS 'immatrikuleerimise lõpp';
COMMENT ON COLUMN "public"."scholarship_term"."name_et" IS 'õppetoetuse nimetus';
COMMENT ON COLUMN "public"."scholarship_term"."max_absences" IS 'maks puudumiste arv';
COMMENT ON COLUMN "public"."scholarship_term"."is_academic_leave" IS 'kas akadeemilisel puhkusel taotlemine lubatud';
COMMENT ON COLUMN "public"."scholarship_term"."is_study_backlog" IS 'kas õppevõlgnevusega taotlemine lubatud';
COMMENT ON COLUMN "public"."scholarship_term"."is_teacher_confirm" IS 'kas rühmajuhtaja peab kinnitama';
COMMENT ON COLUMN "public"."scholarship_term"."is_family_incomes" IS 'kas kuvada perekonna sissetuleku sektsiooni';
COMMENT ON COLUMN "public"."scholarship_term"."is_open" IS 'kas avalikustatud';

-- ----------------------------
-- Table structure for scholarship_term_course
-- ----------------------------
DROP TABLE IF EXISTS "public"."scholarship_term_course";
CREATE TABLE "public"."scholarship_term_course" (
"id" int8 DEFAULT nextval('scholarschip_terme_course_id_seq'::regclass) NOT NULL,
"scholarship_term_id" int8 NOT NULL,
"course_code" varchar(100) COLLATE "default" NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."scholarship_term_course" IS 'stipend/õppetoetuste tingimuste kursused';
COMMENT ON COLUMN "public"."scholarship_term_course"."scholarship_term_id" IS 'viide tingimustele';
COMMENT ON COLUMN "public"."scholarship_term_course"."course_code" IS 'viide klassifikaatorile KURSUS';

-- ----------------------------
-- Table structure for scholarship_term_curriculum
-- ----------------------------
DROP TABLE IF EXISTS "public"."scholarship_term_curriculum";
CREATE TABLE "public"."scholarship_term_curriculum" (
"id" int8 DEFAULT nextval('scholarship_term_curriculum_id_seq'::regclass) NOT NULL,
"scholarship_term_id" int8 NOT NULL,
"curriculum_id" int8 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."scholarship_term_curriculum" IS 'stip tingimustega seotud õppekavad';
COMMENT ON COLUMN "public"."scholarship_term_curriculum"."scholarship_term_id" IS 'viide tingimustele';
COMMENT ON COLUMN "public"."scholarship_term_curriculum"."curriculum_id" IS 'viide õppekavale';

-- ----------------------------
-- Table structure for scholarship_term_study_form
-- ----------------------------
DROP TABLE IF EXISTS "public"."scholarship_term_study_form";
CREATE TABLE "public"."scholarship_term_study_form" (
"id" int8 DEFAULT nextval('scholarship_term_study_form_id_seq'::regclass) NOT NULL,
"scholarship_term_id" int8 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"study_form_code" varchar(100) COLLATE "default" NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."scholarship_term_study_form" IS 'õppet/stipend tingimused ja õppevormid';
COMMENT ON COLUMN "public"."scholarship_term_study_form"."study_form_code" IS 'viide õppevormile';

-- ----------------------------
-- Table structure for scholarship_term_study_load
-- ----------------------------
DROP TABLE IF EXISTS "public"."scholarship_term_study_load";
CREATE TABLE "public"."scholarship_term_study_load" (
"id" int8 DEFAULT nextval('scholarship_term_study_load_id_seq'::regclass) NOT NULL,
"scholarship_term_id" int8 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"study_load_code" varchar(100) COLLATE "default" NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."scholarship_term_study_load"."scholarship_term_id" IS 'viide tingimustele';
COMMENT ON COLUMN "public"."scholarship_term_study_load"."study_load_code" IS 'viide klassifikaatorile õppekoormus';

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
"phone" varchar(100) COLLATE "default",
"email_domain" varchar(255) COLLATE "default",
"generate_user_email" bool,
"rtip_school_code" varchar(10) COLLATE "default",
"ad_domain" varchar(255) COLLATE "default",
"ad_base" varchar(255) COLLATE "default",
"ad_port" int2,
"ad_url" varchar(255) COLLATE "default",
"ad_idcode_field" varchar(50) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."school" IS 'HÕISiga liitunud õppeasutused';
COMMENT ON COLUMN "public"."school"."logo_f" IS 'logo faili nimi';
COMMENT ON COLUMN "public"."school"."code" IS 'kooli lühend';
COMMENT ON COLUMN "public"."school"."ois_file_id" IS 'viide logo file''le';
COMMENT ON COLUMN "public"."school"."phone" IS 'telefon';
COMMENT ON COLUMN "public"."school"."email_domain" IS 'õppeasutuse e-posti domeen';
COMMENT ON COLUMN "public"."school"."generate_user_email" IS 'E-posti automaatne genereerimine uuele kasutajale. Kui on valitud, siis uue „õppuri“ või „õpetaja“ rollis kasutaja lisamisel genereeritakse vastavale kasutajale e-posti aadress.';
COMMENT ON COLUMN "public"."school"."rtip_school_code" IS 'kooli kood RTIP-s';
COMMENT ON COLUMN "public"."school"."ad_domain" IS 'AD domeen kujul @devtest.test';
COMMENT ON COLUMN "public"."school"."ad_base" IS 'AD haru, kust kasutajat otsitakse, kujul CN=Users,DC=devtest,DC=test';
COMMENT ON COLUMN "public"."school"."ad_port" IS 'AD port kujul 636';
COMMENT ON COLUMN "public"."school"."ad_url" IS 'AD serveri IP või nimi, nt 141.192.105.54';
COMMENT ON COLUMN "public"."school"."ad_idcode_field" IS 'AD välja nimi, kust hakatase isikukoodi otsima, nt  employeeNumber';

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
COMMENT ON COLUMN "public"."school_department"."school_id" IS 'viide õpepasutusele';
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
"objectives_et" text COLLATE "default",
"objectives_en" text COLLATE "default",
"outcomes_et" text COLLATE "default",
"outcomes_en" text COLLATE "default",
"admission_requirements_et" text COLLATE "default",
"admission_requirements_en" text COLLATE "default",
"graduation_requirements_et" text COLLATE "default",
"graduation_requirements_en" text COLLATE "default",
"credits" numeric(4,1) NOT NULL,
"practice_description" text COLLATE "default",
"state_curr_class_code" varchar(100) COLLATE "default" NOT NULL,
"final_exam_description" text COLLATE "default",
"optional_study_credits" numeric(4,1),
"valid_from" date,
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
COMMENT ON COLUMN "public"."state_curriculum"."final_exam_description" IS 'lõpueksami kirjeldus';
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
"changed_by" varchar(100) COLLATE "default",
"is_additional" bool
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
COMMENT ON COLUMN "public"."state_curriculum_module"."is_additional" IS 'kas on lisamoodul';

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
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS "public"."student";
CREATE TABLE "public"."student" (
"id" int8 DEFAULT nextval('student_id_seq'::regclass) NOT NULL,
"person_id" int8 NOT NULL,
"school_id" int4 NOT NULL,
"curriculum_version_id" int8 NOT NULL,
"study_form_code" varchar(100) COLLATE "default",
"student_group_id" int8,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default",
"language_code" varchar(100) COLLATE "default",
"email" varchar(100) COLLATE "default",
"is_special_need" bool NOT NULL,
"is_representative_mandatory" bool NOT NULL,
"special_need_code" varchar(100) COLLATE "default",
"student_card" varchar(50) COLLATE "default",
"previous_study_level_code" varchar(100) COLLATE "default",
"status_code" varchar(100) COLLATE "default",
"ois_file_id" int8,
"curriculum_speciality_id" int8,
"study_start" date,
"study_end" date,
"nominal_study_end" date,
"study_load_code" varchar(100) COLLATE "default",
"fin_code" varchar(100) COLLATE "default",
"fin_specific_code" varchar(100) COLLATE "default",
"study_company" varchar(1000) COLLATE "default",
"boarding_school" varchar(1000) COLLATE "default",
"student_history_id" int8
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."student" IS 'õppurite tabel';
COMMENT ON COLUMN "public"."student"."person_id" IS 'viide isikule';
COMMENT ON COLUMN "public"."student"."school_id" IS 'viide õppeasutusele';
COMMENT ON COLUMN "public"."student"."study_form_code" IS 'õppevormi kood, viide õppevormi klassifikaatorile, eksternõppes võib puududa';
COMMENT ON COLUMN "public"."student"."student_group_id" IS 'viide õpperühmale, eksternõppes võib puududa';
COMMENT ON COLUMN "public"."student"."email" IS 'kooli e-posti aadress';
COMMENT ON COLUMN "public"."student"."is_special_need" IS 'õppuri erivajadus, vaikimisi false true - õppuril on erivajadus false - erivajadus puudub';
COMMENT ON COLUMN "public"."student"."is_representative_mandatory" IS 'kas erivajadusega õppuril peab olema kohustuslikus korras esindaja, vaikimisi false  true - peab olema esindaja false - ei pea olema esindajat';
COMMENT ON COLUMN "public"."student"."special_need_code" IS 'erivajadus, viide klassifikaatorile ERIVAJADUS';
COMMENT ON COLUMN "public"."student"."student_card" IS 'õpilaspileti number';
COMMENT ON COLUMN "public"."student"."previous_study_level_code" IS 'eelnev haridus, viide klassifikaatorile OPPEASTE';
COMMENT ON COLUMN "public"."student"."status_code" IS 'õppuri staatus, viide klassifikaatorile OPPURSTAATUS';
COMMENT ON COLUMN "public"."student"."ois_file_id" IS 'viide õppuri fotole';
COMMENT ON COLUMN "public"."student"."curriculum_speciality_id" IS 'viide õppuri peaerialale, ainult kõrgharidusõppes';
COMMENT ON COLUMN "public"."student"."study_start" IS 'õpingute algus kp';
COMMENT ON COLUMN "public"."student"."study_end" IS 'õpingute lõpp kp või eksmat kp';
COMMENT ON COLUMN "public"."student"."nominal_study_end" IS 'nominaalaja eeldatav lõpp';
COMMENT ON COLUMN "public"."student"."study_load_code" IS 'õppekoormuse kood, viide klassifikaatorile OPPEKOORMUS';
COMMENT ON COLUMN "public"."student"."fin_code" IS 'finantsallika täpsustus, viide klassifikaatorile FINALLIKAS';
COMMENT ON COLUMN "public"."student"."fin_specific_code" IS 'fiinantsallika täpsustus, viide klassifikaatorile FINTAPSUSTUS';
COMMENT ON COLUMN "public"."student"."study_company" IS 'õpipoisi ettevõte';
COMMENT ON COLUMN "public"."student"."boarding_school" IS 'õpilaskodu';
COMMENT ON COLUMN "public"."student"."student_history_id" IS 'viide õppuri ajaloo tabelile';

-- ----------------------------
-- Table structure for student_absence
-- ----------------------------
DROP TABLE IF EXISTS "public"."student_absence";
CREATE TABLE "public"."student_absence" (
"id" int8 DEFAULT nextval('student_absence_id_seq'::regclass) NOT NULL,
"student_id" int8 NOT NULL,
"valid_from" date NOT NULL,
"valid_thru" date,
"cause" varchar(4000) COLLATE "default" NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default",
"is_accepted" bool NOT NULL,
"accepted_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."student_absence" IS 'õppuri puudumistõend';
COMMENT ON COLUMN "public"."student_absence"."student_id" IS 'viide õppurile';
COMMENT ON COLUMN "public"."student_absence"."valid_from" IS 'algus kp';
COMMENT ON COLUMN "public"."student_absence"."valid_thru" IS 'lõpp kp';
COMMENT ON COLUMN "public"."student_absence"."cause" IS 'puudumise põhjus';
COMMENT ON COLUMN "public"."student_absence"."is_accepted" IS 'kas puudumistõend aktsepteeritud false - ei true - jah vaikimisi false';
COMMENT ON COLUMN "public"."student_absence"."accepted_by" IS 'tõendi aktsepteerija nimi';

-- ----------------------------
-- Table structure for student_curriculum_completion
-- ----------------------------
DROP TABLE IF EXISTS "public"."student_curriculum_completion";
CREATE TABLE "public"."student_curriculum_completion" (
"id" int8 DEFAULT nextval('student_curriculum_completion_id_seq'::regclass) NOT NULL,
"student_id" int8 NOT NULL,
"study_backlog" numeric(4,1) NOT NULL,
"study_backlog_without_graduate" numeric(4,1) NOT NULL,
"average_mark" numeric(4,2),
"average_mark_last_study_period" numeric(4,2),
"average_mark_before_current_study_period" numeric(4,2),
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"credits" numeric(4,1),
"credits_last_study_period" numeric(4,1),
"credits_before_current_study_period" numeric(4,1)
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."student_curriculum_completion" IS 'õppuri õppekava täitmine';
COMMENT ON COLUMN "public"."student_curriculum_completion"."student_id" IS 'viide õppurile';
COMMENT ON COLUMN "public"."student_curriculum_completion"."study_backlog" IS 'õppevõlg EAP/EKAP';
COMMENT ON COLUMN "public"."student_curriculum_completion"."study_backlog_without_graduate" IS 'õppevõlg ilma lõputööta';
COMMENT ON COLUMN "public"."student_curriculum_completion"."average_mark" IS 'jooksev KKH';
COMMENT ON COLUMN "public"."student_curriculum_completion"."average_mark_last_study_period" IS 'viimase õppeperioodi KKH';
COMMENT ON COLUMN "public"."student_curriculum_completion"."average_mark_before_current_study_period" IS 'KKH jooskva õppeperioodi alguseks';
COMMENT ON COLUMN "public"."student_curriculum_completion"."credits" IS 'jooksev EAP/EKAP maht';
COMMENT ON COLUMN "public"."student_curriculum_completion"."credits_last_study_period" IS 'viimase õppeperioodi EAP/EKAP maht';
COMMENT ON COLUMN "public"."student_curriculum_completion"."credits_before_current_study_period" IS 'EAP/EKAP maht jooksva õpepperioodi alguseks';

-- ----------------------------
-- Table structure for student_group
-- ----------------------------
DROP TABLE IF EXISTS "public"."student_group";
CREATE TABLE "public"."student_group" (
"id" int8 DEFAULT nextval('student_group_id_seq'::regclass) NOT NULL,
"code" varchar(50) COLLATE "default" NOT NULL,
"school_id" int4 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default",
"course" int2,
"curriculum_version_id" int8,
"curriculum_id" int8 NOT NULL,
"study_form_code" varchar(100) COLLATE "default" NOT NULL,
"language_code" varchar(100) COLLATE "default",
"teacher_id" int8,
"speciality_code" varchar(100) COLLATE "default",
"places" int4,
"valid_from" date,
"valid_thru" date
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."student_group" IS 'õpperühm';
COMMENT ON COLUMN "public"."student_group"."code" IS 'õpperühma kood';
COMMENT ON COLUMN "public"."student_group"."school_id" IS 'viide õppeasutusele';
COMMENT ON COLUMN "public"."student_group"."course" IS 'kursuse nr';
COMMENT ON COLUMN "public"."student_group"."curriculum_version_id" IS 'viide õppekava versioonile või rakenduskavale';
COMMENT ON COLUMN "public"."student_group"."curriculum_id" IS 'viide õppekavale';
COMMENT ON COLUMN "public"."student_group"."study_form_code" IS 'viide klassifikaatorile OPPEVORM';
COMMENT ON COLUMN "public"."student_group"."language_code" IS 'viide klassifikaatorile OPPEKEEL';
COMMENT ON COLUMN "public"."student_group"."teacher_id" IS 'viide õpetajae, rühmajuhataja';
COMMENT ON COLUMN "public"."student_group"."speciality_code" IS 'spetsialiseerumine kutseõppes, viide klassifikaatorile SPETSKUTSE';
COMMENT ON COLUMN "public"."student_group"."places" IS 'kohtade arv, kasutatakse immat käskkirja tegemisel';
COMMENT ON COLUMN "public"."student_group"."valid_from" IS 'kehtib alates';
COMMENT ON COLUMN "public"."student_group"."valid_thru" IS 'kehtib kuni';

-- ----------------------------
-- Table structure for student_higher_result
-- ----------------------------
DROP TABLE IF EXISTS "public"."student_higher_result";
CREATE TABLE "public"."student_higher_result" (
"id" int8 DEFAULT nextval('student_higher_result_id_seq'::regclass) NOT NULL,
"student_id" int8 NOT NULL,
"subject_id" int8,
"curriculum_version_hmodule_id" int8 NOT NULL,
"subject_name_et" varchar(255) COLLATE "default" NOT NULL,
"subject_name_en" varchar(255) COLLATE "default" NOT NULL,
"subject_code" varchar(20) COLLATE "default",
"apel_school_id" int8,
"grade_code" varchar(100) COLLATE "default" NOT NULL,
"grade" varchar(3) COLLATE "default" NOT NULL,
"grade_mark" int2,
"protocol_student_id" int8,
"apel_application_record_id" int8,
"credits" numeric(4,1) NOT NULL,
"is_optional" bool NOT NULL,
"teachers" varchar(255) COLLATE "default",
"grade_date" date,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6)
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."student_higher_result" IS 'Õppuri õppetulemused';
COMMENT ON COLUMN "public"."student_higher_result"."student_id" IS 'viide õppurile';
COMMENT ON COLUMN "public"."student_higher_result"."subject_id" IS 'viide õppeainele';
COMMENT ON COLUMN "public"."student_higher_result"."curriculum_version_hmodule_id" IS 'viide õppekava versiooni moodulile';
COMMENT ON COLUMN "public"."student_higher_result"."subject_name_et" IS 'õppeaine nimi';
COMMENT ON COLUMN "public"."student_higher_result"."subject_name_en" IS 'õppeaine nimi i.k.';
COMMENT ON COLUMN "public"."student_higher_result"."subject_code" IS 'õppeaine kood';
COMMENT ON COLUMN "public"."student_higher_result"."apel_school_id" IS 'viide koolile, kasutatakse ainult VÕTA puhul';
COMMENT ON COLUMN "public"."student_higher_result"."grade_code" IS 'viide klassifikaatorile KORGHINDAMINE';
COMMENT ON COLUMN "public"."student_higher_result"."grade" IS 'tulemus kujul A, 1, 2 jne';
COMMENT ON COLUMN "public"."student_higher_result"."grade_mark" IS 'numbriline hinne, vajalik KKH arvutamiseks';
COMMENT ON COLUMN "public"."student_higher_result"."protocol_student_id" IS 'viide õppuri protokollile';
COMMENT ON COLUMN "public"."student_higher_result"."apel_application_record_id" IS 'viide õppuri VÕTA taotlusele';
COMMENT ON COLUMN "public"."student_higher_result"."credits" IS 'aine EAP maht';
COMMENT ON COLUMN "public"."student_higher_result"."is_optional" IS 'kas on valik või kohustuslik aine';
COMMENT ON COLUMN "public"."student_higher_result"."teachers" IS 'õpetaja(d) vabatekstina';
COMMENT ON COLUMN "public"."student_higher_result"."grade_date" IS 'soorituse kp';

-- ----------------------------
-- Table structure for student_history
-- ----------------------------
DROP TABLE IF EXISTS "public"."student_history";
CREATE TABLE "public"."student_history" (
"id" int8 DEFAULT nextval('student_history_id_seq'::regclass) NOT NULL,
"student_id" int8 NOT NULL,
"curriculum_version_id" int8 NOT NULL,
"study_form_code" varchar(100) COLLATE "default",
"student_group_id" int8,
"email" varchar(100) COLLATE "default",
"language_code" varchar(100) COLLATE "default",
"is_special_need" bool NOT NULL,
"is_representative_mandatory" bool NOT NULL,
"special_need_code" varchar(100) COLLATE "default",
"student_card" varchar(50) COLLATE "default",
"previous_study_level_code" varchar(100) COLLATE "default",
"status_code" varchar(100) COLLATE "default",
"ois_file_id" int8,
"curriculum_speciality_id" int8,
"study_start" date,
"study_end" date,
"nominal_study_end" date,
"study_load_code" varchar(100) COLLATE "default",
"fin_code" varchar(100) COLLATE "default",
"fin_specific_code" varchar(100) COLLATE "default",
"study_company" varchar(1000) COLLATE "default",
"boarding_school" varchar(1000) COLLATE "default",
"valid_from" timestamp(6) NOT NULL,
"valid_thru" timestamp(6),
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default",
"prev_student_history_id" int8
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."student_history" IS 'õppurite ajaloo tabel';
COMMENT ON COLUMN "public"."student_history"."study_form_code" IS 'õppevormi kood, viide õppevormi klassifikaatorile, eksternõppes võib puududa';
COMMENT ON COLUMN "public"."student_history"."student_group_id" IS 'viide õpperühmale, eksternõppes võib puududa';
COMMENT ON COLUMN "public"."student_history"."email" IS 'kooli e-posti aadress';
COMMENT ON COLUMN "public"."student_history"."is_special_need" IS 'õppuri erivajadus, vaikimisi false true - õppuril on erivajadus false - erivajadus puudub';
COMMENT ON COLUMN "public"."student_history"."is_representative_mandatory" IS 'kas erivajadusega õppuril peab olema kohustuslikus korras esindaja, vaikimisi false  true - peab olema esindaja false - ei pea olema esindajat';
COMMENT ON COLUMN "public"."student_history"."special_need_code" IS 'erivajadus, viide klassifikaatorile ERIVAJADUS';
COMMENT ON COLUMN "public"."student_history"."student_card" IS 'õpilaspileti number';
COMMENT ON COLUMN "public"."student_history"."previous_study_level_code" IS 'eelnev haridus, viide klassifikaatorile OPPEASTE';
COMMENT ON COLUMN "public"."student_history"."status_code" IS 'õppuri staatus, viide klassifikaatorile OPPURSTAATUS';
COMMENT ON COLUMN "public"."student_history"."ois_file_id" IS 'viide õppuri fotole';
COMMENT ON COLUMN "public"."student_history"."curriculum_speciality_id" IS 'viide õppuri peaerialale, ainult kõrgharidusõppes';
COMMENT ON COLUMN "public"."student_history"."study_start" IS 'õpingute algus kp';
COMMENT ON COLUMN "public"."student_history"."study_end" IS 'õpingute lõpp kp või eksmat kp';
COMMENT ON COLUMN "public"."student_history"."nominal_study_end" IS 'nominaalaja eeldatav lõpp';
COMMENT ON COLUMN "public"."student_history"."study_load_code" IS 'õppekoormuse kood, viide klassifikaatorile OPPEKOORMUS';
COMMENT ON COLUMN "public"."student_history"."fin_code" IS 'finantsallika täpsustus, viide klassifikaatorile FINALLIKAS';
COMMENT ON COLUMN "public"."student_history"."fin_specific_code" IS 'fiinantsallika täpsustus, viide klassifikaatorile FINTAPSUSTUS';
COMMENT ON COLUMN "public"."student_history"."study_company" IS 'õpipoisi ettevõte';
COMMENT ON COLUMN "public"."student_history"."boarding_school" IS 'õpilaskodu';
COMMENT ON COLUMN "public"."student_history"."valid_from" IS 'kirje kehtivuse algus';
COMMENT ON COLUMN "public"."student_history"."valid_thru" IS 'kirje kehtivuse lõpp';
COMMENT ON COLUMN "public"."student_history"."prev_student_history_id" IS 'viide eelmisele kirjele';

-- ----------------------------
-- Table structure for student_occupation_certificate
-- ----------------------------
DROP TABLE IF EXISTS "public"."student_occupation_certificate";
CREATE TABLE "public"."student_occupation_certificate" (
"id" int8 DEFAULT nextval('student_occupation_certificate_id_seq'::regclass) NOT NULL,
"student_id" int8 NOT NULL,
"certificate_nr" varchar(20) COLLATE "default" NOT NULL,
"occupation_code" varchar(100) COLLATE "default" NOT NULL,
"part_occupation_code" varchar(100) COLLATE "default",
"speciality_code" varchar(100) COLLATE "default",
"valid_from" date NOT NULL,
"valid_thru" date NOT NULL,
"issuer" varchar(255) COLLATE "default" NOT NULL,
"issue_date" date NOT NULL,
"language" varchar(100) COLLATE "default",
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."student_occupation_certificate" IS 'Õppurite kutsetunnistused';
COMMENT ON COLUMN "public"."student_occupation_certificate"."student_id" IS 'viide õppurile';
COMMENT ON COLUMN "public"."student_occupation_certificate"."certificate_nr" IS 'tunnistuse nr';
COMMENT ON COLUMN "public"."student_occupation_certificate"."occupation_code" IS 'kutsestandard, viide klassifikaatorile KUTSE';
COMMENT ON COLUMN "public"."student_occupation_certificate"."part_occupation_code" IS 'osakutse, viide klassifikaatorile OSAKUTSE';
COMMENT ON COLUMN "public"."student_occupation_certificate"."speciality_code" IS 'spetsialiseerumine, viide klassifikaatorile SPETSKUTSE';
COMMENT ON COLUMN "public"."student_occupation_certificate"."valid_from" IS 'kehtib alates';
COMMENT ON COLUMN "public"."student_occupation_certificate"."valid_thru" IS 'kehtib kuni';
COMMENT ON COLUMN "public"."student_occupation_certificate"."issuer" IS 'väljastaja';
COMMENT ON COLUMN "public"."student_occupation_certificate"."issue_date" IS 'väljastamise kp';
COMMENT ON COLUMN "public"."student_occupation_certificate"."language" IS 'keel';

-- ----------------------------
-- Table structure for student_representative
-- ----------------------------
DROP TABLE IF EXISTS "public"."student_representative";
CREATE TABLE "public"."student_representative" (
"id" int8 DEFAULT nextval('student_representative_id_seq'::regclass) NOT NULL,
"student_id" int8 NOT NULL,
"person_id" int8 NOT NULL,
"relation_code" varchar(100) COLLATE "default" NOT NULL,
"is_student_visible" bool NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
"version" int4 NOT NULL
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."student_representative" IS 'õppuri esindajad';
COMMENT ON COLUMN "public"."student_representative"."student_id" IS 'viide õppurile';
COMMENT ON COLUMN "public"."student_representative"."person_id" IS 'viide isikule, kes on õppuri esindaja';
COMMENT ON COLUMN "public"."student_representative"."relation_code" IS 'seos õppuriga, viide klassifikaatorile OPPURESINDAJA';
COMMENT ON COLUMN "public"."student_representative"."is_student_visible" IS 'kas õppuri andmed on esindajale nähtavad  true - õppuri andmed on esindajale nähtavad false - ei ole nähtavad';

-- ----------------------------
-- Table structure for student_representative_application
-- ----------------------------
DROP TABLE IF EXISTS "public"."student_representative_application";
CREATE TABLE "public"."student_representative_application" (
"id" int8 DEFAULT nextval('student_representative_application_id_seq'::regclass) NOT NULL,
"student_id" int8 NOT NULL,
"status_code" varchar(100) COLLATE "default" NOT NULL,
"reject_reason" varchar(4000) COLLATE "default",
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default",
"person_id" int8 NOT NULL,
"relation_code" varchar(100) COLLATE "default" NOT NULL
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."student_representative_application" IS 'õppuri esindaja avaldus';
COMMENT ON COLUMN "public"."student_representative_application"."student_id" IS 'viide õppurile';
COMMENT ON COLUMN "public"."student_representative_application"."status_code" IS 'avalduse staatus, viide klassifikaatorile AVALDUS_ESINDAJA_STAATUS';
COMMENT ON COLUMN "public"."student_representative_application"."reject_reason" IS 'tagasi lükkamise põhjendus';
COMMENT ON COLUMN "public"."student_representative_application"."person_id" IS 'viide isikule, avalduse esitaja';
COMMENT ON COLUMN "public"."student_representative_application"."relation_code" IS 'seos õppuriga, viide klassifikaatorile OPPURESINDAJA';

-- ----------------------------
-- Table structure for student_vocational_result
-- ----------------------------
DROP TABLE IF EXISTS "public"."student_vocational_result";
CREATE TABLE "public"."student_vocational_result" (
"id" int8 DEFAULT nextval('student_vocational_result_id_seq'::regclass) NOT NULL,
"student_id" int8 NOT NULL,
"apel_application_record_id" int8,
"curriculum_version_omodule_id" int8 NOT NULL,
"module_name_et" varchar(255) COLLATE "default",
"module_name_en" varchar(255) COLLATE "default",
"apel_school_id" int8,
"grade" varchar(3) COLLATE "default" NOT NULL,
"grade_code" varchar(100) COLLATE "default" NOT NULL,
"grade_mark" int2,
"credits" numeric(4,1) NOT NULL,
"teachers" varchar(255) COLLATE "default",
"grade_date" date,
"protocol_student_id" int8,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6)
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."student_vocational_result" IS 'õppetulemused kutseõppes';
COMMENT ON COLUMN "public"."student_vocational_result"."student_id" IS 'viide õppurile';
COMMENT ON COLUMN "public"."student_vocational_result"."apel_application_record_id" IS 'viide VÕTAle';
COMMENT ON COLUMN "public"."student_vocational_result"."curriculum_version_omodule_id" IS 'viide kutseõp moodulile';
COMMENT ON COLUMN "public"."student_vocational_result"."module_name_et" IS 'mooduli nimi e.k.';
COMMENT ON COLUMN "public"."student_vocational_result"."module_name_en" IS 'mooduli nimi i.k.';
COMMENT ON COLUMN "public"."student_vocational_result"."apel_school_id" IS 'viide õppeasutusele VÕTA puhul';
COMMENT ON COLUMN "public"."student_vocational_result"."grade" IS 'õõetulemus kujul A, 3 jne';
COMMENT ON COLUMN "public"."student_vocational_result"."grade_code" IS 'viide klassifikaatorile KUTSEHINDAMINE';
COMMENT ON COLUMN "public"."student_vocational_result"."grade_mark" IS 'hindeline õppetulemus';
COMMENT ON COLUMN "public"."student_vocational_result"."credits" IS 'EKAP';
COMMENT ON COLUMN "public"."student_vocational_result"."teachers" IS 'õpetajad';
COMMENT ON COLUMN "public"."student_vocational_result"."grade_date" IS 'soorituse kp';
COMMENT ON COLUMN "public"."student_vocational_result"."protocol_student_id" IS 'viide protokollile';

-- ----------------------------
-- Table structure for study_period
-- ----------------------------
DROP TABLE IF EXISTS "public"."study_period";
CREATE TABLE "public"."study_period" (
"id" int8 DEFAULT nextval('study_period_id_seq'::regclass) NOT NULL,
"study_year_id" int8 NOT NULL,
"name_et" varchar(100) COLLATE "default" NOT NULL,
"type_code" varchar(100) COLLATE "default" NOT NULL,
"name_en" varchar(100) COLLATE "default",
"start_date" date NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
"end_date" date NOT NULL
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."study_period" IS 'õppeperioodide tabel';
COMMENT ON COLUMN "public"."study_period"."name_et" IS 'nimi e.k.';
COMMENT ON COLUMN "public"."study_period"."type_code" IS 'viide klaasifikaatorile OPPEPERIOOD';
COMMENT ON COLUMN "public"."study_period"."name_en" IS 'nimi i.k.';
COMMENT ON COLUMN "public"."study_period"."start_date" IS 'alguskuupäev';
COMMENT ON COLUMN "public"."study_period"."end_date" IS 'lõppkuupäev';

-- ----------------------------
-- Table structure for study_period_event
-- ----------------------------
DROP TABLE IF EXISTS "public"."study_period_event";
CREATE TABLE "public"."study_period_event" (
"id" int8 DEFAULT nextval('study_period_event_id_seq'::regclass) NOT NULL,
"study_year_id" int8 NOT NULL,
"study_period_id" int8,
"description_et" varchar(1000) COLLATE "default" NOT NULL,
"event_type_code" varchar(100) COLLATE "default" NOT NULL,
"description_en" varchar(1000) COLLATE "default",
"start" timestamp(6) NOT NULL,
"end" timestamp(6),
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."study_period_event" IS 'kalendri-sündmuste tabel';
COMMENT ON COLUMN "public"."study_period_event"."study_year_id" IS 'viide õppeaastale';
COMMENT ON COLUMN "public"."study_period_event"."study_period_id" IS 'viide õppeperioodile';
COMMENT ON COLUMN "public"."study_period_event"."description_et" IS 'kirjeldus e.k.';
COMMENT ON COLUMN "public"."study_period_event"."event_type_code" IS 'viide klassifikaatorile SYNDMUS';
COMMENT ON COLUMN "public"."study_period_event"."description_en" IS 'kirjeldus i.k.';
COMMENT ON COLUMN "public"."study_period_event"."start" IS 'algus koos kellaajaga';
COMMENT ON COLUMN "public"."study_period_event"."end" IS 'lõpp koos kellaajaga';

-- ----------------------------
-- Table structure for study_year
-- ----------------------------
DROP TABLE IF EXISTS "public"."study_year";
CREATE TABLE "public"."study_year" (
"id" int8 DEFAULT nextval('study_year_id_seq'::regclass) NOT NULL,
"school_id" int8 NOT NULL,
"start_date" date NOT NULL,
"year_code" varchar(100) COLLATE "default" NOT NULL,
"end_date" date NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."study_year" IS 'õppeaastate tabel';
COMMENT ON COLUMN "public"."study_year"."school_id" IS 'viide õppeasutusele';
COMMENT ON COLUMN "public"."study_year"."start_date" IS 'alguskuupäev';
COMMENT ON COLUMN "public"."study_year"."year_code" IS 'viide klassifikaatorile OPPEAASTA';
COMMENT ON COLUMN "public"."study_year"."end_date" IS 'lõppkuupäev';

-- ----------------------------
-- Table structure for study_year_schedule
-- ----------------------------
DROP TABLE IF EXISTS "public"."study_year_schedule";
CREATE TABLE "public"."study_year_schedule" (
"id" int8 DEFAULT nextval('study_year_schedule_id_seq'::regclass) NOT NULL,
"school_id" int8 NOT NULL,
"study_year_schedule_legend_id" int8 NOT NULL,
"study_period_id" int8 NOT NULL,
"week_nr" int2 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"student_group_id" int8 NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
"add_info" varchar(4000) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."study_year_schedule" IS 'õppeasutuse õppetöögraafik';
COMMENT ON COLUMN "public"."study_year_schedule"."school_id" IS 'viide õppeasutusele';
COMMENT ON COLUMN "public"."study_year_schedule"."study_year_schedule_legend_id" IS 'viide õppetöö liigile/legendile';
COMMENT ON COLUMN "public"."study_year_schedule"."study_period_id" IS 'viide õppeperioodile';
COMMENT ON COLUMN "public"."study_year_schedule"."week_nr" IS 'nädala nr, arvutamine algab õppeaasta algusest';
COMMENT ON COLUMN "public"."study_year_schedule"."student_group_id" IS 'viide õpperühmale';
COMMENT ON COLUMN "public"."study_year_schedule"."add_info" IS 'kommentaar';

-- ----------------------------
-- Table structure for study_year_schedule_legend
-- ----------------------------
DROP TABLE IF EXISTS "public"."study_year_schedule_legend";
CREATE TABLE "public"."study_year_schedule_legend" (
"id" int8 DEFAULT nextval('study_year_schedule_legend_id_seq'::regclass) NOT NULL,
"code" varchar(2) COLLATE "default" NOT NULL,
"school_id" int8 NOT NULL,
"name_et" varchar(50) COLLATE "default" NOT NULL,
"name_en" varchar(50) COLLATE "default",
"color" char(7) COLLATE "default" NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."study_year_schedule_legend" IS 'õppetöögraafiku legendid';
COMMENT ON COLUMN "public"."study_year_schedule_legend"."code" IS 'legendi kood';
COMMENT ON COLUMN "public"."study_year_schedule_legend"."school_id" IS 'viide õppeasutusele';
COMMENT ON COLUMN "public"."study_year_schedule_legend"."name_et" IS 'legendi nimetus e.k.';
COMMENT ON COLUMN "public"."study_year_schedule_legend"."name_en" IS 'legendi nimetus i.k.';
COMMENT ON COLUMN "public"."study_year_schedule_legend"."color" IS 'värvikood kujul #FFFFFF';

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
"assessment_description" varchar(10000) COLLATE "default",
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
"changed_by" varchar(100) COLLATE "default",
"is_practice" bool NOT NULL
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."subject" IS 'ainete tabel';
COMMENT ON COLUMN "public"."subject"."school_id" IS 'viide õppeasutusele';
COMMENT ON COLUMN "public"."subject"."code" IS 'õppeaine kood';
COMMENT ON COLUMN "public"."subject"."name_et" IS 'nimetus e.k.';
COMMENT ON COLUMN "public"."subject"."name_en" IS 'nimetus i.k.';
COMMENT ON COLUMN "public"."subject"."assessment_code" IS 'hindamisviis, klassifikaator HINDAMISVIIS';
COMMENT ON COLUMN "public"."subject"."assessment_description" IS 'hindamisviisi kirjeldus';
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
COMMENT ON COLUMN "public"."subject"."is_practice" IS 'kas tegemist on praktikaga false - ei ole praktika true - praktika ';

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
-- Table structure for subject_study_period
-- ----------------------------
DROP TABLE IF EXISTS "public"."subject_study_period";
CREATE TABLE "public"."subject_study_period" (
"id" int8 DEFAULT nextval('subject_study_period_id_seq'::regclass) NOT NULL,
"subject_id" int8 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"study_period_id" int8 NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
"add_info" text COLLATE "default",
"group_proportion_code" varchar(100) COLLATE "default",
"declaration_type_code" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."subject_study_period" IS 'aine-õpetaja-perioodid';
COMMENT ON COLUMN "public"."subject_study_period"."subject_id" IS 'viide õppeainele';
COMMENT ON COLUMN "public"."subject_study_period"."study_period_id" IS 'viide õppeperioodile';
COMMENT ON COLUMN "public"."subject_study_period"."add_info" IS 'märkus';
COMMENT ON COLUMN "public"."subject_study_period"."group_proportion_code" IS 'viide klassifikaatorile PAEVIK_GRUPI_JAOTUS, vaikimisi 1/1';
COMMENT ON COLUMN "public"."subject_study_period"."declaration_type_code" IS 'viide klassifikatorile DEKLARATSIOON';

-- ----------------------------
-- Table structure for subject_study_period_capacity
-- ----------------------------
DROP TABLE IF EXISTS "public"."subject_study_period_capacity";
CREATE TABLE "public"."subject_study_period_capacity" (
"id" int8 DEFAULT nextval('subject_study_period_capacity_id_seq'::regclass) NOT NULL,
"subject_study_period_id" int8 NOT NULL,
"capacity_type_code" varchar(100) COLLATE "default" NOT NULL,
"hours" int2 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."subject_study_period_capacity" IS 'õppeaine mahujaotus etteantud perioodis';
COMMENT ON COLUMN "public"."subject_study_period_capacity"."subject_study_period_id" IS 'viide õppeaine-perioodile';
COMMENT ON COLUMN "public"."subject_study_period_capacity"."capacity_type_code" IS 'viide klassifikaatorile MAHT';
COMMENT ON COLUMN "public"."subject_study_period_capacity"."hours" IS 'maht tundides';

-- ----------------------------
-- Table structure for subject_study_period_plan
-- ----------------------------
DROP TABLE IF EXISTS "public"."subject_study_period_plan";
CREATE TABLE "public"."subject_study_period_plan" (
"id" int8 DEFAULT nextval('subject_study_period_plan_id_seq'::regclass) NOT NULL,
"subject_id" int8 NOT NULL,
"study_period_id" int8 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."subject_study_period_plan" IS 'õppeaine koormus õppeperioodis';
COMMENT ON COLUMN "public"."subject_study_period_plan"."subject_id" IS 'viide õppeainele';
COMMENT ON COLUMN "public"."subject_study_period_plan"."study_period_id" IS 'viide õppeperioodile';

-- ----------------------------
-- Table structure for subject_study_period_plan_capacity
-- ----------------------------
DROP TABLE IF EXISTS "public"."subject_study_period_plan_capacity";
CREATE TABLE "public"."subject_study_period_plan_capacity" (
"id" int8 DEFAULT nextval('subject_study_period_plan_capacity_id_seq'::regclass) NOT NULL,
"inserted" timestamp(6) NOT NULL,
"subject_study_period_plan_id" int8 NOT NULL,
"capacity_type_code" varchar(100) COLLATE "default" NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
"is_contact" bool NOT NULL,
"hours" int2
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."subject_study_period_plan_capacity" IS 'õppeaine koormus mahtude kaupa';
COMMENT ON COLUMN "public"."subject_study_period_plan_capacity"."subject_study_period_plan_id" IS 'viide õppeaine koormuse plaanile';
COMMENT ON COLUMN "public"."subject_study_period_plan_capacity"."capacity_type_code" IS 'mahu jaotuse liik, viide klassifikaatorile MAHT, peab arvestama ainult nende väärtustega, kus is_higher=true';
COMMENT ON COLUMN "public"."subject_study_period_plan_capacity"."hours" IS 'maht tundides';

-- ----------------------------
-- Table structure for subject_study_period_plan_curriculum
-- ----------------------------
DROP TABLE IF EXISTS "public"."subject_study_period_plan_curriculum";
CREATE TABLE "public"."subject_study_period_plan_curriculum" (
"id" int8 DEFAULT nextval('subject_study_period_plan_curriculum_id_seq'::regclass) NOT NULL,
"subject_study_period_plan_id" int8 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
"curriculum_id" int8 NOT NULL
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."subject_study_period_plan_curriculum" IS 'õppeaine koormus -kehtib õppekavades...';
COMMENT ON COLUMN "public"."subject_study_period_plan_curriculum"."subject_study_period_plan_id" IS 'viide õppeaine koormusele plaanile';
COMMENT ON COLUMN "public"."subject_study_period_plan_curriculum"."curriculum_id" IS 'viide kõrgharidusõppekavale';

-- ----------------------------
-- Table structure for subject_study_period_plan_studyform
-- ----------------------------
DROP TABLE IF EXISTS "public"."subject_study_period_plan_studyform";
CREATE TABLE "public"."subject_study_period_plan_studyform" (
"id" int8 DEFAULT nextval('subject_study_period_plan_studyform_id_seq'::regclass) NOT NULL,
"subject_study_period_plan_id" int8 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
"study_form_code" varchar(100) COLLATE "default" NOT NULL
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."subject_study_period_plan_studyform" IS 'õppeaine koormus - kehtib õppevormides';
COMMENT ON COLUMN "public"."subject_study_period_plan_studyform"."subject_study_period_plan_id" IS 'viide õppeaine õppekoormusele';
COMMENT ON COLUMN "public"."subject_study_period_plan_studyform"."study_form_code" IS 'viide klassifikaatorile OPPEVORM';

-- ----------------------------
-- Table structure for subject_study_period_student_group
-- ----------------------------
DROP TABLE IF EXISTS "public"."subject_study_period_student_group";
CREATE TABLE "public"."subject_study_period_student_group" (
"id" int8 DEFAULT nextval('subject_study_period_student_group_id_seq'::regclass) NOT NULL,
"subject_study_period_id" int8 NOT NULL,
"student_group_id" int8 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."subject_study_period_student_group" IS 'õppeaine ja rühma seos';
COMMENT ON COLUMN "public"."subject_study_period_student_group"."subject_study_period_id" IS 'viide õppeaine perioodile';
COMMENT ON COLUMN "public"."subject_study_period_student_group"."student_group_id" IS 'viide õpperühmale';

-- ----------------------------
-- Table structure for subject_study_period_teacher
-- ----------------------------
DROP TABLE IF EXISTS "public"."subject_study_period_teacher";
CREATE TABLE "public"."subject_study_period_teacher" (
"id" int8 DEFAULT nextval('subject_study_period_teacher_id_seq'::regclass) NOT NULL,
"subject_study_period_id" int8 NOT NULL,
"teacher_id" int8 NOT NULL,
"is_signatory" bool NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."subject_study_period_teacher"."subject_study_period_id" IS 'viide aine perioodile';
COMMENT ON COLUMN "public"."subject_study_period_teacher"."teacher_id" IS 'viide õpetajale';
COMMENT ON COLUMN "public"."subject_study_period_teacher"."is_signatory" IS 'kas on protokolli allkirjastaja';

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS "public"."teacher";
CREATE TABLE "public"."teacher" (
"id" int8 DEFAULT nextval('teacher_id_seq'::regclass) NOT NULL,
"person_id" int8 NOT NULL,
"school_id" int8 NOT NULL,
"email" varchar(100) COLLATE "default",
"phone" varchar(100) COLLATE "default",
"is_vocational" bool NOT NULL,
"is_higher" bool NOT NULL,
"schedule_load" int2 NOT NULL,
"teacher_occupation_id" int8 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
"is_study_period_schedule_load" bool,
"is_active" bool NOT NULL,
"rtip_nr" varchar(20) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."teacher" IS 'õpetajad';
COMMENT ON COLUMN "public"."teacher"."person_id" IS 'viide isikule';
COMMENT ON COLUMN "public"."teacher"."school_id" IS 'viide õppeasutusele';
COMMENT ON COLUMN "public"."teacher"."email" IS 'õpetaja e-posti aadress';
COMMENT ON COLUMN "public"."teacher"."phone" IS 'õpetaja kontakttelefon antud õppeasutuses';
COMMENT ON COLUMN "public"."teacher"."is_vocational" IS 'kas tegemist on kutseõppe õpetajaga  true - kutseõppe õpetaja false - ei ole kutseõppe õpetaja';
COMMENT ON COLUMN "public"."teacher"."is_higher" IS 'kas tegemist on kõrgharidusõppe õpetajaga  true - kõrgharidusõppe õpetaja false - ei ole kõrgharidusõppe õpetaja';
COMMENT ON COLUMN "public"."teacher"."schedule_load" IS 'tunniplaani koormus, vaikimisi 0';
COMMENT ON COLUMN "public"."teacher"."teacher_occupation_id" IS 'viide ametikohale õppeasutuses';
COMMENT ON COLUMN "public"."teacher"."is_study_period_schedule_load" IS 'kas tegemist on õppeperioodi või õppeaasta koormusega:
true - õppeperioodi koormus
false - õpepaasta koormus

';
COMMENT ON COLUMN "public"."teacher"."is_active" IS 'kas õpetaja on aktiivne 
true - jah 
false - ei
';
COMMENT ON COLUMN "public"."teacher"."rtip_nr" IS 'RTIP õpetaja personaalne number';

-- ----------------------------
-- Table structure for teacher_absence
-- ----------------------------
DROP TABLE IF EXISTS "public"."teacher_absence";
CREATE TABLE "public"."teacher_absence" (
"id" int8 DEFAULT nextval('teacher_absence_id_seq'::regclass) NOT NULL,
"teacher_id" int8 NOT NULL,
"start_date" date NOT NULL,
"end_date" date,
"reason" varchar(255) COLLATE "default" NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(50) COLLATE "default",
"version" int4 NOT NULL
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."teacher_absence" IS 'õpetajate puudumised';
COMMENT ON COLUMN "public"."teacher_absence"."teacher_id" IS 'viide õpetaja tabelile';
COMMENT ON COLUMN "public"."teacher_absence"."start_date" IS 'puudumise algus';
COMMENT ON COLUMN "public"."teacher_absence"."end_date" IS 'puudumise lõpp';
COMMENT ON COLUMN "public"."teacher_absence"."reason" IS 'puudumise põhjus';

-- ----------------------------
-- Table structure for teacher_continuing_education
-- ----------------------------
DROP TABLE IF EXISTS "public"."teacher_continuing_education";
CREATE TABLE "public"."teacher_continuing_education" (
"id" int8 DEFAULT nextval('teacher_continuing_education_id_seq'::regclass) NOT NULL,
"school_code" varchar(100) COLLATE "default",
"teacher_id" int8 NOT NULL,
"name_et" varchar(1000) COLLATE "default" NOT NULL,
"other_school" varchar(1000) COLLATE "default",
"field_code" varchar(100) COLLATE "default" NOT NULL,
"diploma_code" varchar(100) COLLATE "default" NOT NULL,
"diploma_date" date NOT NULL,
"diploma_nr" varchar(100) COLLATE "default",
"is_abroad" bool NOT NULL,
"abroad_desc" varchar(4000) COLLATE "default",
"capacity" int2 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."teacher_continuing_education" IS 'õpetaja täiendusõpe';
COMMENT ON COLUMN "public"."teacher_continuing_education"."school_code" IS 'viide klassifikaatorile EHIS_TAIEND_OPPEAS';
COMMENT ON COLUMN "public"."teacher_continuing_education"."teacher_id" IS 'viide õpetajale';
COMMENT ON COLUMN "public"."teacher_continuing_education"."name_et" IS 'koolituse nimi';
COMMENT ON COLUMN "public"."teacher_continuing_education"."field_code" IS 'koolituse valdkond, viide klassifikaatorile EHIS_TAIEND_VALDKOND';
COMMENT ON COLUMN "public"."teacher_continuing_education"."diploma_code" IS 'koolitusel saadud dokument, viide klassifikaatorile EHIS_TAIEND_DOK';
COMMENT ON COLUMN "public"."teacher_continuing_education"."diploma_date" IS 'dokumendi kp';
COMMENT ON COLUMN "public"."teacher_continuing_education"."diploma_nr" IS 'dokumendi nr';
COMMENT ON COLUMN "public"."teacher_continuing_education"."is_abroad" IS 'kas tehtud välismaal true - jah false - ei';
COMMENT ON COLUMN "public"."teacher_continuing_education"."abroad_desc" IS 'välisriigi koolituse sisu';
COMMENT ON COLUMN "public"."teacher_continuing_education"."capacity" IS 'koolituse maht tundides';

-- ----------------------------
-- Table structure for teacher_mobility
-- ----------------------------
DROP TABLE IF EXISTS "public"."teacher_mobility";
CREATE TABLE "public"."teacher_mobility" (
"id" int8 DEFAULT nextval('teacher_mobility_id_seq'::regclass) NOT NULL,
"teacher_id" int8 NOT NULL,
"start" date NOT NULL,
"end" date NOT NULL,
"target_code" varchar(100) COLLATE "default" NOT NULL,
"school" varchar(255) COLLATE "default" NOT NULL,
"state_code" varchar(100) COLLATE "default" NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."teacher_mobility" IS 'lühiajaline mobiilsus,eemalolek';
COMMENT ON COLUMN "public"."teacher_mobility"."start" IS 'algus kp';
COMMENT ON COLUMN "public"."teacher_mobility"."end" IS 'lõpp kp';
COMMENT ON COLUMN "public"."teacher_mobility"."target_code" IS 'lähetuse eesmärk, viide klassifikaatorile EHIS_MOBIILSUS';
COMMENT ON COLUMN "public"."teacher_mobility"."school" IS 'sihtõppeasutus, tekst';
COMMENT ON COLUMN "public"."teacher_mobility"."state_code" IS 'lähetuse riik, viide klassifikaatorile RIIK';

-- ----------------------------
-- Table structure for teacher_occupation
-- ----------------------------
DROP TABLE IF EXISTS "public"."teacher_occupation";
CREATE TABLE "public"."teacher_occupation" (
"id" int8 DEFAULT nextval('teacher_occupation_id_seq'::regclass) NOT NULL,
"occupation_et" varchar(100) COLLATE "default" NOT NULL,
"school_id" int4 NOT NULL,
"occupation_en" varchar(100) COLLATE "default",
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
-- Table structure for teacher_position_ehis
-- ----------------------------
DROP TABLE IF EXISTS "public"."teacher_position_ehis";
CREATE TABLE "public"."teacher_position_ehis" (
"id" int8 DEFAULT nextval('teacher_position_ehis_id_seq'::regclass) NOT NULL,
"is_vocational" bool NOT NULL,
"teacher_id" int8 NOT NULL,
"position_code" varchar(100) COLLATE "default",
"position_specification_et" varchar(255) COLLATE "default",
"contract_start" date NOT NULL,
"is_contract_ended" bool NOT NULL,
"contract_type_code" varchar(100) COLLATE "default" NOT NULL,
"load" numeric(3,2) NOT NULL,
"language_code" varchar(100) COLLATE "default",
"meets_qualification" bool,
"is_child_care" bool,
"is_class_teacher" bool,
"position_specification_en" varchar(255) COLLATE "default",
"employment_type_code" varchar(100) COLLATE "default",
"employment_type_specification" varchar(255) COLLATE "default",
"is_teacher" bool NOT NULL,
"employment_code" varchar(100) COLLATE "default",
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default",
"contract_end" date,
"school_department_id" int8
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."teacher_position_ehis" IS 'õpetaja EHISe ametikoht';
COMMENT ON COLUMN "public"."teacher_position_ehis"."is_vocational" IS 'kas tegemist on kutse või kõrgharidusametikohaga, vaikimisi true true - kutse ametikoht false - kõrg ametikoht vajalik EHISe jaoks, sest sõltuvalt antud väärtusest tuleb kasutada erinevaid veebiteenuseid';
COMMENT ON COLUMN "public"."teacher_position_ehis"."teacher_id" IS 'viide õpetajale';
COMMENT ON COLUMN "public"."teacher_position_ehis"."position_code" IS 'ametikoha kood, viide klassifikaatorile EHIS_AMETIKOHT';
COMMENT ON COLUMN "public"."teacher_position_ehis"."position_specification_et" IS 'ametikoha täpsustus';
COMMENT ON COLUMN "public"."teacher_position_ehis"."contract_start" IS 'lepingu algus kp';
COMMENT ON COLUMN "public"."teacher_position_ehis"."is_contract_ended" IS 'kas leping on lõppenud true - leping on lõppenud false - leping ei ole lõppenud';
COMMENT ON COLUMN "public"."teacher_position_ehis"."contract_type_code" IS 'lepingu liik, viide klassifikaatorile EHIS_LEPING';
COMMENT ON COLUMN "public"."teacher_position_ehis"."load" IS 'koormus, väärtus 0 kuni 5, nt 0.8 jne';
COMMENT ON COLUMN "public"."teacher_position_ehis"."language_code" IS 'kutse puhul õppekeel, viide klassifikaatorile EHIS_OPETAJA_KEEL';
COMMENT ON COLUMN "public"."teacher_position_ehis"."meets_qualification" IS 'kutse puhul kas vastab kvalifikatsioonile true - vastab false - ei vasta';
COMMENT ON COLUMN "public"."teacher_position_ehis"."is_child_care" IS 'kutse puhul kas viibib lapsehoolduspuhkusel true - on lapsehoolduspuhkusel false - ei ole lapsehoolduspuhkusel';
COMMENT ON COLUMN "public"."teacher_position_ehis"."is_class_teacher" IS 'kutse puhul kas on klassijuhataja true - on klassijuhataja false - on klassijuhataja';
COMMENT ON COLUMN "public"."teacher_position_ehis"."position_specification_en" IS 'kõrg puhul ametikoha täpsustus';
COMMENT ON COLUMN "public"."teacher_position_ehis"."employment_type_code" IS 'kõrg puhul töösuhe, viide klassifikaatorile EHIS_TOOSUHE';
COMMENT ON COLUMN "public"."teacher_position_ehis"."employment_type_specification" IS 'töösuhte täpsustus';
COMMENT ON COLUMN "public"."teacher_position_ehis"."is_teacher" IS 'kõrg puhul kas tegemist on õppejõuga, vaikimisi true true - on õpõpejõud/õpetaja false - ei ole (nt nooremteadur vms)';
COMMENT ON COLUMN "public"."teacher_position_ehis"."employment_code" IS 'kõrg puhul töösuhte kood';
COMMENT ON COLUMN "public"."teacher_position_ehis"."contract_end" IS 'lepingu lõpp kp';
COMMENT ON COLUMN "public"."teacher_position_ehis"."school_department_id" IS 'viide struktuuriüksusele';

-- ----------------------------
-- Table structure for teacher_qualification
-- ----------------------------
DROP TABLE IF EXISTS "public"."teacher_qualification";
CREATE TABLE "public"."teacher_qualification" (
"id" int8 DEFAULT nextval('teacher_qualification_id_seq'::regclass) NOT NULL,
"teacher_id" int8 NOT NULL,
"qualification_code" varchar(100) COLLATE "default" NOT NULL,
"qualification_name_code" varchar(100) COLLATE "default",
"state_code" varchar(100) COLLATE "default" NOT NULL,
"school_code" varchar(100) COLLATE "default",
"ex_school_code" varchar(100) COLLATE "default",
"qualification_other" varchar(255) COLLATE "default",
"year" int2 NOT NULL,
"school_other" varchar(255) COLLATE "default",
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default",
"diploma_nr" varchar(100) COLLATE "default",
"study_level_code" varchar(100) COLLATE "default",
"end_date" date,
"specialty" varchar(255) COLLATE "default",
"add_info" varchar(4000) COLLATE "default",
"language_code" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."teacher_qualification" IS 'õpetaja kvalifikatsioon';
COMMENT ON COLUMN "public"."teacher_qualification"."qualification_code" IS 'kvalifikatsioon, viide klassifikaatorile EHIS_KVALIFIKATSIOON';
COMMENT ON COLUMN "public"."teacher_qualification"."qualification_name_code" IS 'kvalifikatsiooni nimetus, viide klassifikaatorile EHIS_KVALIFIKATSIOON_NIMI';
COMMENT ON COLUMN "public"."teacher_qualification"."state_code" IS 'riik, viide klassifikaatorile RIIK';
COMMENT ON COLUMN "public"."teacher_qualification"."school_code" IS 'õppeasutus, viide klassifikaatorile EHIS_EESTI_OPPEASUTUS';
COMMENT ON COLUMN "public"."teacher_qualification"."ex_school_code" IS 'õppeasutuse endine nimi, viide klassifikaatorile EHIS_EESTI_OPPEASUTUS_ENDINE';
COMMENT ON COLUMN "public"."teacher_qualification"."qualification_other" IS 'muu kvalifikatsiooni nimetus, täpsustus';
COMMENT ON COLUMN "public"."teacher_qualification"."year" IS 'aasta kujul YYYY';
COMMENT ON COLUMN "public"."teacher_qualification"."school_other" IS 'Muu õppeasutus (täidetakse juhul, kui väljale ’Riik’ ei ole valitud ’Eesti’)';
COMMENT ON COLUMN "public"."teacher_qualification"."diploma_nr" IS 'dokumendi nr kutseõppes';
COMMENT ON COLUMN "public"."teacher_qualification"."study_level_code" IS 'haridustase kutseõppes, viide klassifikaatorile EHIS_EDUCATION_LEVEL';
COMMENT ON COLUMN "public"."teacher_qualification"."end_date" IS 'lõpetamise kp kutseõppes';
COMMENT ON COLUMN "public"."teacher_qualification"."specialty" IS 'peaeriala/õppekava kutseõppes';
COMMENT ON COLUMN "public"."teacher_qualification"."add_info" IS 'kommentaar kutseõppes';
COMMENT ON COLUMN "public"."teacher_qualification"."language_code" IS 'keel kutseõppes, viide klassifikaatorile EHIS_OPETAJA_KEEL';

-- ----------------------------
-- Table structure for timetable
-- ----------------------------
DROP TABLE IF EXISTS "public"."timetable";
CREATE TABLE "public"."timetable" (
"id" int8 DEFAULT nextval('timetable_id_seq'::regclass) NOT NULL,
"school_id" int8 NOT NULL,
"start_date" date NOT NULL,
"study_period_id" int8 NOT NULL,
"end_date" date NOT NULL,
"inserted" timestamp(6) NOT NULL,
"status_code" varchar(100) COLLATE "default",
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
"is_higher" bool NOT NULL
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."timetable" IS 'tunniplaan';
COMMENT ON COLUMN "public"."timetable"."school_id" IS 'viide õppeasutusele';
COMMENT ON COLUMN "public"."timetable"."start_date" IS 'algus kp';
COMMENT ON COLUMN "public"."timetable"."study_period_id" IS 'viide õppeperioodile';
COMMENT ON COLUMN "public"."timetable"."end_date" IS 'lõpp kp';
COMMENT ON COLUMN "public"."timetable"."status_code" IS 'viide klassifikaatorile TUNNIPLAAN_STAATUS';
COMMENT ON COLUMN "public"."timetable"."is_higher" IS 'kas kõrg või kutse tunniplaan
true - kõrg
false - kutse';

-- ----------------------------
-- Table structure for timetable_event
-- ----------------------------
DROP TABLE IF EXISTS "public"."timetable_event";
CREATE TABLE "public"."timetable_event" (
"id" int8 DEFAULT nextval('timetable_event_id_seq'::regclass) NOT NULL,
"start" timestamp(6) NOT NULL,
"end" timestamp(6) NOT NULL,
"lessons" int2,
"consider_break" bool,
"timetable_object_id" int8,
"name" varchar(255) COLLATE "default",
"repeat_code" varchar(100) COLLATE "default" NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
"lesson_nr" int2,
"capacity_type_code" varchar(100) COLLATE "default",
"subject_study_period_student_group_id" int8
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."timetable_event" IS 'tunniplaani sündmus - üksik või kõik';
COMMENT ON COLUMN "public"."timetable_event"."id" IS 'viide klassifikaatorile MAHT';
COMMENT ON COLUMN "public"."timetable_event"."start" IS 'algus koos kellaajaga';
COMMENT ON COLUMN "public"."timetable_event"."end" IS 'lõpp koos kellaajaga';
COMMENT ON COLUMN "public"."timetable_event"."lessons" IS 'tundide arv';
COMMENT ON COLUMN "public"."timetable_event"."consider_break" IS 'kas arvestada vahetundi';
COMMENT ON COLUMN "public"."timetable_event"."name" IS 'sündmuse nimi, kasutatakse üksiksündmuste puhul';
COMMENT ON COLUMN "public"."timetable_event"."repeat_code" IS 'viide klassifikaatorile TUNNIPLAAN_SYNDMUS_KORDUS, vaikimisi TUNNIPLAAN_SYNDMUS_KORDUS_EI';
COMMENT ON COLUMN "public"."timetable_event"."lesson_nr" IS 'tunni nr';
COMMENT ON COLUMN "public"."timetable_event"."subject_study_period_student_group_id" IS 'viide subject_study_period_student_group''le';

-- ----------------------------
-- Table structure for timetable_event_room
-- ----------------------------
DROP TABLE IF EXISTS "public"."timetable_event_room";
CREATE TABLE "public"."timetable_event_room" (
"id" int8 DEFAULT nextval('timetable_event_room_id_seq'::regclass) NOT NULL,
"room_id" int8 NOT NULL,
"timetable_event_time_id" int8 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."timetable_event_room" IS 'tunniplaani ruum';
COMMENT ON COLUMN "public"."timetable_event_room"."room_id" IS 'viide ruumile';
COMMENT ON COLUMN "public"."timetable_event_room"."timetable_event_time_id" IS 'viide toimumisajale';

-- ----------------------------
-- Table structure for timetable_event_teacher
-- ----------------------------
DROP TABLE IF EXISTS "public"."timetable_event_teacher";
CREATE TABLE "public"."timetable_event_teacher" (
"id" int8 DEFAULT nextval('timetable_event_teacher_id_seq'::regclass) NOT NULL,
"teacher_id" int8 NOT NULL,
"timetable_event_time_id" int8,
"inserted" timestamp(6) NOT NULL,
"changed" time(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."timetable_event_teacher" IS 'tunniplaani õpetaja';
COMMENT ON COLUMN "public"."timetable_event_teacher"."teacher_id" IS 'viide õpetajale';
COMMENT ON COLUMN "public"."timetable_event_teacher"."timetable_event_time_id" IS 'viide toimumisajale';

-- ----------------------------
-- Table structure for timetable_event_time
-- ----------------------------
DROP TABLE IF EXISTS "public"."timetable_event_time";
CREATE TABLE "public"."timetable_event_time" (
"id" int8 DEFAULT nextval('timetable_event_time_id_seq'::regclass) NOT NULL,
"timetable_event_id" int8 NOT NULL,
"start" timestamp(6) NOT NULL,
"end" timestamp(6) NOT NULL,
"other_teacher" varchar(1000) COLLATE "default",
"other_room" varchar(1000) COLLATE "default",
"inserted" timestamp(6),
"changed" timestamp(6),
"version" int4,
"inserted_by" varchar(100) COLLATE "default",
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."timetable_event_time" IS 'reaalne toimumisaeg';
COMMENT ON COLUMN "public"."timetable_event_time"."timetable_event_id" IS 'viide tunniplaanile';
COMMENT ON COLUMN "public"."timetable_event_time"."start" IS 'üksiksündmuse algus';
COMMENT ON COLUMN "public"."timetable_event_time"."end" IS 'üksiksündmuse lõpp';

-- ----------------------------
-- Table structure for timetable_object
-- ----------------------------
DROP TABLE IF EXISTS "public"."timetable_object";
CREATE TABLE "public"."timetable_object" (
"id" int8 DEFAULT nextval('timetable_object_id_seq'::regclass) NOT NULL,
"timetable_id" int8 NOT NULL,
"journal_id" int8,
"subject_study_period_id" int8,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int8 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."timetable_object" IS 'tunniplaani objekt, täidetakse juhul kui tegemist on päeviku või aine-õppejõu paariga';
COMMENT ON COLUMN "public"."timetable_object"."timetable_id" IS 'viide tunniplaanile';
COMMENT ON COLUMN "public"."timetable_object"."journal_id" IS 'viide päevikule';
COMMENT ON COLUMN "public"."timetable_object"."subject_study_period_id" IS 'viide aine-õppejõu paarile';

-- ----------------------------
-- Table structure for timetable_object_student_group
-- ----------------------------
DROP TABLE IF EXISTS "public"."timetable_object_student_group";
CREATE TABLE "public"."timetable_object_student_group" (
"id" int8 DEFAULT nextval('timetable_object_student_group_id_seq'::regclass) NOT NULL,
"student_group_id" int8 NOT NULL,
"timetable_object_id" int8 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."timetable_object_student_group" IS 'tunniplaani objektidega seotud rühmad';
COMMENT ON COLUMN "public"."timetable_object_student_group"."student_group_id" IS 'viide tunniplaani sündmusega seotud grupile';

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
"changed_by" varchar(100) COLLATE "default",
"valid_from" date,
"valid_thru" date,
"student_id" int8,
"teacher_id" int8
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."user_" IS 'kasutajate tabel';
COMMENT ON COLUMN "public"."user_"."school_id" IS 'viide õppeasutusele';
COMMENT ON COLUMN "public"."user_"."person_id" IS 'viide isikule';
COMMENT ON COLUMN "public"."user_"."role_code" IS 'kasutaja roll, nt peaadministraator, admin. töötaja jne';
COMMENT ON COLUMN "public"."user_"."valid_from" IS 'kehtib alates';
COMMENT ON COLUMN "public"."user_"."valid_thru" IS 'kehtib kuni';
COMMENT ON COLUMN "public"."user_"."student_id" IS 'viide õppurile';
COMMENT ON COLUMN "public"."user_"."teacher_id" IS 'viide õpetajale';

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
-- Table structure for user_role_default
-- ----------------------------
DROP TABLE IF EXISTS "public"."user_role_default";
CREATE TABLE "public"."user_role_default" (
"object_code" varchar(100) COLLATE "default" NOT NULL,
"permission_code" varchar(100) COLLATE "default" NOT NULL,
"role_code" varchar(100) COLLATE "default" NOT NULL
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."user_role_default" IS 'vaikimisi õigused';
COMMENT ON COLUMN "public"."user_role_default"."object_code" IS 'viide klassifikaatorile TEEMAOIGUS';
COMMENT ON COLUMN "public"."user_role_default"."permission_code" IS 'viide klassifikaatorile OIGUS';
COMMENT ON COLUMN "public"."user_role_default"."role_code" IS 'viide klassifikaatorile ROLL';

-- ----------------------------
-- Table structure for user_sessions
-- ----------------------------
DROP TABLE IF EXISTS "public"."user_sessions";
CREATE TABLE "public"."user_sessions" (
"id" int8 DEFAULT nextval('user_sessions_id_seq'::regclass) NOT NULL,
"person_id" int8 NOT NULL,
"user_id" int8 NOT NULL,
"ip_address" varchar(20) COLLATE "default" NOT NULL,
"user_browser" varchar(1000) COLLATE "default" NOT NULL,
"type_code" varchar(100) COLLATE "default" NOT NULL,
"inserted" timestamp(6) NOT NULL,
"inserted_by" varchar(50) COLLATE "default" NOT NULL,
"session_id" varchar(100) COLLATE "default" NOT NULL,
"ended" timestamp(6)
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."user_sessions" IS 'kasutajate sessioonid';
COMMENT ON COLUMN "public"."user_sessions"."person_id" IS 'isiku id';
COMMENT ON COLUMN "public"."user_sessions"."user_id" IS 'kasutaja id';
COMMENT ON COLUMN "public"."user_sessions"."ip_address" IS 'ip aadress';
COMMENT ON COLUMN "public"."user_sessions"."user_browser" IS 'kasutaja brauser';
COMMENT ON COLUMN "public"."user_sessions"."type_code" IS 'viide klassifikaaotirile LOGIN_TYPE';
COMMENT ON COLUMN "public"."user_sessions"."inserted" IS 'sisselogimise aeg';
COMMENT ON COLUMN "public"."user_sessions"."inserted_by" IS 'sisselogija nimi';
COMMENT ON COLUMN "public"."user_sessions"."session_id" IS 'tehtud sessiooni id (token?)';
COMMENT ON COLUMN "public"."user_sessions"."ended" IS 'sessioni lõpp (välja logimise nupule vajutamine)';

-- ----------------------------
-- Table structure for ws_ehis_curriculum_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."ws_ehis_curriculum_log";
CREATE TABLE "public"."ws_ehis_curriculum_log" (
"id" int8 DEFAULT nextval('ws_ehis_curriculum_log_id_seq'::regclass) NOT NULL,
"school_id" int8 NOT NULL,
"ws_name" varchar(255) COLLATE "default" NOT NULL,
"request" text COLLATE "default" NOT NULL,
"response" text COLLATE "default",
"log_txt" text COLLATE "default",
"has_xtee_errors" bool NOT NULL,
"has_other_errors" bool NOT NULL,
"inserted" timestamp(6) NOT NULL,
"inserted_by" varchar(100) COLLATE "default",
"curriculum_id" int8 NOT NULL
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."ws_ehis_curriculum_log" IS 'õppekava EHIS log fail';
COMMENT ON COLUMN "public"."ws_ehis_curriculum_log"."school_id" IS 'viide õppeasutusele';
COMMENT ON COLUMN "public"."ws_ehis_curriculum_log"."ws_name" IS 'teenuse nimi';
COMMENT ON COLUMN "public"."ws_ehis_curriculum_log"."curriculum_id" IS 'viide õppekavale';

-- ----------------------------
-- Table structure for ws_ehis_student_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."ws_ehis_student_log";
CREATE TABLE "public"."ws_ehis_student_log" (
"id" int8 DEFAULT nextval('ws_ehis_student_log_id_seq'::regclass) NOT NULL,
"school_id" int8 NOT NULL,
"directive_id" int8,
"ws_name" varchar(255) COLLATE "default",
"request" text COLLATE "default",
"response" text COLLATE "default",
"has_xtee_errors" bool NOT NULL,
"has_other_errors" bool NOT NULL,
"log_txt" text COLLATE "default",
"inserted" timestamp(6) NOT NULL,
"inserted_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."ws_ehis_student_log"."school_id" IS 'viide õppeasutusele';
COMMENT ON COLUMN "public"."ws_ehis_student_log"."directive_id" IS 'viide käskkirjale';
COMMENT ON COLUMN "public"."ws_ehis_student_log"."ws_name" IS 'teenuse nimi';
COMMENT ON COLUMN "public"."ws_ehis_student_log"."has_other_errors" IS 'kas on vigu, nt vastuses on sõna Viga false - ei true - jah  kui has_xtee_errors=true, siis has_other_errors=false';

-- ----------------------------
-- Table structure for ws_ehis_teacher_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."ws_ehis_teacher_log";
CREATE TABLE "public"."ws_ehis_teacher_log" (
"id" int8 DEFAULT nextval('ws_ehis_teacher_log_id_seq'::regclass) NOT NULL,
"ws_name" varchar(255) COLLATE "default" NOT NULL,
"request" text COLLATE "default" NOT NULL,
"response" text COLLATE "default",
"has_xtee_errors" bool NOT NULL,
"has_other_errors" bool NOT NULL,
"log_txt" text COLLATE "default",
"inserted" timestamp(6),
"school_id" int8 NOT NULL,
"teacher_id" int8 NOT NULL,
"inserted_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."ws_ehis_teacher_log" IS 'log tabel õppejõu andmete EHISe edastamsie jaoks';
COMMENT ON COLUMN "public"."ws_ehis_teacher_log"."ws_name" IS 'teenuse nimi';

-- ----------------------------
-- Table structure for ws_ekis_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."ws_ekis_log";
CREATE TABLE "public"."ws_ekis_log" (
"id" int8 DEFAULT nextval('ws_ekis_log_id_seq'::regclass) NOT NULL,
"ws_name" varchar(255) COLLATE "default" NOT NULL,
"request" text COLLATE "default" NOT NULL,
"response" text COLLATE "default",
"has_errors" bool NOT NULL,
"log_txt" text COLLATE "default",
"school_id" int8,
"contract_id" int8,
"directive_id" int8,
"certificate_id" int8,
"inserted" timestamp(6) NOT NULL,
"inserted_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."ws_ekis_log" IS 'EKISe teenuste logi';
COMMENT ON COLUMN "public"."ws_ekis_log"."ws_name" IS 'teenuse nimi';

-- ----------------------------
-- Table structure for ws_qf_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."ws_qf_log";
CREATE TABLE "public"."ws_qf_log" (
"id" int8 DEFAULT nextval('ws_qf_log_id_seq'::regclass) NOT NULL,
"school_id" int8,
"ws_name" varchar(255) COLLATE "default" NOT NULL,
"request" text COLLATE "default" NOT NULL,
"response" text COLLATE "default",
"has_errors" bool NOT NULL,
"log_txt" text COLLATE "default",
"inserted" timestamp(6) NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."ws_qf_log" IS 'kutseregistri päringute logi';
COMMENT ON COLUMN "public"."ws_qf_log"."school_id" IS 'viide õppeasutusele';
COMMENT ON COLUMN "public"."ws_qf_log"."ws_name" IS 'teenuse nimi';
COMMENT ON COLUMN "public"."ws_qf_log"."request" IS 'päringu sisend';
COMMENT ON COLUMN "public"."ws_qf_log"."response" IS 'päringu väljund';
COMMENT ON COLUMN "public"."ws_qf_log"."has_errors" IS 'kas on vigu';
COMMENT ON COLUMN "public"."ws_qf_log"."log_txt" IS 'log tekst, sh veateade';

-- ----------------------------
-- Table structure for ws_rtip_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."ws_rtip_log";
CREATE TABLE "public"."ws_rtip_log" (
"id" int8 DEFAULT nextval('ws_rtip_log_id_seq'::regclass) NOT NULL,
"school_id" int8 NOT NULL,
"ws_name" varchar(255) COLLATE "default" NOT NULL,
"request" text COLLATE "default" NOT NULL,
"response" text COLLATE "default",
"has_errors" bool NOT NULL,
"log_txt" text COLLATE "default",
"inserted" timestamp(6) NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."ws_rtip_log" IS 'RTIP teenuse logitabel';

-- ----------------------------
-- Table structure for ws_sais_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."ws_sais_log";
CREATE TABLE "public"."ws_sais_log" (
"id" int8 DEFAULT nextval('ws_sais_log_id_seq'::regclass) NOT NULL,
"school_id" int8,
"ws_name" varchar(255) COLLATE "default" NOT NULL,
"query_date_from" date,
"query_date_thru" date,
"process_query_start" timestamp(6),
"process_query_end" timestamp(6),
"request" text COLLATE "default",
"response" text COLLATE "default",
"has_xtee_errors" bool NOT NULL,
"has_other_errors" bool NOT NULL,
"record_count" int4,
"first_ws_sais_log_id" int8,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."ws_sais_log" IS 'sais andmevahetuse log';
COMMENT ON COLUMN "public"."ws_sais_log"."school_id" IS 'päringu esitaja õppeasutus';
COMMENT ON COLUMN "public"."ws_sais_log"."ws_name" IS 'teenuse nimi';
COMMENT ON COLUMN "public"."ws_sais_log"."query_date_from" IS 'päringu sisendi algus kp';
COMMENT ON COLUMN "public"."ws_sais_log"."query_date_thru" IS 'päringu sisendi lõpp kp';
COMMENT ON COLUMN "public"."ws_sais_log"."process_query_start" IS 'päringu töötlemise algus';
COMMENT ON COLUMN "public"."ws_sais_log"."process_query_end" IS 'päringu töötlemise lõpp';
COMMENT ON COLUMN "public"."ws_sais_log"."request" IS 'päringu sisend (xml)';
COMMENT ON COLUMN "public"."ws_sais_log"."response" IS 'päringu väljund (xml)';
COMMENT ON COLUMN "public"."ws_sais_log"."has_xtee_errors" IS 'kas on vigu (turvaserver ei vasta, vigane SOAP vms) true - jah false - ei';
COMMENT ON COLUMN "public"."ws_sais_log"."has_other_errors" IS 'kas on vigu töötlemisel (ei saanud midagi andmebaasi salvestada vms) false - ei true - jah  kui has_xtee_errors=true, siis has_other_errors=false';
COMMENT ON COLUMN "public"."ws_sais_log"."record_count" IS 'kirjete arv';
COMMENT ON COLUMN "public"."ws_sais_log"."first_ws_sais_log_id" IS 'kui päring esitatakse mitmekordselt, siis viide esimesele päringule';

-- ----------------------------
-- Table structure for ws_sais_log_detail
-- ----------------------------
DROP TABLE IF EXISTS "public"."ws_sais_log_detail";
CREATE TABLE "public"."ws_sais_log_detail" (
"id" int8 DEFAULT nextval('ws_sais_log_detail_id_seq'::regclass) NOT NULL,
"is_error" bool NOT NULL,
"ws_sais_log_id" int8 NOT NULL,
"log_txt" text COLLATE "default" NOT NULL,
"inserted" timestamp(6) NOT NULL
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."ws_sais_log_detail" IS 'täpne veateade või logi rida';
COMMENT ON COLUMN "public"."ws_sais_log_detail"."is_error" IS 'kas on viga või mitte true - jah false - ei';
COMMENT ON COLUMN "public"."ws_sais_log_detail"."ws_sais_log_id" IS 'viide päringule ';
COMMENT ON COLUMN "public"."ws_sais_log_detail"."log_txt" IS 'logi kirje, sh veateade';

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------

-- ----------------------------
-- Indexes structure for table apel_application
-- ----------------------------
CREATE INDEX "IXFK_apel_application_classifier" ON "public"."apel_application" USING btree ("status_code");
CREATE INDEX "IXFK_apel_application_school" ON "public"."apel_application" USING btree ("school_id");
CREATE INDEX "IXFK_apel_application_student" ON "public"."apel_application" USING btree ("student_id");

-- ----------------------------
-- Triggers structure for table apel_application
-- ----------------------------
CREATE TRIGGER "apel_application_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."apel_application"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table apel_application
-- ----------------------------
ALTER TABLE "public"."apel_application" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table apel_application_comment
-- ----------------------------
CREATE INDEX "IXFK_apel_application_comment_apel_application" ON "public"."apel_application_comment" USING btree ("apel_application_id");

-- ----------------------------
-- Triggers structure for table apel_application_comment
-- ----------------------------
CREATE TRIGGER "apel_application_comment_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."apel_application_comment"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table apel_application_comment
-- ----------------------------
ALTER TABLE "public"."apel_application_comment" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table apel_application_file
-- ----------------------------
CREATE INDEX "IXFK_apel_application_file_apel_application" ON "public"."apel_application_file" USING btree ("apel_application_id");
CREATE INDEX "IXFK_apel_application_file_ois_file" ON "public"."apel_application_file" USING btree ("ois_file_id");

-- ----------------------------
-- Triggers structure for table apel_application_file
-- ----------------------------
CREATE TRIGGER "apel_application_file_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."apel_application_file"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table apel_application_file
-- ----------------------------
ALTER TABLE "public"."apel_application_file" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table apel_application_formal_replaced_subject_or_module
-- ----------------------------
CREATE INDEX "IXFK_apel_application_formal_replaced_subject_apel_application_" ON "public"."apel_application_formal_replaced_subject_or_module" USING btree ("apel_application_record_id");
CREATE INDEX "IXFK_apel_application_formal_replaced_subject_curriculum_versio" ON "public"."apel_application_formal_replaced_subject_or_module" USING btree ("curriculum_version_omodule_id");
CREATE INDEX "IXFK_apel_application_formal_replaced_subject_subject" ON "public"."apel_application_formal_replaced_subject_or_module" USING btree ("subject_id");

-- ----------------------------
-- Primary Key structure for table apel_application_formal_replaced_subject_or_module
-- ----------------------------
ALTER TABLE "public"."apel_application_formal_replaced_subject_or_module" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table apel_application_formal_subject_or_module
-- ----------------------------
CREATE INDEX "IXFK_apel_application_formal_subject_apel_application_record" ON "public"."apel_application_formal_subject_or_module" USING btree ("apel_application_record_id");
CREATE INDEX "IXFK_apel_application_formal_subject_apel_school" ON "public"."apel_application_formal_subject_or_module" USING btree ("apel_school_id");
CREATE INDEX "IXFK_apel_application_formal_subject_classifier" ON "public"."apel_application_formal_subject_or_module" USING btree ("grade_code");
CREATE INDEX "IXFK_apel_application_formal_subject_classifier_02" ON "public"."apel_application_formal_subject_or_module" USING btree ("type_code");
CREATE INDEX "IXFK_apel_application_formal_subject_curriculum_version_omodule" ON "public"."apel_application_formal_subject_or_module" USING btree ("curriculum_version_omodule_id");
CREATE INDEX "IXFK_apel_application_formal_subject_subject" ON "public"."apel_application_formal_subject_or_module" USING btree ("subject_id");
CREATE INDEX "IXFK_apel_application_formal_subject_or_module_classifier" ON "public"."apel_application_formal_subject_or_module" USING btree ("assessment_code");
CREATE INDEX "IXFK_apel_application_formal_subject_or_module_curriculum_versi" ON "public"."apel_application_formal_subject_or_module" USING btree ("curriculum_version_hmodule_id");

-- ----------------------------
-- Primary Key structure for table apel_application_formal_subject_or_module
-- ----------------------------
ALTER TABLE "public"."apel_application_formal_subject_or_module" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table apel_application_informal_experience
-- ----------------------------
CREATE INDEX "IXFK_apel_application_informal_experience_apel_application_reco" ON "public"."apel_application_informal_experience" USING btree ("apel_application_record_id");
CREATE INDEX "IXFK_apel_application_informal_experience_classifier" ON "public"."apel_application_informal_experience" USING btree ("type_code");

-- ----------------------------
-- Triggers structure for table apel_application_informal_experience
-- ----------------------------
CREATE TRIGGER "apel_application_informal_experience_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."apel_application_informal_experience"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table apel_application_informal_experience
-- ----------------------------
ALTER TABLE "public"."apel_application_informal_experience" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table apel_application_informal_subject_or_module
-- ----------------------------
CREATE INDEX "IXFK_apel_application_informal_subject_apel_application_record" ON "public"."apel_application_informal_subject_or_module" USING btree ("apel_application_record_id");
CREATE INDEX "IXFK_apel_application_informal_subject_curriculum_version_hmodu" ON "public"."apel_application_informal_subject_or_module" USING btree ("curriculum_version_hmodule_id");
CREATE INDEX "IXFK_apel_application_informal_subject_or_module_classifier" ON "public"."apel_application_informal_subject_or_module" USING btree ("grade_code");
CREATE INDEX "IXFK_apel_application_informal_subject_or_module_curriculum_ve1" ON "public"."apel_application_informal_subject_or_module" USING btree ("curriculum_version_omodule_id");
CREATE INDEX "IXFK_apel_application_informal_subject_or_module_curriculum_ve2" ON "public"."apel_application_informal_subject_or_module" USING btree ("curriculum_version_omodule_theme_id");
CREATE INDEX "IXFK_apel_application_informal_subject_subject" ON "public"."apel_application_informal_subject_or_module" USING btree ("subject_id");

-- ----------------------------
-- Triggers structure for table apel_application_informal_subject_or_module
-- ----------------------------
CREATE TRIGGER "apel_application_informal_subject_or_module_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."apel_application_informal_subject_or_module"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table apel_application_informal_subject_or_module
-- ----------------------------
ALTER TABLE "public"."apel_application_informal_subject_or_module" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table apel_application_informal_subject_or_module_outcomes
-- ----------------------------
CREATE INDEX "IXFK_apel_application_informal_subject_or_module_outcomes_apel_" ON "public"."apel_application_informal_subject_or_module_outcomes" USING btree ("apel_application_informal_subject_or_module_id");
CREATE INDEX "IXFK_apel_application_informal_subject_or_module_outcomes_curri" ON "public"."apel_application_informal_subject_or_module_outcomes" USING btree ("curriculum_module_outcomes_id");

-- ----------------------------
-- Triggers structure for table apel_application_informal_subject_or_module_outcomes
-- ----------------------------
CREATE TRIGGER "apel_application_informal_subject_or_module_outcomes_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."apel_application_informal_subject_or_module_outcomes"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table apel_application_informal_subject_or_module_outcomes
-- ----------------------------
ALTER TABLE "public"."apel_application_informal_subject_or_module_outcomes" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table apel_application_record
-- ----------------------------
CREATE INDEX "IXFK_apel_application_record_apel_application" ON "public"."apel_application_record" USING btree ("apel_application_id");

-- ----------------------------
-- Triggers structure for table apel_application_record
-- ----------------------------
CREATE TRIGGER "apel_application_record_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."apel_application_record"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table apel_application_record
-- ----------------------------
ALTER TABLE "public"."apel_application_record" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table apel_school
-- ----------------------------
CREATE INDEX "IXFK_apel_school_classifier" ON "public"."apel_school" USING btree ("ehis_school_code");
CREATE INDEX "IXFK_apel_school_classifier_02" ON "public"."apel_school" USING btree ("country_code");
CREATE INDEX "IXFK_apel_school_school" ON "public"."apel_school" USING btree ("school_id");

-- ----------------------------
-- Uniques structure for table apel_school
-- ----------------------------
ALTER TABLE "public"."apel_school" ADD UNIQUE ("country_code", "ehis_school_code", "name_et");

-- ----------------------------
-- Primary Key structure for table apel_school
-- ----------------------------
ALTER TABLE "public"."apel_school" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table application
-- ----------------------------
CREATE INDEX "IXFK_application_classifier" ON "public"."application" USING btree ("type_code");
CREATE INDEX "IXFK_application_classifier_02" ON "public"."application" USING btree ("status_code");
CREATE INDEX "IXFK_application_classifier_03" ON "public"."application" USING btree ("reason_code");
CREATE INDEX "IXFK_application_classifier_04" ON "public"."application" USING btree ("old_study_form_code");
CREATE INDEX "IXFK_application_classifier_05" ON "public"."application" USING btree ("new_study_form_code");
CREATE INDEX "IXFK_application_classifier_06" ON "public"."application" USING btree ("old_fin_code");
CREATE INDEX "IXFK_application_classifier_07" ON "public"."application" USING btree ("new_fin_code");
CREATE INDEX "IXFK_application_classifier_08" ON "public"."application" USING btree ("old_fin_specific_code");
CREATE INDEX "IXFK_application_classifier_09" ON "public"."application" USING btree ("new_fin_specific_code");
CREATE INDEX "IXFK_application_classifier_10" ON "public"."application" USING btree ("country_code");
CREATE INDEX "IXFK_application_classifier_11" ON "public"."application" USING btree ("ehis_school_code");
CREATE INDEX "IXFK_application_classifier_12" ON "public"."application" USING btree ("abroad_purpose_code");
CREATE INDEX "IXFK_application_classifier_13" ON "public"."application" USING btree ("abroad_programme_code");
CREATE INDEX "IXFK_application_curriculum_version" ON "public"."application" USING btree ("old_curriculum_version_id");
CREATE INDEX "IXFK_application_curriculum_version_02" ON "public"."application" USING btree ("new_curriculum_version_id");
CREATE INDEX "IXFK_application_student" ON "public"."application" USING btree ("student_id");
CREATE INDEX "IXFK_application_study_period_end" ON "public"."application" USING btree ("study_period_end_id");
CREATE INDEX "IXFK_application_study_period_start" ON "public"."application" USING btree ("study_period_start_id");
CREATE INDEX "IXFK_application_academic_application" ON "public"."application" USING btree ("academic_application_id");

-- ----------------------------
-- Triggers structure for table application
-- ----------------------------
CREATE TRIGGER "application_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."application"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table application
-- ----------------------------
ALTER TABLE "public"."application" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table application_file
-- ----------------------------
CREATE INDEX "IXFK_application_file_application" ON "public"."application_file" USING btree ("application_id");
CREATE INDEX "IXFK_application_file_ois_file" ON "public"."application_file" USING btree ("ois_file_id");

-- ----------------------------
-- Triggers structure for table application_file
-- ----------------------------
CREATE TRIGGER "application_file_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."application_file"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table application_file
-- ----------------------------
ALTER TABLE "public"."application_file" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table application_planned_subject
-- ----------------------------
CREATE INDEX "IXFK_application_planned_subject_application" ON "public"."application_planned_subject" USING btree ("application_id");

-- ----------------------------
-- Triggers structure for table application_planned_subject
-- ----------------------------
CREATE TRIGGER "application_planned_subject_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."application_planned_subject"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table application_planned_subject
-- ----------------------------
ALTER TABLE "public"."application_planned_subject" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table application_planned_subject_equivalent
-- ----------------------------
CREATE INDEX "IXFK_application_planned_subject_equivalent _application_planne" ON "public"."application_planned_subject_equivalent" USING btree ("application_planned_subject_id");
CREATE INDEX "IXFK_application_planned_subject_equivalent _subject" ON "public"."application_planned_subject_equivalent" USING btree ("subject_id");

-- ----------------------------
-- Triggers structure for table application_planned_subject_equivalent
-- ----------------------------
CREATE TRIGGER "application_planned_subject_equivalent_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."application_planned_subject_equivalent"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table application_planned_subject_equivalent
-- ----------------------------
ALTER TABLE "public"."application_planned_subject_equivalent" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table building
-- ----------------------------
CREATE INDEX "IXFK_building_school" ON "public"."building" USING btree ("school_id");

-- ----------------------------
-- Triggers structure for table building
-- ----------------------------
CREATE TRIGGER "building_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."building"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table building
-- ----------------------------
ALTER TABLE "public"."building" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table certificate
-- ----------------------------
CREATE INDEX "IXFK_certificate_classifier" ON "public"."certificate" USING btree ("type_code");
CREATE INDEX "IXFK_certificate_classifier_02" ON "public"."certificate" USING btree ("status_code");
CREATE INDEX "IXFK_certificate_school" ON "public"."certificate" USING btree ("school_id");
CREATE INDEX "IXFK_certificate_student" ON "public"."certificate" USING btree ("student_id");

-- ----------------------------
-- Triggers structure for table certificate
-- ----------------------------
CREATE TRIGGER "certificate_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."certificate"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table certificate
-- ----------------------------
ALTER TABLE "public"."certificate" ADD PRIMARY KEY ("id");

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
-- Indexes structure for table committee
-- ----------------------------
CREATE INDEX "IXFK_committee_school" ON "public"."committee" USING btree ("school_id");

-- ----------------------------
-- Triggers structure for table committee
-- ----------------------------
CREATE TRIGGER "committee_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."committee"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table committee
-- ----------------------------
ALTER TABLE "public"."committee" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table committee_member
-- ----------------------------
CREATE INDEX "IXFK_committee_member_committee" ON "public"."committee_member" USING btree ("committee_id");
CREATE INDEX "IXFK_committee_member_teacher" ON "public"."committee_member" USING btree ("teacher_id");

-- ----------------------------
-- Triggers structure for table committee_member
-- ----------------------------
CREATE TRIGGER "committee_member_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."committee_member"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table committee_member
-- ----------------------------
ALTER TABLE "public"."committee_member" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table contract
-- ----------------------------
CREATE INDEX "IXFK_contract_classifier" ON "public"."contract" USING btree ("status_code");
CREATE INDEX "IXFK_contract_curriculum_version_omodule" ON "public"."contract" USING btree ("curriculum_version_omodule_id");
CREATE INDEX "IXFK_contract_curriculum_version_omodule_theme" ON "public"."contract" USING btree ("curriculum_version_omodule_theme_id");
CREATE INDEX "IXFK_contract_directive_coordinator" ON "public"."contract" USING btree ("contract_coordinator_id");
CREATE INDEX "IXFK_contract_enterprise" ON "public"."contract" USING btree ("enterprise_id");
CREATE INDEX "IXFK_contract_student" ON "public"."contract" USING btree ("student_id");
CREATE INDEX "IXFK_contract_teacher" ON "public"."contract" USING btree ("teacher_id");
CREATE INDEX "IXFK_contract_subject" ON "public"."contract" USING btree ("subject_id");

-- ----------------------------
-- Triggers structure for table contract
-- ----------------------------
CREATE TRIGGER "contract_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."contract"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table contract
-- ----------------------------
ALTER TABLE "public"."contract" ADD PRIMARY KEY ("id");

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
-- Triggers structure for table curriculum
-- ----------------------------
CREATE TRIGGER "curriculum_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."curriculum"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

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
-- Triggers structure for table curriculum_department
-- ----------------------------
CREATE TRIGGER "curriculum_department_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."curriculum_department"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

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
-- Triggers structure for table curriculum_files
-- ----------------------------
CREATE TRIGGER "curriculum_files_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."curriculum_files"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

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
-- Triggers structure for table curriculum_grade
-- ----------------------------
CREATE TRIGGER "curriculum_grade_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."curriculum_grade"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

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
-- Triggers structure for table curriculum_joint_partners
-- ----------------------------
CREATE TRIGGER "curriculum_joint_partners_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."curriculum_joint_partners"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

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
-- Triggers structure for table curriculum_module
-- ----------------------------
CREATE TRIGGER "curriculum_module_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."curriculum_module"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

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
-- Triggers structure for table curriculum_module_competence
-- ----------------------------
CREATE TRIGGER "curriculum_module_competence_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."curriculum_module_competence"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

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
-- Triggers structure for table curriculum_module_occupation
-- ----------------------------
CREATE TRIGGER "curriculum_module_occupation_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."curriculum_module_occupation"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Uniques structure for table curriculum_module_occupation
-- ----------------------------
ALTER TABLE "public"."curriculum_module_occupation" ADD UNIQUE ("curriculum_module_id", "occupation_code");

-- ----------------------------
-- Primary Key structure for table curriculum_module_occupation
-- ----------------------------
ALTER TABLE "public"."curriculum_module_occupation" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table curriculum_module_outcomes
-- ----------------------------
CREATE INDEX "IXFK_curriculum_module_outcomes_curriculum_module" ON "public"."curriculum_module_outcomes" USING btree ("curriculum_module_id");

-- ----------------------------
-- Triggers structure for table curriculum_module_outcomes
-- ----------------------------
CREATE TRIGGER "curriculum_module_outcomes_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."curriculum_module_outcomes"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table curriculum_module_outcomes
-- ----------------------------
ALTER TABLE "public"."curriculum_module_outcomes" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table curriculum_occupation
-- ----------------------------
CREATE INDEX "IXFK_curriculum_occupation_classifier" ON "public"."curriculum_occupation" USING btree ("occupation_code");
CREATE INDEX "IXFK_curriculum_occupation_curriculum" ON "public"."curriculum_occupation" USING btree ("curriculum_id");

-- ----------------------------
-- Triggers structure for table curriculum_occupation
-- ----------------------------
CREATE TRIGGER "curriculum_occupation_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."curriculum_occupation"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Uniques structure for table curriculum_occupation
-- ----------------------------
ALTER TABLE "public"."curriculum_occupation" ADD UNIQUE ("curriculum_id", "occupation_code");

-- ----------------------------
-- Primary Key structure for table curriculum_occupation
-- ----------------------------
ALTER TABLE "public"."curriculum_occupation" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table curriculum_occupation_speciality
-- ----------------------------
CREATE INDEX "IXFK_curriculum_occupation_speciality_classifier" ON "public"."curriculum_occupation_speciality" USING btree ("speciality_code");
CREATE INDEX "IXFK_curriculum_occupation_speciality_curriculum_occupation" ON "public"."curriculum_occupation_speciality" USING btree ("curriculum_occupation_id");

-- ----------------------------
-- Triggers structure for table curriculum_occupation_speciality
-- ----------------------------
CREATE TRIGGER "curriculum_occupation_speciality_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."curriculum_occupation_speciality"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table curriculum_occupation_speciality
-- ----------------------------
ALTER TABLE "public"."curriculum_occupation_speciality" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table curriculum_speciality
-- ----------------------------
CREATE INDEX "IXFK_curriculum_speciality_classifier" ON "public"."curriculum_speciality" USING btree ("occupation_code");
CREATE INDEX "IXFK_curriculum_speciality_curriculum_grade" ON "public"."curriculum_speciality" USING btree ("curriculum_id");

-- ----------------------------
-- Triggers structure for table curriculum_speciality
-- ----------------------------
CREATE TRIGGER "curriculum_speciality_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."curriculum_speciality"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

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
-- Triggers structure for table curriculum_study_form
-- ----------------------------
CREATE TRIGGER "curriculum_study_form_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."curriculum_study_form"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

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
-- Triggers structure for table curriculum_study_lang
-- ----------------------------
CREATE TRIGGER "curriculum_study_lang_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."curriculum_study_lang"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Uniques structure for table curriculum_study_lang
-- ----------------------------
ALTER TABLE "public"."curriculum_study_lang" ADD UNIQUE ("curriculum_id", "study_lang_code");

-- ----------------------------
-- Primary Key structure for table curriculum_study_lang
-- ----------------------------
ALTER TABLE "public"."curriculum_study_lang" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table curriculum_version
-- ----------------------------
CREATE INDEX "IXFK_curriculum_version_curriculum" ON "public"."curriculum_version" USING btree ("curriculum_id");
CREATE INDEX "IXFK_curriculum_version_classifier" ON "public"."curriculum_version" USING btree ("type_code");
CREATE INDEX "IXFK_curriculum_version_classifier_02" ON "public"."curriculum_version" USING btree ("status_code");
CREATE INDEX "IXFK_curriculum_version_curriculum_study_form" ON "public"."curriculum_version" USING btree ("curriculum_study_form_id");
CREATE INDEX "IXFK_curriculum_version_school_department" ON "public"."curriculum_version" USING btree ("school_department_id");

-- ----------------------------
-- Triggers structure for table curriculum_version
-- ----------------------------
CREATE TRIGGER "curriculum_version_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."curriculum_version"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table curriculum_version
-- ----------------------------
ALTER TABLE "public"."curriculum_version" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table curriculum_version_elective_module
-- ----------------------------
CREATE INDEX "IXFK_curriculum_version_elective_module_curriculum_version_hmod" ON "public"."curriculum_version_elective_module" USING btree ("curriculum_version_hmodule_id");

-- ----------------------------
-- Triggers structure for table curriculum_version_elective_module
-- ----------------------------
CREATE TRIGGER "curriculum_version_elective_module_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."curriculum_version_elective_module"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table curriculum_version_elective_module
-- ----------------------------
ALTER TABLE "public"."curriculum_version_elective_module" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table curriculum_version_hmodule
-- ----------------------------
CREATE INDEX "IXFK_curriculum_version_hmodule_classifier" ON "public"."curriculum_version_hmodule" USING btree ("type_code");
CREATE INDEX "IXFK_curriculum_version_hmodule_curriculum_version" ON "public"."curriculum_version_hmodule" USING btree ("curriculum_version_id");

-- ----------------------------
-- Triggers structure for table curriculum_version_hmodule
-- ----------------------------
CREATE TRIGGER "curriculum_version_hmodule_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."curriculum_version_hmodule"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table curriculum_version_hmodule
-- ----------------------------
ALTER TABLE "public"."curriculum_version_hmodule" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table curriculum_version_hmodule_speciality
-- ----------------------------
CREATE INDEX "IXFK_curriculum_version_hmodule_speciality_curriculum_version_h" ON "public"."curriculum_version_hmodule_speciality" USING btree ("curriculum_version_hmodule_id");
CREATE INDEX "IXFK_curriculum_version_hmodule_speciality_curriculum_version_s" ON "public"."curriculum_version_hmodule_speciality" USING btree ("curriculum_version_speciality_id");

-- ----------------------------
-- Triggers structure for table curriculum_version_hmodule_speciality
-- ----------------------------
CREATE TRIGGER "curriculum_version_hmodule_speciality_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."curriculum_version_hmodule_speciality"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table curriculum_version_hmodule_speciality
-- ----------------------------
ALTER TABLE "public"."curriculum_version_hmodule_speciality" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table curriculum_version_hmodule_subject
-- ----------------------------
CREATE INDEX "IXFK_curriculum_version_hmodule_subject_curriculum_version_elec" ON "public"."curriculum_version_hmodule_subject" USING btree ("curriculum_version_elective_module_id");
CREATE INDEX "IXFK_curriculum_version_hmodule_subject_curriculum_version_hmod" ON "public"."curriculum_version_hmodule_subject" USING btree ("curriculum_version_hmodule_id");
CREATE INDEX "IXFK_curriculum_version_hmodule_subject_subject" ON "public"."curriculum_version_hmodule_subject" USING btree ("subject_id");

-- ----------------------------
-- Triggers structure for table curriculum_version_hmodule_subject
-- ----------------------------
CREATE TRIGGER "curriculum_version_hmodule_subject_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."curriculum_version_hmodule_subject"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table curriculum_version_hmodule_subject
-- ----------------------------
ALTER TABLE "public"."curriculum_version_hmodule_subject" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table curriculum_version_omodule
-- ----------------------------
CREATE INDEX "IXFK_curriculum_version_omodule_classifier" ON "public"."curriculum_version_omodule" USING btree ("assessment_code");
CREATE INDEX "IXFK_curriculum_version_omodule_curriculum_module" ON "public"."curriculum_version_omodule" USING btree ("curriculum_module_id");
CREATE INDEX "IXFK_curriculum_version_omodule_curriculum_version" ON "public"."curriculum_version_omodule" USING btree ("curriculum_version_id");

-- ----------------------------
-- Triggers structure for table curriculum_version_omodule
-- ----------------------------
CREATE TRIGGER "curriculum_version_omodule_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."curriculum_version_omodule"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table curriculum_version_omodule
-- ----------------------------
ALTER TABLE "public"."curriculum_version_omodule" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table curriculum_version_omodule_capacity
-- ----------------------------
CREATE INDEX "IXFK_curriculum_version_omodule_capacity_classifier" ON "public"."curriculum_version_omodule_capacity" USING btree ("capacity_type_code");
CREATE INDEX "IXFK_curriculum_version_omodule_capacity_curriculum_version_omo" ON "public"."curriculum_version_omodule_capacity" USING btree ("curriculum_version_omodule_id");

-- ----------------------------
-- Triggers structure for table curriculum_version_omodule_capacity
-- ----------------------------
CREATE TRIGGER "curriculum_version_omodule_capacity_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."curriculum_version_omodule_capacity"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table curriculum_version_omodule_capacity
-- ----------------------------
ALTER TABLE "public"."curriculum_version_omodule_capacity" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table curriculum_version_omodule_outcomes
-- ----------------------------
CREATE INDEX "IXFK_curriculum_version_omodule_outcomes_curriculum_module_outc" ON "public"."curriculum_version_omodule_outcomes" USING btree ("curriculum_module_outcomes_id");
CREATE INDEX "IXFK_curriculum_version_omodule_outcomes_curriculum_version_omo" ON "public"."curriculum_version_omodule_outcomes" USING btree ("curriculum_version_omodule_theme_id");

-- ----------------------------
-- Triggers structure for table curriculum_version_omodule_outcomes
-- ----------------------------
CREATE TRIGGER "curriculum_version_omodule_outcomes_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."curriculum_version_omodule_outcomes"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table curriculum_version_omodule_outcomes
-- ----------------------------
ALTER TABLE "public"."curriculum_version_omodule_outcomes" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table curriculum_version_omodule_theme
-- ----------------------------
CREATE INDEX "IXFK_curriculum_version_omodule_theme_curriculum_version_omodul" ON "public"."curriculum_version_omodule_theme" USING btree ("curriculum_version_omodule_id");
CREATE INDEX "IXFK_curriculum_version_omodule_theme_classifier" ON "public"."curriculum_version_omodule_theme" USING btree ("assessment_code");

-- ----------------------------
-- Triggers structure for table curriculum_version_omodule_theme
-- ----------------------------
CREATE TRIGGER "curriculum_version_omodule_theme_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."curriculum_version_omodule_theme"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table curriculum_version_omodule_theme
-- ----------------------------
ALTER TABLE "public"."curriculum_version_omodule_theme" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table curriculum_version_omodule_theme_capacity
-- ----------------------------
CREATE INDEX "IXFK_curriculum_version_omodule_theme_capacity_classifier" ON "public"."curriculum_version_omodule_theme_capacity" USING btree ("capacity_type_code");
CREATE INDEX "IXFK_curriculum_version_omodule_theme_capacity_curriculum_versi" ON "public"."curriculum_version_omodule_theme_capacity" USING btree ("curriculum_version_omodule_theme_id");

-- ----------------------------
-- Triggers structure for table curriculum_version_omodule_theme_capacity
-- ----------------------------
CREATE TRIGGER "curriculum_version_omodule_theme_capacity_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."curriculum_version_omodule_theme_capacity"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table curriculum_version_omodule_theme_capacity
-- ----------------------------
ALTER TABLE "public"."curriculum_version_omodule_theme_capacity" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table curriculum_version_omodule_year_capacity
-- ----------------------------
CREATE INDEX "IXFK_curriculum_version_omodule_year_capacity_curriculum_versio" ON "public"."curriculum_version_omodule_year_capacity" USING btree ("curriculum_version_omodule_id");

-- ----------------------------
-- Triggers structure for table curriculum_version_omodule_year_capacity
-- ----------------------------
CREATE TRIGGER "curriculum_version_omodule_year_capacity_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."curriculum_version_omodule_year_capacity"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table curriculum_version_omodule_year_capacity
-- ----------------------------
ALTER TABLE "public"."curriculum_version_omodule_year_capacity" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table curriculum_version_speciality
-- ----------------------------
CREATE INDEX "IXFK_curriculum_version_speciality_curriculum_speciality" ON "public"."curriculum_version_speciality" USING btree ("curriculum_speciality_id");
CREATE INDEX "IXFK_curriculum_version_speciality_curriculum_version" ON "public"."curriculum_version_speciality" USING btree ("curriculum_version_id");

-- ----------------------------
-- Triggers structure for table curriculum_version_speciality
-- ----------------------------
CREATE TRIGGER "curriculum_version_speciality_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."curriculum_version_speciality"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table curriculum_version_speciality
-- ----------------------------
ALTER TABLE "public"."curriculum_version_speciality" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table declaration
-- ----------------------------
CREATE INDEX "IXFK_declaration_classifier" ON "public"."declaration" USING btree ("status_code");
CREATE INDEX "IXFK_declaration_student" ON "public"."declaration" USING btree ("student_id");
CREATE INDEX "IXFK_declaration_study_period" ON "public"."declaration" USING btree ("study_period_id");

-- ----------------------------
-- Triggers structure for table declaration
-- ----------------------------
CREATE TRIGGER "declaration_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."declaration"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table declaration
-- ----------------------------
ALTER TABLE "public"."declaration" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table declaration_subject
-- ----------------------------
CREATE INDEX "IXFK_declaration_subject_curriculum_version_hmodule" ON "public"."declaration_subject" USING btree ("curriculum_version_hmodule_id");
CREATE INDEX "IXFK_declaration_subject_declaration" ON "public"."declaration_subject" USING btree ("declaration_id");
CREATE INDEX "IXFK_declaration_subject_subject_study_period" ON "public"."declaration_subject" USING btree ("subject_study_period_id");

-- ----------------------------
-- Triggers structure for table declaration_subject
-- ----------------------------
CREATE TRIGGER "declaration_subject_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."declaration_subject"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table declaration_subject
-- ----------------------------
ALTER TABLE "public"."declaration_subject" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table directive
-- ----------------------------
CREATE INDEX "IXFK_directive_classifier" ON "public"."directive" USING btree ("type_code");
CREATE INDEX "IXFK_directive_classifier_02" ON "public"."directive" USING btree ("status_code");
CREATE INDEX "IXFK_directive_classifier_03" ON "public"."directive" USING btree ("cancel_type_code");
CREATE INDEX "IXFK_directive_directive" ON "public"."directive" USING btree ("canceled_directive_id");
CREATE INDEX "IXFK_directive_directive_coordinator" ON "public"."directive" USING btree ("directive_coordinator_id");
CREATE INDEX "IXFK_directive_school" ON "public"."directive" USING btree ("school_id");

-- ----------------------------
-- Triggers structure for table directive
-- ----------------------------
CREATE TRIGGER "directive_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."directive"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table directive
-- ----------------------------
ALTER TABLE "public"."directive" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table directive_coordinator
-- ----------------------------
CREATE INDEX "IXFK_directive_coordinator_school" ON "public"."directive_coordinator" USING btree ("school_id");

-- ----------------------------
-- Triggers structure for table directive_coordinator
-- ----------------------------
CREATE TRIGGER "directive_coordinator_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."directive_coordinator"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Uniques structure for table directive_coordinator
-- ----------------------------
ALTER TABLE "public"."directive_coordinator" ADD UNIQUE ("idcode", "school_id");

-- ----------------------------
-- Primary Key structure for table directive_coordinator
-- ----------------------------
ALTER TABLE "public"."directive_coordinator" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table directive_student
-- ----------------------------
CREATE INDEX "IXFK_directive_student_classifier" ON "public"."directive_student" USING btree ("reason_code");
CREATE INDEX "IXFK_directive_student_classifier_02" ON "public"."directive_student" USING btree ("study_load_code");
CREATE INDEX "IXFK_directive_student_classifier_03" ON "public"."directive_student" USING btree ("study_form_code");
CREATE INDEX "IXFK_directive_student_classifier_04" ON "public"."directive_student" USING btree ("fin_code");
CREATE INDEX "IXFK_directive_student_classifier_05" ON "public"."directive_student" USING btree ("fin_specific_code");
CREATE INDEX "IXFK_directive_student_classifier_06" ON "public"."directive_student" USING btree ("language_code");
CREATE INDEX "IXFK_directive_student_classifier_07" ON "public"."directive_student" USING btree ("country_code");
CREATE INDEX "IXFK_directive_student_classifier_08" ON "public"."directive_student" USING btree ("ehis_school_code");
CREATE INDEX "IXFK_directive_student_classifier_09" ON "public"."directive_student" USING btree ("abroad_purpose_code");
CREATE INDEX "IXFK_directive_student_classifier_10" ON "public"."directive_student" USING btree ("abroad_programme_code");
CREATE INDEX "IXFK_directive_student_classifier_11" ON "public"."directive_student" USING btree ("previous_study_level_code");
CREATE INDEX "IXFK_directive_student_classifier_12" ON "public"."directive_student" USING btree ("state_language_ects_code");
CREATE INDEX "IXFK_directive_student_curriculum_grade" ON "public"."directive_student" USING btree ("curriculum_grade_id");
CREATE INDEX "IXFK_directive_student_curriculum_version" ON "public"."directive_student" USING btree ("curriculum_version_id");
CREATE INDEX "IXFK_directive_student_directive" ON "public"."directive_student" USING btree ("directive_id");
CREATE INDEX "IXFK_directive_student_period" ON "public"."directive_student" USING btree ("study_period_start_id");
CREATE INDEX "IXFK_directive_student_period_02" ON "public"."directive_student" USING btree ("study_period_end_id");
CREATE INDEX "IXFK_directive_student_student" ON "public"."directive_student" USING btree ("student_id");
CREATE INDEX "IXFK_directive_student_student_group" ON "public"."directive_student" USING btree ("student_group_id");
CREATE INDEX "IXFK_directive_student_application" ON "public"."directive_student" USING btree ("application_id");
CREATE INDEX "IXFK_directive_student_person" ON "public"."directive_student" USING btree ("person_id");
CREATE INDEX "IXFK_directive_student_history" ON "public"."directive_student" USING btree ("student_history_id");
CREATE INDEX "IXFK_directive_student_sais_application" ON "public"."directive_student" USING btree ("sais_application_id");

-- ----------------------------
-- Triggers structure for table directive_student
-- ----------------------------
CREATE TRIGGER "directive_student_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."directive_student"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Uniques structure for table directive_student
-- ----------------------------
ALTER TABLE "public"."directive_student" ADD UNIQUE ("directive_id", "student_id");

-- ----------------------------
-- Primary Key structure for table directive_student
-- ----------------------------
ALTER TABLE "public"."directive_student" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Triggers structure for table enterprise
-- ----------------------------
CREATE TRIGGER "enterprise_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."enterprise"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table enterprise
-- ----------------------------
ALTER TABLE "public"."enterprise" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table general_message
-- ----------------------------
CREATE INDEX "IXFK_general_message_school" ON "public"."general_message" USING btree ("school_id");

-- ----------------------------
-- Triggers structure for table general_message
-- ----------------------------
CREATE TRIGGER "general_message_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."general_message"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table general_message
-- ----------------------------
ALTER TABLE "public"."general_message" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table general_message_target
-- ----------------------------
CREATE INDEX "IXFK_general_message_target_classifier" ON "public"."general_message_target" USING btree ("role_code");
CREATE INDEX "IXFK_general_message_target_general_message" ON "public"."general_message_target" USING btree ("general_message_id");

-- ----------------------------
-- Triggers structure for table general_message_target
-- ----------------------------
CREATE TRIGGER "general_message_target_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."general_message_target"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table general_message_target
-- ----------------------------
ALTER TABLE "public"."general_message_target" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table job
-- ----------------------------
CREATE INDEX "IXFK_job_classifier" ON "public"."job" USING btree ("type_code");
CREATE INDEX "IXFK_job_classifier_02" ON "public"."job" USING btree ("status_code");
CREATE INDEX "IXFK_job_directive" ON "public"."job" USING btree ("directive_id");
CREATE INDEX "IXFK_job_school" ON "public"."job" USING btree ("school_id");
CREATE INDEX "IXFK_job_student" ON "public"."job" USING btree ("student_id");
CREATE INDEX "ix_job_directive_id" ON "public"."job" USING btree ("directive_id");
CREATE INDEX "ix_job_student_id" ON "public"."job" USING btree ("student_id");
CREATE INDEX "ix_job_time_status" ON "public"."job" USING btree ("job_time", "status_code") WHERE status_code::text = 'JOB_STATUS_VALMIS'::text;
CREATE INDEX "IXFK_job_contract" ON "public"."job" USING btree ("contract_id");

-- ----------------------------
-- Primary Key structure for table job
-- ----------------------------
ALTER TABLE "public"."job" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table journal
-- ----------------------------
CREATE INDEX "IXFK_journal_classifier" ON "public"."journal" USING btree ("assessment_code");
CREATE INDEX "IXFK_journal_classifier_02" ON "public"."journal" USING btree ("group_proportion_code");
CREATE INDEX "IXFK_journal_classifier_03" ON "public"."journal" USING btree ("status_code");
CREATE INDEX "IXFK_journal_school" ON "public"."journal" USING btree ("school_id");
CREATE INDEX "IXFK_journal_study_year" ON "public"."journal" USING btree ("study_year_id");

-- ----------------------------
-- Triggers structure for table journal
-- ----------------------------
CREATE TRIGGER "journal_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."journal"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table journal
-- ----------------------------
ALTER TABLE "public"."journal" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table journal_capacity
-- ----------------------------
CREATE INDEX "IXFK_journal_capacity_journal" ON "public"."journal_capacity" USING btree ("journal_id");
CREATE INDEX "IXFK_journal_capacity_journal_capacity_type" ON "public"."journal_capacity" USING btree ("journal_capacity_type_id");
CREATE INDEX "IXFK_journal_capacity_study_period" ON "public"."journal_capacity" USING btree ("study_period_id");

-- ----------------------------
-- Triggers structure for table journal_capacity
-- ----------------------------
CREATE TRIGGER "journal_capacity_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."journal_capacity"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table journal_capacity
-- ----------------------------
ALTER TABLE "public"."journal_capacity" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table journal_capacity_type
-- ----------------------------
CREATE INDEX "IXFK_journal_capacity_type_classifier" ON "public"."journal_capacity_type" USING btree ("capacity_type_code");
CREATE INDEX "IXFK_journal_capacity_type_journal" ON "public"."journal_capacity_type" USING btree ("journal_id");

-- ----------------------------
-- Triggers structure for table journal_capacity_type
-- ----------------------------
CREATE TRIGGER "journal_capacity_type_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."journal_capacity_type"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table journal_capacity_type
-- ----------------------------
ALTER TABLE "public"."journal_capacity_type" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table journal_entry
-- ----------------------------
CREATE INDEX "IXFK_journal_entry_classifier" ON "public"."journal_entry" USING btree ("entry_type_code");
CREATE INDEX "IXFK_journal_entry_journal" ON "public"."journal_entry" USING btree ("journal_id");

-- ----------------------------
-- Triggers structure for table journal_entry
-- ----------------------------
CREATE TRIGGER "journal_entry_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."journal_entry"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table journal_entry
-- ----------------------------
ALTER TABLE "public"."journal_entry" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table journal_entry_capacity_type
-- ----------------------------
CREATE INDEX "IXFK_journal_entry_capacity_type_classifier" ON "public"."journal_entry_capacity_type" USING btree ("capacity_type_code");
CREATE INDEX "IXFK_journal_entry_capacity_type_journal_entry" ON "public"."journal_entry_capacity_type" USING btree ("journal_entry_id");

-- ----------------------------
-- Triggers structure for table journal_entry_capacity_type
-- ----------------------------
CREATE TRIGGER "journal_entry_capacity_type_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."journal_entry_capacity_type"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table journal_entry_capacity_type
-- ----------------------------
ALTER TABLE "public"."journal_entry_capacity_type" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table journal_entry_student
-- ----------------------------
CREATE INDEX "IXFK_journal_entry_student_classifier" ON "public"."journal_entry_student" USING btree ("absence_code");
CREATE INDEX "IXFK_journal_entry_student_classifier_02" ON "public"."journal_entry_student" USING btree ("grade_code");
CREATE INDEX "IXFK_journal_entry_student_journal_entry" ON "public"."journal_entry_student" USING btree ("journal_entry_id");
CREATE INDEX "IXFK_journal_entry_student_journal_student" ON "public"."journal_entry_student" USING btree ("journal_student_id");

-- ----------------------------
-- Triggers structure for table journal_entry_student
-- ----------------------------
CREATE TRIGGER "journal_entry_student_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."journal_entry_student"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table journal_entry_student
-- ----------------------------
ALTER TABLE "public"."journal_entry_student" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table journal_entry_student_history
-- ----------------------------
CREATE INDEX "IXFK_journal_entry_student_history_classifier" ON "public"."journal_entry_student_history" USING btree ("grade_code");
CREATE INDEX "IXFK_journal_entry_student_history_journal_entry_student" ON "public"."journal_entry_student_history" USING btree ("journal_entry_student_id");

-- ----------------------------
-- Triggers structure for table journal_entry_student_history
-- ----------------------------
CREATE TRIGGER "journal_entry_student_history_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."journal_entry_student_history"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table journal_entry_student_history
-- ----------------------------
ALTER TABLE "public"."journal_entry_student_history" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table journal_omodule_theme
-- ----------------------------
CREATE INDEX "IXFK_journal_omodule_theme_curriculum_version_omodule_theme" ON "public"."journal_omodule_theme" USING btree ("curriculum_version_omodule_theme_id");
CREATE INDEX "IXFK_journal_omodule_theme_journal" ON "public"."journal_omodule_theme" USING btree ("journal_id");
CREATE INDEX "IXFK_journal_omodule_theme_lesson_plan_module" ON "public"."journal_omodule_theme" USING btree ("lesson_plan_module_id");

-- ----------------------------
-- Triggers structure for table journal_omodule_theme
-- ----------------------------
CREATE TRIGGER "journal_omodule_theme_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."journal_omodule_theme"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table journal_omodule_theme
-- ----------------------------
ALTER TABLE "public"."journal_omodule_theme" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table journal_room
-- ----------------------------
CREATE INDEX "IXFK_journal_room_journal" ON "public"."journal_room" USING btree ("journal_id");
CREATE INDEX "IXFK_journal_room_room" ON "public"."journal_room" USING btree ("room_id");

-- ----------------------------
-- Triggers structure for table journal_room
-- ----------------------------
CREATE TRIGGER "journal_room_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."journal_room"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table journal_room
-- ----------------------------
ALTER TABLE "public"."journal_room" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table journal_student
-- ----------------------------
CREATE INDEX "IXFK_journal_student_journal" ON "public"."journal_student" USING btree ("journal_id");
CREATE INDEX "IXFK_journal_student_student" ON "public"."journal_student" USING btree ("student_id");

-- ----------------------------
-- Triggers structure for table journal_student
-- ----------------------------
CREATE TRIGGER "journal_student_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."journal_student"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table journal_student
-- ----------------------------
ALTER TABLE "public"."journal_student" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table journal_teacher
-- ----------------------------
CREATE INDEX "IXFK_journal_teacher_journal" ON "public"."journal_teacher" USING btree ("journal_id");
CREATE INDEX "IXFK_journal_teacher_teacher" ON "public"."journal_teacher" USING btree ("teacher_id");
CREATE UNIQUE INDEX "UQ_journal_teacher" ON "public"."journal_teacher" USING btree ("journal_id", "teacher_id");

-- ----------------------------
-- Triggers structure for table journal_teacher
-- ----------------------------
CREATE TRIGGER "journal_teacher_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."journal_teacher"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Uniques structure for table journal_teacher
-- ----------------------------
ALTER TABLE "public"."journal_teacher" ADD UNIQUE ("journal_id", "teacher_id");

-- ----------------------------
-- Primary Key structure for table journal_teacher
-- ----------------------------
ALTER TABLE "public"."journal_teacher" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table lesson_plan
-- ----------------------------
CREATE INDEX "IXFK_lesson_distribution_plan_curriculum_version" ON "public"."lesson_plan" USING btree ("curriculum_version_id");
CREATE INDEX "IXFK_lesson_distribution_plan_school" ON "public"."lesson_plan" USING btree ("school_id");
CREATE INDEX "IXFK_lesson_distribution_plan_student_group" ON "public"."lesson_plan" USING btree ("student_group_id");
CREATE INDEX "IXFK_lesson_distribution_plan_study_year" ON "public"."lesson_plan" USING btree ("study_year_id");

-- ----------------------------
-- Triggers structure for table lesson_plan
-- ----------------------------
CREATE TRIGGER "lesson_plan_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."lesson_plan"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table lesson_plan
-- ----------------------------
ALTER TABLE "public"."lesson_plan" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table lesson_plan_module
-- ----------------------------
CREATE INDEX "IXFK_lesson_distribution_plan_module_lesson_distribution_plan" ON "public"."lesson_plan_module" USING btree ("lesson_plan_id");
CREATE INDEX "IXFK_lesson_plan_module_curriculum_version_omodule" ON "public"."lesson_plan_module" USING btree ("curriculum_version_omodule_id");
CREATE INDEX "IXFK_lesson_plan_module_teacher" ON "public"."lesson_plan_module" USING btree ("teacher_id");

-- ----------------------------
-- Triggers structure for table lesson_plan_module
-- ----------------------------
CREATE TRIGGER "lesson_plan_module_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."lesson_plan_module"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table lesson_plan_module
-- ----------------------------
ALTER TABLE "public"."lesson_plan_module" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table lesson_time
-- ----------------------------
CREATE INDEX "IXFK_lesson_time_lesson_time_building_group" ON "public"."lesson_time" USING btree ("lesson_time_building_group_id");
CREATE INDEX "IXFK_lesson_time_school" ON "public"."lesson_time" USING btree ("school_id");

-- ----------------------------
-- Triggers structure for table lesson_time
-- ----------------------------
CREATE TRIGGER "lesson_time_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."lesson_time"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table lesson_time
-- ----------------------------
ALTER TABLE "public"."lesson_time" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table lesson_time_building
-- ----------------------------
CREATE INDEX "IXFK_lesson_time_building_lesson_time_building_group" ON "public"."lesson_time_building" USING btree ("lesson_time_building_group_id");
CREATE INDEX "IXFK_lesson_day_building_building" ON "public"."lesson_time_building" USING btree ("building_id");

-- ----------------------------
-- Triggers structure for table lesson_time_building
-- ----------------------------
CREATE TRIGGER "lesson_time_building_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."lesson_time_building"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table lesson_time_building
-- ----------------------------
ALTER TABLE "public"."lesson_time_building" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Triggers structure for table lesson_time_building_group
-- ----------------------------
CREATE TRIGGER "lesson_time_building_group_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."lesson_time_building_group"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table lesson_time_building_group
-- ----------------------------
ALTER TABLE "public"."lesson_time_building_group" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table log_table_data
-- ----------------------------
ALTER TABLE "public"."log_table_data" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table message
-- ----------------------------
CREATE INDEX "IXFK_message_classifier" ON "public"."message" USING btree ("role_code");
CREATE INDEX "IXFK_message_message" ON "public"."message" USING btree ("message_id");
CREATE INDEX "IXFK_message_person" ON "public"."message" USING btree ("person_id");
CREATE INDEX "IXFK_message_school" ON "public"."message" USING btree ("school_id");

-- ----------------------------
-- Triggers structure for table message
-- ----------------------------
CREATE TRIGGER "message_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."message"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table message
-- ----------------------------
ALTER TABLE "public"."message" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table message_receiver
-- ----------------------------
CREATE INDEX "IXFK_message_receiver_classifier" ON "public"."message_receiver" USING btree ("status_code");
CREATE INDEX "IXFK_message_receiver_message" ON "public"."message_receiver" USING btree ("message_id");
CREATE INDEX "IXFK_message_receiver_person" ON "public"."message_receiver" USING btree ("person_id");

-- ----------------------------
-- Triggers structure for table message_receiver
-- ----------------------------
CREATE TRIGGER "message_receiver_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."message_receiver"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table message_receiver
-- ----------------------------
ALTER TABLE "public"."message_receiver" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table message_template
-- ----------------------------
CREATE INDEX "IXFK_message_template_classifier" ON "public"."message_template" USING btree ("type_code");
CREATE INDEX "IXFK_message_template_school" ON "public"."message_template" USING btree ("school_id");

-- ----------------------------
-- Triggers structure for table message_template
-- ----------------------------
CREATE TRIGGER "message_template_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."message_template"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table message_template
-- ----------------------------
ALTER TABLE "public"."message_template" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table midterm_task
-- ----------------------------
CREATE INDEX "IXFK_midterm_task_subject_study_period" ON "public"."midterm_task" USING btree ("subject_study_period_id");

-- ----------------------------
-- Triggers structure for table midterm_task
-- ----------------------------
CREATE TRIGGER "midterm_task_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."midterm_task"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table midterm_task
-- ----------------------------
ALTER TABLE "public"."midterm_task" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table midterm_task_student_result
-- ----------------------------
CREATE INDEX "IXFK_midterm_task_student_result_declaration_subject" ON "public"."midterm_task_student_result" USING btree ("declaration_subject_id");
CREATE INDEX "IXFK_midterm_task_student_result_midterm_task" ON "public"."midterm_task_student_result" USING btree ("midterm_task_id");

-- ----------------------------
-- Triggers structure for table midterm_task_student_result
-- ----------------------------
CREATE TRIGGER "midterm_task_student_result_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."midterm_task_student_result"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table midterm_task_student_result
-- ----------------------------
ALTER TABLE "public"."midterm_task_student_result" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table ois_file
-- ----------------------------
ALTER TABLE "public"."ois_file" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table person
-- ----------------------------
CREATE INDEX "IXFK_person_classifier_04" ON "public"."person" USING btree ("residence_country_code");
CREATE INDEX "IX_person_email" ON "public"."person" USING btree ("email");

-- ----------------------------
-- Triggers structure for table person
-- ----------------------------
CREATE TRIGGER "person_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."person"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Uniques structure for table person
-- ----------------------------
ALTER TABLE "public"."person" ADD UNIQUE ("idcode");

-- ----------------------------
-- Primary Key structure for table person
-- ----------------------------
ALTER TABLE "public"."person" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table practice_journal
-- ----------------------------
CREATE INDEX "IXFK_practice_journal_classifier" ON "public"."practice_journal" USING btree ("status_code");
CREATE INDEX "IXFK_practice_journal_classifier_02" ON "public"."practice_journal" USING btree ("grade_code");
CREATE INDEX "IXFK_practice_journal_contract" ON "public"."practice_journal" USING btree ("contract_id");
CREATE INDEX "IXFK_practice_journal_curriculum_version_omodule" ON "public"."practice_journal" USING btree ("curriculum_version_omodule_id");
CREATE INDEX "IXFK_practice_journal_curriculum_version_omodule_theme" ON "public"."practice_journal" USING btree ("curriculum_version_omodule_theme_id");
CREATE INDEX "IXFK_practice_journal_school" ON "public"."practice_journal" USING btree ("school_id");
CREATE INDEX "IXFK_practice_journal_student" ON "public"."practice_journal" USING btree ("student_id");
CREATE INDEX "IXFK_practice_journal_study_year" ON "public"."practice_journal" USING btree ("study_year_id");
CREATE INDEX "IXFK_practice_journal_teacher" ON "public"."practice_journal" USING btree ("teacher_id");
CREATE INDEX "IXFK_practice_journal_subject" ON "public"."practice_journal" USING btree ("subject_id");

-- ----------------------------
-- Triggers structure for table practice_journal
-- ----------------------------
CREATE TRIGGER "practice_journal_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."practice_journal"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table practice_journal
-- ----------------------------
ALTER TABLE "public"."practice_journal" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table practice_journal_entry
-- ----------------------------
CREATE INDEX "IXFK_practice_journal_entry_practice_journal" ON "public"."practice_journal_entry" USING btree ("practice_journal_id");

-- ----------------------------
-- Triggers structure for table practice_journal_entry
-- ----------------------------
CREATE TRIGGER "practice_journal_entry_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."practice_journal_entry"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table practice_journal_entry
-- ----------------------------
ALTER TABLE "public"."practice_journal_entry" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table practice_journal_file
-- ----------------------------
CREATE INDEX "IXFK_practice_journal_file_ois_file" ON "public"."practice_journal_file" USING btree ("ois_file_id");
CREATE INDEX "IXFK_practice_journal_file_practice_journal" ON "public"."practice_journal_file" USING btree ("practice_journal_id");

-- ----------------------------
-- Triggers structure for table practice_journal_file
-- ----------------------------
CREATE TRIGGER "practice_journal_file_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."practice_journal_file"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table practice_journal_file
-- ----------------------------
ALTER TABLE "public"."practice_journal_file" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table protocol
-- ----------------------------
CREATE INDEX "IXFK_protocol_classifier" ON "public"."protocol" USING btree ("status_code");
CREATE INDEX "IXFK_protocol_school" ON "public"."protocol" USING btree ("school_id");
CREATE INDEX "IXFK_protocol_ois_file" ON "public"."protocol" USING btree ("ois_file_id");

-- ----------------------------
-- Triggers structure for table protocol
-- ----------------------------
CREATE TRIGGER "protocol_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."protocol"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table protocol
-- ----------------------------
ALTER TABLE "public"."protocol" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table protocol_hdata
-- ----------------------------
CREATE INDEX "IXFK_protocol_hdata_classifier" ON "public"."protocol_hdata" USING btree ("type_code");
CREATE INDEX "IXFK_protocol_hdata_protocol" ON "public"."protocol_hdata" USING btree ("protocol_id");
CREATE INDEX "IXFK_protocol_hdata_subject_study_period" ON "public"."protocol_hdata" USING btree ("subject_study_period_id");

-- ----------------------------
-- Primary Key structure for table protocol_hdata
-- ----------------------------
ALTER TABLE "public"."protocol_hdata" ADD PRIMARY KEY ("protocol_id");

-- ----------------------------
-- Indexes structure for table protocol_student
-- ----------------------------
CREATE INDEX "IXFK_protocol_student_classifier" ON "public"."protocol_student" USING btree ("grade_code");
CREATE INDEX "IXFK_protocol_student_protocol" ON "public"."protocol_student" USING btree ("protocol_id");
CREATE INDEX "IXFK_protocol_student_student" ON "public"."protocol_student" USING btree ("student_id");

-- ----------------------------
-- Triggers structure for table protocol_student
-- ----------------------------
CREATE TRIGGER "protocol_student_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."protocol_student"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table protocol_student
-- ----------------------------
ALTER TABLE "public"."protocol_student" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table protocol_student_history
-- ----------------------------
CREATE INDEX "IXFK_protocol_student_history_classifier" ON "public"."protocol_student_history" USING btree ("grade_code");
CREATE INDEX "IXFK_protocol_student_history_protocol_student" ON "public"."protocol_student_history" USING btree ("protocol_student_id");

-- ----------------------------
-- Triggers structure for table protocol_student_history
-- ----------------------------
CREATE TRIGGER "protocol_student_history_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."protocol_student_history"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table protocol_student_history
-- ----------------------------
ALTER TABLE "public"."protocol_student_history" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table protocol_vdata
-- ----------------------------
CREATE INDEX "IXFK_protocol_vdata_curriculum_version" ON "public"."protocol_vdata" USING btree ("curriculum_version_id");
CREATE INDEX "IXFK_protocol_vdata_curriculum_version_omodule" ON "public"."protocol_vdata" USING btree ("curriculum_version_omodule_id");
CREATE INDEX "IXFK_protocol_vdata_protocol" ON "public"."protocol_vdata" USING btree ("protocol_id");
CREATE INDEX "IXFK_protocol_vdata_study_year" ON "public"."protocol_vdata" USING btree ("study_year_id");
CREATE INDEX "IXFK_protocol_vdata_teacher" ON "public"."protocol_vdata" USING btree ("teacher_id");

-- ----------------------------
-- Primary Key structure for table protocol_vdata
-- ----------------------------
ALTER TABLE "public"."protocol_vdata" ADD PRIMARY KEY ("protocol_id");

-- ----------------------------
-- Indexes structure for table room
-- ----------------------------
CREATE INDEX "IXFK_room_building" ON "public"."room" USING btree ("building_id");

-- ----------------------------
-- Triggers structure for table room
-- ----------------------------
CREATE TRIGGER "room_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."room"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

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
-- Triggers structure for table room_equipment
-- ----------------------------
CREATE TRIGGER "room_equipment_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."room_equipment"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table room_equipment
-- ----------------------------
ALTER TABLE "public"."room_equipment" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sais_admission
-- ----------------------------
CREATE INDEX "IXFK_sais_admission_classifier" ON "public"."sais_admission" USING btree ("fin_code");
CREATE INDEX "IXFK_sais_admission_classifier_02" ON "public"."sais_admission" USING btree ("language_code");
CREATE INDEX "IXFK_sais_admission_classifier_03" ON "public"."sais_admission" USING btree ("study_form_code");
CREATE INDEX "IXFK_sais_admission_classifier_04" ON "public"."sais_admission" USING btree ("study_level_code");
CREATE INDEX "IXFK_sais_admission_classifier_05" ON "public"."sais_admission" USING btree ("study_load_code");
CREATE INDEX "IXFK_sais_admission_curriculum_version" ON "public"."sais_admission" USING btree ("curriculum_version_id");

-- ----------------------------
-- Triggers structure for table sais_admission
-- ----------------------------
CREATE TRIGGER "sais_admission_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."sais_admission"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table sais_admission
-- ----------------------------
ALTER TABLE "public"."sais_admission" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sais_application
-- ----------------------------
CREATE INDEX "IXFK_sais_application_classifier" ON "public"."sais_application" USING btree ("sex_code");
CREATE INDEX "IXFK_sais_application_classifier_02" ON "public"."sais_application" USING btree ("fin_code");
CREATE INDEX "IXFK_sais_application_classifier_03" ON "public"."sais_application" USING btree ("status_code");
CREATE INDEX "IXFK_sais_application_classifier_04" ON "public"."sais_application" USING btree ("citizenship_code");
CREATE INDEX "IXFK_sais_application_classifier_05" ON "public"."sais_application" USING btree ("study_load_code");
CREATE INDEX "IXFK_sais_application_classifier_06" ON "public"."sais_application" USING btree ("residence_country_code");
CREATE INDEX "IXFK_sais_application_classifier_07" ON "public"."sais_application" USING btree ("study_form_code");
CREATE INDEX "IXFK_sais_application_classifier_08" ON "public"."sais_application" USING btree ("language_code");
CREATE INDEX "IXFK_sais_application_sais_admission" ON "public"."sais_application" USING btree ("sais_admission_id");

-- ----------------------------
-- Triggers structure for table sais_application
-- ----------------------------
CREATE TRIGGER "sais_application_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."sais_application"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table sais_application
-- ----------------------------
ALTER TABLE "public"."sais_application" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sais_application_grade
-- ----------------------------
CREATE INDEX "IXFK_sais_application_grade_sais_application" ON "public"."sais_application_grade" USING btree ("sais_application_id");

-- ----------------------------
-- Triggers structure for table sais_application_grade
-- ----------------------------
CREATE TRIGGER "sais_application_grade_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."sais_application_grade"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table sais_application_grade
-- ----------------------------
ALTER TABLE "public"."sais_application_grade" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sais_application_graduated_school
-- ----------------------------
CREATE INDEX "IXFK_sais_application_graduated_school_classifier" ON "public"."sais_application_graduated_school" USING btree ("study_level_code");
CREATE INDEX "IXFK_sais_application_graduated_school_classifier_02" ON "public"."sais_application_graduated_school" USING btree ("study_form_code");
CREATE INDEX "IXFK_sais_application_graduated_school_sais_application" ON "public"."sais_application_graduated_school" USING btree ("sais_application_id");

-- ----------------------------
-- Triggers structure for table sais_application_graduated_school
-- ----------------------------
CREATE TRIGGER "sais_application_graduated_school_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."sais_application_graduated_school"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table sais_application_graduated_school
-- ----------------------------
ALTER TABLE "public"."sais_application_graduated_school" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sais_application_other_data
-- ----------------------------
CREATE INDEX "IXFK_sais_application_other_data_sais_application" ON "public"."sais_application_other_data" USING btree ("sais_application_id");

-- ----------------------------
-- Triggers structure for table sais_application_other_data
-- ----------------------------
CREATE TRIGGER "sais_application_other_data_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."sais_application_other_data"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table sais_application_other_data
-- ----------------------------
ALTER TABLE "public"."sais_application_other_data" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sais_classifier
-- ----------------------------
ALTER TABLE "public"."sais_classifier" ADD PRIMARY KEY ("code");

-- ----------------------------
-- Indexes structure for table scholarship_application
-- ----------------------------
CREATE INDEX "IXFK_scholarship_application_classifier" ON "public"."scholarship_application" USING btree ("status_code");
CREATE INDEX "IXFK_scholarship_application_classifier_02" ON "public"."scholarship_application" USING btree ("compensation_reason_code");
CREATE INDEX "IXFK_scholarship_application_classifier_03" ON "public"."scholarship_application" USING btree ("compensation_frequency_code");
CREATE INDEX "IXFK_scholarship_application_curriculum_version" ON "public"."scholarship_application" USING btree ("curriculum_version_id");
CREATE INDEX "IXFK_scholarship_application_scholarship_term" ON "public"."scholarship_application" USING btree ("scholarship_term_id");
CREATE INDEX "IXFK_scholarship_application_student" ON "public"."scholarship_application" USING btree ("student_id");
CREATE INDEX "IXFK_scholarship_application_student_group" ON "public"."scholarship_application" USING btree ("student_group_id");

-- ----------------------------
-- Primary Key structure for table scholarship_application
-- ----------------------------
ALTER TABLE "public"."scholarship_application" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table scholarship_application_family
-- ----------------------------
CREATE INDEX "IXFK_scholarship_application_family_scholarship_application" ON "public"."scholarship_application_family" USING btree ("scholarship_application_id");

-- ----------------------------
-- Primary Key structure for table scholarship_application_family
-- ----------------------------
ALTER TABLE "public"."scholarship_application_family" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table scholarship_application_file
-- ----------------------------
CREATE INDEX "IXFK_scholarship_application_file_ois_file" ON "public"."scholarship_application_file" USING btree ("ois_file_id");
CREATE INDEX "IXFK_scholarship_application_file_scholarship_application" ON "public"."scholarship_application_file" USING btree ("scholarship_application_id");

-- ----------------------------
-- Primary Key structure for table scholarship_application_file
-- ----------------------------
ALTER TABLE "public"."scholarship_application_file" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table scholarship_term
-- ----------------------------
CREATE INDEX "IXFK_scholarship_term_classifier" ON "public"."scholarship_term" USING btree ("type_code");
CREATE INDEX "IXFK_scholarship_term_classifier_02" ON "public"."scholarship_term" USING btree ("average_mark_priority_code");
CREATE INDEX "IXFK_scholarship_term_classifier_03" ON "public"."scholarship_term" USING btree ("last_period_mark_priority_code");
CREATE INDEX "IXFK_scholarship_term_classifier_04" ON "public"."scholarship_term" USING btree ("curriculum_completion_priority_code");
CREATE INDEX "IXFK_scholarship_term_school" ON "public"."scholarship_term" USING btree ("school_id");
CREATE INDEX "IXFK_scholarship_term_study_period" ON "public"."scholarship_term" USING btree ("study_period_id");
CREATE INDEX "IXFK_scholarship_term_classifier_05" ON "public"."scholarship_term" USING btree ("max_absences_priority_code");

-- ----------------------------
-- Primary Key structure for table scholarship_term
-- ----------------------------
ALTER TABLE "public"."scholarship_term" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table scholarship_term_course
-- ----------------------------
CREATE INDEX "IXFK_scholarschip_terme_course_classifier" ON "public"."scholarship_term_course" USING btree ("course_code");
CREATE INDEX "IXFK_scholarschip_terme_course_scholarship_term" ON "public"."scholarship_term_course" USING btree ("scholarship_term_id");

-- ----------------------------
-- Primary Key structure for table scholarship_term_course
-- ----------------------------
ALTER TABLE "public"."scholarship_term_course" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table scholarship_term_curriculum
-- ----------------------------
CREATE INDEX "IXFK_scholarship_term_curriculum_curriculum" ON "public"."scholarship_term_curriculum" USING btree ("curriculum_id");
CREATE INDEX "IXFK_scholarship_term_curriculum_scholarship_term" ON "public"."scholarship_term_curriculum" USING btree ("scholarship_term_id");

-- ----------------------------
-- Primary Key structure for table scholarship_term_curriculum
-- ----------------------------
ALTER TABLE "public"."scholarship_term_curriculum" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table scholarship_term_study_form
-- ----------------------------
CREATE INDEX "IXFK_scholarship_term_study_form_classifier" ON "public"."scholarship_term_study_form" USING btree ("study_form_code");
CREATE INDEX "IXFK_scholarship_term_study_form_scholarship_term" ON "public"."scholarship_term_study_form" USING btree ("scholarship_term_id");

-- ----------------------------
-- Primary Key structure for table scholarship_term_study_form
-- ----------------------------
ALTER TABLE "public"."scholarship_term_study_form" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table scholarship_term_study_load
-- ----------------------------
CREATE INDEX "IXFK_scholarship_term_study_load_classifier" ON "public"."scholarship_term_study_load" USING btree ("study_load_code");
CREATE INDEX "IXFK_scholarship_term_study_load_scholarship_term" ON "public"."scholarship_term_study_load" USING btree ("scholarship_term_id");

-- ----------------------------
-- Primary Key structure for table scholarship_term_study_load
-- ----------------------------
ALTER TABLE "public"."scholarship_term_study_load" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table school
-- ----------------------------
CREATE INDEX "IXFK_school_OIS_FILE" ON "public"."school" USING btree ("ois_file_id");
CREATE INDEX "IXFK_school_classifier" ON "public"."school" USING btree ("ehis_school_code");

-- ----------------------------
-- Triggers structure for table school
-- ----------------------------
CREATE TRIGGER "school_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."school"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Uniques structure for table school
-- ----------------------------
ALTER TABLE "public"."school" ADD UNIQUE ("ehis_school_code");

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
-- Triggers structure for table school_department
-- ----------------------------
CREATE TRIGGER "school_department_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."school_department"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

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
-- Triggers structure for table school_study_level
-- ----------------------------
CREATE TRIGGER "school_study_level_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."school_study_level"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

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
-- Triggers structure for table state_curriculum
-- ----------------------------
CREATE TRIGGER "state_curriculum_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."state_curriculum"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

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
-- Triggers structure for table state_curriculum_module
-- ----------------------------
CREATE TRIGGER "state_curriculum_module_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."state_curriculum_module"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

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
-- Triggers structure for table state_curriculum_module_occupation
-- ----------------------------
CREATE TRIGGER "state_curriculum_module_occupation_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."state_curriculum_module_occupation"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

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
-- Triggers structure for table state_curriculum_module_outcomes
-- ----------------------------
CREATE TRIGGER "state_curriculum_module_outcomes_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."state_curriculum_module_outcomes"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

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
-- Triggers structure for table state_curriculum_occupation
-- ----------------------------
CREATE TRIGGER "state_curriculum_occupation_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."state_curriculum_occupation"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table state_curriculum_occupation
-- ----------------------------
ALTER TABLE "public"."state_curriculum_occupation" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table student
-- ----------------------------
CREATE INDEX "IXFK_student_classifier" ON "public"."student" USING btree ("study_form_code");
CREATE INDEX "IXFK_student_curriculum_version" ON "public"."student" USING btree ("curriculum_version_id");
CREATE INDEX "IXFK_student_person" ON "public"."student" USING btree ("person_id");
CREATE INDEX "IXFK_student_school" ON "public"."student" USING btree ("school_id");
CREATE INDEX "IXFK_student_student_group" ON "public"."student" USING btree ("student_group_id");
CREATE INDEX "IXFK_student_classifier_02" ON "public"."student" USING btree ("language_code");
CREATE INDEX "IXFK_student_classifier_03" ON "public"."student" USING btree ("special_need_code");
CREATE INDEX "IXFK_student_classifier_04" ON "public"."student" USING btree ("previous_study_level_code");
CREATE INDEX "IXFK_student_classifier_05" ON "public"."student" USING btree ("status_code");
CREATE INDEX "IXFK_student_classifier_06" ON "public"."student" USING btree ("study_load_code");
CREATE INDEX "IXFK_student_classifier_07" ON "public"."student" USING btree ("fin_code");
CREATE INDEX "IXFK_student_classifier_08" ON "public"."student" USING btree ("fin_specific_code");
CREATE INDEX "IXFK_student_curriculum_speciality" ON "public"."student" USING btree ("curriculum_speciality_id");
CREATE INDEX "IXFK_student_ois_file" ON "public"."student" USING btree ("ois_file_id");
CREATE INDEX "IXFK_student_history" ON "public"."student" USING btree ("student_history_id");
CREATE INDEX "IX_student_email" ON "public"."student" USING btree ("email");

-- ----------------------------
-- Triggers structure for table student
-- ----------------------------
CREATE TRIGGER "student_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."student"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table student
-- ----------------------------
ALTER TABLE "public"."student" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table student_absence
-- ----------------------------
CREATE INDEX "IXFK_student_absence_student" ON "public"."student_absence" USING btree ("student_id");

-- ----------------------------
-- Triggers structure for table student_absence
-- ----------------------------
CREATE TRIGGER "student_absence_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."student_absence"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table student_absence
-- ----------------------------
ALTER TABLE "public"."student_absence" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table student_curriculum_completion
-- ----------------------------
CREATE INDEX "IXFK_student_curriculum_completion_student" ON "public"."student_curriculum_completion" USING btree ("student_id");

-- ----------------------------
-- Primary Key structure for table student_curriculum_completion
-- ----------------------------
ALTER TABLE "public"."student_curriculum_completion" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table student_group
-- ----------------------------
CREATE INDEX "IXFK_student_group_school" ON "public"."student_group" USING btree ("school_id");
CREATE INDEX "IXFK_student_group_classifier" ON "public"."student_group" USING btree ("study_form_code");
CREATE INDEX "IXFK_student_group_classifier_02" ON "public"."student_group" USING btree ("language_code");
CREATE INDEX "IXFK_student_group_classifier_03" ON "public"."student_group" USING btree ("speciality_code");
CREATE INDEX "IXFK_student_group_curriculum" ON "public"."student_group" USING btree ("curriculum_id");
CREATE INDEX "IXFK_student_group_curriculum_version" ON "public"."student_group" USING btree ("curriculum_version_id");
CREATE INDEX "IXFK_student_group_teacher" ON "public"."student_group" USING btree ("teacher_id");

-- ----------------------------
-- Triggers structure for table student_group
-- ----------------------------
CREATE TRIGGER "student_group_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."student_group"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table student_group
-- ----------------------------
ALTER TABLE "public"."student_group" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table student_higher_result
-- ----------------------------
CREATE INDEX "IXFK_student_higher_result_apel_application_record" ON "public"."student_higher_result" USING btree ("apel_application_record_id");
CREATE INDEX "IXFK_student_higher_result_apel_school" ON "public"."student_higher_result" USING btree ("apel_school_id");
CREATE INDEX "IXFK_student_higher_result_classifier" ON "public"."student_higher_result" USING btree ("grade_code");
CREATE INDEX "IXFK_student_higher_result_curriculum_version_hmodule" ON "public"."student_higher_result" USING btree ("curriculum_version_hmodule_id");
CREATE INDEX "IXFK_student_higher_result_protocol_student" ON "public"."student_higher_result" USING btree ("protocol_student_id");
CREATE INDEX "IXFK_student_higher_result_subject" ON "public"."student_higher_result" USING btree ("subject_id");
CREATE INDEX "IXFK_student_result_student" ON "public"."student_higher_result" USING btree ("student_id");

-- ----------------------------
-- Primary Key structure for table student_higher_result
-- ----------------------------
ALTER TABLE "public"."student_higher_result" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table student_history
-- ----------------------------
CREATE INDEX "IXFK_student_history_classifier" ON "public"."student_history" USING btree ("study_form_code");
CREATE INDEX "IXFK_student_history_classifier_02" ON "public"."student_history" USING btree ("language_code");
CREATE INDEX "IXFK_student_history_classifier_03" ON "public"."student_history" USING btree ("special_need_code");
CREATE INDEX "IXFK_student_history_classifier_04" ON "public"."student_history" USING btree ("previous_study_level_code");
CREATE INDEX "IXFK_student_history_classifier_05" ON "public"."student_history" USING btree ("status_code");
CREATE INDEX "IXFK_student_history_classifier_06" ON "public"."student_history" USING btree ("study_load_code");
CREATE INDEX "IXFK_student_history_classifier_07" ON "public"."student_history" USING btree ("fin_code");
CREATE INDEX "IXFK_student_history_classifier_08" ON "public"."student_history" USING btree ("fin_specific_code");
CREATE INDEX "IXFK_student_history_curriculum_speciality" ON "public"."student_history" USING btree ("curriculum_speciality_id");
CREATE INDEX "IXFK_student_history_curriculum_version" ON "public"."student_history" USING btree ("curriculum_version_id");
CREATE INDEX "IXFK_student_history_ois_file" ON "public"."student_history" USING btree ("ois_file_id");
CREATE INDEX "IXFK_student_history_student" ON "public"."student_history" USING btree ("student_id");
CREATE INDEX "IXFK_student_history_student_group" ON "public"."student_history" USING btree ("student_group_id");
CREATE INDEX "IXFK_student_history_id" ON "public"."student_history" USING btree ("prev_student_history_id");

-- ----------------------------
-- Triggers structure for table student_history
-- ----------------------------
CREATE TRIGGER "student_history_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."student_history"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table student_history
-- ----------------------------
ALTER TABLE "public"."student_history" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table student_occupation_certificate
-- ----------------------------
CREATE INDEX "IXFK_student_occupation_certificate_classifier" ON "public"."student_occupation_certificate" USING btree ("occupation_code");
CREATE INDEX "IXFK_student_occupation_certificate_classifier_02" ON "public"."student_occupation_certificate" USING btree ("part_occupation_code");
CREATE INDEX "IXFK_student_occupation_certificate_classifier_03" ON "public"."student_occupation_certificate" USING btree ("speciality_code");
CREATE INDEX "IXFK_student_occupation_certificate_student" ON "public"."student_occupation_certificate" USING btree ("student_id");

-- ----------------------------
-- Primary Key structure for table student_occupation_certificate
-- ----------------------------
ALTER TABLE "public"."student_occupation_certificate" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table student_representative
-- ----------------------------
CREATE INDEX "IXFK_student_representative_classifier" ON "public"."student_representative" USING btree ("relation_code");
CREATE INDEX "IXFK_student_representative_person" ON "public"."student_representative" USING btree ("person_id");
CREATE INDEX "IXFK_student_representative_student" ON "public"."student_representative" USING btree ("student_id");

-- ----------------------------
-- Triggers structure for table student_representative
-- ----------------------------
CREATE TRIGGER "student_representative_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."student_representative"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Uniques structure for table student_representative
-- ----------------------------
ALTER TABLE "public"."student_representative" ADD UNIQUE ("student_id", "person_id");

-- ----------------------------
-- Primary Key structure for table student_representative
-- ----------------------------
ALTER TABLE "public"."student_representative" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table student_representative_application
-- ----------------------------
CREATE INDEX "IXFK_student_representative_application_classifier" ON "public"."student_representative_application" USING btree ("status_code");
CREATE INDEX "IXFK_student_representative_application_student" ON "public"."student_representative_application" USING btree ("student_id");
CREATE INDEX "IXFK_student_represantitive_person" ON "public"."student_representative_application" USING btree ("person_id");
CREATE INDEX "IXFK_student_representative_application_2" ON "public"."student_representative_application" USING btree ("relation_code");

-- ----------------------------
-- Triggers structure for table student_representative_application
-- ----------------------------
CREATE TRIGGER "student_representative_application_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."student_representative_application"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table student_representative_application
-- ----------------------------
ALTER TABLE "public"."student_representative_application" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table student_vocational_result
-- ----------------------------
CREATE INDEX "IXFK_student_vocational_result_apel_application_record" ON "public"."student_vocational_result" USING btree ("apel_application_record_id");
CREATE INDEX "IXFK_student_vocational_result_apel_school" ON "public"."student_vocational_result" USING btree ("apel_school_id");
CREATE INDEX "IXFK_student_vocational_result_classifier" ON "public"."student_vocational_result" USING btree ("grade_code");
CREATE INDEX "IXFK_student_vocational_result_curriculum_version_omodule" ON "public"."student_vocational_result" USING btree ("curriculum_version_omodule_id");
CREATE INDEX "IXFK_student_vocational_result_protocol_student" ON "public"."student_vocational_result" USING btree ("protocol_student_id");
CREATE INDEX "IXFK_student_vocational_result_student" ON "public"."student_vocational_result" USING btree ("student_id");

-- ----------------------------
-- Primary Key structure for table student_vocational_result
-- ----------------------------
ALTER TABLE "public"."student_vocational_result" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table study_period
-- ----------------------------
CREATE INDEX "IXFK_study_period_classifier" ON "public"."study_period" USING btree ("type_code");
CREATE INDEX "IXFK_study_period_study_year" ON "public"."study_period" USING btree ("study_year_id");

-- ----------------------------
-- Triggers structure for table study_period
-- ----------------------------
CREATE TRIGGER "study_period_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."study_period"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table study_period
-- ----------------------------
ALTER TABLE "public"."study_period" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table study_period_event
-- ----------------------------
CREATE INDEX "IXFK_study_period_event_classifier" ON "public"."study_period_event" USING btree ("event_type_code");
CREATE INDEX "IXFK_study_period_event_study_period" ON "public"."study_period_event" USING btree ("study_period_id");
CREATE INDEX "IXFK_study_period_event_study_year" ON "public"."study_period_event" USING btree ("study_year_id");

-- ----------------------------
-- Triggers structure for table study_period_event
-- ----------------------------
CREATE TRIGGER "study_period_event_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."study_period_event"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table study_period_event
-- ----------------------------
ALTER TABLE "public"."study_period_event" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table study_year
-- ----------------------------
CREATE INDEX "IXFK_study_year_classifier" ON "public"."study_year" USING btree ("year_code");
CREATE INDEX "IXFK_study_year_school" ON "public"."study_year" USING btree ("school_id");

-- ----------------------------
-- Triggers structure for table study_year
-- ----------------------------
CREATE TRIGGER "study_year_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."study_year"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Uniques structure for table study_year
-- ----------------------------
ALTER TABLE "public"."study_year" ADD UNIQUE ("school_id", "year_code");

-- ----------------------------
-- Primary Key structure for table study_year
-- ----------------------------
ALTER TABLE "public"."study_year" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table study_year_schedule
-- ----------------------------
CREATE INDEX "IXFK_study_year_schedule_school" ON "public"."study_year_schedule" USING btree ("school_id");
CREATE INDEX "IXFK_study_year_schedule_student_group" ON "public"."study_year_schedule" USING btree ("student_group_id");
CREATE INDEX "IXFK_study_year_schedule_study_period" ON "public"."study_year_schedule" USING btree ("study_period_id");
CREATE INDEX "IXFK_study_year_schedule_study_year_schedule_legend" ON "public"."study_year_schedule" USING btree ("study_year_schedule_legend_id");

-- ----------------------------
-- Triggers structure for table study_year_schedule
-- ----------------------------
CREATE TRIGGER "study_year_schedule_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."study_year_schedule"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table study_year_schedule
-- ----------------------------
ALTER TABLE "public"."study_year_schedule" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table study_year_schedule_legend
-- ----------------------------
CREATE INDEX "IXFK_study_year_schedule_legend_school" ON "public"."study_year_schedule_legend" USING btree ("school_id");

-- ----------------------------
-- Triggers structure for table study_year_schedule_legend
-- ----------------------------
CREATE TRIGGER "study_year_schedule_legend_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."study_year_schedule_legend"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table study_year_schedule_legend
-- ----------------------------
ALTER TABLE "public"."study_year_schedule_legend" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table subject
-- ----------------------------
CREATE INDEX "IXFK_subject_classifier" ON "public"."subject" USING btree ("assessment_code");
CREATE INDEX "IXFK_subject_classifier_02" ON "public"."subject" USING btree ("status_code");
CREATE INDEX "IXFK_subject_school" ON "public"."subject" USING btree ("school_id");
CREATE INDEX "IXFK_subject_school_department" ON "public"."subject" USING btree ("school_department_id");

-- ----------------------------
-- Triggers structure for table subject
-- ----------------------------
CREATE TRIGGER "subject_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."subject"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Uniques structure for table subject
-- ----------------------------
ALTER TABLE "public"."subject" ADD UNIQUE ("school_id", "code");

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
-- Triggers structure for table subject_connect
-- ----------------------------
CREATE TRIGGER "subject_connect_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."subject_connect"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

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
-- Triggers structure for table subject_lang
-- ----------------------------
CREATE TRIGGER "subject_lang_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."subject_lang"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table subject_lang
-- ----------------------------
ALTER TABLE "public"."subject_lang" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table subject_study_period
-- ----------------------------
CREATE INDEX "IXFK_subject_teaching_study_period" ON "public"."subject_study_period" USING btree ("study_period_id");
CREATE INDEX "IXFK_subject_teaching_subject" ON "public"."subject_study_period" USING btree ("subject_id");
CREATE INDEX "IXFK_subject_study_period_classifier" ON "public"."subject_study_period" USING btree ("group_proportion_code");
CREATE INDEX "IXFK_subject_study_period_classifier_02" ON "public"."subject_study_period" USING btree ("declaration_type_code");

-- ----------------------------
-- Triggers structure for table subject_study_period
-- ----------------------------
CREATE TRIGGER "subject_study_period_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."subject_study_period"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table subject_study_period
-- ----------------------------
ALTER TABLE "public"."subject_study_period" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table subject_study_period_capacity
-- ----------------------------
CREATE INDEX "IXFK_subject_study_period_capacity_classifier" ON "public"."subject_study_period_capacity" USING btree ("capacity_type_code");
CREATE INDEX "IXFK_subject_study_period_capacity_subject_study_period" ON "public"."subject_study_period_capacity" USING btree ("subject_study_period_id");

-- ----------------------------
-- Triggers structure for table subject_study_period_capacity
-- ----------------------------
CREATE TRIGGER "subject_study_period_capacity_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."subject_study_period_capacity"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table subject_study_period_capacity
-- ----------------------------
ALTER TABLE "public"."subject_study_period_capacity" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table subject_study_period_plan
-- ----------------------------
CREATE INDEX "IXFK_subject_study_period_plan_study_period" ON "public"."subject_study_period_plan" USING btree ("study_period_id");
CREATE INDEX "IXFK_subject_study_period_plan_subject" ON "public"."subject_study_period_plan" USING btree ("subject_id");

-- ----------------------------
-- Triggers structure for table subject_study_period_plan
-- ----------------------------
CREATE TRIGGER "subject_study_period_plan_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."subject_study_period_plan"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table subject_study_period_plan
-- ----------------------------
ALTER TABLE "public"."subject_study_period_plan" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table subject_study_period_plan_capacity
-- ----------------------------
CREATE INDEX "IXFK_subject_study_period_plan_capacity_classifier" ON "public"."subject_study_period_plan_capacity" USING btree ("capacity_type_code");
CREATE INDEX "IXFK_subject_study_period_plan_capacity_subject_study_period_pl" ON "public"."subject_study_period_plan_capacity" USING btree ("subject_study_period_plan_id");

-- ----------------------------
-- Triggers structure for table subject_study_period_plan_capacity
-- ----------------------------
CREATE TRIGGER "subject_study_period_plan_capacity_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."subject_study_period_plan_capacity"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table subject_study_period_plan_capacity
-- ----------------------------
ALTER TABLE "public"."subject_study_period_plan_capacity" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table subject_study_period_plan_curriculum
-- ----------------------------
CREATE INDEX "IXFK_subject_study_period_plan_curriculum_curriculum" ON "public"."subject_study_period_plan_curriculum" USING btree ("curriculum_id");
CREATE INDEX "IXFK_subject_study_period_plan_curriculum_subject_study_period_" ON "public"."subject_study_period_plan_curriculum" USING btree ("subject_study_period_plan_id");

-- ----------------------------
-- Triggers structure for table subject_study_period_plan_curriculum
-- ----------------------------
CREATE TRIGGER "subject_study_period_plan_curriculum_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."subject_study_period_plan_curriculum"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table subject_study_period_plan_curriculum
-- ----------------------------
ALTER TABLE "public"."subject_study_period_plan_curriculum" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table subject_study_period_plan_studyform
-- ----------------------------
CREATE INDEX "IXFK_subject_study_period_plan_studyform_classifier" ON "public"."subject_study_period_plan_studyform" USING btree ("study_form_code");
CREATE INDEX "IXFK_subject_study_period_plan_studyform_subject_study_period_p" ON "public"."subject_study_period_plan_studyform" USING btree ("subject_study_period_plan_id");

-- ----------------------------
-- Triggers structure for table subject_study_period_plan_studyform
-- ----------------------------
CREATE TRIGGER "subject_study_period_plan_studyform_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."subject_study_period_plan_studyform"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table subject_study_period_plan_studyform
-- ----------------------------
ALTER TABLE "public"."subject_study_period_plan_studyform" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table subject_study_period_student_group
-- ----------------------------
CREATE INDEX "IXFK_subject_study_period_student_group_student_group" ON "public"."subject_study_period_student_group" USING btree ("student_group_id");
CREATE INDEX "IXFK_subject_study_period_student_group_subject_study_period" ON "public"."subject_study_period_student_group" USING btree ("subject_study_period_id");

-- ----------------------------
-- Triggers structure for table subject_study_period_student_group
-- ----------------------------
CREATE TRIGGER "subject_study_period_student_group_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."subject_study_period_student_group"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table subject_study_period_student_group
-- ----------------------------
ALTER TABLE "public"."subject_study_period_student_group" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table subject_study_period_teacher
-- ----------------------------
CREATE INDEX "IXFK_subject_study_period_teacher_subject_study_period" ON "public"."subject_study_period_teacher" USING btree ("subject_study_period_id");
CREATE INDEX "IXFK_subject_study_period_teacher_teacher" ON "public"."subject_study_period_teacher" USING btree ("teacher_id");

-- ----------------------------
-- Triggers structure for table subject_study_period_teacher
-- ----------------------------
CREATE TRIGGER "subject_study_period_teacher_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."subject_study_period_teacher"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table subject_study_period_teacher
-- ----------------------------
ALTER TABLE "public"."subject_study_period_teacher" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table teacher
-- ----------------------------
CREATE INDEX "IXFK_teacher_person" ON "public"."teacher" USING btree ("person_id");
CREATE INDEX "IXFK_teacher_school" ON "public"."teacher" USING btree ("school_id");
CREATE INDEX "IXFK_teacher_teacher_occupation" ON "public"."teacher" USING btree ("teacher_occupation_id");
CREATE INDEX "IX_teacher_email" ON "public"."teacher" USING btree ("email");

-- ----------------------------
-- Uniques structure for table teacher
-- ----------------------------
ALTER TABLE "public"."teacher" ADD UNIQUE ("person_id", "school_id");

-- ----------------------------
-- Primary Key structure for table teacher
-- ----------------------------
ALTER TABLE "public"."teacher" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table teacher_absence
-- ----------------------------
CREATE INDEX "IXFK_teacher_absence_teacher" ON "public"."teacher_absence" USING btree ("teacher_id");

-- ----------------------------
-- Primary Key structure for table teacher_absence
-- ----------------------------
ALTER TABLE "public"."teacher_absence" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table teacher_continuing_education
-- ----------------------------
CREATE INDEX "IXFK_teacher_continuing_education_classifier" ON "public"."teacher_continuing_education" USING btree ("school_code");
CREATE INDEX "IXFK_teacher_continuing_education_classifier_02" ON "public"."teacher_continuing_education" USING btree ("field_code");
CREATE INDEX "IXFK_teacher_continuing_education_classifier_03" ON "public"."teacher_continuing_education" USING btree ("diploma_code");
CREATE INDEX "IXFK_teacher_continuing_education_teacher" ON "public"."teacher_continuing_education" USING btree ("teacher_id");

-- ----------------------------
-- Triggers structure for table teacher_continuing_education
-- ----------------------------
CREATE TRIGGER "teacher_continuing_education_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."teacher_continuing_education"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table teacher_continuing_education
-- ----------------------------
ALTER TABLE "public"."teacher_continuing_education" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table teacher_mobility
-- ----------------------------
CREATE INDEX "IXFK_teacher_mobility_classifier" ON "public"."teacher_mobility" USING btree ("target_code");
CREATE INDEX "IXFK_teacher_mobility_classifier_02" ON "public"."teacher_mobility" USING btree ("state_code");
CREATE INDEX "IXFK_teacher_mobility_teacher" ON "public"."teacher_mobility" USING btree ("teacher_id");

-- ----------------------------
-- Triggers structure for table teacher_mobility
-- ----------------------------
CREATE TRIGGER "teacher_mobility_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."teacher_mobility"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table teacher_mobility
-- ----------------------------
ALTER TABLE "public"."teacher_mobility" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table teacher_occupation
-- ----------------------------
CREATE INDEX "IXFK_teacher_occupation_school" ON "public"."teacher_occupation" USING btree ("school_id");

-- ----------------------------
-- Triggers structure for table teacher_occupation
-- ----------------------------
CREATE TRIGGER "teacher_occupation_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."teacher_occupation"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table teacher_occupation
-- ----------------------------
ALTER TABLE "public"."teacher_occupation" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table teacher_position_ehis
-- ----------------------------
CREATE INDEX "IXFK_teacher_position_ehis_classifier" ON "public"."teacher_position_ehis" USING btree ("position_code");
CREATE INDEX "IXFK_teacher_position_ehis_classifier_02" ON "public"."teacher_position_ehis" USING btree ("contract_type_code");
CREATE INDEX "IXFK_teacher_position_ehis_classifier_03" ON "public"."teacher_position_ehis" USING btree ("language_code");
CREATE INDEX "IXFK_teacher_position_ehis_classifier_04" ON "public"."teacher_position_ehis" USING btree ("employment_type_code");
CREATE INDEX "IXFK_teacher_position_ehis_teacher" ON "public"."teacher_position_ehis" USING btree ("teacher_id");
CREATE INDEX "IXFK_teacher_position_ehis_sd" ON "public"."teacher_position_ehis" USING btree ("school_department_id");

-- ----------------------------
-- Triggers structure for table teacher_position_ehis
-- ----------------------------
CREATE TRIGGER "teacher_position_ehis_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."teacher_position_ehis"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table teacher_position_ehis
-- ----------------------------
ALTER TABLE "public"."teacher_position_ehis" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table teacher_qualification
-- ----------------------------
CREATE INDEX "IXFK_teacher_qualification_classifier" ON "public"."teacher_qualification" USING btree ("qualification_code");
CREATE INDEX "IXFK_teacher_qualification_classifier_02" ON "public"."teacher_qualification" USING btree ("qualification_name_code");
CREATE INDEX "IXFK_teacher_qualification_classifier_03" ON "public"."teacher_qualification" USING btree ("state_code");
CREATE INDEX "IXFK_teacher_qualification_classifier_04" ON "public"."teacher_qualification" USING btree ("school_code");
CREATE INDEX "IXFK_teacher_qualification_classifier_05" ON "public"."teacher_qualification" USING btree ("ex_school_code");
CREATE INDEX "IXFK_teacher_qualification_teacher" ON "public"."teacher_qualification" USING btree ("teacher_id");
CREATE INDEX "IXFK_teacher_qualification_classifier_06" ON "public"."teacher_qualification" USING btree ("study_level_code");
CREATE INDEX "IXFK_teacher_qualification_classifier_07" ON "public"."teacher_qualification" USING btree ("language_code");

-- ----------------------------
-- Triggers structure for table teacher_qualification
-- ----------------------------
CREATE TRIGGER "teacher_qualification_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."teacher_qualification"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table teacher_qualification
-- ----------------------------
ALTER TABLE "public"."teacher_qualification" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table timetable
-- ----------------------------
CREATE INDEX "IXFK_timetable_classifier" ON "public"."timetable" USING btree ("status_code");
CREATE INDEX "IXFK_timetable_school" ON "public"."timetable" USING btree ("school_id");
CREATE INDEX "IXFK_timetable_study_period" ON "public"."timetable" USING btree ("study_period_id");

-- ----------------------------
-- Triggers structure for table timetable
-- ----------------------------
CREATE TRIGGER "timetable_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."timetable"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table timetable
-- ----------------------------
ALTER TABLE "public"."timetable" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table timetable_event
-- ----------------------------
CREATE INDEX "IXFK_timetable_event_classifier" ON "public"."timetable_event" USING btree ("repeat_code");
CREATE INDEX "IXFK_timetable_event_timetable_object" ON "public"."timetable_event" USING btree ("timetable_object_id");
CREATE INDEX "IXFK_timetable_event_classifier_02" ON "public"."timetable_event" USING btree ("capacity_type_code");
CREATE INDEX "IXFK_timetable_event_subject_study_period_student_group" ON "public"."timetable_event" USING btree ("subject_study_period_student_group_id");

-- ----------------------------
-- Triggers structure for table timetable_event
-- ----------------------------
CREATE TRIGGER "timetable_event_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."timetable_event"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table timetable_event
-- ----------------------------
ALTER TABLE "public"."timetable_event" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table timetable_event_room
-- ----------------------------
CREATE INDEX "IXFK_timetable_event_room_room" ON "public"."timetable_event_room" USING btree ("room_id");
CREATE INDEX "IXFK_timetable_event_room_timetable_event_time" ON "public"."timetable_event_room" USING btree ("timetable_event_time_id");

-- ----------------------------
-- Triggers structure for table timetable_event_room
-- ----------------------------
CREATE TRIGGER "timetable_event_room_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."timetable_event_room"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table timetable_event_room
-- ----------------------------
ALTER TABLE "public"."timetable_event_room" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table timetable_event_teacher
-- ----------------------------
CREATE INDEX "IXFK_timetable_event_teacher_teacher" ON "public"."timetable_event_teacher" USING btree ("teacher_id");
CREATE INDEX "IXFK_timetable_event_teacher_timetable_event_time" ON "public"."timetable_event_teacher" USING btree ("timetable_event_time_id");

-- ----------------------------
-- Triggers structure for table timetable_event_teacher
-- ----------------------------
CREATE TRIGGER "timetable_event_teacher_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."timetable_event_teacher"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table timetable_event_teacher
-- ----------------------------
ALTER TABLE "public"."timetable_event_teacher" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table timetable_event_time
-- ----------------------------
CREATE INDEX "IXFK_timetable_event_time_timetable_event" ON "public"."timetable_event_time" USING btree ("timetable_event_id");

-- ----------------------------
-- Triggers structure for table timetable_event_time
-- ----------------------------
CREATE TRIGGER "timetable_event_time_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."timetable_event_time"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table timetable_event_time
-- ----------------------------
ALTER TABLE "public"."timetable_event_time" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table timetable_object
-- ----------------------------
CREATE INDEX "IXFK_timetable_object_journal" ON "public"."timetable_object" USING btree ("journal_id");
CREATE INDEX "IXFK_timetable_object_subject_study_period" ON "public"."timetable_object" USING btree ("subject_study_period_id");
CREATE INDEX "IXFK_timetable_object_timetable" ON "public"."timetable_object" USING btree ("timetable_id");

-- ----------------------------
-- Triggers structure for table timetable_object
-- ----------------------------
CREATE TRIGGER "timetable_object_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."timetable_object"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table timetable_object
-- ----------------------------
ALTER TABLE "public"."timetable_object" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table timetable_object_student_group
-- ----------------------------
CREATE INDEX "IXFK_timetable_event_student_group_student_group" ON "public"."timetable_object_student_group" USING btree ("student_group_id");
CREATE INDEX "IXFK_timetable_object_student_group_timetable_object" ON "public"."timetable_object_student_group" USING btree ("timetable_object_id");

-- ----------------------------
-- Triggers structure for table timetable_object_student_group
-- ----------------------------
CREATE TRIGGER "timetable_object_student_group_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."timetable_object_student_group"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Primary Key structure for table timetable_object_student_group
-- ----------------------------
ALTER TABLE "public"."timetable_object_student_group" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table user_
-- ----------------------------
CREATE INDEX "IXFK_user__classifier" ON "public"."user_" USING btree ("role_code");
CREATE INDEX "IXFK_user__person" ON "public"."user_" USING btree ("person_id");
CREATE INDEX "IXFK_user__school" ON "public"."user_" USING btree ("school_id");
CREATE INDEX "IXFK_user__student" ON "public"."user_" USING btree ("student_id");
CREATE INDEX "IXFK_user__teacher" ON "public"."user_" USING btree ("teacher_id");

-- ----------------------------
-- Triggers structure for table user_
-- ----------------------------
CREATE TRIGGER "user__audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."user_"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

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
-- Triggers structure for table user_rights
-- ----------------------------
CREATE TRIGGER "user_rights_audit" AFTER INSERT OR UPDATE OR DELETE ON "public"."user_rights"
FOR EACH ROW
EXECUTE PROCEDURE "hois_audit"();

-- ----------------------------
-- Uniques structure for table user_rights
-- ----------------------------
ALTER TABLE "public"."user_rights" ADD UNIQUE ("permission_code", "object_code", "user_id");

-- ----------------------------
-- Primary Key structure for table user_rights
-- ----------------------------
ALTER TABLE "public"."user_rights" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table user_role_default
-- ----------------------------
CREATE INDEX "IXFK_user_role_default2_classifier" ON "public"."user_role_default" USING btree ("role_code");
CREATE INDEX "IXFK_user_role_default2_classifier_02" ON "public"."user_role_default" USING btree ("object_code");
CREATE INDEX "IXFK_user_role_default2_classifier_03" ON "public"."user_role_default" USING btree ("permission_code");

-- ----------------------------
-- Primary Key structure for table user_role_default
-- ----------------------------
ALTER TABLE "public"."user_role_default" ADD PRIMARY KEY ("role_code", "object_code", "permission_code");

-- ----------------------------
-- Indexes structure for table user_sessions
-- ----------------------------
CREATE INDEX "IXFK_user_sessions_classifier" ON "public"."user_sessions" USING btree ("type_code");
CREATE INDEX "IX_user_sessions_session_person" ON "public"."user_sessions" USING btree ("person_id", "session_id");

-- ----------------------------
-- Primary Key structure for table user_sessions
-- ----------------------------
ALTER TABLE "public"."user_sessions" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table ws_ehis_curriculum_log
-- ----------------------------
CREATE INDEX "IXFK_ws_ehis_curriculum_log_curriculum" ON "public"."ws_ehis_curriculum_log" USING btree ("curriculum_id");
CREATE INDEX "IXFK_ws_ehis_log_school" ON "public"."ws_ehis_curriculum_log" USING btree ("school_id");

-- ----------------------------
-- Primary Key structure for table ws_ehis_curriculum_log
-- ----------------------------
ALTER TABLE "public"."ws_ehis_curriculum_log" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table ws_ehis_student_log
-- ----------------------------
CREATE INDEX "IXFK_ws_ehis_student_log_directive" ON "public"."ws_ehis_student_log" USING btree ("directive_id");
CREATE INDEX "IXFK_ws_ehis_student_log_school" ON "public"."ws_ehis_student_log" USING btree ("school_id");

-- ----------------------------
-- Primary Key structure for table ws_ehis_student_log
-- ----------------------------
ALTER TABLE "public"."ws_ehis_student_log" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table ws_ehis_teacher_log
-- ----------------------------
CREATE INDEX "IXFK_ws_ehis_teacher_log_school" ON "public"."ws_ehis_teacher_log" USING btree ("school_id");
CREATE INDEX "IXFK_ws_ehis_teacher_log_teacher" ON "public"."ws_ehis_teacher_log" USING btree ("teacher_id");

-- ----------------------------
-- Primary Key structure for table ws_ehis_teacher_log
-- ----------------------------
ALTER TABLE "public"."ws_ehis_teacher_log" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table ws_ekis_log
-- ----------------------------
CREATE INDEX "IXFK_ws_ekis_log_certificate" ON "public"."ws_ekis_log" USING btree ("certificate_id");
CREATE INDEX "IXFK_ws_ekis_log_contract" ON "public"."ws_ekis_log" USING btree ("contract_id");
CREATE INDEX "IXFK_ws_ekis_log_directive" ON "public"."ws_ekis_log" USING btree ("directive_id");
CREATE INDEX "IXFK_ws_ekis_log_school" ON "public"."ws_ekis_log" USING btree ("school_id");

-- ----------------------------
-- Primary Key structure for table ws_ekis_log
-- ----------------------------
ALTER TABLE "public"."ws_ekis_log" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table ws_qf_log
-- ----------------------------
CREATE INDEX "IXFK_ws_qf_log_school" ON "public"."ws_qf_log" USING btree ("school_id");

-- ----------------------------
-- Primary Key structure for table ws_qf_log
-- ----------------------------
ALTER TABLE "public"."ws_qf_log" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table ws_rtip_log
-- ----------------------------
CREATE INDEX "IXFK_ws_rtip_log_school" ON "public"."ws_rtip_log" USING btree ("school_id");

-- ----------------------------
-- Primary Key structure for table ws_rtip_log
-- ----------------------------
ALTER TABLE "public"."ws_rtip_log" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table ws_sais_log
-- ----------------------------
CREATE INDEX "IXFK_ws_sais_log_school" ON "public"."ws_sais_log" USING btree ("school_id");
CREATE INDEX "IXFK_ws_sais_log_ws_sais_log" ON "public"."ws_sais_log" USING btree ("first_ws_sais_log_id");

-- ----------------------------
-- Primary Key structure for table ws_sais_log
-- ----------------------------
ALTER TABLE "public"."ws_sais_log" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table ws_sais_log_detail
-- ----------------------------
CREATE INDEX "IXFK_ws_sais_log_detail_ws_sais_log" ON "public"."ws_sais_log_detail" USING btree ("ws_sais_log_id");

-- ----------------------------
-- Primary Key structure for table ws_sais_log_detail
-- ----------------------------
ALTER TABLE "public"."ws_sais_log_detail" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Foreign Key structure for table "public"."apel_application"
-- ----------------------------
ALTER TABLE "public"."apel_application" ADD FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."apel_application" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."apel_application" ADD FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."apel_application_comment"
-- ----------------------------
ALTER TABLE "public"."apel_application_comment" ADD FOREIGN KEY ("apel_application_id") REFERENCES "public"."apel_application" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."apel_application_file"
-- ----------------------------
ALTER TABLE "public"."apel_application_file" ADD FOREIGN KEY ("apel_application_id") REFERENCES "public"."apel_application" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."apel_application_file" ADD FOREIGN KEY ("ois_file_id") REFERENCES "public"."ois_file" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."apel_application_formal_replaced_subject_or_module"
-- ----------------------------
ALTER TABLE "public"."apel_application_formal_replaced_subject_or_module" ADD FOREIGN KEY ("subject_id") REFERENCES "public"."subject" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."apel_application_formal_replaced_subject_or_module" ADD FOREIGN KEY ("apel_application_record_id") REFERENCES "public"."apel_application_record" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."apel_application_formal_replaced_subject_or_module" ADD FOREIGN KEY ("curriculum_version_omodule_id") REFERENCES "public"."curriculum_version_omodule" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."apel_application_formal_subject_or_module"
-- ----------------------------
ALTER TABLE "public"."apel_application_formal_subject_or_module" ADD FOREIGN KEY ("type_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."apel_application_formal_subject_or_module" ADD FOREIGN KEY ("curriculum_version_omodule_id") REFERENCES "public"."curriculum_version_omodule" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."apel_application_formal_subject_or_module" ADD FOREIGN KEY ("grade_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."apel_application_formal_subject_or_module" ADD FOREIGN KEY ("assessment_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."apel_application_formal_subject_or_module" ADD FOREIGN KEY ("curriculum_version_hmodule_id") REFERENCES "public"."curriculum_version_hmodule" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."apel_application_formal_subject_or_module" ADD FOREIGN KEY ("apel_school_id") REFERENCES "public"."apel_school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."apel_application_formal_subject_or_module" ADD FOREIGN KEY ("subject_id") REFERENCES "public"."subject" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."apel_application_formal_subject_or_module" ADD FOREIGN KEY ("apel_application_record_id") REFERENCES "public"."apel_application_record" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."apel_application_informal_experience"
-- ----------------------------
ALTER TABLE "public"."apel_application_informal_experience" ADD FOREIGN KEY ("type_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."apel_application_informal_experience" ADD FOREIGN KEY ("apel_application_record_id") REFERENCES "public"."apel_application_record" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."apel_application_informal_subject_or_module"
-- ----------------------------
ALTER TABLE "public"."apel_application_informal_subject_or_module" ADD FOREIGN KEY ("subject_id") REFERENCES "public"."subject" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."apel_application_informal_subject_or_module" ADD FOREIGN KEY ("apel_application_record_id") REFERENCES "public"."apel_application_record" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."apel_application_informal_subject_or_module" ADD FOREIGN KEY ("curriculum_version_omodule_theme_id") REFERENCES "public"."curriculum_version_omodule_theme" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."apel_application_informal_subject_or_module" ADD FOREIGN KEY ("curriculum_version_omodule_id") REFERENCES "public"."curriculum_version_omodule" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."apel_application_informal_subject_or_module" ADD FOREIGN KEY ("grade_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."apel_application_informal_subject_or_module" ADD FOREIGN KEY ("curriculum_version_hmodule_id") REFERENCES "public"."curriculum_version_hmodule" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."apel_application_informal_subject_or_module_outcomes"
-- ----------------------------
ALTER TABLE "public"."apel_application_informal_subject_or_module_outcomes" ADD FOREIGN KEY ("apel_application_informal_subject_or_module_id") REFERENCES "public"."apel_application_informal_subject_or_module" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."apel_application_informal_subject_or_module_outcomes" ADD FOREIGN KEY ("curriculum_module_outcomes_id") REFERENCES "public"."curriculum_module_outcomes" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."apel_application_record"
-- ----------------------------
ALTER TABLE "public"."apel_application_record" ADD FOREIGN KEY ("apel_application_id") REFERENCES "public"."apel_application" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."apel_school"
-- ----------------------------
ALTER TABLE "public"."apel_school" ADD FOREIGN KEY ("ehis_school_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."apel_school" ADD FOREIGN KEY ("country_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."apel_school" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."application"
-- ----------------------------
ALTER TABLE "public"."application" ADD FOREIGN KEY ("abroad_purpose_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application" ADD FOREIGN KEY ("ehis_school_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application" ADD FOREIGN KEY ("study_period_end_id") REFERENCES "public"."study_period" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application" ADD FOREIGN KEY ("new_fin_specific_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application" ADD FOREIGN KEY ("type_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application" ADD FOREIGN KEY ("reason_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application" ADD FOREIGN KEY ("new_curriculum_version_id") REFERENCES "public"."curriculum_version" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application" ADD FOREIGN KEY ("old_curriculum_version_id") REFERENCES "public"."curriculum_version" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application" ADD FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application" ADD FOREIGN KEY ("old_fin_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application" ADD FOREIGN KEY ("new_fin_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application" ADD FOREIGN KEY ("academic_application_id") REFERENCES "public"."application" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application" ADD FOREIGN KEY ("abroad_programme_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application" ADD FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application" ADD FOREIGN KEY ("old_fin_specific_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application" ADD FOREIGN KEY ("study_period_start_id") REFERENCES "public"."study_period" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application" ADD FOREIGN KEY ("old_study_form_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application" ADD FOREIGN KEY ("country_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application" ADD FOREIGN KEY ("new_study_form_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."application_file"
-- ----------------------------
ALTER TABLE "public"."application_file" ADD FOREIGN KEY ("ois_file_id") REFERENCES "public"."ois_file" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."application_planned_subject_equivalent"
-- ----------------------------
ALTER TABLE "public"."application_planned_subject_equivalent" ADD FOREIGN KEY ("application_planned_subject_id") REFERENCES "public"."application_planned_subject" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application_planned_subject_equivalent" ADD FOREIGN KEY ("subject_id") REFERENCES "public"."subject" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."building"
-- ----------------------------
ALTER TABLE "public"."building" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."certificate"
-- ----------------------------
ALTER TABLE "public"."certificate" ADD FOREIGN KEY ("type_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."certificate" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."certificate" ADD FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."certificate" ADD FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."classifier"
-- ----------------------------
ALTER TABLE "public"."classifier" ADD FOREIGN KEY ("parent_class_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."classifier" ADD FOREIGN KEY ("main_class_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."classifier_connect"
-- ----------------------------
ALTER TABLE "public"."classifier_connect" ADD FOREIGN KEY ("main_classifier_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."classifier_connect" ADD FOREIGN KEY ("classifier_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."classifier_connect" ADD FOREIGN KEY ("connect_classifier_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."committee"
-- ----------------------------
ALTER TABLE "public"."committee" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."committee_member"
-- ----------------------------
ALTER TABLE "public"."committee_member" ADD FOREIGN KEY ("committee_id") REFERENCES "public"."committee" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."committee_member" ADD FOREIGN KEY ("teacher_id") REFERENCES "public"."teacher" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."contract"
-- ----------------------------
ALTER TABLE "public"."contract" ADD FOREIGN KEY ("contract_coordinator_id") REFERENCES "public"."directive_coordinator" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."contract" ADD FOREIGN KEY ("curriculum_version_omodule_id") REFERENCES "public"."curriculum_version_omodule" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."contract" ADD FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."contract" ADD FOREIGN KEY ("subject_id") REFERENCES "public"."subject" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."contract" ADD FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."contract" ADD FOREIGN KEY ("enterprise_id") REFERENCES "public"."enterprise" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."contract" ADD FOREIGN KEY ("curriculum_version_omodule_theme_id") REFERENCES "public"."curriculum_version_omodule_theme" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."contract" ADD FOREIGN KEY ("teacher_id") REFERENCES "public"."teacher" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum"
-- ----------------------------
ALTER TABLE "public"."curriculum" ADD FOREIGN KEY ("ehis_status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum" ADD FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum" ADD FOREIGN KEY ("orig_study_level_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum" ADD FOREIGN KEY ("joint_mentor_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum" ADD FOREIGN KEY ("state_curriculum_id") REFERENCES "public"."state_curriculum" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum" ADD FOREIGN KEY ("consecution_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum" ADD FOREIGN KEY ("draft_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum" ADD FOREIGN KEY ("isced_class_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

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
ALTER TABLE "public"."curriculum_grade" ADD FOREIGN KEY ("curriculum_id") REFERENCES "public"."curriculum" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_grade" ADD FOREIGN KEY ("ehis_grade_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

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
ALTER TABLE "public"."curriculum_module_occupation" ADD FOREIGN KEY ("occupation_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_module_occupation" ADD FOREIGN KEY ("curriculum_module_id") REFERENCES "public"."curriculum_module" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum_module_outcomes"
-- ----------------------------
ALTER TABLE "public"."curriculum_module_outcomes" ADD FOREIGN KEY ("curriculum_module_id") REFERENCES "public"."curriculum_module" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum_occupation"
-- ----------------------------
ALTER TABLE "public"."curriculum_occupation" ADD FOREIGN KEY ("curriculum_id") REFERENCES "public"."curriculum" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_occupation" ADD FOREIGN KEY ("occupation_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum_occupation_speciality"
-- ----------------------------
ALTER TABLE "public"."curriculum_occupation_speciality" ADD FOREIGN KEY ("speciality_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_occupation_speciality" ADD FOREIGN KEY ("curriculum_occupation_id") REFERENCES "public"."curriculum_occupation" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

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
-- Foreign Key structure for table "public"."curriculum_version"
-- ----------------------------
ALTER TABLE "public"."curriculum_version" ADD FOREIGN KEY ("type_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_version" ADD FOREIGN KEY ("school_department_id") REFERENCES "public"."school_department" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_version" ADD FOREIGN KEY ("curriculum_id") REFERENCES "public"."curriculum" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_version" ADD FOREIGN KEY ("curriculum_study_form_id") REFERENCES "public"."curriculum_study_form" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_version" ADD FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum_version_elective_module"
-- ----------------------------
ALTER TABLE "public"."curriculum_version_elective_module" ADD FOREIGN KEY ("curriculum_version_hmodule_id") REFERENCES "public"."curriculum_version_hmodule" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum_version_hmodule"
-- ----------------------------
ALTER TABLE "public"."curriculum_version_hmodule" ADD FOREIGN KEY ("curriculum_version_id") REFERENCES "public"."curriculum_version" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_version_hmodule" ADD FOREIGN KEY ("type_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum_version_hmodule_speciality"
-- ----------------------------
ALTER TABLE "public"."curriculum_version_hmodule_speciality" ADD FOREIGN KEY ("curriculum_version_speciality_id") REFERENCES "public"."curriculum_version_speciality" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_version_hmodule_speciality" ADD FOREIGN KEY ("curriculum_version_hmodule_id") REFERENCES "public"."curriculum_version_hmodule" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum_version_hmodule_subject"
-- ----------------------------
ALTER TABLE "public"."curriculum_version_hmodule_subject" ADD FOREIGN KEY ("curriculum_version_hmodule_id") REFERENCES "public"."curriculum_version_hmodule" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_version_hmodule_subject" ADD FOREIGN KEY ("curriculum_version_elective_module_id") REFERENCES "public"."curriculum_version_elective_module" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_version_hmodule_subject" ADD FOREIGN KEY ("subject_id") REFERENCES "public"."subject" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum_version_omodule"
-- ----------------------------
ALTER TABLE "public"."curriculum_version_omodule" ADD FOREIGN KEY ("assessment_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_version_omodule" ADD FOREIGN KEY ("curriculum_module_id") REFERENCES "public"."curriculum_module" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_version_omodule" ADD FOREIGN KEY ("curriculum_version_id") REFERENCES "public"."curriculum_version" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum_version_omodule_capacity"
-- ----------------------------
ALTER TABLE "public"."curriculum_version_omodule_capacity" ADD FOREIGN KEY ("curriculum_version_omodule_id") REFERENCES "public"."curriculum_version_omodule" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_version_omodule_capacity" ADD FOREIGN KEY ("capacity_type_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum_version_omodule_outcomes"
-- ----------------------------
ALTER TABLE "public"."curriculum_version_omodule_outcomes" ADD FOREIGN KEY ("curriculum_module_outcomes_id") REFERENCES "public"."curriculum_module_outcomes" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_version_omodule_outcomes" ADD FOREIGN KEY ("curriculum_version_omodule_theme_id") REFERENCES "public"."curriculum_version_omodule_theme" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum_version_omodule_theme"
-- ----------------------------
ALTER TABLE "public"."curriculum_version_omodule_theme" ADD FOREIGN KEY ("curriculum_version_omodule_id") REFERENCES "public"."curriculum_version_omodule" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_version_omodule_theme" ADD FOREIGN KEY ("assessment_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum_version_omodule_theme_capacity"
-- ----------------------------
ALTER TABLE "public"."curriculum_version_omodule_theme_capacity" ADD FOREIGN KEY ("capacity_type_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_version_omodule_theme_capacity" ADD FOREIGN KEY ("curriculum_version_omodule_theme_id") REFERENCES "public"."curriculum_version_omodule_theme" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum_version_omodule_year_capacity"
-- ----------------------------
ALTER TABLE "public"."curriculum_version_omodule_year_capacity" ADD FOREIGN KEY ("curriculum_version_omodule_id") REFERENCES "public"."curriculum_version_omodule" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum_version_speciality"
-- ----------------------------
ALTER TABLE "public"."curriculum_version_speciality" ADD FOREIGN KEY ("curriculum_version_id") REFERENCES "public"."curriculum_version" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_version_speciality" ADD FOREIGN KEY ("curriculum_speciality_id") REFERENCES "public"."curriculum_speciality" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."declaration"
-- ----------------------------
ALTER TABLE "public"."declaration" ADD FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."declaration" ADD FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."declaration" ADD FOREIGN KEY ("study_period_id") REFERENCES "public"."study_period" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."declaration_subject"
-- ----------------------------
ALTER TABLE "public"."declaration_subject" ADD FOREIGN KEY ("declaration_id") REFERENCES "public"."declaration" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."declaration_subject" ADD FOREIGN KEY ("subject_study_period_id") REFERENCES "public"."subject_study_period" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."declaration_subject" ADD FOREIGN KEY ("curriculum_version_hmodule_id") REFERENCES "public"."curriculum_version_hmodule" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."directive"
-- ----------------------------
ALTER TABLE "public"."directive" ADD FOREIGN KEY ("cancel_type_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive" ADD FOREIGN KEY ("directive_coordinator_id") REFERENCES "public"."directive_coordinator" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive" ADD FOREIGN KEY ("type_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive" ADD FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive" ADD FOREIGN KEY ("canceled_directive_id") REFERENCES "public"."directive" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."directive_coordinator"
-- ----------------------------
ALTER TABLE "public"."directive_coordinator" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."directive_student"
-- ----------------------------
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("country_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("curriculum_version_id") REFERENCES "public"."curriculum_version" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("study_load_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("student_history_id") REFERENCES "public"."student_history" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("study_period_start_id") REFERENCES "public"."study_period" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("curriculum_grade_id") REFERENCES "public"."curriculum_grade" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("reason_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("study_period_end_id") REFERENCES "public"."study_period" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("fin_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("abroad_programme_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("study_form_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("sais_application_id") REFERENCES "public"."sais_application" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("language_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("directive_id") REFERENCES "public"."directive" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("student_group_id") REFERENCES "public"."student_group" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("abroad_purpose_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("state_language_ects_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("person_id") REFERENCES "public"."person" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("previous_study_level_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("ehis_school_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("fin_specific_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."general_message"
-- ----------------------------
ALTER TABLE "public"."general_message" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."general_message_target"
-- ----------------------------
ALTER TABLE "public"."general_message_target" ADD FOREIGN KEY ("role_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."general_message_target" ADD FOREIGN KEY ("general_message_id") REFERENCES "public"."general_message" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."job"
-- ----------------------------
ALTER TABLE "public"."job" ADD FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."job" ADD FOREIGN KEY ("contract_id") REFERENCES "public"."contract" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."job" ADD FOREIGN KEY ("directive_id") REFERENCES "public"."directive" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."job" ADD FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."job" ADD FOREIGN KEY ("type_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."job" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."journal"
-- ----------------------------
ALTER TABLE "public"."journal" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."journal" ADD FOREIGN KEY ("group_proportion_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."journal" ADD FOREIGN KEY ("assessment_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."journal" ADD FOREIGN KEY ("study_year_id") REFERENCES "public"."study_year" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."journal" ADD FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."journal_capacity"
-- ----------------------------
ALTER TABLE "public"."journal_capacity" ADD FOREIGN KEY ("journal_capacity_type_id") REFERENCES "public"."journal_capacity_type" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."journal_capacity" ADD FOREIGN KEY ("journal_id") REFERENCES "public"."journal" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."journal_capacity" ADD FOREIGN KEY ("study_period_id") REFERENCES "public"."study_period" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."journal_capacity_type"
-- ----------------------------
ALTER TABLE "public"."journal_capacity_type" ADD FOREIGN KEY ("journal_id") REFERENCES "public"."journal" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."journal_capacity_type" ADD FOREIGN KEY ("capacity_type_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."journal_entry"
-- ----------------------------
ALTER TABLE "public"."journal_entry" ADD FOREIGN KEY ("journal_id") REFERENCES "public"."journal" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."journal_entry" ADD FOREIGN KEY ("entry_type_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."journal_entry_capacity_type"
-- ----------------------------
ALTER TABLE "public"."journal_entry_capacity_type" ADD FOREIGN KEY ("journal_entry_id") REFERENCES "public"."journal_entry" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."journal_entry_capacity_type" ADD FOREIGN KEY ("capacity_type_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."journal_entry_student"
-- ----------------------------
ALTER TABLE "public"."journal_entry_student" ADD FOREIGN KEY ("journal_entry_id") REFERENCES "public"."journal_entry" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."journal_entry_student" ADD FOREIGN KEY ("grade_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."journal_entry_student" ADD FOREIGN KEY ("absence_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."journal_entry_student" ADD FOREIGN KEY ("journal_student_id") REFERENCES "public"."journal_student" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."journal_entry_student_history"
-- ----------------------------
ALTER TABLE "public"."journal_entry_student_history" ADD FOREIGN KEY ("journal_entry_student_id") REFERENCES "public"."journal_entry_student" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."journal_entry_student_history" ADD FOREIGN KEY ("grade_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."journal_omodule_theme"
-- ----------------------------
ALTER TABLE "public"."journal_omodule_theme" ADD FOREIGN KEY ("journal_id") REFERENCES "public"."journal" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."journal_omodule_theme" ADD FOREIGN KEY ("curriculum_version_omodule_theme_id") REFERENCES "public"."curriculum_version_omodule_theme" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."journal_omodule_theme" ADD FOREIGN KEY ("lesson_plan_module_id") REFERENCES "public"."lesson_plan_module" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."journal_room"
-- ----------------------------
ALTER TABLE "public"."journal_room" ADD FOREIGN KEY ("journal_id") REFERENCES "public"."journal" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."journal_room" ADD FOREIGN KEY ("room_id") REFERENCES "public"."room" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."journal_student"
-- ----------------------------
ALTER TABLE "public"."journal_student" ADD FOREIGN KEY ("journal_id") REFERENCES "public"."journal" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."journal_student" ADD FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."journal_teacher"
-- ----------------------------
ALTER TABLE "public"."journal_teacher" ADD FOREIGN KEY ("teacher_id") REFERENCES "public"."teacher" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."journal_teacher" ADD FOREIGN KEY ("journal_id") REFERENCES "public"."journal" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."lesson_plan"
-- ----------------------------
ALTER TABLE "public"."lesson_plan" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."lesson_plan" ADD FOREIGN KEY ("study_year_id") REFERENCES "public"."study_year" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."lesson_plan" ADD FOREIGN KEY ("student_group_id") REFERENCES "public"."student_group" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."lesson_plan" ADD FOREIGN KEY ("curriculum_version_id") REFERENCES "public"."curriculum_version" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."lesson_plan_module"
-- ----------------------------
ALTER TABLE "public"."lesson_plan_module" ADD FOREIGN KEY ("teacher_id") REFERENCES "public"."teacher" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."lesson_plan_module" ADD FOREIGN KEY ("curriculum_version_omodule_id") REFERENCES "public"."curriculum_version_omodule" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."lesson_plan_module" ADD FOREIGN KEY ("lesson_plan_id") REFERENCES "public"."lesson_plan" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."lesson_time"
-- ----------------------------
ALTER TABLE "public"."lesson_time" ADD FOREIGN KEY ("lesson_time_building_group_id") REFERENCES "public"."lesson_time_building_group" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."lesson_time" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."lesson_time_building"
-- ----------------------------
ALTER TABLE "public"."lesson_time_building" ADD FOREIGN KEY ("lesson_time_building_group_id") REFERENCES "public"."lesson_time_building_group" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."lesson_time_building" ADD FOREIGN KEY ("building_id") REFERENCES "public"."building" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."message"
-- ----------------------------
ALTER TABLE "public"."message" ADD FOREIGN KEY ("message_id") REFERENCES "public"."message" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."message" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."message" ADD FOREIGN KEY ("role_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."message" ADD FOREIGN KEY ("person_id") REFERENCES "public"."person" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."message_receiver"
-- ----------------------------
ALTER TABLE "public"."message_receiver" ADD FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."message_receiver" ADD FOREIGN KEY ("message_id") REFERENCES "public"."message" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."message_receiver" ADD FOREIGN KEY ("person_id") REFERENCES "public"."person" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."message_template"
-- ----------------------------
ALTER TABLE "public"."message_template" ADD FOREIGN KEY ("type_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."message_template" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."midterm_task"
-- ----------------------------
ALTER TABLE "public"."midterm_task" ADD FOREIGN KEY ("subject_study_period_id") REFERENCES "public"."subject_study_period" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."midterm_task_student_result"
-- ----------------------------
ALTER TABLE "public"."midterm_task_student_result" ADD FOREIGN KEY ("declaration_subject_id") REFERENCES "public"."declaration_subject" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."midterm_task_student_result" ADD FOREIGN KEY ("midterm_task_id") REFERENCES "public"."midterm_task" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."person"
-- ----------------------------
ALTER TABLE "public"."person" ADD FOREIGN KEY ("citizenship_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."person" ADD FOREIGN KEY ("residence_country_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."person" ADD FOREIGN KEY ("sex_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."person" ADD FOREIGN KEY ("language_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."practice_journal"
-- ----------------------------
ALTER TABLE "public"."practice_journal" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."practice_journal" ADD FOREIGN KEY ("subject_id") REFERENCES "public"."subject" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."practice_journal" ADD FOREIGN KEY ("grade_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."practice_journal" ADD FOREIGN KEY ("curriculum_version_omodule_theme_id") REFERENCES "public"."curriculum_version_omodule_theme" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."practice_journal" ADD FOREIGN KEY ("contract_id") REFERENCES "public"."contract" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."practice_journal" ADD FOREIGN KEY ("teacher_id") REFERENCES "public"."teacher" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."practice_journal" ADD FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."practice_journal" ADD FOREIGN KEY ("curriculum_version_omodule_id") REFERENCES "public"."curriculum_version_omodule" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."practice_journal" ADD FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."practice_journal" ADD FOREIGN KEY ("study_year_id") REFERENCES "public"."study_year" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."practice_journal_entry"
-- ----------------------------
ALTER TABLE "public"."practice_journal_entry" ADD FOREIGN KEY ("practice_journal_id") REFERENCES "public"."practice_journal" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."practice_journal_file"
-- ----------------------------
ALTER TABLE "public"."practice_journal_file" ADD FOREIGN KEY ("practice_journal_id") REFERENCES "public"."practice_journal" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."practice_journal_file" ADD FOREIGN KEY ("ois_file_id") REFERENCES "public"."ois_file" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."protocol"
-- ----------------------------
ALTER TABLE "public"."protocol" ADD FOREIGN KEY ("ois_file_id") REFERENCES "public"."ois_file" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."protocol" ADD FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."protocol" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."protocol_hdata"
-- ----------------------------
ALTER TABLE "public"."protocol_hdata" ADD FOREIGN KEY ("type_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."protocol_hdata" ADD FOREIGN KEY ("subject_study_period_id") REFERENCES "public"."subject_study_period" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."protocol_hdata" ADD FOREIGN KEY ("protocol_id") REFERENCES "public"."protocol" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."protocol_student"
-- ----------------------------
ALTER TABLE "public"."protocol_student" ADD FOREIGN KEY ("protocol_id") REFERENCES "public"."protocol" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."protocol_student" ADD FOREIGN KEY ("grade_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."protocol_student" ADD FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."protocol_student_history"
-- ----------------------------
ALTER TABLE "public"."protocol_student_history" ADD FOREIGN KEY ("protocol_student_id") REFERENCES "public"."protocol_student" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."protocol_student_history" ADD FOREIGN KEY ("grade_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."protocol_vdata"
-- ----------------------------
ALTER TABLE "public"."protocol_vdata" ADD FOREIGN KEY ("study_year_id") REFERENCES "public"."study_year" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."protocol_vdata" ADD FOREIGN KEY ("protocol_id") REFERENCES "public"."protocol" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."protocol_vdata" ADD FOREIGN KEY ("curriculum_version_omodule_id") REFERENCES "public"."curriculum_version_omodule" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."protocol_vdata" ADD FOREIGN KEY ("teacher_id") REFERENCES "public"."teacher" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."protocol_vdata" ADD FOREIGN KEY ("curriculum_version_id") REFERENCES "public"."curriculum_version" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

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
-- Foreign Key structure for table "public"."sais_admission"
-- ----------------------------
ALTER TABLE "public"."sais_admission" ADD FOREIGN KEY ("fin_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."sais_admission" ADD FOREIGN KEY ("study_level_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."sais_admission" ADD FOREIGN KEY ("language_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."sais_admission" ADD FOREIGN KEY ("study_load_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."sais_admission" ADD FOREIGN KEY ("curriculum_version_id") REFERENCES "public"."curriculum_version" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."sais_admission" ADD FOREIGN KEY ("study_form_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."sais_application"
-- ----------------------------
ALTER TABLE "public"."sais_application" ADD FOREIGN KEY ("citizenship_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."sais_application" ADD FOREIGN KEY ("language_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."sais_application" ADD FOREIGN KEY ("sais_admission_id") REFERENCES "public"."sais_admission" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."sais_application" ADD FOREIGN KEY ("fin_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."sais_application" ADD FOREIGN KEY ("study_load_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."sais_application" ADD FOREIGN KEY ("residence_country_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."sais_application" ADD FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."sais_application" ADD FOREIGN KEY ("sex_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."sais_application" ADD FOREIGN KEY ("study_form_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."sais_application_grade"
-- ----------------------------
ALTER TABLE "public"."sais_application_grade" ADD FOREIGN KEY ("sais_application_id") REFERENCES "public"."sais_application" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."sais_application_graduated_school"
-- ----------------------------
ALTER TABLE "public"."sais_application_graduated_school" ADD FOREIGN KEY ("study_form_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."sais_application_graduated_school" ADD FOREIGN KEY ("sais_application_id") REFERENCES "public"."sais_application" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."sais_application_graduated_school" ADD FOREIGN KEY ("study_level_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."sais_application_other_data"
-- ----------------------------
ALTER TABLE "public"."sais_application_other_data" ADD FOREIGN KEY ("sais_application_id") REFERENCES "public"."sais_application" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."scholarship_application"
-- ----------------------------
ALTER TABLE "public"."scholarship_application" ADD FOREIGN KEY ("student_group_id") REFERENCES "public"."student_group" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."scholarship_application" ADD FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."scholarship_application" ADD FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."scholarship_application" ADD FOREIGN KEY ("compensation_reason_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."scholarship_application" ADD FOREIGN KEY ("scholarship_term_id") REFERENCES "public"."scholarship_term" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."scholarship_application" ADD FOREIGN KEY ("curriculum_version_id") REFERENCES "public"."curriculum_version" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."scholarship_application" ADD FOREIGN KEY ("compensation_frequency_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."scholarship_application_family"
-- ----------------------------
ALTER TABLE "public"."scholarship_application_family" ADD FOREIGN KEY ("scholarship_application_id") REFERENCES "public"."scholarship_application" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."scholarship_application_file"
-- ----------------------------
ALTER TABLE "public"."scholarship_application_file" ADD FOREIGN KEY ("scholarship_application_id") REFERENCES "public"."scholarship_application" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."scholarship_application_file" ADD FOREIGN KEY ("ois_file_id") REFERENCES "public"."ois_file" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."scholarship_term"
-- ----------------------------
ALTER TABLE "public"."scholarship_term" ADD FOREIGN KEY ("last_period_mark_priority_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."scholarship_term" ADD FOREIGN KEY ("max_absences_priority_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."scholarship_term" ADD FOREIGN KEY ("curriculum_completion_priority_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."scholarship_term" ADD FOREIGN KEY ("study_period_id") REFERENCES "public"."study_period" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."scholarship_term" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."scholarship_term" ADD FOREIGN KEY ("type_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."scholarship_term" ADD FOREIGN KEY ("average_mark_priority_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."scholarship_term_course"
-- ----------------------------
ALTER TABLE "public"."scholarship_term_course" ADD FOREIGN KEY ("course_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."scholarship_term_course" ADD FOREIGN KEY ("scholarship_term_id") REFERENCES "public"."scholarship_term" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."scholarship_term_curriculum"
-- ----------------------------
ALTER TABLE "public"."scholarship_term_curriculum" ADD FOREIGN KEY ("scholarship_term_id") REFERENCES "public"."scholarship_term" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."scholarship_term_curriculum" ADD FOREIGN KEY ("curriculum_id") REFERENCES "public"."curriculum" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."scholarship_term_study_form"
-- ----------------------------
ALTER TABLE "public"."scholarship_term_study_form" ADD FOREIGN KEY ("scholarship_term_id") REFERENCES "public"."scholarship_term" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."scholarship_term_study_form" ADD FOREIGN KEY ("study_form_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."scholarship_term_study_load"
-- ----------------------------
ALTER TABLE "public"."scholarship_term_study_load" ADD FOREIGN KEY ("study_load_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."scholarship_term_study_load" ADD FOREIGN KEY ("scholarship_term_id") REFERENCES "public"."scholarship_term" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

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
ALTER TABLE "public"."state_curriculum" ADD FOREIGN KEY ("state_curr_class_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."state_curriculum" ADD FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

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
ALTER TABLE "public"."state_curriculum_occupation" ADD FOREIGN KEY ("state_curriculum_id") REFERENCES "public"."state_curriculum" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."state_curriculum_occupation" ADD FOREIGN KEY ("occupation_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."student"
-- ----------------------------
ALTER TABLE "public"."student" ADD FOREIGN KEY ("student_history_id") REFERENCES "public"."student_history" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student" ADD FOREIGN KEY ("curriculum_speciality_id") REFERENCES "public"."curriculum_speciality" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student" ADD FOREIGN KEY ("study_form_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student" ADD FOREIGN KEY ("student_group_id") REFERENCES "public"."student_group" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student" ADD FOREIGN KEY ("ois_file_id") REFERENCES "public"."ois_file" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student" ADD FOREIGN KEY ("study_load_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student" ADD FOREIGN KEY ("fin_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student" ADD FOREIGN KEY ("fin_specific_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student" ADD FOREIGN KEY ("person_id") REFERENCES "public"."person" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student" ADD FOREIGN KEY ("special_need_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student" ADD FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student" ADD FOREIGN KEY ("language_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student" ADD FOREIGN KEY ("curriculum_version_id") REFERENCES "public"."curriculum_version" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student" ADD FOREIGN KEY ("previous_study_level_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."student_absence"
-- ----------------------------
ALTER TABLE "public"."student_absence" ADD FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."student_curriculum_completion"
-- ----------------------------
ALTER TABLE "public"."student_curriculum_completion" ADD FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."student_group"
-- ----------------------------
ALTER TABLE "public"."student_group" ADD FOREIGN KEY ("curriculum_version_id") REFERENCES "public"."curriculum_version" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_group" ADD FOREIGN KEY ("study_form_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_group" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_group" ADD FOREIGN KEY ("teacher_id") REFERENCES "public"."teacher" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_group" ADD FOREIGN KEY ("language_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_group" ADD FOREIGN KEY ("speciality_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_group" ADD FOREIGN KEY ("curriculum_id") REFERENCES "public"."curriculum" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."student_higher_result"
-- ----------------------------
ALTER TABLE "public"."student_higher_result" ADD FOREIGN KEY ("subject_id") REFERENCES "public"."subject" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_higher_result" ADD FOREIGN KEY ("apel_school_id") REFERENCES "public"."apel_school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_higher_result" ADD FOREIGN KEY ("grade_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_higher_result" ADD FOREIGN KEY ("curriculum_version_hmodule_id") REFERENCES "public"."curriculum_version_hmodule" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_higher_result" ADD FOREIGN KEY ("protocol_student_id") REFERENCES "public"."protocol_student" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_higher_result" ADD FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_higher_result" ADD FOREIGN KEY ("apel_application_record_id") REFERENCES "public"."apel_application_record" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."student_history"
-- ----------------------------
ALTER TABLE "public"."student_history" ADD FOREIGN KEY ("language_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_history" ADD FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_history" ADD FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_history" ADD FOREIGN KEY ("prev_student_history_id") REFERENCES "public"."student_history" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_history" ADD FOREIGN KEY ("special_need_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_history" ADD FOREIGN KEY ("ois_file_id") REFERENCES "public"."ois_file" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_history" ADD FOREIGN KEY ("curriculum_speciality_id") REFERENCES "public"."curriculum_speciality" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_history" ADD FOREIGN KEY ("fin_specific_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_history" ADD FOREIGN KEY ("curriculum_version_id") REFERENCES "public"."curriculum_version" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_history" ADD FOREIGN KEY ("study_form_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_history" ADD FOREIGN KEY ("study_load_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_history" ADD FOREIGN KEY ("fin_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_history" ADD FOREIGN KEY ("student_group_id") REFERENCES "public"."student_group" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_history" ADD FOREIGN KEY ("previous_study_level_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."student_occupation_certificate"
-- ----------------------------
ALTER TABLE "public"."student_occupation_certificate" ADD FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_occupation_certificate" ADD FOREIGN KEY ("part_occupation_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_occupation_certificate" ADD FOREIGN KEY ("speciality_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_occupation_certificate" ADD FOREIGN KEY ("occupation_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."student_representative"
-- ----------------------------
ALTER TABLE "public"."student_representative" ADD FOREIGN KEY ("relation_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_representative" ADD FOREIGN KEY ("person_id") REFERENCES "public"."person" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_representative" ADD FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."student_representative_application"
-- ----------------------------
ALTER TABLE "public"."student_representative_application" ADD FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_representative_application" ADD FOREIGN KEY ("person_id") REFERENCES "public"."person" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_representative_application" ADD FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_representative_application" ADD FOREIGN KEY ("relation_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."student_vocational_result"
-- ----------------------------
ALTER TABLE "public"."student_vocational_result" ADD FOREIGN KEY ("apel_application_record_id") REFERENCES "public"."apel_application_record" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_vocational_result" ADD FOREIGN KEY ("protocol_student_id") REFERENCES "public"."protocol_student" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_vocational_result" ADD FOREIGN KEY ("grade_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_vocational_result" ADD FOREIGN KEY ("curriculum_version_omodule_id") REFERENCES "public"."curriculum_version_omodule" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_vocational_result" ADD FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_vocational_result" ADD FOREIGN KEY ("apel_school_id") REFERENCES "public"."apel_school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."study_period"
-- ----------------------------
ALTER TABLE "public"."study_period" ADD FOREIGN KEY ("study_year_id") REFERENCES "public"."study_year" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."study_period" ADD FOREIGN KEY ("type_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."study_period_event"
-- ----------------------------
ALTER TABLE "public"."study_period_event" ADD FOREIGN KEY ("study_year_id") REFERENCES "public"."study_year" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."study_period_event" ADD FOREIGN KEY ("study_period_id") REFERENCES "public"."study_period" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."study_period_event" ADD FOREIGN KEY ("event_type_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."study_year"
-- ----------------------------
ALTER TABLE "public"."study_year" ADD FOREIGN KEY ("year_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."study_year" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."study_year_schedule"
-- ----------------------------
ALTER TABLE "public"."study_year_schedule" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."study_year_schedule" ADD FOREIGN KEY ("study_year_schedule_legend_id") REFERENCES "public"."study_year_schedule_legend" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."study_year_schedule" ADD FOREIGN KEY ("study_period_id") REFERENCES "public"."study_period" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."study_year_schedule" ADD FOREIGN KEY ("student_group_id") REFERENCES "public"."student_group" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."study_year_schedule_legend"
-- ----------------------------
ALTER TABLE "public"."study_year_schedule_legend" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."subject"
-- ----------------------------
ALTER TABLE "public"."subject" ADD FOREIGN KEY ("assessment_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."subject" ADD FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."subject" ADD FOREIGN KEY ("school_department_id") REFERENCES "public"."school_department" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."subject" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."subject_connect"
-- ----------------------------
ALTER TABLE "public"."subject_connect" ADD FOREIGN KEY ("connection_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."subject_connect" ADD FOREIGN KEY ("primary_subject_id") REFERENCES "public"."subject" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."subject_connect" ADD FOREIGN KEY ("connect_subject_id") REFERENCES "public"."subject" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."subject_lang"
-- ----------------------------
ALTER TABLE "public"."subject_lang" ADD FOREIGN KEY ("lang_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."subject_lang" ADD FOREIGN KEY ("subject_id") REFERENCES "public"."subject" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."subject_study_period"
-- ----------------------------
ALTER TABLE "public"."subject_study_period" ADD FOREIGN KEY ("group_proportion_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."subject_study_period" ADD FOREIGN KEY ("subject_id") REFERENCES "public"."subject" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."subject_study_period" ADD FOREIGN KEY ("declaration_type_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."subject_study_period" ADD FOREIGN KEY ("study_period_id") REFERENCES "public"."study_period" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."subject_study_period_capacity"
-- ----------------------------
ALTER TABLE "public"."subject_study_period_capacity" ADD FOREIGN KEY ("capacity_type_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."subject_study_period_capacity" ADD FOREIGN KEY ("subject_study_period_id") REFERENCES "public"."subject_study_period" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."subject_study_period_plan"
-- ----------------------------
ALTER TABLE "public"."subject_study_period_plan" ADD FOREIGN KEY ("study_period_id") REFERENCES "public"."study_period" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."subject_study_period_plan" ADD FOREIGN KEY ("subject_id") REFERENCES "public"."subject" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."subject_study_period_plan_capacity"
-- ----------------------------
ALTER TABLE "public"."subject_study_period_plan_capacity" ADD FOREIGN KEY ("capacity_type_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."subject_study_period_plan_capacity" ADD FOREIGN KEY ("subject_study_period_plan_id") REFERENCES "public"."subject_study_period_plan" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."subject_study_period_plan_curriculum"
-- ----------------------------
ALTER TABLE "public"."subject_study_period_plan_curriculum" ADD FOREIGN KEY ("subject_study_period_plan_id") REFERENCES "public"."subject_study_period_plan" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."subject_study_period_plan_curriculum" ADD FOREIGN KEY ("curriculum_id") REFERENCES "public"."curriculum" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."subject_study_period_plan_studyform"
-- ----------------------------
ALTER TABLE "public"."subject_study_period_plan_studyform" ADD FOREIGN KEY ("subject_study_period_plan_id") REFERENCES "public"."subject_study_period_plan" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."subject_study_period_plan_studyform" ADD FOREIGN KEY ("study_form_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."subject_study_period_student_group"
-- ----------------------------
ALTER TABLE "public"."subject_study_period_student_group" ADD FOREIGN KEY ("student_group_id") REFERENCES "public"."student_group" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."subject_study_period_student_group" ADD FOREIGN KEY ("subject_study_period_id") REFERENCES "public"."subject_study_period" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."subject_study_period_teacher"
-- ----------------------------
ALTER TABLE "public"."subject_study_period_teacher" ADD FOREIGN KEY ("teacher_id") REFERENCES "public"."teacher" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."subject_study_period_teacher" ADD FOREIGN KEY ("subject_study_period_id") REFERENCES "public"."subject_study_period" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."teacher"
-- ----------------------------
ALTER TABLE "public"."teacher" ADD FOREIGN KEY ("teacher_occupation_id") REFERENCES "public"."teacher_occupation" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."teacher" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."teacher" ADD FOREIGN KEY ("person_id") REFERENCES "public"."person" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."teacher_absence"
-- ----------------------------
ALTER TABLE "public"."teacher_absence" ADD FOREIGN KEY ("teacher_id") REFERENCES "public"."teacher" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."teacher_continuing_education"
-- ----------------------------
ALTER TABLE "public"."teacher_continuing_education" ADD FOREIGN KEY ("field_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."teacher_continuing_education" ADD FOREIGN KEY ("diploma_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."teacher_continuing_education" ADD FOREIGN KEY ("school_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."teacher_continuing_education" ADD FOREIGN KEY ("teacher_id") REFERENCES "public"."teacher" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."teacher_mobility"
-- ----------------------------
ALTER TABLE "public"."teacher_mobility" ADD FOREIGN KEY ("state_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."teacher_mobility" ADD FOREIGN KEY ("teacher_id") REFERENCES "public"."teacher" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."teacher_mobility" ADD FOREIGN KEY ("target_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."teacher_occupation"
-- ----------------------------
ALTER TABLE "public"."teacher_occupation" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."teacher_position_ehis"
-- ----------------------------
ALTER TABLE "public"."teacher_position_ehis" ADD FOREIGN KEY ("teacher_id") REFERENCES "public"."teacher" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."teacher_position_ehis" ADD FOREIGN KEY ("school_department_id") REFERENCES "public"."school_department" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."teacher_position_ehis" ADD FOREIGN KEY ("employment_type_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."teacher_position_ehis" ADD FOREIGN KEY ("language_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."teacher_position_ehis" ADD FOREIGN KEY ("contract_type_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."teacher_position_ehis" ADD FOREIGN KEY ("position_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."teacher_qualification"
-- ----------------------------
ALTER TABLE "public"."teacher_qualification" ADD FOREIGN KEY ("study_level_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."teacher_qualification" ADD FOREIGN KEY ("teacher_id") REFERENCES "public"."teacher" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."teacher_qualification" ADD FOREIGN KEY ("state_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."teacher_qualification" ADD FOREIGN KEY ("ex_school_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."teacher_qualification" ADD FOREIGN KEY ("school_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."teacher_qualification" ADD FOREIGN KEY ("qualification_name_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."teacher_qualification" ADD FOREIGN KEY ("qualification_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."teacher_qualification" ADD FOREIGN KEY ("language_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."timetable"
-- ----------------------------
ALTER TABLE "public"."timetable" ADD FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."timetable" ADD FOREIGN KEY ("study_period_id") REFERENCES "public"."study_period" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."timetable" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."timetable_event"
-- ----------------------------
ALTER TABLE "public"."timetable_event" ADD FOREIGN KEY ("repeat_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."timetable_event" ADD FOREIGN KEY ("subject_study_period_student_group_id") REFERENCES "public"."subject_study_period_student_group" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."timetable_event" ADD FOREIGN KEY ("timetable_object_id") REFERENCES "public"."timetable_object" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."timetable_event" ADD FOREIGN KEY ("capacity_type_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."timetable_event_room"
-- ----------------------------
ALTER TABLE "public"."timetable_event_room" ADD FOREIGN KEY ("room_id") REFERENCES "public"."room" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."timetable_event_room" ADD FOREIGN KEY ("timetable_event_time_id") REFERENCES "public"."timetable_event_time" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."timetable_event_teacher"
-- ----------------------------
ALTER TABLE "public"."timetable_event_teacher" ADD FOREIGN KEY ("teacher_id") REFERENCES "public"."teacher" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."timetable_event_teacher" ADD FOREIGN KEY ("timetable_event_time_id") REFERENCES "public"."timetable_event_time" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."timetable_event_time"
-- ----------------------------
ALTER TABLE "public"."timetable_event_time" ADD FOREIGN KEY ("timetable_event_id") REFERENCES "public"."timetable_event" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."timetable_object"
-- ----------------------------
ALTER TABLE "public"."timetable_object" ADD FOREIGN KEY ("subject_study_period_id") REFERENCES "public"."subject_study_period" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."timetable_object" ADD FOREIGN KEY ("timetable_id") REFERENCES "public"."timetable" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."timetable_object" ADD FOREIGN KEY ("journal_id") REFERENCES "public"."journal" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."timetable_object_student_group"
-- ----------------------------
ALTER TABLE "public"."timetable_object_student_group" ADD FOREIGN KEY ("student_group_id") REFERENCES "public"."student_group" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."timetable_object_student_group" ADD FOREIGN KEY ("timetable_object_id") REFERENCES "public"."timetable_object" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."user_"
-- ----------------------------
ALTER TABLE "public"."user_" ADD FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."user_" ADD FOREIGN KEY ("role_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."user_" ADD FOREIGN KEY ("teacher_id") REFERENCES "public"."teacher" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."user_" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."user_" ADD FOREIGN KEY ("person_id") REFERENCES "public"."person" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."user_rights"
-- ----------------------------
ALTER TABLE "public"."user_rights" ADD FOREIGN KEY ("permission_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."user_rights" ADD FOREIGN KEY ("object_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."user_rights" ADD FOREIGN KEY ("user_id") REFERENCES "public"."user_" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."user_role_default"
-- ----------------------------
ALTER TABLE "public"."user_role_default" ADD FOREIGN KEY ("role_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."user_role_default" ADD FOREIGN KEY ("permission_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."user_role_default" ADD FOREIGN KEY ("object_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."user_sessions"
-- ----------------------------
ALTER TABLE "public"."user_sessions" ADD FOREIGN KEY ("type_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."ws_ehis_curriculum_log"
-- ----------------------------
ALTER TABLE "public"."ws_ehis_curriculum_log" ADD FOREIGN KEY ("curriculum_id") REFERENCES "public"."curriculum" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."ws_ehis_curriculum_log" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."ws_ehis_student_log"
-- ----------------------------
ALTER TABLE "public"."ws_ehis_student_log" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."ws_ehis_student_log" ADD FOREIGN KEY ("directive_id") REFERENCES "public"."directive" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."ws_ehis_teacher_log"
-- ----------------------------
ALTER TABLE "public"."ws_ehis_teacher_log" ADD FOREIGN KEY ("teacher_id") REFERENCES "public"."teacher" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."ws_ehis_teacher_log" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."ws_ekis_log"
-- ----------------------------
ALTER TABLE "public"."ws_ekis_log" ADD FOREIGN KEY ("contract_id") REFERENCES "public"."contract" ("id") ON DELETE SET NULL ON UPDATE NO ACTION;
ALTER TABLE "public"."ws_ekis_log" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE SET NULL ON UPDATE NO ACTION;
ALTER TABLE "public"."ws_ekis_log" ADD FOREIGN KEY ("directive_id") REFERENCES "public"."directive" ("id") ON DELETE SET NULL ON UPDATE NO ACTION;
ALTER TABLE "public"."ws_ekis_log" ADD FOREIGN KEY ("certificate_id") REFERENCES "public"."certificate" ("id") ON DELETE SET NULL ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."ws_qf_log"
-- ----------------------------
ALTER TABLE "public"."ws_qf_log" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."ws_rtip_log"
-- ----------------------------
ALTER TABLE "public"."ws_rtip_log" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."ws_sais_log"
-- ----------------------------
ALTER TABLE "public"."ws_sais_log" ADD FOREIGN KEY ("first_ws_sais_log_id") REFERENCES "public"."ws_sais_log" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."ws_sais_log" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."ws_sais_log_detail"
-- ----------------------------
ALTER TABLE "public"."ws_sais_log_detail" ADD FOREIGN KEY ("ws_sais_log_id") REFERENCES "public"."ws_sais_log" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;


--INSERT DATA
\i db_data.sql;