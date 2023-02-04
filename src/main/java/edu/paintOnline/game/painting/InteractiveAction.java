package edu.paintOnline.game.painting;

import javafx.scene.canvas.Canvas;


public abstract class InteractiveAction extends CanvasAction implements InteractionWithCanvasInterface {

    InteractiveAction(Canvas canvas) {
        super(canvas);
    }
}
