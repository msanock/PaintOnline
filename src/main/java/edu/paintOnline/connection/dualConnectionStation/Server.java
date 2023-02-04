package edu.paintOnline.connection.dualConnectionStation;

import edu.paintOnline.connection.ConnectionSettings;
import edu.paintOnline.connection.dualConnectionStation.download.Reader;
import edu.paintOnline.connection.dualConnectionStation.download.SocketStreamReader;
import edu.paintOnline.connection.protocol.MessagePacket;
import edu.paintOnline.connection.protocol.ProtocolMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Clients connects to the Server, with usage of edu.paintOnline.connection settings.
 *
 * @see Client
 *
 */

public class Server extends BaseDualConnectionStation {
    private final LinkedList<Reader> connectedClients = new LinkedList<>();
    boolean isDisconnected = true;

    public Server(){ }

    public void start() {
        new Thread(() -> {
            isDisconnected = false;

            if (downlink == null) {
                Logger.getAnonymousLogger().info("No listener set. ");
                return;
            }
            try {
                ServerSocket serverSocket = new ServerSocket(ConnectionSettings.PORT);
                while (true) {
                    Logger.getAnonymousLogger().info("Wait until  new edu.paintOnline.connection");
                    Socket clientSocket = serverSocket.accept();
                    var objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                    var socketCompactData = new Reader(objectOutputStream, clientSocket);
                    synchronized (connectedClients) {
                        connectedClients.add(socketCompactData);
                    }
                    SocketStreamReader streamReader = new SocketStreamReader(clientSocket, downlink);
                    streamReader.start();
                    downlink.onNewSocketConnection(clientSocket);
                }
            } catch (Exception exception) {
                Logger.getAnonymousLogger().warning("Server exception: " + exception.getMessage());
            } finally {
                isDisconnected = true;
            }
        }).start();
    }

    @Override
    public void flush() {
        if (isDisconnected) {
            streamBuffer.clear();
            return;
        }
        synchronized (connectedClients) {
            connectedClients.removeIf(Reader::isClosed);
            connectedClients.forEach(
                    reader -> {
                        streamBuffer.forEach(
                                (socket, protocolMessages) -> {
                                    if (reader.socket() == socket) {
                                        sendAll(reader, protocolMessages);
                                    }
                                }
                        );
                    }
            );
        }
        streamBuffer.clear();
    }

    private void sendAll(Reader client, List<ProtocolMessage> messagesToClient) {

        try {
            ProtocolMessage.PureData[] messagesArray = new ProtocolMessage.PureData[messagesToClient.size()];
            for (int i = 0; i < messagesArray.length; i++) {
                messagesArray[i] = messagesToClient.get(i).toPureData();
                Logger.getAnonymousLogger()
                        .info("SENT : " + messagesToClient.get(i).getProtocol() +
                                ", for " + messagesToClient.get(i).getRoomId());

            }
            client.stream().writeObject(new MessagePacket(messagesArray));
        } catch (IOException ignore) {
        }
    }

    @Override
    public boolean isDisconnected() {
        return isDisconnected;
    }

    @Override
    public boolean isConnecting() {
        return false;
    }

    @Override
    public void changeIp() {

    }


    @Override
    public List<Socket> getReceivers() {
        synchronized (connectedClients) {
            return connectedClients.stream()
                    .map(Reader::socket)
                    .toList();
        }
    }
}
