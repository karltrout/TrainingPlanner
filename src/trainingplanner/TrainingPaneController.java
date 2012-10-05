/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;
import trainingplanner.controls.CalendarPickerController;
import trainingplanner.org.calendar.TrainingCalendarDay;
import trainingplanner.org.extensions.TrainingCalendarExt;

/**
 * FXML Controller class
 *
 * @author troutk
 */
public class TrainingPaneController extends AnchorPane  implements Initializable {
   @FXML private AnchorPane tdStartCalendar;
   @FXML private AnchorPane tdEndCalendar;
   
   @FXML private Text startDate;
   @FXML private Text endDate;
   @FXML private Label sDateLabel;
   @FXML private Label eDateLabel;
   
   @FXML private Group addTrainingDays;
   @FXML private AnchorPane trainingPlanWorkoutDialog;
   
   @FXML private ListView trainingDaysList;
   
   
   private SimpleObjectProperty<Calendar> selectedStartDate;
   private SimpleObjectProperty<Color> color = new SimpleObjectProperty<>(Color.ALICEBLUE);
   private CalendarPickerController startCalendar;
   private CalendarPickerController endCalendar;
   private DateFormat df = new SimpleDateFormat("EEEE - MM/dd/yyyy");
   private TrainingCalendarExt trainingCalendar;
   
   private SimpleObjectProperty<Calendar> selectedEndDate;
    private TrainingCalendarDayHBox selected;
   
    public TrainingPaneController(TrainingCalendarExt _trainingCalendar, SimpleObjectProperty<Color> color){  
        trainingCalendar = _trainingCalendar;
        URL location = getClass().getResource("FXML/TrainingPane.fxml");
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
        
        sDateLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                showHidePane(tdStartCalendar);
            }
        });
        
        eDateLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                showHidePane(tdEndCalendar);
            }
        });
        color = new SimpleObjectProperty<>(Color.ALICEBLUE);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        addTrainingDays.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                showHidePane(trainingPlanWorkoutDialog);
            }
        });
        
        startDate.setText(df.format(Calendar.getInstance().getTime()));
        endDate.setText(df.format(Calendar.getInstance().getTime()));
        
        selectedStartDate = new SimpleObjectProperty<>(Calendar.getInstance());
        
        startCalendar = new CalendarPickerController(selectedStartDate, color);
        tdStartCalendar.getChildren().clear();
        tdStartCalendar.getChildren().add(startCalendar);
        selectedEndDate = new SimpleObjectProperty<>(Calendar.getInstance());

        selectedStartDate.addListener(new ChangeListener<Calendar>() {
            @Override
            public void changed(ObservableValue<? extends Calendar> ov, Calendar t, Calendar t1) {
                if(t1.before(selectedEndDate.getValue())){
                startDate.setText(df.format(t1.getTime()));
                startDate.setFill(Color.BLACK);
                showHidePane(tdStartCalendar);
                }
                else {
                    startDate.setFill(Color.RED);
                }
            }
        });
       
       selectedEndDate.addListener(new ChangeListener<Calendar>() {
           @Override
           public void changed(ObservableValue<? extends Calendar> ov, Calendar t, Calendar t1) {
               if(t1.after(selectedStartDate.getValue())){
                    endDate.setText(df.format(t1.getTime()));
                   endDate.setFill(Color.BLACK);
                    showHidePane(tdEndCalendar);
                }
               else{
                   endDate.setFill(Color.RED);
               }
           }
       });
       endCalendar = new CalendarPickerController(selectedEndDate, color);
       tdEndCalendar.getChildren().clear();
       tdEndCalendar.getChildren().add(endCalendar);
       
       trainingDaysList.setCellFactory(new Callback<ListView<TrainingCalendarDay>, ListCell<TrainingCalendarDay>>() {
           @Override public ListCell<TrainingCalendarDay> call(ListView<TrainingCalendarDay> list) {
               
                return new TrainingCalendarDayHBox();
           }
        });
       trainingDaysList.setItems(trainingCalendar.getAllTrainingDays());
       
    }
    
    private void showHidePane(AnchorPane pane){
        pane.setVisible(!pane.isVisible());
    }
    
    private void setSelectedWorkoutFromList(final TrainingCalendarDayHBox wb){
        if (selected != null){
           // selected.workout.getSportsTypeProperty().unbind();
        }
        
        selected = wb;
    }
    
    class TrainingCalendarDayHBox extends ListCell<TrainingCalendarDay>{
         private  Text woDate;
         
        @Override
        public void updateItem(TrainingCalendarDay item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                
                woDate = new Text(df.format(item.getCalendar().getTime()));

                setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent t) {
                        setSelectedWorkoutFromList((TrainingCalendarDayHBox)t.getSource());
                    }
                });
            }
            
            HBox rect = new HBox();
            rect.setSpacing(5.0);
            rect.setAlignment(Pos.CENTER_LEFT);
            if (item != null) {
                rect.getChildren().add(woDate);
                setGraphic(rect);
            }
        }
     }
}
