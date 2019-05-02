
package neljansuora.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import neljansuora.controller.Usercontrol;
import neljansuora.dao.FileUserDao;
import static neljansuora.ui.NeljanSuoraUi.HEIGHT;
import static neljansuora.ui.NeljanSuoraUi.WIDTH;


public class Rules {
    
    private Usercontrol userControl;
    private FileUserDao fileUserDao;
    private Scene prevScene;
    
    public Rules(Usercontrol userControl, FileUserDao fileUserDao, Scene prevScene) {
        this.userControl = userControl;
        this.fileUserDao = fileUserDao;
        this.prevScene = prevScene;
    }
    
    public void display(Stage window) {
        
        // create main layout
        VBox layout = new VBox(20);
        layout.setPrefSize(WIDTH, HEIGHT);
        layout.setStyle("-fx-background-color: white");
        layout.setAlignment(Pos.CENTER);
        
        //create its components
        Label header = new Label("RULES");
        header.setTextFill(Color.BLACK);
        header.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        
        Label rules1 = new Label("Both players take turns in dropping colored discs into the gameboard.");
        Label rules2 = new Label("The objective is to form a continuous horizontal, ");
        Label rules3 = new Label("vertical or diagonal line of one's own discs.");
        Label rules4 = new Label("Winner is the player who manages to do this first.");
        
        rules1.setFont(Font.font("Verdana", 12));
        rules2.setFont(Font.font("Verdana", 12));
        rules3.setFont(Font.font("Verdana", 12));
        rules4.setFont(Font.font("Verdana", 12));
        
        // including a button w/ functionality
        Button backButton = new Button("BACK");
        backButton.setStyle("-fx-background-color: white;-fx-text-fill: black;-fx-font-size: 15pt;");
        backButton.setOnAction(event -> {
            window.setScene(this.prevScene);
        });
        
        //add components to layout
        layout.getChildren().addAll(header, rules1, rules2, rules3, rules4, backButton);
        
        Scene rulesScene = new Scene(layout);
        
        window.setScene(rulesScene);
        window.show();
    }

}
