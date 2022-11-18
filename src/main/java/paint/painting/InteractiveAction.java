package charades.painting;

import javafx.scene.canvas.Canvas;
import javafx.util.Pair;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class InteractiveAction extends CanvasAction implements InteractionWithCanvasInterface {
    ConcurrentLinkedQueue<Pair<Double, Double>> queue;
    AtomicBoolean continues;

    InteractiveAction(Canvas canvas) {
        super(canvas);
    }
}
