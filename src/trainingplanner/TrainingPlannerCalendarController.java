/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner;

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
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import trainingplanner.org.calendar.TrainingCalendarDay;
import trainingplanner.org.extensions.TrainingCalendarExt;
import trainingplanner.org.extensions.WorkoutExt;

/**
 * FXML Controller class
 *
 * @author troutk
 */
public class TrainingPlannerCalendarController  extends AnchorPane implements Initializable {
    public static final String PROP_SELECTEDTRAININGDAY = "PROP_SELECTEDTRAININGDAY";
    @FXML private GridPane calendarGridPane;
    @FXML Text monthName;
    @FXML private Polygon nextMonthButton;
    @FXML private Polygon previousMonthButton;
    @FXML private Region firstDay;
    @FXML private Rectangle calendarBackGround;
    
    private GregorianCalendar calendar;
    private SimpleObjectProperty<TrainingCalendarDay> selectedTrainingDay;
    ObservableList<TrainingCalendarDay> calendarDays;
    private TrainingCalendarExt trainingCalendar;
    private Color color = Color.RED;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (this.trainingCalendar == null) 
            trainingCalendar = new TrainingCalendarExt();
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

    public TrainingPlannerCalendarController(SimpleObjectProperty<TrainingCalendarDay> _selectedTrainingDay, TrainingCalendarExt _trainingCalendar,
            SimpleObjectProperty<Color> _color){
        
        URL location = getClass().getResource("FXML/TrainingPlannerCalendar.fxml");
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
        
        selectedTrainingDay = _selectedTrainingDay;
        trainingCalendar = _trainingCalendar;
        
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
    
    private TrainingCalendarDay[] setCalendar() {
        int dayObjectsCnt = 0; 
        TrainingCalendarDay[] dayObjects = new TrainingCalendarDay[42];    
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        
        int calFirstDay = calendar.get(Calendar.DAY_OF_WEEK);
        if(calFirstDay>1){
            //set last months days
            for (int lmd = calFirstDay-1; lmd>0; lmd --){
                Calendar lmdCal = Calendar.getInstance();
                lmdCal.setTime(calendar.getTime());
                int neglmd = 1-lmd;
                lmdCal.set(Calendar.DAY_OF_MONTH,neglmd);
                dayObjects[dayObjectsCnt] = trainingCalendar.getTrainingDay((GregorianCalendar)lmdCal);
                dayObjectsCnt++;
            }
        }
        //set all current months days
        int totalDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int dayCounter = 0; dayCounter<totalDays; dayCounter++){
            Calendar thisDay = Calendar.getInstance();
            thisDay.setTime(calendar.getTime());
            thisDay.add(Calendar.DAY_OF_MONTH, dayCounter);
            dayObjects[dayObjectsCnt]=trainingCalendar.getTrainingDay((GregorianCalendar)thisDay);
            dayObjectsCnt++;
        }
       //set Remeaing Days from next month
        int nextMonthDays = 0;
        while(dayObjectsCnt<42){
            Calendar thisDay = Calendar.getInstance();
            thisDay.setTime(calendar.getTime());
            thisDay.add(Calendar.MONTH,1);
            thisDay.add(Calendar.DAY_OF_MONTH, nextMonthDays);
            dayObjects[dayObjectsCnt]=trainingCalendar.getTrainingDay((GregorianCalendar)thisDay);
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
                //FlowPane flow = (FlowPane) calObj;
                removedRectangles.add((FlowPane)calObj);
            }
            
            if (calObj instanceof Group){
                Group group = (Group) calObj;
                ObservableMap<Object, Object> properties = group.getProperties();
                int column = (Integer)properties.get("gridpane-column");
                int row = (Integer)properties.get("gridpane-row");
                int calNumber = ((row-1)*7)+column;
                TrainingCalendarDay trainingDay = calendarDays.get(calNumber);
                Calendar curCal = trainingDay.getCalendar();
                
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
                TrainingCalendarDay trainingDay = calendarDays.get(calNumber);
                Calendar curCal = trainingDay.getCalendar();
                // add workout graphbar to region area
                int numberOfWorkouts = trainingDay.getWorkoutCount();
                double totalWoVolume = (trainingDay.getTotalVolume()==0)?1.0:trainingDay.getTotalVolume();
                //double layoutX = 0;
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
                  
                for(WorkoutExt wo : trainingDay.getObservableWorkOuts()){
                    double woWidth = 10;//region.getWidth()/numberOfWorkouts;
                    double volume = (wo.getVolume()==0)?1.0:wo.getVolume();
                    double woHeight = 45*(volume/totalWoVolume);
                    Rectangle woGraphBar = new Rectangle();
                    woGraphBar.setWidth(woWidth);
                    woGraphBar.setHeight(woHeight);
                    woGraphBar.setFill(color);
                    woGraphBar.setStroke(color.darker());
                    woGraphBar.setEffect(new Blend(BlendMode.MULTIPLY));
                    woGraphBar.setStrokeWidth(0.5);
                    woGraphBar.setStrokeType(StrokeType.INSIDE);
                    flowPane.getChildren().add(woGraphBar);
                }
                    woRectangles.add(flowPane);
               
                region.setOpacity(.75);
                
                flowPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        if(selectedTrainingDay.get() != null && selectedTrainingDay.get().equals(calendarDays.get(calNumber)))
                            selectedTrainingDay.set(new TrainingCalendarDay(trainingCalendar));
                        
                        selectedTrainingDay.set(calendarDays.get(calNumber));
                    }
                });
                
                //not this month so inactivate the region
                if (curCal.get(Calendar.MONTH) != calendar.get(Calendar.MONTH)){
                        region.getStyleClass().clear();
                        region.getStyleClass().add("inActiveWeek");
                    }
                //Today has a special Region code
                if (trainingDay.isToday()){
                   if (this.selectedTrainingDay == null) {
                       this.selectedTrainingDay = new SimpleObjectProperty<>(trainingDay);
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
     * @return the selectedTrainingDay
     */
    public SimpleObjectProperty<TrainingCalendarDay> getSelectedTrainingDay() {
        return selectedTrainingDay;
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
}
