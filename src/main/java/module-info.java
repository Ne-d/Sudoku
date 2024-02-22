module org.prepro {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.junit.jupiter.api;

    opens org.prepro to javafx.fxml;
    exports org.prepro;
}