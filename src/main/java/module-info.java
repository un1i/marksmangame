module org.example.marksmangame {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens org.example.marksmangame to javafx.fxml, com.google.gson;
    exports org.example.marksmangame;
}