package charades.scenes;

import charades.painting.Colors;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class SimpleColorPicker extends TilePane {
    private ColorButton currentColorButton;

    public SimpleColorPicker(){
        super();
        this.setHgap(5);
        this.setVgap(5);
        for (var col : Colors.values()){
            this.getChildren().add(new ColorButton(col.getColor()));
        }

    }

    public static void handleEntered(MouseEvent e){
        ScaleTransition btnTrans=new ScaleTransition(Duration.millis(100), (Node) e.getSource());
        btnTrans.setToX(1.2);
        btnTrans.setToY(1.2);
        btnTrans.play();
    }

    public static void handleExited(MouseEvent e){
        ScaleTransition btnTrans=new ScaleTransition(Duration.millis(100), (Node) e.getSource());
        btnTrans.setToX(1);
        btnTrans.setToY(1);
        btnTrans.play();
    }

    public Color getCurrentColor(){
        return currentColorButton.getColor();
    }

}
