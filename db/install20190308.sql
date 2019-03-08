\c hois

alter table subject_study_period add column is_capacity_diff boolean;
comment on column subject_study_period.is_capacity_diff is 'märgitakse mitme õpetaja korral kas neil on erinevad mahud või mitte, vaikimisi mahud on identsed, need samad mis on märgitud subject_study_period_capacity tabelisse';



CREATE TABLE "subject_study_period_teacher_capacity"
(
	"id" bigserial NOT NULL,
	"subject_study_period_capacity_id" bigint NOT NULL,
	"subject_study_period_teacher_id" bigint NOT NULL,
	"hours" smallint NOT NULL,
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "subject_study_period_teacher_capacity"	IS 'õpetaja täpsem koormus';
/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE "subject_study_period_teacher_capacity" ADD CONSTRAINT "PK_subject_study_period_teacher_capacity"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_subject_study_period_teacher_capacity_subject_study_period_capacity" ON "subject_study_period_teacher_capacity" ("subject_study_period_capacity_id" ASC);
CREATE INDEX "IXFK_subject_study_period_teacher_capacity_X" ON "subject_study_period_teacher_capacity" ("subject_study_period_teacher_id" ASC);

ALTER TABLE "subject_study_period_teacher_capacity" ADD CONSTRAINT "FK_subject_study_period_teacher_capacity_subject_study_period_capacity"	FOREIGN KEY ("subject_study_period_capacity_id") REFERENCES "subject_study_period_capacity" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "subject_study_period_teacher_capacity" ADD CONSTRAINT "FK_subject_study_period_teacher_capacity_X"	FOREIGN KEY ("subject_study_period_teacher_id") REFERENCES "subject_study_period_teacher" ("id") ON DELETE No Action ON UPDATE No Action;

create trigger subject_study_period_teacher_capacity_audit after insert or delete or update on subject_study_period_teacher_capacity for each row execute procedure hois_audit();

alter table student_absence add column reject_reason varchar(4000);
comment on column student_absence.reject_reason is 'tagasi lükkamise põhjus';

alter table contract add column	"student_practice_evaluation_id" bigint NULL    -- viide hindamisvormile
;
CREATE INDEX "IXFK_contract_practice_evaluation2" ON "contract" ("student_practice_evaluation_id" ASC);
ALTER TABLE "contract" ADD CONSTRAINT "FK_contract_practice_evaluation2"	FOREIGN KEY ("student_practice_evaluation_id") REFERENCES "practice_evaluation" ("id") ON DELETE No Action ON UPDATE No Action;
COMMENT ON COLUMN "contract"."student_practice_evaluation_id"	IS 'viide õppuri hindamisvormile';

CREATE TABLE "practice_journal_evaluation"
(
	"id" bigserial NOT NULL,
	"practice_journal_id" bigint NOT NULL,    -- viide praktikapäevikule
	"practice_evaluation_id" bigint NOT NULL,
	"practice_evaluation_criteria_id" bigint NOT NULL,    -- viide hindamisvormi kriteeriumile
	"value_txt" varchar(4000)	 NULL,    -- vastus tekst
	"value_nr" numeric NULL,    -- vastus nr
	"value_clf_code" varchar(100)	 NULL,    -- vastus klassifikaator
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "practice_journal_evaluation"	IS 'praktika päeviku hindamine';
COMMENT ON COLUMN "practice_journal_evaluation"."practice_journal_id"	IS 'viide praktikapäevikule';
COMMENT ON COLUMN "practice_journal_evaluation"."practice_evaluation_criteria_id"	IS 'viide hindamisvormi kriteeriumile';
COMMENT ON COLUMN "practice_journal_evaluation"."value_txt"	IS 'vastus tekst';
COMMENT ON COLUMN "practice_journal_evaluation"."value_nr"	IS 'vastus nr';
COMMENT ON COLUMN "practice_journal_evaluation"."value_clf_code"	IS 'vastus klassifikaator';

ALTER TABLE "practice_journal_evaluation" ADD CONSTRAINT "PK_practice_journal_evaluation"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_practice_journal_evaluation_classifier" ON "practice_journal_evaluation" ("value_clf_code" ASC);
CREATE INDEX "IXFK_practice_journal_evaluation_practice_evaluation" ON "practice_journal_evaluation" ("practice_evaluation_id" ASC);
CREATE INDEX "IXFK_practice_journal_evaluation_practice_evaluation_criteria" ON "practice_journal_evaluation" ("practice_evaluation_criteria_id" ASC);
CREATE INDEX "IXFK_practice_journal_evaluation_practice_journal" ON "practice_journal_evaluation" ("practice_journal_id" ASC);

ALTER TABLE "practice_journal_evaluation" ADD CONSTRAINT "FK_practice_journal_evaluation_classifier"	FOREIGN KEY ("value_clf_code") REFERENCES "classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "practice_journal_evaluation" ADD CONSTRAINT "FK_practice_journal_evaluation_practice_evaluation"	FOREIGN KEY ("practice_evaluation_id") REFERENCES "practice_evaluation" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "practice_journal_evaluation" ADD CONSTRAINT "FK_practice_journal_evaluation_practice_evaluation_criteria"	FOREIGN KEY ("practice_evaluation_criteria_id") REFERENCES "practice_evaluation_criteria" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "practice_journal_evaluation" ADD CONSTRAINT "FK_practice_journal_evaluation_practice_journal"	FOREIGN KEY ("practice_journal_id") REFERENCES "practice_journal" ("id") ON DELETE No Action ON UPDATE No Action;

alter table practice_journal_file add column is_student boolean;
comment on column practice_journal_file.is_student is 'kas file on lisatud õppuri poolt';
