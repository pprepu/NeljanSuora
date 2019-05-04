# Neljän suora -peli

Sovelluksella on mahdollista pelata lokaalisti kaksinpelinä [neljän suora](https://fi.wikipedia.org/wiki/Nelj%C3%A4n_suora) -peliä, jossa on tarkoituksena laittaa neljä omaa kiekkoa pelialueelle vierekkäin samalla estäen vastustajaa tekemästä samaa. Lisäksi sovellukseen voi luoda oman käyttäjätunnuksen, jonka perusteella vanhojen pelien tuloksia tilastoidaan. Myös yleisiä kaikki käyttäjät huomioivia voittotilastoja on mahdollista seurata.


## Dokumentaatio

[Käyttöohje](https://github.com/pprepu/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)

[Vaatimusmäärittely](https://github.com/pprepu/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Arkkitehtuuri](https://github.com/pprepu/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

[Testausdokumentti](https://github.com/pprepu/NeljanSuora/edit/master/dokumentaatio/testaus.md)

[Työaikakirjanpito](https://github.com/pprepu/ot-harjoitustyo/blob/master/dokumentaatio/tyoaikakirjanpito.md)

## Releaset

[Viikon 5 release](https://github.com/pprepu/ot-harjoitustyo/releases/tag/viikko5)
[Loppupalautus](https://github.com/pprepu/NeljanSuora/releases/tag/loppupalautus)

## Komentorivitoiminnot

### Suoritettavan .jarin generointi

Komento
> mvn package

luo *target* hakemistoon suoritettavan jar-tiedoston harjoitusprojekti-1.0-SNAPSHOT.jar

### Testaus

Testit suoritetaan komennolla
> mvn test

Testikattavuusraportti luodaan komennolla
> mvn test jacoco:report

Kattavuusraportti löytyy tiedostosta *target/site/jacoco/index.html*

### JavaDoc

JavaDoc generoidaan komennolla
> mvn javadoc:javadoc

Generoitu JavaDoc löytyy tiedostosta *target/site/apidocs/index.html*

### Checkstyle

Tiedostoon checkstyle.xml määritellyt tarkistukset suoritetaan komennolla

> mvn jxr:jxr checkstyle:checkstyle

Tarkistuksen tulokset löytyvät selaimella tiedostosta *target/site/checkstyle.html*
