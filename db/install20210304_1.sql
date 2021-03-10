\c hois

CREATE OR REPLACE PROCEDURE public.get_higher_student_cc(p_id bigint, is_last_period boolean, INOUT p_abs_credits numeric, INOUT p_fabs_credits numeric, INOUT p_avg_credits numeric, INOUT p_avg_total_credits numeric, INOUT p_total_credits numeric, INOUT p_opt_credits numeric, INOUT pb_modules_ok boolean)
 LANGUAGE plpgsql
AS $procedure$
declare 
  pb_exist boolean:=false;
	pb_exist2 boolean:=false;
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
  iii int:=0;
  p_total int:=0;
	--p_opt_credits numeric:=0;
	--p_avg_credits numeric:=0;
	p_x_credits numeric:=0;
	--p_avg_total_credits numeric:=0;
	--p_total_credits numeric:=0;
	--p_abs_credits numeric:=0;
	--p_fabs_credits numeric:=0;
	p_curriculum_credits numeric:=0;
	a_count int:=0;
	pb_is_hgrade boolean:=false;

  p_vcurr_modules bigint array;
	p_vcurr_modules2 bigint array;

	p_fcurr_modules bigint array;
	p_fcurr_modules_ok bigint array;
	
	mod_id bigint;

	is_higher_curriculum boolean:=true;
	
	--pb_modules_ok boolean:=true;
	p_modules_count integer:=0; --mitu moodulit on ette nähtud hindamiseks
	p_id2 bigint;
	p_final_ok boolean:=false;

	p_last_date date:='2100-12-31';
	p_last_id bigint:=0;
