VERSIOON:  1.3.1/20190620

STRUKTUUR:
------------------------------------------------------
README.md - tarne ja installeerimise kirjeldus
/db - andmebaasi skriptide kaust
/hois_back - rakenduse backendi kood
/hois_front - rakenduse frontendi kood
/hois_ws - rakenduse veebiteenuste kood
/hois_html - rakenduse genereeritud html-id


EELDUS: ver. 1.3.0/20190527
------------------------------------------------------

ANDMEBAASI INSTALLEERIMINE:
------------------------------------------------------

KIRJELDUS: olemasolev andmebaas "hois" täiendatakse. Andmebaasi skripti on db/install20190620.sql
EELDUS: kasutaja teab andmebaasi asukohta ja andmebaasi peakasutaja salasõna, oskab kasutada "psql" käsku.

Andmebaasi installeerimiseks:
1. käivitada install20190620.sql skript, nt
   
   psql -h devhois -f install20190620.sql 2>&1 | tee log.txt
   
   , kus
   
   -h devhois - andmebaasi host, kus devhois on vastava serveri/hosti nimi, selle asemel võib panna ka IP aadressi. NB! kui skripti käivitamine toimub andmebaasi lokaalses masinas, siis -h parameetrit võib ära jätta
   -f install20190620.sql - install faili nimi
   log.txt - andmebaasi installeerimise logi fail
   
   Installeerimise käigus küsitakse andmebaasi peakasutaja salasõna ja viiakse andmebaasi vastavad muudatused sisse



RAKENDUSE INSTALLEERIMINE:
------------------------------------------------------
1. Backendi paigaldamiseks
	1. Teisendada kaasa pandud hois_back.jar /opt/hois kausta
	2. veenduda, et /opt/hois kaustas on olemas muudetud application.properties fail	
	3. Lisada application.properties faili järgmised read (HarID ühenduse parameetrid ja paar automaatselt käivitavat protsessi) ja muuta vastavad parameetrid õigeks ):
        

		# HarID configuration
		# Klientrakenduse identifikaator, vajab muutmist 
		harid.clientId=aa22dd
		# HarID klientrakenduse parool, vajab muutmist
		harid.clientSecret=XXX
		# HarID soovitud skoop
		harid.scope=personal_code,profile,openid
		# HarID klientrakenduse asukoht
		harid.userAuthorizationUri=https://test.harid.ee/et/authorizations/new
		# HarID klientrakenduse token'i asukoht
		harid.accessTokenUri=https://test.harid.ee/et/access_tokens
		# HarID klientrakenduse jwks asukoht
		harid.jwkUri=https://test.harid.ee/jwks.json
		# Klientrakenduse tagasisuunamis-URL, vajab muutmist ja hiljem ka nginx vms koha seadistamist (vt allpool 3. HarID tagasisuunamise seadistamine)
		harid.redirectUri=https://devhois3.fujitsu.ee/haridcallback
		# HarID issuer
		harid.issuer=https://test.harid.ee
		# HarID kasutaja info asukoht
		harid.userInfoUri=https://test.harid.ee/et/user_info
		

		# Tugiteenuste lõppemise saatmise meeldetuletus
		# every day at 00:01
		hois.jobs.supportService.message.cron=0 1 0 * * *
		# mitu päeva enne lõppemist tuleb saata, vaikimisi 30
		hois.jobs.supportService.message.days=30

		# Küsitluse url saatmine juhendajale, kui tihti kontrollitake kas küsitlus on avatud, peab olema üks kord päevas
		hois.jobs.email.supervisor=0 0 1 * * * 

		
	4. käivitada käsk "java -jar hois_back.jar", rakendus läheb käima.
	
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
	4. Muuta /opt/hois/html/index.html õigeks:
		PLUMBR_ACCOUNT_ID  - Plumbr account'i id
		PLUMBR_APP_NAME - Plumbp rakenduse nimi
	NB! valede parameetrite puhul rakendus töötab pisut aeglasemalt ja konsool annab vigu:
		OPTIONS https://bdr.plumbr.io/api/browser/data/xhr?accountId=PLUMBR_ACCOUNT_ID 401
		Failed to load https://bdr.plumbr.io/api/browser/data/xhr?accountId=PLUMBR_ACCOUNT_ID: Response for preflight does not have HTTP ok status.

3. TARA tagasisuunamise seadistamine
	
	Õnnestunud sisse logimise puhul kasutaja suunatakse tagasi Tahvli rakendusse. Vastav url peab olema RIAs registreeritud ja Tahvlis õigesti seadistatud. 
	Sõltumata asukohast tara.redirectUri kirjeldatud parameeter peab lõppkokkuvõttes suunama Tahvli rakendusse http://backend/hois_back/taraCallback koos kõikide parameetritega.
	
	Üks seadistamise viis on seadistada Tahvli nginx, lisades sinna uus location /callback
	
	location /haridcallback {
		proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
		proxy_set_header Host $http_host;
		proxy_redirect off;
		proxy_connect_timeout      240;
		proxy_send_timeout         240;
		proxy_read_timeout         240;
	    client_max_body_size 100M;
		proxy_pass http://backend/hois_back/haridCallback$is_args$args;
	}

    Pärast seadistamist nginx vajab restarti
	
		