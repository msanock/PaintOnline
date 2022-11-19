package paintOnline.painting;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.util.Pair;

public class ServerDrawAction extends ServerAction {
    Color currentColor = Color.BLACK;
    double currentLineWidth = 2.0;


    Pair<Double,Double> lastPoint;

    public ServerDrawAction(Canvas canvas){
        super(canvas);
    }


    @Override
    public void reset(){
        lastPoint = null;
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

    public void setParameters(double size, Colors color) {
        currentLineWidth = size;
        currentColor = color.getColor();
    }
}
