package com.a7.view.commands;

public abstract class Command {

    private final String _key, _description;

    public Command(String key, String description) {
        this._key = key;
        this._description = description;
    }

    public abstract void execute();

    public String getKey() { return _key; }

    public String getDescription() { return _description; }
}
