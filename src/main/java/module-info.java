module cheshire.cheshirec482pa {
    requires javafx.controls;
    requires javafx.fxml;


    opens cheshire.cheshirec482pa to javafx.fxml;
    exports cheshire.cheshirec482pa;
    exports controller;
    opens controller to javafx.fxml;
    exports model;
    opens model to javafx.base;
}