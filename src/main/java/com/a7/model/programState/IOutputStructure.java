package com.a7.model.programState;

import com.a7.model.exceptions.InterpreterException;
import com.a7.model.utility.MyIList;
import com.a7.model.values.IValue;

public interface IOutputStructure extends MyIList<IValue> {
    @Override
    IOutputStructure deepCopy() throws InterpreterException;
}
