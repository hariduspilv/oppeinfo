\c hois;

ALTER TABLE "public"."protocol_vdata" DROP COLUMN "id";


CREATE SEQUENCE "public"."contract_id_seq" START 101;
CREATE SEQUENCE "public"."declaration_id_seq" START 101;
CREATE SEQUENCE "public"."declaration_subject_id_seq" START 101;
CREATE SEQUENCE "public"."enterprise_id_seq" START 101;
CREATE SEQUENCE "public"."midterm_task_id_seq" START 101;
CREATE SEQUENCE "public"."midterm_task_student_result_id_seq" START 101;
CREATE SEQUENCE "public"."timetable_event_id_seq" START 101;
CREATE SEQUENCE "public"."timetable_event_room_id_seq" START 101;
CREATE SEQUENCE "public"."timetable_event_teacher_id_seq" START 101;
CREATE SEQUENCE "public"."timetable_event_time_id_seq" START 101;
CREATE SEQUENCE "public"."timetable_id_seq" START 101;
CREATE SEQUENCE "public"."timetable_object_id_seq" START 101;
CREATE SEQUENCE "public"."timetable_object_student_group_id_seq" START 101;


CREATE TABLE "public"."contract" (
"id" int8 DEFAULT nextval('contract_id_seq'::regclass) NOT NULL,
"student_id" int8 NOT NULL,
"curriculum_version_omodule_id" int8 NOT NULL,
"curriculum_version_omodule_theme_id" int8,
"credits" numeric(4,1) NOT NULL,
"hours" int2 NOT NULL,
"start" date NOT NULL,
"end" date NOT NULL,
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
CONSTRAINT "PK_contract" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE);
COMMENT ON TABLE "public"."contract" IS 'leping, esialgu ainult praktika leping';
COMMENT ON COLUMN "public"."contract"."student_id" IS 'viide õppurile';
COMMENT ON COLUMN "public"."contract"."curriculum_version_omodule_id" IS 'viide praktika moodulile';
COMMENT ON COLUMN "public"."contract"."curriculum_version_omodule_theme_id" IS 'viide teemale';
COMMENT ON COLUMN "public"."contract"."credits" IS 'mah EKAP';
COMMENT ON COLUMN "public"."contract"."hours" IS 'maht tundides';
COMMENT ON COLUMN "public"."contract"."start" IS 'lepingu algus';
COMMENT ON COLUMN "public"."contract"."end" IS 'lepingu lõpp';
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
"changed_by" varchar(100) COLLATE "default",
CONSTRAINT "PK_declaration" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE);
COMMENT ON TABLE "public"."declaration" IS 'õpingukava ehk õppeainete deklaratsioon';
COMMENT ON COLUMN "public"."declaration"."student_id" IS 'viide õppurile';
COMMENT ON COLUMN "public"."declaration"."status_code" IS 'viide klassifikaatorile OPINGUKAVA_STAATUS';
COMMENT ON COLUMN "public"."declaration"."study_period_id" IS 'viide õppeperioodile';
COMMENT ON COLUMN "public"."declaration"."confirm_date" IS 'kinnitamise kp';
COMMENT ON COLUMN "public"."declaration"."confirmer" IS 'kinnitaja nimi';

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
"is_optional" bool NOT NULL,
CONSTRAINT "PK_declaration_subject" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE);
COMMENT ON COLUMN "public"."declaration_subject"."declaration_id" IS 'viide deklaratsioonile';
COMMENT ON COLUMN "public"."declaration_subject"."subject_study_period_id" IS 'viide aine-õppeju paarile';
COMMENT ON COLUMN "public"."declaration_subject"."curriculum_version_hmodule_id" IS 'moodul, täidetakse automaatselt lisamisel';
COMMENT ON COLUMN "public"."declaration_subject"."is_optional" IS 'kas tegemist on valik või kohustusliku ainega true - ei ole kohustuslik false - kohustuslik';



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
"changed_by" varchar(100) COLLATE "default",
CONSTRAINT "PK_enterprise" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE);
COMMENT ON TABLE "public"."enterprise" IS 'ettevõtete register';
COMMENT ON COLUMN "public"."enterprise"."reg_code" IS 'ettevõtte reg. kood';
COMMENT ON COLUMN "public"."enterprise"."name" IS 'ettevõtte nimi';
COMMENT ON COLUMN "public"."enterprise"."contact_person_name" IS 'kontaktisiku nimi';
COMMENT ON COLUMN "public"."enterprise"."contact_person_phone" IS 'kontaktisiku telefon';
COMMENT ON COLUMN "public"."enterprise"."contact_person_email" IS 'kontaktisiku e-mail';



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
"changed_by" varchar(100) COLLATE "default",
CONSTRAINT "PK_midterm_task" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE);
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


