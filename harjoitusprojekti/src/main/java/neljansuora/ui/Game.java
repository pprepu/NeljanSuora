
package neljansuora.ui;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import neljansuora.controller.Gamecontrol;
import neljansuora.controller.Usercontrol;
import neljansuora.dao.FileUserDao;
import static neljansuora.ui.NeljanSuoraUi.HEIGHT;
import static neljansuora.ui.NeljanSuoraUi.WIDTH;


public class Game {
    private Usercontrol userControl;
    private FileUserDao fileUserDao;
    private Gamecontrol controller;
    private Label[][] gameArea;
    private Button[] buttons;
    private String playerTurn;
    private boolean gameOver;
    private Scene prevScene;
    
    public Game (Usercontrol userControl, FileUserDao fileUserDao, Scene prevScene) {
        this.userControl = userControl;
        this.fileUserDao = fileUserDao;
        this.controller = new Gamecontrol();
        this.gameArea = new Label[7][6]; // turned horisontally
        this.buttons = new Button[7];
        this.playerTurn = "X";
        this.gameOver = false;
        this.prevScene = prevScene;
    }
    
    public void display(Stage window) {
        
        //create mainlayout for the game
        VBox mainLayout = new VBox(30);
        mainLayout.setPrefSize(WIDTH, HEIGHT);
        mainLayout.setAlignment(Pos.CENTER);
        
        // create its components and add them to the layout
        Button resetButton = new Button("Reset");
        resetButton.setOnAction((event) -> {
            this.gameOver = false;
            this.playerTurn = "X";
            mainLayout.getChildren().clear();
            mainLayout.getChildren().addAll(getGamePage(), resetButton);
            //mainLayout.setCenter(getGamePage());
        });
        //mainLayout.setCenter(getGamePage());
        //mainLayout.setBottom(resetButton);
        mainLayout.getChildren().addAll(getGamePage(), resetButton);
        
        Scene gameScene = new Scene(mainLayout);
        
        window.setScene(gameScene);
        window.show();
    }
    
    public Parent getGamePage() {
        
        //create main layout
        VBox layout = new VBox(20);
        layout.setPrefSize(WIDTH, HEIGHT);
        layout.setAlignment(Pos.CENTER);
        
        //create its components
        
        Label turnLabel = new Label("Turn: " + playerTurn + " (" + userControl.getCurrentUser().getName() + ")");
        turnLabel.setFont(Font.font("Monospaced", 40));
        turnLabel.setStyle("-fx-border-style: solid inside;");
        
        GridPane gameTiles = new GridPane();
        for (int i = 0; i < 6; i++) {
            for (int y = 0; y < 7; y++) {
                Label currentLabel = new Label(" ");
                currentLabel.setFont(Font.font("Monospaced", FontWeight.BOLD, 50));
                //currentLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
                currentLabel.setStyle("-fx-border-style: solid inside;");
                currentLabel.setMinWidth(50);
                currentLabel.setMinHeight(50);
                currentLabel.setAlignment(Pos.BASELINE_CENTER);
                gameArea[y][i] = currentLabel;
                gameTiles.add(currentLabel, y, i);            
            }
        }
        
        for (int i = 0; i < 7; i++) {
            Button currentButton = new Button("Insert");
            currentButton.setFont(Font.font("Monospaced", 10));
            this.buttons[i] = currentButton;
            gameTiles.add(currentButton, i, 6);
        }
        
        gameTiles.setAlignment(Pos.CENTER);
        
        Label playerInfo = new Label("Name: " + userControl.getCurrentUser().getName()
        + ", wins: " + userControl.getCurrentUser().getWins() + ", losses: " + 
                userControl.getCurrentUser().getLosses());
        playerInfo.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        
        //functionality to buttons
        
        for (int i = 0; i < 7; i++) {
            int counter = i;
            buttons[i].setOnAction((ActionEvent event) -> {
                int playableTile = controller.getPlayableTile(this.gameArea, counter);
                if (playableTile != -1 && gameOver == false) {
                    playTile(counter, playableTile);
                    
                    if (this.playerTurn.equals("X")) {
                        this.playerTurn = "O";
                        turnLabel.setText("Turn: " + playerTurn);
                    } else {
                        this.playerTurn = "X";
                        turnLabel.setText("Turn: " + playerTurn + " (" + userControl.getCurrentUser().getName() + ")");
                    }
                    
                    
                    if (controller.gameIsOver(this.gameArea)) {
                        this.gameOver = true;
                        turnLabel.setText("GAME OVER");
                        // adding win/loss to user
                        if (this.playerTurn.equals("O")) {
                            this.userControl.win();
                        } else {
                            this.userControl.lose();
                        }
                        //System.out.println(userControl.getCurrentUser());
                        fileUserDao.changeUserState(userControl.getCurrentUser());
                        fileUserDao.saveToFile();
                        
                        playerInfo.setText("Name: " + userControl.getCurrentUser().getName()
                        + ", wins: " + userControl.getCurrentUser().getWins() + ", losses: " + 
                                userControl.getCurrentUser().getLosses());
                    }
                }
            });
        }
        /*
        resetButton.setOnAction((event) -> {
            Stage peliStage = new Stage();
            peliStage.setScene(getGamePage());
        });
        */
        
        
        //components added to main layout
        //layout.setTop(turnLabel);
        //layout.setCenter(gameTiles);
        //layout.setBottom(playerInfo);
        layout.getChildren().addAll(turnLabel, gameTiles, playerInfo);
        //this.fileUserDao.getUsers().stream().forEach(e -> System.out.println(e));
        //System.out.println("Current user: " + this.userControl.getCurrentUser());
        
        return layout;
    }
    
    public void playTile(int posCol, int posRow) {
        this.gameArea[posCol][posRow].setText(this.playerTurn);
    }

}
