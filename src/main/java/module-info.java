module com.example.charades {
    requires javafx.controls;
    requires javafx.fxml;


    opens charades to javafx.fxml;
    exports charades;
    exports charades.scenes;
    opens charades.scenes to javafx.fxml;
    exports charades.scenes.components;
    opens charades.scenes.components to javafx.fxml;
}