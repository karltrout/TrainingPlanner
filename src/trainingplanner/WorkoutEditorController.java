/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.util.Callback;
import trainingplanner.org.extensions.ExerciseProperty;
import trainingplanner.org.extensions.WorkoutExt;
import trainingplanner.org.xsd.ExerciseType;
import trainingplanner.org.xsd.WeightLiftingDatabase;
import trainingplanner.org.xsd.WeightLiftingExerciseDataBase.Exercise;
import trainingplanner.org.xsd.WeightLiftingExtension;

/**
 * FXML Controller class
 *
 * @author troutk
 */
public class WorkoutEditorController  extends AnchorPane implements Initializable {

    @FXML private Group closeWorkoutDetailsButton;
    @FXML private ListView muscleGroupExcersizeList;
    @FXML private Group newExcersizeButton;
    @FXML private Group saveExcersizeButton;
    @FXML private Group cancelExcersizeButton;
    @FXML private TextField oneRepMaxField;
    @FXML private TextField restIntervalField;
    @FXML private TextField repitionField;
    @FXML private TextField setsField;
    @FXML private TextField weightField;
    @FXML private TextField ExerciseNameField;
    @FXML private Text intensityField;
    @FXML private Text durationField;
    @FXML private Text volumeField;
                                    
    @FXML private AnchorPane databaseChangedMessage;
    @FXML private Group saveDatabaseButton;
    @FXML private Group cancelSaveDatabaseButton;
    @FXML private Group selectExerciseButton;
    
    private WeightLiftingDatabase _database;
    private ExerciseProperty currentExercise;
    private final ObservableList<ExerciseProperty> obserableExerciseList;
    private boolean workoutDatabaseIsDirty;
    private final ObservableStringValue currentExerciseName= new SimpleStringProperty();;
    private boolean isEditing;
    private WorkoutDetailsHBox currentSelection;
    private WorkoutExt workout;
   
