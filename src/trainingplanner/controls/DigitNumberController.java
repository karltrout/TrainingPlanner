/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner.controls;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Group;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 *
 * @author troutk
 */
public class DigitNumberController extends AnchorPane implements Initializable{
    /* digital number leds, top = 1, rigth-top =2, right-bottom = 3, bottom = 4, left-bottom = 5, left-top = 6, center = 7 */ 
    @FXML private Polygon p1;
    @FXML private Polygon p2;
    @FXML private Polygon p3;
    @FXML private Polygon p4;
    @FXML private Polygon p5;
    @FXML private Polygon p6;
    @FXML private Polygon p7;
    @FXML private Group ledGroup;
    
    //private int number = 0;
    private String off = "polygonOff" ;
    //private String on = "polygonOn";
    private Color ledColorOn = Color.LIME;
    private Color ledColorOff = Color.web("#202020",1.0);
    private int number;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
 
        public DigitNumberController(double scaleX, double scaleY,double translateX, int value, SimpleObjectProperty<Color> color){
        loadFXML();
        setScaleX(scaleX);
        setScaleY(scaleY);
        setLayoutX(translateX);
        setBlendMode(BlendMode.ADD);
        
        setLedColor(color);
        number = value;
        setNumberLeds();
    }
    
    public DigitNumberController(int value, SimpleObjectProperty<Color> color){
        loadFXML();
        setLedColor(color);
        number = value;
        setNumberLeds();
    }
    
    private void setNumberLeds(){
        switch (getNumber()){
            case 0:
                clearCss();
                p1.setFill(ledColorOn);
                p2.setFill(ledColorOn);
                p3.setFill(ledColorOn);
                p4.setFill(ledColorOn);
                p5.setFill(ledColorOn);
                p6.setFill(ledColorOn);
                p7.setFill(ledColorOff);
                break;
            case 1:
                clearCss();
                p1.setFill(ledColorOff);
                p2.setFill(ledColorOn);
                p3.setFill(ledColorOn);
                p4.setFill(ledColorOff);
                p5.setFill(ledColorOff);
                p6.setFill(ledColorOff);
                p7.setFill(ledColorOff);
                break;
            case 2:
                clearCss();
                p1.setFill(ledColorOn);
                p2.setFill(ledColorOn);
                p3.setFill(ledColorOff);
                p4.setFill(ledColorOn);
                p5.setFill(ledColorOn);
                p6.setFill(ledColorOff);
                p7.setFill(ledColorOn);
                break;
            case 3:
                clearCss();
                p1.setFill(ledColorOn);
                p2.setFill(ledColorOn);
                p3.setFill(ledColorOn);
                p4.setFill(ledColorOn);
                p5.setFill(ledColorOff);
                p6.setFill(ledColorOff);
                p7.setFill(ledColorOn);
                break;
            case 4:
                clearCss();
                p1.setFill(ledColorOff);
                p2.setFill(ledColorOn);
                p3.setFill(ledColorOn);
                p4.setFill(ledColorOff);
                p5.setFill(ledColorOff);
                p6.setFill(ledColorOn);
                p7.setFill(ledColorOn);
                break;
            case 5:
                p1.setFill(ledColorOn);
                p2.setFill(ledColorOff);
                p3.setFill(ledColorOn);
                p4.setFill(ledColorOn);
                p5.setFill(ledColorOff);
                p6.setFill(ledColorOn);
                p7.setFill(ledColorOn);
                break;
            case 6:
                clearCss();
                p1.setFill(ledColorOn);
                p2.setFill(ledColorOff);
                p3.setFill(ledColorOn);
                p4.setFill(ledColorOn);
                p5.setFill(ledColorOn);
                p6.setFill(ledColorOn);
                p7.setFill(ledColorOn);
                break;
            case 7:
                clearCss();
                p1.setFill(ledColorOn);
                p2.setFill(ledColorOn);
                p3.setFill(ledColorOn);
                p4.setFill(ledColorOff);
                p5.setFill(ledColorOff);
                p6.setFill(ledColorOff);
                p7.setFill(ledColorOff);
                break;
            case 8:
                clearCss();
                p1.setFill(ledColorOn);
                p2.setFill(ledColorOn);
                p3.setFill(ledColorOn);
                p4.setFill(ledColorOn);
                p5.setFill(ledColorOn);
                p6.setFill(ledColorOn);
                p7.setFill(ledColorOn);
                break;
            case 9:
                clearCss();
                p1.setFill(ledColorOn);
                p2.setFill(ledColorOn);
                p3.setFill(ledColorOn);
                p4.setFill(ledColorOff);
                p5.setFill(ledColorOff);
                p6.setFill(ledColorOn);
                p7.setFill(ledColorOn);
                break;
            default:
                clearCss();
                break;
        }
    }

    private void clearCss() {
        p1.getStyleClass().clear();
        p2.getStyleClass().clear();
        p3.getStyleClass().clear();
        p4.getStyleClass().clear();
        p5.getStyleClass().clear();
        p6.getStyleClass().clear();
        p7.getStyleClass().clear();
    }

    /**
     * @param ledGroup the ledGroup to set
     */
    private void setLedGroupBlendColor() {
        this.ledGroup.blendModeProperty();
        DropShadow effect = (DropShadow) this.ledGroup.getEffect();
        effect.setColor(ledColorOn.brighter());
    }

    /**
     * @return the ledColor
     */
    public Color getLedColor() {
        return ledColorOn;
    }

    /**
     * @param ledColor the ledColor to set
     */
    private void setLedColor(ObjectProperty<Color> color ) {
        this.ledColorOn = color.getValue();
        color.addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> ov, Color old_val, Color new_val) {
                ledColorOn = new_val;
                setNumberLeds();
                setLedGroupBlendColor();
            }
        });
        setLedGroupBlendColor();
    }

    /**
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(int number) {
        this.number = number;
        setNumberLeds();
    }

    private void loadFXML() throws RuntimeException {
        URL location = getClass().getResource("DigitNumber.fxml");
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
    
}
