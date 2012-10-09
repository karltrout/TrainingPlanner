/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import trainingplanner.org.xsd.WeightLiftingDatabase;
import trainingplanner.org.xsd.WeightLiftingExerciseDataBase.Exercises;
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
    
    private WeightLiftingDatabase _database;
    private ExerciseProperty currentExercise;
    private final ObservableList<Exercises> obserableExerciseList;
    private boolean workoutDatabaseIsDirty;
    private final ObservableStringValue currentExerciseName= new SimpleStringProperty();;
    private boolean isEditing;
    private WorkoutDetailsHBox currentSelection;
   
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
                saveExersize();
                
            }
        });
        
        cancelExcersizeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                closeDialog();
                
            }
        });
        
        obserableExerciseList = FXCollections.observableList(_database.getDataBase().getExercises());
        obserableExerciseList.addListener(new ListChangeListener<Exercises>() {
            @Override
            public void onChanged(Change<? extends Exercises> change) {
               workoutDatabaseIsDirty = true;
            }
        });

        muscleGroupExcersizeList.setItems(obserableExerciseList);
        setEditing(false);
    }  
        
    private void closeDialog(){
        this.setVisible(false);
    }
    

    private void createNewExersize() {
        ExerciseProperty exercise = new ExerciseProperty();       
        exercise.setName("New exercise");
        exercise.setWeightLiftingExtension(new WeightLiftingExtension());
        exercise.getWeightLiftingExtension().setExcersizeName("New exercise");
        obserableExerciseList.add(exercise);
        muscleGroupExcersizeList.getSelectionModel().selectLast();
        editExersize();
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

    private void editExersize() {
        if (currentExercise != null) currentExercise.nameProperty.unbind();
        currentExercise = (ExerciseProperty) muscleGroupExcersizeList.getSelectionModel().getSelectedItem(); 
        currentExercise.nameProperty.bind(ExerciseNameField.textProperty());
        setEditing(true);
    }
    
    private void saveExersize(){
        setEditing(false);   
        WeightLiftingExtension ext = currentExercise.getWeightLiftingExtension();
        ext.setOneRepMax(Double.parseDouble(oneRepMaxField.getText()));
        ext.setRestInterval(Double.parseDouble(restIntervalField.getText()));
        ext.setRepitions(Integer.parseInt(repitionField.getText()));
        ext.setSets(Integer.parseInt(setsField.getText()));
        ext.setWeight(Double.parseDouble(weightField.getText()));
        ext.setExcersizeName(ExerciseNameField.getText());
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
         currentSelection = selectedExercise;
         ExerciseNameField.setText(selectedExercise.getItem().getName());
    }
    
     class Delta { double x, y; } 
     
     private class WorkoutDetailsHBox extends ListCell<ExerciseProperty> {
         private Text title = new Text();
        @Override
        public void updateItem(ExerciseProperty item, boolean empty) {
         super.updateItem(item, empty);
            title.getStyleClass().add("workout-listbox-title-text");
             if (item != null) {
                //title.setText(item.getName());
                title.textProperty().bind(item.nameProperty);
                 setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent t) {
                      setSelectedExerciseFromList((WorkoutDetailsHBox)t.getSource());                             
                    }
                });
                 
                HBox rect = new HBox();
                rect.setSpacing(5.0);
                rect.setAlignment(Pos.CENTER_LEFT);
                rect.getChildren().add(title);
                setGraphic(rect);
            }
        }
    }
     
     private class ExerciseProperty extends Exercises{
         private SimpleStringProperty nameProperty = new SimpleStringProperty();

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
         
     }
}

