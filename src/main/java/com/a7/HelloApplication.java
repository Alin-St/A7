package com.a7;

import com.a7.gui.View1Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view1.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 900);
        stage.setTitle("View 1");
        stage.setScene(scene);

        var viewController = fxmlLoader.<View1Controller>getController();
        viewController.initData(Interpreter.getControllers());

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void showMessage(String msg) {
        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Interpreter:");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
