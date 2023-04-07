package edu.paintOnline.game;

import edu.paintOnline.game.painting.ActionParameters;
import edu.paintOnline.game.painting.ServerActionHandler;
import edu.paintOnline.game.serverConnection.MyClient;
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
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class App extends Application {
    private static Pane root;
    public static MyClient myClient;
    public static Canvas canvas;

    ConcurrentMap<Integer, ServerActionHandler> idToPen = new ConcurrentHashMap<>();

    public void connect() {
        Thread connection = new Thread(() -> {
            Random rand = new Random(System.currentTimeMillis());
            myClient = new MyClient(this, rand.nextInt());
            myClient.start();
        });
        connection.setDaemon(true);
        connection.start();
    }

    public static void setCanvas(Canvas canvas) {
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
    }

    @Override
    public void start(Stage stage) throws IOException {
        //getNickname();

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("scenes/Painter.fxml"));
        root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setMinHeight(800);
        stage.setMinWidth(600);
        stage.show();
        connect();
    }

    public static void main(String[] args) {
        launch();
    }

    // FROM SERVER
    public void createPen(int id) {
        idToPen.put(id, new ServerActionHandler(canvas));
        System.out.println("CREATE PEN " + id);
    }

    public void deletePen(int id) {
        System.out.println("DELETE PEN " + id);
        idToPen.remove(id);
    }

    public void moveToPoint(int id, Pair<Double, Double> point) {
        System.out.println("MOVE PEN " + id);
        idToPen.get(id).performAction(point);
    }


    public void setType(int id, ActionParameters type) {
        System.out.println("SET PEN " + id);
        idToPen.get(id).reset();
        idToPen.get(id).setCurrentAction(type);

    }

}