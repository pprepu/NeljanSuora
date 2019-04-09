
package neljansuora.ui;

import NeljanSuora.controller.Gamecontrol;
import neljansuora.domain.User;
import neljansuora.controller.Usercontrol;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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

public class NeljanSuoraUi extends Application {
    
    private Usercontrol control;
    private User currentUser;
    
    private Gamecontrol controller;
    private Label[][] gameArea;
    private Button[] buttons;
    private String playerTurn;
    private boolean gameOver;
    
    @Override
    public void init() throws Exception {
        HashMap<String, User> users = new HashMap<>();
        this.control = new Usercontrol(users);
        
        this.controller = new Gamecontrol();
        this.gameArea = new Label[7][6]; // turned horisontally
        this.buttons = new Button[7];
        this.playerTurn = "X";
        this.gameOver = false;
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
            if (this.control.userExists(loginName)) {
                loginFailureText.setText("Login successful");
                createUserFailureText.setText("");
                this.currentUser = this.control.getUser(loginName);
            } else {
                loginFailureText.setText("Login failed");
                createUserFailureText.setText("");
            }
        });
        
        createUserButton.setOnAction((event) -> {
            if (this.control.addUser(loginField.getText()) == false || loginField.getText().equals("")) {
                createUserFailureText.setText("Cannot create user, try another name");
                loginFailureText.setText("");
            } else {
                createUserFailureText.setText("User created");
                loginFailureText.setText("");
            }
        });
        
        continueButton.setOnAction((event) -> {
            if (loginFailureText.getText().equals("Login successful")) window.setScene(getMainPage());
        });
        
        
        Scene mainView = new Scene(mainLayout);
        
        window.setScene(mainView);
        window.setTitle("Test mode, basic login page and a single game");
        window.show();
        
    }
    
    public static void main(String[] args) {
        launch(NeljanSuoraUi.class);
        
    }
    
    public Scene getMainPage() {
        //create main layout
        BorderPane layout = new BorderPane();
        layout.setPrefSize(650, 500);
        //create its components
        
        Label turnLabel = new Label("Turn: " + playerTurn);
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
        
        //functionality to buttons
        
        for (int i = 0; i < 7; i++) {
            int counter = i;
            buttons[i].setOnAction((ActionEvent event) -> {
                int playableTile = controller.getPlayableTile(this.gameArea, counter);
                if (playableTile != -1 && gameOver == false) {
                    playTile(counter, playableTile);
                    
                    if (this.playerTurn.equals("X")) {
                        this.playerTurn = "O";
                    } else {
                        this.playerTurn = "X";
                    }
                    
                    turnLabel.setText("Turn: " + playerTurn);
                    
                    if (controller.gameIsOver(this.gameArea)) {
                        this.gameOver = true;
                        turnLabel.setText("GAME OVER");
                    }
                }
            });
        }
        
        
        //components added to main layout
        layout.setTop(turnLabel);
        layout.setCenter(gameTiles);
        
        //create the returnable scene
        Scene view = new Scene(layout);
        
        return view;
        
    }
    
    public void playTile(int posCol, int posRow) {
        this.gameArea[posCol][posRow].setText(this.playerTurn);
    }
    

}
