package com.a7.model.utility;

import com.a7.model.exceptions.AdtException;
import com.a7.model.exceptions.InterpreterException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {

    private final Stack<T> _items = new Stack<>();
    protected final Class<T> _type;

    public MyStack(Class<T> type) {
        _type = type;
    }

    /** Items are not deeply copied. Items are added in the given order.
     */
    public MyStack(Class<T> type, Iterable<T> items) {
        this(type);
        for (var item : items)
            _items.push(item);
    }

    /** If T implements IDeepCopyable, items are deeply copied.
     */
    public MyStack(MyStack<T> other) throws InterpreterException {
        this(other._type);
        var itemsArray = other.toArrayList();
        Collections.reverse(itemsArray);

        for (var item : itemsArray) {
            if (item instanceof IDeepCopyable dci) {
                var ic = dci.deepCopy();
                if (_type.isInstance(ic))
                    item = _type.cast(ic);
            }
            push(item);
        }
    }

    @Override
    public void push(T item) {
        _items.push(item);
    }

    @Override
    public T pop() {
        return _items.pop();
    }

    @Override
    public boolean empty() {
        return _items.empty();
    }

    /** Items are returned in pop order.
     */
    @Override
    public ArrayList<T> toArrayList() throws AdtException {
        var shallowCopy = (Stack<?>)_items.clone();
        var result = new ArrayList<T>();
        while (!shallowCopy.empty()) {
            var item = shallowCopy.pop();
            if (!_type.isInstance(item))
                throw new AdtException("Invalid item type.");
            result.add(_type.cast(item));
        }
        return result;
    }

    @Override
    public MyStack<T> deepCopy() throws InterpreterException {
        return new MyStack<>(this);
    }
}
