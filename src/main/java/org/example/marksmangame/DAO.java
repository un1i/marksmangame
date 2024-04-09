package org.example.marksmangame;

import javafx.scene.shape.Circle;

public class DAO {
    private Point[] targets;
    private Arrow[] arrows;
    int score;

    public Arrow[] get_arrows() {
        return arrows;
    }

    public Point[] get_targets() {
        return targets;
    }

    public int get_score() {
        return score;
    }

    public void set_score(int score) {
        this.score = score;
    }

    public void setTargets(Point[] targets) {
        this.targets = targets;
    }
}
