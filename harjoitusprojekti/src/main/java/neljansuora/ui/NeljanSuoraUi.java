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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
        
    }
    
    @Override
    public void start(Stage window) throws Exception {
        
        Login login = new Login(this.userControl, this.fileUserDao);
        login.display(window);
    }
    
    @Override
    public void stop(){
       this.fileUserDao.saveToFile();
}
    
    public static void main(String[] args) {
        launch(NeljanSuoraUi.class);
        
    }
    
    
    

}