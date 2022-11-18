package paint.packets;

import paint.painting.ActionTypes;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class ActionPacket extends Packet {
    ActionTypes type;

    ActionPacket(ActionTypes type){
        this.type = type;
    }

    void sendPacket(ObjectOutputStream outputStream){
        try {
            outputStream.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
