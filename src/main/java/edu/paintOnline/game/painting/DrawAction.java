package edu.paintOnline.game.painting;

import edu.paintOnline.game.scenes.components.SimpleColorPicker;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;


public class DrawAction extends InteractiveAction {
    private final SimpleColorPicker scp;
    boolean isActive;
    private Colors currentColor;
    double currentLineWidth;

    DrawAction(Canvas canvas, SimpleColorPicker scp) {
        super(canvas);
        this.scp = scp;
    }

    @Override
    public void handleOnMousePressed(MouseEvent e) {

        try {
            currentColor = scp.getCurrentColor();
            //this.sendAction();
            gc.setStroke(currentColor.getColor());
        } catch (Exception ex) {
            //isActive = false;
            return;
        }
        //isActive = true;
        gc.beginPath();
        synchronized (gc) {
            currentLineWidth = ActionHandler.sizeSlider.getValue();
            gc.setStroke(currentColor.getColor());
            gc.setLineWidth(currentLineWidth);
            gc.lineTo(e.getX(), e.getY());
        }
//        System.out.println(e.getX()+ "--" + e.getY());
    }

    @Override
    public void handleOnMouseDragged(MouseEvent e) {
        //if (!isActive) return;

        //     System.out.println(e.getX()+ "--" + e.getY());
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
//        System.out.println(e.getX()+ "--" + e.getY());
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

    public double getDrawSize() {
        return currentLineWidth;
    }

    public Colors getColor() {
        return currentColor;
    }
}
