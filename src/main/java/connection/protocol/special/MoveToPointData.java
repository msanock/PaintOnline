package connection.protocol.special;

import javafx.util.Pair;

import java.io.Serializable;

public class MoveToPointData implements Serializable {
    public int id;
    public Pair<Double, Double> point;

    public MoveToPointData(int id, Pair<Double, Double> point) {
        this.id = id;
        this.point = point;
    }
}