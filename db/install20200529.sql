\c tahvel_20200518

alter table scholarship_application add column wag_mark numeric (5,2);
alter table scholarship_application add column last_period_wag_mark numeric (5,2);

alter table scholarship_application alter column average_mark type numeric (5,2);
alter table scholarship_application alter column last_period_mark type numeric (5,2);

alter table scholarship_application add column is_sais boolean;

alter table protocol_hdata add column curriculum_version_hmodule_id bigint;
create index IXFK_protocol_hdata_curriculum_version_hmodule on protocol_hdata(curriculum_version_hmodule_id);
alter table protocol_hdata add constraint FK_protocol_hdata_curriculum_version_hmodule foreign key(curriculum_version_hmodule_id) references curriculum_version_hmodule(id);
comment on column protocol_hdata.curriculum_version_hmodule_id is 'viide kõrgh moodulile moodulite hindamise korral';

alter table protocol_hdata add teacher_id bigint;
create index protocol_hdata_teacher_idx on protocol_hdata(teacher_id);
alter table protocol_hdata add constraint FK_protocol_hdata_teacher_id foreign key(teacher_id) references teacher(id);
comment on column protocol_hdata.teacher_id is 'viide õpetajale mooduli protokolli puhul kõrgh moodulite hindamise puhul';

alter table student_higher_result add column is_module boolean default false not null;

INSERT INTO classifier (code, value,  name_et,  main_class_code, inserted, valid, is_vocational, is_higher, version) 
VALUES ('KASKKIRI_DUPLIKAAT', 'DUPLIKAAT', 'Duplikaadi väljastamine', 'KASKKIRI', current_timestamp(3), 't', 't', 't', '0');
INSERT INTO classifier (code, value,  name_et,  main_class_code, inserted, valid, is_vocational, is_higher, version) 
VALUES ('LOPUDOK_STAATUS_C', 'C', 'Tühistatud', 'LOPUDOK_STAATUS', current_timestamp(3), 't', 't', 't', '0');


alter table diploma 
add column diploma_id bigint NULL,    -- viide tunnistusele/diplomile, mille kohta tehti duplikaat
add column is_duplicate boolean NULL    -- kas tegemist on duplikaadiga
;

alter table diploma_supplement
add column diploma_supplement_id bigint NULL,    -- viide hinnetelehele, mille kohta tehti duplikaat
add column is_duplicate boolean NULL,    -- kas hinneteleht/eesti k. õiend on duplikaat
add column is_duplicate_en boolean NULL    -- kas i.k. akad. õiend on duplikaat
;

alter TABLE directive_student
add column diploma_id bigint NULL,    -- viide lõputunnistusele/diplomile
add column diploma_supplement_id bigint NULL,    -- viide hinnetelehele/akad õiendile
add column diploma_supplement_en_id bigint NULL,    -- viide ingl.k. akad õiendile
add column diploma_form_id bigint NULL    -- viide tunnistue/diplomi blanketile
;

CREATE TABLE directive_student_duplicate_form
(
	id bigserial NOT NULL,
	directive_student_id bigint NOT NULL,
	form_id bigint NOT NULL,
	is_en boolean NOT NULL,    -- kas on ingliskeele akad õiend, eestikeelse puhul peab olema false
	inserted timestamp without time zone NOT NULL,
	changed timestamp without time zone NULL,
	version integer NOT NULL,
	inserted_by varchar(100)	 NOT NULL,
	changed_by varchar(100)	 NULL
)
;

COMMENT ON COLUMN diploma.diploma_id	IS 'viide tunnistusele/diplomile, mille kohta tehti duplikaat';
COMMENT ON COLUMN diploma.is_duplicate	IS 'kas tegemist on duplikaadiga';
COMMENT ON COLUMN diploma_supplement.diploma_supplement_id	IS 'viide hinnetelehele, mille kohta tehti duplikaat';
COMMENT ON COLUMN diploma_supplement.is_duplicate	IS 'kas hinneteleht/eesti k. õiend on duplikaat';
COMMENT ON COLUMN diploma_supplement.is_duplicate_en	IS 'kas i.k. akad. õiend on duplikaat';
COMMENT ON COLUMN directive_student.diploma_id	IS 'viide lõputunnistusele/diplomile';
COMMENT ON COLUMN directive_student.diploma_supplement_id	IS 'viide hinnetelehele/akad õiendile';
COMMENT ON COLUMN directive_student.diploma_supplement_en_id	IS 'viide ingl.k. akad õiendile';
COMMENT ON COLUMN directive_student.diploma_form_id	IS 'viide tunnistue/diplomi blanketile';

COMMENT ON TABLE directive_student_duplicate_form	IS 'duplikaadi väljastamisel tühistatavad hinnetelehtede ja akad. õiendite blanketid ';
COMMENT ON COLUMN directive_student_duplicate_form.is_en	IS 'kas on ingliskeele akad õiend, eestikeelse puhul peab olema false';

CREATE INDEX IXFK_diploma_diploma ON diploma (diploma_id ASC);
CREATE INDEX IXFK_diploma_supplement_diploma_supplement ON diploma_supplement (diploma_supplement_id ASC);
CREATE INDEX IXFK_directive_student_diploma ON directive_student (diploma_id ASC);
CREATE INDEX IXFK_directive_student_diploma_supplement ON directive_student (diploma_supplement_id ASC);
CREATE INDEX IXFK_directive_student_diploma_supplement_02 ON directive_student (diploma_supplement_en_id ASC);
CREATE INDEX IXFK_directive_student_form ON directive_student (diploma_form_id ASC);

