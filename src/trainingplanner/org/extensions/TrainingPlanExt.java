/**
 * 
 */
package trainingplanner.org.extensions;

import java.util.GregorianCalendar;
import java.util.UUID;
import javax.xml.datatype.DatatypeConfigurationException;
import trainingplanner.org.xsd.TrainingPlan;

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
    if (this.athlete == null)
    {AthleteExt athlete = new AthleteExt();
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
    System.out.println("Athlete is :"+this.athlete.getFirstName());}
    else{  
        AthleteExt a = (AthleteExt) this.athlete;
        System.out.println("Loaded Athelete: "+a.getFullName()); }
    }

    @Override
    public AthleteExt getAthlete() {
            return (AthleteExt) this.athlete;
    }

    public void setRoot(TrainingPlan root) {
        super.setAthlete(new AthleteExt(root.getAthlete()));
        super.setId(root.getId());
        super.setPhaseStrategy(root.getPhaseStrategy());
        super.setTrainingCalendar(root.getTrainingCalendar());
    }
    
    @Override
    public TrainingCalendarExt getTrainingCalendar(){
        if (super.getTrainingCalendar() == null){
            super.setTrainingCalendar(new TrainingCalendarExt());
        }
        return (TrainingCalendarExt) super.getTrainingCalendar();
    }

}
