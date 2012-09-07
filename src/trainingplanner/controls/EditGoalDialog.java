/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner.controls;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.layout.AnchorPane;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import trainingplanner.org.xsd.DistanceMeasures;
import trainingplanner.org.xsd.IKPIType;
import trainingplanner.org.xsd.KpiValueType;
import trainingplanner.org.xsd.MeasureTypes;
import trainingplanner.org.xsd.SportTypes;
import trainingplanner.org.xsd.TimeMeasures;


/**
 *
 * @author troutk
 */
public class EditGoalDialog extends AnchorPane implements Initializable  {
    @FXML private ChoiceBox sportsTypes;
    @FXML private ChoiceBox displayedAs;
    @FXML private ChoiceBox distanceIn;
    @FXML private ChoiceBox timeMeasuredAs;
    
    private Parent application;
    private Effect effect = null;
    private ObservableList<IKPIType> kpiItems;
    private IKPIType kpi;
    private XMLGregorianCalendar XMLGregorianCalendar;


    public EditGoalDialog(Parent parent) {
        
        application = parent;
        effect = application.getScene().getRoot().getEffect();
        application.getScene().getRoot().setEffect(new BoxBlur());
        URL location = getClass().getResource("/trainingplanner/FXML/TrainingPlannerEditGoalDialog.fxml");
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
        sportsTypes.getItems().clear();
        sportsTypes.getItems().addAll(Arrays.asList(SportTypes.values()));
        displayedAs.getItems().clear();
        displayedAs.getItems().addAll(Arrays.asList(MeasureTypes.values()));
        distanceIn.getItems().clear();
        distanceIn.getItems().addAll(Arrays.asList(DistanceMeasures.values()));
        timeMeasuredAs.getItems().clear();
        timeMeasuredAs.getItems().addAll(Arrays.asList(TimeMeasures.values()));
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @FXML public void closeDialog(){
        application.getScene().getRoot().setEffect(effect);
        Scene scene = this.getScene();
        scene.getWindow().hide();       
    }
    
    @FXML public void validateDate(){
        System.out.println("text changed");
    }
    
    @FXML void saveGoal(){
        try {
            this.kpi = validate();
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(EditGoalDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.kpiItems.add(this.kpi);
        closeDialog();
    }

    private IKPIType validate() throws DatatypeConfigurationException {
        DatatypeFactory datatypefactory = DatatypeFactory.newInstance();	
                GregorianCalendar today = new GregorianCalendar();
		datatypefactory.newXMLGregorianCalendar(today);
        IKPIType currentKpi = new IKPIType();
        if (sportsTypes.getSelectionModel().isEmpty()){
               currentKpi.setSportsType(SportTypes.OTHER); 
        }
        else {
           currentKpi.setSportsType((SportTypes)sportsTypes.getSelectionModel().getSelectedItem());
        }
        KpiValueType cVal = new KpiValueType();
        cVal.setValue(BigDecimal.TEN);
        cVal.setValueDate(datatypefactory.newXMLGregorianCalendar(today));

        currentKpi.setCurrentValue(cVal);
        IKPIType.Measurement measurement = new IKPIType.Measurement();
        measurement.setMeasureType((MeasureTypes)displayedAs.getSelectionModel().getSelectedItem());
        measurement.setDistanceMeasure((DistanceMeasures)distanceIn.getSelectionModel().getSelectedItem());
        measurement.setTimeMeasure((TimeMeasures)timeMeasuredAs.getSelectionModel().getSelectedItem());
        currentKpi.setMeasurement(measurement);
        KpiValueType gVal = new KpiValueType();
        gVal.setValue(BigDecimal.TEN);
        currentKpi.setGoalValue(gVal);
        
        return currentKpi;
    }

    public void setKPIs(ObservableList<IKPIType> kpis) {
        this.kpiItems = kpis;
    }
}
