\c hois

DELETE FROM journal_omodule_theme a USING journal_omodule_theme b
WHERE
    a.id < b.id
    AND a.journal_id = b.journal_id and a.curriculum_version_omodule_theme_id = b.curriculum_version_omodule_theme_id and a.lesson_plan_module_id = b.lesson_plan_module_id;

ALTER TABLE journal_omodule_theme ADD UNIQUE (journal_id, lesson_plan_module_id, curriculum_version_omodule_theme_id);