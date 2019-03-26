package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(1000);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void konstruktoriAsettaaSaldonOikein() {
        assertEquals("saldo: 10.0", kortti.toString());
    }
    
    @Test
    public void kortilleVoiLadataRahaa() {
        kortti.lataaRahaa(1000);
        assertEquals("saldo: 20.0", kortti.toString());
    }
    
    @Test
    public void saldoVaheneeOikeinKunRahaaOtetaan() {
        kortti.otaRahaa(500);
        assertEquals("saldo: 5.0", kortti.toString());
        
    }
    
    @Test
    public void saldoMetodiToimii() {
        assertEquals(1000, kortti.saldo());
    }
    
    @Test
    public void saldoEiMuutuKunRahatEiRiita() {
        kortti.otaRahaa(500000);
        assertEquals("saldo: 10.0", kortti.toString());
    }
    
    @Test
    public void otaRahaaPalauttaaTrueJosRahatRiittaa() {
        boolean vastaus = kortti.otaRahaa(500);
        assertEquals(true, vastaus);
    }
    
    @Test
    public void otaRahaaPalauttaaFalseJosRahatRiittaa() {
        boolean vastaus = kortti.otaRahaa(50000);
        assertEquals(false, vastaus);
    }
}
