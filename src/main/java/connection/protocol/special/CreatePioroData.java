package connection.protocol.special;


import java.io.Serializable;

public class CreatePioroData implements Serializable {
    public int id;

    public CreatePioroData(int id) {
        this.id = id;
    }
}