CREATE TABLE "public"."midterm_task_student_result" (
"id" int8 DEFAULT nextval('midterm_task_student_result_id_seq'::regclass) NOT NULL,
"midterm_task_id" int8 NOT NULL,
"declaration_subject_id" int8 NOT NULL,
"points" numeric(5,2),
"ponts_txt" varchar(10) COLLATE "default",
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
"version" int4 NOT NULL,
CONSTRAINT "PK_midterm_task_student_result" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE);
COMMENT ON COLUMN "public"."midterm_task_student_result"."midterm_task_id" IS 'viide vahetööle';
COMMENT ON COLUMN "public"."midterm_task_student_result"."declaration_subject_id" IS 'viide õppuri deklaratsioonile';
COMMENT ON COLUMN "public"."midterm_task_student_result"."points" IS 'punktid numbrilisel kujul';
COMMENT ON COLUMN "public"."midterm_task_student_result"."ponts_txt" IS 'punktid tekstilisel kujul';

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
CONSTRAINT "PK_timetable" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE);
COMMENT ON TABLE "public"."timetable" IS 'tunniplaan';
COMMENT ON COLUMN "public"."timetable"."school_id" IS 'viide õppeasutusele';
COMMENT ON COLUMN "public"."timetable"."start_date" IS 'algus kp';
COMMENT ON COLUMN "public"."timetable"."study_period_id" IS 'viide õppeperioodile';
COMMENT ON COLUMN "public"."timetable"."end_date" IS 'lõpp kp';
COMMENT ON COLUMN "public"."timetable"."status_code" IS 'viide klassifikaatorile TUNNIPLAAN_STAATUS';

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
CONSTRAINT "PK_timetable_event" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE);
COMMENT ON TABLE "public"."timetable_event" IS 'tunniplaani sündmus - üksik või kõik';
COMMENT ON COLUMN "public"."timetable_event"."start" IS 'algus koos kellaajaga';
COMMENT ON COLUMN "public"."timetable_event"."end" IS 'lõpp koos kellaajaga';
COMMENT ON COLUMN "public"."timetable_event"."lessons" IS 'tundide arv';
COMMENT ON COLUMN "public"."timetable_event"."consider_break" IS 'kas arvestada vahetundi';
COMMENT ON COLUMN "public"."timetable_event"."name" IS 'sündmuse nimi, kasutatakse üksiksündmuste puhul';
COMMENT ON COLUMN "public"."timetable_event"."repeat_code" IS 'viide klassifikaatorile TUNNIPLAAN_SYNDMUS_KORDUS, vaikimisi TUNNIPLAAN_SYNDMUS_KORDUS_EI';

CREATE TABLE "public"."timetable_event_room" (
"id" int8 DEFAULT nextval('timetable_event_room_id_seq'::regclass) NOT NULL,
"room_id" int8 NOT NULL,
"timetable_event_time_id" int8 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
CONSTRAINT "PK_timetable_event_room" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE);
COMMENT ON TABLE "public"."timetable_event_room" IS 'tunniplaani ruum';
COMMENT ON COLUMN "public"."timetable_event_room"."room_id" IS 'viide ruumile';
COMMENT ON COLUMN "public"."timetable_event_room"."timetable_event_time_id" IS 'viide toimumisajale';

