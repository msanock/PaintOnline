package paintOnline;

import paintOnline.painting.ActionTypes;
import paintOnline.painting.PerformServerDrawAction;
import paintOnline.serverConnection.MyClient;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class App extends Application {
    private static Pane root;
    public static MyClient myClient;
    public static Canvas canvas;

    public void connect() {
        new Thread(() -> {

            Random rand = new Random(System.currentTimeMillis());
            myClient = new MyClient(this, rand.nextInt()); // generowane dla kazdego clienta inne
            myClient.start();
        }).start();
    }

    public static void setCanvas(Canvas canvas){
        App.canvas = canvas;
    }

    public static void exit() {
        Platform.exit();
    }

    public static Pane getRoot() {
        return root;
    }

    public void getNickname() {
        Dialog<String> dialog = new TextInputDialog();
        dialog.setTitle("Dialog");
        dialog.setOnCloseRequest((e) -> exit());
        dialog.setResizable(false);

        Optional<String> result = dialog.showAndWait();
        String entered;

        if (result.isPresent()) {
            entered = result.get();
        }
        return;

//        ButtonType type = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
//        //Setting the content of the dialog
//        dialog.setContentText("This is a sample dialog");
//        //Adding buttons to the dialog pane
//        dialog.getDialogPane().getButtonTypes().add(type);
//        //Setting the label
//        dialog.showAndWait();
    }

    @Override
    public void start(Stage stage) throws IOException {
        //getNickname();
        connect();

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("scenes/Painter.fxml"));
        //root = new StackPane();
       // root.getChildren().add(fxmlLoader.load());
        root = fxmlLoader.load();
//        fxmlLoader = new FXMLLoader(App.class.getResource("scenes/Painter.fxml"))
//        root.getChildren().add
        Scene scene = new Scene(root);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setMinHeight(800);
        stage.setMinWidth(600);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    Map<Integer, PerformServerDrawAction> idToPioro;

    // FROM SERVER
    public void createPioro(int id) {
        idToPioro.put(id, new PerformServerDrawAction(canvas));
    }

    public void deletePioro(int id) {
        idToPioro.remove(id);
    }

    public void moveToPoint(int id, Pair<Double, Double> point) {
        idToPioro.get(id).performAction(point);
    }

    // TUTAJ musi byc jakis type ...
    public void setType(int id, ActionTypes type) {

    }

}