STRUKTUUR:
------------------------------------------------------
README.txt - tarne ja installeerimise kirjeldus
/db - andmebaasi skriptide kaust
/hois_back - rakenduse backendi kood
/hois_front - rakenduse frontendi kood



KIRJELDUS: 
----------------------------------------------------- 
Antud tarne sisaldab j�rgmist anal��sis kirjeldatud funktsionaalsust:
DOK1 - HOIS_analyys_klassifikaatorid_seaded.docx
	   2.1	�ppeasutus
	   2.2	�ppeasutuse struktuur
	   3.1	�ppeasutuse ja �ppeastmete vastavus
	   3.2	�ppesutuse hooned ja ruumid
	   3.3	�petaja amet
	   4	Klassifikaatorite haldamine
	   
DOK2 - HOIS_analyys_oppekavad_ained.docx
	   2.3	Riikliku �ppekava sisestamine s�steemi
	   2.4	Riikliku �ppekava otsing
	   2.5	Kutse�ppe �ppekava ja moodulite rakenduskava sisestamine s�steemi - ainult �ppekava sisestamine (ilma rakenduskava sisestamiseta)
	   2.6	K�rgharidus�ppe �ppekava ja �ppekava versiooni sisestamine s�steemi - ainult �ppekava sisestamine (ilma �ppekava versiooni sisestamiseta)
	   2.7	Kutse�ppe ja k�rgharidus�ppe �ppekava ja versiooni otsing
	   2.8	K�rgharidus�ppe �ppeainete sisestamine s�steemi
	   2.9	K�rgharidus�ppe �ppeainete otsing
	   
Testisikute kasutajanimed, kellena saab �ISi sisse logida ja s�steemi testida (kasutajanimi - isiku nimi, rollid):
test1 - Test1 Kasutaja1, peaadministraator, administratiivne t��taja Tallinna Pol�tehnikumis ja EBS-s
test2 - Test2 Kasutaja2, administratiivne t��taja Tallinna Tehnikak�rgkoolis ja Tallinna Tervishoiu K�rgkoolis, �petaja Tallinna Tervishoiu K�rgkoolis
test3 - Test3 Kasutaja3, administratiivne t��taja Tallinna Pol�tehnikumis
test4 - Test4 Kasutaja4, administratiivne t��taja Tallinna Tervishoiu K�rgkoolis
test5 - Test5 Kasutaja5, �petaja Tallinna Tehnikak�rgkoolis
	   

EELDUS: //siia paneme kirja nt eelmist paketti, esmasel �leandmisel paneme kirja millised vajalikud komponendid peavad olema installeeritud
------------------------------------------------------
1. Serveris on installeeritud (ops�steem Linux, nt CentOS Linux 7.2.x):
	   1. PostgreSQL v 9.5.x
	   2. Java Versioon 1.8
	   3. Nginx Versioon: 1.10
	   4. Redis 3.2
	   5. Gradle 3.x
	   6. Node.js (viimane versioon)

2. Kasutajab omab k�ik vajalikud ligip��sud ja salas�nad