CREATE INDEX IXFK_directive_student_duplicate_form_directive_student ON directive_student_duplicate_form (directive_student_id ASC);
CREATE INDEX IXFK_directive_student_duplicate_form_form ON directive_student_duplicate_form (form_id ASC);

/* Create Foreign Key Constraints */

ALTER TABLE diploma ADD CONSTRAINT FK_diploma_diploma	FOREIGN KEY (diploma_id) REFERENCES diploma (id) ON DELETE No Action ON UPDATE No Action;
ALTER TABLE diploma_supplement ADD CONSTRAINT FK_diploma_supplement_diploma_supplement	FOREIGN KEY (diploma_supplement_id) REFERENCES diploma_supplement (id) ON DELETE No Action ON UPDATE No Action;
ALTER TABLE directive_student ADD CONSTRAINT FK_directive_student_diploma	FOREIGN KEY (diploma_id) REFERENCES diploma (id) ON DELETE No Action ON UPDATE No Action;
ALTER TABLE directive_student ADD CONSTRAINT FK_directive_student_diploma_supplement	FOREIGN KEY (diploma_supplement_id) REFERENCES diploma_supplement (id) ON DELETE No Action ON UPDATE No Action;
ALTER TABLE directive_student ADD CONSTRAINT FK_directive_student_diploma_supplement_02	FOREIGN KEY (diploma_supplement_en_id) REFERENCES diploma_supplement (id) ON DELETE No Action ON UPDATE No Action;
ALTER TABLE directive_student ADD CONSTRAINT FK_directive_student_form	FOREIGN KEY (diploma_form_id) REFERENCES form (id) ON DELETE No Action ON UPDATE No Action;

ALTER TABLE directive_student_duplicate_form ADD CONSTRAINT FK_directive_student_duplicate_form_directive_student	FOREIGN KEY (directive_student_id) REFERENCES directive_student (id) ON DELETE No Action ON UPDATE No Action;
ALTER TABLE directive_student_duplicate_form ADD CONSTRAINT FK_directive_student_duplicate_form_form	FOREIGN KEY (form_id) REFERENCES form (id) ON DELETE No Action ON UPDATE No Action;

alter table student_curriculum_completion add column is_modules_ok boolean default true not null;

