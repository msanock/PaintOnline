package edu.paintOnline.game.painting;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public abstract class ServerAction implements ViewerInterface{
    final GraphicsContext gc;
    final Canvas canvas;

    public void reset(){

    }

    public ServerAction(Canvas canvas){
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
    }
}
