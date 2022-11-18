module com.example.charades {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.datatransfer;


    opens paint to javafx.fxml;
    exports paint;
    exports paint.scenes;
    opens paint.scenes to javafx.fxml;
    exports paint.scenes.components;
    opens paint.scenes.components to javafx.fxml;
    opens connection;

}