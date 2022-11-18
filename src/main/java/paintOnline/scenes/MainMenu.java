package paintOnline.scenes;

import paintOnline.App;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class MainMenu {

    @FXML
    public BorderPane mainMenuPane;



    public void handleExitButtonAction(ActionEvent e){
        App.exit();
    }

    public void handleEntered(MouseEvent e){
        ScaleTransition btnTrans=new ScaleTransition(Duration.millis(100), (Node) e.getSource());
        btnTrans.setToX(1.2);
        btnTrans.setToY(1.2);
        btnTrans.play();
    }

    public void handleExited(MouseEvent e){
        ScaleTransition btnTrans=new ScaleTransition(Duration.millis(100), (Node) e.getSource());
        btnTrans.setToX(1);
        btnTrans.setToY(1);
        btnTrans.play();
    }

}
