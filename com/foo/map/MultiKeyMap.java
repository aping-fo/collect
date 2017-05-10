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
	 * 所以要把hash方法写好，不然容易产生冲突
	  这个是JDK HashMap的 hash算法，其中如果是字符串，则用sun.misc带的hash
	  没有源码，也看不到里面是怎么实现的
	 是根据取模的来确定位置的，如果是string 的key，可能各平台的位置都不一样，这个地方要注意
	 win 和 linux
	 
	  final int hash(Object k) {
        int h = 0;
        if (useAltHashing) {
            if (k instanceof String) {
                return sun.misc.Hashing.stringHash32((String) k);
            }
            h = hashSeed;
        }
        h ^= k.hashCode();
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }
	 * @param hashCode
	 * @param dataSize
	 * @return
	 */
	private int hashIndex(int hashCode, int dataSize) {
        return hashCode & (dataSize - 1);
    }
	
	/**
	 * 在这里，大家可以理解MAP的顺序问题了
	 * 
	 * 这里可以看出，如果产生hash冲突，是根据equals来找值的
	 * 
	 * java8里hash冲突算法有所改变，在N个元素以内是采用链表结构，如果冲突元素超过N，则改用红黑树，来提高检索效率
	 * 
	 * 另外，注意hash扩容cpu问题
	 * 
	 * @param key1
	 * @param key2
	 * @param value
	 * @return
	 */
	public Object put(Object key1, Object key2, Object value) {
        int hashCode = hash(key1, key2);
        int index = hashIndex(hashCode, data.length);
        HashEntry entry = data[index]; //取改元素
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
	
	/**
	 * 获取值
	 * @param key1
	 * @param key2
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(Object key1, Object key2) {
		int hashCode = hash(key1, key2);
		int index = hashIndex(hashCode, data.length);
		HashEntry entry = data[index]; // 取改元素
		while (entry != null) {
			if (entry.hashCode == hashCode && isEqualKey(entry, key1, key2)) {
				return (T) entry.getValue();
			}
			entry = entry.next;
		}
		return null;
	}
	
	/**
	 * 移除元素
	 * @param key1
	 * @param key2
	 * @return
	 */
	public Object remove(Object key1, Object key2) {
        int hashCode = hash(key1, key2);
        int index = hashIndex(hashCode, data.length);
        HashEntry entry = data[index];
        HashEntry previous = null;
        while (entry != null) {
            if (entry.hashCode == hashCode && isEqualKey(entry, key1, key2)) {
                Object oldValue = entry.getValue();
                
                modCount ++;
                if (previous == null) { //链表中的第一个元素,取下一个
                    data[index] = entry.next;
                } else {//链表中的第N个元素
                    previous.next = entry.next;
                }
                //释放内存
                entry.next = null;
                entry.key = null;
                entry.value = null;
                return oldValue;
            }
            previous = entry;
            entry = entry.next;
        }
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
