/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner.org.extensions;

import javafx.collections.FXCollections;
import trainingplanner.org.xsd.IWorkoutType;

/**
 *
 * @author Karl
 */
public class WorkoutExt extends IWorkoutType {
    
    public WorkoutExt(){
        super();
        this.sportType = sportType.OTHER;
        this.description = "New Workout.";
        this.duration = null;
        this.excersize = FXCollections.observableArrayList();
        this.intensity = 0.0;
        this.volume = 0;
    }
    
    public String ToString(){
        return sportType.name();
    }
    
}
