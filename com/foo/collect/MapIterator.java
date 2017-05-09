package com.foo.collect;

import java.util.Iterator;

public interface MapIterator<T> extends Iterator<T>{
    
    boolean hasNext();

    T next();

    Object getKey();

    Object getValue();
    
    void remove();
    
    T setValue(T value);
}
