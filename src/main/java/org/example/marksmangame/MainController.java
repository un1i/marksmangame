package org.example.marksmangame;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class MainController {
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

    private Thread gaming_thread;
    private boolean is_pause;
    private boolean is_run_game;
    private Target[] targets;
    private Arrow arrow;
    int shot_counter;
    int score;

    @FXML
    public void initialize() {
        arrow = new Arrow(playing_field, AppConfig.arrow_speed);
        is_run_game = false;
        is_pause = false;
        targets = new Target[] {
                new Target(big_target, playing_field,
                        AppConfig.big_target_distance,
                        AppConfig.big_target_speed,
                        AppConfig.big_target_points_for_hit),
                new Target(small_target, playing_field,
                        AppConfig.small_target_distance,
                        AppConfig.small_target_speed,
                        AppConfig.small_target_points_for_hit)
        };
        gaming_thread = null;
    }

    @FXML
    void start() {
        if (is_run_game) {
            return;
        }

        is_run_game = true;
        reset_score();

        gaming_thread = new Thread(
                () -> {
                    try {
                        while (is_run_game) {
                            if (is_pause) {
                                synchronized (this) {
                                    this.wait();
                                }
                            }
                            Platform.runLater(
                                    () -> {
                                        if (arrow.is_active()) {
                                            arrow.move();
                                        }
                                        for (Target target : targets) {
                                            target.move();
                                            if (arrow.is_active() && target.check_hit(arrow)) {
                                                arrow.remove();
                                                score += target.get_points_for_hit();
                                                update_score();
                                            }
                                        }
                                    }
                            );
                            Thread.sleep(50);
                        }
                    }
                    catch (InterruptedException e) {
                        Platform.runLater(
                                () -> {
                                    arrow.remove();
                                    for (Target target : targets) {
                                        target.set_start_coords();
                                    }
                                }
                        );
                    }
                }
        );
        gaming_thread.setDaemon(true);
        gaming_thread.start();
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
        if (is_run_game && !is_pause && !arrow.is_active()) {
            arrow.set_active(true);
            arrow.set_start_coords();
            playing_field.getChildren().add(arrow);
            shot_counter++;
            update_shoot_cnt();
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
}