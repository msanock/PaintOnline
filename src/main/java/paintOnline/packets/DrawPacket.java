package paint.packets;

import paint.painting.ActionTypes;
import paint.painting.Colors;

public class DrawPacket extends ActionPacket {
    final static ActionTypes type = ActionTypes.drawAction;
    Colors color;
    double strokeSize;

    public DrawPacket(Colors color, double strokeSize) {
        super(type);
        this.color = color;
        this.strokeSize = strokeSize;
    }


}
