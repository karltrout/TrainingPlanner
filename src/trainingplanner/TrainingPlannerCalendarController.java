/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner;

import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.effect.Glow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import trainingplanner.org.calendar.TrainingCalendarDay;

/**
 * FXML Controller class
 *
 * @author troutk
 */
public class TrainingPlannerCalendarController implements Initializable {
        @FXML private GridPane calendarGridPane;
        @FXML Text monthName;
        private GregorianCalendar calendar;
        
        @FXML private Region firstDay;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        calendar = new GregorianCalendar();
        updateCalendar();

    }  
    @FXML private void nextMonth(){
        calendar.add(Calendar.MONTH, 1);
        updateCalendar();
    } 
    @FXML private void prevMonth(){
        calendar.add(Calendar.MONTH, -1);
        updateCalendar();
    } 

    private TrainingCalendarDay[] setCalendar() {
        //String dateFormatString = "%1$tb %1$te, %1$tY";
        int dayObjectsCnt = 0; 
        TrainingCalendarDay[] dayObjects = new TrainingCalendarDay[42];    
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        
        int calFirstDay = calendar.get(Calendar.DAY_OF_WEEK);
        if(calFirstDay>1){
            //set last months days
            for (int lmd = calFirstDay-1; lmd>0; lmd --){
                TrainingCalendarDay calendarDay = new TrainingCalendarDay();
                Calendar lmdCal = Calendar.getInstance();
                lmdCal.setTime(calendar.getTime());
                int neglmd = 1-lmd;
                lmdCal.set(Calendar.DAY_OF_MONTH,neglmd);
                calendarDay.setCalendar(lmdCal);
                dayObjects[dayObjectsCnt]=calendarDay;
                dayObjectsCnt++;
            }
        }
        //setall these months days
        int totalDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int dayCounter = 0; dayCounter<totalDays; dayCounter++){
            TrainingCalendarDay calendarDay = new TrainingCalendarDay();
                Calendar thisDay = Calendar.getInstance();
            thisDay.setTime(calendar.getTime());
            thisDay.add(Calendar.DAY_OF_MONTH, dayCounter);
            calendarDay.setCalendar(thisDay);
            dayObjects[dayObjectsCnt]=calendarDay;
            dayObjectsCnt++;
        }
       //setRemeaingDays from next month
        int nextMonthDays = 0;
        while(dayObjectsCnt<42){
            TrainingCalendarDay calendarDay = new TrainingCalendarDay();
            Calendar thisDay = Calendar.getInstance();
            thisDay.setTime(calendar.getTime());
            thisDay.add(Calendar.MONTH,1);
            thisDay.add(Calendar.DAY_OF_MONTH, nextMonthDays);
            calendarDay.setCalendar(thisDay);
            dayObjects[dayObjectsCnt]=calendarDay;
            nextMonthDays++;
            dayObjectsCnt++;  
        }
        return dayObjects;
    }

    private void updateCalendar() {
        //calendar.setTime(new Date());
        monthName.setText(String.format("%1$tY %1$tB", calendar));
        TrainingCalendarDay[] calendarDays = setCalendar();

        for(Object calObj : calendarGridPane.getChildren()){
            if (calObj instanceof Group){
                Group group = (Group) calObj;
                ObservableMap<Object, Object> properties = group.getProperties();
                int column = (Integer)properties.get("gridpane-column");
                int row = (Integer)properties.get("gridpane-row");
                int calNumber = ((row-1)*7)+column;
                TrainingCalendarDay trainingDay = calendarDays[calNumber];
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
                int calNumber = ((row-1)*7)+column;
                TrainingCalendarDay trainingDay = calendarDays[calNumber];
                Calendar curCal = trainingDay.getCalendar();
                //not this month so inactivate the region
                if (curCal.get(Calendar.MONTH) != calendar.get(Calendar.MONTH)){
                        region.getStyleClass().clear();
                        region.getStyleClass().add("inActiveWeek");
                    }
                //Today has a special Region code
                if (trainingDay.isToday()){
                    region.getStyleClass().clear();
                    region.getStyleClass().add("todayBox");
                    region.setEffect(new Glow(.2));
                }
            }
        }
    }
}
