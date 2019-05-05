# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovellus on versio [connect four/neljän suora](https://fi.wikipedia.org/wiki/Nelj%C3%A4n_suora) lautapelistä, jossa kiekkoja pudotetaan kehikkoon tavoitteena saada neljän peräkkäisen kiekon suora.

Peliin luodaan käyttäjätunnus, jonka perusteella pidetään kirjaa pelatuista peleistä ja niiden tuloksista. Käyttäjätunnuksia voi luoda useita ja niiden pelaamia pelejä seurataan tallentamalla käyttäjiä koskevat tiedot omaan tiedostoonsa.

## Käyttäjät

Sovellus sisältää ainoastaan kaksi käyttäjäroolia, normaalin käyttäjän ja kaksinpelissä toisen pelaajan. 
*Normaali käyttäjä* on se rooli, jolla peliin ensiin kirjaudutaan. Tässä roolissa pääsee pelaamisen lisäksi katsastelemaan omia voitto- ja tappiotilastojaan tilasto-osiossa.
*Toinen pelaaja* kirjautuu sisään ennen kuin kaksinpeliä aletaan pelaamaan. Kun peli(t) on pelattu, kirjataan hänet kuitenkin ulos sovelluksesta.

## Käyttöliittymä

Sovelluksessa on graafinen käyttöliittymä, joka tarjoaa useita näkymiä käyttäjälle.

Sovellus käynnistyy kirjautumisnäkymään, josta onnistunut kirjautuminen siirtää käyttäjän päävalikkoon, josta taas pääsee sekä sääntöjen lukemiseen, pelialueelle, tilastojen tarkasteluun että takaisin kirjautumisnäkymään kirjautumalla ulos sovelluksesta. Päävalikosta sovellus voidaan myös sulkea.

## Perusversion tarjoama toiminnallisuus

### Ennen kirjautumista

* käyttäjätunnuksen luominen
  * käyttäjätunnus voi sisältää erikoismerkkejä ja se voi olla minkä pituinen tahansa, muttei kuitenkaan tyhjä
* käyttäjä voi kirjautua sovellukseen

### Kirjautumisen jälkeen

* neljän suora -pelin pelaaminen lokaalisti toista käyttäjää vastaan.
* käyttäjätunnuksella pelattujen pelien tulosten tarkastelu
* eniten voittoja omaavien käyttäjien tilastojen tarkastelu
* käyttäjä voi kirjautua ulos sovelluksesta

## Jatkokehitysideoita

Ajan riittäessä perusversiota laajennetaan esim. seuraavilla toiminnallisuuksilla:

* käyttäjätunnuksen ja siihen liittyvän pelihistorian poisto
* salasanan lisääminen kirjautumiseen
* pelimuodon sääntöjen muuttaminen, esim. rivien tai sarakkeiden lisääminen *(perusversiossa 7 saraketta ja 6 riviä)* ja/tai tarvittavien peräkkäisten kiekkojen määrän muuttaminen
* pelaaminen yksinkertaista tekoälyä vastaan
* monimutkaisempien tilastojen ylläpito, esim. käyttäjäkohtaiset viimeisen 10 pelin tulokset
