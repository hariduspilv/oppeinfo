\c hois

update classifier set name_et='arvestatud', name_en='Passed', extraval1='A', extraval2='P' where code = 'KORGHINDAMINE_A';
update classifier set name_et='mittearvestatud', name_en='Fail', extraval1='MA', extraval2='F' where code = 'KORGHINDAMINE_M';
update classifier set name_et='mitteilmunud', name_en='Not participated', extraval1='MI', extraval2='NP' where code = 'KORGHINDAMINE_MI';