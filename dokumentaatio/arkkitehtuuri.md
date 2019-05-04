# Arkkitehtuurikuvaus

## Rakenne

Alla tämänhetkinen applikaation rakenne pakkauksineen:

![alt alustavaArkkitehtuuri](https://github.com/pprepu/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/arkkitehtuuriv2.png)

Kuvaa on yksinkertaistettu niin, että yhteydet FileUserDaosta ja Usercontrolista on piirretty yleisesti koko Ui-pakkaukseen ja ne alkuperäisesti luovaan NeljanSuoraUi -luokkaan. Todellisuudessa ne kuitenkin liittyvät jokaiseen kyseisen pakkaukseen luokkaan, mutta viivaviidakon estämiseksi ne on piirretty edellä mainitulla tavalla. Haluan kuitenkin eksplisiittisesti todeta tämän.

Pakkaus NeljanSuora.ui sisältää JavaFX:llä toteutetun käyttöliittymä, NeljanSuora.dao käyttäjien tietojen pysyväistalletukseen liittyvän koodin, NeljanSuora.domain käyttäjiin viittaavaan User -luokan ja NeljanSuora.controller pääasiallisen sovelluslogiikan kuten kirjautuneeseen käyttäjään liittyvät toiminnot sekä itse pelaamisen toimimisesta huolehtimisen.

## Käyttöliittymä

Käyttöliittymä sisältää tällä hetkellä monta erillistä näkymää
* kirjautuminen
* päämenu
* säännöt
* tilastot
* peli

Sovellusta käynnistettäessä ensimmäinen ladattava näkymä on login -näkymä, jonka tarjoama Scene-olio ladataan sovellusta käynnistettäessä NeljanSuoraUi -luookassa määriteltyyn Stage -olioon. Myös muut näkymät toimivat samalla logiikalla eli ne on eristetty omiksi luokiksiin, jotka liittävät oman layouttinsa sisältävän Scene -olion samaan sovelluksen käyttäjälle näkyvään Stage-olioon.

Käyttöliittymä on toteutettu ohjelmallisesti paketin neljansuora.ui luokissa NeljansuoraUi, Login, MainMenu, Rules, Statistics ja Game.

Game -näkymä sisältää itse pelaamisen lisäksi kirjautumisnäkymän, jossa kaksinpelin toinen osapuoli kirjautuu sisään.

Käyttäjä on eriytetty sovelluslogiikasta niin, että käyttäjien toiminnoista huolehtii FileUserDao, joka sekä lataa että tallentaa niihin liittyvän tiedon ja Usercontrol, joka pitää huolta kirjautuneesta käyttäjästä. Vastaavasti Gamecontrollin vastuulla on pelin toiminta sääntöjen puitteissa.

Kuitenkin Game -luokan ja Gamecontrollerin yhteistyö on varsin läheistä. Gamecontroller suorittaa määrittelynsä käyttämällä Game -luokassa määriteltyä pelialustaa, joka pelaamisen myötä muokkautuu (pelialueen täyttyessä) saman luokan playTile metodin avulla.

## Sovelluslogiikka

Käyttäjien toimintaan liittyvä keskeinen luokka on User, jota käyttävät pysyväistallennuksessa FileUserDao ja kirjautuneen käyttäjän koordinoinnissa UserControl.

![alt User](https://github.com/pprepu/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/user.png)

Usercontrol -luokka hoitaa käyttäjien kirjautumiset sisään sekä ulos. Keskeiset tähän toiminnallisuuteen liittyvät metodit ovat ensinnäkin *normaalikäyttäjän* kohdalla

* void logIn(User user)
* void logOut()

sekä vastaavasti kaksinpelissä toisen pelaajan kohdalla

* void setPlayer2(User user)

Toisaalta Usercontrol pitää myös huolta voittojen ja tappioiden kirjaamisesta itse pelin aikana kaksinpelin molemmille pelaajalle. Tässä keskeisiä metodeja ovat

* void win()
* void lose()
* void p2Win()
* void p2Lose()

Pelissä tapahtuvien siirtojen oikeellisuudesta ja pelin loppumisen tarkkailusta vastaa Gamecontrol -luokka. Kun pelaaja yrittää pelata jotain siirtoa painamalla käyttöliittymässä olevaa nappia (toiminnallisuus tästä kuvataan tarkemmin sekvenssikaaviossa päätoiminnallisuuksissa), metodi *int getPlayableTile(Label[][] gameArea, int index)* pitää huolen siitä, että siirto kohdistuu pelialueen tietyssä sarakkeessa oikealle riville. Lisäksi mikäli kyseinen sarake on jo täynnä, palauttaa se vastaavasti tiedon siitä (luvulla -1), jolloin siirtoa ei käyttöliittymässä tehdä.

Toinen keskeinen Gamecontrolin metodi on *boolean gameIsOver(Label[][] gameArea)*, joka tutkii pelialueen tilanteen ja tarkastaa, ettei se jo sisällä voittavaa yhdistelmää. Apumetodeina sillä on tässä tarkastuksessa

* boolean checkRows(Label[][] gameArea)
* boolean checkColumns(Label[][] gameArea)
* boolean checkDiagonallyLToR(Label[][] gameArea)
* boolean checkDiagonallyRToL(Label[][] gameArea)

## Tietojen pysyväistallennus

Pakkauksen neljansuora.dao luokka FileUserDao tallettaa käyttäjiin liittyvän tiedon tiedostoihin.

Luokan looginen toiminta on rakennettu eristämällä yleiset pysyväistallennukseen liittyvät vaatimukset rajapinnan *UserDao* taakse, jonka FileUserDao toteuttaa. 

### Tiedostot

Sovellus tallettaa käyttäjien tiedot erilliseen tiedostoon.

Sovelluksen juureen sijoitettu konfiguraatiotiedosto config.properties määrittelee tämän tiedoston nimen.

Käyttäjät tallennetaan seuraavassa muodossa:

> nimi;voitot;tappiot

Esim.

> pekka;10;3


eli ensin käyttäjätunnus ja puolipisteellä erotettuna voitot ja sitten tappiot.

## Päätoiminnallisuudet

Kuvataan muutamia sovelluksen päätoimintoja sekvenssikaavioilla.

### Käyttäjän kirjautuminen

Kun Nimi-kenttään on kirjoitettu nimi ja painetaan login -nappia, etenee sovelluksen sisäiset toiminnot seuraavalla tavalla.

![alt kirjautuminen](https://github.com/pprepu/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/loginSekvenssi.png)

Napin painallukseen reagoiva tapahtumankäsittelijä kutsuu FileUserDaon userExists -metodia nimi-kenttään kirjoitetulla käyttäjätunnus-parametrilla ja selvittää FileUserDaon avulla onko käyttäjätunnus olemassa. Mikäli näin on, kirjautuminen onnistuu, jolloin Usercontrol hakee käyttäjätunnusta vastaavan käyttäjän tiedot User oliossa FileUserDaon getUser metodin avulla. Tämän jälkeen Usercontrol asettaa kirjautuneen käyttäjän setCurrentUser metodilla. Lopuksi käyttäjälle näytettävään näkymään tulee teksti onnistuneesta kirjautumisesta.

### Kiekon lisääminen pelialueelle

Kun pelaaja painaa pelialueen alapuolella olevaa insert -nappia (eli tekee sen vuoron siirtonsa) etenee sovellus seuraavalla tavalla:

![alt pelisiirto](https://github.com/pprepu/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/gameSequence.png)

Insert -nappiin liitetty tapahtumankäsittelijä kysyy Gamecontrollilta sen getPlayableTile -metodilla, mihin riville tiettyyn sarakkeeseen kohdistunut siirto lisää pelaajan kiekon. Mikäli sarake ei ole täynnä, palauttaa Gamecontrol sopivan indeksin, johon käyttöliittymään Game-luokassa lisätään siirto pelialueelle määrättyyn kohtaan. Tämän jälkeen Gamecontrollilta kysytään, onko peli tämän siirron jälkeen loppunut jomman kumman voittoon, mitä se selvittää omilla sisäisillä metodeillaan. Mikäli peli voi jatkua, siirtyy vuoro seuraavalle pelaajalle, minkä pelaajat huomaavat pelialueen yläpuolella olevasta muuttuneesta tekstistä, joka kertoo uuden vuorossa olevan pelaajan nimen.

## Ohjelman rakenteeseen jääneet heikkoudet

### Käyttöliittymä

Suurin toisteisuus löytyy ui-pakkauksen luokista Login ja Game, joissa molemmissa kirjataan käyttäjiä Usercontrollin avulla sisään. Ensimmäisen kohdalla kirjaudutaan *normaalikäyttäjällä* ja jälkimmäisellä *toisella pelaajalla*. Näkymä ja toiminnallisuus, joka näihin tilanteisiin liittyy on kuitenkin samanlaista ja koodi täten hyvin toisteista, joten olisi mielekästä luoda erillinen kirjautumiseen liittyvä luokka, joka pystyisi kuitenkin pitämään huolta edellä mainittuihin eroihin näiden näkymien toiminnallisuuksien välillä.
