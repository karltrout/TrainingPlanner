/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import trainingplanner.FXML.TrainingPlannerGoalController;
import trainingplanner.org.xsd.Athlete;
import trainingplanner.org.extensions.KPI;
import trainingplanner.org.xsd.IKPIType;
import trainingplanner.org.xsd.SportTypes;

/**
 * FXML Controller class
 *
 * @author troutk
 */
public class TrainingPlannerGoalsController implements Initializable {

    @FXML 
    private AnchorPane goalsListPane;
    
    private Athlete.KeyPerformanceIndicators KPIs;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("initializing goals controller");
        if (this.KPIs != null)  setGoals();
    } 
    
    public void setGoals(){
        List<IKPIType> kpis = KPIs.getKPI();
       // if (goalsListPane == null ) goalsListPane = new AnchorPane();
        goalsListPane.getChildren().clear();
        int x = 0;
        for (IKPIType kpi : kpis){
            System.out.println("KPI: "+kpi.getSportsType()+" "+kpi.getMeasurement().getMeasureType()+" current value: "+
                                kpi.getCurrentValue().getValue()+" goal value: "+kpi.getGoalValue().getValue());  
            try {
                URL location = getClass().getResource("FXML/TrainingPlannerGoal.fxml");
                FXMLLoader goalLoader = new FXMLLoader();
                goalLoader.setLocation(location);
                goalLoader.setBuilderFactory(new JavaFXBuilderFactory());
                
                Parent goalPane = (Parent) goalLoader.load(location.openStream());
                goalPane.setLayoutY(x*35.0);
                x++;
                TrainingPlannerGoalController goalController = goalLoader.getController();
                goalController.setKPI((KPI)kpi);
                //goalController.setSportsTitle(kpi.getSportsType().value());
                goalsListPane.getChildren().add(goalPane);
            } catch (IOException ex) {
                System.out.println("Error: "+ex.getMessage());
                Logger.getLogger(TrainingPlannerWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
     
    }
    
    public void setKPIs(Athlete.KeyPerformanceIndicators KPIs){
     this.KPIs = KPIs;   
    }
}
