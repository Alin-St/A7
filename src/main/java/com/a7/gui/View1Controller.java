package com.a7.gui;

import com.a7.HelloApplication;
import com.a7.controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class View1Controller {
    @FXML
    public ListView<String> listView;
    public ArrayList<Controller> controllers;

    @FXML
    public Button button;

    public void buttonClicked() {
        controllers = HelloApplication.Controllers;

        for (var c : controllers) {
            var prg = c.getRepository().getProgramList().get(0);
            listView.getItems().add(prg.toString());
        }
    }

    public void selectProgramClicked() {
        int ind = listView.getSelectionModel().getSelectedIndex();
        var prg = controllers.get(ind).getRepository().getProgramList().get(0);
        System.out.println("Selected:\n" + prg.toString().indent(4));
    }
}