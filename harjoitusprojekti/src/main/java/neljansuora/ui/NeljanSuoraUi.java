package neljansuora.ui;

import java.io.FileInputStream;
import neljansuora.controller.Gamecontrol;
import neljansuora.domain.User;
import neljansuora.controller.Usercontrol;
import neljansuora.dao.FileUserDao;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

/**
  * Handles all the UI-related aspects of the program.
  */
public class NeljanSuoraUi extends Application {
    
    private Usercontrol userControl;
    private FileUserDao fileUserDao;
    private Gamecontrol controller;
    private Label[][] gameArea;
    private Button[] buttons;
    private String playerTurn;
    private boolean gameOver;
    
    @Override
    public void init() throws Exception {
        
        Properties properties = new Properties();

        properties.load(new FileInputStream("config.properties"));

        String userFile = properties.getProperty("userFile");
        
        this.userControl = new Usercontrol();
        this.fileUserDao = new FileUserDao(userFile);
        this.fileUserDao.readFromFile();
        
        this.controller = new Gamecontrol();
        this.gameArea = new Label[7][6]; // turned horisontally
        this.buttons = new Button[7];
        this.playerTurn = "X";
        this.gameOver = false;
        //System.out.println(userFile);
    }
    
    @Override
    public void start(Stage window) throws Exception {
        
        
        //create the main layout
        BorderPane mainLayout = new BorderPane();
        //edit it
        mainLayout.setPrefSize(650, 500);
        
            //create loginView
        // create the layout
        GridPane loginLayout = new GridPane();
        
        // create its elements
        Label loginText = new Label("Name: ");
        TextField loginField = new TextField();
        
        Button loginButton = new Button("Login");
        Button createUserButton = new Button("Create user");
        
        Label loginFailureText = new Label("");
        Label createUserFailureText = new Label("");
        
        Button continueButton = new Button("Continue");
        Button resetButton = new Button("ResetGame");
        
        // add elements to layout
        loginLayout.add(loginText, 0, 0);
        loginLayout.add(loginField, 1, 0);
        loginLayout.add(loginButton, 1, 1);
        loginLayout.add(loginFailureText, 1, 2);
        loginLayout.add(createUserButton, 1, 3);
        loginLayout.add(createUserFailureText, 1, 4);
        loginLayout.add(continueButton, 1, 5);
        
        // make the layout a bit more stylish
        loginLayout.setHgap(10);
        loginLayout.setVgap(10);
        loginLayout.setPadding(new Insets(10, 10, 10, 10));
        
        
        //add login layout to main layout
        mainLayout.setCenter(loginLayout);
        
        // add functionality to buttons
        
        
        loginButton.setOnAction((event) -> {
            String loginName = loginField.getText();
            if (this.fileUserDao.userExists(loginName)) {
                loginFailureText.setText("Login successful");
                createUserFailureText.setText("");
                this.userControl.logIn(this.fileUserDao.getUser(loginName));
            } else {
                loginFailureText.setText("Login failed");
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
            if (loginFailureText.getText().equals("Login successful")) { 
                mainLayout.setCenter(getGamePage());
                mainLayout.setBottom(resetButton);
            }
        });
        
        resetButton.setOnAction((event) -> {
            this.gameOver = false;
            this.playerTurn = "X";
            mainLayout.setCenter(getGamePage());
        });
        
        Scene mainView = new Scene(mainLayout);
        
        window.setScene(mainView);
        window.setTitle("Test mode, login page and game with stats");
        window.show();
        window.centerOnScreen();
        
    }
    
    @Override
    public void stop(){
        //make an if-statement for checking right state of app -->
       this.fileUserDao.saveToFile();
       System.out.println("Saving current stats");
}
    
    public static void main(String[] args) {
        launch(NeljanSuoraUi.class);
        
    }
    
    public Parent getGamePage() {
        //create main layout
        BorderPane layout = new BorderPane();
        //layout.setPrefSize(650, 500);
        //create its components
        
        Label turnLabel = new Label("Turn: " + playerTurn + " (" + userControl.getCurrentUser().getName() + ")");
        turnLabel.setFont(Font.font("Monospaced", 40));
        turnLabel.setStyle("-fx-border-style: solid inside;");
        
        GridPane gameTiles = new GridPane();
        for (int i = 0; i < 6; i++) {
            for (int y = 0; y < 7; y++) {
                Label currentLabel = new Label(" ");
                currentLabel.setFont(Font.font("Monospaced", 40));
                currentLabel.setStyle("-fx-border-style: solid inside;");
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
        
        Button resetButton = new Button("Reset");
        Label playerInfo = new Label("Name: " + userControl.getCurrentUser().getName()
        + ", wins: " + userControl.getCurrentUser().getWins() + ", losses: " + 
                userControl.getCurrentUser().getLosses());
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
        layout.setTop(turnLabel);
        layout.setCenter(gameTiles);
        layout.setBottom(playerInfo);
        //this.fileUserDao.getUsers().stream().forEach(e -> System.out.println(e));
        //System.out.println("Current user: " + this.userControl.getCurrentUser());
        
        return layout;
    }
    
    public void playTile(int posCol, int posRow) {
        this.gameArea[posCol][posRow].setText(this.playerTurn);
    }
    

}