package org.example.marksmangame;

import javafx.scene.shape.Polygon;
import javafx.scene.layout.Pane;

public class Arrow extends Polygon {
    private boolean activity_flag;
    private final int speed;
    private final Pane field;
    double length;

    Arrow(Pane arrow_field, int arrow_speed) {
        super(0.0, 0.0,
                -10.0, -5.0,
                -10.0, -2.0,
                -40.0, -2.0,
                -40.0, 2.0,
                -10.0, 2.0,
                -10.0, 5.0);
        length = 40.0;
        activity_flag = false;
        field = arrow_field;
        speed = arrow_speed;
    }

    public void set_active(boolean value) {
        activity_flag = value;
    }

    public void set_start_coords() {
        this.setLayoutY(field.getHeight() / 2);
        this.setLayoutX(0);
    }

    public boolean is_active() {
        return activity_flag;
    }

    public void remove() {
        field.getChildren().remove(this);
        this.set_active(false);
    }

    public void move() {
        this.setLayoutX(this.getLayoutX() + speed);
        if (this.getLayoutX() >= field.getWidth()) {
            remove();
        }
    }
}
