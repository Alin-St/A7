package com.a7.model.utility;

import com.a7.model.exceptions.InterpreterException;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyDictionary<TKey, TValue> implements MyIDictionary<TKey, TValue> {

    private final HashMap<TKey, TValue> _items = new HashMap<>();
    private final Class<TKey> _keyType;
    private final Class<TValue> _valueType;

    public MyDictionary(Class<TKey> keyType, Class<TValue> valueType) {
        _keyType = keyType;
        _valueType = valueType;
    }

    /** Items are not deeply copied.
     */
    public MyDictionary(Class<TKey> keyType, Class<TValue> valueType, Iterable<Map.Entry<TKey, TValue>> items) {
        this(keyType, valueType);
        for (var item : items)
            put(item.getKey(), item.getValue());
    }

    /** If TKey or TValue implement IDeepCopyable, they are deeply copied in the new Dictionary.
     */
    public MyDictionary(MyDictionary<TKey, TValue> other) throws InterpreterException {
        this(other._keyType, other._valueType);
        for (var entry : other._items.entrySet()) {
            var key = entry.getKey();
            var value = entry.getValue();

            if (key instanceof IDeepCopyable dck) {
                var kc = dck.deepCopy();
                if (_keyType.isInstance(kc))
                    key = _keyType.cast(kc);
            }

            if (value instanceof IDeepCopyable dcv) {
                var vc = dcv.deepCopy();
                if (_valueType.isInstance(vc))
                    value = _valueType.cast(vc);
            }

            put(key, value);
        }
    }

    @Override
    public void put(TKey key, TValue value) {
        _items.put(key, value);
    }

    @Override
    public TValue get(TKey key) {
        return _items.get(key);
    }

    @Override
    public boolean containsKey(TKey key) {
        return _items.containsKey(key);
    }

    @Override
    public void remove(TKey key) {
        _items.remove(key);
    }

    @Override
    public ArrayList<Map.Entry<TKey, TValue>> toArrayList() {
        var result = new ArrayList<Map.Entry<TKey, TValue>>();
        for (var entry : _items.entrySet())
            result.add(new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue()));
        return result;
    }

    @Override
    public MyDictionary<TKey, TValue> deepCopy() throws InterpreterException {
        return new MyDictionary<>(this);
    }
}
