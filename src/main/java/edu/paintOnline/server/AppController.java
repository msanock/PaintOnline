package edu.paintOnline.server;

import edu.paintOnline.connection.dualConnectionStation.Server;
import edu.paintOnline.connection.gates.Gates;
import edu.paintOnline.connection.gates.SendFilters;
import edu.paintOnline.connection.protocol.MessageApplier;
import edu.paintOnline.connection.protocol.Protocol;
import edu.paintOnline.connection.protocol.ProtocolMessage;
import edu.paintOnline.connection.protocol.special.CreatePioroData;
import edu.paintOnline.connection.protocol.special.DeletePioroData;
import edu.paintOnline.connection.protocol.special.SubscribeRequest;
import javafx.util.Pair;

import java.io.Serializable;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AppController implements MessageApplier {
    private final Gates gates = new Gates(new Server(), this);
    private final int TICK_PER_SECOND = 150;

    public AppController() {
    }

    public void start() {
        gates.connect();
        gates.setOnGatesLostSocketEvent(socket -> {
            var idOfPioro = socketToPioroIdAndRoom.get(socket).getKey();
            var idOfRoom = socketToPioroIdAndRoom.get(socket).getValue();
            applyMessage(new ProtocolMessage(Protocol.DELETE_PIORO, idOfRoom, new DeletePioroData(idOfPioro)));
        });
        long currentTime = System.currentTimeMillis();
        try {
            while (true) {
                long deltaTime = System.currentTimeMillis() - currentTime;
                currentTime += deltaTime;
                gates.tick(deltaTime);
                try {
                    TimeUnit.MILLISECONDS.sleep(Math.round(1000. / TICK_PER_SECOND));
                } catch (InterruptedException ignore) {
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    Map<Integer, LinkedList<ProtocolMessage.PureData>> history = new HashMap<>();
    Map<Socket, Pair<Integer, Integer>> socketToPioroIdAndRoom = new HashMap<>();

    @Override
    public boolean applyMessage(ProtocolMessage message) {
        Protocol protocol = message.getProtocol();
        Serializable data = message.getData();
        switch (protocol) {
            case SUBSCRIBE_FOR -> {
                gates.setSendFilter(message.getOwner(), SendFilters.sendOnlyIfSubscribed((SubscribeRequest) data));

                if (history.containsKey(message.getRoomId())) {
                    gates.sendWithoutCheck(
                            Protocol.ALL_INFO,
                            ((SubscribeRequest) data).roomsToSubscribeFor().get(0),
                            history.get(message.getRoomId())
                    );
                } else {
                    gates.sendWithoutCheck(
                            Protocol.ALL_INFO,
                            ((SubscribeRequest) data).roomsToSubscribeFor().get(0),
                            new LinkedList<ProtocolMessage.PureData>()
                    );
                }
            }

            case CREATE_PIORO, MOVE_TO_POINT, SET_TYPE, DELETE_PIORO -> {
                if (protocol == Protocol.CREATE_PIORO) {
                    socketToPioroIdAndRoom.put(
                            message.getOwner(),
                            new Pair<>((
                                    (CreatePioroData) data).id
                                    , message.getRoomId()
                            )
                    );
                }
                if (!history.containsKey(message.getRoomId())) {
                    var list = new LinkedList<ProtocolMessage.PureData>();
                    list.add(message.toPureData());
                    history.put(message.getRoomId(), list);
                } else {
                    history.get(message.getRoomId()).add(message.toPureData());
                }
                gates.sendWithoutCheck(protocol, message.getRoomId(), data);
            }
        }
        return true;
    }
}
