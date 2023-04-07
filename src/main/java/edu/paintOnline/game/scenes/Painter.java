package edu.paintOnline.game.scenes;

import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import edu.paintOnline.game.App;
import edu.paintOnline.game.painting.ActionHandler;
import edu.paintOnline.game.painting.ActionTypes;
import edu.paintOnline.game.scenes.components.SimpleColorPicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;

import java.net.URL;
import java.util.ResourceBundle;

import static edu.paintOnline.game.App.myClient;
import static java.lang.Thread.sleep;

public class Painter implements Initializable {

    static  Button rb;
    @FXML
    VBox vBox;
    @FXML
    Canvas canvas;
    @FXML
    Slider sizeSlider;
    @FXML
    SimpleColorPicker simpleColorPicker;
    @FXML
    Button rubberButton;

    ActionHandler actionHandler;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rb = rubberButton;
        sizeSlider.setMin(1);
        sizeSlider.setMax(20);
        sizeSlider.setValue(10);
        actionHandler = new ActionHandler(canvas, simpleColorPicker, sizeSlider);
        vBox.setDisable(true);
        App.setCanvas(canvas);
        new Thread(() -> {
            try {
                sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            vBox.setDisable(false);
        }).start();
    }

    public double sizeSliderValue(){
        return sizeSlider.getValue();
    }

    public void handleEntered(MouseEvent e){
        ScaleTransition btnTrans=new ScaleTransition(Duration.millis(100), (Node) e.getSource());
        btnTrans.setToX(1.2);
        btnTrans.setToY(1.2);
        btnTrans.play();
    }

    public void handleExited(MouseEvent e){
        ScaleTransition btnTrans=new ScaleTransition(Duration.millis(100), (Node) e.getSource());
        btnTrans.setToX(1);
        btnTrans.setToY(1);
        btnTrans.play();
    }

    public static void unselectRubber(){
        if (rb != null)
            rb.setBorder(null);
    }



    public void handleCanvasOnMousePressed(MouseEvent e){
        myClient.setTypeOfMyPiora(ActionHandler.getTypes());
        myClient.moveMyPenToPoint(new Pair<>(e.getX(),e.getY()));
        actionHandler.getCurrentAction().handleOnMousePressed(e);
    }

    public void handleCanvasOnMouseDragged(MouseEvent e) {
        myClient.moveMyPenToPoint(new Pair<>(e.getX(),e.getY()));
        actionHandler.getCurrentAction().handleOnMouseDragged(e);
    }

    public void handleCanvasOnMouseReleased(MouseEvent e) {
        myClient.moveMyPenToPoint(new Pair<>(e.getX(),e.getY()));
        actionHandler.getCurrentAction().handleOnMouseReleased(e);
    }


    public void handleRubberButton(ActionEvent e) {
        rubberButton.setBorder(new Border(new BorderStroke(Paint.valueOf(Color.BLACK.toString()), BorderStrokeStyle.SOLID, new CornerRadii(17.5), new BorderWidths(1.4))));
        actionHandler.setCurrentAction(ActionTypes.rubberAction);
    }
}

