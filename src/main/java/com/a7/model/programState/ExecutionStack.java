package com.a7.model.programState;

import com.a7.model.exceptions.InterpreterException;
import com.a7.model.statements.IStatement;
import com.a7.model.utility.MyStack;

public class ExecutionStack extends MyStack<IStatement> implements IExecutionStack {
    public ExecutionStack() {
        super(IStatement.class);
    }

    public ExecutionStack(IStatement firstItem) {
        super(IStatement.class);
        push(firstItem);
    }

    /** Deep copy constructor.
     */
    public ExecutionStack(ExecutionStack other) throws InterpreterException {
        super(other);
    }

    @Override
    public ExecutionStack deepCopy() throws InterpreterException {
        return new ExecutionStack(this);
    }
}
