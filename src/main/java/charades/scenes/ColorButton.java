package charades.scenes;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class ColorButton extends Button {
    private final Color buttonColor;

    public ColorButton(Color color){
        super();
        this.setMinWidth(35);
        this.setMinHeight(35);
        this.buttonColor = color;
        Color borderColor = Color.BLACK; //(color == Color.BLACK) ? Color.WHITE : Color.BLACK;
        this.setBackground(new Background(new BackgroundFill(Paint.valueOf(buttonColor.toString()), new CornerRadii(2), new Insets(0.5,0.5,0.5, 0.5))));
        this.setBorder(new Border(new BorderStroke(Paint.valueOf(borderColor.toString()), BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(1.5))));
        this.setOnMouseEntered(SimpleColorPicker::handleEntered);
        this.setOnMouseExited(SimpleColorPicker::handleExited);

    }
    
    public Color getColor(){
        return buttonColor;
    }



}
