package com.a7.model.expressions;

import com.a7.model.exceptions.ExpressionEvaluationException;
import com.a7.model.exceptions.InterpreterException;
import com.a7.model.programState.IHeapTable;
import com.a7.model.programState.ISymbolTable;
import com.a7.model.types.IType;
import com.a7.model.utility.MyIDictionary;
import com.a7.model.values.IValue;

public class VariableExpression implements IExpression {

    private final String _identifier;

    public VariableExpression(String identifier) {
        _identifier = identifier;
    }

    @Override
    public String toString() {
        return _identifier;
    }

    @Override
    public IValue evaluate(ISymbolTable symbolTable, IHeapTable heapTable) throws ExpressionEvaluationException {
        if (!symbolTable.containsKey(_identifier))
            throw new ExpressionEvaluationException("Variable '" + _identifier + "' not declared.");
        return symbolTable.get(_identifier);
    }

    @Override
    public VariableExpression deepCopy() {
        return this; // Class is immutable.
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws InterpreterException {
        return typeEnv.get(_identifier);
    }
}
