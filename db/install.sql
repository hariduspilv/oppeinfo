SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE datname = 'hois';

drop database if exists hois;

create database hois; 
\c hois;

DROP SEQUENCE IF EXISTS "public"."application_file_id_seq";
CREATE SEQUENCE "public"."application_file_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 39
 CACHE 1;
SELECT setval('"public"."application_file_id_seq"', 39, true);

-- ----------------------------
-- Sequence structure for application_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."application_id_seq";
CREATE SEQUENCE "public"."application_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 12
 CACHE 1;
SELECT setval('"public"."application_id_seq"', 12, true);

-- ----------------------------
-- Sequence structure for application_planned_subject_equivalent _id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."application_planned_subject_equivalent _id_seq";
CREATE SEQUENCE "public"."application_planned_subject_equivalent _id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;

-- ----------------------------
-- Sequence structure for application_planned_subject_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."application_planned_subject_id_seq";
CREATE SEQUENCE "public"."application_planned_subject_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;

-- ----------------------------
-- Sequence structure for building_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."building_id_seq";
CREATE SEQUENCE "public"."building_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 262
 CACHE 1;
SELECT setval('"public"."building_id_seq"', 262, true);

-- ----------------------------
-- Sequence structure for certificate_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."certificate_id_seq";
CREATE SEQUENCE "public"."certificate_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 81
 CACHE 1;
SELECT setval('"public"."certificate_id_seq"', 81, true);

-- ----------------------------
-- Sequence structure for curriculum_department_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_department_id_seq";
CREATE SEQUENCE "public"."curriculum_department_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1973
 CACHE 1;
SELECT setval('"public"."curriculum_department_id_seq"', 1973, true);

-- ----------------------------
-- Sequence structure for curriculum_files_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_files_id_seq";
CREATE SEQUENCE "public"."curriculum_files_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1894
 CACHE 1;
SELECT setval('"public"."curriculum_files_id_seq"', 1894, true);

-- ----------------------------
-- Sequence structure for curriculum_grade_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_grade_id_seq";
CREATE SEQUENCE "public"."curriculum_grade_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1736
 CACHE 1;
SELECT setval('"public"."curriculum_grade_id_seq"', 1736, true);

-- ----------------------------
-- Sequence structure for curriculum_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_id_seq";
CREATE SEQUENCE "public"."curriculum_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 3377
 CACHE 1;
SELECT setval('"public"."curriculum_id_seq"', 3377, true);

-- ----------------------------
-- Sequence structure for curriculum_joint_partners_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_joint_partners_id_seq";
CREATE SEQUENCE "public"."curriculum_joint_partners_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1900
 CACHE 1;
SELECT setval('"public"."curriculum_joint_partners_id_seq"', 1900, true);

-- ----------------------------
-- Sequence structure for curriculum_module_competence_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_module_competence_id_seq";
CREATE SEQUENCE "public"."curriculum_module_competence_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 3782
 CACHE 1;
SELECT setval('"public"."curriculum_module_competence_id_seq"', 3782, true);

-- ----------------------------
-- Sequence structure for curriculum_module_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_module_id_seq";
CREATE SEQUENCE "public"."curriculum_module_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 2109
 CACHE 1;
SELECT setval('"public"."curriculum_module_id_seq"', 2109, true);

-- ----------------------------
-- Sequence structure for curriculum_module_occupation_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_module_occupation_id_seq";
CREATE SEQUENCE "public"."curriculum_module_occupation_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 4096
 CACHE 1;
SELECT setval('"public"."curriculum_module_occupation_id_seq"', 4096, true);

-- ----------------------------
-- Sequence structure for curriculum_module_outcomes_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_module_outcomes_id_seq";
CREATE SEQUENCE "public"."curriculum_module_outcomes_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 3819
 CACHE 1;
SELECT setval('"public"."curriculum_module_outcomes_id_seq"', 3819, true);

-- ----------------------------
-- Sequence structure for curriculum_occupation_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_occupation_id_seq";
CREATE SEQUENCE "public"."curriculum_occupation_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1674
 CACHE 1;
SELECT setval('"public"."curriculum_occupation_id_seq"', 1674, true);

-- ----------------------------
-- Sequence structure for curriculum_occupation_speciality_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_occupation_speciality_id_seq";
CREATE SEQUENCE "public"."curriculum_occupation_speciality_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 3240
 CACHE 1;
SELECT setval('"public"."curriculum_occupation_speciality_id_seq"', 3240, true);

-- ----------------------------
-- Sequence structure for curriculum_speciality_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_speciality_id_seq";
CREATE SEQUENCE "public"."curriculum_speciality_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 2884
 CACHE 1;
SELECT setval('"public"."curriculum_speciality_id_seq"', 2884, true);

-- ----------------------------
-- Sequence structure for curriculum_study_form_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_study_form_id_seq";
CREATE SEQUENCE "public"."curriculum_study_form_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1994
 CACHE 1;
SELECT setval('"public"."curriculum_study_form_id_seq"', 1994, true);

-- ----------------------------
-- Sequence structure for curriculum_study_lang_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_study_lang_id_seq";
CREATE SEQUENCE "public"."curriculum_study_lang_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 4524
 CACHE 1;
SELECT setval('"public"."curriculum_study_lang_id_seq"', 4524, true);

-- ----------------------------
-- Sequence structure for curriculum_version_elective_module_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_version_elective_module_id_seq";
CREATE SEQUENCE "public"."curriculum_version_elective_module_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1236
 CACHE 1;
SELECT setval('"public"."curriculum_version_elective_module_id_seq"', 1236, true);

-- ----------------------------
-- Sequence structure for curriculum_version_hmodule_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_version_hmodule_id_seq";
CREATE SEQUENCE "public"."curriculum_version_hmodule_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 2044
 CACHE 1;
SELECT setval('"public"."curriculum_version_hmodule_id_seq"', 2044, true);

-- ----------------------------
-- Sequence structure for curriculum_version_hmodule_speciality_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_version_hmodule_speciality_id_seq";
CREATE SEQUENCE "public"."curriculum_version_hmodule_speciality_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1244
 CACHE 1;
SELECT setval('"public"."curriculum_version_hmodule_speciality_id_seq"', 1244, true);

-- ----------------------------
-- Sequence structure for curriculum_version_hmodule_subject_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_version_hmodule_subject_id_seq";
CREATE SEQUENCE "public"."curriculum_version_hmodule_subject_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 2062
 CACHE 1;
SELECT setval('"public"."curriculum_version_hmodule_subject_id_seq"', 2062, true);

-- ----------------------------
-- Sequence structure for curriculum_version_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_version_id_seq";
CREATE SEQUENCE "public"."curriculum_version_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 2046
 CACHE 1;
SELECT setval('"public"."curriculum_version_id_seq"', 2046, true);

