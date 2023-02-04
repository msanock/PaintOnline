package edu.paintOnline.connection.protocol.special;

import edu.paintOnline.game.painting.ActionParameters;
import edu.paintOnline.game.painting.ActionTypes;
import edu.paintOnline.game.painting.Colors;

import java.io.Serializable;


public class SetType implements Serializable {
    public int id;
    public ActionParameters info;



    public SetType(int id, ActionParameters info) {
        this.id = id;
        this.info = info;
    }

    public SetType(int idOfMyPioro, ActionTypes type, double size, Colors color) {
        id = idOfMyPioro;
        this.info = new ActionParameters(type, size, color);
    }
}