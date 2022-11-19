package connection.gates;


import connection.dualConnectionStation.BaseDualConnectionStation;
import connection.dualConnectionStation.download.Downlink;
import connection.protocol.MessageApplier;
import connection.protocol.Protocol;
import connection.protocol.ProtocolMessage;

import javafx.util.Pair;
import paintOnline.Timer;

import java.io.Serializable;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Logger;

/**
 * Reads stream from Client or Server, as SocketEventListner
 * Controls Server or Client by Backhaul interface and send messages
 *
 * @see Uplink
 * @see Downlink
 */
public class Gates implements Downlink {
    private final BaseDualConnectionStation uplink;
    private final MessageApplier listener;
    private final LinkedList<ProtocolMessage> mesages = new LinkedList<>();

    // EVENTS
    Runnable onConnectEvent;

    Consumer<Socket> onGatesLostSocketEvent;

    Runnable spamEvent;
    Timer spamTimer;

    // Filter Scenario
    ReceiveScenario receiveScenario = new ScenarioBuilder().build(ReceiveFilters.noFilter());
    Map<Socket, Function<ProtocolMessage, Boolean>> sendFilters = new HashMap<>();

    Function<Protocol, Boolean> filter;

    public Gates(BaseDualConnectionStation uplink, MessageApplier listener) {
        this.uplink = uplink;
        this.listener = listener;
        this.uplink.setDownlink(this);
    }

    public void setOnConnectEvent(Runnable onConnectEvent){
        this.onConnectEvent = onConnectEvent;
    }

    public void connect() {
        uplink.start();
    }

    public void tick(double deltaTime) {
        if (uplink.isDisconnected()) {
            return;
        }
        if (spamTimer != null) {
            spamTimer.tick(deltaTime);
            if (spamTimer.isReady()) {
                spamEvent.run();
                spamTimer.restart();
            }
        }

        synchronized (mesages) {
            mesages.removeIf(listener::applyMessage);
        }
        uplink.flush();
    }

    @Override
    public void onReceiveMessage(ProtocolMessage message) {
        if (isFiltered(message.getProtocol())) {
            return;
        }
        synchronized (mesages) {
            mesages.add(message);
        }
    }

    private boolean isFiltered(Protocol protocol) {
        filter = receiveScenario.get();
        if ((!filter.apply(protocol))) {
            receiveScenario.pop();
        }
        return filter.apply(protocol);
    }

    @Override
    public void onNewSocketConnection(Socket socket) {
        //       Logger.getAnonymousLogger().info("Uplink created . . .");
        if (onConnectEvent != null) {
            onConnectEvent.run();
        }
    }

    @Override
    public void onLostSocketConnection(Socket socket) {
        sendFilters.remove(socket);
        if (onGatesLostSocketEvent != null) {
            onGatesLostSocketEvent.accept(socket);
        }
    }

    private void send(ProtocolMessage message) throws NobodyReceivedMessageException {
        var ref = new Object() {
            Boolean wasSent = false;
        };
        uplink.getReceivers().forEach(socket -> {
            if (sendFilters.containsKey(socket)) {
                if (sendFilters.get(socket).apply(message)) {
                    return;
                }
            }
            uplink.send(socket, message);
            ref.wasSent = true;
        });
        if (!ref.wasSent) {
            throw new NobodyReceivedMessageException();
        }
    }

    public void send(Protocol protocol, int roomId, Serializable data) throws NobodyReceivedMessageException {
        send(new ProtocolMessage(protocol, roomId, data));
    }

    public void setOnGatesLostSocketEvent(Consumer<Socket> onGatesLostSocketEvent) {
        this.onGatesLostSocketEvent = onGatesLostSocketEvent;
    }

    public void setReceiveScenario(ReceiveScenario receiveScenario) {
        this.receiveScenario = receiveScenario;
    }

    public void setSpamEvent(Runnable spamEvent, double timeToWaitBetweenSpams) {
        this.spamEvent = spamEvent;
        spamTimer = new Timer(timeToWaitBetweenSpams);
    }

    public void setSendFilter(Socket filtered, Function<ProtocolMessage, Boolean> sendOnlyIfSubscribed) {
        sendFilters.put(filtered, sendOnlyIfSubscribed);
    }

    public void sendWithoutCheck(Protocol protocol, int roomId, Serializable data) {
        try {
            send(protocol, roomId, data);
        } catch (NobodyReceivedMessageException e) {
            throw new RuntimeException("Nobody received message, please use usual send " + protocol);
        }
    }

    public void sendWithoutCheckMultiple(Protocol protocol, List<Pair<Integer, Serializable>> dataList) {
        dataList.forEach(data -> {
            try {
                send(protocol, data.getKey(), data.getValue());
            } catch (NobodyReceivedMessageException e) {
                Logger.getAnonymousLogger().info(e.getMessage());
                e.printStackTrace();
            }
        });
    }

    public void changeServer() {
        uplink.changeIp();
    }

    public static class NobodyReceivedMessageException extends Throwable {
    }
}
