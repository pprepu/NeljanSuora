
package neljansuora.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import neljansuora.controller.Usercontrol;
import neljansuora.dao.FileUserDao;
import neljansuora.domain.User;
import static neljansuora.ui.NeljanSuoraUi.HEIGHT;
import static neljansuora.ui.NeljanSuoraUi.WIDTH;


public class Login {
    
    private Usercontrol userControl;
    private FileUserDao fileUserDao;
    
    public Login(Usercontrol userControl, FileUserDao fileUserDao) {
        this.userControl = userControl;
        this.fileUserDao = fileUserDao;
    }
    
    public void display(Stage window) {
        
        //create the main layout
        //VBox mainLayout = new VBox();
        BorderPane mainLayout = new BorderPane();
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
        Label header = new Label("Neljän suora");
        header.setTextFill(Color.BLACK);
        header.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        
        Label currentUserText = new Label("Current user: NOT LOGGED IN");
        currentUserText.setFont(Font.font("Verdana", 12));
        
        // put header and loginlayout in a vbox
        VBox headerAndLoginLayout = new VBox();
        headerAndLoginLayout.getChildren().addAll(header, loginLayout);
        headerAndLoginLayout.setAlignment(Pos.CENTER);
        headerAndLoginLayout.setSpacing(50);
        
        // add all elements to main layout
        mainLayout.setCenter(headerAndLoginLayout);
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
            }
        });
        
        
        
        Scene mainView = new Scene(mainLayout);
        
        window.setScene(mainView);
        window.setTitle("LOGIN");
        window.show();
        window.centerOnScreen();
    }

}
