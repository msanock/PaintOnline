package charades.painting;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public abstract class CanvasAction implements PainterInterface, ViewerInterface {
    protected Canvas canvas;
    protected GraphicsContext gc;

    CanvasAction(Canvas canvas){
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
    }

    public void stopAction() {
        gc.closePath();
    }
}
