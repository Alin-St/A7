package com.a7.model.programState;

import com.a7.model.exceptions.InterpreterException;
import com.a7.model.utility.MyDictionary;
import com.a7.model.values.StringValue;

import java.io.BufferedReader;

public class FileTable extends MyDictionary<StringValue, BufferedReader> implements IFileTable {
    public FileTable() {
        super(StringValue.class, BufferedReader.class);
    }

    /** Deep copy constructor.
     */
    public FileTable(FileTable other) throws InterpreterException {
        super(other);
    }

    @Override
    public FileTable deepCopy() throws InterpreterException {
        return new FileTable(this);
    }
}
