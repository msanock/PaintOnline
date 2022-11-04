module com.example.charades {
    requires javafx.controls;
    requires javafx.fxml;


    opens charades to javafx.fxml;
    exports charades;
    exports charades.scenesControlers;
    opens charades.scenesControlers to javafx.fxml;
}