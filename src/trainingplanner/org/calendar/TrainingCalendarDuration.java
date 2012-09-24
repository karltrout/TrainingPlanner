/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner.org.calendar;

import java.math.BigDecimal;
import java.util.Calendar;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeConstants.Field;
import javax.xml.datatype.Duration;

/**
 *
 * @author troutk
 */
public class TrainingCalendarDuration extends Duration {

    int years = 0;
    int months = 0;
    int days = 0;
    int hours = 0;
    int minutes = 0;
    int seconds = 0;
    
    @Override
    public int getSign() {
        return 1;
    }

    @Override
    public Number getField(Field field) {
            /*YEARS,MONTHS,DAYS,HOURS, MINUTES, or SECONDS */
        if (field.equals(DatatypeConstants.YEARS)) 
            return years;
        if (field.equals(DatatypeConstants.MONTHS)) 
            return months;
        if (field.equals(DatatypeConstants.DAYS))
            return days;
        if (field.equals(DatatypeConstants.HOURS))
            return hours;
        if (field.equals(DatatypeConstants.MINUTES))
            return minutes;
        if (field.equals(DatatypeConstants.SECONDS))
            return seconds;
        else return null;
    }

    @Override
    public boolean isSet(Field field) {
        if (getField(field) == null) return false;
        else return true;
    }

    @Override
    public Duration add(Duration drtn) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addTo(Calendar clndr) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Duration multiply(BigDecimal bd) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Duration negate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Duration normalizeWith(Calendar clndr) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int compare(Duration drtn) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
 
    /*public String ToString(){
        String s = "";
        if(isSet(DatatypeConstants.YEARS)) s.concat(getYears()+"y:");
        if(isSet(DatatypeConstants.MONTHS)) s.concat(getMonths()+"mo:");
        if(isSet(DatatypeConstants.DAYS)) s.concat(getDays()+"d:");
        if(isSet(DatatypeConstants.HOURS)) s.concat(getHours()+"hh:");
        if(isSet(DatatypeConstants.MINUTES)) s.concat(getMinutes()+"mm:+");
        s.concat(":"+(isSet(DatatypeConstants.SECONDS)?getSeconds():0)+"ss");
        return s;
    }*/
}
