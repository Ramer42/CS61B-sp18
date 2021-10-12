package lab9;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Your name here
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;
    private static final double MIN_LF = 0.25;

    private ArrayMap<K, V>[] buckets;
    private int size;

    private double loadFactor() {
        return size * 1.0 / buckets.length;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    public MyHashMap(int setSize) {
        buckets = new ArrayMap[setSize];
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /**
     * Computes the hash function of the given key. Consists of
     * computing the hashcode, followed by modding by the number of buckets.
     * To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    private int hash(K key, ArrayMap<K, V>[] buckets) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (buckets[hash(key)].containsKey(key)) {
            return buckets[hash(key)].get(key);
        }
        return null;
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (!buckets[hash(key)].containsKey(key)) {
            size += 1;
        }
        buckets[hash(key)].put(key, value);
        if (loadFactor() > MAX_LF) {
            resize(2);
        }
    }

//    private void resize(double rate) {
//        ArrayMap<K, V>[] bucketsOld = buckets;
//        buckets = new ArrayMap[(int)(buckets.length * rate)];
//        clear();
//        for (ArrayMap<K, V> ks : bucketsOld) {
//            for (K key : ks) {
//                put(key, bucketsOld[hash(key, bucketsOld)].get(key));
//            }
//        }
//    }

    private void resize(double rate) {
        MyHashMap<K, V> temp = new MyHashMap<K, V>((int) (buckets.length * rate));
        for (ArrayMap<K, V> ks : buckets) {
            for (K key : ks) {
                temp.put(key, get(key));
            }
        }
        this.buckets = temp.buckets;
        this.size = temp.size;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
//        throw new UnsupportedOperationException();
        Set<K> set = new TreeSet<K>();
        for (ArrayMap<K, V> ks : buckets) {
            for (K key : ks) {
                set.add(key);
            }
        }
        return set;
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        if (get(key) != null) {
            size -= 1;
            V removedValue = buckets[hash(key)].remove(key);
            if (loadFactor() < MIN_LF) {
                resize(0.5);
            }
            return removedValue;
        }
        return null;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        if (get(key) == value) {
            return remove(key);
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    public static void main(String[] args) {
        MyHashMap<String, Integer> hashmap = new MyHashMap<>();
//        hashmap.put("hello", 5);
////        hashmap.put("cat", 10);
////        hashmap.put("fish", 22);
////        hashmap.put("zebra", 90);
        for (int i = 0; i < 14; i++) {
            hashmap.put("hi" + i, 1);
            System.out.println(hashmap.loadFactor());
        }
        for (String key : hashmap) {
            System.out.println(key + '=' + hashmap.get(key));
        }
//        hashmap.remove("fish");
//        hashmap.remove("hello");
//        for (String key : hashmap) {
//            System.out.println(key + '=' + hashmap.get(key));
//        }
    }
}
