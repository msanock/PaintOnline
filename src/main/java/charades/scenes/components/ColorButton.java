package charades.scenes.components;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class ColorButton extends Button {
    private final Color buttonColor;
    Color borderColor;

    public ColorButton(Color color){
        super();
        this.setMinWidth(20);
        this.setMinHeight(20);
        this.setMaxWidth(20);
        this.setMaxHeight(20);
        this.buttonColor = color;
        borderColor = (color == Color.BLACK) ? Color.DARKGRAY : Color.BLACK;
        this.setBackground(new Background(new BackgroundFill(Paint.valueOf(buttonColor.toString()), new CornerRadii(10), new Insets(0.5,0.5,0.5, 0.5))));
        this.setBorder(new Border(new BorderStroke(Paint.valueOf(borderColor.toString()), BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(1.5))));
        this.setOnMouseEntered(SimpleColorPicker::handleEntered);
        this.setOnMouseExited(SimpleColorPicker::handleExited);
        this.setOnAction(SimpleColorPicker::handleAction);
    }

    public void select(){
        this.setBorder(new Border(new BorderStroke(Paint.valueOf(borderColor.toString()), BorderStrokeStyle.SOLID, new CornerRadii(17.5), new BorderWidths(3))));
    }

    public void unselect(){
        this.setBorder(new Border(new BorderStroke(Paint.valueOf(borderColor.toString()), BorderStrokeStyle.SOLID, new CornerRadii(17.5), new BorderWidths(1.5))));
    }

    public Color getColor(){
        return buttonColor;
    }



}
