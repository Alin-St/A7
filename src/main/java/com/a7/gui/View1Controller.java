package com.a7.gui;

import com.a7.HelloApplication;
import com.a7.controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class View1Controller {
    @FXML
    public AnchorPane anchorPane;
    @FXML
    public ListView<String> listView;
    @FXML
    public Button button;

    private List<Controller> _controllers;

    public void initData(List<Controller> controllers) {
        _controllers = controllers;
        for (var c : _controllers) {
            var prg = c.getRepository().getProgramList().get(0);
            listView.getItems().add(prg.toString());
        }
    }

    public void buttonClicked() {
        for (var c : _controllers) {
            var prg = c.getRepository().getProgramList().get(0);
            listView.getItems().add(prg.toString());
        }
    }

    public void selectProgramClicked() {
        int ind = listView.getSelectionModel().getSelectedIndex();
        if (ind < 0) {
            HelloApplication.showMessage("No program selected.");
            return;
        }
        var controller = _controllers.get(ind);

        try {
            var fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view2.fxml"));
            var stage = new Stage();
            stage.setScene(new Scene(fxmlLoader.load(), 1280, 720));
            stage.setTitle("View2");

            var view2Controller = fxmlLoader.<View2Controller>getController();
            view2Controller.initData(controller);

            stage.show();
            stage.setOnHidden(e -> view2Controller.shutdown());
            var currentStage = (Stage)anchorPane.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}