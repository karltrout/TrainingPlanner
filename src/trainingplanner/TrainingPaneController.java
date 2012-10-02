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
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import trainingplanner.controls.CalendarPickerController;

/**
 * FXML Controller class
 *
 * @author troutk
 */
public class TrainingPaneController extends AnchorPane  implements Initializable {
   @FXML private AnchorPane tdCalendar;
   @FXML private Text startDate;
   @FXML private Label sDateLabel;
   
   private SimpleObjectProperty<Calendar> selectedDate;
   private SimpleObjectProperty<Color> color = new SimpleObjectProperty<>(Color.ALICEBLUE);
   private CalendarPickerController calendar;
   private DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    public TrainingPaneController(){
                
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
                showStartDateCalendar();
            }
        });
        
        color = new SimpleObjectProperty<>(Color.ALICEBLUE);
        //calendar = new CalendarPickerController(selectedDate, color);
       // tdCalendar.getChildren().add(calendar);
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectedDate = new SimpleObjectProperty<>(Calendar.getInstance());
        selectedDate.addListener(new ChangeListener<Calendar>() {

            @Override
            public void changed(ObservableValue<? extends Calendar> ov, Calendar t, Calendar t1) {
                startDate.setText(df.format(t1.getTime()));
                System.out.print("selected calendarday Changed");
            }
            
        });
       calendar = new CalendarPickerController(selectedDate, color);
       
        //Scale scaleTransform = new Scale(.5, .5, 0, 0);
       // calendar.getTransforms().add(scaleTransform);
       tdCalendar.getChildren().add(calendar);
    }
    
    private void showStartDateCalendar(){
        tdCalendar.setVisible(!tdCalendar.isVisible());
    }
}
