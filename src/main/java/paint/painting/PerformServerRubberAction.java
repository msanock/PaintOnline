package paint.painting;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Pair;

public class PerformServerRubberAction extends PerformServerAction{
    double currentSize;

    final GraphicsContext gc;
    final Canvas canvas;

    public PerformServerRubberAction(Canvas canvas){
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
    }

    @Override
    public void performAction(Pair<Double, Double> point) {
        synchronized (gc){
            gc.clearRect((point.getKey() - currentSize) / 2, (point.getValue() - currentSize) / 2, currentSize, currentSize);
        }
    }
}
