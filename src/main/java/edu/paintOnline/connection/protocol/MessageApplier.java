package edu.paintOnline.connection.protocol;

import edu.paintOnline.connection.gates.Gates;


public interface MessageApplier {
    boolean applyMessage(ProtocolMessage message);
}
