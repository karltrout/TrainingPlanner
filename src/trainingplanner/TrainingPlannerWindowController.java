/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner;

import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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
    private SimpleObjectProperty<Color> color = new SimpleObjectProperty<>(this, "color", new Color(1,1,0.4,1));
    private TrainingPlanExt trainingPlan;
    private AthleteExt athlete;
    
    @FXML  private Label athleteName;
    @FXML  private Label athleteAge;
    @FXML  private Label athleteDOB;
    @FXML  private Label athleteWeight;
    @FXML  private Label todaysDate;
    // main Windows selector buttons
    @FXML  private Label dashboardButton;
    @FXML  private Label workoutButton;
    @FXML  private Label trainingButton;
    // corrisponding anchorPanes - would love some animation here
    @FXML  private AnchorPane dashBoardPane;
    @FXML  private AnchorPane workoutsPane;
    @FXML  private AnchorPane trainingPane;
    
    @FXML  private StackPane goalsPane;
    @FXML  private StackPane calendarPane;
    @FXML  private FlowPane goalIcons;
    @FXML  private ColorPicker colorPicker;
    @FXML  private AreaChart volumeChart;
    @FXML  private AnchorPane rootPane;
    
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
            trainingPlan.getTrainingCalendar().prune();
            try {
                serializeObjectToXML(planFile,trainingPlan);
            } catch (Exception ex) {
                Logger.getLogger(TrainingPlannerWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        todaysDate.setText(String.format(dateFormatString, new GregorianCalendar()));
        color.bind(colorPicker.valueProperty());
        colorPicker.valueProperty().setValue(Color.LIME);
        
        dashboardButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                switchWindowViewTo("dashboard");
            }
        });
        
        workoutButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                switchWindowViewTo("workouts");
            }
        });
        
        trainingButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                switchWindowViewTo("training");
            }
        });
        
        loadTrainingPlan();
        initializeDashBoard();        
        
        switchWindowViewTo("dashboard");
    }
    
    
    private void displayNotePad(PaperBackController _currentNotePad) {
        if (!_currentNotePad.isVisible()){
            _currentNotePad.setVisible(true);
        }
        _currentNotePad.setTrainingDay(selectedCalendarDate.get()); 
    }
    
    /**
    * This method saves (serializes) any java bean object into xml file
    */
    public void serializeObjectToXML(String xmlFileLocation, Object objectToSerialize) throws Exception {
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

    private void initializeDashBoard() {
        selectedCalendarDate = new SimpleObjectProperty<>();
        selectedCalendarDate.addListener(new ChangeListener<TrainingCalendarDay>() {
            @Override
            public void changed(ObservableValue<? extends TrainingCalendarDay> ov, TrainingCalendarDay t, TrainingCalendarDay t1) {
                displayNotePad(currentNotePad);
            }
        });
        
        currentNotePad.setVisible(false);
        rootPane.getChildren().add(currentNotePad);
        
        athlete = trainingPlan.getAthlete();
        athleteName.setText(athlete.getFullName());
        athleteAge.setText(String.valueOf(athlete.getAge()));
        athleteWeight.setText(String.valueOf(athlete.getWeight()));
        athleteDOB.setText(String.format(dateFormatString,athlete.getDateOfBirth().toGregorianCalendar()));
        
        calendarPane.getChildren().clear();
        TrainingPlannerCalendarController calendar = new TrainingPlannerCalendarController(selectedCalendarDate, trainingPlan.getTrainingCalendar(), color);
        calendar.updateCalendar();
        currentNotePad.setParentCalendar(calendar);
        calendarPane.getChildren().add(calendar);
        
        goalsPane.getChildren().clear();       
        TrainingPlannerGoalsController goalsControler = new TrainingPlannerGoalsController();
        goalsControler.setKPIs(athlete.getKeyPerformanceIndicators());
        goalsPane.getChildren().add(goalsControler);  

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

    private void loadTrainingPlan() {
        try {
            jc = JAXBContext.newInstance( "trainingplanner.org.xsd" );
        } catch (JAXBException ex) {
            Logger.getLogger(TrainingPlannerWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        trainingPlan = new TrainingPlanExt();
        try {
            trainingPlan.setRoot(deserializeXMLToTrainingPlan(planFile));
            
        } catch (Exception ex) {
            Logger.getLogger(TrainingPlannerWindowController.class.getName()).log(Level.SEVERE, null, ex);
            trainingPlan.initialize();
        }
    }
    
    private void switchWindowViewTo(String mainWindowView) {
                if(mainWindowView == null){ return;}
                
                if (mainWindowView.equals("dashboard")){
                    dashBoardPane.setVisible(true);
                    workoutsPane.setVisible(false);
                    trainingPane.setVisible(false);
                    return;
                }
                if (mainWindowView.equals("training")){
                    dashBoardPane.setVisible(false);
                    workoutsPane.setVisible(false);
                    trainingPane.setVisible(true);                  
                    return;
                }
                if (mainWindowView.equals("workouts")){
                    dashBoardPane.setVisible(false);
                    workoutsPane.setVisible(true);
                    trainingPane.setVisible(false);                 
                }
            }
}