CREATE TABLE "public"."timetable_event_teacher" (
"id" int8 DEFAULT nextval('timetable_event_teacher_id_seq'::regclass) NOT NULL,
"teacher_id" int8 NOT NULL,
"timetable_event_time_id" int8,
"inserted" timestamp(6) NOT NULL,
"changed" time(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
CONSTRAINT "PK_timetable_event_teacher" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE);
COMMENT ON TABLE "public"."timetable_event_teacher" IS 'tunniplaani õpetaja';
COMMENT ON COLUMN "public"."timetable_event_teacher"."teacher_id" IS 'viide õpetajale';
COMMENT ON COLUMN "public"."timetable_event_teacher"."timetable_event_time_id" IS 'viide toimumisajale';

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
"changed_by" varchar(100) COLLATE "default",
CONSTRAINT "PK_timetable_event_time" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE);
COMMENT ON TABLE "public"."timetable_event_time" IS 'reaalne toimumisaeg';
COMMENT ON COLUMN "public"."timetable_event_time"."timetable_event_id" IS 'viide tunniplaanile';
COMMENT ON COLUMN "public"."timetable_event_time"."start" IS 'üksiksündmuse algus';
COMMENT ON COLUMN "public"."timetable_event_time"."end" IS 'üksiksündmuse lõpp';

CREATE TABLE "public"."timetable_object" (
"id" int8 DEFAULT nextval('timetable_object_id_seq'::regclass) NOT NULL,
"timetable_id" int8 NOT NULL,
"journal_id" int8,
"subject_study_period_id" int8,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int8 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
CONSTRAINT "PK_timetable_object" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE);
COMMENT ON TABLE "public"."timetable_object" IS 'tunniplaani objekt, täidetakse juhul kui tegemist on päeviku või aine-õppejõu paariga';
COMMENT ON COLUMN "public"."timetable_object"."timetable_id" IS 'viide tunniplaanile';
COMMENT ON COLUMN "public"."timetable_object"."journal_id" IS 'viide päevikule';
COMMENT ON COLUMN "public"."timetable_object"."subject_study_period_id" IS 'viide aine-õppejõu paarile';

CREATE TABLE "public"."timetable_object_student_group" (
"id" int8 DEFAULT nextval('timetable_object_student_group_id_seq'::regclass) NOT NULL,
"student_group_id" int8 NOT NULL,
"timetable_object_id" int8 NOT NULL,
"inserted" timestamp(6) NOT NULL,
"changed" timestamp(6),
"version" int4 NOT NULL,
"inserted_by" varchar(100) COLLATE "default" NOT NULL,
"changed_by" varchar(100) COLLATE "default",
CONSTRAINT "PK_timetable_event_student_group" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE);
COMMENT ON TABLE "public"."timetable_object_student_group" IS 'tunniplaani objektidega seotud rühmad';
COMMENT ON COLUMN "public"."timetable_object_student_group"."student_group_id" IS 'viide tunniplaani sündmusega seotud grupile';


