package com.a7.model.utility;

import com.a7.model.exceptions.InterpreterException;

public interface IDeepCopyable {
    IDeepCopyable deepCopy() throws InterpreterException;
}
