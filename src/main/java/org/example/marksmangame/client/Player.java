package org.example.marksmangame.client;

import org.example.marksmangame.server.PlayerStatistic;
import org.example.marksmangame.server.ServerArrow;
import org.example.marksmangame.server.SocketServer;

public class Player {
    SocketServer socket;
    boolean ready_status;
    PlayerInfo info;
    ServerArrow arrow;

    public Player(SocketServer socket, ServerArrow arrow, int id, String name, PlayerStatistic stat) {
        this.socket = socket;
        ready_status = false;
        this.arrow = arrow;
        info = new PlayerInfo(id, name, stat);
    }

    public void increase_num_wins() {
        info.increase_num_wins();
    }

    public PlayerStatistic get_stat() {
        return info.get_stat();
    }

    public boolean is_ready() {
        return ready_status;
    }

    public void set_ready(boolean value) {
        ready_status = value;
    }

    public ServerArrow get_arrow() {
        return arrow;
    }

    public SocketServer get_socket() {
        return socket;
    }

    public PlayerInfo get_info() {
        return info;
    }
}
