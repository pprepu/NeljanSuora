
package NeljanSuora.controller;
import javafx.scene.control.Label;

public class Gamecontrol {
    
    public int getPlayableTile(Label[][] gameArea, int index) {
        
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

}
