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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author troutk
 */
public class PaceClockController extends AnchorPane implements Initializable {

    @FXML private Group numbers;
    @FXML private Circle rimColor;
    @FXML private Slider paceSlider;
    @FXML private TextField paceValueText;
    @FXML private Group paceSettings;
    @FXML private Rectangle settingsButton;
    
    private ObservableList<Node> kids;
    private int number = 0000;
    private SimpleObjectProperty<Color> color = new SimpleObjectProperty<>(this, "color", Color.RED);
    private SimpleIntegerProperty numberProperty;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setNumbers();
    }
    
    public PaceClockController(SimpleIntegerProperty value, SimpleObjectProperty<Color> color){
        if (null == color) { color = new SimpleObjectProperty<>(Color.LIME); }
        this.number = value.getValue();
        value.addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                number = t1.intValue();
                setNumbers();
            }
        });
        this.numberProperty = value;
        this.color = color;
        color.addListener(new ChangeListener<Color>() {

            @Override
            public void changed(ObservableValue<? extends Color> ov, Color t, Color t1) {
                rimColor.setStroke(t1);
            }
        });
        
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
        rimColor.setStroke(color.getValue());
        
        settingsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if(paceSettings.visibleProperty().getValue()) 
                    { paceSettings.visibleProperty().set(false); }
                else 
                    { paceSettings.visibleProperty().set(true); }         
            }
        });
        
        paceValueText.setOnInputMethodTextChanged(new EventHandler<InputMethodEvent>() {

            @Override
            public void handle(InputMethodEvent t) {
                System.out.println("text changed.");
                numberProperty.set(Integer.parseInt(paceValueText.getText()));
            }
        });
    }

    private void setNumbers() {
        this.paceValueText.textProperty().setValue(String.valueOf(number));
        if (number > 9999){number = 9999;}       
        char[] ints = ("0000").toCharArray();       
        char[] _ints = String.valueOf(number).toCharArray();
        for(int i=_ints.length;i > 0; i--){
            ints[4-i] = _ints[_ints.length-i];
        }
        
        kids = numbers.getChildren();
        kids.clear();
        kids.add(new DigitNumberController(0.5,0.5,0.0,Integer.valueOf(String.valueOf(ints[0])),color));
        kids.add(new DigitNumberController(0.5,0.5,30.0,Integer.valueOf(String.valueOf(ints[1])),color));
        kids.add(new DigitNumberController(0.5,0.5,65.0,Integer.valueOf(String.valueOf(ints[2])),color));
        kids.add(new DigitNumberController(0.5,0.5,95.0,Integer.valueOf(String.valueOf(ints[3])),color));
        
    }

    /*private DigitNumberController creatDigitalNumber(double scaleX, double scaleY,double translateX, int numberValue) {
        DigitNumberController dc = new DigitNumberController(numberValue,color);
        dc.setScaleX(scaleX);
        dc.setScaleY(scaleY);
        dc.setLayoutX(translateX);
        dc.setBlendMode(BlendMode.ADD);
        return dc;
    }*/
    
    @FXML private void settingsDisplay(){
        if(this.paceSettings.visibleProperty().getValue()) 
            { this.paceSettings.visibleProperty().set(false); }
        else 
            { this.paceSettings.visibleProperty().set(true); }      
    }
}