CREATE INDEX "IXFK_contract_classifier" ON "public"."contract" USING btree ("status_code");
CREATE INDEX "IXFK_contract_curriculum_version_omodule" ON "public"."contract" USING btree ("curriculum_version_omodule_id");
CREATE INDEX "IXFK_contract_curriculum_version_omodule_theme" ON "public"."contract" USING btree ("curriculum_version_omodule_theme_id");
CREATE INDEX "IXFK_contract_directive_coordinator" ON "public"."contract" USING btree ("contract_coordinator_id");
CREATE INDEX "IXFK_contract_enterprise" ON "public"."contract" USING btree ("enterprise_id");
CREATE INDEX "IXFK_contract_student" ON "public"."contract" USING btree ("student_id");
CREATE INDEX "IXFK_contract_teacher" ON "public"."contract" USING btree ("teacher_id");
CREATE INDEX "IXFK_declaration_classifier" ON "public"."declaration" USING btree ("status_code");
CREATE INDEX "IXFK_declaration_student" ON "public"."declaration" USING btree ("student_id");
CREATE INDEX "IXFK_declaration_study_period" ON "public"."declaration" USING btree ("study_period_id");
CREATE INDEX "IXFK_declaration_subject_curriculum_version_hmodule" ON "public"."declaration_subject" USING btree ("curriculum_version_hmodule_id");
CREATE INDEX "IXFK_declaration_subject_declaration" ON "public"."declaration_subject" USING btree ("declaration_id");
CREATE INDEX "IXFK_declaration_subject_subject_study_period" ON "public"."declaration_subject" USING btree ("subject_study_period_id");
CREATE INDEX "IXFK_midterm_task_student_result_declaration_subject" ON "public"."midterm_task_student_result" USING btree ("declaration_subject_id");
CREATE INDEX "IXFK_midterm_task_student_result_midterm_task" ON "public"."midterm_task_student_result" USING btree ("midterm_task_id");
CREATE INDEX "IXFK_midterm_task_subject_study_period" ON "public"."midterm_task" USING btree ("subject_study_period_id");
CREATE INDEX "IXFK_timetable_classifier" ON "public"."timetable" USING btree ("status_code");
CREATE INDEX "IXFK_timetable_event_classifier" ON "public"."timetable_event" USING btree ("repeat_code");
CREATE INDEX "IXFK_timetable_event_room_room" ON "public"."timetable_event_room" USING btree ("room_id");
CREATE INDEX "IXFK_timetable_event_room_timetable_event_time" ON "public"."timetable_event_room" USING btree ("timetable_event_time_id");
CREATE INDEX "IXFK_timetable_event_student_group_student_group" ON "public"."timetable_object_student_group" USING btree ("student_group_id");
CREATE INDEX "IXFK_timetable_event_teacher_teacher" ON "public"."timetable_event_teacher" USING btree ("teacher_id");
CREATE INDEX "IXFK_timetable_event_teacher_timetable_event_time" ON "public"."timetable_event_teacher" USING btree ("timetable_event_time_id");
CREATE INDEX "IXFK_timetable_event_time_timetable_event" ON "public"."timetable_event_time" USING btree ("timetable_event_id");
CREATE INDEX "IXFK_timetable_event_timetable_object" ON "public"."timetable_event" USING btree ("timetable_object_id");
CREATE INDEX "IXFK_timetable_object_journal" ON "public"."timetable_object" USING btree ("journal_id");
CREATE INDEX "IXFK_timetable_object_student_group_timetable_object" ON "public"."timetable_object_student_group" USING btree ("timetable_object_id");
CREATE INDEX "IXFK_timetable_object_subject_study_period" ON "public"."timetable_object" USING btree ("subject_study_period_id");
CREATE INDEX "IXFK_timetable_object_timetable" ON "public"."timetable_object" USING btree ("timetable_id");
CREATE INDEX "IXFK_timetable_school" ON "public"."timetable" USING btree ("school_id");
CREATE INDEX "IXFK_timetable_study_period" ON "public"."timetable" USING btree ("study_period_id");


ALTER SEQUENCE "public"."contract_id_seq" OWNED BY "public"."contract"."id";
ALTER SEQUENCE "public"."declaration_id_seq" OWNED BY "public"."declaration"."id";
ALTER SEQUENCE "public"."declaration_subject_id_seq" OWNED BY "public"."declaration_subject"."id";
ALTER SEQUENCE "public"."enterprise_id_seq" OWNED BY "public"."enterprise"."id";
ALTER SEQUENCE "public"."midterm_task_id_seq" OWNED BY "public"."midterm_task"."id";
ALTER SEQUENCE "public"."midterm_task_student_result_id_seq" OWNED BY "public"."midterm_task_student_result"."id";
ALTER SEQUENCE "public"."timetable_event_id_seq" OWNED BY "public"."timetable_event"."id";
ALTER SEQUENCE "public"."timetable_event_room_id_seq" OWNED BY "public"."timetable_event_room"."id";
ALTER SEQUENCE "public"."timetable_event_teacher_id_seq" OWNED BY "public"."timetable_event_teacher"."id";
ALTER SEQUENCE "public"."timetable_event_time_id_seq" OWNED BY "public"."timetable_event_time"."id";
ALTER SEQUENCE "public"."timetable_id_seq" OWNED BY "public"."timetable"."id";
ALTER SEQUENCE "public"."timetable_object_id_seq" OWNED BY "public"."timetable_object"."id";
ALTER SEQUENCE "public"."timetable_object_student_group_id_seq" OWNED BY "public"."timetable_object_student_group"."id";


