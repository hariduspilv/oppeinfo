STRUKTUUR:
------------------------------------------------------
README.txt - tarne ja installeerimise kirjeldus
/db - andmebaasi skriptide kaust
/hois_back - rakenduse backendi kood
/hois_front - rakenduse frontendi kood



KIRJELDUS: 
----------------------------------------------------- 
Antud tarne sisaldab järgmist analüüsis kirjeldatud funktsionaalsust:
DOK1 - HOIS_analyys_klassifikaatorid_seaded.docx
	   2.1	Õppeasutus
	   2.2	Õppeasutuse struktuur
	   3.1	Õppeasutuse ja õppeastmete vastavus
	   3.2	Õppesutuse hooned ja ruumid
	   3.3	Õpetaja amet
	   4	Klassifikaatorite haldamine
	   
DOK2 - HOIS_analyys_oppekavad_ained.docx
	   2.3	Riikliku õppekava sisestamine süsteemi
	   2.4	Riikliku õppekava otsing
	   2.5	Kutseõppe õppekava ja moodulite rakenduskava sisestamine süsteemi - ainult õppekava sisestamine (ilma rakenduskava sisestamiseta)
	   2.6	Kõrgharidusõppe õppekava ja õppekava versiooni sisestamine süsteemi - ainult õppekava sisestamine (ilma õppekava versiooni sisestamiseta)
	   2.7	Kutseõppe ja kõrgharidusõppe õppekava ja versiooni otsing
	   2.8	Kõrgharidusõppe õppeainete sisestamine süsteemi
	   2.9	Kõrgharidusõppe õppeainete otsing
	   
Testisikute kasutajanimed, kellena saab ÕISi sisse logida ja süsteemi testida (kasutajanimi - isiku nimi, rollid):
test1 - Test1 Kasutaja1, peaadministraator, administratiivne töötaja Tallinna Polütehnikumis ja EBS-s
test2 - Test2 Kasutaja2, administratiivne töötaja Tallinna Tehnikakõrgkoolis ja Tallinna Tervishoiu Kõrgkoolis, õpetaja Tallinna Tervishoiu Kõrgkoolis
test3 - Test3 Kasutaja3, administratiivne töötaja Tallinna Polütehnikumis
test4 - Test4 Kasutaja4, administratiivne töötaja Tallinna Tervishoiu Kõrgkoolis
test5 - Test5 Kasutaja5, õpetaja Tallinna Tehnikakõrgkoolis
	   

EELDUS: //siia paneme kirja nt eelmist paketti, esmasel üleandmisel paneme kirja millised vajalikud komponendid peavad olema installeeritud
------------------------------------------------------
1. Serveris on installeeritud (opsüsteem Linux, nt CentOS Linux 7.2.x):
	   1. PostgreSQL v 9.5.x
	   2. Java Versioon 1.8
	   3. Nginx Versioon: 1.10
	   4. Redis 3.2
	   5. Gradle 3.x
	   6. Node.js (viimane versioon)

2. Kasutajab omab kõik vajalikud ligipääsud ja salasõnad

