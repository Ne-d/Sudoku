module org.prepro {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.prepro to javafx.fxml;
    exports org.prepro;

    exports org.prepro.model;

    opens org.prepro.controller to javafx.fxml;
    exports org.prepro.controller;

    opens org.prepro.view to javafx.fxml;
    exports org.prepro.view;
}
