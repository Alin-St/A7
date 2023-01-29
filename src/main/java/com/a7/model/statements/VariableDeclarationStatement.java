package com.a7.model.statements;

import com.a7.model.exceptions.InterpreterException;
import com.a7.model.exceptions.StatementExecutionException;
import com.a7.model.programState.ProgramState;
import com.a7.model.types.IType;
import com.a7.model.utility.MyIDictionary;

public class VariableDeclarationStatement implements IStatement {

    private final String _identifier;
    private final IType _type;

    public VariableDeclarationStatement(String identifier, IType type) {
        _identifier = identifier;
        _type = type;
    }

    @Override
    public String toString() {
        return _type.toString() + " " + _identifier;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementExecutionException {
        var symbolTable = state.getSymbolTable();
        if (symbolTable.containsKey(_identifier))
            throw new StatementExecutionException("Variable '" + _identifier + "' is already declared.");

        symbolTable.put(_identifier, _type.defaultValue());
        return null;
    }

    @Override
    public VariableDeclarationStatement deepCopy() {
        return new VariableDeclarationStatement(_identifier, _type);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws InterpreterException {
        typeEnv.put(_identifier, _type);
        return typeEnv;
    }
}
