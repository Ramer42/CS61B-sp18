package lab9;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Linpu Zhang
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (key == null) throw new IllegalArgumentException("calls get() with a null key");
        if (p == null) {
            return null;
        }
        int cmp = key.compareTo(p.key);
        if (cmp == 0) {
            return p.value;
        }
        else if (cmp < 0){
            return getHelper(key, p.left);
        } else {
            return getHelper(key, p.right);
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            size += 1;
            return new Node(key, value);
        }
        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            p.left = putHelper(key, value, p.left);
        }
        else if (cmp > 0) {
            p.right = putHelper(key, value, p.right);
        }
        else {
            p.value = value;
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        root = putHelper(key, value, root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    // Check if the map is empty.
    public boolean isEmpty() {
        return size() == 0;
    }

    // Return the minimum key of the map.
    public K min() {
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) return x;
        else                return min(x.left);
    }

    // Return the maximum key of the map.
    public K max() {
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null) return x;
        else                return min(x.right);
    }

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        if (isEmpty()) return new TreeSet<K>();
        Set<K> set = new TreeSet<K>();
        keys(root, set);
        return set;
    }

    private void keys(Node x, Set<K> set) {
        if (x == null) return;
        set.add(x.key);
        keys(x.left, set);
        keys(x.right, set);
    }

//    @Override
//    public Set<K> keySet() {
//        if (isEmpty()) return new TreeSet<K>();
//        return keys(min(), max());
//    }
//
//    public Set<K> keys(K lo, K hi) {
//        Set<K> set = new TreeSet<K>();
//        keys(root, set, lo, hi);
//        return set;
//    }
//
//    private void keys(Node x, Set<K> set, K lo, K hi) {
//        if (x == null) return;
//        int cmplo = lo.compareTo(x.key);
//        int cmphi = hi.compareTo(x.key);
//        if (cmplo < 0) keys(x.left, set, lo, hi);
//        if (cmplo <= 0 && cmphi >= 0) set.add(x.key);
//        if (cmphi > 0) keys(x.right, set, lo, hi);
//    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        if (key == null) throw new IllegalArgumentException("calls delete() with a null key");
        V value = get(key);
        if (value != null) root = remove(root, key);
        return value;
    }

    private Node remove(Node x, K key) {
        if (x == null) return null;

        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left  = remove(x.left,  key);
        else if (cmp > 0) x.right = remove(x.right, key);
        else {
            size -= 1;
            if (x.right == null) return x.left;
            if (x.left  == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = removeMin(t.right);
            x.left = t.left;
        }
        return x;
    }
    private void removeMin(){
        root = removeMin(root);
    }

    private Node removeMin(Node x){
        if (x == null) return null;
        if (x.left == null) {
            return x.right;
        }
        x.left = removeMin(x.left);
        return x;
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        if (get(key) == value) return remove(key);
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

//    private class KeyIterator implements Iterator<K> {
//        private Set<K> set;
//        private int wizardPosition;
//
//        public KeyIterator() {
//            set = keySet();
//            wizardPosition = 0;
//        }
//
//        public boolean hasNext() {
//            return !set.isEmpty();
//        }
//
//        public K next() {
//            K returnVal = keys[wizardPosition];
//            wizardPosition += 1;
//            return set.;
//        }
//    }

    public static void main(String[] args) {
        BSTMap<String, Integer> bstmap = new BSTMap<>();
        bstmap.put("hello", 5);
        bstmap.put("cat", 10);
        bstmap.put("fish", 22);
        bstmap.put("zebra", 90);
        Set set = bstmap.keySet();
        bstmap.removeMin();
        Set set1 = bstmap.keySet();
        for (String k : bstmap) {
            System.out.println(k);
        }
//        HashMap<String, Integer> hashmap = new HashMap<>();
//        hashmap.put("hello", 5);
//        hashmap.put("cat", 10);
//        for (String k, Integer i : hashmap) {
//            System.out.println(k);
//        }
    }
}
