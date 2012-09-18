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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
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
    @FXML PieChart calorieChart;
    @FXML PieChart WorkoutLoadChart;
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

    void setTrainingDay(TrainingCalendarDay _trainingCalendarDay) {
        
            trainingDay = _trainingCalendarDay;
            trainingDate.setText(String.format("%1$tb %1$te,%1$tY",trainingDay.getCalendar()));
            
    }
    
     class Delta { double x, y; } 
}
