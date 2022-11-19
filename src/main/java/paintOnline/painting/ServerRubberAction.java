package paintOnline.painting;

import javafx.scene.canvas.Canvas;
import javafx.util.Pair;

public class ServerRubberAction extends ServerAction {
    double currentSize = 20;

    public ServerRubberAction(Canvas canvas){
        super(canvas);
    }

    @Override
    public void performAction(Pair<Double, Double> point) {
        synchronized (gc){
            gc.clearRect(point.getKey()+10 - currentSize / 2, point.getValue()+10 - currentSize / 2, currentSize, currentSize);
        }
    }

    public void setParameters(double size) {
        this.currentSize = size;
    }
}
