package org.example.marksmangame;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class MainController {
    @FXML
    public VBox RightVBox;
    @FXML
    public VBox LeftVBox;
    public BorderPane main;
    @FXML
    public Button ready_button;
    @FXML
    private Pane playing_field;
    @FXML
    private Circle big_target;
    @FXML
    private Circle small_target;
    @FXML
    private Polygon player;
    @FXML
    private Label score_cnt_label;
    @FXML
    private Label shoot_cnt_label;

    private PlayerInfoControlPanel controlPane;

    private PlayerIconsField iconsField;

    private Thread gaming_thread;
    private boolean is_pause;
    private boolean is_run_game;
    private Target[] targets;
    private Arrow[] arrows;
    int shot_counter;
    int score;

    ClientModel model = BClientModel.get_model();

    @FXML
    public void initialize() {
        main.getStylesheets().add("style.css");
        ready_button.getStyleClass().add("unready-btn");
        model.mc = this;
        arrows = new Arrow[4];
        controlPane = new PlayerInfoControlPanel();
        RightVBox.getChildren().add(controlPane);
        iconsField = new PlayerIconsField();
        LeftVBox.getChildren().add(iconsField);

        for (int i = 0; i < 4; i++) {
            arrows[i] = new Arrow();
        }
        is_run_game = false;
        is_pause = false;
        targets = new Target[] {
                new Target(big_target,
                        AppConfig.big_target_distance,
                        AppConfig.big_target_speed,
                        AppConfig.big_target_points_for_hit),
                new Target(small_target,
                        AppConfig.small_target_distance,
                        AppConfig.small_target_speed,
                        AppConfig.small_target_points_for_hit)
        };
        gaming_thread = null;
        ConnectWindow connect_window = new ConnectWindow();
        connect_window.show();
    }

    @FXML
    void ready() {
        if (model.cls != null) {
            model.cls.send_signal(new SignalMsg(MsgAction.SET_READY, true));
            ready_button.getStyleClass().remove("unready-btn");
            ready_button.getStyleClass().add("ready-btn");
        }
    }

    void unready() {
        ready_button.getStyleClass().remove("ready-btn");
        ready_button.getStyleClass().add("unready-btn");
    }

    @FXML
    void stop() {
        if (is_run_game) {
            is_run_game = false;
            gaming_thread.interrupt();
            gaming_thread = null;
            is_pause = false;
        }
    }

    @FXML
    void shoot() {
        if (model.cls != null) {
            model.cls.send_signal(new SignalMsg(MsgAction.SHOT, true));
        }
    }

    @FXML
    void pause_game() {
        if (is_run_game) {
            is_pause = true;
        }
    }

    @FXML
    void continue_game() {
        if (is_run_game && is_pause) {
            is_pause = false;
            synchronized (this) {
                notifyAll();
            }
        }
    }

    private void update_shoot_cnt() {
        shoot_cnt_label.setText(Integer.toString(shot_counter));
    }

    private void update_score() {
        score_cnt_label.setText(Integer.toString(score));
    }

    private void reset_score() {
        shot_counter = 0;
        score = 0;
        update_score();
        update_shoot_cnt();
    }

    public void add_new_player(PlayerInfo info) {
        Platform.runLater( () -> {
            iconsField.add_new_icon(info.id, info.id == model.player_id);
            controlPane.add_new_player(info);
        });
    }
    public void update_data(PlayerInfo[] infos) {
        Platform.runLater(() -> {
            Point[] new_target_coords = model.get_target_coords();
            big_target.setLayoutX(new_target_coords[0].x);
            big_target.setLayoutY(new_target_coords[0].y);

            small_target.setLayoutX(new_target_coords[1].x);
            small_target.setLayoutY(new_target_coords[1].y);

            ArrowData[] arrows_data = model.get_arrows();
            for (int i = 0; i < arrows_data.length; i++) {
                if (arrows_data[i].is_active()) {
                    arrows[i].setLayoutX(arrows_data[i].get_coords().x);
                    arrows[i].setLayoutY(arrows_data[i].get_coords().y);
                    if (!playing_field.getChildren().contains(arrows[i])) {
                        playing_field.getChildren().add(arrows[i]);
                    }
                }
                else {
                    playing_field.getChildren().remove(arrows[i]);
                }
            }
            controlPane.update_data(infos);
        });
    }

    public void show_winner(PlayerInfo info) {
        Platform.runLater( () -> {
            WinnerWindow.show(info);
        });
    }
}