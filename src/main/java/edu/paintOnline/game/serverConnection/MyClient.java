package edu.paintOnline.game.serverConnection;

import edu.paintOnline.game.App;
import edu.paintOnline.game.painting.ActionParameters;
import edu.paintOnline.connection.dualConnectionStation.Client;
import edu.paintOnline.connection.gates.Gates;
import edu.paintOnline.connection.gates.ReceiveFilters;
import edu.paintOnline.connection.gates.ScenarioBuilder;
import edu.paintOnline.connection.protocol.MessageApplier;
import edu.paintOnline.connection.protocol.Protocol;
import edu.paintOnline.connection.protocol.ProtocolMessage;
import edu.paintOnline.connection.protocol.special.*;
import javafx.util.Pair;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MyClient implements MessageApplier {

    private final int roomId = 41;
    private final Gates gates = new Gates(new Client(), this);
    private static final int TICK_PER_SECOND = 200;
    private final App app;
    private int idOfMyPen;

    public MyClient(App app, int idOfMyPen) {
        this.app = app;
        this.idOfMyPen = idOfMyPen;
    }

    public void start() {
        initGates();
        long currentTime = System.currentTimeMillis();
        while (true) {
            long deltaTime = System.currentTimeMillis() - currentTime;
            currentTime += deltaTime;
            gates.tick(deltaTime);
            try {
                TimeUnit.MILLISECONDS.sleep(Math.round(1000. / TICK_PER_SECOND));
            } catch (InterruptedException ignore) {
                System.out.println(ignore);
            }
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


    public void moveMyPenToPoint(Pair<Double, Double> point) {
        var moveToPoint = new MoveToPointData(idOfMyPen, point);
        gates.sendWithoutCheck(Protocol.MOVE_TO_POINT, roomId, moveToPoint);
    }

    public void setTypeOfMyPiora(ActionParameters type) {
        var setType = new SetType(idOfMyPen, type);
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
                    applyPenActions(historyMessage.getProtocol(), historyMessage.getData());
                }
                var createPen = new CreatePenData(idOfMyPen);
                gates.sendWithoutCheck(Protocol.CREATE_PEN, roomId, createPen);
            }
            default:
                applyPenActions(protocol, data);
        }
        return true;
    }

    private void applyPenActions(Protocol protocol, Serializable data) {
        switch (protocol) {
            case CREATE_PEN: {
                var createPenData = (CreatePenData) data;
                app.createPen(createPenData.id);//nickname
                break;
            }
            case DELETE_PEN: {
                var deletePenData = (DeletePenData) data;
                app.deletePen(deletePenData.id);
                break;
            }
            case MOVE_TO_POINT: {
                var moveToPointData = (MoveToPointData) data;
                app.moveToPoint(moveToPointData.id, moveToPointData.point);
                break;
            }
            case SET_TYPE: {
                var setType = (SetType) data;
                app.setType(setType.id, setType.info);
                break;
            }
        }
    }
}