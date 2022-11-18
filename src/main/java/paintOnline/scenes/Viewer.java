package paintOnline.scenes;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;

public class Viewer implements Initializable {

    @FXML
    VBox vBox;
    @FXML
    Canvas canvas;
    @FXML
    ProgressBar pb;

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



    }

    public void handleCanvasOnMousePressed(MouseEvent e) {
        gc.setStroke(Color.BLACK);
        gc.beginPath();
        gc.lineTo(e.getX(), e.getY());
        System.out.println(e.getX()+ "--" + e.getY());
    }

    public void handleCanvasOnMouseDragged(MouseEvent e) {
        gc.lineTo(e.getX(), e.getY());
        System.out.println(e.getX()+ "--" + e.getY());
        gc.stroke();
    }

    public void handleCanvasOnMouseReleased(MouseEvent e) {
        gc.lineTo(e.getX(), e.getY());
        System.out.println(e.getX()+ "--" + e.getY());
        gc.stroke();
        gc.closePath();
    }
}

