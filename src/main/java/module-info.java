module com.example.pharmacy {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.jetbrains.annotations;


    opens com.example.pharmacy to javafx.fxml;
    exports com.example.pharmacy;
}