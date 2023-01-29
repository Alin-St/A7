package com.a7.model.values;

import com.a7.model.types.IntType;

public class IntValue implements IValue {

    private final int _value;

    public IntValue(int value) {
        _value = value;
    }

    @Override
    public String toString() {
        return Integer.toString(_value);
    }

    public int getValue() { return _value; }

    @Override
    public IntType getType() { return IntType.get(); }

    @Override
    public IntValue deepCopy() {
        return new IntValue(_value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof IntValue other && _value == other._value;
    }
}
