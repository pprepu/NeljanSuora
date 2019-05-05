# Testausdokumentti

Ohjelmaa on testattu pääosin jUnitin yksikkö- ja integraatiotesteillä sekä testaamalla ohjelman toimintaa erilaisilla syötteillä.

## Yksikkö- ja integraatiotestaus

### FileUserDao

FileUserDaoa on testattu luomalla testeissä tilapäinen tiedosto käyttäjätiedoille. Tämä on tehty jUnitin [TemporaryFolderin](https://junit.org/junit4/javadoc/4.12/org/junit/rules/TemporaryFolder.html) avulla.

### Sovelluslogiikka

Testien tarkoituksena on ollut tarkastaa kaikkien luokkien ja niihin kuuluvien metodien toimivuus syötteillä, jotka tuottaisivat mahdollisimman erilaisia tuloksia. Tärkeää on ollut varmistaa User -luokan sekä sitä (ja sen metodeita) hyödyntävän kontrolleriluokan eli Usercontrollin toimivuus asianmukaisella tavalla. Usercontrollin testauksessa on samalla kokeiltu sen toimivuutta FileUserDao luokan kanssa.

Keskeinen huomio testien muodostamisessa on se, että Gamecontrolliin kohdistunut testaus on tehty luokkaan FakeGamecontrol. Tämä luokka on nimenomaan testausta varten luotu luokka, jonka ainoana erona on sen metodien parametreissa eli toisin sanoen pelialueen tyypissä. Sovelluksen oikeassa Gamecontrollissa sen metodit tarkistavat pelialueen tilan kaksiulotteisesta Label[][] -taulukosta. Testiluokan luomisen helpottamiseksi FakeGamecontrollin metodien parametrina on sen sijaan kaksiulotteinen String[][] -taulukko. Koska testit on suunnattu testaamaan sitä, miten hyvin Gamecontrol pystyy päättelemään pelitilanteita ja sallittuja siirtoja, ei taulukon tyypin tulisi kuitenkaan vaikuttaa niiden tuloksiin, minkä vuoksi tällaiseen ratkaisuun ollaankin päädytty.

### Testikattavuus

Käyttöliittymäkerrosta lukuunottamatta (ja edellä esitetyt huomiot controller-pakkauksen Gamecontroller sekä FakeGamecontroller -luokista huomioiden) sovelluksen testien rivikattavuus on 99% ja haarautumakattavuus 100%.

![](https://github.com/pprepu/NeljanSuora/blob/master/dokumentaatio/kuvat/testikattavuus.PNG)

Testaamatta jäivät ne tiedostojen käsittelyyn liittyvät tilanteet (esim. tiedosta ei ole tai sitä ei voi jostain syystä lukea/siihen kirjoittaa), jotka synnyttävät poikkeuksia (Exception).


## Järjestelmätestaus

Järjestelmätestaus on suoritettu manuaalisesti.

### Asennus ja konfigurointi

Sovellus on ladattu ja testattu eri OSX-ympäristöissä niin, että se käynnistyshakemistossa on ollut käyttöohjeen mainitsema pakollinen *config.properties* -tiedosto.

Sovellusta on testattu paitsi tilanteissa, joissa käyttäjät sisältävä tiedosto on ollut olemassa, myös niissä tilanteissa, joissa sitä ei ole ollut. Tällöin sovellus on luonut sen itse.

### Toiminnallisuudet

[Määrittelydokumentissa](https://github.com/pprepu/NeljanSuora/edit/master/dokumentaatio/vaatimusmaarittely.md) listatut toiminnallisuudet on käyty läpi. Lisäksi sovelluksen kirjautumis- ja uuden käyttäjän luomisprosesseja on testattu syöttämällä sovelluksessa monenlaisia eri arvoja, kuten selkeästi virheellisiä (tyhjiä) arvoja. Myös sovelluksen generoimia tilastoihin liittyvien diagrammien muotoja on testailtu erilaisilla voitto- sekä tappiomäärillä.

## Sovellukseen jääneet ongelmat

Sovelluksen virheilmoitukset eivät ole järkeviä, kun FileUserDao ei pysty käsittelemään sen kohdetiedostoja.
