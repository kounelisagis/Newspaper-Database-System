module db_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires bcrypt;

    opens org.openjfx to javafx.fxml;
    exports org.openjfx;
}