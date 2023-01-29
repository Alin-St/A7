package com.a7.model.statements;

import com.a7.model.exceptions.InterpreterException;
import com.a7.model.exceptions.StatementExecutionException;
import com.a7.model.expressions.IExpression;
import com.a7.model.programState.ProgramState;
import com.a7.model.types.IType;
import com.a7.model.types.ReferenceType;
import com.a7.model.values.ReferenceValue;
import com.a7.model.utility.MyIDictionary;

public class HeapAllocationStatement implements IStatement {

    private final String _identifier;
    private final IExpression _expression;

    public HeapAllocationStatement(String identifier, IExpression expression) {
        _identifier = identifier;
        _expression = expression;
    }

    @Override
    public String toString() {
        return _identifier + " = new(" + _expression.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        var symbolTable = state.getSymbolTable();
        var heapTable = state.getHeapTable();

        if (!symbolTable.containsKey(_identifier))
            throw new StatementExecutionException("Variable not declared.");

        var identifierType = symbolTable.get(_identifier).getType();

        if (!(identifierType instanceof ReferenceType identifierRType))
            throw new StatementExecutionException("Variable is not a reference type.");

        var expressionValue = _expression.evaluate(symbolTable, heapTable);

        if (!expressionValue.getType().equals(identifierRType.getInnerType()))
            throw new StatementExecutionException("Expression type is not compatible with reference inner type.");

        int heapAddress = heapTable.getFreeAddress();
        heapTable.put(heapAddress, expressionValue);
        symbolTable.put(_identifier, new ReferenceValue(heapAddress, identifierRType.getInnerType()));

        return null;
    }

    @Override
    public HeapAllocationStatement deepCopy() {
        return new HeapAllocationStatement(_identifier, _expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws InterpreterException {
        var identifierType = typeEnv.get(_identifier);
        var expressionType = _expression.typeCheck(typeEnv);
        if (!identifierType.equals(new ReferenceType(expressionType)))
            throw new InterpreterException("Expression type is different from identifier type.");
        return typeEnv;
    }
}
