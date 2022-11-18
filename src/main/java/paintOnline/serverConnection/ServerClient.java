package paintOnline.serverConnection;

import connection.ConnectionSettings;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerClient {
    ObjectOutputStream clientOutputStream;
    ObjectInputStream clientInputStream;

    public ServerClient(){
        try {
            Socket socketConnection = new Socket (ConnectionSettings.HOST, ConnectionSettings.PORT);
            clientOutputStream = new
                    ObjectOutputStream(socketConnection.getOutputStream());
            clientInputStream = new
                    ObjectInputStream(socketConnection.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