-- ----------------------------
-- Sequence structure for curriculum_version_omodule_capacity_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_version_omodule_capacity_id_seq";
CREATE SEQUENCE "public"."curriculum_version_omodule_capacity_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 232
 CACHE 1;
SELECT setval('"public"."curriculum_version_omodule_capacity_id_seq"', 232, true);

-- ----------------------------
-- Sequence structure for curriculum_version_omodule_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_version_omodule_id_seq";
CREATE SEQUENCE "public"."curriculum_version_omodule_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 249
 CACHE 1;
SELECT setval('"public"."curriculum_version_omodule_id_seq"', 249, true);

-- ----------------------------
-- Sequence structure for curriculum_version_omodule_outcomes_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_version_omodule_outcomes_id_seq";
CREATE SEQUENCE "public"."curriculum_version_omodule_outcomes_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 214
 CACHE 1;
SELECT setval('"public"."curriculum_version_omodule_outcomes_id_seq"', 214, true);

-- ----------------------------
-- Sequence structure for curriculum_version_omodule_theme_capacity_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_version_omodule_theme_capacity_id_seq";
CREATE SEQUENCE "public"."curriculum_version_omodule_theme_capacity_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 226
 CACHE 1;
SELECT setval('"public"."curriculum_version_omodule_theme_capacity_id_seq"', 226, true);

-- ----------------------------
-- Sequence structure for curriculum_version_omodule_theme_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_version_omodule_theme_id_seq";
CREATE SEQUENCE "public"."curriculum_version_omodule_theme_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 229
 CACHE 1;
SELECT setval('"public"."curriculum_version_omodule_theme_id_seq"', 229, true);

-- ----------------------------
-- Sequence structure for curriculum_version_omodule_year_capacity_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_version_omodule_year_capacity_id_seq";
CREATE SEQUENCE "public"."curriculum_version_omodule_year_capacity_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 39
 CACHE 1;
SELECT setval('"public"."curriculum_version_omodule_year_capacity_id_seq"', 39, true);

-- ----------------------------
-- Sequence structure for curriculum_version_speciality_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."curriculum_version_speciality_id_seq";
CREATE SEQUENCE "public"."curriculum_version_speciality_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1017
 CACHE 1;
SELECT setval('"public"."curriculum_version_speciality_id_seq"', 1017, true);

-- ----------------------------
-- Sequence structure for directive_coordinator_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."directive_coordinator_id_seq";
CREATE SEQUENCE "public"."directive_coordinator_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 317
 CACHE 1;
SELECT setval('"public"."directive_coordinator_id_seq"', 317, true);

-- ----------------------------
-- Sequence structure for directive_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."directive_id_seq";
CREATE SEQUENCE "public"."directive_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;

-- ----------------------------
-- Sequence structure for directive_student_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."directive_student_id_seq";
CREATE SEQUENCE "public"."directive_student_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;

-- ----------------------------
-- Sequence structure for general_message_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."general_message_id_seq";
CREATE SEQUENCE "public"."general_message_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 302
 CACHE 1;
SELECT setval('"public"."general_message_id_seq"', 302, true);

-- ----------------------------
-- Sequence structure for general_message_target_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."general_message_target_id_seq";
CREATE SEQUENCE "public"."general_message_target_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 318
 CACHE 1;
SELECT setval('"public"."general_message_target_id_seq"', 318, true);

-- ----------------------------
-- Sequence structure for message_template_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."message_template_id_seq";
CREATE SEQUENCE "public"."message_template_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 132
 CACHE 1;
SELECT setval('"public"."message_template_id_seq"', 132, true);

-- ----------------------------
-- Sequence structure for ois_file_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."ois_file_id_seq";
CREATE SEQUENCE "public"."ois_file_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 7489
 CACHE 1;
SELECT setval('"public"."ois_file_id_seq"', 7489, true);

-- ----------------------------
-- Sequence structure for person_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."person_id_seq";
CREATE SEQUENCE "public"."person_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 33
 CACHE 1;
SELECT setval('"public"."person_id_seq"', 33, true);

-- ----------------------------
-- Sequence structure for room_equipment_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."room_equipment_id_seq";
CREATE SEQUENCE "public"."room_equipment_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 18
 CACHE 1;
SELECT setval('"public"."room_equipment_id_seq"', 18, true);

-- ----------------------------
-- Sequence structure for room_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."room_id_seq";
CREATE SEQUENCE "public"."room_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 249
 CACHE 1;
SELECT setval('"public"."room_id_seq"', 249, true);

-- ----------------------------
-- Sequence structure for school_department_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."school_department_id_seq";
CREATE SEQUENCE "public"."school_department_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 3078
 CACHE 1;
SELECT setval('"public"."school_department_id_seq"', 3078, true);

-- ----------------------------
-- Sequence structure for school_school_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."school_school_id_seq";
CREATE SEQUENCE "public"."school_school_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 957
 CACHE 1;
SELECT setval('"public"."school_school_id_seq"', 957, true);

-- ----------------------------
-- Sequence structure for school_study_level_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."school_study_level_id_seq";
CREATE SEQUENCE "public"."school_study_level_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 63
 CACHE 1;
SELECT setval('"public"."school_study_level_id_seq"', 63, true);

-- ----------------------------
-- Sequence structure for state_curriculum_module_occup_state_curriculum_module_occup_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."state_curriculum_module_occup_state_curriculum_module_occup_seq";
CREATE SEQUENCE "public"."state_curriculum_module_occup_state_curriculum_module_occup_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 4463
 CACHE 1;
SELECT setval('"public"."state_curriculum_module_occup_state_curriculum_module_occup_seq"', 4463, true);

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
 START 4562
 CACHE 1;
SELECT setval('"public"."state_curriculum_module_outco_state_curriculum_module_outco_seq"', 4562, true);

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
 START 4593
 CACHE 1;
SELECT setval('"public"."state_curriculum_module_state_curriculum_module_id_seq"', 4593, true);

-- ----------------------------
-- Sequence structure for state_curriculum_occupation_state_curriculum_occupation_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."state_curriculum_occupation_state_curriculum_occupation_id_seq";
CREATE SEQUENCE "public"."state_curriculum_occupation_state_curriculum_occupation_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 4501
 CACHE 1;
SELECT setval('"public"."state_curriculum_occupation_state_curriculum_occupation_id_seq"', 4501, true);

-- ----------------------------
-- Sequence structure for state_curriculum_state_curriculum_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."state_curriculum_state_curriculum_id_seq";
CREATE SEQUENCE "public"."state_curriculum_state_curriculum_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 3982
 CACHE 1;
SELECT setval('"public"."state_curriculum_state_curriculum_id_seq"', 3982, true);

-- ----------------------------
-- Sequence structure for student_absence_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."student_absence_id_seq";
CREATE SEQUENCE "public"."student_absence_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 11
 CACHE 1;
SELECT setval('"public"."student_absence_id_seq"', 11, true);

