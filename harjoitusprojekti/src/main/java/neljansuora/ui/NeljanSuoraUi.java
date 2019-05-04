package neljansuora.ui;

import java.io.FileInputStream;
import neljansuora.controller.Usercontrol;
import neljansuora.dao.FileUserDao;
import java.util.Properties;
import javafx.application.Application;
import javafx.stage.Stage;


/**
  * Handles events related to starting and ending the programme. 
  * Most important task is to load current users from a file to FileUserDao
  */
public class NeljanSuoraUi extends Application {
    
    private Usercontrol userControl;
    private FileUserDao fileUserDao;
    
    /**
     * A Global variable used in all Ui classes to maintain regular layouts
     */
    public static int WIDTH = 550;
    /**
     * A Global variable used in all Ui classes to maintain regular layouts
     */
    public static int HEIGHT = 550;
    
    /**
     * Initializes the programme and creates Usercontrol and FileUserDao instances used throughout the programme.
     * @throws Exception 
     */
    @Override
    public void init() throws Exception {
        
        Properties properties = new Properties();

        properties.load(new FileInputStream("config.properties"));

        String userFile = properties.getProperty("userFile");
        
        this.userControl = new Usercontrol();
        this.fileUserDao = new FileUserDao(userFile);
        this.fileUserDao.readFromFile();
        
    }
    
    /**
     * Starts the graphical ui and opens the login-view.
     * @param   window  Stage variable for the JavaFX ui.
     * @throws Exception 
     */
    @Override
    public void start(Stage window) throws Exception {
        
        Login login = new Login(this.userControl, this.fileUserDao);
        login.display(window);
    }
    
    /**
     * When the window is closed or user quits, changes are saved to userfile.
     */
    @Override
    public void stop(){
       this.fileUserDao.saveToFile();
}
    
    public static void main(String[] args) {
        launch(NeljanSuoraUi.class);
        
    }
    
    
    

}