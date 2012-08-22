/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import trainingplanner.org.extensions.AthleteExt;
import trainingplanner.org.extensions.TrainingPlanExt;

/**
 *
 * @author troutk
 */
public class TrainingPlannerWindowController implements Initializable {
    
    @FXML
    private Label athleteName;
    private TrainingPlanExt trainingPlan;
    private AthleteExt athlete;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       trainingPlan = new TrainingPlanExt();
        trainingPlan.initialize();
        athlete = trainingPlan.getAthlete();
        athleteName.setText(athlete.getFullName());
        
        
    }    
}
