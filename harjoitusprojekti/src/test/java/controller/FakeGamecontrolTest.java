/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import neljansuora.controller.FakeGamecontrol;
/**
 *
 * @author annika
 */
public class FakeGamecontrolTest {
    
    private String[][] gameArea;
    private FakeGamecontrol controller;
    
    @Before
    public void setUp() {
        this.controller = new FakeGamecontrol();
        this.gameArea = new String[7][6];
        for (int i = 0; i < 6; i++) {
            for (int y = 0; y < 7; y++) {
                this.gameArea[y][i] = " ";        
            }
        }
    }
    
    @Test
    public void getPlayableTileFindsCorrectEmptyTiles() {
        resetGameArea();
        int firstIndex = this.controller.getPlayableTile(this.gameArea, 0);
        // on an empty gameboard, playable tile should be the last index of the array
        assertEquals(5, firstIndex);
        // using a helper method similar to the actual playTile method in Ui class Game
        playTile(0, firstIndex);
        // however after a move it is not empty anymore and thus playable tile should be the next index
        int secondIndex = this.controller.getPlayableTile(this.gameArea, 0);
        assertEquals(4, secondIndex);
    }
    
    @Test 
    public void getPlayableTileReturnsMinusOneOnAFullColumn() {
        resetGameArea();
        for (int i = 0; i < this.gameArea[0].length; i++) {
            playTile(0, i); // packs the column in a reverse order
        }
        
        assertEquals(-1, this.controller.getPlayableTile(this.gameArea, 0));
    }
    
    @Test
    public void checkRowsReturnsFalseWhenGameStarts() {
        resetGameArea();
        assertEquals(false, this.controller.checkRows(this.gameArea));
    }
    
    @Test
    public void checkRowsReturnsTrueWhenFourDiscsAreConnected() {
        resetGameArea();
        playTile(5, 0);
        playTile(5, 1);
        playTile(5, 2);
        playTile(5, 3);
        assertEquals(true, this.controller.checkRows(this.gameArea));
    }
    
    @Test
    public void checkColumnsReturnsFalseWhenGameStarts() {
        resetGameArea();
        assertEquals(false, this.controller.checkColumns(this.gameArea));
    }
    
    @Test
    public void checkColumnsReturnsTrueWhenFourDiscsAreConnected() {
        resetGameArea();
        // a reminder, that the 2d-array is turned horisontally in Game class
        playTile(0, 5);
        playTile(1, 5);
        playTile(2, 5);
        playTile(3, 5);
        assertEquals(true, this.controller.checkColumns(this.gameArea));
    }
    
    @Test
    public void bothDiagonalChecksReturnFalseWhenGameStarts() {
        resetGameArea();
        assertEquals(false, this.controller.checkDiagonallyLToR(this.gameArea));
        assertEquals(false, this.controller.checkDiagonallyRToL(this.gameArea));
    }
    
    @Test
    public void checkDiagonallyLToRReturnsTrueWhenFourDiscsAreConnected() {
        resetGameArea();
        playTile(0, 0);
        playTile(1, 1);
        playTile(2, 2);
        playTile(3, 3);
        assertEquals(true, this.controller.checkDiagonallyLToR(this.gameArea));
    }
    
    @Test
    public void checkDiagonallyRToLReturnsTrueWhenFourDiscsAreConnected() {
        resetGameArea();
        playTile(0, 5);
        playTile(1, 4);
        playTile(2, 3);
        playTile(3, 2);
        assertEquals(true, this.controller.checkDiagonallyRToL(this.gameArea));
    }
    
    @Test
    public void gameIsOverReturnsFalseWhenGameStarts() {
        resetGameArea();
        assertEquals(false, this.controller.gameIsOver(this.gameArea));
    }
    
    @Test
    public void gameIsOverReturnsTrueWhenAnyKindOfWinningConditionIsFilled() {
        resetGameArea();
        playTile(5, 0);
        playTile(5, 1);
        playTile(5, 2);
        playTile(5, 3);
        assertEquals(true, this.controller.gameIsOver(this.gameArea));
        
        resetGameArea();
        playTile(0, 5);
        playTile(1, 5);
        playTile(2, 5);
        playTile(3, 5);
        assertEquals(true, this.controller.gameIsOver(this.gameArea));
        
        resetGameArea();
        playTile(0, 0);
        playTile(1, 1);
        playTile(2, 2);
        playTile(3, 3);
        assertEquals(true, this.controller.gameIsOver(this.gameArea));
        
        resetGameArea();
        playTile(0, 5);
        playTile(1, 4);
        playTile(2, 3);
        playTile(3, 2);
        assertEquals(true, this.controller.gameIsOver(this.gameArea));
    }
    
    
    private void resetGameArea() {
        
        for (int i = 0; i < 6; i++) {
            for (int y = 0; y < 7; y++) {
                String currentString = " ";
                this.gameArea[y][i] = currentString;         
            }
        }
    }
    
    private void playTile(int posCol, int posRow) {
        this.gameArea[posCol][posRow] = "X";
    }
}
