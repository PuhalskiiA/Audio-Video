package ru.puhalskii.coursebd;

import Utils.LayerOfTypes.Employee;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class App extends Application {
    public static Stage stage;
    public static String userLogin;
    public static int userId;
    public static int userRole;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        App.stage = stage;

        setScene("Authentication", getClass().getClassLoader().getResource("AuthWindow.fxml"));
    }

    public static void setScene(String title, URL url) {
        Parent root = null;

        try {
            root = FXMLLoader.load(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        App.stage.setTitle(title);
        App.stage.setScene(new Scene(root));
        App.stage.show();
    }

    public static void setAlert(String title, Alert.AlertType type, String message) {
        Alert alert = new Alert(type);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}