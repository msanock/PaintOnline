package charades.painting;

import javafx.scene.paint.Color;

public enum ActionTypes {
    rubberAction(RubberAction.class),
    drawAction(DrawAction.class);

    private final Class<? extends CanvasAction> action;

    ActionTypes(Class<? extends CanvasAction> action) {
        this.action = action;
    }

    public Class<? extends CanvasAction> getAction(){
        return action;
    }

}
