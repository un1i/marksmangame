package org.example.marksmangame.client;

import org.example.marksmangame.server.ServerArrow;
import org.example.marksmangame.server.SocketServer;

public class Player {
    SocketServer socket;
    boolean ready_status;
    PlayerInfo info;
    ServerArrow arrow;

    public Player(SocketServer socket, ServerArrow arrow, int id, String name) {
        this.socket = socket;
        ready_status = false;
        this.arrow = arrow;
        info = new PlayerInfo(id, name);
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