-- ----------------------------
-- Sequence structure for student_group_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."student_group_id_seq";
CREATE SEQUENCE "public"."student_group_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 22
 CACHE 1;
SELECT setval('"public"."student_group_id_seq"', 22, true);

-- ----------------------------
-- Sequence structure for student_history_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."student_history_id_seq";
CREATE SEQUENCE "public"."student_history_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 13
 CACHE 1;
SELECT setval('"public"."student_history_id_seq"', 13, true);

-- ----------------------------
-- Sequence structure for student_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."student_id_seq";
CREATE SEQUENCE "public"."student_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 9
 CACHE 1;
SELECT setval('"public"."student_id_seq"', 9, true);

-- ----------------------------
-- Sequence structure for student_representative_application_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."student_representative_application_id_seq";
CREATE SEQUENCE "public"."student_representative_application_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 9
 CACHE 1;
SELECT setval('"public"."student_representative_application_id_seq"', 9, true);

-- ----------------------------
-- Sequence structure for student_representative_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."student_representative_id_seq";
CREATE SEQUENCE "public"."student_representative_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 21
 CACHE 1;
SELECT setval('"public"."student_representative_id_seq"', 21, true);

-- ----------------------------
-- Sequence structure for study_period_event_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."study_period_event_id_seq";
CREATE SEQUENCE "public"."study_period_event_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;

-- ----------------------------
-- Sequence structure for study_period_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."study_period_id_seq";
CREATE SEQUENCE "public"."study_period_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 7
 CACHE 1;
SELECT setval('"public"."study_period_id_seq"', 7, true);

-- ----------------------------
-- Sequence structure for study_year_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."study_year_id_seq";
CREATE SEQUENCE "public"."study_year_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 6
 CACHE 1;
SELECT setval('"public"."study_year_id_seq"', 6, true);

-- ----------------------------
-- Sequence structure for subject_connect_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."subject_connect_id_seq";
CREATE SEQUENCE "public"."subject_connect_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 30
 CACHE 1;
SELECT setval('"public"."subject_connect_id_seq"', 30, true);

-- ----------------------------
-- Sequence structure for subject_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."subject_id_seq";
CREATE SEQUENCE "public"."subject_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 69
 CACHE 1;
SELECT setval('"public"."subject_id_seq"', 69, true);

-- ----------------------------
-- Sequence structure for subject_lang_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."subject_lang_id_seq";
CREATE SEQUENCE "public"."subject_lang_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 16
 CACHE 1;
SELECT setval('"public"."subject_lang_id_seq"', 16, true);

-- ----------------------------
-- Sequence structure for teacher_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."teacher_id_seq";
CREATE SEQUENCE "public"."teacher_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 23
 CACHE 1;
SELECT setval('"public"."teacher_id_seq"', 23, true);

-- ----------------------------
-- Sequence structure for teacher_mobility_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."teacher_mobility_id_seq";
CREATE SEQUENCE "public"."teacher_mobility_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 4
 CACHE 1;
SELECT setval('"public"."teacher_mobility_id_seq"', 4, true);

-- ----------------------------
-- Sequence structure for teacher_occupation_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."teacher_occupation_id_seq";
CREATE SEQUENCE "public"."teacher_occupation_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 518
 CACHE 1;
SELECT setval('"public"."teacher_occupation_id_seq"', 518, true);

-- ----------------------------
-- Sequence structure for teacher_position_ehis_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."teacher_position_ehis_id_seq";
CREATE SEQUENCE "public"."teacher_position_ehis_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 13
 CACHE 1;
SELECT setval('"public"."teacher_position_ehis_id_seq"', 13, true);

-- ----------------------------
-- Sequence structure for teacher_qualification_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."teacher_qualification_id_seq";
CREATE SEQUENCE "public"."teacher_qualification_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 4
 CACHE 1;
SELECT setval('"public"."teacher_qualification_id_seq"', 4, true);

-- ----------------------------
-- Sequence structure for user__id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."user__id_seq";
CREATE SEQUENCE "public"."user__id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 14
 CACHE 1;
SELECT setval('"public"."user__id_seq"', 14, true);

-- ----------------------------
-- Sequence structure for user_rights_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."user_rights_id_seq";
CREATE SEQUENCE "public"."user_rights_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 11
 CACHE 1;
SELECT setval('"public"."user_rights_id_seq"', 11, true);

