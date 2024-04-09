module com.example.magicfruitsgame {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.graphics;
    opens com.example.magicfruitsgame to javafx.fxml;
    exports com.example.magicfruitsgame.menu to javafx.graphics;
}