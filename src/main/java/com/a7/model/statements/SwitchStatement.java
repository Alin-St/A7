package com.a7.model.statements;

import com.a7.model.exceptions.InterpreterException;
import com.a7.model.expressions.IExpression;
import com.a7.model.expressions.RelationalExpression;
import com.a7.model.expressions.RelationalOperator;
import com.a7.model.programState.ProgramState;
import com.a7.model.types.IType;
import com.a7.model.utility.MyIDictionary;

public class SwitchStatement implements IStatement {

    private final IExpression _switchExpression;
    private final IExpression _case1Expression;
    private final IStatement _case1Statement;
    private final IExpression _case2Expression;
    private final IStatement _case2Statement;
    private final IStatement _defaultStatement;

    public SwitchStatement(IExpression switchExpression, IExpression case1Expression, IStatement case1Statement,
                           IExpression case2Expression, IStatement case2Statement, IStatement defaultStatement) {
        _switchExpression = switchExpression;
        _case1Expression = case1Expression;
        _case1Statement = case1Statement;
        _case2Expression = case2Expression;
        _case2Statement = case2Statement;
        _defaultStatement = defaultStatement;
    }

    @Override
    public String toString() {
        return "switch (" + _switchExpression + ")\n(case " + _case1Expression + ":\n" +
                _case1Statement.toString().indent(4) + ") (case " + _case2Expression + ":\n" +
                _case2Statement.toString().indent(4) + ") ( default:\n" + _defaultStatement.toString().indent(4) + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        var stack = state.getExecutionStack();
        stack.push(
                new ConditionalStatement(
                        new RelationalExpression(_switchExpression, _case1Expression, RelationalOperator.EQUALS),
                        _case1Statement,
                        new ConditionalStatement(
                                new RelationalExpression(_switchExpression, _case2Expression, RelationalOperator.EQUALS),
                                _case2Statement,
                                _defaultStatement
                        )
                )
        );

        return null;
    }

    @Override
    public SwitchStatement deepCopy() {
        return new SwitchStatement(_switchExpression, _case1Expression, _case1Statement, _case2Expression,
                _case2Statement, _defaultStatement);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws InterpreterException {
        var type1 = _switchExpression.typeCheck(typeEnv);
        var type2 = _case1Expression.typeCheck(typeEnv);
        var type3 = _case2Expression.typeCheck(typeEnv);

        if (!type1.equals(type2) || !type2.equals(type3))
            throw new InterpreterException("Switch expression types are not the same.");

        _case1Statement.typeCheck(typeEnv.deepCopy());
        _case2Statement.typeCheck(typeEnv.deepCopy());
        _defaultStatement.typeCheck(typeEnv.deepCopy());
        return typeEnv;
    }
}
