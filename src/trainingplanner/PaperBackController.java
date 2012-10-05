/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;
import trainingplanner.org.calendar.TrainingCalendarDay;
import trainingplanner.org.extensions.TrainingCalendarExt;
import trainingplanner.org.extensions.WorkoutExt;
import trainingplanner.org.xsd.ExcersizeType;
import trainingplanner.org.xsd.SportTypes;
/**
 * FXML Controller class
 *
 * @author Karl
 */
public class PaperBackController extends AnchorPane implements Initializable {

    
    @FXML Group closeButton;
    @FXML Text trainingDate;
    @FXML PieChart calorieChart;
    @FXML PieChart WorkoutLoadChart;
    @FXML ImageView editWorkoutInfoButton;
    @FXML AnchorPane workoutEditBox;
    @FXML Group deleteWorkoutButton;
    @FXML Group addWorkoutButton;
    @FXML Group editWorkoutButton;
    @FXML ChoiceBox sportsTypes;
    @FXML TextField intensity;
    @FXML TextField duration;
    @FXML TextField volume;
    @FXML TextArea description;
    @FXML ListView<WorkoutExt> workoutList;
    
    @FXML Text noteHoursSlept;
    @FXML Text noteSleepQuality;
    @FXML Text noteEnergyLevel;
    @FXML Text noteMoodLevel;    
    
    @FXML Text noteSportsName;
    @FXML Text noteIntensity;
    @FXML Text noteDuration;
    @FXML Text noteVolume;
    @FXML TextArea noteDescription;
    
    @FXML ListView<ExcersizeType> noteDetailList;

