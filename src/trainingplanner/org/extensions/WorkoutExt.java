/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner.org.extensions;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import trainingplanner.org.calendar.TrainingCalendarDuration;
import trainingplanner.org.xsd.IWorkoutType;
import trainingplanner.org.xsd.SportTypes;

/**
 *
 * @author Karl
 */
public class WorkoutExt extends IWorkoutType {
    private SimpleObjectProperty<SportTypes> sportsTypeProperty;
    private SimpleStringProperty sportsTypeNameProperty;
    public WorkoutExt(){
        super();
        this.sportType = SportTypes.OTHER;
        this.description = "New Workout.";
        this.duration = new TrainingCalendarDuration();
        this.excersize = FXCollections.observableArrayList();
        this.intensity = 0.0;
        this.volume = 0;
        sportsTypeNameProperty = new SimpleStringProperty(this.sportType.toString());
        //sportsTypeNameProperty.bind(this.sportType.value());
        sportsTypeProperty = new SimpleObjectProperty<SportTypes>(this.sportType);
        
        sportsTypeProperty.addListener(new ChangeListener<SportTypes>() {

            @Override
            public void changed(ObservableValue<? extends SportTypes> ov, SportTypes t, SportTypes t1) {
                sportsTypeNameProperty.set(t1.value());
            }
        });
    }
    
    public StringProperty getSportsTypeNameProperty(){
        return this.sportsTypeNameProperty;
    }
    
    @Override
    public SportTypes getSportType(){
        return (SportTypes)sportsTypeProperty.get();
    }
    
    @Override
    public void setSportType(SportTypes value) {
        sportsTypeProperty.set(value);
    }
    
    public ObjectProperty<SportTypes> getSportsTypeProperty(){
        return sportsTypeProperty;
    }
    
    public String ToString(){
        return sportType.name();
    }  
}