alter table diploma_supplement alter column status_code drop not null;

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
                        array(select sm.curriculum_version_omodule_id from apel_application_formal_replaced_subject_or_module sm where sm.apel_application_record_id=ar.id and sm.curriculum_version_omodule_theme_id is null) as arr
									from apel_application_record ar
											 join apel_application_formal_subject_or_module afr on ar.id=afr.apel_application_record_id
											 join classifier clf on clf.code=afr.grade_code
											 left join apel_school aps on afr.apel_school_id=aps.id
											 left join curriculum_version_omodule cmo on afr.curriculum_version_omodule_id=cmo.id
											 left join curriculum_module cm on cmo.curriculum_module_id=cm.id
									WHERE ar.apel_application_id=NEW.id and afr.transfer=true	and (select count(sm.curriculum_version_omodule_id) from apel_application_formal_replaced_subject_or_module sm where sm.apel_application_record_id=ar.id and sm.curriculum_version_omodule_theme_id is null)>0														
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
					for rr in (select distinct jes.id, jes.grade_code, js.id as s_id, je.id as e_id
										from journal jj
												 join journal_entry je on jj.id=je.journal_id
												 join journal_student js on jj.id=js.journal_id
												 join journal_omodule_theme jot on jj.id=jot.journal_id
												 join curriculum_version_omodule_theme cot on jot.curriculum_version_omodule_theme_id=cot.id
												 left join journal_entry_student jes on je.id=jes.journal_entry_id and js.id=jes.journal_student_id
										where je.entry_type_code='SISSEKANNE_L' and js.student_id=NEW.student_id and 
													((r.arr is null or cardinality(r.arr) = 0)  and cot.curriculum_version_omodule_id=r.id or 
													 cardinality(r.arr)  > 0 and cot.curriculum_version_omodule_id=any(r.arr)))
					loop
						if rr.id is null THEN
							insert into journal_entry_student(journal_entry_id,journal_student_id,grade_code,grade_inserted,inserted,version,inserted_by,grade_inserted_by)
							values(rr.e_id,rr.s_id,case when cardinality(r.arr) > 1 then 'KUTSEHINDAMINE_A' else r.grade_code end,now(),now(),0,NEW.changed_by,NEW.changed_by);
						ELSE
							if (case when cardinality(r.arr) > 1 then 'KUTSEHINDAMINE_A' else r.grade_code end) !=coalesce(rr.grade_code,'x') then
								insert into journal_entry_student_history(journal_entry_student_id,grade_code,grade_inserted,inserted,version,inserted_by,grade_inserted_by)
								select rr.id,grade_code,grade_inserted,now(),0,NEW.changed_by,grade_inserted_by
								from journal_entry_student where id=rr.id and coalesce(grade_code,'x')!='x';
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
										afr.teachers, afr.apel_school_id, ar.id as record_id,afr.is_optional,afr.curriculum_version_hmodule_id,null as study_period_id,coalesce(afr.grade_date,aa.confirmed) as grade_date
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
										null, null, ar.id as record_id,air.is_optional,air.curriculum_version_hmodule_id, null as study_period_id, coalesce(aa.confirmed,now())
										from apel_application_record ar
												 join apel_application_informal_subject_or_module air on ar.id=air.apel_application_record_id
												 join subject subj on air.subject_id=subj.id
												 join classifier clf on clf.code=air.grade_code
		 										 join apel_application aa on ar.apel_application_id = aa.id
		 										 join school s on aa.school_id = s.id
										WHERE ar.apel_application_id=NEW.id and air.transfer=true)
				LOOP
					p_id:=get_study_period(r.grade_date::date, NEW.school_id::int);
					if coalesce(p_id,0)=0 then
							p_id:=null;
					end if;
					insert into student_higher_result (
						student_id,			subject_id,				apel_application_record_id,				grade,				grade_date,				grade_mark,				grade_code,
										apel_school_id,				inserted,
						subject_name_et,				subject_name_en,				teachers,				credits,				subject_code,				is_optional,study_period_id,curriculum_version_hmodule_id) 
					values(
						NEW.student_id,r.subject_id,r.record_id,r.grade,r.grade_date,
						case when (r.grade='0' or r.grade='F') then 0 when (r.grade='1' or r.grade='E') then 1 when (r.grade='2' or r.grade='D') then 2 when (r.grade='3' or r.grade='C') then 3 when (r.grade='4' or r.grade='B') then 4 when (r.grade='5' or r.grade='A' and r.grade_code = 'KORGHINDAMINE_5') then 5 else null end,
						r.grade_code,
						r.apel_school_id,--apel_school_id
						now(),
						r.name_et,coalesce(r.name_en,r.name_et),r.teachers,	r.credits,r.code,				r.is_optional,p_id,r.curriculum_version_hmodule_id) returning id into p_id; --is_optional

				if coalesce(r.subject_id,0) > 0 then
					--select distinct first_value(id)over(partition by r.subject_id order by grade_date desc nulls last) into p_id from student_higher_result where student_id=NEW.student_id and subject_id=r.subject_id;
					update student_higher_result set is_active=false where student_id=NEW.student_id and subject_id=r.subject_id and id!=p_id;
				end if;
				x:=upd_student_curriculum_completion(new.student_id);
				--return null;
			end loop;
		end if;
	elsif new.status_code!='VOTA_STAATUS_C' and OLD.status_code='VOTA_STAATUS_C' THEN
			--kustutame kõik ära
			if new.is_vocational=true THEN
				delete from student_vocational_result where apel_application_record_id in (select aa.id from apel_application_record aa where aa.apel_application_id=NEW.id);
				x:=upd_student_curriculum_completion(new.student_id);
			ELSE
				delete from student_higher_result_module where student_higher_result_id in (select id from student_higher_result where apel_application_record_id in (select aa.id from apel_application_record aa where aa.apel_application_id=NEW.id));
				delete from student_higher_result where apel_application_record_id in (select aa.id from apel_application_record aa where aa.apel_application_id=NEW.id);
				for r in (select distinct first_value(id)over(partition by subject_id order by grade_date desc nulls last) as id, subject_id 
								from student_higher_result 
								where student_id=NEW.student_id)
				loop
					update student_higher_result ss 
							set is_active=case when ss.id=r.id then true else false end
					where ss.student_id=NEW.student_id and r.subject_id=ss.subject_id;
				end LOOP;
				x:=upd_student_curriculum_completion(new.student_id);
			end if;
		end if;
	end if;
	return null;
end;
$function$
;

CREATE OR REPLACE FUNCTION public.ins_upd_del_result()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
declare
    u_count integer;
		b_count integer;
		r record;
		x integer;
		p_id bigint;
	st_id bigint;
