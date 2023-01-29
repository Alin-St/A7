package com.a7.model.programState;

import com.a7.model.exceptions.InterpreterException;
import com.a7.model.utility.MyIDictionary;
import com.a7.model.values.StringValue;

import java.io.BufferedReader;

public interface IFileTable extends MyIDictionary<StringValue, BufferedReader> {
    @Override
    IFileTable deepCopy() throws InterpreterException;
}
