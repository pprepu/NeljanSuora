# Käyttöohje

Lataa projektin [.jar -tiedosto](https://github.com/pprepu/NeljanSuora/releases/download/loppupalautus/harjoitusprojekti-1.0-SNAPSHOT.jar) sekä konfiguraatiotiedosto [config.properties](https://github.com/pprepu/NeljanSuora/releases/download/loppupalautus/config.properties)

## Konfigurointi

Ohjelma olettaa, että käynnistyshakemistossa on konfiguraatiotiedosto *config.properties*, joka määrittelee sen tiedoston nimen, johon käyttäjien tiedot tallennetaan.
Tiedoston sisältämä määrittely on tehty seuraavalla tavalla:

> userFile=users.txt

## Ohjelman käynnistäminen

Ohjelma käynnistyy komennolla:

> java -jar harjoitusprojekti-1.0-SNAPSHOT.jar

## Kirjautuminen

Sovellus käynnistyy ensin kirjautumisnäkymään, jossa kirjautuminen onnistuu kirjoittamalla olemassaoleva käyttäjätunnus name -kenttään ja painamalla sen jälkeen login -nappia.

![](https://raw.githubusercontent.com/pprepu/ot-harjoitustyo/master/dokumentaatio/kuvat/loginnakyma.PNG)

Eteneminen peliin tapahtuu painamalla continue -nappia.

## Uuden käyttäjän luominen

Kirjautumisnäkymässä on myös mahdollisuus luoda uusi käyttäjä kirjoittamalla haluttu käyttäjätunnus name -kenttään ja painamalla sen jälkeen create user -nappia.

Mikäli samannimistä käyttäjää ei ole olemassa (tarkistus tapahtuu kirjainten koosta riippumatta), voi uudella käyttäjällä kirjautua saman tien sisään peliin.

## Navigointi

Kirjautumisnäkymän jälkeen sovellus avaa päävalikon josta pääsee eteenpäin painamalla haluttua tekstiä. Myös ohjelmasta poistuminen ja uloskirjautuminen onnistuu tästä näkymästä.

![](https://raw.githubusercontent.com/pprepu/ot-harjoitustyo/master/dokumentaatio/kuvat/menuNakyma.PNG)

## Pelaaminen

Pelaaminen tapahtuu painamalla pelialueen alapuolella olevia insert nappeja. Pelialueen yläpuolella oleva teksti taas kertoo, kenen vuoro tietyllä hetkellä on. Se myös ilmoittaa, mikäli peli päättyy jomman kumman voittoon. Alhaalla lukee molempien kirjautuneiden pelaajien voittojen ja tappioiden määrät.

![](https://raw.githubusercontent.com/pprepu/ot-harjoitustyo/master/dokumentaatio/kuvat/peliNakyma.PNG)

Lisäksi pelin voi aloittaa alusta painamalla Reset -nappia. Palaaminen päävalikkoon onnistuu painamalla BACK -nappia.
