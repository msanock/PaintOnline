package connection.dualConnectionStation.download;

import connection.protocol.MessagePacket;
import connection.protocol.ProtocolMessage;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Class in thread read stream and return object to the event listener
 *
 * @see Downlink
 */

public class SocketStreamReader extends Thread {
    private final Socket socket;
    private final Downlink downlink;
    private boolean isClosed = true;

    public SocketStreamReader(Socket socket, Downlink downlink) {
        this.socket = socket;
        this.downlink = downlink;
    }

    public boolean isClosed() {
        return isClosed;
    }

    @Override
    public void run() {
        try {
            isClosed = false;
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            while (true) {
                var messages = ((MessagePacket) objectInputStream.readObject()).messages();
                if (messages == null) {
                    break;
                }
                for (int i = 0; i < messages.length; i++) {
                    var message = new ProtocolMessage(messages[i]);
                    message.setOwner(socket);
                    downlink.onReceiveMessage(message);
                }
            }
        } catch (Exception exception) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            isClosed = true;
            downlink.onLostSocketConnection(socket);
           // exception.printStackTrace();
        }
    }
}