ALTER TABLE "public"."contract" ADD CONSTRAINT "FK_contract_directive_coordinator" FOREIGN KEY ("contract_coordinator_id") REFERENCES "public"."directive_coordinator" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."contract" ADD CONSTRAINT "FK_contract_enterprise" FOREIGN KEY ("enterprise_id") REFERENCES "public"."enterprise" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."contract" ADD CONSTRAINT "FK_contract_student" FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."contract" ADD CONSTRAINT "FK_contract_teacher" FOREIGN KEY ("teacher_id") REFERENCES "public"."teacher" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."contract" ADD CONSTRAINT "FK_contract_curriculum_version_omodule_theme" FOREIGN KEY ("curriculum_version_omodule_theme_id") REFERENCES "public"."curriculum_version_omodule_theme" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."contract" ADD CONSTRAINT "FK_contract_curriculum_version_omodule" FOREIGN KEY ("curriculum_version_omodule_id") REFERENCES "public"."curriculum_version_omodule" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."contract" ADD CONSTRAINT "FK_contract_classifier" FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."declaration" ADD CONSTRAINT "FK_declaration_student" FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."declaration" ADD CONSTRAINT "FK_declaration_classifier" FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."declaration" ADD CONSTRAINT "FK_declaration_study_period" FOREIGN KEY ("study_period_id") REFERENCES "public"."study_period" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."declaration_subject" ADD CONSTRAINT "FK_declaration_subject_curriculum_version_hmodule" FOREIGN KEY ("curriculum_version_hmodule_id") REFERENCES "public"."curriculum_version_hmodule" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."declaration_subject" ADD CONSTRAINT "FK_declaration_subject_declaration" FOREIGN KEY ("declaration_id") REFERENCES "public"."declaration" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."declaration_subject" ADD CONSTRAINT "FK_declaration_subject_subject_study_period" FOREIGN KEY ("subject_study_period_id") REFERENCES "public"."subject_study_period" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."midterm_task" ADD CONSTRAINT "FK_midterm_task_subject_study_period" FOREIGN KEY ("subject_study_period_id") REFERENCES "public"."subject_study_period" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."midterm_task_student_result" ADD CONSTRAINT "FK_midterm_task_student_result_midterm_task" FOREIGN KEY ("midterm_task_id") REFERENCES "public"."midterm_task" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."midterm_task_student_result" ADD CONSTRAINT "FK_midterm_task_student_result_declaration_subject" FOREIGN KEY ("declaration_subject_id") REFERENCES "public"."declaration_subject" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."timetable" ADD CONSTRAINT "FK_timetable_school" FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."timetable" ADD CONSTRAINT "FK_timetable_classifier" FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."timetable" ADD CONSTRAINT "FK_timetable_study_period" FOREIGN KEY ("study_period_id") REFERENCES "public"."study_period" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."timetable_event" ADD CONSTRAINT "FK_timetable_event_timetable_object" FOREIGN KEY ("timetable_object_id") REFERENCES "public"."timetable_object" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."timetable_event" ADD CONSTRAINT "FK_timetable_event_classifier" FOREIGN KEY ("repeat_code") REFERENCES "public"."classifier" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."timetable_event_room" ADD CONSTRAINT "FK_timetable_event_room_room" FOREIGN KEY ("room_id") REFERENCES "public"."room" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."timetable_event_room" ADD CONSTRAINT "FK_timetable_event_room_timetable_event_time" FOREIGN KEY ("timetable_event_time_id") REFERENCES "public"."timetable_event_time" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."timetable_event_teacher" ADD CONSTRAINT "FK_timetable_event_teacher_timetable_event_time" FOREIGN KEY ("timetable_event_time_id") REFERENCES "public"."timetable_event_time" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."timetable_event_teacher" ADD CONSTRAINT "FK_timetable_event_teacher_teacher" FOREIGN KEY ("teacher_id") REFERENCES "public"."teacher" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."timetable_event_time" ADD CONSTRAINT "FK_timetable_event_time_timetable_event" FOREIGN KEY ("timetable_event_id") REFERENCES "public"."timetable_event" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."timetable_object" ADD CONSTRAINT "FK_timetable_object_journal" FOREIGN KEY ("journal_id") REFERENCES "public"."journal" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."timetable_object" ADD CONSTRAINT "FK_timetable_object_subject_study_period" FOREIGN KEY ("subject_study_period_id") REFERENCES "public"."subject_study_period" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."timetable_object" ADD CONSTRAINT "FK_timetable_object_timetable" FOREIGN KEY ("timetable_id") REFERENCES "public"."timetable" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."timetable_object_student_group" ADD CONSTRAINT "FK_timetable_event_student_group_student_group" FOREIGN KEY ("student_group_id") REFERENCES "public"."student_group" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."timetable_object_student_group" ADD CONSTRAINT "FK_timetable_object_student_group_timetable_object" FOREIGN KEY ("timetable_object_id") REFERENCES "public"."timetable_object" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;


--INSERT DATA
\i db_data.sql;