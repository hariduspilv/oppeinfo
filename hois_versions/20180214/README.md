VERSIOON: 0.6.4/20180214

STRUKTUUR:
------------------------------------------------------
README.md - tarne ja installeerimise kirjeldus

KIRJELDUS: 
----------------------------------------------------- 
Vigade parandused, käskkirja kinnitamine ilma EKISesse saatmata
	   

EELDUS: ver. 0.6.3/20180202
------------------------------------------------------

ANDMEBAASI INSTALLEERIMINE:
------------------------------------------------------

KIRJELDUS: olemasolev andmebaas "hois" täiendatakse. Andmebaasi skripti on install20180214.sql
EELDUS: kasutaja teab andmebaasi asukohta ja andmebaasi peakasutaja salasõna, oskab kasutada "psql" käsku.

Andmebaasi installeerimiseks:
1. käivitada install20180214.sql skript, nt
   
   psql -h devhois -f install20180214.sql 2>&1 | tee log.txt
   
   , kus
   
   -h devhois - andmebaasi host, kus devhois on vastava serveri/hosti nimi, selle asemel võib panna ka IP aadressi. NB! kui skripti käivitamine toimub andmebaasi lokaalses masinas, siis -h parameetrit võib ära jätta
   -f install20180214.sql - install faili nimi
   log.txt - andmebaasi installeerimise logi fail
   
   Installeerimise käigus küsitakse andmebaasi peakasutaja salasõna ja viiakse andmebaasi vastavad muudatused sisse


RAKENDUSE INSTALLEERIMINE:
------------------------------------------------------
1. Backendi paigaldamiseks
	1. Teisendada kaasa pandud hois_back.jar /opt/hois kausta
	2. veenduda, et /opt/hois kaustas on olemas muudetud application.properties fail ja käivitada käsk "java -jar hois_back.jar", rakendus läheb käima.
	
2. Frontendi paigaldamiseks
	1. Kustutada vanad html jms failid: käivitada käsk "rm -Rf /opt/hois/html/*" (nginxist vana seisu tühjendamiseks)
	2. Kopeerida kaasa pandud html kausta sisu /opt/hois/html kausta
	3. Muuta /opt/hois/html/config.js õigeks:
		apiUrl - frontendi server, nt https://localhost/hois_back
		idCardLoginUrl - ID-kaardiga sisselogimiseks seadistatud server (vt proxy_backend.conf, host peab olema avaliku kasutaja jaoks nime järgi kättesaadav, soovitatav kasutada kehtivat sertifikaati, self-signed sertifikaadiga serveris võivad tekkida mõningad probleemid ID-kaardiga autentimisel), nt https://idlogin.devhois
		ekisUrl - EKISe lepingute, käskkirjade, tõendite asukoht, testimiseks kasutatakse https://kis-test.hm.ee/?wdsturi=3Dpage%3=Dview_dynobj%26pid%3D
		mobileIdInitialDelay - mobiil-ID sisselogimisel mitme millisekundi pärast esimest korda küsitakse (nt 5000)
		mobileIdPollInterval - mobiil-ID sisselogimisel pollimise intervaal millisekundites (nt 4000)
		mobileIdMaxPolls - mobiil-ID sisselogimisel mitu korda maksimaalselt pollitakse, nt 15
