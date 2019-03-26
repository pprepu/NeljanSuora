# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovellus on versio [connect four/neljän suora](https://fi.wikipedia.org/wiki/Nelj%C3%A4n_suora) lautapelistä, jossa kiekkoja pudotetaan kehikkoon tavoitteena saada neljän peräkkäisen kiekon suora.

Peliin luodaan käyttäjätunnus, jonka perusteella pidetään kirjaa pelatuista peleistä ja niiden tuloksista.

## Käyttäjät

Sovellus sisältää ainoastaan yhden käyttäjäroolin, *normaalin käyttäjän*, jolla peliä pääsee pelaamaan.

## Perusversion tarjoama toiminnallisuus

### Ennen kirjautumista

* käyttäjätunnuksen luominen
* käyttäjä voi kirjautua sovellukseen

### Kirjautumisen jälkeen

* neljän suora -pelin pelaaminen tietokonetta/yksinkertaista tekoälyä vastaan
* käyttäjätunnuksella pelattujen pelien tulosten tarkastelu
* käyttäjä voi kirjautua ulos sovelluksesta

## Jatkokehitysideoita

Ajan riittäessä perusversiota laajennetaan esim. seuraavilla toiminnallisuksilla:

* käyttäjätunnuksen ja siihen liittyvän pelihistorian poisto
* salasanan lisääminen kirjautumiseen
* pelimuodon sääntöjen muuttaminen, esim. rivien tai sarakkeiden lisääminen *(perusversiossa 7 saraketta ja 6 riviä)* tai tarvittavien peräkkäisten kehikkojen määrän muuttaminen
