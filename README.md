VERSIOON:  1.2.0/20190215

STRUKTUUR:
------------------------------------------------------
README.md - tarne ja installeerimise kirjeldus
/db - andmebaasi skriptide kaust
/hois_back - rakenduse backendi kood
/hois_front - rakenduse frontendi kood
/hois_ws - rakenduse veebiteenuste kood
/hois_html - rakenduse genereeritud html-id


EELDUS: ver. 1.1.5/20190130
------------------------------------------------------

ANDMEBAASI INSTALLEERIMINE:
------------------------------------------------------

KIRJELDUS: olemasolev andmebaas "hois" täiendatakse. Andmebaasi skripti on db/install20190215.sql
EELDUS: kasutaja teab andmebaasi asukohta ja andmebaasi peakasutaja salasõna, oskab kasutada "psql" käsku.

Andmebaasi installeerimiseks:
1. käivitada install20190215.sql skript, nt
   
   psql -h devhois -f install20190215.sql 2>&1 | tee log.txt
   
   , kus
   
   -h devhois - andmebaasi host, kus devhois on vastava serveri/hosti nimi, selle asemel võib panna ka IP aadressi. NB! kui skripti käivitamine toimub andmebaasi lokaalses masinas, siis -h parameetrit võib ära jätta
   -f install20190215.sql - install faili nimi
   log.txt - andmebaasi installeerimise logi fail
   
   Installeerimise käigus küsitakse andmebaasi peakasutaja salasõna ja viiakse andmebaasi vastavad muudatused sisse



RAKENDUSE INSTALLEERIMINE:
------------------------------------------------------
1. Backendi paigaldamiseks
	1. Teisendada kaasa pandud hois_back.jar /opt/hois kausta
	2. veenduda, et /opt/hois kaustas on olemas muudetud application.properties fail	
	3. Lisada application.properties faili järgmised read (äriregistri päringu parameetrid):
        
        # ariregister configuration
		ariregister.endpoint=https://141.192.105.176/cgi-bin/consumer-proxy   #x-tee serveri asukoht
		ariregister.useridprefix=EE         
		ariregister.userid=EE47909050309  #päringu esitaja isikukood
		ariregister.client.xRoadInstance=ee-dev    #x-tee versioon
		ariregister.client.memberClass=COM     #päringu esitaja asutuse memberClass
		ariregister.client.memberCode=10239452  #päringu esitaja asutuse reg kood
		ariregister.client.subsystemCode=generic-consumer  #päringu esitaja  allsüsteem

		ariregister.service.xRoadInstance=ee-dev  #x-tee versioon
		ariregister.service.memberClass=GOV  #äriregistri memberClass
		ariregister.service.memberCode=70000310
		ariregister.service.subsystemCode=arireg
		ariregister.service.serviceCode=lihtandmed_v1


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
		