/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner.org.calendar;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import trainingplanner.org.extensions.WorkoutExt;
import trainingplanner.org.xsd.IWorkoutType;
import trainingplanner.org.xsd.WeekType.DayType;

/**
 *
 * @author troutk
 */
public class TrainingCalendarDay extends DayType {
    private Calendar calendar;
    private int week;
    private SimpleStringProperty date = new SimpleStringProperty();
    //private TrainingDay trainingDay;
    private ObservableList<PieChart.Data> workoutLoadChartData = FXCollections.observableArrayList(); 
    private ObservableList<WorkoutExt> workouts = FXCollections.observableArrayList();
    
    public TrainingCalendarDay(){
        this.setCalendar(Calendar.getInstance());
    }
    
    public TrainingCalendarDay(GregorianCalendar day){
        super.setDate(new XMLGregorianCalendarImpl(day));
        calendar = day;
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
    public final void setCalendar(Calendar calendar) {
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
    
    public StringProperty getDateProperty(){
        return date;
    }

    /**
     * @return the trainingDay
     */
   /* public DayType getTrainingDay() {
        if( trainingDay == null ){
            GregorianCalendar gCal = new GregorianCalendar();
            gCal.setTimeInMillis(calendar.getTimeInMillis());
            trainingDay = new TrainingDay(gCal);
        }
        return trainingDay;
    }*/

    /**
     * @param trainingDay the trainingDay to set
     */
    /*public void setTrainingDay(TrainingDay trainingDay) {
        this.trainingDay = trainingDay;
    }*/

    public void addWorkout(WorkoutExt wo){ 
        workouts.add(wo);
        this.workoutType.add(wo);
        //trainingDay.getWorkoutType().add(wo);
        workoutLoadChartData.add(new PieChart.Data(wo.getSportType().name(), wo.getIntensity()));
    }
    
    public ObservableList<PieChart.Data> getWorkoutLoadChartData(){
        return workoutLoadChartData;
    }
    public void refreshLoadChartData(){
        workoutLoadChartData.clear();
        for(IWorkoutType workout :getWorkoutType()){
            workoutLoadChartData.add(new PieChart.Data(workout.getSportType().name(), workout.getIntensity()));
        }
    }

    public void setDayType(DayType dayType) {
        setCalendar(dayType.getDate().toGregorianCalendar());
        this.details = dayType.getDetails();
        this.id = dayType.getId();
        this.parentId = dayType.getParentId();
        for(IWorkoutType wo : dayType.getWorkoutType()){
            addWorkout(new WorkoutExt(wo));
        }
        refreshLoadChartData();
    }

}
