package com.a7.model.exceptions;

public class ExpressionEvaluationException extends InterpreterException {

    public ExpressionEvaluationException(String message, Throwable cause) { super(message, cause); }

    public ExpressionEvaluationException(String message) { this(message, null); }
}
