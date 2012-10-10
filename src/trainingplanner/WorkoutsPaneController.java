/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;
import trainingplanner.org.extensions.TrainingCalendarExt;
import trainingplanner.org.extensions.WorkoutExt;
import trainingplanner.org.xsd.ExcersizeType;
import trainingplanner.org.xsd.garmin.ActivityT;

/**
 * FXML Controller class
 *
 * @author troutk
 */
public class WorkoutsPaneController extends AnchorPane implements Initializable {
    
    private ObservableList<ActivityT> workouts = FXCollections.observableArrayList();
    @FXML private ListView<WorkoutExt> workoutList;
    @FXML private ListView<ExcersizeType> workoutDetailList;
    @FXML private LineChart maxLapSpeed;
        
    @FXML Group workoutDetailsRemoveButton;
    @FXML Group workoutDetailsAddButton;
    @FXML Group workoutDetailsEditButton;
    
    private TrainingPlannerWindowController parentPane;
    
    private TrainingCalendarExt trainingCalendar;
    private SimpleObjectProperty<Color> color;
    private DateFormat df = new SimpleDateFormat("EEEE - MMM. dd,yyyy");
    private DateFormat dfTime = new SimpleDateFormat("HH:mm:ss a");
    private SimpleObjectProperty<WorkoutExt> selectedWorkout;
    private ExcersizeType selectedDetail;
    
