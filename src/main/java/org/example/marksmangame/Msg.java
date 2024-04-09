package org.example.marksmangame;

public class Msg {
    MsgAction action;
    Point[] targets;

    ArrowData[] arrows;
    PlayerInfo[] info;

    int new_player_id;

    public Msg(MsgAction action, Point[] targets, ArrowData[] arrows, PlayerInfo[] info) {
        this.action = action;
        this.targets = targets;
        this.arrows = arrows;
        this.info = info;
    }

    MsgAction get_action() {
        return action;
    }

    Point[] get_target() {
        return targets;
    }

    public ArrowData[] get_arrows() {
        return arrows;
    }

    public PlayerInfo[] get_info() {
        return info;
    }

    public void set_new_player_id(int id) {
        new_player_id = id;
    }

    public int get_new_player_id() {
        return new_player_id;
    }
}
