package edu.paintOnline.game.scenes.components;

import edu.paintOnline.game.painting.ActionHandler;
import edu.paintOnline.game.painting.ActionTypes;
import edu.paintOnline.game.painting.Colors;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class SimpleColorPicker extends TilePane {
    private static ColorButton currentColorButton;
    private static List<ColorButton> colorButtons;

    public SimpleColorPicker(){
        super();
        this.setHgap(3);
        this.setVgap(3);
        colorButtons = new ArrayList<>();
        for (var col : Colors.values()){
            colorButtons.add(new ColorButton(col));
        }
        this.getChildren().addAll(colorButtons);
        currentColorButton = colorButtons.get(0);
        currentColorButton.select();
    }

    public static void handleEntered(MouseEvent e){
        ScaleTransition btnTrans = new ScaleTransition(Duration.millis(100), (Node) e.getSource());
        btnTrans.setToX(1.2);
        btnTrans.setToY(1.2);
        btnTrans.play();
    }

    public static void handleExited(MouseEvent e){
        ScaleTransition btnTrans = new ScaleTransition(Duration.millis(100), (Node) e.getSource());
        btnTrans.setToX(1);
        btnTrans.setToY(1);
        btnTrans.play();
    }

    public static void handleAction(ActionEvent e){
        if(currentColorButton != null) currentColorButton.unselect();
        currentColorButton = (ColorButton) e.getSource();
        currentColorButton.select();
        ActionHandler.setCurrentAction(ActionTypes.drawAction);

    }

    public Colors getCurrentColor() throws RuntimeException {
        if (currentColorButton == null) throw new RuntimeException("No color chosen");
        return currentColorButton.getColor();
    }

}
