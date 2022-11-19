package paintOnline.painting;

import paintOnline.scenes.components.SimpleColorPicker;
import javafx.scene.canvas.Canvas;

public class ActionHandler {
    static SimpleColorPicker cp;

    static class Actions{
        static InteractiveAction currentAction;
        static DrawAction drawAction;
        static RubberAction rubberAction;
//        RedoAction redoAction;
//        UndoAction undoAction;
    }
    //static ServerClient serverClient;

    public ActionHandler(Canvas canvas, SimpleColorPicker cp) {
        ActionHandler.cp = cp;
        Actions.currentAction = Actions.drawAction = new DrawAction(canvas, cp);
        Actions.rubberAction = new RubberAction(canvas);
    }

    public static ActionParameters getTypes(){
        if(Actions.currentAction.getClass() == DrawAction.class)
            return new ActionParameters(ActionTypes.drawAction, Actions.drawAction.getDrawSize(), cp.getCurrentColor());
        else
            return new ActionParameters(ActionTypes.rubberAction, Actions.rubberAction.getRubberSize(), null);
    }

    public static InteractiveAction getCurrentAction() {
        return Actions.currentAction;
    }

    public static void setCurrentAction(ActionTypes action) {
        Actions.currentAction = (action.getAction() == Actions.drawAction.getClass()) ? Actions.drawAction : Actions.rubberAction;
    }
}
