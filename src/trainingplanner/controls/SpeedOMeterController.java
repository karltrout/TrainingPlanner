/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner.controls;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author troutk
 */
public class SpeedOMeterController extends AnchorPane implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private double speed = 0.0;
    @FXML private double goalSpeed = 0.0;
    @FXML private Text speedText ;
    @FXML private Text goalSpeedText;
    @FXML private Pane   speedIndcator;
    @FXML private Pane   goalIndcator;
    @FXML private double speedRotation = 0;
    @FXML private double goalRotation = 0;
          private int scaleMax = 80;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        goalSpeedText = new Text();
        this.setGoalSpeed(0.0);
        this.setSpeed(0.0);
    }    

    public SpeedOMeterController(){
    URL location = getClass().getResource("SpeedOMeter.fxml");
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
     * @param speedText the speedText to set
     */
    public void setSpeed(double speed) {
        this.speed = speed;
        setSpeedText(String.valueOf(speed));
        setSpeedRotation();
    }

    /**
     * @param goalSpeed the goalSpeed to set
     */
    public void setGoalSpeed(double goalSpeed) {
        this.goalSpeed = goalSpeed;
        setGoalSpeedText(String.valueOf(this.goalSpeed));
        setGoalRotation();
    }

    /**
     * @param speedText the speedText to set
     */
    private void setSpeedText(String speedText) {
        this.speedText.textProperty().set(speedText);;
        try{
        speed = Double.valueOf(speedText);
        }catch (NumberFormatException x){
            speed = 0;
            this.speedText.textProperty().set("0.0");
        }
    }

    /**
     * @param goalSpeedText the goalSpeedText to set
     */
    private void setGoalSpeedText(String goalSpeedText) {
        
        this.goalSpeedText.textProperty().set(goalSpeedText);
    }

    /**
     * @param speedRotation the speedRotation to set
     */
    private void setSpeedRotation() {
        this.speedRotation = calculateRotation(speed);
        this.speedIndcator.setRotate(this.speedRotation);
    }

    /**
     * @param goalRotation the goalRotation to set
     */
    private void setGoalRotation() {
        this.goalRotation = calculateRotation(goalSpeed);
        this.goalIndcator.setRotate(this.goalRotation);
    }
    
    private double calculateRotation(double _speed){
        if (_speed == 0 || scaleMax == 0) {
            return -90;
        }
        else {
            return ((_speed/scaleMax)*180)-90;
        }
    }
    
}
