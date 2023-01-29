package com.a7.model.types;

import com.a7.model.values.IValue;
import com.a7.model.values.ReferenceValue;

public class ReferenceType implements IType {

    private final IType _innerType;

    public ReferenceType(IType innerType) {
        _innerType = innerType;
    }

    public IType getInnerType() { return _innerType; }

    @Override
    public String toString() {
        return "Ref<" + _innerType.toString() + ">";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ReferenceType rt && _innerType.equals(rt.getInnerType());
    }

    @Override
    public IValue defaultValue() {
        return new ReferenceValue(0, _innerType);
    }

    @Override
    public IType deepCopy() {
        return new ReferenceType(_innerType.deepCopy());
    }
}
