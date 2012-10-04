/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner.org.calendar;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
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
    private SimpleStringProperty dateProperty = new SimpleStringProperty();
    private ObservableList<PieChart.Data> workoutLoadChartData = FXCollections.observableArrayList(); 
    private ObservableList<WorkoutExt> workouts = FXCollections.observableArrayList();
    private DayType _dayType = new DayType();
    private HashMap<PieChart.Data, WorkoutExt> dataMap = new HashMap();
    
    public TrainingCalendarDay(){
        this.setCalendar(Calendar.getInstance());
    }
    
    public TrainingCalendarDay(DayType dayType){
        _dayType    = dayType;
        setCalendar(dayType.getDate().toGregorianCalendar());
        details     = dayType.getDetails();
        id          = dayType.getId();
        parentId    = dayType.getParentId();
        for(IWorkoutType wo : dayType.getWorkoutType()){
            workouts.add(new WorkoutExt(wo, dayType.getDate().toGregorianCalendar()));
        }
        this.date = dayType.getDate();
        refreshLoadChartData();
    }

    public TrainingCalendarDay(GregorianCalendar day){
        super.setDate(new XMLGregorianCalendarImpl(day));
        calendar = day;
    }

    public Calendar getCalendar() {
        return calendar;
    }
    public final void setCalendar(Calendar calendar) {
        this.calendar = calendar;
        dateProperty.set(calendar.getTime().toString());
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public boolean isToday() {
        Calendar today = Calendar.getInstance();
        int dayOfYear = this.calendar.get(Calendar.DAY_OF_YEAR);
        int currentYear = this.calendar.get(Calendar.YEAR);        
        return (today.get(Calendar.YEAR)==currentYear && today.get(Calendar.DAY_OF_YEAR)==dayOfYear);
    }
    
    public StringProperty getDateProperty(){
        return dateProperty;
    }

    public final void addWorkout(WorkoutExt wo){ 
        workouts.add(wo);
        this._dayType.getWorkoutType().add(wo);
        workoutLoadChartData.add(new PieChart.Data(wo.getSportType().name(), wo.getIntensity()));
    }
    
    public final void removeWorkout(WorkoutExt wo){
        workouts.remove(wo);
        if (_dayType.getWorkoutType().contains(wo.getWorkOutType())){
        _dayType.getWorkoutType().remove(wo.getWorkOutType());}
        
        refreshLoadChartData();
    }
    
    public ObservableList<PieChart.Data> getWorkoutLoadChartData(){
        refreshLoadChartData();
        return workoutLoadChartData;
    }
    
    public final void refreshLoadChartData(){
        workoutLoadChartData.clear();
        dataMap.clear();
        for(WorkoutExt workout :workouts){
            PieChart.Data data = new PieChart.Data(workout.getSportType().value(), workout.getVolume());
            dataMap.put(data, workout);
            workoutLoadChartData.add(data);
        }
    }
    
    public ObservableList<WorkoutExt> getObservableWorkOuts(){
        return workouts;
    } 

    public int getWorkoutCount(){ return workouts.size(); }
    
    
    public void setDayType(DayType dayType) {
        setCalendar(dayType.getDate().toGregorianCalendar());
        this._dayType = dayType;
        this.details = dayType.getDetails();
        this.id = dayType.getId();
        this.parentId = dayType.getParentId();
        for(IWorkoutType wo : dayType.getWorkoutType()){
            workouts.add(new WorkoutExt(wo, dayType.getDate().toGregorianCalendar()));
        }
        refreshLoadChartData();
    }

    public double getTotalVolume() {
        double totalVolume = 0;
        for (WorkoutExt workout: workouts){
            totalVolume = totalVolume + workout.getVolume();
        }
        return totalVolume;
    }
    
    public HashMap<PieChart.Data, WorkoutExt> getDataMap(){
        return dataMap;
    }

}