3. Andmebaasi soovitatavad seadistused (vt nt http://pgtune.leopard.in.ua )
	   1. V�iks sisse l�litada andmebaasi p�ringute statistikat "pg_stat_statements": https://www.postgresql.org/docs/9.5/static/pgstatstatements.html
	   2. Andmebaasis v�iks �ra seadistada j�rgmised parameetrid:
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
KIRJELDUS: luuakse uue �ppeinfos�steemi andmebaas "hois", vajalikud tabelid, klassifikaatorid ja testkasutajate andmed. Andmebaasi skriptid asuvad "db" kaustas.

EELDUS: kasutaja teab andmebaasi asukohta ja andmebaasi peakasutaja salas�na, oskab kasutada "psql" k�sku.


Andmebaasi installeerimiseks:
1. k�ivitada install.sql skript, nt
   
   psql -h devhois -f install.sql 2>&1 | tee log.txt
   
   , kus
   
   -h devhois - andmebaasi host, kus devhois on vastava serveri/hosti nimi, selle asemel v�ib panna ka IP aadressi. NB! kui skripti k�ivitamine toimub andmebaasi lokaalses masinas, siis -h parameetrit v�ib �ra j�tta
   -f install.sql - install faili nimi, install.sql ja db_data.sql peavad asuma samas kaustas, install.sql fail kasutab db_data.sql faili
   log.txt - andmebaasi installeerimise logi fail
   
   Installeerimise k�igus k�sitakse andmebaasi peakasutaja salas�na ja luuakse vajalikud tabelid ning andmed

2. Veeenduda, et on loodud "hois" andmebaas, tabelites "school", "person", "classifier" on olemas andmed


RAKENDUSE INSTALLEERIMINE:
------------------------------------------------------
KIRJELDUS: vajalik kood asub githubis https://github.com/hariduspilv/oppeinfo 
EELDUS: 
	Backend:
		Java8
		Gradle
		yhendus redise serveriga(1 instance puhul saab kasutada muid lahenduse 'spring.session.store-type' m2rks6naks), kontrollimiseks saab kasutada k�sku "redis-cli ping", vastus peab olema PONG
		
	Frontend:
		nodejs
		nginx
		nginx on confitud teatud urlil otse l2bi saatma p2rinugid backendi suunas:
		     1) nginx.conf failis on olemas rida, mis viitab nginx/conf.d kaustale, nt     include /etc/nginx/conf.d/*.conf;
			 2) nginx/conf.d kausta tekitada uus *.conf fail, nt "proxy_backend.conf", mille sisu v�iks olla j�rgmine:
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


PAIGALDUS (�ks v�imalus):
NB! XXX - frontendi server, nt devhoisfront
	YYY - backendi server, nt devhoisback
	Igal pool konfides peaks tegema vastavad muudatused
	
	1. Tekitada uus kaust, kuhu pannakse hois asjad peale, nt /opt/hois
	2. Laadida kood github'st ja pakkida see lahti hois kausta
	3. Tekitada hois kausta application.properites fail (vt n�idist application_sample.properties) ja muuta j�rgmised parameetrid:
		1. spring.datasource.url - andmebaasi asukoht, vaikimisi lokaalne masin
		2. spring.datasource.username - kasutajanimi, kes p��seb ligi hois andmebaasile
		3. spring.datasource.password - eelnevalt sisestatud andmebaasi kasutaja salas�na
		4. spring.redis.host - redise asukoht, vaikimisi localhost
		5. spring.redis.port - redise port, vaikimisi 6379
    4. Tekitada hois kausta frontend.config.js ja muuta backendi url: http://XXX/hois_back, kus XXX asemel kirjutada frontendi server
	5. Backendi ehitamiseka paigaldamiseks
		1. Navigeerida hois_back kausta
		2. K�ivitada k�sk "gradle bootRepackage". NB! Kui soovitakse k�ivitada ka teste, tuleks k�ivitada k�sk "gradle build". Veenduda, et l�pus tuleb "BUILD SUCCESSFUL"
		3. Ehitamise k6igus tekkis build/libs/hois_back_xxx.jar fail. See teisendada hois kausta (nt /opt/hois)
		4. Navigeerida tgasi /opt/hois kausta ja k�ivitada k�sk "java -jar (insert-name-here).jar", rakendus l�heb k�ima.
		5. Veenduda et asi t��tab. Selleks brauseris sisestada aadressi reale: "http://YYY:8080/hois_back/school/all", kus YYY asendada vastava hosti nimega
    6. Frontendi ehitamiseks ja paigaldamiseks
		1. Navigeerida hois_front kausta ja kopeerida /opt/hois/frontend.config.js > app/config.js faili, nt
		   cp /opt/hois/frontend.config.js app/config.js
		2. K�ivitada k�sk "npm install"
		3. K�ivitada k�sk "bower install"
		4. K�ivitada k�sk "grunt build"
		5. K�ivitada k�sk "rm -Rf /usr/share/nginx/html/*" (nginxist vana seisu t�hjendamiseks)
		6. K�ivitada k�sk "cp -r dist/. /usr/share/nginx/html/" (kopeeritakse fontendi uus seis nginx/html kausta)
