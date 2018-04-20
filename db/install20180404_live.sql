\c hois;

update person set idcode='49510170019',changed_by='Automaat',changed=now() where id=4164;
update curriculum set status_code='OPPEKAVA_STAATUS_S' where id=67;
update curriculum_version set status_code='OPPEKAVA_VERSIOON_STAATUS_S' where curriculum_id=67;
