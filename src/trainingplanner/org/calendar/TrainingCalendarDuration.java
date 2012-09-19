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

    @Override
    public int getSign() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Number getField(Field field) {
            /*YEARS,MONTHS,DAYS,HOURS, MINUTES, or SECONDS */
        if (field.equals(DatatypeConstants.YEARS)) 
            return getYears();
        if (field.equals(DatatypeConstants.MONTHS)) 
            return getMonths();
        if (field.equals(DatatypeConstants.DAYS))
            return getDays();
        if (field.equals(DatatypeConstants.HOURS))
            return getHours();
        if (field.equals(DatatypeConstants.MINUTES))
            return getMinutes();
        if (field.equals(DatatypeConstants.SECONDS))
            return getSeconds();
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
 
    public String ToString(){
        String s = "";
        if(isSet(DatatypeConstants.YEARS)) s.concat(getYears()+":");
        if(isSet(DatatypeConstants.MONTHS)) s.concat(getMonths()+":");
        if(isSet(DatatypeConstants.DAYS)) s.concat(getDays()+":");
        if(isSet(DatatypeConstants.HOURS)) s.concat(getHours()+":");
        if(isSet(DatatypeConstants.MINUTES)) s.concat(":"+getMinutes());
        s.concat(":"+(isSet(DatatypeConstants.SECONDS)?getSeconds():0));
        return s;
    }
}
