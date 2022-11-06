package charades.painting;

import charades.scenes.components.SimpleColorPicker;
import javafx.scene.canvas.Canvas;

public class ActionHandler {
    InteractiveAction currentAction;
    

    DrawAction drawAction;
    RubberAction rubberAction;
//    RedoAction redoAction;
//    UndoAction undoAction;



    public ActionHandler(Canvas canvas, SimpleColorPicker cp) {
        currentAction = drawAction = new DrawAction(canvas, cp);
        rubberAction = new RubberAction(canvas);
    }

    public InteractiveAction getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction(ActionTypes action) {
        currentAction = (action.getAction() == drawAction.getClass()) ? drawAction : rubberAction;
    }
}
