package com.a7.model.statements;

import com.a7.model.expressions.IExpression;
import com.a7.model.programState.ProgramState;
import com.a7.model.types.IType;
import com.a7.model.exceptions.InterpreterException;
import com.a7.model.exceptions.StatementExecutionException;
import com.a7.model.utility.MyIDictionary;

public class AssignmentStatement implements IStatement {

    private final String _identifier;
    private final IExpression _expression;

    public AssignmentStatement(String identifier, IExpression expression) {
        _identifier = identifier;
        _expression = expression;
    }

    @Override
    public String toString() {
        return _identifier + " = " + _expression.toString();
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        var symbolTable = state.getSymbolTable();

        if (!symbolTable.containsKey(_identifier))
            throw new StatementExecutionException("Variable Id '" + _identifier + "' is not declared.");

        var value = _expression.evaluate(symbolTable, state.getHeapTable());
        var type = symbolTable.get(_identifier).getType();

        if (!value.getType().equals(type))
            throw new StatementExecutionException("Type of expression '" + value.getType().toString() +
                    "' and type of variable '" + type.toString() + "' do not match.");

        symbolTable.put(_identifier, value);
        return null;
    }

    @Override
    public AssignmentStatement deepCopy() {
        return new AssignmentStatement(_identifier, _expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws InterpreterException {
        var identifierType = typeEnv.get(_identifier);
        var expressionType = _expression.typeCheck(typeEnv);
        if (!identifierType.equals(expressionType))
            throw new InterpreterException("Expression type is different from identifier type.");
        return typeEnv;
    }
}
