package org.example.marksmangame;

public class ArrowData {
    Point coords;
    boolean active_flag;

    ArrowData(Point coords, boolean is_actve) {
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
