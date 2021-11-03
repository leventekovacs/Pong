module com.pong {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.pong to javafx.fxml;
    exports com.pong;
    exports com.pong.controllers;
    opens com.pong.controllers to javafx.fxml;
    exports com.pong.components;
    opens com.pong.components to javafx.fxml;
    exports com.pong.constants;
    opens com.pong.constants to javafx.fxml;
    exports com.pong.engines;
    opens com.pong.engines to javafx.fxml;
    exports com.pong.model;
    opens com.pong.model to javafx.fxml;
}