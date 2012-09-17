/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner.org.extensions;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import java.util.GregorianCalendar;
import trainingplanner.org.xsd.DayType;

/**
 *
 * @author troutk
 */
public class TrainingDay extends DayType {
    TrainingDay(final GregorianCalendar day){
        super.setDate(new XMLGregorianCalendarImpl(day));
    }
    TrainingDay(){
        super();
    }
}
