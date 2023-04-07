package edu.paintOnline.connection.protocol.special;


import java.io.Serializable;

public class CreatePenData implements Serializable {
    public int id;

    public CreatePenData(int id) {
        this.id = id;
    }
}
