\c hois

CREATE OR REPLACE FUNCTION get_study_year(p_date date, p_school int)
 RETURNS integer
 LANGUAGE plpgsql
AS $function$
declare
    t_rec1 record;
	  p_id integer:=0;
begin
  for t_rec1 in (select * from study_year where school_id=p_school and p_date between start_date and end_date)
	loop
		p_id:=t_rec1.id;
  end loop;
	if p_id=0 then
		for t_rec1 in (select * from study_year where school_id=p_school and start_date > p_date order by start_date)
		loop
			p_id:=t_rec1.id;
			exit;
		end loop;
  end if;
	return p_id;
end
$function$;

CREATE OR REPLACE FUNCTION upd_del_apel_result()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
declare
    u_count integer;
		b_count integer;
		r record;
		p_id integer;
		x integer;
begin
	if tg_op = 'UPDATE' THEN
		if new.status_code='VOTA_STAATUS_C' and OLD.status_code!='VOTA_STAATUS_C' THEN
			--lisame
			if new.is_vocational=true THEN
				for r in (SELECT cmo.id,ar.id as apel_id, clf.value as grade, afr.apel_school_id, --sp.study_period_id,
												case when afr.apel_school_id is not null then afr.name_et else cm.name_et end as name_et,
												case when afr.apel_school_id is not null then afr.name_en else cm.name_en end as name_en,
												case when afr.apel_school_id is not null then afr.credits else cm.credits end as credits, afr.grade_code,
												afr.teachers, afr.apel_school_id, ar.id as record_id,afr.is_optional,afr.curriculum_version_hmodule_id,null as study_period_id,afr.grade_date,
                        array(select sm.curriculum_version_omodule_id from apel_application_formal_replaced_subject_or_module sm where sm.apel_application_record_id=ar.id) as arr
									from apel_application_record ar
											 join apel_application_formal_subject_or_module afr on ar.id=afr.apel_application_record_id
											 join classifier clf on clf.code=afr.grade_code
											 left join apel_school aps on afr.apel_school_id=aps.id
											 left join curriculum_version_omodule cmo on afr.curriculum_version_omodule_id=cmo.id
											 left join curriculum_module cm on cmo.curriculum_module_id=cm.id
									WHERE ar.apel_application_id=NEW.id and afr.transfer=true									
									union
									SELECT cmo.id,ar.id as apel_id,clf.value as grade, null,--sp.study_period_id,
										cm.name_et,
										cm.name_en,
										cm.credits ,air.grade_code,
										null, null, ar.id as record_id,air.is_optional,air.curriculum_version_hmodule_id, null as study_period_id, now(), null
										from apel_application_record ar
												 join apel_application_informal_subject_or_module air on ar.id=air.apel_application_record_id
												 join curriculum_version_omodule cmo on air.curriculum_version_omodule_id=cmo.id
												 join curriculum_module cm on cmo.curriculum_module_id=cm.id
												 join classifier clf on clf.code=air.grade_code
										WHERE ar.apel_application_id=NEW.id and air.transfer=true and air.curriculum_version_omodule_theme_id is null
				)
				LOOP
					p_id:=get_study_year(r.grade_date::date,NEW.school_id::int);
					insert into student_vocational_result (
							student_id,			
							curriculum_version_omodule_id,	
						  apel_application_record_id, apel_school_id,	
							grade,	grade_date,grade_mark,grade_code,
							credits,	teachers,	inserted, 
							module_name_et,	module_name_en,study_year_id,arr_modules)
				values(NEW.student_id,
							 r.id,r.apel_id,	r.apel_school_id, r.grade,r.grade_date,	
							case r.grade when '0' then 0 when '1' then 1 when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else null end,
						r.grade_code,	r.credits,	r.teachers,	now(), r.name_et,	r.name_en, case when p_id > 0 then p_id else null end, r.arr);
				end loop;
				x:=upd_student_curriculum_completion(new.student_id);
			ELSE
				for r in (SELECT subj.id as subject_id,clf.value as grade, --sp.study_period_id,
										case when afr.apel_school_id is not null then afr.name_et else subj.name_et end as name_et,
										case when afr.apel_school_id is not null then afr.name_en else subj.name_en end as name_en,
										case when afr.apel_school_id is not null then afr.subject_code else subj.code end as code,
										case when afr.apel_school_id is not null then afr.credits else subj.credits end as credits, afr.grade_code,
										afr.teachers, afr.apel_school_id, ar.id as record_id,afr.is_optional,afr.curriculum_version_hmodule_id,null as study_period_id,afr.grade_date
										from apel_application_record ar
												 join apel_application_formal_subject_or_module afr on ar.id=afr.apel_application_record_id
												 join classifier clf on clf.code=afr.grade_code
												 left join apel_school aps on afr.apel_school_id=aps.id
												 left join subject subj on afr.subject_id=subj.id 
										WHERE ar.apel_application_id=NEW.id and afr.transfer=true
										union
										SELECT subj.id,clf.value as grade, --sp.study_period_id,
										subj.name_et,
										subj.name_en,
										subj.code,
										subj.credits ,air.grade_code,
										null, null, ar.id as record_id,air.is_optional,air.curriculum_version_hmodule_id, null as study_period_id, now()
										from apel_application_record ar
												 join apel_application_informal_subject_or_module air on ar.id=air.apel_application_record_id
												 join subject subj on air.subject_id=subj.id
												 join classifier clf on clf.code=air.grade_code
										WHERE ar.apel_application_id=NEW.id and air.transfer=true)
				LOOP
					insert into student_higher_result (
						student_id,			subject_id,				apel_application_record_id,				grade,				grade_date,				grade_mark,				grade_code,
										apel_school_id,				inserted,
						subject_name_et,				subject_name_en,				teachers,				credits,				subject_code,				is_optional,study_period_id,curriculum_version_hmodule_id) 
					values(
						NEW.student_id,r.subject_id,r.record_id,r.grade,r.grade_date,
						case r.grade when '0' then 0 when '1' then 1 when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else null end,
						r.grade_code,
						r.apel_school_id,--apel_school_id
						now(),
						r.name_et,coalesce(r.name_en,r.name_et),r.teachers,	r.credits,r.code,				r.is_optional,null,r.curriculum_version_hmodule_id); --is_optional
				--return null;
			end loop;
		end if;
	elsif new.status_code!='VOTA_STAATUS_C' and OLD.status_code='VOTA_STAATUS_C' THEN
			--kustutame kõik ära
			if new.is_vocational=true THEN
				delete from student_vocational_result where apel_application_record_id in (select aa.id from apel_application_record aa where aa.apel_application_id=NEW.id);
				x:=upd_student_curriculum_completion(new.student_id);
			ELSE
				delete from student_higher_result where apel_application_record_id in (select aa.id from apel_application_record aa where aa.apel_application_id=NEW.id);
			end if;
		end if;
	end if;
	return null;
