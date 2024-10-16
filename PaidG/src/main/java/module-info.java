module com.example.paidg {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.compiler;
    requires java.sql;


    opens com.example.paidg to javafx.fxml;
    exports com.example.paidg;
}