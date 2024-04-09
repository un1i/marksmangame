package org.example.marksmangame;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    Model model = BModel.get_model();
    Thread game_thread = null;
    int port = 5588;
    InetAddress ip = null;

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }

    void run() {
        model.set_server(this);
        ServerSocket ss;
        Socket cs;
        model.set_target(new ServerTarget[]{
                new ServerTarget(new Point(475, 270),
                        AppConfig.big_target_radius,
                        AppConfig.big_target_distance,
                        AppConfig.big_target_speed,
                        AppConfig.big_target_points_for_hit),
                new ServerTarget(new Point(675, 270),
                        AppConfig.small_target_radius,
                        AppConfig.small_target_distance,
                        AppConfig.small_target_speed,
                        AppConfig.small_target_points_for_hit),
        });

        try {
            ip = InetAddress.getLocalHost();
            ss = new ServerSocket(port, 0, ip);

            while (true) {
                cs = ss.accept();
                System.out.println("Client connect - " + cs.getPort());

                SocketServer server_socket = new SocketServer(cs);
                AuthMsg msg = server_socket.read_auth_msg();
                String result_text = "";
                boolean result = true;
                if (model.get_cnt_players() >= Model.get_max_players()) {
                    result_text = "Сервер заполнен!";
                    result = false;
                }
                else if (!model.is_unique_name(msg.get_name())) {
                    result_text = "Игрок с таким именем уже есть. Введите другое имя.";
                    result = false;
                }
                server_socket.send_auth_resp(new AuthResponse(result, result_text));

                if (result) {
                    model.increase_cnt_players();
                    int id = model.get_free_id();

                    Player player = new Player(server_socket, Model.arrows[id], id, msg.get_name());
                    model.set_player_id(cs.getPort(), id);
                    model.add_player(id, player);
                    model.send_new_player(id);
                    server_socket.listen_msg();
                }
                else {
                    server_socket.close();
                }
            }
        }
        catch (IOException ex) {
            System.out.println("Server startup error!");
        }
    }

    public void start_game() {
        model.set_is_run_game(true);
        model.clear_statistic();
        game_thread = new Thread(this::game);
        game_thread.setDaemon(true);
        game_thread.start();
    }

    void stop_game() {
        model.set_is_run_game(false);
        model.set_target_start_coords();
        model.send_unready_players();
        game_thread.interrupt();
        game_thread = null;
        model.send_data_to_players();
    }
    void game() {
        try {
            while (model.game_status) {
                model.move_arrows();
                model.move_targets();
                for (ServerTarget target : model.get_targets()) {
                    for (Player player: model.get_players()) {
                        if (player != null && player.get_arrow().is_active() && target.check_hit(player.get_arrow())) {
                            player.get_arrow().remove();
                            player.get_info().increase_score(target.get_points_for_hit());
                            if (player.get_info().get_score() >= AppConfig.points_for_win) {
                                model.send_winner(player.get_info());
                                stop_game();
                            }
                        }
                    }
                }
                model.send_data_to_players();

                Thread.sleep(50);
            }
        }
        catch (InterruptedException e) {
            ;
        }
    }
}
