package charades.painting;

import javafx.scene.input.MouseEvent;

public interface InteractionWithCanvasInterface {

    void handleOnMousePressed(MouseEvent e);

    void handleOnMouseDragged(MouseEvent e);

    void handleOnMouseReleased(MouseEvent e);

}
