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
import javafx.geometry.Pos;
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
import javafx.scene.text.FontWeight;

/**
  * Handles all the UI-related aspects of the program.
  */
public class NeljanSuoraUi extends Application {
    
    private Usercontrol userControl;
    private FileUserDao fileUserDao;
    
    public static int WIDTH = 550;
    public static int HEIGHT = 550;
    
    @Override
    public void init() throws Exception {
        
        Properties properties = new Properties();

        properties.load(new FileInputStream("config.properties"));

        String userFile = properties.getProperty("userFile");
        
        this.userControl = new Usercontrol();
        this.fileUserDao = new FileUserDao(userFile);
        this.fileUserDao.readFromFile();
        
        
        //System.out.println(userFile);
    }
    
    @Override
    public void start(Stage window) throws Exception {
        
        
        //create the main layout
        BorderPane mainLayout = new BorderPane();
        //edit it
        mainLayout.setPrefSize(WIDTH, HEIGHT);
        
        
            //create loginView
        // create the layout
        GridPane loginLayout = new GridPane();
        loginLayout.setAlignment(Pos.CENTER);
        // create its elements
        Label loginText = new Label("Name: ");
        loginText.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        TextField loginField = new TextField();
        loginField.setPrefWidth(275);
        
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
        Label currentUserText = new Label("Current user: NOT LOGGED IN");
        
        //add login layout and additional elements to main layout
        mainLayout.setCenter(loginLayout);
        mainLayout.setBottom(currentUserText);
        
        // add functionality to buttons
        
        
        loginButton.setOnAction((event) -> {
            String loginName = loginField.getText();
            if (this.fileUserDao.userExists(loginName)) {
                loginFailureText.setText("Login successful");
                createUserFailureText.setText("");
                this.userControl.logIn(this.fileUserDao.getUser(loginName));
                currentUserText.setText("Current user: " + this.userControl.getCurrentUser().getName());
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
            if (this.userControl.getCurrentUser() != null) {
                MainMenu mainMenu = new MainMenu(this.userControl, this.fileUserDao);
                
                try {
                    mainMenu.display(window);
                } catch (Exception e) {

                }
                //mainLayout.setCenter(getGamePage());
                //mainLayout.setBottom(resetButton);
            }
        });
        
        
        
        Scene mainView = new Scene(mainLayout);
        
        window.setScene(mainView);
        window.setTitle("LOGIN");
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
    
    
    

}