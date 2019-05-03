
package neljansuora.ui;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import neljansuora.controller.Gamecontrol;
import neljansuora.controller.Usercontrol;
import neljansuora.dao.FileUserDao;
import neljansuora.domain.User;
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
    
    public void displayPreGameStage(Stage window) {
        VBox layout = new VBox(30);
        layout.setPrefSize(WIDTH, HEIGHT);
        layout.setAlignment(Pos.CENTER);
        

         // create loginView
        // create the layout
        GridPane loginLayout = new GridPane();
        loginLayout.setAlignment(Pos.CENTER);
        // create its elements
        Label descriptionText1 = new Label("Currently in 2-player mode.");
        descriptionText1.setFont(Font.font("Verdana", FontWeight.BOLD, 26));
        Label descriptionText2 = new Label("Please log in with player 2.");
        descriptionText2.setFont(Font.font("Verdana", 22));
        
        Label loginText = new Label("Name: ");
        loginText.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        TextField loginField = new TextField();
        loginField.setPrefWidth(400);
        
        Button loginButton = new Button("Login");
        Button createUserButton = new Button("Create user");
        
        Label loginFailureText = new Label("");
        loginFailureText.setFont(Font.font("Verdana", 14));
        Label createUserFailureText = new Label("");
        createUserFailureText.setFont(Font.font("Verdana", 14));
        
        Button continueButton = new Button("Continue");
        
        // add elements to layout
        loginLayout.add(loginText, 0, 0);
        loginLayout.add(loginField, 1, 0);
        loginLayout.add(loginButton, 1, 1);
        loginLayout.add(loginFailureText, 1, 2);
        loginLayout.add(createUserButton, 1, 3);
        loginLayout.add(createUserFailureText, 1, 4);
        loginLayout.add(continueButton, 1, 5);
        
        // make the layout a bit more stylish
        loginLayout.setHgap(5);
        loginLayout.setVgap(5);
        loginLayout.setPadding(new Insets(10, 10, 10, 10));
        
        // create additional elements to main layout
        Label player2Text = new Label("Player 2: NOT LOGGED IN");
        player2Text.setFont(Font.font("Verdana", 12));
        Button backButton = new Button("BACK");
        backButton.setStyle("-fx-text-fill: black;-fx-font-size: 15pt;");
        
        // add login layout and additional elements to main layout
        layout.getChildren().addAll(descriptionText1, descriptionText2, loginLayout, backButton, player2Text);
        
        // add functionality to buttons
        loginButton.setOnAction((event) -> {
            String loginName = loginField.getText();
            if (userControl.getCurrentUser().getName().equals(loginName)) {
                loginFailureText.setText("Login failed - " + loginName + " is already logged in as player 1");
                createUserFailureText.setText("");
            } else if (this.fileUserDao.userExists(loginName)) {
                loginFailureText.setText("Login successful");
                createUserFailureText.setText("");
                this.userControl.setPlayer2(this.fileUserDao.getUser(loginName));
                player2Text.setText("Player 2: " + this.userControl.getPlayer2().getName());
            } else {
                loginFailureText.setText("Login failed - username doesn't exist");
                createUserFailureText.setText("");
            }
        });
        
        createUserButton.setOnAction((event) -> {
            if (this.fileUserDao.addUser(loginField.getText()) == false || loginField.getText().equals("")) {
                createUserFailureText.setText("Cannot create user, try another name");
                loginFailureText.setText("");
            } else {
                this.fileUserDao.addUser(new User(loginField.getText(), 0, 0));
                createUserFailureText.setText("User created");
                loginFailureText.setText("");
                fileUserDao.saveToFile();
            }
        });
        
        continueButton.setOnAction((event) -> {
            if (this.userControl.getPlayer2() != null) {
                
                try {
                    display(window);
                } catch (Exception e) {

                }
            }
        });
        
        
        backButton.setOnAction(event -> {
            window.setScene(this.prevScene);
        });
        
        Scene preGameScene = new Scene(layout);
        window.setScene(preGameScene);
        window.show();
    }
    
    public void display(Stage window) {
        
        // create main-layout for the game
        VBox mainLayout = new VBox(30);
        mainLayout.setPrefSize(WIDTH, HEIGHT);
        mainLayout.setAlignment(Pos.CENTER);
        HBox buttons = new HBox();
        // create its components and add them to the layout
        Button resetButton = new Button("Reset");
        resetButton.setStyle("-fx-text-fill: black;-fx-font-size: 15pt;");
        resetButton.setOnAction((event) -> {
            this.gameOver = false;
            this.playerTurn = "X";
            mainLayout.getChildren().clear();
            mainLayout.getChildren().addAll(getGamePage(), buttons);
        });
        Button backButton = new Button("BACK");
        backButton.setStyle("-fx-text-fill: black;-fx-font-size: 15pt;");
        backButton.setOnAction(event -> {
            window.setScene(this.prevScene);
        });
        
        buttons.getChildren().addAll(resetButton, backButton);
        buttons.setAlignment(Pos.CENTER);
        mainLayout.getChildren().addAll(getGamePage(), buttons);
        
        Scene gameScene = new Scene(mainLayout);
        
        window.setScene(gameScene);
        window.show();
    }
    
    public Parent getGamePage() {
        
        // create layout
        VBox layout = new VBox(20);
        layout.setPrefSize(WIDTH, HEIGHT);
        layout.setAlignment(Pos.CENTER);
        
        // create its components
        Label turnLabel = new Label("Turn: " + userControl.getCurrentUser().getName());
        turnLabel.setFont(Font.font("Verdana", 25));
        
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
            currentButton.setFont(Font.font("Verdana", 12));
            currentButton.setMinWidth(50);
            currentButton.setMinHeight(25);
            this.buttons[i] = currentButton;
            gameTiles.add(currentButton, i, 6);
        }
        
        gameTiles.setAlignment(Pos.CENTER);
        
        Label playerInfo = new Label("Player 1: " + userControl.getCurrentUser().getName()
        + ", wins: " + userControl.getCurrentUser().getWins() + ", losses: " + 
                userControl.getCurrentUser().getLosses());
        playerInfo.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        
        Label player2Info = new Label("Player 2: " + userControl.getPlayer2().getName()
        + ", wins: " + userControl.getPlayer2().getWins() + ", losses: " + 
                userControl.getPlayer2().getLosses());
        player2Info.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        
        // functionality to buttons
        for (int i = 0; i < 7; i++) {
            int counter = i;
            buttons[i].setOnAction((ActionEvent event) -> {
                int playableTile = controller.getPlayableTile(this.gameArea, counter);
                if (playableTile != -1 && gameOver == false) {
                    playTile(counter, playableTile);
                    
                    if (this.playerTurn.equals("X")) {
                        this.playerTurn = "O";
                        turnLabel.setText("Turn: " + userControl.getPlayer2().getName());
                    } else {
                        this.playerTurn = "X";
                        turnLabel.setText("Turn: " + userControl.getCurrentUser().getName());
                    }
                    
                    
                    if (controller.gameIsOver(this.gameArea)) {
                        this.gameOver = true;
                        // adding wins/losses to players
                        if (this.playerTurn.equals("O")) {
                            this.userControl.win();
                            this.userControl.p2Lose();
                            turnLabel.setText("GAME OVER, " + userControl.getCurrentUser().getName() + " wins!");
                        } else {
                            this.userControl.lose();
                            this.userControl.p2Win();
                            turnLabel.setText("GAME OVER, " + userControl.getPlayer2().getName() + " wins!");
                        }
                        // save changes to file
                        fileUserDao.changeUserState(userControl.getCurrentUser());
                        fileUserDao.changeUserState(userControl.getPlayer2());
                        fileUserDao.saveToFile();
                        
                        // update current player stats on screen
                        playerInfo.setText("Player 1: " + userControl.getCurrentUser().getName()
                                + ", wins: " + userControl.getCurrentUser().getWins() + ", losses: " + 
                                userControl.getCurrentUser().getLosses());
                        player2Info.setText("Player 2: " + userControl.getPlayer2().getName()
                                + ", wins: " + userControl.getPlayer2().getWins() + ", losses: " + 
                                userControl.getPlayer2().getLosses());
                    }
                }
            });
        }
        
        // add all elements into layout
        layout.getChildren().addAll(turnLabel, gameTiles, playerInfo, player2Info);
        
        return layout;
    }
    
    public void playTile(int posCol, int posRow) {
        this.gameArea[posCol][posRow].setText(this.playerTurn);
        if (this.playerTurn.equals("X")) {
            this.gameArea[posCol][posRow].setStyle("-fx-border-style: solid inside; -fx-background-color:RED; -fx-text-fill:RED");
        } else {
            this.gameArea[posCol][posRow].setStyle("-fx-border-style: solid inside; -fx-background-color:YELLOW; -fx-text-fill:YELLOW");
        }
    }

}
