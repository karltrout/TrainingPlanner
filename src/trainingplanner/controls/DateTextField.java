/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner.controls;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;

/**
 *
 * @author Karl
 */
public class DateTextField extends TextField {
    private final SimpleDateFormat df;
    private ObjectProperty<Date> date = new SimpleObjectProperty<>();
    
    public final Date getDate(){
        return date.get();
    }
    public final void setDate(Date _date){
        date.set(_date);
    }
    public ObjectProperty<Date> dateProperty(){
        return date;
    }
    public DateTextField(){
        this(new Date());
    }
    public DateTextField(Date _date){
        df = new SimpleDateFormat("MMM/dd/yyyy");
        initHandlers();
        setDate(_date);
    }
 
 private void initHandlers() {

        // try to parse when focus is lost or RETURN is hit
        setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                parseAndFormatInput();
            }

        });

        focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue.booleanValue()) {
                    parseAndFormatInput();
                }
            }
        });

        // Set text in field if BigDecimal property is changed from outside.
        dateProperty().addListener(new ChangeListener<Date>() {

            @Override
            public void changed(ObservableValue<? extends Date> obserable, Date oldValue, Date newValue) {
                setText(df.format(newValue));
            }
        });
    }
private void parseAndFormatInput() {
    try {
        String input = getText();
        if (input == null || input.length() == 0) {
            return;
        }
        
        Date parsedDate = df.parse(input);
        String newValue = df.format(parsedDate); 
        setText(newValue);
        selectAll();
    } catch (ParseException ex) {
      // If parsing fails keep old number
      setText(df.format(date.get()));
    }
}

}
