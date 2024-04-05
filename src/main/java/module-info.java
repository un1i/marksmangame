module org.example.marksmangame {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.marksmangame to javafx.fxml;
    exports org.example.marksmangame;
}