/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author annika
 */
public class KassapaateTest {
    
    Kassapaate paate;
    Maksukortti kortti;

    @Before
    public void setUp() {
        paate = new Kassapaate();
        kortti = new Maksukortti(1000);
    }

    @Test
    public void luotuKassapaateOlemassa() {
        assertTrue(paate!=null);      
    }
    
    @Test
    public void rahamaaraAlussaOikein() {
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void myydytEdullisetLounaatAlussaNolla() {
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void myydytMaukkaatLounaatAlussaNolla() {
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void rahatKassassaKasvaaKunEdullinenKateismaksuRiittava() {
        paate.syoEdullisesti(300);
        assertEquals(100240, paate.kassassaRahaa());
    }
    
    @Test
    public void vaihtorahojenMaaraOikeaKunEdullinenKateismaksuRiittava() {
        assertEquals(60, paate.syoEdullisesti(300));
    }
    
    @Test
    public void edullistenLounaidenMaaraKasvaaKunEdullinenKateismaksuRiittava() {
        paate.syoEdullisesti(300);
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void kassassaOlevaRahamaaraEiMuutuEdullisessaJosKateismaksuEiRiittava() {
        paate.syoEdullisesti(150);
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void myytyjenLounaidenMaaraEiKasvaEdullisessaJosKateismaksuEiRiittava() {
        paate.syoEdullisesti(150);
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void palauttaaKokoRahasummanEdullisessaJosKateismaksuEiRiittava() {
        assertEquals(150, paate.syoEdullisesti(150));
    }
    
    @Test
    public void rahatKassassaKasvaaKunMaukasKateismaksuRiittava() {
        paate.syoMaukkaasti(500);
        assertEquals(100400, paate.kassassaRahaa());
    }
    
    @Test
    public void vaihtorahojenMaaraOikeaKunMaukasKateismaksuRiittava() {
        assertEquals(100, paate.syoMaukkaasti(500));
    }
    
    @Test
    public void maukkaidenLounaidenMaaraKasvaaKunKateismaksuRiittava() {
        paate.syoMaukkaasti(500);
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kassassaOlevaRahamaaraEiMuutuMaukkaassaJosKateismaksuEiRiittava() {
        paate.syoMaukkaasti(300);
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void myytyjenLounaidenMaaraEiKasvaMaukkaassaJosKateismaksuEiRiittava() {
        paate.syoMaukkaasti(300);
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void palauttaaKokoRahasummanMaukkaassaJosKateismaksuEiRiittava() {
        assertEquals(300, paate.syoMaukkaasti(300));
    }
    
    //testit lisäksi maksukortilla
    @Test
    public void korttiostossaEdullinenVeloitetaanOikein() {
        paate.syoEdullisesti(kortti);
        assertEquals(760, kortti.saldo());
    }
    
    @Test
    public void korttiostossaEdullinenPalauttaaTrueKunRiittavastiRahaa() {
        assertEquals(true, paate.syoEdullisesti(kortti));
    }
    
    @Test
    public void edullistenLounaidenMaaraKasvaaKunKortillaRahaa() {
        paate.syoEdullisesti(kortti);
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void korttiostoEdulliseenEiMuutaSaldoaKortissaJosEiTarpeeksiRahaa() {
        //kortin saldo kahteen euroon
        paate.syoMaukkaasti(kortti);
        paate.syoMaukkaasti(kortti);
        
        paate.syoEdullisesti(kortti);
        
        assertEquals(200, kortti.saldo());
    }
    
    @Test
    public void korttiostoEdulliseenEiMuutaMyytyjenLounaidenMaaraaJosEiTarpeeksiRahaa() {
        //kortin saldo kahteen euroon
        paate.syoMaukkaasti(kortti);
        paate.syoMaukkaasti(kortti);
        
        paate.syoEdullisesti(kortti);
        
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void korttiostoEdulliseenPalauttaaFalseJosEiTarpeeksiRahaa() {
        //kortin saldo kahteen euroon
        paate.syoMaukkaasti(kortti);
        paate.syoMaukkaasti(kortti);
        
        assertEquals(false, paate.syoEdullisesti(kortti));
    
    }
    
    @Test
    public void korttiostoMaukkaaseenEiMuutaSaldoaKortissaJosEiTarpeeksiRahaa() {
        //kortin saldo kahteen euroon
        paate.syoMaukkaasti(kortti);
        paate.syoMaukkaasti(kortti);
        
        paate.syoMaukkaasti(kortti);
        
        assertEquals(200, kortti.saldo());
    }
    
    @Test
    public void korttiostoMaukkaaseenEiMuutaMyytyjenLounaidenMaaraaJosEiTarpeeksiRahaa() {
        //kortin saldo 2,5 euroon
        
        paate.syoEdullisesti(kortti);
        paate.syoEdullisesti(kortti);
        paate.syoEdullisesti(kortti);
        
        paate.syoMaukkaasti(kortti);
        
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void korttiostoMaukkaaseenPalauttaaFalseJosEiTarpeeksiRahaa() {
        //kortin saldo kahteen euroon
        paate.syoMaukkaasti(kortti);
        paate.syoMaukkaasti(kortti);
        
        assertEquals(false, paate.syoMaukkaasti(kortti));
    
    }
    
    @Test
    public void korttiostoEiMuutaKassanSaldoa() {
        paate.syoMaukkaasti(kortti);
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void rahanLatausMuuttaaKortinSaldoa() {
        paate.lataaRahaaKortille(kortti, 500);
        assertEquals(1500, kortti.saldo());
    }
    
    @Test
    public void rahanLatausMuuttaaKassanSaldoa() {
        paate.lataaRahaaKortille(kortti, 500);
        assertEquals(100500, paate.kassassaRahaa());
    }
    
    @Test
    public void rahanLatausEiMuutaKortinSaldoaJosSummaNegatiivinen() {
        paate.lataaRahaaKortille(kortti, -500);
        assertEquals(1000, kortti.saldo());
    }
    
    @Test
    public void rahanLatausEiMuutaKassanSaldoaJosSummaNegatiivinen() {
        paate.lataaRahaaKortille(kortti, -500);
        assertEquals(100000, paate.kassassaRahaa());
    }
}
