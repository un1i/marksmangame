package org.example.marksmangame.messages.MsgData;

public class ArrowData {
    Point coords;
    boolean active_flag;

    public ArrowData(Point coords, boolean is_actve) {
        this.coords = coords;
        this.active_flag = is_actve;
    }

    public Point get_coords() {
        return coords;
    }

    public boolean is_active() {
        return active_flag;
    }
}
