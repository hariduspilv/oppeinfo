\c hois

insert into user_rights(user_id,permission_code,object_code,inserted,version,inserted_by)
select u.id, 'OIGUS_V', 'TEEMAOIGUS_TEADE', now(), 0, 'admin'
from user_ u 
where u.role_code in ('ROLL_T','ROLL_O','ROLL_L') and 
not exists (select 1 from user_rights rr where rr.user_id=u.id and rr.permission_code='OIGUS_V' and rr.object_code='TEEMAOIGUS_TEADE');

insert into user_rights(user_id,permission_code,object_code,inserted,version,inserted_by)
select u.id, 'OIGUS_V', 'TEEMAOIGUS_EKSAM', now(), 0, 'admin'
from user_ u 
where u.role_code in ('ROLL_O') and 
not exists (select 1 from user_rights rr where rr.user_id=u.id and rr.permission_code='OIGUS_V' and rr.object_code='TEEMAOIGUS_EKSAM');

insert into user_rights(user_id,permission_code,object_code,inserted,version,inserted_by)
select u.id, 'OIGUS_M', 'TEEMAOIGUS_EKSAM', now(), 0, 'admin'
from user_ u 
where u.role_code in ('ROLL_O') and 
not exists (select 1 from user_rights rr where rr.user_id=u.id and rr.permission_code='OIGUS_M' and rr.object_code='TEEMAOIGUS_EKSAM');