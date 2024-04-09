package org.example.marksmangame;

import java.util.HashMap;
import java.util.Objects;

public class Model {
    DAO dao = new DAO();

    Server server = null;

    public static final ServerArrow[] arrows = {
            new ServerArrow(new Point(0, 100)),
            new ServerArrow(new Point(0, 200)),
            new ServerArrow(new Point(0, 300)),
            new ServerArrow(new Point(0, 400))
    };
    ServerTarget[] targets;
    static final int max_players = AppConfig.max_players;

    boolean game_status = false;

    int cnt_players = 0;

    HashMap<Integer, Integer> player_id_by_port = new HashMap<>();
    Player[] players = new Player[max_players];

    void add_player(int id, Player player) {
        players[id] = player;
    }

    boolean check_ready() {
        for (Player pl: players) {
            if (pl != null && !pl.is_ready()) {
                return false;
            }
        }
        return true;
    }

    void move_arrows() {
        for (Player player: players) {
            if (player != null && player.arrow.is_active()) {
                player.get_arrow().move();
            }
        }
    }

    void move_targets() {
        for (ServerTarget target : targets) {
            target.move();
        }
    }

    public void set_target_start_coords() {
        for (ServerTarget target : targets) {
            target.set_start_coords();
        }
    }

    public void clear_statistic() {
        for (Player player: players) {
            if (player != null) {
                player.get_info().clear_statistic();
            }
        }
    }

    public void send_unready_players() {
        for (Player player: players) {
            if (player != null) {
                player.set_ready(false);
                player.get_socket().send_unready();
            }
        }
    }

    void increase_score(int val) {
        dao.set_score(dao.get_score() + val);
    }

    public void send_winner(PlayerInfo info) {
        for (Player player: players) {
            if (player != null) {
                player.get_socket().send_winner(info);
            }
        }
    }

    void send_data_to_players() {
        for (Player player: players) {
            if (player != null) {
                player.get_socket().send_data();
            }
        }
    }

    void send_new_player(int id) {
        for (Player player: players) {
            if (player != null) {
                player.get_socket().send_new_player(id);
            }
        }
    }

    public PlayerInfo[] get_players_info() {
        PlayerInfo[] res = new PlayerInfo[cnt_players];
        int cur_ind = 0;
        for (Player player: players) {
            if (player != null) {
                PlayerInfo info = player.get_info();
                res[cur_ind] = info;
                cur_ind++;
            }
        }
        return res;
    }

    public Point[] get_target_coords() {
        Point[] res = new Point[2];
        for (int i = 0; i < 2; i++) {
            res[i] = targets[i].get_coords();
        }
        return res;
    }

    public ArrowData[] get_data_arrows() {
        ArrowData[] res = new ArrowData[cnt_players];
        int cur_ind = 0;
        for (Player player: players) {
            if (player != null) {
                ServerArrow arrow = player.get_arrow();
                res[cur_ind] = new ArrowData(arrow.get_coords(), arrow.is_active());
                cur_ind++;
            }
        }
        return res;
    }

    public PlayerInfo[] get_data_info() {
        PlayerInfo[] res = new PlayerInfo[cnt_players];
        int cur_ind = 0;
        for (Player player: players) {
            if (player != null) {
                res[cur_ind] = player.get_info();
                cur_ind++;
            }
        }
        return res;
    }

    public boolean is_unique_name(String name) {
        for (Player player : players) {
            if (player != null && Objects.equals(player.get_info().get_name(), name)) {
                return false;
            }
        }
        return true;
    }

    public void run_game() {
        server.start_game();
    }

    public ServerTarget[] get_targets() {
        return targets;
    }

    public void set_server(Server server) {
        this.server = server;
    }

    public void set_target(ServerTarget[] target) {
        this.targets = target;
    }

    public Player get_player(int id) {
        return players[id];
    }

    public Player[] get_players() {
        return players;
    }

    public void set_player_id(int port, int id) {
        player_id_by_port.put(port, id);
    }

    public int get_player_id(int port) {
        return player_id_by_port.get(port);
    }

    public int get_cnt_players() {
        return cnt_players;
    }

    public void increase_cnt_players() {
        cnt_players++;
    }

    public void decrease_cnt_players() {
        cnt_players--;
    }

    public static int get_max_players() {
        return max_players;
    }

    public void set_is_run_game(boolean val) {
        game_status = val;
    }

    public boolean is_run_game() {
        return game_status;
    }

    public int get_free_id() {
        for (int i = 0; i < max_players; i++) {
            if (players[i] == null) {
                return i;
            }
        }
        return -1;
    }
}
