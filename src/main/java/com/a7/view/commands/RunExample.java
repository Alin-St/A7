package com.a7.view.commands;

import com.a7.controller.Controller;
import com.a7.model.exceptions.InterpreterException;

public class RunExample extends Command {

    private final Controller _controller;

    public RunExample(String key, String desc, Controller controller) {
        super(key, desc);
        this._controller = controller;
    }

    @Override
    public void execute() {
        try {
            _controller.allSteps();
        } catch (InterpreterException | InterruptedException e) {
            System.out.println("Error: " + e);
        }
    }
}