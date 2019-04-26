
package neljansuora.controller;
import javafx.scene.control.Label;

/**
* Checks the current state of the game and gives information about valid moves and if the game is already over.
*/
public class Gamecontrol {
    
    
    /**
    * Returns the tile that can be played in a given column.
    * 
    * @param    gameArea    Current gametiles representing state of the game.
    * @param    index   Index of the column player has chosen play.
    * 
    * @return Index of the tile that is free. If the column is full, returns -1.
    */
    public int getPlayableTile(Label[][] gameArea, int index) {
        
        //checking playable tile from the bottom
        for (int i = 5; i >= 0; i--) {
            if (gameArea[index][i].getText().equals(" ")) {
                return i;
            }
        }
        return -1;
    }
    
    /**
    * Checks if the game has been already won and returns that information.
    * 
    * @param    gameArea    Current gametiles representing state of the game.
    * 
    * @return True if game is indeed over and false, if it still should continue.
    */
    public boolean gameIsOver(Label[][] gameArea) {
        if (checkRows(gameArea)) { 
            return true;
        }
        if (checkColumns(gameArea)) { 
            return true;
        }
        
        if (checkDiagonallyLToR(gameArea)) {
            return true;
        }
        
        if (checkDiagonallyRToL(gameArea)) {
            return true;
        }
        
        return false;
    }
    
    /**
    * Checks if one of the players has formed a winning combo in any of the rows.
    * 
    * @param    gameArea    Current gametiles representing state of the game.
    * 
    * @return True if such combo exists, false if not.
    */
    private boolean checkRows(Label[][] gameArea) {
        String currentRow = "";
        for (int i = 0; i < 7; i++) {
            for (int y = 0; y < 6; y++) {
                currentRow += gameArea[i][y].getText();
            }
            if (currentRow.contains("XXXX") || currentRow.contains("OOOO")) {
                return true;
            }
            currentRow = "";
        }
        return false;
    }
    
    /**
    * Checks if one of the players has formed a winning combo in any of the columns.
    * 
    * @param    gameArea    Current gametiles representing state of the game.
    * 
    * @return True if such combo exists, false if not.
    */
    private boolean checkColumns(Label[][] gameArea) {
        String currentCol = "";
        for (int i = 0; i < 6; i++) {
            for (int y = 0; y < 7; y++) {
                currentCol += gameArea[y][i].getText();
            }
            if (currentCol.contains("XXXX") || currentCol.contains("OOOO")) {
                return true;
            }
            currentCol = "";
        }
        return false;
    }
    
    /**
    * Checks if one of the players has formed a winning combo in any diagonal tiles starting from upper left to lower right.
    * 
    * @param    gameArea    Current gametiles representing state of the game.
    * 
    * @return True if such combo exists, false if not.
    */
    private boolean checkDiagonallyLToR(Label[][] gameArea) {
        String currentDiag = "";
        int sarake = 0;
        for (int lahtorivi = 0; lahtorivi < 3; lahtorivi++) {
            for (int lahtosarake = 0; lahtosarake < 4; lahtosarake++) {
                sarake = lahtosarake;
                for (int rivi = lahtorivi; rivi < gameArea[0].length; rivi++) {
                    if (sarake < gameArea.length) {
                        currentDiag += gameArea[sarake][rivi].getText();
                        sarake++;
                    }
                }

                if (currentDiag.contains("XXXX") || currentDiag.contains("OOOO")) {
                    return true;
                }

                currentDiag = "";
            }
        }
   
        return false;
    }
    
    /**
    * Checks if one of the players has formed a winning combo in any diagonal tiles starting from upper right to lower left.
    * 
    * @param    gameArea    Current gametiles representing state of the game.
    * 
    * @return True if such combo exists, false if not.
    */
    
    private boolean checkDiagonallyRToL(Label[][] gameArea) {
        String currentDiag = "";
        int sarake = 0;
        for (int lahtorivi = 0; lahtorivi < 3; lahtorivi++) {
            for (int lahtosarake = gameArea.length - 1; lahtosarake >= 3; lahtosarake--) {
                sarake = lahtosarake;
                for (int rivi = lahtorivi; rivi < gameArea[0].length; rivi++) {
                    if (sarake >= 0) {
                        currentDiag += gameArea[sarake][rivi].getText();
                        sarake--;
                    }
                }

                if (currentDiag.contains("XXXX") || currentDiag.contains("OOOO")) {
                    return true;
                }

                currentDiag = "";
            }
        }
   
        return false;
    }

}
