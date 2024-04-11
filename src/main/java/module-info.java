module com.example.magicfruitsgame {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.graphics;
    requires java.logging;
    opens com.example.magicfruitsgame.controller to javafx.fxml;
    exports com.example.magicfruitsgame to javafx.graphics;
    exports com.example.magicfruitsgame.service to javafx.graphics;
    exports com.example.magicfruitsgame.model;
}
