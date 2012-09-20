/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;
import trainingplanner.org.calendar.TrainingCalendarDay;
import trainingplanner.org.calendar.TrainingCalendarDuration;
import trainingplanner.org.extensions.WorkoutExt;
import trainingplanner.org.xsd.IWorkoutType;
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
    
    @FXML ListView<WorkoutExt> workoutList;
    private ObservableList<WorkoutExt> workouts = FXCollections.observableArrayList();;
    private TrainingCalendarDay trainingDay = new TrainingCalendarDay();;
    private WorkoutHBox selected;
/* default Constructor 
 * 
 */
public PaperBackController(){                   
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
    }

    public void setTrainingDay(TrainingCalendarDay _trainingCalendarDay) {    
        if (selected != null){
            selected.workout.getSportsTypeProperty().unbind();
        }  
        trainingDay = _trainingCalendarDay;
        trainingDate.setText(String.format("%1$tb %1$te,%1$tY",trainingDay.getCalendar()));
        workouts.clear();

        ObservableList<PieChart.Data> workoutLoadChartData = FXCollections.observableArrayList();
        for(IWorkoutType workout :trainingDay.getTrainingDay().getWorkoutType()){
            workoutLoadChartData.add(new PieChart.Data(workout.getSportType().name(), workout.getIntensity()));
            workouts.add((WorkoutExt)workout);
        }
        WorkoutLoadChart.dataProperty().set(workoutLoadChartData);
        workoutList.setItems(workouts);
    }
    
    private void editWorkoutInfo(){
        workoutEditBox.setVisible(!workoutEditBox.isVisible());
    }
    
    private void addWorkout(){
        WorkoutExt wo = new WorkoutExt();      
        workoutList.getItems().add(wo);
        trainingDay.getTrainingDay().getWorkoutType().add(wo);
    }
    
    private boolean deleteWorkout(){
        WorkoutExt selected = workoutList.getSelectionModel().getSelectedItem();       
        workoutList.getItems().remove(selected);
        trainingDay.getTrainingDay().getWorkoutType().remove(selected);
        return true;
    }
    
    private boolean editWorkout(){
        //WorkoutExt selected = workoutList.getSelectionModel().getSelectedItem();
        trainingDay.getTrainingDay().getWorkoutType().clear();
        trainingDay.getTrainingDay().getWorkoutType().addAll(workouts);
        return true;
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
               editWorkout();
            }
        });
        
        addWorkoutButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
               addWorkout();
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
        ObservableList<PieChart.Data> calorieChartData =
               FXCollections.observableArrayList(
               new PieChart.Data("Protein", 75),
               new PieChart.Data("Fat", 40),
               new PieChart.Data("Carbs", 130));
                        
        ObservableList<PieChart.Data> workoutLoadChartData =
               FXCollections.observableArrayList(
               new PieChart.Data("Swimming", 90),
               new PieChart.Data("Running", 45),
               new PieChart.Data("Cycling", 180),
               new PieChart.Data("Weights", 45),
               new PieChart.Data("Rest", 30));
               
        calorieChart.dataProperty().set(calorieChartData);
        WorkoutLoadChart.dataProperty().set(workoutLoadChartData);

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
                    System.out.println("clicked on "+data.getName());
                }
            });
        }
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
    
    private void setSelected(final WorkoutHBox wb){
        if (selected != null){
            selected.workout.getSportsTypeProperty().unbind();
        }
        System.out.println(wb.workout.getSportType().name()+" Selected");
        sportsTypes.getSelectionModel().select(wb.workout.getSportType());
        wb.workout.getSportsTypeProperty().bind(sportsTypes.valueProperty());
        selected = wb;
        
        intensity.setText(String.valueOf(wb.workout.getIntensity()));
        volume.setText(String.valueOf(wb.workout.getVolume()));
        //duration.setText(String.valueOf(wb.workout.getDuration()));
    }
    
     class Delta { double x, y; } 
     
     class WorkoutHBox extends ListCell<WorkoutExt>{
         private Text title;
         private  Text intensity;
         private  Text volume;
         private  Text duration;
         private WorkoutExt workout;
         
        @Override
        public void updateItem(WorkoutExt item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                workout = item;
                title = new Text();
                title.textProperty().bind(item.getSportsTypeNameProperty());

                intensity = new Text(String.valueOf(item.getIntensity()));
                volume = new Text(String.valueOf(item.getVolume()));
                TrainingCalendarDuration td = (TrainingCalendarDuration) item.getDuration();
                System.out.print(td.ToString());
                duration = new Text("0");

                setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent t) {
                        setSelected((WorkoutHBox)t.getSource());
                    }
                });
            }
            HBox rect = new HBox();
            rect.setSpacing(5.0);
            rect.setAlignment(Pos.CENTER_LEFT);
            if (item != null) {
                rect.getChildren().add(title);
                rect.getChildren().add(intensity);
                rect.getChildren().add(volume);
                rect.getChildren().add(duration);
                setGraphic(rect);
            }
        }
     }
}
