package com.a7.model.types;

import com.a7.model.values.BoolValue;

public class BoolType implements IType {

    private BoolType() {}

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BoolType;
    }

    @Override
    public BoolValue defaultValue() {
        return new BoolValue(false);
    }

    @Override
    public BoolType deepCopy() {
        return BoolType.get();
    }

    private final static BoolType _global = new BoolType();

    public static BoolType get() { return _global; }
}
