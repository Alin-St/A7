package com.a7.model.utility;

import com.a7.model.exceptions.InterpreterException;

import java.util.ArrayList;

public class MyList<T> implements MyIList<T> {

    private final ArrayList<T> _items = new ArrayList<>();
    private final Class<T> _type;

    public MyList(Class<T> type) {
        _type = type;
    }

    /** Items are not deeply copied.
     */
    public MyList(Class<T> type, Iterable<T> items) {
        this(type);
        for (var item : items)
            add(item);
    }


    /** If T implements IDeepCopyable, items are deeply copied.
     */
    public MyList(MyList<T> other) throws InterpreterException {
        this(other._type);
        for (var item : other._items) {
            if (item instanceof IDeepCopyable dci) {
                var ic = dci.deepCopy();
                if (_type.isInstance(ic))
                    item = _type.cast(ic);
            }
            add(item);
        }
    }

    @Override
    public void add(T item) {
        _items.add(item);
    }

    @Override
    public ArrayList<T> toArrayList() {
        return new ArrayList<>(_items);
    }

    @Override
    public MyList<T> deepCopy() throws InterpreterException {
        return new MyList<>(this);
    }
}
