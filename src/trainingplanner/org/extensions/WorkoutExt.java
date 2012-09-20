/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner.org.extensions;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import trainingplanner.org.calendar.TrainingCalendarDuration;
import trainingplanner.org.xsd.IWorkoutType;

/**
 *
 * @author Karl
 */
public class WorkoutExt extends IWorkoutType {
    public SimpleStringProperty sportsProperty;
    public WorkoutExt(){
        super();
        this.sportType = sportType.OTHER;
        this.description = "New Workout.";
        this.duration = new TrainingCalendarDuration();
        this.excersize = FXCollections.observableArrayList();
        this.intensity = 0.0;
        this.volume = 0;
        sportsProperty = new SimpleStringProperty(this.sportType.name());
    }
    
    public String ToString(){
        return sportType.name();
    }  
}
