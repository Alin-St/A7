package com.a7.model.programState;

import com.a7.model.exceptions.InterpreterException;
import com.a7.model.utility.MyList;
import com.a7.model.values.IValue;

public class OutputStructure extends MyList<IValue> implements IOutputStructure {
    public OutputStructure() {
        super(IValue.class);
    }

    /** Deep copy constructor.
     */
    public OutputStructure(OutputStructure other) throws InterpreterException {
        super(other);
    }

    @Override
    public OutputStructure deepCopy() throws InterpreterException {
        return new OutputStructure(this);
    }
}
