package com.a7.model.exceptions;

public class InterpreterException extends Exception {

    public InterpreterException(String message, Throwable cause) { super(message, cause); }

    public InterpreterException(String message) { this(message, null); }
}
