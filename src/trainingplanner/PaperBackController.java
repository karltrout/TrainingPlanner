/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Group;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;
import trainingplanner.org.calendar.TrainingCalendarDay;
import trainingplanner.org.extensions.WorkoutExt;
import trainingplanner.org.xsd.IWorkoutType;
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
    @FXML ListView<WorkoutExt> workoutList;
    
    TrainingCalendarDay trainingDay;
    
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
        
        trainingDay = new TrainingCalendarDay(); 
        //trainingDate.textProperty().bind(trainingDay.getDate());
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
        
        //workoutList.setItems(data);
 
        workoutList.setCellFactory(new Callback<ListView<WorkoutExt>, ListCell<WorkoutExt>>() {
            @Override public ListCell<WorkoutExt> call(ListView<WorkoutExt> list) {
                return new WorkoutHBox();
            }
        });
        
         ObservableList<PieChart.Data> calorieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Protein", 75),
                new PieChart.Data("Fat", 40),
                new PieChart.Data("Carbs", 130));
         
         calorieChart.dataProperty().set(calorieChartData);
         
                 
         ObservableList<PieChart.Data> workoutLoadChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Swimming", 90),
                new PieChart.Data("Running", 45),
                new PieChart.Data("Cycling", 180),
                new PieChart.Data("Weights", 45),
                new PieChart.Data("Rest", 30));
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
        
            trainingDay = _trainingCalendarDay;
            trainingDate.setText(String.format("%1$tb %1$te,%1$tY",trainingDay.getCalendar()));
            ObservableList<PieChart.Data> workoutLoadChartData = FXCollections.observableArrayList();
            for(IWorkoutType workout :trainingDay.getTrainingDay().getWorkoutType()){
                workoutLoadChartData.add(new PieChart.Data(workout.getSportType().name(), workout.getIntensity()));
            }
            WorkoutLoadChart.dataProperty().set(workoutLoadChartData);
    }
    
    private void editWorkoutInfo(){
        workoutEditBox.setVisible(!workoutEditBox.isVisible());
    }
    
    private void addWorkout(){
       workoutList.getItems().add(new WorkoutExt());
    }
    
    private boolean deleteWorkout(){
        
        return false;
    }
    
    private boolean editWorkout(){
    
        return false;
    }
    
     class Delta { double x, y; } 
     
     class WorkoutHBox extends ListCell<WorkoutExt>{
         private final Text title;
         private final Text intensity;
         private final Text volume;
         private final Text duration;
         private boolean selected;
         
         WorkoutHBox(String _title, int _intesity, int _volume, int _duration ){
             title = new Text(_title);
             intensity = new Text(String.valueOf(_intesity));
             volume = new Text(String.valueOf(_volume));
             duration = new Text(String.valueOf(_duration));
             selected = false;
             this.setText(_title);
             this.setOnMouseClicked(new EventHandler<MouseEvent>() {

                 @Override
                 public void handle(MouseEvent t) {
                     selected = true;
                     getStyleClass().add("workoutSelected");
                     System.out.println(title.getText() + " Selected.");
                 }
             });
             
         }
         
         WorkoutHBox(){
             this("demo", 10, 10, 60 );
         }
         
     }
}
