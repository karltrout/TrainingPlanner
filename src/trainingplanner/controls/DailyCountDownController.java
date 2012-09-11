/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner.controls;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author troutk
 */
public class DailyCountDownController extends AnchorPane implements Initializable {
    @FXML private Group numbers;
    private int number = 000;
    private SimpleObjectProperty<Color> color = new SimpleObjectProperty<>(this, "color", Color.RED);
    private SimpleIntegerProperty numberProperty;
    private ObservableList<Node> kids;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       setNumbers();
    }
    
     public DailyCountDownController(SimpleIntegerProperty _number, SimpleObjectProperty<Color> color){
        if (null == color) { color = new SimpleObjectProperty<>(Color.LIME); }
        this.number = _number.getValue();
        _number.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                number = t1.intValue();
                setNumbers();
            }
        });
        this.numberProperty = _number;
        this.color = color;
        color.addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> ov, Color t, Color t1) {
            }
        });
        
        URL location = getClass().getResource("DailyCountDown.fxml");
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
     
    private void setNumbers() {
        if (number > 999){number = 999;}       
        char[] ints = ("0000").toCharArray();       
        char[] _ints = String.valueOf(number).toCharArray();
        for(int i=_ints.length;i > 0; i--){
            ints[4-i] = _ints[_ints.length-i];
        }    
        kids = numbers.getChildren();
        kids.clear();
        kids.add(new DigitNumberController(0.45,0.45,7.0,Integer.valueOf(String.valueOf(ints[0])),color));
        kids.add(new DigitNumberController(0.45,0.45,41.0,Integer.valueOf(String.valueOf(ints[1])),color));
        kids.add(new DigitNumberController(0.45,0.45,74.0,Integer.valueOf(String.valueOf(ints[2])),color));
    }
}
