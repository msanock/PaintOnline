package charades.sceneControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

 public class Painter implements Initializable {

     @FXML
     Canvas canvas;
     @FXML
     ColorPicker cp;

     GraphicsContext gc;

     @Override
     public void initialize(URL url, ResourceBundle resourceBundle) {
         gc = canvas.getGraphicsContext2D();
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

