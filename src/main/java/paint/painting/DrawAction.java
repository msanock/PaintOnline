package paint.painting;

import paint.scenes.components.SimpleColorPicker;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class DrawAction extends InteractiveAction {
    private final SimpleColorPicker scp;
    boolean isActive;
    private Colors currentColor;
    double currentLineWidth;



    DrawAction(Canvas canvas, SimpleColorPicker scp) {
        super(canvas);
        this.scp = scp;
        continues = new AtomicBoolean(false);
        queue = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void handleOnMousePressed(MouseEvent e) {
        continues.set(true);

        try {
            currentColor = scp.getCurrentColor();
            //this.sendAction();
            gc.setStroke(currentColor.getColor());
        } catch (Exception ex) {
            //isActive = false;
            return;
        }
        //isActive = true;
        queue.add(new Pair<>(e.getX(), e.getY()));
        gc.beginPath();
        synchronized (gc) {
            gc.setStroke(currentColor.getColor());
            gc.setLineWidth(currentLineWidth);
            gc.lineTo(e.getX(), e.getY());
        }
        System.out.println(e.getX()+ "--" + e.getY());
    }

    @Override
    public void handleOnMouseDragged(MouseEvent e) {
        //if (!isActive) return;

        queue.add(new Pair<>(e.getX(), e.getY()));
        System.out.println(e.getX()+ "--" + e.getY());
        synchronized (gc) {
            gc.setStroke(currentColor.getColor());
            gc.setLineWidth(currentLineWidth);
            gc.lineTo(e.getX(), e.getY());
            gc.stroke();
        }
    }

    @Override
    public void handleOnMouseReleased(MouseEvent e) {
        if (!isActive) return;
        gc.lineTo(e.getX(), e.getY());
        queue.add(new Pair<>(e.getX(), e.getY()));
        continues.set(false);
        System.out.println(e.getX()+ "--" + e.getY());
        synchronized (gc) {
            gc.setStroke(currentColor.getColor());
            gc.setLineWidth(currentLineWidth);
            gc.stroke();
            gc.closePath();
        }
    }


    @Override
    public void sendAction() {

    }

}