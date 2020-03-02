\c hois

insert into classifier (code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version,inserted_by) values('KASKKIRI_MUU','MUU','Muu','KASKKIRI',current_timestamp(3),true,true,true,0,'HÕIS');

CREATE TABLE "subject_study_period_subgroup"
(
	"id" bigserial NOT NULL,
	"subject_study_period_id" bigint NOT NULL,    -- viide AÕ paarile, kuhu osarühm kuulub
	"code" varchar(100)	 NOT NULL,    -- osarühma nimi
	"subject_study_period_teacher_id" bigint NULL,    -- viide AÕ paariga seotud õppejõule
	"places" smallint NULL,    -- kohtade arv osarühmas
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

alter TABLE "declaration_subject" add "subject_study_period_subgroup_id" bigint NULL ;

CREATE TABLE "timetable_event_subgroup"
(
	"id" bigserial NOT NULL,
	"subject_study_period_subgroup_id" bigint NOT NULL,    -- viide osarühmale
	"timetable_event_time_id" bigint NOT NULL,    -- viide toimumisajale
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "subject_study_period_subgroup"	IS 'osarühm';
COMMENT ON COLUMN "subject_study_period_subgroup"."subject_study_period_id"	IS 'viide AÕ paarile, kuhu osarühm kuulub';
COMMENT ON COLUMN "subject_study_period_subgroup"."code"	IS 'osarühma nimi';
COMMENT ON COLUMN "subject_study_period_subgroup"."subject_study_period_teacher_id"	IS 'viide AÕ paariga seotud õppejõule';
COMMENT ON COLUMN "subject_study_period_subgroup"."places"	IS 'kohtade arv osarühmas';
COMMENT ON COLUMN "declaration_subject"."subject_study_period_subgroup_id"	IS 'viide osarühmale, ei ole kohustuslik';
COMMENT ON TABLE "timetable_event_subgroup"	IS 'tunniplaan ja osarühmad';
COMMENT ON COLUMN "timetable_event_subgroup"."subject_study_period_subgroup_id"	IS 'viide osarühmale';
COMMENT ON COLUMN "timetable_event_subgroup"."timetable_event_time_id"	IS 'viide toimumisajale';


/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE "subject_study_period_subgroup" ADD CONSTRAINT "PK_subject_study_period_subgroup"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_subject_study_period_subgroup_subject_study_period" ON "subject_study_period_subgroup" ("subject_study_period_id" ASC);
CREATE INDEX "IXFK_subject_study_period_subgroup_subject_study_period_teacher" ON "subject_study_period_subgroup" ("subject_study_period_teacher_id" ASC);
CREATE INDEX "IXFK_declaration_subject_subject_study_period_subgroup" ON "declaration_subject" ("subject_study_period_subgroup_id" ASC);
ALTER TABLE "timetable_event_subgroup" ADD CONSTRAINT "PK_timetable_event_subgroup"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_timetable_event_subgroup_subject_study_period_subgroup" ON "timetable_event_subgroup" ("subject_study_period_subgroup_id" ASC);
CREATE INDEX "IXFK_timetable_event_subgroup_timetable_event_time" ON "timetable_event_subgroup" ("timetable_event_time_id" ASC);

/* Create Foreign Key Constraints */

ALTER TABLE "subject_study_period_subgroup" ADD CONSTRAINT "FK_subject_study_period_subgroup_subject_study_period"	FOREIGN KEY ("subject_study_period_id") REFERENCES "subject_study_period" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "subject_study_period_subgroup" ADD CONSTRAINT "FK_subject_study_period_subgroup_subject_study_period_teacher"	FOREIGN KEY ("subject_study_period_teacher_id") REFERENCES "subject_study_period_teacher" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "declaration_subject" ADD CONSTRAINT "FK_declaration_subject_subject_study_period_subgroup"	FOREIGN KEY ("subject_study_period_subgroup_id") REFERENCES "subject_study_period_subgroup" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "timetable_event_subgroup" ADD CONSTRAINT "FK_timetable_event_subgroup_subject_study_period_subgroup"	FOREIGN KEY ("subject_study_period_subgroup_id") REFERENCES "subject_study_period_subgroup" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "timetable_event_subgroup" ADD CONSTRAINT "FK_timetable_event_subgroup_timetable_event_time"	FOREIGN KEY ("timetable_event_time_id") REFERENCES "timetable_event_time" ("id") ON DELETE No Action ON UPDATE No Action
;

alter table "person" add column "unique_code" varchar(20)	 NULL    -- unikaalne kood, mida kasutatakse sisselogimiseks
;

COMMENT ON COLUMN "public"."person"."unique_code"	IS 'unikaalne kood, mida kasutatakse sisselogimiseks';

ALTER TABLE "public"."person" ADD CONSTRAINT "UQ_unique_code" UNIQUE ("unique_code");


------------------------------
alter table directive_student add column ehis_id varchar(50);
comment on column directive_student.ehis_id is  'EHISe kirje ID';

CREATE OR REPLACE FUNCTION public.upd_student_curriculum_completion(p_id bigint)
 RETURNS integer
 LANGUAGE plpgsql
AS $function$
declare 
  pb_exist boolean:=false;
	p_curr_modules bigint array;
	p_curr_module_types bigint array;
  p_curr_modules_credits numeric array;
	p_curr_modules_opt_credits numeric array;
	p_curr_modules2 bigint array;
  p_study_modules bigint array;
  p_vstudy_modules bigint array;
  p_optional numeric:=0;
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
	a_count int:=0;

  p_vcurr_modules bigint array;
	p_vcurr_modules2 bigint array;

	p_fcurr_modules bigint array;
	
	mod_id bigint;

	is_higher_curriculum boolean:=true;
BEGIN

	--raise notice 'Tere %',to_char(current_timestamp(3),'mi:ss.ms');
	for r in (select distinct cvo.id,cm.id as m_id, cm.credits, cc.optional_study_credits, cm.module_code, cc.is_higher
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
		if r.module_code='KUTSEMOODUL_L' then
			p_fcurr_modules[i]:=r.id;
		ELSE
			p_fcurr_modules[i]:=0;
		end if;
		is_higher_curriculum:=r.is_higher;
	end loop;

	if is_higher_curriculum then
		i:=0;
		--KĆµrgĆµppurid
		for r in (select distinct cm.id as m_id, cm.total_credits,cm.optional_study_credits,cm.compulsory_study_credits, cm.type_code, cc.is_higher, cc.optional_study_credits as total_optional_study_credits
					  from curriculum_version cv
								 join curriculum_version_hmodule cm on cv.id=cm.curriculum_version_id
								 --join curriculum_module cm on cvo.curriculum_module_id=cm.id and cv.curriculum_id=cm.curriculum_id and coalesce(cm.is_additional,false)=false and cm.module_code!='KUTSEMOODUL_V' and coalesce(cm.is_additional,false)=false
								 join curriculum cc on cv.curriculum_id=cc.id
								 join student ss on cv.id=ss.curriculum_version_id
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
			--p_curr_module_types[i]:=r.type_code;
			--p_vcurr_modules[i]:=r.m_id;
			p_curr_modules2[i]:=r.m_id;
			--p_vcurr_modules2[i]:=r.m_id;
			p_curr_modules_credits[i]:=coalesce(r.compulsory_study_credits,0);
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
		--Ć•ppuri positiived tulemused
		for r in (select coalesce(svm.curriculum_version_hmodule_id,sv.curriculum_version_hmodule_id) as curriculum_version_hmodule_id,
										 coalesce(svm.is_optional,sv.is_optional) is_optional,
										sv.grade_code, sv.credits,sv.subject_id, sv.grade_mark
							from student_higher_result sv
									 left join student_higher_result_module svm on sv.id=svm.student_higher_result_id
							where sv.student_id=p_id and sv.is_active=true and sv.grade_code in ('KORGHINDAMINE_1','KORGHINDAMINE_2','KORGHINDAMINE_3','KORGHINDAMINE_4','KORGHINDAMINE_5','KORGHINDAMINE_A') /*and sv.arr_modules is null*/
							order by sv.grade_date desc nulls last) 
		LOOP
			pb_exist:=false;
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
			if not pb_exist THEN
				--p_study_modules[case when p_study_modules is null then 0 else array_length(p_study_modules,1) end+1]:=r.curriculum_version_omodule_id;
				p_total_credits:=p_total_credits+r.credits;
				if r.grade_code in ('KORGHINDAMINE_1','KORGHINDAMINE_2','KORGHINDAMINE_3','KORGHINDAMINE_4','KORGHINDAMINE_5') THEN
 					p_avg_total_credits:=p_avg_total_credits+r.credits;
 					p_avg_credits:=p_avg_credits+r.credits*(r.grade_mark::int);
 				end if;
 				if array_length(p_curr_modules,1) > 0 and r.curriculum_version_hmodule_id is not null then
 					pb_exist:=false;
					for ii in 1..array_length(p_curr_modules,1)
					loop
						if p_curr_modules[ii]=r.curriculum_version_hmodule_id then
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
					--valikĆµpingud
					if not pb_exist then
 						p_opt_credits:=p_opt_credits+r.credits;
 					end if;
 				end if; 
			end if;
		end loop;

		if array_length(p_curr_modules,1) > 0 THEN
			for ii in 1..array_length(p_curr_modules,1)
			LOOP
				if p_curr_modules_credits[ii] > 0 then
					--raise notice '%', p_curr_modules[ii];
					p_abs_credits:=p_abs_credits+p_curr_modules_credits[ii];
					if p_curr_modules[ii]=p_fcurr_modules[ii] then
						p_fabs_credits:=p_fabs_credits+p_curr_modules_credits[ii];
					end if;
				else
					p_opt_credits:=p_opt_credits+abs(p_curr_modules_credits[ii]);
				end if;
				if p_curr_modules_opt_credits[ii] > 0 then
					--raise notice 'o %', p_curr_modules[ii];
					p_abs_credits:=p_abs_credits+p_curr_modules_opt_credits[ii];
				else
					p_opt_credits:=p_opt_credits+abs(p_curr_modules_opt_credits[ii]);
				end if;
			end loop;
		end if;

	--	raise NOTICE 'Fopt: %/%', p_fabs_credits, p_abs_credits;
	--	raise NOTICE 'opt: %/%', p_opt_credits, p_optional;

		if p_opt_credits > p_optional THEN
			p_opt_credits:=0;
		ELSE
			p_opt_credits:=p_optional-p_opt_credits;
		end if;

		--raise NOTICE 'opt: %/%', p_opt_credits, p_optional;


		p_abs_credits:=coalesce(p_abs_credits,0)+p_opt_credits;
		p_fabs_credits:=p_abs_credits-p_fabs_credits;
		
	else
		--Ä†Āµppuri positiivsed tulemused
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
					--kahte tÄ†Ā¼Ä†Ā¼pi moodulid
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
					--valikÄ†Āµpingud
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

		--kokku vÄ†Āµlg
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
	set study_backlog=-p_abs_credits, study_backlog_without_graduate=-p_fabs_credits,
				average_mark=case when p_avg_total_credits > 0 then floor(p_avg_credits*100/p_avg_total_credits)/100 else 0 end, credits=p_total_credits, changed=current_timestamp(3)
	where student_id=p_id;
	
	GET DIAGNOSTICS a_count = ROW_COUNT;
	if a_count=0 THEN
			insert into student_curriculum_completion(student_id,study_backlog,study_backlog_without_graduate,average_mark,credits,inserted,changed)
			values(p_id,-p_abs_credits,-p_fabs_credits,case when p_avg_total_credits > 0 then floor(p_avg_credits*100/p_avg_total_credits)/100 else 0 end,p_total_credits,current_timestamp(3),current_timestamp(3));
	end if;

	return 0;
exception when others THEN
	--raise notice '%, %',p_id,p_abs_credits;
	return 0;
end;
$function$
;



/** person unique code with wished length */

CREATE OR REPLACE FUNCTION public.generate_person_unique_code(length integer)
	RETURNS text
	LANGUAGE plpgsql
AS $function$
declare
  chars text[] := '{0,1,2,3,4,5,6,7,8,9,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z}';
  result text := '';
  i integer := 0;
begin
  if length < 0 then
    raise exception 'Given length cannot be less than 0';
  end if;
  loop
	  result := '';
	  for i in 1..length loop
		result := result || chars[ceil(61 * random())];
	  end loop;
	  EXIT WHEN NOT EXISTS(select 1 from person p where (p.unique_code = result or p.idcode = result));
  end loop;
  return result;
end;
$function$;

/** trigger function */

CREATE OR REPLACE FUNCTION public.upd_person_unique_code()
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
	if (tg_op = 'UPDATE' or tg_op = 'INSERT') and new.idcode is null and new.unique_code is null THEN
		update person set unique_code = generate_person_unique_code(11), changed=current_timestamp(3) where id=NEW.id;
	end if;
	return null;
end;
$function$;

/** create trigger */

CREATE TRIGGER person_unique_code_trg AFTER INSERT OR UPDATE ON person
    FOR EACH ROW EXECUTE PROCEDURE upd_person_unique_code();

/** query to set school person unique codes */

update person 
set unique_code = generate_person_unique_code(11) 
from student 
where student.person_id = person.id and student.school_id = 989 and person.unique_code is null and person.idcode is null;

/** query to set all person unique codes */

update person 
set unique_code = generate_person_unique_code(11)
where person.unique_code is null and person.idcode is null;

update student set type_Code='OPPUR_T' where type_code='OPPUR_O';
