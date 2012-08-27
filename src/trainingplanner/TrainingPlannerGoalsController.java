/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.LabelBuilder;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
    
    private Athlete.KeyPerformanceIndicators KPIs;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("initializing goals controller");
        if (this.KPIs != null) {
            setGoals();
        } 
    } 
    
    public void setGoals(){
        List<IKPIType> kpis = KPIs.getKPI();
       // if (goalsListPane == null ) goalsListPane = new AnchorPane();
        goalsListPane.getChildren().clear();
        int x = 0;
        for (IKPIType kpi : kpis){
            System.out.println("KPI: "+kpi.getSportsType()+" "+kpi.getMeasurement().getMeasureType()+" current value: "+
                                kpi.getCurrentValue().getValue()+" goal value: "+kpi.getGoalValue().getValue());  
            try {
                URL location = getClass().getResource("FXML/TrainingPlannerGoal.fxml");
                FXMLLoader goalLoader = new FXMLLoader();
                goalLoader.setLocation(location);
                goalLoader.setBuilderFactory(new JavaFXBuilderFactory());
                
                Parent goalPane = (Parent) goalLoader.load(location.openStream());
                goalPane.setLayoutY(x*35.0);
                x++;
                
                TrainingPlannerGoalController goalController = goalLoader.getController();
                goalController.setKPI((KPI)kpi);
                //goalController.setSportsTitle(kpi.getSportsType().value());
                goalsListPane.getChildren().add(goalPane);
            } catch (IOException ex) {
                System.out.println("Error: "+ex.getMessage());
                Logger.getLogger(TrainingPlannerWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
     
    }
    
    public void setKPIs(Athlete.KeyPerformanceIndicators KPIs){
     this.KPIs = KPIs;   
    }
    
    @FXML private void editGoals(){
        System.out.println("Edit Button Pressed.");
        
        Scene scene = goalsListPane.getScene();
        
        if (scene != null){
            Parent currentStage = scene.getRoot();
            addDialogBox(currentStage);
        }
    }
    
    private void addDialogBox(final Parent parent){
        final Stage dialog = new Stage(StageStyle.TRANSPARENT);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(parent.getScene().getWindow());
        dialog.setScene(
            new Scene(
              HBoxBuilder.create().styleClass("modal-dialog").children(
                LabelBuilder.create().text("Will you like this page?").build(),
                ButtonBuilder.create().text("Yes").defaultButton(true).onAction(new EventHandler<ActionEvent>() {
                  @Override public void handle(ActionEvent actionEvent) {
                    // take action and close the dialog.
                    //System.out.println("Liked: " + webView.getEngine().getTitle());
                    parent.getScene().getRoot().setEffect(null);
                    dialog.close();
                  }
                }).build(),
                ButtonBuilder.create().text("No").cancelButton(true).onAction(new EventHandler<ActionEvent>() {
                  @Override public void handle(ActionEvent actionEvent) {
                    // abort action and close the dialog.
                    //System.out.println("Disliked: " + webView.getEngine().getTitle());
                    parent.getScene().getRoot().setEffect(null);
                    dialog.close();
                  }
                }).build()
              ).build()
              , Color.TRANSPARENT
            )
        );
        dialog.getScene().getStylesheets().add(getClass().getResource("FXML/css/modal-dialog.css").toExternalForm());

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
        parent.getScene().getRoot().setEffect(new BoxBlur());
        dialog.show();
    }
     class Delta { double x, y; } 
}
