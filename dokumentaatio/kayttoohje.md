# Käyttöohje

Lataa projektin [.jar -tiedosto.](https://github.com/pprepu/ot-harjoitustyo/releases/download/viikko5/harjoitusprojekti-1.0-SNAPSHOT.jar)

## Konfigurointi

Ohjelma olettaa, että käynnistyshakemistossa on konfiguraatiotiedosto *config.properties*, joka määrittelee sen tiedoston nimen, johon käyttäjien tiedot tallennetaan.
Tiedoston muoto on seuraava:

> userFile=users.txt

## Ohjelman käynnistäminen

Ohjelma käynnistetään komennolla:

> java -jar harjoitusprojekti-1.0-SNAPSHOT.jar

## Kirjautuminen

Sovellus käynnistyy ensin kirjautumisnäkymään, jossa kirjautuminen onnistuu kirjoittamalla olemassaoleva käyttäjätunnus name -kenttään ja painamalla sen jälkeen login -nappia.

![](https://raw.githubusercontent.com/pprepu/ot-harjoitustyo/master/dokumentaatio/kuvat/loginnakyma.PNG)

Eteneminen peliin tapahtuu painamalla continue -nappia.

## Uuden käyttäjän luominen

Kirjautumisnäkymässä on myös mahdollisuus luoda uusi käyttäjä kirjoittamalla haluttu käyttäjätunnus name -kenttään ja painamalla sen jälkeen create user -nappia.

Mikäli samannimistä käyttäjää ei ole olemassa (tarkistus tapahtuu kirjainten koosta riippumatta), voi uudella käyttäjällä kirjautua saman tien sisään peliin.

## Pelaaminen

Tällä hetkellä pelaaminen tapahtuu painamalla pelialueen alapuolella olevia insert nappeja. Ylhäällä oleva teksti taas kertoo, kenen vuoro tietyllä hetkellä on. Vasemmassa alalaidassa lukee nykyisen kirjautuneen käyttäjän voittojen ja tappoiden määärä.

Tätä näkymää kuitenkin hiotaan vielä.
