package com.a7.view;

import com.a7.view.commands.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {

    private final Map<String, Command> _commands;

    public TextMenu() {
        _commands = new HashMap<>();
    }

    public void addCommand(Command command) {
        _commands.put(command.getKey(), command);
    }

    private void printMenu() {
        for (var command : _commands.values()) {
            System.out.println(command.getKey() + ":");
            System.out.println(command.getDescription().indent(4));
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void show() {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            System.out.print("Input the option: ");
            String key = scanner.nextLine();

            Command command = _commands.get(key);
            if (command == null) {
                System.out.println("Invalid Option");
                continue;
            }

            command.execute();
        }
    }
}