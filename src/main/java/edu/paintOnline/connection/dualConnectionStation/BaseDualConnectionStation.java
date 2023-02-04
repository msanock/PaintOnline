package edu.paintOnline.connection.dualConnectionStation;

import edu.paintOnline.connection.dualConnectionStation.download.Downlink;
import edu.paintOnline.connection.dualConnectionStation.upload.Uplink;
import edu.paintOnline.connection.protocol.ProtocolMessage;


import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Base station is a class that can get write information to Downlink
 * and read information from Uplink
 *
 * @see Uplink
 * @see Downlink
 */
public abstract class BaseDualConnectionStation implements Uplink {
    protected Downlink downlink;

    public abstract void start();

    public abstract void flush();

    public abstract boolean isDisconnected();
    public abstract boolean isConnecting();

    public void send(Socket receiver, ProtocolMessage message) {
        if(isDisconnected()){
            streamBuffer.clear();
            return;
        }
        synchronized (streamBuffer) {
            var bufferedMessages = streamBuffer.get(receiver);
            if (bufferedMessages == null) {
                streamBuffer.put(receiver, new LinkedList<>());
                bufferedMessages = streamBuffer.get(receiver);
            }
            bufferedMessages.add(message);
        }
    }

    public void setDownlink(Downlink downlink) {
        this.downlink = downlink;
    }

    Map<Socket, List<ProtocolMessage>> streamBuffer = new HashMap<>();

    public abstract void changeIp() ;
}