end;
$function$;

CREATE OR REPLACE FUNCTION ins_upd_del_result()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
declare
    u_count integer;
		b_count integer;
		r record;
		x integer;
begin
	if tg_op in ('INSERT','UPDATE') and NEW.id is not null and COALESCE(NEW.grade,'x')='x' or tg_op in ('DELETE') THEN
		if tg_op in ('INSERT','UPDATE') THEN	
			delete from student_higher_result where protocol_student_id=NEW.id;
			delete from student_vocational_result where protocol_student_id=NEW.id;
		ELSE
			delete from student_higher_result where protocol_student_id=old.id;
			delete from student_vocational_result where protocol_student_id=old.id;
		  x:=upd_student_curriculum_completion(old.student_id);
		end if;
	elsif NEW.id is not null then
		for r in (SELECT coalesce(ph.final_subject_id, sp.subject_id) as subject_id,clf.value as grade, sp.study_period_id,pp.school_id,
											--curriculum_version_hmodule_id
											subj.name_et,subj.name_en,subj.code,subj.credits,
											--is_optional
											(select string_agg(pers.firstname||' '||pers.lastname,', ') 
												from subject_study_period_teacher st join teacher tt on st.teacher_id=tt.id join person pers on tt.person_id=pers.id
													where st.subject_study_period_id=sp.id) as teachers --teachers
							from protocol pp 
									 join protocol_hdata ph on pp.id=ph.protocol_id
									 left join subject_study_period sp on ph.subject_study_period_id=sp.id
									 join subject subj on sp.subject_id=subj.id or ph.final_subject_id = subj.id
									 join classifier clf on clf.code=NEW.grade_code
							where new.protocol_id=pp.id)
		LOOP
			update student_higher_result set 
				grade=coalesce(NEW.grade,r.grade),
				grade_date=NEW.grade_date,
				grade_mark=coalesce(NEW.grade_mark,case r.grade when '0' then 0 when '1' then 1 when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else null end),
				grade_code=NEW.grade_code,
				apel_school_id=null,
				subject_name_et=r.name_et,
				subject_name_en=coalesce(r.name_en,r.name_et),
				teachers=r.teachers,
				credits=r.credits,
				subject_code=r.code,
				is_optional=false,
				subject_id=r.subject_id,
				changed=now(),
				study_period_id=coalesce(r.study_period_id, get_study_period(NEW.grade_date::date, r.school_id::int))
			where protocol_student_id=NEW.id;
			GET DIAGNOSTICS u_count = ROW_COUNT;

			if coalesce(u_count,0)=0 THEN
				insert into student_higher_result (
					student_id,			subject_id,				protocol_student_id,				grade,				grade_date,				grade_mark,				grade_code,
					apel_application_record_id,				apel_school_id,				inserted,
					subject_name_et,				subject_name_en,				teachers,				credits,				subject_code,				is_optional,study_period_id) 
				values(
					NEW.student_id,r.subject_id,NEW.id,coalesce(NEW.grade,r.grade),NEW.grade_date,
					coalesce(NEW.grade_mark,case r.grade when '0' then 0 when '1' then 1 when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else null end),				NEW.grade_code,
					null,--apel_application_record_id,
					null,--apel_school_id
					now(),
					r.name_et,coalesce(r.name_en,r.name_et),r.teachers,	r.credits,r.code,				FALSE,r.study_period_id); --is_optional
			end if;

			/*--kustutame üleliigset eelmist rida
			delete from student_higher_result
WHERE id IN (SELECT id
              FROM (SELECT id,
                             ROW_NUMBER() OVER (partition BY column1, column2, column3 ORDER BY id) AS rnum
                     FROM tablename) t
              WHERE t.rnum > 1);*/
			return null;
		end loop;
    for r in (select crm.name_et, crm.name_en, crm.credits, vv.curriculum_version_omodule_id, crm.credits, pers.firstname||' '||pers.lastname as teachers, clf.value as grade,
							  vv.study_year_id, (select count(*) from student_vocational_result sr where sr.curriculum_version_omodule_id=vv.curriculum_version_omodule_id and sr.student_id=NEW.student_id) as total
							from protocol pp 
									 join protocol_vdata vv on pp.id=vv.protocol_id
									 join curriculum_version_omodule cro on vv.curriculum_version_omodule_id=cro.id
									 join curriculum_module crm on cro.curriculum_module_id=crm.id
									 join teacher tt on vv.teacher_id=tt.id 
									 join person pers on tt.person_id=pers.id
									 join classifier clf on clf.code=NEW.grade_code
							where new.protocol_id=pp.id)
		LOOP
			update student_vocational_result set 
				curriculum_version_omodule_id=r.curriculum_version_omodule_id,				
				grade=coalesce(NEW.grade,r.grade),				
				grade_date=NEW.grade_date,				
				grade_mark=coalesce(NEW.grade_mark,case r.grade when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else null end),				
				grade_code=NEW.grade_code,
				credits=r.credits,	
				teachers=r.teachers,	
				changed=now(), 
				module_name_et=r.name_et,	
				module_name_en=r.name_en,
				study_year_id=r.study_year_id
			where protocol_student_id=NEW.id;
			GET DIAGNOSTICS u_count = ROW_COUNT;

			if coalesce(u_count,0)=0 THEN
				insert into student_vocational_result (
					student_id,			curriculum_version_omodule_id,				protocol_student_id,		grade,				grade_date,				grade_mark,				grade_code,
					credits,	teachers,	inserted, module_name_et,	module_name_en,study_year_id)
				values(NEW.student_id,r.curriculum_version_omodule_id,NEW.id,		coalesce(NEW.grade,r.grade),	NEW.grade_date,	
							coalesce(NEW.grade_mark,case r.grade when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else null end),	NEW.grade_code,
					r.credits,	r.teachers,	now(), r.name_et,	r.name_en, r.study_year_id);
			end if;
			
