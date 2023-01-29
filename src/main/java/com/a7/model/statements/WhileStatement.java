package com.a7.model.statements;

import com.a7.model.expressions.IExpression;
import com.a7.model.types.IType;
import com.a7.model.values.BoolValue;
import com.a7.model.exceptions.InterpreterException;
import com.a7.model.exceptions.StatementExecutionException;
import com.a7.model.programState.ProgramState;
import com.a7.model.types.BoolType;
import com.a7.model.utility.MyIDictionary;

public class WhileStatement implements IStatement {

    private final IExpression _condition;
    private final IStatement _whileStatement;

    public WhileStatement(IExpression condition, IStatement whileStatement) {
        _condition = condition;
        _whileStatement = whileStatement;
    }

    @Override
    public String toString() {
        return "WHILE " + _condition + " EXECUTE\n" + _whileStatement.toString().indent(4) + "END_WHILE";
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        var symbolTable = state.getSymbolTable();
        var stack = state.getExecutionStack();
        var condVal = _condition.evaluate(symbolTable, state.getHeapTable());

        if (!condVal.getType().equals(BoolType.get()))
            throw new StatementExecutionException("Conditional expression is not a boolean.");

        boolean bCond = ((BoolValue)condVal).getValue();

        if (bCond) {
            stack.push(this);
            stack.push(_whileStatement);
        }

        return null;
    }

    @Override
    public WhileStatement deepCopy() {
        return new WhileStatement(_condition.deepCopy(), _whileStatement.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws InterpreterException {
        if (!_condition.typeCheck(typeEnv).equals(BoolType.get()))
            throw new InterpreterException("Condition is not a boolean.");
        _whileStatement.typeCheck(typeEnv.deepCopy());
        return typeEnv;
    }
}
