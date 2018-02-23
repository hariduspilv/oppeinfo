\c hois;

alter table teacher add column add_info varchar(4000);
alter table journal add column add_module_outcomes boolean;
update journal set add_module_outcomes=false, changed=current_timestamp(3),changed_by='Automaat';
comment on column journal.add_module_outcomes is 'kas lisada õpiväljundid ';
INSERT INTO classifier ("code", "value", "value2", "name_et", "name_en", "name_ru", "parent_class_code", "main_class_code", "inserted", "changed", "valid", "description", "valid_from", "valid_thru", "extraval1", "extraval2", "ehis_value", "is_vocational", "is_higher", "version", "inserted_by", "changed_by") VALUES ('SISSEKANNE_O', 'O', NULL, 'Õpiväljund', NULL, NULL, NULL, 'SISSEKANNE', '2017-05-01 15:45:11.913934', NULL, 't', NULL, NULL, NULL, NULL, NULL, NULL, 't', 't', '0', NULL, NULL);
alter table journal_entry add column curriculum_module_outcomes_id BIGINT;
comment on column journal_entry.curriculum_module_outcomes_id is 'viide õpiväljundile';
create index IXFK_journal_entry_curriculum_module_outcomes on journal_entry(curriculum_module_outcomes_id);
alter table journal_entry add constraint FK_journal_entry_curriculum_module_outcomes foreign key (curriculum_module_outcomes_id) references  curriculum_module_outcomes(id);
create unique index UQ_journal_entry_ourcomes on journal_entry(curriculum_module_outcomes_id,journal_id);

alter table person add address_ads_oid varchar(50);
alter table school add address_ads_oid varchar(50);
alter table sais_application add address_ads_oid varchar(50);
alter table building add address_ads_oid varchar(50);
alter table scholarship_application add address_ads_oid varchar(50);

CREATE TABLE "student_higher_result_module"
(
	"student_higher_result_id" bigserial NOT NULL,
	"curriculum_version_hmodule_id" bigserial NOT NULL,    -- viide kõrghariduse moodulile
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL,
	"version" integer NOT NULL
)
;


COMMENT ON TABLE "student_higher_result_module"	IS 'ainete ja moodulite vastavus';
COMMENT ON COLUMN "student_higher_result_module"."curriculum_version_hmodule_id"	IS 'viide kõrghariduse moodulile';



ALTER TABLE "student_higher_result_module" ADD CONSTRAINT "PK_student_higher_result_module"	PRIMARY KEY ("student_higher_result_id");
CREATE INDEX "IXFK_student_higher_result_module_curriculum_version_hmodule" ON "student_higher_result_module" ("curriculum_version_hmodule_id" ASC);
CREATE INDEX "IXFK_student_higher_result_module_student_higher_result" ON "student_higher_result_module" ("student_higher_result_id" ASC);

ALTER TABLE "student_higher_result_module" ADD CONSTRAINT "FK_student_higher_result_module_curriculum_version_hmodule"	FOREIGN KEY ("curriculum_version_hmodule_id") REFERENCES "public"."curriculum_version_hmodule" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "student_higher_result_module" ADD CONSTRAINT "FK_student_higher_result_module_student_higher_result"	FOREIGN KEY ("student_higher_result_id") REFERENCES "student_higher_result" ("id") ON DELETE No Action ON UPDATE No Action;

