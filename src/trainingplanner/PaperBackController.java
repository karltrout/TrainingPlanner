/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import trainingplanner.org.calendar.TrainingCalendarDay;
/**
 * FXML Controller class
 *
 * @author Karl
 */
public class PaperBackController extends AnchorPane implements Initializable {

    
    @FXML Group closeButton;
    @FXML Text trainingDate;
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

    void setTrainingDay(TrainingCalendarDay _trainingCalendarDay) {
        
            trainingDay = _trainingCalendarDay;
            trainingDate.setText(String.format("%1$tb %1$te,%1$tY",trainingDay.getCalendar()));
            
    }
    
     class Delta { double x, y; } 
}
