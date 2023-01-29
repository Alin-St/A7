package com.a7.model.statements;

import com.a7.model.exceptions.InterpreterException;
import com.a7.model.programState.ProgramState;
import com.a7.model.types.IType;
import com.a7.model.utility.IDeepCopyable;
import com.a7.model.utility.MyIDictionary;

public interface IStatement extends IDeepCopyable {
    ProgramState execute(ProgramState state) throws InterpreterException;
    String toString();
    IStatement deepCopy();
    MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws InterpreterException;
}
