module com.example.charades {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.charades to javafx.fxml;
    exports com.example.charades;
}