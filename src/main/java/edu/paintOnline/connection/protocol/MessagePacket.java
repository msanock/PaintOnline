package edu.paintOnline.connection.protocol;

import java.io.Serializable;

public record MessagePacket(ProtocolMessage.PureData[] messages) implements Serializable {
}
