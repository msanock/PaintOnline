package paintOnline.painting;

import javafx.scene.canvas.Canvas;
import javafx.util.Pair;



public abstract class InteractiveAction extends CanvasAction implements InteractionWithCanvasInterface {

    InteractiveAction(Canvas canvas) {
        super(canvas);
    }
}
