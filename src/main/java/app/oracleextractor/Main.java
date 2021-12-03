package app.oracleextractor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Layout.fxml")));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Oracle Extractor");
            Image icon = new Image("crystal-ball.png");
            primaryStage.getIcons().add(icon);
            // scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setMaximized(true);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}