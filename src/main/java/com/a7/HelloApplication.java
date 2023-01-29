package com.a7;

import com.a7.controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class HelloApplication extends Application {
    public static ArrayList<Controller> Controllers;

    @Override
    public void start(Stage stage) throws IOException {
        // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view1.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("View 1");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(ArrayList<Controller> controllers) {
        Controllers = controllers;
        launch();
    }
}
