package charades.painting;

import javafx.scene.input.MouseEvent;

public interface PainterAction {

    void handleOnMousePressed(MouseEvent e);

    void handleOnMouseDragged(MouseEvent e);

    void handleOnMouseReleased(MouseEvent e);

    void stopAction();

    void sendAction();

}
