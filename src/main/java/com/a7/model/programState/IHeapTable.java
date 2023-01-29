package com.a7.model.programState;

import com.a7.model.exceptions.InterpreterException;
import com.a7.model.utility.MyIDictionary;
import com.a7.model.values.IValue;

public interface IHeapTable extends MyIDictionary<Integer, IValue> {
    @Override
    IHeapTable deepCopy() throws InterpreterException;

    int getFreeAddress();
}
