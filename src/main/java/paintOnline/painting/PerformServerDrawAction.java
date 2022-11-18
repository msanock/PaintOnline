package paintOnline.painting;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Pair;

public class PerformServerDrawAction extends PerformServerAction{
    Color currentColor = Color.BLACK;
    double currentLineWidth = 2.0;

    final GraphicsContext gc;
    final Canvas canvas;
    Pair<Double,Double> lastPoint;

    public PerformServerDrawAction(Canvas canvas){
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
    }


    @Override
    public void performAction(Pair<Double, Double> newPoint) {
        synchronized (gc) {
            gc.setStroke(currentColor);
            gc.setLineWidth(currentLineWidth);
            if (lastPoint == null) lastPoint = newPoint;
            gc.strokeLine(newPoint.getKey(), newPoint.getValue(), lastPoint.getKey(), lastPoint.getValue());
            lastPoint = newPoint;
        }
    }
}
