# Arkkitehtuurikuvaus

## Rakenne

Alla tämänhetkinen, alustava applikaation rakenne pakkauksineen:

![alt alustavaArkkitehtuuri](https://github.com/pprepu/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/arkkitehtuuri.png)

Pakkaus NeljanSuora.ui sisältää JavaFX:llä toteutetun käyttöliittymä, NeljanSuora.dao käyttäjien tietojen pysyväistalletukseen liittyvän koodin, NeljanSuora.domain käyttäjiin viittaavaan User -luokan ja NeljanSuora.controller pääasiallisen sovelluslogiikan kuten kirjautuneeseen käyttäjään liittyvät toiminnot sekä itse pelaamisen toimimisesta huolehtimisen.

## Käyttöliittymä

Tämänhetkinen käyttöliittymä sisältää kaksi osiota: kirjautumisen/uuden käyttäjän luomisen ja itse pelin.

Molemmat näistä on toteutettu omana BorderPane -layoutteina, jotka ladataan yksi kerrallaan UI:n Scene olioon. Käyttöliittymä on toteutettu ohjelmallisesti luokassa neljansuora.ui.NeljanSuoraUi.

Käyttäjä on eriytetty sovelluslogiikasta niin, että käyttäjien toiminnoista huolehtii FileUserDao, joka sekä lataa että tallentaa niihin liittyvän tiedon ja Usercontrol, joka pitää huolta kirjautuneesta käyttäjästä. Vastaavasti Gamecontrollin vastuulla on pelin toiminta sääntöjen puitteissa.

## Sovelluslogiikka

Käyttäjien toimintaan liittyvä keskeinen luokka on User, jota käyttävät pysyväistallennuksessa FileUserDao ja kirjautuneen käyttäjän koordinoinnissa UserControl.

Pelissä tapahtuvien siirtojen oikeellisuudesta ja pelin loppumisen tarkkailusta vastaa Gamecontrol -luokka.

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
