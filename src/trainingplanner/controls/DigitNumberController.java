/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner.controls;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
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
    
    //private int number = 0;
    private String off = "polygonOff";
    private String on = "polygonOn";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        p1.setStyle(off);
    }
    
    public DigitNumberController(int value){
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
        setNumber(value);
    }
    
    public void setNumber(int number){
        switch (number){
            case 0:
                clearCss();
                p1.getStyleClass().add(on);
                p2.getStyleClass().add(on);
                p3.getStyleClass().add(on);
                p4.getStyleClass().add(on);
                p5.getStyleClass().add(on);
                p6.getStyleClass().add(on);
                p7.getStyleClass().add(off);
                break;
            case 1:
                clearCss();
                p1.getStyleClass().add(off);
                p2.getStyleClass().add(on);
                p3.getStyleClass().add(on);
                p4.getStyleClass().add(off);
                p5.getStyleClass().add(off);
                p6.getStyleClass().add(off);
                p7.getStyleClass().add(off);
                break;
            case 2:
                clearCss();
                p1.getStyleClass().add(on);
                p2.getStyleClass().add(on);
                p3.getStyleClass().add(off);
                p4.getStyleClass().add(on);
                p5.getStyleClass().add(on);
                p6.getStyleClass().add(off);
                p7.getStyleClass().add(on);
                break;
            case 3:
                clearCss();
                p1.getStyleClass().add(on);
                p2.getStyleClass().add(on);
                p3.getStyleClass().add(on);
                p4.getStyleClass().add(on);
                p5.getStyleClass().add(off);
                p6.getStyleClass().add(off);
                p7.getStyleClass().add(on);
                break;
            case 4:
                clearCss();
                p1.getStyleClass().add(off);
                p2.getStyleClass().add(on);
                p3.getStyleClass().add(on);
                p4.getStyleClass().add(off);
                p5.getStyleClass().add(off);
                p6.getStyleClass().add(on);
                p7.getStyleClass().add(on);
                break;
            case 5:
                clearCss();
                p1.getStyleClass().add(on);
                p2.getStyleClass().add(off);
                p3.getStyleClass().add(on);
                p4.getStyleClass().add(on);
                p5.getStyleClass().add(off);
                p6.getStyleClass().add(on);
                p7.getStyleClass().add(on);
                break;
            case 6:
                clearCss();
                p1.getStyleClass().add(on);
                p2.getStyleClass().add(off);
                p3.getStyleClass().add(on);
                p4.getStyleClass().add(on);
                p5.getStyleClass().add(on);
                p6.getStyleClass().add(on);
                p7.getStyleClass().add(on);
                break;
            case 7:
                clearCss();
                p1.getStyleClass().add(on);
                p2.getStyleClass().add(on);
                p3.getStyleClass().add(on);
                p4.getStyleClass().add(off);
                p5.getStyleClass().add(off);
                p6.getStyleClass().add(off);
                p7.getStyleClass().add(off);
                break;
            case 8:
                clearCss();
                p1.getStyleClass().add(on);
                p2.getStyleClass().add(on);
                p3.getStyleClass().add(on);
                p4.getStyleClass().add(on);
                p5.getStyleClass().add(on);
                p6.getStyleClass().add(on);
                p7.getStyleClass().add(on);
                break;
            case 9:
                clearCss();
                p1.getStyleClass().add(on);
                p2.getStyleClass().add(on);
                p3.getStyleClass().add(on);
                p4.getStyleClass().add(off);
                p5.getStyleClass().add(off);
                p6.getStyleClass().add(on);
                p7.getStyleClass().add(on);
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
    
}
