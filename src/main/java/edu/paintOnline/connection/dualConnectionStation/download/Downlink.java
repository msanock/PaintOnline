package edu.paintOnline.connection.dualConnectionStation.download;

import edu.paintOnline.connection.protocol.ProtocolMessage;

import java.net.Socket;

/**
 * Used to define object that can listen
 */
public interface Downlink {
    void onReceiveMessage(ProtocolMessage message);

    void onNewSocketConnection(Socket socket);

    void onLostSocketConnection(Socket socket);
}