begin
	if tg_op in ('DELETE') then
		st_id:=old.student_id;
	else
		st_id:=new.student_id;
	end if;
	if tg_op in ('INSERT','UPDATE') and NEW.id is not null and COALESCE(NEW.grade,'x')='x' or tg_op in ('DELETE') THEN
		if tg_op in ('INSERT','UPDATE') THEN	
			delete from student_higher_result_module where student_higher_result_id in (select id from student_higher_result where protocol_student_id=NEW.id);
			delete from student_higher_result where protocol_student_id=NEW.id;
			delete from student_vocational_result where protocol_student_id=NEW.id;
		ELSE
			delete from student_higher_result_module where student_higher_result_id in (select id from student_higher_result where protocol_student_id=old.id);
			delete from student_higher_result where protocol_student_id=old.id;
			delete from student_vocational_result where protocol_student_id=old.id;
		end if;
		for r in (select distinct first_value(id)over(partition by subject_id order by case when coalesce(apel_application_record_id,0)=0 then 1 else 0 end, grade_date desc nulls last) as id, subject_id 
								from student_higher_result 
								where student_id=st_id)
		loop
			update student_higher_result ss 
				set is_active=case when ss.id=r.id then true else false end
			where ss.student_id=st_id and r.subject_id=ss.subject_id;
		end LOOP;
		x:=upd_student_curriculum_completion(st_id);
	elsif NEW.id is not null then
		for r in (SELECT coalesce(ph.final_subject_id, sp.subject_id) as subject_id,clf.value as grade, sp.study_period_id,pp.school_id,
									coalesce(cvh.id,coalesce(hn.id,ds.curriculum_version_hmodule_id)) as curriculum_version_hmodule_id,coalesce(shs.is_optional,ds.is_optional) is_optional,
											--curriculum_version_hmodule_id
											coalesce(cvh.name_et,subj.name_et) as name_et,coalesce(cvh.name_en,subj.name_en) name_en,subj.code,
											coalesce(cvh.total_credits,subj.credits) credits,
											--is_optional
											case when ppp.id is not null then ppp.firstname||' '||ppp.lastname else (select string_agg(pers.firstname||' '||pers.lastname,', ') 
												from subject_study_period_teacher st join teacher tt on st.teacher_id=tt.id join person pers on tt.person_id=pers.id
													where st.subject_study_period_id=sp.id) end as teachers, --teachers
									cvh.id as cvh_id
							from protocol pp 
									 join protocol_hdata ph on pp.id=ph.protocol_id
									 join student st on st.id=new.student_id
									 left join subject_study_period sp on ph.subject_study_period_id=sp.id
									 left join (declaration_subject ds join declaration  dd on ds.declaration_id=dd.id and dd.student_id=new.student_id) on sp.id=ds.subject_study_period_id 
									 left join subject subj on ph.curriculum_version_hmodule_id is null and (sp.subject_id=subj.id or ph.final_subject_id = subj.id)
									 left join curriculum_version_hmodule cvh on ph.curriculum_version_hmodule_id=cvh.id
									 left join (teacher ttt join person ppp on ttt.person_id=ppp.id) on ph.teacher_id=ttt.id
									 join classifier clf on clf.code=NEW.grade_code
									 left join (curriculum_version_hmodule_subject shs join curriculum_version_hmodule hn on shs.curriculum_version_hmodule_id=hn.id) 
															on shs.subject_id=ph.final_subject_id and 
																hn.curriculum_version_id=st.curriculum_version_id and hn.type_code in ('KORGMOODUL_F','KORGMOODUL_L')
							where new.protocol_id=pp.id and (subj.id is not null or cvh.id is not null))
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
				curriculum_version_hmodule_id=r.curriculum_version_hmodule_id,
				is_optional=coalesce(r.is_optional,false),
				--is_optional=false,
				subject_id=r.subject_id,
				changed=now(),
				study_period_id=coalesce(r.study_period_id, get_study_period(NEW.grade_date::date, r.school_id::int)),
				is_module=case when coalesce(r.cvh_id,0) > 0 then true else false end
			where protocol_student_id=NEW.id;
			GET DIAGNOSTICS u_count = ROW_COUNT;

			if coalesce(u_count,0)=0 THEN
				insert into student_higher_result (
					student_id,			subject_id,				protocol_student_id,				grade,				grade_date,				grade_mark,				grade_code,
					apel_application_record_id,				apel_school_id,				inserted, curriculum_version_hmodule_id,is_optional,
					subject_name_et,				subject_name_en,				teachers,				credits,				subject_code,				study_period_id, is_module) 
				values(
					NEW.student_id,r.subject_id,NEW.id,coalesce(NEW.grade,r.grade),NEW.grade_date,
					coalesce(NEW.grade_mark,case r.grade when '0' then 0 when '1' then 1 when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else null end),				NEW.grade_code,
					null,--apel_application_record_id,
					null,--apel_school_id
					now(),r.curriculum_version_hmodule_id,coalesce(r.is_optional,false),
					r.name_et,coalesce(r.name_en,r.name_et),r.teachers,	r.credits,r.code,				coalesce(r.study_period_id, get_study_period(NEW.grade_date::date, r.school_id::int)),case when coalesce(r.cvh_id,0) > 0 then true else false end); --is_optional
			end if;

			if coalesce(r.subject_id,0) > 0 then
				--select distinct first_value(id)over(partition by r.subject_id order by case when coalesce(apel_application_record_id,0)=0 then 1 else 0 end, grade_date desc nulls last, ) into p_id 
				--from student_higher_result where student_id=case when tg_op='DELETE' then old.student_id else NEW.student_id end and subject_id=r.subject_id;
				select distinct first_value(sr.id)over(partition by sr.subject_id order by case when coalesce(sr.apel_application_record_id,0)=0 then 1 else 0 end, sr.grade_date desc nulls last,ph.type_code asc,ph.inserted desc) into p_id 
				from student_higher_result sr
						 left join protocol_student ps on sr.protocol_student_id=ps.id
						 left join protocol_hdata ph on ps.protocol_id=ph.protocol_id
				where sr.student_id=case when tg_op='DELETE' then old.student_id else NEW.student_id end and sr.subject_id=r.subject_id;
				update student_higher_result set is_active=false where student_id=st_id and subject_id=r.subject_id and id!=p_id;
			elsif coalesce(r.cvh_id,0) > 0 then
				select distinct first_value(sr.id)over(partition by sr.subject_id order by case when coalesce(sr.apel_application_record_id,0)=0 then 1 else 0 end, sr.grade_date desc nulls last,ph.type_code asc,ph.inserted desc) into p_id 
				from student_higher_result sr
						 left join protocol_student ps on sr.protocol_student_id=ps.id
						 left join protocol_hdata ph on ps.protocol_id=ph.protocol_id
				where sr.student_id=case when tg_op='DELETE' then old.student_id else NEW.student_id end and sr.curriculum_version_hmodule_id=r.cvh_id and sr.is_module=true;
				update student_higher_result set is_active=false where student_id=st_id and curriculum_version_hmodule_id=r.cvh_id and is_module=true and id!=p_id;
			end if;

			/*--kustutame Ć¼leliigset eelmist rida
			delete from student_higher_result
WHERE id IN (SELECT id
              FROM (SELECT id,
                             ROW_NUMBER() OVER (partition BY column1, column2, column3 ORDER BY id) AS rnum
                     FROM tablename) t
              WHERE t.rnum > 1);*/
			x:=upd_student_curriculum_completion(new.student_id);
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
			-- kustutame Ć¼leliigsed vanad read
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
$function$
;

