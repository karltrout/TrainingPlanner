/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import trainingplanner.controls.EditGoalDialog;
import trainingplanner.org.xsd.Athlete;
import trainingplanner.org.xsd.IKPIType;

/**
 * FXML Controller class
 *
 * @author troutk
 */
public class TrainingPlannerGoalsController  extends AnchorPane implements Initializable {
 
    @FXML private AnchorPane goalsListPane;
    @FXML private ImageView editGoalsButton;
    Stage dialog;
    //private Athlete.KeyPerformanceIndicators KPIs;
    private ObservableList<IKPIType> kpis;
    
    public ObservableList<IKPIType> getKPIs() {
        return kpis;
    }
    
     public TrainingPlannerGoalsController(){                   
        URL location = getClass().getResource("FXML/TrainingPlannerGoals.fxml");
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
        
        editGoalsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent t) {
                 editGoals();
             }
         });
        
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("initializing goals controller");
        if (this.kpis != null) {
            setGoals();
        } 
    } 
    
    public void setGoals(){
        goalsListPane.getChildren().clear();
        int layoutYvalue = 0;
        for (IKPIType kpi : kpis){
            TrainingPlannerGoalController goal = new TrainingPlannerGoalController();
            goal.setLayoutY(layoutYvalue*35.0);
            layoutYvalue++;
            goal.setKPI(kpi);
            goalsListPane.getChildren().add(goal);
            double height = goalsListPane.getHeight();
            goalsListPane.setMinHeight(height+35.0);
        }
    }
    
    public void setKPIs(Athlete.KeyPerformanceIndicators KPIs){
       this.kpis = FXCollections.observableList(KPIs.getKPI());
       kpis.addListener(new ListChangeListener() {
               @Override
               public void onChanged(ListChangeListener.Change change) {
                   System.out.println("Detected a change! ");
                   setGoals();
               }
           });
       setGoals(); 
    }
    
    @FXML public void editGoals(){
        Scene scene = goalsListPane.getScene();
        if (scene != null){
            Parent currentStage = scene.getRoot();
            if (dialog == null) {
                addDialogBox(currentStage);
            }
                currentStage.getScene().getRoot().setEffect(new BoxBlur());
                dialog.show();
        }
    }
    
    private void addDialogBox(final Parent parent){
        dialog = new Stage(StageStyle.TRANSPARENT);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(parent.getScene().getWindow());
        EditGoalDialog editGoalDialog = new EditGoalDialog(parent);
        editGoalDialog.setKPIs(getKPIs());
        dialog.setScene(
            new Scene(editGoalDialog)
        );

        // allow the dialog to be dragged around.
        final Node root = dialog.getScene().getRoot();
        final Delta dragDelta = new Delta();
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            // record a delta distance for the drag and drop operation.
            dragDelta.x = dialog.getX() - mouseEvent.getScreenX();
            dragDelta.y = dialog.getY() - mouseEvent.getScreenY();
          }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
                dialog.setX(mouseEvent.getScreenX() + dragDelta.x);
                dialog.setY(mouseEvent.getScreenY() + dragDelta.y);
          }
        });

    }
     class Delta { double x, y; } 
}
