VERSIOON:  2.6.0/20201210

STRUKTUUR:
------------------------------------------------------
README.md - tarne ja installeerimise kirjeldus
/db - andmebaasi skriptide kaust
/hois_back - rakenduse backendi kood
/hois_front - rakenduse frontendi kood
/hois_ws - rakenduse veebiteenuste kood
/hois_html - rakenduse genereeritud html-id


EELDUS: ver. 2.5.0/20201210
------------------------------------------------------

ANDMEBAASI INSTALLEERIMINE:
------------------------------------------------------

KIRJELDUS: olemasolev andmebaas "hois" täiendatakse. Andmebaasi skriptid on db/install20210128.sql ja db/install20210128_1.sql
EELDUS: kasutaja teab andmebaasi asukohta ja andmebaasi peakasutaja salasõna, oskab kasutada "psql" käsku.

Andmebaasi installeerimiseks:
1. käivitada install20210128.sql ja install20210128_1.sql skript, nt
   
   psql -h devhois -f install20210128.sql 2>&1 | tee log.txt
   
   , kus
   
   -h devhois - andmebaasi host, kus devhois on vastava serveri/hosti nimi, selle asemel võib panna ka IP aadressi. NB! kui skripti käivitamine toimub andmebaasi lokaalses masinas, siis -h parameetrit võib ära jätta
   -f install20210128.sql - install faili nimi
   log.txt - andmebaasi installeerimise logi fail
   
   Installeerimise käigus küsitakse andmebaasi peakasutaja salasõna ja viiakse andmebaasi vastavad muudatused sisse



RAKENDUSE INSTALLEERIMINE:
------------------------------------------------------
1. Backendi paigaldamiseks
	1. Teisendada Fujitsu Laotajas asuv hois_back.jar /opt/hois kausta
	2. Lisada application.properties võõrkeelte saatmiseks vajalikud parameetrid
	

	# aineprogrammi maksimaalne lukustamise aeg minutites
	hois.subject.program.lock=30

	# koolilõunatoetuse õigsuse kontroll
	hois.jobs.meal.support.cron=0 30 2 * * *

	# kui mitu päeva vana (vähemalt) teadet tohib kustutada
	hois.mail.deleteMinDays=90

	
	3. käivitada käsk "java -jar hois_back.jar", rakendus läheb käima.
	
2. Frontendi paigaldamiseks
	1. Kustutada vanad html jms failid: käivitada käsk "rm -Rf /opt/hois/html/*" (nginxist vana seisu tühjendamiseks)
	2. Kopeerida kaasa pandud hois_html kausta sisu /opt/hois/html kausta
	3. Muuta /opt/hois/html/config.js õigeks:
		apiUrl - frontendi server, nt https://localhost/hois_back
		idCardLoginUrl - ID-kaardiga sisselogimiseks seadistatud server (vt proxy_backend.conf, host peab olema avaliku kasutaja jaoks nime järgi kättesaadav, soovitatav kasutada kehtivat sertifikaati, self-signed sertifikaadiga serveris võivad tekkida mõningad probleemid ID-kaardiga autentimisel), nt https://idlogin.devhois
		ekisUrl - EKISe lepingute, käskkirjade, tõendite asukoht, testimiseks kasutatakse https://test.ekis.ee/?wdsturi=3Dpage%3=Dview_dynobj%26pid%3D
		mobileIdInitialDelay - mobiil-ID sisselogimisel mitme millisekundi pärast esimest korda küsitakse (nt 5000)
		mobileIdPollInterval - mobiil-ID sisselogimisel pollimise intervaal millisekundites (nt 4000)
		mobileIdMaxPolls - mobiil-ID sisselogimisel mitu korda maksimaalselt pollitakse, nt 15
		schoolBoardRedirectInSeconds - infokioski tagasi suunamise timeout sekundites
		schoolBoardRefreshInSeconds - infokioski hetke tundide värskendamise aeg sekundites 
     
	4. Üle vaadata JUHANI parameetrid
	   
	   Liidestus JUHANiga on hetkel avalikust vaatest kätte saadav, seega palun lisada nginx serverisse proxy_backend (vms) faili alajaotusse
	   
	   server {
		listen 443;
		listen [::]:443;
		.....
		
	  järgmised read (juhan pidi eraldi urli kaudu kätte saadav olema ja ligipääs pidi olema lubatud ainult kindlalt IP-lt
	  
	     location /hois_back/juhan {
			deny all;
			proxy_pass http://backend/hois_back/juhan;
		}

	5. Immatrikuleeritud/ekmsatrikuleeritud õppijate kätte saamiseks (uus veebiteenus) muuta nginx serveris proxy_backend.conf ning sarnaselt EKISega lisada sinna järgmised read:

      	location /adstudent {
			allow all;
			# deny all;
			proxy_pass http://backend/hois_back/api/student;
		}

		NB! allow all tuleks asendada õppeasutuse serveri IP aadressiga, kes soovib vastavale teenusele ligi saadav
		
		Sarnaselt JUHANiga tuleks vastav teenus teha avalikust vaatest mitte kättesaadavaks
	
		seega palun lisada nginx serverisse proxy_backend (vms) faili alajaotusse
	   
	   server {
		listen 443;
		listen [::]:443;
		.....
		
	  järgmised read (juhan pidi eraldi urli kaudu kätte saadav olema ja ligipääs pidi olema lubatud ainult kindlalt IP-lt
	  
	     location /hois_back/api/student {
			deny all;
			proxy_pass http://backend/hois_back/api/student;
		}
	
	
	

		

