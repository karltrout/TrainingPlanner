/**
 * 
 */
package trainingplanner.org.extensions;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import trainingplanner.org.xsd.Athlete;
import trainingplanner.org.xsd.DistanceMeasures;
import trainingplanner.org.xsd.IKPIType.Measurement;
import trainingplanner.org.xsd.KpiValueType;
import trainingplanner.org.xsd.MeasureTypes;
import trainingplanner.org.xsd.SportTypes;
import trainingplanner.org.xsd.TimeMeasures;

/**
 * @author troutk
 *
 */
public class AthleteExt extends Athlete {
	/**
	 * 
	 */
	public AthleteExt() {
        keyPerformanceIndicators = new KeyPerformanceIndicators();
        KPI bikeGoal = new KPI();
        bikeGoal.setSportsType(SportTypes.BIKE);
        Measurement speed = new Measurement();
        speed.setMeasureType(MeasureTypes.SPEED);
        speed.setTimeMeasure(TimeMeasures.HOUR);
        speed.setDistanceMeasure(DistanceMeasures.MILE);
        
        bikeGoal.setMeasurement(speed);
        KpiValueType currentValue = new KpiValueType();
        currentValue.setValue(BigDecimal.valueOf((21.1*1609.34*3600)));
        currentValue.setValueDate(new XMLGregorianCalendarImpl());
        currentValue.setWorkoutId("000000000");
        
        bikeGoal.setCurrentValue(currentValue);
        
        KpiValueType goalValue = new KpiValueType();
        goalValue.setValue(BigDecimal.valueOf((23.1*1609.34*3600)));
        XMLGregorianCalendarImpl goaldate = new XMLGregorianCalendarImpl();
        goaldate.setYear(2013);
        goalValue.setValueDate(goaldate);
        goalValue.setWorkoutId("000000001");
        
        bikeGoal.setGoalValue(goalValue);
        
        KPI swimGoal = new KPI();
        swimGoal.setSportsType(SportTypes.SWIM);
        speed = new Measurement();
        speed.setTimeMeasure(TimeMeasures.MINUTE);
        speed.setMeasureType(MeasureTypes.PACE);
        speed.setDistanceMeasure(DistanceMeasures.YARD);
        
        swimGoal.setMeasurement(speed);
        currentValue = new KpiValueType();
        currentValue.setValue(BigDecimal.valueOf(95));
        currentValue.setValueDate(new XMLGregorianCalendarImpl());
        currentValue.setWorkoutId("000000002");
        
        swimGoal.setCurrentValue(currentValue);
        
        goalValue = new KpiValueType();
        goalValue.setValue(BigDecimal.valueOf(75));
        goaldate = new XMLGregorianCalendarImpl();
        goaldate.setYear(2013);
        goalValue.setValueDate(goaldate);
        goalValue.setWorkoutId("000000003");
        
        swimGoal.setGoalValue(goalValue);
        
        
        keyPerformanceIndicators.getKPI().add(swimGoal);
        keyPerformanceIndicators.getKPI().add(bikeGoal);
        //setKeyPerformanceIndicators(keyPerformanceIndicators);
        
	}

    AthleteExt(Athlete athlete) {
        super.age = athlete.getAge();
        super.dateOfBirth = athlete.getDateOfBirth();
        super.firstName = athlete.getFirstName();
        super.id = athlete.getId();
        super.keyPerformanceIndicators = athlete.getKeyPerformanceIndicators();
        super.lastName = athlete.getLastName();
        super.weight = athlete.getWeight();
    }
	
	public String getFullName(){
		return this.getFirstName()+" "+this.getLastName();
	}

	public void setDateOfBirth(GregorianCalendar birthDay) throws DatatypeConfigurationException{
		DatatypeFactory datatypefactory = DatatypeFactory.newInstance();		
		super.setDateOfBirth(datatypefactory.newXMLGregorianCalendar(birthDay));
	}
	
	public int getAge() {
	    if(dateOfBirth!= null) {
	    	Calendar born = dateOfBirth.toGregorianCalendar();
	    	Calendar now = Calendar.getInstance();
	        now.setTime(new Date()); 
	        
	        if(born.after(now)) {
	            throw new IllegalArgumentException("Can't be born in the future");
	        }
	        age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);             
	        if(now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR))  {
	            age-=1;
	        }
	    }  
	    return age;
	}
        
}
