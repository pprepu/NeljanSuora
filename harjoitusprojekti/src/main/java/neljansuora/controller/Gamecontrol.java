
package neljansuora.controller;
import javafx.scene.control.Label;

public class Gamecontrol {
    
    public int getPlayableTile(Label[][] gameArea, int index) {
        
        //checking playable tile from the bottom
        for (int i = 5; i >= 0; i--) {
            if (gameArea[index][i].getText().equals(" ")) {
                return i;
            }
        }
        return -1;
    }
    
    public boolean gameIsOver(Label[][] gameArea) {
        if (checkRows(gameArea)) { 
            return true;
        }
        if (checkColumns(gameArea)) { 
            return true;
        }
        
        if (checkDiagonallyLeftToRight(gameArea)) {
            return true;
        }
        
        if (checkDiagonallyRightToLeft(gameArea)) {
            return true;
        }
        
        return false;
    }
    
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
    
    private boolean checkDiagonallyLeftToRight(Label[][] gameArea) {
        String currentDiag = "";
        int sarake = 0;
        for (int lahtorivi = 0; lahtorivi < 3; lahtorivi++) {
            for (int lahtosarake = 0; lahtosarake < 4; lahtosarake++) {
                sarake = lahtosarake;
                for (int rivi = lahtorivi; rivi < gameArea[0].length; rivi++) {
                    if (sarake >= gameArea.length) {
                        // out of bounds, do nothing
                    } else {
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
    
    private boolean checkDiagonallyRightToLeft(Label[][] gameArea) {
        String currentDiag = "";
        int sarake = 0;
        for (int lahtorivi = 0; lahtorivi < 3; lahtorivi++) {
            for (int lahtosarake = gameArea.length - 1; lahtosarake >= 3; lahtosarake--) {
                sarake = lahtosarake;
                for (int rivi = lahtorivi; rivi < gameArea[0].length; rivi++) {
                    if (sarake < 0) {
                        // out of bounds, do nothing
                    } else {
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
