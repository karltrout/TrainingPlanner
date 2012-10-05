/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Pos;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;
import trainingplanner.org.extensions.TrainingCalendarExt;
import trainingplanner.org.xsd.garmin.ActivityLapT;
import trainingplanner.org.xsd.garmin.ActivityT;
import trainingplanner.org.xsd.garmin.TrainingCenterDatabaseT;

/**
 * FXML Controller class
 *
 * @author troutk
 */
public class WorkoutsPaneController extends AnchorPane implements Initializable {
    
    private ObservableList<ActivityT> workouts = FXCollections.observableArrayList();
    @FXML private ListView workoutList;
    @FXML private LineChart maxLapSpeed;
    private TrainingCalendarExt trainingCalendar;
    private SimpleObjectProperty<Color> color;
    
    WorkoutsPaneController(TrainingCalendarExt _trainingCalendar, SimpleObjectProperty<Color> _color){
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
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       workoutList.setCellFactory(new Callback<ListView<ActivityT>, ListCell<ActivityT>>() {
           @Override public ListCell<ActivityT> call(ListView<ActivityT> list) {
               
                return new WorkoutsPaneController.ActivityHBox();
           }
        });
        workoutList.getItems().clear();
       Axis yAxis =  maxLapSpeed.getYAxis();        
    }

    void setActivities(TrainingCenterDatabaseT tcd) {
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
    }
    
    class ActivityHBox extends ListCell<ActivityT>{
         private Text title;
         private  Text idTxt;
         private  Text volumeTxt;
         private  Text durationTxt;
         private ActivityT workout;
         
        @Override
        public void updateItem(ActivityT item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                workout = item;
                title = new Text(item.getSport().value());

                idTxt = new Text(item.getId().toXMLFormat());
                //volumeTxt.textProperty().set(String.valueOf(item.getVolume()));
                //TrainingCalendarDuration td = (TrainingCalendarDuration) item.getDuration();
                //System.out.print(td.ToString());
                //durationTxt = new Text("0");

                setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent t) {
                       // setSelectedWorkoutFromList((PaperBackController.WorkoutHBox)t.getSource());
                    }
                });
            }
            //refreshWorkoutChart();
            
            HBox rect = new HBox();
            rect.setSpacing(5.0);
            rect.setAlignment(Pos.CENTER_LEFT);
            if (item != null) {
                rect.getChildren().add(title);
                rect.getChildren().add(idTxt);
                //rect.getChildren().add(volumeTxt);
                //rect.getChildren().add(durationTxt);
                setGraphic(rect);
            }
        }
     }
     
}

