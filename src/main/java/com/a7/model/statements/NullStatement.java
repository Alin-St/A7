package com.a7.model.statements;

import com.a7.model.programState.ProgramState;
import com.a7.model.types.IType;
import com.a7.model.exceptions.InterpreterException;
import com.a7.model.utility.MyIDictionary;

public class NullStatement implements IStatement {

    @Override
    public String toString() {
        return "nop";
    }

    @Override
    public ProgramState execute(ProgramState state) {
        return null;
    }

    @Override
    public NullStatement deepCopy() {
        return new NullStatement();
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws InterpreterException {
        return typeEnv;
    }
}
