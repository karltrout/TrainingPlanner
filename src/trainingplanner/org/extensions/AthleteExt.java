/**
 * 
 */
package trainingplanner.org.extensions;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import trainingplanner.org.xsd.Athlete;

/**
 * @author troutk
 *
 */
public class AthleteExt extends Athlete {

	/**
	 * 
	 */
	public AthleteExt() {
		// TODO Auto-generated constructor stub
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
