\c tahvel_20180412_2

do $$
declare r record;
	i integer:=0;
  v_cnt integer:=0;
begin
	for r in (
				select distinct *
				FROM
				(select cm.id oige_mid,cm2.id vale_mid,je.curriculum_module_outcomes_id,
				(select min(ooo.id)
				 from curriculum_module_outcomes ooo
				 where ooo.curriculum_module_id=cm.id and (lower(replace(ooo.outcome_et,' ',''))=lower(replace(oo2.outcome_et,' ','')) OR
				lower(replace(ooo.outcome_et,' ','')) like lower(replace(oo2.outcome_et,' ',''))||'%' OR
				lower(replace(oo2.outcome_et,' ','')) like lower(replace(ooo.outcome_et,' ',''))||'%')) as oige_oid,
				je.*
				from journal jj
						 join journal_entry je on jj.id=je.journal_id
						 join journal_omodule_theme jt on jj.id=jt.journal_id
						 join lesson_plan_module lm on jt.lesson_plan_module_id=lm.id
						 join lesson_plan lp on lm.lesson_plan_id=lp.id
						 join student_group sg on lp.student_group_id=sg.id
						 join curriculum_version_omodule cmm on lm.curriculum_version_omodule_id=cmm.id
						 join curriculum_module cm on cmm.curriculum_module_id=cm.id
						 left join curriculum_module_outcomes oo on cm.id=oo.curriculum_module_id and oo.id=je.curriculum_module_outcomes_id
						 join curriculum_module_outcomes oo2 on oo2.id=je.curriculum_module_outcomes_id
						 join curriculum_module cm2 on oo2.curriculum_module_id=cm2.id
						 join jarva.x_jarva_data xd on xd.hois_table='curriculum_module_outcomes' and xd.hois_id=oo2.id--jarva_id=1518
						 join jarva.x_jarva_data xd2 on xd2.hois_table='curriculum_module' and xd2.hois_id=cm2.id
									--where co.id=xd.hois_id and co.curriculum_module_id=xd2.hois_id
				where je.entry_type_code='SISSEKANNE_O' and jj.school_id in (7) --and jj.id=3706
				and oo.id is null) xx where xx.oige_oid is not null and (select count(*) from journal_entry where journal_id=xx.journal_id and curriculum_module_outcomes_id=xx.oige_oid)=0)
LOOP
	update journal_entry je
	set curriculum_module_outcomes_id=r.oige_oid
	where je.id=r.id and (select count(*) from journal_entry rr where rr.journal_id=je.journal_id and rr.curriculum_module_outcomes_id=r.oige_oid)=0;
	GET DIAGNOSTICS v_cnt = ROW_COUNT;
  if v_cnt > 0 THEN
		i:=i+1;
	end if;
end loop;
raise notice 'i: %', i;
END;
$$;

