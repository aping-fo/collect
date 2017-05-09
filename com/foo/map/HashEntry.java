package com.foo.map;

import java.util.Map;

public class HashEntry implements Map.Entry {
    /** The next entry in the hash chain */
    protected HashEntry next;
    /** The hash code of the key */
    protected int hashCode;
    /** The key */
    protected Object key;
    /** The value */
    protected Object value;
    
    protected HashEntry(HashEntry next, int hashCode, Object key, Object value) {
        super();
        this.next = next;
        this.hashCode = hashCode;
        this.key = key;
        this.value = value;
    }
    
    public Object getKey() {
        return key;
    }
    
    public Object getValue() {
        return value;
    }
    
    public Object setValue(Object value) {
        Object old = this.value;
        this.value = value;
        return old;
    }
    
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Map.Entry == false) {
            return false;
        }
        Map.Entry other = (Map.Entry) obj;
        return
            (getKey() == null ? other.getKey() == null : getKey().equals(other.getKey())) &&
            (getValue() == null ? other.getValue() == null : getValue().equals(other.getValue()));
    }
    
    public int hashCode() {
        return (getKey() == null ? 0 : getKey().hashCode()) ^
               (getValue() == null ? 0 : getValue().hashCode()); 
    }
    
    public String toString() {
        return new StringBuffer().append(getKey()).append('=').append(getValue()).toString();
    }
}
