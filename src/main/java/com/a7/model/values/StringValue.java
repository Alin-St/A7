package com.a7.model.values;

import com.a7.model.types.StringType;

import java.util.Objects;

public class StringValue implements IValue {

    private final String _value;

    public StringValue(String value) {
        _value = value;
    }

    @Override
    public String toString() {
        return "\"" + _value + "\"";
    }

    public String getValue() {
        return _value;
    }

    @Override
    public StringType getType() {
        return StringType.get();
    }

    @Override
    public IValue deepCopy() {
        return new StringValue(_value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof StringValue other && Objects.equals(_value, other._value);
    }

    @Override
    public int hashCode() {
        return _value.hashCode();
    }
}
