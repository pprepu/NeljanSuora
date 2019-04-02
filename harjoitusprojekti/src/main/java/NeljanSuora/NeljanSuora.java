
package NeljanSuora;

import NeljanSuora.domain.User;
import NeljanSuora.logic.Usercontrol;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import javafx.application.Application;
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

public class NeljanSuora extends Application {
    
    private Usercontrol control;
    private User currentUser;
    
    @Override
    public void init() throws Exception {
        HashMap<String, User> users = new HashMap<>();
        this.control = new Usercontrol(users);
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
            if (loginFailureText.getText().equals("Login successful")) mainLayout.setCenter(getMainPage());
        });
        
        
        Scene mainView = new Scene(mainLayout);
        
        window.setScene(mainView);
        window.setTitle("Testing login page, user data is not saved yet");
        window.show();
        
    }
    
    public static void main(String[] args) {
        launch(NeljanSuora.class);
        
    }
    
    public Parent getMainPage() {
        return new Label("UNDER CONSTRUCTION");
        
    
    }
    

}
