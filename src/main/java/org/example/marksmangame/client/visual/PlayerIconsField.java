package org.example.marksmangame.client.visual;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import org.example.marksmangame.AppConfig;

public class PlayerIconsField extends GridPane {
    PlayerIcon[] icons;
    public PlayerIconsField() {
        super();
        this.addColumn(0);
        for (int i = 0; i < AppConfig.max_players; i++) {
            this.getRowConstraints().add(new RowConstraints(AppConfig.playing_field_height / 4));
        }
        this.setAlignment(Pos.CENTER);
        icons = new PlayerIcon[AppConfig.max_players];
    }

    public void add_new_icon(int id, boolean is_main) {
        if (icons[id] == null){
            icons[id] = new PlayerIcon(is_main);
            this.add(icons[id], 0, id);
        }
    }
}
