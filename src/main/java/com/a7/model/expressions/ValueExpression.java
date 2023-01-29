package com.a7.model.expressions;

import com.a7.model.programState.ISymbolTable;
import com.a7.model.types.IType;
import com.a7.model.exceptions.InterpreterException;
import com.a7.model.programState.IHeapTable;
import com.a7.model.utility.MyIDictionary;
import com.a7.model.values.IValue;

public class ValueExpression implements IExpression {

    private final IValue _value;

    public ValueExpression(IValue value) {
        _value = value;
    }

    @Override
    public String toString() {
        return _value.toString();
    }

    @Override
    public IValue evaluate(ISymbolTable symbolTable, IHeapTable heapTable) {
        return _value;
    }

    @Override
    public ValueExpression deepCopy() {
        return new ValueExpression(_value.deepCopy());
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws InterpreterException {
        return _value.getType();
    }
}
