module com.example.charades {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.datatransfer;


    opens paintOnline to javafx.fxml;
    exports paintOnline;
    exports paintOnline.scenes;
    opens paintOnline.scenes to javafx.fxml;
    exports paintOnline.scenes.components;
    opens paintOnline.scenes.components to javafx.fxml;
    opens connection;

}