BEGIN

		if is_last_period then
			p_last_id:=get_student_last_study_period(p_id);
			if coalesce(p_last_id,0) > 0 then
				select end_date into p_last_date from study_period where id=p_last_id;
			end if;
		end if;
	
		i:=0;
		--moodulite hindamise puhul mitte õppekava moodulid ei tohi arvesse minna
		for r in (select sr.id, cm.id as id2
								from student_higher_result sr
										 join student s on sr.student_id=s.id
										 join curriculum_version cv on s.curriculum_version_id=cv.id
										 left join curriculum_version_hmodule cm on cv.id=cm.curriculum_version_id and (coalesce(s.curriculum_speciality_id,0)=0 or 
																							coalesce(s.curriculum_speciality_id,0) > 0 and exists(
																									select 1 
																									from curriculum_version_hmodule_speciality hs
																											 join curriculum_version_speciality cvs on hs.curriculum_version_speciality_id=cvs.id
																									where hs.curriculum_version_hmodule_id=cm.id and
																												cvs.curriculum_speciality_id=s.curriculum_speciality_id
																												)) and sr.curriculum_version_hmodule_id=cm.id
								where sr.student_id=p_id and sr.is_module=true )
		loop
			if r.id2 is null then
				update student_higher_result sr set is_active=false where sr.id=r.id;
			else
				select distinct first_value(sr.id)over(partition by sr.curriculum_version_hmodule_id order by case when coalesce(sr.apel_application_record_id,0)=0 then 1 else 0 end, sr.grade_date desc nulls last,ph.type_code asc,ph.inserted desc) into p_id2 
				from student_higher_result sr
						 left join protocol_student ps on sr.protocol_student_id=ps.id
						 left join protocol_hdata ph on ps.protocol_id=ph.protocol_id
				where sr.student_id=p_id and sr.curriculum_version_hmodule_id=r.id2 and sr.is_module=true;
				update student_higher_result set is_active=false where student_id=p_id and curriculum_version_hmodule_id=r.id2 and is_module=true and id!=p_id2;
				update student_higher_result set is_active=true where student_id=p_id and curriculum_version_hmodule_id=r.id2 and is_module=true and id=p_id2;
			end if;
		end loop;
		
		
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
				p_fcurr_modules_ok[i]:=0;
			ELSE
				p_fcurr_modules[i]:=0;
			end if;
		end loop;
		--raise notice '%', array_length(p_curr_modules,1);
		--Õppuri positiived tulemused
		for r in (/*select coalesce(svm.curriculum_version_hmodule_id,sv.curriculum_version_hmodule_id) as curriculum_version_hmodule_id,
										 coalesce(svm.is_optional,sv.is_optional) is_optional,
										sv.grade_code, sv.credits,sv.subject_id, sv.grade_mark, sv.is_module, sv.grade_date
							from student_higher_result sv
									 left join student_higher_result_module svm on sv.id=svm.student_higher_result_id
							where sv.student_id=p_id and sv.is_module=false and sv.is_active=true and sv.grade_code in ('KORGHINDAMINE_1','KORGHINDAMINE_2','KORGHINDAMINE_3','KORGHINDAMINE_4','KORGHINDAMINE_5','KORGHINDAMINE_A') 
              */
							select coalesce(svm.curriculum_version_hmodule_id,sv.curriculum_version_hmodule_id) as curriculum_version_hmodule_id,
										 coalesce(svm.is_optional,sv.is_optional) is_optional,
										sv.grade_code, sv.credits,sv.subject_id, sv.grade_mark, sv.is_module, sv.grade_date, 0 as asend_kokku, 0 as asend_koik_kokku, 0 as ylek_kokku, 0 as vaba_kokku, 0 as vota
							from student_higher_result sv
									 left join student_higher_result_module svm on sv.id=svm.student_higher_result_id
									 left join apel_application_record ar on sv.apel_application_record_id=ar.id
									 left join apel_application ap on ar.apel_application_id=ap.id
							where sv.student_id=p_id and coalesce(sv.grade_date,'2100-12-31') <=p_last_date   and sv.is_module=false and sv.is_active=true 
										and sv.grade_code in ('KORGHINDAMINE_1','KORGHINDAMINE_2','KORGHINDAMINE_3','KORGHINDAMINE_4','KORGHINDAMINE_5','KORGHINDAMINE_A') 
										and (ar.id is null or coalesce(ar.is_formal_learning,false)=false or coalesce(ap.is_new,false)=false)
							union all
							select x.chm_id,x.is_optional,sv.grade_code, sv.credits,coalesce(sv.subject_id,-sv.id), sv.grade_mark, sv.is_module, sv.grade_date,
							(select sum(sb.credits)
							 from student_higher_result_replaced_subject rs join subject sb on rs.subject_id=sb.id 
							 where rs.student_higher_result_id=x.id and rs.curriculum_version_hmodule_id=x.chm_id and rs.is_optional=x.is_optional) as asend_kokku,
(select sum(sb.credits)/count(distinct rs.student_higher_result_id)
							 from student_higher_result_replaced_subject rs join subject sb on rs.subject_id=sb.id 
							 	  join student_higher_result sr on rs.student_higher_result_id =sr.id 
							 where  sr.is_Active=true and sr.apel_application_record_id=x.apel_application_record_id) as asend_koik_kokku, 
							(select sum(sr.credits)
							 from student_higher_result sr
							where sr.is_Active=true and sr.apel_application_record_id=x.apel_application_record_id) as ylek_kokku, 
						(select sum(sb.credits)
						 from student_higher_result_replaced_subject rs join subject sb on rs.subject_id=sb.id 
						 where rs.student_higher_result_id=x.id and rs.curriculum_version_hmodule_id is not null) as vaba_kokku, 1 as vota
							from (
							select distinct sv.id,sv.apel_application_record_id,chm.id as chm_id,rs.is_optional--sv.credits,sb.id,sb.credits as sb_credits
							from student_higher_result sv
									 join apel_application_record ar on sv.apel_application_record_id=ar.id and coalesce(ar.is_formal_learning,false)=true
									 join apel_application ap on ar.apel_application_id=ap.id and coalesce(ap.is_new,false)=true
									 left join student_higher_result_replaced_subject rs on sv.id=rs.student_higher_result_id
									 --left join subject sb on rs.subject_id=sb.id
									 left join curriculum_version_hmodule chm on rs.curriculum_version_hmodule_id=chm.id
							where sv.student_id=p_id  and coalesce(sv.grade_date,'2100-12-31') <=p_last_date and sv.is_module=false and sv.is_active=true 
										and sv.grade_code in ('KORGHINDAMINE_1','KORGHINDAMINE_2','KORGHINDAMINE_3','KORGHINDAMINE_4','KORGHINDAMINE_5','KORGHINDAMINE_A')) x
								join student_higher_result sv on x.id=sv.id
							union all
							select sv.curriculum_version_hmodule_id as curriculum_version_hmodule_id,
										 false is_optional,
										sv.grade_code, sv.credits,sv.subject_id, sv.grade_mark, sv.is_module, sv.grade_date, 0 as asend_kokku, 0, 0 as ylek_kokku, 0 as vaba_kokku,  0 as vota
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
								p_x_credits:=p_x_credits+r.credits;
								if r.grade_code in ('KORGHINDAMINE_1','KORGHINDAMINE_2','KORGHINDAMINE_3','KORGHINDAMINE_4','KORGHINDAMINE_5') THEN
									p_avg_total_credits:=p_avg_total_credits+r.credits;
									p_avg_credits:=p_avg_credits+r.credits*(r.grade_mark::int);
								end if;
							end if;
						end loop;
			end if;
			pb_exist:=false;
			pb_exist2:=false;
			if r.is_module=false then
				if r.subject_id is not null or r.vota=1 then
					if array_length(p_study_modules,1) > 0 then
						for ii in 1..array_length(p_study_modules,1)
						LOOP
							if p_study_modules[ii]=r.subject_id THEN
								pb_exist:=true;
								pb_exist2:=true;
								exit;
							end if;
						end loop;
					end if;
					if not pb_exist THEN
						p_study_modules[case when p_study_modules is null then 0 else array_length(p_study_modules,1) end+1]:= r.subject_id;
					end if;
				ELSE
					pb_exist:=false;
				end if;
				if r.vota=1 then
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
					
					if not pb_exist2 then
						p_total_credits:=p_total_credits+r.credits;
						if r.vota=1 and r.ylek_kokku > 0 and r.ylek_kokku > r.vaba_kokku then
							p_opt_credits:=p_opt_credits+(r.credits*(r.ylek_kokku-r.vaba_kokku))/r.ylek_kokku;
						end if;
						--p_x_credits:=p_x_credits+r.credits;
						--raise notice '1 % % ',p_total_credits,p_x_credits;
					elsif r.vota=1 and r.asend_kokku > 0 and r.ylek_kokku > 0  then
						--p_x_credits:=p_x_credits+((r.asend_kokku*r.credits)/r.ylek_kokku);
						--raise notice '2 % % ',p_total_credits,p_x_credits;
					end if;
					
					if r.grade_code in ('KORGHINDAMINE_1','KORGHINDAMINE_2','KORGHINDAMINE_3','KORGHINDAMINE_4','KORGHINDAMINE_5') and not pb_exist2 THEN
						p_avg_total_credits:=p_avg_total_credits+r.credits;
						p_avg_credits:=p_avg_credits+r.credits*(r.grade_mark::int);
					end if;
					if array_length(p_curr_modules,1) > 0 and r.curriculum_version_hmodule_id is not null then
						pb_exist:=false;
						for ii in 1..array_length(p_curr_modules,1)
						loop
							if p_curr_modules[ii]=coalesce(r.curriculum_version_hmodule_id,0) then
								if coalesce(r.is_optional,false)=true then
									if r.vota=1 and r.asend_kokku > 0 and r.ylek_kokku > 0 then
										--valem nüüd sõltub sellest kas asend_kokku > kui ylek_kokku või mitte
										if r.ylek_kokku >= r.asend_koik_kokku then
											p_curr_modules_opt_credits[ii]:=p_curr_modules_opt_credits[ii]-((r.asend_kokku*r.credits)/r.ylek_kokku);
										else
											p_curr_modules_opt_credits[ii]:=p_curr_modules_opt_credits[ii]-((r.asend_kokku*r.credits)/r.asend_koik_kokku);
										end if;
										--raise notice '1: %, % %',r.credits,p_curr_modules_opt_credits[ii],((r.asend_kokku*r.credits)/r.ylek_kokku);
									else
										p_curr_modules_opt_credits[ii]:=p_curr_modules_opt_credits[ii]-r.credits;
									end if;
									if p_curr_modules[ii]=p_vcurr_modules[ii] then
										--raise notice 'SIIN: ';
										if r.vota=1  then
											--p_opt_credits:=p_opt_credits+((r.asend_kokku*r.credits)/r.ylek_kokku);
											null;
										else
										  p_opt_credits:=p_opt_credits+r.credits;
										end if;
									end if;
									--raise notice '%', p_curr_modules_opt_credits[ii];
								else
									if r.vota=1 and r.asend_kokku > 0 and r.ylek_kokku > 0 then
										--valem nüüd sõltub sellest kas asend_kokku > kui ylek_kokku või mitte
										if r.ylek_kokku >= r.asend_koik_kokku then
											p_curr_modules_credits[ii]:=p_curr_modules_credits[ii]-((r.asend_kokku*r.credits)/r.ylek_kokku);
										else
											p_curr_modules_credits[ii]:=p_curr_modules_credits[ii]-((r.asend_kokku*r.credits)/r.asend_koik_kokku);
										end if;
									else
										p_curr_modules_credits[ii]:=p_curr_modules_credits[ii]-r.credits;
									end if;
									--raise notice '-%', p_curr_modules_credits[ii];
								end if;
								pb_exist:=true;
							end if;
						end loop;
						--valikõpingud
						if not pb_exist then
							if r.vota=1  then
								--p_opt_credits:=p_opt_credits+((r.asend_kokku*r.credits)/r.ylek_kokku);
								null;
							else
								p_opt_credits:=p_opt_credits+r.credits;
							end if;
						end if;
					else
						if not pb_exist then
							if r.vota=1 then
								--p_opt_credits:=p_opt_credits+((r.asend_kokku*r.credits)/r.ylek_kokku);
								null;
							else
								p_opt_credits:=p_opt_credits+r.credits;
							end if;
						end if;
					end if; 
				end if;
			end if;
		end loop;

		if array_length(p_curr_modules,1) > 0 THEN
			for ii in 1..array_length(p_curr_modules,1)
			LOOP
				--vabaõppe ei pea siin kontrollima vist
				p_final_ok:=false;
				if p_vcurr_modules[ii] = 0 then
					if p_curr_modules_credits[ii] > 0 then
						-- siin kontrollime, kas moodul on jõuga täidetud; kui jah, siis nn võlga ei tohi tekkida
						if p_curr_modules_ok[ii]=false then
							--raise notice '% ',p_curr_modules_credits[ii];
							if p_curr_modules[ii]!=p_fcurr_modules[ii] then
								p_abs_credits:=p_abs_credits+p_curr_modules_credits[ii];
							end if;
						end if;
						-- lõputöö/eksam ei lähe arvesse, nende jaoks võla arvutamine jääb endiseks
						if p_curr_modules[ii]=p_fcurr_modules[ii] then
							if p_curr_modules_credits[ii]!=0 then --kui ei ole korras
								for iii in 1..array_length(p_fcurr_modules,1)
								loop
									if p_fcurr_modules[iii] > 0 and p_curr_modules_credits[iii]=0 then --midagi tehtud, ei liida
										p_final_ok:=true;
									end if;
								end loop;
								if not p_final_ok and p_fabs_credits=0 then
									p_fabs_credits:=p_fabs_credits+p_curr_modules_credits[ii];
								end if;
							end if;
						end if;
					else
						p_opt_credits:=p_opt_credits+abs(p_curr_modules_credits[ii]);
					end if;
					if p_curr_modules_opt_credits[ii] > 0 then
						--raise notice 'o %', p_curr_modules[ii];
						-- siin kontrollime, kas moodul on jõuga täidetud; kui jah, siis nn võlga ei tohi tekkida
						if p_curr_modules_ok[ii]=false then
							--raise notice 'x% ',p_curr_modules_opt_credits[ii];
							p_abs_credits:=p_abs_credits+p_curr_modules_opt_credits[ii];
						end if;
					else
						--raise notice 'xx% ',p_curr_modules_opt_credits[ii];
						p_opt_credits:=p_opt_credits+abs(p_curr_modules_opt_credits[ii]);
					end if;
					--raise notice '3. %: % % ', p_curr_modules[ii],p_abs_credits,p_opt_credits;
				end if;
			end loop;
		end if;

		--raise notice 'p_abs: %, p_fabs: %',p_abs_credits,p_fabs_credits;

		p_abs_credits:=p_abs_credits+p_fabs_credits;

	--	raise NOTICE 'Fopt: %/%', p_fabs_credits, p_abs_credits;
		--raise NOTICE 'opt: %/%', p_opt_credits, p_optional;

		

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

		if is_last_period then
			p_abs_credits:=p_curriculum_credits-p_abs_credits;
			if p_abs_credits < 0 then
				p_abs_credits:=0;
			end if;
		end if;
		--raise notice 'siin %', p_abs_credits;

exception when others THEN
	raise notice '%, %',p_id,sqlerrm;
end;
$procedure$
;
