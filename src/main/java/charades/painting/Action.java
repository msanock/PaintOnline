package charades.painting;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public abstract class Action implements PainterAction, ViewerAction {
    protected Canvas canvas;
    protected GraphicsContext gc;

    Action(Canvas canvas){
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
    }

    @Override
    public void stopAction() {
        gc.closePath();
    }
}
