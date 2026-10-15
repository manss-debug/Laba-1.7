
module org.example.javalab3 {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires java.prefs;

    opens org.example.javalab3.controller to javafx.fxml;

    exports org.example.javalab3;
    exports org.example.javalab3.controller;
    exports org.example.javalab3.model;
}
