\c hois

update classifier set extraval1='#FFECB3' where code='MAHT_a';
update classifier set extraval1='#F0F4C3' where code='MAHT_E';
update classifier set extraval1='#B2DFDB' where code='MAHT_i';
update classifier set extraval1='#C5CAE9' where code='MAHT_l';
update classifier set extraval1='#A5D6A7' where code='MAHT_p';
update classifier set extraval1='#FFCCBC' where code='MAHT_PR';
update classifier set extraval1='#B39DDB' where code='MAHT_S';

alter table school add column is_not_absence boolean;
comment on column school.is_not_absence is 'puudumistõendi esitamine ei ole lubatud';


CREATE TABLE "ws_photo_log"
(
	"id" bigserial NOT NULL ,
	"school_id" bigint NOT NULL,
	"student_id" bigint NULL,
	"directive_id" bigint NULL,
	"request" text NOT NULL,
	"response" text NULL,
	"is_multiple_request" boolean NOT NULL,
	"inserted" timestamp without time zone NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"version" integer NOT NULL,
	"changed" timestamp without time zone NULL,
	"changed_by" varchar(100)	 NULL,
	"log_txt" text NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "ws_photo_log"	IS 'Fotoboksi logi';
ALTER TABLE "ws_photo_log" ADD CONSTRAINT "PK_ws_photo_log"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_ws_photo_log_directive" ON "ws_photo_log" ("directive_id" ASC);
CREATE INDEX "IXFK_ws_photo_log_school" ON "ws_photo_log" ("school_id" ASC);
CREATE INDEX "IXFK_ws_photo_log_student" ON "ws_photo_log" ("student_id" ASC);

ALTER TABLE "ws_photo_log" ADD CONSTRAINT "FK_ws_photo_log_directive"	FOREIGN KEY ("directive_id") REFERENCES "public"."directive" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "ws_photo_log" ADD CONSTRAINT "FK_ws_photo_log_school"	FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "ws_photo_log" ADD CONSTRAINT "FK_ws_photo_log_student"	FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE No Action ON UPDATE No Action;

alter table school add column is_student_declaration_delete boolean not null default true;
comment on column school.is_student_declaration_delete is 'kas tudeng saab kohust. deklar kustutada';

alter table journal add column add_students boolean;
comment on column journal.add_students is 'kas saab automaatselt lisada tudengid päevikusse';


CREATE OR REPLACE FUNCTION public.ins_journal_trgr()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
declare
	p1 varchar(100):='';
	p2 varchar(100):='';
	p3 varchar(100):='';
BEGIN
  if coalesce(new.untis_code,'&?x') like '%_null' then
  	p1:=substr(new.untis_code,1,position('_null' in new.untis_code)-1);
  	p2:='_'||new.id::varchar;
  	p3:=case when length(p1)+length(p2) > 12 then substr(p1,1,12-length(p2)) else p1 end||p2;
		update journal set untis_code=p3 where id=new.id;
  end if;
  return null;
exception when others then
	return null;
end;
$function$
;

create trigger journal_ins_c_trg after insert on
public.journal for each row execute procedure ins_journal_trgr();