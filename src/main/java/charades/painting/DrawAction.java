package charades.painting;

import charades.scenes.components.SimpleColorPicker;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

public class DrawAction extends InteractiveAction {
    protected SimpleColorPicker scp;
    boolean isActive;


    DrawAction(Canvas canvas, SimpleColorPicker scp) {
        super(canvas);
        this.scp = scp;
    }

    @Override
    public void handleOnMousePressed(MouseEvent e) {

        try {
            gc.setStroke(scp.getCurrentColor());
        } catch (Exception ex) {
            isActive = false;
            return;
        }
        isActive = true;

        gc.beginPath();
        gc.lineTo(e.getX(), e.getY());
        System.out.println(e.getX()+ "--" + e.getY());
    }

    @Override
    public void handleOnMouseDragged(MouseEvent e) {
        if (!isActive) return;
        gc.lineTo(e.getX(), e.getY());
        System.out.println(e.getX()+ "--" + e.getY());
        gc.stroke();
    }

    @Override
    public void handleOnMouseReleased(MouseEvent e) {
        if (!isActive) return;
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