CREATE OR REPLACE FUNCTION public.upd_student_curriculum_completion(p_id bigint)
 RETURNS integer
 LANGUAGE plpgsql
AS $function$
declare 
  pb_exist boolean:=false;
	p_curr_modules bigint array;
  p_is_grade_modules boolean array;
	p_is_positive_grade_modules boolean array;
	p_curr_modules_ok boolean array;
	p_curr_module_types bigint array;
  p_curr_modules_credits numeric array;
	p_curr_modules_opt_credits numeric array;
	p_curr_modules2 bigint array;
  p_study_modules bigint array;
  p_vstudy_modules bigint array;
  p_optional numeric:=0;
	pb_modules boolean:=false; --kas on olemas jõuga moodulite täitmist
	r record;
	i int:=0;
  ii int:=0;
  p_total int:=0;
	p_opt_credits numeric:=0;
	p_avg_credits numeric:=0;
	p_avg_total_credits numeric:=0;
	p_total_credits numeric:=0;
	p_abs_credits numeric:=0;
	p_fabs_credits numeric:=0;
	p_curriculum_credits numeric:=0;
	a_count int:=0;
	pb_is_hgrade boolean:=false;

  p_vcurr_modules bigint array;
	p_vcurr_modules2 bigint array;

	p_fcurr_modules bigint array;
	
	mod_id bigint;

	is_higher_curriculum boolean:=true;
	
	pb_modules_ok boolean:=true;
	p_modules_count integer:=0; --mitu moodulit on ette nähtud hindamiseks
