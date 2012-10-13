/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner.org.extensions;

import javafx.beans.property.SimpleStringProperty;
import trainingplanner.org.xsd.ExerciseType;
import trainingplanner.org.xsd.WeightLiftingExerciseDataBase;
import trainingplanner.org.xsd.WeightLiftingExerciseDataBase.Exercise;

/**
 *
 * @author troutk
 */
public class ExerciseProperty  extends Exercise{
    private SimpleStringProperty nameProperty = new SimpleStringProperty();
    private Exercise _exercise;
    private ExerciseType exerciseType;
         
         /**
     *
     * @param e
     */
    public ExerciseProperty(Exercise e){
             _exercise = e;
             setSuperFields(e);
             nameProperty.set(_exercise.getName());
         }

         @Override
         public String getName(){
             return nameProperty.get();
         }
         
         @Override
         public void setName(String name){
             super.setName(name);
             this.nameProperty.set(name);
         }
        /**
         * @return the nameProperty
         */
        public SimpleStringProperty getNameProperty() {
            return nameProperty;
        }

        /**
         * @param nameProperty the nameProperty to set
         */
        public void setNameProperty(SimpleStringProperty nameProperty) {
            this.nameProperty = nameProperty;
        }

        private void setSuperFields(WeightLiftingExerciseDataBase.Exercise e) {
            super.setName(e.getName());
            super.setDescription(e.getDescription());
            super.setDuration(e.getDuration());
            super.setFocus(e.getFocus());
            super.setId(e.getId());
            super.setIntensity(e.getIntensity());
            super.setParentId(e.getParentId());
            super.setSportType(e.getSportType());
            super.setVolume(e.getVolume());
            super.setWeightLiftingExtension(e.getWeightLiftingExtension());
        }

        public void calulateVDI() {
            super.volume = weightLiftingExtension.getRepitions()*weightLiftingExtension.getSets();
            if (weightLiftingExtension.getOneRepMax() > 0){
                super.intensity = (weightLiftingExtension.getWeight()/weightLiftingExtension.getOneRepMax())*weightLiftingExtension.getRepitions()*weightLiftingExtension.getSets();
            }
            else super.intensity = weightLiftingExtension.getRepitions()*weightLiftingExtension.getSets();
            super.duration = (int) ((2*weightLiftingExtension.getRepitions())*weightLiftingExtension.getSets()+
                           (weightLiftingExtension.getRestInterval()*weightLiftingExtension.getSets()-1));
           
            _exercise.setVolume(super.getVolume());
            _exercise.setDuration(super.getDuration());
            _exercise.setIntensity(super.getIntensity());
        }
        
        public ExerciseType getExerciseType(){
            exerciseType = new ExerciseType();
            exerciseType.setDescription(getExercise().getDescription());
            exerciseType.setDuration(duration);
            exerciseType.setFocus(focus);
            exerciseType.setId(id);
            exerciseType.setIntensity(intensity);
            exerciseType.setName(name);
            exerciseType.setSportType(sportType);
            exerciseType.setVolume(volume);
            exerciseType.getExtensions().add(getExercise().getWeightLiftingExtension());
            return exerciseType;
        }

    /**
     * @return the _exercise
     */
    public Exercise getExercise() {
        return _exercise;
    }

    /**
     * @param exercise the _exercise to set
     */
    public void setExercise(WeightLiftingExerciseDataBase.Exercise exercise) {
        this._exercise = exercise;
    }
         
     }