3. Andmebaasi soovitatavad seadistused (vt nt http://pgtune.leopard.in.ua )
	   1. Võiks sisse lülitada andmebaasi päringute statistikat "pg_stat_statements": https://www.postgresql.org/docs/9.5/static/pgstatstatements.html
	   2. Andmebaasis võiks ära seadistada järgmised parameetrid:
			max_connections = 100
			shared_buffers = 256MB
			effective_cache_size = 768MB
			work_mem = 2621kB
			maintenance_work_mem = 64MB
			min_wal_size = 1GB
			max_wal_size = 2GB
			checkpoint_completion_target = 0.7
			wal_buffers = 7864kB
			default_statistics_target = 100


ANDMEBAASI INSTALLEERIMINE:
------------------------------------------------------
KIRJELDUS: luuakse uue õppeinfosüsteemi andmebaas "hois", vajalikud tabelid, klassifikaatorid ja testkasutajate andmed. Andmebaasi skriptid asuvad "db" kaustas.

EELDUS: kasutaja teab andmebaasi asukohta ja andmebaasi peakasutaja salasõna, oskab kasutada "psql" käsku.


Andmebaasi installeerimiseks:
1. käivitada install.sql skript, nt
   
   psql -h devhois -f install.sql 2>&1 | tee log.txt
   
   , kus
   
   -h devhois - andmebaasi host, kus devhois on vastava serveri/hosti nimi, selle asemel võib panna ka IP aadressi. NB! kui skripti käivitamine toimub andmebaasi lokaalses masinas, siis -h parameetrit võib ära jätta
   -f install.sql - install faili nimi, install.sql ja db_data.sql peavad asuma samas kaustas, install.sql fail kasutab db_data.sql faili
   log.txt - andmebaasi installeerimise logi fail
   
   Installeerimise käigus küsitakse andmebaasi peakasutaja salasõna ja luuakse vajalikud tabelid ning andmed

2. Veeenduda, et on loodud "hois" andmebaas, tabelites "school", "person", "classifier" on olemas andmed


RAKENDUSE INSTALLEERIMINE:
------------------------------------------------------
KIRJELDUS: vajalik kood asub githubis https://github.com/hariduspilv/oppeinfo 
EELDUS: 
	Backend:
		Java8
		Gradle
		yhendus redise serveriga(1 instance puhul saab kasutada muid lahenduse 'spring.session.store-type' m2rks6naks), kontrollimiseks saab kasutada käsku "redis-cli ping", vastus peab olema PONG
		
	Frontend:
		nodejs
		nginx
		nginx on confitud teatud urlil otse l2bi saatma p2rinugid backendi suunas:
		     1) nginx.conf failis on olemas rida, mis viitab nginx/conf.d kaustale, nt     include /etc/nginx/conf.d/*.conf;
			 2) nginx/conf.d kausta tekitada uus *.conf fail, nt "proxy_backend.conf", mille sisu võiks olla järgmine:
				server {
					listen 80;
					listen [::]:80;
					location /hois_back/ {
							proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
							proxy_set_header Host $http_host;
							proxy_redirect off;
							proxy_connect_timeout      240;
							proxy_send_timeout         240;
							proxy_read_timeout         240;
							proxy_pass http://backend/hois_back/;
					}


						gzip on;
						gzip_disable "msie6";

						gzip_vary on;
						gzip_proxied any;
						gzip_comp_level 6;
						gzip_buffers 16 8k;
						gzip_http_version 1.1;

						gzip_types application/json;

				}

				upstream backend {
						server YYY:8080 fail_timeout=0;
				}


PAIGALDUS (üks võimalus):
NB! XXX - frontendi server, nt devhoisfront
	YYY - backendi server, nt devhoisback
	Igal pool konfides peaks tegema vastavad muudatused
	
	1. Tekitada uus kaust, kuhu pannakse hois asjad peale, nt /opt/hois
	2. Laadida kood github'st ja pakkida see lahti hois kausta
	3. Tekitada hois kausta application.properites fail (vt näidist application_sample.properties) ja muuta järgmised parameetrid:
		1. spring.datasource.url - andmebaasi asukoht, vaikimisi lokaalne masin
		2. spring.datasource.username - kasutajanimi, kes pääseb ligi hois andmebaasile
		3. spring.datasource.password - eelnevalt sisestatud andmebaasi kasutaja salasõna
		4. spring.redis.host - redise asukoht, vaikimisi localhost
		5. spring.redis.port - redise port, vaikimisi 6379
    4. Tekitada hois kausta frontend.config.js ja muuta backendi url: http://XXX/hois_back, kus XXX asemel kirjutada frontendi server
	5. Backendi ehitamiseka paigaldamiseks
		1. Navigeerida hois_back kausta
		2. Käivitada käsk "gradle bootRepackage". NB! Kui soovitakse käivitada ka teste, tuleks käivitada käsk "gradle build". Veenduda, et lõpus tuleb "BUILD SUCCESSFUL"
		3. Ehitamise k6igus tekkis build/libs/hois_back_xxx.jar fail. See teisendada hois kausta (nt /opt/hois)
		4. Navigeerida tgasi /opt/hois kausta ja käivitada käsk "java -jar (insert-name-here).jar", rakendus läheb käima.
		5. Veenduda et asi töötab. Selleks brauseris sisestada aadressi reale: "http://YYY:8080/hois_back/school/all", kus YYY asendada vastava hosti nimega
    6. Frontendi ehitamiseks ja paigaldamiseks
		1. Navigeerida hois_front kausta ja kopeerida /opt/hois/frontend.config.js > app/config.js faili, nt
		   cp /opt/hois/frontend.config.js app/config.js
		2. Käivitada käsk "npm install"
		3. Käivitada käsk "bower install"
		4. Käivitada käsk "grunt build"
		5. Käivitada käsk "rm -Rf /usr/share/nginx/html/*" (nginxist vana seisu tühjendamiseks)
		6. Käivitada käsk "cp -r dist/. /usr/share/nginx/html/" (kopeeritakse fontendi uus seis nginx/html kausta)
