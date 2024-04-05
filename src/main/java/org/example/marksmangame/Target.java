package org.example.marksmangame;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class Target {
    private final Circle circle;
    private boolean is_upper;
    private final int speed;
    private int points;

    private Pane field;

    private double x_distance;

    Target(Circle shape, Pane target_field, double distance_from_edge, int target_speed, int points_for_hit) {
        circle = shape;
        field = target_field;
        is_upper = true;
        speed = target_speed;
        points = points_for_hit;
        x_distance = distance_from_edge;
    }

    public void move() {
        if (is_upper) {
            if (circle.getLayoutY() - circle.getRadius() - speed > 0) {
                circle.setLayoutY(circle.getLayoutY() - speed);
            }
            else {
                is_upper = false;
                circle.setLayoutY(circle.getLayoutY() + speed);
            }
        }
        else {
            if (circle.getLayoutY() + circle.getRadius() + speed <= field.getHeight()) {
                circle.setLayoutY(circle.getLayoutY() + speed);
            }
            else {
                is_upper = true;
                circle.setLayoutY(circle.getLayoutY() - speed);
            }
        }
    }

    public boolean check_hit(Arrow arrow) {
        double x = Math.pow(circle.getLayoutX() - arrow.getLayoutX(), 2);
        double y = Math.pow(circle.getLayoutY() - arrow.getLayoutY(), 2);
        double r = Math.pow(circle.getRadius(), 2);
        return x + y <= r;
    }

    public int get_points_for_hit() {
        return points;
    }

    public void set_start_coords() {
        is_upper = true;
        circle.setLayoutX(x_distance);
        circle.setLayoutY(field.getHeight() / 2);
    }
}