package edu.paintOnline.connection.protocol;

import java.io.Serializable;
import java.net.Socket;

/**
 * All data, that is sent via socket object stream, is stored by this class.
 */


public class ProtocolMessage {

    private Socket owner;

    private final PureData pureData;

    public ProtocolMessage(PureData pureData) {
        this.pureData = pureData;
    }


    public static class PureData implements Serializable {
        private final Protocol protocol;
        private final int roomId;
        private final Serializable data;
        private long timeStump;

        public PureData(Protocol protocol, int roomId, Serializable data){
            this.protocol = protocol;
            this.roomId = roomId;
            this.data = data;
        }

        public PureData(Protocol protocol, int roomId, Serializable data, long timeStump) {
            this.protocol = protocol;
            this.roomId = roomId;
            this.data = data;
            this.timeStump = timeStump;
        }

        public void setTimeStump(long timeStump) {
            this.timeStump = timeStump;
        }
    }

    public Socket getOwner() {
        return owner;
    }

    public void setOwner(Socket socket){
        this.owner = socket;
    }

    public ProtocolMessage(Protocol protocol, int roomId, Serializable data) {
        pureData = new PureData(protocol, roomId, data);
    }

    public Protocol getProtocol() {
        return pureData.protocol;
    }

    public Serializable getData() {
        return pureData.data;
    }

    public long getTimeStump() {
        return pureData.timeStump;
    }

    public int getRoomId() {
        return pureData.roomId;
    }

    public PureData toPureData() {
        pureData.setTimeStump(System.currentTimeMillis());
        return new PureData(pureData.protocol, pureData.roomId, pureData.data, pureData.timeStump);
    }
}
