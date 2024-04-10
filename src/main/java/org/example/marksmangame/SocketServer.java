package org.example.marksmangame;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;

public class SocketServer {
    Gson gson = new Gson();
    Model model = BModel.get_model();
    Socket ss;
    InputStream is;
    OutputStream os;
    DataInputStream dis;
    DataOutputStream dos;

    public SocketServer(Socket ss) {
        this.ss = ss;

        try {
            os = ss.getOutputStream();
            dos = new DataOutputStream(os);
            is = ss.getInputStream();
            dis = new DataInputStream(is);
        }
        catch (IOException e) {
            System.out.println("Error SocketClient");
        }
    }

    public void listen_msg() {
        Thread thread = new Thread(this::run);
        thread.setDaemon(true);
        thread.start();
    }

    void run() {
        while (true) {
            SignalMsg msg = read_msg();
            if (msg.get_action() == MsgAction.SET_READY) {
                model.get_player(model.get_player_id(ss.getPort())).set_ready(msg.get_signal());
                if (!model.is_run_game() && model.check_ready()) {
                    model.run_game();
                }
                else if (model.is_run_game() && model.is_pause() && model.check_ready()) {
                    model.unpause_game();
                }
            }
            else if (msg.get_action() == MsgAction.SHOT) {
                Player player = model.get_player(model.get_player_id(ss.getPort()));
                if (!player.get_arrow().is_active()) {
                    player.info.increase_shots();
                    player.get_arrow().set_active(msg.get_signal());
                }
            }
            else if (msg.get_action() == MsgAction.PAUSE) {
                if (!model.is_pause()) {
                    model.pause_game();
                }
            }

        }
    }

    public AuthMsg read_auth_msg() {
        AuthMsg msg = null;
        try {
            String msg_str = dis.readUTF();
            msg = gson.fromJson(msg_str, AuthMsg.class);
        }
        catch (IOException e) {
            System.out.println("Error read");
        }
        return msg;
    }

    public void send_unready() {
        String str_msg = gson.toJson(new Msg(MsgAction.SET_UNREADY, null, null, null));
        try {
            dos.writeUTF(str_msg);
        } catch (IOException e) {
            System.out.println("Error send unready to player");
        }
    }

    public void send_auth_resp(AuthResponse resp) {
        String str_msg = gson.toJson(resp);
        try {
            dos.writeUTF(str_msg);
        } catch (IOException e) {
            System.out.println("Error send auth res to player");
        }
    }

    public void send_winner(PlayerInfo info) {
        PlayerInfo[] info_data = new PlayerInfo[] {info};
        String str_msg = gson.toJson(new Msg(MsgAction.WINNER, null, null, info_data));
        try {
            dos.writeUTF(str_msg);
        } catch (IOException e) {
            System.out.println("Error send winner");
        }
    }
    public void send_data() {
        ArrowData[] arrows = model.get_data_arrows();
        String str_msg = gson.toJson(new Msg(MsgAction.SEND_DATA, model.get_target_coords(), arrows, model.get_data_info()));
        try {
            dos.writeUTF(str_msg);
        } catch (IOException e) {
            System.out.println("Error send data to player");
        }
    }

    public void send_new_player(int id) {
        PlayerInfo[] info = model.get_players_info();
        Msg msg = new Msg(MsgAction.NEW_PLAYER, null, null, info);
        msg.set_new_player_id(id);
        String str_msg = gson.toJson(msg);
        try {
            dos.writeUTF(str_msg);
        } catch (IOException e) {
            System.out.println("Error send new player");
        }
    }

    public SignalMsg read_msg() {
        SignalMsg msg = null;
        try {
            String msg_str = dis.readUTF();
            msg = gson.fromJson(msg_str, SignalMsg.class);
        }
        catch (IOException e) {
            System.out.println("Error read");
        }
        return msg;
    }

    public void close() {
        try {
            System.out.println("Client disconnect - " + ss.getPort());
            ss.close();
        }
        catch (IOException e) {
            System.out.println("Error close connect");
        }
    }
}
