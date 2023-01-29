package com.a7.model.exceptions;

public class AdtException extends InterpreterException {

    public AdtException(String message, Throwable cause) { super(message, cause); }

    public AdtException(String message) { this(message, null); }
}
