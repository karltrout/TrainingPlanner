/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import trainingplanner.org.extensions.AthleteExt;
import trainingplanner.org.extensions.TrainingPlanExt;

/**
 *
 * @author troutk
 */
public class TrainingPlanner extends Application {
    TrainingPlanExt trainingPlan;
    AthleteExt athlete;
    TrainingPlannerWindowController windowController;
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader calendarLoader = new FXMLLoader();
                
        Parent root = calendarLoader.load(getClass().getResource("FXML/TrainingPlannerWindow.fxml"));
        windowController = (TrainingPlannerWindowController) calendarLoader.getController();
        Scene scene = new Scene(root);
        stage.setFullScreen(true);
        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
