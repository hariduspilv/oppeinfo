\c hois

DO
$$
DECLARE
	k record;
begin
	for k in (select ss.school_id,ss.id,ds.directive_id,ds.start_date,ds.end_date,ds.reason_code
						from student ss
								 join directive_student ds on ss.id=ds.student_id
								 join directive dd on ds.directive_id=dd.id
								 left join job jj on dd.id=jj.directive_id and jj.student_id=ss.id and jj.type_Code like 'JOB_AKAD_TULEK%'
						where ss.school_id=7 and ss.status_code='OPPURSTAATUS_A' and dd.type_code='KASKKIRI_AKAD' and ds.end_date > to_Date('01.04.2018','dd.mm.yyyy') and jj.id is null
						order by 1, 4
						)
LOOP
	insert into job(school_id,type_code,status_code,directive_id,job_time,student_id,inserted,changed)
	values(k.school_id,'JOB_AKAD_TULEK','JOB_STATUS_VALMIS',k.directive_id,k.end_date+1,k.id,current_timestamp(3),current_timestamp(3));
	if k.end_date < now() THEN
		insert into job(school_id,type_code,status_code,directive_id,job_time,student_id,inserted,changed)
		values(k.school_id,'JOB_AKAD_LOPP_TEADE','JOB_STATUS_VALMIS',k.directive_id,k.end_date-14,k.id,current_timestamp(3),current_timestamp(3));
	end if;
end loop;
for k in (select ss.school_id,ss.id,ds.directive_id,ds.start_date,ds.end_date,ds.reason_code, jj.job_time
from student ss
		 join directive_student ds on ss.id=ds.student_id
		 join directive dd on ds.directive_id=dd.id and dd.id not in (6666,6628,6598)
     left join job jj on dd.id=jj.directive_id and jj.student_id=ss.id and jj.type_Code like 'JOB_AKAD_TULEK%'
where /*ss.school_id=7 and*/ ss.status_code='OPPURSTAATUS_A' and dd.type_code='KASKKIRI_AKAD' and ds.end_date > to_Date('01.05.2018','dd.mm.yyyy') and jj.id is null
order by 2, 5
						)
LOOP
	insert into job(school_id,type_code,status_code,directive_id,job_time,student_id,inserted,changed)
	values(k.school_id,'JOB_AKAD_TULEK','JOB_STATUS_VALMIS',k.directive_id,k.end_date+1,k.id,current_timestamp(3),current_timestamp(3));
	if k.end_date < now() THEN
		insert into job(school_id,type_code,status_code,directive_id,job_time,student_id,inserted,changed)
		values(k.school_id,'JOB_AKAD_LOPP_TEADE','JOB_STATUS_VALMIS',k.directive_id,k.end_date-14,k.id,current_timestamp(3),current_timestamp(3));
	end if;
end loop;
end;
$$;
