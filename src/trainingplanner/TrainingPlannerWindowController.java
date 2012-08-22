/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner;

import java.net.URL;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;
import trainingplanner.org.extensions.AthleteExt;
import trainingplanner.org.extensions.TrainingPlanExt;

/**
 *
 * @author troutk
 */
public class TrainingPlannerWindowController implements Initializable {
    
    private String dateFormatString = "%1$tb %1$te, %1$tY";
    
    private TrainingPlanExt trainingPlan;
    private AthleteExt athlete;
    
    @FXML  private Label athleteName;
    @FXML  private Label athleteAge;
    @FXML  private Label athleteDOB;
    @FXML  private Label athleteWeight;
    @FXML  private Label todaysDate;
    @FXML  private AnchorPane dashBoardPane;
    @FXML
    private void quitAction(ActionEvent event){
        System.exit(0);
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        todaysDate.setText(String.format(dateFormatString, new GregorianCalendar()));
        trainingPlan = new TrainingPlanExt();
        trainingPlan.initialize();
        athlete = trainingPlan.getAthlete();
        athleteName.setText(athlete.getFullName());
        athleteAge.setText(String.valueOf(athlete.getAge()));
        athleteWeight.setText(String.valueOf(athlete.getWeight()));
        athleteDOB.setText(String.format(dateFormatString,athlete.getDateOfBirth().toGregorianCalendar()));
        
        dashBoardPane = new AnchorPane();
    }    
}
