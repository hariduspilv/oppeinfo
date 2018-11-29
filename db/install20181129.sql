\c hois

alter table curriculum add column teacher_id bigint;
comment on column curriculum.teacher_id is 'Õppekava juht';
alter table curriculum add constraint FK_curriculum_teacher foreign key (teacher_id) references teacher(id);
create index IXFK_curriculum_teacher on curriculum (teacher_id);
ALTER TABLE "public"."diploma_supplement_form" ADD COLUMN "is_english" bool;
ALTER TABLE "public"."school" ADD COLUMN "is_letter_grade" bool;
alter table subject_program add column reject_info varchar(4000);
comment on column subject_program.reject_info is 'Tagasilükkamise teade';


CREATE OR REPLACE FUNCTION public.upd_del_apel_result()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
declare
    u_count integer;
		b_count integer;
		r record;
		rr record;
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
					--päevikud, kus on olemas õppur, aga lõpptulemus puudub
					for rr in (select jes.id, jes.grade_code, js.id as s_id, je.id as e_id
										from journal jj
												 join journal_entry je on jj.id=je.journal_id
												 join journal_student js on jj.id=js.journal_id
												 join journal_omodule_theme jot on jj.id=jot.journal_id
												 join curriculum_version_omodule_theme cot on jot.curriculum_version_omodule_theme_id=cot.id
												 left join journal_entry_student jes on je.id=jes.journal_entry_id and js.id=jes.journal_student_id
										where je.entry_type_code='SISSEKANNE_L' and js.student_id=NEW.student_id and 
													(cardinality(r.arr) = 0 and cot.curriculum_version_omodule_id=r.id or 
													 cardinality(r.arr)  > 0 and cot.curriculum_version_omodule_id=any(r.arr)))
					loop
						if rr.id is null THEN
							insert into journal_entry_student(journal_entry_id,journal_student_id,grade_code,grade_inserted,inserted,version,inserted_by,grade_inserted_by)
							values(rr.e_id,rr.s_id,case when cardinality(r.arr) > 1 then 'KUTSEHINDAMINE_A' else r.grade_code end,now(),now(),0,NEW.changed_by,NEW.changed_by);
						ELSE
							if (case when cardinality(r.arr) > 1 then 'KUTSEHINDAMINE_A' else r.grade_code end) !=coalesce(rr.grade_code,'x') then
								insert into journal_entry_student_history(journal_entry_student_id,grade_code,grade_inserted,inserted,version,inserted_by,grade_inserted_by)
								select rr.id,grade_code,grade_inserted,now(),0,NEW.changed_by,grade_inserted_by
								from journal_entry_student where id=rr.id;
								update journal_entry_student set grade_code=case when cardinality(r.arr) > 1 then 'KUTSEHINDAMINE_A' else r.grade_code end,grade_inserted=now(),grade_inserted_by=NEW.changed_by where id=rr.id;
							end if;
						end if;
					end loop;
				end loop;
				x:=upd_student_curriculum_completion(new.student_id);
			ELSE
				for r in (SELECT subj.id as subject_id,
										case when s.is_letter_grade then clf.value2 else clf.value end as grade, --sp.study_period_id,
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
		 										 join apel_application aa on ar.apel_application_id = aa.id
		 										 join school s on aa.school_id = s.id
										WHERE ar.apel_application_id=NEW.id and afr.transfer=true
										union
										SELECT subj.id,
										case when s.is_letter_grade then clf.value2 else clf.value end as grade,
										subj.name_et,
										subj.name_en,
										subj.code,
										subj.credits ,air.grade_code,
										null, null, ar.id as record_id,air.is_optional,air.curriculum_version_hmodule_id, null as study_period_id, now()
										from apel_application_record ar
												 join apel_application_informal_subject_or_module air on ar.id=air.apel_application_record_id
												 join subject subj on air.subject_id=subj.id
												 join classifier clf on clf.code=air.grade_code
		 										 join apel_application aa on ar.apel_application_id = aa.id
		 										 join school s on aa.school_id = s.id
										WHERE ar.apel_application_id=NEW.id and air.transfer=true)
				LOOP
					insert into student_higher_result (
						student_id,			subject_id,				apel_application_record_id,				grade,				grade_date,				grade_mark,				grade_code,
										apel_school_id,				inserted,
						subject_name_et,				subject_name_en,				teachers,				credits,				subject_code,				is_optional,study_period_id,curriculum_version_hmodule_id) 
					values(
						NEW.student_id,r.subject_id,r.record_id,r.grade,r.grade_date,
						case when (r.grade='0' or r.grade='F') then 0 when (r.grade='1' or r.grade='E') then 1 when (r.grade='2' or r.grade='F') then 2 when (r.grade='3' or r.grade='C') then 3 when (r.grade='4' or r.grade='B') then 4 when (r.grade='5' or r.grade='A' and r.grade_code = 'KORGHINDAMINE_5') then 5 else null end,
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