    //private ObservableList<WorkoutExt> workouts = FXCollections.observableArrayList();
    private TrainingCalendarDay trainingDay;
    private WorkoutHBox selected;
    private TrainingPlannerCalendarController parentCalendar;
    private boolean editing;
    private TrainingCalendarExt trainingCalendar;
/* default Constructor 
 * 
 */
    public PaperBackController(TrainingCalendarExt _trainingCalendar){  
        trainingCalendar = _trainingCalendar;
        trainingDay = trainingCalendar.getTrainingDay((GregorianCalendar)Calendar.getInstance());
 
        URL location = getClass().getResource("FXML/PaperBack.fxml");
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
        initializeButtonActions();
        initializeCharts();
        initializeWorkoutList();
        
        final Delta dragDelta = new Delta();
        setOnMousePressed(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            // record a delta distance for the drag and drop operation.
            dragDelta.x = getLayoutX() - mouseEvent.getScreenX();
            dragDelta.y = getLayoutY() - mouseEvent.getScreenY();
          }
        });
        setOnMouseDragged(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
                setLayoutX(mouseEvent.getScreenX() + dragDelta.x);
                setLayoutY(mouseEvent.getScreenY() + dragDelta.y);
          }
        });
    }
    
    private void hidePaperBackWindow(){
        this.setVisible(false);
        this.parentCalendar.updateCalendar();
    }

    public void setTrainingDay(TrainingCalendarDay _trainingCalendarDay) {    
        if (selected != null){
            selected.workout.getSportsTypeProperty().unbind();
        }  
        trainingDay = _trainingCalendarDay;
        trainingDate.setText(String.format("%1$tb %1$te,%1$tY",trainingDay.getCalendar()));
        
        WorkoutLoadChart.dataProperty().set(trainingDay.getWorkoutLoadChartData());
        workoutList.setItems(trainingDay.getObservableWorkOuts());
        if(workoutList.getItems().size()>0){
            setWorkoutDetails(workoutList.getItems().get(0));
        }
        else setWorkoutDetails(null);
    }
    
    private void editWorkoutInfo(){
        workoutEditBox.setVisible(!workoutEditBox.isVisible());
    }
    
    private boolean deleteWorkout(){
        WorkoutExt woselected = workoutList.getSelectionModel().getSelectedItem();       
        if (null != woselected){
            trainingCalendar.removeWorkoutFromTrainingDay(trainingDay, woselected);  
        return true;}
        else return false;
    }
    
    private boolean editWorkout(){
        WorkoutExt woselected = workoutList.getSelectionModel().getSelectedItem();
        if (woselected == null ) return false;
        editing =(editing)?false:true;
        intensity.setEditable(editing);
        duration.setEditable(editing);
        volume.setEditable(editing);
        description.setEditable(editing);
        if (editing){
            intensity.getStyleClass().remove("workout-editor-text-field");
            duration.getStyleClass().remove("workout-editor-text-field");
            volume.getStyleClass().remove("workout-editor-text-field");
        }
        else{
            intensity.getStyleClass().add("workout-editor-text-field");
            duration.getStyleClass().add("workout-editor-text-field");
            volume.getStyleClass().add("workout-editor-text-field");
            selected.workout.setVolume(Double.parseDouble(volume.getText()));
            selected.workout.setDuration(Integer.parseInt(duration.getText()));
            selected.workout.setIntensity(Double.parseDouble(intensity.getText()));
            selected.workout.setDescription(description.getText());            
            refreshWorkoutChart();
            this.parentCalendar.updateCalendar();
        }
        return editing;
    }

    private void initializeButtonActions() {
        closeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                hidePaperBackWindow();
            }
        });
        
        editWorkoutInfoButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
               editWorkoutInfo();
            }
        });
        
        editWorkoutButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
              editing = editWorkout();
            }
        });
        
        addWorkoutButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
               //addWorkout();
                //trainingDay.getWorkoutType().add(new IWorkoutType());
                //trainingDay.addWorkout(new WorkoutExt(trainingDay.getDate().toGregorianCalendar()));4
                trainingCalendar.addWorkoutToTrainingDay(trainingDay, new WorkoutExt(trainingDay.getDate().toGregorianCalendar()));
            }
        });

        deleteWorkoutButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
               deleteWorkout();
            }
        });
    }

    private void initializeCharts() {
        refreshNutritionChart();
        refreshWorkoutChart();
    }

    private void initializeWorkoutList() {
        workoutList.setCellFactory(new Callback<ListView<WorkoutExt>, ListCell<WorkoutExt>>() {
           @Override public ListCell<WorkoutExt> call(ListView<WorkoutExt> list) {
               
                return new WorkoutHBox();
           }
        });
        
        sportsTypes.getItems().clear();
        sportsTypes.getItems().addAll(Arrays.asList(SportTypes.values()));
    }
    
    private void setSelectedWorkoutFromList(final WorkoutHBox wb){
        if (selected != null){
            selected.workout.getSportsTypeProperty().unbind();
        }
        
        sportsTypes.getSelectionModel().select(wb.workout.getSportType());
        wb.workout.getSportsTypeProperty().bind(sportsTypes.valueProperty());

        intensity.setText(String.valueOf(wb.workout.getIntensity()));
        volume.setText(String.valueOf(wb.workout.getVolume()));
        duration.setText(String.valueOf(wb.workout.getDuration()));
        description.setText(wb.workout.getDescription());
        
        noteDetailList.setItems(wb.workout.getExcersizes());
        
        selected = wb;
    }

    private void refreshWorkoutChart() {
        trainingDay.refreshLoadChartData();
        final HashMap<PieChart.Data, WorkoutExt> dataMap = trainingDay.getDataMap();
        final Label caption = new Label("");
            caption.setTextFill(Color.DARKORANGE);
            caption.setStyle("-fx-font: 24 arial;");
            
        for (final PieChart.Data data : WorkoutLoadChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
            new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent e) {
                    caption.setTranslateX(e.getSceneX());
                    caption.setTranslateY(e.getSceneY());
                    caption.setText(String.valueOf(data.getPieValue()) + "%");
                    WorkoutExt workout = dataMap.get(data);
                    setWorkoutDetails(workout);
                }
            });
        }
    }

    private void setWorkoutDetails(WorkoutExt workout) {
        if (null == workout){
            noteSportsName.setText("");
            noteIntensity.setText("");
            noteDuration.setText("");
            noteVolume.setText("");
            noteDescription.setText("");
            return;
        }
        noteSportsName.setText(workout.getSportType().value());
        noteIntensity.setText(String.valueOf(workout.getIntensity()));
        noteDuration.setText(String.valueOf(workout.getDuration()));
        noteVolume.setText(String.valueOf(workout.getVolume()));
        noteDescription.setText(workout.getDescription());
    }
    
    private void refreshNutritionChart() {
        ObservableList<PieChart.Data> calorieChartData =
               FXCollections.observableArrayList(
               new PieChart.Data("Protein", 75),
               new PieChart.Data("Fat", 40),
               new PieChart.Data("Carbs", 130));
        calorieChart.dataProperty().set(calorieChartData);
    }

    /**
     * @param parentCalendar the parentCalendar to set
     */
    public void setParentCalendar(TrainingPlannerCalendarController parentCalendar) {
        this.parentCalendar = parentCalendar;
    }
    
     class Delta { double x, y; } 
     
     class WorkoutHBox extends ListCell<WorkoutExt>{
         private Text title;
         private  Text intensityTxt;
         private  Text volumeTxt;
         private  Text durationTxt;
         private WorkoutExt workout;
         
        @Override
        public void updateItem(WorkoutExt item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                workout = item;
                title = new Text();
                volumeTxt = new Text();
                title.textProperty().bind(item.getSportsTypeNameProperty());

                intensityTxt = new Text(String.valueOf(item.getIntensity()));
                volumeTxt.textProperty().set(String.valueOf(item.getVolume()));
                //TrainingCalendarDuration td = (TrainingCalendarDuration) item.getDuration();
                //System.out.print(td.ToString());
                durationTxt = new Text("0");

                setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent t) {
                        setSelectedWorkoutFromList((WorkoutHBox)t.getSource());
                    }
                });
            }
            refreshWorkoutChart();
            
            HBox rect = new HBox();
            rect.setSpacing(5.0);
            rect.setAlignment(Pos.CENTER_LEFT);
            if (item != null) {
                rect.getChildren().add(title);
                rect.getChildren().add(intensityTxt);
                rect.getChildren().add(volumeTxt);
                rect.getChildren().add(durationTxt);
                setGraphic(rect);
            }
        }
     }
}
