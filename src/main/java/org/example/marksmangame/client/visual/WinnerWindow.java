package org.example.marksmangame.client.visual;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.marksmangame.client.PlayerInfo;

public class WinnerWindow {
    public static void show(PlayerInfo info) {
        Stage window = new Stage();
        window.initModality(Modality.WINDOW_MODAL);
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox, 300, 200);
        window.setScene(scene);
        window.setTitle("Игра закончилась");

        Label winner_label = new Label("Победитель");
        vBox.getChildren().add(winner_label);

        Label winner_name = new Label(info.get_name());
        vBox.getChildren().add(winner_name);

        Button connect_button = new Button("Продолжить");
        connect_button.setOnAction(event-> {window.close();});
        vBox.getChildren().add(connect_button);
        window.showAndWait();
    }

}

