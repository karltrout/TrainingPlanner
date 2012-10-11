/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner.org.extensions;

import java.util.GregorianCalendar;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import trainingplanner.org.xsd.ExerciseType;
import trainingplanner.org.xsd.IWorkoutType;
import trainingplanner.org.xsd.SportTypes;

/**
 *
 * @author Karl
 */
public class WorkoutExt extends IWorkoutType {
    private SimpleObjectProperty<SportTypes> sportsTypeProperty;
    private SimpleStringProperty sportsTypeNameProperty;
    private GregorianCalendar workoutDate;
    private IWorkoutType workOutType = new IWorkoutType();
    private ObservableList<ExerciseType> exercises = FXCollections.observableArrayList();
    private Image sportIconImage;
    
        
    public WorkoutExt(GregorianCalendar date){
        sportType = SportTypes.OTHER;
        description = "New Workout";
        duration = 0;
        intensity = 0.0;
        volume = 0;
        workoutDate = date;
        setProperties();
    }

    public WorkoutExt(IWorkoutType wo, GregorianCalendar date) {
        workoutDate = date;
        workOutType = wo;
        description = wo.getDescription();
        duration = wo.getDuration();
        id = wo.getId();
        intensity = wo.getIntensity();
        parentId = wo.getParentId();
        sportType = wo.getSportType();
        volume = wo.getVolume();
        super.getExercise().addAll(wo.getExercise());
        exercises.addAll(wo.getExercise());
        sportIconImage = getSportsTypeIconImage();
        setProperties();
        calulateVDI();
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
        
        super.setSportType(value);
        sportType = value;
        getWorkOutType().setSportType(value);
        //sportsTypeProperty.set(value);
    }
    
    public ObjectProperty<SportTypes> getSportsTypeProperty(){
        return sportsTypeProperty;
    }
    
    public String ToString(){
        return sportType.name();
    }  
    
    public String getSuperSportstype(){
        return super.getSportType().value();
    }

    private void setProperties() {
        sportsTypeNameProperty = new SimpleStringProperty(sportType.value());
        sportsTypeProperty = new SimpleObjectProperty<>(sportType);
        
        sportsTypeProperty.addListener(new ChangeListener<SportTypes>() {

            @Override
            public void changed(ObservableValue<? extends SportTypes> ov, SportTypes t, SportTypes t1) {
                sportsTypeNameProperty.set(t1.value());
                setSportType(t1);
            }
        });
        
    }
    public IWorkoutType getWorkOutType() {
        return workOutType;
    }

    @Override
    public void setIntensity(double value) {
        super.setIntensity(value);
        workOutType.setIntensity(value);
    }

    @Override
    public void setDescription(String value) {
        super.setDescription(value);
        workOutType.setDescription(value);
    }

    @Override
    public void setDuration(int value) {
        super.setDuration(value);
        workOutType.setDuration(value);
    }

    @Override
    public void setVolume(double value) {
        workOutType.setVolume(value);
        super.setVolume(value);
    }

    public void addWorkoutExercise(ExerciseType exercise){
        super.getExercise().add(exercise);
        exercises.add(exercise);
        workOutType.getExercise().add(exercise);
        calulateVDI();
    }
    /**
     * @return the exercises
     */
    public ObservableList<ExerciseType> getObservableExercises() {
        return exercises;
    }

    /**
     * @return the workoutDate
     */
    public GregorianCalendar getWorkoutDate() {
        return workoutDate;
    }

    /**
     * @param workoutDate the workoutDate to set
     */
    public void setWorkoutDate(GregorianCalendar workoutDate) {
        this.workoutDate = workoutDate;
    }

    public final Image getSportsTypeIconImage() {
        switch (this.sportType){
            case STRENGTH:
                return new Image("/trainingplanner/images/icons-weights.gif"); 
            case SWIM:
                return new Image("/trainingplanner/images/icons-swimming.gif"); 
            case BIKE:
                return new Image("/trainingplanner/images/icons-cycling.gif"); 
            case DAY_OFF:
                return new Image("/trainingplanner/images/icons-other.gif"); 
            case OTHER:
                return new Image("/trainingplanner/images/icons-other.gif"); 
            case RACE:
                return new Image("/trainingplanner/images/icons-other.gif"); 
            case RUN:
                return new Image("/trainingplanner/images/icons-running.gif"); 
            default:
                return new Image("/trainingplanner/images/icons-other.gif");
        }
    }
    public void calulateVDI() {
        volume = 0;
        intensity = 0;
        duration = 0;
        for (ExerciseType e:exercise){
            volume = volume + e.getVolume();
            intensity = intensity + e.getIntensity();
            duration = duration + e.getDuration();
        }
        workOutType.setVolume(volume);
        workOutType.setDuration(duration);
        workOutType.setIntensity(intensity);  
    }
   
}
