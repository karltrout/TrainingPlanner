/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner.controls;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author troutk
 */
public class CalendarPickerController  extends AnchorPane implements Initializable {
    public static final String PROP_SELECTEDTRAININGDAY = "PROP_SELECTEDTRAININGDAY";
    @FXML private GridPane calendarGridPane;
    @FXML Text monthName;
    @FXML private Polygon nextMonthButton;
    @FXML private Polygon previousMonthButton;
    @FXML private Region firstDay;
    @FXML private Rectangle calendarBackGround;
    
    private GregorianCalendar calendar;
    private SimpleObjectProperty<Calendar> selectedDay;
    ObservableList<Calendar> calendarDays;
    private Color color = Color.RED;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        calendar = (GregorianCalendar) Calendar.getInstance();
        calendarDays = FXCollections.observableArrayList();
        calendarDays.setAll(setCalendar());
    }  
    
    @FXML private void nextMonth(){
        calendar.add(Calendar.MONTH, 1);
        updateCalendar();
    } 
    @FXML private void prevMonth(){
        calendar.add(Calendar.MONTH, -1);
        updateCalendar();
    } 

    public CalendarPickerController(SimpleObjectProperty<Calendar> _selectedDay,
            SimpleObjectProperty<Color> _color){
        
        URL location = getClass().getResource("CalendarPicker.fxml");
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
        //calendarBackGround.setFill(_color.getValue());
        setColor(_color);
        selectedDay = _selectedDay;
        
        nextMonthButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                nextMonth();
            }
        });
        
        previousMonthButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                prevMonth();
            }
        });
      updateCalendar();  
    }
    
    private Calendar[] setCalendar() {
        int dayObjectsCnt = 0; 
        Calendar[] dayObjects = new Calendar[42];    
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        
        int calFirstDay = calendar.get(Calendar.DAY_OF_WEEK);
        if(calFirstDay>1){
            //set last months days
            for (int lmd = calFirstDay-1; lmd>0; lmd --){
                Calendar lmdCal = Calendar.getInstance();
                lmdCal.setTime(calendar.getTime());
                int neglmd = 1-lmd;
                lmdCal.set(Calendar.DAY_OF_MONTH,neglmd);
                dayObjects[dayObjectsCnt] = (GregorianCalendar)lmdCal;
                dayObjectsCnt++;
            }
        }
        //set all current months days
        int totalDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int dayCounter = 0; dayCounter<totalDays; dayCounter++){
            Calendar thisDay = Calendar.getInstance();
            thisDay.setTime(calendar.getTime());
            thisDay.add(Calendar.DAY_OF_MONTH, dayCounter);
            dayObjects[dayObjectsCnt]=(GregorianCalendar)thisDay;
            dayObjectsCnt++;
        }
       //set Remeaing Days from next month
        int nextMonthDays = 0;
        while(dayObjectsCnt<42){
            Calendar thisDay = Calendar.getInstance();
            thisDay.setTime(calendar.getTime());
            thisDay.add(Calendar.MONTH,1);
            thisDay.add(Calendar.DAY_OF_MONTH, nextMonthDays);
            dayObjects[dayObjectsCnt]=(GregorianCalendar)thisDay;
            nextMonthDays++;
            dayObjectsCnt++;  
        }
        return dayObjects;
    }

    public final void updateCalendar() {
        monthName.setText(String.format("%1$tY %1$tB", calendar));
        calendarDays.setAll(setCalendar());
        ArrayList<FlowPane> woRectangles = new ArrayList<>();
        ArrayList<FlowPane> removedRectangles = new ArrayList<>();
        for(Object calObj : calendarGridPane.getChildren()){
            
            if (calObj instanceof FlowPane){
                removedRectangles.add((FlowPane)calObj);
            }
            
            if (calObj instanceof Group){
                Group group = (Group) calObj;
                ObservableMap<Object, Object> properties = group.getProperties();
                int column = (Integer)properties.get("gridpane-column");
                int row = (Integer)properties.get("gridpane-row");
                int calNumber = ((row-1)*7)+column;
                Calendar curCal = calendarDays.get(calNumber);
                
                for(Object child : group.getChildren()){
                    if (child instanceof Text){
                        Text childText = (Text) child;
                        childText.setText(String.valueOf(curCal.get(Calendar.DAY_OF_MONTH)));
                        childText.getStyleClass().clear();
                        if (curCal.get(Calendar.MONTH) == calendar.get(Calendar.MONTH))
                        {
                            childText.getStyleClass().add("monthDateBoxActiveText");
                        }
                        else
                        {
                            childText.getStyleClass().add("monthDateBoxInactiveText");
                        }
                    }
                    if (child instanceof Rectangle){
                        Rectangle childRectangle = (Rectangle) child;
                        childRectangle.getStyleClass().clear();
                        if (curCal.get(Calendar.MONTH) == calendar.get(Calendar.MONTH))
                        {
                            childRectangle.getStyleClass().add("monthDateBoxActive");
                        }
                        else
                        {
                            childRectangle.getStyleClass().add("monthDateBoxInactive");
                        }
                    }
                }
            }
            
            if (calObj instanceof Region){
                Region region = (Region)calObj;
                region.getStyleClass().clear();
                region.setEffect(null);
                ObservableMap<Object, Object> properties = region.getProperties();
                int column = (Integer)properties.get("gridpane-column");
                int row = (Integer)properties.get("gridpane-row");
                String rowClass = (row % 2 == 0)? "evenWeek":"oddWeek"; 
                region.getStyleClass().add(rowClass);
                final int calNumber = ((row-1)*7)+column;
                Calendar curCal = calendarDays.get(calNumber);
               
                FlowPane flowPane = new FlowPane();
                flowPane.setOrientation(Orientation.HORIZONTAL);
                flowPane.setAlignment(Pos.BOTTOM_RIGHT);
                flowPane.setColumnHalignment(HPos.RIGHT);
                flowPane.setRowValignment(VPos.BOTTOM);
                flowPane.setHgap(1);
                flowPane.setPadding(new Insets(2, 2, 2, 2));
                ObservableMap<Object, Object> graphBarProperties = flowPane.getProperties();
                    graphBarProperties.put("gridpane-column", column);
                    graphBarProperties.put("gridpane-row", row);
                  
                region.setOnMouseClicked(new EventHandler<MouseEvent>() {
                 
                    @Override
                    public void handle(MouseEvent t) {
                        if(selectedDay.get() != null && selectedDay.get().equals(calendarDays.get(calNumber)))
                            selectedDay.set(Calendar.getInstance());
                        selectedDay.set(calendarDays.get(calNumber));
                    }
                });
                //woRectangles.add(flowPane);
                
                //not this month so inactivate the region
                if (curCal.get(Calendar.MONTH) != calendar.get(Calendar.MONTH)){
                        region.getStyleClass().clear();
                        region.getStyleClass().add("inActiveWeek");
                    }
                //Today has a special Region code
                if (isToday(curCal)){
                   if (this.selectedDay == null) {
                       this.selectedDay = new SimpleObjectProperty<>(curCal);
                    } 
                    region.getStyleClass().clear();
                    region.getStyleClass().add("todayBox");
                    region.setEffect(new Glow(.2));
                }
                
            }
        }
        calendarGridPane.getChildren().removeAll(removedRectangles);
        calendarGridPane.getChildren().addAll(woRectangles);
    }

    /**
     * @return the selectedDay
     */
    public SimpleObjectProperty<Calendar> getSelectedTrainingDay() {
        return selectedDay;
    }

    /**
     * @param color the color to set
     */
    private void setColor(SimpleObjectProperty<Color> _color) {
        this.color = _color.getValue();
        _color.addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> ov, Color t, Color t1) {
                color = t1;
                 //calendarBackGround.setFill(color);
                updateCalendar();
            }
        });
    }
    
        private boolean isToday(Calendar dateInQuestion) {
        Calendar today = Calendar.getInstance();
        int dayOfYear = dateInQuestion.get(Calendar.DAY_OF_YEAR);
        int currentYear = dateInQuestion.get(Calendar.YEAR);        
        return (today.get(Calendar.YEAR)==currentYear && today.get(Calendar.DAY_OF_YEAR)==dayOfYear);
    }
}
