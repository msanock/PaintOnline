package paintOnline.painting;

import java.io.Serializable;

public class ActionParameters implements Serializable {
    public ActionTypes type;
    public Colors color;
    public double size;


    public ActionParameters(ActionTypes type, double size, Colors color){
        this.type = type;
        this.size = size;
        this.color = color;
    }

}
