
package neljansuora.ui;

import java.util.ArrayList;
import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import neljansuora.controller.Usercontrol;
import neljansuora.dao.FileUserDao;
import neljansuora.domain.User;
import static neljansuora.ui.NeljanSuoraUi.HEIGHT;
import static neljansuora.ui.NeljanSuoraUi.WIDTH;

/**
 * Represents the statistics view.
 */
public class Statistics {
    private Usercontrol userControl;
    private FileUserDao fileUserDao;
    private Scene prevScene;
    
    /**
     * Constructor
     * @param   userControl Usercontrol which was created at the start of the game.
     * @param   fileUserDao FileUserDao which was created at the start of the game.
     * @param   prevScene   The scene where the user arrived from.
     */
    public Statistics(Usercontrol userControl, FileUserDao fileUserDao, Scene prevScene) {
        this.userControl = userControl;
        this.fileUserDao = fileUserDao;
        this.prevScene = prevScene;
    }
    
    /**
     * Displays the statistics -page.
     * @param   window  Stage shown to user.
     */
    public void display(Stage window) {
        // create main layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPrefSize(WIDTH, HEIGHT);
        mainLayout.setStyle("-fx-background-color: white");

        // create its components
        // create current user's stats in a barchart
        CategoryAxis xAxisUser = new CategoryAxis();
        NumberAxis yAxisUser = new NumberAxis();
        yAxisUser.setLabel("Quantity");
        yAxisUser.setAutoRanging(false);
        yAxisUser.setLowerBound(0);
        yAxisUser.setUpperBound(this.userControl.getCurrentUser().getWins() + this.userControl.getCurrentUser().getLosses());
        yAxisUser.setTickUnit(2);
        
        BarChart<String, Number> barChartUser = new BarChart<>(xAxisUser, yAxisUser);
        
        barChartUser.setTitle(this.userControl.getCurrentUser().getName());
        barChartUser.setLegendVisible(false);
        barChartUser.setCategoryGap(70);

        XYChart.Series userStats = new XYChart.Series();
        userStats.getData().add(new XYChart.Data("Wins", this.userControl.getCurrentUser().getWins()));
        userStats.getData().add(new XYChart.Data("Losses", this.userControl.getCurrentUser().getLosses()));

        barChartUser.getData().add(userStats);
        
        // create top winners' stats in a barchart
        CategoryAxis xAxisTop = new CategoryAxis();
        NumberAxis yAxisTop = new NumberAxis();
        
        // tickunit and upperbound are defined later
        xAxisTop.setLabel("Name");
        yAxisTop.setLabel("Number of wins");
        yAxisTop.setAutoRanging(false);
        yAxisTop.setLowerBound(0);
                
        BarChart<String, Number> barChartTopWinners = new BarChart<>(xAxisTop, yAxisTop);
        
        barChartTopWinners.setTitle("Top winners");
        barChartTopWinners.setLegendVisible(false);
        
        XYChart.Series topWinners = new XYChart.Series();
        ArrayList<User> allUsersSortedByWins = fileUserDao.getUsers().stream()
                .sorted().collect(Collectors.toCollection(ArrayList::new));
          
        int maxSize = Math.min(5, fileUserDao.userCount());
        
        for (int i = 0; i < maxSize; i++) {
            User user = allUsersSortedByWins.get(i);
            topWinners.getData().add(new XYChart.Data(user.getName(), user.getWins()));
        }
        
        barChartTopWinners.getData().add(topWinners);

        // to ensure a nice, good looking graph, both upperbound and tickunit are set to be scaled by top winner's total wins
        
        int currentTickUnit = Math.max(1, allUsersSortedByWins.get(0).getWins() / 10);

        yAxisTop.setTickUnit(currentTickUnit);
        
        yAxisTop.setUpperBound(currentTickUnit * 11);
        
        // buttons for changing stats-view
        final ToggleGroup viewOptions = new ToggleGroup();

        RadioButton myStats = new RadioButton("My stats");
        myStats.setToggleGroup(viewOptions);
        myStats.setSelected(true);

        RadioButton topWins = new RadioButton("Most wins");
        topWins.setToggleGroup(viewOptions);
        
        // ..add functionality to them
        viewOptions.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {

                if (viewOptions.getSelectedToggle() != null) {

                    if (viewOptions.getSelectedToggle() == myStats) {
                        mainLayout.setCenter(barChartUser);
                    }
                    
                    if (viewOptions.getSelectedToggle() == topWins) {
                        mainLayout.setCenter(barChartTopWinners);
                    }

                }

            } 
        });
        

        // add radiobuttons to their own layout
        VBox buttons = new VBox();
        buttons.getChildren().addAll(myStats, topWins);
        
        Button backButton = new Button("BACK");
        backButton.setStyle("-fx-background-color: white;-fx-text-fill: black;-fx-font-size: 15pt;");
        backButton.setOnAction(event -> {
            window.setScene(this.prevScene);
        });
        
        // add components to main layout
        mainLayout.setCenter(barChartUser);
        mainLayout.setRight(buttons);
        mainLayout.setBottom(backButton);
        
        // create scene and add it to stage
        Scene statsScene = new Scene(mainLayout);
        
        window.setScene(statsScene);
        window.show();
    }

}
