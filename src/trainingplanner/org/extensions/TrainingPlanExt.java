/**
 * 
 */
package trainingplanner.org.extensions;

import java.util.GregorianCalendar;
import java.util.UUID;
import javax.xml.datatype.DatatypeConfigurationException;

/**
 * @author troutk
 *
 */
public class TrainingPlanExt extends trainingplanner.org.xsd.TrainingPlan {

	/**
	 * 
	 */
	public TrainingPlanExt() {
		// TODO Auto-generated constructor stub
	}

	public void initialize() {
	System.out.println("inintializing training Plan...");
	AthleteExt athlete = new AthleteExt();
		athlete.setId(UUID.randomUUID().toString());
		athlete.setFirstName("Karl");
		athlete.setLastName("Trout");
		try {
			athlete.setDateOfBirth(new GregorianCalendar(1970, 8, 23));
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		athlete.setWeight(180);
		this.athlete = athlete;
	System.out.println("Athlete is :"+this.athlete.getFirstName());
	}

	@Override
	public AthleteExt getAthlete() {
		return (AthleteExt) this.athlete;
	}

}
