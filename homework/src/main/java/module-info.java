module petproject.homework1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens petproject.homework1 to javafx.fxml;
    exports petproject.homework1;
}