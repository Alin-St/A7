package com.a7.model.types;

import com.a7.model.utility.IDeepCopyable;
import com.a7.model.values.IValue;

public interface IType extends IDeepCopyable {
    String toString();
    boolean equals(Object other);
    IValue defaultValue();
    IType deepCopy();
}
