# Testausdokumentti

Ohjelmaa on testattu pääosin jUnit-yksikkötesteillä sekä testaamalla ohjelman toimintaa erilaisilla syötteillä.

## Yksikkötestaus

### FileUserDao

FileUserDaoa on testattu luomalla testeissä tilapäinen tiedosto käyttäjätiedoille. Tämä on tehty jUnitin [TemporaryFolderin](https://junit.org/junit4/javadoc/4.12/org/junit/rules/TemporaryFolder.html) avulla.

### Sovelluslogiikka

Testien tarkoituksena on ollut tarkastaa kaikkien luokkien ja niihin kuuluvien metodien toimivuus syötteillä, jotka tuottaisivat mahdollisimman erilaisia tuloksia.
