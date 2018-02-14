\c hois;


update timetable set status_code='TUNNIPLAAN_STAATUS_P' where id=1;
update lesson_time_building_group set valid_thru=null where id=2;
update student set changed=CURRENT_TIMESTAMP(3), changed_by='DATA_TRANSFER_PROCESS' where coalesce(changed_by,'x')='x';

/*delete from lesson_time_building where lesson_time_building_group_id=3;
delete from lesson_time where lesson_time_building_group_id=3;
delete from lesson_time_building_group where id=3;*/