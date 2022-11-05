package charades.painting;

import javafx.scene.canvas.Canvas;

import java.util.ArrayList;

public class ActionHandler {
    Action currentAction;

    DrawAction drawAction;
//    RubberAction rubberAction;
//    RedoAction redoAction;
//    UndoAction undoAction;



    public ActionHandler(Canvas canvas){
        currentAction = drawAction = new DrawAction(canvas);
//        rubberAction = new RubberAction();
    }

    public Action getCurrentAction() {
        return currentAction;
    }
}
