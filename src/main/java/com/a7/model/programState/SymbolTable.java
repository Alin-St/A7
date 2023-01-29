package com.a7.model.programState;

import com.a7.model.exceptions.InterpreterException;
import com.a7.model.utility.MyDictionary;
import com.a7.model.values.IValue;

public class SymbolTable extends MyDictionary<String, IValue> implements ISymbolTable {
    public SymbolTable() {
        super(String.class, IValue.class);
    }

    /** Deep copy constructor.
     */
    public SymbolTable(SymbolTable other) throws InterpreterException {
        super(other);
    }

    @Override
    public SymbolTable deepCopy() throws InterpreterException {
        return new SymbolTable(this);
    }
}
