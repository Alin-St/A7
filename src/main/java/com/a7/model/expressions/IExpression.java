package com.a7.model.expressions;

import com.a7.model.programState.ISymbolTable;
import com.a7.model.types.IType;
import com.a7.model.utility.IDeepCopyable;
import com.a7.model.exceptions.ExpressionEvaluationException;
import com.a7.model.exceptions.InterpreterException;
import com.a7.model.programState.IHeapTable;
import com.a7.model.utility.MyIDictionary;
import com.a7.model.values.IValue;

public interface IExpression extends IDeepCopyable {
    String toString();
    IValue evaluate(ISymbolTable symbolTable, IHeapTable heapTable) throws ExpressionEvaluationException;
    IExpression deepCopy();
    IType typeCheck(MyIDictionary<String, IType> typeEnv) throws InterpreterException;
}
