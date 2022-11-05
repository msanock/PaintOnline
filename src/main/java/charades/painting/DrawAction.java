package charades.painting;

import charades.scenes.Painter;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class DrawAction extends Action{

    DrawAction(Canvas canvas) {
        super(canvas);
    }

    @Override
    public void handleOnMousePressed(MouseEvent e) {
        gc.setStroke(Color.BLACK);
        gc.beginPath();
        gc.lineTo(e.getX(), e.getY());
        System.out.println(e.getX()+ "--" + e.getY());
    }

    @Override
    public void handleOnMouseDragged(MouseEvent e) {
        gc.lineTo(e.getX(), e.getY());
        System.out.println(e.getX()+ "--" + e.getY());
        gc.stroke();
    }

    @Override
    public void handleOnMouseReleased(MouseEvent e) {
        gc.lineTo(e.getX(), e.getY());
        System.out.println(e.getX()+ "--" + e.getY());
        gc.stroke();
        gc.closePath();
    }


    @Override
    public void sendAction() {

    }

    @Override
    public void performAction() {

    }
}