BEGIN
	for r in (select distinct cvo.id,cm.id as m_id, cm.credits, cc.optional_study_credits, cm.module_code, cc.is_higher, cc.credits as curriculum_credits
					  from curriculum_version cv
								 join curriculum_version_omodule cvo on cv.id=cvo.curriculum_version_id
								 join curriculum_module cm on cvo.curriculum_module_id=cm.id and cv.curriculum_id=cm.curriculum_id and coalesce(cm.is_additional,false)=false and cm.module_code!='KUTSEMOODUL_V' and coalesce(cm.is_additional,false)=false
								 join curriculum cc on cv.curriculum_id=cc.id
								 join student ss on cv.id=ss.curriculum_version_id
								 left join student_group sg on ss.student_group_id=sg.id
					 where ss.id=p_id and (sg.id is null or 
																 coalesce(sg.speciality_code,'x')='x' or 
																 coalesce(sg.speciality_code,'x')!='x' and exists(
																					select 1 
																					from curriculum_module_occupation cmo 
																							 left join classifier_connect ccc on cmo.occupation_code=ccc.connect_classifier_code
																					where cmo.curriculum_module_id=cm.id and (cmo.occupation_code=sg.speciality_code or ccc.classifier_code=sg.speciality_code))))
	LOOP
		i:=i+1;
		p_curr_modules[i]:=r.id;
		p_vcurr_modules[i]:=r.m_id;
		p_curr_modules2[i]:=r.id;
		p_vcurr_modules2[i]:=r.m_id;
		
		p_curr_modules_credits[i]:=r.credits;
		p_optional:=coalesce(r.optional_study_credits,0);
		p_curriculum_credits:=coalesce(r.curriculum_credits,0);
		if r.module_code='KUTSEMOODUL_L' then
			p_fcurr_modules[i]:=r.id;
		ELSE
			p_fcurr_modules[i]:=0;
		end if;
		is_higher_curriculum:=r.is_higher;
	end loop;

	if is_higher_curriculum then
		i:=0;
		--Kõrgõppurid
		for r in (select distinct cm.id as m_id, cm.total_credits,cm.optional_study_credits,cm.compulsory_study_credits, cm.type_code, cc.is_higher, cc.optional_study_credits as total_optional_study_credits,
										 sch.curriculum_version_hmodule_id as ok_id, cc.credits as curriculum_credits, cm.is_grade, shh.is_hmodules
					  from curriculum_version cv
								 join curriculum_version_hmodule cm on cv.id=cm.curriculum_version_id
								 --join curriculum_module cm on cvo.curriculum_module_id=cm.id and cv.curriculum_id=cm.curriculum_id and coalesce(cm.is_additional,false)=false and cm.module_code!='KUTSEMOODUL_V' and coalesce(cm.is_additional,false)=false
								 join curriculum cc on cv.curriculum_id=cc.id
								 join student ss on cv.id=ss.curriculum_version_id
								 join school shh on ss.school_id=shh.id 
								--jõuga täidetud moodule
								 left join student_curriculum_completion_hmodule sch on ss.id=sch.student_id and cm.id=sch.curriculum_version_hmodule_id
					 where ss.id=p_id and (coalesce(ss.curriculum_speciality_id,0)=0 or 
															coalesce(ss.curriculum_speciality_id,0) > 0 and exists(
																	select 1 
																	from curriculum_version_hmodule_speciality hs
									 										 join curriculum_version_speciality cvs on hs.curriculum_version_speciality_id=cvs.id
																	where hs.curriculum_version_hmodule_id=cm.id and
																				cvs.curriculum_speciality_id=ss.curriculum_speciality_id
																				)))
		LOOP
			i:=i+1;
			p_curr_modules[i]:=r.m_id;
			pb_is_hgrade:=coalesce(r.is_hmodules,false);
			p_is_grade_modules[i]:=coalesce(r.is_grade,false);
			if p_is_grade_modules[i]=true then
				p_modules_count:=p_modules_count+1;
			end if;
			p_curriculum_credits:=coalesce(r.curriculum_credits,0);
			p_is_positive_grade_modules[i]:=false;
			p_curr_modules_ok[i]:=case when r.ok_id is not null and r.m_id=r.ok_id then true else false end;
			if p_curr_modules_ok[i]=true then
				pb_modules:=true; --nende puhul EAP võlg vaadetakse pisut teistmoodi
			end if;
			p_curr_modules2[i]:=r.m_id;
			-- mooduli kohustuslikud EAP
			p_curr_modules_credits[i]:=coalesce(r.compulsory_study_credits,0);
			-- mooduli valik EAP
			p_curr_modules_opt_credits[i]:=coalesce(r.optional_study_credits,0);
			if r.type_code in ('KORGMOODUL_V') then
				p_optional:=coalesce(r.optional_study_credits,0)+p_optional;
				p_vcurr_modules[i]:=r.m_id;
			else
				p_vcurr_modules[i]:=0;
			end if;
			if r.type_code in ('KORGMOODUL_F','KORGMOODUL_L') then
				p_fcurr_modules[i]:=r.m_id;
			ELSE
				p_fcurr_modules[i]:=0;
			end if;
		end loop;
		--raise notice '%', array_length(p_curr_modules,1);
		--Õppuri positiived tulemused
		for r in (select coalesce(svm.curriculum_version_hmodule_id,sv.curriculum_version_hmodule_id) as curriculum_version_hmodule_id,
										 coalesce(svm.is_optional,sv.is_optional) is_optional,
										sv.grade_code, sv.credits,sv.subject_id, sv.grade_mark, sv.is_module, sv.grade_date
							from student_higher_result sv
									 left join student_higher_result_module svm on sv.id=svm.student_higher_result_id
							where sv.student_id=p_id and sv.is_module=false and sv.is_active=true and sv.grade_code in ('KORGHINDAMINE_1','KORGHINDAMINE_2','KORGHINDAMINE_3','KORGHINDAMINE_4','KORGHINDAMINE_5','KORGHINDAMINE_A') /*and sv.arr_modules is null*/
							union
							select sv.curriculum_version_hmodule_id as curriculum_version_hmodule_id,
										 false is_optional,
										sv.grade_code, sv.credits,sv.subject_id, sv.grade_mark, sv.is_module, sv.grade_date
							from student_higher_result sv
							where sv.student_id=p_id and sv.is_module=true and sv.is_active=true and sv.grade_code in ('KORGHINDAMINE_1','KORGHINDAMINE_2','KORGHINDAMINE_3','KORGHINDAMINE_4','KORGHINDAMINE_5','KORGHINDAMINE_A') 
							order by is_module desc, grade_date desc nulls last) 
		LOOP
			--korjame kõige pealt positiivsed moodulid kokku 
			--märgime positiivset tulemust
			if pb_is_hgrade and r.is_module and array_length(p_curr_modules,1) > 0 THEN
						for i in 1..array_length(p_curr_modules,1)
						loop
							if p_curr_modules[i]=r.curriculum_version_hmodule_id then
								p_modules_count:=p_modules_count-1;
								p_is_positive_grade_modules[i]:=true;
								p_total_credits:=p_total_credits+r.credits;
								if r.grade_code in ('KORGHINDAMINE_1','KORGHINDAMINE_2','KORGHINDAMINE_3','KORGHINDAMINE_4','KORGHINDAMINE_5') THEN
									p_avg_total_credits:=p_avg_total_credits+r.credits;
									p_avg_credits:=p_avg_credits+r.credits*(r.grade_mark::int);
								end if;
							end if;
						end loop;
			end if;
			pb_exist:=false;
			if r.is_module=false then
				if r.subject_id is not null then
					if array_length(p_study_modules,1) > 0 then
						for ii in 1..array_length(p_study_modules,1)
						LOOP
							if p_study_modules[ii]=r.subject_id THEN
								pb_exist:=true;
								exit;
							end if;
						end loop;
					end if;
					if not pb_exist THEN
						p_study_modules[case when p_study_modules is null then 0 else array_length(p_study_modules,1) end+1]:=r.subject_id;
					end if;
				ELSE
					pb_exist:=false;
				end if;
			end if;
			if not pb_exist THEN
				--ja siin on nüüd uus asi - kui aine kuulub minu positiivselt hinnatud moodulisse, siis me ei arvesta ainet EAP ja KKH jne arvutamisel
				if coalesce(r.curriculum_version_hmodule_id,0) > 0 and array_length(p_curr_modules,1) > 0 THEN
						for i in 1..array_length(p_curr_modules,1)
						loop
							if p_curr_modules[i]=r.curriculum_version_hmodule_id and p_is_positive_grade_modules[i]=true then 
								--jätame vahele
								p_curr_modules_opt_credits[i]:=0;
								p_curr_modules_credits[i]:=0;
								pb_exist:=true;
								exit;
							end if;
						end loop;
				end if;

				if not pb_exist THEN --aine ei kuulu positiivselt hinnatud moodulisse

					p_total_credits:=p_total_credits+r.credits;
					if r.grade_code in ('KORGHINDAMINE_1','KORGHINDAMINE_2','KORGHINDAMINE_3','KORGHINDAMINE_4','KORGHINDAMINE_5') THEN
						p_avg_total_credits:=p_avg_total_credits+r.credits;
						p_avg_credits:=p_avg_credits+r.credits*(r.grade_mark::int);
					end if;
					if array_length(p_curr_modules,1) > 0 and r.curriculum_version_hmodule_id is not null then
						pb_exist:=false;
						for ii in 1..array_length(p_curr_modules,1)
						loop
							if p_curr_modules[ii]=coalesce(r.curriculum_version_hmodule_id,0) then
								if coalesce(r.is_optional,false)=true then
									p_curr_modules_opt_credits[ii]:=p_curr_modules_opt_credits[ii]-r.credits;
									if p_curr_modules[ii]=p_vcurr_modules[ii] then
										--raise notice 'SIIN: ';
										p_opt_credits:=p_opt_credits+r.credits;
									end if;
									--raise notice '%', p_curr_modules_opt_credits[ii];
								else
									p_curr_modules_credits[ii]:=p_curr_modules_credits[ii]-r.credits;
									--raise notice '-%', p_curr_modules_credits[ii];
								end if;
								pb_exist:=true;
							end if;
						end loop;
						--valikõpingud
						if not pb_exist then
							p_opt_credits:=p_opt_credits+r.credits;
						end if;
					else
						if not pb_exist then
							p_opt_credits:=p_opt_credits+r.credits;
						end if;
					end if; 
				end if;
			end if;
		end loop;

		if array_length(p_curr_modules,1) > 0 THEN
			for ii in 1..array_length(p_curr_modules,1)
			LOOP
				--vabaõppe ei pea siin kontrollima vist
