\c hois

CREATE TABLE "final_thesis"
(
	"id" bigserial NOT NULL,
	"student_id" bigint NOT NULL,    -- viide õppurile
	"theme_et" varchar(4000)	 NOT NULL,    -- teema nimetus e.k.
	"theme_en" varchar(4000)	 NULL,    -- teema nimetus i.k.
	"has_draft" boolean NOT NULL,    -- kas on olemas lõputöö kavand true - jah false - ei
	"draft" text NULL,    -- kavand
	"status_code" varchar(100)	 NOT NULL,    -- viide klassifikaatoriule LOPUTOO_STAATUS
	"confirmed" timestamp without time zone NULL,    -- kinnitamise kp
	"confirmed_by" varchar(100)	 NULL,    -- kinnitaja nimi
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

CREATE TABLE "final_thesis_supervisor"
(
	"id" bigserial NOT NULL,
	"final_thesis_id" bigint NOT NULL,    -- viide lõputööle
	"is_primary" boolean NOT NULL,    -- kas on põhijuhendaja true - jah false - ei
	"is_external" boolean NOT NULL,    -- kas väline juhendaja true - jah false - ei, sisene
	"teacher_id" bigint NULL,    -- viide õpetajale kui tegemist on koolisisese juhendajaga
	"firstname" varchar(100)	 NULL,    -- välise juhendaja eesnimi välise juhendaja perekonnanimi välise juhendaja isikukood
	"lastname" varchar(100)	 NULL,
	"idcode" varchar(11)	 NULL,
	"occupation" varchar(100)	 NULL,    -- välise juhendaja amet
	"email" varchar(100)	 NULL,    -- välise juhendaja e-mail
	"phone" varchar(100)	 NULL,    -- välise juhendaja telefon
	"bankaccount" varchar(50)	 NULL,    -- välise juhendaja konto nr
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "final_thesis"	IS 'lõputööd';
COMMENT ON COLUMN "final_thesis"."student_id"	IS 'viide õppurile';
COMMENT ON COLUMN "final_thesis"."theme_et"	IS 'teema nimetus e.k.';
COMMENT ON COLUMN "final_thesis"."theme_en"	IS 'teema nimetus i.k.';
COMMENT ON COLUMN "final_thesis"."has_draft"	IS 'kas on olemas lõputöö kavand true - jah false - ei';
COMMENT ON COLUMN "final_thesis"."draft"	IS 'kavand';
COMMENT ON COLUMN "final_thesis"."status_code"	IS 'viide klassifikaatoriule LOPUTOO_STAATUS';
COMMENT ON COLUMN "final_thesis"."confirmed"	IS 'kinnitamise kp';
COMMENT ON COLUMN "final_thesis"."confirmed_by"	IS 'kinnitaja nimi';

COMMENT ON TABLE "final_thesis_supervisor"	IS 'lõputöö juhendajad';
COMMENT ON COLUMN "final_thesis_supervisor"."final_thesis_id"	IS 'viide lõputööle';
COMMENT ON COLUMN "final_thesis_supervisor"."is_primary"	IS 'kas on põhijuhendaja true - jah false - ei';
COMMENT ON COLUMN "final_thesis_supervisor"."is_external"	IS 'kas väline juhendaja true - jah false - ei, sisene';
COMMENT ON COLUMN "final_thesis_supervisor"."teacher_id"	IS 'viide õpetajale kui tegemist on koolisisese juhendajaga';
COMMENT ON COLUMN "final_thesis_supervisor"."firstname"	IS 'välise juhendaja eesnimi välise juhendaja perekonnanimi välise juhendaja isikukood';
COMMENT ON COLUMN "final_thesis_supervisor"."occupation"	IS 'välise juhendaja amet';
COMMENT ON COLUMN "final_thesis_supervisor"."email"	IS 'välise juhendaja e-mail';
COMMENT ON COLUMN "final_thesis_supervisor"."phone"	IS 'välise juhendaja telefon';
COMMENT ON COLUMN "final_thesis_supervisor"."bankaccount"	IS 'välise juhendaja konto nr';

/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE "final_thesis" ADD CONSTRAINT "PK_final_thesis"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_final_thesis_classifier" ON "final_thesis" ("status_code" ASC);
CREATE INDEX "IXFK_final_thesis_student" ON "final_thesis" ("student_id" ASC);
ALTER TABLE "final_thesis_supervisor" ADD CONSTRAINT "PK_final_thesis_supervisor"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_final_thesis_supervisor_final_thesis" ON "final_thesis_supervisor" ("final_thesis_id" ASC);
CREATE INDEX "IXFK_final_thesis_supervisor_teacher" ON "final_thesis_supervisor" ("teacher_id" ASC);

/* Create Foreign Key Constraints */
ALTER TABLE "final_thesis" ADD CONSTRAINT "FK_final_thesis_classifier"	FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "final_thesis" ADD CONSTRAINT "FK_final_thesis_student"	FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "final_thesis_supervisor" ADD CONSTRAINT "FK_final_thesis_supervisor_final_thesis"	FOREIGN KEY ("final_thesis_id") REFERENCES "final_thesis" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "final_thesis_supervisor" ADD CONSTRAINT "FK_final_thesis_supervisor_teacher"	FOREIGN KEY ("teacher_id") REFERENCES "public"."teacher" ("id") ON DELETE No Action ON UPDATE No Action;

insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('LOPUTOO_STAATUS','LOPUTOO_STAATUS','Lõputöö staatus',null,now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('LOPUTOO_STAATUS_S','S','Sisestatud','LOPUTOO_STAATUS',now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('LOPUTOO_STAATUS_K','K','Kinnitatud','LOPUTOO_STAATUS',now(),true,true,true,0);

CREATE INDEX "IXFK_application_directive" ON "public"."application" ("directive_id" ASC);
ALTER TABLE "public"."application" ADD CONSTRAINT "FK_application_directive"	FOREIGN KEY ("directive_id") REFERENCES "public"."directive" ("id") ON DELETE No Action ON UPDATE No Action;
COMMENT ON COLUMN "public"."application"."directive_id"	IS 'viide käskkirjale, nt akad puhkuse katkestamise puhul';

alter table final_thesis add column add_info varchar(4000);
comment on column final_thesis.add_info is 'Märkused';

alter table protocol add column is_final_thesis boolean;
comment on column protocol.is_final_thesis is 'kas on lõputöö protokoll';

alter table protocol_hdata add column final_subject_id bigint;
comment on column protocol_hdata.final_subject_id is 'viide lõputöö ainele';
alter table protocol_hdata add constraint FK_protocol_hdata_subject foreign key(final_subject_id) references subject(id);
create index IXFK_protocol_hdata_subject on protocol_hdata(final_subject_id);
alter table protocol_hdata alter column final_subject_id drop not null;

CREATE TABLE "form"
(
	"id" bigserial NOT NULL,
	"school_id" bigint NOT NULL,    -- viide õppeasutusele
	"type_code" varchar(100)	 NOT NULL,    -- viide klassifikaatorile LOPUBLANKETT
	"code" varchar(10)	 NULL,    -- blanketi tähis, nt MS 
	"status_code" varchar(100)	 NOT NULL,    -- viide klassifikaatorile LOPUBLANKETT_STAATUS
	"numeral" integer NOT NULL,    -- blanketi number, nt 1, 2 jne
	"full_code" varchar(20)	 NOT NULL,    -- blanketi number, nt MS00001
	"defect_reason" varchar(255)	 NULL,    -- rikutuks märkimise põhjus
	"printed" date NULL,    -- trükkimise kuupäev
	"defected" date NULL,    -- rikutuks märkimise kuupäev
	"inserted" date NOT NULL,
	"changed" date NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL,
	"defected_by" varchar(100)	 NULL    -- Rikutuks märkija nimi
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "form"	IS 'blanketid';
COMMENT ON COLUMN "form"."school_id"	IS 'viide õppeasutusele';
COMMENT ON COLUMN "form"."type_code"	IS 'viide klassifikaatorile LOPUBLANKETT';
COMMENT ON COLUMN "form"."code"	IS 'blanketi tähis, nt MS ';
COMMENT ON COLUMN "form"."status_code"	IS 'viide klassifikaatorile LOPUBLANKETT_STAATUS';
COMMENT ON COLUMN "form"."numeral"	IS 'blanketi number, nt 1, 2 jne';
COMMENT ON COLUMN "form"."full_code"	IS 'blanketi number, nt MS00001';
COMMENT ON COLUMN "form"."defect_reason"	IS 'rikutuks märkimise põhjus';
COMMENT ON COLUMN "form"."printed"	IS 'trükkimise kuupäev';
COMMENT ON COLUMN "form"."defected"	IS 'rikutuks märkimise kuupäev';
COMMENT ON COLUMN "form"."defected_by"	IS 'Rikutuks märkija nimi';

/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE "form" ADD CONSTRAINT "PK_form"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_form_classifier" ON "form" ("type_code" ASC);
CREATE INDEX "IXFK_form_classifier_02" ON "form" ("status_code" ASC);
CREATE INDEX "IXFK_form_school" ON "form" ("school_id" ASC);

/* Create Foreign Key Constraints */

ALTER TABLE "form" ADD CONSTRAINT "FK_form_classifier"	FOREIGN KEY ("type_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "form" ADD CONSTRAINT "FK_form_classifier_02"	FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "form" ADD CONSTRAINT "FK_form_school"	FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE No Action ON UPDATE No Action;

insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('LOPUBLANKETT_STAATUS','LOPUBLANKETT_STAATUS','Lõpudokumendi blankettide staatus',null,now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('LOPUBLANKETT_STAATUS_K','K','Kasutamata','LOPUBLANKETT_STAATUS',now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('LOPUBLANKETT_STAATUS_T','T','Trükitud','LOPUBLANKETT_STAATUS',now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('LOPUBLANKETT_STAATUS_R','R','Rikutud','LOPUBLANKETT_STAATUS',now(),true,true,true,0);

insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('LOPUBLANKETT','LOPUBLANKETT','Lõpudokumendi blankettide liigid',null,now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('LOPUBLANKETT_E','E','E - rakenduskõrghariduse diplom','LOPUBLANKETT',now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('LOPUBLANKETT_L','L','L - bakalaureuse diplom','LOPUBLANKETT',now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('LOPUBLANKETT_M','M','M - magistriõppe diplom ja integreeritud (bakal.+mag.) õppekava diplom','LOPUBLANKETT',now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('LOPUBLANKETT_O','O','O - doktoriõppe diplom','LOPUBLANKETT',now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('LOPUBLANKETT_EY','EY','EY - rakenduskõrghariduse diplom – ühisõppekava','LOPUBLANKETT',now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('LOPUBLANKETT_LY','LY','LY - bakalaureuse diplom – ühisõppekava','LOPUBLANKETT',now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('LOPUBLANKETT_MY','MY','MY - magistriõppe diplom ja integreeritud (bakal.+mag.) õppekava diplom –ühisõppekava','LOPUBLANKETT',now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('LOPUBLANKETT_OY','OY','OY - doktoriõppe diplom -  ühisõppekava','LOPUBLANKETT',now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('LOPUBLANKETT_KK','KK','KK - kutsekeskhariduse lõputunnistus','LOPUBLANKETT',now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('LOPUBLANKETT_K','K','K -  kutseõppe lõputunnistus','LOPUBLANKETT',now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('LOPUBLANKETT_KE','KE','KE - kutseeriharidusõppe lõputunnistus','LOPUBLANKETT',now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('LOPUBLANKETT_R','R','R - eestikeelne akadeemiline õiend','LOPUBLANKETT',now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('LOPUBLANKETT_DE','DE','DE - ingliskeelne akadeemiline õiend','LOPUBLANKETT',now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('LOPUBLANKETT_S','S','S - akadeemilise õiendi lisaleht','LOPUBLANKETT',now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('LOPUBLANKETT_HIN','HIN','Hinneteleht','LOPUBLANKETT',now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('LOPUBLANKETT_HINL','HINL','Hinnetelehe lisaleht','LOPUBLANKETT',now(),true,true,true,0);


insert into user_role_default(object_code,permission_code,role_code) values('TEEMAOIGUS_TUNNIJAOTUSPLAAN','OIGUS_V','ROLL_O');
insert into user_role_default(object_code,permission_code,role_code) values('TEEMAOIGUS_AINEOPPETAJA','OIGUS_V','ROLL_O');
insert into user_rights(user_id,permission_code,object_code,inserted,changed,version,inserted_by,changed_by)
select id, 'OIGUS_V', 'TEEMAOIGUS_TUNNIJAOTUSPLAAN', current_timestamp(3), current_timestamp(3),0,'Automaat', 'Automaat' from user_ uu where role_code='ROLL_O'
and (select count(*) from user_rights rr where rr.user_id=uu.id and object_code='TEEMAOIGUS_TUNNIJAOTUSPLAAN' and permission_code='OIGUS_V')=0;
insert into user_rights(user_id,permission_code,object_code,inserted,changed,version,inserted_by,changed_by)
select id, 'OIGUS_V', 'TEEMAOIGUS_AINEOPPETAJA', current_timestamp(3), current_timestamp(3),0,'Automaat', 'Automaat' from user_ uu where role_code='ROLL_O'
and (select count(*) from user_rights rr where rr.user_id=uu.id and object_code='TEEMAOIGUS_AINEOPPETAJA' and permission_code='OIGUS_V')=0;

insert into classifier (code, value, name_et, main_class_code, inserted, valid, is_vocational, is_higher, version) values
('TEEMAOIGUS_UUDE_AASTASSE', 'UUDE_AASTASSE', 'Uude aastasse viimine', 'TEEMAOIGUS', now(), true,  true,  true, 0);
 
insert into user_role_default (object_code, permission_code, role_code) values 
('TEEMAOIGUS_UUDE_AASTASSE', 'OIGUS_V', 'ROLL_A');


insert into user_rights(user_id,permission_code,object_code,inserted,version,inserted_by)
select u.id, 'OIGUS_V', 'TEEMAOIGUS_UUDE_AASTASSE', now(), 0, 'admin'
from user_ u where u.role_code in ('ROLL_A');


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

CREATE OR REPLACE FUNCTION get_study_period(p_date date, p_school int)
 RETURNS integer
 LANGUAGE plpgsql
AS $function$
declare
    t_rec1 record;
	  p_id integer:=0;
begin
  for t_rec1 in (select sp.* from study_year st join study_period sp on st.id=sp.study_year_id where st.school_id=p_school and p_date between sp.start_date and sp.end_date)
	loop
		p_id:=t_rec1.id;
  end loop;
	if p_id=0 then
		for t_rec1 in (select sp.* from study_year st join study_period sp on st.id=sp.study_year_id where st.school_id=p_school and sp.start_date > p_date order by sp.start_date)
		loop
			p_id:=t_rec1.id;
			exit;
		end loop;
  end if;
	return p_id;
end
$function$;

alter table student_vocational_result alter column curriculum_version_omodule_id drop not null;


CREATE OR REPLACE FUNCTION public.upd_del_apel_result()
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

alter table protocol_hdata alter column subject_study_period_id drop not null;

alter table directive add column is_higher boolean;
comment on column directive.is_higher is 'kas lõpet käskkiri on kõrg või kutse jaoks';

CREATE TABLE "directive_student_occupation"
(
	"id" bigserial NOT NULL,
	"directive_student_id" bigint NOT NULL ,
	"inserted" timestamp without time zone NOT NULL,
	"occupation_code" varchar(100)	 NOT NULL,    -- viide kutsele/osakutsele
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "directive_student_occupation"	IS 'lõpetamisel saadavad kutsed/osakutsed';
COMMENT ON COLUMN "directive_student_occupation"."directive_student_id"	IS 'viide õppurile käskkirjal';
COMMENT ON COLUMN "directive_student_occupation"."occupation_code"	IS 'viide kutsele/osakutsele';

ALTER TABLE "directive_student_occupation" ADD CONSTRAINT "PK_directive_student_occupation"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_directive_student_occupation_classifier" ON "directive_student_occupation" ("occupation_code" ASC);
CREATE INDEX "IXFK_directive_student_occupation_directive_student" ON "directive_student_occupation" ("directive_student_id" ASC);

/* Create Foreign Key Constraints */

ALTER TABLE "directive_student_occupation" ADD CONSTRAINT "FK_directive_student_occupation_classifier"	FOREIGN KEY ("occupation_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "directive_student_occupation" ADD CONSTRAINT "FK_directive_student_occupation_directive_student"	FOREIGN KEY ("directive_student_id") REFERENCES "public"."directive_student" ("id") ON DELETE No Action ON UPDATE No Action;

alter table journal_entry_student add column  grade_inserted_by varchar(100);
alter table journal_entry_student_history add column  grade_inserted_by varchar(100);


/* Create Tables */

CREATE TABLE "final_doc_signer"
(
	"id" bigserial NOT NULL,
	"school_id" bigint NOT NULL,    -- viide õppeasutusele
	"name" varchar(100)	 NOT NULL,    -- allkirjastaja nimi
	"position" varchar(255)	 NOT NULL,    -- ametikoht
	"is_first" boolean NOT NULL,    -- kas esimene allkirjastaja
	"is_valid" boolean NOT NULL,    -- kas kehtiv, vaikimisi jah
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "final_doc_signer"	IS 'lõpudokumentide allkirjastaja';
COMMENT ON COLUMN "final_doc_signer"."school_id"	IS 'viide õppeasutusele';
COMMENT ON COLUMN "final_doc_signer"."name"	IS 'allkirjastaja nimi';
COMMENT ON COLUMN "final_doc_signer"."position"	IS 'ametikoht';

COMMENT ON COLUMN "final_doc_signer"."is_first"	IS 'kas esimene allkirjastaja';
COMMENT ON COLUMN "final_doc_signer"."is_valid"	IS 'kas kehtiv, vaikimisi jah';

/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE "final_doc_signer" ADD CONSTRAINT "PK_final_doc_signer"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_final_doc_signer_school" ON "final_doc_signer" ("school_id" ASC);

ALTER TABLE "final_doc_signer" ADD CONSTRAINT "FK_final_doc_signer_school"	FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE No Action ON UPDATE No Action;


insert into classifier_connect(classifier_code,connect_classifier_code,inserted,version,main_classifier_code,inserted_by)
values('OPPEASTE_441','LOPUBLANKETT_KK',current_timestamp(3),0,'LOPUBLANKETT','Automaat');
insert into classifier_connect(classifier_code,connect_classifier_code,inserted,version,main_classifier_code,inserted_by)
values('OPPEASTE_323','LOPUBLANKETT_KK',current_timestamp(3),0,'LOPUBLANKETT','Automaat');
insert into classifier_connect(classifier_code,connect_classifier_code,inserted,version,main_classifier_code,inserted_by)
values('OPPEASTE_411','LOPUBLANKETT_KK',current_timestamp(3),0,'LOPUBLANKETT','Automaat');
insert into classifier_connect(classifier_code,connect_classifier_code,inserted,version,main_classifier_code,inserted_by)
values('OPPEASTE_413','LOPUBLANKETT_KK',current_timestamp(3),0,'LOPUBLANKETT','Automaat');

insert into classifier_connect(classifier_code,connect_classifier_code,inserted,version,main_classifier_code,inserted_by)
values('OPPEASTE_442','LOPUBLANKETT_K',current_timestamp(3),0,'LOPUBLANKETT','Automaat');
insert into classifier_connect(classifier_code,connect_classifier_code,inserted,version,main_classifier_code,inserted_by)
values('OPPEASTE_443','LOPUBLANKETT_K',current_timestamp(3),0,'LOPUBLANKETT','Automaat');
insert into classifier_connect(classifier_code,connect_classifier_code,inserted,version,main_classifier_code,inserted_by)
values('OPPEASTE_421','LOPUBLANKETT_K',current_timestamp(3),0,'LOPUBLANKETT','Automaat');
insert into classifier_connect(classifier_code,connect_classifier_code,inserted,version,main_classifier_code,inserted_by)
values('OPPEASTE_431','LOPUBLANKETT_K',current_timestamp(3),0,'LOPUBLANKETT','Automaat');
insert into classifier_connect(classifier_code,connect_classifier_code,inserted,version,main_classifier_code,inserted_by)
values('OPPEASTE_408','LOPUBLANKETT_K',current_timestamp(3),0,'LOPUBLANKETT','Automaat');
insert into classifier_connect(classifier_code,connect_classifier_code,inserted,version,main_classifier_code,inserted_by)
values('OPPEASTE_409','LOPUBLANKETT_K',current_timestamp(3),0,'LOPUBLANKETT','Automaat');
insert into classifier_connect(classifier_code,connect_classifier_code,inserted,version,main_classifier_code,inserted_by)
values('OPPEASTE_410','LOPUBLANKETT_K',current_timestamp(3),0,'LOPUBLANKETT','Automaat');
insert into classifier_connect(classifier_code,connect_classifier_code,inserted,version,main_classifier_code,inserted_by)
values('OPPEASTE_412','LOPUBLANKETT_K',current_timestamp(3),0,'LOPUBLANKETT','Automaat');

insert into classifier_connect(classifier_code,connect_classifier_code,inserted,version,main_classifier_code,inserted_by)
values('OPPEASTE_452','LOPUBLANKETT_KE',current_timestamp(3),0,'LOPUBLANKETT','Automaat');
insert into classifier_connect(classifier_code,connect_classifier_code,inserted,version,main_classifier_code,inserted_by)
values('OPPEASTE_453','LOPUBLANKETT_KE',current_timestamp(3),0,'LOPUBLANKETT','Automaat');

insert into classifier_connect(classifier_code,connect_classifier_code,inserted,version,main_classifier_code,inserted_by)
values('OPPEASTE_732','LOPUBLANKETT_O',current_timestamp(3),0,'LOPUBLANKETT','Automaat');
insert into classifier_connect(classifier_code,connect_classifier_code,inserted,version,main_classifier_code,inserted_by)
values('OPPEASTE_733','LOPUBLANKETT_O',current_timestamp(3),0,'LOPUBLANKETT','Automaat');
insert into classifier_connect(classifier_code,connect_classifier_code,inserted,version,main_classifier_code,inserted_by)
values('OPPEASTE_734','LOPUBLANKETT_O',current_timestamp(3),0,'LOPUBLANKETT','Automaat');

insert into classifier_connect(classifier_code,connect_classifier_code,inserted,version,main_classifier_code,inserted_by)
values('OPPEASTE_502','LOPUBLANKETT_M',current_timestamp(3),0,'LOPUBLANKETT','Automaat');
insert into classifier_connect(classifier_code,connect_classifier_code,inserted,version,main_classifier_code,inserted_by)
values('OPPEASTE_503','LOPUBLANKETT_M',current_timestamp(3),0,'LOPUBLANKETT','Automaat');
insert into classifier_connect(classifier_code,connect_classifier_code,inserted,version,main_classifier_code,inserted_by)
values('OPPEASTE_612','LOPUBLANKETT_M',current_timestamp(3),0,'LOPUBLANKETT','Automaat');
insert into classifier_connect(classifier_code,connect_classifier_code,inserted,version,main_classifier_code,inserted_by)
values('OPPEASTE_613','LOPUBLANKETT_M',current_timestamp(3),0,'LOPUBLANKETT','Automaat');
insert into classifier_connect(classifier_code,connect_classifier_code,inserted,version,main_classifier_code,inserted_by)
values('OPPEASTE_614','LOPUBLANKETT_M',current_timestamp(3),0,'LOPUBLANKETT','Automaat');
insert into classifier_connect(classifier_code,connect_classifier_code,inserted,version,main_classifier_code,inserted_by)
values('OPPEASTE_633','LOPUBLANKETT_M',current_timestamp(3),0,'LOPUBLANKETT','Automaat');

insert into classifier_connect(classifier_code,connect_classifier_code,inserted,version,main_classifier_code,inserted_by)
values('OPPEASTE_511','LOPUBLANKETT_L',current_timestamp(3),0,'LOPUBLANKETT','Automaat');
insert into classifier_connect(classifier_code,connect_classifier_code,inserted,version,main_classifier_code,inserted_by)
values('OPPEASTE_512','LOPUBLANKETT_L',current_timestamp(3),0,'LOPUBLANKETT','Automaat');
insert into classifier_connect(classifier_code,connect_classifier_code,inserted,version,main_classifier_code,inserted_by)
values('OPPEASTE_501','LOPUBLANKETT_M',current_timestamp(3),0,'LOPUBLANKETT','Automaat');

insert into classifier_connect(classifier_code,connect_classifier_code,inserted,version,main_classifier_code,inserted_by)
values('OPPEASTE_513','LOPUBLANKETT_M',current_timestamp(3),0,'LOPUBLANKETT','Automaat');
insert into classifier_connect(classifier_code,connect_classifier_code,inserted,version,main_classifier_code,inserted_by)
values('OPPEASTE_514','LOPUBLANKETT_M',current_timestamp(3),0,'LOPUBLANKETT','Automaat');


CREATE TABLE "diploma"
(
	"id" bigserial NOT NULL ,
	"school_id" bigint NOT NULL,
	"firstname" varchar(100)	 NOT NULL,
	"lastname" varchar(100)	 NOT NULL,
	"idcode" varchar(11)	 NULL,    -- Eesti isikukood
	"birthdate" date NULL,    -- sünnikp, vajalik isikukoodi puudumisel
	"is_cum_lade" boolean NOT NULL,    -- kas lõpetatud kiitusega/cum laude
	"mer_code" varchar(10)	 NOT NULL,    -- õppekava HTM kood
	"curriculum_name_et" varchar(255)	 NOT NULL,    -- õppekava nimetus (nimetav kääne)
	"school_name_genitive_et" varchar(255)	 NULL,    -- õppeasutuse nimi omastavas käändes
	"type_code" varchar(100)	 NOT NULL,    -- viide klassifikaatorile LOPUBLANKETT
	"status_code" varchar(100)	 NOT NULL,    -- viide klassifikaatorile LOPUDOK_STAATUS
	"student_id" bigint NOT NULL,
	"directive_id" bigint NOT NULL,    -- viide käskkirjale
	"form_id" bigint NULL,    -- viide blanketile
	"level" varchar(255)	 NOT NULL,    -- tase, nt kolmanda taseme, 4. taseme kutseõpe jne
	"signer1_name" varchar(100)	 NULL,    -- 1. allkirjastaja nimi
	"signer1_position" varchar(255)	 NULL,    -- 1. allkirjastaja ametikoht
	"signer2_name" varchar(100)	 NULL,    -- 2. allkirjastaja nimi
	"signer2_position" varchar(255)	 NULL,    -- 2. allkirjastaja ametikoht
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "diploma"	IS 'Tunnistused ja diplomid';
COMMENT ON COLUMN "diploma"."idcode"	IS 'Eesti isikukood';
COMMENT ON COLUMN "diploma"."birthdate"	IS 'sünnikp, vajalik isikukoodi puudumisel';
COMMENT ON COLUMN "diploma"."is_cum_lade"	IS 'kas lõpetatud kiitusega/cum laude';
COMMENT ON COLUMN "diploma"."mer_code"	IS 'õppekava HTM kood';
COMMENT ON COLUMN "diploma"."curriculum_name_et"	IS 'õppekava nimetus (nimetav kääne)';
COMMENT ON COLUMN "diploma"."school_name_genitive_et"	IS 'õppeasutuse nimi omastavas käändes';
COMMENT ON COLUMN "diploma"."type_code"	IS 'viide klassifikaatorile LOPUBLANKETT';
COMMENT ON COLUMN "diploma"."status_code"	IS 'viide klassifikaatorile LOPUDOK_STAATUS';
COMMENT ON COLUMN "diploma"."directive_id"	IS 'viide käskkirjale';
COMMENT ON COLUMN "diploma"."form_id"	IS 'viide blanketile';
COMMENT ON COLUMN "diploma"."level"	IS 'tase, nt kolmanda taseme, 4. taseme kutseõpe jne';
COMMENT ON COLUMN "diploma"."signer1_name"	IS '1. allkirjastaja nimi';
COMMENT ON COLUMN "diploma"."signer1_position"	IS '1. allkirjastaja ametikoht';
COMMENT ON COLUMN "diploma"."signer2_name"	IS '2. allkirjastaja nimi';
COMMENT ON COLUMN "diploma"."signer2_position"	IS '2. allkirjastaja ametikoht';

/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE "diploma" ADD CONSTRAINT "PK_diploma"	PRIMARY KEY ("id")
;

CREATE INDEX "IXFK_diploma_classifier" ON "diploma" ("type_code" ASC)
;

CREATE INDEX "IXFK_diploma_classifier_02" ON "diploma" ("status_code" ASC)
;

CREATE INDEX "IXFK_diploma_directive" ON "diploma" ("directive_id" ASC)
;

CREATE INDEX "IXFK_diploma_form" ON "diploma" ("form_id" ASC)
;

CREATE INDEX "IXFK_diploma_school" ON "diploma" ("school_id" ASC)
;

CREATE INDEX "IXFK_diploma_student" ON "diploma" ("student_id" ASC)
;

/* Create Foreign Key Constraints */

ALTER TABLE "diploma" ADD CONSTRAINT "FK_diploma_classifier"	FOREIGN KEY ("type_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "diploma" ADD CONSTRAINT "FK_diploma_classifier_02"	FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "diploma" ADD CONSTRAINT "FK_diploma_directive"	FOREIGN KEY ("directive_id") REFERENCES "public"."directive" ("id") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "diploma" ADD CONSTRAINT "FK_diploma_form"	FOREIGN KEY ("form_id") REFERENCES "form" ("id") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "diploma" ADD CONSTRAINT "FK_diploma_school"	FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "diploma" ADD CONSTRAINT "FK_diploma_student"	FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE No Action ON UPDATE No Action
;


alter table diploma add COLUMN 
"occupation_text" varchar(1000)	 NULL,    -- kutse teks, täidetakse juhul kui asutyus on kutseandja ja ta saab kutse
add COLUMN 	"partoccupation_text" varchar(1000)	 NULL,    -- osakutse teks, täidetakse juhul kui asutus on kutseandja ja ta saab osakutse
add COLUMN 	"is_occupation" boolean NOT NULL,    -- kas asutus on kutseandja ja õppur saab kutse
add COLUMN 	"is_partoccupation" boolean NOT NULL    -- kas asutus on kutseandja ja õppur saab osakutse
;

COMMENT ON COLUMN "diploma"."occupation_text"	IS 'kutse teks, täidetakse juhul kui asutyus on kutseandja ja ta saab kutse';
COMMENT ON COLUMN "diploma"."partoccupation_text"	IS 'osakutse teks, täidetakse juhul kui asutus on kutseandja ja ta saab osakutse';
COMMENT ON COLUMN "diploma"."is_occupation"	IS 'kas asutus on kutseandja ja õppur saab kutse';
COMMENT ON COLUMN "diploma"."is_partoccupation"	IS 'kas asutus on kutseandja ja õppur saab osakutse';

alter table school add column name_genitive_et varchar(255);
comment on column school.name_genitive_et is 'õppeasutuse nimetus omastavas';

insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('LOPUDOK_STAATUS','LOPUDOK_STAATUS','Lõpudokumentide staatus',null,now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('LOPUDOK_STAATUS_K','K','Koostatud','LOPUDOK_STAATUS',now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('LOPUDOK_STAATUS_T','T','Trükitud','LOPUDOK_STAATUS',now(),true,true,true,0);
insert into classifier(code,value,name_et,main_class_code,inserted,valid,is_vocational,is_higher,version) values('LOPUDOK_STAATUS_V','V','Väljastatud','LOPUDOK_STAATUS',now(),true,true,true,0);

insert into classifier (code, value, name_et, main_class_code, inserted, valid, is_vocational, is_higher, version) values
('TEEMAOIGUS_LOPDOKALLKIRI', 'LOPDOKALLKIRI', 'Lõpudok. allkirjastajad', 'TEEMAOIGUS', now(), true,  true, true, 0);

insert into user_role_default (object_code, permission_code, role_code) values 
('TEEMAOIGUS_LOPDOKALLKIRI', 'OIGUS_V', 'ROLL_A');

insert into user_rights(user_id,permission_code,object_code,inserted,version,inserted_by)
select u.id, 'OIGUS_V', 'TEEMAOIGUS_LOPDOKALLKIRI', now(), 0, 'admin'
from user_ u where u.role_code in ('ROLL_A');



insert into classifier (code, value, name_et, main_class_code, inserted, valid, is_vocational, is_higher, version) values
('TEEMAOIGUS_LOPTUNNISTUS_TRUKKIMINE', 'LOPTUNNISTUS_TRUKKIMINE', 'Lõputunnistuste/diplomite trükkimine', 'TEEMAOIGUS', now(), true,  true, true, 0);

insert into user_role_default (object_code, permission_code, role_code) values 
('TEEMAOIGUS_LOPTUNNISTUS_TRUKKIMINE', 'OIGUS_V', 'ROLL_A');

insert into user_rights(user_id,permission_code,object_code,inserted,version,inserted_by)
select u.id, 'OIGUS_V', 'TEEMAOIGUS_LOPTUNNISTUS_TRUKKIMINE', now(), 0, 'admin'
from user_ u where u.role_code in ('ROLL_A');



insert into classifier (code, value, name_et, main_class_code, inserted, valid, is_vocational, is_higher, version) values
('TEEMAOIGUS_HINNETELEHT_TRUKKIMINE', 'HINNETELEHT_TRUKKIMINE', 'Akad. õiendite/hinnetelehtede trükkimine', 'TEEMAOIGUS', now(), true,  true, true, 0);

insert into user_role_default (object_code, permission_code, role_code) values 
('TEEMAOIGUS_HINNETELEHT_TRUKKIMINE', 'OIGUS_V', 'ROLL_A');

insert into user_rights(user_id,permission_code,object_code,inserted,version,inserted_by)
select u.id, 'OIGUS_V', 'TEEMAOIGUS_HINNETELEHT_TRUKKIMINE', now(), 0, 'admin'
from user_ u where u.role_code in ('ROLL_A');

update classifier set extraval1=trim(substr(name_et,5)) where main_class_code='OPPEASTE';


/* Create Tables */

CREATE TABLE "diploma_supplement"
(
	"id" bigserial NOT NULL,
	"diploma_id" bigint NOT NULL,
	"student_id" bigint NOT NULL,
	"school_name_et" varchar(100)	 NOT NULL,    -- õppeasutuse nimi e.k.
	"school_name_en" varchar(100)	 NULL,    -- õppeasutuse nimi i.k.
	"firstname" varchar(100)	 NOT NULL,
	"lastname" varchar(100)	 NOT NULL,
	"idcode" varchar(11)	 NULL,    -- eesti isikukood
	"birthdate" date NULL,    -- sünnikp, vajalik isikukoodi puudumisel
	"curriculum_name_et" varchar(255)	 NOT NULL,    -- õppekava nimetus (nimetav kääne)
	"curriculum_name_en" varchar(255)	 NULL,
	"mer_code" varchar(10)	 NOT NULL,    -- õppekava HTM kood
	"ekr" varchar(1)	 NULL,    -- ekr tase kujul 1, 2, 3, 4, 5, 6, 7, 8
	"credits" numeric(5,1) NOT NULL,    -- õppekava maht
	"vocational_curriculum_type" varchar(100)	 NULL,    -- õppekava liik
	"study_form_name_et" varchar(100)	 NULL,    -- õppevorm e.k.
	"study_language_name_et" varchar(100)	 NULL,    -- õppekeel e.k.
	"study_form_name_en" varchar(100)	 NULL,    -- õppevorm i.k.
	"study_language_name_en" varchar(100)	 NULL,    -- õppekeel i.k.
	"outcomes_et" varchar(20000)	 NOT NULL,    -- õppekava õpiväljundid
	"study_company" varchar(1000)	 NULL,    -- töökohapõhise õppe puhul õppeasutus
	"signer1_name" varchar(100)	 NULL,    -- 1. allkirjastaja nimi
	"signer1_position" varchar(255)	 NULL,    -- 1. allkirjastaja ametikoht
	"status_code" varchar(100)	 NOT NULL,    -- staatus, viide klassifikaatorile LOPUDOK_STAATUS
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL,
	"pg_nr_et" smallint NULL,    -- lk arv hinnetelehtede ja eesti keelse akad õiendi puhul
	"pg_nr_en" smallint NULL    -- lk arv ingliskeelse akad õiendi puhul
)
;

CREATE TABLE "diploma_supplement_study_result"
(
	"id" bigserial NOT NULL,
	"diploma_supplement_id" bigint NOT NULL,
	"name_et" varchar(255)	 NOT NULL,    -- nimetus e.k.
	"name_en" varchar(255)	 NULL,    -- nimetus i.k.
	"credits" numeric(4,1) NOT NULL,    -- maht EAP/EKAP, mahu puudumisel 0
	"grade" varchar(3)	 NOT NULL,
	"grade_name_et" varchar(100)	 NOT NULL,    -- tulemuse tekstiline vaste, nt väga hea jms
	"grade_name_en" varchar(100)	 NULL,    -- tulemuse tekstiline vaste i.k., nt väga hea jms
	"grade_date" date NULL,    -- soorituse kp
	"teacher" varchar(255)	 NULL,    -- õpetaja nimi
	"is_apel_formal" boolean NOT NULL,    -- kas tegemist on formaalse VÕTAga
	"is_apel_informal" boolean NOT NULL,    -- kas tegemist on informaalse VÕTAga
	"apel_school_name_et" varchar(100)	 NULL,    -- VÕTA kooli nimi
	"apel_school_name_en" varchar(100)	 NULL,    -- VÕTA kooli nimi i.k.
	"is_final" boolean NOT NULL,    -- kas tegemist lõpusooritusega
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

CREATE TABLE "diploma_supplement_form"
(
	"form_id" bigint NOT NULL,    -- viide blanketile
	"diploma_supplement_id" bigint NOT NULL,    -- viide hinnetelehele
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "diploma_supplement"
	IS 'hinnetelehed/akad õiendid'
;

COMMENT ON COLUMN "diploma_supplement"."school_name_et"
	IS 'õppeasutuse nimi e.k.'
;

COMMENT ON COLUMN "diploma_supplement"."school_name_en"
	IS 'õppeasutuse nimi i.k.'
;

COMMENT ON COLUMN "diploma_supplement"."idcode"
	IS 'eesti isikukood'
;

COMMENT ON COLUMN "diploma_supplement"."birthdate"
	IS 'sünnikp, vajalik isikukoodi puudumisel'
;

COMMENT ON COLUMN "diploma_supplement"."curriculum_name_et"
	IS 'õppekava nimetus (nimetav kääne)'
;

COMMENT ON COLUMN "diploma_supplement"."mer_code"
	IS 'õppekava HTM kood'
;

COMMENT ON COLUMN "diploma_supplement"."ekr"
	IS 'ekr tase kujul 1, 2, 3, 4, 5, 6, 7, 8'
;

COMMENT ON COLUMN "diploma_supplement"."credits"
	IS 'õppekava maht'
;

COMMENT ON COLUMN "diploma_supplement"."vocational_curriculum_type"
	IS 'õppekava liik'
;

COMMENT ON COLUMN "diploma_supplement"."study_form_name_et"
	IS 'õppevorm e.k.'
;

COMMENT ON COLUMN "diploma_supplement"."study_language_name_et"
	IS 'õppekeel e.k.'
;

COMMENT ON COLUMN "diploma_supplement"."study_form_name_en"
	IS 'õppevorm i.k.'
;

COMMENT ON COLUMN "diploma_supplement"."study_language_name_en"
	IS 'õppekeel i.k.'
;

COMMENT ON COLUMN "diploma_supplement"."outcomes_et"
	IS 'õppekava õpiväljundid'
;

COMMENT ON COLUMN "diploma_supplement"."study_company"
	IS 'töökohapõhise õppe puhul õppeasutus'
;

COMMENT ON COLUMN "diploma_supplement"."signer1_name"
	IS '1. allkirjastaja nimi'
;

COMMENT ON COLUMN "diploma_supplement"."signer1_position"
	IS '1. allkirjastaja ametikoht'
;

COMMENT ON COLUMN "diploma_supplement"."status_code"
	IS 'staatus, viide klassifikaatorile LOPUDOK_STAATUS'
;

COMMENT ON COLUMN "diploma_supplement"."pg_nr_et"
	IS 'lk arv hinnetelehtede ja eesti keelse akad õiendi puhul'
;

COMMENT ON COLUMN "diploma_supplement"."pg_nr_en"
	IS 'lk arv ingliskeelse akad õiendi puhul'
;


COMMENT ON TABLE "diploma_supplement_study_result"
	IS 'hinnetelehtede/akad õiendite sooritused'
;

COMMENT ON COLUMN "diploma_supplement_study_result"."name_et"
	IS 'nimetus e.k.'
;

COMMENT ON COLUMN "diploma_supplement_study_result"."name_en"
	IS 'nimetus i.k.'
;

COMMENT ON COLUMN "diploma_supplement_study_result"."credits"
	IS 'maht EAP/EKAP, mahu puudumisel 0'
;

COMMENT ON COLUMN "diploma_supplement_study_result"."grade_name_et"
	IS 'tulemuse tekstiline vaste, nt väga hea jms'
;

COMMENT ON COLUMN "diploma_supplement_study_result"."grade_name_en"
	IS 'tulemuse tekstiline vaste i.k., nt väga hea jms'
;

COMMENT ON COLUMN "diploma_supplement_study_result"."grade_date"
	IS 'soorituse kp'
;

COMMENT ON COLUMN "diploma_supplement_study_result"."teacher"
	IS 'õpetaja nimi'
;

COMMENT ON COLUMN "diploma_supplement_study_result"."is_apel_formal"
	IS 'kas tegemist on formaalse VÕTAga'
;

COMMENT ON COLUMN "diploma_supplement_study_result"."is_apel_informal"
	IS 'kas tegemist on informaalse VÕTAga'
;

COMMENT ON COLUMN "diploma_supplement_study_result"."apel_school_name_et"
	IS 'VÕTA kooli nimi'
;

COMMENT ON COLUMN "diploma_supplement_study_result"."apel_school_name_en"
	IS 'VÕTA kooli nimi i.k.'
;

COMMENT ON COLUMN "diploma_supplement_study_result"."is_final"
	IS 'kas tegemist lõpusooritusega'
;

COMMENT ON TABLE "diploma_supplement_form"
	IS 'hinnetelehetede ja blankettide seos'
;

COMMENT ON COLUMN "diploma_supplement_form"."form_id"
	IS 'viide blanketile'
;

COMMENT ON COLUMN "diploma_supplement_form"."diploma_supplement_id"
	IS 'viide hinnetelehele'
;

/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE "diploma_supplement" ADD CONSTRAINT "PK_diploma_supplement"
	PRIMARY KEY ("id")
;

CREATE INDEX "IXFK_diploma_supplement_classifier" ON "diploma_supplement" ("status_code" ASC)
;

CREATE INDEX "IXFK_diploma_supplement_diploma" ON "diploma_supplement" ("diploma_id" ASC)
;

CREATE INDEX "IXFK_diploma_supplement_student" ON "diploma_supplement" ("student_id" ASC)
;

ALTER TABLE "diploma_supplement_study_result" ADD CONSTRAINT "PK_diploma_supplement_study_result"
	PRIMARY KEY ("id")
;

CREATE INDEX "IXFK_diploma_supplement_study_result_diploma_supplement" ON "diploma_supplement_study_result" ("diploma_supplement_id" ASC)
;

ALTER TABLE "diploma_supplement_form" ADD CONSTRAINT "PK_diploma_supplement_form"
	PRIMARY KEY ("form_id")
;

CREATE INDEX "IXFK_diploma_supplement_form_diploma_supplement" ON "diploma_supplement_form" ("diploma_supplement_id" ASC)
;

CREATE INDEX "IXFK_diploma_supplement_form_form" ON "diploma_supplement_form" ("form_id" ASC)
;

/* Create Foreign Key Constraints */

ALTER TABLE "diploma_supplement" ADD CONSTRAINT "FK_diploma_supplement_classifier"
	FOREIGN KEY ("status_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "diploma_supplement" ADD CONSTRAINT "FK_diploma_supplement_diploma"
	FOREIGN KEY ("diploma_id") REFERENCES "diploma" ("id") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "diploma_supplement" ADD CONSTRAINT "FK_diploma_supplement_student"
	FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "diploma_supplement_study_result" ADD CONSTRAINT "FK_diploma_supplement_study_result_diploma_supplement"
	FOREIGN KEY ("diploma_supplement_id") REFERENCES "diploma_supplement" ("id") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "diploma_supplement_form" ADD CONSTRAINT "FK_diploma_supplement_form_diploma_supplement"
	FOREIGN KEY ("diploma_supplement_id") REFERENCES "diploma_supplement" ("id") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "diploma_supplement_form" ADD CONSTRAINT "FK_diploma_supplement_form_form"
	FOREIGN KEY ("form_id") REFERENCES "form" ("id") ON DELETE No Action ON UPDATE No Action
;

alter table student_vocational_result add column arr_modules bigint[];

alter table school add column is_minor_student_absence boolean;
comment on column school.is_minor_student_absence is 'alla 18-aastane esitab puudumistõendi';

CREATE OR REPLACE FUNCTION get_study_period(p_date date, p_school integer)
 RETURNS integer
 LANGUAGE plpgsql
AS $function$
declare
    t_rec1 record;
	  p_id integer:=0;
begin
  for t_rec1 in (select sp.* from study_year st join study_period sp on st.id=sp.study_year_id where st.school_id=p_school and p_date between sp.start_date and sp.end_date)
	loop
		p_id:=t_rec1.id;
  end loop;
	if p_id=0 then
		for t_rec1 in (select sp.* from study_year st join study_period sp on st.id=sp.study_year_id where st.school_id=p_school and sp.start_date > p_date order by sp.start_date)
		loop
			p_id:=t_rec1.id;
			exit;
		end loop;
  end if;
	return p_id;
end
$function$;

CREATE OR REPLACE FUNCTION get_study_year(p_date date, p_school integer)
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

CREATE OR REPLACE FUNCTION public.ins_upd_del_result()
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

CREATE OR REPLACE FUNCTION upd_del_apel_result()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
declare
    u_count integer;
		b_count integer;
		r record;
		p_id integer;
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
			ELSE
				delete from student_higher_result where apel_application_record_id in (select aa.id from apel_application_record aa where aa.apel_application_id=NEW.id);
			end if;
		end if;
	end if;
	return null;
end
$function$;

CREATE OR REPLACE FUNCTION upd_student_curriculum_completion(p_id bigint)
 RETURNS integer
 LANGUAGE plpgsql
AS $function$
declare 
  pb_exist boolean:=false;
	p_curr_modules bigint array;
  p_curr_modules_credits numeric array;
	p_curr_modules2 bigint array;
  p_study_modules bigint array;
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
	a_count int:=0;
BEGIN
	--raise notice 'Tere %',to_char(current_timestamp(3),'mi:ss.ms');
	for r in (select distinct cvo.id,cm.credits, cc.optional_study_credits
					  from curriculum_version cv
								 join curriculum_version_omodule cvo on cv.id=cvo.curriculum_version_id
								 join curriculum_module cm on cvo.curriculum_module_id=cm.id and cv.curriculum_id=cm.curriculum_id and coalesce(cm.is_additional,false)=false and cm.module_code!='KUTSEMOODUL_V'
								 join curriculum cc on cv.curriculum_id=cc.id
								 join student ss on cv.id=ss.curriculum_version_id
					 where ss.id=p_id)
	LOOP
		i:=i+1;
		p_curr_modules[i]:=r.id;
		p_curr_modules2[i]:=r.id;
		p_curr_modules_credits[i]:=r.credits;
		p_optional:=r.optional_study_credits;
	end loop;
  --õppuri positiivsed tulemused
  for r in (select coalesce(svm.curriculum_version_omodule_id,sv.curriculum_version_omodule_id) as curriculum_version_omodule_id, sv.grade, sv.credits, sv.arr_modules
						from student_vocational_result sv
								 left join student_vocational_result_omodule svm on sv.id=svm.student_vocational_result_id
						where sv.student_id=p_id and grade in ('A','3','4','5')
						order by sv.grade_date desc) 
  LOOP
		pb_exist:=false;
		if r.curriculum_version_omodule_id is not null then
			if array_length(p_study_modules,1) > 0 then
				for ii in 1..array_length(p_study_modules,1)
				LOOP
					if p_study_modules[ii]=r.curriculum_version_omodule_id THEN
						pb_exist:=true;
						exit;
					end if;
				end loop;
			end if;
			if not pb_exist THEN
				p_study_modules[case when p_study_modules is null then 0 else array_length(p_study_modules,1) end+1]:=r.curriculum_version_omodule_id;
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
						if p_curr_modules[ii]=r.curriculum_version_omodule_id THEN
							p_curr_modules2[ii]=0;
							p_total:=p_total+1;
							pb_exist:=true;
							exit;
						end if;
					end loop;
				elsif array_length(r.arr_modules,1) > 0 THEN
					for i in 1..array_length(r.arr_modules,1)
					LOOP
							for ii in 1..array_length(p_curr_modules,1)
							LOOP
								if p_curr_modules[ii]=r.arr_modules[i] THEN
									p_curr_modules2[ii]=0;
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
			end if;
		end loop;
	end if;

	--kokku võlg
  if p_opt_credits > p_optional THEN
		p_opt_credits:=0;
	ELSE
		p_opt_credits:=p_optional-p_opt_credits;
	end if;
	p_abs_credits:=p_abs_credits+p_opt_credits;
	
	p_abs_credits:=coalesce(p_abs_credits,0);
	
		
	update student_curriculum_completion 
	set study_backlog=-p_abs_credits, study_backlog_without_graduate=-p_abs_credits,
			average_mark=case when p_avg_total_credits > 0 then floor(p_avg_credits*100/p_avg_total_credits)/100 else 0 end, credits=p_total_credits, changed=current_timestamp(3)
	where student_id=p_id;
	GET DIAGNOSTICS a_count = ROW_COUNT;
	if a_count=0 THEN
		insert into student_curriculum_completion(student_id,study_backlog,study_backlog_without_graduate,average_mark,credits,inserted,changed)
		values(p_id,-p_abs_credits,-p_abs_credits,case when p_avg_total_credits > 0 then floor(p_avg_credits*100/p_avg_total_credits)/100 else 0 end,p_total_credits,current_timestamp(3),current_timestamp(3));
	end if;
	return 0;
	
	--raise notice 'Tere %, %, %, %', p_abs_credits, p_avg_credits, p_avg_total_credits,to_char(current_timestamp(3),'mi:ss.ms');
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

CREATE OR REPLACE FUNCTION public.hois_audit()
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

alter table diploma rename column is_cum_lade to is_cum_laude;



create trigger apel_application_formal_replaced_subject_or_module_audit after insert or delete or update on apel_application_formal_replaced_subject_or_module for each row execute procedure hois_audit();
create trigger apel_application_formal_subject_or_module_audit after insert or delete or update on apel_application_formal_subject_or_module for each row execute procedure hois_audit();
create trigger apel_school_audit after insert or delete or update on apel_school for each row execute procedure hois_audit();
create trigger diploma_audit after insert or delete or update on diploma for each row execute procedure hois_audit();
create trigger diploma_supplement_audit after insert or delete or update on diploma_supplement for each row execute procedure hois_audit();
create trigger diploma_supplement_form_audit after insert or delete or update on diploma_supplement_form for each row execute procedure hois_audit();
create trigger diploma_supplement_study_result_audit after insert or delete or update on diploma_supplement_study_result for each row execute procedure hois_audit();
create trigger directive_student_occupation_audit after insert or delete or update on directive_student_occupation for each row execute procedure hois_audit();
create trigger final_doc_signer_audit after insert or delete or update on final_doc_signer for each row execute procedure hois_audit();
create trigger final_thesis_audit after insert or delete or update on final_thesis for each row execute procedure hois_audit();
create trigger final_thesis_supervisor_audit after insert or delete or update on final_thesis_supervisor for each row execute procedure hois_audit();
create trigger form_audit after insert or delete or update on form for each row execute procedure hois_audit();
create trigger protocol_committee_member_audit after insert or delete or update on protocol_committee_member for each row execute procedure hois_audit();
create trigger protocol_hdata_audit after insert or delete or update on protocol_hdata for each row execute procedure hois_audit();
create trigger protocol_student_occupation_audit after insert or delete or update on protocol_student_occupation for each row execute procedure hois_audit();
create trigger protocol_vdata_audit after insert or delete or update on protocol_vdata for each row execute procedure hois_audit();
create trigger scholarship_application_audit after insert or delete or update on scholarship_application for each row execute procedure hois_audit();
create trigger scholarship_application_family_audit after insert or delete or update on scholarship_application_family for each row execute procedure hois_audit();
create trigger scholarship_application_file_audit after insert or delete or update on scholarship_application_file for each row execute procedure hois_audit();
create trigger scholarship_term_audit after insert or delete or update on scholarship_term for each row execute procedure hois_audit();
create trigger scholarship_term_course_audit after insert or delete or update on scholarship_term_course for each row execute procedure hois_audit();
create trigger scholarship_term_curriculum_audit after insert or delete or update on scholarship_term_curriculum for each row execute procedure hois_audit();
create trigger scholarship_term_study_form_audit after insert or delete or update on scholarship_term_study_form for each row execute procedure hois_audit();
create trigger scholarship_term_study_load_audit after insert or delete or update on scholarship_term_study_load for each row execute procedure hois_audit();
create trigger student_group_year_transfer_audit after insert or delete or update on student_group_year_transfer for each row execute procedure hois_audit();
create trigger student_group_year_transfer_log_audit after insert or delete or update on student_group_year_transfer_log for each row execute procedure hois_audit();
create trigger student_higher_result_module_audit after insert or delete or update on student_higher_result_module for each row execute procedure hois_audit();
create trigger student_occupation_certificate_audit after insert or delete or update on student_occupation_certificate for each row execute procedure hois_audit();
create trigger student_vocational_result_omodule_audit after insert or delete or update on student_vocational_result_omodule for each row execute procedure hois_audit();
create trigger study_material_audit after insert or delete or update on study_material for each row execute procedure hois_audit();
create trigger study_material_connect_audit after insert or delete or update on study_material_connect for each row execute procedure hois_audit();
create trigger subject_study_period_exam_audit after insert or delete or update on subject_study_period_exam for each row execute procedure hois_audit();
create trigger subject_study_period_exam_student_audit after insert or delete or update on subject_study_period_exam_student for each row execute procedure hois_audit();
create trigger teacher_absence_audit after insert or delete or update on teacher_absence for each row execute procedure hois_audit();
create trigger teacher_audit after insert or delete or update on teacher for each row execute procedure hois_audit();


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