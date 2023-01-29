package com.a7.model.programState;

import com.a7.model.exceptions.InterpreterException;
import com.a7.model.utility.MyDictionary;
import com.a7.model.values.IValue;

public class HeapTable extends MyDictionary<Integer, IValue> implements IHeapTable {

    public HeapTable() {
        super(Integer.class, IValue.class);
    }

    public HeapTable(HeapTable other) throws InterpreterException {
        super(other);
    }

    @Override
    public HeapTable deepCopy() throws InterpreterException {
        return new HeapTable(this);
    }

    @Override
    public int getFreeAddress() {
        int i = 1;
        while (containsKey(i))
            ++i;
        return i;
    }
}
