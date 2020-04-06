\c hois

alter table school add column timetable_code varchar(100);
comment on column school.timetable_code is 'tunniplaani lisamise viis, viide klassifikaatorile TIMETABLE';
create index IXFK_school_classifier_03 on school(timetable_code);
alter table school add constraint FK_school_classifier_03 foreign key(timetable_code) references classifier(code);



insert into classifier(code, value, name_et, name_en, main_class_code, "valid", ehis_value, is_vocational, is_higher, inserted, "version") values
('TIMETABLE', 'TIMETABLE', 'Tunniplaani lisamise viis', null, null, true, null, true, true, now(), 0),
('TIMETABLE_UNTIS', '0', 'Untis', 'Untis', 'TIMETABLE', true, 0, true, true, now(), 0),
('TIMETABLE_ASC', '1', 'aSc TimeTables', 'aSc TimeTables', 'TIMETABLE', true, 1, true, true, now(), 0),
('TIMETABLE_EI', '2', 'Ei kasutata', 'Ei kasutata', 'TIMETABLE', true, 2, true, true, now(), 0); 

update school set timetable_Code='TIMETABLE_EI';
update student set previous_study_level_code=null where type_code='OPPUR_K' and previous_study_level_code like 'EHIS_KODU_OPPEASTE%';

update directive_student set scholarship_application_id=null where scholarship_application_id=1513;
delete from scholarship_application where id=1513;
create unique index scholarship_application_uq on scholarship_application(student_id,scholarship_term_id);

do $$
declare r record;
	p_id integer;
BEGIN
	for r in (select distinct s.school_id,ec.id as e_id,escp.id as pp_id,cc.* 
						from enterprise e 
								join enterprise_school ec on e.id=ec.enterprise_id 
								join contract cc on e.id=cc.enterprise_id join student s on cc.student_id=s.id and cc.inserted < '12-03-2019'
								left join enterprise_school_person escp on ec.id=escp.enterprise_school_id and upper(trim(replace(escp.firstname||' '||escp.lastname,' ','')))=upper(trim(replace(cc.supervisor_name,' ',''))))
	loop
		if r.pp_id is not null then
			update enterprise_school_person set is_supervisor=true where id=r.pp_id;
		else
			insert into enterprise_school_person(enterprise_school_id,firstname,lastname,is_supervisor,is_contact,email,phone,inserted,version,inserted_by)
			values(r.e_id,case when position(' ' in r.supervisor_name) > 1 then substring(r.supervisor_name,1,position(' ' in r.supervisor_name)-1) else ' ' end ,
			case when position(' ' in r.supervisor_name) > 1 then coalesce(substring(r.supervisor_name,position(' ' in r.supervisor_name)+1),' ') else ' ' end,true,false,
			r.supervisor_email,r.supervisor_phone,current_Timestamp(3),0,'Automaat');
		end if;
	end loop;
end;
$$;

INSERT INTO "public"."classifier" ("code", "value", "value2", "name_et", "name_en", "name_ru", "parent_class_code", "main_class_code", "inserted", "changed", "valid", "description", "valid_from", "valid_thru", 
"extraval1", "extraval2", "ehis_value", "is_vocational", "is_higher", "version", "inserted_by", "changed_by") 
VALUES ('OPPEVORM_SO', 'SO', NULL, 'Sessioonõpe', NULL, NULL, NULL, 'OPPEVORM', '2016-10-31 18:43:15.681826', '2019-12-16 10:28:44.788', 'f', NULL, NULL, NULL, NULL, NULL, 'SO', 'f', 't', '5', NULL, 'Anu Piirisild (47611116519)');
