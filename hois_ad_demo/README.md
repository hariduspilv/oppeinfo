Tahvli ja õppeasutuse AD õppijate andmete sünkroniseerimise näidisprogramm.
----------------------------------------------------------------------------

1. Kopeerida ad_demo.jar ja application.properties serverisse, kuhu on paigaldatud java 1.8.x
2. application.properties failis seadistada järgmised parameetrid:

#õppeasutuse EHISe kooli ID
hois.school.ehis_value=1022
#õppeasutuse AD server ja port
hois.school.ad_url=DEVTESTAD2.devtest.test:636
#õppeasutuse AD serveri domeen
hois.school.ad_domain=@devtest.test
#õppeasutuse AD serveri põhipuu, kuhu pannakse kasutajad
hois.school.ad_base=OU=XXX,DC=devtest,DC=test
#välja nimi, kuhu pannakse kasutaja isikukood/unikaalne kood, vajalik ka Tahvlisse sisse logimiseks
hois.school.ad_idcode_field=employeeNumber
#õppeasutuse AD serveri puu, kuhu pannakse kustutatud kasutajad
hois.school.ad_disabled_base=OU=suspended_users,OU=Arhiiv,DC=devtest,DC=test

# admin user, vajalik AD andmetega manipuleerimiseks
hois.admin.user=Administrator
hois.admin.password=Admin

# kui mitu päeva tagasi alates tänasest vaadetakse immatrikuleeritud ja eksmatrikuleeritud õppijaid
hois.sync.days=1
# logi failide kaust
log.path=logs


# immatrikuleeritud õppijate andmete tõmbamise automaatse jobi käivitamise aeg, iga öö kell 01:00
hois.jobs.immat.cron=0 0 1 * * *
# eksmatrikuleeritud õppijate andmete tõmbamise automaatse jobi käivitamise aeg, iga öö kell 01:00
hois.jobs.exmat.cron=0 0 1 * * *

# Tahvli põhiteenuse asukoht, ligipääs peab olema lubatud tahvli administraatorite poolt
hois.backend.student.endpoint=http://TAHVEL/adstudent

# näidisporgrammi port
server.port=8091

3. Programm jookseb iseseiva tükina ja tõmbab andmed automaatselt vastavalt seadistatud ajale

4. Kuna tegemist on java programmiga, siis tuleks teha keystore (võib ka windows'is) ja lisada sinna lisada nii tahvli kui ka asutuse AD sertifikaadid

5. Kopeerida vastav kestore serverisse demo programmi juurde

6. Programmi käivitamiseks tuleb navigeerida ad_demo.jar kausta ja käivitada programm järgmise käsuga:

nohup java -jar -Djavax.net.ssl.trustStore=keystore -Djavax.net.ssl.trustStorePassword=changeit ad_demo.jar &



