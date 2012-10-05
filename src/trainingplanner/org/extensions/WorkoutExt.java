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
import trainingplanner.org.xsd.ExcersizeType;
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
    private ObservableList<ExcersizeType> excersizes = FXCollections.observableArrayList();
    
        
    public WorkoutExt(GregorianCalendar date){
        sportType = SportTypes.OTHER;
        description = "New Workout";
        duration = 0;//new TrainingCalendarDuration();
        excersize = FXCollections.observableArrayList();
        intensity = 1.0;
        volume = 1;
        workoutDate = date;
        setProperties();
    }

    public WorkoutExt(IWorkoutType wo, GregorianCalendar date) {
        workoutDate = date;
        this.workOutType = wo;
        this.description = wo.getDescription();
        this.duration = wo.getDuration();
        this.excersize = wo.getExcersize();
        this.id = wo.getId();
        this.intensity = wo.getIntensity();
        this.parentId = wo.getParentId();
        this.sportType = wo.getSportType();
        this.volume = wo.getVolume();
        this.excersizes.addAll(wo.getExcersize());
        setProperties();
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
        //sportsTypeNameProperty.bind(this.sportType.value());
        sportsTypeProperty = new SimpleObjectProperty<SportTypes>(sportType);
        
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

    /**
     * @return the excersizes
     */
    public ObservableList<ExcersizeType> getExcersizes() {
        return excersizes;
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
   
}