--raise notice 'siin %', r.total;
			-- kustutame üleliigsed vanad read
			if r.total > 1 THEN
					DELETE FROM student_vocational_result 
						WHERE id IN (SELECT id
              FROM (SELECT id,
                             ROW_NUMBER() OVER (partition BY curriculum_version_omodule_id, student_id ORDER BY grade_date desc nulls last, id desc) AS rnum
                     FROM student_vocational_result where student_id=NEW.student_id and curriculum_version_omodule_id=r.curriculum_version_omodule_id) t
              WHERE t.rnum > 1);
			end if;
			x:=upd_student_curriculum_completion(new.student_id);
			return null;
		end loop;
  end if;
	return null;
end;
$function$;

CREATE OR REPLACE FUNCTION hois_audit()
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
exception when others then
	return null;
end;
$function$;


update protocol_student set changed=current_timestamp(3), changed_by=coalesce(changed_by,'Automaat');
update apel_application set status_code='OPINGUKAVA_STAATUS_S' where status_code='VOTA_STAATUS_C';
update apel_application set status_code='VOTA_STAATUS_C' where status_code='OPINGUKAVA_STAATUS_S';



do $$
DECLARE
	r record;
	x integer;
BEGIN
	for r in (select * from student)
	LOOP
		x:=upd_student_curriculum_completion(r.id);
	end loop;
