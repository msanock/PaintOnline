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

public class SimpleColorPicker extends TilePane {
    private static ColorButton currentColorButton;

    public SimpleColorPicker(){
        super();
        this.setHgap(3);
        this.setVgap(3);
        for (var col : Colors.values()){
            this.getChildren().add(new ColorButton(col));
        }
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