update curriculum_version set status_code='OPPEKAVA_VERSIOON_STAATUS_S' where id=297;
create table character_entity(
    name text primary key,
    ch char(1) unique
);
insert into character_entity (ch, name) values
    (E'\u00C6','AElig'),(E'\u00C1','Aacute'),(E'\u00C2','Acirc'),(E'\u00C0','Agrave'),(E'\u0391','Alpha'),(E'\u00C5','Aring'),(E'\u00C3','Atilde'),(E'\u00C4','Auml'),(E'\u0392','Beta'),(E'\u00C7','Ccedil'),
    (E'\u03A7','Chi'),(E'\u2021','Dagger'),(E'\u0394','Delta'),(E'\u00D0','ETH'),(E'\u00C9','Eacute'),(E'\u00CA','Ecirc'),(E'\u00C8','Egrave'),(E'\u0395','Epsilon'),(E'\u0397','Eta'),(E'\u00CB','Euml'),
    (E'\u0393','Gamma'),(E'\u00CD','Iacute'),(E'\u00CE','Icirc'),(E'\u00CC','Igrave'),(E'\u0399','Iota'),(E'\u00CF','Iuml'),(E'\u039A','Kappa'),(E'\u039B','Lambda'),(E'\u039C','Mu'),(E'\u00D1','Ntilde'),
    (E'\u039D','Nu'),(E'\u0152','OElig'),(E'\u00D3','Oacute'),(E'\u00D4','Ocirc'),(E'\u00D2','Ograve'),(E'\u03A9','Omega'),(E'\u039F','Omicron'),(E'\u00D8','Oslash'),(E'\u00D5','Otilde'),(E'\u00D6','Ouml'),
    (E'\u03A6','Phi'),(E'\u03A0','Pi'),(E'\u2033','Prime'),(E'\u03A8','Psi'),(E'\u03A1','Rho'),(E'\u0160','Scaron'),(E'\u03A3','Sigma'),(E'\u00DE','THORN'),(E'\u03A4','Tau'),(E'\u0398','Theta'),
    (E'\u00DA','Uacute'),(E'\u00DB','Ucirc'),(E'\u00D9','Ugrave'),(E'\u03A5','Upsilon'),(E'\u00DC','Uuml'),(E'\u039E','Xi'),(E'\u00DD','Yacute'),(E'\u0178','Yuml'),(E'\u0396','Zeta'),(E'\u00E1','aacute'),
    (E'\u00E2','acirc'),(E'\u00B4','acute'),(E'\u00E6','aelig'),(E'\u00E0','agrave'),(E'\u2135','alefsym'),(E'\u03B1','alpha'),(E'\u0026','amp'),(E'\u2227','and'),(E'\u2220','ang'),(E'\u00E5','aring'),
    (E'\u2248','asymp'),(E'\u00E3','atilde'),(E'\u00E4','auml'),(E'\u201E','bdquo'),(E'\u03B2','beta'),(E'\u00A6','brvbar'),(E'\u2022','bull'),(E'\u2229','cap'),(E'\u00E7','ccedil'),(E'\u00B8','cedil'),
    (E'\u00A2','cent'),(E'\u03C7','chi'),(E'\u02C6','circ'),(E'\u2663','clubs'),(E'\u2245','cong'),(E'\u00A9','copy'),(E'\u21B5','crarr'),(E'\u222A','cup'),(E'\u00A4','curren'),(E'\u21D3','dArr'),
    (E'\u2020','dagger'),(E'\u2193','darr'),(E'\u00B0','deg'),(E'\u03B4','delta'),(E'\u2666','diams'),(E'\u00F7','divide'),(E'\u00E9','eacute'),(E'\u00EA','ecirc'),(E'\u00E8','egrave'),(E'\u2205','empty'),
    (E'\u2003','emsp'),(E'\u2002','ensp'),(E'\u03B5','epsilon'),(E'\u2261','equiv'),(E'\u03B7','eta'),(E'\u00F0','eth'),(E'\u00EB','euml'),(E'\u20AC','euro'),(E'\u2203','exist'),(E'\u0192','fnof'),
    (E'\u2200','forall'),(E'\u00BD','frac12'),(E'\u00BC','frac14'),(E'\u00BE','frac34'),(E'\u2044','frasl'),(E'\u03B3','gamma'),(E'\u2265','ge'),(E'\u003E','gt'),(E'\u21D4','hArr'),(E'\u2194','harr'),
    (E'\u2665','hearts'),(E'\u2026','hellip'),(E'\u00ED','iacute'),(E'\u00EE','icirc'),(E'\u00A1','iexcl'),(E'\u00EC','igrave'),(E'\u2111','image'),(E'\u221E','infin'),(E'\u222B','int'),(E'\u03B9','iota'),
    (E'\u00BF','iquest'),(E'\u2208','isin'),(E'\u00EF','iuml'),(E'\u03BA','kappa'),(E'\u21D0','lArr'),(E'\u03BB','lambda'),(E'\u2329','lang'),(E'\u00AB','laquo'),(E'\u2190','larr'),(E'\u2308','lceil'),
    (E'\u201C','ldquo'),(E'\u2264','le'),(E'\u230A','lfloor'),(E'\u2217','lowast'),(E'\u25CA','loz'),(E'\u200E','lrm'),(E'\u2039','lsaquo'),(E'\u2018','lsquo'),(E'\u003C','lt'),(E'\u00AF','macr'),
    (E'\u2014','mdash'),(E'\u00B5','micro'),(E'\u00B7','middot'),(E'\u2212','minus'),(E'\u03BC','mu'),(E'\u2207','nabla'),(E'\u00A0','nbsp'),(E'\u2013','ndash'),(E'\u2260','ne'),(E'\u220B','ni'),
    (E'\u00AC','not'),(E'\u2209','notin'),(E'\u2284','nsub'),(E'\u00F1','ntilde'),(E'\u03BD','nu'),(E'\u00F3','oacute'),(E'\u00F4','ocirc'),(E'\u0153','oelig'),(E'\u00F2','ograve'),(E'\u203E','oline'),
    (E'\u03C9','omega'),(E'\u03BF','omicron'),(E'\u2295','oplus'),(E'\u2228','or'),(E'\u00AA','ordf'),(E'\u00BA','ordm'),(E'\u00F8','oslash'),(E'\u00F5','otilde'),(E'\u2297','otimes'),(E'\u00F6','ouml'),
    (E'\u00B6','para'),(E'\u2202','part'),(E'\u2030','permil'),(E'\u22A5','perp'),(E'\u03C6','phi'),(E'\u03C0','pi'),(E'\u03D6','piv'),(E'\u00B1','plusmn'),(E'\u00A3','pound'),(E'\u2032','prime'),
    (E'\u220F','prod'),(E'\u221D','prop'),(E'\u03C8','psi'),(E'\u0022','quot'),(E'\u21D2','rArr'),(E'\u221A','radic'),(E'\u232A','rang'),(E'\u00BB','raquo'),(E'\u2192','rarr'),(E'\u2309','rceil'),
    (E'\u201D','rdquo'),(E'\u211C','real'),(E'\u00AE','reg'),(E'\u230B','rfloor'),(E'\u03C1','rho'),(E'\u200F','rlm'),(E'\u203A','rsaquo'),(E'\u2019','rsquo'),(E'\u201A','sbquo'),(E'\u0161','scaron'),
    (E'\u22C5','sdot'),(E'\u00A7','sect'),(E'\u00AD','shy'),(E'\u03C3','sigma'),(E'\u03C2','sigmaf'),(E'\u223C','sim'),(E'\u2660','spades'),(E'\u2282','sub'),(E'\u2286','sube'),(E'\u2211','sum'),
    (E'\u2283','sup'),(E'\u00B9','sup1'),(E'\u00B2','sup2'),(E'\u00B3','sup3'),(E'\u2287','supe'),(E'\u00DF','szlig'),(E'\u03C4','tau'),(E'\u2234','there4'),(E'\u03B8','theta'),(E'\u03D1','thetasym'),
    (E'\u2009','thinsp'),(E'\u00FE','thorn'),(E'\u02DC','tilde'),(E'\u00D7','times'),(E'\u2122','trade'),(E'\u21D1','uArr'),(E'\u00FA','uacute'),(E'\u2191','uarr'),(E'\u00FB','ucirc'),(E'\u00F9','ugrave'),
    (E'\u00A8','uml'),(E'\u03D2','upsih'),(E'\u03C5','upsilon'),(E'\u00FC','uuml'),(E'\u2118','weierp'),(E'\u03BE','xi'),(E'\u00FD','yacute'),(E'\u00A5','yen'),(E'\u00FF','yuml'),(E'\u03B6','zeta'),
    (E'\u200D','zwj'),(E'\u200C','zwnj')
