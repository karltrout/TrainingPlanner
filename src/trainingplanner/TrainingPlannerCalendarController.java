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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
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
    private GregorianCalendar calendar;
    private SimpleObjectProperty<TrainingCalendarDay> selectedTrainingDay;
    ObservableList<TrainingCalendarDay> calendarDays;
    private TrainingCalendarExt trainingCalendar;

    @FXML private Region firstDay;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (this.trainingCalendar == null) trainingCalendar = new TrainingCalendarExt();
        calendar = new GregorianCalendar();
        calendarDays = FXCollections.observableArrayList();
    }  
    
    @FXML private void nextMonth(){
        calendar.add(Calendar.MONTH, 1);
        updateCalendar();
    } 
    @FXML private void prevMonth(){
        calendar.add(Calendar.MONTH, -1);
        updateCalendar();
    } 

    public TrainingPlannerCalendarController(SimpleObjectProperty<TrainingCalendarDay> _selectedTrainingDay, TrainingCalendarExt _trainingCalendar){
            
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
        this.selectedTrainingDay = _selectedTrainingDay;
        this.trainingCalendar = _trainingCalendar;
        
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

    private void updateCalendar() {
        monthName.setText(String.format("%1$tY %1$tB", calendar));
        calendarDays.setAll(setCalendar());

        ArrayList<Rectangle> woRectangles = new ArrayList<>();
        ArrayList<Rectangle> removedRectangles = new ArrayList<>();
        for(Object calObj : calendarGridPane.getChildren()){
            
            if (calObj instanceof Rectangle){
                removedRectangles.add((Rectangle)calObj);
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
                //int numberOfWorkouts = trainingDay.getWorkoutCount();
                //double totalWoVolume = trainingDay.getTotalVolume();
                double layoutX = 0;
                for(WorkoutExt wo : trainingDay.getObservableWorkOuts()){
                    double woWidth = 5;//region.getWidth()/numberOfWorkouts;
                    double woHeight = 20;//region.getHeight()*(wo.getVolume()/totalWoVolume);
                    Rectangle woGraphBar = new Rectangle();
                    woGraphBar.setWidth(woWidth);
                    woGraphBar.setHeight(woHeight);
                    woGraphBar.setLayoutX(layoutX);
                    layoutX = layoutX-woWidth;
                    woGraphBar.setFill(Color.LIME);
                    ObservableMap<Object, Object> graphBarProperties = woGraphBar.getProperties();
                    
                    graphBarProperties.put("gridpane-column", column);
                    graphBarProperties.put("gridpane-row", row);
                    graphBarProperties.put("gridpane-halignment", HPos.RIGHT);
                    graphBarProperties.put("gridpane-valignment", VPos.BOTTOM);
                    woRectangles.add(woGraphBar);
                }
                region.setOpacity(.75);
                
                region.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        if(selectedTrainingDay.get() != null && selectedTrainingDay.get().equals(calendarDays.get(calNumber)))
                            selectedTrainingDay.set(new TrainingCalendarDay());
                        
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
}