    WorkoutsPaneController(TrainingPlannerWindowController parent, TrainingCalendarExt _trainingCalendar, SimpleObjectProperty<Color> _color){
        trainingCalendar = _trainingCalendar;
        color = _color;      
        
        URL location = getClass().getResource("FXML/WorkoutsPane.fxml");
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
        
        parentPane = parent;
        workoutList.setItems(trainingCalendar.getAllWorkouts());
        if (! workoutList.getItems().isEmpty()){
            setDetailsList(workoutList.getItems().get(0));
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       workoutList.setCellFactory(new Callback<ListView<WorkoutExt>, ListCell<WorkoutExt>>() {
          
           @Override public ListCell<WorkoutExt> call(ListView<WorkoutExt> list) {
               
                return new ActivityHBox();
           }
        });
       workoutList.getItems().clear();
        
       workoutDetailList.setCellFactory(new Callback<ListView<ExcersizeType>, ListCell<ExcersizeType>>() {

            @Override
            public ListCell<ExcersizeType> call(ListView<ExcersizeType> p) {
                return new WorkoutDetailsHBox();
            }
        });
       
       Axis yAxis =  maxLapSpeed.getYAxis(); 
       initializeButtonActions();
       isEditable(false);
       selectedWorkout = new SimpleObjectProperty<>();
            selectedWorkout.addListener(new ChangeListener<WorkoutExt>() {
            @Override
            public void changed(ObservableValue<? extends WorkoutExt> ov, WorkoutExt t, WorkoutExt t1) {
                setDetailsList(t1);
                if(selectedWorkout.get() != null)
                    isEditable(true);
                else isEditable(false);
            }
        });
    }

    /*void setActivities(TrainingCenterDatabaseT tcd) {
        for (ActivityT activity : tcd.getActivities().getActivity()){
            workouts.add(activity);

            XYChart.Series series = new XYChart.Series();
            series.setName("Speed");
            //populating the series with data
            int start = 0;
            for( ActivityLapT lap:activity.getLap()){
                
                //String time = String.format("%1$tb %1$te, %1$tY",lap.getStartTime());
                GregorianCalendar date = lap.getStartTime().toGregorianCalendar();
                //if (start == 0) start = date.get(Calendar.HOUR_OF_DAY)*60*60;
                int time = (date.get(Calendar.HOUR_OF_DAY)*60*60)+(date.get(Calendar.MINUTE)*60)+(date.get(Calendar.SECOND)); 
                series.getData().add(new XYChart.Data(time, lap.getMaximumSpeed()));
            }
            maxLapSpeed.getData().add(series);
        }
            workoutList.setItems(workouts);
    }*/
    
  /*  public void setWorkouts(ObservableList<WorkoutExt> _workouts){
        workoutList.setItems(_workouts);
        selectedWorkout = _workouts.get(0);
    }
    * */

    private void isEditable(boolean editMode) {
        if (editMode){
            workoutDetailsAddButton.setDisable(false);
            if (selectedDetail != null){
                workoutDetailsEditButton.setDisable(false);
                workoutDetailsRemoveButton.setDisable(false);
            }
            else {
                workoutDetailsEditButton.setDisable(true);
                workoutDetailsRemoveButton.setDisable(true);
            }
        }
        else {
            workoutDetailsAddButton.setDisable(true);
            workoutDetailsEditButton.setDisable(true);
            workoutDetailsRemoveButton.setDisable(true);
        }
    }

    private void setDetailsList(WorkoutExt workout) {
        if (workout != null){
            workoutDetailList.setItems(workout.getExcersizes());
        }
    }

    private static class WorkoutDetailsHBox extends ListCell<ExcersizeType> {
         private Text title = new Text();
        @Override
        public void updateItem(ExcersizeType item, boolean empty) {
         super.updateItem(item, empty);
            title.getStyleClass().add("workout-listbox-title-text");
             if (item != null) {
                title.textProperty().set(item.getName());  
                 setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent t) {
                       //setSelectedWorkoutFromList((ActivityHBox)t.getSource());
                    }
                });
                 
                HBox rect = new HBox();
                rect.setSpacing(5.0);
                rect.setAlignment(Pos.CENTER_LEFT);
                rect.getChildren().add(title);
                setGraphic(rect);
            }
        }
    }
    
    
    class ActivityHBox extends ListCell<WorkoutExt>{
        private Text title = new Text();
        private Text woDateTxt;
        private Text woTimeTxt;
        private ImageView im;
         
        @Override
        public void updateItem(WorkoutExt item, boolean empty) {
            super.updateItem(item, empty);
            title.getStyleClass().add("workout-listbox-title-text");
            woDateTxt = new Text();
            woDateTxt.getStyleClass().add("workout-listbox-text");
            woTimeTxt = new Text();
            woTimeTxt.getStyleClass().add("workout-listbox-text");
            im = new ImageView();
            if (item != null) {
                title.textProperty().bind(item.getSportsTypeNameProperty());               
                woDateTxt = new Text(df.format(item.getWorkoutDate().getTime()));
                woTimeTxt = new Text(dfTime.format(item.getWorkoutDate().getTime()));
                im = new ImageView(item.getSportsTypeIconImage());
                
                setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent t) {
                       setSelectedWorkoutFromList((ActivityHBox)t.getSource());
                    }
                });
            }
            //refreshWorkoutChart();
            
            AnchorPane border = new AnchorPane();
            border.setMaxSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
            border.setMinSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
            border.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
            
            AnchorPane.setTopAnchor(im, 0.0);
            AnchorPane.setLeftAnchor(im, 5.0);
            AnchorPane.setTopAnchor(title, 5.0);
            AnchorPane.setLeftAnchor(title, 36.0);
            AnchorPane.setTopAnchor(woDateTxt, 30.0);
            AnchorPane.setLeftAnchor(woDateTxt, 10.0);
            AnchorPane.setTopAnchor(woTimeTxt, 8.0);
            AnchorPane.setLeftAnchor(woTimeTxt, 150.0);
            
            border.getChildren().addAll(im,title,woDateTxt,woTimeTxt);
            
            HBox rect = new HBox();
            //rect.setSpacing(5.0);
            //rect.setAlignment(Pos.CENTER_LEFT);
            if (item != null) {
                rect.getChildren().add(border);
                setGraphic(rect);
            }
        }
     }
    private void setSelectedWorkoutFromList(ActivityHBox activityHBox) {
        selectedWorkout.set( activityHBox.getItem() );
    }
      
      private void initializeButtonActions() {
/*        closeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
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
 */       
        workoutDetailsEditButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
              parentPane.displayWorkoutEditor();
            }
        });
        
        workoutDetailsAddButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
              parentPane.displayWorkoutEditor();
               //addWorkout();
                //trainingDay.getWorkoutType().add(new IWorkoutType());
                //trainingDay.addWorkout(new WorkoutExt(trainingDay.getDate().toGregorianCalendar()));4
                //trainingCalendar.addWorkoutToTrainingDay(trainingDay, new WorkoutExt(trainingDay.getDate().toGregorianCalendar()));
            }
        });

        workoutDetailsRemoveButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
               
              parentPane.displayWorkoutEditor();
            }
        });
    }

}

