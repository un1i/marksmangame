package org.example.marksmangame;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class PlayerInfoControlPanel extends GridPane {
    PlayerInfoVBox[] players;
    PlayerInfoControlPanel() {
        super();
        this.addColumn(0);
        for (int i = 0; i < AppConfig.max_players; i++) {
            this.getRowConstraints().add(new RowConstraints(AppConfig.playing_field_height / 4));
        }
        this.setAlignment(Pos.CENTER);
        players = new PlayerInfoVBox[AppConfig.max_players];
    }

    public void add_new_player(PlayerInfo info) {
        if (players[info.get_id()] == null) {
            players[info.get_id()] = new PlayerInfoVBox(info);
            this.add(players[info.get_id()], 0, info.get_id());
        }
    }

    public void update_data(PlayerInfo[] infos) {
        for (PlayerInfo info: infos) {
            int id = info.get_id();
            players[id].update_score_val(info.get_score());
            players[id].update_shots_val(info.get_shots());
        }
    }
}
