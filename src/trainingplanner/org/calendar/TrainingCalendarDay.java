/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner.org.calendar;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import trainingplanner.org.extensions.TrainingDay;
import trainingplanner.org.xsd.DayType;

/**
 *
 * @author troutk
 */
public class TrainingCalendarDay {
    private Calendar calendar;
    private int week;
    private SimpleStringProperty date = new SimpleStringProperty();
    private DayType trainingDay;

    public TrainingCalendarDay(){
        setCalendar(Calendar.getInstance());
    }
    /**
     * @return the calendar
     */
    public Calendar getCalendar() {
        return calendar;
    }

    /**
     * @param calendar the calendar to set
     */
    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
        date.set(calendar.getTime().toString());
    }

    /**
     * @return the week
     */
    public int getWeek() {
        return week;
    }

    /**
     * @param week the week to set
     */
    public void setWeek(int week) {
        this.week = week;
    }

    /**
     * @return the isToday
     */
    public boolean isToday() {
        Calendar today = Calendar.getInstance();
        int dayOfYear = this.calendar.get(Calendar.DAY_OF_YEAR);
        int currentYear = this.calendar.get(Calendar.YEAR);        
        return (today.get(Calendar.YEAR)==currentYear && today.get(Calendar.DAY_OF_YEAR)==dayOfYear);
    }
    
    public StringProperty getDate(){
        return date;
    }

    /**
     * @return the trainingDay
     */
    public DayType getTrainingDay() {
        if( trainingDay == null ){
            GregorianCalendar gCal = new GregorianCalendar();
            gCal.setTimeInMillis(calendar.getTimeInMillis());
            trainingDay = new TrainingDay(gCal);
        }
        return trainingDay;
    }

    /**
     * @param trainingDay the trainingDay to set
     */
    public void setTrainingDay(DayType trainingDay) {
        this.trainingDay = trainingDay;
    }


}