-- ----------------------------
-- Table structure for application
-- ----------------------------
DROP TABLE IF EXISTS "public"."application";
CREATE TABLE "public"."application" (
"id" int8 DEFAULT nextval('application_id_seq'::regclass) NOT NULL,
"student_id" int8 NOT NULL,
"status_code" varchar(100) COLLATE "default" NOT NULL,
"type_code" varchar(100) COLLATE "default" NOT NULL,
"inserted" timestamptz(6) NOT NULL,
"changed" timestamptz(6),
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
"study_period_start_id" int8
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
DROP TABLE IF EXISTS "public"."application_planned_subject_equivalent ";
CREATE TABLE "public"."application_planned_subject_equivalent " (
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
COMMENT ON TABLE "public"."application_planned_subject_equivalent " IS 'avalduse planeeritud aine vastavusained';

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
"signatory_idcode" varchar(20) COLLATE "default" NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
"wd_id" int8,
"wd_url" text COLLATE "default",
"status_code" varchar(100) COLLATE "default" NOT NULL,
"other_idcode" varchar(20) COLLATE "default",
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
"structure" varchar(4000) COLLATE "default",
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
"optional_study_description" varchar(4000) COLLATE "default",
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
"curriculum_module_id" int4
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."curriculum_module_outcomes" IS 'mooduli õpiväljundid';
COMMENT ON COLUMN "public"."curriculum_module_outcomes"."outcome_et" IS 'õpiväljund e.k.';
COMMENT ON COLUMN "public"."curriculum_module_outcomes"."outcome_en" IS 'õpiväljund i.k.';

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
"changed_by" varchar(100) COLLATE "default"
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
"student_id" int8 NOT NULL,
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
"student_history_id" int8
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
"birthdate" date
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
COMMENT ON COLUMN "public"."person"."native_language" IS 'sünnikuupäev';

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
"final_exam_description" text COLLATE "default",
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
"is_accepted" bool NOT NULL
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."student_absence" IS 'õppuri puudumistõend';
COMMENT ON COLUMN "public"."student_absence"."student_id" IS 'viide õppurile';
COMMENT ON COLUMN "public"."student_absence"."valid_from" IS 'algus kp';
COMMENT ON COLUMN "public"."student_absence"."valid_thru" IS 'lõpp kp';
COMMENT ON COLUMN "public"."student_absence"."cause" IS 'puudumise põhjus';
COMMENT ON COLUMN "public"."student_absence"."is_accepted" IS 'kas puudumistõend aktsepteeritud false - ei true - jah vaikimisi false';

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
"speciality_code" varchar(100) COLLATE "default"
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
"changed_by" varchar(100) COLLATE "default"
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
"changed_by" varchar(100) COLLATE "default"
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
-- Table structure for teacher_position_ehis
-- ----------------------------
DROP TABLE IF EXISTS "public"."teacher_position_ehis";
CREATE TABLE "public"."teacher_position_ehis" (
"id" int8 DEFAULT nextval('teacher_position_ehis_id_seq'::regclass) NOT NULL,
"is_vocational" bool NOT NULL,
"teacher_id" int8 NOT NULL,
"position_code" varchar(100) COLLATE "default" NOT NULL,
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
"changed_by" varchar(100) COLLATE "default"
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
ALTER SEQUENCE "public"."application_file_id_seq" OWNED BY "application_file"."id";
ALTER SEQUENCE "public"."application_planned_subject_equivalent _id_seq" OWNED BY "application_planned_subject_equivalent "."id";
ALTER SEQUENCE "public"."application_planned_subject_id_seq" OWNED BY "application_planned_subject"."id";
ALTER SEQUENCE "public"."building_id_seq" OWNED BY "building"."id";
ALTER SEQUENCE "public"."certificate_id_seq" OWNED BY "certificate"."id";
ALTER SEQUENCE "public"."curriculum_department_id_seq" OWNED BY "curriculum_department"."id";
ALTER SEQUENCE "public"."curriculum_files_id_seq" OWNED BY "curriculum_files"."id";
ALTER SEQUENCE "public"."curriculum_grade_id_seq" OWNED BY "curriculum_grade"."id";
ALTER SEQUENCE "public"."curriculum_id_seq" OWNED BY "curriculum"."id";
ALTER SEQUENCE "public"."curriculum_joint_partners_id_seq" OWNED BY "curriculum_joint_partners"."id";
ALTER SEQUENCE "public"."curriculum_module_competence_id_seq" OWNED BY "curriculum_module_competence"."id";
ALTER SEQUENCE "public"."curriculum_module_id_seq" OWNED BY "curriculum_module"."id";
ALTER SEQUENCE "public"."curriculum_module_occupation_id_seq" OWNED BY "curriculum_module_occupation"."id";
ALTER SEQUENCE "public"."curriculum_module_outcomes_id_seq" OWNED BY "curriculum_module_outcomes"."id";
ALTER SEQUENCE "public"."curriculum_occupation_id_seq" OWNED BY "curriculum_occupation"."id";
ALTER SEQUENCE "public"."curriculum_occupation_speciality_id_seq" OWNED BY "curriculum_occupation_speciality"."id";
ALTER SEQUENCE "public"."curriculum_speciality_id_seq" OWNED BY "curriculum_speciality"."id";
ALTER SEQUENCE "public"."curriculum_study_form_id_seq" OWNED BY "curriculum_study_form"."id";
ALTER SEQUENCE "public"."curriculum_study_lang_id_seq" OWNED BY "curriculum_study_lang"."id";
ALTER SEQUENCE "public"."curriculum_version_elective_module_id_seq" OWNED BY "curriculum_version_elective_module"."id";
ALTER SEQUENCE "public"."curriculum_version_hmodule_id_seq" OWNED BY "curriculum_version_hmodule"."id";
ALTER SEQUENCE "public"."curriculum_version_hmodule_speciality_id_seq" OWNED BY "curriculum_version_hmodule_speciality"."id";
ALTER SEQUENCE "public"."curriculum_version_hmodule_subject_id_seq" OWNED BY "curriculum_version_hmodule_subject"."id";
ALTER SEQUENCE "public"."curriculum_version_id_seq" OWNED BY "curriculum_version"."id";
ALTER SEQUENCE "public"."curriculum_version_omodule_capacity_id_seq" OWNED BY "curriculum_version_omodule_capacity"."id";
ALTER SEQUENCE "public"."curriculum_version_omodule_id_seq" OWNED BY "curriculum_version_omodule"."id";
ALTER SEQUENCE "public"."curriculum_version_omodule_outcomes_id_seq" OWNED BY "curriculum_version_omodule_outcomes"."id";
ALTER SEQUENCE "public"."curriculum_version_omodule_theme_capacity_id_seq" OWNED BY "curriculum_version_omodule_theme_capacity"."id";
ALTER SEQUENCE "public"."curriculum_version_omodule_theme_id_seq" OWNED BY "curriculum_version_omodule_theme"."id";
ALTER SEQUENCE "public"."curriculum_version_omodule_year_capacity_id_seq" OWNED BY "curriculum_version_omodule_year_capacity"."id";
ALTER SEQUENCE "public"."curriculum_version_speciality_id_seq" OWNED BY "curriculum_version_speciality"."id";
ALTER SEQUENCE "public"."directive_coordinator_id_seq" OWNED BY "directive_coordinator"."id";
ALTER SEQUENCE "public"."directive_id_seq" OWNED BY "directive"."id";
ALTER SEQUENCE "public"."directive_student_id_seq" OWNED BY "directive_student"."id";
ALTER SEQUENCE "public"."general_message_id_seq" OWNED BY "general_message"."id";
ALTER SEQUENCE "public"."general_message_target_id_seq" OWNED BY "general_message_target"."id";
ALTER SEQUENCE "public"."message_template_id_seq" OWNED BY "message_template"."id";
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
ALTER SEQUENCE "public"."student_absence_id_seq" OWNED BY "student_absence"."id";
ALTER SEQUENCE "public"."student_group_id_seq" OWNED BY "student_group"."id";
ALTER SEQUENCE "public"."student_history_id_seq" OWNED BY "student_history"."id";
ALTER SEQUENCE "public"."student_id_seq" OWNED BY "student"."id";
ALTER SEQUENCE "public"."student_representative_application_id_seq" OWNED BY "student_representative_application"."id";
ALTER SEQUENCE "public"."student_representative_id_seq" OWNED BY "student_representative"."id";
ALTER SEQUENCE "public"."study_period_event_id_seq" OWNED BY "study_period_event"."id";
ALTER SEQUENCE "public"."study_period_id_seq" OWNED BY "study_period"."id";
ALTER SEQUENCE "public"."study_year_id_seq" OWNED BY "study_year"."id";
ALTER SEQUENCE "public"."subject_connect_id_seq" OWNED BY "subject_connect"."id";
ALTER SEQUENCE "public"."subject_id_seq" OWNED BY "subject"."id";
ALTER SEQUENCE "public"."subject_lang_id_seq" OWNED BY "subject_lang"."id";
ALTER SEQUENCE "public"."teacher_id_seq" OWNED BY "teacher"."id";
ALTER SEQUENCE "public"."teacher_mobility_id_seq" OWNED BY "teacher_mobility"."id";
ALTER SEQUENCE "public"."teacher_occupation_id_seq" OWNED BY "teacher_occupation"."id";
ALTER SEQUENCE "public"."teacher_position_ehis_id_seq" OWNED BY "teacher_position_ehis"."id";
ALTER SEQUENCE "public"."teacher_qualification_id_seq" OWNED BY "teacher_qualification"."id";
ALTER SEQUENCE "public"."user__id_seq" OWNED BY "user_"."id";
ALTER SEQUENCE "public"."user_rights_id_seq" OWNED BY "user_rights"."id";

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
-- Primary Key structure for table application
-- ----------------------------
ALTER TABLE "public"."application" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table application_file
-- ----------------------------
CREATE INDEX "IXFK_application_file_application" ON "public"."application_file" USING btree ("application_id");
CREATE INDEX "IXFK_application_file_ois_file" ON "public"."application_file" USING btree ("ois_file_id");

-- ----------------------------
-- Primary Key structure for table application_file
-- ----------------------------
ALTER TABLE "public"."application_file" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table application_planned_subject
-- ----------------------------
CREATE INDEX "IXFK_application_planned_subject_application" ON "public"."application_planned_subject" USING btree ("application_id");

-- ----------------------------
-- Primary Key structure for table application_planned_subject
-- ----------------------------
ALTER TABLE "public"."application_planned_subject" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table application_planned_subject_equivalent 
-- ----------------------------
CREATE INDEX "IXFK_application_planned_subject_equivalent _application_planne" ON "public"."application_planned_subject_equivalent " USING btree ("application_planned_subject_id");
CREATE INDEX "IXFK_application_planned_subject_equivalent _subject" ON "public"."application_planned_subject_equivalent " USING btree ("subject_id");

-- ----------------------------
-- Primary Key structure for table application_planned_subject_equivalent 
-- ----------------------------
ALTER TABLE "public"."application_planned_subject_equivalent " ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table building
-- ----------------------------
CREATE INDEX "IXFK_building_school" ON "public"."building" USING btree ("school_id");

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
-- Indexes structure for table curriculum_module_outcomes
-- ----------------------------
CREATE INDEX "IXFK_curriculum_module_outcomes_curriculum_module" ON "public"."curriculum_module_outcomes" USING btree ("curriculum_module_id");

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
-- Primary Key structure for table curriculum_occupation_speciality
-- ----------------------------
ALTER TABLE "public"."curriculum_occupation_speciality" ADD PRIMARY KEY ("id");

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
-- Indexes structure for table curriculum_version
-- ----------------------------
CREATE INDEX "IXFK_curriculum_version_curriculum" ON "public"."curriculum_version" USING btree ("curriculum_id");
CREATE INDEX "IXFK_curriculum_version_classifier" ON "public"."curriculum_version" USING btree ("type_code");
CREATE INDEX "IXFK_curriculum_version_classifier_02" ON "public"."curriculum_version" USING btree ("status_code");
CREATE INDEX "IXFK_curriculum_version_curriculum_study_form" ON "public"."curriculum_version" USING btree ("curriculum_study_form_id");
CREATE INDEX "IXFK_curriculum_version_school_department" ON "public"."curriculum_version" USING btree ("school_department_id");

-- ----------------------------
-- Primary Key structure for table curriculum_version
-- ----------------------------
ALTER TABLE "public"."curriculum_version" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table curriculum_version_elective_module
-- ----------------------------
CREATE INDEX "IXFK_curriculum_version_elective_module_curriculum_version_hmod" ON "public"."curriculum_version_elective_module" USING btree ("curriculum_version_hmodule_id");

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
-- Primary Key structure for table curriculum_version_hmodule
-- ----------------------------
ALTER TABLE "public"."curriculum_version_hmodule" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table curriculum_version_hmodule_speciality
-- ----------------------------
CREATE INDEX "IXFK_curriculum_version_hmodule_speciality_curriculum_version_h" ON "public"."curriculum_version_hmodule_speciality" USING btree ("curriculum_version_hmodule_id");
CREATE INDEX "IXFK_curriculum_version_hmodule_speciality_curriculum_version_s" ON "public"."curriculum_version_hmodule_speciality" USING btree ("curriculum_version_speciality_id");

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
-- Primary Key structure for table curriculum_version_omodule
-- ----------------------------
ALTER TABLE "public"."curriculum_version_omodule" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table curriculum_version_omodule_capacity
-- ----------------------------
CREATE INDEX "IXFK_curriculum_version_omodule_capacity_classifier" ON "public"."curriculum_version_omodule_capacity" USING btree ("capacity_type_code");
CREATE INDEX "IXFK_curriculum_version_omodule_capacity_curriculum_version_omo" ON "public"."curriculum_version_omodule_capacity" USING btree ("curriculum_version_omodule_id");

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
-- Primary Key structure for table curriculum_version_omodule_outcomes
-- ----------------------------
ALTER TABLE "public"."curriculum_version_omodule_outcomes" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table curriculum_version_omodule_theme
-- ----------------------------
CREATE INDEX "IXFK_curriculum_version_omodule_theme_curriculum_version_omodul" ON "public"."curriculum_version_omodule_theme" USING btree ("curriculum_version_omodule_id");
CREATE INDEX "IXFK_curriculum_version_omodule_theme_classifier" ON "public"."curriculum_version_omodule_theme" USING btree ("assessment_code");

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
-- Primary Key structure for table curriculum_version_omodule_theme_capacity
-- ----------------------------
ALTER TABLE "public"."curriculum_version_omodule_theme_capacity" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table curriculum_version_omodule_year_capacity
-- ----------------------------
CREATE INDEX "IXFK_curriculum_version_omodule_year_capacity_curriculum_versio" ON "public"."curriculum_version_omodule_year_capacity" USING btree ("curriculum_version_omodule_id");

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
-- Primary Key structure for table curriculum_version_speciality
-- ----------------------------
ALTER TABLE "public"."curriculum_version_speciality" ADD PRIMARY KEY ("id");

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
-- Primary Key structure for table directive
-- ----------------------------
ALTER TABLE "public"."directive" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table directive_coordinator
-- ----------------------------
CREATE INDEX "IXFK_directive_coordinator_school" ON "public"."directive_coordinator" USING btree ("school_id");

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

-- ----------------------------
-- Primary Key structure for table directive_student
-- ----------------------------
ALTER TABLE "public"."directive_student" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table general_message
-- ----------------------------
CREATE INDEX "IXFK_general_message_school" ON "public"."general_message" USING btree ("school_id");

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
-- Primary Key structure for table general_message_target
-- ----------------------------
ALTER TABLE "public"."general_message_target" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table message_template
-- ----------------------------
CREATE INDEX "IXFK_message_template_classifier" ON "public"."message_template" USING btree ("type_code");
CREATE INDEX "IXFK_message_template_school" ON "public"."message_template" USING btree ("school_id");

-- ----------------------------
-- Primary Key structure for table message_template
-- ----------------------------
ALTER TABLE "public"."message_template" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table ois_file
-- ----------------------------
ALTER TABLE "public"."ois_file" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table person
-- ----------------------------
CREATE INDEX "IXFK_person_classifier_04" ON "public"."person" USING btree ("residence_country_code");

-- ----------------------------
-- Uniques structure for table person
-- ----------------------------
ALTER TABLE "public"."person" ADD UNIQUE ("idcode");

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

-- ----------------------------
-- Primary Key structure for table student
-- ----------------------------
ALTER TABLE "public"."student" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table student_absence
-- ----------------------------
CREATE INDEX "IXFK_student_absence_student" ON "public"."student_absence" USING btree ("student_id");

-- ----------------------------
-- Primary Key structure for table student_absence
-- ----------------------------
ALTER TABLE "public"."student_absence" ADD PRIMARY KEY ("id");

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
-- Primary Key structure for table student_group
-- ----------------------------
ALTER TABLE "public"."student_group" ADD PRIMARY KEY ("id");

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

-- ----------------------------
-- Primary Key structure for table student_history
-- ----------------------------
ALTER TABLE "public"."student_history" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table student_representative
-- ----------------------------
CREATE INDEX "IXFK_student_representative_classifier" ON "public"."student_representative" USING btree ("relation_code");
CREATE INDEX "IXFK_student_representative_person" ON "public"."student_representative" USING btree ("person_id");
CREATE INDEX "IXFK_student_representative_student" ON "public"."student_representative" USING btree ("student_id");

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
-- Primary Key structure for table student_representative_application
-- ----------------------------
ALTER TABLE "public"."student_representative_application" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table study_period
-- ----------------------------
CREATE INDEX "IXFK_study_period_classifier" ON "public"."study_period" USING btree ("type_code");
CREATE INDEX "IXFK_study_period_study_year" ON "public"."study_period" USING btree ("study_year_id");

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
-- Primary Key structure for table study_period_event
-- ----------------------------
ALTER TABLE "public"."study_period_event" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table study_year
-- ----------------------------
CREATE INDEX "IXFK_study_year_classifier" ON "public"."study_year" USING btree ("year_code");
CREATE INDEX "IXFK_study_year_school" ON "public"."study_year" USING btree ("school_id");

-- ----------------------------
-- Uniques structure for table study_year
-- ----------------------------
ALTER TABLE "public"."study_year" ADD UNIQUE ("school_id", "year_code");

-- ----------------------------
-- Primary Key structure for table study_year
-- ----------------------------
ALTER TABLE "public"."study_year" ADD PRIMARY KEY ("id");

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
-- Indexes structure for table teacher
-- ----------------------------
CREATE INDEX "IXFK_teacher_person" ON "public"."teacher" USING btree ("person_id");
CREATE INDEX "IXFK_teacher_school" ON "public"."teacher" USING btree ("school_id");
CREATE INDEX "IXFK_teacher_teacher_occupation" ON "public"."teacher" USING btree ("teacher_occupation_id");

-- ----------------------------
-- Uniques structure for table teacher
-- ----------------------------
ALTER TABLE "public"."teacher" ADD UNIQUE ("person_id", "school_id");

-- ----------------------------
-- Primary Key structure for table teacher
-- ----------------------------
ALTER TABLE "public"."teacher" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table teacher_mobility
-- ----------------------------
CREATE INDEX "IXFK_teacher_mobility_classifier" ON "public"."teacher_mobility" USING btree ("target_code");
CREATE INDEX "IXFK_teacher_mobility_classifier_02" ON "public"."teacher_mobility" USING btree ("state_code");
CREATE INDEX "IXFK_teacher_mobility_teacher" ON "public"."teacher_mobility" USING btree ("teacher_id");

-- ----------------------------
-- Primary Key structure for table teacher_mobility
-- ----------------------------
ALTER TABLE "public"."teacher_mobility" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table teacher_occupation
-- ----------------------------
CREATE INDEX "IXFK_teacher_occupation_school" ON "public"."teacher_occupation" USING btree ("school_id");

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

-- ----------------------------
-- Primary Key structure for table teacher_qualification
-- ----------------------------
ALTER TABLE "public"."teacher_qualification" ADD PRIMARY KEY ("id");

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
-- Foreign Key structure for table "public"."application"
-- ----------------------------
ALTER TABLE "public"."application" ADD FOREIGN KEY ("study_period_start_id") REFERENCES "public"."study_period" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application" ADD FOREIGN KEY ("study_period_end_id") REFERENCES "public"."study_period" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application" ADD FOREIGN KEY ("academic_application_id") REFERENCES "public"."application" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application" ADD FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application" ADD FOREIGN KEY ("new_curriculum_version_id") REFERENCES "public"."curriculum_version" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application" ADD FOREIGN KEY ("old_curriculum_version_id") REFERENCES "public"."curriculum_version" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application" ADD FOREIGN KEY ("abroad_programme_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application" ADD FOREIGN KEY ("abroad_purpose_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application" ADD FOREIGN KEY ("type_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application" ADD FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application" ADD FOREIGN KEY ("ehis_school_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application" ADD FOREIGN KEY ("country_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application" ADD FOREIGN KEY ("new_fin_specific_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application" ADD FOREIGN KEY ("old_fin_specific_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application" ADD FOREIGN KEY ("new_fin_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application" ADD FOREIGN KEY ("old_fin_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application" ADD FOREIGN KEY ("new_study_form_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application" ADD FOREIGN KEY ("reason_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application" ADD FOREIGN KEY ("old_study_form_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."application_file"
-- ----------------------------
ALTER TABLE "public"."application_file" ADD FOREIGN KEY ("ois_file_id") REFERENCES "public"."ois_file" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."application_planned_subject_equivalent "
-- ----------------------------
ALTER TABLE "public"."application_planned_subject_equivalent " ADD FOREIGN KEY ("application_planned_subject_id") REFERENCES "public"."application_planned_subject" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."application_planned_subject_equivalent " ADD FOREIGN KEY ("subject_id") REFERENCES "public"."subject" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."building"
-- ----------------------------
ALTER TABLE "public"."building" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."certificate"
-- ----------------------------
ALTER TABLE "public"."certificate" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."certificate" ADD FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."certificate" ADD FOREIGN KEY ("type_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
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
ALTER TABLE "public"."classifier_connect" ADD FOREIGN KEY ("connect_classifier_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."classifier_connect" ADD FOREIGN KEY ("classifier_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum"
-- ----------------------------
ALTER TABLE "public"."curriculum" ADD FOREIGN KEY ("isced_class_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum" ADD FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum" ADD FOREIGN KEY ("draft_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum" ADD FOREIGN KEY ("state_curriculum_id") REFERENCES "public"."state_curriculum" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum" ADD FOREIGN KEY ("consecution_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum" ADD FOREIGN KEY ("orig_study_level_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum" ADD FOREIGN KEY ("joint_mentor_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum" ADD FOREIGN KEY ("ehis_status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum_department"
-- ----------------------------
ALTER TABLE "public"."curriculum_department" ADD FOREIGN KEY ("school_department_id") REFERENCES "public"."school_department" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_department" ADD FOREIGN KEY ("curriculum_id") REFERENCES "public"."curriculum" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum_files"
-- ----------------------------
ALTER TABLE "public"."curriculum_files" ADD FOREIGN KEY ("curriculum_id") REFERENCES "public"."curriculum" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_files" ADD FOREIGN KEY ("ehis_file_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_files" ADD FOREIGN KEY ("ois_file_id") REFERENCES "public"."ois_file" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

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
ALTER TABLE "public"."curriculum_module_competence" ADD FOREIGN KEY ("curriculum_module_id") REFERENCES "public"."curriculum_module" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_module_competence" ADD FOREIGN KEY ("competence_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum_module_occupation"
-- ----------------------------
ALTER TABLE "public"."curriculum_module_occupation" ADD FOREIGN KEY ("curriculum_module_id") REFERENCES "public"."curriculum_module" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_module_occupation" ADD FOREIGN KEY ("occupation_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

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
ALTER TABLE "public"."curriculum_speciality" ADD FOREIGN KEY ("curriculum_id") REFERENCES "public"."curriculum" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_speciality" ADD FOREIGN KEY ("occupation_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum_study_form"
-- ----------------------------
ALTER TABLE "public"."curriculum_study_form" ADD FOREIGN KEY ("study_form_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum_study_lang"
-- ----------------------------
ALTER TABLE "public"."curriculum_study_lang" ADD FOREIGN KEY ("curriculum_id") REFERENCES "public"."curriculum" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_study_lang" ADD FOREIGN KEY ("study_lang_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum_version"
-- ----------------------------
ALTER TABLE "public"."curriculum_version" ADD FOREIGN KEY ("curriculum_id") REFERENCES "public"."curriculum" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_version" ADD FOREIGN KEY ("curriculum_study_form_id") REFERENCES "public"."curriculum_study_form" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_version" ADD FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_version" ADD FOREIGN KEY ("school_department_id") REFERENCES "public"."school_department" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_version" ADD FOREIGN KEY ("type_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

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
ALTER TABLE "public"."curriculum_version_hmodule_subject" ADD FOREIGN KEY ("subject_id") REFERENCES "public"."subject" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_version_hmodule_subject" ADD FOREIGN KEY ("curriculum_version_elective_module_id") REFERENCES "public"."curriculum_version_elective_module" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_version_hmodule_subject" ADD FOREIGN KEY ("curriculum_version_hmodule_id") REFERENCES "public"."curriculum_version_hmodule" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum_version_omodule"
-- ----------------------------
ALTER TABLE "public"."curriculum_version_omodule" ADD FOREIGN KEY ("assessment_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_version_omodule" ADD FOREIGN KEY ("curriculum_version_id") REFERENCES "public"."curriculum_version" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_version_omodule" ADD FOREIGN KEY ("curriculum_module_id") REFERENCES "public"."curriculum_module" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum_version_omodule_capacity"
-- ----------------------------
ALTER TABLE "public"."curriculum_version_omodule_capacity" ADD FOREIGN KEY ("curriculum_version_omodule_id") REFERENCES "public"."curriculum_version_omodule" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_version_omodule_capacity" ADD FOREIGN KEY ("capacity_type_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."curriculum_version_omodule_outcomes"
-- ----------------------------
ALTER TABLE "public"."curriculum_version_omodule_outcomes" ADD FOREIGN KEY ("curriculum_version_omodule_theme_id") REFERENCES "public"."curriculum_version_omodule_theme" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_version_omodule_outcomes" ADD FOREIGN KEY ("curriculum_module_outcomes_id") REFERENCES "public"."curriculum_module_outcomes" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

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
ALTER TABLE "public"."curriculum_version_speciality" ADD FOREIGN KEY ("curriculum_speciality_id") REFERENCES "public"."curriculum_speciality" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."curriculum_version_speciality" ADD FOREIGN KEY ("curriculum_version_id") REFERENCES "public"."curriculum_version" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."directive"
-- ----------------------------
ALTER TABLE "public"."directive" ADD FOREIGN KEY ("type_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive" ADD FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive" ADD FOREIGN KEY ("cancel_type_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive" ADD FOREIGN KEY ("canceled_directive_id") REFERENCES "public"."directive" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive" ADD FOREIGN KEY ("directive_coordinator_id") REFERENCES "public"."directive_coordinator" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."directive_coordinator"
-- ----------------------------
ALTER TABLE "public"."directive_coordinator" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."directive_student"
-- ----------------------------
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("reason_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("study_load_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("study_form_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("fin_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("study_period_end_id") REFERENCES "public"."study_period" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("language_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("fin_specific_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("country_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("ehis_school_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("abroad_purpose_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("abroad_programme_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("previous_study_level_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("state_language_ects_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("curriculum_grade_id") REFERENCES "public"."curriculum_grade" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("curriculum_version_id") REFERENCES "public"."curriculum_version" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("directive_id") REFERENCES "public"."directive" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("student_group_id") REFERENCES "public"."student_group" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("person_id") REFERENCES "public"."person" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("study_period_start_id") REFERENCES "public"."study_period" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."directive_student" ADD FOREIGN KEY ("student_history_id") REFERENCES "public"."student_history" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."general_message"
-- ----------------------------
ALTER TABLE "public"."general_message" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."general_message_target"
-- ----------------------------
ALTER TABLE "public"."general_message_target" ADD FOREIGN KEY ("general_message_id") REFERENCES "public"."general_message" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."general_message_target" ADD FOREIGN KEY ("role_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."message_template"
-- ----------------------------
ALTER TABLE "public"."message_template" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."message_template" ADD FOREIGN KEY ("type_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."person"
-- ----------------------------
ALTER TABLE "public"."person" ADD FOREIGN KEY ("sex_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."person" ADD FOREIGN KEY ("citizenship_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."person" ADD FOREIGN KEY ("language_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."person" ADD FOREIGN KEY ("residence_country_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."room"
-- ----------------------------
ALTER TABLE "public"."room" ADD FOREIGN KEY ("building_id") REFERENCES "public"."building" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."room_equipment"
-- ----------------------------
ALTER TABLE "public"."room_equipment" ADD FOREIGN KEY ("room_id") REFERENCES "public"."room" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."room_equipment" ADD FOREIGN KEY ("equipment_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."school"
-- ----------------------------
ALTER TABLE "public"."school" ADD FOREIGN KEY ("ois_file_id") REFERENCES "public"."ois_file" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."school" ADD FOREIGN KEY ("ehis_school_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."school_department"
-- ----------------------------
ALTER TABLE "public"."school_department" ADD FOREIGN KEY ("parent_school_department_id") REFERENCES "public"."school_department" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."school_department" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."school_study_level"
-- ----------------------------
ALTER TABLE "public"."school_study_level" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."school_study_level" ADD FOREIGN KEY ("study_level_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

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
ALTER TABLE "public"."state_curriculum_module_occupation" ADD FOREIGN KEY ("occupation_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."state_curriculum_module_occupation" ADD FOREIGN KEY ("state_curriculum_module_id") REFERENCES "public"."state_curriculum_module" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

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
-- Foreign Key structure for table "public"."student"
-- ----------------------------
ALTER TABLE "public"."student" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student" ADD FOREIGN KEY ("curriculum_version_id") REFERENCES "public"."curriculum_version" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student" ADD FOREIGN KEY ("person_id") REFERENCES "public"."person" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student" ADD FOREIGN KEY ("student_history_id") REFERENCES "public"."student_history" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student" ADD FOREIGN KEY ("ois_file_id") REFERENCES "public"."ois_file" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student" ADD FOREIGN KEY ("curriculum_speciality_id") REFERENCES "public"."curriculum_speciality" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student" ADD FOREIGN KEY ("fin_specific_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student" ADD FOREIGN KEY ("fin_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student" ADD FOREIGN KEY ("study_load_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student" ADD FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student" ADD FOREIGN KEY ("student_group_id") REFERENCES "public"."student_group" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student" ADD FOREIGN KEY ("study_form_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student" ADD FOREIGN KEY ("previous_study_level_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student" ADD FOREIGN KEY ("special_need_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student" ADD FOREIGN KEY ("language_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."student_absence"
-- ----------------------------
ALTER TABLE "public"."student_absence" ADD FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."student_group"
-- ----------------------------
ALTER TABLE "public"."student_group" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_group" ADD FOREIGN KEY ("study_form_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_group" ADD FOREIGN KEY ("language_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_group" ADD FOREIGN KEY ("speciality_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_group" ADD FOREIGN KEY ("curriculum_id") REFERENCES "public"."curriculum" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_group" ADD FOREIGN KEY ("curriculum_version_id") REFERENCES "public"."curriculum_version" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_group" ADD FOREIGN KEY ("teacher_id") REFERENCES "public"."teacher" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."student_history"
-- ----------------------------
ALTER TABLE "public"."student_history" ADD FOREIGN KEY ("curriculum_version_id") REFERENCES "public"."curriculum_version" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_history" ADD FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_history" ADD FOREIGN KEY ("ois_file_id") REFERENCES "public"."ois_file" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_history" ADD FOREIGN KEY ("curriculum_speciality_id") REFERENCES "public"."curriculum_speciality" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_history" ADD FOREIGN KEY ("special_need_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_history" ADD FOREIGN KEY ("previous_study_level_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_history" ADD FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_history" ADD FOREIGN KEY ("study_load_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_history" ADD FOREIGN KEY ("fin_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_history" ADD FOREIGN KEY ("fin_specific_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_history" ADD FOREIGN KEY ("study_form_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_history" ADD FOREIGN KEY ("student_group_id") REFERENCES "public"."student_group" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_history" ADD FOREIGN KEY ("language_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."student_representative"
-- ----------------------------
ALTER TABLE "public"."student_representative" ADD FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_representative" ADD FOREIGN KEY ("person_id") REFERENCES "public"."person" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_representative" ADD FOREIGN KEY ("relation_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."student_representative_application"
-- ----------------------------
ALTER TABLE "public"."student_representative_application" ADD FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_representative_application" ADD FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_representative_application" ADD FOREIGN KEY ("person_id") REFERENCES "public"."person" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."student_representative_application" ADD FOREIGN KEY ("relation_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

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
-- Foreign Key structure for table "public"."subject"
-- ----------------------------
ALTER TABLE "public"."subject" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."subject" ADD FOREIGN KEY ("assessment_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."subject" ADD FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."subject" ADD FOREIGN KEY ("school_department_id") REFERENCES "public"."school_department" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."subject_connect"
-- ----------------------------
ALTER TABLE "public"."subject_connect" ADD FOREIGN KEY ("primary_subject_id") REFERENCES "public"."subject" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."subject_connect" ADD FOREIGN KEY ("connect_subject_id") REFERENCES "public"."subject" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."subject_connect" ADD FOREIGN KEY ("connection_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."subject_lang"
-- ----------------------------
ALTER TABLE "public"."subject_lang" ADD FOREIGN KEY ("subject_id") REFERENCES "public"."subject" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."subject_lang" ADD FOREIGN KEY ("lang_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."teacher"
-- ----------------------------
ALTER TABLE "public"."teacher" ADD FOREIGN KEY ("person_id") REFERENCES "public"."person" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."teacher" ADD FOREIGN KEY ("teacher_occupation_id") REFERENCES "public"."teacher_occupation" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."teacher" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."teacher_mobility"
-- ----------------------------
ALTER TABLE "public"."teacher_mobility" ADD FOREIGN KEY ("target_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."teacher_mobility" ADD FOREIGN KEY ("state_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."teacher_mobility" ADD FOREIGN KEY ("teacher_id") REFERENCES "public"."teacher" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."teacher_occupation"
-- ----------------------------
ALTER TABLE "public"."teacher_occupation" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."teacher_position_ehis"
-- ----------------------------
ALTER TABLE "public"."teacher_position_ehis" ADD FOREIGN KEY ("employment_type_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."teacher_position_ehis" ADD FOREIGN KEY ("teacher_id") REFERENCES "public"."teacher" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."teacher_position_ehis" ADD FOREIGN KEY ("language_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."teacher_position_ehis" ADD FOREIGN KEY ("contract_type_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."teacher_position_ehis" ADD FOREIGN KEY ("position_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."teacher_position_ehis" ADD FOREIGN KEY ("school_department_id") REFERENCES "public"."school_department" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."teacher_qualification"
-- ----------------------------
ALTER TABLE "public"."teacher_qualification" ADD FOREIGN KEY ("school_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."teacher_qualification" ADD FOREIGN KEY ("qualification_name_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."teacher_qualification" ADD FOREIGN KEY ("teacher_id") REFERENCES "public"."teacher" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."teacher_qualification" ADD FOREIGN KEY ("ex_school_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."teacher_qualification" ADD FOREIGN KEY ("state_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."teacher_qualification" ADD FOREIGN KEY ("qualification_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."user_"
-- ----------------------------
ALTER TABLE "public"."user_" ADD FOREIGN KEY ("person_id") REFERENCES "public"."person" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."user_" ADD FOREIGN KEY ("role_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."user_" ADD FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."user_rights"
-- ----------------------------
ALTER TABLE "public"."user_rights" ADD FOREIGN KEY ("object_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;

--INSERT DATA
\i db_data.sql;
