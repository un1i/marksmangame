package org.example.marksmangame.client;

public class PlayerInfo {
    int shots;
    int score;
    int id;

    String name;

    PlayerInfo(int id, String name) {
        shots = 0;
        score = 0;
        this.id = id;
        this.name = name;
    }

    public int get_score() {
        return score;
    }

    public int get_shots() {
        return shots;
    }

    public void increase_shots() {
        shots++;
    }

    public void increase_score(int points) {
        score += points;
    }

    public void clear_statistic() {
        shots = 0;
        score = 0;
    }

    public String get_name() {
        return name;
    }

    public int get_id() {
        return id;
    }
}
