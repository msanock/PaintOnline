package connection.gates;

import connection.protocol.Protocol;

import java.util.function.Function;

public class ReceiveFilters {
    public static Function<Protocol, Boolean> noFilter() {
        return (protocol -> false);
    }
    public static Function<Protocol, Boolean> catchOnlyAllInfo() {
        return protocol -> protocol != Protocol.ALL_INFO;
    }
}
