package connection.gates;

import connection.protocol.ProtocolMessage;
import connection.protocol.special.SubscribeRequest;

import java.util.function.Function;

public class SendFilters {
    public static Function<ProtocolMessage, Boolean> sendOnlyIfSubscribed(SubscribeRequest request) {
        return protocolMessage -> {
            if (protocolMessage.getRoomId() == -1) {
                return false;
            }
            return !request.roomsToSubscribeFor().contains(protocolMessage.getRoomId());
        };
    }

    public static Function<ProtocolMessage, Boolean> sendEveryMessage() {
        return (ignored) -> false;
    }
}

