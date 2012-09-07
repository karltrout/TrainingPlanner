/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner.controls;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author troutk
 */
public class PaceClockController extends AnchorPane implements Initializable {

    @FXML private Group numbers;
    private ObservableList<Node> kids;
    private int number = 0000;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setNumbers(number);
    }
    
    public PaceClockController(int value){
        this.number = value;
        URL location = getClass().getResource("PaceClock.fxml");
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

    private void setNumbers(int value) {
        
        if (value > 9999){
            value = 9999;
        }
        
        char[] ints = ("0000").toCharArray();
        
        char[] _ints = String.valueOf(value).toCharArray();
        for(int i=_ints.length;i > 0; i--){
            ints[4-i] = _ints[_ints.length-i];
        }
        
        kids = numbers.getChildren();
        kids.clear();
        DigitNumberController d1 = new DigitNumberController(Integer.valueOf(String.valueOf(ints[0])));
        d1.setScaleX(0.5);
        d1.setScaleY(0.5);
        kids.add(d1);
        
        DigitNumberController d2 = new DigitNumberController(Integer.valueOf(String.valueOf(ints[1])));
        d2.setScaleX(0.5);
        d2.setScaleY(0.5);
        d2.setLayoutX(30.0);
        kids.add(d2);
        
        DigitNumberController d3 = new DigitNumberController(Integer.valueOf(String.valueOf(ints[2])));
        d3.setScaleX(0.5);
        d3.setScaleY(0.5);
        d3.setLayoutX(65.0);
        kids.add(d3);
        
        DigitNumberController d4 = new DigitNumberController(Integer.valueOf(String.valueOf(ints[3])));
        d4.setScaleX(0.5);
        d4.setScaleY(0.5);
        d4.setLayoutX(95.0);
        kids.add(d4);
    }
}
