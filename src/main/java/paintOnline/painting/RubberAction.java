package paintOnline.painting;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

public class RubberAction extends InteractiveAction{
    private double size = 20;
    RubberAction(Canvas canvas) {
        super(canvas);
    }

    @Override
    public void handleOnMousePressed(MouseEvent e) {
        gc.clearRect((e.getX() - size) / 2, (e.getY() - size) / 2, size, size);
    }

    @Override
    public void handleOnMouseDragged(MouseEvent e) {
        gc.clearRect(e.getX() - size / 2, e.getY() - size / 2, size, size);
    }

    @Override
    public void handleOnMouseReleased(MouseEvent e) {
        gc.clearRect(e.getX() - size / 2, e.getY() - size / 2, size, size);
    }


    @Override
    public void sendAction() {

    }

    public double getRubberSize() {
        return size;
    }

//    @Override
//    public void performAction() {
//
//    }
}