    public WorkoutEditorController(WeightLiftingDatabase database){
        this._database = database;
        
        URL location = getClass().getResource("FXML/WorkoutEditor.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        closeWorkoutDetailsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                closeDialog();
            }
        });
        
        newExcersizeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                createNewExersize();
            }

        });
        
        saveExcersizeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                saveExercise();
                
            }
        });
        
        cancelExcersizeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                closeDialog();
                
            }
        });
        saveDatabaseButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                saveDatabase();
                databaseChangedMessage.setVisible(false);
                hideDialog();
            }
        });
        
        cancelSaveDatabaseButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                databaseChangedMessage.setVisible(false);
            }
        });
        selectExerciseButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                useThisExercise();
            }
        });
        
        obserableExerciseList = FXCollections.observableArrayList();
        
        for (Exercise e : _database.getExercise()){
            
            obserableExerciseList.add(new ExerciseProperty(e));
            
        }
        
        obserableExerciseList.addListener(new ListChangeListener<ExerciseProperty>() {
                @Override
                public void onChanged(Change<? extends ExerciseProperty> change) {
                   workoutDatabaseIsDirty = true;
                }
            });

        muscleGroupExcersizeList.setItems(obserableExerciseList);
        setEditing(false);
    }  
        
    private void closeDialog(){
        if(workoutDatabaseIsDirty){
            databaseChangedMessage.visibleProperty().set(true);
        }
        else{
            hideDialog();
        }
    }
    

    private void createNewExersize() {
        Exercise exercise = new Exercise();       
        exercise.setName("New exercise");
        exercise.setDuration(0);
        exercise.setIntensity(0.0);
        exercise.setVolume(0.0);
        exercise.setWeightLiftingExtension(new WeightLiftingExtension());
        exercise.getWeightLiftingExtension().setExerciseName("New exercise");
        ExerciseProperty ep  = new ExerciseProperty(exercise);
        obserableExerciseList.add(ep);
        muscleGroupExcersizeList.getSelectionModel().selectLast();
        editExercise((ExerciseProperty) muscleGroupExcersizeList.getSelectionModel().getSelectedItem());
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       muscleGroupExcersizeList.setCellFactory(new Callback<ListView<ExerciseProperty>, ListCell<ExerciseProperty>>() {

            @Override
            public ListCell<ExerciseProperty> call(ListView<ExerciseProperty> p) {
                WorkoutDetailsHBox wdBox = new WorkoutDetailsHBox();
                return wdBox;
            }
        });
        /*
        * Sets Draggable for this Component
        */
        final Delta dragDelta = new Delta();
        setOnMousePressed(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            // record a delta distance for the drag and drop operation.
            dragDelta.x = getLayoutX() - mouseEvent.getScreenX();
            dragDelta.y = getLayoutY() - mouseEvent.getScreenY();
          }
        });
        setOnMouseDragged(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
                setLayoutX(mouseEvent.getScreenX() + dragDelta.x);
                setLayoutY(mouseEvent.getScreenY() + dragDelta.y);
          }
        });
    } 

    private void editExercise(ExerciseProperty exercise) {
        if (currentExercise != null) currentExercise.getNameProperty().unbind();
        currentExercise = exercise;//(ExerciseProperty) muscleGroupExcersizeList.getSelectionModel().getSelectedItem(); 
        ExerciseNameField.setText(currentExercise.getName());
        currentExercise.getNameProperty().bind(ExerciseNameField.textProperty());
        setEditing(true);
    }
    
    private void saveExercise(){
        setEditing(false);
        if (currentExercise != null)currentExercise.getNameProperty().unbind();
        currentExercise.getExercise().setName(ExerciseNameField.getText());
        WeightLiftingExtension ext = currentExercise.getWeightLiftingExtension();
        ext.setOneRepMax(Double.parseDouble(oneRepMaxField.getText()));
        ext.setRestInterval(Double.parseDouble(restIntervalField.getText()));
        ext.setRepitions(Integer.parseInt(repitionField.getText()));
        ext.setSets(Integer.parseInt(setsField.getText()));
        ext.setWeight(Double.parseDouble(weightField.getText()));
        ext.setExerciseName(ExerciseNameField.getText());
        currentExercise.calulateVDI();
        if (! _database.getExercise().contains(currentExercise.getExercise()))
        {
            _database.getExercise().add(currentExercise.getExercise());
            this.workoutDatabaseIsDirty = true;
        }
    }
    
    private void saveDatabase(){
        try {
            TrainingPlannerWindowController.saveWeightTrainingDatabase(_database);
        } catch (Exception ex) {
            Logger.getLogger(WorkoutEditorController.class.getName()).log(Level.SEVERE, null, ex);
        }
            this.workoutDatabaseIsDirty = false;
    }
    
    private void useThisExercise(){
        workout.addWorkoutExercise(currentExercise.getExerciseType());
        closeDialog();
    }
    
    private void hideDialog(){
        
        this.setVisible(false);
    }
    
    private void removeExercise(ExerciseProperty exercise){
        if(obserableExerciseList.contains(exercise)){
            obserableExerciseList.remove(exercise);
        }
        if(_database.getExercise().contains(exercise.getExercise())){
            _database.getExercise().remove(exercise.getExercise());
            this.workoutDatabaseIsDirty = true;
        }
        currentExercise = null;
    }
    
    private void setEditing(boolean editing){
        oneRepMaxField.setEditable(editing);
        restIntervalField.setEditable(editing);
        repitionField.setEditable(editing);
        setsField.setEditable(editing);
        weightField.setEditable(editing);
        ExerciseNameField.setEditable(editing);
        isEditing = editing;

    }
    
     private void setSelectedExerciseFromList(final WorkoutDetailsHBox selectedExercise ){
       
         if(null != currentExercise && null != currentExercise.getNameProperty() && !currentExercise.equals(selectedExercise.getItem())){
            setEditing(false);
            currentExercise.getNameProperty().unbind();
       }
        if(selectedExercise.getItem() != null){
            Exercise e = selectedExercise.getItem();
            WeightLiftingExtension w = e.getWeightLiftingExtension();
            ExerciseNameField.setText(e.getName());
            oneRepMaxField.setText(String.valueOf(w.getOneRepMax()));
            restIntervalField.setText(String.valueOf(w.getRestInterval()));
            repitionField.setText(String.valueOf(w.getRepitions()));
            setsField.setText(String.valueOf(w.getSets()));
            weightField.setText(String.valueOf(w.getWeight()));
            intensityField.setText(String.valueOf(e.getIntensity()));
            durationField.setText(String.valueOf(e.getDuration()));
            volumeField.setText(String.valueOf(e.getVolume()));
            currentExercise = selectedExercise.getItem();
        }
    }

    void setWorkout(WorkoutExt wo) {
        this.workout = wo;
    }
    
     class Delta { double x, y; } 
     
     private class WorkoutDetailsHBox extends ListCell<ExerciseProperty> {
         private Text title = new Text();
         private Group editGroup = new Group();
         private Group removeGroup = new Group();
        public WorkoutDetailsHBox() {
        }
        @Override
        public void updateItem(final ExerciseProperty item, boolean empty) {
         super.updateItem(item, empty);
            title.getStyleClass().add("workout-listbox-title-text");
             if (item != null) {
                //title.setText(item.getName());
                title.textProperty().bind(item.getNameProperty());
                 setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent t) {
                      setSelectedExerciseFromList((WorkoutDetailsHBox)t.getSource());                             
                    }
                });
                
                editGroup.idProperty().set("notePadCloseBox");
                editGroup.setOnMouseClicked(new EventHandler<MouseEvent>() {

                     @Override
                     public void handle(MouseEvent t) {
                         editExercise(item);
                     }
                     
                 });
                Rectangle box = new Rectangle(20, 20, Color.WHITE);
                box.arcHeightProperty().set(10.0);
                box.arcWidthProperty().set(10.0);
                box.strokeTypeProperty().set(StrokeType.INSIDE);
                box.strokeWidthProperty().set(2.0);
                box.strokeProperty().set(Color.web("#001899"));
                ImageView editIcon = new ImageView(new Image("/trainingplanner/images/blue_file_edit.png"));
                editIcon.fitHeightProperty().set(13.0);
                editIcon.fitWidthProperty().set(18.0);
                editIcon.layoutYProperty().set(3.0);
                editIcon.layoutXProperty().set(3.0);
                editIcon.pickOnBoundsProperty().set(true);
                editIcon.preserveRatioProperty().set(true);
                
                editGroup.getChildren().addAll(box,editIcon);
                
                removeGroup.idProperty().set("notePadCloseBox");
                removeGroup.setOnMouseClicked(new EventHandler<MouseEvent>() {

                     @Override
                     public void handle(MouseEvent t) {
                         Group g = (Group) t.getSource();
                         removeExercise(item);
                     }
                     
                 });
                
                Circle circle = new Circle(10.0, Color.WHITE);
                circle.strokeTypeProperty().set(StrokeType.INSIDE);
                circle.strokeWidthProperty().set(2.0);
                circle.strokeProperty().set(Color.web("#990000"));
                
                removeGroup.getChildren().add(circle);
                
                
                AnchorPane border = new AnchorPane();
                border.setMaxSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
                border.setMinSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
                border.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);

                AnchorPane.setTopAnchor(title, 5.0);
                AnchorPane.setLeftAnchor(title, 5.0);
                AnchorPane.setTopAnchor(editGroup, 5.0);
                AnchorPane.setLeftAnchor(editGroup, 250.0);
                AnchorPane.setTopAnchor(removeGroup, 5.0);
                AnchorPane.setLeftAnchor(removeGroup, 225.0);

                border.getChildren().addAll(title,editGroup, removeGroup);
                 
                HBox rect = new HBox();
                rect.setSpacing(5.0);
                rect.setAlignment(Pos.CENTER_LEFT);
                rect.getChildren().add(border);
                setGraphic(rect);
            }
        }
    }
}
    /* private class ExerciseProperty extends Exercise{
         private SimpleStringProperty nameProperty = new SimpleStringProperty();
         private Exercise _exercise;
        private ExerciseType exerciseType;
         
         ExerciseProperty(Exercise e){
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
/*        public SimpleStringProperty getNameProperty() {
            return nameProperty;
        }

        /**
         * @param nameProperty the nameProperty to set
         */
/*        public void setNameProperty(SimpleStringProperty nameProperty) {
            this.nameProperty = nameProperty;
        }

        private void setSuperFields(Exercise e) {
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

        private void calulateVDI() {
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
            exerciseType.setDescription(_exercise.getDescription());
            exerciseType.setDuration(duration);
            exerciseType.setFocus(focus);
            exerciseType.setId(id);
            exerciseType.setIntensity(intensity);
            exerciseType.setName(name);
            exerciseType.setSportType(sportType);
            exerciseType.setVolume(volume);
            exerciseType.getExtensions().add(_exercise.getWeightLiftingExtension());
            return exerciseType;
            
        }
         
     }
}*/

