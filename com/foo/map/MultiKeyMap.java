package com.foo.map;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;


import com.foo.collect.IterableMap;
import com.foo.collect.MapIterator;

/**
 * 练习MAP之多建值MAP
 * 还没支持扩容，稍后加入
 * @author aping.foo
 *
 */
public class MultiKeyMap implements IterableMap,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4345088159741941867L;
	protected transient HashEntry[] data;
	protected transient int modCount;
	static final int DEFAULT_INITIAL_CAPACITY = 16; //默认大小
	
	public MultiKeyMap(){
		 this.data = new HashEntry[DEFAULT_INITIAL_CAPACITY]; //默认值
		 modCount = 0;
	}
	
	/**
	 * 取模,获得索引,如果重了，就是hash冲突了
	 * @param hashCode
	 * @param dataSize
	 * @return
	 */
	private int hashIndex(int hashCode, int dataSize) {
        return hashCode & (dataSize - 1);
    }
	
	public Object put(Object key1, Object key2, Object value) {
        int hashCode = hash(key1, key2);
        int index = hashIndex(hashCode, data.length);
        HashEntry entry = data[index];
        while (entry != null) {
            if (entry.hashCode == hashCode && isEqualKey(entry, key1, key2)) {
                Object oldValue = entry.getValue();
                entry.setValue(value);
                return oldValue;
            }
            entry = entry.next;
        }
        
        //没有，则新建一个entry
        entry = new HashEntry(null, hashCode, new MultiKey(key1, key2), value);
        data[index] = entry;
        modCount ++;
        return null;
    }
	
	private boolean isEqualKey(HashEntry entry, Object key1, Object key2) {
        MultiKey multi = (MultiKey) entry.getKey();
        return
            multi.size() == 2 &&
            (key1 == null ? multi.getKey(0) == null : key1.equals(multi.getKey(0))) &&
            (key2 == null ? multi.getKey(1) == null : key2.equals(multi.getKey(1)));
    }
	
	/**
	 * 计算2个KEY的hash
	 * @param key1
	 * @param key2
	 * @return
	 */
	protected int hash(Object key1, Object key2) {
        int h = 0;
        if (key1 != null) {
            h ^= key1.hashCode();
        }
        if (key2 != null) {
            h ^= key2.hashCode();
        }
        h += ~(h << 9);
        h ^=  (h >>> 14);
        h +=  (h << 4);
        h ^=  (h >>> 10);
        return h;
    }
	
	protected int hash(Object key1, Object key2, Object key3, Object key4, Object key5) {
        int h = 0;
        if (key1 != null) {
            h ^= key1.hashCode();
        }
        if (key2 != null) {
            h ^= key2.hashCode();
        }
        if (key3 != null) {
            h ^= key3.hashCode();
        }
        if (key4 != null) {
            h ^= key4.hashCode();
        }
        if (key5 != null) {
            h ^= key5.hashCode();
        }
        h += ~(h << 9);
        h ^=  (h >>> 14);
        h +=  (h << 4);
        h ^=  (h >>> 10);
        return h;
    }
	
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object get(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object put(Object key, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(Map m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MapIterator mapIterator() {
		// TODO Auto-generated method stub
		return null;
	}
}
