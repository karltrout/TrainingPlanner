/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import trainingplanner.org.xsd.IKPIType;
import trainingplanner.org.xsd.KpiValueType;

/**
 * FXML Controller class
 *
 * @author Karl
 */
public class TrainingPlannerGoalController extends AnchorPane implements Initializable {

    @FXML private Text goalSportTitle;
    @FXML private Text sportTimeCurrent;
    @FXML private Text sportTimeGoal;
    
    private IKPIType kpi;
    
        public TrainingPlannerGoalController(){                   
        URL location = getClass().getResource("FXML/TrainingPlannerGoal.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setSportsTitle(String title){
        this.goalSportTitle.setText(title);
    }
    
    public void setKPI(IKPIType kpi){
        this.kpi = kpi;
        this.goalSportTitle.setText(kpi.getSportsType().value());
        this.sportTimeCurrent.setText(kpiMeasurementValue(kpi.getCurrentValue()));
        this.sportTimeGoal.setText(kpiMeasurementValue(kpi.getGoalValue()));
    }
    
    public String kpiMeasurementValue(KpiValueType value){
        String formatedValue = "";
        String timeMeasure = kpi.getMeasurement().getTimeMeasure().value();
        String distanceMeasure = kpi.getMeasurement().getDistanceMeasure().value();
        
        float distanceNormalized = 0;
        BigDecimal kpiValue = value.getValue();
        float timeNormalized = 0;
        
        /*
         * kpi measurement values are stored as seconds per meter. this just makes sense to me so thats why I did it.
         * so miles per hour  translates to Math.round((x*0.000621371))/(d/3600)
        
        */
         switch (kpi.getMeasurement().getMeasureType().value()){
            case "pace": 
                switch (timeMeasure){
                    case "second":
                        timeNormalized =  1*kpiValue.floatValue();
                        break;
                    case "minute":
                        timeNormalized = kpiValue.floatValue()/60;
                        timeMeasure = "mins";
                        distanceMeasure = "100yds";
                        break;
                    case "hour":
                        timeNormalized = Math.round(kpiValue.floatValue()/3600);
                        break;
                    default:
                        break;
                }
                int hour = (int) (timeNormalized * 1);
                int seconds = (int) ((timeNormalized % 1)*60);
                formatedValue = hour+":"+seconds+" "+timeMeasure+"/"+distanceMeasure;
                break;
                
            case "speed": 
                switch (distanceMeasure){
                    case "feet":
                        distanceNormalized = (float)(.3048 * kpiValue.doubleValue());
                        break;
                    case "yard":
                        distanceNormalized = (float)(.9144 * kpiValue.doubleValue());
                        distanceMeasure = "100yds";
                        break;
                    case "mile":
                        distanceNormalized = (float)((kpiValue.doubleValue()/1609.34)/3600);
                        distanceMeasure = "Miles";
                        break;
                    case "meter":
                        distanceNormalized = (float)(1 * kpiValue.doubleValue());
                        distanceMeasure = "100M";
                        break;
                    case"kilometer":
                        distanceNormalized = (float)(kpiValue.doubleValue()/1000);
                        break;
                    default:
                        distanceNormalized = (float)(1 * kpiValue.doubleValue());
                        break;
                }
                formatedValue = distanceNormalized+" "+distanceMeasure+"/"+timeMeasure;
                break;
            default:
                formatedValue = String.valueOf(value.getValue());
                break;     
        }
         
        return formatedValue;
    }
}
