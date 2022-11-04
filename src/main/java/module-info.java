module com.example.charades {
    requires javafx.controls;
    requires javafx.fxml;


    opens charades to javafx.fxml;
    exports charades;
    exports charades.sceneControllers;
    opens charades.sceneControllers to javafx.fxml;
}