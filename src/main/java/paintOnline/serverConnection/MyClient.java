package paintOnline.serverConnection;

import paintOnline.App;
import paintOnline.painting.ActionTypes;
import connection.dualConnectionStation.Client;
import connection.gates.Gates;
import connection.gates.ReceiveFilters;
import connection.gates.ScenarioBuilder;
import connection.protocol.MessageApplier;
import connection.protocol.Protocol;
import connection.protocol.ProtocolMessage;
import connection.protocol.special.*;
import javafx.util.Pair;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MyClient implements MessageApplier {

    private final int roomId = 42;
    private final Gates gates = new Gates(new Client(), this);
    private static final int TICK_PER_SECOND = 200;
    private final App app;
    private int idOfMyPioro;

    public MyClient(App app, int idOfMyPioro) {
        this.app = app;
        this.idOfMyPioro = idOfMyPioro;
    }

    public void start() {
        initGates();
        // to jest zwykla petla, ty musisz tylko ->
        long currentTime = System.currentTimeMillis();
        try {
            while (true) {
                long deltaTime = System.currentTimeMillis() - currentTime;
                currentTime += deltaTime;
                gates.tick(deltaTime); // <- wkleic ta linijke w petle i podac czas ktory minal od ostatniego ticka
                try {
                    TimeUnit.MILLISECONDS.sleep(Math.round(1000. / TICK_PER_SECOND));
                } catch (InterruptedException ignore) {
                }
            }
        } catch (Exception e) {
        }
    }

    private void initGates() {
        gates.setOnConnectEvent(() -> {
            gates.sendWithoutCheck(Protocol.SUBSCRIBE_FOR, roomId, new SubscribeRequest(List.of(roomId)));
        });

        ScenarioBuilder connectedScenario = new ScenarioBuilder().add(ReceiveFilters.catchOnlyAllInfo());
        gates.setReceiveScenario(connectedScenario.build(ReceiveFilters.noFilter()));
        gates.setOnGatesLostSocketEvent((ignored) -> {
            gates.setReceiveScenario(connectedScenario.build(ReceiveFilters.noFilter()));
            gates.connect();
        });
        gates.connect();
    }


    public void moveMyPioroToPoint(Pair<Double, Double> point) {
        var moveToPoint = new MoveToPointData(idOfMyPioro, point);
        gates.sendWithoutCheck(Protocol.MOVE_TO_POINT, roomId, moveToPoint);
    }

    public void setTypeOfMyPiora(ActionTypes type) {
        var setType = new SetType(idOfMyPioro, type);

        gates.sendWithoutCheck(Protocol.SET_TYPE, roomId, setType);
    }


    @Override
    public boolean applyMessage(ProtocolMessage message) {
        Protocol protocol = message.getProtocol();
        Serializable data = message.getData();
        switch (protocol) {
            case ALL_INFO: {
                var history = (LinkedList<ProtocolMessage.PureData>) data;
                for (ProtocolMessage.PureData pureData : history) {
                    var historyMessage = new ProtocolMessage(pureData);
                    applyPioroActions(historyMessage.getProtocol(), historyMessage.getData());
                }
                var createPioro = new CreatePioroData(idOfMyPioro);
                gates.sendWithoutCheck(Protocol.CREATE_PIORO, roomId, createPioro);
            }
            default:
                applyPioroActions(protocol, data);
        }
        return true;
    }

    private void applyPioroActions(Protocol protocol, Serializable data) {
        switch (protocol) {
            case CREATE_PIORO: {
                var createPioroData = (CreatePioroData) data;
                app.createPioro(createPioroData.id);
                break;
            }
            case DELETE_PIORO: {
                var deletePioroData = (DeletePioroData) data;
                app.deletePioro(deletePioroData.id);
                break;
            }
            case MOVE_TO_POINT: {
                var moveToPointData = (MoveToPointData) data;
                app.moveToPoint(moveToPointData.id, moveToPointData.point);
                break;
            }
            case SET_TYPE: {
                var setType = (SetType) data;
                app.setType(setType.id, setType.type);
                break;
            }
        }
    }
}