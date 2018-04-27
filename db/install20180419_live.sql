\c hois

do $$
DECLARE
	r record;
begin
	for r in (select
							pud.type_code,
							pst.person_id,
							sd.hois_id student_id,
							dd.hois_id directive_id,
							pst.status_code,
							case
								when d.doc_type = 'K_academic_begin' then cast (du.user_data as json) ->> 'academic_begin'
								when d.doc_type = 'K_academic_end' then cast (du.user_data as json) ->> 'academic_end'
								when d.doc_type = 'K_academic_extend' then cast (du.user_data as json) ->> 'doc_date'
								else null
							end start_date,
							case
								when d.doc_type like 'K_academic_%'
								and cast(cast (du.user_data as json) ->> 'reason_code' as varchar) = 'L' then 'AKADPUHKUS_POHJUS_L'
								when d.doc_type like 'K_academic_%'
								and cast(cast (du.user_data as json) ->> 'reason_code' as varchar) = 'H' then 'AKADPUHKUS_POHJUS_T'
								when d.doc_type like 'K_academic_%'
								and cast(cast (du.user_data as json) ->> 'reason_code' as varchar) = 'K' then 'AKADPUHKUS_POHJUS_A'
								when d.doc_type like 'K_academic_%'
								and cast(cast (du.user_data as json) ->> 'reason_code' as varchar) = '04' then 'AKADPUHKUS_POHJUS_O'
								when d.doc_type like 'K_leave'
								and cast(cast (du.user_data as json) ->> 'reason_code' as varchar) = '01' then 'EKSMAT_POHJUS_A'
								when d.doc_type like 'K_leave'
								and cast(cast (du.user_data as json) ->> 'reason_code' as varchar) = '02' then 'EKSMAT_POHJUS_T'
								when d.doc_type like 'K_leave'
								and cast(cast (du.user_data as json) ->> 'reason_code' as varchar) = '03' then 'EKSMAT_POHJUS_O'
								when d.doc_type like 'K_leave'
								and cast(cast (du.user_data as json) ->> 'reason_code' as varchar) = '04' then 'EKSMAT_POHJUS_S'
								when d.doc_type like 'K_leave'
								and cast(cast (du.user_data as json) ->> 'reason_code' as varchar) = '05' then 'EKSMAT_POHJUS_V'
								when d.doc_type like 'K_leave'
								and cast(cast (du.user_data as json) ->> 'reason_code' as varchar) = '06' then 'EKSMAT_POHJUS_M'
								when d.doc_type like 'K_leave'
								and cast(cast (du.user_data as json) ->> 'reason_code' as varchar) = '07' then 'EKSMAT_POHJUS_E'
								when d.doc_type like 'K_leave'
								and cast(cast (du.user_data as json) ->> 'reason_code' as varchar) = '08' then 'EKSMAT_POHJUS_O'
								when d.doc_type like 'K_leave'
								and cast(cast (du.user_data as json) ->> 'reason_code' as varchar) = '09' then 'EKSMAT_POHJUS_R'
								when d.doc_type like 'K_leave'
								and cast(cast (du.user_data as json) ->> 'reason_code' as varchar) = '10' then 'EKSMAT_POHJUS_M'
								when d.doc_type like 'K_leave'
								and cast(cast (du.user_data as json) ->> 'reason_code' as varchar) = '11' then 'EKSMAT_POHJUS_H'
								when d.doc_type like 'K_leave'
								and cast(cast (du.user_data as json) ->> 'reason_code' as varchar) = '12' then 'EKSMAT_POHJUS_C'
								when d.doc_type like 'K_leave'
								and cast(cast (du.user_data as json) ->> 'reason_code' as varchar) = '13' then 'EKSMAT_POHJUS_C'
								when d.doc_type like 'K_leave'
								and cast(cast (du.user_data as json) ->> 'reason_code' as varchar) = '14' then 'EKSMAT_POHJUS_N'
								when d.doc_type like 'K_leave'
								and cast(cast (du.user_data as json) ->> 'reason_code' as varchar) = '15' then 'EKSMAT_POHJUS_M'
								when d.doc_type like 'K_leave'
								and cast(cast (du.user_data as json) ->> 'reason_code' as varchar) = '16' then 'EKSMAT_POHJUS_J'
								when d.doc_type like 'K_leave'
								and cast(cast (du.user_data as json) ->> 'reason_code' as varchar) = '17' then 'EKSMAT_POHJUS_E'
								when d.doc_type like 'K_leave'
								and cast(cast (du.user_data as json) ->> 'reason_code' as varchar) = '18' then 'EKSMAT_POHJUS_O'
								when d.doc_type like 'K_leave'
								and cast(cast (du.user_data as json) ->> 'reason_code' as varchar) = '19' then 'EKSMAT_POHJUS_H'
								when d.doc_type like 'K_leave'
								and cast(cast (du.user_data as json) ->> 'reason_code' as varchar) = '20' then 'EKSMAT_POHJUS_J'
								when d.doc_type like 'K_leave'
								and cast(cast (du.user_data as json) ->> 'reason_code' as varchar) = '21' then 'EKSMAT_POHJUS_N'
								when d.doc_type like 'K_leave'
								and cast(cast (du.user_data as json) ->> 'reason_code' as varchar) = '22' then 'EKSMAT_POHJUS_D'
								when d.doc_type like 'K_leave'
								and cast(cast (du.user_data as json) ->> 'reason_code' as varchar) = '23' then 'EKSMAT_POHJUS_O'
								when d.doc_type like 'K_leave'
								and cast(cast (du.user_data as json) ->> 'reason_code' as varchar) = '24' then 'EKSMAT_POHJUS_W'
								when d.doc_type like 'K_leave'
								and cast(cast (du.user_data as json) ->> 'reason_code' as varchar) = '25' then 'EKSMAT_POHJUS_B'
								when d.doc_type like 'K_leave'
								and cast(cast (du.user_data as json) ->> 'reason_code' as varchar) = '26' then 'EKSMAT_POHJUS_G'
								when d.doc_type like 'K_leave'
								and cast(cast (du.user_data as json) ->> 'reason_code' as varchar) = '27' then 'EKSMAT_POHJUS_F'
								else null
							end reason_code,
							case
								when d.doc_type = 'K_academic_begin' then cast (du.user_data as json) ->> 'academic_end'
								when d.doc_type = 'K_academic_extend' then cast (du.user_data as json) ->> 'academic_end'
								else null
							end end_date
						from
							jarva.document_register_doc_users du join jarva.document_register_docs d on
							d.doc_id = du.doc_id join jarva.users u on
							u.user_id = du.user_id join jarva.K_students st on
							st.user_id = du.user_id
							and st.k_student_id = du.unit_id
							and st.valid = 1 left join(
								select
									k_student_id,
									coalesce(
										max( cum_laude ),
										'0'
									) cum
								from
									jarva.K_graduate g
								group by
									k_student_id
							) gr on
							gr.k_student_id = st.k_student_id left join jarva.K_entry_applications ea on
							ea.K_entry_application_id = st.K_entry_application_id
							and ea.valid = 1 left join(
								select
									st2.*
								from
									jarva.K_students_status st2 join(
										select
											max( st.id ) maxid,
											st.k_student_id,
											st.doc_id
										from
											jarva.K_students_status st
										where
											st.doc_id is not null
										group by
											st.k_student_id,
											st.doc_id
									) st3 on
									st2.id = st3.maxid
							) ss on
							ss.doc_id = d.doc_id
							and ss.k_student_id = st.K_student_id left join jarva.K_groups gr2 on
							gr2.K_group_id = ss.k_group_id
							and gr2.valid = 1
							join jarva.x_jarva_data sd on sd.hois_table = 'student' and sd.jarva_id = st.k_student_id
							join jarva.x_jarva_data dd on dd.hois_table = 'directive' and dd.jarva_id = d.doc_id
							join public.student pst on pst.id = sd.hois_id
							join public.directive pud on pud.id = dd.hois_id
						where
							d.doc_type IN('K_academic_begin','K_academic_end','K_academic_extend')
							and d.valid = 1
							and du.valid = 1
							and u.valid = 1
)
LOOP
	update directive_student
		set changed=CURRENT_TIMESTAMP(3),
				changed_by='DATA_TRANSFER_PROCESS',
        start_date=to_date(r.start_date,'yyyy-mm-dd'),
				reason_code=r.reason_code,
				end_date=to_date(r.end_date,'yyyy-mm-dd')
	where directive_id=r.directive_id and student_id=r.student_id;
	if r.type_code in ('KASKKIRI_AKAD') and r.status_code in ('OPPURSTAATUS_O','OPPURSTAATUS_A') THEN
		raise notice 's_id: %, end: %, start: %, vahe: %', r.student_id, r.end_date, r.start_date,to_date(r.end_date,'yyyy-mm-dd')-to_date(r.start_date,'yyyy-mm-dd')+1;
		update student 
		set nominal_study_end=nominal_study_end+(to_date(r.end_date,'yyyy-mm-dd')-to_date(r.start_date,'yyyy-mm-dd')+1),
				changed=CURRENT_TIMESTAMP(3),
				changed_by='DATA_TRANSFER_PROCESS' 
		where id=r.student_id;
  end if;
	if r.end_date is not null and to_date(r.end_date,'yyyy-mm-dd') > now() and r.status_code='OPPURSTAATUS_O' THEN
		--raise notice 's_id: %, end: %', r.student_id, r.end_date;
		update student 
		set status_code='OPPURSTAATUS_A',
				changed=CURRENT_TIMESTAMP(3),
				changed_by='DATA_TRANSFER_PROCESS' 
		where id=r.student_id;
  end if;
	--raise notice 'dd: %', r.directive_id;
end loop;
end;
$$;