;

create or replace function entity2char(t text)
returns text as $body$
declare
    r record;
begin
    for r in
        select distinct ce.ch, ce.name
        from
            character_entity ce
            inner join (
                select name[1] "name"
                from regexp_matches(t, '&([A-Za-z]+?);', 'g') r(name)
            ) s on ce.name = s.name
    loop
        t := replace(t, '&' || r.name || ';', r.ch);
    end loop;

    for r in
        select distinct
            hex[1] hex,
            ('x' || repeat('0', 8 - length(hex[1])) || hex[1])::bit(32)::int codepoint
        from regexp_matches(t, '&#x([0-9a-f]{1,8}?);', 'gi') s(hex)
    loop
        t := regexp_replace(t, '&#x' || r.hex || ';', chr(r.codepoint), 'gi');
    end loop;

    for r in
        select distinct
            chr(codepoint[1]::int) ch,
            codepoint[1] codepoint
        from regexp_matches(t, '&#([0-9]{1,10}?);', 'g') s(codepoint)
    loop
        t := replace(t, '&#' || r.codepoint || ';', r.ch);
    end loop;

    return t;
end;
$body$
language plpgsql immutable;


update journal_entry set name_et=entity2char(name_et) where name_et like '%&%';
update journal_entry set name_et=entity2char(name_et) where name_et like '%#%';
update journal_entry set content=entity2char(content) where content like '%&%';
update journal_entry set content=entity2char(content) where content like '%#%';
update journal_entry set homework=entity2char(homework) where homework like '%&%';
update journal_entry set homework=entity2char(homework) where homework like '%#%';
