/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner.org.extensions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import trainingplanner.org.calendar.TrainingCalendarDay;
import trainingplanner.org.xsd.DayType;
import trainingplanner.org.xsd.ICalendarType;
import trainingplanner.org.xsd.MonthType;
import trainingplanner.org.xsd.WeekType;

/**
 *
 * @author troutk
 */
public class TrainingCalendarExt extends ICalendarType {
    //private ICalendarType calType;
    
    private ObservableList<TrainingCalendarDay> allTrainingDays = FXCollections.observableArrayList();
    private ObservableList<WorkoutExt> allTrainingDayWorkouts = FXCollections.observableArrayList();

    public TrainingCalendarExt(){
    }

    public TrainingCalendarExt(ICalendarType cal){
        
        if (cal == null) 
        {
            cal = new ICalendarType();
        }
        //calType = cal;
        endDate = cal.getEndDate();
        id = cal.getId();
        monthType = cal.getMonthType();
        numberOfDays = cal.getNumberOfDays();
        numberOfMonths = cal.getNumberOfMonths();
        numberOfWeeks = cal.getNumberOfWeeks();
        parentId = cal.getParentId();
        startDate = cal.getEndDate();
        //this.getTrainingDay((GregorianCalendar)Calendar.getInstance());
        for( MonthType mType: getMonthType() ){
            for (WeekType weekType:mType.getWeekType()){
                for(DayType dayType:weekType.getDayType()){
                    if(! dayType.getWorkoutType().isEmpty()){
                        TrainingCalendarDay nreDay = new TrainingCalendarDay(dayType, this);
                       allTrainingDays.add(nreDay);
                       allTrainingDayWorkouts.addAll(nreDay.getObservableWorkOuts());
                    }        
                }
            }
        }
        sortAllTrainingDays();
        sortTrainingDayWorkouts();
    }
    
    public TrainingCalendarDay getTrainingDay(GregorianCalendar gCal) {
        int year = gCal.get(Calendar.YEAR);
        int month = gCal.get(Calendar.MONTH);
        int weekOfMonth = gCal.get(Calendar.WEEK_OF_MONTH);
        int dayOfMonth = gCal.get(Calendar.DAY_OF_MONTH);
        
        for( TrainingCalendarDay day : allTrainingDays){
            if (day.getCalendar().equals(gCal)){
                return day;
            }
        }
        
        TrainingCalendarDay newTrainingDay = new TrainingCalendarDay(gCal);
       
        for( MonthType mType: getMonthType() ){

            if (mType.getYear() == year && 
                    mType.getMonthId()== month){
                for (WeekType weekType:mType.getWeekType()){
                    if (weekType.getWeekNumber()== weekOfMonth){
                        for(DayType dayType:weekType.getDayType()){
                            
                            if(dayType.getDate().getDay() == dayOfMonth) {
                                newTrainingDay.setDayType(dayType);
                                return newTrainingDay;
                            }
                        }
                        // Did not find the Day in the week
                        weekType.getDayType().add(newTrainingDay);
                        return newTrainingDay;
                    }
                }
                //Did not find the Week in the Month
                WeekType nWeek = createNewWeekType(gCal);
                nWeek.getDayType().add(newTrainingDay);
                mType.getWeekType().add(nWeek);
                return newTrainingDay;
            }
        }
        // Did not find the Month 
        MonthType nMonth = createNewMonth(gCal);
        WeekType nWeek = createNewWeekType(gCal);
        nWeek.getDayType().add(newTrainingDay);
        nMonth.getWeekType().add(nWeek);
        monthType.add(nMonth);
        //calType.getMonthType().add(nMonth);
        return newTrainingDay;
    }
    
    private MonthType createNewMonth(GregorianCalendar gCal){
        MonthType nMonth = new MonthType();
        nMonth.setMonthId(gCal.get(Calendar.MONTH));
        nMonth.setYear(gCal.get(Calendar.YEAR));
        nMonth.setMonthName(new SimpleDateFormat("MMMM").format(gCal.getTime()));
        return nMonth;
    }
    
    private WeekType createNewWeekType(GregorianCalendar gCal){
        WeekType defaultWeek = new WeekType();
        defaultWeek.setTrainingPlanWeek(gCal.getWeeksInWeekYear());
        defaultWeek.setWeekNumber(gCal.get(Calendar.WEEK_OF_MONTH));
        return defaultWeek;
    }

    public void prune() {
        ArrayList<MonthType> prunableMonths= new ArrayList<>();
        for( MonthType mType: getMonthType() ){
            ArrayList<WeekType> prunableWeeks= new ArrayList<>();
            for (WeekType weekType:mType.getWeekType()){
                ArrayList<DayType> prunableDays = new ArrayList<>();
                for(DayType dayType:weekType.getDayType()){
                    if(dayType.getWorkoutType().isEmpty()) 
                        prunableDays.add(dayType);
                }
                weekType.getDayType().removeAll(prunableDays);
                if (weekType.getDayType().isEmpty())
                    prunableWeeks.add(weekType);
            }
            mType.getWeekType().removeAll(prunableWeeks);
            if (mType.getWeekType().isEmpty()) 
                prunableMonths.add(mType);
        }
        getMonthType().removeAll(prunableMonths);
    }
    
    public ObservableList<TrainingCalendarDay> getAllTrainingDays(){
          return allTrainingDays;
    }
    
    public void addTrainingDay(TrainingCalendarDay day){
        allTrainingDays.add(day);
        sortAllTrainingDays();
    }
    
    public void addWorkoutToTrainingDay(TrainingCalendarDay day, WorkoutExt workout){
        day.addWorkout(workout);
        if (!allTrainingDays.contains(day)) allTrainingDays.add(day);
        if (!allTrainingDayWorkouts.contains(workout)) allTrainingDayWorkouts.add(workout);
        sortTrainingDayWorkouts();
        sortAllTrainingDays();
    }
    
    public void removeWorkoutFromTrainingDay (TrainingCalendarDay day, WorkoutExt workout){
        if(day.getObservableWorkOuts().contains(workout)) day.getObservableWorkOuts().remove(workout);
        if(day.getWorkoutCount() == 0 && allTrainingDays.contains(day) ) allTrainingDays.remove(day); 
        if (allTrainingDayWorkouts.contains(workout)) allTrainingDayWorkouts.remove(workout);
        sortTrainingDayWorkouts();
        sortAllTrainingDays();
    }
    
    public final void sortAllTrainingDays(){
        if (allTrainingDays != null){
        Collections.sort(allTrainingDays, new Comparator<TrainingCalendarDay>() {
            @Override
            public int compare(TrainingCalendarDay t, TrainingCalendarDay t1) {
                if (t.getDate().toGregorianCalendar().after(t1.getDate().toGregorianCalendar())) return 1;
                if (t.getDate().toGregorianCalendar().before(t1.getDate().toGregorianCalendar())) return -1;
                else return 0;
            }
        });
        }
    }

    public ObservableList<WorkoutExt> getAllWorkouts() {
        return allTrainingDayWorkouts;
    }

    private void sortTrainingDayWorkouts() {
        if (allTrainingDayWorkouts != null){
        Collections.sort(allTrainingDayWorkouts, new Comparator<WorkoutExt>() {
            @Override
            public int compare(WorkoutExt t, WorkoutExt t1) {
                if (t.getWorkoutDate().after(t1.getWorkoutDate())) return 1;
                if (t.getWorkoutDate().before(t1.getWorkoutDate())) return -1;
                else return 0;
            }
        });
        }
    } 
}
