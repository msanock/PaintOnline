package connection.protocol.special;

import paint.painting.ActionTypes;

import java.io.Serializable;

public class SetType implements Serializable {
    public int id;
    public ActionTypes type;

    public SetType(int idOfMyPioro, ActionTypes type) {
        id = idOfMyPioro;
        this.type = type;
    }
}