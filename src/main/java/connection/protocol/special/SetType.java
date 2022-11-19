package connection.protocol.special;

import paintOnline.painting.ActionParameters;
import paintOnline.painting.ActionTypes;
import paintOnline.painting.Colors;

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