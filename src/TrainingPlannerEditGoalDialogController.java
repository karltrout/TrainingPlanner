/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import trainingplanner.org.extensions.KPI;
import trainingplanner.org.xsd.MeasureTypes;

/**
 * FXML Controller class
 *
 * @author troutk
 */
public class TrainingPlannerEditGoalDialogController implements Initializable {

    @FXML private List sportsTypes;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        KPI kpi = new KPI();
        sportsTypes.addAll(Arrays.asList(MeasureTypes.values()));
    }    
}
