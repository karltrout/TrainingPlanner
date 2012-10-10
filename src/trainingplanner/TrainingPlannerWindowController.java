/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner;

import java.io.File;
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
import javafx.scene.transform.Scale;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import trainingplanner.controls.DailyCountDownController;
import trainingplanner.controls.PaceClockController;
import trainingplanner.controls.SpeedOMeterController;
import trainingplanner.org.calendar.TrainingCalendarDay;
import trainingplanner.org.extensions.AthleteExt;
import trainingplanner.org.extensions.TrainingCalendarExt;
import trainingplanner.org.extensions.TrainingPlanExt;
import trainingplanner.org.extensions.WorkoutExt;
import trainingplanner.org.xsd.TrainingPlan;
import trainingplanner.org.xsd.WeightLiftingDatabase;
import trainingplanner.org.xsd.WeightLiftingExerciseDataBase;
import trainingplanner.org.xsd.garmin.TrainingCenterDatabaseT;

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
    private PaperBackController currentNotePad;
    final private String planFile = "trainingPLan.xml";
    private String tcdFile = "data/bike_test.tcx";
    private JAXBContext jcTCD;
    private TrainingCenterDatabaseT tcd;
    private TrainingCalendarExt trainingCalendar;
    private WorkoutEditorController workoutEditor;
    private String weightsDbFile = "WeightExercisesDataBase.xml";
    private WeightLiftingDatabase WeightExerciseDataBase;
    static private ExersizeDataBase WeightsExersizeDBSerializer;
    
    
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
        try {
                 WeightsExersizeDBSerializer = new ExersizeDataBase("trainingplanner.org.xsd",weightsDbFile);
                if (new File(weightsDbFile).exists()){
                     WeightExerciseDataBase = (WeightLiftingDatabase) WeightsExersizeDBSerializer.deserializeXMLTodataBase();
                }
                else {
                     WeightExerciseDataBase = new WeightLiftingDatabase();
                     WeightsExersizeDBSerializer.serializeObjectToXML(WeightExerciseDataBase); 
                }
            } catch (Exception ex) {
                Logger.getLogger(TrainingPlannerWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        loadTrainingPlan();
        initializeDashBoard();        
        initializeWorkouts();
        initializeTrainingPane();
        
        
        
        switchWindowViewTo("dashboard");
    }
    
    
    private void displayNotePad(PaperBackController _currentNotePad) {
        if (!_currentNotePad.isVisible()){
            _currentNotePad.setVisible(true);
        }
        _currentNotePad.setTrainingDay(selectedCalendarDate.get()); 
    }
    
    void displayWorkoutEditor(WorkoutExt wo){
        workoutEditor.setWorkout(wo);
        workoutEditor.setVisible(!workoutEditor.isVisible());
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
    
    public TrainingCenterDatabaseT XMLToTCD(String xmlFileLocation)
      throws Exception {
        FileInputStream is = new FileInputStream(xmlFileLocation);
        Unmarshaller u = jcTCD.createUnmarshaller();
        javax.xml.bind.JAXBElement tcd = (javax.xml.bind.JAXBElement)u.unmarshal(is);
        return (TrainingCenterDatabaseT)tcd.getValue();   
    }
    
    private void initializeDashBoard() {
        selectedCalendarDate = new SimpleObjectProperty<>();
        selectedCalendarDate.addListener(new ChangeListener<TrainingCalendarDay>() {
            @Override
            public void changed(ObservableValue<? extends TrainingCalendarDay> ov, TrainingCalendarDay t, TrainingCalendarDay t1) {
                displayNotePad(currentNotePad);
            }
        });
        
        currentNotePad = new PaperBackController(trainingCalendar);
        currentNotePad.setVisible(false);
        rootPane.getChildren().add(currentNotePad);
        
        athlete = trainingPlan.getAthlete();
        athleteName.setText(athlete.getFullName());
        athleteAge.setText(String.valueOf(athlete.getAge()));
        athleteWeight.setText(String.valueOf(athlete.getWeight()));
        athleteDOB.setText(String.format(dateFormatString,athlete.getDateOfBirth().toGregorianCalendar()));
        
        calendarPane.getChildren().clear();
        TrainingPlannerCalendarController calendar = new TrainingPlannerCalendarController(selectedCalendarDate, trainingCalendar, color);
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
        Scale scaleTransform = new Scale(.5, .5, 100, 100);
       bikeSpeed.getTransforms().add(scaleTransform);
        //bikeSpeed.setScaleX(0.5);
        //bikeSpeed.setScaleY(0.5);
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
            jcTCD = JAXBContext.newInstance("trainingplanner.org.xsd.garmin");
            
        } catch (JAXBException ex) {
            Logger.getLogger(TrainingPlannerWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        trainingPlan = new TrainingPlanExt();
        try {
            trainingPlan.setRoot(deserializeXMLToTrainingPlan(planFile));
            tcd = XMLToTCD(tcdFile);
        } catch (Exception ex) {
            Logger.getLogger(TrainingPlannerWindowController.class.getName()).log(Level.SEVERE, null, ex);
            trainingPlan.initialize();
        }
        trainingCalendar = trainingPlan.getTrainingCalendar();
        
    }
    
    private void switchWindowViewTo(String mainWindowView) {
        if(mainWindowView == null){ return;}             
        dashboardButton.getStyleClass().remove("tab-header-text-selected");  
        workoutButton.getStyleClass().remove("tab-header-text-selected");
        trainingButton.getStyleClass().remove("tab-header-text-selected");

        if (mainWindowView.equals("dashboard")){
            dashBoardPane.setVisible(true);
            workoutsPane.setVisible(false);
            trainingPane.setVisible(false);
            dashboardButton.getStyleClass().add("tab-header-text-selected"); 
            return;
        }
        if (mainWindowView.equals("training")){
            dashBoardPane.setVisible(false);
            workoutsPane.setVisible(false);
            trainingPane.setVisible(true);
            trainingButton.getStyleClass().add("tab-header-text-selected");           
            return;
        }
        if (mainWindowView.equals("workouts")){
            dashBoardPane.setVisible(false);
            workoutsPane.setVisible(true);
            trainingPane.setVisible(false);                 
            workoutButton.getStyleClass().add("tab-header-text-selected");
        }
    }

    private void initializeWorkouts() {
        workoutsPane.getChildren().clear();
        WorkoutsPaneController workouts = new WorkoutsPaneController(this, trainingCalendar, color);
        //workouts.setActivities(tcd);
        AnchorPane.setLeftAnchor(workouts, 0.0);
        AnchorPane.setBottomAnchor(workouts, 0.0);
        AnchorPane.setRightAnchor(workouts, 0.0);
        AnchorPane.setTopAnchor(workouts, 0.0);
        
        workouts.setMaxHeight(workouts.USE_COMPUTED_SIZE);
        workouts.setMaxWidth(workouts.USE_COMPUTED_SIZE);
        
        workoutsPane.getChildren().add(workouts);
        workoutsPane.setVisible(false);
        
        workoutEditor = new WorkoutEditorController(WeightExerciseDataBase);
        workoutEditor.setVisible(false);
        rootPane.getChildren().add(workoutEditor);
    }
    
    static public void saveWeightTrainingDatabase( WeightLiftingDatabase sWeightExerciseDataBase) throws Exception{
                     WeightsExersizeDBSerializer.serializeObjectToXML(sWeightExerciseDataBase); 
    }
    
    
    private void initializeTrainingPane() {
        trainingPane.getChildren().clear();
        TrainingPaneController training = new TrainingPaneController(trainingCalendar, color);

        AnchorPane.setLeftAnchor(training, 0.0);
        AnchorPane.setBottomAnchor(training, 0.0);
        AnchorPane.setRightAnchor(training, 0.0);
        AnchorPane.setTopAnchor(training, 0.0);
        
        training.setMaxHeight(training.USE_COMPUTED_SIZE);
        training.setMaxWidth(training.USE_COMPUTED_SIZE);
        //workouts.setActivities(tcd);
        trainingPane.getChildren().add(training);
        trainingPane.setVisible(false);
    }

}
