module edu.paintOnline {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.datatransfer;


    opens edu.paintOnline.game to javafx.fxml;
    exports edu.paintOnline.game;
    exports edu.paintOnline.game.scenes;
    opens edu.paintOnline.game.scenes to javafx.fxml;
    exports edu.paintOnline.game.scenes.components;
    opens edu.paintOnline.game.scenes.components to javafx.fxml;
    opens edu.paintOnline.connection;

}