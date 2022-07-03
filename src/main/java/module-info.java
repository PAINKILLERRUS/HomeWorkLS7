module com.example.homeworkls7jfxchat {
    requires javafx.controls;
    requires javafx.fxml;


    exports com.example.homeworkls7jfxchat.client;
    opens com.example.homeworkls7jfxchat.client to javafx.fxml;
}