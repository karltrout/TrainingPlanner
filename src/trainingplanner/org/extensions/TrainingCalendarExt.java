/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner.org.extensions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import trainingplanner.org.calendar.TrainingCalendarDay;
import trainingplanner.org.xsd.ICalendarType;
import trainingplanner.org.xsd.MonthType.WeekType;
import trainingplanner.org.xsd.WeekType.DayType;

/**
 *
 * @author troutk
 */
public class TrainingCalendarExt extends ICalendarType {
    public TrainingCalendarExt(){
        //GregorianCalendar gCal = (GregorianCalendar) Calendar.getInstance();
        //WeekType defaultWeek = new WeekType();
        //defaultWeek.setTrainingPlanWeek(1);
        //MonthType defaultMonth = new MonthType();
        //MonthName mname = new MonthName();
       // mname.setValue(new XMLGregorianCalendarImpl(gCal));
        //defaultMonth.setMonthName(mname);
        //defaultMonth.setYear(new XMLGregorianCalendarImpl(gCal));
        //defaultMonth.getWeekType().add(defaultWeek);
        //getMonthType().add(defaultMonth);
    }

    public TrainingCalendarExt(ICalendarType cal){
        if (cal == null) 
        {
            cal = new ICalendarType();
        }
        endDate = cal.getEndDate();
        id = cal.getId();
        monthType = cal.getMonthType();
        numberOfDays = cal.getNumberOfDays();
        numberOfMonths = cal.getNumberOfMonths();
        numberOfWeeks = cal.getNumberOfWeeks();
        parentId = cal.getParentId();
        startDate = cal.getEndDate();
    }
    
    public TrainingCalendarDay getTrainingDay(GregorianCalendar gCal) {
        int year = gCal.get(Calendar.YEAR);
        int month = gCal.get(Calendar.MONTH);
        int weekOfMonth = gCal.get(Calendar.WEEK_OF_MONTH);
        int dayOfMonth = gCal.get(Calendar.DAY_OF_MONTH);
        
        TrainingCalendarDay newTrainingDay = new TrainingCalendarDay(gCal);
        for( MonthType mType: getMonthType() ){

            if (mType.getYear() == year && 
                    mType.getMonthId()== month){
                for (WeekType weekType:mType.getWeekType()){
                    if (weekType.getWeekNumber()== weekOfMonth){
                        for(DayType dayType:weekType.getDayType()){
                            
                            if(dayType.getDate().getDay() == dayOfMonth) {
                                //newTrainingDay.setDayType(dayType);
                                //dayType = newTrainingDay;
                                return (TrainingCalendarDay) dayType;
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
}
