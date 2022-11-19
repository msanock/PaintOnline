package paintOnline.scenes;

import javafx.application.Platform;
import paintOnline.App;
import paintOnline.Constants;
import paintOnline.painting.ActionHandler;
import paintOnline.painting.ActionTypes;
import paintOnline.scenes.components.SimpleColorPicker;
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

import static paintOnline.App.myClient;
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
        App.setCanvas(canvas);
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
        myClient.setTypeOfMyPiora((ActionHandler.getCurrentAction().getClass() == ActionTypes.drawAction.getAction())? ActionTypes.drawAction : ActionTypes.rubberAction);
        myClient.moveMyPioroToPoint(new Pair<>(e.getX()+10,e.getY()+10));
        actionHandler.getCurrentAction().handleOnMousePressed(e);
    }

    public void handleCanvasOnMouseDragged(MouseEvent e) {
        myClient.moveMyPioroToPoint(new Pair<>(e.getX()+10,e.getY()+10));
        actionHandler.getCurrentAction().handleOnMouseDragged(e);
    }

    public void handleCanvasOnMouseReleased(MouseEvent e) {
        myClient.moveMyPioroToPoint(new Pair<>(e.getX()+10,e.getY()+10));
        actionHandler.getCurrentAction().handleOnMouseReleased(e);
    }


    public void handleRubberButton(ActionEvent e) {
        actionHandler.setCurrentAction(ActionTypes.rubberAction);
    }
}

