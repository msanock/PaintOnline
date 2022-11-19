package paintOnline.painting;

import javafx.scene.canvas.Canvas;
import javafx.util.Pair;
import paintOnline.scenes.components.SimpleColorPicker;

public class ServerActionHandler {
    final ServerActions serverActions;

    private class ServerActions{
        ServerAction currentAction;
        final ServerDrawAction drawAction;
        final ServerRubberAction rubberAction;
//        RedoAction redoAction;
//        UndoAction undoAction;
        ServerActions(Canvas canvas){
            currentAction = drawAction = new ServerDrawAction(canvas);
            rubberAction = new ServerRubberAction(canvas);
        }

    }

    public void reset(){
        serverActions.currentAction.reset();
    }

    public void setCurrentAction(ActionTypes action) {
        serverActions.currentAction = (action.getAction() == ActionHandler.Actions.drawAction.getClass()) ? serverActions.drawAction : serverActions.rubberAction;
    }

    public void performAction(Pair<Double, Double> point) {
        serverActions.currentAction.performAction(point);
    }

    public ServerActionHandler(Canvas canvas) {
        serverActions = new ServerActions(canvas);
    }
}
