package paint.scenes;

import paint.Constants;
import paint.painting.ActionHandler;
import paint.painting.ActionTypes;
import paint.scenes.components.SimpleColorPicker;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.net.URL;
import java.util.ResourceBundle;

import static paint.App.myClient;
import static java.lang.Thread.sleep;

public class Painter implements Initializable {

     @FXML
     VBox vBox;
     @FXML
     Canvas canvas;
     @FXML
     ProgressBar pb;
     @FXML
     SimpleColorPicker simpleColorPicker;
     @FXML
     Button rubberButton;

     ActionHandler actionHandler;




     @Override
     public void initialize(URL url, ResourceBundle resourceBundle) {
         actionHandler = new ActionHandler(canvas, simpleColorPicker);
         vBox.setDisable(true);
         startPainting();
     }

     public void startPainting(){
         vBox.setDisable(false);

//         new Thread(() -> {
//             final long startTime = System.currentTimeMillis();
//             float progress = (float) ( System.currentTimeMillis() - startTime) / Constants.paintingTime;
//             try {
//                 while (progress < 1) {
//                     progress = (float) (System.currentTimeMillis() - startTime) / Constants.paintingTime;
//                     pb.setProgress(progress);
//                     sleep(50);
//                 }
//             } catch (InterruptedException ex) {
//                 ex.printStackTrace();
//             } finally {
//                 actionHandler.getCurrentAction().stopAction();
//                 canvas.setOnMouseDragged(null);
//                 canvas.setOnMouseReleased(null);
//                 vBox.setDisable(true);
//             }
//         }).start();

     }



     public void handleCanvasOnMousePressed(MouseEvent e){

         myClient.moveMyPioroToPoint(new Pair<>(e.getX(),e.getY()));

         actionHandler.getCurrentAction().handleOnMousePressed(e);
     }

     public void handleCanvasOnMouseDragged(MouseEvent e) {
         myClient.moveMyPioroToPoint(new Pair<>(e.getX(),e.getY()));
         actionHandler.getCurrentAction().handleOnMouseDragged(e);
     }

     public void handleCanvasOnMouseReleased(MouseEvent e) {
         myClient.moveMyPioroToPoint(new Pair<>(e.getX(),e.getY()));
         actionHandler.getCurrentAction().handleOnMouseReleased(e);
     }


     public void handleRubberButton(ActionEvent e) {
         actionHandler.setCurrentAction(ActionTypes.rubberAction);
     }
 }

