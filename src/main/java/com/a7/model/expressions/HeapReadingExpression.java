package com.a7.model.expressions;

import com.a7.model.exceptions.ExpressionEvaluationException;
import com.a7.model.exceptions.InterpreterException;
import com.a7.model.programState.IHeapTable;
import com.a7.model.programState.ISymbolTable;
import com.a7.model.types.IType;
import com.a7.model.types.ReferenceType;
import com.a7.model.utility.MyIDictionary;
import com.a7.model.values.IValue;
import com.a7.model.values.ReferenceValue;

public class HeapReadingExpression implements IExpression {

    private final IExpression _address;

    public HeapReadingExpression(IExpression address) {
        _address = address;
    }

    @Override
    public String toString() {
        return "readHeap(" + _address + ")";
    }

    @Override
    public IValue evaluate(ISymbolTable symbolTable, IHeapTable heapTable) throws ExpressionEvaluationException {
        var addressVal = _address.evaluate(symbolTable, heapTable);
        if (!(addressVal instanceof ReferenceValue refVal))
            throw new ExpressionEvaluationException("Heap reading expression must receive a reference value.");

        int address = refVal.getAddress();
        if (!heapTable.containsKey(address))
            throw new ExpressionEvaluationException("Address not found.");

        return heapTable.get(address);
    }

    @Override
    public HeapReadingExpression deepCopy() {
        return new HeapReadingExpression(_address.deepCopy());
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws InterpreterException {
        var at = _address.typeCheck(typeEnv);
        if (!(at instanceof ReferenceType art))
            throw new InterpreterException("The given argument is not a reference type.");
        return art.getInnerType();
    }
}
