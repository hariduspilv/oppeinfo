VERSIOON: 0.6.7/20180404

STRUKTUUR:
------------------------------------------------------
README.md - tarne ja installeerimise kirjeldus
/db - andmebaasi skriptide kaust
/hois_back - rakenduse backendi kood
/hois_front - rakenduse frontendi kood
/hois_ws - rakenduse veebiteenuste kood
/hois_html - rakenduse genereeritud html-id


EELDUS: ver. 0.6.6/20180309
------------------------------------------------------

ANDMEBAASI INSTALLEERIMINE:
------------------------------------------------------

KIRJELDUS: olemasolev andmebaas "hois" täiendatakse. Andmebaasi skripti on db/install20180404.sql
EELDUS: kasutaja teab andmebaasi asukohta ja andmebaasi peakasutaja salasõna, oskab kasutada "psql" käsku.

Andmebaasi installeerimiseks:
1. käivitada install20180404.sql skript, nt
   
   psql -h devhois -f install20180404.sql 2>&1 | tee log.txt
   
   , kus
   
   -h devhois - andmebaasi host, kus devhois on vastava serveri/hosti nimi, selle asemel võib panna ka IP aadressi. NB! kui skripti käivitamine toimub andmebaasi lokaalses masinas, siis -h parameetrit võib ära jätta
   -f install20180404.sql - install faili nimi
   log.txt - andmebaasi installeerimise logi fail
   
   Installeerimise käigus küsitakse andmebaasi peakasutaja salasõna ja viiakse andmebaasi vastavad muudatused sisse


RAKENDUSE INSTALLEERIMINE:
------------------------------------------------------
1. Backendi paigaldamiseks
	1. Teisendada kaasa pandud hois_back.jar /opt/hois kausta
	2. veenduda, et /opt/hois kaustas on olemas muudetud application.properties fail
    3. Lisada application.properties faili Moodle seadistamise parameetrid:
		# Moodle liidese asukoht
		moodle.address - Moodle asukoht, nt https://test.moodle.hitsa.ee
		moodle.pluginPath - Moodle plugin'i path, nt /local/mois
		moodle.pluginName - Moodle plugin, nt /ois.php (NB! Liidese poole pöördumine toimub alati moodle.address+moodle.pluginPath+moodle.pluginName urli kaudu)
		# Sõnumi krüpteerimiseks vajalikud parameetrud (koostatakse krüpteerides parameetrite string AES-256-CBC meetodil kasutades moodle.salti ja moodle.vectorit), vt ka Hitsa poolt koostatud dokumenti mois_ver08.pdf
		moodle.salt=http://img.allw.mn/content/zi/kz/pjx0oz0d55c8d5c1a1939530314946.jpg 
		moodle.vector=abcdefghijklmnop
		# Privaatvõtme ja sertifikaadid
		moodle.privateKey - privaatvõtme asukoht,nt /opt/hois/certs/mdl_key.pem. Privaatvõtme saab HITSA Moodle arendaja käest
		moodle.certificate - Moodle sertifikaadi asukoht, nt /opt/hois/certs/sis_cert.crt	
	4. ja käivitada käsk "java -jar hois_back.jar", rakendus läheb käima.
	
2. Frontendi paigaldamiseks
	1. Kustutada vanad html jms failid: käivitada käsk "rm -Rf /opt/hois/html/*" (nginxist vana seisu tühjendamiseks)
	2. Kopeerida kaasa pandud hois_html kausta sisu /opt/hois/html kausta
	3. Muuta /opt/hois/html/config.js õigeks:
		apiUrl - frontendi server, nt https://localhost/hois_back
		idCardLoginUrl - ID-kaardiga sisselogimiseks seadistatud server (vt proxy_backend.conf, host peab olema avaliku kasutaja jaoks nime järgi kättesaadav, soovitatav kasutada kehtivat sertifikaati, self-signed sertifikaadiga serveris võivad tekkida mõningad probleemid ID-kaardiga autentimisel), nt https://idlogin.devhois
		ekisUrl - EKISe lepingute, käskkirjade, tõendite asukoht, testimiseks kasutatakse https://kis-test.hm.ee/?wdsturi=3Dpage%3=Dview_dynobj%26pid%3D
		mobileIdInitialDelay - mobiil-ID sisselogimisel mitme millisekundi pärast esimest korda küsitakse (nt 5000)
		mobileIdPollInterval - mobiil-ID sisselogimisel pollimise intervaal millisekundites (nt 4000)
		mobileIdMaxPolls - mobiil-ID sisselogimisel mitu korda maksimaalselt pollitakse, nt 15