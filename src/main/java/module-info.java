module app.oracleextractor {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;


    opens app.oracleextractor to javafx.fxml;
    exports app.oracleextractor;
}