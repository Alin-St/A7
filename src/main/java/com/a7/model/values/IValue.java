package com.a7.model.values;

import com.a7.model.types.IType;
import com.a7.model.utility.IDeepCopyable;

public interface IValue extends IDeepCopyable {
    IType getType();
    String toString();
    IValue deepCopy();
    boolean equals(Object other);
}
