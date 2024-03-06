module org.prepro {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.junit.jupiter.api;

    opens org.prepro to javafx.fxml;
    exports org.prepro;

    exports org.prepro.model;

    opens org.prepro.model.test to org.junit.platform.commons;
    exports org.prepro.model.test;

    opens org.prepro.controller to javafx.fxml;
    exports org.prepro.controller;

    opens org.prepro.view to javafx.fxml;
    exports org.prepro.view;
}
