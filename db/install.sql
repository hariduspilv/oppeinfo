\c hois;


CREATE TABLE "journal_room"
(
	"id" bigserial NOT NULL,
	"journal_id" bigint NOT NULL,    -- viide päevikule
	"room_id" bigint NOT NULL,    -- viide ruumile
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

COMMENT ON TABLE "journal_room"	IS 'päeviku planeerimisel soovitud ruumid';
COMMENT ON COLUMN "journal_room"."journal_id" IS 'viide päevikule';
COMMENT ON COLUMN "journal_room"."room_id"	IS 'viide ruumile';

ALTER TABLE "journal_room" ADD CONSTRAINT "PK_journal_room" PRIMARY KEY ("id");
CREATE INDEX "IXFK_journal_room_journal" ON "journal_room" ("journal_id" ASC) ;
CREATE INDEX "IXFK_journal_room_room" ON "journal_room" ("room_id" ASC) ;

ALTER TABLE "journal_room" ADD CONSTRAINT "FK_journal_room_journal" 	FOREIGN KEY ("journal_id") REFERENCES "public"."journal" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "journal_room" ADD CONSTRAINT "FK_journal_room_room" FOREIGN KEY ("room_id") REFERENCES "public"."room" ("id") ON DELETE No Action ON UPDATE No Action;

ALTER TABLE "student_absence" ADD COLUMN "accepted_by" varchar(100) COLLATE "default";
COMMENT ON COLUMN "student_absence"."accepted_by" IS 'tõendi aktsepteerija nimi';