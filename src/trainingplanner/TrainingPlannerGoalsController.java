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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import trainingplanner.controls.EditGoalDialog;
import trainingplanner.org.extensions.KPI;
import trainingplanner.org.xsd.Athlete;
import trainingplanner.org.xsd.IKPIType;

/**
 * FXML Controller class
 *
 * @author troutk
 */
public class TrainingPlannerGoalsController implements Initializable {
 
    @FXML private AnchorPane goalsListPane;
    
    Stage dialog;
    //private Athlete.KeyPerformanceIndicators KPIs;
    private ObservableList<IKPIType> kpis;
    
    public ObservableList<IKPIType> getKPIs() {
        return kpis;
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
        //kpis = FXCollections.observableList(KPIs.getKPI());
       // if (goalsListPane == null ) goalsListPane = new AnchorPane();
        goalsListPane.getChildren().clear();
        int x = 0;
        for (IKPIType kpi : kpis){
          //  System.out.println("KPI: "+kpi.getSportsType()+" "+kpi.getMeasurement().getMeasureType()+" current value: "+
           //                     kpi.getCurrentValue().getValue()+" goal value: "+kpi.getGoalValue().getValue());  
            try {
                URL location = getClass().getResource("FXML/TrainingPlannerGoal.fxml");
                FXMLLoader goalLoader = new FXMLLoader();
                goalLoader.setLocation(location);
                goalLoader.setBuilderFactory(new JavaFXBuilderFactory());
                
                Parent goalPane = (Parent) goalLoader.load(location.openStream());
                goalPane.setLayoutY(x*35.0);
                x++;
                
                TrainingPlannerGoalController goalController = goalLoader.getController();
                goalController.setKPI(kpi);
                //goalController.setSportsTitle(kpi.getSportsType().value());
                goalsListPane.getChildren().add(goalPane);
                double height = goalsListPane.getHeight();
                goalsListPane.setMinHeight(height+35.0);
                
            } catch (IOException ex) {
                System.out.println("Error: "+ex.getMessage());
                Logger.getLogger(TrainingPlannerWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        //dialog.getScene().getStylesheets().add(getClass().getResource("FXML/css/modal-dialog.css").toExternalForm());

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
