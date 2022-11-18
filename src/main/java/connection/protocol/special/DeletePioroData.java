package connection.protocol.special;

import java.io.Serializable;

public class DeletePioroData implements Serializable {
    public int id;

    public DeletePioroData(int id) {
        this.id = id;
    }

}