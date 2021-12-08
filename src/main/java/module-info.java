module app.oracleextractor {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.antlr.antlr4.runtime;


    opens app.oracleextractor to javafx.fxml;
    exports app.oracleextractor;
}