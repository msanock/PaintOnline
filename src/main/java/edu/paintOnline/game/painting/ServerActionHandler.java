package edu.paintOnline.game.painting;

import javafx.scene.canvas.Canvas;
import javafx.util.Pair;

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

    public void setCurrentAction(ActionParameters action) {
        if (action.type.getAction() == ActionHandler.Actions.drawAction.getClass()) {
            serverActions.currentAction = serverActions.drawAction;
            serverActions.drawAction.setParameters(action.size, action.color);
        }
        else {
            serverActions.currentAction = serverActions.rubberAction;
            serverActions.rubberAction.setParameters(action.size);
        }
    }

    public void performAction(Pair<Double, Double> point) {
        serverActions.currentAction.performAction(point);
    }

    public ServerActionHandler(Canvas canvas) {
        serverActions = new ServerActions(canvas);
    }
}
