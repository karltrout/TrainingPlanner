/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import trainingplanner.controls.DailyCountDownController;
import trainingplanner.controls.PaceClockController;
import trainingplanner.controls.SpeedOMeterController;
import trainingplanner.org.calendar.TrainingCalendarDay;
import trainingplanner.org.extensions.AthleteExt;
import trainingplanner.org.extensions.TrainingPlanExt;
import trainingplanner.org.xsd.TrainingPlan;

/**
 *
 * @author troutk
 */
public class TrainingPlannerWindowController implements Initializable {
    private JAXBContext jc;
    final private String dateFormatString = "%1$tb %1$te, %1$tY";
    private SimpleObjectProperty<Color> color = new SimpleObjectProperty<>(this, "color", Color.LIME);
    
    private TrainingPlanExt trainingPlan;
    private AthleteExt athlete;
    
    @FXML  private Label athleteName;
    @FXML  private Label athleteAge;
    @FXML  private Label athleteDOB;
    @FXML  private Label athleteWeight;
    @FXML  private Label todaysDate;
    @FXML  private AnchorPane dashBoardPane;
    @FXML  private StackPane goalsPane;
    @FXML  private StackPane calendarPane;
    @FXML  private FlowPane goalIcons;
    @FXML  private ColorPicker colorPicker;
    @FXML  private AreaChart volumeChart;
    
    private SimpleObjectProperty<TrainingCalendarDay> selectedCalendarDate;
    final private PaperBackController currentNotePad = new PaperBackController();
    final private String planFile = "trainingPLan.xml";
    
    
    @FXML
    private void quitAction(ActionEvent event){
        System.exit(0);
    }
    
    @FXML
    private void saveAction(ActionEvent event){
        System.out.println("Saving... "+trainingPlan.getAthlete().getFullName());
        if( null != trainingPlan)
        {
            try {
                trainingplanner.org.xsd.TrainingPlan tp = new TrainingPlan();
                serializeObjectToXML(planFile,trainingPlan);
            } catch (Exception ex) {
                Logger.getLogger(TrainingPlannerWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            jc = JAXBContext.newInstance( "trainingplanner.org.xsd" );
        } catch (JAXBException ex) {
            Logger.getLogger(TrainingPlannerWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        selectedCalendarDate = new SimpleObjectProperty<>();
        selectedCalendarDate.addListener(new ChangeListener<TrainingCalendarDay>() {
            @Override
            public void changed(ObservableValue<? extends TrainingCalendarDay> ov, TrainingCalendarDay t, TrainingCalendarDay t1) {
                displayNotePad(currentNotePad);
            }
        });
        
        currentNotePad.setVisible(false);
        dashBoardPane.getChildren().add(currentNotePad);
        
        
        todaysDate.setText(String.format(dateFormatString, new GregorianCalendar()));
        
        trainingPlan = new TrainingPlanExt();
        try {
            trainingPlan.setRoot(deserializeXMLToTrainingPlan(planFile));
            
        } catch (Exception ex) {
            Logger.getLogger(TrainingPlannerWindowController.class.getName()).log(Level.SEVERE, null, ex);
            trainingPlan.initialize();
        } 
        
        athlete = trainingPlan.getAthlete();
        athleteName.setText(athlete.getFullName());
        athleteAge.setText(String.valueOf(athlete.getAge()));
        athleteWeight.setText(String.valueOf(athlete.getWeight()));
        athleteDOB.setText(String.format(dateFormatString,athlete.getDateOfBirth().toGregorianCalendar()));
        colorPicker.valueProperty().setValue(Color.LIME);
        
        calendarPane.getChildren().clear();
        TrainingPlannerCalendarController calendar = new TrainingPlannerCalendarController(selectedCalendarDate);
        calendarPane.getChildren().add(calendar);
        
        URL location = getClass().getResource("FXML/TrainingPlannerGoals.fxml");
        FXMLLoader goalsLoader = new FXMLLoader();
        goalsLoader.setLocation(location);
        goalsLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent goalsPaneParent;
            
        try {
            goalsPaneParent = (Parent) goalsLoader.load(location.openStream());          
            goalsPane.getChildren().clear();
            goalsPane.getChildren().add(goalsPaneParent);  
            
        } catch (IOException ex) {
            Logger.getLogger(TrainingPlannerWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        color.bind(colorPicker.valueProperty());

        TrainingPlannerGoalsController goalsControler = goalsLoader.getController();
        goalsControler.setKPIs(athlete.getKeyPerformanceIndicators());
        goalsControler.setGoals();

        ObservableList<Node> goals = goalIcons.getChildren();
        goals.clear();
        SpeedOMeterController bikeSpeed = new SpeedOMeterController(new SimpleDoubleProperty(21.4),color);
        bikeSpeed.setScaleX(0.5);
        bikeSpeed.setScaleY(0.5);
        goalIcons.getChildren().add(bikeSpeed);
        
        DailyCountDownController counter = new DailyCountDownController(new SimpleObjectProperty<>(new GregorianCalendar(2013, 7, 18) ), color);
        goalIcons.getChildren().add(counter);
        
        PaceClockController paceClock = new PaceClockController(new SimpleIntegerProperty(537), color);
        paceClock.setScaleX(0.5);
        paceClock.setScaleY(0.5);
        goalIcons.getChildren().add(paceClock);

    }
    
    
            private void displayNotePad(PaperBackController _currentNotePad) {
                
                if (!_currentNotePad.isVisible()){
                    _currentNotePad.setVisible(true);
                }
                _currentNotePad.setTrainingDay(selectedCalendarDate.get());
                
                System.out.println( "selected date is :"+selectedCalendarDate.getValue().getCalendar().getTime());
            }
            /**
	 * This method saves (serializes) any java bean object into xml file
	 */
	public void serializeObjectToXML(String xmlFileLocation,
		Object objectToSerialize) throws Exception {
                Marshaller m = jc.createMarshaller();
                m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		FileOutputStream os = new FileOutputStream(xmlFileLocation);
                
                m.marshal( objectToSerialize, os);
                os.close();
                
	}

	/**
	 * Reads Java Bean Object From XML File
	 */
	public TrainingPlan deserializeXMLToTrainingPlan(String xmlFileLocation)
		throws Exception {
		FileInputStream is = new FileInputStream(xmlFileLocation);
		Unmarshaller u = jc.createUnmarshaller();
                TrainingPlan tp = (TrainingPlan)u.unmarshal(is);
		return tp;
	}

}
