package charades.scenes;

import charades.Constants;
import charades.painting.Action;
import charades.painting.ActionHandler;
import charades.painting.DrawAction;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;

public class Painter implements Initializable {

     @FXML
     VBox vBox;
     @FXML
     Canvas canvas;
     @FXML
     ProgressBar pb;
     @FXML
     ColorPicker cp;

     ActionHandler actionHandler;



     @Override
     public void initialize(URL url, ResourceBundle resourceBundle) {
         actionHandler = new ActionHandler(canvas);
         vBox.setDisable(true);
         new Thread(() -> {
             try {
                 sleep(2000);
                 Platform.runLater(() -> startPainting("niewiem"));
             } catch (InterruptedException ex) {
                 ex.printStackTrace();
             }
         }).start();
     }

     public void startPainting(String word){
         vBox.setDisable(false);

         new Thread(() -> {
             final long startTime = System.currentTimeMillis();
             float progress = (float) ( System.currentTimeMillis() - startTime) / Constants.paintingTime;
             try {
                 while (progress < 1) {
                     progress = (float) (System.currentTimeMillis() - startTime) / Constants.paintingTime;
                     pb.setProgress(progress);
                     sleep(50);
                 }
             } catch (InterruptedException ex) {
                 ex.printStackTrace();
             } finally {
                 actionHandler.getCurrentAction().stopAction();
                 canvas.setOnMouseDragged(null);
                 canvas.setOnMouseReleased(null);
                 vBox.setDisable(true);
             }
         }).start();

     }

     public void handleCanvasOnMousePressed(MouseEvent e){
         actionHandler.getCurrentAction().handleOnMousePressed(e);
     }

     public void handleCanvasOnMouseDragged(MouseEvent e) {
         actionHandler.getCurrentAction().handleOnMouseDragged(e);
     }

     public void handleCanvasOnMouseReleased(MouseEvent e) {
         actionHandler.getCurrentAction().handleOnMouseReleased(e);
     }
 }

