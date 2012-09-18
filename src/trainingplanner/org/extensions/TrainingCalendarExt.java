/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner.org.extensions;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import java.util.Calendar;
import java.util.GregorianCalendar;
import trainingplanner.org.xsd.DayType;
import trainingplanner.org.xsd.ICalendarType;
import trainingplanner.org.xsd.MonthType.MonthName;
import trainingplanner.org.xsd.MonthType.WeekType;

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

    public DayType getTrainingDay(GregorianCalendar gCal) {
        int year = gCal.get(Calendar.YEAR);
        int month = gCal.get(Calendar.MONTH);
        int weekOfMonth = gCal.get(Calendar.WEEK_OF_MONTH);
        int dayOfMonth = gCal.get(Calendar.DAY_OF_MONTH);
        
        TrainingDay newTrainingDay = createNewTrainingDay(gCal);
        for( MonthType monthType: getMonthType() ){

            if (monthType.getYear().getYear() == year && 
                    monthType.getMonthName().getMonthId().getMonth() == month){
                for (WeekType weekType:monthType.getWeekType()){
                    if (weekType.getWeekNumber()== weekOfMonth){
                        for(DayType dayType:weekType.getDayType()){
                            if(dayType.getDate().getDay()==dayOfMonth) {return dayType;}
                        }
                        // Did not find the Day in the week
                        weekType.getDayType().add(newTrainingDay);
                        return newTrainingDay;
                    }
                }
                //Did not find the Week in the Month
                WeekType nWeek = createNewWeekType(new XMLGregorianCalendarImpl(gCal));
                nWeek.getDayType().add(newTrainingDay);
                monthType.getWeekType().add(nWeek);
                return newTrainingDay;
            }
        }
        // Did not find the Month 
        MonthType nMonth = createNewMonth(new XMLGregorianCalendarImpl(gCal));
        WeekType nWeek = createNewWeekType(new XMLGregorianCalendarImpl(gCal));
        nWeek.getDayType().add(newTrainingDay);
        nMonth.getWeekType().add(nWeek);
        this.monthType.add(nMonth);
        return newTrainingDay;
    }
    
    private TrainingDay createNewTrainingDay( GregorianCalendar gCal){
        return new TrainingDay(gCal);               
    }
    
    private MonthType createNewMonth(XMLGregorianCalendarImpl gCal){
        MonthType nMonth = new MonthType();
        MonthName mname = new MonthName();
        mname.setMonthId(gCal);
        nMonth.setYear(gCal);
        mname.setValue(gCal);
        nMonth.setMonthName(mname);
        return nMonth;
    }
    
    private WeekType createNewWeekType(XMLGregorianCalendarImpl gCal){
        WeekType defaultWeek = new WeekType();
        defaultWeek.setTrainingPlanWeek(gCal.toGregorianCalendar().getWeeksInWeekYear());
        defaultWeek.setWeekNumber(gCal.toGregorianCalendar().get(Calendar.WEEK_OF_MONTH));
        return defaultWeek;
    }
}
