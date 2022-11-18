package connection.gates;

import connection.protocol.Protocol;

import java.util.List;
import java.util.function.Function;

/*
 * This is a list of filters that will be activated in order. The last Filter will be applied
 *  until new scenario is set
 */

public class ReceiveScenario {
    private final List<Function<Protocol, Boolean>> filterSequence;
    private Runnable onStart;
    private Runnable onEnd;

    ReceiveScenario(List<Function<Protocol, Boolean>> filterSequence, Runnable onStart, Runnable onEnd){
        this.filterSequence = filterSequence;
        this.onStart = onStart;
        this.onEnd = onEnd;
    }

    public void pop() {
        if (onStart != null) {
            onStart.run();
            onStart = null;
        }
        if (filterSequence.size() != 1) {
            filterSequence.remove(0);

        } else {
            if (onEnd != null) {
                onEnd.run();
                onEnd = null;
            }
        }
    }

    public Function<Protocol, Boolean> get() {
        return filterSequence.get(0);
    }
}
