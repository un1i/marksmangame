module org.example.marksmangame {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens org.example.marksmangame to javafx.fxml, com.google.gson;
    exports org.example.marksmangame;
    exports org.example.marksmangame.server;
    opens org.example.marksmangame.server to com.google.gson, javafx.fxml;
    exports org.example.marksmangame.messages;
    opens org.example.marksmangame.messages to com.google.gson, javafx.fxml;
    exports org.example.marksmangame.client;
    opens org.example.marksmangame.client to com.google.gson, javafx.fxml;
    exports org.example.marksmangame.messages.MsgData;
    opens org.example.marksmangame.messages.MsgData to com.google.gson, javafx.fxml;
    exports org.example.marksmangame.client.visual;
    opens org.example.marksmangame.client.visual to com.google.gson, javafx.fxml;
}