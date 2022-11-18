package paint.painting;

import paint.scenes.components.SimpleColorPicker;
import javafx.scene.canvas.Canvas;

public class ActionHandler {

    static class Actions{
        static InteractiveAction currentAction;
        static DrawAction drawAction;
        static RubberAction rubberAction;
//        RedoAction redoAction;
//        UndoAction undoAction;
    }
    //static ServerClient serverClient;


    public ActionHandler(Canvas canvas, SimpleColorPicker cp) {
        Actions.currentAction = Actions.drawAction = new DrawAction(canvas, cp);
        Actions.rubberAction = new RubberAction(canvas);
        //serverClient = new ServerClient();
    }

    public InteractiveAction getCurrentAction() {
        return Actions.currentAction;
    }

    public static void setCurrentAction(ActionTypes action) {
        Actions.currentAction = (action.getAction() == Actions.drawAction.getClass()) ? Actions.drawAction : Actions.rubberAction;
    }
}
