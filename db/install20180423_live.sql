\c hois

delete from journal_capacity_type where journal_id=13573;
delete from journal_omodule_theme where journal_id=13573;
delete from journal_room where journal_id=13573;
delete from journal_teacher where journal_id=13573;
delete from timetable_object_student_group where timetable_object_id in (select id from timetable_object where journal_id=13573);
delete from timetable_object where journal_id=13573;
delete from journal where id=13573;