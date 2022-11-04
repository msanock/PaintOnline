package charades.sceneControllers;

import charades.Constants;
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

     GraphicsContext gc;

     @Override
     public void initialize(URL url, ResourceBundle resourceBundle) {
         gc = canvas.getGraphicsContext2D();
         gc.setLineWidth(4);
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
                     System.out.println(progress);
                     pb.setProgress(progress);
                     sleep(50);
                 }
             } catch (InterruptedException ex) {
                 ex.printStackTrace();
             } finally {
                 gc.closePath();
                 canvas.setOnMouseDragged(null);
                 canvas.setOnMouseReleased(null);
                 vBox.setDisable(true);
             }
         }).start();

     }

     public void handleCanvasOnMousePressed(MouseEvent e) {
         gc.setStroke(Color.BLACK);
         gc.beginPath();
         gc.lineTo(e.getX(), e.getY());
     }

     public void handleCanvasOnMouseDragged(MouseEvent e) {
         gc.lineTo(e.getX(), e.getY());
         gc.stroke();

     }

     public void handleCanvasOnMouseReleased(MouseEvent e) {
         gc.lineTo(e.getX(), e.getY());
         gc.stroke();
         gc.closePath();
     }
 }

