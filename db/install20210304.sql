\c hois

alter table student_support_service add is_visible boolean;
comment on column student_support_service.is_visible is 'kas osapooltele nähtav';
update student_support_service set is_visible=true;

alter table classifier add is_secondary boolean;
comment on column classifier.is_secondary is 'kas on üldharidusõppe klassifikaator';
update classifier set is_secondary=is_vocational;
alter table classifier alter column is_secondary set not null;

--üldharidusõppe
alter table state_curriculum add column is_vocational boolean;
update state_curriculum set is_vocational=true;
alter table state_curriculum alter column is_vocational set not null;
comment on column state_curriculum.is_vocational is 'kas on kutseõppekava RÕK';

alter table state_curriculum add column courses smallint;
comment on column state_curriculum.courses is 'minimaalne kursuste arv GRÕK puhul';

update classifier set is_secondary=false where main_class_code = 'EHIS_ROK';

INSERT INTO public.classifier
(code, value, value2, name_et, name_en, name_ru, parent_class_code, main_class_code, inserted, changed, "valid", description, valid_from, valid_thru, extraval1, extraval2, ehis_value, is_vocational, is_higher, "version", inserted_by, changed_by, is_secondary)
VALUES('EHIS_ROK_PROK', 'PROK', NULL, 'PRÕK', NULL, NULL, NULL, 'EHIS_ROK', current_timestamp(3), NULL, true, NULL, NULL, NULL, NULL, NULL, NULL, false, false, 0, NULL, NULL, true);
INSERT INTO public.classifier
(code, value, value2, name_et, name_en, name_ru, parent_class_code, main_class_code, inserted, changed, "valid", description, valid_from, valid_thru, extraval1, extraval2, ehis_value, is_vocational, is_higher, "version", inserted_by, changed_by, is_secondary)
VALUES('EHIS_ROK_GROK', 'GROK', NULL, 'GRÕK', NULL, NULL, NULL, 'EHIS_ROK', current_timestamp(3), NULL, true, NULL, NULL, NULL, NULL, NULL, NULL, false, false, 0, NULL, NULL, true);

INSERT INTO public.classifier
(code, value, value2, name_et, name_en, name_ru, parent_class_code, main_class_code, inserted, changed, "valid", description, valid_from, valid_thru, extraval1, extraval2, ehis_value, is_vocational, is_higher, "version", inserted_by, changed_by,is_secondary)
VALUES('EHIS_AINE', 'EHIS_AINE', NULL, 'EHISe ained', NULL, NULL, NULL, NULL, current_timestamp(3), NULL, true, NULL, NULL, NULL, NULL, NULL, NULL, false, false, 0, NULL, NULL,true);

INSERT INTO public.classifier
(code, value, value2, name_et, name_en, name_ru, parent_class_code, main_class_code, inserted, changed, "valid", description, valid_from, valid_thru, extraval1, extraval2, ehis_value, is_vocational, is_higher, "version", inserted_by, changed_by,is_secondary)
VALUES('AINEVALDKOND', 'AINEVALDKOND', NULL, 'Ainevaldkond', NULL, NULL, NULL, NULL, current_timestamp(3), NULL, true, NULL, NULL, NULL, NULL, NULL, NULL, false, false, 0, NULL, NULL,true);


INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('AINEVALDKOND_KEEL','KEEL', 'Keel ja kirjandus',  'AINEVALDKOND', current_timestamp(3),  true,  null, false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('AINEVALDKOND_VOORKEEL','VOORKEEL', 'Võõrkeeled',  'AINEVALDKOND', current_timestamp(3),  true,  null, false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('AINEVALDKOND_MATEMAATIKA','MATEMAATIKA', 'Matemaatika',  'AINEVALDKOND', current_timestamp(3),  true,  null, false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('AINEVALDKOND_LOODUS','LOODUS', 'Loodusained',  'AINEVALDKOND', current_timestamp(3),  true,  null, false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('AINEVALDKOND_SOTS','SOTS', 'Sotsiaalained',  'AINEVALDKOND', current_timestamp(3),  true,  null, false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('AINEVALDKOND_KUNST','KUNST', 'Kunstiained',  'AINEVALDKOND', current_timestamp(3),  true,  null, false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('AINEVALDKOND_TEHNO','TEHNO', 'Tehnoloogia',  'AINEVALDKOND', current_timestamp(3),  true,  null, false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('AINEVALDKOND_KEHA','KEHA', 'Kehaline kasvatus',  'AINEVALDKOND', current_timestamp(3),  true,  null, false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('AINEVALDKOND_MUU','MUU', 'Muu',  'AINEVALDKOND', current_timestamp(3),  true,  null, false, false, 0, true);

INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17256','17256', '3d-modelleerimine',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_A','A', 'Ajalugu',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17182','17182', 'Ajalugu ja kodanikuõpetus',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17241','17241', 'Arvuteooria elemendid I',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17242','17242', 'Arvuteooria elemendid II',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17258','17258', 'Arvuti kasutamine uurimistöös',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17169','17169', 'A-võõrkeel',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17200','17200', 'B1-keeleoskustaseme kursus',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17201','17201', 'B2-keeleoskustaseme kursus',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_B','B', 'Bioloogia',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17170','17170', 'B-võõrkeel',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17243','17243', 'Diskreetse matemaatika elemendid I',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17244','17244', 'Diskreetse matemaatika elemendid II',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17233','17233', 'Draama ja teater',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_E','E', 'Eesti keel',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_R','R', 'Eesti keel  teise keelena',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17161','17161', 'Eesti keel ja kirjandus',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17269','17269', 'Eesti usuline maastik',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17163','17163', 'Eesti viipekeel',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17250','17250', 'Elementide keemia',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17191','17191', 'Elu- ja olustikuõpe',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17251','17251', 'Elu keemia',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17162','17162', 'Emakeel',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17273','17273', 'Ettevõtlusõpetus',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_F','F', 'Füüsika',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17252','17252', 'Füüsika ja tehnika',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_G','G', 'Geograafia',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17198','17198', 'Geograafia (inimgeograafia)',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17199','17199', 'Geograafia (loodusgeograafia)',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17248','17248', 'Geoinformaatika',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17263','17263', 'Globaliseeruv maailm',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_zh','zh', 'hiina keel',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_es','es', 'hispaania keel',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17277','17277', 'Informaatika',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17210','17210', 'Inglise keel',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17268','17268', 'Inimene ja religioon',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17262','17262', 'Inimene ja õigus',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_io','io', 'Inimeseõpetus',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_it','it', 'itaalia keel',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17257','17257', 'Joonestamine',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17274','17274', 'Karjääriõpetus',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_K','K', 'Keemia',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17179','17179', 'Keemia ja füüsika elementaarkursus',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17249','17249', 'Keemiliste protsesside seaduspärasused',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_kehk','kehk', 'Kehaline kasvatus',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17205','17205', 'Kehaline kasvatus ja rütmika',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17266','17266', 'Kehalised võimed ja liikumisoskused',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_ki','ki', 'Kirjandus',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17234','17234', 'Kirjandus ja film',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17232','17232', 'Kirjandus ja ühiskond',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17197','17197', 'Kitsas matemaatika',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17342','17342', 'Klassiõpetaja',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17206','17206', 'Kodulugu, loodusõpetus',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_knst','knst', 'Kunst',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17187','17187', 'Kunstiõpetus',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17230','17230', 'Kõne ja väitlus',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_kodu','kodu', 'Käsitöö ja kodundus',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_la','la', 'ladina keel',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17196','17196', 'Lai matemaatika',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17267','17267', 'Liikumine välistingimustes',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17254','17254', 'Loodusteadused, tehnoloogia ja ühiskond',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_lo','lo', 'Loodusõpetus',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17239','17239', 'Loogika',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_lv','lv', 'läti keel',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17236','17236', 'Maailmakirjandus antiikajast 18. sajandini',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17240','17240', 'Majandusmatemaatika elemendid',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17272','17272', 'Majandusõpetus',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_M','M', 'Matemaatika',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17255','17255', 'Mehhatroonika ja robootika',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_mu','mu', 'Muusika',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17184','17184', 'Muusikaõpetus',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17231','17231', 'Müüt ja kirjandus',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17208','17208', 'Perekonnaõpetus',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17245','17245', 'Planimeetria I. Kolmnurkade ja ringide geomeetria',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17246','17246', 'Planimeetria II. Hulknurkade ja ringide geomeetria',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17271','17271', 'Praktiline õpe välilaagris',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17211','17211', 'Prantsuse keel',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17209','17209', 'Psühholoogia',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17247','17247', 'Rakendusbioloogia',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17259','17259', 'Rakenduste loomise ja programmeerimise alused',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17270','17270', 'Riigikaitse',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_sw','sw', 'rootsi keel',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17212','17212', 'Saksa keel',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17264','17264', 'Sissejuhatus filosoofilisse mõtlemisse',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_S','S', 'soome keel',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_tehn','tehn', 'Tehnoloogiaõpetus',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17253','17253', 'Teistsugune füüsika',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17265','17265', 'Tänapäeva filosoofilised küsimused',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17237','17237', 'Tänapäeva vene kirjandus',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17238','17238', 'Tänapäeva väliskirjandus',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_to','to', 'Tööõpetus',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17207','17207', 'Tööõpetus/käsitöö',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17276','17276', 'Usundiõpetus',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17275','17275', 'Uurimistöö alused',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_W','W', 'Vene keel',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17235','17235', 'Vene keel Eestis',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17165','17165', 'Vene keel ja kirjandus',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17164','17164', 'Vene viipekeel',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17171','17171', 'Võõrkeel',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17345','17345', 'Õpiabirühm',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_C','C', 'Ühiskonnaõpetus',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17260','17260', 'Üldajalugu – maailma ajalugu: tsivilisatsioonid väljaspool Euroopat',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('EHIS_AINE_17261','17261', 'Üldajalugu – Euroopa maade ja Ameerika Ühendriikide ajalugu',  'EHIS_AINE', current_timestamp(3),  true,  ';B5;', false, false, 0, true);

update classifier set ehis_value=value where main_class_code='EHIS_AINE';


INSERT INTO public.classifier_connect (classifier_code, connect_classifier_code, inserted, changed, "version", main_classifier_code, inserted_by, changed_by) VALUES('EHIS_AINE_E', 'AINEVALDKOND_KEEL', current_timestamp(3), NULL, 0, 'AINEVALDKOND', 'Skript', NULL);
INSERT INTO public.classifier_connect (classifier_code, connect_classifier_code, inserted, changed, "version", main_classifier_code, inserted_by, changed_by) VALUES('EHIS_AINE_ki', 'AINEVALDKOND_KEEL', current_timestamp(3), NULL, 0, 'AINEVALDKOND', 'Skript', NULL);
INSERT INTO public.classifier_connect (classifier_code, connect_classifier_code, inserted, changed, "version", main_classifier_code, inserted_by, changed_by) VALUES('EHIS_AINE_W', 'AINEVALDKOND_KEEL', current_timestamp(3), NULL, 0, 'AINEVALDKOND', 'Skript', NULL);

INSERT INTO public.classifier_connect (classifier_code, connect_classifier_code, inserted, changed, "version", main_classifier_code, inserted_by, changed_by) VALUES('EHIS_AINE_17169', 'AINEVALDKOND_VOORKEEL', current_timestamp(3), NULL, 0, 'AINEVALDKOND', 'Skript', NULL);
INSERT INTO public.classifier_connect (classifier_code, connect_classifier_code, inserted, changed, "version", main_classifier_code, inserted_by, changed_by) VALUES('EHIS_AINE_17170', 'AINEVALDKOND_VOORKEEL', current_timestamp(3), NULL, 0, 'AINEVALDKOND', 'Skript', NULL);
INSERT INTO public.classifier_connect (classifier_code, connect_classifier_code, inserted, changed, "version", main_classifier_code, inserted_by, changed_by) VALUES('EHIS_AINE_R', 'AINEVALDKOND_VOORKEEL', current_timestamp(3), NULL, 0, 'AINEVALDKOND', 'Skript', NULL);

INSERT INTO public.classifier_connect (classifier_code, connect_classifier_code, inserted, changed, "version", main_classifier_code, inserted_by, changed_by) VALUES('EHIS_AINE_M', 'AINEVALDKOND_MATEMAATIKA', current_timestamp(3), NULL, 0, 'AINEVALDKOND', 'Skript', NULL);

INSERT INTO public.classifier_connect (classifier_code, connect_classifier_code, inserted, changed, "version", main_classifier_code, inserted_by, changed_by) VALUES('EHIS_AINE_lo', 'AINEVALDKOND_LOODUS', current_timestamp(3), NULL, 0, 'AINEVALDKOND', 'Skript', NULL);
INSERT INTO public.classifier_connect (classifier_code, connect_classifier_code, inserted, changed, "version", main_classifier_code, inserted_by, changed_by) VALUES('EHIS_AINE_B', 'AINEVALDKOND_LOODUS', current_timestamp(3), NULL, 0, 'AINEVALDKOND', 'Skript', NULL);
INSERT INTO public.classifier_connect (classifier_code, connect_classifier_code, inserted, changed, "version", main_classifier_code, inserted_by, changed_by) VALUES('EHIS_AINE_G', 'AINEVALDKOND_LOODUS', current_timestamp(3), NULL, 0, 'AINEVALDKOND', 'Skript', NULL);
INSERT INTO public.classifier_connect (classifier_code, connect_classifier_code, inserted, changed, "version", main_classifier_code, inserted_by, changed_by) VALUES('EHIS_AINE_F', 'AINEVALDKOND_LOODUS', current_timestamp(3), NULL, 0, 'AINEVALDKOND', 'Skript', NULL);
INSERT INTO public.classifier_connect (classifier_code, connect_classifier_code, inserted, changed, "version", main_classifier_code, inserted_by, changed_by) VALUES('EHIS_AINE_K', 'AINEVALDKOND_LOODUS', current_timestamp(3), NULL, 0, 'AINEVALDKOND', 'Skript', NULL);

INSERT INTO public.classifier_connect (classifier_code, connect_classifier_code, inserted, changed, "version", main_classifier_code, inserted_by, changed_by) VALUES('EHIS_AINE_io', 'AINEVALDKOND_SOTS', current_timestamp(3), NULL, 0, 'AINEVALDKOND', 'Skript', NULL);
INSERT INTO public.classifier_connect (classifier_code, connect_classifier_code, inserted, changed, "version", main_classifier_code, inserted_by, changed_by) VALUES('EHIS_AINE_A', 'AINEVALDKOND_SOTS', current_timestamp(3), NULL, 0, 'AINEVALDKOND', 'Skript', NULL);
INSERT INTO public.classifier_connect (classifier_code, connect_classifier_code, inserted, changed, "version", main_classifier_code, inserted_by, changed_by) VALUES('EHIS_AINE_C', 'AINEVALDKOND_SOTS', current_timestamp(3), NULL, 0, 'AINEVALDKOND', 'Skript', NULL);

INSERT INTO public.classifier_connect (classifier_code, connect_classifier_code, inserted, changed, "version", main_classifier_code, inserted_by, changed_by) VALUES('EHIS_AINE_mu', 'AINEVALDKOND_KUNST', current_timestamp(3), NULL, 0, 'AINEVALDKOND', 'Skript', NULL);
INSERT INTO public.classifier_connect (classifier_code, connect_classifier_code, inserted, changed, "version", main_classifier_code, inserted_by, changed_by) VALUES('EHIS_AINE_knst', 'AINEVALDKOND_KUNST', current_timestamp(3), NULL, 0, 'AINEVALDKOND', 'Skript', NULL);

INSERT INTO public.classifier_connect (classifier_code, connect_classifier_code, inserted, changed, "version", main_classifier_code, inserted_by, changed_by) VALUES('EHIS_AINE_to', 'AINEVALDKOND_TEHNO', current_timestamp(3), NULL, 0, 'AINEVALDKOND', 'Skript', NULL);
INSERT INTO public.classifier_connect (classifier_code, connect_classifier_code, inserted, changed, "version", main_classifier_code, inserted_by, changed_by) VALUES('EHIS_AINE_kodu', 'AINEVALDKOND_TEHNO', current_timestamp(3), NULL, 0, 'AINEVALDKOND', 'Skript', NULL);
INSERT INTO public.classifier_connect (classifier_code, connect_classifier_code, inserted, changed, "version", main_classifier_code, inserted_by, changed_by) VALUES('EHIS_AINE_tehn', 'AINEVALDKOND_TEHNO', current_timestamp(3), NULL, 0, 'AINEVALDKOND', 'Skript', NULL);

INSERT INTO public.classifier_connect (classifier_code, connect_classifier_code, inserted, changed, "version", main_classifier_code, inserted_by, changed_by) VALUES('EHIS_AINE_kehk', 'AINEVALDKOND_KEHA', current_timestamp(3), NULL, 0, 'AINEVALDKOND', 'Skript', NULL);

INSERT INTO public.classifier_connect (classifier_code, connect_classifier_code, inserted, changed, "version", main_classifier_code, inserted_by, changed_by) VALUES('EHIS_AINE_17276', 'AINEVALDKOND_MUU', current_timestamp(3), NULL, 0, 'AINEVALDKOND', 'Skript', NULL);
INSERT INTO public.classifier_connect (classifier_code, connect_classifier_code, inserted, changed, "version", main_classifier_code, inserted_by, changed_by) VALUES('EHIS_AINE_17277', 'AINEVALDKOND_MUU', current_timestamp(3), NULL, 0, 'AINEVALDKOND', 'Skript', NULL);
INSERT INTO public.classifier_connect (classifier_code, connect_classifier_code, inserted, changed, "version", main_classifier_code, inserted_by, changed_by) VALUES('EHIS_AINE_17274', 'AINEVALDKOND_MUU', current_timestamp(3), NULL, 0, 'AINEVALDKOND', 'Skript', NULL);
INSERT INTO public.classifier_connect (classifier_code, connect_classifier_code, inserted, changed, "version", main_classifier_code, inserted_by, changed_by) VALUES('EHIS_AINE_17273', 'AINEVALDKOND_MUU', current_timestamp(3), NULL, 0, 'AINEVALDKOND', 'Skript', NULL);



alter TABLE "public"."state_curriculum_module"
add column "courses_or_weeks" smallint NULL,    -- kursuste või nädala tundide arv
add column 	"riigiteataja_url" varchar(4000)	 NULL    -- link ainekavale
;

CREATE TABLE "state_curriculum_module_competence"
(
	"id" bigserial NOT NULL,
	"state_curriculum_module_id" bigint NOT NULL,
	"competence_code" varchar(100)	 NOT NULL,    -- viide klassifikaatorile PADEVUS
	"description" text NULL,
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp with time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

COMMENT ON COLUMN "public"."state_curriculum_module"."courses_or_weeks"	IS 'kursuste või nädala tundide arv';
COMMENT ON COLUMN "public"."state_curriculum_module"."riigiteataja_url"	IS 'link ainekavale';

COMMENT ON TABLE "state_curriculum_module_competence"	IS 'ainete pädevused';
COMMENT ON COLUMN "state_curriculum_module_competence"."competence_code"	IS 'viide klassifikaatorile PADEVUS';

ALTER TABLE "state_curriculum_module_competence" ADD CONSTRAINT "PK_state_curriculum_module_competence"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_state_curriculum_module_competence_classifier" ON "state_curriculum_module_competence" ("competence_code" ASC);
CREATE INDEX "IXFK_state_curriculum_module_competence_state_curriculum_module" ON "state_curriculum_module_competence" ("state_curriculum_module_id" ASC);

ALTER TABLE "state_curriculum_module_competence" ADD CONSTRAINT "FK_state_curriculum_module_competence_classifier"	FOREIGN KEY ("competence_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "state_curriculum_module_competence" ADD CONSTRAINT "FK_state_curriculum_module_competence_state_curriculum_module"	FOREIGN KEY ("state_curriculum_module_id") REFERENCES "public"."state_curriculum_module" ("id") ON DELETE No Action ON UPDATE No Action;


INSERT INTO public.classifier
(code, value, value2, name_et, name_en, name_ru, parent_class_code, main_class_code, inserted, changed, "valid", description, valid_from, valid_thru, extraval1, extraval2, ehis_value, is_vocational, is_higher, "version", inserted_by, changed_by,is_secondary)
VALUES('PADEVUS', 'PADEVUS', NULL, 'Pädevus', NULL, NULL, NULL, NULL, current_timestamp(3), NULL, true, NULL, NULL, NULL, NULL, NULL, NULL, false, false, 0, NULL, NULL,true);

INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('PADEVUS_1','1', 'kultuuri- ja väärtuspädevus',  'PADEVUS', current_timestamp(3),  true,  null, false, false, 0, true);
INSERT INTO classifier (code, value, name_et,  main_class_code, inserted, valid, ehis_value, is_vocational, is_higher, version,  is_secondary)VALUES('PADEVUS_2','2', 'sotsiaalne ja kodanikupädevus',  'PADEVUS', current_timestamp(3),  true,  null, false, false, 0, true);



alter TABLE "public"."ws_ehis_teacher_log" add column	"ws_ehis_teacher_meta_log_id" bigint NULL;

CREATE TABLE "ws_ehis_teacher_meta_log"
(
	"id" bigserial NOT NULL,
	"school_id" bigint NOT NULL,
	"valid_from" date NOT NULL,
	"valid_thru" date NOT NULL,
	"inserted" timestamp without time zone NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL
)
;

alter TABLE  "public"."teacher" add column "ehis_sent_dt" timestamp without time zone NULL    -- viimane EHISesse satamise kp
;

COMMENT ON TABLE "ws_ehis_teacher_meta_log"	IS 'kutseõptejatajate EHISesse metaandmete saatmise logi';
COMMENT ON COLUMN "public"."teacher"."ehis_sent_dt"	IS 'viimane EHISesse satamise kp';

CREATE INDEX "IXFK_ws_ehis_teacher_log_ws_ehis_teacher_meta_log" ON "public"."ws_ehis_teacher_log" ("ws_ehis_teacher_meta_log_id" ASC);
ALTER TABLE "ws_ehis_teacher_meta_log" ADD CONSTRAINT "PK_ws_ehis_teacher_meta_log"	PRIMARY KEY ("id");

CREATE INDEX "IXFK_ws_ehis_teacher_meta_log_school" ON "ws_ehis_teacher_meta_log" ("school_id" ASC);

/* Create Foreign Key Constraints */

ALTER TABLE "public"."ws_ehis_teacher_log" ADD CONSTRAINT "FK_ws_ehis_teacher_log_ws_ehis_teacher_meta_log"
	FOREIGN KEY ("ws_ehis_teacher_meta_log_id") REFERENCES "ws_ehis_teacher_meta_log" ("id") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "ws_ehis_teacher_meta_log" ADD CONSTRAINT "FK_ws_ehis_teacher_meta_log_school"
	FOREIGN KEY ("school_id") REFERENCES "public"."school" ("id") ON DELETE No Action ON UPDATE No Action
;

with s as (select teacher_id, max(inserted) as inserted
from ws_ehis_teacher_log
group by teacher_id)
update teacher t
 set ehis_sent_dt=s.inserted
from s
where s.teacher_id=t.id;

CREATE TABLE "student_vocational_omodule_theme"
(
	"id" bigserial NOT NULL,
	"student_id" bigint NOT NULL,
	"curriculum_version_omodule_id" bigint NOT NULL,
	"old_curriculum_version_omodule_id" bigint NOT NULL,
	"curriculum_version_omodule_theme_id" bigint NULL,
	"old_curriculumid_version_omodule_theme_id" bigint NULL,
	"inserted" timestamp without time zone NOT NULL,
	"journal_id" bigint NULL,
	"changed" timestamp without time zone NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL,
	"version" integer NOT NULL,
	"practice_journal_id" bigint NULL
)
;

ALTER TABLE "student_vocational_omodule_theme" ADD CONSTRAINT "PK_student_vocational_omodule_theme"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_student_vocational_omodule_theme_curriculum_version_omodule" ON "student_vocational_omodule_theme" ("curriculum_version_omodule_id" ASC);
CREATE INDEX "IXFK_student_vocational_version_omodule_02" ON "student_vocational_omodule_theme" ("old_curriculum_version_omodule_id" ASC);
CREATE INDEX "IXFK_student_vocational_version_omodule_theme_02" ON "student_vocational_omodule_theme" ("curriculum_version_omodule_theme_id" ASC);
CREATE INDEX "IXFK_student_vocational_version_omodule_theme_03" ON "student_vocational_omodule_theme" ("old_curriculumid_version_omodule_theme_id" ASC);
CREATE INDEX "IXFK_student_vocational_omodule_theme_journal" ON "student_vocational_omodule_theme" ("journal_id" ASC);
CREATE INDEX "IXFK_student_vocational_omodule_theme_practice_journal" ON "student_vocational_omodule_theme" ("practice_journal_id" ASC);
CREATE INDEX "IXFK_student_vocational_omodule_theme_student" ON "student_vocational_omodule_theme" ("student_id" ASC);

ALTER TABLE "student_vocational_omodule_theme" ADD CONSTRAINT "FK_student_vocational_omodule_theme_curriculum_version_omodule"	FOREIGN KEY ("curriculum_version_omodule_id") REFERENCES "public"."curriculum_version_omodule" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "student_vocational_omodule_theme" ADD CONSTRAINT "FK_student_vocational_omodule_theme_curriculum_version_omodule_02"	FOREIGN KEY ("old_curriculum_version_omodule_id") REFERENCES "public"."curriculum_version_omodule" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "student_vocational_omodule_theme" ADD CONSTRAINT "FK_student_vocational_curriculum_version_omodule_theme_02"	FOREIGN KEY ("curriculum_version_omodule_theme_id") REFERENCES "public"."curriculum_version_omodule_theme" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "student_vocational_omodule_theme" ADD CONSTRAINT "FK_student_vocational_curriculum_version_omodule_theme_03"	FOREIGN KEY ("old_curriculumid_version_omodule_theme_id") REFERENCES "public"."curriculum_version_omodule_theme" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "student_vocational_omodule_theme" ADD CONSTRAINT "FK_student_vocational_journal"	FOREIGN KEY ("journal_id") REFERENCES "public"."journal" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "student_vocational_omodule_theme" ADD CONSTRAINT "FK_student_vocational_practice_journal"	FOREIGN KEY ("practice_journal_id") REFERENCES "public"."practice_journal" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "student_vocational_omodule_theme" ADD CONSTRAINT "FK_student_vocational_student"	FOREIGN KEY ("student_id") REFERENCES "public"."student" ("id") ON DELETE No Action ON UPDATE No Action;


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
											case when ph.final_subject_id is not null then (select string_agg(coalesce(pers.firstname,fts.firstname )||' '||coalesce(pers.lastname,fts.lastname ),', ' order by fts.is_primary desc) from final_thesis ft
											join final_thesis_supervisor fts on ft.id=final_thesis_id 
											left join teacher ttt on fts.teacher_id =ttt.id 
											left join person pers on ttt.person_id =pers.id
											where ft.student_id=st.id )
											when ppp.id is not null then ppp.firstname||' '||ppp.lastname else (select string_agg(pers.firstname||' '||pers.lastname,', ') 
												from subject_study_period_teacher st join teacher tt on st.teacher_id=tt.id join person pers on tt.person_id=pers.id
													where st.subject_study_period_id=sp.id and coalesce(st.is_diploma_supplement,true)=true) end as teachers, --teachers
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
				subject_name_et=coalesce(r.name_et,'-'),
				subject_name_en=coalesce(r.name_en,r.name_et),
				teachers=substr(r.teachers,1,255),
				credits=r.credits,
				subject_code=r.code,
				curriculum_version_hmodule_id=r.curriculum_version_hmodule_id,
				is_optional=coalesce(r.is_optional,false),
				--is_optional=false,
				subject_id=r.subject_id,
				changed=now(),
				study_period_id=coalesce(r.study_period_id, get_study_period(NEW.grade_date::date, r.school_id::int)),
				is_module=case when coalesce(r.cvh_id,0) > 0 then true else false end,
			  grading_schema_row_id=new.grading_schema_row_id
			where protocol_student_id=NEW.id;
			GET DIAGNOSTICS u_count = ROW_COUNT;

			if coalesce(u_count,0)=0 THEN
				insert into student_higher_result (
					student_id,			subject_id,				protocol_student_id,				grade,				grade_date,				grade_mark,				grade_code,
					apel_application_record_id,				apel_school_id,				inserted, curriculum_version_hmodule_id,is_optional,
					subject_name_et,				subject_name_en,				teachers,				credits,				subject_code,				study_period_id, is_module,
			  grading_schema_row_id) 
				values(
					NEW.student_id,r.subject_id,NEW.id,coalesce(NEW.grade,r.grade),NEW.grade_date,
					coalesce(NEW.grade_mark,case r.grade when '0' then 0 when '1' then 1 when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else null end),				NEW.grade_code,
					null,--apel_application_record_id,
					null,--apel_school_id
					now(),r.curriculum_version_hmodule_id,coalesce(r.is_optional,false),
					coalesce(r.name_et,'-'),coalesce(r.name_en,r.name_et),substr(r.teachers,1,255),	r.credits,r.code,				coalesce(r.study_period_id, get_study_period(NEW.grade_date::date, r.school_id::int)),case when coalesce(r.cvh_id,0) > 0 then true else false end,
         new.grading_schema_row_id); --is_optional
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
				select distinct first_value(sr.id)over(partition by sr.curriculum_version_hmodule_id order by case when coalesce(sr.apel_application_record_id,0)=0 then 1 else 0 end, sr.grade_date desc nulls last,ph.type_code asc,ph.inserted desc) into p_id 
				from student_higher_result sr
						 left join protocol_student ps on sr.protocol_student_id=ps.id
						 left join protocol_hdata ph on ps.protocol_id=ph.protocol_id
				where sr.student_id=case when tg_op='DELETE' then old.student_id else NEW.student_id end and sr.curriculum_version_hmodule_id=r.cvh_id and sr.is_module=true;
				update student_higher_result set is_active=false where student_id=st_id and curriculum_version_hmodule_id=r.cvh_id and is_module=true and id!=p_id;
			end if;

			/*--kustutame üleliigset eelmist rida
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
				teachers=substr(r.teachers,1,255),	
				changed=now(), 
				module_name_et=r.name_et,	
				module_name_en=r.name_en,
				study_year_id=r.study_year_id,
			  grading_schema_row_id=new.grading_schema_row_id
			where protocol_student_id=NEW.id;
			GET DIAGNOSTICS u_count = ROW_COUNT;

			if coalesce(u_count,0)=0 THEN
				insert into student_vocational_result (
					student_id,			curriculum_version_omodule_id,				protocol_student_id,		grade,				grade_date,				grade_mark,				grade_code,
					credits,	teachers,	inserted, module_name_et,	module_name_en,study_year_id,grading_schema_row_id)
				values(NEW.student_id,r.curriculum_version_omodule_id,NEW.id,		coalesce(NEW.grade,r.grade),	NEW.grade_date,	
							coalesce(NEW.grade_mark,case r.grade when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else null end),	NEW.grade_code,
					r.credits,	r.teachers,	now(), r.name_et,	r.name_en, r.study_year_id,NEW.grading_schema_row_id);
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
$function$
;


alter TABLE "public"."student_group" add column "curriculum_address_id" bigint NULL    -- viide aadressile
;

COMMENT ON COLUMN "public"."student_group"."curriculum_address_id"	IS 'viide aadressile';

CREATE INDEX "IXFK_student_group_curriculum_address" ON "public"."student_group" ("curriculum_address_id" ASC);

ALTER TABLE "public"."student_group" ADD CONSTRAINT "FK_student_group_curriculum_address" FOREIGN KEY ("curriculum_address_id") REFERENCES "public"."curriculum_address" ("id") ON DELETE No Action ON UPDATE No Action;

alter TABLE  "public"."teacher" rename column "ehis_sent_dt" to ehis_sent_vdt;
alter TABLE  "public"."teacher" add column "ehis_sent_hdt" timestamp without time zone NULL;
COMMENT ON COLUMN "public"."teacher"."ehis_sent_vdt"	IS 'viimane EHISesse satamise kp (kutse)';
COMMENT ON COLUMN "public"."teacher"."ehis_sent_hdt"	IS 'viimane EHISesse satamise kp (kõrg)';

with s as (select teacher_id, max(inserted) as inserted, ws_name
from ws_ehis_teacher_log
group by teacher_id,  ws_name order by 1,3 desc)
update teacher t
 set ehis_sent_vdt=case when s.ws_name like '%laePedagoogid%' then s.inserted else ehis_sent_vdt end,
		 ehis_sent_hdt=case when s.ws_name like '%laeOppejoud%' then s.inserted else ehis_sent_hdt end
from s
where s.teacher_id=t.id;

alter table journal_entry add is_test boolean;
comment on column journal_entry.is_test is 'kas tegemist on kontrolltööga ehk kas tuleb õppijat teavitada';

update journal_entry set is_test = true where entry_type_code in ('SISSEKANNE_H', 'SISSEKANNE_L', 'SISSEKANNE_E', 'SISSEKANNE_I', 'SISSEKANNE_P', 'SISSEKANNE_R');

alter table school_capacity_type
add column is_vocational boolean,
add column is_basic boolean,
add column is_secondary boolean;

update school_capacity_type set is_vocational=false where is_higher=true;
update school_capacity_type set is_vocational=true where is_higher=false;
update school_capacity_type set is_basic=false, is_secondary=false;

alter table school_capacity_type alter column is_vocational set not null;
alter table school_capacity_type alter column is_basic set not null;
alter table school_capacity_type alter column is_secondary set not null;

comment on column school_capacity_type.is_vocational is 'kas on kutse seadistus';
comment on column school_capacity_type.is_basic is 'kas on põhi seadistus';
comment on column school_capacity_type.is_secondary is 'kas on kesk seadistus';


alter TABLE "public"."student" add column	"speciality_code" varchar(100)	 NULL;
alter TABLE "public"."student_history" add column	"speciality_code" varchar(100)	 NULL;

alter TABLE "public"."student" add column	"curriculum_study_field_id" bigint	 NULL;
alter TABLE "public"."student_history" add column	"curriculum_study_field_id" bigint	 NULL;

CREATE INDEX "IXFK_student_curriculum_study_field" ON "public"."student" ("curriculum_study_field_id" ASC);
CREATE INDEX "curriculum_study_field_id" ON "public"."student_history" ("curriculum_study_field_id" ASC);

CREATE INDEX "IXFK_student_classifier_13" ON "public"."student" ("speciality_code" ASC);
CREATE INDEX "IXFK_student_history_classifier_11" ON "public"."student_history" ("speciality_code" ASC);

ALTER TABLE "public"."student" ADD CONSTRAINT "FK_student_classifier_12"	FOREIGN KEY ("speciality_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "public"."student_history" ADD CONSTRAINT "FK_student_history_classifier_11" FOREIGN KEY ("speciality_code") REFERENCES "public"."classifier" ("code") ON DELETE No Action ON UPDATE No Action;



with s as (select s.id,sg.speciality_code 
from student s 
	 join student_group sg on s.student_group_id = sg.id
where sg.speciality_code is not null)
update student st
	set speciality_code=s.speciality_code
from s
where s.id=st.id;

with s as (select s.id,sg.speciality_code 
from student s 
	 join student_group sg on s.student_group_id = sg.id
where sg.speciality_code is not null)
update student_history st
	set speciality_code=s.speciality_code
from s
where s.id=st.student_id;

ALTER TABLE public.student_vocational_omodule_theme RENAME COLUMN old_curriculumid_version_omodule_theme_id TO old_curriculum_version_omodule_theme_id;

alter table teacher add is_secondary boolean;
comment on column teacher.is_secondary is 'kas on üldharidusõppe õpetaja';

alter table curriculum
add column is_vocational boolean,
add column is_basic boolean,
add column is_secondary boolean;

update curriculum set is_vocational=false where is_higher=true;
update curriculum set is_vocational=true where is_higher=false;
update curriculum set is_basic=false, is_secondary=false;

alter table curriculum alter column is_vocational set not null;
alter table curriculum alter column is_basic set not null;
alter table curriculum alter column is_secondary set not null;

comment on column curriculum.is_vocational is 'kas on kutse õppekava';
comment on column curriculum.is_basic is 'kas on põhi õppekava';
comment on column curriculum.is_secondary is 'kas on kesk õppekava';


/* Create Tables */

alter TABLE "public"."curriculum"
add column	"courses" smallint NULL,    -- minimaalne kursuste arv GRÕK puhul
add column	"grading_system_description" text NULL    -- hindamissüsteemi kirjeldus
;

CREATE TABLE "curriculum_study_field"
(
	"id" bigserial NOT NULL,
	"curriculum_id" bigint NOT NULL,
	"name_et" varchar(1000)	 NOT NULL,
	"name_en" varchar(1000)	 NULL,
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

ALTER TABLE "curriculum_study_field" ADD CONSTRAINT "PK_curriculum_study_field"	PRIMARY KEY ("id");

ALTER TABLE "public"."student" ADD CONSTRAINT "FK_student_curriculum_study_field"	FOREIGN KEY ("curriculum_study_field_id") REFERENCES "public"."curriculum_study_field" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "public"."student_history" ADD CONSTRAINT "FK_student_history_curriculum_study_field" FOREIGN KEY ("curriculum_study_field_id") REFERENCES "public"."curriculum_study_field" ("id") ON DELETE No Action ON UPDATE No Action;


alter TABLE "public"."curriculum_module" add column "courses_or_weeks" smallint NULL    -- nädalatundide arv või kursuste arv
;

alter TABLE "public"."curriculum_module_competence" add column "description" text NULL;

CREATE TABLE "curriculum_module_study_field"
(
	"id" bigserial NOT NULL,
	"curriculum_study_field_id" bigint NOT NULL,
	"curriculum_module_id" bigint NOT NULL,
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

COMMENT ON COLUMN "public"."curriculum"."courses"	IS 'minimaalne kursuste arv GRÕK puhul';
COMMENT ON COLUMN "public"."curriculum"."grading_system_description"	IS 'hindamissüsteemi kirjeldus';

COMMENT ON TABLE "curriculum_study_field"	IS 'õppesuunad';
COMMENT ON COLUMN "public"."curriculum_module"."courses_or_weeks"	IS 'nädalatundide arv või kursuste arv';

CREATE INDEX "IXFK_curriculum_study_field_curriculum" ON "curriculum_study_field" ("curriculum_id" ASC);

ALTER TABLE "curriculum_module_study_field" ADD CONSTRAINT "PK_curriculum_module_study_field"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_curriculum_module_study_field_curriculum_module" ON "curriculum_module_study_field" ("curriculum_module_id" ASC);
CREATE INDEX "IXFK_curriculum_module_study_field_curriculum_study_field" ON "curriculum_module_study_field" ("curriculum_study_field_id" ASC);

ALTER TABLE "curriculum_study_field" ADD CONSTRAINT "FK_curriculum_study_field_curriculum"
	FOREIGN KEY ("curriculum_id") REFERENCES "public"."curriculum" ("id") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "curriculum_module_study_field" ADD CONSTRAINT "FK_curriculum_module_study_field_curriculum_module"
	FOREIGN KEY ("curriculum_module_id") REFERENCES "public"."curriculum_module" ("id") ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "curriculum_module_study_field" ADD CONSTRAINT "FK_curriculum_module_study_field_curriculum_study_field"
	FOREIGN KEY ("curriculum_study_field_id") REFERENCES "curriculum_study_field" ("id") ON DELETE No Action ON UPDATE No Action
;

CREATE UNIQUE INDEX student_vocational_omodule_theme_uq ON student_vocational_omodule_theme (student_id,curriculum_version_omodule_id, (curriculum_version_omodule_theme_id IS NULL)) WHERE curriculum_version_omodule_theme_id IS NULL;

CREATE OR REPLACE FUNCTION public.upd_student_curriculum_completion(p_id bigint)
 RETURNS integer
 LANGUAGE plpgsql
AS $function$
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
	p_opt_credits numeric:=0;
	p_avg_credits numeric:=0;
	p_x_credits numeric:=0;
	p_avg_total_credits numeric:=0;
	p_total_credits numeric:=0;
  p_pohi_credits numeric:=0;
	p_abs_credits numeric:=0;
	p_fabs_credits numeric:=0;
	p_curriculum_credits numeric:=0;
	a_count int:=0;
	pb_is_hgrade boolean:=false;

  p_vcurr_modules bigint array;
	p_vcurr_modules2 bigint array;

	p_fcurr_modules bigint array;
	p_fcurr_modules_ok bigint array;
	
	mod_id bigint;

	is_higher_curriculum boolean:=true;
	
	pb_modules_ok boolean:=true;
	p_modules_count integer:=0; --mitu moodulit on ette nähtud hindamiseks
	p_id2 bigint;
	p_final_ok boolean:=false;

	lp_abs_credits numeric:=0;
	lp_fabs_credits numeric:=0;
	lp_avg_total_credits numeric:=0;
  lp_avg_credits numeric:=0;
	lp_total_credits numeric:=0; 
	lp_opt_credits  numeric:=0;
	lpb_modules_ok boolean:=true;
	p_last_period_credits numeric:=null;
BEGIN
	for r in (select distinct cvo.id,cm.id as m_id, cm.credits, cc.optional_study_credits, cm.module_code, cc.is_higher, cc.credits as curriculum_credits
					  from curriculum_version cv
								 join curriculum_version_omodule cvo on cv.id=cvo.curriculum_version_id
								 join curriculum_module cm on cvo.curriculum_module_id=cm.id and cv.curriculum_id=cm.curriculum_id and coalesce(cm.is_additional,false)=false and cm.module_code!='KUTSEMOODUL_V' and coalesce(cm.is_additional,false)=false
								 join curriculum cc on cv.curriculum_id=cc.id
								 join student ss on cv.id=ss.curriculum_version_id
								 --left join student_group sg on ss.student_group_id=sg.id
					 where ss.id=p_id and (/*sg.id is null or */
																 coalesce(ss.speciality_code,'x')='x' or 
																 coalesce(ss.speciality_code,'x')!='x' and exists(
																					select 1 
																					from curriculum_module_occupation cmo 
																							 left join classifier_connect ccc on cmo.occupation_code=ccc.connect_classifier_code
																					where cmo.curriculum_module_id=cm.id and (cmo.occupation_code=ss.speciality_code or ccc.classifier_code=ss.speciality_code))))
	LOOP
		i:=i+1;
		p_curr_modules[i]:=r.id;
		p_vcurr_modules[i]:=r.m_id;
		p_curr_modules2[i]:=r.id;
		p_vcurr_modules2[i]:=r.m_id;
		p_x_credits:=0;
		
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
		call get_higher_student_cc(p_id, false, p_abs_credits, p_fabs_credits, p_avg_credits, p_avg_total_credits, p_total_credits, p_opt_credits, pb_modules_ok);
		--raise notice '% % % % % %', p_abs_credits, p_fabs_credits, p_avg_total_credits, p_total_credits, p_opt_credits, pb_modules_ok;
		call get_higher_student_cc(p_id, true, lp_abs_credits, lp_fabs_credits, lp_avg_credits, lp_avg_total_credits, lp_total_credits, lp_opt_credits, lpb_modules_ok);
		p_last_period_credits:=lp_abs_credits;
		--raise notice '%', p_last_period_credits;
		i:=0;
		
	else
		--Õppuri positiivsed tulemused
		for r in (select case when sv.arr_modules is null then coalesce(cvo.id,sv.curriculum_version_omodule_id) else null end curriculum_version_omodule_id, 
										 case when sv.arr_modules is null then coalesce(cvo.curriculum_module_id ,cmm.curriculum_module_id) else null end curriculum_module_id,
										sv.grade, sv.credits, sv.arr_modules, cvo.id as replaced_cvm_id, cvo.curriculum_module_id as replaced_cv_id
							from student_vocational_result sv
									 left join curriculum_version_omodule cmm on sv.curriculum_version_omodule_id=cmm.id
									  left join student_vocational_omodule_theme svot on svot.student_id =sv.student_id and sv.curriculum_version_omodule_id = svot.old_curriculum_version_omodule_id and svot.curriculum_version_omodule_theme_id is null
									 left join curriculum_version_omodule cvo on svot.curriculum_version_omodule_id = cvo.id 
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
				p_pohi_credits:=p_pohi_credits+r.credits;
						
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
						--raise notice 'SIIN';
						p_opt_credits:=p_opt_credits+r.credits;
						p_pohi_credits:=p_pohi_credits-r.credits;
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

		
		--põhimoodulite nn üle punktid peavad minema ka arvesse
    if p_pohi_credits > p_curriculum_credits-p_opt_credits then
			p_opt_credits:=p_opt_credits+(p_pohi_credits - (p_curriculum_credits-p_opt_credits));
	  end if;
		--kokku võlg
		if p_opt_credits > p_optional THEN
			p_opt_credits:=0;
		ELSE
			p_opt_credits:=p_optional-p_opt_credits;
		end if;

		--raise NOTICE 'opt: %/%', p_opt_credits, p_optional;


		p_abs_credits:=coalesce(p_abs_credits,0)+p_opt_credits;
		p_fabs_credits:=p_abs_credits-p_fabs_credits;

		--peame üle vaatama, et kokku punktid ikka oleks >= õppekava punktid
		if p_abs_credits=0 and p_total_credits < p_curriculum_credits then
			p_abs_credits:=p_curriculum_credits-p_total_credits;
		end if;

		--raise NOTICE 'opt: %/%/%/%', p_fabs_credits, p_abs_credits, p_total_credits,p_pohi_credits;

		--raise notice 'Tere %, %, %, %', p_abs_credits, p_avg_credits, p_avg_total_credits,to_char(current_timestamp(3),'mi:ss.ms');
	end if;

	--raise notice 'p_avg_credits: % %', p_avg_credits, p_avg_total_credits;

	update student_curriculum_completion 
	set study_backlog=-p_abs_credits, 
			study_backlog_without_graduate=-p_fabs_credits,
			average_mark=case when p_avg_total_credits > 0 then floor(p_avg_credits*1000/p_avg_total_credits)/1000 else 0 end, 
			credits=p_total_credits, 
			changed=current_timestamp(3),
			study_optional_backlog=-p_opt_credits,
			credits_before_current_study_period = p_last_period_credits,
			is_modules_ok=pb_modules_ok
	where student_id=p_id;
	
	GET DIAGNOSTICS a_count = ROW_COUNT;
	if a_count=0 THEN
			insert into student_curriculum_completion(student_id,study_backlog,study_backlog_without_graduate,average_mark,credits,inserted,changed,study_optional_backlog,is_modules_ok)
			values(p_id,-p_abs_credits,-p_fabs_credits,case when p_avg_total_credits > 0 then floor(p_avg_credits*100/p_avg_total_credits)/100 else 0 end,p_total_credits,current_timestamp(3),current_timestamp(3),-p_opt_credits,pb_modules_ok);
	end if;

	return 0;
exception when others THEN
	raise notice '%, %',p_id,sqlerrm;
	return -1;
end;
$function$
;

create trigger student_vocational_omodule_theme_trg after
insert
    or
delete
    or
update
    on
    public.student_vocational_omodule_theme for each row execute procedure upd_curriculum_completion_trgr2();
	
/* Create Tables */

CREATE TABLE "teacher_other_load"
(
	"id" bigserial NOT NULL,
	"teacher_id" bigint NOT NULL,
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL,
	"name_et" varchar(4000)	 NOT NULL,    -- koormuse kirjeldus
	"hours" numeric(8,2) NOT NULL,    -- tunnid
	"percent" numeric(5,2) NOT NULL,    -- protsent
	"study_year_id" bigint NOT NULL
)
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE "teacher_other_load"	IS 'õpetaja muu koormus';
COMMENT ON COLUMN "teacher_other_load"."name_et"	IS 'koormuse kirjeldus';
COMMENT ON COLUMN "teacher_other_load"."hours"	IS 'tunnid';
COMMENT ON COLUMN "teacher_other_load"."percent"	IS 'protsent';

/* Create Primary Keys, Indexes, Uniques, Checks */
ALTER TABLE "teacher_other_load" ADD CONSTRAINT "PK_teacher_other_load"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_teacher_other_load_study_year" ON "teacher_other_load" ("study_year_id" ASC);
CREATE INDEX "IXFK_teacher_other_load_teacher" ON "teacher_other_load" ("teacher_id" ASC);

/* Create Foreign Key Constraints */
ALTER TABLE "teacher_other_load" ADD CONSTRAINT "FK_teacher_other_load_study_year"	FOREIGN KEY ("study_year_id") REFERENCES "public"."study_year" ("id") ON DELETE No Action ON UPDATE No Action;
ALTER TABLE "teacher_other_load" ADD CONSTRAINT "FK_teacher_other_load_teacher"	FOREIGN KEY ("teacher_id") REFERENCES "public"."teacher" ("id") ON DELETE No Action ON UPDATE No Action;

alter table student_group add column ehis_school_code varchar(100);
create index IXFK_student_group_classifier_04 on student_group(ehis_school_code);
alter table student_group add constraint FK_student_group_classifier_04 foreign key(ehis_school_code) references classifier(code);


CREATE TABLE "curriculum_version_omodule_theme_study_field"
(
	"id" bigserial NOT NULL,
	"curriculum_version_omodule_id" bigint NOT NULL,
	"curriculum_study_field_id" bigint NOT NULL,
	"inserted" timestamp without time zone NOT NULL,
	"changed" timestamp without time zone NULL,
	"version" integer NOT NULL,
	"inserted_by" varchar(100)	 NOT NULL,
	"changed_by" varchar(100)	 NULL
)
;

COMMENT ON TABLE "curriculum_version_omodule_theme_study_field"	IS 'teema õppesuunad';
ALTER TABLE "curriculum_version_omodule_theme_study_field" ADD CONSTRAINT "PK_curriculum_version_omodule_theme_study_field"	PRIMARY KEY ("id");
CREATE INDEX "IXFK_curriculum_version_omodule_theme_study_field_curriculum_study_field" ON "curriculum_version_omodule_theme_study_field" ("curriculum_study_field_id" ASC);
CREATE INDEX "IXFK_curriculum_version_omodule_theme_study_field_curriculum_version_omodule_theme" ON "curriculum_version_omodule_theme_study_field" ("curriculum_version_omodule_id" ASC);

ALTER TABLE "curriculum_version_omodule_theme_study_field" ADD CONSTRAINT "FK_curriculum_version_omodule_theme_study_field_curriculum_study_field"
	FOREIGN KEY ("curriculum_study_field_id") REFERENCES "curriculum_study_field" ("id") ON DELETE No Action ON UPDATE No Action;

ALTER TABLE "curriculum_version_omodule_theme_study_field" ADD CONSTRAINT "FK_curriculum_version_omodule_theme_study_field_curriculum_version_omodule_theme"
	FOREIGN KEY ("curriculum_version_omodule_id") REFERENCES "public"."curriculum_version_omodule_theme" ("id") ON DELETE No Action ON UPDATE No Action;

alter table curriculum_version_omodule_theme add column is_additional boolean;	


CREATE OR REPLACE FUNCTION public.upd_curriculum_completion_trgr3() RETURNS trigger LANGUAGE plpgsql AS $function$
declare 
	i bigint;
	p_id	bigint:=0;
BEGIN
	--raise notice 'siin';
	if tg_op='DELETE' then
		i:=old.student_higher_result_id;
	else
		i:=new.student_higher_result_id;
	end if;
	select student_id into p_id from student_higher_result where id=i;
	--raise notice 'p_id: %',p_id;
	i:=upd_student_curriculum_completion(p_id);
  return null;
	exception when others then
		--raise notice 'err: %', sqlerrm;
		return null;
end;
$function$
;

create trigger student_higher_result_module_trg after insert or update or delete on
public.student_higher_result_module for each row execute procedure upd_curriculum_completion_trgr3();

CREATE OR REPLACE FUNCTION public.del_student_vocational_result_matches(p_student bigint)
 RETURNS integer
 LANGUAGE plpgsql
AS $function$
begin
	delete from student_vocational_omodule_theme where id in (
		select svot.id from student_vocational_omodule_theme svot
		where svot.student_id = p_student and svot.old_curriculum_version_omodule_theme_id is null
			and not exists (select 1 from student_vocational_result svr where svr.student_id = svot.student_id
				and svr.curriculum_version_omodule_id = svot.old_curriculum_version_omodule_id
				and svr.grade_code in ('KUTSEHINDAMINE_A', 'KUTSEHINDAMINE_3', 'KUTSEHINDAMINE_4', 'KUTSEHINDAMINE_5')));
	return 0;
end;
$function$
;

CREATE OR REPLACE FUNCTION public.upd_del_apel_result()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
declare
    u_count integer;
		b_count integer;
		r record;
		rr record;
		rrr record;
		p_id integer;
		x integer;
		pm_id bigint;
		pm_is_optional boolean;
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
								insert into journal_entry_student_history(journal_entry_student_id,grade_code,grade_inserted,inserted,version,inserted_by,grade_inserted_by,grading_schema_row_id,verbal_grade)
								select rr.id,grade_code,grade_inserted,now(),0,NEW.changed_by,grade_inserted_by,grading_schema_row_id,verbal_grade
								from journal_entry_student where id=rr.id and coalesce(grade_code,'x')!='x';
								update journal_entry_student set grading_schema_row_id=null, verbal_grade=null, grade_code=case when cardinality(r.arr) > 1 then 'KUTSEHINDAMINE_A' else r.grade_code end,grade_inserted=now(),grade_inserted_by=NEW.changed_by where id=rr.id;
							end if;
						end if;
					end loop;
				end loop;
				x:=upd_student_curriculum_completion(new.student_id);
			ELSE
				--siin midagi peaks tegema hakkama suurte võta ainetega
				for r in (SELECT subj.id as subject_id,aa.is_new,
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
										SELECT subj.id,false,
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
						coalesce(r.name_et,'-'),coalesce(coalesce(r.name_en,r.name_et),'-'),r.teachers,	r.credits,r.code,				coalesce(r.is_optional,true),p_id,r.curriculum_version_hmodule_id) returning id into p_id; --is_optional

					if coalesce(r.subject_id,0) > 0 then
						--select distinct first_value(id)over(partition by r.subject_id order by grade_date desc nulls last) into p_id from student_higher_result where student_id=NEW.student_id and subject_id=r.subject_id;
						update student_higher_result set is_active=false where student_id=NEW.student_id and subject_id=r.subject_id and id!=p_id;
					end if;
					--nüüd midagi peaks tegema asendusainetega
					--ideaalis peaks ained paigutama hetkel kehtiva õppekava ver järgi
					--seeega leiame iga aine jaoks temale sobivat moodulit
					if coalesce(r.is_new,false)=true then
						x:=upd_apel_replaced_subjects(NEW.student_id,p_id,r.record_id);
--s_id bigint, p_id bigint
						/*for rr in (select * from apel_application_formal_replaced_subject_or_module ar where ar.apel_application_record_id=r.record_id)
						loop
							pm_id:=0;
							pm_is_optional:=true;
							for rrr in (select cm.id, hms.is_optional
									from student st
											 join curriculum_version_hmodule cm on st.curriculum_version_id=cm.curriculum_version_id
											 join curriculum_version_hmodule_subject hms on cm.id=hms.curriculum_version_hmodule_id
								  where st.id=NEW.student_id and hms.subject_id=rr.subject_id and 
											 (coalesce(st.curriculum_speciality_id,0)=0 or 
												coalesce(st.curriculum_speciality_id,0) > 0 and 
												exists(
														select 1 
														from curriculum_version_hmodule_speciality hs
																	join curriculum_version_speciality cvs on hs.curriculum_version_speciality_id=cvs.id
														where hs.curriculum_version_hmodule_id=cm.id and
														cvs.curriculum_speciality_id=st.curriculum_speciality_id))
									order by cm.id)
							LOOP
								pm_id:=rrr.id;
								pm_is_optional:=rrr.is_optional;
								exit;
							end loop;
							--leaime mooduli ja kohustuslikkuse
							insert into student_higher_result_replaced_subject(student_higher_result_id,subject_id,curriculum_version_hmodule_id,is_optional)
							values(p_id,rr.subject_id,case when pm_id > 0 then pm_id else null end,pm_is_optional);
							raise notice 's % %', r.record_id, rr.subject_id;
						end loop;*/
					end if;
				--return null;
			end loop;
			x:=upd_student_curriculum_completion(new.student_id);
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
				--x:=del_student_vocational_result_matches(st_id);
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
	if tg_op in ('INSERT','UPDATE') and NEW.id is not null and COALESCE(NEW.grade_code,'x')='x' or tg_op in ('DELETE') THEN
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
		x:=del_student_vocational_result_matches(st_id);
		x:=upd_student_curriculum_completion(st_id);
	elsif NEW.id is not null then
		for r in (SELECT coalesce(ph.final_subject_id, sp.subject_id) as subject_id,clf.value as grade, sp.study_period_id,pp.school_id,
									coalesce(cvh.id,coalesce(hn.id,ds.curriculum_version_hmodule_id)) as curriculum_version_hmodule_id,coalesce(shs.is_optional,ds.is_optional) is_optional,
											--curriculum_version_hmodule_id
											coalesce(cvh.name_et,subj.name_et) as name_et,coalesce(cvh.name_en,subj.name_en) name_en,subj.code,
											coalesce(cvh.total_credits,subj.credits) credits,
											--is_optional
											case when ph.final_subject_id is not null then (select string_agg(coalesce(pers.firstname,fts.firstname )||' '||coalesce(pers.lastname,fts.lastname ),', ' order by fts.is_primary desc) from final_thesis ft
											join final_thesis_supervisor fts on ft.id=final_thesis_id 
											left join teacher ttt on fts.teacher_id =ttt.id 
											left join person pers on ttt.person_id =pers.id
											where ft.student_id=st.id )
											when ppp.id is not null then ppp.firstname||' '||ppp.lastname else (select string_agg(pers.firstname||' '||pers.lastname,', ') 
												from subject_study_period_teacher st join teacher tt on st.teacher_id=tt.id join person pers on tt.person_id=pers.id
													where st.subject_study_period_id=sp.id and coalesce(st.is_diploma_supplement,true)=true) end as teachers, --teachers
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
				subject_name_et=coalesce(r.name_et,'-'),
				subject_name_en=coalesce(r.name_en,r.name_et),
				teachers=substr(r.teachers,1,255),
				credits=r.credits,
				subject_code=r.code,
				curriculum_version_hmodule_id=r.curriculum_version_hmodule_id,
				is_optional=coalesce(r.is_optional,false),
				--is_optional=false,
				subject_id=r.subject_id,
				changed=now(),
				study_period_id=coalesce(r.study_period_id, get_study_period(NEW.grade_date::date, r.school_id::int)),
				is_module=case when coalesce(r.cvh_id,0) > 0 then true else false end,
			  grading_schema_row_id=new.grading_schema_row_id
			where protocol_student_id=NEW.id;
			GET DIAGNOSTICS u_count = ROW_COUNT;

			if coalesce(u_count,0)=0 THEN
				insert into student_higher_result (
					student_id,			subject_id,				protocol_student_id,				grade,				grade_date,				grade_mark,				grade_code,
					apel_application_record_id,				apel_school_id,				inserted, curriculum_version_hmodule_id,is_optional,
					subject_name_et,				subject_name_en,				teachers,				credits,				subject_code,				study_period_id, is_module,
			  grading_schema_row_id) 
				values(
					NEW.student_id,r.subject_id,NEW.id,coalesce(NEW.grade,r.grade),NEW.grade_date,
					coalesce(NEW.grade_mark,case r.grade when '0' then 0 when '1' then 1 when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else null end),				NEW.grade_code,
					null,--apel_application_record_id,
					null,--apel_school_id
					now(),r.curriculum_version_hmodule_id,coalesce(r.is_optional,false),
					coalesce(r.name_et,'-'),coalesce(r.name_en,r.name_et),substr(r.teachers,1,255),	r.credits,r.code,				coalesce(r.study_period_id, get_study_period(NEW.grade_date::date, r.school_id::int)),case when coalesce(r.cvh_id,0) > 0 then true else false end,
         new.grading_schema_row_id); --is_optional
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
				update student_higher_result set is_active=true where student_id=st_id and subject_id=r.subject_id and id=p_id and is_active=false;
			elsif coalesce(r.cvh_id,0) > 0 then
				select distinct first_value(sr.id)over(partition by sr.curriculum_version_hmodule_id order by case when coalesce(sr.apel_application_record_id,0)=0 then 1 else 0 end, sr.grade_date desc nulls last,ph.type_code asc,ph.inserted desc) into p_id 
				from student_higher_result sr
						 left join protocol_student ps on sr.protocol_student_id=ps.id
						 left join protocol_hdata ph on ps.protocol_id=ph.protocol_id
				where sr.student_id=case when tg_op='DELETE' then old.student_id else NEW.student_id end and sr.curriculum_version_hmodule_id=r.cvh_id and sr.is_module=true;
				update student_higher_result set is_active=false where student_id=st_id and curriculum_version_hmodule_id=r.cvh_id and is_module=true and id!=p_id;
			end if;

			/*--kustutame üleliigset eelmist rida
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
				teachers=substr(r.teachers,1,255),	
				changed=now(), 
				module_name_et=r.name_et,	
				module_name_en=r.name_en,
				study_year_id=r.study_year_id,
			  grading_schema_row_id=new.grading_schema_row_id
			where protocol_student_id=NEW.id;
			GET DIAGNOSTICS u_count = ROW_COUNT;

			if coalesce(u_count,0)=0 THEN
				insert into student_vocational_result (
					student_id,			curriculum_version_omodule_id,				protocol_student_id,		grade,				grade_date,				grade_mark,				grade_code,
					credits,	teachers,	inserted, module_name_et,	module_name_en,study_year_id,grading_schema_row_id)
				values(NEW.student_id,r.curriculum_version_omodule_id,NEW.id,		coalesce(NEW.grade,r.grade),	NEW.grade_date,	
							coalesce(NEW.grade_mark,case r.grade when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else null end),	NEW.grade_code,
					r.credits,	r.teachers,	now(), r.name_et,	r.name_en, r.study_year_id,NEW.grading_schema_row_id);
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
			x:=del_student_vocational_result_matches(st_id);
			x:=upd_student_curriculum_completion(new.student_id);
			return null;
		end loop;
  end if;
	return null;
end;
$function$
;

alter table curriculum_version_omodule_theme_study_field rename curriculum_version_omodule_id to curriculum_version_omodule_theme_id;