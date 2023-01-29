package com.a7.model.programState;

import com.a7.model.exceptions.InterpreterException;
import com.a7.model.utility.MyIDictionary;
import com.a7.model.values.IValue;

public interface ISymbolTable extends MyIDictionary<String, IValue> {
    @Override
    ISymbolTable deepCopy() throws InterpreterException;
}