--
				if p_vcurr_modules[ii] = 0 then
					if p_curr_modules_credits[ii] > 0 then
						-- siin kontrollime, kas moodul on jõuga täidetud; kui jah, siis nn võlga ei tohi tekkida
						if p_curr_modules_ok[ii]=false then
							p_abs_credits:=p_abs_credits+p_curr_modules_credits[ii];
						end if;
						-- lõputöö/eksam ei lähe arvesse, nende jaoks võla arvutamine jääb endiseks
						if p_curr_modules[ii]=p_fcurr_modules[ii] then
							p_fabs_credits:=p_fabs_credits+p_curr_modules_credits[ii];
						end if;
					else
						p_opt_credits:=p_opt_credits+abs(p_curr_modules_credits[ii]);
					end if;
					if p_curr_modules_opt_credits[ii] > 0 then
						--raise notice 'o %', p_curr_modules[ii];
						-- siin kontrollime, kas moodul on jõuga täidetud; kui jah, siis nn võlga ei tohi tekkida
						if p_curr_modules_ok[ii]=false then
							p_abs_credits:=p_abs_credits+p_curr_modules_opt_credits[ii];
						end if;
					else
						p_opt_credits:=p_opt_credits+abs(p_curr_modules_opt_credits[ii]);
					end if;
					--raise notice '3. %: % % ', p_curr_modules[ii],p_abs_credits,p_opt_credits;
				end if;
			end loop;
		end if;

	--	raise NOTICE 'Fopt: %/%', p_fabs_credits, p_abs_credits;
	--	raise NOTICE 'opt: %/%', p_opt_credits, p_optional;

		--raise notice '% %', p_optional, p_opt_credits;

		if p_opt_credits > p_optional THEN
			p_opt_credits:=0;
		ELSE
			p_opt_credits:=p_optional-p_opt_credits;
		end if;

		--raise NOTICE 'opt: %/%', p_opt_credits, p_optional;

		--kui kõik modulid on jõuga märgitud täidetuks, siis ikkagi kogutud EAP peab olema vähemalt sama suur kui õppekava EAP
		--raise notice '% - % - %', p_curriculum_credits, p_total_credits, pb_modules;
		if p_curriculum_credits > p_total_credits and pb_modules then
			p_abs_credits:=p_curriculum_credits-p_total_credits;
			--raise notice 'var 1';
		else
			p_abs_credits:=coalesce(p_abs_credits,0)+p_opt_credits;
			--raise notice 'var 2';
		end if;
		p_fabs_credits:=p_abs_credits-p_fabs_credits;
		if p_modules_count > 0 then
			pb_modules_ok:=false;
		end if;
		
	else
		--Õppuri positiivsed tulemused
		for r in (select case when sv.arr_modules is null then sv.curriculum_version_omodule_id else null end curriculum_version_omodule_id, 
										 case when sv.arr_modules is null then cmm.curriculum_module_id else null end curriculum_module_id,
										sv.grade, sv.credits, sv.arr_modules
							from student_vocational_result sv
									 left join curriculum_version_omodule cmm on sv.curriculum_version_omodule_id=cmm.id
									 --left join student_vocational_result_omodule svm on sv.id=svm.student_vocational_result_id
							where sv.student_id=p_id and grade in ('A','3','4','5') /*and sv.arr_modules is null*/
							order by sv.grade_date desc) 
		LOOP
			pb_exist:=false;
			if r.curriculum_version_omodule_id is not null then
				if array_length(p_study_modules,1) > 0 then
					for ii in 1..array_length(p_study_modules,1)
					LOOP
						if p_study_modules[ii]=r.curriculum_version_omodule_id or p_vstudy_modules[ii]=r.curriculum_module_id THEN
							pb_exist:=true;
							exit;
						end if;
					end loop;
				end if;
				if not pb_exist THEN
					p_study_modules[case when p_study_modules is null then 0 else array_length(p_study_modules,1) end+1]:=r.curriculum_version_omodule_id;
					p_vstudy_modules[case when p_vstudy_modules is null then 0 else array_length(p_vstudy_modules,1) end+1]:=r.curriculum_module_id;
				end if;
			ELSE
				pb_exist:=false;
			end if;
			
			if not pb_exist THEN
				--p_study_modules[case when p_study_modules is null then 0 else array_length(p_study_modules,1) end+1]:=r.curriculum_version_omodule_id;
				p_total_credits:=p_total_credits+r.credits;
				if r.grade in ('3','4','5') THEN
					p_avg_total_credits:=p_avg_total_credits+r.credits;
					p_avg_credits:=p_avg_credits+r.credits*(r.grade::int);
				end if;
				if array_length(p_curr_modules,1) > 0 then
					pb_exist:=false;
					--kahte tüüpi moodulid
					if r.curriculum_version_omodule_id is not null then
						for ii in 1..array_length(p_curr_modules,1)
						LOOP
							if p_curr_modules[ii]=r.curriculum_version_omodule_id or p_vcurr_modules[ii]=r.curriculum_module_id THEN
								p_curr_modules2[ii]=0;
								p_vcurr_modules2[ii]=0;
								p_total:=p_total+1;
								pb_exist:=true;
								exit;
							end if;
						end loop;
					elsif array_length(r.arr_modules,1) > 0 THEN
						for i in 1..array_length(r.arr_modules,1)
						LOOP
								select curriculum_module_id into mod_id from curriculum_version_omodule where id=r.arr_modules[i];
								for ii in 1..array_length(p_curr_modules,1)
								LOOP
									if p_curr_modules[ii]=r.arr_modules[i] or p_vcurr_modules[ii]=mod_id THEN
										p_curr_modules2[ii]=0;
										p_vcurr_modules2[ii]=0;
										p_total:=p_total+1;
										pb_exist:=true;
										exit;
									end if;
								end loop;
						end loop;
					end if;
					--valikõpingud
					if not pb_exist then
						p_opt_credits:=p_opt_credits+r.credits;
					end if;
				end if; 
			end if;
		end loop;

		if array_length(p_curr_modules,1) > 0 THEN
			for ii in 1..array_length(p_curr_modules,1)
			LOOP
				if p_curr_modules2[ii] > 0 then
					p_abs_credits:=p_abs_credits+p_curr_modules_credits[ii];
					if p_curr_modules[ii]=p_fcurr_modules[ii] then
						p_fabs_credits:=p_fabs_credits+p_curr_modules_credits[ii];
					end if;
				end if;
			end loop;
		end if;

		--raise NOTICE 'Fopt: %/%', p_fabs_credits, p_abs_credits;
		--raise NOTICE 'opt: %/%', p_opt_credits, p_optional;

		--kokku võlg
		if p_opt_credits > p_optional THEN
			p_opt_credits:=0;
		ELSE
			p_opt_credits:=p_optional-p_opt_credits;
		end if;

		--raise NOTICE 'opt: %/%', p_opt_credits, p_optional;


		p_abs_credits:=coalesce(p_abs_credits,0)+p_opt_credits;
		p_fabs_credits:=p_abs_credits-p_fabs_credits;

		--raise NOTICE 'opt: %/%', p_fabs_credits, p_abs_credits;
		--raise notice 'Tere %, %, %, %', p_abs_credits, p_avg_credits, p_avg_total_credits,to_char(current_timestamp(3),'mi:ss.ms');
	end if;

	update student_curriculum_completion 
	set study_backlog=-p_abs_credits, 
			study_backlog_without_graduate=-p_fabs_credits,
			average_mark=case when p_avg_total_credits > 0 then floor(p_avg_credits*1000/p_avg_total_credits)/1000 else 0 end, 
			credits=p_total_credits, 
			changed=current_timestamp(3),
			study_optional_backlog=-p_opt_credits,
			is_modules_ok=pb_modules_ok
	where student_id=p_id;
	
	GET DIAGNOSTICS a_count = ROW_COUNT;
	if a_count=0 THEN
			insert into student_curriculum_completion(student_id,study_backlog,study_backlog_without_graduate,average_mark,credits,inserted,changed,study_optional_backlog,is_modules_ok)
			values(p_id,-p_abs_credits,-p_fabs_credits,case when p_avg_total_credits > 0 then floor(p_avg_credits*100/p_avg_total_credits)/100 else 0 end,p_total_credits,current_timestamp(3),current_timestamp(3),-p_opt_credits,pb_modules_ok);
	end if;

	return 0;
exception when others THEN
	--raise notice '%, %',p_id,sqlerrm;
	return 0;
end;
$function$
;

update apel_application set status_code='OPINGUKAVA_STAATUS_S' where status_code='VOTA_STAATUS_C' and student_id in (select id from student where status_code in ('OPPURSTAATUS_O','OPPURSTAATUS_A'));
update apel_application set status_code='VOTA_STAATUS_C' where status_code='OPINGUKAVA_STAATUS_S';

create trigger directive_student_duplicate_form_audit after insert or delete or update on directive_student_duplicate_form for each row execute procedure hois_audit();
create trigger student_curriculum_completion_hmodule_audit after insert or delete or update on student_curriculum_completion_hmodule for each row execute procedure hois_audit();

