/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner.org.extensions;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import trainingplanner.org.calendar.TrainingCalendarDuration;
import trainingplanner.org.xsd.IWorkoutType;
import trainingplanner.org.xsd.SportTypes;

/**
 *
 * @author Karl
 */
public class WorkoutExt extends IWorkoutType {
    private SimpleObjectProperty sportsTypeProperty;
    public WorkoutExt(){
        super();
        this.sportType = sportType.OTHER;
        this.description = "New Workout.";
        this.duration = new TrainingCalendarDuration();
        this.excersize = FXCollections.observableArrayList();
        this.intensity = 0.0;
        this.volume = 0;
        sportsTypeProperty = new SimpleObjectProperty(this.sportType);
    }
    
    @Override
    public SportTypes getSportType(){
        return (SportTypes)sportsTypeProperty.get();
    }
    
    @Override
    public void setSportType(SportTypes value) {
        sportsTypeProperty.set(value);
    }
    
    public ObjectProperty getSportsTypeProperty(){
        return sportsTypeProperty;
    }
    
    public String ToString(){
        return sportType.name();
    }  
}
