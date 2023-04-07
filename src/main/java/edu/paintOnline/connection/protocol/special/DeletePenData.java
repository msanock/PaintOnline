package edu.paintOnline.connection.protocol.special;

import java.io.Serializable;

public class DeletePenData implements Serializable {
    public int id;

    public DeletePenData(int id) {
        this.id = id;
    }

}