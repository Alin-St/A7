package com.a7.model.programState;

import com.a7.model.exceptions.InterpreterException;
import com.a7.model.statements.IStatement;
import com.a7.model.utility.MyIStack;

public interface IExecutionStack extends MyIStack<IStatement> {
    @Override
    IExecutionStack deepCopy() throws InterpreterException;
}
