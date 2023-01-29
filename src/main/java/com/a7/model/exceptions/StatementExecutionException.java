package com.a7.model.exceptions;

public class StatementExecutionException extends InterpreterException {

    public StatementExecutionException(String message, Throwable cause) { super(message, cause); }

    public StatementExecutionException(String message) { this(message, null); }
}