end;
$$;

update classifier set extraval2='M' where code in ('EHIS_KOOL_108','EHIS_KOOL_144','EHIS_KOOL_82','EHIS_KOOL_60','EHIS_KOOL_2317','EHIS_KOOL_133','EHIS_KOOL_56','EHIS_KOOL_103','EHIS_KOOL_5751','EHIS_KOOL_146');
update classifier set extraval2='B' where code='EHIS_KOOL_93';
update classifier set extraval2='A' where code='EHIS_KOOL_61';
update classifier set extraval2='C' where code='EHIS_KOOL_94';
update classifier set extraval2='D' where code='EHIS_KOOL_112';
update classifier set extraval2='E' where code='EHIS_KOOL_60';
update classifier set extraval2='K' where code='EHIS_KOOL_57';
update classifier set extraval2='L' where main_class_code like 'EHIS_KOOL' and coalesce(extraval2,'x')='x';

alter table diploma add column school_name_et varchar(100);
alter table diploma add column curriculum_grade_name_et varchar(255);
alter table diploma add column curriculum_grade_name_en varchar(255);
alter table diploma add column city varchar(100);
comment on column diploma.school_name_et is 'kooli nimi nimetavas';
comment on column diploma.curriculum_grade_name_et is 'kraadi nimi e.k.';
comment on column diploma.curriculum_grade_name_en is 'kraadi nimi i.k.';
comment on column diploma.city is 'koha nimi kõrg. puhul';
update classifier set extraval2='bakalaureuseõppe' where code in ('OPPEASTE_511','OPPEASTE_512');
update classifier set extraval2='inseneriõppe' where code in ('OPPEASTE_513');
update classifier set extraval2='rakenduskõrgharidusõppe' where code in ('OPPEASTE_514');
update classifier set extraval2='magistriõppe' where code in ('OPPEASTE_612','OPPEASTE_613','OPPEASTE_614');
update classifier set extraval2='doktoriõppe' where code in ('OPPEASTE_732','OPPEASTE_733','OPPEASTE_734','OPPEASTE_7R');
update classifier set extraval2='õpetajakoolituse' where code in ('OPPEASTE_633');
update classifier set extraval2='kutsekõrgharidusõppe' where code in ('OPPEASTE_523','OPPEASTE_523T');
update classifier set extraval2='integreeritud õppe' where code in ('OPPEASTE_503','OPPEASTE_502');

insert into classifier (code, value, name_et, main_class_code, inserted, valid, is_vocational, is_higher, version) values
('TEEMAOIGUS_RYHMAJUHATAJA', 'RYHMAJUHATAJA', 'Rühmajuhataja aruanne', 'TEEMAOIGUS', now(), true,  true,  true, 0);
 insert into user_role_default (object_code, permission_code, role_code) values 
('TEEMAOIGUS_RYHMAJUHATAJA', 'OIGUS_V', 'ROLL_A');
insert into user_role_default (object_code, permission_code, role_code) values 
('TEEMAOIGUS_RYHMAJUHATAJA', 'OIGUS_V', 'ROLL_O');