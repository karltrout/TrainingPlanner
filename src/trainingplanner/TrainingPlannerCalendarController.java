/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

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

    private Calendar[] setCalendar() {
        String dateFormatString = "%1$tb %1$te, %1$tY";
        int dayObjectsCnt = 0; 
        Calendar[] dayObjects = new Calendar[42];    
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        System.out.println(String.format(dateFormatString, calendar));
        
        int firstDay = calendar.get(Calendar.DAY_OF_WEEK);
        if(firstDay>1){
            //set last months days
            for (int lmd = firstDay-1; lmd>0; lmd --){
                Calendar lmdCal = Calendar.getInstance();
                lmdCal.setTime(calendar.getTime());
                System.out.println("Before:" +String.format(dateFormatString, lmdCal));
                int neglmd = 1-lmd;
                lmdCal.set(Calendar.DAY_OF_MONTH,neglmd);
                
                System.out.println("After:"+String.format(dateFormatString, lmdCal));
                dayObjects[dayObjectsCnt]=lmdCal;
                dayObjectsCnt++;
            }
        }
        //setall these months days
        int totalDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int dayCounter = 0; dayCounter<totalDays; dayCounter++){
            Calendar thisDay = Calendar.getInstance();
            thisDay.setTime(calendar.getTime());
            thisDay.add(Calendar.DAY_OF_MONTH, dayCounter);
            dayObjects[dayObjectsCnt]=thisDay;
            dayObjectsCnt++;
        }
       //setRemeaingDays from next month
        int nextMonthDays = 0;
        while(dayObjectsCnt<42){
            Calendar thisDay = Calendar.getInstance();
            thisDay.setTime(calendar.getTime());
            thisDay.add(Calendar.MONTH,1);
            thisDay.add(Calendar.DAY_OF_MONTH, nextMonthDays);
            dayObjects[dayObjectsCnt]=thisDay;
            nextMonthDays++;
            dayObjectsCnt++;  
        }
        return dayObjects;
    }

    private void updateCalendar() {
        //calendar.setTime(new Date());
        monthName.setText(String.format("%1$tY %1$tB", calendar));
        Calendar[] calendarDays = setCalendar();
        
        
        for(Object calObj : calendarGridPane.getChildren()){
            if (calObj instanceof Group){
                Group group = (Group) calObj;
                ObservableMap<Object, Object> properties = group.getProperties();
                int column = (Integer)properties.get("gridpane-column");
                int row = (Integer)properties.get("gridpane-row");
                int calnumber = ((row-1)*7)+column;
                Calendar curCal = calendarDays[calnumber];
                
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
                }
            }
        }
    }
}
